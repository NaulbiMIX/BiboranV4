package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.pbots.PBotThread;
import net.minecraft.src.Packet;

public class ModuleBase {
	public boolean toggled;
	public String name;
	public PBotThread bot;
	
	public ModuleBase(PBotThread bot, String name) {
		this.bot = bot;
		this.name = name;
	}
	
	public void onEnable() {}
	
	public void onDisable() {}
	
	public void onReadPacket(Packet packet) {}
	
	public void onTick() {}
	
	//client render
	public void onRender() {}
	
	//client render
	public void onRenderHand() {}
	
	//client render
	public void onRenderOverlay() {}
	
	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}
}
