package hack.rawfish2d.client.ModRender;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class Fullbright extends Module {
	
	private float gamma = 0F;
	
	public Fullbright() {
		super("Fullbright", 0, ModuleType.RENDER);
		setDescription("Максимальная яркость");
	}
	
	@Override
	public void onEnable() {
		this.gamma = mc.gameSettings.gammaSetting;
		//mc.renderGlobal.loadRenderers();
		//if(Client.getInstance().getGUI().notifications.size() > 0)
		//	Client.getInstance().getGUI().notifications.get(0).show();
		
		/*
		TEST_ScriptTest st = new TEST_ScriptTest();
		st.test();
		*/
	}

	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = this.gamma;
	}
	
	private int frames = 0;
	private int framesFine = 0;
	
	@Override
	public void onUpdate() {
		mc.gameSettings.gammaSetting = 1337F;
		
		//power safe
		if(!Client.isFocused() && framesFine == 0) {
			framesFine = mc.gameSettings.ofLimitFramerateFine;
			
			mc.gameSettings.ofLimitFramerateFine = 20;
		}
		
		if(Client.isFocused() && framesFine != 0) {
			mc.gameSettings.ofLimitFramerateFine = framesFine;
			
			framesFine = 0;
		}
		
	}
}
