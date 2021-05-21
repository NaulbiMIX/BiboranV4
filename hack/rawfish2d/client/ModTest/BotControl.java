package hack.rawfish2d.client.ModTest;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.ListBox;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.modules.BPM;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.SharedIntList;
import hack.rawfish2d.client.utils.SharedStringList;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.TcpConnection;

public class BotControl extends Module {
	
	public static Module instance = null;
	public static Vector3f origin;
	public static Vector3f botpos;
	public static PBot bot;
	
	public static BoolValue chunkPacketRedirect;
	public static BoolValue autoEnable;
	
	public static TimeHelper time;
	public static SharedStringList list; 	
	private ListBox listbox;
	
	public BotControl() {
		super("BotControl", 0, ModuleType.TEST);
		setDescription("”правление ботом");
		instance = this;
		
		list = new SharedStringList();
		
		origin = new Vector3f(0, 0, 0);
		botpos = new Vector3f(0, 0, 0);
		
		chunkPacketRedirect = new BoolValue(false);
		autoEnable = new BoolValue(false);
		time = new TimeHelper();
		
		elements.add(new CheckBox(this, "Packet 51/56 redirect", chunkPacketRedirect, 0, 0));
		elements.add(new CheckBox(this, "Auto enable", autoEnable, 0, 10));
		
		listbox = new ListBox(this, "Bot name", list, 0, 20);
		elements.add(listbox);
	}
	
	@Override
	public void onUpdate() {
		if(time.hasReached(1000L)) {
			if(mc.netClientHandler != null) {
				if(mc.netClientHandler.getNetManager() != null) {
					mc.netClientHandler.getNetManager().addToSendQueue(new Packet11PlayerPosition(origin.x, origin.y, origin.y + PBotThread.yOffset, origin.z, true));
				}
			}
			time.reset();
		}
	}
	
	@Override
	public void onDisable() {
		bot = null;
		mc.thePlayer.setPosition(origin.x, origin.y, origin.z);
	}
	
	@Override
	public void onEnable() {
		//bot = PBotCmd.instance.getBotByName("IWasTesting0".toLowerCase());
		
		bot = PBotCmd.instance.getBotByName(listbox.getValue());
		
		if(bot == null)
			bot = BPM.lastBot;
		
		//bot = BasicPacketManager.lastBot;
		
		origin.x = (float) mc.thePlayer.posX;
		origin.y = (float) mc.thePlayer.posY;
		origin.z = (float) mc.thePlayer.posZ;
		
		if(bot == null) {
			toggle();
			return;
		}
		if(bot.pbotth == null) {
			toggle();
			return;
		}
		if(!bot.pbotth.enabled || !bot.pbotth.ready || bot.pbotth.stop) {
			toggle();
			return;
		}
		
		botpos.x = (float) bot.pbotth.x;
		botpos.y = (float) bot.pbotth.y;
		botpos.z = (float) bot.pbotth.z;
		
		mc.thePlayer.setPosition(botpos.x, botpos.y + bot.pbotth.yOffset, botpos.z);
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(bot == null) {
			toggle();
			return;
		}
		if(bot.pbotth == null) {
			toggle();
			return;
		}
		if(!bot.pbotth.enabled || !bot.pbotth.ready || bot.pbotth.stop) {
			toggle();
			return;
		}
		
		if(packet instanceof Packet28EntityVelocity) {
			Packet28EntityVelocity p = (Packet28EntityVelocity)packet;
			if(p.entityId == bot.pbotth.entityId) {
				if(mc.thePlayer != null)
					p.entityId = mc.thePlayer.entityId;
			}
		}
		return;
	}
	
	@Override
	public boolean DontProcessPacket(Packet packet) {
		//return false;
		return true;
	}
	
	public static void clientProcessPacket(Packet packet) {
		TcpConnection tcp = (TcpConnection)Client.getInstance().mc.getNetHandler().getNetManager();
		
		if (!tcp.isServerTerminating)
		{
			if (packet.canProcessAsync() && tcp.theNetHandler.canProcessPacketsAsync()) {
				tcp.field_74490_x = 0;
				packet.processPacket(tcp.theNetHandler);
			}
			else {
				tcp.readPackets.add(packet);
			}
		}
	}

	public static boolean packetBotReadToClient(Packet packet, PBotThread pbotth) {
		if(BotControl.chunkPacketRedirect.getValue()) {
			if(		packet.getPacketId() == 51 ||
					packet.getPacketId() == 56) {
				if(BPM.lastBot.name.equalsIgnoreCase(pbotth.instance.name)) {
					Client.getInstance().onPacket(packet);
					
					if(Client.getInstance().mc == null) {
						return false;
					}
					if(Client.getInstance().mc.getNetHandler() == null) {
						return false;
					}
					if(Client.getInstance().mc.getNetHandler().getNetManager() == null) {
						return false;
					}
					
					BotControl.clientProcessPacket(packet);
					return false;
				}
			}
		}
		
		if(instance.isToggled()) {
			PBot bot = BotControl.bot;
			if(bot != null) {
				if(bot.name.equalsIgnoreCase(pbotth.instance.name)) {
					Client.getInstance().onPacket(packet);
					
					if(Client.getInstance().mc == null) {
						return false;
					}
					if(Client.getInstance().mc.getNetHandler() == null) {
						return false;
					}
					if(Client.getInstance().mc.getNetHandler().getNetManager() == null) {
						return false;
					}
					
					BotControl.clientProcessPacket(packet);
					return false;
				}
			}
		}
		return true;
	}

	public static boolean packetClientSendToBot(Packet packet) {
		if(instance.isToggled()) {
			PBot bot = BotControl.bot;
			if(bot != null) {
				bot.pbotth.sendPacket(packet);
				
				//client sends only Packet10Flying and Packet0KeepAlive
				//it also need to send Packet11PlayerPostition once for 1000ms
				if(packet.getPacketId() != 10 && packet.getPacketId() != 0)
					return false;
			}
		}
		return true;
	}
}
