package hack.rawfish2d.client.ModRender;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;

public class CaveFinder extends Module {
	public static Module instance = null;
	
	public CaveFinder() {
		super("CaveFinder", Keyboard.KEY_K, ModuleType.RENDER);
		setDescription("Позволяет увидеть пещеры");
		instance = this;
	}
	
	public void fastReRender() {
	    if (mc.thePlayer != null && mc.theWorld != null) {
	        int x = (int)mc.thePlayer.posX;
	        int y = (int)mc.thePlayer.posY;
	        int z = (int)mc.thePlayer.posZ;
	        mc.renderGlobal.markBlockRangeForRenderUpdate(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200);
	    }
	}
	
	@Override
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
	
	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
}
