package hack.rawfish2d.client.pbots.utils;

import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.modules.*;

public class ModuleManager {
	
	public static void CreateModules(PBotThread bot) {
		for(ModuleBase mod : bot.modules) {
			if(mod.toggled)
				mod.toggle();
		}
		bot.modules.clear();
		
		bot.modules.add(new Anouncer(bot));
		bot.modules.add(new AntiCaptcha(bot));
		
		//bot.modules.add(new AutoPvP(bot));
		//bot.modules.add(new AutoPvP2(bot));
		
		bot.modules.add(new AutoChat(bot));
		bot.modules.add(new AutoJoin(bot));
		bot.modules.add(new AutoLogin(bot));
		bot.modules.add(new AutoReconnect(bot));
		bot.modules.add(new AutoRegister(bot));
		bot.modules.add(new Brute(bot));
		bot.modules.add(new BPM(bot));
		bot.modules.add(new BLB(bot));
		//bot.modules.add(new ShopDupe_A(bot));
		
		bot.modules.add(new Follow(bot));
		bot.modules.add(new Idot(bot));
		bot.modules.add(new KillAura(bot));
		bot.modules.add(new KillFarm(bot));
		//bot.modules.add(new MoneyBot(bot));
		bot.modules.add(new Repeater(bot));
		
		bot.modules.add(new ShopDupe_A2(bot));
		//bot.modules.add(new ShopDupe_B(bot));
		bot.modules.add(new ShopDupe_B2(bot));
		bot.modules.add(new Spam(bot));
		bot.modules.add(new Walk(bot));
		bot.modules.add(new SelfBan(bot));
	}
}
