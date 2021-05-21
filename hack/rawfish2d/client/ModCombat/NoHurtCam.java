package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class NoHurtCam extends Module{
	public static Module instance = null;

	public NoHurtCam() {
		super("NoHurtCam", 0, ModuleType.COMBAT);
		setDescription("При получении урона экран не трясётся");
		instance = this;
	}
}
