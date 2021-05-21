package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemSpade;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;

public class FastBow extends Module{
	public static int loops = 25;

	public FastBow() {
		super("FastBow", 0, ModuleType.COMBAT);
		setDescription("Стреляет из лука как из пулемёта");
	}

	@Override
	public void onUpdate() {
		fastbow();
	}

	private void fastbow() {
		if
		(
			mc.thePlayer.getHealth() > 0
			&& mc.thePlayer.inventory.getCurrentItem() != null
			&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
			&& mc.gameSettings.keyBindUseItem.pressed
		)
		{
			mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
			mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

			mc.getNetHandler().addToSendQueue(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getCurrentItem(), 1.0F, 1.0F, 1.0F));

			MiscUtils.sleep(1L);
			//MiscUtils.sleep(2L);
			
			for(int i = 0; i < loops; i++)
			{
				mc.getNetHandler().addToSendQueue(new Packet10Flying(true));
				MiscUtils.sleep(1L);
			}
			
			/*
			mc.getNetHandler().addToSendQueue(new Packet10Flying(true));
			MiscUtils.sleep(1L);
			mc.getNetHandler().addToSendQueue(new Packet10Flying(true));
			MiscUtils.sleep(2L);
			mc.getNetHandler().addToSendQueue(new Packet0KeepAlive());
			MiscUtils.sleep(2L);
			mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
			MiscUtils.sleep(2L);
			*/
			mc.getNetHandler().addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255));
			mc.getNetHandler().addToSendQueue(new Packet15Place(-1, -1, -1, -1, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
		}
	}
}
