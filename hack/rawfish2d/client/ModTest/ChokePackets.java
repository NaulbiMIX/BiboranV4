package hack.rawfish2d.client.ModTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.NoKnockback;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.pbots.utils.PRepeaterNode;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;

public class ChokePackets extends Module {
	public static int choke;
	public static DoubleValue chokeMax;
	public static BoolValue newChoke;
	public static Module instance = null;
	public CopyOnWriteArrayList<Packet> list = new CopyOnWriteArrayList();

	public ChokePackets() {
		super("ChokePackets", 0, ModuleType.TEST);
		setDescription("Ïðîïóñêàåò ìåíüøå óðîíà ïî èãðîêó");
		chokeMax = new DoubleValue(18f, 0f, 200f);
		newChoke = new BoolValue(false);
		this.elements.add(new NewSlider(this, "Max choke", chokeMax, 0, 0, true));
		this.elements.add(new CheckBox(this, "New", newChoke, 0, 20));
	}

	@Override
	public void onEnable() {
		choke = 0;
	}

	@Override
	public void onDisable() {
		setName("ChokePackets");
		choke = 0;

		if(newChoke.getValue())
			sendpackets();
	}
	
	public void sendpackets() {
		for (Packet packet : list) {
			mc.thePlayer.sendQueue.addToSendQueue(packet);
			MiscUtils.sleep(3L);
		}
		list.clear();
	}
	
	@Override
	public void onUpdate() {
		if(choke == 0 && newChoke.getValue()) {
			sendpackets();
		}
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
		boolean isok = false;
		
		if(!NoKnockback.instance.isToggled()) {
			return;
		}
		
		if(newChoke.getValue() == true) {
			if(packet instanceof Packet10Flying || packet instanceof Packet11PlayerPosition || packet instanceof Packet0KeepAlive) {
				isok = true;
			}
		}
		else {
			if(packet instanceof Packet10Flying) {
				isok = true;
			}
		}
		
		//if(packet instanceof Packet10Flying || packet instanceof Packet11PlayerPosition || packet instanceof Packet0KeepAlive) {
		if(isok) {
			if(mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0) {
				
				if(mc.thePlayer.hurtTime > 0 && choke == 0)
					choke = (int)chokeMax.getValue();

				if(choke > 0) {
					setName("§dChoke §8[§a" + choke + "§8]");
					doNotSendNextPacket(true);
					choke--;
					if(newChoke.getValue())
						list.add(packet);
				}

				if(choke <= 0)
					setName("§dChoke §8[§eStay§8]");

				if(choke == 0)
					doNotSendNextPacket(false);
			}
			else
				setName("§dChoke §8[§cMoving§8]");
		}
	}
}
