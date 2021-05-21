package hack.rawfish2d.client.ModRender;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnderChest;

public class ChestESP extends Module {
	public static Module instance = null;
	
	public ChestESP() {
		super("ChestESP", Keyboard.KEY_U, ModuleType.RENDER);
		setDescription("Сундуки подсвечиваются через стены");
		instance = this;
	}
	
	@Override
	public void onRenderHand() {
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		Iterator it = mc.theWorld.loadedTileEntityList.iterator();
		
		while (it.hasNext()) {
			Object obj = it.next();
			
			if (obj instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest)obj;
				
				if (chest.xCoord != 0 && chest.yCoord != 0 && chest.zCoord != 0) {
					double x = chest.xCoord - RenderManager.renderPosX;
					double y = chest.yCoord - RenderManager.renderPosY;
					double z = chest.zCoord - RenderManager.renderPosZ;
					
					GL11.glColor4f(0.6F, 0.6F, 0.0F, 1.0f);
					render(x, y, z);
				}
			}
			
			if (obj instanceof TileEntityEnderChest) {
				TileEntityEnderChest chest = (TileEntityEnderChest)obj;
				
				if (chest.xCoord != 0 && chest.yCoord != 0 && chest.zCoord != 0) {
					double x = chest.xCoord - RenderManager.renderPosX;
					double y = chest.yCoord - RenderManager.renderPosY;
					double z = chest.zCoord - RenderManager.renderPosZ;
					
					GL11.glColor4f(0.6F, 0.0F, 0.6F, 1.0f);
					render(x, y, z);
				}
			}
		}
		GL11.glColor3f(0F, 0F, 0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void render(double x, double y, double z)
	{
		//R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x + 1.0D, y + 1.0D, z + 1.0D, x, y, z));
	}
}
