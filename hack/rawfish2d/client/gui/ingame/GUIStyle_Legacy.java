package hack.rawfish2d.client.gui.ingame;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.R2DUtils;

public class GUIStyle_Legacy {
	private static GUIStyleEnum style = GUIStyleEnum.DEFAULT;

	public static GUIStyleEnum getStyle() {
		return style;
	}
	
	public static void setStyle(GUIStyleEnum newstyle) {
		style = newstyle;
	}

	public static int getToolTipTextColor() {
		if(style == GUIStyleEnum.NEW_GREEN)
			return 0xFF00FF00;
		else if(style == GUIStyleEnum.NEW_RED)
			return 0xFFCC0000;
		else if(style == GUIStyleEnum.NEW_BLUE)
			return 0xFF0000FF;
		else if(style == GUIStyleEnum.NEW_GRAY)
			return 0xFFFFFFFF;
		else if(style == GUIStyleEnum.DD_B1)
			return 0xFFFFFFFF;
		else if(style == GUIStyleEnum.DD_B6)
			return 0xFFFFFFFF;
		else if(style == GUIStyleEnum.NODUS)
			return 0xFF00FF00;
		
		else if(style == GUIStyleEnum.DOKIDOKI)
			return 0xFFFFFFFF;
		else if(style == GUIStyleEnum.PURPLE)
			return 0xFFCF00FF;

		return 0xFF00FF00;
	}

	public static int getToolTipBorderColor() {
		if(style == GUIStyleEnum.NEW_GREEN)
			return 0xFF00FF00;
		else if(style == GUIStyleEnum.NEW_RED)
			return 0xFFFF0000;
		else if(style == GUIStyleEnum.NEW_BLUE)
			return 0xFF0000FF;
		else if(style == GUIStyleEnum.NEW_GRAY)
			return 0xFFFFFFFF;
		else if(style == GUIStyleEnum.DD_B1)
			return 0xFF64BA00;
		else if(style == GUIStyleEnum.DD_B6)
			return 0xFF0246FF;
		else if(style == GUIStyleEnum.NODUS)
			return 0xFF00FF00;
		
		else if(style == GUIStyleEnum.DOKIDOKI)
			return 0xFFFF00FF;
		else if(style == GUIStyleEnum.PURPLE)
			return 0xFFAF00FF;

		return 0xFF00FF00;
	}

	/*
	public static int getWinBorderColor() {
		if(style == GUIStyleEnum.NEW_GREEN)
			return 0xFF00FF00;
		else if(style == GUIStyleEnum.NEW_RED)
			return 0xFFFF0000;
		else if(style == GUIStyleEnum.NEW_BLUE)
			return 0xFF0000FF;
		else if(style == GUIStyleEnum.NEW_GRAY)
			return 0xAA999999;
		else if(style == GUIStyleEnum.DD_B1)
			return 0xFF000000;
		else if(style == GUIStyleEnum.DD_B6)
			return 0xFFAAAAAA;
		else if(style == GUIStyleEnum.NODUS)
			return 0xFF00FF00;

		return 0xFFFFFFFF;
	}
	*/

	public static int[] getWinColors() {
		//enabled border | disabled border | top border
		if(style == GUIStyleEnum.NEW_GREEN)
			return new int[] {0xFF00FF00, 0xFFAAAAAA, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.NEW_RED)
			return new int[] {0xFFFF0000, 0xFFAAAAAA, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.NEW_BLUE)
			return new int[] {0xFF0000FF, 0xFFAAAAAA, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.NEW_GRAY)
			return new int[] {0xAA999999, 0xAA555555, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.DD_B1)
			return new int[] {0xFF000000, 0xFFAAAAAA, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.DD_B6)
			return new int[] {0xFFAAAAAA, 0xFFAAAAAA, 0xFFFFFFFF};
		else if(style == GUIStyleEnum.NODUS)
			return new int[] {0xFF00FF00, 0xFFAAAAAA, 0xFFFFFFFF};
		
		else if(style == GUIStyleEnum.DOKIDOKI)
			return new int[] {0xFFFF00FF, 0xFFFFE6F4, 0xFFFF00FF};
		else if(style == GUIStyleEnum.PURPLE)
			return new int[] {0xFFAF00FF, 0xFF7F00AF, 0xFFAF00FF};

		return new int[] {0xFFFFFFFF, 0xFFAAAAAA, 0xFFFFFFFF};
	}

	public static void drawWindow(int x, int y, Window win) {
		drawWindows_Default(x, y, win);
	}

	public static void drawWindows_Default(int x, int y, Window win) {
		if(win.isOpen) {
			if(win.dragging) {
				win.windowDragged(x, y);
			}
			
			if(win.hBG == 30) {
				win.update();
			}

			int[] borderColor = getWinColors();

			if(!win.isExtended) {
				GuiUtils.drawBorderedRect1(win.xyTOP[0], win.xyTOP[1], win.xyTOP[0] + win.sizeTOP[0], win.xyTOP[1] + win.sizeTOP[1], borderColor[2], 0xAA000000);
			} else {
				GuiUtils.drawBorderedRect1(win.xyTOP[0], win.xyTOP[1], win.xyTOP[0] + win.sizeTOP[0], win.xyTOP[1] + win.sizeTOP[1], borderColor[2], 0xAA000000);

				if(win.rainbow)
					GuiUtils.drawBorderedRect2(win.xyBG[0], win.xyBG[1], win.xyBG[0] + win.wBG, win.hBG, 0.5f, 0xFE9F1F79, ColorUtil.rainbow(0.1, 1.0f).getRGB());
				else
					GuiUtils.drawBorderedRect2(win.xyBG[0], win.xyBG[1], win.xyBG[0] + win.wBG, win.hBG, 0.5F, 0xAA000000, 0x50000000);
			}

			//Client.getInstance().mc.fontRenderer.drawString(win.title, win.xPos + 2 + win.dragX, win.yPos + win.dragY + 2, 0xFFFFFFFF);
			//Client.getInstance().mc.fontRenderer.drawString(win.title + "\u2699", win.xPos + 2 + win.dragX, win.yPos + win.dragY + 2, 0xFFFFFFFF);
			Client.getInstance().mc.fontRenderer.drawString(win.title, win.xyTOP[0] + 2, win.xyTOP[1] + 2, 0xFFFFFFFF);

			GuiUtils.drawBorderedRect1(win.xyzaPIN[0], win.xyzaPIN[1], win.xyzaPIN[2], win.xyzaPIN[3], win.isPinned ? borderColor[0] : borderColor[1], win.isPinned ? 0x70070707 : 0x70070707);
			GuiUtils.drawBorderedRect1(win.xyzaEXTEND[0], win.xyzaEXTEND[1], win.xyzaEXTEND[2], win.xyzaEXTEND[3], win.isExtended ? borderColor[0] : borderColor[1], win.isExtended ? 0x70070707 : 0x70070707);
			
			if(win.isExtended || win.isPinned) {
				int index = 0;
				for(Button button : win.buttons) {
					button.draw(index);
					button.mod.xywhBUTTON = button.xywhBUTTON;
					button.mod.onDrawSettings(x, y);
					
					Button b = Client.getInstance().getGUI().getButtonUnderMouse(x, y);
					Window wSettings = Client.getInstance().getGUI().getWindowSettingsUnderMouse(x, y);
					if(b == button && wSettings == null) {
						button.isOverButton = true;
					}
					else {
						button.isOverButton = false;
					}
					
					/*
					if(		x >= button.xywhBUTTON[0] &&
							y >= button.xywhBUTTON[1] &&
							x <= button.xywhBUTTON[0] + button.xywhBUTTON[2] &&
							y <= button.xywhBUTTON[1] + button.xywhBUTTON[3]) {
						button.isOverButton = true;
					} else {
						button.isOverButton = false;
					}
					*/
					
					index++;
				}
			}
		}
	}

	public static void drawButton(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod)
	{
		//FastClick.angle = 0;
		//GL11.glTranslated(0, 0, 0);
		//GL11.glRotatef((float)FastClick.angle, -1.0f, 0f, -1.0f);
		//FastClick.angle += 0.001;
		
		if(style == GUIStyleEnum.NEW_GREEN) {
			drawButton_NEW_GREEN(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.NEW_RED) {
			drawButton_NEW_RED(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.NEW_BLUE) {
			drawButton_NEW_BLUE(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.NEW_GRAY) {
			drawButton_NEW_GRAY(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.DD_B1) {
			drawButton_DD_B1(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.DD_B6) {
			drawButton_DD_B6(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.NODUS) {
			drawButton_Nodus(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		
		else if(style == GUIStyleEnum.DOKIDOKI) {
			drawButton_DokiDoki(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		else if(style == GUIStyleEnum.PURPLE) {
			drawButton_Purple(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
		
		else {
			drawButton_Default(x1, x2, y1, y2, x, y, isOverButton, mod);
		}
	}

	public static void nextStyle() {
		if(style == GUIStyleEnum.DEFAULT) {
			style = GUIStyleEnum.DD_B6;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §eDoomsDay §6B6");
			return;
		}
		else if(style == GUIStyleEnum.DD_B6) {
			style = GUIStyleEnum.DD_B1;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §bDoomsDay §6B1");
			return;
		}
		else if(style == GUIStyleEnum.DD_B1) {
			style = GUIStyleEnum.NODUS;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §aNodus");
			return;
		}
		else if(style == GUIStyleEnum.NODUS) {
			style = GUIStyleEnum.NEW_RED;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §c(NEW RED)");
			return;
		}
		else if(style == GUIStyleEnum.NEW_RED) {
			style = GUIStyleEnum.NEW_GREEN;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §a(NEW GREEN)");
			return;
		}
		else if(style == GUIStyleEnum.NEW_GREEN) {
			style = GUIStyleEnum.NEW_BLUE;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §9(NEW BLUE)");
			return;
		}
		else if(style == GUIStyleEnum.NEW_BLUE) {
			style = GUIStyleEnum.NEW_GRAY;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §f(NEW GRAY)");
			return;
		}
		/*
		else if(style == GUIStyleEnum.NEW_GRAY) {
			style = GUIStyleEnum.DEFAULT;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §r(Default)");
			return;
		}
		*/
		else if(style == GUIStyleEnum.NEW_GRAY) {
			style = GUIStyleEnum.DOKIDOKI;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §dDokiDoki");
			return;
		}
		else if(style == GUIStyleEnum.DOKIDOKI) {
			style = GUIStyleEnum.PURPLE;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §5Purple");
			return;
		}
		else if(style == GUIStyleEnum.PURPLE) {
			style = GUIStyleEnum.DEFAULT;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §r(Default)");
			return;
		}
		
		else {
			style = GUIStyleEnum.DEFAULT;
			Client.getInstance().mc.thePlayer.sendChatToPlayer("GUI Style switched to §cUltimate§eHack §r(Default)");
			return;
		}
	}

	public static void drawButton_Default(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					mod.isToggled() ? 0xFF000000 : 0xFF000000,
					mod.isToggled() ? 0xFF00FF00 : 0xFFBBBBBB,
					mod.isToggled() ? 0xFF00FF00 : 0xFF000000);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF00D000 : 0xFF666666,
					mod.isToggled() ? 0xFF10A010 : 0xFF000000);
		
		//-25 FPS
		Client.getInstance().mc.fontRenderer.drawString(mod.getName(), x, y, mod.isToggled() ? 0x000000 : 0xFFFFFF);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1.5f;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			R2DUtils.drawRect(nx1, ny1, nx2, ny2, 0xFFFFFFFF);
		}
	}

	public static void drawButton_NEW_GREEN(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		//green
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF00CC00 : 0xFF666666,
					mod.isToggled() ? 0xFF00CC00 : 0xFF666666);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF008800 : 0xFF000000,
					mod.isToggled() ? 0xFF008800 : 0xFF000000);
		
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFF : 0xAAAAAA);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	public static void drawButton_NEW_BLUE(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		//blue
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF0000CC : 0xFF666666,
					mod.isToggled() ? 0xFF0000CC : 0xFF666666);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF000088 : 0xFF000000,
					mod.isToggled() ? 0xFF000088 : 0xFF000000);

		Client.getInstance().mc.fontRenderer.drawStringWithShadow(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFF : 0xAAAAAA);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	public static void drawButton_NEW_RED(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		//red
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFFCC0000 : 0xFF666666,
					mod.isToggled() ? 0xFFCC0000 : 0xFF666666);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0xFF000000,
					mod.isToggled() ? 0xFF880000 : 0xFF000000,
					mod.isToggled() ? 0xFF880000 : 0xFF000000);

		Client.getInstance().mc.fontRenderer.drawStringWithShadow(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFF : 0xAAAAAA);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	public static void drawButton_NEW_GRAY(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		//grey
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0x00CCCCCC,
					mod.isToggled() ? 0xAACCCCCC : 0x66999999,
					mod.isToggled() ? 0xAACCCCCC : 0x66999999);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0x00CCCCCC,
					mod.isToggled() ? 0xAA999999 : 0x00AAAAAA,
					mod.isToggled() ? 0xAA999999 : 0x00AAAAAA);

		Client.getInstance().mc.fontRenderer.drawStringWithShadow(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFF : 0xAAAAAA);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	//done
	public static void drawButton_DD_B6(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xFF3073D6 : 0xFF666666,
					mod.isToggled() ? 0xFF4286FF : 0xFF656565,
					mod.isToggled() ? 0xFF0246FF : 0xFF454545);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xFF000000 : 0xFF000000,
					mod.isToggled() ? 0xFF0A6FB9 : 0xFF2A2B2A,
					mod.isToggled() ? 0xFF0A12B8 : 0xFF0A0C0A);

		Client.getInstance().mc.fontRenderer.drawString(mod.getName(), x, y, 0xFFFFFFFF);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1f;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1 + 1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2 - 1;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	//done
	public static void drawButton_DD_B1(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			R2DUtils.drawGradientDoubleBorderedRect_DD((float)x1, (float)y1, (float)x2, (float)y2,
					mod.isToggled() ? 0xFF88CA3C : 0xFF474746,
					mod.isToggled() ? 0xFF7EB441 : 0xFF474746,
					mod.isToggled() ? 0xFF88CA3C : 0xFF474746,
					mod.isToggled() ? 0xFF76A93C : 0xFF474746);
		else
			R2DUtils.drawGradientDoubleBorderedRect_DD((float)x1, (float)y1, (float)x2, (float)y2,
					mod.isToggled() ? 0xFF64BA00 : 0xFF14120E,
					mod.isToggled() ? 0xFF61A710 : 0xFF14120E,
					mod.isToggled() ? 0xFF64BA00 : 0xFF0F0E0C,
					mod.isToggled() ? 0xFF4C8E00 : 0xFF0E0D0B);

		Client.getInstance().mc.fontRenderer.drawStringWithShadow(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFF: 0xFFFFFF);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1f;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1 + 1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2 - 1;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}

	public static void drawButton_Nodus(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0x00000000,
					mod.isToggled() ? 0x00000000 : 0x00000000,
					mod.isToggled() ? 0x00000000 : 0x00000000);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 0.5F,
					0x00000000,
					mod.isToggled() ? 0x00000000 : 0x00000000,
					mod.isToggled() ? 0x00000000 : 0x00000000);

		if(mod.isToggled())
			Client.getInstance().mc.fontRenderer.drawString(mod.getName(), (int)x1, y, 0xFF00FF00);
		else
			Client.getInstance().mc.fontRenderer.drawString(mod.getName(), (int)x1, y, 0xFFFFFFFF);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 2;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2;
			
			R2DUtils.drawRect(nx1, ny1, nx2, ny2, 0xFFFFFFFF);
		}
	}
	
	
	public static void drawButton_DokiDoki(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xFFFFBDE1 : 0xFFFFBDE1,
					mod.isToggled() ? 0xFFFFFFFF : 0xFFFFFFFF,
					mod.isToggled() ? 0xFFFFFFFF : 0xFFFFFFFF);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xCCFFBDE1 : 0xCCFFBDE1,
					mod.isToggled() ? 0xCCFF88FF : 0xCCFFE6F4,
					mod.isToggled() ? 0xCCFF88FF : 0xCCFFE6F4);

		if(isOverButton)
			Client.getInstance().mc.fontRenderer.drawString(mod.getName(), x, y, 0xFFFFAA99);
		else
			Client.getInstance().mc.fontRenderer.drawString(mod.getName(), x, y, mod.isToggled() ? 0xFFFFFFFF : 0xFF000000);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1f;
			float nx1 = (float)x2 - nsize - 0.5f;
			float ny1 = (float)y1 + 1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2 - 1;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFF000000, 0xFF000000, 0xFF000000);
		}
	}
	
	public static void drawButton_Purple(
			double x1,
			double x2,
			double y1,
			double y2,
			int x,
			int y,
			boolean isOverButton,
			Module mod) {
		if(isOverButton && GUI.instance.isToggled())
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xFF000000 : 0xFF000000,
					mod.isToggled() ? 0xFFCFBFFF : 0xFF6F6F6F,
					mod.isToggled() ? 0xFFAFBFFF : 0xFF4F4F4F);
		else
			GuiUtils.drawGradientBorderedRect(x1, y1, x2, y2, 1F,
					mod.isToggled() ? 0xFF000000 : 0xFF000000,
					mod.isToggled() ? 0xFFCF00FF : 0xFF2F2F2F,
					mod.isToggled() ? 0xFFAF00FF : 0xFF0F0F0F);

		Client.getInstance().mc.fontRenderer.drawString(mod.getName(), x, y, 0xFFFFFFFF);
		
		if(mod.elements.size() == 0)
			return;
		else {
			float nsize = 1f;
			float nx1 = (float)x2 - nsize;
			float ny1 = (float)y1 + 1;
			
			float nx2 = (float)nx1 + nsize;
			float ny2 = (float)y2 - 1;
			
			GuiUtils.drawGradientBorderedRect(nx1, ny1, nx2, ny2, 1f, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF);
		}
	}
}
