package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.MovementInput;

public class Fly extends Module{
	
	private DoubleValue speed;

	public Fly() {
		super("Fly", Keyboard.KEY_R, ModuleType.MOVEMENT);
		setDescription("Режим полёта (лучше работает при получении урона)");
		
		speed = new DoubleValue(1.5f, 0f, 3f);
		this.elements.add(new NewSlider(this, "Speed", speed, 0, 0, false));
	}

	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		//mc.thePlayer.capabilities.isFlying = false;
	}
	
	@Override
	public void onUpdate() {
		//mc.thePlayer.capabilities.isFlying = true;
		
		/*
		//EntitySpeed
		if (mc.thePlayer.ridingEntity != null) {
			MovementInput movementInput = mc.thePlayer.movementInput;
			double forward = movementInput.moveForward;
			double strafe = movementInput.moveStrafe;
			float yaw = mc.thePlayer.rotationYaw;
			if ((forward == 0.0D) && (strafe == 0.0D)) {
				mc.thePlayer.ridingEntity.motionX = 0.0D;
				mc.thePlayer.ridingEntity.motionZ = 0.0D;
			}else{
				if (forward != 0.0D) {
					if (strafe > 0.0D) {
						yaw += (forward > 0.0D ? -45 : 45);
					}else if (strafe < 0.0D) {
						yaw += (forward > 0.0D ? 45 : -45);
					}
					strafe = 0.0D;
					if (forward > 0.0D) {
						forward = 1.0D;
					}else if (forward < 0.0D) {
						forward = -1.0D;
					}
				}
				float speed = 1.0f;
				mc.thePlayer.ridingEntity.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
				mc.thePlayer.ridingEntity.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
			}
		}
		*/
		//speed.setValue(0.204D);
		
		mc.thePlayer.capabilities.isFlying = false;
		
		mc.thePlayer.onGround = true;
		mc.thePlayer.motionX = 0.0;
		mc.thePlayer.motionY = 0.0;
		mc.thePlayer.motionZ = 0.0;
		mc.thePlayer.jumpMovementFactor = (float) speed.getValue();
		if (mc.inGameHasFocus) {
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode)) {
				mc.thePlayer.motionY += speed.getValue();
			}
			else if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
				mc.thePlayer.motionY -= speed.getValue();
			}
		}
		
		mc.thePlayer.onGround = false;
		
		//mc.thePlayer.capabilities.isFlying = true;
	}
}
