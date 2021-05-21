package hack.rawfish2d.client.ModCombat;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemSpade;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Vec3;

public class StormAura extends Module{
	public static DoubleValue range1;
	public static DoubleValue range2;
	public static BoolValue legit;
	
	public static Module instance = null;

	public StormAura() {
		super("StormAura", Keyboard.KEY_V, ModuleType.COMBAT);
		setDescription("ЅьЄт всех вокруг (StormAura delay0)");
		instance = this;
		legit = new BoolValue(false);
		range1 = new DoubleValue(3.45, 1, 7);
		range2 = new DoubleValue(5, 1, 7);
		
		this.elements.add(new CheckBox(this, "Legit", legit, 0, 0));
		
		this.elements.add(new NewSlider(this, "Range 1", range1, 0, 10, false));
		this.elements.add(new NewSlider(this, "Range 2", range2, 0, 30, false));
	}

	//StormAura - TitanPvP
	@Override
	public void preMotionUpdate() {
		for(int a = 0; a < mc.theWorld.loadedEntityList.size(); ++a) {
			Object obj = mc.theWorld.loadedEntityList.get(a);
			if(obj instanceof Entity && obj instanceof EntityLiving)
			{
				Entity ent = (Entity)obj;
				if (MiscUtils.isValidTarget(ent, range2.getValue(), legit.getValue())) {
					mc.thePlayer.swingItem();
					mc.getNetHandler().addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, ent.entityId, 1));
				}
			}
		}
	}

	@Override
	public void postMotionUpdate() {
		Iterator it = mc.theWorld.loadedEntityList.iterator();
		while(it.hasNext()) {
			Object obj = it.next();

			if(obj instanceof EntityPlayer) {
				for(int a = 0; a < mc.theWorld.loadedEntityList.size(); ++a) {
					Entity ent = (Entity)mc.theWorld.getLoadedEntityList().get(a);
					if (MiscUtils.isValidTarget(ent, range1.getValue(), legit.getValue())) {
						mc.thePlayer.swingItem();
						mc.getNetHandler().addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, ent.entityId, 1));
					}
				}
			}
		}
	}
	//StormAura - TitanPvP
}
