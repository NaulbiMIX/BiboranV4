package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class Lagometr extends Module{
	private TimeHelper time = new TimeHelper();
	
	public Lagometr() {
		super("Lagometr", 0, ModuleType.MISC);
		setDescription("Уведомляет вас когда сервер лагает");
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
	public void onRenderOverlay() {
		if(time.hasReached(1250L)) {
			String str = "Lag:" + (time.getCurrentMS() - time.getLastMS());
			int strw = Client.getInstance().mc.fontRenderer.getStringWidth(str);
			int x = MiscUtils.getScreenWidth() / 2 - (strw / 2);
			int y = 2;
			Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, 0xFFFF0000);
		}
	}
	
	@Override
	public void preMotionUpdate() {
		
	}

	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(packet.getPacketId() == 0) {
			time.reset();
			return;
		}
	}
}
