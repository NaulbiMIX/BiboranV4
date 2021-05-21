package hack.rawfish2d.client.ModRender;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.WindowItemsScroll;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.SharedIntList;

public class XRAY extends Module {
	public static Module instance = null;
	public static SharedIntList blocks;
	public static int opacity;
	
	public XRAY() {
		super("XRAY", Keyboard.KEY_X, ModuleType.RENDER);
		setDescription("Можно видеть ценные руды сквозь землю");
		instance = this;
		blocks = new SharedIntList();
		opacity = 0;
		
		this.elements.add(new WindowItemsScroll(this, "XRAY Settings", blocks, 1, 160, 300, 250));
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
		ConfigManager.saveXrayList();
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
	
	@Override
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		fastReRender();
	}
	
	public static boolean isXrayBlock(int id) {
		return blocks.contains(id);
	}
	
	@Override
	public void onButtonRightClick() {
		
	}
}
