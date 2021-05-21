package hack.rawfish2d.client.ModMovement;

import java.util.List;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;

public class MysFly extends Module{

	private double yVal;
	static double flyRange;
	static double flySpeed;
	
	public MysFly() {
		super("MysFly", Keyboard.KEY_M, ModuleType.MOVEMENT);
		setDescription("Мистический полёт! (лучше работает при получении урона)");
		
		flyRange = 0.0;
		flySpeed = 2f;
	}
	
	@Override
	public void onEnable() {
		if (mc.thePlayer.onGround) {
			yVal = mc.thePlayer.posY;
		}
	}
	
	@Override
	public void preMotionUpdate() {
		if (mc.thePlayer.motionY < 0.0 && mc.thePlayer.isAirBorne && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInsideOfMaterial(Material.lava)) {
			mc.thePlayer.motionY = 0;
			mc.thePlayer.jumpMovementFactor = (float)(mc.thePlayer.jumpMovementFactor * flySpeed);
		}
	}
	
	@Override
	public void postMotionUpdate() {
		for(int a = 0; a < 10; ++a) {
			mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
			mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
			//mc.thePlayer.sendQueue.addToSendQueue(new Packet10Flying(false));
		}
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if(packet != null && packet instanceof Packet10Flying) {
			Packet10Flying packet10flying = (Packet10Flying)packet;
			packet10flying.onGround = false;
		}
	}
}
