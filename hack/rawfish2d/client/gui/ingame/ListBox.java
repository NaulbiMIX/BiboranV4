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
import hack.rawfish2d.client.utils.SharedIntList;
import hack.rawfish2d.client.utils.SharedStringList;

public class ListBox implements IGuiElement {
	public Module mod;
	private SharedStringList list;
	private int index;
	private String value;
	public String text;
	private int xPos;
	private int yPos;
	private int w;
	private int h;
	public int[] xywhTextBox;
	public int[] xywhArrowLeft;
	public int[] xywhArrowRight;

	public ListBox(Module mod, String text, SharedStringList list2, int xPos, int yPos) {
		this.mod = mod;
		this.text = text;
		this.xPos = xPos;
		this.yPos = yPos;
		this.list = list2;
		this.index = 0;
		this.value = "";
		this.w = 8;
		this.h = 8;
		this.xywhTextBox = new int[] {0, 0, 0, 0};
		this.xywhArrowLeft = new int[] {0, 0, 0, 0};
		this.xywhArrowRight = new int[] {0, 0, 0, 0};
	}

	public void update() {
		xywhArrowLeft[0] = (int) (xPos + mod.xywhBUTTON[0] + 90);
		xywhArrowLeft[1] = (int) (yPos + mod.xywhBUTTON[1] + 5);
		xywhArrowLeft[2] = 5;
		xywhArrowLeft[3] = 10;
		
		xywhTextBox[0] = xywhArrowLeft[0] + 8;
		xywhTextBox[1] = (int) (yPos + mod.xywhBUTTON[1] + 5) - 1;
		xywhTextBox[2] = 118;
		xywhTextBox[3] = 10 + 2;
		
		xywhArrowRight[0] = xywhArrowLeft[0] + xywhTextBox[2] + 3;
		xywhArrowRight[1] = xywhArrowLeft[1];
		xywhArrowRight[2] = 5;
		xywhArrowRight[3] = 10;
	}

	@Override
	public void draw(int x, int y) {
		if(!mod.settings_visible)
			return;
		
		update();
		
		if(list.getList().size() == 0) {
			value = null;
		}
		
		if(index < list.getList().size() && list.getList().size() != 0) {
			try {
				value = list.get(index);
			}
			catch(Exception ex) {
				ex.printStackTrace();
				return;
			}
		}
		else {
			index = 0;
		}
		
		R2DUtils.drawLeftArrow(xywhArrowLeft[0], xywhArrowLeft[1], 0xFF00FF00, 0xFF22AA22);
		
		if(value != null) {
			int w = Client.getInstance().mc.fontRenderer.getStringWidth(value);
			R2DUtils.drawBetterRect(xywhTextBox[0], xywhTextBox[1], xywhArrowLeft[0] + xywhTextBox[2], xywhTextBox[1] + xywhTextBox[3], 1, 0x00000000, 0xFF00FF00);
			GuiUtils.drawCenteredString(value, xywhTextBox[0] + 2, xywhTextBox[1] + 2, 0xFFFFFFFF);
		}
		else {
			R2DUtils.drawBetterRect(xywhTextBox[0], xywhTextBox[1], xywhArrowLeft[0] + xywhTextBox[2], xywhTextBox[1] + xywhTextBox[3], 1, 0x00000000, 0xFF00FF00);
			GuiUtils.drawCenteredString("Empty", xywhTextBox[0] + 2, xywhTextBox[1] + 2, 0xFFFFFFFF);
		}
		
		R2DUtils.drawRightArrow(xywhArrowRight[0], xywhArrowRight[1], 0xFF00FF00, 0xFF22AA22);
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		if(!mod.settings_visible)
			return;
		
		if(x >= xywhArrowLeft[0] && y >= xywhArrowLeft[1] && x <= xywhArrowLeft[0] + xywhArrowLeft[2] && y <= xywhArrowLeft[1] + xywhArrowLeft[3] && button == 0) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			if(index > 0) {
				index--;
			}
			else {
				index = list.getList().size() - 1;
			}
		}
		else if(x >= xywhArrowRight[0] && y >= xywhArrowRight[1] && x <= xywhArrowRight[0] + xywhArrowRight[2] && y <= xywhArrowRight[1] + xywhArrowRight[3] && button == 0) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			if(index < list.getList().size()) {
				index++;
			}
			else {
				index = 0;
			}
		}
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public int getX() {
		return xPos;
	}
	
	@Override
	public int getY() {
		return yPos + 3;
	}

	@Override
	public int getW() {
		return xywhTextBox[2] + 5;
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
	public int getTextWidth() {
		return getW();
	}

	@Override
	public void keyTyped(char ch, int ivar) {
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