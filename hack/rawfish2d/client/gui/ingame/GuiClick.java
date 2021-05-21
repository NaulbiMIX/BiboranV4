package hack.rawfish2d.client.gui.ingame;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.gui.windows.WindowCombat;
import hack.rawfish2d.client.gui.windows.WindowMisc;
import hack.rawfish2d.client.gui.windows.WindowMovement;
import hack.rawfish2d.client.gui.windows.WindowTest;
import hack.rawfish2d.client.gui.windows.WindowRender;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TestUtils;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;

public class GuiClick extends GuiScreen
{
	public ArrayList<Window> windows = new ArrayList<Window>();
	public ArrayList<Window> unFocusedWindows = new ArrayList<Window>();
	public CopyOnWriteArrayList<Notification> notifications;
	//public WindowItemsScroll wsc = null;

	public Window render = null;
	public Window movement = null;
	public Window combat = null;
	public Window misc = null;
	public Window test = null;
	//public Window world = null;
	//public Window player = null;
	public boolean showedOnce = false;
	
	public void displayNotification(Notification notif) {
		if(!notifications.contains(notif)) {
			notifications.add(notif);
		}
	}
	
	public GuiClick() {
		notifications = new CopyOnWriteArrayList<Notification>();
		
		int x = 2;
		int y = 12;
		combat = new WindowCombat(x, y).init();
		x += 100;
		misc = new WindowMisc(x, y).init();
		x += 100;
		//world = new WindowWorld(x, y).init();
		//x += 100;
		//player = new WindowPlayer(x, y).init();
		//x += 100;
		movement = new WindowMovement(x, y).init();
		x += 100;
		render = new WindowRender(x, y).init();
		x += 100;
		
		test = new WindowTest(x, y).init();
		x += 100;

		/*
		NewSlider slider = new NewSlider(misc, 228f, x, y, 0, 512, false);
		misc.addSlider(slider);
		*/

		windows.add(render);
		windows.add(movement);
		windows.add(combat);
		windows.add(misc);
		//windows.add(world);
		//windows.add(player);
		windows.add(test);

		x = 10;
		y = 300;

		for(Window w : windows) {
			w.setOpen(true);
			w.setExtended(true);
		}
		
		ScaledResolution resolution = MiscUtils.getScaledResolution();
	}

	public void initGui() {
		
	}

	public void sendPanelToFront(Window window) {
		if(windows.contains(window)) {
			int panelIndex = windows.indexOf(window);
			windows.remove(panelIndex);
			windows.add(windows.size(), window);
		}
	}

	public Window getFocusedPanel() {
		return windows.get(windows.size() - 1);
	}
	
	public void drawNotifications(int x, int y) {
		if(this.showedOnce == false) {
			displayNotification(new Notification("1", "§l§nУведомление", "Для того чтоб поменять стиль меню напишите команду '.nextstyle'"));
			
			displayNotification(new Notification("2", "§l§nУведомление", "Для просмотра команд чита напишите в чат '.help'"));
			
			displayNotification(new Notification("3", "§l§nУведомление", "Чтоб написать в общий IRC чат пишите '.c текст'"));
			
			displayNotification(new Notification("4", "§l§nУведомление", "Меню чита открывается на кнопку Ё или ~"));
			
			this.showedOnce = true;
		}
		//showedOnce = false;
		
		//float yoff = -this.height;
		float yoff = -5;
		for(Notification n : notifications) {
			//n.visible = true;
			n.setYOffset(yoff);
			
			if(n.isVisible()) {
				n.draw(x, y);
			}
			else {
				notifications.remove(n);
			}
			yoff -= 30f;
		}
	}

	public void drawScreen(int x, int y) {
		drawNotifications(x, y);
		
		if(this.mc != null) {
			if(this.mc.gameSettings != null) {
				if(this.mc.gameSettings.keyBindPlayerList != null) {
					if(this.mc.gameSettings.showDebugInfo == true || this.mc.gameSettings.keyBindPlayerList.pressed) {
						return;
					}
				}
			}
		}
		
		if(GUI.instance.isToggled() == false) {
			for(Window w : windows) {
				if(w.isPinned()) {
					w.draw(x, y);
				}
			}
		}
		else {
			GuiUtils.drawGradientRect(0, 0, this.width, this.height, 0x60101010, 0x60101010);
			for(Window w : windows) {
				w.draw(x, y);
			}

			for(Window w : windows) {
				w.drawToolTip();
			}
		}
		/*
		for(Window w : windows) {
			for(Button button : w.buttons) {
				button.mod.onDrawSettings(x, y);
			}
		}
		*/
	}
	
	public Window getWindowUnderMouse(int mx, int my) {
		Window frontWindow = null;
		//Button frontButton = null;
		
		//Get window list under mouse
		List<Window> win_list = new ArrayList<Window>();
		for(Window window: windows) {
			if(window.isOpen && !window.isExtended) {
				int x1 = window.xyTOP[0];
				int y1 = window.xyTOP[1];
				
				int x2 = window.xyzaEXTEND[2];
				int y2 = window.xyzaEXTEND[3];
				
				if(R2DUtils.isMouseOver(x1, y1, x2, y2, mx, my)) {
					win_list.add(window);
				}
			}
			else if(window.isExtended) {
				int x1 = window.xyTOP[0];
				int y1 = window.xyTOP[1];
				
				int x2 = window.xyBG[0] + window.wBG;
				int y2 = window.xyBG[1] + (11 * window.buttons.size() + 4);
				
				if(R2DUtils.isMouseOver(x1, y1, x2, y2, mx, my)) {
					win_list.add(window);
				}
			}
		}
		
		//win.xyBG[0] + win.wBG, win.xyBG[1] + (11 * win.buttons.size() + 4)
		
		if(win_list.size() == 1){
			Window window = win_list.get(0);
			frontWindow = window;
		}
		else {
			for(Window window: win_list) {
				if(this.getFocusedPanel() == window) {
					frontWindow = window;
					break;
				}
			}
		}
		return frontWindow;
	}
	
	public Button getButtonUnderMouse(int mx, int my) {
		Window frontWindow = getWindowUnderMouse(mx, my);
		
		if(frontWindow == null)
			return null;
		
		for(Button b : frontWindow.buttons) {
			if(R2DUtils.isMouseOver(b.xywhBUTTON[0], b.xywhBUTTON[1], b.xywhBUTTON[0] + b.xywhBUTTON[2], b.xywhBUTTON[1] + b.xywhBUTTON[3], mx, my)) {
				return b;
			}
		}
		return null;
	}
	
	public Window getWindowSettingsUnderMouse(int mx, int my) {
		for(Window w : windows) {
			for(Button b : w.buttons) {
				if(b.mod.settings_visible && b.mod.xywhBUTTON != null && b.mod.xywhBG != null) {
					if(R2DUtils.isMouseOver(b.mod.xywhBG[0], b.mod.xywhBG[1], b.mod.xywhBG[0] + b.mod.xywhBG[2], b.mod.xywhBG[1] + b.mod.xywhBG[3], mx, my)) {
						return w;
					}
				}
			}
		}
		return null;
	}
	
	protected void mouseClicked(int mx, int my, int button) {
		try {
			Window frontWindow = getWindowUnderMouse(mx, my);
			
			//If mouse over setting menu, then we dont need to call MouseClick for Window and Button at all
			Window wSettings = getWindowSettingsUnderMouse(mx, my);
			if(wSettings != null) {
				if(frontWindow != getFocusedPanel()) {
					this.sendPanelToFront(wSettings);
					settingsClick(mx, my, button);
					return;
				}
			}
			
			if(frontWindow != null) {
				this.sendPanelToFront(frontWindow);
				frontWindow.mouseClicked(mx, my, button);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		for(Module m : Client.getInstance().getModules()) {
			for(IGuiElement ime : m.elements) {
				//if(ime instanceof WindowItemsScroll) {
					ime.mouseClicked(mx, my, button);
				//}
			}
		}
		/*
		for(Notification n : notifications) {
			n.mouseClicked(mx, my, button);
		}
		*/
	}
	
	private void settingsClick(int mx, int my, int button) {		
		for(Module m : Client.getInstance().getModules()) {
			
			for(IGuiElement el : m.elements) {
				
				if(el instanceof RadioBox) {
					RadioBox r = (RadioBox)el;
					
					if(mx >= r.xyBOX[0] - (r.getW() / 2) && my >= r.xyBOX[1] - (r.getW() / 2) && mx <= r.xyBOX[0] - (r.getW() / 2) + r.getW() && my <= r.xyBOX[1] - (r.getW() / 2) + r.getH() && button == 0) {
						
						for(IGuiElement el2 : m.elements) {
							
							if(el2 instanceof RadioBox) {
								RadioBox r2 = (RadioBox)el2;
								
								if(el2 != el) {
									r2.setState(false);
								}
							}
						}
						el.mouseClicked(mx, my, button);
					}
				}
			}
		}
		
		for(Module m : Client.getInstance().getModules()) {
			for(IGuiElement ime : m.elements) {
				if(!(ime instanceof RadioBox))
					ime.mouseClicked(mx, my, button);
			}
		}
	}

	protected void mouseMovedOrUp(int x, int y, int button) {
		try {
			for(Window window: windows) {
				if(window.isOpen) {
					window.mouseMovedOrUp(x, y, button);
				}
			}
		}
		catch(Exception ex) {}

		for(Module m : Client.getInstance().getModules()) {
			for(IGuiElement ime : m.elements) {
				ime.mouseMovedOrUp(x, y, button);
			}
		}
	}
	
	public void keyTyped(char ch, int ivar) {
		int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		Window win = getWindowSettingsUnderMouse(var1, var2);
		
		if(win == null) {
			return;
		}
		
		//MiscUtils.sendChatClient("screen:" + mc.currentScreen);
		
		if(!win.isExtended || !win.isOpen) {
			return;
		}
		
		for(Module m : Client.getInstance().getModules()) {
			for(IGuiElement ime : m.elements) {
				if(ime instanceof InputBox) {
					InputBox ib = (InputBox)ime;
					ib.keyTyped(ch, ivar);
				}
			}
		}
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
}