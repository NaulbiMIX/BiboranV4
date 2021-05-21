package hack.rawfish2d.client.ModCombat;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.R3DUtils;
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
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class God extends Module{
	public God() {
		super("God", 0, ModuleType.COMBAT);
		setDescription("Бессмертие для миниигр");
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void preMotionUpdate() {
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void postMotionUpdate() {
	}

	@Override
	public void onRenderHand() {
		//God
		long n = -1L;
		if (n + 1000L < System.currentTimeMillis() || n == -1L) {
			mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY - 0.2, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ, mc.thePlayer.onGround));
			n = System.currentTimeMillis();
		}
	}

	@Override
	public void onRender() {
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
	}

	@Override
	public void onRenderOverlay() {
	}

	public static void static_sub1() {
	}

	public static void static_sub2() {
	}

	public static void sub1() {
	}

	public static void sub2() {
	}
}
