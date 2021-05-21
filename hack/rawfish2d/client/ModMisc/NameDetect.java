package hack.rawfish2d.client.ModMisc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModUnused.BanMuteKick;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet207SetScore;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class NameDetect extends Module{
	
	public static Module instance = null;
	//public static List<String> names = new ArrayList<String>();
	//public static boolean banused = false;
	//public static boolean tempbanused = false;
	
	public BoolValue owner;
	public BoolValue autoBan;
	List<String> enemy = new ArrayList<String>();
	
	public NameDetect() {
		super("NameDetect", 0, ModuleType.MISC);
		setDescription("Детектит заход/выход игроков на сервер");
		instance = this;
		
		owner = new BoolValue(false);
		autoBan = new BoolValue(false);
		
		elements.add(new CheckBox(this, "Only Owner", owner, 0, 0));
		elements.add(new CheckBox(this, "Auto ban enemyes", autoBan, 0, 10));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		enemy.clear();
		//enemy.add("Potroshitel228");
		enemy.add("artemo");
		enemy.add("Vlad222888222");
		enemy.add("Hest_bd");
		enemy.add("darrirrov");
		//enemy.add("vopros999");
		enemy.add("marten");
	}
	
	@Override
	public void onPacket(Packet packet) {
		/*
		if(packet instanceof Packet207SetScore) {
			Packet207SetScore p207 = (Packet207SetScore) packet;
			if(p207.itemName.contains("Денег"))
				p207.value = 978651430;
		}
		*/
		
		if(packet instanceof Packet201PlayerInfo) {
			Packet201PlayerInfo p = (Packet201PlayerInfo) packet;
			
			if(mc.thePlayer == null)
				return;
			
			if(p.playerName.equalsIgnoreCase(mc.thePlayer.username))
				return;
			
			// элиту можно банить навсегда
			
			//  //replace 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 20
			//  /undo
			
			
			if(p.isConnected) {
				if(autoBan.getValue()) {
					for(String name : enemy) {
						if(name.equalsIgnoreCase(p.playerName)) {
							MiscUtils.sendChat("/tempban " + name + " 7h пока пуся не абижайся))");
							toggle();
							//MiscUtils.sleep(200L);
							//MiscUtils.selfKick();
							return;
						}
					}
				}
			}
			
			if(p.ping < 20)
				return;
			
			if(!p.playerName.contains("dasmagichippo") && owner.getValue())
				return;
			else {
				if(p.isConnected) {
					MiscUtils.sendChatClient("§d[NameDetect] §e" + p.playerName + "§a detected. " + p.ping);
				}
				else if(!p.isConnected){
					MiscUtils.sendChatClient("§d[NameDetect] §e" + p.playerName + "§c left from the server/vanished");
				}
				return;
			}
			
			/*
			//NameDetect Ban
			for(int a = 0; a < Client.acc_ban.size(); ++a) {
				if(	p.playerName.equalsIgnoreCase("Noyanov")
					//|| p.playerName.equalsIgnoreCase("kevin052") ||
					//|| p.playerName.equalsIgnoreCase("newgod911")
					) {
				//if(Client.acc_ban.get(a).equalsIgnoreCase(p.playerName)) {
					if(!banused) {
						//connection.addToSendQueue(new Packet3Chat("/ban " + p.playerName + " МПН, доигрался, пуся"));
						MiscUtils.sendChat("/ban " + p.playerName + " 5h лож баны/муты/кики, доигрался пуся");
						banused = true;
					}
					else if(!tempbanused) {
						//connection.addToSendQueue(new Packet3Chat("/tempban " + p.playerName + " 7d МПН, доигрался, пуся"));
						MiscUtils.sendChat("/tempban " + p.playerName + " 5h лож баны/муты/кики, доигрался пуся");
						tempbanused = true;
					}
					//connection.addToSendQueue(new Packet3Chat("/tempban " + p.playerName + " 7d МПН, доигрался, пуся, факинга не трожь"));
					if(tempbanused && banused)
						MiscUtils.sendChat("/hub");
						//connection.addToSendQueue(new Packet255KickDisconnect("Quitting"));
				}
				
				if(p.playerName.equalsIgnoreCase("awesomerain")) {
					if(!banused) {
						//connection.addToSendQueue(new Packet3Chat("/ban " + p.playerName + " МПН, доигрался, пуся"));
						MiscUtils.sendChat("/ban " + p.playerName + " 5h лож муты/кики, доигрался пуся");
						banused = true;
					}
					else if(!tempbanused) {
						//connection.addToSendQueue(new Packet3Chat("/tempban " + p.playerName + " 7d МПН, доигрался, пуся"));
						MiscUtils.sendChat("/tempban " + p.playerName + " 5h лож муты/кики, доигрался пуся");
						tempbanused = true;
					}
					//connection.addToSendQueue(new Packet3Chat("/tempban " + p.playerName + " 7d МПН, доигрался, пуся, факинга не трожь"));
					if(tempbanused && banused)
						MiscUtils.sendChat("/hub");
						//connection.addToSendQueue(new Packet255KickDisconnect("Quitting"));
				}
			}
			*/
			//System.out.println("+p.playerName : " + p.playerName);
			//System.out.println("+p.ping : " + p.ping);
			//System.out.println("+p.isConnected : " + p.isConnected);
		}
	}
	
	@Override
	public void onRenderOverlay() {
		
	}
}
