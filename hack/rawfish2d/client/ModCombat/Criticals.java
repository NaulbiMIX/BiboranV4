package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.WorldClient;

public class Criticals extends Module{
	public Criticals() {
		super("Criticals", 0, ModuleType.COMBAT);
		setDescription("Критические удары (старые)");
	}

	@Override
	public void postMotionUpdate() {
		if (shouldCrit() && this.checkBlock()) {
			mc.thePlayer.boundingBox.offset(0.0, 0.6, 0.0);
		}
	}

	private boolean shouldCrit() {
		if (mc.thePlayer.onGround) {
			if (!mc.thePlayer.isOnLadder()) {
				if (!mc.thePlayer.isInWater()) {
					if (!mc.gameSettings.keyBindJump.pressed) {
						if (!mc.thePlayer.isCollidedHorizontally && (KillAura.curTarget != null)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkBlock() {
		WorldClient world = mc.theWorld;
		int x = MathHelper.floor_double(mc.thePlayer.posX);
		int y = MathHelper.floor_double(mc.thePlayer.boundingBox.maxY + 1.0);
		return world.isAirBlock(x, y, MathHelper.floor_double(mc.thePlayer.posZ));
	}
}
