package hack.rawfish2d.client.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModCombat.MultiAura;
import hack.rawfish2d.client.ModCombat.StormAura;
import hack.rawfish2d.client.ModMovement.BHOP;
import hack.rawfish2d.client.ModOther.ChatHotkeys;
import hack.rawfish2d.client.ModRender.ESP;
import hack.rawfish2d.client.ModRender.MobESP;
import hack.rawfish2d.client.ModRender.Nametags;
import hack.rawfish2d.client.ModRender.PlayerInfo;
import hack.rawfish2d.client.ModRender.Tracers;
import hack.rawfish2d.client.ModRender.XRAY;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.GUIStyleEnum;
import hack.rawfish2d.client.gui.ingame.GUIStyle_Legacy;
import hack.rawfish2d.client.gui.ingame.IGuiElement;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.pbots.modules.Brute;
import hack.rawfish2d.client.pbots.modules.Spam;
import hack.rawfish2d.client.pbots.utils.ProxyConnection;
import hack.rawfish2d.client.pbots.utils.ProxyManager;

public class ConfigManager {
	public static String dirname = "UltimateHack";
	public static String friendlist = "friends.ini";
	public static String config = "settings.ini";
	public static String xray = "xray.ini";
	public static String modbinds = "modbinds.ini";
	public static String chatbinds = "chatbinds.ini";
	public static String proxy = "proxy.txt";
	public static String packets = "packets.txt";
	public static String spam = "spam.txt";
	public static String guistyle = "guistyle.ini";
	
	public static void init() {
		new File(dirname).mkdir();
		
		if(ifExist(dirname + "/" + config))	
			loadConfig();
		else {
			createFile(dirname + "/" + config);
			saveConfig();
		}
		
		if(ifExist(dirname + "/" + friendlist))	
			loadFriendList();
		else {
			createFile(dirname + "/" + friendlist);
			saveFriendList();
		}
		
		if(ifExist(dirname + "/" + xray))
			loadXrayList();
		else {
			createFile(dirname + "/" + xray);
			saveStartingXrayList();
		}
		
		if(ifExist(dirname + "/" + modbinds))
			loadModBinds();
		else {
			createFile(dirname + "/" + modbinds);
			saveModBinds();
		}
		
		if(ifExist(dirname + "/" + chatbinds))
			loadChatBinds();
		else {
			createFile(dirname + "/" + chatbinds);
			saveChatBinds();
		}
		
		if(ifExist(dirname + "/" + guistyle))
			loadGuiStyle();
		else {
			createFile(dirname + "/" + guistyle);
			saveGuiStyle();
		}
		
		if(ifExist(dirname + "/" + proxy))
			ProxyManager.loadProxyList();
		
		if(!ifExist(dirname + "/" + packets))
			createFile(dirname + "/" + packets);
		
		if(ifExist(dirname + "/" + spam)) {
			loadBotSpam();
		}
	}
	
	public static void saveAll() {
		saveChatBinds();
		saveModBinds();
		saveFriendList();
		saveConfig();
		saveXrayList();
		saveGuiStyle();
	}
	
	public static void saveGuiStyle() {
		String filename = dirname + "/" + guistyle;
		createFile(filename);
		
		try(FileWriter writer = new FileWriter(filename, false)) {
		//try {
			//FileWriter writer = new FileWriter(filename, false);
			writer.write(GUIStyle_Legacy.getStyle().toString());
			writer.flush();
		}
		catch(Exception ex){} 
	}
	
	public static void loadGuiStyle() {
		String filename = dirname + "/" + guistyle;
		
		try (FileReader fr = new FileReader(filename)) {
			Scanner scan = new Scanner(fr);
			
			//while (scan.hasNextLine()) {
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				GUIStyle_Legacy.setStyle(GUIStyleEnum.valueOf(line));
			}
			
			scan.close();
		}
		catch(Exception ex){}
	}
	
	public static void savePacketName(String str) {
		try {
			Files.write(Paths.get(dirname + "/" + packets), (str + "\r\n").getBytes(), StandardOpenOption.APPEND);
		}
		catch (Exception ex) {}
	}
	
	public static boolean ifExist(String name) {
		if ( new File(name).exists() )
			return true;
		else
			return false;
	}
	
	public static void createFile(String name) {
		if (!ifExist(name)) {
			try {
				FileWriter writer = new FileWriter(name, false);
				writer.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void loadModBinds() {
		String filename = dirname + "//" + modbinds;
		
		for(Module mod : Client.getInstance().getModules()) {
			String name = mod.getName();
			String read = IniRW.readIni(filename, name);
			if(read != null) {
				int code = Integer.parseInt(read);
				mod.setKeyCode(code);
			}
		}
	}
	
	public static void saveModBinds() {
		String filename = dirname + "//" + modbinds;
		createFile(filename);
		
		for(Module mod : Client.getInstance().getModules()) {
			IniRW.writeIni(filename, mod.getName(), "" + mod.getKeyCode());
		}
	}
	
	public static void loadChatBinds() {
		try {
			File file = new File(dirname, chatbinds);
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			ChatHotkeys.unbindAll();
			
			boolean bkey = true;
			String key = "";
			String command = "";
			
			while ((line = br.readLine()) != null) {
				if(bkey) {
					key = line.trim();
				}
				else {
					command = line.trim();
					int nkey = Integer.parseInt(key);
					key = Keyboard.getKeyName(nkey);
					ChatHotkeys.bind(key, command);
				}
				bkey = !bkey;
			}
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveChatBinds() {
		try {
			createFile(dirname + "/" + chatbinds);
			
			File file = new File(dirname, chatbinds);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			HashMap<Integer, String> map = ChatHotkeys.getMap();
			
			for(Integer key : map.keySet()) {
				out.write(key.intValue() + "\r\n");
				out.write(map.get(key) + "\r\n");
			}
			
			out.close();
		}
		catch (Exception ex) {}
	}
	
	public static void loadBotSpam() {
		try {
			File file = new File(dirname, spam);
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			Spam.msgs.clear();
			while ((line = br.readLine()) != null) {
				String curLine = line.trim();
				Spam.msgs.add(curLine);
			}
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Brute.passwords.clear();
			List<String> list = Files.readAllLines(Paths.get(dirname + "//passwords.txt"), StandardCharsets.UTF_8);
			Brute.passwords.addAll(list);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveXrayList() {
		try {
			createFile(dirname + "/" + xray);
			
			File file = new File(dirname, xray);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (Integer n : XRAY.blocks.getList()) {
				out.write(n.intValue() + "\r\n");
			}
			out.close();
		}
		catch (Exception ex) {}
	}
	
	public static void saveStartingXrayList() {
		try {
			createFile(dirname + "/" + xray);
			
			ArrayList<Integer> arr = new ArrayList<Integer>();
			arr.add(14);
			arr.add(15);
			arr.add(16);
			arr.add(21);
			arr.add(27);
			arr.add(28);
			arr.add(29);
			arr.add(30);
			arr.add(33);
			arr.add(41);
			arr.add(42);
			arr.add(54);
			arr.add(56);
			arr.add(57);
			arr.add(73);
			arr.add(74);
			arr.add(116);
			arr.add(129);
			arr.add(130);
			arr.add(133);
			arr.add(152);
			
			File file = new File(dirname, xray);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i : arr) {
				out.write(i + "\r\n");
			}
			out.close();
		}
		catch (Exception ex) {}
	}
	
	public static void loadXrayList() {
		try {
			File file = new File(dirname, xray);
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			XRAY.blocks.clear();
			while ((line = br.readLine()) != null) {
				String curLine = line.toLowerCase().trim();
				int id = Integer.parseInt(curLine);
				XRAY.blocks.add(id);
			}
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			saveXrayList();
		}
	}
	
	public static void loadFriendList() {
		try {
			FileReader reader = new FileReader(dirname + "/" + friendlist);
			Scanner scan = new Scanner(reader);
			
			PlayerUtils.friendListClear();
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				PlayerUtils.friendListAdd(line);
			}
			
			reader.close();
			scan.close();
		} catch(Exception ex) {
			File f = new File(dirname + "/" + friendlist);
			f.delete();
			
			ex.printStackTrace();
		}
	}
	
	public static void saveFriendList() {
		try {
			createFile(dirname + "/" + friendlist);
			
			FileWriter writer = new FileWriter(dirname + "/" + friendlist, false);
			for(int a = 0; a < PlayerUtils.getFriendList().size(); ++a) {
				String name = PlayerUtils.getFriendList().get(a);
				writer.write(name + "\r\n");
				writer.flush();
			}
			
			writer.close();
		} catch(Exception ex) {
		    System.out.println(ex.getMessage());
		}
	}
	
	public static void saveConfig() {
		String filename = dirname + "//" + config;
		createFile(filename);
		
		/*
		Module name
		Settings text
		Value
		*/
		try {
			FileWriter writer = new FileWriter(filename, false);
			
			for(Module mod : Client.getInstance().getModules()) {
				for(IGuiElement el : mod.elements) {
					String value = "[ERROR]";
					if(el instanceof CheckBox) {
						CheckBox cb = (CheckBox)el;
						boolean bl = cb.getValue();
						
						value = Boolean.toString(bl);
					}
					if(el instanceof NewSlider) {
						NewSlider ns = (NewSlider)el;
						double fl = ns.getValue();
						
						value = "" + fl;
					}
					if(el instanceof RadioBox) {
						RadioBox rb = (RadioBox)el;
						boolean bl = rb.getState();
						
						value = Boolean.toString(bl);
					}
					writer.write(mod.getName() + "." + el.text() + "\r\n");
					writer.write(value + "\r\n");
				}
			}
			
			writer.close();
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	/*
		par1 = "module_name.element_name"
	*/
	private static IGuiElement get(String par1) {
		for(Module mod : Client.getInstance().getModules()) {
			for(IGuiElement el : mod.elements) {
				
				String name = mod.getName() + "." + el.text();
				if(par1.equalsIgnoreCase(name)) {
					return el;
				}
			}
		}
		return null;
	}
	
	public static void loadConfig() {
		String file = dirname + "/" + config;
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			FileReader reader = new FileReader(file);
			Scanner scan = new Scanner(reader);
			
			while (scan.hasNextLine()) {
				String key;
				String value;
				
				key = scan.nextLine().trim(); //"module_name.element_name"
				
				value = scan.nextLine().trim(); //value
				
				//System.out.println("key:" + key);
				//System.out.println("value:" + value);
				
				map.put(key, value);
			}
			
			reader.close();
			scan.close();
		} catch(Exception ex) {
			File f = new File(file);
			f.delete();
			
			ex.printStackTrace();
		}
		
		//System.out.println("MAP:SIZE:" + map.size());
		
		for(String key : map.keySet()) {
			String value = map.get(key);
			IGuiElement el = get(key);
			
			if(value != null) {
				try {
					if(el instanceof CheckBox) {
						CheckBox cb = (CheckBox)el;
						
						if(value.equalsIgnoreCase("true")) {
							cb.setValue(true);
						}
						else {
							cb.setValue(false);
						}
						
						//boolean b = Boolean.getBoolean(value);
						//cb.setValue(b);
					}
					if(el instanceof NewSlider) {
						NewSlider ns = (NewSlider)el;
						
						ns.setValue(Double.parseDouble(value));
					}
					if(el instanceof RadioBox) {
						RadioBox rb = (RadioBox)el;
						
						if(value.equalsIgnoreCase("true")) {
							rb.setState(true);
						}
						else {
							rb.setState(false);
						}
						
						//boolean b = Boolean.getBoolean(value);
						//rb.setState(b);
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
					//System.out.println("[loadConfig] value: " + value);
				}
			}
		}
	}
}
