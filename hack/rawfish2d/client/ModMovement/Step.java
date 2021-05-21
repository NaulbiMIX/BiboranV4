package hack.rawfish2d.client.ModMovement;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;

public class Step extends Module {
	public static Module instance = null;
	private boolean okflag;
	public static int counter;
	//private int var3; //unused
	private boolean okflag_ex;
	private BoolValue oldstep;
	private DoubleValue stepHeight;
	 
	public Step() {
		super("Step", 0, ModuleType.MOVEMENT);
		setDescription("Автоматическое взбирание на блоки (высота до 3 блоков, c SKB до 9)");
		instance = this;
		//from DD 06
		okflag = true;
		counter = 0;
		okflag_ex = false;
		
		oldstep = new BoolValue(false);
		stepHeight = new DoubleValue(1.2D, 0D, 12D);
		
		this.elements.add(new CheckBox(this, "Old Step", oldstep, 0, 0));
		this.elements.add(new NewSlider(this, "Step height", stepHeight, 0, 10, false));
	}
	
	@Override
	public void preMotionUpdate() {
		if(oldstep.getValue())
			step_Old();
		else
			step_New();
	}
	
	public void step_Old() {
		long delay = 2L;
		double n = mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
		
		if (Entity.some_entity_var == 0.0) {
			n = 0.0;
		}
		
		mc.thePlayer.ySize = 0.0f;
		
		if (mc.thePlayer.fallDistance == 0.0f && mc.thePlayer.onGround && !mc.thePlayer.isInWater()) {
			mc.thePlayer.stepHeight = (float) stepHeight.getValue();
		}
		else {
			mc.thePlayer.stepHeight = 0.6f;
		}
		
		if (oldstep.getValue()) {
			if (n != 0.0) {
				okflag = true;
			}
			//else { //else if (!SpeedHack.instance.isToggled()) {
				mc.thePlayer.bbyoffset = 0.0;
				okflag = false;
				okflag_ex = false;
				counter = 0;
			//}
			
			if (okflag) {
				++counter;
				if (n < 1.35) {
					if (n > 0.9374 && n < 1.0626 && counter == 1) {
						mc.thePlayer.bbyoffset = 0.11;
						okflag_ex = true;
					}
					if (counter > 1 && (mc.thePlayer.posY - mc.thePlayer.lastTickPosY != 0.5 || okflag_ex) && (okflag_ex || mc.thePlayer.posY - mc.thePlayer.lastTickPosY == 1.0)) {
						mc.thePlayer.bbyoffset = 0.11;
						mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
						mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, false));
					}
				}
			}
			if (n > 1.35 && n <= 2.3) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.34, mc.thePlayer.lastTickPosY + 1.34, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.96, mc.thePlayer.lastTickPosY + 0.96, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 2.3 && n <= 2.8) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3499, mc.thePlayer.lastTickPosY + 1.3499, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.9748, mc.thePlayer.lastTickPosY + 0.9748, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.5998, mc.thePlayer.lastTickPosY + 0.5998, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.3247, mc.thePlayer.lastTickPosY + 2.3247, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.45734, mc.thePlayer.lastTickPosY + 1.45734, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
			if (n > 2.8 && n <= 3.0) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3499, mc.thePlayer.lastTickPosY + 1.3499, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.9748, mc.thePlayer.lastTickPosY + 0.9748, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.5998, mc.thePlayer.lastTickPosY + 0.5998, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.3247, mc.thePlayer.lastTickPosY + 2.3247, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.45734, mc.thePlayer.lastTickPosY + 1.45734, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, true));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.8073, mc.thePlayer.lastTickPosY + 2.8073, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.69864, mc.thePlayer.lastTickPosY + 1.69864, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
		}
	}
	
	public void step_New() {
		long delay = 2L;
		double n = mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
		if (Entity.some_entity_var == 0.0) {
			n = 0.0;
		}
		mc.thePlayer.ySize = 0.0f;
		if (mc.thePlayer.fallDistance == 0.0f && mc.thePlayer.onGround && !mc.thePlayer.isInWater()) {
			mc.thePlayer.stepHeight = (float) stepHeight.getValue();
		}
		else {
			mc.thePlayer.stepHeight = 0.6f;
		}
		/*
		if (oldstep.getValue()) {
			if (n != 0.0) {
				okflag = true;
			}
			//else if (!SpeedHack.instance.isToggled()) {
			else {
				mc.thePlayer.bbyoffset = 0.0;
				okflag = false;
				okflag_ex = false;
				counter = 0;
			}
			if (okflag) {
				++counter;
				if (n < 1.35) {
					if (n > 0.9374 && n < 1.0626 && counter == 1) {
						mc.thePlayer.bbyoffset = 0.11;
						okflag_ex = true;
					}
					if (counter > 1 && (mc.thePlayer.posY - mc.thePlayer.lastTickPosY != 0.5 || okflag_ex) && (okflag_ex || mc.thePlayer.posY - mc.thePlayer.lastTickPosY == 1.0)) {
						mc.thePlayer.bbyoffset = 0.11;
						mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
						mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, false));
						MiscUtils.sleep(delay);
					}
				}
			}
			if (n > 1.35 && n <= 2.3) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.34, mc.thePlayer.lastTickPosY + 1.34, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.96, mc.thePlayer.lastTickPosY + 0.96, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 2.3 && n <= 2.8) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3499, mc.thePlayer.lastTickPosY + 1.3499, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.9748, mc.thePlayer.lastTickPosY + 0.9748, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.5998, mc.thePlayer.lastTickPosY + 0.5998, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.3247, mc.thePlayer.lastTickPosY + 2.3247, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.45734, mc.thePlayer.lastTickPosY + 1.45734, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
			if (n > 2.8 && n <= 3.0) {
				if (okflag_ex) {
					mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0), mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
					MiscUtils.sleep(delay);
				}
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3499, mc.thePlayer.lastTickPosY + 1.3499, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.9748, mc.thePlayer.lastTickPosY + 0.9748, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.5998, mc.thePlayer.lastTickPosY + 0.5998, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.3247, mc.thePlayer.lastTickPosY + 2.3247, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.45734, mc.thePlayer.lastTickPosY + 1.45734, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, true));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 2.8073, mc.thePlayer.lastTickPosY + 2.8073, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), false));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.69864, mc.thePlayer.lastTickPosY + 1.69864, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.59, mc.thePlayer.lastTickPosY + 0.59, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
		}
		else if (oldstep.getValue()) {
		*/
		if (!oldstep.getValue()) {
			if (n > 0.6 && n <= 0.65) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.35, mc.thePlayer.lastTickPosY + 0.35, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n == 0.6875) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.37, mc.thePlayer.lastTickPosY + 0.35, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 0.74 && n <= 0.77) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.419999, mc.thePlayer.lastTickPosY + 0.419999, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 0.8 && n <= 0.877) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.37, mc.thePlayer.lastTickPosY + 0.37, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.654, mc.thePlayer.lastTickPosY + 0.654, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
			if (n > 0.877 && n <= 1.001) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.419999, mc.thePlayer.lastTickPosY + 0.419999, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.75, mc.thePlayer.lastTickPosY + 0.75, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
			if (n > 1.001 && n <= 1.25) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.424, mc.thePlayer.lastTickPosY + 0.424, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.774, mc.thePlayer.lastTickPosY + 0.774, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.05, mc.thePlayer.lastTickPosY + 1.05, mc.thePlayer.lastTickPosZ, false));
			}
			if (n > 1.25 && n <= 1.3125) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.424, mc.thePlayer.lastTickPosY + 0.424, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.774, mc.thePlayer.lastTickPosY + 0.774, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.05, mc.thePlayer.lastTickPosY + 1.05, mc.thePlayer.lastTickPosZ, false));
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.25, mc.thePlayer.lastTickPosY + 1.25, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 1.3125 && n <= 1.74) {
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.424, mc.thePlayer.lastTickPosY + 0.424, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.774, mc.thePlayer.lastTickPosY + 0.774, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.05, mc.thePlayer.lastTickPosY + 1.05, mc.thePlayer.lastTickPosZ, false));
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.25, mc.thePlayer.lastTickPosY + 1.25, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.24, mc.thePlayer.lastTickPosY + 1.24, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
			}
			if (n > 1.74 && n <= 2.18) {
				final double n2 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
				final double n3 = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
				final double n4 = mc.gameSettings.keyBindBack.pressed ? 0.07 : -0.07;
				final double n5 = 1.0 * n4 * n2 + 0.0 * n4 * n3;
				final double n6 = 1.0 * n4 * n3 - 0.0 * n4 * n2;
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.424, mc.thePlayer.lastTickPosY + 0.424, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.42399999998, mc.thePlayer.lastTickPosY + 0.42399999998, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX + n5, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.42399999997, mc.thePlayer.lastTickPosY + 0.42399999997, mc.thePlayer.lastTickPosZ + n6, false));
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.848, mc.thePlayer.lastTickPosY + 0.848, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.198, mc.thePlayer.lastTickPosY + 1.198, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.478, mc.thePlayer.lastTickPosY + 1.478, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.681, mc.thePlayer.lastTickPosY + 1.681, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.68, mc.thePlayer.lastTickPosY + 1.68, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
			if (n > 2.18 && n <= 2.51) {
				final double n2 = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
				final double n3 = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90.0f));
				final double n4 = mc.gameSettings.keyBindBack.pressed ? 0.07 : -0.07;
				final double n5 = 1.0 * n4 * n2 + 0.0 * n4 * n3;
				final double n6 = 1.0 * n4 * n3 - 0.0 * n4 * n2;
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.424, mc.thePlayer.lastTickPosY + 0.424, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.774, mc.thePlayer.lastTickPosY + 0.774, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.041, mc.thePlayer.lastTickPosY + 1.041, mc.thePlayer.lastTickPosZ, false));
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.225, mc.thePlayer.lastTickPosY + 1.225, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3275, mc.thePlayer.lastTickPosY + 1.3275, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.3499, mc.thePlayer.lastTickPosY + 1.3499, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.2899, mc.thePlayer.lastTickPosY + 1.2899, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.1529, mc.thePlayer.lastTickPosY + 1.1529, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.9409, mc.thePlayer.lastTickPosY + 0.9409, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.6549, mc.thePlayer.lastTickPosY + 0.6549, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.580899998, mc.thePlayer.lastTickPosY + 0.580899998, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX + n5, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 0.580899997, mc.thePlayer.lastTickPosY + 0.580899997, mc.thePlayer.lastTickPosZ + n6, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.004, mc.thePlayer.lastTickPosY + 1.004, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.354, mc.thePlayer.lastTickPosY + 1.354, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.621, mc.thePlayer.lastTickPosY + 1.621, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.805, mc.thePlayer.lastTickPosY + 1.805, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.9184, mc.thePlayer.lastTickPosY + 1.9184, mc.thePlayer.lastTickPosZ, Client.getInstance().getRotationUtils().getYaw(), Client.getInstance().getRotationUtils().getPitch(), true));
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.9507, mc.thePlayer.lastTickPosY + 1.9507, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
				mc.getNetHandler().addToSendQueue(new Packet11PlayerPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.prevPosY - 1.62 + (mc.thePlayer.isSneaking() ? 0.080000006 : 0.0) + 1.9107, mc.thePlayer.lastTickPosY + 1.9107, mc.thePlayer.lastTickPosZ, false));
				MiscUtils.sleep(delay);
			}
		}
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.stepHeight = 0.6f;
		okflag_ex = false;
		mc.thePlayer.bbyoffset = 0.0;
		counter = 0;
	}
}
