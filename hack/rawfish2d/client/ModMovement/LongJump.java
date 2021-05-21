package hack.rawfish2d.client.ModMovement;

import java.util.List;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Potion;

public class LongJump extends Module{
	public static Module instance = null;
	
	private BoolValue forceStraigthAngles;
	
	public LongJump() {
		super("LongJump", Keyboard.KEY_L, ModuleType.MOVEMENT);
		setDescription("Длинный прыжок (лучше работает при получении урона)");
		instance = this;
		//from khamaslkij i think
		
		forceStraigthAngles = new BoolValue(true);
		elements.add(new CheckBox(this, "Force straigth angles", forceStraigthAngles, 0, 0));
	}
	
	public static boolean active;
	boolean speedTick;
	static int delay;
	static int delay2;
	boolean hypickle2bigboostready = true;
	private double moveSpeed;
	private double lastDist;
	public static int stage;
	private double boost = 17.0D;
	
	private boolean move = true;
	private boolean canChangeMotion = false;
	private static int airTicks;
	private static int groundTicks;
	private static float headStart;
	private static double lastHDistance;
	private static boolean isSpeeding;
	static int timeonground;
	static boolean off = false;

	@Override
	public void preMotionUpdate(){
		if ((mc.thePlayer == null) || (mc.theWorld == null)) {
			return;
		}
		
		if(forceStraigthAngles.getValue()) {
			float yaw = Client.getInstance().getRotationUtils().getYaw();
			float pitch = Client.getInstance().getRotationUtils().getPitch();
			yaw = MiscUtils.normalizeYaw(yaw);
			
			if(yaw <= 270 + 45 && yaw >= 270 - 45)
				yaw = 270;
			else if(yaw <= 180 + 45 && yaw >= 180 - 45)
				yaw = 180;
			else if(yaw <= 0 + 45 && yaw >= 0 - 45)
				yaw = 0;
			else if(yaw <= 45 && yaw >= 0 || yaw >= 360 - 45 && yaw <= 360)
				yaw = 360;
			
			MiscUtils.setAngles(pitch, yaw, false);
			MiscUtils.setAngles(pitch, yaw, true);
		}
		
		timeonground = mc.thePlayer.onGround ? ++timeonground : 0;
		
		delay += 1;
		delay2 += 1;
		
		if (mc.gameSettings.keyBindSneak.pressed) {
			return;
		}
		
		mc.thePlayer.setSprinting(false);
		mc.gameSettings.keyBindLeft.pressed = false;
		mc.gameSettings.keyBindRight.pressed = false;
		mc.gameSettings.keyBindBack.pressed = false;
		
		if (isMoving()) {
			if ((mc.theWorld != null) && (mc.thePlayer != null) && (mc.thePlayer.onGround) &&
			(!mc.thePlayer.isDead)) {
				lastHDistance = 0.0D;
			}
			float direction3 = mc.thePlayer.rotationYaw + (mc.thePlayer.moveForward < 0.0F ? 180 : 0) + (mc.thePlayer.moveStrafing > 0.0F ? -90.0F * (mc.thePlayer.moveStrafing > 0.0F ? 0.5F : mc.thePlayer.moveStrafing < 0.0F ? -0.5F : 1.0F) : 0.0F) - (mc.thePlayer.moveStrafing < 0.0F ? -90.0F * (mc.thePlayer.moveStrafing> 0.0F ? 0.5F : mc.thePlayer.moveStrafing < 0.0F ? -0.5F : 1.0F) : 0.0F);
			float xDir = (float)Math.cos((direction3 + 90.0F) * 3.141592653589793D / 180.0D);
			float zDir = (float)Math.sin((direction3 + 90.0F) * 3.141592653589793D / 180.0D);
			if (!mc.thePlayer.isCollidedVertically)
			{
				airTicks += 1;
				isSpeeding = true;
				if (mc.gameSettings.keyBindSneak.isPressed()) {
					mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(0.0D, 2.147483647E9D, 2.147483647E9D, 0.0D, false));
				}
				groundTicks = 0; 	
				
				if (!mc.thePlayer.isCollidedVertically)
				{
					if (mc.thePlayer.motionY == -0.07190068807140403D) {
						mc.thePlayer.motionY *= 0.3499999940395355D;
					}
					if (mc.thePlayer.motionY == -0.10306193759436909D) {
						mc.thePlayer.motionY *= 0.550000011920929D;
					}
					if (mc.thePlayer.motionY == -0.13395038817442878D) {
						mc.thePlayer.motionY *= 0.6700000166893005D;
					}
					if (mc.thePlayer.motionY == -0.16635183030382D) {
						mc.thePlayer.motionY *= 0.6899999976158142D;
					}
					if (mc.thePlayer.motionY == -0.19088711097794803D) {
						mc.thePlayer.motionY *= 0.7099999785423279D;
					}
					if (mc.thePlayer.motionY == -0.21121925191528862D) {
						mc.thePlayer.motionY *= 0.20000000298023224D;
					}
					if (mc.thePlayer.motionY == -0.11979897632390576D) {
						mc.thePlayer.motionY *= 0.9300000071525574D;
					}
					if (mc.thePlayer.motionY == -0.18758479151225355D) {
						mc.thePlayer.motionY *= 0.7200000286102295D;
					}
					if (mc.thePlayer.motionY == -0.21075983825251726D) {
						mc.thePlayer.motionY *= 0.7599999904632568D;
					}
				}
				if ((mc.thePlayer.motionY < -0.2D) && (mc.thePlayer.motionY > -0.24D)) {
					mc.thePlayer.motionY *= 0.7D;
				}
				if ((mc.thePlayer.motionY < -0.25D) && (mc.thePlayer.motionY > -0.32D)) {
					mc.thePlayer.motionY *= 0.8D;
				}
				if ((mc.thePlayer.motionY < -0.35D) && (mc.thePlayer.motionY > -0.8D)) {
					mc.thePlayer.motionY *= 0.98D;
				}
				if ((mc.thePlayer.motionY < -0.8D) && (mc.thePlayer.motionY > -1.6D)) {
					mc.thePlayer.motionY *= 0.99D;
				}
				
				mc.timer.timerSpeed = 0.85F;
				double[] speedVals ={ 0.420606D, 0.417924D, 0.415258D, 0.412609D, 0.409977D, 0.407361D, 0.404761D, 0.402178D, 0.399611D, 0.39706D, 0.394525D, 0.392D, 0.3894D, 0.38644D, 0.383655D, 0.381105D, 0.37867D, 0.37625D, 0.37384D, 0.37145D, 0.369D, 0.3666D, 0.3642D, 0.3618D, 0.35945D, 0.357D, 0.354D, 0.351D, 0.348D, 0.345D, 0.342D, 0.339D, 0.336D, 0.333D, 0.33D, 0.327D, 0.324D, 0.321D, 0.318D, 0.315D, 0.312D, 0.309D, 0.307D, 0.305D, 0.303D, 0.3D, 0.297D, 0.295D, 0.293D, 0.291D, 0.289D, 0.287D, 0.285D, 0.283D, 0.281D, 0.279D, 0.277D, 0.275D, 0.273D, 0.271D, 0.269D, 0.267D, 0.265D, 0.263D, 0.261D, 0.259D, 0.257D, 0.255D, 0.253D, 0.251D, 0.249D, 0.247D, 0.245D, 0.243D, 0.241D, 0.239D, 0.237D };
				if (mc.gameSettings.keyBindForward.pressed) {
					try
					{
						mc.thePlayer.motionX = (xDir * speedVals[(this.airTicks - 1)] * 3.0D * addSpeedForSpeedEffect());
						mc.thePlayer.motionZ = (zDir * speedVals[(this.airTicks - 1)] * 3.0D * addSpeedForSpeedEffect());
						this.off = true;
					}
					catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
				}
				else {
					mc.thePlayer.motionX = 0.0D;
					mc.thePlayer.motionZ = 0.0D;
				}
			}
			else {
				mc.timer.timerSpeed = 1.0F;
				airTicks = 0;
				groundTicks += 1;
				headStart -= 1.0F;
				mc.thePlayer.motionX /= 13.0D;
				mc.thePlayer.motionZ /= 13.0D;
				if (groundTicks == 1) {
					updatePosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.dimension);
					
					updatePosition(mc.thePlayer.posX + 0.0624D, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.dimension);
					
					updatePosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.419D, mc.thePlayer.posZ + 0.419D, mc.thePlayer.dimension);
					
					updatePosition(mc.thePlayer.posX + 0.0624D, mc.thePlayer.posY,mc.thePlayer.posZ, mc.thePlayer.dimension);
					
					updatePosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.419D, mc.thePlayer.posZ + 0.419D, mc.thePlayer.dimension);
					
				}
				if (groundTicks > 2)
				{
					groundTicks = 0;
					mc.thePlayer.motionX = (xDir * 0.3D);
					mc.thePlayer.motionZ = (zDir * 0.3D);
					mc.thePlayer.motionY = 0.42399999499320984D;
				}
			}
		}
	}

	private static double addSpeedForSpeedEffect() {
		double baseSpeed = 1.0D;
		/*
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed = 0.4D;
		}
		*/
		return baseSpeed;
	}

	public static void updatePosition(double x, double y, double z, double s) {
		mc.thePlayer.sendQueue.addToSendQueue(new Packet11PlayerPosition(x, y, z, s, mc.thePlayer.onGround));
	}

	private static double getDistance(EntityPlayer player, double distance) {
		List boundingBoxes = player.worldObj.getCollidingBoundingBoxes(player,
		player.boundingBox.addCoord(0.0D, -distance, 0.0D));
		if (boundingBoxes.isEmpty()) {
			return 0.0D;
		}
		double y = 0.0D;
		
		return player.posY - y;
	}

	public static boolean isMoving() {
		if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
			return false;
		}
		return true;
	}

	@Override
	public void onEnable() {
		active = true;
		this.speedTick = false;
		this.canChangeMotion = true;
		this.airTicks = 0;
		stage = 0;
		off = false;
		mc.timer.timerSpeed = 1.0F;
	}

	@Override
	public void onDisable() {
		active = false;
		mc.timer.timerSpeed = 1.0F;
		if(mc.thePlayer.getActivePotionEffects().size() > 0)
			return;
	}
}
