package hack.rawfish2d.client.ModMisc;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Potion;

public class ChestFill extends Module{
	
	public static int count = -1;
	public static boolean flag = false;
	public static boolean flagOld = false;
	private BoolValue dupe;
	private BoolValue fill;
	private DoubleValue delay;
	private BoolValue dupeHand;
	
	public ChestFill() {
		super("ChestDupe", 0, ModuleType.MISC);
		setDescription("Раздюпывает первый слот в сундук");
		dupe = new BoolValue(true);
		fill = new BoolValue(false);
		dupeHand = new BoolValue(false);
		delay = new DoubleValue(30, 2, 200);
		
		elements.add(new RadioBox(this, "Дюп сундука", dupe, 0, 0));
		elements.add(new RadioBox(this, "Заполнение сундука с инвентаря", fill, 0, 10));
		elements.add(new CheckBox(this, "Дюпать не текущий предмет а первый слот", dupeHand, 0, 20));
		elements.add(new NewSlider(this, "Задержка", delay, 0, 30, true));
	}
	
	@Override
	public void onDisable() {
		flag = true;
		flagOld = false;
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void preMotionUpdate() {
		fill();
	}
	
	@Override
	public void onUpdate() {
	}
	
	@Override
	public void postMotionUpdate() {

	}
	
	@Override
	public void onRenderHand() {

	}
	
	@Override
	public void onRender() {
		
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		
	}
	
	@Override
	public void onRenderOverlay() {
		
	}
	
	public void fill() {
		if(mc.thePlayer.openContainer instanceof ContainerChest) {
			flag = true;
			if(flag != flagOld)
			{		
				Thread th = new Thread(new Runnable() {
					public void run() {
						int curSlot;
						
						if(!dupeHand.getValue()) {
							curSlot = mc.thePlayer.inventory.currentItem;
						}
						else {
							curSlot = 0;
						}
						
						int newSlot = 81 + curSlot;
						try {
							if(mc.thePlayer.inventory.getStackInSlot(curSlot) != null)
							{
								ItemStack item = mc.thePlayer.inventory.getStackInSlot(curSlot).copy();
								item.stackSize = 64;
								
								long ldelay = (long)delay.getValue();
								
								//ChestFill
								if(fill.getValue()) {
									for(int a = 54; a < 90; ++a) {
										if(a != 81) {
											MiscUtils.sleep(ldelay);
											mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, a, 0, 1, Client.getInstance().mc.thePlayer);
										}
									}
								}
								
								//ChestDupe
								if(dupe.getValue()){
									for(int a = 0; a < 54; ++a) {
										List list = mc.thePlayer.openContainer.getInventory();
										Object obj = list.get(a);
										
										if(obj == null) {
											mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, newSlot, 0, 3, Client.getInstance().mc.thePlayer);
											Thread.sleep((ldelay + 1) / 2);
											mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, a, 0, 0, Client.getInstance().mc.thePlayer);
											Thread.sleep((ldelay + 1) / 2);
										}
									}
								}
							}
						}catch(Exception e) {e.printStackTrace();}
					}
				}, "Chest Fill/Dupe");
				th.start();
			}
		}
		else
			flag = false;
		
		flagOld = flag;
	}
}
