package hack.rawfish2d.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import hack.rawfish2d.client.Client;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiPlayerInfo;

public class PlayerUtils {
	
	private static ArrayList<String> friends = new ArrayList<String>();
	//public static String []authors = {"RawFish2D", "Yurik888", "vlad289zip1"};
	//public static String []authorFriends = {"ilyapods", "ddos1337", "dimas_777", "_Green_Fox_"};
	
	public static String []authors = {"RawFish2D", "Yurik888", "_fox"};
	public static String []authorFriends = {"ddos1337", "Fucking_Die"};
	public static ConcurrentHashMap<String, String> nickandprefix = new ConcurrentHashMap<String, String>();
	//public static String fucking = "Fucking_Die";
	
	/*
	public static String []goodPLayers = {	"Funtic", "Flupi",
											"dasmagichippo",
											"vulgar_molly", "vulgar_molly2", "meller", "mix", "barsik", "101", //"darrirrov"
											"kasmex", "hest_bd", "marten",
											"SirMark", "SirMark1", "SirMark2", "SirMark3",
											"TheSuperCookie",
											"dryzhok"};
	
	public static String []ebobo = {		"Jhf122",
											"bubinoso", "s3im",
											"ZetaTrooper", "cannon666", "steimoncito",
											"Nextir965", "lexas", "v3f",  //insensetivee
											};
	*/
	public static Vector3f getEntityPos(Object obj)
	{
		if(obj instanceof Entity) {
			Vector3f pos = new Vector3f();
			Entity ent = (Entity)obj;
			pos.x = (float) ent.posX;
			pos.y = (float) ent.posY;
			pos.z = (float) ent.posZ;
			
			return pos;
		}
		return null;
	}
	
	public static boolean isNameEquals(String name1, String name2) {
		String str1 = name1.toLowerCase();
		String str2 = name2.toLowerCase();
		if(str1.contains(str2)) {
			int index = str1.lastIndexOf(str2);
			String str3 = str1.substring(index);
			if(str3.equals(str2)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void friendListClear() {
		friends.clear();
	}
	
	public static void friendListAdd(String name) {
		friends.add(name);
		ConfigManager.saveFriendList();
	}
	
	public static void friendListRemove(String name) {
		friends.remove(name);
		ConfigManager.saveFriendList();
	}
	
	public static ArrayList<String> getFriendList() {
		return friends;
	}
	
	public static boolean isPlayer(Entity ent) {
		if(ent == null)
			return false;
		
		if(ent instanceof EntityPlayer) {
			
			if(Client.getInstance().PlayerListCheck == false)
				return true;
			
			EntityPlayer player = (EntityPlayer)ent;
			String name = player.getEntityName();
			/*
			GuiPlayerInfo pinfo = (GuiPlayerInfo)Client.getInstance().mc.getNetHandler().playerInfoMap.get(name);
			
			if(pinfo == null)
				return false;
			*/
			
			int size = Client.getInstance().mc.getNetHandler().playerInfoMap.values().size();
			Iterator it = Client.getInstance().mc.getNetHandler().playerInfoMap.values().iterator();
			
			while(it.hasNext()) {
				Object obj = it.next();
				GuiPlayerInfo gui = (GuiPlayerInfo)obj;
				if(gui.name.contains(name))
					return true;
			}
			
			return false;
		}
		return false;
	}
	
	public static boolean strEqual(String str1, String str2) {
		str1 = str1.toLowerCase(); //name
		str2 = str2.toLowerCase(); //author
		
		int index = str1.indexOf(str2); //if name contains author
		
		if(index >= 0) {
			String temp = str1.substring(index);
			if(temp.equals(str2)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isAuthor(String name) {
		for(int a = 0; a < authors.length; ++a) {
			if(strEqual(name, authors[a]))
				return true;
		}
		
		return false;
	}
	
	public static boolean isAuthorFriend(String name) {
		for(int a = 0; a < authorFriends.length; ++a) {
			if(strEqual(name, authorFriends[a]))
				return true;
		}
		
		return false;
	}
	
	public static boolean isFriend(Entity ent) {
		if(ent == null)
			return false;
		
		if(ent instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)ent;
			String name = getName(player);
			
			if(name == null)
				return false;
			
			for(String friend : friends) {
				if(friend.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isFriend(String name) {
		if(name == null)
			return false;
		
		if(name != null) {
			for(String friend : friends) {
				if(friend.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String sub_5(String replaceAll) {
		/*
		if (!this.file.exists()) {
			return replaceAll;
		}
		for (int i = 0; i < this.arr2.size(); ++i) {
			if (Client.getInstance().mc.thePlayer.sendQueue.playerInfoMap.containsKey(this.arr1.get(i))) {
				replaceAll = replaceAll.replaceAll("-" + (String)this.arr2.get(i), (String)this.arr1.get(i));
			}
		}
		System.out.println(replaceAll);
		*/
		return replaceAll;
	}
	
	//returns entity name only if this is a real player
	public static String getName(Entity ent) { 
		if(ent == null)
			return null;
		
		if(ent instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)ent;
			String name = player.getEntityName();
			
			int size = Client.getInstance().mc.getNetHandler().playerInfoMap.values().size();
			Iterator it = Client.getInstance().mc.getNetHandler().playerInfoMap.values().iterator();
			
			while(it.hasNext()) {
				Object obj = it.next();
				GuiPlayerInfo gui = (GuiPlayerInfo)obj;
				if(gui.name.contains(name))
					return name;
			}
		}
		return null;
	}
	
	public static String makePrefix(String input) {
		if(input == null)
			return "*NULL*";
		
		boolean ifnot = true;
		String out = "";
		
		/*
		else if(PlayerUtils.strEqual(input, PlayerUtils.vulgar)) {
			if(input.contains("õåëïåð") || input.contains("Õåëïåð"))
				out = "§a[§b§lÕåëïåð §c§lÀôåðèñò§a] §a§l" + PlayerUtils.vulgar;
			else
				out = "§a[§c§lÀôåðèñò§a] §a§l" + PlayerUtils.vulgar;
			ifnot = false;
		}
		else if(PlayerUtils.strEqual(input, PlayerUtils.sirmark)) {
			if(input.contains("õåëïåð") || input.contains("Õåëïåð"))
				out = "§8[§4ÍåäîÕåëïåð§8] §c§lS§6§li§e§lr§e§lM§a§la§9§lr§5§lK";
			else
				out = "§8[§dLGBT§8] §c§lS§6§li§e§lr§e§lM§a§la§9§lr§5§lK";
			ifnot = false;
		}
		*/
		
		for(String asd : nickandprefix.keySet()) {
			if(strEqual(input, asd)) {
				String prefix = nickandprefix.get(asd);
				out = prefix + " " + input;
				ifnot = false;
			}
		}
		
		if(PlayerUtils.isAuthor(input)) {
			out = "§8[§c§lCreator§8] " + input;
			ifnot = false;
		}
		else if(PlayerUtils.isAuthorFriend(input)) {
			out = "§8[§c§lHacker§8] " + input;
			ifnot = false;
		}
		
		if(ifnot == false)
			return out;
		else
			return input;
	}
}
