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

public class UGuiChangeNickname extends GuiScreen {
	/** This GUI's parent GUI. */
	private GuiScreen parentGui;
	private GuiTextField nickname;
	int xe = MiscUtils.getScreenWidth() / 2 - 100;
	int ye = MiscUtils.getScreenHeight() / 4 + 100;

	public UGuiChangeNickname(GuiScreen parent)
	{
		this.parentGui = parent;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, xe, ye, "Ok"));
		this.buttonList.add(new GuiButton(1, xe, ye + 30, "Cancel"));

		this.nickname = new GuiTextField(this.fontRenderer, this.width / 2 - 100, ye - 50, 200, 20);
		this.nickname.setFocused(true);
		this.nickname.setText(mc.session.username);
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
			else if (par1GuiButton.id == 0)
			{
				//this.newServerData.serverName = this.serverName.getText();
				//this.newServerData.serverIP = this.serverAddress.getText();
				Client.getInstance().changeName(nickname.getText());
				this.parentGui.confirmClicked(true, 0);
			}
		}
	}

	protected void keyTyped(char par1, int par2)
	{
		this.nickname.textboxKeyTyped(par1, par2);
		//this.serverName.textboxKeyTyped(par1, par2);
		//this.serverAddress.textboxKeyTyped(par1, par2);

		if (par1 == 9)
		{
			this.nickname.setFocused(true);
		}

		if (par1 == 13)
		{
			this.actionPerformed((GuiButton)this.buttonList.get(0));
		}

		//((GuiButton)this.buttonList.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0;
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.nickname.mouseClicked(par1, par2, par3);
		//this.serverAddress.mouseClicked(par1, par2, par3);
		//this.serverName.mouseClicked(par1, par2, par3);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();

		UGuiMainMenu.instance.drawBackground();
		//this.drawGradientRect(0, 0, this.width, this.height, 0x80101010, 0x80101010);
		R2DUtils.drawBetterRect(0, 0, width, height, 1, 0x2F000000, 0x2F000000);

		this.drawCenteredString(this.fontRenderer, "Change Nickname", this.width / 2, 17, 0xFFFFFF);
		this.drawString(this.fontRenderer, "Enter nickname:", this.width / 2 - 100, ye - 65, 0xA0A0A0);
		//this.drawString(this.fontRenderer, var4.translateKey("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
		this.nickname.drawTextBox();
		//this.serverName.drawTextBox();
		//this.serverAddress.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
