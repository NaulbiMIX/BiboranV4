package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockLadder;
import net.minecraft.src.BlockVine;
import net.minecraft.src.MathHelper;

public class FastLadder extends Module {

	private double motion = 0.28999999;
	private int state = 0;
	
	private BoolValue mode1;
	private BoolValue mode2;
	private BoolValue mode3;
	private BoolValue latest;
	
	public FastLadder() {
		super("FastLadder", 0, ModuleType.MOVEMENT);
		setDescription("Быстрее лазит по лестницам");
		//from DD 06
		mode1 = new BoolValue(true);
		mode2 = new BoolValue(false);
		mode3 = new BoolValue(false);
		latest = new BoolValue(false);
		
		this.elements.add(new CheckBox(this, "Mode 1", mode1, 0, 0));
		this.elements.add(new CheckBox(this, "Mode 2", mode2, 0, 10));
		this.elements.add(new CheckBox(this, "Mode 3", mode3, 0, 20));
		this.elements.add(new CheckBox(this, "Latest (for mode 1)", latest, 0, 30));
	}
	
	@Override
	public void onUpdate() {
		//from Pontifex maybe, idk
		/*
		if(mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.pressed) {
			mc.thePlayer.motionY = 0.287299D; //0.25
		} else if(mc.thePlayer.isOnLadder() && !mc.gameSettings.keyBindBack.pressed) {
			mc.thePlayer.motionY = -0.287299D; //-0.25
		}
		*/
	}
	
	@Override
	public void preMotionUpdate() {
		if(mode1.getValue())
			this.fastLadder_1();
		else if(mode2.getValue())
			this.fastLadder_2();
		else if(mode3.getValue())
			this.fastLadder_3();
		//fastLadder_2();
	}
	
	public static double speed = 1.0D;
	public static boolean wasClimbing = false;
	public static boolean wasSprinting = false;
	
	@Override
	public void onDisable() {
		wasClimbing = false;
		speed = 0.0D;
	}
	
	void fastLadder_3() {
		boolean movementInput = this.mc.gameSettings.keyBindForward.pressed || this.mc.gameSettings.keyBindBack.pressed || this.mc.gameSettings.keyBindLeft.pressed || this.mc.gameSettings.keyBindLeft.pressed;
		int posX = MathHelper.floor_double(mc.thePlayer.posX);
		int minY = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);
		int maxY = MathHelper.floor_double(mc.thePlayer.boundingBox.minY + 1.0);
		int posZ = MathHelper.floor_double(mc.thePlayer.posZ);
		if (movementInput && mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isInWater()) {
			if (!mc.thePlayer.isOnLadder()) {
				
				Block block = MiscUtils.getBlock(posX, minY, posZ);
				if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
					
					block = MiscUtils.getBlock(posX, maxY, posZ);
					if(block instanceof BlockLadder || block instanceof BlockVine) {
						mc.thePlayer.motionY = 0.5;
					}
				}
			} else if (mc.thePlayer.isOnLadder()) {
				mc.thePlayer.motionY *= 2.4;
			}
		}
	}
	
	void fastLadder_2() {
		/*
		if (!isEnabled()) {
			wasClimbing = false;
			speed = 0.0D;
		}
		*/
		//else {
			if (mc.thePlayer.isSprinting()) {
				wasSprinting = true;
			} else if (!wasClimbing) {
				wasSprinting = false;
			}
			if (mc.thePlayer.isCollidedHorizontally) {
				if (mc.thePlayer.isOnLadder()) {
					mc.thePlayer.motionY = 0.287299D / 2; //0.287299D
					wasClimbing = true;
				}
				else if (wasClimbing) {
					speed += 1.0D;
					if (speed < 4.0D) {
						mc.thePlayer.motionY = 0.287299D / 2; //0.287299D
						speed = 0.0D;
					}
					else {
						wasClimbing = false;
					}
				}
			}
			else if (wasClimbing) {
				if (wasSprinting) {
					mc.thePlayer.setSprinting(true);
					wasSprinting = false;
				}
				wasClimbing = false;
			}
		//}
	}
	
	void fastLadder_1() {
		if (latest.getValue()) {
			Block.var_y = 1.0E-6;
		}
		if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder()) {
			mc.thePlayer.motionY = motion;
		}
		if (canClimb() && !mc.thePlayer.isOnLadder() && mc.thePlayer.isCollidedHorizontally) {
			++state;
			if (state == 1) {
				if (mc.thePlayer.boundingBox.minY - (int)mc.thePlayer.boundingBox.minY < motion && mc.thePlayer.boundingBox.minY - (int)mc.thePlayer.boundingBox.minY != 0.0 && latest.getValue()) {
					mc.thePlayer.motionY = -(mc.thePlayer.boundingBox.minY - (int)mc.thePlayer.boundingBox.minY);
					state = 0;
				}
				else {
					mc.thePlayer.motionY = 0.39;
				}
			}
			if (state == 2) {
				mc.thePlayer.motionY = 0.35;
			}
			if (state == 3) {
				mc.thePlayer.motionY = 0.26;
				state = 0;
			}
		}
		else {
			state = 0;
		}
	}
	
	public static boolean canClimb() {
		AxisAlignedBB contract = mc.thePlayer.boundingBox.contract(0.001, 0.001, 0.001);
		int floor_double = MathHelper.floor_double(contract.minX + 0.29);
		int floor_double2 = MathHelper.floor_double(contract.maxX + 0.71);
		int floor_double3 = MathHelper.floor_double(contract.minY);
		int floor_double4 = MathHelper.floor_double(contract.maxY + 1.0);
		int floor_double5 = MathHelper.floor_double(contract.minZ + 0.29);
		int floor_double6 = MathHelper.floor_double(contract.maxZ + 0.71);
		for (int i = floor_double; i < floor_double2; ++i) {
			for (int j = floor_double3; j < floor_double4; ++j) {
				for (int k = floor_double5; k < floor_double6; ++k) {
					Block block = Block.blocksList[mc.theWorld.getBlockId(i, j, k)];
					if (block != null && block.blockID == 106) {
						return true;
					}
					if (block != null && block.blockID == 65) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
