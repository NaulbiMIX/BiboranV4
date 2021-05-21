package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;

public class UGuiButton2 extends GuiButton
{
	private String text;
	public boolean isOverButton = false;
	private float scroll;
	
	private float anim;
	private int alpha;
	private long delay;
	private boolean first;
	private boolean rainbow;
	
	private int color = 0xBB00FF00;
	private int colortext = 0xFFFFFFFF;

	public UGuiButton2(int buttonId, int x, int y, int w, int h, String text, long delay, boolean rainbow) {
		super(buttonId, x, y, w, h, text);
		this.text = text;
		this.time = new TimeHelper();
		this.scrolspeed = 0.5f;
		this.rainbow = rainbow;
		
		this.anim = 0f;
		this.alpha = 0x00000000;
		this.delay = delay;
		this.first = false;
	}

	public void update(int mouseX, int mouseY) {
		isOverButton = R2DUtils.isMouseOver(xPosition, yPosition, xPosition + width, yPosition + height, mouseX, mouseY);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int mx, int my) {
		if(!this.drawButton) 
			return;
		
		update(mx, my);
		
		if(this.rainbow)
			color = ColorUtil.rainbow((long) 1f, 1f).getRGB();
		
		if(isOverButton){
			if(this.scroll < 8){
				this.scroll += 0.69f;
			}
			
			colortext = 0xFFFFFF00;
		} else {
			if(this.scroll > 0){
				this.scroll -= 0.69f;
			}
			colortext = 0xFFFFFFFF;
		}
		
		R2DUtils.drawBetterRect(xPosition + scroll, yPosition + anim, xPosition + width - scroll - 1, yPosition + height + anim, 1.5f, 0x88888888, color);
		
		int scale = 18;
		if(!isOverButton) {
			//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, this.textColor);
			R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + (int)anim + 2, colortext);
		} else {
			//Client.mc.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - Client.mc.fontRenderer.getStringWidth(this.displayString) / 2 + 1, this.yPosition + this.height + 4, -200);
			R2DUtils.drawScaledFont(displayString, scale, (int)(xPosition + this.width / 2 - R2DUtils.getScaledFontWidth(displayString, scale) / 2 + 1), this.yPosition + (int)anim + 1, colortext);
		}
	}
}
