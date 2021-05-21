package hack.rawfish2d.client.ModMisc;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class Nuker extends Module{
	private int nukeID;
	private boolean shouldNuke;
	
	public Nuker() {
		super("Nuker", 0, ModuleType.MISC);
		setDescription("Ломает блоки вокруг");
	}

	@Override
	public void onEnable() {
		//mc.playerController.setGameType(EnumGameType.CREATIVE);
	}

	@Override
	public void onDisable() {
		//mc.playerController.setGameType(EnumGameType.SURVIVAL);
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void preMotionUpdate() {
		
	}
	
	private void nuker() {
		int maxH = 5;
		int minH = -5;
		int maxW = 3;
		int minW = -3;
		int y2 = maxH;
		while (y2 >= minH) {
			int z2 = minW;
			while (z2 <= maxW) {
				int x2 = minW;
				while (x2 <= maxW) {
					int xPos = (int)Math.round(mc.thePlayer.posX + (double)x2);
					int yPos = (int)Math.round(mc.thePlayer.posY + (double)y2);
					int zPos = (int)Math.round(mc.thePlayer.posZ + (double)z2);
					int id2 = mc.theWorld.getBlockId(xPos, yPos, zPos);
					//if (mc.playerController.isInCreativeMode() ? id2 != 0 : id2 == this.nukeID) {
					if (id2 != 0) {
						MiscUtils.faceBlock(xPos, yPos, zPos, true);
						mc.thePlayer.sendQueue.addToSendQueue(new Packet12PlayerLook(Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), shouldNuke));
						//MiscUtils.sleep(2L);
						mc.thePlayer.sendQueue.addToSendQueue(new Packet14BlockDig(0, xPos, yPos, zPos, 1));
						//MiscUtils.sleep(2L);
						mc.thePlayer.sendQueue.addToSendQueue(new Packet14BlockDig(2, xPos, yPos, zPos, 1));
					}
					++x2;
				}
				++z2;
			}
			--y2;
		}
	}

	@Override
	public void postMotionUpdate() {
		nuker();
	}
}
