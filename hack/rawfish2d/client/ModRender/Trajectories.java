package hack.rawfish2d.client.ModRender;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemEgg;
import net.minecraft.src.ItemEnderPearl;
import net.minecraft.src.ItemFishingRod;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemSnowball;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Packet3Chat;

public class Trajectories extends Module {
	private boolean release;
	public int Tempo;
	
	public static BoolValue newtrajectories;
	public static CheckBox newmodebox;	
	
	public Trajectories() {
		super("Trajectories", 0, ModuleType.RENDER); //TrajectoriesNifee
		setDescription("Показывает траектории полёта чего либо");
		this.Tempo = 0;
		
		newtrajectories = new BoolValue(false);
		elements.add(newmodebox = new CheckBox(this, "New Trajectories", newtrajectories, 0, 0));
	}
	
	@Override
	public void onRenderHand() {
		if(newtrajectories.getValue())
			drawTrajectory();
		else
			drawTrajectory2();
	}
	
	private void renderPoint() {
		GL11.glBegin(1);
		GL11.glVertex3d(-0.4, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, -0.4);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(0.4, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 0.4);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glEnd();
		Cylinder cylinder = new Cylinder();
		GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
		cylinder.setDrawStyle(100011);
		//cylinder.draw(0.4f, 0.4f, 0.1f, 24, 1);
		cylinder.draw(0.4f, 0.4f, 0.01f, 24, 1);
	}
	
	public void spara() {
		if (Tempo == 4) {
			mc.gameSettings.keyBindUseItem.pressed = false;
			Tempo = 0;
		}
	}
	
	public static boolean isInMaterial(AxisAlignedBB var0, Material var1) {
		boolean inMat = false;
		int floor_d1 = MathHelper.floor_double(var0.minX);
		int floor_d2 = MathHelper.floor_double(var0.maxX + 1.0);
		int floor_d3 = MathHelper.floor_double(var0.minY);
		int floor_d4 = MathHelper.floor_double(var0.maxY + 1.0);
		int floor_d5 = MathHelper.floor_double(var0.minZ);
		int floor_d6 = MathHelper.floor_double(var0.maxZ + 1.0);
		
		if (!mc.theWorld.checkChunksExist(floor_d1, floor_d3, floor_d5, floor_d2, floor_d4, floor_d6)) {
			return false;
		}
		for (int var9 = floor_d1; var9 < floor_d2; ++var9) {
			for (int var10 = floor_d3; var10 < floor_d4; ++var10) {
				for (int var11 = floor_d5; var11 < floor_d6; ++var11) {
					Block[] blocksList = Block.blocksList;
					
					Block var12 = blocksList[mc.theWorld.getBlockId(var9, var10, var11)];
					if (var12 != null && var12.blockMaterial == var1) {
						float n = var10 + 1;
						
						double var13 = n - BlockFluid.getFluidHeightPercent(mc.theWorld.getBlockMetadata(var9, var10, var11));
						if (floor_d6 >= var13) {
							inMat = true;
						}
					}
				}
			}
		}
		return inMat;
	}
	
	public static List getEntitiesWithinAABB(AxisAlignedBB var0) {
		ArrayList var = new ArrayList();
		int var2 = MathHelper.floor_double((var0.minX - 2.0) / 16.0);
		int var3 = MathHelper.floor_double((var0.maxX + 2.0) / 16.0);
		int var4 = MathHelper.floor_double((var0.minZ - 2.0) / 16.0);
		int var5 = MathHelper.floor_double((var0.maxZ + 2.0) / 16.0);
		for (int var6 = var2; var6 <= var3; ++var6) {
			for (int var7 = var4; var7 <= var5; ++var7) {
				
				if (mc.theWorld.chunkExists(var6, var7)) {
					Chunk chunkFromChunkCoords = mc.theWorld.getChunkFromChunkCoords(var6, var7);
					chunkFromChunkCoords.getEntitiesWithinAABBForEntity(mc.thePlayer, var0, var, null);
				}
			}
		}
		return var;
	}
	
	private void drawTrajectory() {
		EntityPlayer player = mc.thePlayer;
		
		// check if player is holding item
		if(player.inventory.getCurrentItem() == null)
			return;
		Item item = player.inventory.getCurrentItem().getItem();
		
		if (player.getItemInUseCount() <= 0) {
			return;
		}
		
		// check if item is throwable
		if (!(item instanceof ItemBow) && !(item instanceof ItemSnowball) && !(item instanceof ItemEnderPearl) && !(item instanceof ItemEgg)) {
			return;
		}
		
		boolean usingBow = item instanceof ItemBow;
		
		// calculate starting position
		double arrowPosX = player.lastTickPosX
			+ (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks
			- MathHelper.cos((float)Math.toRadians(player.rotationYaw)) * 0.16F;
		double arrowPosY = player.lastTickPosY
			+ (player.posY - player.lastTickPosY)
				* mc.timer.renderPartialTicks
			+ player.getEyeHeight() - 0.1;
		double arrowPosZ = player.lastTickPosZ
			+ (player.posZ - player.lastTickPosZ)
				* mc.getMinecraft().timer.renderPartialTicks
			- MathHelper.sin((float)Math.toRadians(player.rotationYaw)) * 0.16F;
		
		// calculate starting motion
		float arrowMotionFactor = usingBow ? 1F : 0.4F;
		float yaw = (float)Math.toRadians(player.rotationYaw);
		float pitch = (float)Math.toRadians(player.rotationPitch);
		float arrowMotionX =
			-MathHelper.sin(yaw) * MathHelper.cos(pitch) * arrowMotionFactor;
		float arrowMotionY = -MathHelper.sin(pitch) * arrowMotionFactor;
		float arrowMotionZ =
				MathHelper.cos(yaw) * MathHelper.cos(pitch) * arrowMotionFactor;
		double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX
			+ arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
		arrowMotionX /= arrowMotion;
		arrowMotionY /= arrowMotion;
		arrowMotionZ /= arrowMotion;
		if(usingBow)
		{
			float bowPower = (72000 - player.getItemInUseCount()) / 20F;
			bowPower = (bowPower * bowPower + bowPower * 2F) / 3F;
			
			if(bowPower > 1F || bowPower <= 0.1F)
				bowPower = 1F;
			
			bowPower *= 3F;
			arrowMotionX *= bowPower;
			arrowMotionY *= bowPower;
			arrowMotionZ *= bowPower;
			
		}else
		{
			arrowMotionX *= 1.5D;
			arrowMotionY *= 1.5D;
			arrowMotionZ *= 1.5D;
		}
		
		// GL settings
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);
		
		RenderManager renderManager = RenderManager.instance;
		
		// draw trajectory line
		double gravity =
			usingBow ? 0.05D : item instanceof ItemPotion ? 0.4D
				: item instanceof ItemFishingRod ? 0.15D : 0.03D;
		Vec3 playerVector = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		
		GL11.glColor4f(0, 1, 0, 0.75F);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(int i = 0; i < 1000; i++)
		{
			GL11.glVertex3d(arrowPosX - renderManager.renderPosX,
				arrowPosY - renderManager.renderPosY,
				arrowPosZ - renderManager.renderPosZ);
			
			arrowPosX += arrowMotionX * 0.1;
			arrowPosY += arrowMotionY * 0.1;
			arrowPosZ += arrowMotionZ * 0.1;
			arrowMotionX *= 0.999D;
			arrowMotionY *= 0.999D;
			arrowMotionZ *= 0.999D;
			arrowMotionY -= gravity * 0.1;
			
			if(mc.theWorld.rayTraceBlocks(playerVector,
				new Vec3(arrowPosX, arrowPosY, arrowPosZ)) != null)
				break;
		}
		GL11.glEnd();
		
		// draw end of trajectory line
		double renderX = arrowPosX - renderManager.renderPosX;
		double renderY = arrowPosY - renderManager.renderPosY;
		double renderZ = arrowPosZ - renderManager.renderPosZ;
		
		GL11.glPushMatrix();
		GL11.glTranslated(renderX - 0.5, renderY - 0.5, renderZ - 0.5);
		
		GL11.glColor4f(0F, 1F, 0F, 0.25F);
		//RenderUtils.drawSolidBox();
		GL11.glColor4f(0, 1, 0, 0.75F);
		//RenderUtils.drawOutlinedBox();
		
		GL11.glPopMatrix();
		
		// GL resets
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}
	
	private void drawTrajectory2() {
		try {
			boolean var1 = false;
			EntityClientPlayerMP thePlayer = mc.thePlayer;
			if (thePlayer.getCurrentEquippedItem() == null) {
				return;
			}
			Item var3 = thePlayer.getCurrentEquippedItem().getItem();
			if (!(var3 instanceof ItemBow) && !(var3 instanceof ItemSnowball) && !(var3 instanceof ItemEnderPearl) && !(var3 instanceof ItemEgg)) {
				return;
			}
			
			int var12 = 72000 - thePlayer.getItemInUseCount();
			float var13 = var12 / 20.0f;
			var13 = (var13 * var13 + var13 * 2.0f) / 3.0f;
			
			if (var13 < 0.1) {
				return;
			}
			
			var1 = (var3 instanceof ItemBow);
			double var4 = RenderManager.renderPosX - MathHelper.cos(thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
			double var5 = RenderManager.renderPosY + thePlayer.getEyeHeight() - 0.10000000149011612;
			double var6 = RenderManager.renderPosZ - MathHelper.sin(thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
			double var7 = -MathHelper.sin(thePlayer.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(thePlayer.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
			double var8 = -MathHelper.sin(thePlayer.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
			double var9 = MathHelper.cos(thePlayer.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(thePlayer.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
			boolean b = false;
			
			while(true) {
				if (!mc.theWorld.extendedLevelsInChunkCache()) {
					if (mc.theWorld.blockExists((int)var4, (int)var5, (int)var6)) {
						if (mc.thePlayer.getDistance(var4, var5, var6) <= 255.0) {
							b = false;
							break;
						}
					}
				}
				b = true;
			}
			
			boolean var10 = b;
			if (var10) {
				return;
			}
			
			float var11 = MathHelper.sqrt_double(var7 * var7 + var8 * var8 + var9 * var9);
			if (thePlayer.getItemInUseCount() <= 0 && var1) {
				return;
			}
			/*
			int var12 = 72000 - thePlayer.getItemInUseCount();
			float var13 = var12 / 20.0f;
			var13 = (var13 * var13 + var13 * 2.0f) / 3.0f;
			
			if (var13 < 0.1) {
				return;
			}
			*/
			if (var13 > 1.0f) {
				var13 = 1.0f;
			}
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			//GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glColor3f(1.0f - var13, var13, 0.0f);
			
			var7 /= var11;
			var8 /= var11;
			var9 /= var11;
			var7 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
			var8 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
			var9 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
			
			GL11.glLineWidth(1.0f);
			GL11.glBegin(3);
			
			boolean var14 = false;
			boolean var15 = false;
			MovingObjectPosition var16 = null;
			float var17 = var1 ? 0.3f : 0.25f;
			
			while (!var14 && !var10 && var5 > 0.0) {
				Vec3 var18 = mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
				Vec3 var19 = mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var7, var5 + var8, var6 + var9);
				
				MovingObjectPosition var20 = mc.theWorld.rayTraceBlocks_do_do(var18, var19, false, true);
				
				var18 = mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
				var19 = mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var7, var5 + var8, var6 + var9);
				
				if (var20 != null) {
					var14 = true;
					var16 = var20;
				}
				
				AxisAlignedBB var21 = new AxisAlignedBB(var4 - var17, var5 - var17, var6 - var17, var4 + var17, var5 + var17, var6 + var17);
				List var22 = getEntitiesWithinAABB(var21.addCoord(var7, var8, var9).expand(1.0, 1.0, 1.0));
				
				for (int var23 = 0; var23 < var22.size(); ++var23) {
					Entity var24 = (Entity) var22.get(var23);
					
					if (var24.canBeCollidedWith() && var24 != thePlayer) {
						AxisAlignedBB var25 = var24.boundingBox.expand(0.3, 0.3, 0.3);
						MovingObjectPosition calculateIntercept = var25.calculateIntercept(var18, var19);
						
						if (calculateIntercept != null) {
							var14 = true;
							var15 = true;
							var16 = calculateIntercept;
						}
					}
				}
				
				var4 += var7;
				var5 += var8;
				var6 += var9;
				float var27 = 0.99f;
				AxisAlignedBB var28 = new AxisAlignedBB(var4 - var17, var5 - var17, var6 - var17, var4 + var17, var5 + var17, var6 + var17);
				
				if (isInMaterial(var28.expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water)) {
					var27 = 0.8f;
				}
				
				var7 *= var27;
				var8 *= var27;
				var9 *= var27;
				var8 -= (var1 ? 0.05 : 0.03);
				GL11.glVertex3d(var4 - RenderManager.renderPosX, var5 - RenderManager.renderPosY, var6 - RenderManager.renderPosZ);
			}
				
			GL11.glEnd();
			GL11.glPushMatrix();
			GL11.glTranslated(var4 - RenderManager.renderPosX, var5 - RenderManager.renderPosY, var6 - RenderManager.renderPosZ);
			
			if (var16 != null) {
				switch (var16.sideHit) {
					case 2: {
						GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
						break;
					}
					case 3: {
						GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
						break;
					}
					case 4: {
						GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
						break;
					}
					case 5: {
						GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
						break;
					}
				}
				
				if (var15) {
					//GL11.glColor3f(1.0f, 0.0f, 0.0f);
					++this.Tempo;
					
					if (this.release) {
						this.spara();
					}
				}
			}
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			//GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			this.renderPoint();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
			//GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		}
		catch (Exception ex) { }
	}
}
