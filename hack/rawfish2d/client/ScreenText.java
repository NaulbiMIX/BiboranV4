package hack.rawfish2d.client;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import java.util.Random;

import hack.rawfish2d.client.ModUnused.Spam;
import hack.rawfish2d.client.utils.ColorUtil;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TestUtils;
import net.minecraft.client.Minecraft;

public class ScreenText extends GuiScreen {
	private String version_static;

	public ScreenText() {
		this.mc = Minecraft.getMinecraft();
		this.version_static = Client.version + " " + Client.premium_or_public;
	}

	public String getClientStr() {
		String str =  "§c" + Client.client_name + " §6" + Client.version + " §e" + Client.premium_or_public;
		str = TestUtils.make2(str);
		return str;
	}

	public String getStaticTitleClientStr() {
		return Client.client_name + " " + version_static + " " + Client.author;
	}

	public String getMainMenuClientStr() {
		return "§c" + Client.client_name + " §6" + Client.version + " §e" + Client.premium_or_public + " §a" + Client.author;
	}

	public void renderScreen() {
		//R2DUtils.drawbigfont("Biboran V4", 1, 2, 0xFF00FF00);
		/*
		String hackname = "";
		//hackname = "Kevin Client v6.9";
		//hackname = "FalloutShelter";
		hackname = "DoomsDay B4";
		
		int watermarkColor = ColorUtil.rainbow((long) 1f, 1f).getRGB();
		int bg = 0xFF000000;
		int border = 0xFF0000FF;
		
		int textColor = 0xFFFFFFFF;
		int buttonGrad1 = 0xFF2222AA; //0xFFAA22AA
		int buttonGrad2 = 0xFF222266; //0xFF662266
		
		R2DUtils.drawScaledFont(hackname, 48, 3, 3, 0xFF000000);
		R2DUtils.drawScaledFont(hackname, 48, 2, 2, watermarkColor);
		
		double x = 4;
		double y = 40;
		
		R2DUtils.drawBetterRect(x, y, x + 92, y + 12, 1, bg, border);
		GuiUtils.drawCenteredString("Exploits", 32, 42, 0xFFFFFFFF);
		
		x += 94;
		R2DUtils.drawBetterRect(x, y, x + 12, y + 12, 1, bg, border);
		
		x += 14;
		R2DUtils.drawBetterRect(x, y, x + 12, y + 12, 1, bg, border);
		
		x = 4;
		y += 14;
		R2DUtils.drawBetterRect(x, y, x + 120, y + 111, 1, bg, border);
		
		x = 5;
		y = 56;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Краш табом", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Получить опку", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Получить хелперку", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Эксплойт по 9 UDP протоколу", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Взломать север", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Перебан", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		//GuiUtils.drawCenteredString("Расшифровать DoomsDay", (int)x, (int)y, 0xFFFFFFFF);
		GuiUtils.drawCenteredString("Скачать чит Смока", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("Спам двумя ботами", (int)x, (int)y, 0xFFFFFFFF);
		x = 5;
		y += 12;
		R2DUtils.drawGradientBorderedRect((int)x - 1, (int)y - 1, (int)x + 118, (int)y + 12, 1, textColor, buttonGrad1, buttonGrad2);
		GuiUtils.drawCenteredString("[Premium] Спам тремя ботами", (int)x, (int)y, 0xFFFFFFFF);
		*/
		drawString(Client.getInstance().mc.fontRenderer, getClientStr(), 4, 4, 0xFFFFFFFF); //SHADOW
	}

	public String getModulesCountStr() {
		return "§7Loaded modules §2[§c" + Client.getInstance().getModules().size() + "§2]";
	}

	public String getBuildStr() {
		return "§7Build " + Client.build;
	}
}
