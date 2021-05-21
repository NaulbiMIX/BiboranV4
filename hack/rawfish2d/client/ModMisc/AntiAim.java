package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class AntiAim extends Module{
	private Random random = new Random();

	public AntiAim() {
		super("AntiAim", 0, ModuleType.MISC);
		setDescription("Крутит головой так что в вас не смогут попасть (PAntiAim)");
	}

	@Override
	public void onEnable() {
		setName("CS:GO AntiAim");
	}

	@Override
	public void onDisable() {
		setName("AntiAim");
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void preMotionUpdate() {
		float yaw = random.nextBoolean() ? random.nextInt(180) : -random.nextInt(180);
		float pitch = random.nextBoolean() ? random.nextInt(90) : -random.nextInt(90);

		Client.getInstance().getRotationUtils().setYaw(yaw);
		//Client.getInstance().getRotationUtils().setPitch(-180);
		Client.getInstance().getRotationUtils().setPitch(pitch);
		//mc.thePlayer.rotationYawHead = yaw;

		if (!mc.thePlayer.isSwingInProgress) {
			mc.thePlayer.swingItem();
		}
	}

	@Override
	public void postMotionUpdate() {
	}
}
