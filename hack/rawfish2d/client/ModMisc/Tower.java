package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Potion;

public class Tower extends Module{
	public static Module instance = null; 
	private int n;
	private BoolValue bNewTower;
	
	public Tower() {
		super("Tower", Keyboard.KEY_Y, ModuleType.MISC);
		setDescription("Очень быстро делает столб вверх");
		instance = this;
		bNewTower = new BoolValue(true);
		this.elements.add(new CheckBox(this, "New Tower", bNewTower, 0, 0));
		//from DD
	}
	
	@Override
	public void onDisable() {
		n = 0;
	}
	
	private void newTower() {
		if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
			Client.getInstance().getRotationUtils().setPitch(90);
			mc.thePlayer.motionY = 0.0;
			mc.thePlayer.motionX = 0.0;
			mc.thePlayer.motionZ = 0.0;
			
			++n;
			if (n == 2) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY + 0.42, this.mc.thePlayer.posY + 0.42, this.mc.thePlayer.posZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), this.mc.thePlayer.onGround));
				MiscUtils.sleep(4L);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY + 0.75, this.mc.thePlayer.posY + 0.75, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
				MiscUtils.sleep(4L);
			}
			if (n == 3) {
				mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ);
			}
			if(n == 4) {
				n = 1;
			}
			
			int n1;
			if (mc.thePlayer.posX < 0.0) {
				n1 = -1;
			}
			else {
				n1 = 0;
			}
			int n2;
			if (mc.thePlayer.posZ < 0.0) {
				n2 = -1;
			}
			else {
				n2 = 0;
			}
			int n3 = (int)mc.thePlayer.posX + n1;
			int n4 = (int)mc.thePlayer.posY - 3;
			int n5 = (int)mc.thePlayer.posZ + n2;
			mc.thePlayer.swingItem();
			mc.theWorld.setBlock(n3, n4 + 1, n5, mc.thePlayer.inventory.getCurrentItem().itemID);
			mc.getNetHandler().addToSendQueue(new Packet15Place(n3, n4, n5, 1, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
		}
		else {
			n = 0;
		}
	}
	
	private void oldTower() {
		if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
			Client.getInstance().getRotationUtils().setPitch(90);
			mc.thePlayer.motionY = 0.0;
			mc.thePlayer.motionX = 0.0;
			mc.thePlayer.motionZ = 0.0;
			
			++n;
			if (n == 2) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY + 0.50, this.mc.thePlayer.posY + 0.50, this.mc.thePlayer.posZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), this.mc.thePlayer.onGround));
				MiscUtils.sleep(4L);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY + 0.50, this.mc.thePlayer.posY + 0.50, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
				MiscUtils.sleep(4L);
			}
			if (n == 3) {
				mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ);
				n = 1;
			}
			
			int n1;
			if (mc.thePlayer.posX < 0.0) {
				n1 = -1;
			}
			else {
				n1 = 0;
			}
			int n2;
			if (mc.thePlayer.posZ < 0.0) {
				n2 = -1;
			}
			else {
				n2 = 0;
			}
			int n3 = (int)mc.thePlayer.posX + n1;
			int n4 = (int)mc.thePlayer.posY - 3;
			int n5 = (int)mc.thePlayer.posZ + n2;
			mc.thePlayer.swingItem();
			mc.theWorld.setBlock(n3, n4 + 1, n5, mc.thePlayer.inventory.getCurrentItem().itemID);
			mc.getNetHandler().addToSendQueue(new Packet15Place(n3, n4, n5, 1, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
		}
		else {
			n = 0;
		}
	}
	
	@Override
	public void preMotionUpdate() {
		if(this.bNewTower.getValue()) {
			this.newTower();
		}
		else {
			this.oldTower();
		}
	}
}
