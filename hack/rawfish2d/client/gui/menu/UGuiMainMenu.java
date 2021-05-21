package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.Texture;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiDisconnected;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;

public class UGuiMainMenu extends GuiMainMenu {
	public static UGuiMainMenu instance = null;
	
	private static final int bgMax = 16;
	
	private int l_scroll = 0;
	private int r_scroll = 0;
	private final int scrollspeed = 1;

	private static int backgroundNum1 = MiscUtils.random(0, bgMax);
	private String background1 = null;
	private float alpha1 = 1f;
	
	private int backgroundNum2;
	private String background2 = null;
	private float alpha2 = 0f;
	
	private boolean changingBG = false;
	
	private static float alpha = 1f;
	
	private static final long delay = 6000L;
	
	private TimeHelper time;
	private Minecraft mc;
	private static boolean clicked = false;
	
	private UGuiButton2 ok;
	private UGuiButton2 download;
	private int titlew;
	private int titleh;
	private int titlex;
	private int titley;
	
	public UGuiMainMenu() {
		instance = this;
		mc = Minecraft.getMinecraft();
		time = new TimeHelper();
	}
	
	private boolean shouldShowWarning() {
		if(Client.isLatest)
			return false;
		
		if(clicked) 
			return false;
		
		return true;
	}
	
	private void makeButtons() {
		this.buttonList.clear();
		
		if(!shouldShowWarning()) {
			int w = 150;
			int h = 16;
			int x = (MiscUtils.getScreenWidth() / 2) - (w / 2);
			int y = ((MiscUtils.getScreenHeight()) / 2) - (h / 2) - 50;
			int offset = 5;
			
			long delay = 10;
			long delay_offset = 250;
			
			this.buttonList.add(new UGuiButton2(1, x, y + 7 + ((h + offset) * 0), w, h, "SinglePlayer", delay += delay_offset, false));
			this.buttonList.add(new UGuiButton2(2, x, y + 7 + ((h + offset) * 1), w, h, "MultiPlayer", delay += delay_offset, false));
			this.buttonList.add(new UGuiButton2(0, x, y + 7 + ((h + offset) * 2), w, h, "Options", delay += delay_offset, false));
			this.buttonList.add(new UGuiButton2(70, x, y + 7 + ((h + offset) * 4), w, h, ">>>Website<<<", delay += delay_offset, true));
			this.buttonList.add(new UGuiButton2(20, x, y + 7 + ((h + offset) * 5), w, h, "Change Nickname", delay += delay_offset, false));
			this.buttonList.add(new UGuiButton2(21, x, y + 7 + ((h + offset) * 6), w, h, "Change Proxy", delay += delay_offset, false));
			this.buttonList.add(new UGuiButton2(4, x, y + 7 + ((h + offset) * 7), w, h, "Exit", delay += delay_offset, false));
		}
		else {
			int w = 150;
			int h = 16;
			int x = (MiscUtils.getScreenWidth() / 2) - (w / 2);
			int y = ((MiscUtils.getScreenHeight()) / 2) - (h / 2) + 15;
			download = new UGuiButton2(70, x, y - (h / 2), w, h, "Download", 0, false); 
			y += (h) + 3;
			ok = new UGuiButton2(69, x, y - (h / 2), w, h, "OK", 0, false);
			
			ok.drawButton = shouldShowWarning();
			download.drawButton = shouldShowWarning();
			
			this.buttonList.add(ok);
			this.buttonList.add(download);
		}
	}

	public void initGui(){
		super.initGui();
		
		makeButtons();
		
		titlew = 358;
		titleh = 47;
		titlex = (MiscUtils.getScreenWidth() / 2) - (titlew / 2);
		titley = ((MiscUtils.getScreenHeight()) / 2) - (titleh / 2) - 120;
	}
	
	public void drawFadeout() {
		if(alpha > 0) {
			alpha -= 0.015;
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Texture.bindTexture("/backgrounds/loading.png", MiscUtils.getScreenWidth(), MiscUtils.getScreenHeight(), alpha);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	public void drawScreen(int par1, int par2, float par3){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		defineBG();
		
		drawBackground();
		drawOverlay();
		
		//Texture.bindTexture("/backgrounds/title.png", MiscUtils.getScreenWidth(), MiscUtils.getScreenHeight(), 1f);
		R2DUtils.drawIconNew("/backgrounds/title.png", titlex, titley, titlew, titleh);

		if(shouldShowWarning()) {
			String str = "Ваша версия чита устарела, пожалуйста скачайте новую версию.";
			//float w = R2DUtils.getScaledFontWidth(str, 24);
			int w = mc.fontRenderer.getStringWidth(str);
			int x = (int) ((MiscUtils.getScreenWidth() / 2) - w / 2);
			int y = ((MiscUtils.getScreenHeight()) / 2) - 30;
			
			R2DUtils.drawBetterRect(x - 10, y - 10, x + w + 10, y + 70, 1, 0xCC555555, 0xFFFFFFFF);
			//R2DUtils.drawScaledFont(str, 24, x, y, 0xFFFFFFFF);
			GuiUtils.drawCenteredStringWithShadow(str, x, y, 0xFFFFFFFF);
		}
		super.drawScreen(par1, par2, par3);
		
		onRenderBackGroundRect(par1);
		
		//new
		drawFadeout();
		
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void drawOverlay() {
		String str = Client.getInstance().st.getMainMenuClientStr();
		String modules = Client.getInstance().st.getModulesCountStr();
		String build = Client.getInstance().st.getBuildStr();
		FontRenderer fr = Client.getInstance().mc.fontRenderer;
		
		int w;
		int x;
		int y;
		
		w = fr.getStringWidth(str);
		x = (width / 2) - (w / 2);
		y = height - 32;
		R2DUtils.drawBetterRect(x - 2, y - 1, x + w + 2, y + 9, 1, 0x88888888, 0x88888888);
		drawString(fr, str, x, y, 0);
		w = fr.getStringWidth(modules);
		x = (width / 2) - (w / 2);
		y = height - 21;
		R2DUtils.drawBetterRect(x - 2, y - 1, x + w + 2, y + 9, 1, 0x88888888, 0x88888888);
		drawString(fr, modules, x, y, 0);
		w = fr.getStringWidth(build);
		x = (width / 2) - (w / 2);
		y = height - 10;
		R2DUtils.drawBetterRect(x - 2, y - 1, x + w + 2, y + 9, 1, 0x88888888, 0x88888888);
		drawString(fr, build, x, y, 0);
	}
	
	public void defineBG() {
		if(backgroundNum1 > bgMax)
			backgroundNum1 = 0;
		else if(backgroundNum1 < 0)
			backgroundNum1 = bgMax;
		background1 = "/backgrounds/" + backgroundNum1 + ".jpg";
		
		if(changingBG) {
			if(backgroundNum2 > bgMax)
				backgroundNum2 = 0;
			else if(backgroundNum2 < 0)
				backgroundNum2 = bgMax;
			background2 = "/backgrounds/" + backgroundNum2 + ".jpg";
		}
		else {
			background2 = null;
		}
	}
	
	public void drawBG1() {
		if(background1 != null)
			Texture.bindTexture(background1, MiscUtils.getScreenWidth(), MiscUtils.getScreenHeight(), alpha1);
	}
	
	public void drawBG2() {
		if(background2 != null)
			Texture.bindTexture(background2, MiscUtils.getScreenWidth(), MiscUtils.getScreenHeight(), alpha2);
	}

	public void drawBackground(){
		drawBG1();
		
		if(mc.currentScreen != this) {
			time.reset();
			//return;
		}
		
		boolean reached = time.hasReached(delay);
		if(reached || changingBG) {
			changingBG = true;
			
			if(reached) {
				backgroundNum2 = backgroundNum1 + 1;
				defineBG();
			}
			drawBG2();
			
			float offset = 0.015f;
			
			if(!reached)
				offset = 0.03f;
			
			if(alpha1 > 0)
				alpha1 -= offset;
			
			if(alpha2 < 1)
				alpha2 += offset;
			
			if(alpha1 <= 0 && alpha2 >= 1) {
				changingBG = false;
				
				alpha1 = 1f;
				alpha2 = 0f;
				
				backgroundNum1 = backgroundNum2;
				
				defineBG();
				
				time.reset();
			}
		}
	}

	public void onRenderBackGroundRect(int par1){
		//scroll += scrollspeed;

		if(l_scroll != 0) {
			Gui.drawRect(0, 0, 0 + l_scroll, this.height, Integer.MIN_VALUE);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			int size = 40;
			double x = -20 + l_scroll;
			double y = MiscUtils.getScreenHeight() / 2.3;
			GL11.glTranslated(x, y, 0);
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			Texture.bindTexture("/icons/arrowright.png", size, size);
			GL11.glPopMatrix();
		}

		if(r_scroll != 0) {
			Gui.drawRect(MiscUtils.getScreenWidth() - r_scroll, 0, this.width, this.height, Integer.MIN_VALUE);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			int size = 40;
			double x = MiscUtils.getScreenWidth() - r_scroll;
			double y = MiscUtils.getScreenHeight() / 2.3;
			GL11.glTranslated(x, y, 0);
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			Texture.bindTexture("/icons/arrowleft.png", size, size);
			GL11.glPopMatrix();
		}

		if(l_scroll != 0 && par1 > 20) {
			l_scroll -= scrollspeed;
		}
		if(par1 < 20 && l_scroll < 20) {
			l_scroll += scrollspeed;
		}

		if(par1 < MiscUtils.getScreenWidth() - 20 && r_scroll != 0) {
			r_scroll -= scrollspeed;
		}
		if(par1 > MiscUtils.getScreenWidth() - 20 && r_scroll < 20) {
			r_scroll += scrollspeed;
		}
	}

	protected void mouseClicked(int par1, int par2, int par3){
		if(par1 > MiscUtils.getScreenWidth() - 20 && r_scroll >= 10){
			//backgroundNum1++;
			changingBG = true;
			backgroundNum2 = backgroundNum1 + 1;
			defineBG();
			
			time.reset();
		}

		if(par1 < 20 && l_scroll >= 10){
			//backgroundNum1--;
			changingBG = true;
			backgroundNum2 = backgroundNum1 - 1;
			defineBG();
			
			time.reset();
		}
		super.mouseClicked(par1, par2, par3);
	}

	protected void actionPerformed(GuiButton btn){
		if(!shouldShowWarning()) {
			super.actionPerformed(btn);
			if(btn.id == 20) {
				this.mc.displayGuiScreen(new UGuiChangeNickname(this));
			}
			else if(btn.id == 21) {
				this.mc.displayGuiScreen(new UGuiProxy(this));
			}
		}
		
		if(btn.id == 69 && shouldShowWarning()) {
			for(Object obj : buttonList) {
				GuiButton btn2 = (GuiButton)obj;
				btn2.drawButton = true;
			}
			if(ok != null)
				ok.drawButton = false;
			if(download != null)
				download.drawButton = false;
			
			if(!clicked) {
				clicked = true;
				makeButtons();
			}
		}
		/*
		else if(btn.id == 70) {
			MiscUtils.openWebpage(Client.webpage);
			
			for(Object obj : buttonList) {
				GuiButton btn2 = (GuiButton)obj;
				btn2.drawButton = true;
			}
			if(ok != null)
				ok.drawButton = false;
			if(download != null)
				download.drawButton = false;
			
			if(!clicked) {
				clicked = true;
				makeButtons();
			}
		}
		*/
	}

	public void confirmClicked(boolean par1, int par2) {
		if(par2 == 0)
			this.mc.displayGuiScreen(this);
	}

	public List getGuiButton(){
		return this.buttonList;
	}

	public void updateScreen(){
		super.updateScreen();
	}
}
