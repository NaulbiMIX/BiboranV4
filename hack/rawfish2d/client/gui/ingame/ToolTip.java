package hack.rawfish2d.client.gui.ingame;

import java.awt.Rectangle;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.src.ScaledResolution;

public class ToolTip {
	private Window window;
	private Module mod;
	private int xPos;
	private int yPos;
	private String text;

	public ToolTip(Window window, Module mod, int xPos, int yPos, String text) {
		this.window = window;
		this.mod = mod;
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
	}

	public void draw() {
		int[] mpos = R2DUtils.getMousePos();
		float x1 = mpos[0];
		float y1 = mpos[1] - 35;

		String text2 = "Активация : " + Keyboard.getKeyName(mod.getKeyCode());

		int descLen = Client.getInstance().mc.fontRenderer.getStringWidth(text);
		int text2Len = Client.getInstance().mc.fontRenderer.getStringWidth(text2);

		float x2 = mpos[0] + (descLen > text2Len ? descLen : text2Len) + 8;
		float y2 = mpos[1] - 10;

		GUIStyleEnum style = GUIStyle_Legacy.getStyle();
		int borderColor = GUIStyle_Legacy.getToolTipBorderColor();
		int textColor = GUIStyle_Legacy.getToolTipTextColor();
		GuiUtils.drawBorderedRect2(x1, y1, x2, y2, 1f, borderColor, 0xFF000000);

		Client.getInstance().mc.fontRenderer.drawString(text, (int)x1 + 3, (int)y1 + 3, textColor);
		Client.getInstance().mc.fontRenderer.drawString(text2, (int)x1 + 3, (int)y1 + 13, textColor);
	}

	public void mouseClicked(int x, int y, int button) {
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}
}