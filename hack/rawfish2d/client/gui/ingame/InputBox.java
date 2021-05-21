package hack.rawfish2d.client.gui.ingame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.src.GuiButton;

public class InputBox implements IGuiElement {
	private Module mod;
	private int xPos;
	private int yPos;
	private String text;
	public String value;
	public boolean focused;
	
	private int []xywhTEXTBOX;

	public boolean isOverButton;

	public InputBox(Module mod, String text, int x, int y) {
		this.mod = mod;
		this.xPos = x;
		this.yPos = y;
		this.text = text;
		this.focused = false;
		this.value = new String();
		this.value = "";
		
		this.xywhTEXTBOX = new int[] {0, 0, 0, 0};
	}
	
	private void update() {
		xywhTEXTBOX[0] = (int) (xPos + 3 + mod.xywhBUTTON[0] + 88);
		xywhTEXTBOX[1] = (int) (yPos + 1 + mod.xywhBUTTON[1] + 2);
		xywhTEXTBOX[2] = 115;
		xywhTEXTBOX[3] = 12;
	}

	public void draw(int mx, int my) {
		update();
		
		R2DUtils.drawBetterRect(xywhTEXTBOX[0], xywhTEXTBOX[1], xywhTEXTBOX[0] + xywhTEXTBOX[2], xywhTEXTBOX[1] + xywhTEXTBOX[3], 1, 0x00000000, 0xFF00FF00);
		
		GuiUtils.drawCenteredString(value, xywhTEXTBOX[0] + 1, xywhTEXTBOX[1] + 2, 0xFFFFFFFF);
		
		//Client.getInstance().mc.fontRenderer.drawString(text, ((getX() + window.dragX) - (getX() + 85 + window.dragX) - Client.getInstance().mc.fontRenderer.getStringWidth(text) / 2) + 127 + getX() + window.dragX, getY() + 1 + window.dragY, 0xFFFFFF);
	}

	public void mouseClicked(int x, int y, int button) {
		if(x >= xywhTEXTBOX[0] && y >= xywhTEXTBOX[1] && x <= xywhTEXTBOX[0] + xywhTEXTBOX[2] && y <= xywhTEXTBOX[1] + xywhTEXTBOX[3] && button == 0) {
			//GuiClick.sendPanelToFront(window);
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			//mod.toggle();
		}
	}
	
	@Override
	public void keyTyped(char ch, int ivar)
	{
		Keyboard.enableRepeatEvents(true);
		//MiscUtils.sendChatClient("ch: " + ch);
		//MiscUtils.sendChatClient("ivar: " + ivar);
		
		if( ivar == 211 ) {
			value = "";
		}
		else if(ivar == 14) {
			if(value.length() > 0)
				value = value.substring(0, value.length() - 1);
		}
		else if((ivar >= 16 && ivar <= 27) || (ivar >= 30 && ivar <= 40) || (ivar >= 43 && ivar <= 53) || (ivar >= 2 && ivar <= 13) || ivar == 41 || ivar == 57) {
			value += ch;
		}
		//Keyboard.enableRepeatEvents(false);
	}
	
	public void setFocused(boolean state) {
		/*
		Keyboard.enableRepeatEvents(state);
		focused = state;
		*/
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public int getX() {
		return xPos;
	}

	@Override
	public int getY() {
		return yPos + 2;
	}

	@Override
	public int getW() {
		return 114;
	}

	@Override
	public int getH() {
		return 15;
	}

	@Override
	public String text() {
		return text;
	}

	@Override
	public int getTextWidth() {
		return 0;
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
	}

	@Override
	public void mouseOver(int mx, int my) {
	}
	
	@Override
	public boolean isPinned() {
		return false;
	}
}