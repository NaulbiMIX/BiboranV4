package hack.rawfish2d.client.ModMisc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModOther.ChatHotkeys;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.ScorePlayerTeam;

public class DonateDumper extends Module {
	private class Donater {
		public String donate;
		public String name;
		
		public Donater(String donate, String name) {
			this.donate = donate;
			this.name = name;
		}
	}
	
	private TimeHelper timeAdd;
	private TimeHelper timeWrite;
	public static CopyOnWriteArrayList<Donater> arr = new CopyOnWriteArrayList<Donater>();
	
	public DonateDumper() {
		super("DonateDumper", 0, ModuleType.MISC);
		setDescription("Запись ников с донатом в файл");
		timeAdd = new TimeHelper();
		timeWrite = new TimeHelper();
	}
	
	@Override
	public void onDisable() {
		write();
	}
	
	@Override
	public void onEnable() {
		read();
		
		timeAdd.reset();
		timeWrite.reset();
	}
	
	@Override
	public void onUpdate() {
		if(timeAdd.hasReached(3000)) {
			findAndAddDonater();
			timeAdd.reset();
		}
		
		if(timeWrite.hasReached(10000)) {
			write();
			timeWrite.reset();
		}
	}
	
	@Override
	public void onPacket(Packet packet) {
		
	}
	
	private void write() {
		try {
			ConfigManager.createFile(ConfigManager.dirname + "/" + "donaters.txt");
			
			File file = new File(ConfigManager.dirname, "donaters.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			HashMap<Integer, String> map = ChatHotkeys.getMap();
			
			for(Donater don : arr) {
				out.write(don.donate + "\r\n");
				out.write(don.name + "\r\n");
			}
			
			out.close();
		}
		catch (Exception ex) {}
	}
	
	private void read() {
		arr.clear();
		try {
			File file = new File(ConfigManager.dirname, "donaters.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			
			String str1 = "";
			String str2 = "";
			boolean flag = false;
			while ((line = br.readLine()) != null) {
				if(!flag) {
					str1 = line.trim();
					flag = true;
				}
				else if(flag) {
					str2 = line.trim();
					flag = false;
					arr.add(new Donater(str1, str2));
				}
			}
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void addAndRemovePrevClone(Donater don) {
		for(Donater e : arr) {
			if(e.name.equalsIgnoreCase(don.name)) {
				arr.remove(e);
				arr.add(don);
				return;
			}
		}
		arr.add(don);
	}
	
	private void findAndAddDonater() {
		for(Object obj : mc.thePlayer.sendQueue.playerInfoList) {
			if(obj instanceof GuiPlayerInfo) {
				GuiPlayerInfo gpi = (GuiPlayerInfo)obj;
				ScorePlayerTeam scp = mc.theWorld.getScoreboard().getPlayersTeam(gpi.name);
				String tab_nickname = ScorePlayerTeam.func_96667_a(scp, gpi.name);
				
				if(tab_nickname.contains("]")) {
					int index = tab_nickname.lastIndexOf("]");
					String donate = tab_nickname.substring(0, index + 1);
					
					donate = donate.replaceAll("§[0-9]", "");
					donate = donate.replaceAll("§[a-f]", "");
					donate = donate.replaceAll("§[k-l]", "");
					donate = donate.replaceAll("§r", "");
					
					addAndRemovePrevClone(new Donater(donate, gpi.name));
				}
			}
		}
	}
}
