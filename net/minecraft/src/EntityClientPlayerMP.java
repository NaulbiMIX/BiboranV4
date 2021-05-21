package net.minecraft.src;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.Freecam;
import hack.rawfish2d.client.ModMovement.Blink;
import hack.rawfish2d.client.ModTest.SlowHit;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP
{
	public NetClientHandler sendQueue;
	private double oldPosX;
	
	/** Old Minimum Y of the bounding box */
	private double oldMinY;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	public float oldRotationPitch;
	public static EntityOtherPlayerMP fPlayer;
	//public static boolean blink; //some nifee stuff

	/** Check if was on ground last update */
	private boolean wasOnGround = false;

	/** should the player stop sneaking? */
	private boolean shouldStopSneaking = false;
	private boolean wasSneaking = false;
	private int field_71168_co = 0;

	/** has the client player's health been set? */
	private boolean hasSetHealth = false;

	public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler)
	{
		super(par1Minecraft, par2World, par3Session, 0);
		this.sendQueue = par4NetClientHandler;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public void moveEntity(double motionX, double motionY, double motionZ) {
		/*
		EventMovement event = (EventMovement) Client.getEventManager().call(new EventMovement(this, motionX, motionY, motionZ));
		if(event.isCancelled())
			return;
		else {
			motionX = event.getMotionX();
			motionY = event.getMotionY();
			motionZ = event.getMotionZ();
		}
		*/
		super.moveEntity(motionX, motionY, motionZ);
	}
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return false;
	}

	/**
	 * Heal living entity (param: amount of half-hearts)
	 */
	public void heal(int par1) {}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
		{
			//Client.getHookManager().onPreUpdate();
			//ultrakek
			super.onUpdate();
			this.sendMotionUpdates();
		}
	}

	/**
	 * Send updated motion and position information to the server
	 */
	public void sendMotionUpdates()
	{
		boolean var1 = this.isSprinting();
		//ultrakek
		Client.getInstance().getRotationUtils().preUpdate();
		Client.getInstance().preMotionUpdate();
		//Client.getHookManager().onPreMotionUpdate();
		//EventMovement event = (EventMovement) Client.getEventManager().call(new EventMovement(this, EventMovement.EventType.PRE_UPDATE));
		
		//ultrakek
		if (!Freecam.instance.isToggled())
		{
			//if(event.isCancelled())
			//	return;

			if (var1 != this.wasSneaking)
			{
				if (var1)
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
				}
				else
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
				}

				this.wasSneaking = var1;
			}

			boolean var2 = this.isSneaking();

			if (var2 != this.shouldStopSneaking)
			{
				if (var2)
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
				}
				else
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
				}

				this.shouldStopSneaking = var2;
			}

			double var3 = this.posX - this.oldPosX;
			double var5 = this.boundingBox.minY - this.oldMinY;
			double var7 = this.posZ - this.oldPosZ;
			double var9 = (double)(Client.getInstance().getRotationUtils().getYaw() - Client.getInstance().getRotationUtils().getOldYaw());
			double var11 = (double)(Client.getInstance().getRotationUtils().getPitch() - Client.getInstance().getRotationUtils().getOldPitch());
			boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
			boolean var14 = var9 != 0.0D || var11 != 0.0D;
			
			boolean sendMotion = true;
			
			if(SlowHit.instance != null) {
				sendMotion = ((SlowHit)SlowHit.instance).shouldSendMotion();
			}
			
			if(sendMotion) {
				if (this.ridingEntity != null)
				{
					this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0, -999.0, this.motionZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), this.onGround));
		            var13 = false;
				}
				else if (var13 && var14)
				{
					//this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, Client.getRotationUtils().getYaw(), Client.getRotationUtils().getPitch(), this.onGround));
					this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY + this.bbyoffset, this.posY + this.bbyoffset, this.posZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), this.onGround));
				}
				else if (var13)
				{
					//this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
					this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY + this.bbyoffset, this.posY + this.bbyoffset, this.posZ, this.onGround));
				}
				else if (var14)
				{
					//this.sendQueue.addToSendQueue(new Packet12PlayerLook(Client.getRotationUtils().getYaw(), Client.getRotationUtils().getPitch(), this.onGround));
					this.sendQueue.addToSendQueue(new Packet12PlayerLook(Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), this.onGround));
				}
				else
				{
					this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
				}
				/*
				((SlowHit)SlowHit.instance).timer.reset();
				((SlowHit)SlowHit.instance).flag = !((SlowHit)SlowHit.instance).flag; //new
				*/
			}
			
			++this.field_71168_co;
			this.wasOnGround = this.onGround;

			if (var13)
			{
				this.oldPosX = this.posX;
				this.oldMinY = this.boundingBox.minY;
				this.oldPosY = this.posY;
				this.oldPosZ = this.posZ;
				this.field_71168_co = 0;
			}

			if (var14)
			{
				Client.getInstance().getRotationUtils().postUpdate();
			}
			
			//ultrakek
			//Client.getEventManager().call(new EventMovement(this, EventMovement.EventType.POST_UPDATE));
			Client.getInstance().postMotionUpdate();
			//Client.getHookManager().onPostMotionUpdate();
		}
	}

	/**
	 * Called when player presses the drop item key
	 */
	public EntityItem dropOneItem(boolean par1)
	{
		int var2 = par1 ? 3 : 4;
		this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
		return null;
	}

	protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}
	
	public void sendChatMessage(String par1Str)
	{
		Client.getInstance().onSendChat(par1Str);
	}
	
	public void swingItem()
	{
		super.swingItem();
		this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
	}

	public void respawnPlayer()
	{
		this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
	}

	/**
	 * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
	 * second with the reduced value. Args: damageAmount
	 */
	protected void damageEntity(DamageSource par1DamageSource, int par2)
	{
		if (!this.isEntityInvulnerable()) {
			this.setEntityHealth(this.getHealth() - par2);
		}
	}

	/**
	 * sets current screen to null (used on escape buttons of GUIs)
	 */
	public void closeScreen()
	{
		this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
		this.func_92015_f();
	}

	public void func_92015_f()
	{
		this.inventory.setItemStack((ItemStack)null);
		super.closeScreen();
	}

	/**
	 * Updates health locally.
	 */
	public void setHealth(int par1)
	{
		if (this.hasSetHealth)
		{
			super.setHealth(par1);
		}
		else
		{
			this.setEntityHealth(par1);
			this.hasSetHealth = true;
		}
	}

	/**
	 * Adds a value to a statistic field.
	 */
	public void addStat(StatBase par1StatBase, int par2)
	{
		if (par1StatBase != null && par1StatBase.isIndependent)
		{
			super.addStat(par1StatBase, par2);
		}
	}

	/**
	 * Used by NetClientHandler.handleStatistic
	 */
	public void incrementStat(StatBase par1StatBase, int par2)
	{
		if (par1StatBase != null && !par1StatBase.isIndependent)
		{
			super.addStat(par1StatBase, par2);
		}
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	public void sendPlayerAbilities()
	{
		this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
	}

	public boolean func_71066_bF()
	{
		return true;
	}
}
