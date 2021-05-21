package hack.rawfish2d.client.ModTest;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMisc.Tower;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class SpamClick extends Module {
	
	public static DoubleValue clicks;
	public static DoubleValue delay;
	private static TimeHelper time;
	
	public SpamClick() {
		super("SpamClick", 0, ModuleType.TEST);
		setDescription("?");
		
		time = new TimeHelper();
		clicks = new DoubleValue(5000D, 0D, 5000D);
		delay = new DoubleValue(0D, 0D, 100D);
		
		elements.add(new NewSlider(this, "Clicks", clicks, 0, 0, true));
		elements.add(new NewSlider(this, "Delay", delay, 0, 20, false));
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onUpdate() {
		final boolean isLeftClick = mc.gameSettings.keyBindAttack.pressed;
		final boolean isRightClick = mc.gameSettings.keyBindUseItem.pressed;
		
		if(isLeftClick || isRightClick) {
			//mc.player.swingArm(EnumHand.MAIN_HAND);
			final int idelay = (int) this.delay.getValue();
			new Thread(new Runnable() {
				public void run() {
					for(int a = 0; a < clicks.getValue(); ++a) {
						if(isLeftClick) {
							mc.clickMouse(0);
						}
						else if(isRightClick) {
							mc.clickMouse(1);
						}
						
						if(idelay > 1)
							MiscUtils.sleep(idelay);
					}
				}
			}).start();
		}
	}
}
