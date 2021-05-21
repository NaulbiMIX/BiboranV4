package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class AutoRegister extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AutoRegister", "gToggled", true);
	
	private static PBotVar regLimitStop = new PBotVar("AutoRegister", "regLimitStop", true); //delete bot if reg limit
	private static PBotVar regMsg = new PBotVar("AutoRegister", "regMsg", "для начала игры необходима регистрация".toLowerCase());
	private static PBotVar regLimit = new PBotVar("AutoRegister", "regLimit", "Вы использовали максимальное количество возможных регистраций на ваш аккаунт".toLowerCase());
	
	public AutoRegister(PBotThread bot) {
		super(bot, "AutoRegister");
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
			//String pls_reg = 
			//String limit_reg = 
			
			if(message.contains(regMsg.get(""))) {
				register();
			}
			if(message.contains(regLimit.get(""))) {
				//bot.sendChatClient("§a§lBot " + bot.instance.name + "§d лимит регистраций!");
				bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§d лимит регистраций!");
				if(regLimitStop.get(false)) {
					bot.delBot();
				}
			}
		}
	}
	
	private void register() {
		//if(bot.password == null) {
		String pass = PBotCmd.password;
		bot.sendPacket(new Packet3Chat("/register " + pass + " " + pass));
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
	}
}
