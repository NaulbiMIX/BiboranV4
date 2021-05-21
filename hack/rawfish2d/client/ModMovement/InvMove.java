package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.GuiChat;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101CloseWindow;

public class InvMove extends Module {
	public InvMove() {
		super("InvMove", 0, ModuleType.MOVEMENT);
		setDescription("Вы можете двигатся когда у вас открыт инвентарь");
		//from DD 06
	}
	
	@Override
	public void postMotionUpdate() {
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
			KeyBinding[] array = new KeyBinding[] {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindJump };
			for (int i = 0; i < array.length; ++i) {
				KeyBinding.setKeyBindState(array[i].keyCode, Keyboard.isKeyDown(array[i].keyCode));
			}
		}
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		/*
		if (packet instanceof Packet101CloseWindow) {
			doNotSendNextPacket(true);
		}
		*/
	}
}
