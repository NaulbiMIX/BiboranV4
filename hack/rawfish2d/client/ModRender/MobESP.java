package hack.rawfish2d.client.ModRender;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

public class MobESP extends Module {
	public static float mob_r = 1F;
	public static float mob_g = 0.5F;
	public static float mob_b = 0.5F;
	public static float mob_alpha = 0.2F;
	
	public static float animal_r = 0.5F;
	public static float animal_g = 1F;
	public static float animal_b = 0.5F;
	public static float animal_alpha = 0.2F;
	
	public static float passive_r = 0.5F;
	public static float passive_g = 0.5F;
	public static float passive_b = 0.5F;
	public static float passive_alpha = 0.2F;
	
	public MobESP() {
		super("MobESP", 0, ModuleType.RENDER);
		setDescription("Можно видеть мобов через стены");
	}
	
	@Override
	public void onRender() {
		for(Object theObject : mc.theWorld.loadedEntityList) {
			if(!(theObject instanceof EntityLiving))
				continue;
			
			if(PlayerUtils.isPlayer((Entity)theObject) == true)
				continue;
			
			EntityLiving ent = (EntityLiving)theObject;
			
			if(ent instanceof EntityMob) {
				mob(ent);
				continue;
			}
			
			if(ent instanceof EntityAnimal) {
				animal(ent);
				continue;
			}
			
			passive(ent);
		}
	}
	
	public void mob(Entity entity) {
		double xpos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		double ypos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		double zpos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		render(mob_r, mob_g, mob_b, xpos, ypos, zpos, entity.width, entity.height, mob_alpha);
	}

	public void animal(Entity entity) {
		double xpos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		double ypos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		double zpos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		render(animal_r, animal_g, animal_b, xpos, ypos, zpos, entity.width, entity.height, animal_alpha);
	}
	
	public void passive(Entity entity) {
		double xpos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		double ypos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		double zpos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		render(passive_r, passive_g, passive_b, xpos, ypos, zpos, entity.width, entity.height, passive_alpha);
	}
	
	public void render(float r, float g, float b, double x, double y, double z, float w, float h, float alpha) {
		R3DUtils.drawEntityESP(x, y, z, w - (w / 5), h + 0.2D, r, g, b, alpha, 0F, 0F, 0F, 1F, 1F);
	}
}
