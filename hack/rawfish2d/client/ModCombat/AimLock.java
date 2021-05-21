package hack.rawfish2d.client.ModCombat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;

public class AimLock extends Module {
	public static BoolValue onKey;
	public static DoubleValue range;
	public static DoubleValue fov;

	public AimLock() {
		super("AimLock", 0, ModuleType.COMBAT);
		setDescription("Смотрит на ближайшего противника при нажатии левой кнопки мыши");
		onKey = new BoolValue(true);
		
		elements.add(new CheckBox(this, "On key", onKey, 0, 0));
		
		range = new DoubleValue(6.5f, 0f, 30f);
		fov = new DoubleValue(180f, 0f, 361f);
		
		this.elements.add(new NewSlider(this, "Range", range, 0, 10, false));
		this.elements.add(new NewSlider(this, "FOV", fov, 0, 30, false));
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
		//if(packet instanceof Packet7UseEntity) {
		/*
		if(packet instanceof Packet18Animation) {
			double range = 50;
			EntityPlayer target = MiscUtils.getClosestTarget(360, range, false);
			MiscUtils.facePlayer(target, false);
		}
		*/
	}

	@Override
	public void preMotionUpdate() {
		if(mc.gameSettings.keyBindAttack.pressed && onKey.getValue() || !onKey.getValue()) {
			EntityPlayer target = MiscUtils.getClosestTarget(fov.getValue(), range.getValue(), false);

			if(target != null) {
				target = MiscUtils.getClosestTarget(fov.getValue(), range.getValue(), false);
				MiscUtils.facePlayer(target, false);
			}
		}
	}

	@Override
	public void postMotionUpdate() {
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void afterOnAddPacketToQueue(Packet packet) {
	}
}
