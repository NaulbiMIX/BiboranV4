package hack.rawfish2d.client.ModCombat;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModTest.ChokePackets;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TestUtils;
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

public class Regen extends Module{
	public static boolean smart = false;

	public Regen() {
		super("Regen", 0, ModuleType.COMBAT);
		setDescription("Быстрая регенерация хп для миниигр");
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void preMotionUpdate() {
		//System.out.println(TestUtils.make("KillAura"));
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void postMotionUpdate() {
	}

	@Override
	public void onRenderHand() {
		//regen
		//не работающая хуита блядь
		int n2 = 0;
		smart = true;
		
		//System.out.println(shouldRegen());
		
		while (n2 < 2100 && shouldRegen()) {
		//while (n2 < 600) {
			mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
			mc.getNetHandler().addToSendQueue(new Packet10Flying(mc.thePlayer.onGround));
			++n2;
		}
	}

	public static boolean shouldRegen() {
		if(smart && mc.thePlayer.hurtTime > 0)
			return false;

		if(ChokePackets.instance != null)
			if(smart && ChokePackets.instance.isToggled() && ChokePackets.choke > 0)
				return false;

		if (mc.thePlayer.onGround) {
			if (!mc.thePlayer.capabilities.isCreativeMode) {
				if (mc.thePlayer.getFoodStats().getFoodLevel() > 18 && mc.thePlayer.getHealth() < 20.0f) {
					if (!mc.thePlayer.isEating()) {
						return true;
					}
				}
			}
		}
		return false;
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
