package hack.rawfish2d.client.gui.menu;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ScreenText;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.Texture;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiDisconnected;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ServerData;
import net.minecraft.src.StringTranslate;

public class UGuiProxy extends GuiScreen {
	/** This GUI's parent GUI. */
	private GuiScreen parentGui;
	//private GuiTextField ip;
	//private GuiTextField port;
	
	private GuiTextField address;
	
	int xe = MiscUtils.getScreenWidth() / 2 - 100;
	int ye = MiscUtils.getScreenHeight() / 4 + 100;

	public UGuiProxy(GuiScreen parent)
	{
		this.parentGui = parent;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.buttonList.clear();
		this.buttonList.add(new GuiSmallButton(2, xe + 25, ye + 10, "Включить прокси: " + Client.getInstance().nth_use_proxy));
		this.buttonList.add(new GuiButton(0, xe, ye + 60, "Ок"));
		this.buttonList.add(new GuiButton(1, xe, ye + 90, "Отмена"));
		
		this.address = new GuiTextField(this.fontRenderer, xe, ye - 50, 200, 20);
		this.address.setFocused(true);
		this.address.setText(Client.getInstance().nth_proxy_ip + ":" + Client.getInstance().nth_proxy_port);
		
		/*
		this.ip = new GuiTextField(this.fontRenderer, xe, ye - 50, 200, 20);
		this.ip.setFocused(true);
		this.ip.setText(Client.getInstance().nth_proxy_ip);
		
		this.port = new GuiTextField(this.fontRenderer, xe, ye - 20, 200, 20);
		this.port.setFocused(false);
		this.port.setText("" + Client.getInstance().nth_proxy_port);
		*/
	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			if (par1GuiButton.id == 1)
			{
				this.parentGui.confirmClicked(false, 0);
			}
			else if (par1GuiButton.id == 2)
			{
				Client.getInstance().nth_use_proxy = !Client.getInstance().nth_use_proxy;
				GuiSmallButton gsb = (GuiSmallButton)buttonList.get(0);
				gsb.displayString = "UseProxy: " + Client.getInstance().nth_use_proxy;
			}
			else if (par1GuiButton.id == 0)
			{
				/*
				Client.getInstance().nth_proxy_ip = ip.getText();

				try {
					Client.getInstance().nth_proxy_port = Integer.parseInt(port.getText());
				} catch(Exception ex) {
					Client.getInstance().nth_proxy_port = 8080;
				}
				*/
				
				try {
					String []split = address.getText().split(":");
					
					Client.getInstance().nth_proxy_ip = split[0];
					
					Client.getInstance().nth_proxy_port = Integer.parseInt(split[1]);
					
				} catch(Exception ex) {
					address.setText("Ошибка");
				}

				this.parentGui.confirmClicked(true, 0);
			}
		}
	}

	protected void keyTyped(char par1, int par2)
	{
		//this.ip.textboxKeyTyped(par1, par2);
		//this.port.textboxKeyTyped(par1, par2);
		this.address.textboxKeyTyped(par1, par2);

		if (par1 == 9)
		{
			//this.ip.setFocused(true);
			//this.port.setFocused(true);
			this.address.setFocused(true);
		}

		if (par1 == 13)
		{
			this.actionPerformed((GuiButton)this.buttonList.get(0));
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		//this.ip.mouseClicked(par1, par2, par3);
		//this.port.mouseClicked(par1, par2, par3);
		this.address.mouseClicked(par1, par2, par3);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();

		UGuiMainMenu.instance.drawBackground();
		R2DUtils.drawBetterRect(0, 0, width, height, 1, 0x2F000000, 0x2F000000);

		this.drawCenteredString(this.fontRenderer, "Change Proxy", this.width / 2, 17, 0xFFFFFF);
		this.drawString(this.fontRenderer, "Enter proxy ip and port:", xe, ye - 65, 0xFFFFFF);
		
		//this.ip.drawTextBox();
		//this.port.drawTextBox();
		this.address.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
