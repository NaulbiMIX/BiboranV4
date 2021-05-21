package hack.rawfish2d.client.gui.windows;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.Window;

public class WindowCombat extends Window{
	public WindowCombat(int x, int y) {
		super("Combat", x, y);
		// TODO Auto-generated constructor stub
	}

	public Window init() {
		for(Module m : Client.getInstance().getModules()) {
			if(m.getType() == ModuleType.COMBAT)
				addButton(m);
		}
		return this;
	}
}
