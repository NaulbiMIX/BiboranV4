package hack.rawfish2d.client.ModMovement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.pbots.utils.PRepeaterNode;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
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
import net.minecraft.src.RenderManager;

public class Blink extends Module{
	public static Module instance = null;
	
	public CopyOnWriteArrayList<Packet> list = new CopyOnWriteArrayList<Packet>();
	
	private BoolValue repeat;
	private BoolValue limitMode;
	private BoolValue useThread;
	private BoolValue sendP10fly;
	private BoolValue windowFix;
	private DoubleValue limit;
	private DoubleValue delay;
	
	public Blink() {
		super("Blink", Keyboard.KEY_Z, ModuleType.MOVEMENT);
		setDescription("Магическая хренотень");
		instance = this;
		
		repeat = new BoolValue(false);
		limitMode = new BoolValue(true);
		useThread = new BoolValue(false);
		sendP10fly = new BoolValue(false);
		limit = new DoubleValue(50, 0, 300);
		delay = new DoubleValue(4, 0, 50);
		
		this.elements.add(new RadioBox(this, "Режим повтора", repeat, 0, 0));
		this.elements.add(new RadioBox(this, "Режим лимита", limitMode, 0, 10));
		this.elements.add(new CheckBox(this, "Использовать поток", useThread, 0, 20));
		this.elements.add(new CheckBox(this, "Отправлять P10Flying", sendP10fly, 0, 30));
		this.elements.add(new CheckBox(this, "Спец. режим для инвентаря", windowFix, 0, 40));
		this.elements.add(new NewSlider(this, "Лимит пакетов", limit, 0, 40, true));
		this.elements.add(new NewSlider(this, "Задержка между пакетами", delay, 0, 60, true));
	}
	
	private void repeater() {
		this.toggled = false;
		int slot = mc.thePlayer.inventory.currentItem;
		mc.thePlayer.sendQueue.addToSendQueue(new Packet16BlockItemSwitch(slot));
		
		for (PRepeaterNode node : PacketRepeaterAdd.arr) {
			Packet packet = node.getPacket();

			if(packet instanceof Packet102WindowClick) {
				Packet102WindowClick p = (Packet102WindowClick)packet;
				p.window_Id = Client.getInstance().window_id + 1;
			}
			
			if(sendP10fly.getValue()) {
				mc.thePlayer.sendQueue.addToSendQueue(new Packet10Flying(mc.thePlayer.onGround));
				MiscUtils.sleep(2L);
			}
			
			mc.thePlayer.sendQueue.addToSendQueue(packet);
			if(!sendP10fly.getValue()) {
				MiscUtils.sleep((long)delay.getValue());
			}
		}
		
		mc.thePlayer.inventory.currentItem = slot;
		mc.thePlayer.sendQueue.addToSendQueue(new Packet16BlockItemSwitch(slot));
	}
	
	private Packet makePacketRelative(Packet packet) {
		return null;
	}
	
	@Override
	public void onEnable() {
		if(repeat.getValue()) {
			//Pocket Bow
			if(useThread.getValue()) {
				Thread th = new Thread(new Runnable() {
					public void run() {
						repeater();
					}
				}, "Blink Repeater");
				th.start();
			}
			else {
				repeater();
			}
			setName("Blink");
			
			//Pocket Bow
			/*
			this.toggled = false;
			int slot = mc.thePlayer.inventory.currentItem;
			mc.thePlayer.sendQueue.addToSendQueue(new Packet16BlockItemSwitch(slot));
			
			Thread th = new Thread(new Runnable() {
				public void run() {
					for (PRepeaterNode node : PacketRepeaterAdd.arr) {
						Packet packet = node.getPacket();
						
						if(packet instanceof Packet102WindowClick) {
							Packet102WindowClick p = (Packet102WindowClick)packet;
							p.window_Id = Client.getInstance().window_id + 1;
						}
						
						MiscUtils.sleep(4L);
						mc.thePlayer.sendQueue.addToSendQueue(packet);
					}
				}
			}, "Blink Repeater #1");
			th.start();
			mc.thePlayer.inventory.currentItem = slot;
			mc.thePlayer.sendQueue.addToSendQueue(new Packet16BlockItemSwitch(slot));
			*/
		}
		else {
			sendAll();
			setName("Blink");
			
			fake_x = mc.thePlayer.posX;
			fake_y = mc.thePlayer.posY - 1.62;
			fake_z = mc.thePlayer.posZ;
			
			fake_pitch = mc.thePlayer.rotationPitch;
			fake_yaw = mc.thePlayer.rotationYaw;
		}
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if (	packet instanceof Packet15Place ||
				packet instanceof Packet14BlockDig ||
				packet instanceof Packet18Animation ||
				packet instanceof Packet19EntityAction ||
				packet instanceof Packet7UseEntity ||
				packet instanceof Packet11PlayerPosition ||
				packet instanceof Packet12PlayerLook ||
				packet instanceof Packet13PlayerLookMove ||
				packet instanceof Packet16BlockItemSwitch ||
				packet instanceof Packet102WindowClick ||
				packet instanceof Packet3Chat ||
				packet instanceof Packet107CreativeSetSlot) {
			
			if(limitMode.getValue() && limit.getValue() <= list.size()) {
				toggle();
				return;
			}
			list.add(packet);
			setName("Blink [" + list.size() + "]");
			doNotSendNextPacket(true);
		}
	}
	
	private void sendAll() {
		for (Packet packet : list) {
			mc.thePlayer.sendQueue.addToSendQueue(packet);
			MiscUtils.sleep((long)delay.getValue());
		}
		list.clear();
		fake_y = 0;
		setName("Blink");
	}
	
	@Override
	public void onDisable() {
		sendAll();
		fake_y = 0;
	}
	
	private double fake_x = 0;
	private double fake_y = 0;
	private double fake_z = 0;
	
	private double fake_pitch = 0;
	private double fake_yaw = 0;
	
	@Override
	public void onRender() {
		if(fake_y != 0) {
			double render_x;
			double render_y;
			double render_z;
			
			render_x = (fake_x + (fake_x - fake_x) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
			render_y = (fake_y + (fake_y - fake_y) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
			render_z = (fake_z + (fake_z - fake_z) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
			
			R3DUtils.drawEntityESP(render_x, render_y, render_z, 0.5f, 1f, 1.0f, 0f, 0f, 0.15F, 0F, 0F, 0F, 1F, 2F);
		}
	}
}
