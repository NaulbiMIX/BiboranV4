package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.MathHelper;

public class BucketBug extends Module {
	public static Module instance = null; 

	public BucketBug() {
		super("BucketBug", 0, ModuleType.MISC);
		setDescription("Можно лить воду и лаву не только в своём привате!");
		instance = this;
	}
}
