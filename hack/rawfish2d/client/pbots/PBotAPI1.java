package hack.rawfish2d.client.pbots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.ModTest.MoneyBot;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.modules.Spam;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;

public class PBotAPI1 {
	public static int sendMsgIndex = 0;
	public static int sendBotIndex = 0;
	public static TimeHelper time = new TimeHelper();
	public static CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<String>();
	
	public static void setMessageList(CopyOnWriteArrayList<String> list) {
		messages.clear();
		messages = list;
	}
	
	public static void syncSpam() {
		long delay = 0;
		
		for(PBotVar var : PBotCmd.pbot_vars) {
			if(var.getModuleName().equalsIgnoreCase("Spam") && var.getVarName().equalsIgnoreCase("delay")) {
				delay = var.get(0);
				break;
			}
		}
		
		delay = 1100;
		try {
			if(time.hasReached(delay)) {
				
				PBot bot = getNextBot();
				String msg = getNextMessage();
				
				if(bot != null && msg != null) {
					bot.pbotth.send(msg);
				}
				
				time.reset();
			}
		}
		catch(Exception ex) {}
	}
	
	
	public static PBot getNextBot() {
		if(sendBotIndex >= PBotCmd.bots.size()) {
			sendBotIndex = 0;
		}
		
		PBot bot = PBotCmd.bots.get(sendBotIndex);
		/*
		while(!bot.pbotth.onSurvivalServer) {
			if(sendBotIndex < PBotCmd.bots.size() - 1) {
				sendBotIndex++;
			}
			else {
				sendBotIndex = 0;
			}
			
			bot = PBotCmd.bots.get(sendBotIndex);
		}
		*/
		
		if(sendBotIndex < PBotCmd.bots.size() - 1) {
			sendBotIndex++;
		}
		else {
			sendBotIndex = 0;
		}
		
		return bot;
	}
	
	public static String getNextMessage() {
		String msg = messages.get(sendMsgIndex);
		//String msg = "! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk";
		//String msg = "! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk";
		
		if(sendMsgIndex < messages.size() - 1) {
			sendMsgIndex++;
		}
		else {
			sendMsgIndex = 0;
		}
		
		return msg;
	}
}
