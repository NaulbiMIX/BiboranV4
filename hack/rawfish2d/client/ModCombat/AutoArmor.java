package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class AutoArmor extends Module{
	private boolean boots;
	private boolean pants;
	private boolean chest;
	private boolean hat;

	int[] bootsID = {313, 309, 305, 317, 301};
	int[] pantsID = {312, 308, 304, 316, 300};
	int[] chestID = {311, 307, 303, 315, 299};
	int[] hatsID = {310, 306, 302, 314, 298};
	int prevSlot = -1;

	public AutoArmor() {
		super("AutoArmor", 0, ModuleType.COMBAT);
		setDescription("Автоматически надевает броню");
	}

	@Override
	public void onUpdate() {
		ItemStack[] arr_armor = mc.thePlayer.inventory.armorInventory;

		for(int a = 0; a < arr_armor.length; a++) {
			if(arr_armor[0] == null) {
				boots = false;
			} else {
				boots = true;
			}

			if(arr_armor[1] == null) {
				pants = false;
			} else {
				pants = true;
			}

			if(arr_armor[2] == null) {
				chest = false;
			} else {
				chest = true;
			}

			if(arr_armor[3] == null) {
				hat = false;
			} else {
				hat = true;
			}
		}
		equipNewArmor();
	}

	public void setInvSlot(int slot){
		mc.thePlayer.inventory.currentItem = slot;
	}

	public static int getSlotByID(int id) {
		for(int a = 9; a < 36; ++a){
			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(a).getStack();
			if(is != null && is.getItem().itemID == id){
				return a;
			}
		}
		return -1;
	}

	public int getSlotOfHotbarItem(int id){
		for(int i=0; i<9; i++){
			ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
			if(is != null && is.getItem().itemID == id){
				return i;
			}
		}
		return -1;
	}

	public void click(int slot, int mode){
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, mode, mc.thePlayer);
	}

	public void sendItemUse(ItemStack itemStack){
		mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, itemStack);
	}

	private void equipNewArmor() {
		ItemStack[] arr_inv = mc.thePlayer.inventory.mainInventory;

		//equip boots
		if(!boots)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : bootsID) {
				int invSlot = getSlotByID(id);
				if(invSlot != -1) {
					inv = true;
					slot = invSlot;
					break;
				} else {
					int newSlot = getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = mc.thePlayer.inventory.currentItem;

				setInvSlot(slot);
				sendItemUse(mc.thePlayer.getCurrentEquippedItem());
			}
		}

		//equip pants
		if(!pants)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : pantsID) {
				int invSlot = getSlotByID(id);
				if(invSlot != -1) {
					inv = true;
					slot = invSlot;
					break;
				} else {
					int newSlot = getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = mc.thePlayer.inventory.currentItem;

				setInvSlot(slot);
				sendItemUse(mc.thePlayer.getCurrentEquippedItem());
			}
		}

		//equip chest
		if(!chest)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : chestID) {
				int invSlot = getSlotByID(id);
				if(invSlot != -1) {
					inv = true;
					slot = invSlot;
					break;
				} else {
					int newSlot = getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = mc.thePlayer.inventory.currentItem;

				setInvSlot(slot);
				sendItemUse(mc.thePlayer.getCurrentEquippedItem());
			}
		}

		//equip hat
		if(!hat)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : hatsID) {
				int invSlot = getSlotByID(id);
				if(invSlot != -1) {
					inv = true;
					slot = invSlot;
					break;
				} else {
					int newSlot = getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = mc.thePlayer.inventory.currentItem;

				setInvSlot(slot);
				sendItemUse(mc.thePlayer.getCurrentEquippedItem());
			}
		}
	}
}
