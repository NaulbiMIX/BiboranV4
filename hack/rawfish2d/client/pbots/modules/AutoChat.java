package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet3Chat;

public class AutoChat extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AutoChat", "gToggled", false);
	
	public AutoChat(PBotThread bot) {
		super(bot, "AutoChat");
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
		
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			String message = p.message.toLowerCase();
			
			if (message.contains("І7¬ведите в чат цифры: Іc".toLowerCase())) {
				String str2 = message.substring(29, message.length());
				System.out.println("str2:" + str2);
				bot.send(str2);
	        }
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
	}
}
