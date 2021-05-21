package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;

public class MultiAura extends Module{
	public static BoolValue legit;
	private long prevMs;
	public static DoubleValue speed; //4 for kitpvp
	public static DoubleValue range; //6 for warp pvp, and 3.0 - 4.0 for kit pvp i think
	public static DoubleValue angle;
	public static EntityPlayer curTarget;

	public static Module instance = null;
	
	public MultiAura() {
		super("MultiAura", Keyboard.KEY_B, ModuleType.COMBAT);
		setDescription("ЅьЄт всех вокруг (MultiAura)");
		instance = this;
		legit = new BoolValue(false);
		prevMs = -1;
		speed = new DoubleValue(15.0, 1, 32);
		range = new DoubleValue(6.0, 1, 7);
		angle = new DoubleValue(180.0, 1, 360.0);
		curTarget = null;
		//from Smuff
		
		this.elements.add(new CheckBox(this, "Legit", legit, 0, 0));
		
		this.elements.add(new NewSlider(this, "Speed", speed, 0, 10, false));
		this.elements.add(new NewSlider(this, "Range", range, 0, 30, false));
		this.elements.add(new NewSlider(this, "Angle", angle, 0, 50, false));
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void preMotionUpdate() {
		/*
		Block block = MiscUtils.getBlock((int)mc.thePlayer.posX - 1, (int)mc.thePlayer.posY - 2, (int)mc.thePlayer.posZ - 1);
		if(block != null) {
			MiscUtils.sendChatClient("name:" + block.toString());
		}
		*/
		long var1 = (long)(1000.0 / speed.getValue());
		curTarget = MiscUtils.getTarget(angle.getValue(), range.getValue(), legit.getValue());
		
		if (curTarget != null) {
			while (System.nanoTime() / 1000000L - prevMs >= var1) {
				MiscUtils.facePlayer(curTarget, true);

				mc.thePlayer.swingItem();
				mc.getNetHandler().addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, curTarget.entityId, 1));
				prevMs = System.nanoTime() / 1000000L;
			}
		}
	}
	
	@Override
	public void onRenderHand() {
		//R2DUtils.drawBetterRect(0, 0, 55, 55, 1, 0xFFFFFFFF, 0xFFFFFFFF);
	}
	
	@Override
	public void onRender() {
		/*
		double fi;
		double x0;
		double z0;
		
		double x = mc.thePlayer.posX;
		double z = mc.thePlayer.posZ + 1;
		
		x = x - RenderManager.renderPosX;
		z = z - RenderManager.renderPosZ;
		
		fi = mc.thePlayer.rotationYaw + angle.getValue();
		fi = Math.toRadians(fi);
		
		x0 = x * Math.cos(fi) - z * Math.sin(fi);
		z0 = z * Math.cos(fi) + x * Math.sin(fi);
		
		R3DUtils.drawLine(x0, 2, z0, x0, -2, z0, 0xFFFF0000, 3f);
		
		fi = mc.thePlayer.rotationYaw - angle.getValue();
		fi = Math.toRadians(fi);
		
		x0 = x * Math.cos(fi) - z * Math.sin(fi);
		z0 = z * Math.cos(fi) + x * Math.sin(fi);
		
		R3DUtils.drawLine(x0, 2, z0, x0, -2, z0, 0xFFFF0000, 3f);
		*/
	}
	
	@Override
	public void onRenderOverlay() {
		/*
		float w = MiscUtils.getScreenWidth() / 2;
		float h = MiscUtils.getScreenHeight() / 2;
		
		R2DUtils.drawCircle(w, h, (float)(angle.getValue() * 4), 32, 0xFF00FF00);
		*/
		//R2DUtils.drawBetterRect(0, 0, 55, 55, 1, 0xFFFFFFFF, 0xFFFFFFFF);
	}
}
