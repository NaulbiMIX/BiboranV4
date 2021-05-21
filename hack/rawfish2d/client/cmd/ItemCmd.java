package hack.rawfish2d.client.cmd;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.cmd.base.CommandUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.StatCollector;

public class ItemCmd extends Command {
	public ItemCmd(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
	}

	@Override
	public void run(String msg, String []args) {
		if(args.length == 1 && args[0].equalsIgnoreCase("item")) {
			MiscUtils.sendChatClient(color + ".item help");
			return;
		}
		
		/*
		.item name <name>
		.item dupe
		.item stack
		.item dress
		.item enchant <enchant id> [level]
		.item eall
		.item egod
		.item eclear
		.item elist
		.item esuper <level>
		
		.item loreadd <str>
		.item loreedit <line> <str>
		.item loredel <line>
		.item loreclear
		
		.item damage <value> //itemdamageset
		
		.item longerpotion
		*/
		
		if(args[1].equalsIgnoreCase("help") && args.length == 2) {
			//MiscUtils.sendChatClient(color + "%help%");
			MiscUtils.sendChatClient("§e.item name <text> §7- изменить имя предмета");
			MiscUtils.sendChatClient("§e.item dupe §7- заполнить предметом весь инвентарь");
			MiscUtils.sendChatClient("§e.item stack §7- сделать из предмета стак (64 штуки)");
			MiscUtils.sendChatClient("§e.item dress §7- одеть предмет");
			MiscUtils.sendChatClient("§e.item enchant <enchant id> [level] §7- зачаровать предмет");
			MiscUtils.sendChatClient("§e.item elist §7- список зачарований");
			MiscUtils.sendChatClient("§e.item eall §7- зачаровать предмет на все подходящие ему зачарования");
			MiscUtils.sendChatClient("§e.item egod §7- зачаровать премет на всё");
			MiscUtils.sendChatClient("§e.item eclear §7- убрать зачарования");
			MiscUtils.sendChatClient("§e.item esuper <level> §7- зачаровать предмет на всё, на указанный уровень");
			MiscUtils.sendChatClient("§e.item loreadd <text> §7- добавить описание предмета");
			MiscUtils.sendChatClient("§e.item loreedit <line> <text> §7- редактировать строку описания предмета");
			MiscUtils.sendChatClient("§e.item loredel <line> §7- удалить строку описания предмета");
			MiscUtils.sendChatClient("§e.item loreclear §7- удалить описание предмета");
			MiscUtils.sendChatClient("§e.item damage <value> §7- изменить повреждённость предмета");
			MiscUtils.sendChatClient("§e.item longerpotion §7- зелье будет длится дольше");
			return;
		}

		if(args[1].equalsIgnoreCase("name") && args.length >= 3) {
			if(name(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("dupe") && args.length == 2) {
			if(dupe(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("stack") && args.length == 2) {
			if(stack(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("dress") && args.length == 2) {
			if(dress(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("enchant") && args.length >= 3) {
			if(enchant(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("eall") && args.length == 2) {
			if(eall(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("egod") && args.length == 2) {
			if(egod(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("eclear") && args.length == 2) {
			if(eclear(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("elist") && args.length == 2) {
			if(elist(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("esuper") && args.length == 3) {
			if(esuper(args))
				return;
		}
		
		else if(args[1].equalsIgnoreCase("loreadd") && args.length >= 3) {
			if(loreadd(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("loreedit") && args.length >= 4) {
			if(loreedit(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("loredel") && args.length == 3) {
			if(loredel(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("loreclear") && args.length == 2) {
			if(loreclear(args))
				return;
		}
		
		
		else if(args[1].equalsIgnoreCase("damage") && args.length == 3) {
			if(damage(args))
				return;
		}
		else if(args[1].equalsIgnoreCase("longerpotion") && (args.length == 3 || args.length == 2)) {
			if(longerpotion(args))
				return;
		}
		
		mc.thePlayer.sendChatToPlayer("§cInvalid syntax!");
	}
	
	private boolean name(String[] args) { 
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			String name = "";
			for(int a = 2; a < args.length; ++a) {
				args[a] = args[a].replace('&', '§');
				name += args[a];
				if(a != args.length - 1)
					name += " ";
			}
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			item.setItemName(name);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			MiscUtils.sendChatClient("§fItem renamed to " + name);
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}

	private boolean dupe(String args[]) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("§ePlayer must be in creative mode");
			return true;
		}
		
		if(mc.thePlayer.inventory.getCurrentItem() != null)
		{
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			item.stackSize = 64;
			for(int a = 0; a < 45; ++a) {
				if(a != 5 && a != 6 && a != 7 && a != 8) {
					mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(a, item));
				}
			}
			MiscUtils.sendChatClient("§eItem duped!");
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found!");
		}
		
		return true;
	}
	
	private boolean stack(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			item.stackSize = 64;
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));

			MiscUtils.sendChatClient("§aItem stacked!");
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}
	
	private boolean dress(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack item = mc.thePlayer.inventory.getCurrentItem();
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(5, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(6, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(7, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(8, item));

			MiscUtils.sendChatClient("§aItem dressed!");
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}

	private boolean enchant(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		int en_id = 0;
		int level = 0;
		Enchantment[] enList = Enchantment.enchantmentsList;
		int len = Enchantment.enchantmentsList.length;

		if(args.length == 3) {
			try {
				en_id = Integer.parseInt(args[2]);
				if(en_id < 0 || en_id >= len) {
					MiscUtils.sendChatClient("§cInvalid enchant id");
					return true;
				}

				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy(); //getCurrentItem getCurrentEquippedItem

					Enchantment en = enList[en_id];
					level = en.getMaxLevel();
					item.addEnchantment(en, level);

					mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
					MiscUtils.sendChatClient("§eEnchanted your: §c" + item.getDisplayName());
					return true;
				}
				else {
					MiscUtils.sendChatClient("§cNo item in hand found");
					return true;
				}
			}
			catch(Exception ex) {
				MiscUtils.sendChatClient("§cEnchant error 1");
				return true;
			}
		}
		else if(args.length == 4) {
			try {
				en_id = Integer.parseInt(args[2]);
				level = Integer.parseInt(args[3]);
			}
			catch(Exception ex) {
				MiscUtils.sendChatClient("§cEnchant error 2");
				return true;
			}

			try {
				if(en_id < 0 || en_id >= len) {
					MiscUtils.sendChatClient("§cInvalid enchant id");
					return true;
				}

				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy(); //getCurrentItem getCurrentEquippedItem

					Enchantment en = enList[en_id];
					if(en != null) {
						item.addEnchantment(en, level);

						mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
						MiscUtils.sendChatClient("§eEnchanted your: §c" + item.getDisplayName());
						return true;
					}
					else {
						MiscUtils.sendChatClient("§cEnchant error 3");
						return true;
					}
				}
				else {
					MiscUtils.sendChatClient("§cNo item in hand found");
					return true;
				}
			}
			catch(Exception ex) {
				MiscUtils.sendChatClient("§cEnchant error 4");
				return true;
			}
		}
		
		return false;
	}
	
	private boolean eall(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy(); //getCurrentItem getCurrentEquippedItem

			//clear enchant

			NBTTagList list = item.getEnchantmentTagList();
			if(list != null) {
				int count = list.tagCount();
				if(count > 0) {
					for(int a = 0; a < count; ++a) {
						list.removeTag(0);
					}
				}
			}
			//item.setTagInfo(par1Str, par2NBTBase);

			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			//clear enchant

			Enchantment[] enchantmentsList;

			for (int i = 0; i < Enchantment.enchantmentsList.length; ++i) {
				Enchantment en = Enchantment.enchantmentsList[i];

				if (en != null) {
					if(		(item.getItem().itemID == Item.swordDiamond.itemID ||
							item.getItem().itemID == Item.swordGold.itemID ||
							item.getItem().itemID == Item.swordIron.itemID ||
							item.getItem().itemID == Item.swordStone.itemID ||
							item.getItem().itemID == Item.swordWood.itemID) &&
							(en.effectId == 16 ||
							en.effectId == 17 ||
							en.effectId == 18 ||
							en.effectId == 19 ||
							en.effectId == 20 ||
							en.effectId == 34 ||
							en.effectId == 21)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if(item.getItem().itemID == Item.bow.itemID &&
							(en.effectId == 48 ||
							en.effectId == 49 ||
							en.effectId == 50 ||
							en.effectId == 34 ||
							en.effectId == 51)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.pickaxeDiamond.itemID ||
							item.getItem().itemID == Item.pickaxeGold.itemID ||
							item.getItem().itemID == Item.pickaxeIron.itemID ||
							item.getItem().itemID == Item.pickaxeStone.itemID ||
							item.getItem().itemID == Item.pickaxeWood.itemID) &&
							(en.effectId == 32 ||
							en.effectId == 33 ||
							en.effectId == 34 ||
							en.effectId == 35)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.shovelDiamond.itemID ||
							item.getItem().itemID == Item.shovelGold.itemID ||
							item.getItem().itemID == Item.shovelIron.itemID ||
							item.getItem().itemID == Item.shovelStone.itemID ||
							item.getItem().itemID == Item.shovelWood.itemID) &&
							(en.effectId == 32 ||
							en.effectId == 33 ||
							en.effectId == 34 ||
							en.effectId == 35)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.hoeDiamond.itemID ||
							item.getItem().itemID == Item.hoeGold.itemID ||
							item.getItem().itemID == Item.hoeIron.itemID ||
							item.getItem().itemID == Item.hoeStone.itemID ||
							item.getItem().itemID == Item.hoeWood.itemID) &&
							en.effectId == 34) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.axeDiamond.itemID ||
							item.getItem().itemID == Item.axeGold.itemID ||
							item.getItem().itemID == Item.axeIron.itemID ||
							item.getItem().itemID == Item.axeStone.itemID ||
							item.getItem().itemID == Item.axeWood.itemID) &&
							(en.effectId == 32 ||
							en.effectId == 33 ||
							en.effectId == 34 ||
							en.effectId == 35)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if(item.getItem().itemID == Item.shears.itemID &&
							(en.effectId == 32 ||
							en.effectId == 34)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.helmetChain.itemID ||
							item.getItem().itemID == Item.helmetDiamond.itemID ||
							item.getItem().itemID == Item.helmetGold.itemID ||
							item.getItem().itemID == Item.helmetIron.itemID ||
							item.getItem().itemID == Item.helmetLeather.itemID) &&
							(en.effectId == 0 ||
							en.effectId == 1 ||
							//en.effectId == 2 ||
							en.effectId == 3 ||
							en.effectId == 4 ||
							en.effectId == 5 ||
							en.effectId == 6 ||
							//en.effectId == 7 ||
							en.effectId == 34)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.plateChain.itemID ||
							item.getItem().itemID == Item.plateDiamond.itemID ||
							item.getItem().itemID == Item.plateGold.itemID ||
							item.getItem().itemID == Item.plateIron.itemID ||
							item.getItem().itemID == Item.plateLeather.itemID) &&
							(en.effectId == 0 ||
							en.effectId == 1 ||
							//en.effectId == 2 ||
							en.effectId == 3 ||
							en.effectId == 4 ||
							//en.effectId == 5 ||
							//en.effectId == 6 ||
							en.effectId == 34)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.legsChain.itemID ||
							item.getItem().itemID == Item.legsDiamond.itemID ||
							item.getItem().itemID == Item.legsGold.itemID ||
							item.getItem().itemID == Item.legsIron.itemID ||
							item.getItem().itemID == Item.legsLeather.itemID) &&
							(en.effectId == 0 ||
							en.effectId == 1 ||
							//en.effectId == 2 ||
							en.effectId == 3 ||
							en.effectId == 4 ||
							//en.effectId == 5 ||
							//en.effectId == 6 ||
							en.effectId == 34)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					else if((item.getItem().itemID == Item.bootsChain.itemID ||
							item.getItem().itemID == Item.bootsDiamond.itemID ||
							item.getItem().itemID == Item.bootsGold.itemID ||
							item.getItem().itemID == Item.bootsIron.itemID ||
							item.getItem().itemID == Item.bootsLeather.itemID) &&
							(en.effectId == 0 ||
							en.effectId == 1 ||
							en.effectId == 2 ||
							en.effectId == 3 ||
							en.effectId == 4 ||
							//en.effectId == 5 ||
							//en.effectId == 6 ||
							en.effectId == 34)) {
						item.addEnchantment(en, en.getMaxLevel());
					}

					//item.addEnchantment(en, en.getMaxLevel());
				}
			}
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			MiscUtils.sendChatClient("§eEnchanted your: §c" + item.getDisplayName());
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}

	private boolean egod(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy(); //getCurrentItem getCurrentEquippedItem

			//clear enchant
			NBTTagList list = item.getEnchantmentTagList();
			if(list != null) {
				int count = list.tagCount();
				if(count > 0) {
					for(int a = 0; a < count; ++a) {
						list.removeTag(0);
					}
				}
			}
			//item.setTagInfo(par1Str, par2NBTBase);

			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			//clear enchant

			Enchantment[] enchantmentsList;

			for (int i = 0; i < Enchantment.enchantmentsList.length; ++i) {
				Enchantment en = Enchantment.enchantmentsList[i];

				if (en != null) {
					if(en.effectId != 7)
						item.addEnchantment(en, en.getMaxLevel());
				}
			}
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			MiscUtils.sendChatClient("§eEnchanted your: §c" + item.getDisplayName());
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}
	
	private boolean eclear(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy(); //getCurrentItem getCurrentEquippedItem

			NBTTagList list = item.getEnchantmentTagList();
			if(list != null) {
				int count = list.tagCount();
				if(count > 0) {
					for(int a = 0; a < count; ++a) {
						list.removeTag(0);
					}
				}
			}
			//item.setTagInfo(par1Str, par2NBTBase);

			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			return true;
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		return true;
	}
	
	private boolean elist(String[] args) {
		Enchantment[] enList = Enchantment.enchantmentsList;
		int len = Enchantment.enchantmentsList.length;

		MiscUtils.sendChatClient("§aEnchant list:");
		for (int i = 0; i < len; ++i) {
			Enchantment en = enList[i];
			if (en != null) {
				MiscUtils.sendChatClient("§eID " + i + " §a- " + StatCollector.translateToLocal(en.getName()));
			}
		}
		return true;
	}
	
	private boolean esuper(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null)
		{
			int level = 1;
			try {
				level = Integer.parseInt(args[2]);
			}
			catch(NumberFormatException ex) {
				ex.printStackTrace();
				MiscUtils.sendChatClient("§cSuper enchant error");
				return true;
			}

			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			
			//clear enchant
			NBTTagList list = item.getEnchantmentTagList();
			if(list != null) {
				int count = list.tagCount();
				if(count > 0) {
					for(int a = 0; a < count; ++a) {
						list.removeTag(0);
					}
				}
			}
			//item.setTagInfo(par1Str, par2NBTBase);

			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			//clear enchant
			
			Enchantment[] enchantmentsList;

			for (int length = (enchantmentsList = Enchantment.enchantmentsList).length, i = 0; i < length; ++i) {
				Enchantment enchantment = enchantmentsList[i];

				if (enchantment != null) {
					item.addEnchantment(enchantment, level);
				}
			}
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			MiscUtils.sendChatClient("§eEnchanted your: §c" + item.getDisplayName());
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}
	
	private boolean loreadd(String args[]) {
		String text = "";
		for(int a = 2; a < args.length; ++a) {
			args[a] = args[a].replace('&', '§');
			text += args[a];
			if(a != args.length - 1)
				text += " ";
		}

		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem().copy();
			MiscUtils.LoreAddLine(itemStack, text);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, itemStack));
			MiscUtils.sendChatClient("§fItem lore added: " + text);
		}
		else
			MiscUtils.sendChatClient("§cNo item in hand found");
		
		return true;
	}
	
	private boolean loreedit(String args[]) {
		int index = 0;

		try {
			index = Integer.parseInt(args[2]);
		}catch(NumberFormatException ex) {
			MiscUtils.sendChatClient("§cОшибка. Неверный номер строки");
			ex.printStackTrace();
			return true;
		}

		String text = "";
		if(args.length >= 4) {
			for(int a = 3; a < args.length; ++a) {
				args[a] = args[a].replace('&', '§');
				text += args[a];
				if(a != args.length - 1)
					text += " ";
			}
		}

		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem().copy();
			MiscUtils.LoreSetLine(itemStack, index, text);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, itemStack));
			MiscUtils.sendChatClient("§fItem lore line '" + index + "' edited: " + text);
		}
		else
			MiscUtils.sendChatClient("§cNo item in hand found");
		
		return true;
	}
	
	private boolean loredel(String args[]) {
		int index = 0;

		try {
			index = Integer.parseInt(args[2]);
		}catch(NumberFormatException ex) {
			MiscUtils.sendChatClient("§cОшибка. Неверный номер строки");
			ex.printStackTrace();
			return true;
		}

		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem().copy();
			MiscUtils.LoreDelLine(itemStack, index);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, itemStack));
			MiscUtils.sendChatClient("§fItem lore line '" + index + "' removed");
		}
		else
			MiscUtils.sendChatClient("§cNo item in hand found");
		
		return true;
	}
	
	private boolean loreclear(String args[]) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem().copy();
			MiscUtils.LoreClear(itemStack);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, itemStack));
			MiscUtils.sendChatClient("§fItem lore cleared");
		}
		else
			MiscUtils.sendChatClient("§cNo item in hand found");
		
		return true;
	}
	
	private boolean longerpotion(String[] args) {
		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
			return true;
		}

		if(mc.thePlayer.inventory.getCurrentItem() != null)
		{
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			//item.itemDamage |= 64;
			//item.itemDamage |= 8; //8 16 128
			//item.itemDamage |= 16; //8 16 128
			//item.itemDamage |= 128; //8 16 128
			
			if(args.length == 3) {
				try {
					item.itemDamage = Integer.parseInt(args[2]); //16398
				}
				catch(Exception ex) {
					ex.printStackTrace();
					return false;
				}
			}
			else {
				item.itemDamage |= 64;
				/*
				int value = item.itemDamage;
				value = Client.toggleBitValue(value, 32);
				value = Client.toggleBitValue(value, 64);
				item.itemDamage = value;
				*/
			}
			
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
		}
		else {
			MiscUtils.sendChatClient("§cNo item in hand found");
		}
		
		return true;
	}
	
	private boolean damage(String[] args) {
		if(mc.thePlayer.inventory.getCurrentItem() != null)
		{
			ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
			MiscUtils.sendChatClient("§eItem damage :§e " + item.itemDamage);
		}
		else
			MiscUtils.sendChatClient("§cNo item in hand found");

		if (!mc.playerController.isInCreativeMode()) {
			MiscUtils.sendChatClient("Player must be in creative mode");
		}

		try {
			if(mc.thePlayer.inventory.getCurrentItem() != null)
			{
				int damage = Integer.parseInt(args[2]);
				ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
				item.itemDamage = damage;
				mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
				MiscUtils.sendChatClient("§eItem damage set to §e" + item.itemDamage);
			}
			else
				MiscUtils.sendChatClient("§cNo item in hand found");
		}
		catch(NumberFormatException ex) {
			ex.printStackTrace();
			MiscUtils.sendChatClient("§cError");
		}
		
		return true;
	}
}
