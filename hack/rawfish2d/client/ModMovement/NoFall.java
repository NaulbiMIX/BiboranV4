package hack.rawfish2d.client.ModMovement;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class NoFall extends Module {
	public NoFall() {
		super("NoFall", 0, ModuleType.MOVEMENT);
		setDescription("При падении вы не получаете урон (для серверов без античита)");
	}
	
	@Override
	public void preMotionUpdate() {
		if(mc.thePlayer.fallDistance > 3) {
			mc.thePlayer.onGround = true;
		}
	}
}

