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
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Packet7UseEntity;

public class Clicker extends Module {
	public static Module instance = null;

	private DoubleValue cps;
	private BoolValue onKey;
	private TimeHelper time;
	
	public Clicker() {
		super("Clicker", 0, ModuleType.COMBAT);
		setDescription("јвтоматически кликает при наведении прицела на entity");
		instance = this;
		time = new TimeHelper();
		
		onKey = new BoolValue(true);
		elements.add(new CheckBox(this, "On key", onKey, 0, 0));
		
		cps = new DoubleValue(20D, 0D, 20D);
		elements.add(new NewSlider(this, "CPS", cps, 0, 10, true));
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void preMotionUpdate() {
		if(mc.gameSettings.keyBindAttack.pressed && onKey.getValue() || !onKey.getValue()) {
			double delay = cps.getValue();
			if(delay == 0) {
				delay = 1;
			}
			
			if(time.hasReached(1000 / delay)) {
				if(mc.objectMouseOver != null) {
					if(mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
						//mc.clickMouse(0);
						Entity target = mc.objectMouseOver.entityHit; 
						if(MiscUtils.isValidTarget(target, 6, false)) {
							mc.thePlayer.swingItem();
							mc.thePlayer.sendQueue.addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, target.entityId, 1));
						}
					}
				}
				time.reset();
			}
		}
	}
}
