package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Vec3;

public class FastClick extends Module{
	public static BoolValue legit;
	private long prevMs;
	public static DoubleValue speed; //4 for kitpvp
	public static DoubleValue range; //6 for warp pvp, and 3.0 - 4.0 for kit pvp i think
	public static DoubleValue angle;
	public static EntityPlayer curTarget;
	
	public static Module instance = null;

	public FastClick() {
		super("FastClick", Keyboard.KEY_B, ModuleType.COMBAT);
		setDescription("ЅьЄт всех вокруг (MultiAura)");
		instance = this;
		legit = new BoolValue(false);
		prevMs = -1;
		speed = new DoubleValue(17.7, 1, 32);
		range = new DoubleValue(4.3, 1, 7);
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
		curTarget = MiscUtils.getTarget(angle.getValue(), range.getValue(), legit.getValue());
	}

	@Override
	public void onDisable() {
		curTarget = null;
	}

	@Override
	public void preMotionUpdate() {
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
}
