package net.minecraft.src;

import net.minecraft.client.Minecraft;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.R2DUtils;

public class GuiSlider extends GuiButton
{
    /** The value of this slider control. */
    public float sliderValue = 1.0F;

    /** Is this slider control being dragged. */
    public boolean dragging = false;

    /** Additional ID for this slider control. */
    private EnumOptions idFloat = null;

    public GuiSlider(int par1, int par2, int par3, EnumOptions par4EnumOptions, String par5Str, float par6)
    {
        super(par1, par2, par3, 150, 20, par5Str);
        this.idFloat = par4EnumOptions;
        this.sliderValue = par6;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }

                par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
                this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int color = 0xFF000000;
            int color2 = ColorUtil.rainbow((long) 1f, 0.8f).getRGB();
            int color3 = ColorUtil.rainbow((long) 1f, 0.8f).getRGB();
            int x = this.xPosition + (int)(this.sliderValue * (float)(this.width - 8));
            int y = this.yPosition;
            R2DUtils.drawGradientBorderedRect(x + 2, y + 2, x + 6, y + 18, 2, color2, color2, color3);
            //R2DUtils.drawGradientBorderedRect(x + 2, y + 2, x + 6, y + 18, 2, color, color2, color3);
            //R2DUtils.drawGradientHRect(x, y, x + 8, y + 20, 0xFF9F9F9F, 0xFF2F2F2F);
            //R2DUtils.drawGradientBorderedRectReliant(x, y, x + 8, y + 20, 2, new Color(0xFFEAEAEA).getRGB(), 0xFF9F9F9F, 0xFF2F2F2F);
            //this.drawGradientRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 8, this.yPosition + 20, 0xFFFFFFFF, 0xFF0000FF);
            //this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            //this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            par1Minecraft.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
            this.displayString = par1Minecraft.gameSettings.getKeyBinding(this.idFloat);
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }
}
