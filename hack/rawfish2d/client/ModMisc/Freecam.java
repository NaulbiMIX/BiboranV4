package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityOtherPlayerMP;

public class Freecam extends Module {
	public static Module instance = null;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private float yawHead;

	public Freecam() {
		super("Freecam", Keyboard.KEY_J, ModuleType.MISC);
		setDescription("Позволяет полетать и посмотреть что вокруг (полёт не настоящий)");
		instance = this;
	}
	
    @Override
    public void onEnable() {
		mc.thePlayer.noClip = true;
		x = mc.thePlayer.posX;
		y = mc.thePlayer.posY;
		z = mc.thePlayer.posZ;
		yaw = mc.thePlayer.rotationYaw;
		pitch = mc.thePlayer.rotationPitch;
		yawHead = mc.thePlayer.rotationYawHead;
		EntityOtherPlayerMP entityPlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.username);
		entityPlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ);
		entityPlayer.rotationYaw = yaw;
		entityPlayer.rotationPitch = pitch;
		entityPlayer.rotationYawHead = yawHead;
		mc.theWorld.addEntityToWorld(-1, entityPlayer);
    }
    
    @Override
    public void onDisable() {
        mc.theWorld.removeEntityFromWorld(-1);
        mc.thePlayer.noClip = false;
        mc.thePlayer.setPosition(x, y, z);
        mc.thePlayer.rotationPitch = pitch;
        mc.thePlayer.rotationYaw = yaw;
        mc.renderGlobal.loadRenderers();
    }
    
    @Override
    public void onUpdate() {
        mc.thePlayer.noClip = true;
        mc.playerController.curBlockDamageMP = 1.0f;
        if (!mc.thePlayer.capabilities.isFlying) {
        	mc.thePlayer.motionY = 0.0;
        	mc.thePlayer.motionZ = 0.0;
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.onGround = false;
            mc.thePlayer.jumpMovementFactor = 0.8f;
            if (mc.gameSettings.keyBindJump.pressed) {
            	mc.thePlayer.motionY = 0.4;
            }
            if (mc.gameSettings.keyBindSneak.pressed) {
            	mc.thePlayer.motionY = -0.4;
            }
        }
    }
}
