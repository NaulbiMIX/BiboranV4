package hack.rawfish2d.client.ModTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Enchantment;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagInt;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class RainbowHead extends Module{
	
	private List<Integer> idList;
	private List<Integer> armorList;
	private BoolValue HeadOnlyWool;
	private BoolValue HeadOnlyHelmet;
	private BoolValue HeadEveryting;
	private BoolValue coloredArmor;
	private BoolValue armor;
	private BoolValue differentArmor;
	private BoolValue hand;
	
	private ItemStack origHead;
	private ItemStack origChest;
	private ItemStack origPants;
	private ItemStack origBoots;
	private ItemStack origHand;
	
	public RainbowHead() {
		super("RandomHead", 0, ModuleType.TEST);
		setDescription("Меняет блоки на голове");
		
		idList = new ArrayList<Integer>();
		armorList = new ArrayList<Integer>();
		
		HeadOnlyWool = new BoolValue(true);
		HeadOnlyHelmet = new BoolValue(false);
		HeadEveryting = new BoolValue(false);
		coloredArmor = new BoolValue(false);
		armor = new BoolValue(true);
		differentArmor = new BoolValue(false);
		hand = new BoolValue(false);
		
		this.elements.add(new RadioBox(this, "Только шерсть на голове", HeadOnlyWool, 0, 0));
		this.elements.add(new RadioBox(this, "Только шапка на голове", HeadOnlyHelmet, 0, 10));
		this.elements.add(new RadioBox(this, "Всё что угодно на голове", HeadEveryting, 0, 20));
		this.elements.add(new CheckBox(this, "Цветная броня", coloredArmor, 0, 30));
		this.elements.add(new CheckBox(this, "Броня", armor, 0, 40));
		this.elements.add(new CheckBox(this, "Разная броня", differentArmor, 0, 50));
		this.elements.add(new CheckBox(this, "Рука", hand, 0, 60));
		
		armorList.add(298);
		armorList.add(302);
		armorList.add(306);
		armorList.add(310);
		armorList.add(314);
		
		idList.add(1);
		idList.add(2);
		idList.add(3);
		idList.add(4);
		idList.add(5);
		idList.add(6);
		idList.add(12);
		idList.add(13);
		idList.add(14);
		idList.add(15);
		idList.add(16);
		idList.add(17);
		idList.add(18);
		idList.add(19);
		idList.add(20);
		idList.add(21);
		idList.add(22);
		idList.add(24);
		idList.add(25);
		idList.add(26);
		idList.add(27);
		idList.add(28);
		idList.add(29);
		idList.add(30);
		idList.add(31);
		idList.add(32);
		idList.add(33);
		idList.add(34);
		idList.add(35);
		idList.add(37);
		idList.add(38);
		idList.add(39);
		idList.add(40);
		idList.add(41);
		idList.add(42);
		idList.add(44);
		idList.add(45);
		idList.add(47);
		idList.add(48);
		idList.add(49);
		idList.add(50);
		idList.add(51);
		idList.add(53);
		idList.add(54);
		idList.add(56);
		idList.add(57);
		idList.add(58);
		idList.add(61);
		idList.add(65); //cursed
		idList.add(66); //cursed
		idList.add(67);
		idList.add(69); //cursed
		idList.add(70);
		idList.add(72);
		idList.add(73);
		idList.add(76); //cursed
		idList.add(78);
		idList.add(79);
		idList.add(80);
		idList.add(81);
		idList.add(82);
		idList.add(84);
		idList.add(85);
		//idList.add(86); //pumpkin
		idList.add(87);
		idList.add(88);
		idList.add(89);
		idList.add(91);
		idList.add(96);
		idList.add(98);
		idList.add(99);
		idList.add(100);
		idList.add(101); //cursed
		idList.add(102); //cursed
		idList.add(103);
		idList.add(106); //cursed
		idList.add(107);
		idList.add(108);
		idList.add(109);
		idList.add(110);
		idList.add(111); //cursed
		idList.add(112);
		idList.add(113);
		idList.add(114);
		idList.add(116);
		idList.add(121);
		idList.add(123);
		idList.add(124);
		idList.add(126);
		idList.add(128);
		idList.add(129);
		idList.add(130);
		idList.add(131); //cursed
		idList.add(133);
		idList.add(134);
		idList.add(135);
		idList.add(136);
		idList.add(138);
		idList.add(139);
		idList.add(145);
		idList.add(146);
		idList.add(147);
		idList.add(148);
		idList.add(149); //cursed
		idList.add(150); //cursed
		idList.add(151);
		idList.add(152);
		idList.add(153);
		idList.add(155);
		idList.add(156);
		idList.add(157); //cursed
	}

	@Override
	public void onEnable() {
		origHead = mc.thePlayer.inventory.getStackInSlot(5);
		origChest = mc.thePlayer.inventory.getStackInSlot(6);
		origPants = mc.thePlayer.inventory.getStackInSlot(7);
		origBoots = mc.thePlayer.inventory.getStackInSlot(8);
		origHand = mc.thePlayer.inventory.getStackInSlot(36);
	}

	@Override
	public void onDisable() {
		
	}
	
	private ItemStack getRandomArmor() {
		ItemStack item = null;
		
		int id = 298;
		if(!coloredArmor.getValue()) {
			id = MiscUtils.random(0, armorList.size() - 1);
			id = armorList.get(id);
		}
		
		item = new ItemStack(id, 1, 0);
		Enchantment[] enList = Enchantment.enchantmentsList;
		Enchantment en = enList[51];
		int level = en.getMaxLevel();
		item.addEnchantment(en, level);
		
		if(coloredArmor.getValue()) {
			item = MiscUtils.makeItemColored(item);
		}
		
		return item;
	}
	
	private ItemStack getRandomHand() {
		ItemStack item = null;
		
		int id = 1;
		int data = 0;
		
		id = MiscUtils.random(0, idList.size() - 1);
		id = idList.get(id);
		data = MiscUtils.random(0, 15);
		item = new ItemStack(id, 1, data);
		
		return item;
	}
	
	private ItemStack getRandomHat() {
		ItemStack item = null;
		
		int id = 1;
		int data = 0;
		boolean shouldEnchant = false;
		
		if(HeadOnlyWool.getValue()) {
			id = 35;
			data = MiscUtils.random(0, 15);
		}
		
		if(HeadOnlyHelmet.getValue() && !coloredArmor.getValue()) {
			id = MiscUtils.random(0, armorList.size() - 1);
			id = armorList.get(id);
			shouldEnchant = true;
		}
		if(HeadOnlyHelmet.getValue() && coloredArmor.getValue()) {
			id = 298;
			shouldEnchant = true;
		}
		
		if(HeadEveryting.getValue()) {
			id = MiscUtils.random(0, idList.size() - 1);
			id = idList.get(id);
			
			data = MiscUtils.random(0, 15);
		}
		
		item = new ItemStack(id, 1, data);
		if(coloredArmor.getValue()) {
			item = MiscUtils.makeItemColored(item);
		}
		
		if(shouldEnchant) {
			Enchantment[] enList = Enchantment.enchantmentsList;
			Enchantment en = enList[51];
			int level = en.getMaxLevel();
			item.addEnchantment(en, level);
		}
		
		return item;
	}
	
	@Override
	public void onUpdate() {
		if (!mc.playerController.isInCreativeMode()) {
			return;
		}
		
		ItemStack hat = null;
		ItemStack chest = null;
		ItemStack pants = null;
		ItemStack boots = null;
		ItemStack itemhand = null;
		
		hat = this.getRandomHat();
		if(armor.getValue()) {
			if(differentArmor.getValue()) {
				chest = this.getRandomArmor();
				pants = this.getRandomArmor();
				boots = this.getRandomArmor();
			}
			else {
				chest = this.getRandomArmor();
				pants = chest;
				boots = chest;
			}
		}
		
		if(this.hand.getValue()) {
			itemhand = getRandomHand();
		}
		
		if(hat != null)
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(5, hat));
		
		if(chest != null)
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(6, chest));
		if(pants != null)
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(7, pants));
		if(boots != null)
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(8, boots));
		
		if(itemhand != null)
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(36, itemhand));
	}
	
	@Override
	public void preMotionUpdate() {
		
	}

	@Override
	public void postMotionUpdate() {
		
	}
}
