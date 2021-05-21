package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.MathHelper;

public class NoRotate extends Module {
	public static Module instance = null; 

	public NoRotate() {
		super("NoRotate", 0, ModuleType.MISC);
		setDescription("—ервер не будет указывать вам куда смотреть");
		instance = this;
	}
}
