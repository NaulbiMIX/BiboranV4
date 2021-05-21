package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class FakeCreative extends Module{
	private Random random = new Random();

	public FakeCreative() {
		super("FakeCreative", 0, ModuleType.MISC);
		setDescription("Фейковый креатив");
	}

	@Override
	public void onEnable() {
		mc.playerController.setGameType(EnumGameType.CREATIVE);
	}

	@Override
	public void onDisable() {
		mc.playerController.setGameType(EnumGameType.SURVIVAL);
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void preMotionUpdate() {
		
	}

	@Override
	public void postMotionUpdate() {
		
	}
}
