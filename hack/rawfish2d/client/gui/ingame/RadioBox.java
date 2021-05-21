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
import hack.rawfish2d.client.utils.R2DUtils;

public class RadioBox implements IGuiElement {
	private Module mod;
	private BoolValue value;
	private String text;
	private int xPos;
	private int yPos;
	private int w;
	private int h;
	public int[] xyBOX;

	public RadioBox(Module mod, String text, BoolValue value, int xPos, int yPos) {
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
		int strw = Client.getInstance().mc.fontRenderer.getStringWidth(text);
		//xyBOX[0] = (int) (xPos + mod.xywhBUTTON[0] + 92 + (w / 2) + strw);
		xyBOX[0] = (int) (xPos + mod.xywhBUTTON[0] + 89 + (w / 2));
		xyBOX[1] = (int) (yPos + mod.xywhBUTTON[1] + (w / 2) + 4);
	}

	@Override
	public void draw(int x, int y) {
		if(!mod.settings_visible)
			return;
		
		if(value == null)
			return;
		
		update();
		
		R2DUtils.drawFilledCircle(xyBOX[0], xyBOX[1], w / 2, value.getValue() ? 0xFF20AF20 : 0xFFAF2020);
		R2DUtils.drawCircle(xyBOX[0], xyBOX[1], w / 2, value.getValue() ? 0xFF00FF00 : 0xFFFF0000);
		
		int strw = Client.getInstance().mc.fontRenderer.getStringWidth(text);
		GuiUtils.drawStringWithShadow(text, xyBOX[0] + 7, xyBOX[1] - 4, 0xFFFFFFFF);
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		if(!mod.settings_visible)
			return;
		
		if(x >= xyBOX[0] - (w / 2) && y >= xyBOX[1] - (w / 2) && x <= xyBOX[0] - (w / 2) + w && y <= xyBOX[1] - (w / 2) + w && button == 0) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			setState(true);
		}
	}
	
	public void toggle() {
		setState(!getState());
	}
	
	public void setState(boolean state) {
		if(value != null)
			value.setValue(state);
	}
	
	public boolean getState() {
		if(value != null)
			return value.getValue();
		else
			return false;
	}

	@Override
	public int getX() {
		return xPos;
		//return xyBOX[0];
	}

	@Override
	public int getY() {
		return yPos;
	}

	@Override
	public int getW() {
		return this.w;
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
		
	}

	@Override
	public void mouseOver(int mx, int my) {
		
	}
	
	@Override
	public boolean isPinned() {
		return false;
	}
}