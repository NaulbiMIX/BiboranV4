package hack.rawfish2d.client.ModMisc;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Potion;

public class AntiPotion extends Module {

	public AntiPotion() {
		super("AntiPotion", 0, ModuleType.MISC);
		setDescription("На вас не действуют плохие эффекты зелий");
	}
	
	@Override
	public void postMotionUpdate() {
		if (mc.thePlayer.isPotionActive(Potion.blindness))
			mc.thePlayer.removePotionEffect(Potion.blindness.id);
		
		//if (mc.thePlayer.isPotionActive(Potion.moveSlowdown))
		//	mc.thePlayer.removePotionEffect(Potion.moveSlowdown.id);
		
		if (mc.thePlayer.isPotionActive(Potion.confusion))
			mc.thePlayer.removePotionEffect(Potion.confusion.id);
		
		if (mc.thePlayer.isPotionActive(Potion.digSlowdown))
			mc.thePlayer.removePotionEffect(Potion.digSlowdown.id);
		
	}
}
