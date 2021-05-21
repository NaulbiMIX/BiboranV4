package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet19EntityAction;

public class Phase extends Module {
	public static Module instance = null;
	private int var1;
	private boolean var2;

	public Phase() {
		super("Phase", Keyboard.KEY_F, ModuleType.MOVEMENT);
		setDescription("Позволяет проходить сквозь двери/стеклопанели/заборы а иногда даже сквозь пол!");
		instance = this;
		//from DD 06
	}
	
	@Override
	public void onDisable() {
		Block.var_y = 0.0;
		//mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.15f, mc.thePlayer.posZ);
		//mc.thePlayer.noClip = false;
		//mc.thePlayer.capabilities.isFlying = false;
	}
	
	@Override
	public void preMotionUpdate() {
		phase_dd();
		//phase_hackandy();
		//phase_night100mods();
	}
	
	@Override
	public void postMotionUpdate() {
		//phase_night100mods_post();
		//phase_dd_b2();
	}
	
	private boolean didOffset = false;
	
	private void phase_night100mods() {
		if (isInsideBlock()) {
			// empty if block
		}
		mc.thePlayer.boundingBox.minY -= 0.001;
		mc.thePlayer.posY -= 0.001;
		didOffset = true;
	}
	
	private void phase_night100mods_post() {
		if (this.didOffset) {
			mc.thePlayer.boundingBox.minY += 0.001;
			mc.thePlayer.posY += 0.001;
			this.didOffset = false;
		}
	}
	
	private void phase_dd_b2() {
		if (this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater()) {
            ++this.var1;
            if (!this.isInsideBlock()) {
                if (this.var1 == 1) {
                    mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY - 0.6, this.mc.thePlayer.posY - 0.6, this.mc.thePlayer.posZ, false));
                }
                if (this.var1 == 2) {
                    mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY - 0.678, this.mc.thePlayer.posY - 0.678, this.mc.thePlayer.posZ, false));
                }
            }
        }
        else {
            this.var1 = 0;
        }
	}
	
	private void phase_hackandy() {
		Block.var_y = -10.0;
		for(int x = 0; x <=55; x++){
			if(mc.thePlayer.isCollidedHorizontally){
				double var2 = mc.thePlayer.boundingBox.minY + 2.5;
				double var8 = mc.thePlayer.boundingBox.minX + 1.5;
				float var4 = mc.thePlayer.rotationPitch;
				float var5 = mc.thePlayer.rotationYaw;
				mc.thePlayer.capabilities.isFlying = true;
				mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, var2 + var8, mc.thePlayer.posY, mc.thePlayer.posZ, 1.0F, 1.0F, false));
				//mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, var8, mc.thePlayer.posY, mc.thePlayer.posZ, 1.0F, 1.0F, false));
				//int posX = (int) Math.round(mc.thePlayer.posX);
				//int posY = (int) Math.round(mc.thePlayer.posY);
				//int posZ = (int) Math.round(mc.thePlayer.posZ);
				//int minY = (int) Math.round(mc.thePlayer.boundingBox.minY);
				//mc.thePlayer.noClip = true;
				mc.thePlayer.boundingBox.expand(1.0, 1.0, 0.0);
			}
		}
	}
	
	private void phase_dd() {
		float rotationYaw = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0f) {
			rotationYaw += 180.0f;
		}
		if (mc.thePlayer.moveStrafing > 0.0f) {
			rotationYaw -= 90.0f * ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
		}
		if (mc.thePlayer.moveStrafing < 0.0f) {
			rotationYaw += 90.0f * ((mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
		}
		float n = (float)Math.cos((rotationYaw + 90.0f) * 3.141592653589793 / 180.0);
		float n2 = (float)Math.sin((rotationYaw + 90.0f) * 3.141592653589793 / 180.0);
		if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0.0f) {
			var2 = true;
		}
		if (var2) {
			++var1;
		}
		if (this.var1 == 1) {
			mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
			float[] array = { n * 0.25f, 1.0f, n2 * 0.25f };
			double[] array2 = { 0.025000000372529, 0.02857142899717604, 0.0333333338300387, 0.04000000059604645 };
			for (int i = 0; i < array2.length; ++i) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY - array2[i] + 0.02500000037252903, mc.thePlayer.posY - array2[i] + 0.02500000037252903, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
				MiscUtils.sleep(4L);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX + array[0] * i, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ + array[2] * i, false));
				MiscUtils.sleep(4L);
			}
			mc.thePlayer.setPosition(mc.thePlayer.posX + array[0] * 0.05f, mc.thePlayer.posY, mc.thePlayer.posZ + array[2] * 0.05f);
			mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 2));
			Block.var_y = -10.0;
		}
		if (var1 > 8) {
			var1 = 0;
			var2 = false;
		}
		if (!isInsideBlock()) {
			Block.var_y = 0.0;
		}
	}
	
	private boolean isInsideBlock() {
		for (int i = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); i < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++i) {
			for (int j = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); j < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++j) {
				for (int k = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); k < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++k) {
					Block block = Block.blocksList[mc.theWorld.getBlockId(i, j, k)];
					if (block != null && block.blockMaterial.blocksMovement()) {
						AxisAlignedBB collisionBoundingBoxFromPool = block.getCollisionBoundingBoxFromPool(mc.theWorld, i, j, k);
						if (collisionBoundingBoxFromPool != null && mc.thePlayer.boundingBox.custom_sub(collisionBoundingBoxFromPool)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
