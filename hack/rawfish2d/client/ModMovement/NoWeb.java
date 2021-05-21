package hack.rawfish2d.client.ModMovement;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class NoWeb extends Module{
	public NoWeb() {
		super("NoWeb", 0, ModuleType.MOVEMENT);
		setDescription("Паутина не замедляет игрока");
	}
	
	@Override
	public void postMotionUpdate() {
		if (mc.thePlayer.isInWeb) {
			mc.thePlayer.motionY = 0.001D;
			mc.thePlayer.isInWeb = false;
		}
	}
}
