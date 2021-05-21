package net.minecraft.src;

import java.awt.Color;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.client.Minecraft;

public class GuiDisconnected extends GuiScreen
{
	//public static List<Boolean> yool = new ArrayList<Boolean>();
	//public static List<Boolean> yoolf = new ArrayList<Boolean>();

	/** The error message. */
	private String errorMessage;
	public static boolean autojoin = false;
	/** The details about the error. */
	private boolean cancelled = false;
	private String errorDetail;
	private Object[] field_74247_c;
	private List field_74245_d;
	private final GuiScreen field_98098_c;
	private NetClientHandler clientHandler;
	private final GuiScreen field_98095_n;
	String ip = GuiConnecting.IP;
	TimeHelper timer = new TimeHelper();
	int port = GuiConnecting.PORTINT;
	
	public static int time = 0;
	public static int timeMax = 5;

	public GuiDisconnected(GuiScreen par1GuiScreen, String par2Str, String par3Str, Object ... par4ArrayOfObj)
	{
		this.mc = mc.theMinecraft;
		this.field_98098_c = par1GuiScreen;
		StringTranslate var5 = StringTranslate.getInstance();
		this.field_98095_n = par1GuiScreen;
		this.errorMessage = var5.translateKey(par2Str);
		this.errorDetail = par3Str;
		this.field_74247_c = par4ArrayOfObj;
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
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, (this.height / 4) * (2) + 30, var1.translateKey("gui.toMenu")));
		//ultrakek
		this.buttonList.add(new GuiSmallButton(2, this.width / 2 - 75, (this.height / 4) * (2) + 55, var1.translateKey("Авто подключение")));

		if (this.field_74247_c != null)
		{
			this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKeyFormat(this.errorDetail, this.field_74247_c), this.width - 50);
		}
		else
		{
			this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(var1.translateKey(this.errorDetail), this.width - 50);
		}
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 0) {
			this.mc.displayGuiScreen(this.field_98095_n);
		}
		if(par1GuiButton.id == 2){
			this.autojoin = !this.autojoin;
			this.time = timeMax;
			//reJoin();
			/*
			if(yoolf.contains(autojoin)){
				return;
			}
			this.yoolf.add(autojoin);
			*/
		}
	}	

	//ultrakek
	public void reJoin(){
		if(this.autojoin == false){
			return;
		}
			
			for(Object b : this.buttonList){
				if(b instanceof GuiSmallButton){
					GuiSmallButton s = (GuiSmallButton)b;
					if(s.displayString.equalsIgnoreCase("Авто подключение")){
						if(this.timer.hasReached(1000L)){
							if(time <= 0){
								time = timeMax;
								int ports = Integer.parseInt(GuiConnecting.PORT);
								connect(ip, ports, this, mc);
								//connect(ip, port, this, mc);
							}	
							time--;
							timer.reset();
						}
						s.displayString = "Отмена §c["+time+"]";
					}
				}
			}
			
			/*
			long timems = System.currentTimeMillis();
			timems += 20000;
			double time = timems;
			System.out.println("time: " + time);
			*/
			/*
			while(true) {
				long timems = System.currentTimeMillis();
				double time = timems;
				
				double targettime = 1.545198585781E12;
				
				System.out.println("time: " + time);
				System.out.println("targettime: " + targettime);
				
				if(time >= targettime) {
					autojoin = false;
					int ports = Integer.parseInt(GuiConnecting.PORT);
					connect(ip, ports, this, mc);
					return;
				}
			}
			*/
			/*
			while(true) {
				long timems = System.currentTimeMillis();
				BigInteger time = BigInteger.valueOf(timems);
				
				//BigInteger target_time = BigInteger.valueOf(Integer.parseInt(new String("1545197734263")));
				//BigInteger target_time = new BigInteger(1545197734263);
				//BigInteger target_time = Biglnteger.vaiueOf(long x)
				
				System.out.println("time: " + time);
				if(time.compareTo(target_time) >= 0) {
					autojoin = false;
					int ports = Integer.parseInt(GuiConnecting.PORT);
					connect(ip, ports, this, mc);
					return;
				}
			}
			*/
		}
	
	public void connect(String ip, int port, GuiScreen gui, Minecraft mc) {
		mc.setServer(ip, port);
		mc.setServerData(new ServerData("§aConnecing..", ip));
		mc.displayGuiScreen(new GuiConnecting(this.field_98098_c, mc, ip, port));
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		
		//ultrakek
		//Color color1 = MiscUtils.int2Color(0x4F000000);
		//R2DUtils.drawBetterRect(0, 0, this.width, this.height, 1, color1, color1);
		//ultrakek
		
		this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 11184810);
		int var4 = this.height / 2 - 30;
				
		if (this.field_74245_d != null) {
			
			for (Iterator var5 = this.field_74245_d.iterator(); var5.hasNext(); var4 += this.fontRenderer.FONT_HEIGHT) {
				String var6 = (String)var5.next();
				this.drawCenteredString(this.fontRenderer, var6, this.width / 2, var4, 16777215);
			}
			
			//ultrakek
			/*
			String msg1 = "";
			msg1 += "§cКикнут при подключении к lobby: §eВас забанил §c";
			msg1 += "Ddos1337";
			msg1 += "§e. Разбан через: §b";
			msg1 += "4 ч 59 мин 59 сек";
			msg1 += "§e. Причина: §a";
			msg1 += "лож бан ";
			
			String msg2 = "";
			
			String msg3 = "";
			msg3 += "§eРазбан на сайте §dCACTUSC.RU";
			
			List<String> msglist = new ArrayList<String>();
			msglist.add(msg1);
			msglist.add(msg2);
			msglist.add(msg3);
			
			for(int a = 0; a < msglist.size(); ++a, var4 += this.fontRenderer.FONT_HEIGHT) {
				this.drawCenteredString(this.fontRenderer, msglist.get(a), this.width / 2, var4, 16777215);
			}
			*/
		}
		super.drawScreen(par1, par2, par3);
		
		for(Object b : this.buttonList){
			if(b instanceof GuiSmallButton){
				GuiSmallButton s = (GuiSmallButton)b;
				if(s.displayString.startsWith("Отмена")){
					s.displayString = "Авто подключение";
				}
			}
		}
		reJoin();
	}

	public void updateScreen(){
		
	}
}

	
	
