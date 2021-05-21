package hack.rawfish2d.client.ModRender;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class NoFire extends Module{
	public static Module instance = null;
	
	public NoFire() {
		super("NoFire", 0, ModuleType.RENDER);
		setDescription("Уберает у вас эффект огня когда вы горите");
		instance = this;
	}
	/*
	@Override
	public void onRender() {
		nofire();
	}
	
	private void nofire() {
		if (!mc.thePlayer.capabilities.isCreativeMode && mc.thePlayer.isBurning()) {
			if (mc.thePlayer.getActivePotionEffect(Potion.fireResistance) != null) {
				mc.thePlayer.setFlag(0, false);
			}
		}
	}
	*/
}
