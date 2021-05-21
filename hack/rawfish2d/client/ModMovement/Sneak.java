package hack.rawfish2d.client.ModMovement;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class Sneak extends Module{

	private Random random = new Random();
	
	public Sneak() {
		super("Sneak", 0, ModuleType.MOVEMENT);
		setDescription("¬ы посто€нно в прис€ди, но можете бегать");
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 2));
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void preMotionUpdate() {
		/*
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 2));
		*/
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 2));
	}
	
	@Override
	public void postMotionUpdate() {
		/*
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
		*/
		
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 2));
		mc.thePlayer.sendQueue.addToSendQueue(new Packet19EntityAction(mc.thePlayer, 1));
	}
}
