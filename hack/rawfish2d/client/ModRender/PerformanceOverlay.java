package hack.rawfish2d.client.ModRender;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
//import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModCombat.AimLock;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.GraphPoint;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.FontRenderer;

public class PerformanceOverlay extends Module{
	public static Module instance = null;
	
	public static TimeHelper timer = new TimeHelper();
	public static TimeHelper frametimer = new TimeHelper();
	public static TimeHelper maketimer = new TimeHelper();
	public static List<GraphPoint> ft_points = new ArrayList<GraphPoint>();
	public static List<GraphPoint> fps_points = new ArrayList<GraphPoint>();
	public static int counter = 0;
	public static int fps = 0;
	public static FontRenderer fr = Client.getInstance().mc.fontRenderer;
	public static int frametime = 0;
	public static int maxft = 0;
	
	public static BoolValue bfps_graph;
	public static BoolValue bft_graph;
	public static CheckBox fpsbox;
	public static CheckBox ftbox;
	
	public static int yfps = 0;
	public static int yft = 0;
	
	public PerformanceOverlay() {
		super("PerfOverlay", 0, ModuleType.RENDER);
		setDescription("Показывает информацию о производительности");
		instance = this;
		bfps_graph = new BoolValue(true);
		bft_graph = new BoolValue(true);
		
		elements.add(fpsbox = new CheckBox(this, "FPS Graph", bfps_graph, 0, 0));
		elements.add(ftbox = new CheckBox(this, "FT Graph", bft_graph, 0, 10));
	}
	
	@Override
	public void onDisable() {
		ft_points.clear();
		fps_points.clear();
		maxft = 0;
	}
	
	@Override
	public void onEnable() {
		timer.reset();
		frametimer.reset();
		maketimer.reset();
	}
	
	@Override
	public void onRenderOverlay() {
		if(timer.hasReached(1000L)) {
			timer.reset();
			fps = counter;
			counter = 0;
		}
		else
			counter++;
		
		frametime = (int) (frametimer.getCurrentMS() - frametimer.getLastMS());
		frametimer.reset();
		
		//if(counter == 0)
		//	fps = 1000 / frametime;
		
		if(frametime > maxft) {
			maxft = frametime;
		}
		
		/*
		maxft = 0;
		for(GraphPoint gp : ft_points) {
			if(gp.frametime > maxft)
				maxft = gp.frametime;
		}
		*/
		
		//if(maketimer.hasReached(50L)) {
			makeFrameTimeGraph();
			makeFPSGraph();
			//maketimer.reset();
		//}
		
		drawFrameTimeGraph();
		drawFPSGraph();
		
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		double cpuload = osBean.getSystemCpuLoad();
		long freeram = osBean.getFreePhysicalMemorySize();
		long totalram = osBean.getTotalPhysicalMemorySize();
		
		cpuload = cpuload * 100;
		long ram = (totalram - freeram);
		ram /= 1024; //KB
		ram /= 1024; //MB
		
		//GL11.glEnable(GL11.GL_BLEND);
		fr.drawStringWithShadow("CPU: " + ((int)cpuload) + "%", 2, 34, 0xFFFF00);
		fr.drawStringWithShadow("RAM: " + ram + " MB", 2, 44, 0xFFFF00);
		fr.drawStringWithShadow("Max frametime: " + maxft, 2, 54, 0xFFFF00);
		
		yfps = 0;
		yft = 0;
		
		if(!ftbox.getValue() && fpsbox.getValue()) {
			yfps = -66;
			yft = -20;
		}
		else if(ftbox.getValue() && !fpsbox.getValue()) {
			yfps = -76;
			yft = -10;
		}
		else if(!ftbox.getValue() && !fpsbox.getValue()) {
			yfps = -66;
			yft = -20;
		}
		
		fr.drawStringWithShadow("Frametime: " + frametime, 2, 84 + yft, 0xFFFF00);
		fr.drawStringWithShadow("FPS:" + fps, 2, 140 + yfps, 0xFFFF00);
	}
	
	private static void drawFrameTimeGraph() {
		if(!ftbox.getValue()) {
			return;
		}
		GraphPoint prev = null;
		GraphPoint cur = null;
		R2DUtils.drawBetterRect(1, 94 + yft, 203, 134 + yft, 1, 0x00000000, 0xFF000000);
		
		for(int a = 0; a < ft_points.size(); ++a) {
			cur = ft_points.get(a);
			int offset1 = a;
			int offset2 = a - 1;
			int width = 1;
			if(prev == null) {
				R2DUtils.drawLine2D(cur.x + offset1, cur.y, cur.x + offset1, cur.y, width, 0xFFFF00FF);
			}
			else {
				R2DUtils.drawLine2D(cur.x + offset1, cur.y, prev.x + offset2, prev.y, width, 0xFFFF00FF);
			}
			
			prev = cur;
		}
	}
	
	private static void drawFPSGraph() {
		if(!fpsbox.getValue()) {
			return;
		}
		GraphPoint prev = null;
		GraphPoint cur = null;
		R2DUtils.drawBetterRect(1, 150 + yfps, 203, 190 + yfps, 1, 0x00000000, 0xFF000000);
		
		for(int a = 0; a < fps_points.size(); ++a) {
			cur = fps_points.get(a);
			int offset1 = a;
			int offset2 = a - 1;
			int width = 1;
			
			if(prev == null) {
				R2DUtils.drawLine2D(cur.x + offset1, cur.y, cur.x + offset1, cur.y, width, 0xFFFF00FF);
			}
			else {
				R2DUtils.drawLine2D(cur.x + offset1, cur.y, prev.x + offset2, prev.y, width, 0xFFFF00FF);
			}
			
			prev = cur;
		}
	}
	
	private static void makeFrameTimeGraph() {
		if(!ftbox.getValue()) {
			return;
		}
		if(ft_points.size() < 200) {
			if(frametime > 400)
				ft_points.add(new GraphPoint(2, 130 + yft, 400 / 4));
			else if(frametime == 0)
				ft_points.add(new GraphPoint(2, 130 + yft, 1));
			else
				ft_points.add(new GraphPoint(2, 130 + yft, frametime / 4));
		}
		else
			ft_points.remove(0);
	}
	
	private static void makeFPSGraph() {
		if(!fpsbox.getValue()) {
			return;
		}
		if(fps_points.size() < 200) {
			if(fps > 200)
				fps_points.add(new GraphPoint(2, 186 + yfps, 200 / 4));
			else
				fps_points.add(new GraphPoint(2, 186 + yfps, fps / 4));
		}
		else
			fps_points.remove(0);
	}
}
