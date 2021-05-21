package hack.rawfish2d.client.pbots.modules;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAppleGold;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet15Place;

public class AutoPvP extends ModuleBase{
	private static PBotVar gToggled = new PBotVar("AutoPvP", "gToggled", false);
	private TimeHelper time;
	
	private boolean boots;
	private boolean pants;
	private boolean chest;
	private boolean hat;
	
	int[] swordsID = {276, 267, 272, 268, 283};
	int[] bootsID = {313, 309, 305, 317, 301};
	int[] pantsID = {312, 308, 304, 316, 300};
	int[] chestID = {311, 307, 303, 315, 299};
	int[] hatsID = {310, 306, 302, 314, 298};
	int prevSlot = -1;
	
	public AutoPvP(PBotThread bot) {
		super(bot, "AutoPvP");
		toggled = gToggled.get(false);
		time = new TimeHelper();
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		
		if(time.hasReached(400L)) {
			autoarmor();
			time.reset();
		}
		
		//autoapple();
	}
	
	private void autosword() {
		
	}
	
	private void autoapple() {
		try {
			if (!bot.isPotionActive(10)) {
				getApple();
				ItemStack item = bot.inventory[bot.curSlot + 36];
				if (isApple(item)) {
					bot.sendPacket(new Packet15Place(-1, -1, -1, 255, item, 0.0f, 0.0f, 0.0f));
				}
			} else if (bot.isPotionActive(10) && isApple(bot.inventory[bot.curSlot + 36])) {
				ItemStack item = bot.inventory[bot.curSlot + 36];
				bot.sendPacket(new Packet15Place(-1, -1, -1, 255, item, 0.0f, 0.0f, 0.0f));
				getBestWeapon();
			}
		}
		catch (Exception exception) {
			// empty catch block
		}
	}
	
	private boolean isApple(ItemStack item) {
		if(item == null)
			return false;
		
		if(item.getItem() instanceof ItemAppleGold)
			return true;
		
		return false;
	}
	
	private void getApple() {
		float damageModifier = 1.0f;
		int newItem = -1;
		for (int slot = 36; slot < 45; ++slot) {
			ItemStack stack = bot.inventory[slot];
			if (stack == null || !(stack.getItem() instanceof ItemAppleGold)) continue;
			ItemAppleGold is2 = (ItemAppleGold)stack.getItem();
			newItem = slot;
		}
		if (newItem > -1) {
			bot.changeSlot(newItem - 36);
		}
		if (bot.isPotionActive(10)) {
			ItemStack item = bot.inventory[bot.curSlot + 36];
			bot.sendPacket(new Packet15Place(-1, -1, -1, 255, item, 0.0f, 0.0f, 0.0f));
			this.getBestWeapon();
		}
	}

	private void getBestWeapon() {
		float var1 = 1.0f;
		int var2 = -1;
		for (int var3 = 0; var3 < 9; ++var3) {
			ItemSword var5;
			float var6;
			ItemStack var4 = bot.inventory[var3];
			
			if (var4 == null || !(var4.getItem() instanceof ItemSword) || !((var6 = (var5 = (ItemSword)var4.getItem()).func_82803_g()) > var1))
				continue;
			
			var2 = var3;
			var1 = var6;
		}
		if (var2 > -1) {
			bot.changeSlot(var2 - 36);
		}
	}
	
	private void autoarmor() {
		ItemStack[] arr_armor = new ItemStack[] {bot.inventory[8], bot.inventory[7], bot.inventory[6], bot.inventory[5]};

		/*
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
		*/
		
		if(bot.inventory[5] == null) {
			hat = false;
		} else {
			hat = true;
		}
		
		if(bot.inventory[6] == null) {
			chest = false;
		} else {
			chest = true;
		}
		
		if(bot.inventory[7] == null) {
			pants = false;
		} else {
			pants = true;
		}
		
		if(bot.inventory[8] == null) {
			boots = false;
		} else {
			boots = true;
		}
		
		bot.sendChatClient("hat:" + hat);
		bot.sendChatClient("chest:" + chest);
		bot.sendChatClient("pants:" + pants);
		bot.sendChatClient("boots:" + boots);
		
		equipNewArmor();
	}

	private void setInvSlot(int slot){
		//mc.thePlayer.inventory.currentItem = slot;
		slot -= 36;
		bot.sendChatClient("sendSlot:" + slot);
		bot.changeSlot(slot);
	}

	private int getSlotByID(int id) {
		for(int a = 9; a < 36; ++a){
			ItemStack is = bot.inventory[a];
			if(is != null && is.getItem().itemID == id){
				return a;
			}
		}
		return -1;
	}

	public int getSlotOfHotbarItem(int id){
		for(int a = 36; a < 45; ++a){
			ItemStack is = bot.inventory[a];
			if(is != null && is.getItem().itemID == id){
				return a;
			}
		}
		return -1;
	}

	public void click(int slot, int mode){
		ItemStack item = bot.inventory[slot];
		bot.sendChatClient("click slot:" + slot + " item:" + item);
		//bot.sendPacket(new Packet102WindowClick(0, slot, 0, mode, item, (short)1));
		bot.sendPacket(new Packet102WindowClick(bot.windowId, slot, 0, mode, item, bot.getTransactionID()));
		MiscUtils.sleep(10L);
	}

	public void sendItemUse(ItemStack itemStack){
		ItemStack item = bot.inventory[bot.curSlot];
		bot.sendChatClient("sendItemUse:" + itemStack + " slot:" + bot.curSlot);
		bot.sendPacket(new Packet15Place(-1, -1, -1, 255, item, 0.0F, 0.0F, 0.0F));
		MiscUtils.sleep(10L);
	}

	private void equipNewArmor() {
		ItemStack[] arr_inv = bot.inventory;

		//equip boots
		if(!boots)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : bootsID) {
				int newSlot = getSlotOfHotbarItem(id);
				if(newSlot != -1){
					hot = true;
					slot = newSlot;
					break;
				}
				else {
					int invSlot = getSlotByID(id);
					if(invSlot != -1) {
						inv = true;
						slot = invSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = bot.curSlot;
				
				setInvSlot(slot);
				sendItemUse(bot.inventory[bot.curSlot]);
			}
		}

		//equip pants
		if(!pants)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : pantsID) {
				int newSlot = getSlotOfHotbarItem(id);
				if(newSlot != -1){
					hot = true;
					slot = newSlot;
					break;
				}
				else {
					int invSlot = getSlotByID(id);
					if(invSlot != -1) {
						inv = true;
						slot = invSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = bot.curSlot;

				setInvSlot(slot);
				sendItemUse(bot.inventory[bot.curSlot]);
			}
		}

		//equip chest
		if(!chest)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : chestID) {
				int newSlot = getSlotOfHotbarItem(id);
				if(newSlot != -1){
					hot = true;
					slot = newSlot;
					break;
				}
				else {
					int invSlot = getSlotByID(id);
					if(invSlot != -1) {
						inv = true;
						slot = invSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = bot.curSlot;

				setInvSlot(slot);
				sendItemUse(bot.inventory[bot.curSlot]);
			}
		}

		//equip hat
		if(!hat)
		{
			boolean hot = false;
			boolean inv = false;

			int slot = -1;
			for(int id : hatsID) {
				int newSlot = getSlotOfHotbarItem(id);
				if(newSlot != -1){
					hot = true;
					slot = newSlot;
					break;
				}
				else {
					int invSlot = getSlotByID(id);
					if(invSlot != -1) {
						inv = true;
						slot = invSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv) {
				click(slot, 1);
			} else if(slot != -1 && hot) {
				prevSlot = bot.curSlot;

				setInvSlot(slot);
				sendItemUse(bot.inventory[bot.curSlot]);
			}
		}
		
	}
}
