package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;

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
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Vec3;

public class KillAura extends Module{
	public static Module instance = null;
	public static BoolValue legit;
	public static DoubleValue range;
	public static DoubleValue angle;
	public static EntityPlayer curTarget;

	public KillAura() {
		super("KillAura", Keyboard.KEY_C, ModuleType.COMBAT);
		setDescription("ЅьЄт всех вокруг (KillAura delay0)");
		instance = this;
		angle = new DoubleValue(360.0f, 1f, 360.0f);
		range = new DoubleValue(5.9f, 1f, 7f);
		legit = new BoolValue(false);
		
		this.elements.add(new CheckBox(this, "Legit", legit, 0, 0));
		
		this.elements.add(new NewSlider(this, "Range", range, 0, 10, false));
		this.elements.add(new NewSlider(this, "Angle", angle, 0, 30, false));
	}

	@Override
	public void onDisable() {
		curTarget = null;
	}

	@Override
	public void preMotionUpdate() {
		curTarget = MiscUtils.getTarget(angle.getValue(), range.getValue(), legit.getValue());
		if(curTarget != null) {
			if(PlayerUtils.isFriend(curTarget) == false || PlayerUtils.getName(curTarget) != null) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, curTarget.entityId, 1));
			}
			curTarget = null;
		}
	}
}
