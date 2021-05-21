package hack.rawfish2d.client.gui.ingame;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.src.ScaledResolution;

public class Notification implements IGuiElement {
	private int xPos;
	private int yPos;
	private float yOffset;
	private int w;
	private int h;
	private double anim;
	private boolean banimFinished;
	private boolean banim_show_progress;
	private boolean hidden;
	private String name;
	private String text1;
	private String text2;
	private TimeHelper timer;
	private final float crossSize = 12f;
	
	public Notification(String name, String text1, String text2) {
		this.name = name;
		this.text1 = text1;
		this.text2 = text2;
		
		ScaledResolution resolution = MiscUtils.getScaledResolution();
		
		this.w = Client.getInstance().mc.fontRenderer.getStringWidth(text2) + 10;
		this.h = 25;
		
		this.yOffset = 0f;
		this.xPos = resolution.getScaledWidth() - w;
		this.yPos = resolution.getScaledHeight() - h;
		this.timer = new TimeHelper();
		
		reset();
		
		show();
	}
	
	public void setYOffset(float y) {
		this.yOffset = y;
	}
	
	public float getYOffset() {
		return this.yOffset;
	}
	
	public void draw(int mx, int my) {
		if(hidden) {
			return;
		}
		
		if(this.banimFinished) {
			if(this.timer.hasReached(20000)) {
				timer.reset();
				this.hide();
			}
		}
		
		if(!this.banimFinished && banim_show_progress) {
			showAnimation();
		}
		else if(!this.banimFinished && !banim_show_progress) {
			hideAnimation();
		}
		
		float offset = (float) anim;
		float y = yPos + this.yOffset;
		//R2DUtils.drawBetterRect(xPos, yPos, xPos + w, yPos + h, 1f, 0xFF00FF00, 0xFF00AA00);
		R2DUtils.drawBetterRect(xPos + offset, y, xPos + w + offset, y + h, 1f, 0xCC444444, 0xFFFFFFFF);
		
		int text1w = Client.getInstance().mc.fontRenderer.getStringWidth(text1) + 5;
		GuiUtils.drawCenteredString(text1, xPos + 2 + offset + (w / 2) - (text1w / 2), y + 2, 0xFFFFFFFF);
		GuiUtils.drawCenteredString(text2, xPos + 2 + offset, y + 14, 0xFFFFFFFF);
		
		R2DUtils.drawCrossBox(new Vector2f(xPos + offset, y), crossSize, 0x88000000, 0xFFFFFFFF, 0xFFFF0000);
	}
	
	@Override
	public void mouseClicked(int mx, int my, int button) {
		// TODO Auto-generated method stub
		float offset = (float) anim;
		float y = yPos + this.yOffset;
		
		if(R2DUtils.isMouseOver(xPos + offset, y, xPos + offset + crossSize, y + crossSize, mx, my)) {
			if(button == 0) {
				//left click
				Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
				hide();
			}
		}
	}
	
	private void reset() {
		hidden = true;
		banimFinished = false;
		banim_show_progress = false;
		anim = w;
	}
	
	public void hide() {
		banim_show_progress = false;
		anim = 0;
		banimFinished = false;
	}
	
	public boolean isVisible() {
		return !hidden;
	}
	
	public void show() {
		banim_show_progress = true;
		anim = w;
		banimFinished = false;
		hidden = false;
	}
	
	private void hideAnimation() {
		if(banimFinished) {
			return;
		}
		
		if(!hidden && !banim_show_progress) {
			if(anim <= w) {
				anim += 2;
				banimFinished = false;
			}
			else {
				anim = w;
				hidden = true;
				banimFinished = true;
				timer.reset();
			}
		}
	}
	
	private void showAnimation() {
		if(banimFinished) {
			return;
		}
		
		if(!hidden && banim_show_progress) {
			if(anim >= 0) {
				anim -= 2;
				banimFinished = false;
			}
			else {
				anim = 0;
				hidden = false;
				banimFinished = true;
				timer.reset();
			}
		}
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getW() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getH() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String text() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTextWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPinned() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void keyTyped(char ch, int ivar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mx, int my) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}
}
