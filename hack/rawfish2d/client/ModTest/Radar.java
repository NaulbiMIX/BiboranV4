package hack.rawfish2d.client.ModTest;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.WindowRadar;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

public class Radar extends Module{

	public static Module instance = null;
	
	private WindowRadar radar;
	private DoubleValue zoom;
	private DoubleValue scale;
	private DoubleValue radius;
	
	public Radar() {
		super("Radar", 0, ModuleType.TEST);
		setDescription("Показывает список ближайших игроков");
		instance = this;
		
		zoom = new DoubleValue(1, 0, 5);
		scale = new DoubleValue(1, 0, 5);
		radius = new DoubleValue(15, 0, 50);
		radar = new WindowRadar(this, 2, 20, 100, 100, zoom, scale, radius);
		
		this.elements.add(new NewSlider(this, "Zoom", zoom, 0, 0, false));
		this.elements.add(new NewSlider(this, "Scale", scale, 0, 20, false));
		this.elements.add(new NewSlider(this, "Radius", radius, 0, 30, true));
		this.elements.add(radar);
	}
	
	@Override
	public void onRender() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		//radar = new WindowRadar(this, 2, 20, 100, 100, zoom, scale);
		renderRadar();
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	private void renderRadar() {
		ScaledResolution sr = MiscUtils.getScaledResolution();
		int w = sr.getScaledWidth();
		int h = sr.getScaledHeight();
		
		int x = Mouse.getX() * w / mc.displayWidth;
		int y = h - Mouse.getY() * h / mc.displayHeight - 1;
		
		radar.draw(x, y);
	}
}
