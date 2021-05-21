package net.minecraft.src;

import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModRender.GlowESP;
import hack.rawfish2d.client.ModRender.Nametags;
import hack.rawfish2d.client.ModTest.Spectate;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.OutlineUtils;

public class RenderLiving extends Render
{
	private static RenderItem itemRenderer = new RenderItem();
	protected ModelBase mainModel;

	/** The model to be used during the render passes. */
	protected ModelBase renderPassModel;

	public RenderLiving(ModelBase par1ModelBase, float par2)
	{
		this.mainModel = par1ModelBase;
		this.shadowSize = par2;
	}

	/**
	 * Sets the model to be used in the current render pass (the first render pass is done after the primary model is
	 * rendered) Args: model
	 */
	public void setRenderPassModel(ModelBase par1ModelBase)
	{
		this.renderPassModel = par1ModelBase;
	}

	/**
	 * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
	 * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
	 * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
	 */
	private float interpolateRotation(float par1, float par2, float par3)
	{
		float var4;

		for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
		{
			;
		}

		while (var4 >= 180.0F)
		{
			var4 -= 360.0F;
		}

		return par1 + par3 * var4;
	}

	public void doRenderLiving(EntityLiving ent, double par2, double par4, double par6, float par8, float par9)
	{
		/*
		if(Spectate.instance.isToggled()) {
			EntityPlayer e = Spectate.getEntityWithName(Spectate.listbox.getValue());
			if(e != null) {
				if(ent instanceof EntityPlayer) {
					EntityPlayer e2 = (EntityPlayer)ent;
					if(e2.username.equalsIgnoreCase(e.username)) {
						return;
					}
				}
			}
		}
		*/
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.mainModel.onGround = this.renderSwingProgress(ent, par9);

		if (this.renderPassModel != null)
		{
			this.renderPassModel.onGround = this.mainModel.onGround;
		}

		this.mainModel.isRiding = ent.isRiding();

		if (this.renderPassModel != null)
		{
			this.renderPassModel.isRiding = this.mainModel.isRiding;
		}

		this.mainModel.isChild = ent.isChild();

		if (this.renderPassModel != null)
		{
			this.renderPassModel.isChild = this.mainModel.isChild;
		}

		try
		{
			float var10 = this.interpolateRotation(ent.prevRenderYawOffset, ent.renderYawOffset, par9);
			float var11 = this.interpolateRotation(ent.prevRotationYawHead, ent.rotationYawHead, par9);
			float var12 = ent.prevRotationPitch + (ent.rotationPitch - ent.prevRotationPitch) * par9;
			this.renderLivingAt(ent, par2, par4, par6);
			float var13 = this.handleRotationFloat(ent, par9);
			this.rotateCorpse(ent, var13, var10, par9);
			float var14 = 0.0625F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.preRenderCallback(ent, par9);
			GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
			float var15 = ent.prevLimbYaw + (ent.limbYaw - ent.prevLimbYaw) * par9;
			float var16 = ent.limbSwing - ent.limbYaw * (1.0F - par9);
			if (ent.isChild())
			{
				var16 *= 3.0F;
			}

			if (var15 > 1.0F)
			{
				var15 = 1.0F;
			}

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			float var17 = ent.getBrightness(par9);
			int var18 = this.getColorMultiplier(ent, var17, par9);

			{
				int var19 = ent.hurtTime <= 0 && ent.deathTime <= 0 ? 0 : 102;
			}
			
			//=======================
			
			try{
				if (GlowESP.instance != null) {
					if ( GlowESP.instance.isToggled() && ent instanceof EntityPlayer) {
						
						
						this.mainModel.setLivingAnimations(ent, var16, var15, par9);
						
						//ok
						GL11.glPushMatrix();
						GL11.glDepthMask(true);
						GL11.glDisable(GL11.GL_DEPTH_TEST); //inverse render priority
						
						OutlineUtils.renderOne();
						
						if(MiscUtils.isEntityVisable(ent)) {
							//OutlineUtils.setColor(Color.GREEN);
							Color c = new Color((float)GlowESP.c_vis_r.getValue(), (float)GlowESP.c_vis_g.getValue(), (float)GlowESP.c_vis_b.getValue(), (float)GlowESP.c_vis_a.getValue());
							OutlineUtils.setColor(c);
						}
						else {
							Color c = new Color((float)GlowESP.c_notvis_r.getValue(), (float)GlowESP.c_notvis_g.getValue(), (float)GlowESP.c_notvis_b.getValue(), (float)GlowESP.c_notvis_a.getValue());
							OutlineUtils.setColor(c);
							//OutlineUtils.setColor(Color.RED);
						}
						
						//glow esp
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
						
						//without it its awful
						OutlineUtils.renderTwo();
						
						//without it its awful
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
						
						//glow esp
						OutlineUtils.renderThree();
						
						//glow esp
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
						
						//DO NOT REMOVE THIS
						OutlineUtils.renderFive();
						
						//good wallhack glowesp
						GL11.glEnable(GL11.GL_DEPTH_TEST); //inverse render priority
						
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
						GL11.glPopMatrix();
						//ok
						
						//GL11.glDepthFunc(GL11.GL_NEVER);  //block > model ? armor > glow
						//GL11.glDepthFunc(GL11.GL_LESS); //block > model ? armor > glow
						//GL11.glDepthFunc(GL11.GL_EQUAL); //block > model ? armor > glow
						//GL11.glDepthFunc(GL11.GL_LEQUAL); // <<-  //block > model > armor > glow
						//GL11.glDepthFunc(GL11.GL_GREATER); //block > model > glow > armor
						//GL11.glDepthFunc(GL11.GL_NOTEQUAL); //block > glow > armor > model
						//GL11.glDepthFunc(GL11.GL_GEQUAL); //block > glow > model > armor
						//GL11.glDepthFunc(GL11.GL_ALWAYS); //block > glow > armor > model
						
						this.mainModel.setLivingAnimations(ent, var16, var15, par9);
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
					}
					else {
						this.mainModel.setLivingAnimations(ent, var16, var15, par9);
						this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
					}
				}
				else {
					this.mainModel.setLivingAnimations(ent, var16, var15, par9);
					this.renderModel(ent, var16, var15, var13, var11 - var10, var12, var14);
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			//=======================
			
			float var21;
			float var22;
			int var24;
			float var28;

			for (int var23 = 0; var23 < 4; ++var23)
			{
				int var20 = this.shouldRenderPass(ent, var23, par9);

				if (var20 > 0)
				{
					//ultrakek
					//this.renderPassModel.setLivingAnimations(ent, var16, var15, par9);
					//this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
					float f2 = var11 - var10;
					//GL11.glEnable(GL11.GL_ALPHA_TEST);
					this.renderPassModel.setLivingAnimations(ent, var16, var15, par9);
					this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
					
					if ((var20 & 240) == 16)
					{
						this.func_82408_c(ent, var23, par9);
						this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
					}

					if ((var20 & 15) == 15)
					{
						var28 = (float)ent.ticksExisted + par9;
						this.loadTexture("%blur%/misc/glint.png");
						GL11.glEnable(GL11.GL_BLEND);
						var21 = 0.5F;
						GL11.glColor4f(var21, var21, var21, 1.0F);
						GL11.glDepthFunc(GL11.GL_EQUAL);
						GL11.glDepthMask(false);

						for (var24 = 0; var24 < 2; ++var24)
						{
							GL11.glDisable(GL11.GL_LIGHTING);
							var22 = 0.76F;
							GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
							GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
							GL11.glMatrixMode(GL11.GL_TEXTURE);
							GL11.glLoadIdentity();
							float var25 = var28 * (0.001F + (float)var24 * 0.003F) * 20.0F;
							float var26 = 0.33333334F;
							GL11.glScalef(var26, var26, var26);
							GL11.glRotatef(30.0F - (float)var24 * 60.0F, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(0.0F, var25, 0.0F);
							GL11.glMatrixMode(GL11.GL_MODELVIEW);
							this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
						}

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glMatrixMode(GL11.GL_TEXTURE);
						GL11.glDepthMask(true);
						GL11.glLoadIdentity();
						GL11.glMatrixMode(GL11.GL_MODELVIEW);
						GL11.glEnable(GL11.GL_LIGHTING);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glDepthFunc(GL11.GL_LEQUAL);
					}

					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
			}

			GL11.glDepthMask(true);

			this.renderEquippedItems(ent, par9);

			{
				float var29 = var17;
				OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

				if ((var18 >> 24 & 255) > 0 || ent.hurtTime > 0 || ent.deathTime > 0)
				{
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_ALPHA_TEST);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDepthFunc(GL11.GL_EQUAL);

					if (ent.hurtTime > 0 || ent.deathTime > 0)
					{
						GL11.glColor4f(var17, 0.0F, 0.0F, 0.4F);
						this.mainModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);

						for (var24 = 0; var24 < 4; ++var24)
						{
							if (this.inheritRenderPass(ent, var24, par9) >= 0)
							{
								GL11.glColor4f(var29, 0.0F, 0.0F, 0.4F);
								this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
							}
						}
					}

					if ((var18 >> 24 & 255) > 0)
					{
						var28 = (float)(var18 >> 16 & 255) / 255.0F;
						var21 = (float)(var18 >> 8 & 255) / 255.0F;
						float var30 = (float)(var18 & 255) / 255.0F;
						var22 = (float)(var18 >> 24 & 255) / 255.0F;
						GL11.glColor4f(var28, var21, var30, var22);
						this.mainModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);

						for (int var31 = 0; var31 < 4; ++var31)
						{
							if (this.inheritRenderPass(ent, var31, par9) >= 0)
							{
								GL11.glColor4f(var28, var21, var30, var22);
								this.renderPassModel.render(ent, var16, var15, var13, var11 - var10, var12, var14);
							}
						}
					}

					GL11.glDepthFunc(GL11.GL_LEQUAL);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_ALPHA_TEST);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		catch (Exception var27)
		{
			var27.printStackTrace();
		}

		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		this.passSpecialRender(ent, par2, par4, par6);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.func_98190_a(par1EntityLiving);
		if (!par1EntityLiving.isInvisible()) {
			this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
		}
		if (!par1EntityLiving.isInvisible())
		{
			this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
		}
		else if (!par1EntityLiving.func_98034_c(Minecraft.getMinecraft().thePlayer))
		{
			/*
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
			this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			*/
			/*
			//ultrakek
			if( GlowESP.instance.isToggled() ) {
				GL11.glPushMatrix();
				this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
				GL11.glPopMatrix();
				return;
			}
			*/
			//ultrakek
			//GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F); //orig
			
			GL11.glPushMatrix();
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
			this.mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glPopMatrix();
			GL11.glDepthMask(true);
		}
		else
		{
			this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLiving);
		}
	}

	protected void func_98190_a(EntityLiving par1EntityLiving)
	{
		this.loadTexture(par1EntityLiving.getTexture());
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(EntityLiving par1EntityLiving, double par2, double par4, double par6)
	{
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
	}

	protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);

		if (par1EntityLiving.deathTime > 0)
		{
			float var5 = ((float)par1EntityLiving.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
			var5 = MathHelper.sqrt_float(var5);

			if (var5 > 1.0F)
			{
				var5 = 1.0F;
			}

			GL11.glRotatef(var5 * this.getDeathMaxRotation(par1EntityLiving), 0.0F, 0.0F, 1.0F);
		}
	}

	protected float renderSwingProgress(EntityLiving par1EntityLiving, float par2)
	{
		return par1EntityLiving.getSwingProgress(par2);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
	{
		return (float)par1EntityLiving.ticksExisted + par2;
	}

	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {}

	/**
	 * renders arrows the Entity has been attacked with, attached to it
	 */
	protected void renderArrowsStuckInEntity(EntityLiving par1EntityLiving, float par2)
	{
		int var3 = par1EntityLiving.getArrowCountInEntity();

		if (var3 > 0)
		{
			EntityArrow var4 = new EntityArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
			Random var5 = new Random((long)par1EntityLiving.entityId);
			RenderHelper.disableStandardItemLighting();

			for (int var6 = 0; var6 < var3; ++var6)
			{
				GL11.glPushMatrix();
				ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
				ModelBox var8 = (ModelBox)var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
				var7.postRender(0.0625F);
				float var9 = var5.nextFloat();
				float var10 = var5.nextFloat();
				float var11 = var5.nextFloat();
				float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
				float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
				float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
				GL11.glTranslatef(var12, var13, var14);
				var9 = var9 * 2.0F - 1.0F;
				var10 = var10 * 2.0F - 1.0F;
				var11 = var11 * 2.0F - 1.0F;
				var9 *= -1.0F;
				var10 *= -1.0F;
				var11 *= -1.0F;
				float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
				var4.prevRotationYaw = var4.rotationYaw = (float)(Math.atan2((double)var9, (double)var11) * 180.0D / Math.PI);
				var4.prevRotationPitch = var4.rotationPitch = (float)(Math.atan2((double)var10, (double)var15) * 180.0D / Math.PI);
				double var16 = 0.0D;
				double var18 = 0.0D;
				double var20 = 0.0D;
				float var22 = 0.0F;
				this.renderManager.renderEntityWithPosYaw(var4, var16, var18, var20, var22, par2);
				GL11.glPopMatrix();
			}

			RenderHelper.enableStandardItemLighting();
		}
	}

	protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.shouldRenderPass(par1EntityLiving, par2, par3);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return -1;
	}

	protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3) {}

	protected float getDeathMaxRotation(EntityLiving par1EntityLiving)
	{
		return 90.0F;
	}

	/**
	 * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
	 */
	protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3)
	{
		return 0;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {}

	/**
	 * Passes the specialRender and renders it
	 */
	protected void passSpecialRender(EntityLiving par1EntityLiving,
		double par2, double par4, double par6) {
		if (Minecraft.isGuiEnabled() &&
				par1EntityLiving != this.renderManager.livingPlayer &&
				!par1EntityLiving.func_98034_c(Minecraft.getMinecraft().thePlayer) &&
				(par1EntityLiving.func_94059_bO() ||
				par1EntityLiving.func_94056_bM() &&
				par1EntityLiving == this.renderManager.field_96451_i)) {
			float var8 = 1.6F;
			float var9 = 0.016666668F * var8;
			double var10 = par1EntityLiving.getDistanceSqToEntity(this.renderManager.livingPlayer);
			//ultrakek
			float var12 = par1EntityLiving.isSneaking() ? 32.0F : 64.0F;

			if (var10 < var12 * var12) {
				String var13 = par1EntityLiving.getTranslatedEntityName();

				this.func_96449_a(par1EntityLiving, par2, par4, par6, var13, var9, var10);
			}
			else if(Nametags.instance.isToggled()) {
				String var13 = par1EntityLiving.getTranslatedEntityName();
				
				this.func_96449_a(par1EntityLiving, par2, par4, par6, var13, var9, 9D);
			}
		}
	}

	protected void func_96449_a(EntityLiving par1EntityLiving, double par2,
		double par4, double par6, String par8Str, float par9, double par10) {
		if (par1EntityLiving.isPlayerSleeping()) {
			this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4 - 1.5D, par6, 64);
		} else {
			this.renderLivingLabel(par1EntityLiving, par8Str, par2, par4, par6, 64);
		}
	}

	/**
	 * Draws the debug or playername text above a living
	 */
	protected void renderLivingLabel(EntityLiving par1EntityLiving,
		String par2Str, double par3, double par5, double par7, int par9) {
		//ultrakek
		if(Nametags.instance.isToggled())
			return;

		double var10 = par1EntityLiving.getDistanceToEntity(Minecraft.getMinecraft().renderViewEntity);
		GL11.glPushMatrix();
		if (var10 <= 256) {
	        double var4 = par1EntityLiving.posX - RenderManager.instance.viewerPosX;
	        double var6 = par1EntityLiving.posY - RenderManager.instance.viewerPosY;
	        double var8 = par1EntityLiving.posZ - RenderManager.instance.viewerPosZ;
			FontRenderer var12 = this.getFontRendererFromRenderManager();
			float var13 = 2.6F;
			//ultrakek
			//float var14 = (float) (0.016666668F * var13 * (Nametags.instance.isToggled() ? (par1EntityLiving.getDistanceToEntity(this.renderManager.livingPlayer) >= 10 ? (var10 / 10): 1)	: 1));
			float var14 = 0.026f;
			//GL11.glPushMatrix();
			
			/*
			var15.startDrawingQuads();
			int var17 = (var12.getStringWidth(par2Str) / 2) + 1;
			var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			var15.addVertex(-var17 - 1, -1 + var16, 0.0D);
			var15.addVertex(-var17 - 1, 9 + var16, 0.0D);
			var15.addVertex(var17 + 1, 9 + var16, 0.0D);
			var15.addVertex(var17 + 1, -1 + var16, 0.0D);
			var15.draw();
			*/
			
			if (par1EntityLiving != null && par1EntityLiving instanceof EntityPlayer) {
				if (var10 > 7.0D) {
					GL11.glTranslatef((float)var4 + 0.0F, (float)(var6 + 2.299999952316284D + var10 / 30.0D - 0.2D), (float)var8);
				}
				else {
					GL11.glTranslatef((float)var4 + 0.0F, (float)var6 + 2.3F, (float)var8);
				}

				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-var14, -var14, var14);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				//Tessellator var15 = Tessellator.instance;
				byte var16 = 0;

				if (par2Str.equals("deadmau5")) {
					var16 = -10;
				}
				
				GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				//GL11.glEnable(GL11.GL_DEPTH_TEST);
				int var17 = (var12.getStringWidth(par2Str) / 2) + 1;
				Gui.drawRect(-var17 -2, -3, var17 + 2, 11, 0x80000000);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				
				//ultrakek
				var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, var16, 553648127);
				//GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(true);
				var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2,var16, - 1);
				//ultrakek
				
				//GL11.glEnable(GL11.GL_BLEND);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				//GL11.glPopMatrix();
			}
			else {
				if (var10 > 7.0D) {
					GL11.glTranslatef((float)var4 + 0.0F, (float)(var6 + 1.299999952316284D + var10 / 30.0D - 0.2D), (float)var8);
				}
				else {
					GL11.glTranslatef((float)var4 + 0.0F, (float)var6 + 1.3F, (float)var8);
				}

				GL11.glNormal3f(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(-var14, -var14, var14);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				//Tessellator var15 = Tessellator.instance;
				
				GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				int var17 = (var12.getStringWidth(par2Str) / 2) + 1;
				Gui.drawRect(-var17 -2, -3, var17 + 2, 11, 0x80000000);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
				
				var12.drawString(par2Str, -var12.getStringWidth(par2Str) / 2, 0, - 1);
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
	}
}
