package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.KillAura;
import net.minecraft.src.EntityClientPlayerMP;

public class Sprint extends Module{
	
	public Sprint() {
		super("Sprint", 0, ModuleType.MOVEMENT);
		setDescription("Спринт");
	}
	
	@Override
	public void preMotionUpdate() {
		if(mc.thePlayer.onGround && !mc.gameSettings.keyBindBack.pressed)
			mc.thePlayer.setSprinting(true);
	}
}
