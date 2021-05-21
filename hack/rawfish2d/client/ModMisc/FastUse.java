package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModMovement.Jesus;
import hack.rawfish2d.client.ModTest.ChokePackets;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EnumAction;
import net.minecraft.src.ItemAppleGold;
import net.minecraft.src.ItemBucketMilk;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Timer;

public class FastUse extends Module {
	public static Module instance = null;
	private DoubleValue speed;
	private BoolValue newuse;
	private BoolValue olduse;
	private BoolValue superuse;
	
	public FastUse() {
		super("FastUse", 0, ModuleType.MISC);
		setDescription("Áûסענוו וסע/ןü¸ע");
		instance = this;
		
		speed = new DoubleValue(1.9, 1, 3);
		
		newuse = new BoolValue(true);
		olduse = new BoolValue(false);
		superuse = new BoolValue(false);
		
		elements.add(new RadioBox(this, "Old", olduse, 0, 0));
		elements.add(new RadioBox(this, "New", newuse, 0, 10));
		elements.add(new RadioBox(this, "Super", superuse, 0, 20));
		elements.add(new NewSlider(this, "Old Speed", speed, 0, 30, false));
	}
	
	@Override
	public void preMotionUpdate() {
		if(olduse.getValue()) {
			old();
			return;
		}
		if(newuse.getValue()){
			dd_b2();
			return;
		}
		if(superuse.getValue()) {
			super_fast();
			return;
		}
	}
	
	private void super_fast() {
		if
		(
			mc.thePlayer.getHealth() > 0
			&& mc.thePlayer.inventory.getCurrentItem() != null
			&& mc.gameSettings.keyBindUseItem.pressed)
		{
			for (int index = 0; index < 800; ++index) {
				mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(mc.thePlayer.inventory.currentItem));
				mc.getNetHandler().addToSendQueue(new Packet10Flying(false));
				mc.getNetHandler().addToSendQueue(new Packet16BlockItemSwitch(mc.thePlayer.inventory.currentItem));
				mc.getNetHandler().addToSendQueue(new Packet10Flying(false));
			}
		}
	}
	
	private void dd_b2() {
		if (mc.thePlayer.inventory.getCurrentItem() == null)
			return;
		
		if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketMilk ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAppleGold) {
			if(mc.thePlayer.getItemInUseDuration() > 15) {
				
				Thread th = new Thread(new Runnable() {
					public void run() {
						for(int a = 0; a < 20; ++a) {
							MiscUtils.sleep(4L);
							mc.getNetHandler().addToSendQueue(new Packet10Flying(mc.thePlayer.onGround));
						}
						MiscUtils.sleep(4L);
						mc.playerController.onStoppedUsingItem(mc.thePlayer);
					}
				}, "FastUse DDB2");
				th.start();
			}
		}
	}
	
	private void old() {
		if(mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0) {
			if (mc.timer.timerSpeed != 1.0F)
				mc.timer.timerSpeed = 1.0F;
			
			if(fastUseCheck() == false)
				return;
			
			if (mc.timer.timerSpeed != speed.getValue())
				mc.timer.timerSpeed = (float) speed.getValue();
			
		}
		else if(ChokePackets.choke == 0) { //KillAura.curTarget == null
			if(fastUseCheck() == false)
				return;
			
			mc.timer.timerSpeed = 1.0F;
			
			mc.thePlayer.inventory.getCurrentItem().onPlayerStoppedUsing(mc.theWorld, mc.thePlayer, mc.thePlayer.inventory.getCurrentItem().stackSize);
			
			if(!Jesus.isLiquidUnderPlayer()) {
				mc.thePlayer.sendQueue.addToSendQueue(new Packet10Flying(mc.thePlayer.onGround));
			}
		}
	}
	
	public static boolean fastUseCheck()
	{
		if (mc.thePlayer.inventory.getCurrentItem() == null)
			return false;
		
		if(mc.thePlayer.getItemInUseDuration() <= 0)
			return false;
		
		if (!mc.gameSettings.keyBindUseItem.pressed)
			return false;
		
		if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketMilk ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood ||
				mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAppleGold)
				return true;
		
		return true;
	}
}
