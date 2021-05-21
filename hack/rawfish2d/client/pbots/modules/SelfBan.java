package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet3Chat;

public class SelfBan extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("SelfBan", "gToggled", false);
	
	private static PBotVar msg = new PBotVar("SelfBan", "msg", "ban".toLowerCase());
	
	public SelfBan(PBotThread bot) {
		super(bot, "SelfBan");
		toggled = gToggled.get(false);
	}
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
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
		
		if(bot.inLobby) {
			bot.sendChatClient("SEND SPAM");
			
			for(int a = 0; a < 6; ++a)
				bot.send(msg.get(""));
			
			MiscUtils.sleep(1000);
			
			for(int a = 0; a < 6; ++a)
				bot.send(msg.get(""));
		}
	}
}
