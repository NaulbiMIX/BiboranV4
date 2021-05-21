package hack.rawfish2d.client.ModOther;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModRender.ChestESP;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.RenderManager;

public class CreativeTP extends Module {

	public static double x;
	public static double z;
	public static int packets;
	public static boolean toggled;
	
	public CreativeTP() {
		super("CreativeTP", 0, ModuleType.NONE);
		setDescription("Телепортирует на указанные координаты (только в режиме креатива)");
		x = 0;
		z = 0;
		packets = 300;
		toggled = false;
	}
	
	@Override
	public void onDisable() {
		toggled = false;
	}	
	
	@Override
	public void onEnable() {
		toggled = true;
	}
	
	@Override
	public void preMotionUpdate() {
		//regions
		//rick01
		//22895
		//NOLL4N
		//val1
		//_sergiy_
		
		//old home -1700 1090
		//home 335 14 
		if(mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0) {
			return;
		}
		
		if(Math.round(x) == Math.round(mc.thePlayer.posX) && Math.round(z) == Math.round(mc.thePlayer.posZ))
		{
			toggle();
			return;
		}
		
		double y = mc.thePlayer.posY;
		double miny = mc.thePlayer.boundingBox.minY;
		boolean onGround = mc.thePlayer.onGround; //mc.thePlayer.onGround;
		
		packets = 100;
		
		for(int a = 0; a < packets; ++a)
		{
			mc.thePlayer.sendQueue.addToSendQueue(new Packet13PlayerLookMove(x, miny, y, z, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), onGround));
			mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(x, miny, y, z, onGround));
		}
	}
}
