package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;

public class UIconButton2 extends GuiButton
{
	private TimeHelper time;
	private String resLoc;
	private int textColor;
	private String facing;
	private boolean moving;
	private int offset;
	private int offsetMax;
	
	public UIconButton2(int buttonId, String facing, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		this.textColor = -1;
		this.facing = facing;
		this.time = new TimeHelper();
		this.moving = false;
		this.offset = 0;
		this.offsetMax = 32;
	}

	public UIconButton2(int buttonId, int x, int y, String buttonText, String resLoc)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		this.textColor = -1;
	}

	public UIconButton2(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		this.textColor = color;
	}

	public UIconButton2(int buttonId, int x, int y, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		this.textColor = color;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		int xx = 0;
		int yy = 0;
		int xx2 = 0;
		int yy2 = 0;

		if(facing.equalsIgnoreCase("right")) {
			xx2 += offset;
		}
		else if(facing.equalsIgnoreCase("left")) {
			xx2 -= offset;
		}
		else if(facing.equalsIgnoreCase("down")) {
			yy2 += offset;
		}
		else if(facing.equalsIgnoreCase("up")) {
			yy2 -= offset;
		}

		xx += this.xPosition;
		yy += this.yPosition;
		xx2 += this.xPosition + this.width;
		yy2 += this.yPosition + this.height;

		isOverButton = R2DUtils.isMouseOver(xx, yy, xx2, yy2, mouseX, mouseY);

		if (this.enabled)
		{
			offsetMax = 12;
			GL11.glPushMatrix();
			if (isOverButton && offset == 0) {
				moving = true;
			}
			if(!isOverButton && offset > 0) {
				offset--;
				moving = false;
			}

			if(moving && offset < offsetMax) {
				offset++;
			}
			if(offset >= offsetMax) {
				offset = offsetMax;
				moving = false;
			}

			GL11.glEnable(GL11.GL_BLEND);
			if(facing.equalsIgnoreCase("right"))
				R2DUtils.drawIconNew(resLoc, xPosition + offset, yPosition, width, height);
			else if(facing.equalsIgnoreCase("left"))
				R2DUtils.drawIconNew(resLoc, xPosition - offset, yPosition, width, height);
			else if(facing.equalsIgnoreCase("down"))
				R2DUtils.drawIconNew(resLoc, xPosition, yPosition + offset, width, height);
			else if(facing.equalsIgnoreCase("up"))
				R2DUtils.drawIconNew(resLoc, xPosition, yPosition - offset, width, height);

			int scale = 18;
			if(!isOverButton) {
				//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, this.textColor);
				R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + this.height + 4, textColor);
			} else {
				//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, -200);
				R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + this.width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + this.height + 4, -200);
			}
			GL11.glPopMatrix();
		}
	}

	private void animate() {
	}
}
