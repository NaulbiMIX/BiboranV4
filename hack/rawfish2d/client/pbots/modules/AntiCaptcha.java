package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;

public class AntiCaptcha extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("AntiCaptcha", "gToggled", false);
	
	private boolean flag = false;
	public AntiCaptcha(PBotThread bot) {
		super(bot, "AntiCaptcha");
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
			//TODO 
			//ïîôèêñèòü ıòó ïîìîéêó
			if(p.message.toLowerCase().contains("êàï÷ó")) {
				System.out.println(p.message);
				System.out.println("[AntiCaptcha] ^^^^ captcha request ^^^^");
				//String msg = "";
				//int index = p.message.indexOf("'");
				//msg = p.message.substring(index + 3, index + 6);
				//bot.connection.addToSendQueue(new Packet3Chat(msg));
				flag = true;
				return;
			}
			if(flag == true) {
				flag = false;
				String captcha = p.message.substring(2, p.message.length());
				System.out.println(p.message);
				System.out.println("[AntiCaptcha] captcha:" + captcha);
				bot.sendPacket(new Packet3Chat(captcha));
				return;
			}
			if(p.message.toLowerCase().contains("§8[§eCaptcha§8] §aÊàï÷à óñïåøíî ââåäåíà!")) {
				MiscUtils.sendChatClient("§a§lBot " + bot.instance.name + " §8[§eAntiCaptcha§8] §asuccess!");
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
