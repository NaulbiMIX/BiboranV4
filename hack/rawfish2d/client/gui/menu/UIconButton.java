package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;

public class UIconButton extends GuiButton
{
	private TimeHelper time;
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

	public UIconButton(int buttonId, boolean discr, String dis, boolean left, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		this.textColor = -1;
		this.left = left;
		this.dis = dis;
		this.discr = discr;
		this.time = new TimeHelper();
	}

	public UIconButton(int buttonId, int x, int y, String buttonText, String resLoc)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		this.textColor = -1;
	}

	public UIconButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.resLoc = resLoc;
		this.textColor = color;
	}

	public UIconButton(int buttonId, int x, int y, String buttonText, String resLoc, int color)
	{
		super(buttonId, x, y, buttonText);
		this.resLoc = resLoc;
		this.textColor = color;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean isOverButton = (mouseX >= this.xPosition) && (mouseX <= this.xPosition + this.width) && (mouseY >= this.yPosition) && (mouseY <= this.yPosition + this.height);
		if (this.enabled)
		{
			if ((isOverButton) && (this.hoversize <= 1)) {
				this.hoversize += 1;
			} else if ((!isOverButton) && (this.hoversize >= 0)) {
				this.hoversize -= 1;
			}

			GL11.glEnable(GL11.GL_BLEND);
			if(!isOverButton) {
				this.rotation = 0;
				this.rotation2 = 0;
				this.anim1 = 0;
				time.reset();
				R2DUtils.drawIconNew(resLoc, xPosition + 2, yPosition, width, height);
			} else {
				if(this.time.hasReached(1000L)) {
					int width = Client.getInstance().mc.fontRenderer.getStringWidth(dis);
					this.anim1 += anim2;

					if(this.anim1 >= width + 5) {
						this.anim1 -= anim2;
					}
					Client.getInstance().mc.fontRenderer.drawStringWithShadow(dis, -width + (int)anim1, 3, -1);
				}
				GL11.glPushMatrix();

				this.rotation += rotation3;
				this.rotation2 += rotation3;

				if(rotation2 > 5 && rotation > 5) {
					this.rotation -= rotation3;
					this.rotation2 -= rotation3;
				}
				if(this.left) {
					GL11.glRotatef(rotation, 0, -rotation1, 0);
				}

				R2DUtils.drawIconNew(resLoc, xPosition + 2, yPosition, width, height);
				GL11.glPopMatrix();
			}
			//R2DUtils.drawBorderedRect(this.xPosition, this.yPosition - this.hoversize, 0.0F, 0.0F, 50, this.height, this.width);

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
