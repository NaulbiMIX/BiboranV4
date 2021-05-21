package hack.rawfish2d.client.ModUnused;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.GuiScreen;
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
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class Spam2 extends Module{
	public static Module instance = null;
	private static long prevMs;
	public static int index = 0;
	
	public Spam2() {
		super("Spam-Queue", 0, ModuleType.MISC);
		setDescription("–‡Á‰‡˜‡ ‰ÂÌÂ„ ÔÓ 50Í (‡Á‰‡˜‡ Î˛‰ˇÏ ËÁ Ó˜ÂÂ‰Ë)");
		instance = this;
	}
	
	@Override
	public void onDisable() {
		//list.clear();
		index = 0;
	}
	
	public static void store() {
		/*
		list.clear();
		Iterator it = Client.mc.getNetHandler().playerInfoMap.values().iterator();
		
		while(it.hasNext()) {
			Object obj = it.next();
			GuiPlayerInfo gui = (GuiPlayerInfo)obj;
			list.add(gui.name);
		}
		*/
	}
	
	@Override
	public void onEnable() {
		//store();
		index = 0;
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		long var1 = 1000;
		
		if (System.nanoTime() / 1000000L - prevMs >= var1) {
			prevMs = System.nanoTime() / 1000000L;
			
			if(Spam.delayed_list.size() > 0) {
				String name = Spam.delayed_list.get(0);
				Spam.delayed_list.remove(0);
				Spam.Pay(name, Spam.amount);
			}
			//SPAM();
		}
	}
	
	@Override
	public void postMotionUpdate() {

	}
	
	@Override
	public void onRenderHand() {

	}
	
	@Override
	public void onRender() {
		
	}
	
	@Override
	public void onChatMessage(String str) {
		/*
		//ßa? ßf ßaßl? ß8[ß3»„ÓÍß8] ß6ana222ß4 ? ßaßo ‚‡ı‡‚ı‚‡ı‚‡ı‚‡ı
		if(str.contains("+")) {
			int n1 = str.lastIndexOf("ß8]");
			int n2 = str.lastIndexOf("ß4");
			
			if(n1 + 4 < n2 && n1 < str.length() && n2 < str.length()) {
				String name = str.substring(n1 + 4, n2);
				
				name = name.replaceAll("ß[0-9]", "");
				name = name.replaceAll("ß[a-f]", "");
				name = name.replaceAll("ßk", "");
				name = name.replaceAll("ßl", "");
				name = name.replaceAll("ßm", "");
				name = name.replaceAll("ßn", "");
				name = name.replaceAll("ßo", "");
				name = name.replaceAll("ßr", "");
				name = name.replaceAll(" ", "");
				
				//if(!name.equalsIgnoreCase("ana222"))
				delayed_list.add(name);
				//Pay(name, 100000);
			}
		}
		*/
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		
	}
	
	@Override
	public void onRenderOverlay() {
		if( !Spam.instance.isToggled() ) {
			int x = 2;
			int y = 2;
			
			GuiScreen.drawString(mc.fontRenderer, "ßasize : ß6" + Spam.delayed_list.size(), x + 2, y + 12, 0xFFFFFFFF); //SHADOW
			
			for(int a = 0; a < Spam.delayed_list.size(); ++a) {
				GuiScreen.drawString(mc.fontRenderer, "ß6[" + a + "]: ßa" + Spam.delayed_list.get(a), x + 2, y + 22 + (a * 10), 0xFFFFFFFF); //SHADOW
			}
		}
	}
	/*
	public static void SPAM() {
		if(index < list.size())
			index++;
		else
			index = 0;
		
		String name = list.get(index);
		Pay(name, 1);
	}
	*/
	
	public static void static_sub1() {
		
	}
	
	public static void static_sub2() {
		
	}
	
	public static void sub1() {
			
	}
	
	public static void sub2() {
		
	}
}
