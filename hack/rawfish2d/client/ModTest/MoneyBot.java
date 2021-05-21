package hack.rawfish2d.client.ModTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.modules.Spam;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.PathEntity;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class MoneyBot extends Module {
	public static Module instance = null;
	public static BoolValue drawpaylist;
	public static String triggerMsg = "+";
	public static DoubleValue amount;
	public static DoubleValue delay;
	
	public static int sendMsgIndex = 0;
	public static int sendBotIndex = 0;
	public static TimeHelper time = new TimeHelper();
	public static CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<String>();
	
	public MoneyBot() {
		super("MoneyBot", 0, ModuleType.TEST);
		setDescription("–‡Á‰‡˜‡ ‰ÂÌÂ„ ·ÓÚ‡ÏË");
		instance = this;
		drawpaylist = new BoolValue(true);
		amount = new DoubleValue(50000, 0, 5000000);
		delay = new DoubleValue(1100, 10, 3300);
		
		this.elements.add(new CheckBox(this, "Draw pay list", drawpaylist, 0, 0));
		this.elements.add(new NewSlider(this, "Amount", amount, 0, 10, true));
		this.elements.add(new NewSlider(this, "Delay", delay, 0, 30, true));
	}
	
	@Override
	public void onDisable() {
		messages.clear();
	}
	
	@Override
	public void onEnable() {
		sendBotIndex = 0;
		sendMsgIndex = 0;
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void onRender() {
		syncSpam();
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		if(drawpaylist.getValue()) {
			int x = 2;
			int y = 6;
			
			GuiScreen.drawString(mc.fontRenderer, "ßasize : ß6" + messages.size(), x + 2, y + 12, 0xFFFFFFFF); //SHADOW
			
			for(int a = 0; a < messages.size(); ++a) {
				GuiScreen.drawString(mc.fontRenderer, "ß6[" + a + "]: ßa" + messages.get(a), x + 2, y + 22 + (a * 10), 0xFFFFFFFF); //SHADOW
			}
		}
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			
			String message = p.message.toLowerCase();
			String triggermsg = triggerMsg.toLowerCase();
			
			if(message.contains(triggermsg)) {
				addNicknameToList(message);
			}
		}
	}
	
	private String getAndRemoveNickname() {
		String ret = messages.get(messages.size() - 1);
		messages.remove(messages.size() - 1);
		return ret;
	}
	
	private String getNicknameFromChat(String message) {
		String name = null; 
		String msg = triggerMsg.toLowerCase();
		if(message.contains(msg)) {
			int n1 = message.lastIndexOf("ß8] ");
			int n2 = message.lastIndexOf("ß4 ");
			
			if(n1 + 4 < n2 && n1 < message.length() && n2 < message.length()) {
				name = message.substring(n1 + 4, n2);
				
				name = name.replaceAll("ß[0-9]", "");
				name = name.replaceAll("ß[a-f]", "");
				name = name.replaceAll("ßk", "");
				name = name.replaceAll("ßl", "");
				name = name.replaceAll("ßm", "");
				name = name.replaceAll("ßn", "");
				name = name.replaceAll("ßo", "");
				name = name.replaceAll("ßr", "");
				name = name.replaceAll(" ", "");
			}
		}
		//System.out.println("MoneyBot pay name:" + name);
		return name;
	}
	
	private void addNicknameToList(String chatline) {
		String name = getNicknameFromChat(chatline);
		if(name == null) {
			return;
		}
		
		if(!isContains(name)) {
			messages.add("/pay " + name + " " + (int)amount.getValue());
		}
	}
	
	private boolean isContains(String msg) {
		msg = msg.toLowerCase();
		for(String str : messages) {
			str = str.toLowerCase();
			if(str.contains(msg)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void syncSpam() {		
		try {
			if(time.hasReached(delay.getValue())) {
				
				PBot bot = getNextBot();
				if(messages.size() > 0) {
					String msg = messages.get(0);
					
					if(bot != null && msg != null) {
						if(bot.pbotth.money >= amount.getValue()) {
							bot.pbotth.send(msg);
							messages.remove(0);
						}
						else {
							bot.pbotth.sendChatClient("ßeBot ß8[ßa" + bot.instance.name + "ß8] " + "ßeOut of money!");
						}
					}
					
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
