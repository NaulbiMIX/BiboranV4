package hack.rawfish2d.client.ModCombat;

import java.awt.Color;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.Vector3f;
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

public class BowAimbot2 extends Module{
	private EntityPlayer target;
	private boolean ok = false;
	public static int fov = 45;

	public BowAimbot2() {
		super("BowAimPredict", 0, ModuleType.COMBAT);
		setDescription("јвтоприцел на луке с предугадыванием движени€");
		//from Vibrant hack (on Kotlin)
	}

	@Override
	public void preMotionUpdate()
	{
		ok = false;
		if(mc.thePlayer.getItemInUseCount() != 0 &&
				mc.thePlayer.getHeldItem() != null &&
				mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)
		{
			target = MiscUtils.getBowAimTarget(fov);
			//target = MiscUtils.getTarget(360, 255, false);

			if(target == null) {
				return;
			} else {
				double arrowVelocity = (72000 - mc.thePlayer.getItemInUseCount()) / 20.0;
				double yaw = mc.thePlayer.rotationYaw;

				arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0f);

				//if (arrowVelocity < 0.3)
				//	return;

				//if (arrowVelocity > 3)
				//	arrowVelocity = 3.0;

				Vector3f enemyPosition = new Vector3f(target.posX, target.posY + target.getEyeHeight(), target.posZ);

				Vector3f playerHeadPosition = new Vector3f(	mc.thePlayer.posX - Math.cos(Math.toRadians(yaw)) * 0.16f,
															mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 0.1,
															mc.thePlayer.posZ - Math.sin(Math.toRadians(yaw)) * 0.16f);

				System.out.println("ticks : " + mc.timer.renderPartialTicks);
				//println(mc.timer.renderPartialTicks)

				double otherPosX = target.lastTickPosX; //prev   lastTick   server
				double otherPosZ = target.lastTickPosZ;
				double motionX = target.posX - (otherPosX + (target.posX - otherPosX) * mc.timer.renderPartialTicks);
				double motionZ = target.posZ - (otherPosZ + (target.posZ - otherPosZ) * mc.timer.renderPartialTicks);
				Vector3f enemyVelocity = new Vector3f(motionX, 0.0, motionZ);
				Vector3f prediction = predictArrowDirection(enemyPosition, playerHeadPosition, enemyVelocity, arrowVelocity);

				//val prediction = predictArrowDirection(enemyPosition, playerHeadPosition, enemyVelocity, arrowVelocity)

				if (prediction != null) {
					ok = true;
					//System.out.println("IF");
					//System.out.println("arrowVelocity : " + arrowVelocity);
					double hypotenuse = Math.hypot(prediction.x, prediction.z);
					double yawAtan = Math.atan2(prediction.z, prediction.x);
					double pitchAtan = Math.atan2(prediction.y, hypotenuse);
					double deg = 180 / Math.PI;

					double predictedYaw = yawAtan * deg - 90f;
					double predictedPitch = -(pitchAtan * deg);

					Client.getInstance().getRotationUtils().setYaw((float)predictedYaw);
					Client.getInstance().getRotationUtils().setPitch((float)predictedPitch);
				}
				else
				{
					ok = false;
					//System.out.println("ELSE");
					//System.out.println("arrowVelocity : " + arrowVelocity);
				}
			}
		}
		/*
		if (mc.thePlayer.getCurrentEquippedItem() != null)
		{
			if (mc.currentScreen == null)
			{
				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					if (mc.thePlayer.inventory.getCurrentItem().getItem().equals(Item.bow) && Mouse.isButtonDown(1))
					{
						target = MiscUtils.getBowAimTarget();
						if (target == null) {
							return;
						}
						float pitch = MiscUtils.getBowAimPitch(target);
						MiscUtils.facePlayer(target, true);

	                    Client.getInstance().getRotationUtils().setPitch(Client.getInstance().getRotationUtils().getPitch() + pitch);
	                    //Client.getInstance().getRotationUtils().setYaw(getYaw(target));

						return;
					}
				}
			}
		}
		*/
	}

	private static Vector3f getDirectionByTime(
			Vector3f enemyPosition,
			Vector3f playerHeadPosition,
			Vector3f enemyVelocity,
			double arrowVelocity,
			double time) {
		double GRAVITY = 0.05D; //0.044 0.05
		double AIR_RESISTANCE_FACTOR = 0.99D; //0.99

		return new Vector3f(
				(enemyPosition.x + enemyVelocity.x * time - playerHeadPosition.x) * (AIR_RESISTANCE_FACTOR - 1)
						/ (arrowVelocity * (Math.pow(AIR_RESISTANCE_FACTOR, time) - 1)),

				(enemyPosition.y + enemyVelocity.y * time - playerHeadPosition.y) * (AIR_RESISTANCE_FACTOR - 1)
						/ (arrowVelocity * (Math.pow(AIR_RESISTANCE_FACTOR, time) - 1)) + GRAVITY * (Math.pow(AIR_RESISTANCE_FACTOR, time)
						- AIR_RESISTANCE_FACTOR * time + time - 1)
						/ (arrowVelocity * (AIR_RESISTANCE_FACTOR - 1) * (Math.pow(AIR_RESISTANCE_FACTOR, time) - 1)),

				(enemyPosition.z + enemyVelocity.z * time - playerHeadPosition.z) * (AIR_RESISTANCE_FACTOR - 1)
						/ (arrowVelocity * (Math.pow(AIR_RESISTANCE_FACTOR, time) - 1))
		);
	}

	private static Vector3f predictArrowDirection(
			Vector3f enemyPosition,
			Vector3f playerHeadPosition,
			Vector3f enemyVelocity,
			double arrowVelocity) {
		for (int a = 1; a < 180; ++a) {
			Vector3f newLimit = getDirectionByTime(enemyPosition, playerHeadPosition, enemyVelocity, arrowVelocity, a);
			float newLimitLength = newLimit.Length();

			if (Math.abs(newLimitLength - 1) < 1E-1) {
				return newLimit;
			}

			// early escape if the length is already out of scope
			if (newLimitLength > 20 && a > 20) {
				break;
			}
		}

		return null;
	}

	@Override
	public void onEnable()
	{
		Client.getInstance().getRotationUtils().setPitch(mc.thePlayer.rotationPitch);
		Client.getInstance().getRotationUtils().setYaw(mc.thePlayer.rotationYaw);
		target = MiscUtils.getBowAimTarget(fov);
	}

	@Override
	public void onDisable()
	{
		Client.getInstance().getRotationUtils().setPitch(mc.thePlayer.rotationPitch);
		Client.getInstance().getRotationUtils().setYaw(mc.thePlayer.rotationYaw);
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
			/*
			double x = ent.posX - RenderManager.instance.viewerPosX;
			double y = ent.posY - RenderManager.instance.viewerPosY;
			double z = ent.posZ - RenderManager.instance.viewerPosZ;
			GL11.glLineWidth(2.5F);

			if(ok)
				GL11.glColor3f(0.0F, 1.0F, 0.0F);
			else
				GL11.glColor3f(1.0F, 0.0F, 0.0F);

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
			*/
			double x = ent.posX - RenderManager.instance.viewerPosX;
			double y = ent.posY - RenderManager.instance.viewerPosY;
			double z = ent.posZ - RenderManager.instance.viewerPosZ;

			if(ok) {
				R3DUtils.drawEntityESP4(x, y, z, ent, 0f, 1.0f, 0f, 1F, 2F);
			}
			else {
				R3DUtils.drawEntityESP4(x, y, z, ent, 1.0f, 0f, 0f, 1F, 2F);
			}
		}
	}
}
