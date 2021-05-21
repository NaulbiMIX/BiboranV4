package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.MathHelper;

public class CameraRotation extends Module {
	public static Module instance = null; 

	public CameraRotation() {
		super("CameraRotation", 0, ModuleType.MISC);
		setDescription("Можно крутить головой как угодно!");
		instance = this;
	}
}
