package hack.rawfish2d.client.ModRender;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

public class NoRenderItem extends Module{
	public static Module instance = null;
	
	public NoRenderItem() {
		super("NoRenderItem", 0, ModuleType.RENDER);
		setDescription("Не рендерит дроп");
		instance = this;
	}
	
	@Override
	public void onRenderOverlay() {
		String str = "E:" + mc.renderGlobal.countEntitiesTotal;
		int strw = Client.getInstance().mc.fontRenderer.getStringWidth(str);
		int x = MiscUtils.getScreenWidth() / 2 - (strw / 2);
		int y = 12;
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, 0xFFFFFFFF);
	}
}
