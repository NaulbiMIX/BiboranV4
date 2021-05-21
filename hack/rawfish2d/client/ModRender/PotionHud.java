package hack.rawfish2d.client.ModRender;

import java.util.Collection;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.Texture;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StatCollector;

public class PotionHud extends Module {
	
	public PotionHud() {
		super("PotionHud", 0, ModuleType.RENDER);
		setDescription("Показывает какие на вас наложены эффекты");
	}
	
	@Override
	public void onRenderOverlay() {
		ScaledResolution sr = MiscUtils.getScaledResolution();
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		int y;
		int x;
		
		Collection effects = mc.thePlayer.getActivePotionEffects();
		if (!effects.isEmpty()) {
			y = 1;
			for (Object obj : effects) {
				if(obj instanceof PotionEffect) {
					PotionEffect effect = (PotionEffect)obj;
					Potion potion = Potion.potionTypes[effect.getPotionID()];
					String name = StatCollector.translateToLocal(potion.getName());
					String level = "";
					
					if (effect.getAmplifier() == 1) {
						level = " II";
					} else if (effect.getAmplifier() == 2) {
						level = " III";
					} else if (effect.getAmplifier() == 3) {
						level = " IV";
					} else if (effect.getAmplifier() == 4) {
						level = " V";
					} else if (effect.getAmplifier() == 5) {
						level = " VI";
					} else if (effect.getAmplifier() == 6) {
						level = " VII";
					} else if (effect.getAmplifier() == 7) {
						level = " VIII";
					} else if (effect.getAmplifier() == 8) {
						level = " IX";
					} else if (effect.getAmplifier() == 9) {
						level = " X";
					} else if(effect.getAmplifier() != 0){
						level = " " + effect.getAmplifier();
					}
					
					name += level + " :§f " + Potion.getDurationString(effect);
					x = width - mc.fontRenderer.getStringWidth(name) - 20;
					
					GL11.glPushMatrix();
					
					GL11.glEnable(GL11.GL_BLEND);
					//GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					
					mc.fontRenderer.drawStringWithShadow(name, x - 5, y - 15 + height, potion.getLiquidColor());
			        
					int textID = potion.getStatusIconIndex();
					mc.renderEngine.bindTexture("/gui/inventory.png");
					mc.ingameGUI.drawTexturedModalRect(width - 20, y + height - 23, 0 + textID % 8 * 18, 198 + textID / 8 * 18, 18, 18);
					
					//GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
			        GL11.glPopMatrix();
			        
					y -= 20;
				}
			}
		}
	}
}
