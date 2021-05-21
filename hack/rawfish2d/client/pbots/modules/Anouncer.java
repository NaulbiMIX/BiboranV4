package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.ModUnused.BanMuteKick;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet3Chat;

public class Anouncer extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Anouncer", "gToggled", false);
	
	private TimeHelper delay = new TimeHelper();
	private long delayMS;
	private long staticDelayMS;
	private String staticMsg;
	private List<String> remaining = new ArrayList<String>();
	
	private static CopyOnWriteArrayList<String> tempmute_arr = new CopyOnWriteArrayList<String>();
	private static CopyOnWriteArrayList<String> tempban_arr = new CopyOnWriteArrayList<String>();
	
	private static CopyOnWriteArrayList<String> kick_arr = new CopyOnWriteArrayList<String>();
	
	private static CopyOnWriteArrayList<String> mute_arr = new CopyOnWriteArrayList<String>();
	private static CopyOnWriteArrayList<String> ban_arr = new CopyOnWriteArrayList<String>();
	private Random rnd = new Random();
	
	private boolean initialized = false;
	
	public Anouncer(PBotThread bot) {
		super(bot, "Anouncer");
		toggled = gToggled.get(false);
		
		delay.reset();
		delayMS = 2000L;
		staticDelayMS = (1000L * 60L * 10L); //��� � 10 �����
		staticMsg = "���� ��! � 'annoying bot' � � ������� �� ����/����/���� �������� �����������.";
		
		if(initialized == false) {
			initialized = true;
			tempmute_arr.add("![player] ��������� ����� ����������, ������� [admin] ������� ���!");
			tempmute_arr.add("![player] ���� ����� ��� ���� �������! �� ��� �� �������");
			tempmute_arr.add("![player] ������� ��������� ���! ���� ��� ���������");
			tempmute_arr.add("!������ [player] ��������! �� ��� ��������������? ����!! ����!");
			tempmute_arr.add("![admin] �������� �� ����� [player] � ����� ��� ��������");
			tempmute_arr.add("![player] �� ����� ����� [admin]'� � ��� �������!");
			tempmute_arr.add("!���? ������ [player] ��������? ... �� � �����");
			tempmute_arr.add("![admin] ������ ����� ���� �������� [player] ����������! �� ��� ���������");
			tempmute_arr.add("!������� [player] ������ ����� ���� �����, � ���� ����� ������� � ����");
			
			tempban_arr.add("![player] ��� �������! ��� ��� � ����... ��� �� �������");
			tempban_arr.add("![player] ��������. �� ������, ������ �� ����� ������ ������ � ��������");
			tempban_arr.add("![player] ��������, ��� �� �����, �������� ������ ��� ��������!");
			tempban_arr.add("!� [player] ����� ���� ������� ���� ������ ����� ��� ��� ��� ��������!");
			tempban_arr.add("![admin] �����-�� ������� [player]. �� ��� �� ���� ����, ������?");
			tempban_arr.add("![admin] �����-�� ������� [player]. �� ��� �� ���� ����, ������?");
			tempban_arr.add("!���-�� ������ ���? [admin] ��������� ������� [player]");
			tempban_arr.add("!��, �� ��� ���. [player] ������� � ������� �������!");
			tempban_arr.add("![player] ���� ���, ���� <3");
			tempban_arr.add("!�� ��! ���� ���� ������ [player] �� a���! �� �� ��� ��� �� �?");
			
			ban_arr.add("!��, ������� [player] ��� ������� ��������. �� � �����");
			ban_arr.add("![admin] �������� ������� [player]. ��� ��� �����?");
			ban_arr.add("!���, ��� ������ �����? ���! ��� [player] ������� ���! ��!");
			ban_arr.add("![player] ������� ���, ������ �� ����� ����� ������� �����!");
			ban_arr.add("![player] �� ������ ���� ������ � ������. ��������");
			ban_arr.add("!�������? [player] ��� �������, ������ ��� �� ��������");
			ban_arr.add("!���-�� ������� ������, ���-�� ������, � ��� [player] ������� ���");
			ban_arr.add("![player] ������� ��� � ��� ����� ������ �� [admin]");
			ban_arr.add("![admin] ����� ������� [player]. ������� ������ ������");
			ban_arr.add("!��������� ��� ������ [player] ����� ����� ����: NANI?");
			
			mute_arr.add("![player] ��������� ����� ����������, ������� [admin] ������� ���!");
			mute_arr.add("![player] ����������! �� ������� ���! � ��� ���������� ���������!");
			mute_arr.add("![player] ������ ���, � ����� ���� ���-�� ������");
			mute_arr.add("!����� �� �������� [player]? � ���� �� �����");
			mute_arr.add("![admin] �������� �� [player] � ��� ��� ��� ���");
			mute_arr.add("![player] ���� ��� � �� ���������, ���� <3");
			mute_arr.add("![player] ��� ����� �� ����� ������ � ���");
			mute_arr.add("![admin] ������ ������ ����� ���� �������� [player] ����������!");
			mute_arr.add("!������� [player] ������ ����� ���� �����, � ���� ����� ������� � ����");
			
			kick_arr.add("!�� ������ ������� ������ [admin], ����� [player] ��������� � �������!");
			kick_arr.add("![admin] ������ ��� ���� ������ [player] � �������!");
			kick_arr.add("![player] ���� � ������ ��� ������ [admin]");
			kick_arr.add("![player] ����� �� ����� � ��������, ��� ��� �� ������ �������! ��!");
			kick_arr.add("!������ [player] ���������� � �������, �� �� ����� ������� � ����������!");
			kick_arr.add("!� [player] �������� �� ����� ������� ��� ��� �������? � ���������");
			kick_arr.add("![player] ��� ������ ������ ��� ���� � ������� ���");
			kick_arr.add("![player] ������ ���, �� �� ��� �������, ��� ������");
			kick_arr.add("!������� [player] ����� ��� ����� �������");
			kick_arr.add("!���-�� ������ �����, � [admin] ������ ������ [player]");
			kick_arr.add("!������ [player] ������� ������ ��� � � ��� �� ���� ������ ��...");
			kick_arr.add("![player] ����� ������ ����i����");
		}
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
			processPacket((Packet3Chat)packet);
		}
		
		if(packet instanceof Packet201PlayerInfo) {
			Packet201PlayerInfo p = (Packet201PlayerInfo) packet;
			
			if(!p.playerName.contains("dasmagichippo"))
				return;
			else {
				if(p.isConnected) {
					//bot.send("! ����� ������! ��� ���������?");
					bot.sendChatClient("�d[NameDetect] �e" + p.playerName + "�a detected. " + p.ping);
				}
				/*
				else if(!p.isConnected){
					MiscUtils.sendChat("! ���� �����(");
					MiscUtils.sendChatClient("�d[NameDetect] �e" + p.playerName + "�c left from the server/vanished");
				}
				*/
				return;
			}
		}
	}
	
	private String replaceNicknames(String str, BanMuteKick bmk) {
		//System.out.println("str 1: " + str);
		str = str.replaceAll("\\[player]", bmk.player);
		str = str.replaceAll("\\[admin]", bmk.admin);
		
		String test = str.replaceAll("[0-9]", "");
		if(test.length() < str.length() - 5) {
			str = str.replaceAll("[0-9]", "");
		}
		//System.out.println("str 2: " + str);
		return str;
	}
	
	private String getRandomTempBanMessage(BanMuteKick bmk) {
		String str = tempban_arr.get(rnd.nextInt(tempban_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomTempMuteMessage(BanMuteKick bmk) {
		String str = tempmute_arr.get(rnd.nextInt(tempmute_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomKickMessage(BanMuteKick bmk) {
		String str = kick_arr.get(rnd.nextInt(kick_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomBanMessage(BanMuteKick bmk) {
		String str = ban_arr.get(rnd.nextInt(ban_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomMuteMessage(BanMuteKick bmk) {
		String str = mute_arr.get(rnd.nextInt(mute_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private void processPacket(Packet3Chat packet) {
		if(!bot.instance.name.equalsIgnoreCase("1")) {
			//�e
			String msg = packet.message.toLowerCase();
			//msg = "�a�l�a? �cVladik �e�������� ������� �czombue�e.";
			
			if(msg.contains("�a�l�a")) {
				String []split = msg.split(" ");
				
				for(int a = 0; a < split.length; ++a) {
					split[a] = split[a].replaceAll("�[0-9]", "");
					split[a] = split[a].replaceAll("�[a-f]", "");
					
					split[a] = split[a].replaceAll("�[k-l]", "");
					split[a] = split[a].replaceAll("�r", "");
				}
				//remove dot from last str
				split[split.length - 1] = split[split.length - 1].substring(0, split[split.length - 1].length() - 1);
				
				String author_gets_punished = "!�� ��, ������ ����� ���� ����� '� ����� ������ ����'! �������� ��������������!!!";
				String ddos_gets_punished = "!����i���� ����� �� ���������� ����i�� �� ������� ���� ���� ���i� ��i����!";
				BanMuteKick bmk = new BanMuteKick();
				String sendmsg = null;
				
				if(split[2].contains("��������")) {
					if(split[3].contains("�������")) {
						bmk.admin = split[1];
						bmk.action = split[2] + " " + split[3];
						bmk.player = split[4];
						
						sendmsg = this.getRandomTempMuteMessage(bmk);
					}
					else if(split[3].contains("�������")) {
						bmk.admin = split[1];
						bmk.action = split[2] + " " + split[3];
						bmk.player = split[4];
						
						sendmsg = this.getRandomTempBanMessage(bmk);
					}
				}
				else if(split[2].contains("��������")) {
					bmk.admin = split[1];
					bmk.action = split[2] + " " + split[3];
					bmk.player = split[4];
					
					if(split[3].contains("�������")) {
						sendmsg = this.getRandomBanMessage(bmk);
					}
					else if(split[3].contains("�������")) {
						sendmsg = this.getRandomMuteMessage(bmk);
					}	
				}
				else if(split[2].contains("������")) {
					bmk.admin = split[1];
					bmk.action = split[2];
					bmk.player = split[3];
					
					sendmsg = this.getRandomKickMessage(bmk);
				}
				
				if(sendmsg != null) {
					if(bmk.player.equalsIgnoreCase("RawFish2D")) {
						bot.send(author_gets_punished);
						return;
					}
					else if(bmk.player.equalsIgnoreCase("Ddos1337")) {
						bot.send(ddos_gets_punished);
						return;
					}
					
					bot.send(sendmsg);
				}
			}
		}
	}
}
