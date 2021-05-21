package hack.rawfish2d.client.ModTest;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.Schematic.BlockData;
import hack.rawfish2d.client.Schematic.Schematic;
import hack.rawfish2d.client.Schematic.SchematicManager;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.InputBox;
import hack.rawfish2d.client.gui.ingame.ListBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.SharedStringList;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

public class SchemModLoad extends Module {
	public static SchematicManager scm = null;
	private DoubleValue maxDistance;
	private SharedStringList list; 	
	private BoolValue giveBlock;
	private BoolValue giveBlockUnder;
	private BoolValue drawText;
	private BoolValue drawBlock;
	private DoubleValue scale;
	
	private InputBox inputbox;
	private ListBox listbox;
	
	public SchemModLoad() {
		super("SchemModLoad", 0, ModuleType.TEST);
		setDescription("—” ¿  ¿  ∆≈ ﬂ «¿≈¡¿À—ﬂ —“–Œ»“‹ ›“» ≈¡¿Õ€≈ œ» —≈À‹ ¿–“€ œ»«ƒ≈÷");
		
		maxDistance = new DoubleValue(8, 0, 128);
		
		list = new SharedStringList();
		list.add("0");
		list.add("90");
		list.add("180");
		list.add("270");
		giveBlock = new BoolValue(true);
		giveBlockUnder = new BoolValue(true);
		drawText = new BoolValue(true);
		drawBlock = new BoolValue(true);
		scale = new DoubleValue(0.025, 0, 0.08);
		
		inputbox = new InputBox(this, "", 0, 0);
		this.elements.add(inputbox);
		this.elements.add(new CheckBox(this, "Give block", giveBlock, 0, 15));
		this.elements.add(new CheckBox(this, "Give block under", giveBlockUnder, 0, 25));
		this.elements.add(new CheckBox(this, "Draw text", drawText, 0, 35));
		this.elements.add(new CheckBox(this, "Draw block", drawBlock, 0, 45));
		this.elements.add(new NewSlider(this, "Max distance", maxDistance, 0, 55, false));
		this.elements.add(new NewSlider(this, "Scale", scale, 0, 75, false));
		listbox = new ListBox(this, "Rotation", list, 0, 95);
		this.elements.add(listbox);
	}
	
	@Override
	public void onEnable() {
		scm = new SchematicManager();
		
		scm.origin_x = Math.round(mc.thePlayer.posX);
		scm.origin_y = Math.round(mc.thePlayer.posY);
		scm.origin_z = Math.round(mc.thePlayer.posZ);
		
		scm.load(inputbox.getValue());
	}

	@Override
	public void onDisable() {}

	public BlockData getClosest() {
		double min = 0;
		BlockData closest = null;
		
		for(BlockData bd : scm.list) {
			Vector3f blpos = new Vector3f(bd.worldPos.x, bd.worldPos.y, bd.worldPos.z);
			
			double offset = 0.5d;
			
			if(giveBlockUnder.getValue())
				offset += 2.0d;
					
			Vector3f playerpos = new Vector3f(mc.thePlayer.posX - 0.5d, mc.thePlayer.posY - offset, mc.thePlayer.posZ - 0.5d);
			
			double distance = Math.sqrt(MiscUtils.getDistance(blpos, playerpos));
			
			if(min == 0) {
				min = distance;
				closest = bd;
			}
			
			if(distance < min) {
				min = distance;
				closest = bd;
			}
		}
		return closest;
	}
	
	@Override
	public void onUpdate() {
		if(!scm.isOk())
			toggle();
		
		float angle = 0;
		try {
			angle = Float.parseFloat(listbox.getValue());
		}
		catch(Exception ex) {
			return;
		}
		scm.rotate(angle);
		
		if(!giveBlock.getValue()) {
			return;
		}
		
		
		BlockData closest = getClosest();
		
		ItemStack curStack = mc.thePlayer.inventory.getCurrentItem();
		
		if(closest == null) {
			return;
		}
		
		if(closest.id == 0 || closest.id == 43) {
			return;
		}
		
		if(curStack == null) {
			ItemStack item = new ItemStack(closest.id, 1, closest.data);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
		}
		else if(curStack.itemID != closest.id || curStack.itemDamage != closest.data){
			ItemStack item = new ItemStack(closest.id, 1, closest.data);
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
		}
		
		//MiscUtils.sendChatClient("" + closest.id + ":" + closest.data);
	}

	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onRender() {
		if(!scm.isOk())
			toggle();
		/*
		double x2 = scm.origin_x + scm.getXSize() - 1;
		double y2 = scm.origin_y + scm.getYSize() - 1;
		double z2 = scm.origin_z + scm.getZSize() - 1;
		*/
		double x2 = scm.origin_x + scm.getZSize() - 1;
		double y2 = scm.origin_y + scm.getYSize() - 1;
		double z2 = scm.origin_z + scm.getXSize() - 1;
		
		double x = scm.origin_x - RenderManager.instance.viewerPosX;
		double y = scm.origin_y - RenderManager.instance.viewerPosY;
		double z = scm.origin_z - RenderManager.instance.viewerPosZ;
		
		double xx = x2 - RenderManager.instance.viewerPosX;
		double yy = y2 - RenderManager.instance.viewerPosY;
		double zz = z2 - RenderManager.instance.viewerPosZ;
		
		if(y < yy)
			yy += 1D;
		else if(yy < y)
			y += 1D;
		else if(y == yy)
			y += 1D;
		
		if(x < xx)
			xx += 1D;
		else if(xx < x)
			x += 1D;
		else if(x == xx)
			x += 1D;
		
		if(z < zz)
			zz += 1D;
		else if(zz < z)
			z += 1D;
		else if(z == zz)
			z += 1D;
		
		R3DUtils.drawRegionESP(x, y, z, xx, yy, zz, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 2);
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
		for(BlockData bd : scm.list) {
			GL11.glPushMatrix();
			drawBlock(bd);
			GL11.glPopMatrix();
		}
		
		BlockData closest = getClosest();
		if(closest == null) {
			return;
		}
		if(closest.id == 0) {
			return;
		}
		
		int color1 = 0x00000000;
		int color2 = 0xFFFFFF00;
		R3DUtils.drawBlockESP(closest.getRenderPos(), color1, color2, 1.5f);
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	public void drawBlock(BlockData bd) {
		if(bd == null) {
			return;
		}
		
		double distance = MiscUtils.getDistance(new Vector3f(bd.worldPos.x, bd.worldPos.y, bd.worldPos.z), new Vector3f(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
		distance = Math.sqrt(distance);
		if(distance > maxDistance.getValue())
			return;
		
		Block bl = MiscUtils.getBlock(bd.worldPos.x, bd.worldPos.y, bd.worldPos.z);
		if(bl != null) {
			if(bl.blockID == bd.id && bd.id == 21) {
				return;
			}
			
			if(bl.blockID == bd.id && bl.getDamageValue(mc.theWorld, bd.worldPos.x, bd.worldPos.y, bd.worldPos.z) == bd.data) {
				return;
			}
			else {
				int color1 = 0x660000FF;
				int color2 = 0xFFFF0000;
				R3DUtils.drawBlockESP(bd.getRenderPos(), color1, color2, 3f);
			}
		}
		
		if(bd.id == 0) {
			return;
		}
		
		FontRenderer fontRenderer = mc.fontRenderer;
		ItemStack curStack = new ItemStack(bd.id, 1, bd.data); //id, size, damage
		
		float translateDist = 6.5f;
		float scaleDist = 6.5f;
		
		RenderManager renderManager = RenderManager.instance;
		double camX = bd.getRenderX();
		double camY = bd.getRenderY();
		double camZ = bd.getRenderZ();
		
		GL11.glTranslatef((float)camX + 0.5f, (float)camY + 0.5f, (float)camZ + 0.5f);
		
		GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		
		float scale = (float) this.scale.getValue();
		
		GL11.glScaled(-scale, -scale, scale);
		
		if(curStack != null && drawBlock.getValue()) {
			if(curStack.getItem() != null) {
				//GL11.glDisable(GL11.GL_DEPTH_TEST);
				RenderHelper.enableGUIStandardItemLighting();
				RenderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, curStack, -8, -8);
				RenderHelper.disableStandardItemLighting();
				//GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}
		
		if(drawText.getValue()) {
			//GL11.glDisable(GL11.GL_DEPTH_TEST);
			fontRenderer.drawStringWithShadow("" + curStack.itemID + ":" + curStack.itemDamage, -10, 8, 0xFFFFFFFF);
			//GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}
	
	@Override
	public void onRenderOverlay() {
		
	}
}
