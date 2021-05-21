package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;

public class Jesus extends Module{
	public static Module instance = null;
	private boolean bswitch;
	public static BoolValue legit = new BoolValue(false);
	
	private boolean bvar4;
	private int ivar1;
	
	public Jesus() {
		super("Jesus", Keyboard.KEY_O, ModuleType.MOVEMENT);
		setDescription("Хождение по воде");
		instance = this;
		bswitch = false;
		
		this.elements.add(new CheckBox(this, "Legit", legit, 0, 0));
	}
	
	@Override
	public void preMotionUpdate() {
		
		if (legit.getValue()) {
			if (isLiquidUnderPlayer() && !mc.gameSettings.keyBindSneak.pressed) {
				mc.thePlayer.motionY = 0.1;
			}
		}
		else if (isLiquidUnderPlayer2() && !mc.thePlayer.isSneaking()) {
			mc.thePlayer.motionY = 0.13;
		}
		
		/*
		boolean bvar3 = isLiquidUnderPlayer();
		boolean bvar6 = !mc.gameSettings.keyBindJump.pressed;
		
		if (isLiquidUnderPlayer2() && !this.mc.thePlayer.isSneaking() && bvar3 && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.handleLavaMovement()) {
			this.mc.thePlayer.motionY = 0.13;
		}
		if (!this.mc.thePlayer.isSneaking() && bvar3 && this.mc.thePlayer.onGround && !isLiquidUnderPlayer2() && !this.mc.thePlayer.isInWater()) {
			this.bvar4 = true;
		}
		final float[] array = { -0.074f, 0.5f, 0.484f, 0.468f, 0.436f, 0.4f, 0.368f, 0.336f, 0.287f, 0.255f, 0.223f, 0.191f, 0.166f, 0.1667f, 0.162f, 0.137f, 0.112f, 0.124f, 0.1f, 0.068f, 0.084f, 0.06f, 0.035f, 0.046f, 0.023f };
		if (this.bvar4) {
			++this.ivar1;
			if (this.ivar1 - 1 < array.length) {
				this.mc.thePlayer.motionY = array[this.ivar1 - 1];
				if (this.ivar1 > 2 && this.ivar1 < 26 && this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY == 0.0) {
					this.ivar1 = 27;
				}
			}
			else if (bvar6 && this.mc.thePlayer.sendQueue.ivar1 < 79) {
				this.mc.thePlayer.motionY = 0.0;
			}
			else {
				this.bvar4 = false;
				this.ivar1 = 0;
			}
		}
		*/
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if (packet instanceof Packet10Flying && !legit.getValue()) {
			if (isLiquidUnderPlayer()) {
				bswitch = !bswitch;
				
				if (bswitch) {
					Packet10Flying pFlying = (Packet10Flying)packet;
					pFlying.yPosition += 0.0001; //1.0E-4
					pFlying.stance += 0.0001; //1.0E-4
				}
			}
			else
				bswitch = true;
		}
		
		//anti fall damage
		if (packet instanceof Packet10Flying && !legit.getValue()) {
			Packet10Flying pFlying = (Packet10Flying)packet;
			
			if (mc.thePlayer.fallDistance > 3.0f) {
				pFlying.onGround = true;
			}
		}
	}
	
	public static boolean isLiquidUnderPlayer() {
		AxisAlignedBB contract = mc.thePlayer.boundingBox.copy().offset(0.0, -0.01, 0.0).contract(0.001, 0.001, 0.001);
		int floor_double = MathHelper.floor_double(contract.minX);
		int floor_double2 = MathHelper.floor_double(contract.maxX + 1.0);
		int floor_double3 = MathHelper.floor_double(contract.minY);
		int floor_double4 = MathHelper.floor_double(contract.maxY + 1.0);
		int floor_double5 = MathHelper.floor_double(contract.minZ);
		int floor_double6 = MathHelper.floor_double(contract.maxZ + 1.0);
		for (int i = floor_double; i < floor_double2; ++i) {
			for (int j = floor_double3; j < floor_double4; ++j) {
				for (int k = floor_double5; k < floor_double6; ++k) {
					Block block = Block.blocksList[mc.theWorld.getBlockId(i, j, k)];
					if (block != null && block.blockMaterial.isLiquid()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isLiquidUnderPlayer2() {
		AxisAlignedBB contract = mc.thePlayer.boundingBox.contract(0.001, 0.001, 0.001);
		int floor_double = MathHelper.floor_double(contract.minX);
		int floor_double2 = MathHelper.floor_double(contract.maxX + 1.0);
		int floor_double3 = MathHelper.floor_double(contract.minY);
		int floor_double4 = MathHelper.floor_double(contract.maxY + 1.0);
		int floor_double5 = MathHelper.floor_double(contract.minZ);
		int floor_double6 = MathHelper.floor_double(contract.maxZ + 1.0);
		for (int i = floor_double; i < floor_double2; ++i) {
			for (int j = floor_double3; j < floor_double4; ++j) {
				for (int k = floor_double5; k < floor_double6; ++k) {
					Block block = Block.blocksList[mc.theWorld.getBlockId(i, j, k)];
					if (block != null && block.blockMaterial.isLiquid()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
