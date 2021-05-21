package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet8UpdateHealth;

public class AutoWarp extends Module{
	public static Module instance;
	public static String warp;
	public TimeHelper timer;
	
	public AutoWarp() {
		super("AutoWarp", Keyboard.KEY_RBRACKET, ModuleType.MISC);
		setDescription("Фарм килов. Настройка .autowarp warp");
		warp = "#1";
		instance = this;
		timer = new TimeHelper();
		timer.reset();
	}
	
	@Override
	public void onEnable() {
		//
		
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.keyBindForward.pressed = false;
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(packet instanceof Packet8UpdateHealth) {
			Packet8UpdateHealth p = (Packet8UpdateHealth)packet;
			if(p.healthMP <= 0) {
				mc.getNetHandler().addToSendQueue(new Packet205ClientCommand(1));
				
				MiscUtils.sleep(4L);
				
				mc.getNetHandler().addToSendQueue(new Packet3Chat("/warp " + warp));

				MiscUtils.sleep(4L);
			}
		}
	}
	
	@Override
	public void onUpdate() {
		mc.gameSettings.keyBindForward.pressed = true;
		
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
}
