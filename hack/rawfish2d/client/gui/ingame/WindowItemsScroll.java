package hack.rawfish2d.client.gui.ingame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.ModRender.PlayerInfo;
import hack.rawfish2d.client.ModRender.XRAY;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.SharedIntList;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;

public class WindowItemsScroll implements IGuiElement
{
	private class BlockNode {
		public ItemStack item;
		public boolean enabled;
		
		public BlockNode(int id, boolean enabled) {
			item = new ItemStack(id, 1, 0);
			item.getIconIndex();
			this.enabled = enabled;
		}
	}
	
	public String title;
	public int xPos;
	public int yPos;

	public boolean isOpen;
	public boolean isExtended;
	public boolean isPinned;

	public int dragX;
	public int dragY;
	public int lastDragX;
	public int lastDragY;
	public boolean dragging;
	
	private Module mod;
	public ArrayList<BlockNode> stored_blocks = new ArrayList<BlockNode>();
	private SharedIntList mod_blocks;
	public boolean visible;
	
	public int maxcol = 8;
	public int maxrow = 8;
	public int icon_size = 16;
	
	public int[] xyTOP;
	public int[] xyBG;
	public int[] sizeTOP;
	public int wBG;

	//public int[] xyzaPIN;
	public int[] xyzaEXTEND;

	public static boolean rainbow;
	public int hBG;
	
	public int[] xywhSBar;
	public boolean sbar_dragging = false;
	public int sbar_dragY = 0;
	public int sbar_lastDragY = 0;

	public WindowItemsScroll(Module mod, String title, SharedIntList slist_blocks, int minid, int maxid, int x, int y)
	{
		this.mod = mod;
		this.mod_blocks = slist_blocks;
		this.title = title;
		xPos = x;
		yPos = y;
		dragging = false;
		rainbow = false;
		
		visible = true;
		
		sizeTOP = new int[] {62, 12};

		xyTOP = new int[] {0, 0};
		xyBG = new int[] {0, 0};

		//xyzaPIN = new int[] {0, 0, 0, 0};
		xyzaEXTEND = new int[] {0, 0, 0, 0};

		wBG = 0;
		
		update();
	}

	public void update() {
		int scroll_w = 12;
		wBG = (16 * maxcol) + (maxcol * 2) + 6 + scroll_w;
		hBG = (16 * maxrow) + (maxrow * 2) + 6;
		
		if(xywhSBar == null) {
			xywhSBar = new int[] {0, 0, 0, 0};
		}
		
		sizeTOP[0] = wBG - 16;
		sizeTOP[1] = 12;
		xyTOP[0] = xPos + dragX;
		xyTOP[1] = yPos + dragY;
		xyBG[0] = xPos + dragX;
		xyBG[1] = yPos + dragY + sizeTOP[1] + 2;

		//xyzaPIN[0] = xyTOP[0] + sizeTOP[0] + 2;
		//xyzaPIN[1] = xyTOP[1];
		//xyzaPIN[2] = xyzaPIN[0] + sizeTOP[1];
		//xyzaPIN[3] = xyzaPIN[1] + sizeTOP[1];

		xyzaEXTEND[0] = xyTOP[0] + sizeTOP[0] + 4;
		xyzaEXTEND[1] = xyTOP[1];
		xyzaEXTEND[2] = xyzaEXTEND[0] + sizeTOP[1];
		xyzaEXTEND[3] = xyzaEXTEND[1] + sizeTOP[1];

		//wBG = xyzaEXTEND[2] - xyTOP[0];
		xywhSBar[0] = xyBG[0] + wBG - 12;
		xywhSBar[1] = xyTOP[1] + 18 + sbar_dragY;
		xywhSBar[2] = 8;
		xywhSBar[3] = 10;
		
		int id = 0;
		boolean done = false;
		
		stored_blocks.clear();
		for(int col = 0; col < col + 100; ++col) {
			if(done)
				break;
			
			for(int row = 0; row < row + 100; ++row) {
				//if(id >= 500) {
				if(id > 160) {
					done = true;
					break;
				}
				
				//int render_y = xyBG[1] + (col * icon_size) + (col * 2);
				//int render_x = xyBG[0] + (row * icon_size) + (row * 2);
				
				try {
					//if(!XRAY.blocks.contains(id))
					if(!mod_blocks.contains(id))
						stored_blocks.add(new BlockNode(id, false));
					else
						stored_blocks.add(new BlockNode(id, true));
				}
				catch(Exception ex) {
					row--;
				}
				id++;
			}
		}
	}

	public void windowDragged(int x, int y) {
		dragX = x - lastDragX;
		dragY = y - lastDragY;

		update();
	}

	public void addButton(Module mod) {
		//buttons.add(new Button(this, mod, xPos + 2, yPos + (11 * buttons.size()) + sizeTOP[1] + 4, 85, 10));
	}

	public void draw(int x, int y) {
		//GUIStyle.drawWindow(x, y, this);
		//xPos = 100;
		//yPos = 100;
		
		if(!visible)
			return;
		
		if(this.sbar_dragging) {
			sbar_dragY = y - sbar_lastDragY;
			
			if(sbar_dragY < 0) {
				sbar_dragY = 0;
			}
			else if(sbar_dragY > hBG - xywhSBar[3] - 8) {
				sbar_dragY = hBG - xywhSBar[3] - 8;
			}
			
			update();
		}
		
		if(this.dragging) {
			windowDragged(x, y);
			update();
		}
		
		GuiUtils.drawBorderedRect1(xyTOP[0], xyTOP[1], xyTOP[0] + sizeTOP[0], xyTOP[1] + sizeTOP[1], 0xFFFFFFFF, 0xFF000000);
		GuiUtils.drawCenteredString(this.title, xPos + dragX + 2, yPos + dragY + 2, 0xFFFFFFFF);
		
		/*
		if(!isPinned)
			GuiUtils.drawBorderedRect1(xyzaPIN[0], xyzaPIN[1], xyzaPIN[2], xyzaPIN[3], 0xFFFFFFFF, 0xFF000000);
		else
			GuiUtils.drawBorderedRect1(xyzaPIN[0], xyzaPIN[1], xyzaPIN[2], xyzaPIN[3], 0xFF00FF00, 0xFF000000);
		*/
		
		if(!isExtended)
			GuiUtils.drawBorderedRect1(xyzaEXTEND[0], xyzaEXTEND[1], xyzaEXTEND[2], xyzaEXTEND[3], 0xFFFFFFFF, 0xFF000000);
		else
			GuiUtils.drawBorderedRect1(xyzaEXTEND[0], xyzaEXTEND[1], xyzaEXTEND[2], xyzaEXTEND[3], 0xFF00FF00, 0xFF000000);
		
		if(!isExtended)
			return;
		
		GuiUtils.drawBorderedRect1(xyBG[0], xyBG[1], xyBG[0] + wBG, xyBG[1] + hBG, 0xFF00FF00, 0xFF000000);
		
		//scroll bar
		GuiUtils.drawBorderedRect1(xywhSBar[0], xywhSBar[1], xywhSBar[0] + xywhSBar[2], xywhSBar[1] + xywhSBar[3], 0xAAFFFFFF, 0xAABBBBBB);
		//scroll bar
		
		//draw blocks
		drawItems();
		
		//draw tooltip
		int max_sbar_pos = hBG - xywhSBar[3] - 8;
		int total_col = (stored_blocks.size() / maxcol) - maxcol + 1;
		float offset = (max_sbar_pos / total_col);
		float offset2 = (sbar_dragY / offset);
		int id = 1 + Math.round(offset2) * maxcol;
		
		int icon_size = 16;
		
		boolean done = false;
		
		//GuiScreen.zLevel -= 1.2f;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(int col = 0; col < maxcol; ++col) {
			if(done)
				break;
			
			for(int row = 0; row < maxrow; ++row) {
				if(id >= stored_blocks.size()) {
					done = true;
					break;
				}
				
				int render_y = xyBG[1] + (col * icon_size) + (col * 2);
				int render_x = xyBG[0] + (row * icon_size) + (row * 2);
				
				if(R2DUtils.isMouseOver(render_x, render_y, render_x + icon_size, render_y + icon_size, x, y)) {
					BlockNode xn = stored_blocks.get(id);
					
					int ofx = x - render_x + 10;
					int ofy = y - render_y;
					String str = "ID: " + xn.item.itemID;
					int strw = Client.getInstance().mc.fontRenderer.getStringWidth(str) + 5;
					
					//GuiUtils.drawBorderedRect1(render_x + 2, render_y + 2, render_x + icon_size + 3, render_y + icon_size + 3, 0xFF0000FF, 0x00000000);
					R2DUtils.drawBetterRect(render_x + 2, render_y + 2, render_x + icon_size + 2.5f, render_y + icon_size + 2.5f, 2f, 0x00000000, 0xFF0000FF);
					
					GuiUtils.drawBorderedRect1(render_x + ofx, render_y + ofy, render_x + strw + ofx, render_y + 12 + ofy, 0xFF00FF00, 0xFF000000);
					
					GuiUtils.drawCenteredString(str, render_x + ofx + 2, render_y + ofy + 2, 0xFFFFFFFF);
				}
				
				id++;
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
		//GuiScreen.zLevel += 1.2f;
	}
	
	private void drawItems() {
		FontRenderer fr = Client.getInstance().mc.fontRenderer;
		RenderEngine re = Client.getInstance().mc.renderEngine;
		
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		RenderHelper.enableGUIStandardItemLighting();
		
		int max_sbar_pos = hBG - xywhSBar[3] - 8;
		int total_col = (stored_blocks.size() / maxcol) - maxcol + 1;
		float offset = (max_sbar_pos / total_col);
		float offset2 = (sbar_dragY / offset);
		int id = 1 + Math.round(offset2) * maxcol;
		
		int icon_size = 16;
		
		boolean done = false;
		
		for(int col = 0; col < maxcol; ++col) {
			if(done)
				break;
			
			for(int row = 0; row < maxrow; ++row) {
				if(id >= stored_blocks.size()) {
					done = true;
					break;
				}
				
				try {
					BlockNode xn = stored_blocks.get(id);
					drawOneItem(xn, col, row, fr, re);
				}
				catch(Exception ex) {
					row--;
					//done = true;
					//break;
				}
				
				id++;
			}
		}
		RenderHelper.disableStandardItemLighting();
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	private void drawOneItem(BlockNode xn, int col, int row, FontRenderer fr, RenderEngine re) {
		int render_y = xyBG[1] + (col * icon_size) + (col * 2);
		int render_x = xyBG[0] + (row * icon_size) + (row * 2);
		
		float off_x = 2.5f;
		float off_y = 2.5f;
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		if(xn.enabled) {
			//GuiUtils.drawBorderedRect1(render_x + off_x - 1, render_y + off_y - 1, render_x + off_x + 1 + icon_size, render_y + off_y + 1 + icon_size, 0xFF00FF00, 0xFF00AA00);
			//R2DUtils.drawRect2(render_x + off_x - 1, render_y + off_y - 1, render_x + off_x + 1 + icon_size, render_y + off_y + 1 + icon_size, new Color(0xFF00FF00));
			
			float x1 = render_x + off_x - 0.5f;
			float y1 = render_y + off_y - 0.5f;
			float x2 = x1 + 0.5f + icon_size;
			float y2 = y1 + 0.5f + icon_size;
			
			//R2DUtils.drawBetterRect(x1, y1, x2, y2, 1f, 0xFF00AA00, 0xFF00FF00);
			
			//GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);
			R2DUtils.drawBetterRect(x1, y1, x2, y2, 1f, 0x8000AA00, 0xFF00FF00);
			GL11.glEnable(GL11.GL_LIGHTING);
			//GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		//GL11.glEnable(GL11.GL_LIGHTING);
		
		off_x = 2f;
		off_y = 2f;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderItem.renderItemIntoGUI(fr, re, xn.item, (int)(render_x + off_x), (int)(render_y + off_y));
		MiscUtils.renderEffect(fr, re, xn.item, (int)(render_x + off_x), (int)(render_y + off_y));
		RenderItem.renderItemOverlayIntoGUI(fr, re, xn.item, (int)(render_x + off_x), (int)(render_y + off_y));
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public void drawToolTip() {
		
	}

	public void mouseClicked(int x, int y, int button) {		
		if(R2DUtils.isMouseOver(xywhSBar[0], xywhSBar[1], xywhSBar[0] + xywhSBar[2], xywhSBar[1] + xywhSBar[3], x, y)) {
			//Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			sbar_dragging = true;
			sbar_lastDragY = y - sbar_dragY;
		}
		
		/*
		if(R2DUtils.isMouseOver(xyzaPIN[0], xyzaPIN[1], xyzaPIN[2], xyzaPIN[3], x, y)) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isPinned = !isPinned;
		}
		*/
		
		if(R2DUtils.isMouseOver(xyzaEXTEND[0], xyzaEXTEND[1], xyzaEXTEND[2], xyzaEXTEND[3], x, y)) {
			Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			isExtended = !isExtended;
		}
		
		if(x >= xPos + dragX && y >= yPos + dragY && x <= xPos + 200 + dragX && y <= yPos + 12 + dragY)
		{
			//Client.getInstance().getGUI().sendPanelToFront(this);
			dragging = true;
			lastDragX = x - dragX;
			lastDragY = y - dragY;
		}
		
		if(!isExtended)
			return;
		
		int max_sbar_pos = hBG - xywhSBar[3] - 8;
		int total_col = (stored_blocks.size() / maxcol) - maxcol + 1;
		float offset = (max_sbar_pos / total_col);
		float offset2 = (sbar_dragY / offset);
		int id = 1 + Math.round(offset2) * maxcol;
		
		int icon_size = 16;
		
		boolean done = false;
		
		for(int col = 0; col < maxcol; ++col) {
			if(done)
				break;
			
			for(int row = 0; row < maxrow; ++row) {
				if(id >= stored_blocks.size()) {
					done = true;
					break;
				}
				
				int render_y = xyBG[1] + (col * icon_size) + (col * 2);
				int render_x = xyBG[0] + (row * icon_size) + (row * 2);
				
				if(R2DUtils.isMouseOver(render_x, render_y, render_x + icon_size, render_y + icon_size, x, y)) {
					Client.getInstance().mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					BlockNode bn = stored_blocks.get(id);
					bn.enabled = !bn.enabled;
					/*
					if(xn.enabled) {
						if(!XRAY.isXrayBlock(xn.item.itemID)) {
							XRAY.blocks.add(xn.item.itemID);
						}
					}
					else {
						for(int a = 0; a < XRAY.blocks.size(); ++a) {
							if(XRAY.blocks.get(a) == xn.item.itemID) {
								XRAY.blocks.remove(a);
							}	
						}
					}
					*/
					
					if(bn.enabled) {
						if(!mod_blocks.contains(new Integer(bn.item.itemID))) {
							mod_blocks.add(new Integer(bn.item.itemID));
						}
					}
					else {
						for(int a = 0; a < mod_blocks.size(); ++a) {
							Integer number = (Integer) mod_blocks.get(a);
							if(number.intValue() == bn.item.itemID) {
								mod_blocks.remove(a);
							}	
						}
					}
				}
				
				id++;
			}
		}
	}

	public void mouseMovedOrUp(int x, int y, int b) {
		if(b == 0) {
			dragging = false;
			sbar_dragging = false;
		}
	}
	
	public String getTitle() {
		return this.title;
	}

	public boolean isExtended() {
		return isExtended;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setOpen(boolean flag) {
		this.isOpen = flag;
	}

	public void setExtended(boolean flag) {
		this.isExtended = flag;
	}

	public void setPinned(boolean flag) {
		this.isPinned = flag;
	}
	
	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getW() {
		return 0;
	}

	@Override
	public int getH() {
		return 0;
	}

	@Override
	public String text() {
		return title;
	}
	
	@Override
	public int getTextWidth() {
		return 0;
	}

	@Override
	public void keyTyped(char ch, int ivar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseOver(int mx, int my) {
		// TODO Auto-generated method stub
		
	}
}