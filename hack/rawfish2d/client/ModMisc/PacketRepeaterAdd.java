package hack.rawfish2d.client.ModMisc;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.pbots.utils.PRepeaterNode;

import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class PacketRepeaterAdd extends Module {
	public static Module instance = null;
	public static CopyOnWriteArrayList<PRepeaterNode> arr = new CopyOnWriteArrayList();
	public static double origin_x;
	public static double origin_minY;
	public static double origin_y;
	public static double origin_z;
	
	private TimeHelper time = new TimeHelper();
	private BoolValue client_repeat;
	
	public PacketRepeaterAdd() {
		super("PRepeater", 0, ModuleType.MISC);
		setDescription("Запоминает исходящие пакеты, чтоб бот/blink мог их повторить");
		toggled = false;
		time.reset();
		instance = this;
		
		client_repeat = new BoolValue(false);
		this.elements.add(new CheckBox(this, "Client Repeat", client_repeat, 0, 0));
	}
	
	@Override
	public void onDisable() {
		toggled = false;
		time.reset();
		this.setName("PRepeater");
		
		System.out.println("PRepeater disabled!");
	}	
	
	@Override
	public void onEnable() {
		arr.clear();
		toggled = true;
		time.reset();
		
		origin_x = mc.thePlayer.posX;
		origin_minY = mc.thePlayer.boundingBox.minY;
		origin_y = mc.thePlayer.posY;
		origin_z = mc.thePlayer.posZ;
		
		System.out.println("PRepeater enabled!");
		//this.setName("PRepeater §c[§aAdd§c]");
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		
		if(mc.thePlayer == null)
			return;
		
		if(client_repeat.getValue()) {
			//pocket bow
			if(packet == null)
				return;
			
			if(packet.getPacketId() == 11 || packet.getPacketId() == 10 || packet.getPacketId() == 12) {
				return;
			}
			
			long delay = time.getCurrentMS() - time.getLastMS();
			arr.add(new PRepeaterNode(packet, delay, mc.thePlayer.onGround));
			time.reset();
			this.setName("PRepeater §c[§a" + arr.size() + "§c]");
			//pocket bow
		}
		else {
			//Packet Repeater For Bots
			if(packet == null)
				return;
			
			long delay = time.getCurrentMS() - time.getLastMS();
			arr.add(new PRepeaterNode(packet, delay, mc.thePlayer.onGround));
			time.reset();
			this.setName("PRepeater §c[§a" + arr.size() + "§c]");
			//Packet Repeater For Bots
		}
		
		if(packet.getPacketId() == 15) {
			//System.out.println(packet.toString());
			Packet15Place p = (Packet15Place)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet15Place(" + p.xPosition + ", " + p.yPosition + ", " + p.zPosition + ", " + p.direction + ", " + p.itemStack + ", " + p.xOffset + ", " + p.xOffset + ", " + p.xOffset + "));");
		}
		else if(packet.getPacketId() == 102) {
			//System.out.println(packet.toString());
			Packet102WindowClick p = (Packet102WindowClick)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet102WindowClick(" + p.window_Id + ", " + p.inventorySlot + ", " + p.mouseClick + ", " + p.holdingShift + ", " + p.itemStack + ", " + p.action + "));");
		}
		else if(packet.getPacketId() == 7) {
			Packet7UseEntity p = (Packet7UseEntity)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet7UseEntity(bot.entityId, " + p.targetEntity + ", " + p.isLeftClick + "));");
		}
		else if(packet.getPacketId() == 11) {
			Packet11PlayerPosition p = (Packet11PlayerPosition)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet11PlayerPosition(" + p.xPosition + ", " + p.yPosition + ", " + p.stance + ", " + p.zPosition + ", " + p.onGround + "));");
		}
		else if(packet.getPacketId() == 13) {
			Packet13PlayerLookMove p = (Packet13PlayerLookMove)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet13PlayerLookMove(" + p.xPosition + ", " + p.yPosition + ", " + p.stance + ", " + p.zPosition + ", " + p.yaw + ", " + p.pitch + ", " + p.onGround + "));");
		}
		else if(packet.getPacketId() == 10) {
			Packet10Flying p = (Packet10Flying)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet10Flying(" + p.onGround + "));");
		}
		else if(packet.getPacketId() == 18) {
			Packet18Animation p = (Packet18Animation)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet18Animation(bot.entityId, " + p.animate + "));");
		}
		else if(packet.getPacketId() == 19) {
			Packet19EntityAction p = (Packet19EntityAction)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet19EntityAction(bot.entityId, " + p.state + "));");
		}
		else if(packet.getPacketId() == 12) {
			Packet12PlayerLook p = (Packet12PlayerLook)packet;
			System.out.println("bot.connection.addToSendQueue(new Packet12PlayerLook(" + p.yaw + ", " + p.pitch + ", " + p.onGround + "));");
		}
		else
			System.out.println(packet.toString());
		
		System.out.println("MiscUtils.sleep(50L);");
		
	}
}
