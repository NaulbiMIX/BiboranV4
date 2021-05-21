package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EnumFacing;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Vec3;

public class Scaffold_Thread implements Runnable {
	private int x;
	private int y;
	private int z;
	private static EnumFacing facing;
	private Minecraft mc = Client.getInstance().mc;

	public Scaffold_Thread(int par1, int par2, int par3, EnumFacing par4) {
		x = par1;
		y = par2;
		z = par3;
		facing = par4;
	}

	@Override
	public void run() {
		try {
			scaffold();
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void scaffold() throws InterruptedException {
		ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
		
		if(isNormalBlock(itemstack)) {
			scaffoldCurrent(itemstack, mc.thePlayer.inventory.currentItem);
		}
		else {
			for (int i = 44; i >= 9; --i) {
				ItemStack stack = Client.getInstance().mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				
				if (stack != null && i >= 36 && i <= 44 && stack.getItem() instanceof ItemBlock) {
					if(isNormalBlock(stack))
						scaffoldInventory(stack, i);
				}
			}
		}
	}
	
	private void scaffoldInventory(ItemStack stack, int slot) {
		mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(slot - 36));
		MiscUtils.sleep(4L);
		
		Vector2f vec = MiscUtils.getBlockAngles(x + 0.5, y + 0.5f, z, Client.getInstance().mc.thePlayer.posX, Client.getInstance().mc.thePlayer.posY, Client.getInstance().mc.thePlayer.posZ);
		MiscUtils.setAngles(vec.x, vec.y, true);
		mc.getNetHandler().addToSendQueue(new Packet12PlayerLook(vec.y, vec.x, Client.getInstance().mc.thePlayer.onGround));
		MiscUtils.sleep(4L);
		
		if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, x, y, z, facing.ordinal(), Vec3.createVectorHelper(x, y, z))) {
			mc.getNetHandler().addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
		}
		
		if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, x, y, z, facing.ordinal(), Vec3.createVectorHelper(x, y, z))) {
			mc.getNetHandler().addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
		}
		
		mc.getNetHandler().addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 65281));
		MiscUtils.sleep(4L);
		mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(mc.thePlayer.inventory.currentItem));
	}
	
	private void scaffoldCurrent(ItemStack stack, int slot) {
		//mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(slot));
		//MiscUtils.sleep(4L);
		
		Vector2f vec = MiscUtils.getBlockAngles(x + 0.5, y + 0.5f, z, Client.getInstance().mc.thePlayer.posX, Client.getInstance().mc.thePlayer.posY, Client.getInstance().mc.thePlayer.posZ);
		MiscUtils.setAngles(vec.x, vec.y, true);
		mc.getNetHandler().addToSendQueue(new Packet12PlayerLook(vec.y, vec.x, Client.getInstance().mc.thePlayer.onGround));
		MiscUtils.sleep(4L);
		
		if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, x, y, z, facing.ordinal(), Vec3.createVectorHelper(x, y, z))) {
			mc.getNetHandler().addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
		}
		
		if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, stack, x, y, z, facing.ordinal(), Vec3.createVectorHelper(x, y, z))) {
			mc.getNetHandler().addToSendQueue(new Packet18Animation(mc.thePlayer, 1));
		}
		
		mc.getNetHandler().addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 65281));
		MiscUtils.sleep(4L);
		//mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(mc.thePlayer.inventory.currentItem));
	}
	
	private boolean isNormalBlock(ItemStack itemStack) {
		int id;
		
		if(itemStack == null)
			return false;
		else
			id = itemStack.itemID;
		
		if(id >= 1 && id <= 5)
			return true;
		
		if(id == 7)
			return true;
		
		if(id >= 14 && id <= 24)
			return true;
		
		if(id == 35)
			return true;
		
		if(id >= 41 && id <= 49)
			return true;
		
		if(id == 52 || id == 53)
			return true;
		
		if((id >= 56 && id <= 58) || id == 60 || id == 67 || id == 73 || id == 74)
			return true;
		
		if(id >= 79 && id <= 82)
			return true;
		
		if(id != 85 && id != 90 && id >= 84 && id <= 91)
			return true;
		
		if((id >= 97 && id <= 103) || id == 108 || id == 109 || id == 110 || id == 112 || id == 114)
			return true;
		
		if(id == 120 || id == 121 || (id >= 123 && id <= 126))
			return true;
		
		if(id == 128 || id == 129)
			return true;
		
		if(id >= 133 && id <= 136)
			return true;
		
		if(id == 152)
			return true;
		
		if(id == 155)
			return true;
		
		return false;
	}
}
