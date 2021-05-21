package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModCombat.MultiAura;
import hack.rawfish2d.client.ModCombat.StormAura;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class AutoTool extends Module{

	public AutoTool() {
		super("AutoTool", 0, ModuleType.MISC);
		setDescription("Автоматически берёт в руку нужный инструмент");
	}

	@Override
	public void onEnable() {
		//setName("CS:GO AntiAim");
	}

	@Override
	public void onDisable() {
		//setName("AntiAim");
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void preMotionUpdate() {
		//if (KillAura.curTarget != null || MultiAura.curTarget != null || FastClick.curTarget != null || (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit instanceof Entity && this.mc.gameSettings.keyBindAttack.pressed)) {
		if (!KillAura.instance.isToggled() && !StormAura.instance.isToggled() && !MultiAura.instance.isToggled() && !FastClick.instance.isToggled() && (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit instanceof Entity && this.mc.gameSettings.keyBindAttack.pressed)) {
			this.Attack();
		}
		if (this.mc.objectMouseOver != null && this.mc.gameSettings.keyBindAttack.pressed) {
			final int blockX = this.mc.objectMouseOver.blockX;
			final int blockY = this.mc.objectMouseOver.blockY;
			final int blockZ = this.mc.objectMouseOver.blockZ;
			Block block = Block.blocksList[this.mc.theWorld.getBlockId(blockX, blockY, blockZ)];
			if (block instanceof Block) {
				this.Mine(blockX, blockY, blockZ, block);
			}
		}
	}
	
	private void Mine(final int par1, final int par2, final int par3, final Block block) {
		int currentItem = -1;
		float strVsBlock = 1.0f;
		for (int i = 0; i < 9; ++i) {
			try {
				ItemStack stackInSlot = this.mc.thePlayer.inventory.getStackInSlot(i);
				if (stackInSlot != null && stackInSlot.getStrVsBlock(block) > strVsBlock) {
					strVsBlock = stackInSlot.getStrVsBlock(block);
					currentItem = i;
				}
			}
			catch (Exception ex) {}
		}
		if (currentItem != -1 && this.mc.thePlayer.inventory.currentItem != currentItem) {
			this.mc.thePlayer.inventory.currentItem = currentItem;
		}
	}
	
	public void Attack() {
		int currentItem = -1;
		float n = 1.0f;
		for (int i = 0; i < 9; ++i) {
			try {
				if (this.mc.objectMouseOver != null) {
					final ItemStack stackInSlot = this.mc.thePlayer.inventory.getStackInSlot(i);
					if (stackInSlot != null && stackInSlot.getDamageVsEntity(this.mc.thePlayer) > n) {
						n = stackInSlot.getDamageVsEntity(this.mc.thePlayer);
						currentItem = i;
					}
				}
			}
			catch (Exception ex) {}
		}
		if (currentItem != -1 && this.mc.thePlayer.inventory.currentItem != currentItem) {
			this.mc.thePlayer.inventory.currentItem = currentItem;
		}
	}

	@Override
	public void postMotionUpdate() {
		
	}
}
