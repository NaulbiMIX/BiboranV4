package hack.rawfish2d.client.ModOther;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.ConfigManager;

public class GUI extends Module{
	public static Module instance = null;
	
	public GUI() {
		super("GUI", Keyboard.KEY_GRAVE, ModuleType.NONE);
		instance = this;
		setDescription("Меню чита");
	}
	
	@Override
	public void onEnable() {
		//ConfigManager.loadConfig();
	}
	
	@Override
	public void onDisable() {
		mc.displayGuiScreen(null);
		ConfigManager.saveAll();
	}
	
	@Override
	public void onUpdate() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)/* || Keyboard.isKeyDown(getKeyCode())*/) {
			toggle();
			return;
		}
		
		if(this.mc != null) {
			if(this.mc.gameSettings != null) {
				if(this.mc.gameSettings.keyBindPlayerList != null) {
					if(this.mc.gameSettings.showDebugInfo == true || this.mc.gameSettings.keyBindPlayerList.pressed) {
						toggle();
						return;
					}
				}
			}
		}
		
		if(mc.currentScreen == null)
			mc.displayGuiScreen(Client.getInstance().getGUI());
	}
}
