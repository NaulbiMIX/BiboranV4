package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.BoolValue;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;

public class AutoBlock extends Module{
	public static Module instance = null;

	public AutoBlock() {
		super("AutoBlock", Keyboard.KEY_B, ModuleType.COMBAT);
		setDescription("Автоматически делает блок мечём");
		instance = this;
	}

	@Override
	public void preMotionUpdate() {
		if (mc.thePlayer.isBlocking())
			mc.thePlayer.sendQueue.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 65281));
		
		//if (mc.thePlayer.inventory.getCurrentItem() != null) {
		//	if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
		
		if(shouldBlock()) {		
			ItemSword var2 = (ItemSword)mc.thePlayer.inventory.getCurrentItem().getItem();
			var2.onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);
		}
	}

	@Override
	public void postMotionUpdate() {
		if (mc.thePlayer.isBlocking())
			mc.getNetHandler().addToSendQueue(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
	}

	private boolean shouldBlock() {
		if (mc.thePlayer.getCurrentEquippedItem() == null)
			return false;

		//BoolValue onKey = new BoolValue(true);
		
		//if(mc.gameSettings.keyBindAttack.pressed && onKey.getValue() || !onKey.getValue()) {
		if(mc.gameSettings.keyBindAttack.pressed) {
			if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
				if (!mc.ingameGUI.persistantChatGUI.getChatOpen()) {
					return true;
				}
			}
		}
		
		return false;
	}
}
