package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.WorldClient;

public class Criticals2 extends Module{
	public static Module instance = null;
	
	public Criticals2() {
		super("SuperCriticals", Keyboard.KEY_C, ModuleType.COMBAT);
		setDescription("Критические удары (новые)");
		instance = this;
	}

	@Override
	public void postMotionUpdate() {
		if(mc.thePlayer.isSwingInProgress && !mc.thePlayer.isJumping) {
			post();
			criticals2();
		}
	}

	@Override
	public void preMotionUpdate() {
		if(mc.thePlayer.isSwingInProgress && !mc.thePlayer.isJumping)
			pre();
	}

	//Criticals - TitanPvP
	public void criticals2() {
		if(shouldCrit()) {
			mc.thePlayer.onGround = true;
			if(mc.thePlayer.onGround && mc.thePlayer.isSwingInProgress && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder()) {
				if((!mc.gameSettings.keyBindJump.pressed || !mc.thePlayer.isJumping) && mc.thePlayer.motionY < 2.0D && !mc.thePlayer.isOnLadder()) {
					mc.thePlayer.motionY = -1.4000000001D;
				}
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY += 0.1D, mc.thePlayer.posZ);
				mc.thePlayer.onGround = true;
			}
		}
	}
	//Criticals - TitanPvP

	public void pre() {
		if(shouldCrit()) {
			mc.thePlayer.onGround = false;
		}
	}

	public void post() {
		if(shouldCrit()) {
			mc.thePlayer.onGround = true;
		}
	}

	private boolean shouldCrit() {
		if(!mc.thePlayer.isInWater()) {
			if(mc.thePlayer.isCollidedVertically) {
				return true;
			}
		}
		return false;
	}
}
