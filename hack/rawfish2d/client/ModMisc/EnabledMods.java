package hack.rawfish2d.client.ModMisc;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ScaledResolution;

public class EnabledMods extends Module {
	private class ModText {
		private Module mod;
		private int color;
		private TimeHelper time;
		private float offset;
		private float w;
		
		ModText(Module mod, int color) {
			this.mod = mod;
			this.color = color;
			
			this.time = new TimeHelper();
			this.time.reset();
			this.offset = getStrW(mod.getName());
			this.w = this.offset;
		}
		
		public int compare(Object arg0) {
			ModText mod1 = (ModText)arg0;
			ModText mod2 = this;
			
			double var1 = getStrW(mod1.getText());
			double var2 = getStrW(mod2.getText());
			
			if(var1 > var2) return -1;
			else if(var1 < var2) return 1;
			else return 0;
		}
		
		public String getText() {
			return mod.getName();
		}
		
		public int getColor() {
			if(rainbow.getValue())
				return MiscUtils.getRainbow();
			else
				return color;
		}
		
		public float getOffset() {
			return offset;
		}
		
		public float getW() {
			return w;
		}
		
		public boolean shouldDraw() {
			if(mod.isToggled()) {
				return true;
			}
			else if(!mod.isToggled() && this.offset < this.w) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public void draw(int y) {
			if(mod.isToggled())
				anim_enabled();
			else
				anim_disabled();
			
			this.w = getStrW(mod.getName());
			final float offset_x = 2 + this.offset;
			final int bg_color = 0xFF000000;
			
			R2DUtils.drawRect(width - getW() + offset_x - 8, y + 1, width - 2 + offset_x, y + 11, bg_color);
			R2DUtils.drawRect(width - getW() + offset_x - 8, y + 1, width - getW() + 2 + offset_x - 8, y + 11, getColor());
			
			R2DUtils.drawScaledFont(getText(), scale, (int)(width - getW() - 4 + offset_x), y, 0xCCFFFFFF);
		}
		
		public void anim_enabled() {
			if(this.time.hasReached(15L) && this.getOffset() != 0) {
				if(this.getOffset() > 0) {
					this.offset--;
				}
				else if(this.getOffset() < 0) {
					this.offset = 0;
				}
				
				this.time.reset();
			}
		}
		
		public void anim_disabled() {
			if(this.time.hasReached(15L) && this.getOffset() != this.getW()) {
				if(this.getOffset() < this.getW()) {
					this.offset++;
				}
				else if(this.getOffset() > this.getW()) {
					this.offset = this.getW();
				}
				
				this.time.reset();
			}
		}
	}
	
	public static CopyOnWriteArrayList<ModText> list = new CopyOnWriteArrayList();
	
	public static Module instance = null;
	//private FontRenderer fr = null;
	private static int scale = 18;
	private static int width;
	private static int height;
	private static ScaledResolution sr;
	
	private static int mode = 1;
	private static int number = 0;
	private static final int colorCombat = 0xFF0000;
	private static final int colorMisc = 0xFFFF00;
	private static final int colorMovement = 0x00FF00;
	private static final int colorRender = 0x00FFFF;
	private static final int colorTest = 0xFF00FF;
	
	public static BoolValue defaultMode = null;
	public static BoolValue Nifee = null;
	public static BoolValue rainbow = null;
	
	public EnabledMods() {
		super("EnabledMods", 0, ModuleType.MISC);
		setDescription("Показывает включенные функции чита. Режим Nifee это челендж от Serj :)");
		//fr = Client.getInstance().mc.fontRenderer;
		instance = this;
		
		defaultMode = new BoolValue(false);
		Nifee = new BoolValue(true);
		rainbow = new BoolValue(false);
		
		elements.add(new RadioBox(this, "Default mode", defaultMode, 0, 0));
		elements.add(new RadioBox(this, "Nifee mode", Nifee, 0, 10));
		elements.add(new CheckBox(this, "Rainbow", rainbow, 0, 20));
	}

	@Override
	public void onRenderOverlay() {	
		if(this.mc.gameSettings.showDebugInfo == true)
			return;
		
		//drawEnabledMods_2();
		
		if(defaultMode.getValue()) {
			drawEnabledMods_1();
			return;
		}
		/*
		if(mode2.getValue()) {
			drawEnabledMods_2();
			return;
		}
		*/
		if(Nifee.getValue()) {
			makeList();
			drawList();
			return;
		}
	}
	
	public int getStrW(String str) {
		int strw = 0;
		if(mode == 0) {
			strw = Client.getInstance().mc.fontRenderer.getStringWidth(str);
		}
		else if(mode == 1) {
			strw = (int) R2DUtils.getScaledFontWidth(str, scale);
		}
		return strw;
	}
	
	@Override
	public void onDisable() {
		list.clear();
	}
	
	@Override
	public void onEnable() {
		list.clear();
	}
	
	private void makeList() {
		for(Module m : Client.getInstance().getModules()) {
			if(m.isToggled() && m.getType() != ModuleType.NONE) {
			//if(m.isToggled()) {
				boolean exist = false;
				for(ModText mt : list) {
					if(mt.mod == m) {
						exist = true;
						break;
					}
				}
				
				if(!exist) {
					final int color = getModuleColor(m.getType());
					list.add(new ModText(m, color));
				}
			}
		}
		
		//java 8
		//Collections.sort(list, (ModText a, ModText b) -> b.compare(a));
		
		//java 7
		
		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ModText mod1 = (ModText)arg0;
				ModText mod2 = (ModText)arg1;
				
				double var1 = getStrW(mod1.getText());
				double var2 = getStrW(mod2.getText());
				
				if(var1 > var2) return -1;
				else if(var1 < var2) return 1;
				else return 0;
			}
		});
		
	}
	
	private void drawList() {
		final int size_y = 11;
		
		number = 0;
		sr = MiscUtils.getScaledResolution();
		width = sr.getScaledWidth();
		height = sr.getScaledHeight();
		
		for(ModText m : list) {
			if(m.shouldDraw()) {
				int y = 1 + (size_y * number);
				m.draw(y);
				number++;
			}
		}
	}
	
	private int getModuleColor(ModuleType type) {
		int color = 0xFF00FF;
		if(type == ModuleType.COMBAT)
			color = colorCombat;
		else if(type == ModuleType.MISC)
			color = colorMisc;
		else if(type == ModuleType.MOVEMENT)
			color = colorMovement;
		else if(type == ModuleType.RENDER)
			color = colorRender;
		else if(type == ModuleType.TEST)
			color = colorTest;
		
		if(rainbow.getValue())
			return MiscUtils.getRainbow();
		else
			return color + 0xFF000000;
	}
	
	private void drawString(String str, int x, int y, ModuleType type) {
		int color = getModuleColor(type);
		
		mode = 1;
		int strw = getStrW(str);
		if(mode == 0) {
			Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, width - strw - 4, y, color);
		}
		else if(mode == 1) {
			R2DUtils.drawScaledFont(str, scale, width - strw - 4, y, color);
		}
	}
	
	private void drawEnabledMods_1() {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		sr = MiscUtils.getScaledResolution();
		width = sr.getScaledWidth();
		height = sr.getScaledHeight();
		
		ModuleType []types = ModuleType.values();
		for(int a = 0; a < types.length; ++a) {
			for(Module m : Client.getInstance().getModules()){
				int x = 10;
				int y = 1 + (10 * number);
				
				if(m.isToggled()) {
					String str = m.getName();
					
					if(str == getName())
						continue;
					
					if(m.getType() == types[a]) {
						drawString(str, x, y, m.getType());
						number++;
					}
				}
			}
		}
		
		number = 0;
		GL11.glPopMatrix();
	}
	
	private void drawEnabledMods_2() {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		sr = MiscUtils.getScaledResolution();
		width = sr.getScaledWidth();
		height = sr.getScaledHeight();
		
		int offset_x = 2;
		int size_y = 11;
		int bg_color = 0xFF000000;
		
		ModuleType []types = ModuleType.values();
		for(int a = 0; a < types.length; ++a) {
			for(Module m : Client.getInstance().getModules()){
				int y = 1 + (size_y * number);
				
				if(m.isToggled()) {
					String str = m.getName();
					
					if(str == getName())
						continue;
					
					if(m.getType() == types[a]) {
						float str_w = R2DUtils.getScaledFontWidth(str, scale) + 8;
						R2DUtils.drawRect(width - str_w + offset_x, y + 1, width - 2 + offset_x, y + 11, bg_color);
						R2DUtils.drawRect(width - str_w + offset_x, y + 1, width - str_w + 2 + offset_x, y + 11,  getModuleColor(m.getType()));
						
						int strw = (int)R2DUtils.getScaledFontWidth(str, scale);
						R2DUtils.drawScaledFont(str, scale, width - strw - 4 + offset_x, y, getModuleColor(m.getType()));
						number++;
					}
				}
			}
		}
		
		number = 0;
		GL11.glPopMatrix();
	}
}
