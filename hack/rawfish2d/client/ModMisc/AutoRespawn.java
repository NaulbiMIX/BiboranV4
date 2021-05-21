package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet8UpdateHealth;

public class AutoRespawn extends Module{
	
	public AutoRespawn() {
		super("AutoRespawn", 0, ModuleType.MISC);
		setDescription("Автоматическое возрождение");
	}
	
	@Override
	public void onEnable() {
		setName("AutoRespawn");
	}
	
	@Override
	public void onDisable() {
		setName("AutoRespawn");
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(packet instanceof Packet8UpdateHealth) {
			Packet8UpdateHealth p = (Packet8UpdateHealth)packet;
			if(p.healthMP <= 0) {
				mc.getNetHandler().addToSendQueue(new Packet205ClientCommand(1));
			}
		}
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
