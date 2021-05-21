package hack.rawfish2d.client.ModUnused;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;

public class PacketSKB extends Module {
	public static int ivar1 = 0;
	public static int ivar2 = 0;
	public static Module instance = null;
	public static boolean bvar1;
	
	public PacketSKB() {
		super("PacketSKB", 0, ModuleType.MISC);
		//setDescription("Какая-то фигня из Ciber");
		setDescription("Какая-то фигня из DoomsDay B1 (работает)");
		instance = this;
	}
	
	@Override
	public void preMotionUpdate() {
		//skb();
		//skb2();
		
	}
	
	@Override
	public void postMotionUpdate() {
		
		//MiscUtils.sendChatClient();
		//MiscUtils.sendChatClient("bb maxy : " + mc.thePlayer.boundingBox.maxY);
	}
	
	@Override
	public void onRenderOverlay() {
		String str1 = "bb miny : " + mc.thePlayer.boundingBox.minY;
		String str2 = "bb maxy : " + mc.thePlayer.boundingBox.maxY;
		R2DUtils.drawScaledFont(str1, 18, 2, 200, 0xFFFFFFFF);
		R2DUtils.drawScaledFont(str2, 18, 2, 220, 0xFFFFFFFF);
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if (packet instanceof Packet10Flying) {
			Packet10Flying p = (Packet10Flying)packet;
			double x = this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX;
			double z = this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ;
			double y = 0.08 - this.ivar1++ * 1.0E-10;
			
			p.yPosition += y;
			p.stance += y;
			if (Math.sqrt(x * x + z * z) < 0.0625) {
				bvar1 = !bvar1;
				
				if (bvar1) {
					p.xPosition += 0.05;
				}
				else {
					p.xPosition -= 0.05;
				}
			}
		}
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void onEnable() {
		ivar1 = 0;
		bvar1 = false;
	}
	
	@Override
	public void onDisable() {
		ivar1 = 0;
		ivar2 = 0;
		mc.thePlayer.bbyoffset = 0;
	}
	
	private void skb2() {
		mc.thePlayer.onGround = false;
		
		if(ivar1 < 300)
			++ivar1;
		else
			ivar1 = 1;
		mc.thePlayer.bbyoffset = 0.08 - ivar1 * 1.0e-8;
		
		if(mc.thePlayer.motionX != 0 && mc.thePlayer.motionZ != 0) {
			++ivar2;
			if(ivar2 >= 20) {
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX + 0.06, mc.thePlayer.boundingBox.minY + mc.thePlayer.bbyoffset, mc.thePlayer.posY + mc.thePlayer.bbyoffset, mc.thePlayer.posZ + 0.06, false));
				ivar2 = 0;
			}
		}
	}
	
	//some weird code that called "SKB"
	private void skb() {
		//from DD B1
		if(ivar1 < 300)
			++ivar1;
		else
			ivar1 = 0;
		mc.thePlayer.bbyoffset = 0.08 - ivar1 * 1.0e-8;
		//mc.thePlayer.bbyoffset = 0.07799999999999D;
		//MiscUtils.sendChatClient("bb minY: " + mc.thePlayer.boundingBox.minY);
		//MiscUtils.sendChatClient("bb maxY: " + mc.thePlayer.boundingBox.maxY);
		//MiscUtils.sendChatClient("bbyoffset: " + mc.thePlayer.bbyoffset);
		//MiscUtils.sendChatClient("ivar1: " + ivar1);
		//if(!mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindBack.pressed) {
		if(mc.thePlayer.motionX != 0 && mc.thePlayer.motionZ != 0 && mc.thePlayer.onGround) {
			++ivar2;
			if(ivar2 == 50) {
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX + 0.06, mc.thePlayer.boundingBox.minY + mc.thePlayer.bbyoffset, mc.thePlayer.posY + mc.thePlayer.bbyoffset, mc.thePlayer.posZ + 0.06, false));
			}
			if(ivar2 == 100) {
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX - 0.06, mc.thePlayer.boundingBox.minY + mc.thePlayer.bbyoffset, mc.thePlayer.posY + mc.thePlayer.bbyoffset, mc.thePlayer.posZ - 0.06, false));
				ivar2 = 0;
			}
		}
		/*
		//from Ciber
		//old shit 
		if (!mc.thePlayer.onGround) {
			if (mc.thePlayer.isCollidedHorizontally) {
				if(Client.getModuleToggled("Spider")) {
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + step, mc.thePlayer.posZ);
					mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, false));
				}
			}
		}
		*/
	}
}
