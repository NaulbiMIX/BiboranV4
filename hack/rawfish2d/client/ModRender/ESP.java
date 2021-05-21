package hack.rawfish2d.client.ModRender;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.gui.ingame.RadioBox;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiChest;
import net.minecraft.src.InventoryBasic;
import net.minecraft.src.RenderManager;

public class ESP extends Module {
	public static int color = 0;
	public static double r = 0;
	public static double g = 0;
	public static double b = 0;
	
	public static BoolValue BVmode1;
	public static BoolValue BVmode2;
	public static BoolValue BVmode3;
	
	public static DoubleValue DVr;
	public static DoubleValue DVg;
	public static DoubleValue DVb;
	
	public static BoolValue BVrainbow;
	
	public ESP() {
		super("ESP", 0, ModuleType.RENDER);
		setDescription("Игроков видно сквозь стены");
		BVmode1 = new BoolValue(false);
		BVmode2 = new BoolValue(false);
		BVmode3 = new BoolValue(true);
		
		elements.add(new RadioBox(this, "3D Box", BVmode1, 0, 0));
		elements.add(new RadioBox(this, "2D Box", BVmode2, 0, 10));
		elements.add(new RadioBox(this, "2D Box2", BVmode3, 0, 20));
		
		DVr = new DoubleValue(0.0, 0, 1.01);
		DVg = new DoubleValue(1.0, 0, 1.01);
		DVb = new DoubleValue(0.0, 0, 1.01);
		
		elements.add(new NewSlider(this, "Red Color", DVr, 0, 30, false));
		elements.add(new NewSlider(this, "Green Color", DVg, 0, 50, false));
		elements.add(new NewSlider(this, "Blue Color", DVb, 0, 70, false));
		
		BVrainbow = new BoolValue(false);
		
		elements.add(new CheckBox(this, "Rainbow Box?", BVrainbow, 0, 90));
		
		r = DVr.getValue();
		g = DVg.getValue();
		b = DVb.getValue();
	}
	
	@Override
	public void onRender() {
		//if(mc.currentScreen != null)
		//	System.out.println("" + mc.currentScreen);
		/*
		String botname = "IWasTesting0";
		PBot bot = PBotCmd.instance.getBotByName(botname);
		if(bot != null && Client.mc.thePlayer != null) {
			InventoryBasic invb = new InventoryBasic(botname, false, bot.pbotth.inventory.length - 9);
			
			//Client.mc.thePlayer.displayGUIChest(invb);
			mc.displayGuiScreen(new GuiChest(Client.mc.thePlayer.inventory, invb));
			
			for(int a = 0; a < bot.pbotth.inventory.length; ++a) {
				invb.setInventorySlotContents(a, bot.pbotth.inventory[a]);
			}
		}
		*/
		for (int a = 0; a < mc.theWorld.playerEntities.size(); ++a) {
			Object obj = mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			EntityPlayer ent = (EntityPlayer)obj;

			if( !MiscUtils.isValidRenderTarget(ent, 255, false) )
				continue;
			
			draw(ent);
		}
	}
	
	public void draw(Entity entity) {
		if(entity == null)
			return;
		
		double xpos = 0;
		double ypos = 0;
		double zpos = 0;
		
		xpos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		ypos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		zpos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		render(xpos, ypos, zpos, entity.width, entity.height, entity);
	}
	
	public void render(double x, double y, double z, float w, float h, Entity ent) {
		
		r = DVr.getValue();
		g = DVg.getValue();
		b = DVb.getValue();
		
		if(BVrainbow.getValue())
		{
			if(r < 1.0F && color == 0) {
				r += 0.01F;
			}
			else if(g < 1.0F && color == 1) {
				g += 0.01F;
			}
			else if(b < 1.0F && color == 2) {
				b += 0.01F;
			}
			
			if(r >= 1.0F) {
				r = 0;
				color = 1;
			}
			else if(g >= 1.0F) {
				g = 0;
				color = 2;
			}
			else if(b >= 1.0F) {
				b = 0;
				color = 0;
			}
		}
		
		if(!MiscUtils.isEntityVisable(ent)) {
			r = 1;
			g = 0;
			b = 0;
		}
		
		if(BVmode1.getValue())
			R3DUtils.drawEntityESP(x, y, z, w - (w / 5), h + 0.2D, (float)r, (float)g, (float)b, 0.20F, 0F, 0F, 0F, 1F, 1F);
		
		if(BVmode2.getValue())
			R3DUtils.drawEntityESP2(x, y, z, (EntityPlayer) ent, (float)r, (float)g, (float)b, 1F, 2F);
		
		if(BVmode3.getValue())
			R3DUtils.drawEntityESP4(x, y, z, (EntityPlayer) ent, (float)r, (float)g, (float)b, 1F, 2F);
	}
}
