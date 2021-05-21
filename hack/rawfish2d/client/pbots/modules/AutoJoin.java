package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class AutoJoin extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AutoJoin", "gToggled", true);
	private static PBotVar slot = new PBotVar("AutoJoin", "slot", 4);
	private static PBotVar compassSlot = new PBotVar("AutoJoin", "compassSlot", 0);
	
	private TimeHelper itemSwitch = new TimeHelper();
	private TimeHelper packetLook = new TimeHelper();
	private TimeHelper packetPlace = new TimeHelper();
	private TimeHelper packetClick = new TimeHelper();
	
	private TimeHelper joinchecker = new TimeHelper();
	
	private boolean switched = false;
	private boolean looked = false;
	private boolean placed = false;
	private boolean clicked = false;
	
	public AutoJoin(PBotThread bot) {
		super(bot, "AutoJoin");
		toggled = gToggled.get(false);
		
		//toggled = false;
	}
	
	@Override
	public void onEnable() {
		itemSwitch.reset();
		packetLook.reset();
		packetPlace.reset();
		packetClick.reset();
		switched = false;
		looked = false;
		placed = false;
		clicked = false;
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		/*
		bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8]");
		bot.sendChatClient("inLobby: " + bot.inLobby);
		*/
		/*
		if(bot.instance.name.contains("190")) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8]");
			bot.sendChatClient("switched: " + this.switched);
			bot.sendChatClient("looked: " + this.looked);
			bot.sendChatClient("placed: " + this.placed);
			bot.sendChatClient("clicked: " + this.clicked);
			bot.sendChatClient("==================");
			bot.sendChatClient("bot.onGround: " + bot.onGround);
			bot.sendChatClient("bot.inLobby: " + bot.inLobby);
			bot.sendChatClient("bot.onSurvivalServer: " + bot.onSurvivalServer);
			bot.sendChatClient("bot.loggined: " + bot.loggined);
			bot.sendChatClient("==================");
			bot.sendChatClient("bot.windowId: " + bot.windowId);
			bot.sendChatClient("bot.ContainerWindowId: " + this.bot.ContainerWindowId);
		}
		*/
		
		if(bot.onSurvivalServer)
			return;
		
		long delay = 700l; 
		if(this.itemSwitch.hasReached(delay) && !this.switched && !this.looked && !this.placed && !this.clicked) {
			itemSwitch.reset();
			packetLook.reset();
			packetPlace.reset();
			packetClick.reset();
			this.switched = true;
			
			bot.compassSlot = 0;
			bot.changeSlot(bot.compassSlot);
			joinchecker.reset();
			return;
		}
		if(this.packetLook.hasReached(delay) && this.switched && !this.looked && !this.placed && !this.clicked) {
			packetLook.reset();
			packetPlace.reset();
			packetClick.reset();
			this.looked = true;
			bot.sendPacket(new Packet12PlayerLook(297.33f, 11.98f, bot.onGround));
			joinchecker.reset();
			return;
		}
		if(this.packetPlace.hasReached(delay) && this.switched && this.looked && !this.placed && !this.clicked) {
			packetPlace.reset();
			packetClick.reset();
			this.placed = true;
			bot.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
			joinchecker.reset();
			return;
		}
		
		if(this.packetClick.hasReached(delay) && this.switched && this.looked && this.placed && !this.clicked) {
			packetClick.reset();
			this.clicked = true;
			//bot.sendChatClient("bot.ContainerWindowId: " + bot.ContainerWindowId);
			//bot.sendChatClient("bot.windowId: " + bot.windowId);
			bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId, slot.get(0), 0, 0, (ItemStack)null, (short)1));
			joinchecker.reset();
			return;
		}
		
		if(joinchecker.hasReached(6000L) && bot.ready) {
			//bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " +  "hasn't joined after " + (time / 1000) + " seconds. Retrying...");
			//System.out.println("Bot:" + bot.instance.name + " sw:" + switched + " pl:" + placed + " cl:" + clicked + " inLobby:" + bot.inLobby + " onSurvivalServer:" + bot.onSurvivalServer);
			itemSwitch.reset();
			packetLook.reset();
			packetPlace.reset();
			packetClick.reset();
			switched = false;
			looked = false;
			placed = false;
			clicked = false;
			bot.onSurvivalServer = false;
			bot.inLobby = true;
			
			bot.sendPacket(new Packet12PlayerLook(265.0f, 0.0f, bot.onGround));
			joinchecker.reset();
		}
	}
}
