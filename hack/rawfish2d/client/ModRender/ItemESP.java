package hack.rawfish2d.client.ModRender;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

public class ItemESP extends Module{

	public ItemESP() {
		super("ItemESP", 0, ModuleType.RENDER);
		setDescription("Можно видеть предметы через стены");
	}
	
	@Override
	public void onRenderHand() {
		for (Object obj : mc.theWorld.loadedEntityList) {
			
			if (!(obj instanceof EntityItem))
				continue;
			
			EntityItem ent = (EntityItem)obj;
			ItemStack item = ent.getEntityItem();
			
			if (item.getItem() == null)
				continue;
			
			double x;
			double y;
			double z;
			
			float ticks = mc.timer.renderPartialTicks;
			float var11 = MathHelper.sin(((float)ent.age + ticks) / 10.0F + ent.hoverStart) * 0.1F + 0.1F;
			float offsetY;
			
			//x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)mc.timer.renderPartialTicks;
			//y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)mc.timer.renderPartialTicks;
			//z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
			
			x = ent.lastTickPosX;
			y = ent.lastTickPosY;
			z = ent.lastTickPosZ;
			
			
			if (item.getItemSpriteNumber() == 0 && Block.blocksList[item.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[item.itemID].getRenderType()))
				offsetY = 0.25f;
			else
				offsetY = 0.1f;
			
			drawItemESP(x - RenderManager.renderPosX, y - RenderManager.renderPosY + var11 - offsetY, z - RenderManager.renderPosZ, ent, (double)ent.height - 0.1, (double)ent.width - 0.12);
		}
	}
	
	public void drawItemESP(double d2, double d1, double d22, EntityItem ep2, double e2, double f2) {
		GL11.glPushMatrix();
		//GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.15f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(1.8f);
		
		//R3DUtils.drawBoundingBox(new AxisAlignedBB(d2 - f2, d1 + 0.1, d22 - f2, d2 + f2, d1 + e2 + 0.25, d22 + f2));
		GL11.glColor4f(1.0f, 0.25f, 0.0f, 1.0f);
		R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(d2 - f2, d1 + 0.1, d22 - f2, d2 + f2, d1 + e2 + 0.25, d22 + f2));
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}
