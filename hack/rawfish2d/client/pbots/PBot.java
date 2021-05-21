package hack.rawfish2d.client.pbots;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet3Chat;

public class PBot {
	public PBot instance = null;
	public String msg1 = null;
	public String msg2 = null;
	public boolean fastSend = false;
	
	public String banName = null;
	public String banReason = null;
	public boolean autoFastBan = false;
	
	public String kickName = null;
	public String kickReason = null;
	public boolean autoFastKick = false;
	
	public String muteName = null;
	public String muteReason = null;
	public boolean autoFastMute = false;
	
	public String name;
	public PBotThread pbotth;
	public Thread th;
	
	public String mode = "none";
	/*
		modes:
		none
		ShopDupe
		ShopSell
		Brute
	*/
	
	public PBot(String name) {
		this.name = name;
		this.th = null;
		this.instance = this;
	}
	
	public void startBot(final String ip) throws IOException {
		stopBot();
		
		if(th == null) {
			th = new Thread(new Runnable() {
				public void run() {
					pbotth = new PBotThread(ip, instance);
					pbotth.connect(null);
					pbotth.run();
				}
			}, instance.name + " start()");
			th.start();
		}
	}
	
	public void stopBot() {
		try {
			if(th != null && pbotth != null) {
				pbotth.stopBot();
				//pbotth.connection.networkShutdown("BotStop");
				pbotth.interrupt();
				pbotth = null;
				th.interrupt();
				th = null;
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
	}
}
