package hack.rawfish2d.client.ModCombat;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModTest.ChokePackets;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;

public class ChokePotions extends Module {
	public int choke;
	public DoubleValue chokeMax;

	public ChokePotions() {
		super("ChokePotions", 0, ModuleType.COMBAT);
		setDescription("Когда вы стоите, эффекты зелий длятся в 30 раз дольше (только если вас не бьют)");
		choke = 0;
		chokeMax = new DoubleValue(30, 0, 500);
		this.elements.add(new NewSlider(this, "Limit", chokeMax, 0, 0, true));
	}

	@Override
	public void onEnable() {
		choke = 0;
	}

	@Override
	public void onDisable() {
		choke = 0;
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
		if(packet instanceof Packet10Flying || packet instanceof Packet11PlayerPosition || packet instanceof Packet0KeepAlive) {
			if(mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0 && ChokePackets.choke == 0 && mc.thePlayer.hurtTime == 0 && mc.thePlayer.getItemInUseDuration() <= 0) {
				if(choke == 0)
					choke = (int) chokeMax.getValue();

				if(choke > 0) {
					doNotSendNextPacket(true);
					choke--;
				}

				if(choke == 0)
					doNotSendNextPacket(false);
			}
		}
	}
}
