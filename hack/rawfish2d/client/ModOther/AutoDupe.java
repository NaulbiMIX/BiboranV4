package hack.rawfish2d.client.ModOther;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet7UseEntity;

public class AutoDupe extends Module {
	
	public AutoDupe() {
		super("AutoDupe", Keyboard.KEY_I, ModuleType.NONE);
		setDescription("[TEST]");
	}
	
	@Override
	public void onDisable() {
		fillInvent();
	}
	
	@Override
	public void onEnable() {
		fillInvent();
	}
	
	public void fillInvent() {
		ItemStack item = new ItemStack(403, 64, 0);
		item.stackSize = 64;
		String text = "§c§lВаррик §d§lКароль §6§lСасалок";
		item.setItemName(text);
		
		Enchantment[] enchList = Enchantment.enchantmentsList;
		for (int i = 0; i < enchList.length; ++i) {
			Enchantment enchantment = enchList[i];
			if (enchantment != null)
				item.addEnchantment(enchantment, enchantment.getMaxLevel());
		}
		
		for(int a = 0; a < 20; ++a)
			MiscUtils.LoreAddLine(item, text);
		
		mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
		
		for(int a = 9; a < 45; ++a) {
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(a, item));
		}
	}
}
