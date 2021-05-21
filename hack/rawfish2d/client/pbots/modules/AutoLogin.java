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

public class AutoLogin extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AutoLogin", "gToggled", true);
	
	private static PBotVar pls_login1 = new PBotVar("AutoLogin", "pls_login1", "Авторизуйтесь".toLowerCase());
	private static PBotVar pls_login2 = new PBotVar("AutoLogin", "pls_login2", "Для входа в игру введите команду:".toLowerCase());
	private static PBotVar pls_login3 = new PBotVar("AutoLogin", "pls_login3", "/login".toLowerCase());
	private static PBotVar wrong_pass = new PBotVar("AutoLogin", "wrong_pass", "Вы ввели неправильный пароль. Попробуйте ещё раз.".toLowerCase());
	private static PBotVar login_ok = new PBotVar("AutoLogin", "login_ok", "Вы зашли на свой аккаунт, удачной вам игры!".toLowerCase());
	
	private TimeHelper kicktime;
	
	public AutoLogin(PBotThread bot) {
		super(bot, "AutoLogin");
		toggled = gToggled.get(false);
		kicktime = new TimeHelper();
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
			//if(p.message.contains("§eАвторизуйтесь §7-") && p.message.contains("§b/login §8[§dпароль§8]") || p.message.contains("§e§eДля входа в игру введите команду:")) { //§eАвторизуйтесь §7- §b/login §8[§dпароль§8]
			
			String message = p.message.toLowerCase();
			
			if(message.contains(pls_login1.get("")) && message.contains(pls_login2.get("")) || message.contains(pls_login3.get(""))) { //§eАвторизуйтесь §7- §b/login §8[§dпароль§8]
				//if(!bot.loggined)
				login();
			}
			else if(message.contains(login_ok.get(""))) {
				bot.loggined = true;
			}
			else if(message.contains(wrong_pass.get("")) && !bot.loggined) {
				bot.sendChatClient("§a§lBot " + bot.instance.name + " неверный пароль!");
				bot.send("/l azino");
			}
		}
	}
	
	private void login() {
		//if(bot.password == null)
		//respawn
		bot.sendPacket(new Packet205ClientCommand(1));
		
		bot.sendPacket(new Packet3Chat("/login " + PBotCmd.password));
		//else
		//	bot.sendPacket(new Packet3Chat("/l " + bot.password));
		
		if(bot.onSurvivalServer) {
			//bot.sendPacket(new Packet16BlockItemSwitch(4));
			bot.changeSlot(4);
			bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, false));
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(!bot.inLobby) {
			kicktime.reset();
		}
		
		/*
		if(!bot.loggined && bot.inLobby && kicktime.hasReached(15000)) {
			bot.stopBot();
			PBotCmd.bots.remove(bot.instance);
		}
		*/
	}
}
