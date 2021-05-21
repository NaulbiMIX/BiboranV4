package hack.rawfish2d.client.ModRender;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;

public class PlayerInfo extends Module {
	public static Module instance = null;
	//public static float scale = 0.020f;
	public DoubleValue scale;
	public DoubleValue renderDist;
	
	public static BoolValue mode1;
	public static BoolValue mode2;
	public static BoolValue mode3;

	public PlayerInfo() {
		super("PlayerInfo", 0, ModuleType.RENDER);
		setDescription("Показывает здоровье брони других игроков, и то что они держат в руке");
		instance = this;
		mode1 = new BoolValue(true);
		mode2 = new BoolValue(false);
		mode3 = new BoolValue(false);
		elements.add(new RadioBox(this, "Style #1", mode1, 0, 0));
		elements.add(new RadioBox(this, "Style #2", mode2, 0, 10));
		elements.add(new RadioBox(this, "Style #3", mode3, 0, 20));
		
		scale = new DoubleValue(0.020D, 0.01D, 0.04D);
		renderDist = new DoubleValue(20, 0, 255);
		elements.add(new NewSlider(this, "Scale", scale, 0, 30, false));
		elements.add(new NewSlider(this, "Distance", renderDist, 0, 50, false));
	}	
	
	@Override
	public void onRenderHand() {
		for (int a = 0; a < mc.theWorld.playerEntities.size(); ++a) {
			Object obj = mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			EntityPlayer ent = (EntityPlayer)obj;

			if( !MiscUtils.isValidRenderTarget(ent, renderDist.getValue(), false) )
				continue;
			
			if(mode1.getValue()) {
				GL11.glPushMatrix();
				renderPlayerInventory(ent);
				GL11.glPopMatrix();
			}
			if(mode2.getValue()) {
				GL11.glPushMatrix();
				renderPlayerInventory2(ent);
				GL11.glPopMatrix();
			}
			if(mode3.getValue()) {
				GL11.glPushMatrix();
				renderPlayerInventory3(ent);
				GL11.glPopMatrix();
			}
		}
	}
	
	public void renderPlayerInventory(EntityPlayer ent) {
		FontRenderer fontRenderer = mc.fontRenderer;
		int slot = 3;
		int oldx = -25;
		int oldy = -20;
		
		ItemStack curStack = ent.inventory.getCurrentItem();
		if(curStack == null)
			oldx = -35;
		
		int x = oldx;
		int y = oldy;
		
		//=====================================
		//final float scale = 0.02666667f;
		//float scale = 0.026f;
		float translateDist = 3.0f;
		float scaleDist = 6.5f;
		
		RenderManager renderManager = RenderManager.instance;
		double camX = ent.posX - RenderManager.instance.viewerPosX;
		double camY = ent.posY - RenderManager.instance.viewerPosY;
		double camZ = ent.posZ - RenderManager.instance.viewerPosZ;
		double distance = mc.thePlayer.getDistanceToEntity(ent);
		
		/*
		if (distance > translateDist)
			GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.299999952316284 + distance / 100.0 - 0.2), (float)camZ);
		else
			GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.3f, (float)camZ);
		*/
		GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.4f, (float)camZ);
		//GL11.glTranslatef((float)camX, (float)(camY), (float)camZ);
		
		GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		
		float sc = (float) scale.getValue();
		
		if (distance > scaleDist) {
			GL11.glScaled((double)(-sc) * distance / scaleDist, (double)(-sc) * distance / scaleDist, (double)sc * distance / scaleDist);
		} else {
			GL11.glScaled(-sc, -sc, sc);
		}
		/*
		if (distance > scaleDist) {
			GL11.glScaled((double)(-scale) * distance / scaleDist, (double)(-scale) * distance / scaleDist, (double)scale * distance / scaleDist);
		} else {
			GL11.glScaled(-scale, -scale, scale);
		}
		*/
		//=====================================
		
		RenderHelper.enableGUIStandardItemLighting();
		
		while (slot >= 0) {
			ItemStack stack = ent.inventory.armorItemInSlot(slot);
			if (stack != null) {
				RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, stack, x, y);
				RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				x += 18;
			}
			--slot;
		}
		
		if(curStack != null) {
			RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, oldx - 18, y);
			MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, curStack, oldx - 18, y);
			RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, oldx - 18, y);
		}
		RenderHelper.disableStandardItemLighting();
	}
	
	public void renderPlayerInventory2(EntityPlayer ent) {
		FontRenderer fontRenderer = mc.fontRenderer;
		int slot = 3;
		int oldx = 20; //20
		int oldy = 30; //30
		
		ItemStack curStack = ent.inventory.getCurrentItem();
		
		int x = oldx;
		int y = oldy;
		
		//=====================================
		//final float scale = 0.02666667f;
		//float scale = 0.026f;
		float translateDist = 3.0f;
		float scaleDist = 6.5f;
		
		RenderManager renderManager = RenderManager.instance;
		double camX = ent.posX - RenderManager.instance.viewerPosX;
		double camY = ent.posY - RenderManager.instance.viewerPosY;
		double camZ = ent.posZ - RenderManager.instance.viewerPosZ;
		double distance = mc.thePlayer.getDistanceToEntity(ent);
		
		if(distance > 24)
			return;
		
		/*
		if (distance > translateDist)
			GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.3 + distance / 100.f - 0.2f), (float)camZ);
		else
			GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.3f, (float)camZ);
		*/
		GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.4f, (float)camZ);
		//GL11.glTranslatef((float)camX, (float)(camY), (float)camZ);
		
		GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		
		float sc = (float) scale.getValue();
		
		if (distance > scaleDist) {
			GL11.glScaled((double)(-sc) * distance / scaleDist, (double)(-sc) * distance / scaleDist, (double)sc * distance / scaleDist);
		} else {
			GL11.glScaled(-sc, -sc, sc);
		}
		/*
		if (distance > scaleDist) {
			GL11.glScaled((double)(-scale) * distance / scaleDist, (double)(-scale) * distance / scaleDist, (double)scale * distance / scaleDist);
		} else {
			GL11.glScaled(-scale, -scale, scale);
		}
		*/
		//=====================================
		
		RenderHelper.enableGUIStandardItemLighting();
		
		while (slot >= 0) {
			ItemStack stack = ent.inventory.armorItemInSlot(slot);
			if (stack != null) {
				RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, stack, x, y);
				RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				
				MiscUtils.renderText(stack, x + 18, y + 6, 0xFFFFFFFF);
				
				y += 18;
			}
			--slot;
		}
		
		if(curStack != null) {
			RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			MiscUtils.renderText(curStack, x + 18, oldy - 12, 0xFFFFFFFF);
		}
		RenderHelper.disableStandardItemLighting();
	}
	
	public void renderPlayerInventory3(EntityPlayer ent) {
		FontRenderer fontRenderer = mc.fontRenderer;
		int slot = 3;
		int oldx = 0; //20
		int oldy = 0; //30
		
		ItemStack curStack = ent.inventory.getCurrentItem();
		
		int x = oldx;
		int y = oldy;
		
		//=====================================
		//final float scale = 0.02666667f;
		//float scale = 0.026f;
		float translateDist = 6.5f;
		float scaleDist = 6.5f;
		
		RenderManager renderManager = RenderManager.instance;
		double camX = ent.posX - RenderManager.instance.viewerPosX;
		double camY = ent.posY - RenderManager.instance.viewerPosY;
		double camZ = ent.posZ - RenderManager.instance.viewerPosZ;
		double distance = mc.thePlayer.getDistanceToEntity(ent);
		
		if(distance > 24)
			return;
		/*
		if (distance > translateDist)
			GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.4), (float)camZ);
		else
			GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.4), (float)camZ);
		*/
		//GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.4f, (float)camZ);
		//GL11.glTranslatef((float)camX, (float)(camY), (float)camZ);
		GL11.glTranslatef((float)camX, (float)camY - 0.5f, (float)camZ);
		
		GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		
		float sc = (float) scale.getValue();
		
		if (distance > scaleDist) {
			GL11.glScaled((double)(-sc) * distance / scaleDist, (double)(-sc) * distance / scaleDist, (double)sc * distance / scaleDist);
		} else {
			GL11.glScaled(-sc, -sc, sc);
		}
		/*
		if (distance > scaleDist) {
			GL11.glScaled((double)(-scale) * distance / scaleDist, (double)(-scale) * distance / scaleDist, (double)scale * distance / scaleDist);
		} else {
			GL11.glScaled(-scale, -scale, scale);
		}
		*/
		//=====================================
		
		RenderHelper.enableGUIStandardItemLighting();
		
		while (slot >= 0) {
			ItemStack stack = ent.inventory.armorItemInSlot(slot);
			if (stack != null) {
				RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, stack, x, y);
				RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
				
				MiscUtils.renderText(stack, x + 18, y + 6, 0xFFFFFFFF);
				
				y += 18;
			}
			--slot;
		}
		
		if(curStack != null) {
			RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			MiscUtils.renderEffect(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, x, oldy - 18);
			MiscUtils.renderText(curStack, x + 18, oldy - 12, 0xFFFFFFFF);
		}
		RenderHelper.disableStandardItemLighting();
	}
}
