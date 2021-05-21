package net.minecraft.src;

import net.minecraft.client.Minecraft;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.gui.menu.UGuiMainMenu;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;

public class GuiButton extends Gui
{
	float scrol = 0;
	protected float scrolspeed = 0.3f;
	/** Button width in pixels */
	protected int width;
	protected TimeHelper time;
	/** Button height in pixels */
	protected int height;

	/** The x position of this control. */
	public int xPosition;

	/** The y position of this control. */
	public int yPosition;

	/** The string displayed on this control. */
	public String displayString;

	/** ID for this control. */
	public int id;

	/** True if this control is enabled, false to disable. */
	public boolean enabled;

	/** Hides the button completely if false. */
	public boolean drawButton;
	protected boolean isOverButton;

	public GuiButton(int par1, int par2, int par3, String par4Str)
	{
		this(par1, par2, par3, 200, 20, par4Str);
	}

	public GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
	{
		this.time = new TimeHelper();
		this.width = 200;
		this.height = 20;
		this.enabled = true;
		this.drawButton = true;
		this.id = par1;
		this.xPosition = par2;
		this.yPosition = par3;
		this.width = par4;
		this.height = par5;
		this.displayString = par6Str;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
	 * this button.
	 */
	protected int getHoverState(boolean par1)
	{
		byte var2 = 1;

		if (!this.enabled)
		{
			var2 = 0;
		}
		else if (par1)
		{
			var2 = 2;
		}

		return var2;
	}

	/**
	 * Draws this button to the screen.
	 */
	protected void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			int color = 0;
			int colortext = 0xFFFFFFFF;
			FontRenderer fr = par1Minecraft.fontRenderer;
			par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			isOverButton = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var5 = this.getHoverState(isOverButton);
			int var6 = 0xFFE0E0E0;
			
			if(isOverButton){
				//ultrakek
				color = ColorUtil.rainbow((long) 1f, 1f).getRGB();
			}
			if(isOverButton){
				colortext = new Color(0xD1D0D0).getRGB();
				/*
				this.scrol += scrolspeed;
				if(this.scrol >= 5){
					this.scrolspeed = 0;
				}
				*/
				if(this.scrol < 8){
					this.scrol += 0.69f;
				}
			} else {
				/*
				this.scrol = 0;
				//ultrakek
				if(Minecraft.getMinecraft().currentScreen instanceof UGuiMainMenu){
					this.scrolspeed = 0.5f;
				}else{
					this.scrolspeed = 0.3f;
				}
				*/
				if(this.scrol > 0){
					this.scrol -= 0.69f;
				}
			}
			
			//System.out.println("this : " + this);
			
			if (!this.enabled){
				//ultrakek
				R2DUtils.drawBetterRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1, 0, new Color(color).getRGB());
			}else{
				//ultrakek
				R2DUtils.drawBetterRect(this.xPosition + scrol, this.yPosition, this.xPosition + this.width - scrol - 1, this.yPosition + this.height, 1f, 0x80000000, color);
				//Gui.drawRect((int) (this.xPosition + scrol), this.yPosition, (int) (this.xPosition + this.width - scrol), this.yPosition + this.height, Integer.MIN_VALUE);
			}
			
			this.mouseDragged(par1Minecraft, par2, par3);

			if (!this.enabled) {
				colortext = 0xFFA0A0A0;
			}
			else if (isOverButton) {
				colortext = 0xFFFFFFFF;
			}
			else
				colortext = 0xFFDDDDDD;

			this.drawCenteredString(fr, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, colortext);
		}
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int par1, int par2) {}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		return this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
	}

	public boolean func_82252_a()
	{
		return isOverButton;
	}

	public void func_82251_b(int par1, int par2) {}
}
