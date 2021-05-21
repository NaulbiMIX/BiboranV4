package hack.rawfish2d.client.gui.ingame;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.utils.ColorUtil;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;

public class Window
{
	public String title;
	public int xPos;
	public int yPos;

	public boolean isOpen;
	public boolean isExtended;
	public boolean isPinned;

	public int dragX;
	public int dragY;
	public int lastDragX;
	public int lastDragY;
	public boolean dragging;
	public int buttonCount = 0;
	public int sliderCount = 0;
	public ArrayList<Button> buttons = new ArrayList<Button>();
	public int[] xyTOP;
	public int[] xyBG;
	public int[] sizeTOP;
	public int wBG;

	public int[] xyzaPIN;
	public int[] xyzaEXTEND;

	public static boolean rainbow;
	public int hBG;

	public Window(String str, int x, int y)
	{
		title = str;
		xPos = x;
		yPos = y;
		dragging = false;
		rainbow = false;
		
		sizeTOP = new int[] {62, 12};

		xyTOP = new int[] {0, 0};
		xyBG = new int[] {0, 0};

		xyzaPIN = new int[] {0, 0, 0, 0};
		xyzaEXTEND = new int[] {0, 0, 0, 0};

		wBG = 0;

		update();
	}

	public void update() {
		xyTOP[0] = xPos + dragX;
		xyTOP[1] = yPos + dragY;
		xyBG[0] = xPos + dragX;
		xyBG[1] = yPos + dragY + sizeTOP[1] + 2;

		xyzaPIN[0] = xyTOP[0] + sizeTOP[0] + 2;
		xyzaPIN[1] = xyTOP[1];
		xyzaPIN[2] = xyzaPIN[0] + sizeTOP[1];
		xyzaPIN[3] = xyzaPIN[1] + sizeTOP[1];

		xyzaEXTEND[0] = xyzaPIN[2] + 2;
		xyzaEXTEND[1] = xyTOP[1];
		xyzaEXTEND[2] = xyzaEXTEND[0] + sizeTOP[1];
		xyzaEXTEND[3] = xyzaEXTEND[1] + sizeTOP[1];

		wBG = xyzaEXTEND[2] - xyTOP[0];
		hBG = xyBG[1] + (11 * buttons.size() + 4);

		for(Button b : buttons) {
			b.update();
		}
	}

	public void windowDragged(int x, int y) {
		dragX = x - lastDragX;
		dragY = y - lastDragY;

		update();
	}

	public void addButton(Module mod) {
		buttons.add(new Button(this, mod, xPos + 2, yPos + (11 * buttons.size()) + sizeTOP[1] + 4, 85, 10));
	}

	public void draw(int x, int y) {
		GUIStyle_Legacy.drawWindow(x, y, this);
	}

	public void drawToolTip() {
		for(Button button: buttons) {
			if(button.isOverButton)
				button.ttip.draw();
		}
	}

	public void mouseClicked(int x, int y, int button) {
		for(Button xButton: buttons) {
			xButton.mouseClicked(x, y, button);
		}

		if(x >= xPos + 78 + dragX && y >= yPos + dragY && x <= xPos + 90 + dragX && y <= yPos + 12 + dragY)
		{
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isExtended = !isExtended;
		}
		if(x >= xPos + 64 + dragX && y >= yPos + dragY && x <= xPos + 76 + dragX && y <= yPos + 12 + dragY)
		{
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isPinned = !isPinned;
		}
		if(x >= xPos + dragX && y >= yPos + dragY && x <= xPos + 69 + dragX && y <= yPos + 12 + dragY)
		{
			Client.getInstance().getGUI().sendPanelToFront(this);
			dragging = true;
			lastDragX = x - dragX;
			lastDragY = y - dragY;
		}
	}

	public void mouseMovedOrUp(int x, int y, int b) {
		if(b == 0) {
			dragging = false;
		}
	}

	public final String getTitle() {
		return this.title;
	}

	public final int getX() {
		return this.xPos;
	}

	public final int getY() {
		return this.yPos;
	}

	public boolean isExtended() {
		return isExtended;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setOpen(boolean flag) {
		this.isOpen = flag;
	}

	public void setExtended(boolean flag) {
		this.isExtended = flag;
	}

	public void setPinned(boolean flag) {
		this.isPinned = flag;
	}
}