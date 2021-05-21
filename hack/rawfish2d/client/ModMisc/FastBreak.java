package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModMovement.Jesus;
import hack.rawfish2d.client.ModTest.ChokePackets;
import net.minecraft.src.EnumAction;
import net.minecraft.src.ItemAppleGold;
import net.minecraft.src.ItemBucketMilk;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.Timer;

public class FastBreak extends Module {
	public FastBreak() {
		super("FastBreak", 0, ModuleType.MISC);
		setDescription("Быстрее ломает блоки");
	}
	
	@Override
	public void postMotionUpdate() {
		mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 1, 1));
	}
	
	public void onDisable()
	{
		mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
	}
}
