package hack.rawfish2d.client.ModOther;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet7UseEntity;

public class ChatHotkeys extends Module {
	private static HashMap<Integer, String> map = new HashMap<Integer, String>();
	
	public ChatHotkeys() {
		super("ChatHotkeys", 0, ModuleType.NONE);
		setDescription("ChatHotkeys");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		
	}
	
	public static HashMap<Integer, String> getMap() {
		return map;
	}
	
	public static void onKeyPressed(int key) {
		if(map.containsKey(new Integer(key))) {
			MiscUtils.sendChat(map.get(new Integer(key)));
		}
	}
	
	public static boolean isExist(String key) {
		boolean ret = false;
		int nkey = Keyboard.getKeyIndex(key.toUpperCase());
		ret = map.containsKey(new Integer(nkey));
		return ret;
	}
	
	public static void printAllHotkeys() {
		for(Integer key : map.keySet()) {
			MiscUtils.sendChatClient("" + Keyboard.getKeyName(key.intValue()) + " | " + map.get(key));
		}
	}
	
	public static void unbindAll() {
		map.clear();
		MiscUtils.sendChatClient("§aAll chat hotkeys is unbinded!");
	}
	
	public static void bind(String key, String command) {
		int nkey = Keyboard.getKeyIndex(key.toUpperCase());
		if(nkey != 0)
		{
			if(!isExist(key)) {
				map.put(new Integer(nkey), command);
				MiscUtils.sendChatClient("§aHotkey '" + key + "' added!");
			}
			else {
				MiscUtils.sendChatClient("§cHotkey is already exist!");
			}
		}
		else {
			MiscUtils.sendChatClient("§cInvalid hotkey!!");
		}
	}
	
	public static void unbind(String key) {
		int nkey = Keyboard.getKeyIndex(key.toUpperCase());
		if(nkey != 0)
		{
			if(isExist(key)) {
				map.remove(new Integer(nkey));
				MiscUtils.sendChatClient("§aHotkey '" + key + "' removed!");
			}
			else {
				MiscUtils.sendChatClient("§cHotkey doen't exist!");
			}
		}
		else {
			MiscUtils.sendChatClient("§cInvalid hotkey!");
		}
	}
}
