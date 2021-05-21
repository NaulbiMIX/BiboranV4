package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class Spam extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Spam", "gToggled", false);
	private static PBotVar delay = new PBotVar("Spam", "delay", 3000);
	
	private TimeHelper timer = new TimeHelper();
	private int msgs_index;
	public static CopyOnWriteArrayList<String> msgs = new CopyOnWriteArrayList<String>();
	
	public Spam(PBotThread bot) {
		super(bot, "Spam");
		toggled = gToggled.get(false);
		msgs_index = 0;
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() { }
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(timer.hasReached(delay.get(0))) {
			if(msgs_index < msgs.size() - 1)
				msgs_index++;
			else
				msgs_index = 0;
			
			String str = msgs.get(msgs_index);
			bot.sendPacket(new Packet3Chat(str));
			
			timer.reset();
		}
	}
}
