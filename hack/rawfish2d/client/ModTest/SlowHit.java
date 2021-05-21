package hack.rawfish2d.client.ModTest;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.ItemAppleGold;
import net.minecraft.src.ItemBucketMilk;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;

public class SlowHit extends Module {
	public static CopyOnWriteArrayList<Packet> list = new CopyOnWriteArrayList<Packet>();
	public static boolean sending;
	public static DoubleValue limit;
	public static Module instance = null;

	public SlowHit() {
		super("SlowHit", 0, ModuleType.TEST);
		setDescription("Choke + ÷óòîê ìàãèè + ??? = SlowHit êàê â DoomsDay B2");
		limit = new DoubleValue(100f, 0f, 500f);
		sending = false;
		this.elements.add(new NewSlider(this, "Limit", limit, 0, 0, true));
	}

	@Override
	public void onEnable() {
		sending = false;
		list.clear();
	}
	
	@Override
	public void onDisable() {
		setName("SlowHit");
		sendAll();
	}
	
	public void sendAll() {
		this.sending = true;
		
		/*
		Thread th = new Thread(new Runnable() {
			public void run() {
				for(Packet p : list) {
					mc.getNetHandler().addToSendQueue(p);
					MiscUtils.sleep(2L);
				}
				list.clear();
			}
		}, "send ShlowHit");
		th.start();
		*/
		
		for(Packet p : list) {
			mc.getNetHandler().addToSendQueue(p);
			MiscUtils.sleep(2L);
		}
		list.clear();
		
		this.sending = false;
	}

	public boolean shouldSendMotion() {
		return !toggled ||
				(mc.thePlayer.isUsingItem() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword));
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if(!sending && mc.thePlayer != null) {
			if (mc.thePlayer.inventory.getCurrentItem() == null)
				return;
			
			if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion ||
					mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketMilk ||
					mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood ||
					mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAppleGold) {
				if(mc.thePlayer.getItemInUseDuration() > 0) {
					return;
				}
			}
			
			if(mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0) {
				if(list.size() < limit.getValue() && !this.sending) {
					setName("§9SlowHit §8[§e" + list.size() + "§0/§e" + limit.getValue() + "§8]");
					list.add(packet);
					doNotSendNextPacket(true);
				}
				else if(list.size() >= limit.getValue() && !this.sending) {
					sendAll();
				}
			}
			else {
				setName("§9SlowHit §8[§cMoving§8]");
				sendAll();
			}
		}
	}
}
