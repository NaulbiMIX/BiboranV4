package hack.rawfish2d.client.ModRender;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

import org.lwjgl.opengl.GL11;

public class GlowESP extends Module {
	public static Module instance = null;
	
	public static int colorVisible;
	public static int colorNotVisible;
	
	public static DoubleValue c_vis_r;
	public static DoubleValue c_vis_g;
	public static DoubleValue c_vis_b;
	public static DoubleValue c_vis_a;
	
	public static DoubleValue c_notvis_r;
	public static DoubleValue c_notvis_g;
	public static DoubleValue c_notvis_b;
	public static DoubleValue c_notvis_a;
	
	public GlowESP() {
		super("GlowESP", 0, ModuleType.RENDER);
		setDescription("Очень красивое ESP");
		instance = this;
		colorVisible = 0xFF00FF00;
		colorNotVisible = 0xFFFF0000;
		
		c_vis_r = new DoubleValue(0, 0, 1);
		c_vis_g = new DoubleValue(1, 0, 1);
		c_vis_b = new DoubleValue(0, 0, 1);
		c_vis_a = new DoubleValue(1, 0, 1);
		
		c_notvis_r = new DoubleValue(1, 0, 1);
		c_notvis_g = new DoubleValue(0, 0, 1);
		c_notvis_b = new DoubleValue(0, 0, 1);
		c_notvis_a = new DoubleValue(1, 0, 1);
		
		elements.add(new NewSlider(this, "Visible Red", c_vis_r, 0, 0, false));
		elements.add(new NewSlider(this, "Visible Green", c_vis_g, 0, 20, false));
		elements.add(new NewSlider(this, "Visible Blue", c_vis_b, 0, 40, false));
		elements.add(new NewSlider(this, "Visible Alpha", c_vis_a, 0, 60, false));
		
		elements.add(new NewSlider(this, "Not Visible Red", c_notvis_r, 0, 80, false));
		elements.add(new NewSlider(this, "Not Visible Green", c_notvis_g, 0, 100, false));
		elements.add(new NewSlider(this, "Not Visible Blue", c_notvis_b, 0, 120, false));
		elements.add(new NewSlider(this, "Not Visible Alpha", c_notvis_a, 0, 140, false));
	}
	
	@Override
	public void onDisable() {
		
	}	
	
	@Override
	public void onEnable() {
		
	}
	//MaIospaal enemy
}
