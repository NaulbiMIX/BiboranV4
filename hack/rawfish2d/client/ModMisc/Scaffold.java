package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMovement.LongJump;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EnumFacing;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;

public class Scaffold extends Module{
	public static Module instance = null; 
	
	private EnumFacing var1;
	private int var2;
	private double[] arr1;
	private boolean var3;

	public Scaffold() {
		super("Scaffold", Keyboard.KEY_G, ModuleType.MISC);
		setDescription("Àâòîìàòè÷åñêè ñòàâèò ïîä âàìè áëîêè");
		instance = this;
		//from DD 06
		var1 = null;
		arr1 = null;
		var3 = true;
	}
	
	@Override
	public void preMotionUpdate() {
		double motionX = mc.thePlayer.motionX;
		double motionZ = mc.thePlayer.motionZ;
		if (motionX > 0.15) {
			motionX = 0.15;
		}
		if (motionX < -0.15) {
			motionX = -0.15;
		}
		if (motionZ > 0.15) {
			motionZ = 0.15;
		}
		if (motionZ < -0.15) {
			motionZ = -0.15;
		}
		int toFunc1[] = new int[] { MathHelper.floor_double(mc.thePlayer.posX + motionX * 4.0), MathHelper.floor_double(mc.thePlayer.posY - 0.2 - mc.thePlayer.yOffset), MathHelper.floor_double(mc.thePlayer.posZ + motionZ * 4.0) }; 
		arr1 = getForwardPos(toFunc1);
	}
	
	@Override
	public void postMotionUpdate() {
		double motionX = mc.thePlayer.motionX;
		double motionZ = mc.thePlayer.motionZ;
		if (motionX > 0.15) {
			motionX = 0.15;
		}
		if (motionX < -0.15) {
			motionX = -0.15;
		}
		if (motionZ > 0.15) {
			motionZ = 0.15;
		}
		if (motionZ < -0.15) {
			motionZ = -0.15;
		}
		int local_arr[] = new int[] { MathHelper.floor_double(mc.thePlayer.posX + motionX * 4.0), MathHelper.floor_double(mc.thePlayer.posY - 0.2 - mc.thePlayer.yOffset), MathHelper.floor_double(mc.thePlayer.posZ + motionZ * 4.0) };
		if (canReplaceThisBlock(local_arr) && arr1 != null) {
			if (arr1[3] == 1.0) {
				var1 = EnumFacing.NORTH;
			}
			else if (arr1[3] == 2.0) {
				var1 = EnumFacing.SOUTH;
			}
			else if (arr1[3] == 3.0) {
				var1 = EnumFacing.WEST;
			}
			else if (arr1[3] == 4.0) {
				var1 = EnumFacing.EAST;
			}
			else if (arr1[3] == 5.0) {
				var1 = EnumFacing.UP;
			}
			if (var3 && mc.thePlayer.motionY >= -0.10f && mc.thePlayer.motionY <= 0.20f) {
				new Thread(new Scaffold_Thread((int)arr1[0], (int)arr1[1], (int)arr1[2], var1), "ScaffoldThread").start();
			}
			else if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), (int)arr1[0], (int)arr1[1], (int)arr1[2], var1.ordinal(), Vec3.createVectorHelper((int)arr1[0], (int)arr1[1], (int)arr1[2]))) {
				mc.thePlayer.swingItem();
			}
		}
	}
	
	private double[] getForwardPos(int[] array) {
		if (!this.canReplaceThisBlock(new int[] { array[0], array[1], array[2] + 1 }) && this.canReplaceThisBlock(new int[] { array[0], array[1], array[2] - 1 })) {
			return new double[] { array[0], array[1], array[2] + 1, 1.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0], array[1], array[2] - 1 }) && this.canReplaceThisBlock(new int[] { array[0], array[1] + 1, array[2] - 1 })) {
			//MiscUtils.sendChatClient("§aFUNC1(1) x:" + (array[0]) + " y:" + (array[1]) + " z:" + (array[2] - 1));
			return new double[] { array[0], array[1], array[2] - 1, 2.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0] - 1, array[1], array[2] })) {
			return new double[] { array[0] - 1, array[1], array[2], 3.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0] + 1, array[1], array[2] })) {
			return new double[] { array[0] + 1, array[1], array[2], 4.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0], array[1] - 1, array[2] })) {
			return new double[] { array[0], array[1] - 1, array[2], 5.0 };
		}
		
		//???????????????
		
		if (!this.canReplaceThisBlock(new int[] { array[0] + 1, array[1], array[2] + 1 })) {
			//MiscUtils.sendChatClient("§aFUNC1(5) x:" + (array[0] + 1) + " y:" + (array[1]) + " z:" + (array[2] + 1));
			return new double[] { array[0] + 1, array[1], array[2] + 1, 1.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0] - 1, array[1], array[2] - 1 })) {
			//MiscUtils.sendChatClient("§eFUNC1(6) x:" + (array[0] - 1) + " y:" + (array[1]) + " z:" + (array[2] - 1));
			return new double[] { array[0] - 1, array[1], array[2] - 1, 2.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0] + 1, array[1], array[2] - 1 })) {
			//MiscUtils.sendChatClient("§eFUNC1(7) x:" + (array[0] + 1) + " y:" + (array[1]) + " z:" + (array[2] - 1));
			return new double[] { array[0] + 1, array[1], array[2] - 1, 4.0 };
		}
		if (!this.canReplaceThisBlock(new int[] { array[0] - 1, array[1], array[2] + 1 })) {
			//MiscUtils.sendChatClient("§eFUNC1(8) x:" + (array[0] - 1) + " y:" + (array[1]) + " z:" + (array[2] + 1));
			return new double[] { array[0] - 1, array[1], array[2] + 1, 3.0 };
		}
		
		return null;
	}
	
	private boolean canReplaceThisBlock(final int[] array) {
		return	(mc.theWorld.isAirBlock(array[0], array[1], array[2]) ||
				mc.theWorld.getBlockMaterial(array[0], array[1], array[2]).isLiquid() ||
				mc.theWorld.getBlockMaterial(array[0], array[1], array[2]) == Material.fire ||
				mc.theWorld.getBlockMaterial(array[0], array[1], array[2]) == Material.water ||
				mc.theWorld.getBlockMaterial(array[0], array[1], array[2]) == Material.lava);
	}
}
