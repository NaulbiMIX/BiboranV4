package hack.rawfish2d.client.ModCombat;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;
import net.minecraft.src.Vec3Pool;

public class BowAimbot extends Module{
	private EntityPlayer target;
	public static int fov = 45;

	public BowAimbot() {
		super("BowAimbot", 0, ModuleType.COMBAT);
		setDescription("Автоприцел на луке");
	}

	@Override
	public void preMotionUpdate()
	{
		if (mc.thePlayer.getCurrentEquippedItem() != null)
		{
			if (mc.currentScreen == null)
			{
				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					if (mc.thePlayer.inventory.getCurrentItem().getItem().equals(Item.bow) && Mouse.isButtonDown(1))
					{
						target = MiscUtils.getBowAimTarget(fov);
						if (target == null) {
							return;
						}
						float pitch = MiscUtils.getBowAimPitch(target);

	                    Client.getInstance().getRotationUtils().setPitch(Client.getInstance().getRotationUtils().getPitch() + pitch);
	                    Client.getInstance().getRotationUtils().setYaw(MiscUtils.getBowAimYaw(target));

						return;
					}
				}
			}
		}
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
		target = null;
	}

	@Override
	public void onRenderHand()
	{
		EntityPlayer ent = target;
		Item item = null;
		ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem();

		if(itemStack != null)
			item = itemStack.getItem();

		boolean use = mc.gameSettings.keyBindUseItem.pressed;

		if (ent != null && item != null && (item instanceof ItemBow) && use == true)
		{
			double x = ent.posX - RenderManager.instance.viewerPosX;
			double y = ent.posY - RenderManager.instance.viewerPosY;
			double z = ent.posZ - RenderManager.instance.viewerPosZ;
			GL11.glLineWidth(2.5F);
			GL11.glColor3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex3d(x, y + 1.9D, z);
			GL11.glVertex3d(x, y, z);
			GL11.glEnd();
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
		}
	}
}
