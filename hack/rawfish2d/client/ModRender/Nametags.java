package hack.rawfish2d.client.ModRender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.RenderManager;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;

public class Nametags extends Module{
	public static Module instance = null;
	//public static float scale = 0.026f;
	public DoubleValue scale;
	public BoolValue showDistance;
	public BoolValue showID;

	public Nametags() {
		super("Nametags", 0, ModuleType.RENDER);
		setDescription("Имена игроков видно лучше");
		instance = this;
		
		scale = new DoubleValue(0.026D, 0.01D, 0.04D);
		showDistance = new BoolValue(false);
		showID = new BoolValue(false);
		
		elements.add(new NewSlider(this, "Scale", scale, 0, 0, false));
		elements.add(new CheckBox(this, "Show Distance", showDistance, 0, 20));
		elements.add(new CheckBox(this, "Show EntityID", showID, 0, 30));
	}
	
	@Override
	public void onRenderHand() {
		List<Entity> Ents = new ArrayList<Entity>();
		
		for (Object obj : mc.theWorld.playerEntities) {
			Entity ent = (Entity)obj;
			
			if (ent == mc.thePlayer || !ent.isEntityAlive())
				continue;
			/*
			if(!PlayerUtils.isPlayer(ent))
				continue;
			*/
			Ents.add(ent);
		}
		
		Collections.sort(Ents, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Entity ent1 = (Entity)arg0;
				Entity ent2 = (Entity)arg1;
				
				double dist1 = mc.thePlayer.getDistanceSqToEntity(ent1);
				double dist2 = mc.thePlayer.getDistanceSqToEntity(ent2);
				
				if(dist1 > dist2) return -1;
				else if(dist1 < dist2) return 1;
				else return 0;
			}
		});
		
		for (Entity ent : Ents) {
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			mc.entityRenderer.disableLightmap(8.0);
			drawNames(ent);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
		}
	}
	
	private void drawNames(Entity ent) {
		String entName = ent.getTranslatedEntityName();
		
		FontRenderer fontRenderer = mc.fontRenderer;
		
		//scale = 0.026f;
		
		entName = PlayerUtils.makePrefix(entName);
		
		//=====================================
		//final float scale = 0.026f;
		final float translateDist = 3.0f;
		final float scaleDist = 6.5f;
		
		RenderManager renderManager = RenderManager.instance;
		final double camX = ent.posX - RenderManager.instance.viewerPosX;
		final double camY = ent.posY - RenderManager.instance.viewerPosY;
		final double camZ = ent.posZ - RenderManager.instance.viewerPosZ;
		final double distance = mc.thePlayer.getDistanceToEntity(ent);
		
		/*
		if (distance > translateDist)
			GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.299999952316284 + distance / 100.0 - 0.2), (float)camZ);
		else
			GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.3f, (float)camZ);
		*/
		
		//GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.4f, (float)camZ);
		GL11.glTranslatef((float)camX + 0.0f, (float)camY + ent.getEyeHeight() + 0.50f, (float)camZ);
		GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		
		float sc = (float) scale.getValue();
		
		if (distance > scaleDist) {
			GL11.glScaled((double)(-sc) * distance / scaleDist, (double)(-sc) * distance / scaleDist, (double)sc * distance / scaleDist);
		} else {
			GL11.glScaled(-sc, -sc, sc);
		}
		//=====================================
		if(showDistance.getValue()) {
			String entDist = String.format("%.3g", mc.thePlayer.getDistanceToEntity(ent));
			
			entName += " §2[" + entDist + "]";
		}
		
		if(showID.getValue()) {
			String entID = "" + ent.entityId;
			entName += " §2[" + entID + "]";
		}
		
		int strw = fontRenderer.getStringWidth(entName) / 2;
		Gui.drawRect(-strw -2, -3, strw + 2, 11, 0x80000000);
		
		int color;
		
		if(PlayerUtils.isFriend(ent))
			color = 0xFFFFCC12;
		else if(ent.isInvisible())
			color = 0xFFFF9900;
		else if(ent.isSneaking())
			color = 0xFF0489B1;
		else if(distance <= 64.0)
			color = 0xFFFFFFFF;
		else
			color = 0xFF045FB4;
		
		//ent.renderDistanceWeight = 10.0; //no render players
		
		fontRenderer.drawStringWithShadow(entName, -strw, 0, color);
	}
}
