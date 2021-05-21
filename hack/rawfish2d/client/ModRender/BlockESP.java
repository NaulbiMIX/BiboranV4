package hack.rawfish2d.client.ModRender;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.InputBox;
import hack.rawfish2d.client.gui.ingame.WindowItemsScroll;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.Coordinates;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.SharedIntList;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.RenderManager;

public class BlockESP extends Module {
	public static Module instance = null;
	private SharedIntList blocks;
	private CopyOnWriteArrayList<Coordinates> renderList;
	private int opacity;
	private TimeHelper time;
	private InputBox inputbox;
	
	public BlockESP() {
		super("BlockESP", 0, ModuleType.RENDER);
		setDescription("Подсвечивает блоки");
		instance = this;
		blocks = new SharedIntList();
		renderList = new CopyOnWriteArrayList<Coordinates>();
		opacity = 0;
		time = new TimeHelper();
		inputbox = new InputBox(this, "KEK", 0, 0);
		this.elements.add(inputbox);
		
		this.elements.add(new WindowItemsScroll(this, "BlockESP Settings", blocks, 1, 160, 300, 200));
	}
	
	public void fastReRender() {
	    if (mc.thePlayer != null && mc.theWorld != null) {
	        int x = (int)mc.thePlayer.posX;
	        int y = (int)mc.thePlayer.posY;
	        int z = (int)mc.thePlayer.posZ;
	        mc.renderGlobal.markBlockRangeForRenderUpdate(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200);
	    }
	}
	
	@Override
	public void onEnable() {
		time.reset();
		addBlockToRenderList();
	}
	
	@Override
	public void onDisable() {
		//MiscUtils.sendChatClient("text:" + inputbox.getValue());
	}
	
	@Override
	public void onRenderHand() {
		if(time.hasReached(1000L)) {
			addBlockToRenderList();
			time.reset();
		}
	}
	
	@Override
	public void onButtonRightClick() {
		
	}
	
	@Override
	public void onRender() {
		for(Coordinates coord : this.renderList) {
			draw(coord);
		}
	}
	
	private void addBlockToRenderList() {
		final int maxH = 255;
		final int minH = 1;
		final int maxW = 50;
		final int minW = -50;
		
		renderList.clear();
		
		for(int x = minW; x < maxW; ++x) {
			for(int z = minW; z < maxW; ++z) {
				for(int y = minH; y < maxH; ++y) {
					int xPos = (int)Math.round(mc.thePlayer.posX + (double)x);
					int yPos = (int)Math.round((double)y);
					int zPos = (int)Math.round(mc.thePlayer.posZ + (double)z);
					Block block = Block.blocksList[this.mc.theWorld.getBlockId(xPos, yPos, zPos)];
					
					if(block == null)
						continue;
					
					if(blocks.contains(block.blockID)) {
						this.renderList.add(new Coordinates(xPos, yPos, zPos));
					}
				}
			}
		}
	}
	
	public void draw(Coordinates coords) {
		if(coords == null)
			return;
		
		final int color1 = 0x44FF00FF;
		final int color2 = 0xFF000000;
		
		R3DUtils.drawBlockESP(coords.getRenderPos(), color1, color2, 1.0f);
	}
}
