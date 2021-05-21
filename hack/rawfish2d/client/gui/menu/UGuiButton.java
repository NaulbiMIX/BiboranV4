package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;

public class UGuiButton extends GuiButton
{
	TimeHelper time;
	private String resLoc;
	private String resLoc2;
	private int textColor;
	private int hoversize;
	float rotation = 0;
	float rotation1 = 1;
	float rotation2 = 0;
	private String dis;
	private boolean discr;
	private boolean left;
	float rotation3 = 0.7f;
	private float anim1 = 0;
	private float anim2 = 4f;
	private float anim3 = 0;
	private float anim4 = 0.5f;
	public boolean isOverButton = false;

	public UGuiButton(int buttonId, boolean discr, String dis, boolean left, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		textColor = -1;
		this.left = left;
		this.dis = dis;
		this.discr = discr;
		time = new TimeHelper();
	}

	public UGuiButton(int buttonId, int x, int y, String buttonText, String resLoc)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		textColor = -1;
	}

	public UGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		textColor = color;
	}

	public UGuiButton(int buttonId, int x, int y, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		textColor = color;
	}

	public void update(int mouseX, int mouseY) {
		isOverButton = R2DUtils.isMouseOver(xPosition, yPosition, xPosition + width, yPosition + height, mouseX, mouseY);
	}

	public void drawButton(int mouseX, int mouseY) {
		if (enabled)
		{
			update(mouseX, mouseY);

			if ((isOverButton) && (hoversize <= 1)) {
				hoversize += 1;
			} else if ((!isOverButton) && (hoversize >= 0)) {
				hoversize -= 1;
			}

			GL11.glEnable(GL11.GL_BLEND);
			if(!isOverButton) {
				time.reset();
				R2DUtils.drawIconNew(resLoc, xPosition + 2, yPosition, width, height);
			} else {
				if(time.hasReached(2000L)) {
					int width = Client.getInstance().mc.fontRenderer.getStringWidth(dis);
					anim1 += anim2;

					if(anim1 >= width + 5) {
						anim1 -= anim2;
					}
					Client.getInstance().mc.fontRenderer.drawStringWithShadow(dis, -width + (int)anim1, 3, -1);
				}
				GL11.glPushMatrix();

				R2DUtils.drawIconNew(resLoc, xPosition + 2, yPosition, width, height);
				GL11.glPopMatrix();
			}
			R2DUtils.drawBetterRect(xPosition, yPosition - hoversize, 0.0F, 0.0F, 50, height, width);

			/*
			if(!isOverButton) {
				Client.mc.fontRenderer.drawString(displayString, xPosition + width / 2 - Client.mc.fontRenderer.getStringWidth(displayString) / 2 + 1, yPosition + height + 4, textColor);
			} else {
				Client.mc.fontRenderer.drawString(displayString, xPosition + width / 2 - Client.mc.fontRenderer.getStringWidth(displayString) / 2 + 1, yPosition + height + 4, -200);
			}
			*/

			int scale = 18;
			if(!isOverButton) {
				//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, this.textColor);
				R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + this.height + 4, textColor);
			} else {
				//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, -200);
				R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + this.width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + this.height + 4, -200);
			}
		}
	}
}
