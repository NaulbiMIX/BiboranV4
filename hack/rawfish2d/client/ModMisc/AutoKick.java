package hack.rawfish2d.client.ModMisc;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.Packet7UseEntity;

public class AutoKick extends Module {
	//public static int hp = 6;
	
	public DoubleValue hp;
	
	public AutoKick() {
		super("AutoKick", 0, ModuleType.MISC);
		setDescription("Когда у вас мало здоровья вас кикает с сервера (обход AntiRelog системы)");
		
		hp = new DoubleValue(6D, 0D, 20D);
		
		elements.add(new NewSlider(this, "HP", hp, 0, 0, true));
	}
	
	@Override
	public void preMotionUpdate() {
		if(mc.thePlayer.getHealth() <= (int)hp.getValue())
		{
			//mc.thePlayer.sendChatMessage("§cKEK");
			//mc.thePlayer.sendQueue.addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, mc.thePlayer.entityId, 0));
			MiscUtils.selfKick();
			toggle();
		}
	}
}
