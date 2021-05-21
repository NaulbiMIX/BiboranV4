package hack.rawfish2d.client.ModMovement;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Potion;

public class BHOP extends Module{
	public static Module instance = null;
	private DoubleValue timer;
	private BoolValue old;
	
	public BHOP() {
		super("BHOP", Keyboard.KEY_P, ModuleType.MOVEMENT);
		setDescription("БанниХоп");
		instance = this;
		//from Khamalski
		
		old = new BoolValue(false);
		timer = new DoubleValue(1.2D, 0.5D, 2.2D);
		
		this.elements.add(new CheckBox(this, "Old Bhop", old, 0, 0));
		
		this.elements.add(new NewSlider(this, "Old Bhop Timer", timer, 0, 10, false));
	}
	
	@Override
	public void preMotionUpdate()
	{
		if(Speed_DD_B1.instance.isToggled() || mc.thePlayer.isInWater())
			return;
		
		if(old.getValue())
			bhop_khamalski();
		else
			bhop_somesomeweed();
		
		//EnumGameType var3 = EnumGameType.CREATIVE;
		//mc.thePlayer.setGameType(var3);
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if(packet instanceof Packet10Flying) {
			Packet10Flying pfly = (Packet10Flying)packet;
			pfly.onGround = true;
		}
	}
	
	private void bhop_khamalski()
	{
		if (mc.gameSettings.keyBindJump.pressed) {
			return;
		}
		if (!mc.thePlayer.isCollidedHorizontally) {
			if (!mc.thePlayer.isSneaking() && !mc.thePlayer.isInWater()) {
				mc.thePlayer.setSprinting(true);   
			}
		}
		
		if (mc.thePlayer.onGround) {
			if ((!mc.thePlayer.isSneaking() && mc.gameSettings.keyBindForward.pressed) || mc.gameSettings.keyBindBack.pressed ||mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed) {
				mc.thePlayer.jump();
				
				//this.timer = 1.00f;
				mc.timer.timerSpeed = (float) timer.getValue(); //1.25F
				mc.thePlayer.motionY = 0.4;
				mc.thePlayer.motionX *= 1.01;
				mc.thePlayer.motionY *= 0.8D;
				mc.thePlayer.motionZ *= 1.01;
				mc.thePlayer.jumpMovementFactor = 0.02f;
				mc.thePlayer.moveStrafing = 2;
				mc.thePlayer.speedInAir = 0.02f;
				
				for(int a = 0; a < 10; ++a)
					mc.thePlayer.sendQueue.addToSendQueue(new Packet10Flying(mc.thePlayer.onGround));
					//mc.getNetHandler().addToSendQueue(new Packet10Flying(false));
			}
		}
		mc.thePlayer.onGround = true;
	}
	
	private void bhop_somesomeweed() { 
		//BHop from SmokeSomeWeed
		if (this.mc.thePlayer.onGround || (!Material.air.blocksMovement() && !this.mc.thePlayer.isInWater())) {
			float rotationYaw = this.mc.thePlayer.rotationYaw;
			if (this.mc.thePlayer.moveForward < 0.0f) {
				rotationYaw += 180.0f;
			}
			if (this.mc.thePlayer.moveStrafing > 0.0f) {
				rotationYaw -= 90.0f * ((this.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((this.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
			}
			if (this.mc.thePlayer.moveStrafing < 0.0f) {
				rotationYaw += 90.0f * ((this.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((this.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
			}
			double n = 0.221;
			if (this.mc.thePlayer.isSprinting()) {
				n *= 1.3190000119209289;
			}
			if (this.mc.thePlayer.isSneaking()) {
				n *= 0.3;
			}
			if (this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
				for (int i = 0; i < this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1; ++i) {
					n *= 1.2000000029802322;
				}
			}
			final float n2 = (float)((float)Math.cos((rotationYaw + 90.0f) * 3.141592653589793 / 180.0) * n);
			final float n3 = (float)((float)Math.sin((rotationYaw + 90.0f) * 3.141592653589793 / 180.0) * n);
			if (this.mc.gameSettings.keyBindForward.isPressed() || this.mc.gameSettings.keyBindLeft.isPressed() || this.mc.gameSettings.keyBindRight.isPressed() || this.mc.gameSettings.keyBindBack.isPressed()) {
				this.mc.thePlayer.motionX = n2;
				this.mc.thePlayer.motionZ = n3;
			}
			
			//mc.thePlayer.rotationYaw = rotationYaw;
			
			//Client.getInstance().getRotationUtils().setYaw(rotationYaw);
			//MiscUtils.sendChatClient("" + rotationYaw);
			//mc.getNetHandler().addToSendQueue(new Packet12PlayerLook(rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
		}
		if (mc.gameSettings.keyBindJump.pressed) {
			return;
		}
		if (mc.thePlayer.onGround) {
			if ((!mc.thePlayer.isSneaking() && mc.gameSettings.keyBindForward.pressed) || mc.gameSettings.keyBindBack.pressed ||mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed) {
				mc.thePlayer.jump();
				mc.thePlayer.motionX *= 1.0;
				mc.thePlayer.motionY *= 1.0;
				mc.thePlayer.motionZ *= 1.0;
				mc.thePlayer.jumpMovementFactor = 0.02f;
				mc.thePlayer.moveStrafing = 2;
				mc.thePlayer.speedInAir = 0.02f;
			}
		}
		mc.thePlayer.onGround = true;
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.onGround = false;
		mc.timer.timerSpeed = 1.0F;
	}
}
