package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;

public class Glide extends Module {

	private float speed = 1.0f;
	
	public Glide() {
		super("Glide", Keyboard.KEY_H, ModuleType.MOVEMENT);
		setDescription("Замедленное падение");
		//from Ciber
	}
	
	@Override
	public void postMotionUpdate() {
		glideShidou(); //Ciber
	}
	
	@Override
	public void preMotionUpdate() {
		//glideDD(); //DD
	}
	
	private void glideShidou() {
		//DD glide
		//mc.thePlayer.motionY = -0.0326f * speed;
		
		//Ciber glide
		if (mc.thePlayer.motionY < 0.0) {
			if (mc.thePlayer.isAirBorne) {
				if (!mc.thePlayer.isInWater()) {
					if (!mc.thePlayer.isOnLadder()) {
						if (!mc.thePlayer.isInsideOfMaterial(Material.lava)) {
							//mc.thePlayer.motionY = -0.0326f * 2;
							mc.thePlayer.motionY = -0.125;
							mc.thePlayer.jumpMovementFactor *= 1.21337f;
						}
					}
				}
			}
		}
	}
	
	private void glideDD() {
		//DD glide
		mc.thePlayer.motionY = -0.0326f * speed;
	}
}
