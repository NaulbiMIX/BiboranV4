package hack.rawfish2d.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.gui.ingame.GUIStyle_Legacy;
import hack.rawfish2d.client.gui.ingame.IGuiElement;
import hack.rawfish2d.client.gui.ingame.WindowRadar;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityItem;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet28EntityVelocity;

public class Module {
	public static Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private int keyCode;
	protected boolean toggled;
	private ModuleType type;
	protected boolean doNotSendNextPacket;
	public String description;

	public boolean settings_visible = false;
	public float []xywhBUTTON = null;
	public float []xywhBG = null;
	
	public List<IGuiElement> elements = new ArrayList<IGuiElement>();

	/*
	public List<CheckBox> chbox = new ArrayList<CheckBox>();
	public List<NewSlider> sliders = new ArrayList<NewSlider>();
	public List<RadioBox> rbox = new ArrayList<RadioBox>();
	public List<InputBox> inputs = new ArrayList<InputBox>();
	public List<WindowItemsScroll> scrollwindows = new ArrayList<WindowItemsScroll>();
	*/
	
	public void calculateMenuSize() {
		xywhBG = null;
		if(xywhBG == null) {
			
			if(elements.size() == 0) {
				return;
			}
			
			xywhBG = new float[] {0, 0, 0, 0};

			int w = 0;
			int h = 0;
			for(IGuiElement ime : elements) {
				//int totalw = ime.getW() + mc.fontRenderer.getStringWidth(ime.text());
				
				int w1 = ime.getW();
				int w2 = ime.getTextWidth();
				int wtotal = 0;
				
				if(w1 > w2)
					wtotal = w1;
				else
					wtotal = w2;
				
				
				//int wtotal = ime.getTextWidth();
				
				if(w < wtotal)
					w = wtotal;
			}
			//w += 8;
			
			int maxy = elements.get(0).getY();
			for(IGuiElement ime : elements) {
				if(maxy < ime.getY())
					maxy = ime.getY();
			}
			
			h = maxy + 10;
			
			xywhBG[2] = w + 8;
			xywhBG[3] = h + 5;
		}

		xywhBG[0] = xywhBUTTON[0] + 87;
		xywhBG[1] = xywhBUTTON[1];
	}

	public Module(String name, int keyCode, ModuleType type) {
		this.name = name;
		this.keyCode = keyCode;
		this.toggled = false;
		this.type = type;
		this.doNotSendNextPacket = false;
		this.description = "null";
	}

	public void setDescription(String text) {
		this.description = text;
	}

	public void onCommand(String str) {}

	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public void doNotSendNextPacket(boolean b) {
		doNotSendNextPacket = b;
	}

	public boolean getDoNotSendNextPacket() {
		return doNotSendNextPacket;
	}

	public void onEnable() { }

	public void onDisable() { }

	public void onUpdate() { }

	public void onRender() { }

	public void onRenderHand() { }

	public void onRenderOverlay() { }

	public void preMotionUpdate() { }

	public void postMotionUpdate() { }

	public void afterOnAddPacketToQueue(Packet packet) {}

	public void onAddPacketToQueue(Packet packet) {}

	public void onKeyPress(int keyCode) { }

	public void onChatMessage(String msg) { }

	public void onPacket(Packet packet) {}

	public void onButtonRightClick() {}
	
	public void callAlways() {}

	public void onDrawSettings(int x, int y) {
		if(!settings_visible || xywhBUTTON == null)
			return;

		if(elements.size() == 0)
			return;
		
		boolean drawBG = true;
		if(elements.size() == 1) {
			if(elements.get(0).getH() == 0)
				drawBG = false;
		}
		
		if(drawBG) {
			calculateMenuSize();
			
			int border_color = GUIStyle_Legacy.getToolTipBorderColor();
			
			R2DUtils.drawBetterRect(xywhBG[0], xywhBG[1], xywhBG[0] + xywhBG[2], xywhBG[1] + xywhBG[3], 0.5f, 0xFF000000, border_color);
			R2DUtils.drawBetterRect(xywhBG[0], xywhBG[1], xywhBG[0] + xywhBG[2], xywhBG[1] + xywhBG[3], 0.5f, 0xFF000000, border_color);
		}

		for(IGuiElement ime : elements) {
			if(!(ime instanceof WindowRadar))
				ime.draw(x, y);
		}
	}

	public boolean DontProcessPacket(Packet packet) { return false; }

	//public boolean onRenderItem(EntityItem entityItem, double par2, double par4, double par6, float par8, float par9) {return false;}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public boolean isToggled() {
		return this.toggled;
	}

	public ModuleType getType() {
		return this.type;
	}
}
