package hack.rawfish2d.client.ModMovement;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class NoFall extends Module {
	public NoFall() {
		super("NoFall", 0, ModuleType.MOVEMENT);
		setDescription("��� ������� �� �� ��������� ���� (��� �������� ��� ��������)");
	}
	
	@Override
	public void preMotionUpdate() {
		if(mc.thePlayer.fallDistance > 3) {
			mc.thePlayer.onGround = true;
		}
	}
}

