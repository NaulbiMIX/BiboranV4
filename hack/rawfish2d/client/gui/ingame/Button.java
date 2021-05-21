package hack.rawfish2d.client.gui.ingame;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.utils.R2DUtils;

public class Button {
	private Window window;
	public Module mod;
	private int xPos;
	private int yPos;
	public ToolTip ttip;
	//public InputBox inbox;

	public boolean isOverButton;
	public float[] xywhBUTTON;
	public int[] xywTEXT;

	public Button(Window window, Module mod, int xPos, int yPos, int w, int h) {
		this.window = window;
		this.mod = mod;
		this.xPos = xPos;
		this.yPos = yPos;
		this.ttip = new ToolTip(window, mod, xPos, yPos, mod.description);
		//this.inbox = new InputBox(window, mod, xPos, yPos);

		xywhBUTTON = new float[] {0, 0, 0, 0};
		xywTEXT = new int[] {0, 0, 0};

		xywhBUTTON[2] = w;
		xywhBUTTON[3] = h;

		update();
	}

	public void update() {
		xywhBUTTON[0] = xPos + 0.5f + window.dragX;
		xywhBUTTON[1] = yPos + 0.5f + window.dragY;
		xywTEXT[2] = Client.getInstance().mc.fontRenderer.getStringWidth(mod.getName()) / 2;
		xywTEXT[0] = (int)(((xPos + window.dragX) - (xPos + xywhBUTTON[2] + window.dragX) - xywTEXT[2]) + xywhBUTTON[2] + 43 + xPos + window.dragX);
		xywTEXT[1] = yPos + 1 + window.dragY;
	}

	public void draw(int index) {
		double x1 = xPos + 0.5 + window.dragX;
		double y1 = yPos + 0.5 + window.dragY;
		double x2 = xPos + xywhBUTTON[2] + window.dragX;
		double y2 = yPos + xywhBUTTON[3] + window.dragY;
		int x = (int) (((xPos + window.dragX) - (xPos + xywhBUTTON[2] + window.dragX) - Client.getInstance().mc.fontRenderer.getStringWidth(mod.getName()) / 2) + xywhBUTTON[2] + 43 + xPos + window.dragX);
		int y = yPos + 1 + window.dragY;
		
		GUIStyle_Legacy.drawButton(x1, x2, y1, y2, x, y, isOverButton, mod);
		//GUIStyle.drawButton(xywhBUTTON[0], xywhBUTTON[1], xywhBUTTON[0] + xywhBUTTON[2], xywhBUTTON[1] + xywhBUTTON[3], xywTEXT[0], xywTEXT[1], isOverButton, mod);
	}

	public void mouseClicked(int x, int y, int button) {
		/*
		//almost ok fix
		if(Client.getInstance().getGUI().getFocusedPanel() != window) {
			//Client.getInstance().getGUI().sendPanelToFront(window);
			return;
		}
		*/
		if(		x >= xywhBUTTON[0] &&
				y >= xywhBUTTON[1] &&
				x <= xywhBUTTON[0] + xywhBUTTON[2] &&
				y <= xywhBUTTON[1] + xywhBUTTON[3] &&
				window.isOpen() &&
				window.isExtended()) {
			if(button == 0) { //left click
				//Client.getInstance().getGUI().sendPanelToFront(window);
				Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
				mod.toggle();
			}
			else if(button == 1) { //rigth click
				//Client.getInstance().getGUI().sendPanelToFront(window);
				mod.settings_visible = !mod.settings_visible;
				mod.onButtonRightClick();
			}
		}
	}
}