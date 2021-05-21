package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.AutoBlock;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModMisc.FastUse;
import hack.rawfish2d.client.ModMisc.Scaffold;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemSword;
import net.minecraft.src.MovementInput;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class Speed_DD_B1 extends Module {
	public static Module instance = null;
	public int ivar;
	public boolean bvar;
	private double dvar;
	private float flvar;

	private BoolValue speed_on_ground1 = new BoolValue(true);
	private BoolValue speed_on_ground2 = new BoolValue(false);
	private BoolValue speed_speed = new BoolValue(false);
	private BoolValue speed_new = new BoolValue(false);
	private BoolValue speed_old = new BoolValue(false);
	
	public Speed_DD_B1() {
		super("Speed", 0, ModuleType.MOVEMENT);
		setDescription("Спидхаки из DoomsDay");
		instance = this;
		
		elements.add(new RadioBox(this, "SpeedOnGround1", speed_on_ground1, 0, 0));
		elements.add(new RadioBox(this, "SpeedOnGround2", speed_on_ground2, 0, 10));
		elements.add(new RadioBox(this, "Speed-Speed", speed_speed, 0, 20));
		elements.add(new RadioBox(this, "Speed-New", speed_new, 0, 30));
		elements.add(new RadioBox(this, "Speed-Old", speed_old, 0, 40));
	}
	
	@Override
	public void onEnable() {
		ivar = 0;
		bvar = false;
		dvar = 0.0D;
		flvar = 0.0F;
	}
	
	@Override
	public void onDisable() {
		/*
		//speed old
		mc.timer.timerSpeed = 1.0f;
		Block.ice.slipperiness = 0.89f;
		ivar = 0;
		
		//speed new
		this.mc.thePlayer.stepHeight = 0.5f;
		this.bvar = true;
		this.mc.thePlayer.some_entity_var = 0.0;
		Block.ice.slipperiness = 1.0f;
		
		//SpeedOnGround2
		Block.ice.slipperiness = 0.89f;
		//Sprint2.dvar1 = 1.0;
		this.mc.thePlayer.bbyoffset = 0.0;
		this.dvar = 0.2;
		this.mc.thePlayer.stepHeight = 0.5f;
		this.ivar = 0;
		this.bvar = false;
		
		//SpeedOnGround1
		this.mc.thePlayer.stepHeight = 0.5f;
		this.bvar = true;
		this.mc.thePlayer.bbyoffset = 0.0;
		Block.ice.slipperiness = 0.89f;
		*/
		
		mc.timer.timerSpeed = 1.0f;
		mc.thePlayer.stepHeight = 0.5f;
		mc.thePlayer.some_entity_var = 0.0;
		mc.thePlayer.bbyoffset = 0.0;
		
		this.bvar = true;
		this.ivar = 0;
		this.dvar = 0.2D;
		Block.ice.slipperiness = 0.98f;
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void preMotionUpdate() {
		if(this.speed_on_ground1.getValue()) {
			this.speed_sog1();
		}
		else if(this.speed_on_ground2.getValue()) {
			this.speed_sog2();
		}
		else if(this.speed_speed.getValue()) {
			this.speed_speed();
		}
		else if(this.speed_new.getValue()) {
			this.speed_new();
		}
		else if(this.speed_old.getValue()) {
			this.speed_old();
		}
		//speed_speed();
	}
	
	//SpeedOnGround2
	public void speed_sog2() {
		Block.ice.slipperiness = 0.4f;
		boolean b = false;
		boolean b2 = false;
		if (this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
			b = (this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0);
			b2 = (this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() >= 1);
		}
		final MovementInput movementInput = this.mc.thePlayer.movementInput;
		float moveForward = movementInput.moveForward;
		float moveStrafe = movementInput.moveStrafe;
		float rotationYaw = this.mc.thePlayer.rotationYaw;
		if (moveForward == 0.0f && moveStrafe == 0.0f) {
			this.mc.thePlayer.bbyoffset = 0.0;
			this.mc.thePlayer.motionX = 0.0;
			this.mc.thePlayer.motionZ = 0.0;
			this.ivar = 0;
			this.bvar = false;
			this.dvar = 0.1;
			return;
		}
		if (moveForward != 0.0f) {
			if (moveStrafe >= 1.0f) {
				rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
				moveStrafe = 0.0f;
			}
			else if (moveStrafe <= -1.0f) {
				rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
				moveStrafe = 0.0f;
			}
			if (moveForward > 0.0f) {
				moveForward = 1.0f;
			}
			else if (moveForward < 0.0f) {
				moveForward = -1.0f;
			}
		}
		if (sog2_sub()) {
			this.mc.thePlayer.motionY = -0.42;
			++this.ivar;
			//Sprint2.dvar1 = 0.0;
			if (this.ivar < 4) {
				this.dvar += 0.01;
			}
			else {
				this.bvar = !this.bvar;
				if (this.bvar) {
					this.dvar = 0.704899645218 * (b ? 1.2 : (b2 ? 1.4 : 1.0));
					this.mc.thePlayer.bbyoffset = 0.42;
					this.mc.thePlayer.stepHeight = 0.5f;
				}
				else {
					this.mc.thePlayer.stepHeight = 0.0f;
					this.dvar = 1.515534237218 * (b ? 1.2 : (b2 ? 1.4 : 1.0));
					this.mc.thePlayer.bbyoffset = 0.0;
				}
			}
			final double cos = Math.cos(Math.toRadians(rotationYaw + 90.0f));
			final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0f));
			this.mc.thePlayer.motionX = moveForward * this.dvar * cos + moveStrafe * this.dvar * sin;
			this.mc.thePlayer.motionZ = moveForward * this.dvar * sin - moveStrafe * this.dvar * cos;
		}
		else {
			this.bvar = false;
			this.mc.thePlayer.bbyoffset = 0.0;
			this.ivar = 0;
			this.dvar = 0.25;
			//Sprint2.dvar1 = 1.0;
			this.mc.thePlayer.stepHeight = 0.5f;
		}
	}
	
	public void speed_sog1() {
		if (this.mc.thePlayer.fallDistance > 1.0) {
			return;
		}
		Block.ice.slipperiness = 0.4f;
		if (sog2_sub()) {
			this.mc.thePlayer.distanceWalkedModified = 0.0f;
			//if (!ModuleUtils.getModuleByClass(Scaffold.class).isToggled()) {
				this.mc.thePlayer.motionY = -4.0;
			//}
			this.bvar = !this.bvar;
			if (this.bvar) {
				//mc.thePlayer.motionZ /= 1.458;
				mc.thePlayer.motionZ /= 1.458;
				//mc.thePlayer.motionX /= 1.458;
				mc.thePlayer.motionX /= 1.458;
				this.mc.thePlayer.bbyoffset = 0.42;
				this.mc.thePlayer.stepHeight = 0.5f;
			}
			else {
				this.mc.thePlayer.stepHeight = 0.0f;
				//thePlayer3.motionX *= (ModuleUtils.getModuleByClass(Scaffold.class).isToggled() ? 2.7 : 3.15);
				//thePlayer4.motionZ *= (ModuleUtils.getModuleByClass(Scaffold.class).isToggled() ? 2.7 : 3.15);
				
				double mul = 3.15D;
				if(Scaffold.instance != null) {
					if(Scaffold.instance.isToggled()) {
						mul = 2.7D;
					}
				}
				mc.thePlayer.motionX *= mul;
				mc.thePlayer.motionZ *= mul;
				
				this.mc.thePlayer.bbyoffset = 0.0;
			}
		}
		else {
			this.bvar = true;
			this.mc.thePlayer.bbyoffset = 0.0;
		}
	}
	
	public void speed_speed() {
		if (Jesus.isLiquidUnderPlayer()) {
			this.mc.timer.timerSpeed = 1.0f;
			return;
		}
		Block.ice.slipperiness = 0.4f;
		if (Keyboard.isKeyDown(17) || Keyboard.isKeyDown(31) || Keyboard.isKeyDown(30) || Keyboard.isKeyDown(32)) {
			if (this.mc.thePlayer.onGround) {
				switch (++this.ivar) {
					case 1: {
						this.mc.timer.timerSpeed = 1.65f;
						final EntityClientPlayerMP thePlayer = this.mc.thePlayer;
						thePlayer.motionX *= 3.15;
						final EntityClientPlayerMP thePlayer2 = this.mc.thePlayer;
						thePlayer2.motionZ *= 3.15;
						break;
					}
					case 2: {
						this.mc.timer.timerSpeed = 1.0f;
						final EntityClientPlayerMP thePlayer3 = this.mc.thePlayer;
						thePlayer3.motionX /= 1.458;
						final EntityClientPlayerMP thePlayer4 = this.mc.thePlayer;
						thePlayer4.motionZ /= 1.458;
						break;
					}
					case 3: {
						final EntityClientPlayerMP thePlayer5 = this.mc.thePlayer;
						thePlayer5.motionX *= 1.17;
						final EntityClientPlayerMP thePlayer6 = this.mc.thePlayer;
						thePlayer6.motionZ *= 1.17;
						break;
					}
					case 4: {
						this.mc.timer.timerSpeed = 1.2f;
						final EntityClientPlayerMP thePlayer7 = this.mc.thePlayer;
						thePlayer7.motionX *= 0.93;
						final EntityClientPlayerMP thePlayer8 = this.mc.thePlayer;
						thePlayer8.motionZ *= 0.93;
						break;
					}
					case 5: {
						this.mc.timer.timerSpeed = 1.0f;
						final EntityClientPlayerMP thePlayer9 = this.mc.thePlayer;
						thePlayer9.motionX *= 1.0;
						final EntityClientPlayerMP thePlayer10 = this.mc.thePlayer;
						thePlayer10.motionZ *= 1.0;
						this.ivar = 0;
						break;
					}
					default: {
						this.mc.timer.timerSpeed = 1.0f;
						break;
					}
				}
			}
			else {
				final EntityClientPlayerMP thePlayer11 = this.mc.thePlayer;
				thePlayer11.motionX /= 1.04;
				final EntityClientPlayerMP thePlayer12 = this.mc.thePlayer;
				thePlayer12.motionZ /= 1.04;
				this.mc.timer.timerSpeed = 1.0f;
			}
		}
	}
	
	public void speed_newspeed() {
		
	}
	
	public static boolean sog2_sub() {
		final boolean b = mc.thePlayer.movementInput.moveForward != 0.0f || mc.thePlayer.movementInput.moveStrafe != 0.0f;
		return !mc.thePlayer.isInWater() && !Jesus.isLiquidUnderPlayer2() && !Jesus.isLiquidUnderPlayer() && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && b;
	}
	
	public void speed_new() {
		if (this.mc.thePlayer.fallDistance > 1.0) {
			return;
		}
		Block.ice.slipperiness = 0.4f;

		final boolean b = mc.thePlayer.movementInput.moveForward != 0.0f || mc.thePlayer.movementInput.moveStrafe != 0.0f;
		final boolean b2 = !mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && mc.thePlayer.onGround && b;
		
		if (b2) {
			this.mc.thePlayer.distanceWalkedModified = 0.0f;

			if (!Scaffold.instance.isToggled()) {
				this.mc.thePlayer.motionY = -4.0;
			}

			bvar = !bvar;
			if (bvar) {
				mc.thePlayer.motionZ /= 1.458;
				mc.thePlayer.motionX /= 1.458;
				this.mc.thePlayer.some_entity_var = 0.42;
				this.mc.thePlayer.stepHeight = 0.5f;
			}
			else {
				this.mc.thePlayer.stepHeight = 0.0f;
				mc.thePlayer.motionX *= (Scaffold.instance.isToggled() ? 2.7 : 3.15);
				mc.thePlayer.motionZ *= (Scaffold.instance.isToggled() ? 2.7 : 3.15);
				this.mc.thePlayer.some_entity_var = 0.0;
			}
		}
		else {
			bvar = true;
			this.mc.thePlayer.some_entity_var = 0.0;
		}
	}
	
	public void speed_old() {
		if(Jesus.isLiquidUnderPlayer()) {
			mc.timer.timerSpeed = 1.0f;
			return;
		}
		
		if(mc.thePlayer.isUsingItem()) {
			if(mc.thePlayer.getItemInUse() != null) {
				if( !(mc.thePlayer.getItemInUse().getItem() instanceof ItemSword && AutoBlock.instance.isToggled())) {
					mc.timer.timerSpeed = 1.0f;
					return;
				}
			}
		}
		
		Block.ice.slipperiness = 0.4f;
		
		//if(		Keyboard.isKeyDown(17) || 
		//		Keyboard.isKeyDown(31) ||
		//		Keyboard.isKeyDown(30) ||
		//		Keyboard.isKeyDown(32)) {
		
		if(		mc.gameSettings.keyBindForward.pressed ||
				mc.gameSettings.keyBindBack.pressed ||
				mc.gameSettings.keyBindLeft.pressed ||
				mc.gameSettings.keyBindRight.pressed) {
			
			if(mc.thePlayer.onGround) {
				switch(++ivar) {
					case 1: {
						mc.timer.timerSpeed = 1.65f;
						mc.thePlayer.motionX *= 3.15;
						mc.thePlayer.motionZ *= 3.15;
						break;
					}
					case 2: {
						mc.timer.timerSpeed = 1.0f;
						mc.thePlayer.motionX /= 1.458;
						mc.thePlayer.motionZ /= 1.458;
						break;
					}
					case 3: {
						mc.thePlayer.motionX *= 1.17;
						mc.thePlayer.motionZ *= 1.17;
						break;
					}
					case 4: {
						mc.timer.timerSpeed = 1.2f;
						mc.thePlayer.motionX *= 0.93;
						mc.thePlayer.motionZ *= 0.93;
						break;
					}
					case 5: {
						mc.timer.timerSpeed = 1.0f;
						mc.thePlayer.motionX *= 1.0;
						mc.thePlayer.motionZ *= 1.0;
						ivar = 0;
						break;
					}
					default: {
						mc.timer.timerSpeed = 1.0f;
					}
				}
			}
			else {
				ivar = 4;
				//mc.thePlayer.motionX /= 1.04;
				//mc.thePlayer.motionZ /= 1.04;
				mc.thePlayer.motionX *= 1.005;
				mc.thePlayer.motionZ *= 1.005;
				mc.timer.timerSpeed = 1.0f;
			}
		}
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
}
