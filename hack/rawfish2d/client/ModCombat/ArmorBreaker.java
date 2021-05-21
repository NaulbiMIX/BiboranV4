package hack.rawfish2d.client.ModCombat;

import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemSpade;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;

public class ArmorBreaker extends Module{
	public ArmorBreaker() {
		super("ArmorBreaker", Keyboard.KEY_V, ModuleType.COMBAT);
		setDescription("Ломает броню врага быстрее");
	}

	//ArmorBreaker - TitanPvP
	@Override
	public void onUpdate() {
		if(mc.thePlayer.isCollidedVertically) {
			ItemStack current = mc.thePlayer.getCurrentEquippedItem();
			ItemStack toSwitch = mc.thePlayer.inventoryContainer.getSlot(27).getStack();
			if(current != null && toSwitch != null && (current.getItem() instanceof ItemSword || current.getItem() instanceof ItemAxe || current.getItem() instanceof ItemPickaxe || current.getItem() instanceof ItemSpade) && (toSwitch.getItem() instanceof ItemSword || toSwitch.getItem() instanceof ItemAxe || toSwitch.getItem() instanceof ItemPickaxe || toSwitch.getItem() instanceof ItemSpade)) {
				mc.playerController.windowClick(0, 27, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
			}
		}
	}
	//ArmorBreaker - TitanPvP
}
