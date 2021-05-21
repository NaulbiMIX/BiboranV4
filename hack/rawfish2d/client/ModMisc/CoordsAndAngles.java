package hack.rawfish2d.client.ModMisc;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ScaledResolution;

public class CoordsAndAngles extends Module {
	public static Module instance = null;
	
	public CoordsAndAngles() {
		super("Coords&Angles", 0, ModuleType.MISC);
		setDescription("Ïîêàçûâàåò êîîðäèíàòû è óãëû êàìåðû");
		instance = this;
	}

	@Override
	public void onRenderOverlay() {	
		if(this.mc.gameSettings.showDebugInfo == true)
			return;
		
		ScaledResolution sr = MiscUtils.getScaledResolution();
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		
		float pitch = mc.thePlayer.rotationPitch;
		float yaw = mc.thePlayer.rotationYaw;
		
		while(yaw > 360f)
			yaw -= 360f;
		
		while(yaw < 0f)
			yaw += 360f;
		
		/*
		pitch = Math.round(pitch);
		yaw = Math.round(yaw);
		*/
		pitch = (float) (Math.floor(pitch * 100) / 100.0);
		yaw = (float) (Math.floor(yaw * 100) / 100.0);
		
		Client.getInstance().mc.fontRenderer.drawStringWithShadow("§aPitch §e" + pitch, 5, 15, 0xFF0000);
		Client.getInstance().mc.fontRenderer.drawStringWithShadow("§aYaw §e" + yaw, 5, 25, 0xFF0000);
		
		/*
		float x = Math.round(mc.thePlayer.posX);
		float y = Math.round(mc.thePlayer.posY);
		float z = Math.round(mc.thePlayer.posZ);
		*/
		
		float x = (float) mc.thePlayer.posX;
		float y = (float) mc.thePlayer.posY;
		float z = (float) mc.thePlayer.posZ;
		
		x = (float) (Math.floor(x * 100) / 100.0);
		y = (float) (Math.floor(y * 100) / 100.0);
		z = (float) (Math.floor(z * 100) / 100.0);
		
		String coords = "§cX: §e" + x + " §cY: §e" + y + " §cZ: §e" + z;
		if(!Client.getInstance().mc.ingameGUI.persistantChatGUI.getChatOpen())
			Client.getInstance().mc.fontRenderer.drawStringWithShadow(coords, 5, height - 8, 0xFF0000);
		else
			Client.getInstance().mc.fontRenderer.drawStringWithShadow(coords, 5, height - 24, 0xFF0000);
		
		/*
		Client.getInstance().mc.fontRenderer.drawStringWithShadow("§aX §e" + x, 5, height - 30, 0xFF0000);
		Client.getInstance().mc.fontRenderer.drawStringWithShadow("§aY §e" + y, 5, height - 20, 0xFF0000);
		Client.getInstance().mc.fontRenderer.drawStringWithShadow("§aZ §e" + z, 5, height - 10, 0xFF0000);
		*/
		//fr.drawStringWithShadow("X [" + Client.getInstance().getRotationUtils().getPitch() + "]", 5, 45, 0xFF0000);
		//fr.drawStringWithShadow("Y [" + Client.getInstance().getRotationUtils().getYaw() + "]", 5, 55, 0xFF0000);
	}
}
