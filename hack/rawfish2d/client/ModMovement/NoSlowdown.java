package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class NoSlowdown extends Module{
	public static Module instance = null; 
	
	public NoSlowdown() {
		super("NoSlowdown", 0, ModuleType.MOVEMENT);
		setDescription("При использовании лука/еды/зелий вы не замедляетесь");
		instance = this;
	}
}
