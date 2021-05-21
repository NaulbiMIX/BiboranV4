package hack.rawfish2d.client.ModRender;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.StormAura;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.Vector3f;
import hack.rawfish2d.client.utils.Vector4f;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityBlaze;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

public class Tracers extends Module {
	public static float r = 1.0F;
	public static float g = 0.0F;
	public static float b = 1.0F;
	public static float alpha = 0.7f;
	public static float width = 1.0f;
	
	public Tracers() {
		super("Tracers", 0, ModuleType.RENDER);
		setDescription("Показывает линии к игрокам");
	}
	
	@Override
	public void onRender() {
		/*
		if(mc.gameSettings.showDebugInfo == true)
			return;
		*/
		/*
		for(Object theObject : mc.theWorld.loadedEntityList) {
			
			if(!(theObject instanceof EntityLiving && theObject instanceof EntityPlayer))
				continue;
			
			if(PlayerList.isPlayer((Entity)theObject) == false)
				continue;
			
			EntityLiving ent = (EntityLiving)theObject;
			
			if(ent instanceof EntityPlayer) {
				if(ent != mc.thePlayer)
					player(ent);
				continue;
			}
		}
		*/
		/*
		double x = -1700;
		double y = 64;
		double z = 1080;
		
		double xpos = x - RenderManager.instance.renderPosX; 
		double ypos = y - RenderManager.instance.renderPosY;
		double zpos = z - RenderManager.instance.renderPosZ;
		render(r, g, b, xpos, ypos, zpos, alpha, width);
		*/
		
		for (int a = 0; a < mc.theWorld.playerEntities.size(); ++a) {
			Object obj = mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			EntityPlayer ent = (EntityPlayer)obj;

			if( !MiscUtils.isValidRenderTarget(ent, 255, false) )
				continue;
			
			draw(ent);
		}
	}
	
	public static void draw(Entity entity) {
		if(entity == null)
			return;
		
		double xpos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		double ypos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		double zpos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		render(r, g, b, xpos, ypos + entity.getEyeHeight() - 0.2f, zpos, alpha, width);
	}
	
	public static void render(float r, float g, float b, double x, double y, double z, float alpha, float width) {
		R3DUtils.drawTracerLine(x, y, z, r, g, b, alpha, width);
	}
}
