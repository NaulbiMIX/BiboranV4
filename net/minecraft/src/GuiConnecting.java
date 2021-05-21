package net.minecraft.src;

import java.awt.Color;

import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{
	/** A reference to the NetClientHandler. */
	NetClientHandler clientHandler;

	/** True if the connection attempt has been cancelled. */
	private boolean cancelled = false;
	private final GuiScreen field_98098_c;
	public static String IP;
	public static String PORT;
	public static int PORTINT;
   
	public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, ServerData par3ServerData)
	{
		this.mc = par2Minecraft;
		this.field_98098_c = par1GuiScreen;
		ServerAddress var4 = ServerAddress.func_78860_a(par3ServerData.serverIP);
		par2Minecraft.loadWorld((WorldClient)null);
		par2Minecraft.setServerData(par3ServerData);
		/*
		if(!GuiDisconnected.yool.isEmpty()){
			GuiDisconnected.autojoin = GuiDisconnected.yool.get(0);
		}
		if(!GuiDisconnected.yoolf.isEmpty()){
			GuiDisconnected.autojoin = GuiDisconnected.yoolf.get(0);
		}
		*/
		this.spawnNewServerThread(var4.getIP(), var4.getPort());
		this.IP = var4.getIP();
		this.PORT = "" + var4.getPort();
		this.PORTINT = var4.getPort();
	}

	public GuiConnecting(GuiScreen par1GuiScreen, Minecraft par2Minecraft, String par3Str, int par4)
	{
		this.mc = par2Minecraft;
		this.field_98098_c = par1GuiScreen;
		par2Minecraft.loadWorld((WorldClient)null);
		this.spawnNewServerThread(par3Str, par4);
	}

	private void spawnNewServerThread(String par1Str, int par2)
	{
		this.mc.getLogAgent().logInfo("Connecting to " + par1Str + ", " + par2);
		(new ThreadConnectToServer(this, par1Str, par2)).start();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		if (this.clientHandler != null)
		{
			this.clientHandler.processReadPackets();
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
	
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0)
		{
			this.cancelled = true;

			if (this.clientHandler != null)
			{
				this.clientHandler.disconnect();
			}

			this.mc.displayGuiScreen(this.field_98098_c);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		//ultrakek
		this.drawDefaultBackground();
		//Color color1 = MiscUtils.int2Color(0x4F000000);
		//R2DUtils.drawBetterRect(0, 0, this.width, this.height, 1, color1, color1);
		//ultrakek
		
		StringTranslate var4 = StringTranslate.getInstance();
		if (this.clientHandler == null)
		{
			this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.connecting"), this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
		}
		else
		{
			this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.authorizing"), this.width / 2, this.height / 2 - 50, 16777215);
			this.drawCenteredString(this.fontRenderer, this.clientHandler.field_72560_a, this.width / 2, this.height / 2 - 10, 16777215);
		}

		super.drawScreen(par1, par2, par3);
	}

	/**
	 * Sets the NetClientHandler.
	 */
	static NetClientHandler setNetClientHandler(GuiConnecting par0GuiConnecting, NetClientHandler par1NetClientHandler)
	{
		return par0GuiConnecting.clientHandler = par1NetClientHandler;
	}

	static Minecraft func_74256_a(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}

	static boolean isCancelled(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.cancelled;
	}

	static Minecraft func_74254_c(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}

	/**
	 * Gets the NetClientHandler.
	 */
	static NetClientHandler getNetClientHandler(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.clientHandler;
	}

	static GuiScreen func_98097_e(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.field_98098_c;
	}

	static Minecraft func_74250_f(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}

	static Minecraft func_74251_g(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}

	static Minecraft func_98096_h(GuiConnecting par0GuiConnecting)
	{
		return par0GuiConnecting.mc;
	}
}
