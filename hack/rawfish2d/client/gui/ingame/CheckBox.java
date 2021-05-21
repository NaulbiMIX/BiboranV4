package hack.rawfish2d.client.gui.ingame;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.ModCombat.AimLock;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.GuiUtils;

public class CheckBox implements IGuiElement {
	private Module mod;
	private BoolValue value;
	private String text;
	private int xPos;
	private int yPos;
	private int w;
	private int h;
	private int[] xyBOX;

	public CheckBox(Module mod, String text, BoolValue value, int xPos, int yPos) {
		this.mod = mod;
		this.text = text;
		this.xPos = xPos;
		this.yPos = yPos;
		this.value = value;
		this.w = 8;
		this.h = 8;
		this.xyBOX = new int[] {xPos + 95, yPos + 5};
	}

	public void update() {
		int w = Client.getInstance().mc.fontRenderer.getStringWidth(text);
		//xyBOX[0] = (int) (xPos + mod.xywhBUTTON[0] + 95 + w);
		xyBOX[0] = (int) (xPos + mod.xywhBUTTON[0] + 90);
		xyBOX[1] = (int) (yPos + mod.xywhBUTTON[1] + 5);
	}

	@Override
	public void draw(int x, int y) {
		if(!mod.settings_visible)
			return;
		
		if(value == null)
			return;
		
		update();
		
		GuiUtils.drawBorderedRect1(xyBOX[0], xyBOX[1], xyBOX[0] + w, xyBOX[1] + h, value.getValue() ? 0xFF00FF00 : 0xFFFF0000, value.getValue() ? 0xFF20AF20 : 0xFFAF2020);
		int w = Client.getInstance().mc.fontRenderer.getStringWidth(text);
		//GuiUtils.drawStringWithShadow(text, xyBOX[0] - w - 5, xyBOX[1], 0xFFFFFFFF);
		GuiUtils.drawStringWithShadow(text, xyBOX[0] + 10, xyBOX[1], 0xFFFFFFFF);
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		if(!mod.settings_visible)
			return;
		
		if(x >= xyBOX[0] && y >= xyBOX[1] && x <= xyBOX[0] + w && y <= xyBOX[1] + h && button == 0) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			value.setValue(!value.getValue());
		}
	}
	
	public boolean getValue() {
		if(value != null)
			return value.getValue();
		
		return false;
	}
	
	public void setValue(boolean v) {
		if(value != null)
			value.setValue(v);
	}

	@Override
	public int getX() {
		return xPos;
	}
	
	@Override
	public int getY() {
		return yPos;
	}

	@Override
	public int getW() {
		return this.w + 2;
	}

	@Override
	public int getH() {
		return this.h;
	}
	
	@Override
	public String text() {
		return text;
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		
	}

	@Override
	public int getTextWidth() {
		return mod.mc.fontRenderer.getStringWidth(text()) + getW();
	}

	@Override
	public void keyTyped(char ch, int ivar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mx, int my) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPinned() {
		return false;
	}
}