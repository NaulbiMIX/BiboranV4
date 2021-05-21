package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Packet28EntityVelocity;

public class NoKnockback extends Module{
	public static Module instance = null;

	public NoKnockback() {
		super("NoKnockback", Keyboard.KEY_N, ModuleType.COMBAT);
		setDescription("Игрока ничто не сможет сдвинуть с места");
		instance = this;
	}
}
