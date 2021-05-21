package hack.rawfish2d.client.ModRender;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

public class ArmorHud extends Module{

	public ArmorHud() {
		super("ArmorHud", 0, ModuleType.RENDER);
		setDescription("Показывает вашу броню и её здоровье");
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		renderArmorHud();
	}
	
	public static void renderArmorHud() {
		int yOffset = 5;
		int slot = 3;
		int xOffset = 5;
		int x = 0;
		int y = 0;
		
		if(mc.thePlayer.isInsideOfMaterial(Material.water) && !mc.thePlayer.capabilities.isCreativeMode)
			yOffset += 25;
		else if(mc.thePlayer.capabilities.isCreativeMode)
			yOffset += -25;
		else
			yOffset += 5;
		
		while (slot >= 0) {
			ItemStack stack = mc.thePlayer.inventory.armorItemInSlot(slot);
			if (stack != null) {
				ScaledResolution resolution = MiscUtils.getScaledResolution();
				
				int width = resolution.getScaledWidth();
				int height = resolution.getScaledHeight();
				
				int maxDamage = stack.getMaxDamage();
				int itemDamage = stack.getItemDamageForDisplay();
				int durability = maxDamage - itemDamage;
				int damagecolor = (int)Math.round(255.0 - (double)stack.getItemDamageForDisplay() * 255.0 / (double)stack.getMaxDamage());
				int fixedcolor = 255 - damagecolor << 16 | damagecolor << 8;
				
				GL11.glPushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				RenderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, width / 2 + 12 + xOffset, height - 60 - yOffset / 2);
				RenderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, width / 2 + 12 + xOffset, height - 57 - yOffset / 2);
				RenderHelper.disableStandardItemLighting();
				
				GL11.glPopMatrix();
				
				if(maxDamage - itemDamage <= 100 && maxDamage - itemDamage >= 10)
					x = 7;
				else if(maxDamage - itemDamage >= 100)
					x = 4;
				else
					x = 11;
				
				x += width + 26 + xOffset * 2;
				y = height * 2 - 84 - yOffset;
				
				int var8 = (int) ((int)(fixedcolor >> 16 & 255) / 255f);
				int var9 = (int) ((int)(fixedcolor >> 8 & 255) / 255f);
		        int var10 = (int) ((int)(fixedcolor & 255) / 255f);
		        GL11.glPushMatrix();
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_TEXTURE_2D);
		        
		        float scale = 1.3f;
		        String lang = mc.gameSettings.language;
				if(lang.equals("ru_RU") || lang.equals("uk_UA"))
					scale = 1f;
				
		        //float scale = 2f;
		        GL11.glTranslatef(0, 0, 0);
		        GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		        //GL11.glScalef(1.0f * scale, 1.0f * scale, 1.0f * scale);
		        x *= scale / 2;
		        y *= scale / 2;
		        mc.fontRenderer.drawStringWithShadow("" + durability, x, y, fixedcolor);
		        //mc.font.drawStringWithShadow("" + durability, x, y, fixedcolor);
		        GL11.glScalef(1.0f * scale, 1.0f * scale, 1.0f * scale);
		        //GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		        
		        //GL11.glScalef(0.5f, 0.5f, 0.5f);
		        //mc.fontRenderer.drawString("" + durability, x, y, fixedcolor);
		        //GL11.glScalef(2.0f, 2.0f, 2.0f);
		        //GL11.glDisable(GL11.GL_TEXTURE_2D);
		        GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glPopMatrix();
				
				xOffset += 18;
			}
			--slot;
		}
	}
}
