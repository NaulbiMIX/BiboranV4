package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.ModuleManager;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet255KickDisconnect;

public class AutoReconnect extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AutoReconnect", "gToggled", true);
	private static PBotVar delay = new PBotVar("AutoReconnect", "delay", 1000);
	private static PBotVar bigDelay = new PBotVar("AutoReconnect", "bigDelay", 20000);
	private static PBotVar tryBeforeWait = new PBotVar("AutoReconnect", "tryBeforeWait", 3);
	
	private TimeHelper timeDelay;
	private TimeHelper timeBigDelay;
	private int counter;
	private boolean wait;
	
	public AutoReconnect(PBotThread bot) {
		super(bot, "AutoReconnect");
		toggled = gToggled.get(false);
		timeDelay = new TimeHelper();
		timeBigDelay = new TimeHelper();
		counter = 0;
		wait = false;
	}
	
	@Override
	public void onEnable() {
		//counter = 0;
		wait = false;
		timeDelay.reset();
		timeBigDelay.reset();
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		if(packet instanceof Packet255KickDisconnect) {
			Packet255KickDisconnect p = (Packet255KickDisconnect)packet;
			/*
			if(counter >= tryBeforeWait.get(0)) {
				timeBigDelay.reset();
				wait = true;
			}
			
			if(wait && !timeBigDelay.hasReached(bigDelay.get(0))) {
				return;
			}
			else if(wait && timeBigDelay.hasReached(bigDelay.get(0))) {
				wait = false;
			}
			
			if(timeDelay.hasReached(delay.get(0))) {
				timeDelay.reset();
				bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§aRestarting: " + p.reason);
				reconnect();
				counter++;
			}
			*/
			/*
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§aRestarting: " + p.reason);
			reconnect();
			*/
			
			if(timeDelay.hasReached(delay.get(0))) {
				timeDelay.reset();
				bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§aRestarting: " + p.reason);
				reconnect();
			}
			
		}
	}
	
	public void reconnect() {
		//if( !bot.connection.isRunning()) {
		/*
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					bot.sendChatClient("§a§lBot §a" + bot.instance.name + " §aRestarting...");
					bot.disconnect();
					bot.resetVars();
					bot.connect(null);
				} catch (Exception e) {
					bot.sendChatClient("§cBot §a§l" + bot.instance.name + " §cRestarting failed!");
					bot.sendChatClient("§c§l" + e.getMessage());
					System.out.println(e.getStackTrace());
				}
			}
		}, "Reconnect");
		th.start();
		*/
		
		try {
			//bot.sendChatClient("§a§lBot §a" + bot.instance.name + " §aRestarting...");
			bot.disconnect();
			bot.resetVars();
			bot.connect(null);
		} catch (Exception e) {
			bot.sendChatClient("§cBot §a§l" + bot.instance.name + " §cRestarting failed!");
			bot.sendChatClient("§c§lError: " + e.getMessage());
			System.out.println(e.getStackTrace());
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		/*
		MiscUtils.sendChatClient("wait:" + wait);
		MiscUtils.sendChatClient("counter:" + counter);
		MiscUtils.sendChatClient("ready:" + bot.ready);
		MiscUtils.sendChatClient("inLobby:" + bot.inLobby);
		*/
		//reconnect();
	}
}
