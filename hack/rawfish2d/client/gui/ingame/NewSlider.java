package hack.rawfish2d.client.gui.ingame;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.ModCombat.MultiAura;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;

public class NewSlider implements IGuiElement {
	public Module mod;
	private String text;
	public String modName;
	private DoubleValue sliderValue;
	private int xPos;
	private int yPos;
	private float maxValue;
	private float minValue;
	private boolean shouldRound;
	private boolean dragging;
	private double dragX;
	private double lastDragX;
	private int drawSliderWidth;
	public int sliderWidth;

	private float[] xyBOX;
	private float[] xyTEXT;
	private float wBOX;
	private float hBOX;

	public boolean visible = false;

	public void dragSlider(int x2) {
		dragX = x2 - lastDragX;
	}

	public NewSlider(Module mod, String text, DoubleValue value, int x2, int y2, boolean shouldRound) {
		this.mod = mod;
		this.text = text;
		this.sliderValue = value;
		this.xPos = x2;
		this.yPos = y2 + 5;
		this.maxValue = (float) value.getMax();
		this.minValue = (float) value.getMin();
		this.drawSliderWidth = 100;
		this.shouldRound = shouldRound;
		this.sliderWidth = drawSliderWidth;

		setValue(value.getValue());

		//this.xyBOX = new float[] {xPos + menu.dragX + dragX + 3f, yPos + menu.dragY + 3f};
		this.xyBOX = new float[] {(float) (xPos + 90 + dragX), yPos + 10};
		this.xyTEXT = new float[] {xPos + 90, yPos};
		this.wBOX = 6f;
		this.hBOX = 6f;
		//from NullyBean B28 or B31 idk... but modified af
	}

	@Override
	public void draw(int x2, int y2) {
		if(sliderValue == null)
			return;
		
		if (dragging) {
			dragSlider(x2);
		}

		if (dragX < 0.0f)
			dragX = 0.0f;

		if (dragX > sliderWidth)
			dragX = sliderWidth;
		
		update();
		
		DecimalFormat format = new DecimalFormat(shouldRound ? "0" : "0.000");

		GuiUtils.drawStringWithShadow(text + " : " + format.format(sliderValue.getValue()), (int)(xyTEXT[0]), (int)(xyTEXT[1]), 0xFFFFFFFF);

		//System.out.println("y: " + xyBOX[1]);
		
		float lineOffset = 2.5f;
		R2DUtils.drawLine2D(xPos + mod.xywhBUTTON[0] + 90, xyBOX[1] + lineOffset, xPos + mod.xywhBUTTON[0] + drawSliderWidth + 90, xyBOX[1] + lineOffset, 1f, 0xFFAAAAAA);

		R2DUtils.drawLine2D(xPos + mod.xywhBUTTON[0] + 90, xyBOX[1] + lineOffset, xyBOX[0] + (wBOX / 2), xyBOX[1] + lineOffset, 1f, 0xFF00FF00);
		
		wBOX = 5f;
		hBOX = 5f;
		
		GuiUtils.drawBorderedRect1(xyBOX[0], xyBOX[1], xyBOX[0] + wBOX, xyBOX[1] + hBOX, 0xFFAAAAAA, 0xFF222222);

		float fraction = drawSliderWidth / (maxValue - minValue);
		//sliderValue.setValue(dragX / fraction + minValue);
		this.setValue(dragX / fraction + minValue);
	}

	public void update() {
		xyBOX[0] = (int) (xPos + mod.xywhBUTTON[0] + 90 + dragX);
		xyBOX[1] = (int) (yPos + mod.xywhBUTTON[1] + 10);

		xyTEXT[0] = (float)(xPos + mod.xywhBUTTON[0] + 90);
		xyTEXT[1] = (float)(yPos + mod.xywhBUTTON[1]);
	}

	public double getValue() {
		//return Float.parseFloat(new DecimalFormat("0.000").format(sliderValue.getValue()));
		return (float)sliderValue.getValue();
	}

	public void setValue(double value) {
		sliderValue.setValue(value);

		double fraction = drawSliderWidth / (maxValue - minValue);
		dragX = fraction * (value - minValue);
	}
	
	@Override
	public void mouseClicked(int x2, int y2, int button) {
		if (button == 0 && x2 >= xyBOX[0] && y2 >= xyBOX[1] && x2 <= xyBOX[0] + wBOX && y2 <= xyBOX[1] + hBOX) {
			lastDragX = x2 - dragX;
			dragging = true;
		}
	}

	@Override
	public void mouseMovedOrUp(int x2, int y2, int b2) {
		if (b2 == 0) {
			dragging = false;
		}
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public int getX() {
		return this.xPos;
	}

	@Override
	public int getY() {
		return this.yPos;
	}

	@Override
	public int getW() {
		return this.sliderWidth;
	}

	@Override
	public int getH() {
		return this.yPos;
	}
	
	@Override
	public String text() {
		return text;
	}
	
	@Override
	public int getTextWidth() {
		return mod.mc.fontRenderer.getStringWidth(text());
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
