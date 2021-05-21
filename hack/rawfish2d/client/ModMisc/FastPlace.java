package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.EntityClientPlayerMP;

public class FastPlace extends Module{
	private float speed = 1.0F;

	public FastPlace() {
		super("FastPlace", 0, ModuleType.MISC);
		setDescription("Быстрее ставит блоки");
	}

    @Override
    public void preMotionUpdate() {
    	mc.rightClickDelayTimer = 0;
    	//mc.gameSettings.keyBindJump.pressed = true;
    	//mc.gameSettings.keyBindUseItem.pressed = true;
    }
}
