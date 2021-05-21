package hack.rawfish2d.client.ModMisc;


import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;

public class TimerMod extends Module{

	public static DoubleValue speed;
	
	public TimerMod() {
		super("Timer", 0, ModuleType.MISC);
		setDescription("Изменяет скорость времени в игре");
		speed = new DoubleValue(1.0f, 0.0001f, 3.0f);
		
		elements.add(new NewSlider(this, "Speed", speed, 0, 0, false));
	}
	
	@Override
	public void onEnable() {
		setName("Timer");
	}
	
	@Override
	public void onDisable() {
		setName("Timer");
		mc.timer.timerSpeed = 1.0f;
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void preMotionUpdate() {
		mc.timer.timerSpeed = (float) speed.getValue();
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
}
