package hack.rawfish2d.client.Ban;

import java.io.IOException;
import java.util.ArrayList;

import hack.rawfish2d.client.Client;
import net.minecraft.src.GuiConnecting;

public class uBanThread implements Runnable {
	
	String name;
	public static boolean show = false;
	public static ArrayList<BanClass> arr = new ArrayList<BanClass>();
	
	public uBanThread(String n) {
		name = n;
	}
	
	public int getCombinations(String str) {
		char []s = str.toCharArray();
		int count = 0;
		
		for(int a = 0; a < str.length(); ++a) {
			if(Character.isLetter(s[a])) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public void run() {
		Client.getInstance().mc.thePlayer.sendChatToPlayer("§aUltraBan§e in process...");
		
		try {
			if(show)
				ban(name, true);
			else
				ban(name, false);
		}
		catch (IOException e) {System.out.println("§cCatch1. Failed to ban " + name);}
		catch (InterruptedException e) {System.out.println("§cCatch2. Failed to ban " + name);}
		
		try {
			if(show)
				uBan(name, true);
			else
				uBan(name, false);
		}
		catch (InterruptedException e) {System.out.println("§cCatch2. Failed to uBan " + name);}
		
		Thread.currentThread().interrupt();
	}
	
	public static void uBan(String name, boolean showmsg) throws InterruptedException {
		char []s = name.toCharArray();
		char v;
		int l = name.length();
		char []newstr = new char[l];
		String retstr;
		int size = 0;
		int count = 0;
		
		boolean next = false;
		
		for(int i = 0; i < 1 << l; i++) {
			for(int j = 0; j < l; j++) {
				int flag = i & (1 << j);
				if(		s[j] == '0' ||
						s[j] == '1' ||
						s[j] == '2' ||
						s[j] == '3' ||
						s[j] == '4' ||
						s[j] == '5' ||
						s[j] == '6' ||
						s[j] == '7' ||
						s[j] == '8' ||
						s[j] == '9' ||
						s[j] == '_' ) {
					if(flag > 0) {
						next = true;
						break;
					}
				}
			}
			
			if(next)
			{
				next = false;
				continue;
			}
			
			for(int j = 0; j < l; j++) {
				int flag = i & (1 << j);
				if(flag > 0)
					v = Character.toUpperCase(s[j]);
				else
					v = Character.toLowerCase(s[j]);
				
				newstr[size] = (char)v;
				size++;
			}
			count++;
			retstr = new String(newstr);
			
			try {
				ban(retstr, showmsg);
			} catch (IOException e) {System.out.println("§cCatch1. Failed to uBan " + name);}
			
			size = 0;
		}
		/*
		for(int a = 0; a < arr.size(); ++a) {
			BanClass banC = arr.get(a);
			banC.wakeThreads();
			banC.writeThread.destroy();
			banC.readThread.destroy();
			try {
				banC.networkSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			banC.closeConnections();
		}
		arr.clear();
		*/
		Client.getInstance().mc.thePlayer.sendChatToPlayer("§a[§cUltraBan§a]§e Player nickname §a'" + name + "'§e absolutely banned for all §c" + count + "§e combinations!");
		System.gc();
	}
	
	public static void ban(String name, boolean showmsg) throws IOException, InterruptedException {
		BanClass banClass = new BanClass(GuiConnecting.IP + ":" + GuiConnecting.PORT, name);
		//arr.add(new BanClass(GuiConnecting.str, name));
		
		if(showmsg)
			Client.getInstance().mc.thePlayer.sendChatToPlayer(String.valueOf(name) + " was banned");
		
		//Thread.sleep(delay);
		banClass.closeConnections();
	}
}
