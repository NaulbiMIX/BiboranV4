package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemSpade;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.PlayerControllerMP;

public class AutoSoup extends Module{
	private final int soupID = 282;
	private final int bowlID = 281;
	public static double health = 13.0;
	private int count1;
	private int count2;
	public static double soups;

	public AutoSoup() {
		super("AutoSoup", 0, ModuleType.COMBAT);
		setDescription("Автоматически пьёт суп когда остаётся мало здоровья");
		soups = 0;
		count1 = 0;
		count2 = 0;
		//from ShadowDestiny
	}

	@Override
	public void postMotionUpdate() {
		++count1;
		++count2;
		if (count2 >= 10 || count2 >= 5 && isHealthLow()) {
			cleanHotbar();
		}
		if (count1 >= 10 || count1 >= 5 && isHealthLow()) {
			prepareHotbar();
		}
		if (isHealthLow()) {
			eatSoup();

			for(int slot = 9; slot <= 44; ++slot) {
				if(mc.thePlayer.inventoryContainer.getSlot(slot).getStack() != null) {
					if(mc.thePlayer.inventoryContainer.getSlot(slot).getStack().getItem() instanceof ItemSword) {
						//mc.playerController.windowClick(0, slot, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
						mc.thePlayer.inventory.currentItem = slot - 36;
					}
				}
			}

			/*
			ItemStack current = mc.thePlayer.getCurrentEquippedItem();
			ItemStack toSwitch = mc.thePlayer.inventoryContainer.getSlot(27).getStack();
			if(current != null && toSwitch != null && (current.getItem() instanceof ItemSword || current.getItem() instanceof ItemAxe || current.getItem() instanceof ItemPickaxe || current.getItem() instanceof ItemSpade) && (toSwitch.getItem() instanceof ItemSword || toSwitch.getItem() instanceof ItemAxe || toSwitch.getItem() instanceof ItemPickaxe || toSwitch.getItem() instanceof ItemSpade)) {
				mc.playerController.windowClick(0, 27, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
			}
			*/
		}

		updateSoupCount();
		setName("AutoSoup §a[§e" + soups + "§a]");
	}

	private void eatSoup() {
		/*
		boolean var1 = Morbid.getManager().getMod("autosword").isEnabled();
		if (!var1) {
			Morbid.getManager().getMod("autosword").setEnabled(false);
		}
		*/
		int slot = 44;
		while (slot >= 9) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item != null) {
				if (slot >= 36 && slot <= 44) {
					if (item.itemID == soupID) {
						mc.thePlayer.inventory.currentItem = slot - 36;
						mc.playerController.updateController();
						Packet15Place pakPlace = new Packet15Place(-1, -1, -1, -1, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f);
						mc.getNetHandler().addToSendQueue(pakPlace);
						break;
					}
				} else if (item.itemID == soupID) {
					if (!isHotbarFree()) {
						mc.playerController.windowClick(0, 44, 0, 0, mc.thePlayer);
						mc.playerController.windowClick(0, -999, 0, 0, mc.thePlayer);
					}
					mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
					break;
				}
			}
			--slot;
		}
		/*
		if (!var1) {
			Morbid.getManager().getMod("autosword").setEnabled(true);
		}
		*/
	}

	private void prepareHotbar() {
		int slot = 36;
		while (slot <= 44) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item == null) {
				int slot2 = 35;
				while (slot2 >= 9) {
					ItemStack item2 = mc.thePlayer.inventoryContainer.getSlot(slot2).getStack();
					if (item2 != null && item2.itemID == soupID) {
						mc.playerController.windowClick(0, slot2, 0, 1, mc.thePlayer);
						count2 = 0;
						return;
					}
					--slot2;
				}
			}
			++slot;
		}
	}

	private void cleanHotbar() {
		ItemStack item = mc.thePlayer.inventoryContainer.getSlot(9).getStack();
		int slot = 36;
		while (slot <= 44) {
			ItemStack item2 = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item2 != null && item2.itemID == bowlID) {
				if (item != null && (item.itemID != bowlID || item.stackSize >= 64)) {
					if (item.itemID == 282) {
						mc.playerController.windowClick(0, 9, 0, 0, mc.thePlayer);
						mc.playerController.windowClick(0, slot, 0, 0, mc.thePlayer);
						mc.playerController.windowClick(0, 9, 0, 0, mc.thePlayer);
						count1 = 0;
						return;
					}
					mc.playerController.windowClick(0, 9, 0, 0, mc.thePlayer);
					mc.playerController.windowClick(0, -999, 0, 0, mc.thePlayer);
				}
				mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
				count1 = 0;
				return;
			}
			++slot;
		}
	}

	private void updateSoupCount() {
		soups = 0.0;
		int slot = 44;
		while (slot >= 9) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item != null && item.itemID == soupID) {
				soups += 1.0;
			}
			--slot;
		}
	}

	private boolean isHealthLow() {
		if (mc.thePlayer.getHealth() <= health) {
			return true;
		}
		return false;
	}

	private boolean isHotbarFree() {
		int slot = 36;
		while (slot <= 44) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item == null || item.itemID == soupID) {
				return true;
			}
			++slot;
		}
		return false;
	}
}
