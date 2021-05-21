package hack.rawfish2d.client.ModTest;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMisc.Tower;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class AutoBank extends Module {
	
	private TimeHelper time;
	private DoubleValue delay;
	
	private byte state = 0;
	
	public AutoBank() {
		super("AutoBank", 0, ModuleType.TEST);
		setDescription(":thinking:");
		delay = new DoubleValue(800, 500, 2000);
		time = new TimeHelper();
		
		this.elements.add(new NewSlider(this, "Delay", delay, 0, 0, true));
	}

	@Override
	public void onDisable() {
		state = 0;
	}

	@Override
	public void onEnable() {
		time.reset();
		state = 0;
	}

	@Override
	public void onUpdate() {
		
		if(time.hasReached(2000L) && state == 0) {
			time.reset();
			
			MiscUtils.sendChat("/bank");
			state = 1;
			return;
		}
		
		if(time.hasReached(1000L) && state == 1) {
			time.reset();
			
			//mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 11, 0, 0, null);
			mc.getNetHandler().addToSendQueue(new Packet102WindowClick(Client.getInstance().window_id, 11, 0, 0, null, (short)1));
			state = 2;
			return;
		}
		
		/*
		if(time.hasReached(7000L) && state == 1) {
			time.reset();
			state = 2;
			return;
		}
		*/
		if(time.hasReached(delay.getValue()) && state == 2) {
			time.reset();
			
			mc.getNetHandler().addToSendQueue(new Packet102WindowClick(Client.getInstance().window_id, 7, 0, 0, null, (short)1));
			//MiscUtils.sendChatClient("SDASS");
			//mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 7, 0, 0, null);
		}
	}
	
	@Override
	public void onPacket(Packet packet) {
		
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if(packet instanceof Packet101CloseWindow) {
			toggle();
		}
	}
}
