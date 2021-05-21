package hack.rawfish2d.client.ModUnused;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.MiscUtils;

public class BanMuteKick {
	public String admin;
	public String action; //ban/kick/mute
	public String player;
	public String time; //if forever or kick - null
	public String reason;
	
	public void print() {
		if(time != null)
			System.out.println(admin + " " + action + " " + player + " на " + time + " с причиной: " + reason);
		else
			System.out.println(admin + " " + action + " " + player + " с причиной: " + reason);
	}
	
	public void chat() {
		if(time != null)
			MiscUtils.sendChatClient(admin + " " + action + " " + player + " на " + time + " с причиной: " + reason);
		else
			MiscUtils.sendChatClient(admin + " " + action + " " + player + " с причиной: " + reason);
	}
}
