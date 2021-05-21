package hack.rawfish2d.client;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import hack.rawfish2d.client.Ban.uBanThread;
import hack.rawfish2d.client.ModCombat.AimLock;
import hack.rawfish2d.client.ModCombat.ArmorBreaker;
import hack.rawfish2d.client.ModCombat.AutoArmor;
import hack.rawfish2d.client.ModCombat.AutoBlock;
import hack.rawfish2d.client.ModCombat.AutoSoup;
import hack.rawfish2d.client.ModCombat.BowAimbot;
import hack.rawfish2d.client.ModCombat.BowAimbot2;
import hack.rawfish2d.client.ModCombat.ChokePotions;
import hack.rawfish2d.client.ModCombat.Clicker;
import hack.rawfish2d.client.ModCombat.Criticals;
import hack.rawfish2d.client.ModCombat.Criticals2;
import hack.rawfish2d.client.ModCombat.FastBow;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModCombat.God;
import hack.rawfish2d.client.ModCombat.KillAura;
import hack.rawfish2d.client.ModCombat.MultiAura;
import hack.rawfish2d.client.ModCombat.NoHurtCam;
import hack.rawfish2d.client.ModCombat.NoKnockback;
import hack.rawfish2d.client.ModCombat.Regen;
import hack.rawfish2d.client.ModCombat.StormAura;
import hack.rawfish2d.client.ModMisc.AntiAim;
import hack.rawfish2d.client.ModMisc.AntiPotion;
import hack.rawfish2d.client.ModMisc.AutoChat;
import hack.rawfish2d.client.ModMisc.AutoFish;
import hack.rawfish2d.client.ModMisc.AutoKick;
import hack.rawfish2d.client.ModMisc.AutoRespawn;
import hack.rawfish2d.client.ModMisc.AutoTool;
import hack.rawfish2d.client.ModMisc.AutoTpaccept;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.ModMisc.BucketBug;
import hack.rawfish2d.client.ModMisc.CameraRotation;
import hack.rawfish2d.client.ModMisc.ChestFill;
import hack.rawfish2d.client.ModMisc.CoordsAndAngles;
import hack.rawfish2d.client.ModMisc.DonateDumper;
import hack.rawfish2d.client.ModMisc.EnabledMods;
import hack.rawfish2d.client.ModMisc.FakeCreative;
import hack.rawfish2d.client.ModMisc.FastBreak;
import hack.rawfish2d.client.ModMisc.FastPlace;
import hack.rawfish2d.client.ModMisc.FastUse;
import hack.rawfish2d.client.ModMisc.Freecam;
import hack.rawfish2d.client.ModMisc.Lagometr;
import hack.rawfish2d.client.ModMisc.NameDetect;
import hack.rawfish2d.client.ModMisc.NoRotate;
import hack.rawfish2d.client.ModMisc.Nuker;
import hack.rawfish2d.client.ModMisc.PacketFilter;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.ModMisc.Scaffold;
import hack.rawfish2d.client.ModMisc.TimerMod;
import hack.rawfish2d.client.ModMisc.Tower;
import hack.rawfish2d.client.ModMovement.BHOP;
import hack.rawfish2d.client.ModMovement.Blink;
import hack.rawfish2d.client.ModMovement.FakeLag;
import hack.rawfish2d.client.ModMovement.FastLadder;
import hack.rawfish2d.client.ModMovement.Fly;
import hack.rawfish2d.client.ModMovement.Glide;
import hack.rawfish2d.client.ModMovement.InvMove;
import hack.rawfish2d.client.ModMovement.Jesus;
import hack.rawfish2d.client.ModMovement.LongJump;
import hack.rawfish2d.client.ModMovement.MysFly;
import hack.rawfish2d.client.ModMovement.NoFall;
import hack.rawfish2d.client.ModMovement.NoSlowdown;
import hack.rawfish2d.client.ModMovement.NoWeb;
import hack.rawfish2d.client.ModMovement.Phase;
import hack.rawfish2d.client.ModMovement.Sneak;
import hack.rawfish2d.client.ModMovement.Speed_DD_B1;
import hack.rawfish2d.client.ModMovement.Sprint;
import hack.rawfish2d.client.ModMovement.Step;
import hack.rawfish2d.client.ModOther.ChatHotkeys;
import hack.rawfish2d.client.ModOther.CreativeTP;
import hack.rawfish2d.client.ModOther.GUI;
import hack.rawfish2d.client.ModRender.ArmorHud;
import hack.rawfish2d.client.ModRender.BlockESP;
import hack.rawfish2d.client.ModRender.CaveFinder;
import hack.rawfish2d.client.ModRender.ChestESP;
import hack.rawfish2d.client.ModRender.ESP;
import hack.rawfish2d.client.ModRender.Fullbright;
import hack.rawfish2d.client.ModRender.GlowESP;
import hack.rawfish2d.client.ModRender.ItemESP;
import hack.rawfish2d.client.ModRender.MobESP;
import hack.rawfish2d.client.ModRender.Nametags;
import hack.rawfish2d.client.ModRender.NoFire;
import hack.rawfish2d.client.ModRender.NoRenderItem;
import hack.rawfish2d.client.ModRender.PerformanceOverlay;
import hack.rawfish2d.client.ModRender.PlayerInfo;
import hack.rawfish2d.client.ModRender.PotionHud;
import hack.rawfish2d.client.ModRender.RegionRender;
import hack.rawfish2d.client.ModRender.Tracers;
import hack.rawfish2d.client.ModRender.Trajectories;
import hack.rawfish2d.client.ModRender.XRAY;
import hack.rawfish2d.client.ModTest.AutoBank;
import hack.rawfish2d.client.ModTest.AutoBuild;
import hack.rawfish2d.client.ModTest.AutoPath2;
import hack.rawfish2d.client.ModTest.AutoWorldTP;
import hack.rawfish2d.client.ModTest.BotControl;
import hack.rawfish2d.client.ModTest.BotSyncSpam;
import hack.rawfish2d.client.ModTest.ChokePackets;
import hack.rawfish2d.client.ModTest.MoneyBot;
import hack.rawfish2d.client.ModTest.PlayerPull;
import hack.rawfish2d.client.ModTest.Radar;
import hack.rawfish2d.client.ModTest.RainbowHead;
import hack.rawfish2d.client.ModTest.SchemModLoad;
import hack.rawfish2d.client.ModTest.SlowHit;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.cmd.base.CommandUtils;
import hack.rawfish2d.client.gui.ingame.GUIStyle_Legacy;
import hack.rawfish2d.client.gui.ingame.GuiClick;
import hack.rawfish2d.client.gui.ingame.Window;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.FontUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet3Chat;

public class Client {
	private static Client instance = new Client();
	
	public static Client getInstance() {
		return instance;
	}
	
	private ArrayList<Module> modules;
	public static ScreenText st;
	public static Minecraft mc;
	
	public String fontName = "verdana.ttf";
	//public List<FontUtils> fonts = new ArrayList<FontUtils>();
	public HashMap<Integer, FontUtils> fonts = new HashMap<Integer, FontUtils>();

	private GuiClick gui = null;
	private RotationUtils rotationutils = null;
	public CommandUtils cmdutils = null;
	public int window_id = 0;

	public boolean m_handleSetPlayerTeam = false;
	public boolean SetPlayerTeam_log = false;
	public boolean ScoreboardCrash_log = false;

	public boolean PlayerListCheck = true;

	public boolean nth_use_proxy = false;
	public String nth_proxy_ip = "0.0.0.0";
	public int nth_proxy_port = 1080;
	
	public static String realNickname = "";
	
	public final static String build = "04.09.19";
	public final static String version = "v9b4";
	public final static String premium_or_public = "PREMIUM BETA";
	public final static String client_name = "UltimateHack";
	public final static String author = "By RawFish2D";
	
	public static boolean isLatest = true;
	public static String latestBuild = null;
	public static String latestVersion = null;
	public static Frame mcFrame = null;
	
	public Client() {
		System.out.println("Client init.");
		//there is no code 
	}
	
	public void register() {
		System.out.println("Font initialization...");
		
		fonts.put(new Integer(12), new FontUtils(fontName, Font.TRUETYPE_FONT, 12));
		fonts.put(new Integer(18), new FontUtils(fontName, Font.TRUETYPE_FONT, 18));
		fonts.put(new Integer(24), new FontUtils(fontName, Font.TRUETYPE_FONT, 24));
		fonts.put(new Integer(36), new FontUtils(fontName, Font.TRUETYPE_FONT, 36));
		fonts.put(new Integer(48), new FontUtils(fontName, Font.TRUETYPE_FONT, 48));
		fonts.put(new Integer(72), new FontUtils(fontName, Font.TRUETYPE_FONT, 72));
		System.out.println("Font initialization: done");

		System.out.println("Client initialization...");
		
		rotationutils = new RotationUtils();
		cmdutils = new CommandUtils();
		modules = new ArrayList<Module>();

		//Combat
		regModule(new NoKnockback());		//N ok
		regModule(new NoHurtCam());			//0 ok
		regModule(new AutoArmor());			//0 ok
		regModule(new FastBow());			//Z ok
		regModule(new AimLock());			//0 ok
		regModule(new Clicker());			//0 ok
		regModule(new BowAimbot());			//0 ok
		regModule(new BowAimbot2());		//0 ok
		regModule(new Criticals());			//0 ok
		regModule(new Criticals2());		//C ok
		regModule(new KillAura());			//C ok
		regModule(new StormAura());			//V ok
		regModule(new ArmorBreaker());		//V ok
		regModule(new MultiAura());			//B ok
		regModule(new FastClick());			//B ok
		regModule(new AutoBlock());			//B ok
		
		regModule(new ChokePotions());		//0 ok
		regModule(new AutoSoup());			//0 needs fix
		
		regModule(new God());				//0 ?
		regModule(new Regen());				//0 ?

		//Misc
		regModule(new AntiPotion());		//0 ok
		regModule(new FastUse());			//0 ok
		regModule(new FastPlace());			//0 ok
		regModule(new FastBreak());			//0 idk
		regModule(new CameraRotation());	//0 ok
		regModule(new NoRotate());			//0 ok
		regModule(new BucketBug());			//0 ok
		//regModule(new SKB());				//0 fuk this
		//regModule(new Damager());			//0 ? ================================
		regModule(new Tower());				//Y ok
		regModule(new Scaffold());			//G ok
		regModule(new Freecam());			//J ok
		regModule(new AntiAim());			//0 ok
		regModule(new AutoChat());			//0 ok //only for CactusCraft 1.5.2
		//regModule(new EnabledMods());		//0 ok
		//regModule(new EnabledMods2());		//0 ok
		regModule(new EnabledMods());		//0 ok
		regModule(new CoordsAndAngles());	//0 ok
		regModule(new AutoKick());			//0 ok
		//regModule(new ShopDupe());			//[ ok //only for CactusCraft 1.5.2
		regModule(new TimerMod());				//0 ?
		regModule(new AutoFish());			//0 better than nothing
		regModule(new AutoRespawn());		//0 ok
		regModule(new AutoTpaccept());		//0 ok
		regModule(new PacketFilter());		//0 ok
		regModule(new ChestFill());			//0 ?
		
		regModule(new PacketRepeaterAdd());	//0 ??????
		//regModule(new Spam());				//0 ok
		//regModule(new Spam2());				//0 ok
		regModule(new NameDetect());		//0 ok
		regModule(new AutoWarp());			//0 ok
		//regModule(new Brute());				//0 ok

		//Movement
		regModule(new Sprint());			//0 ok
		regModule(new NoSlowdown());		//0 ok
		regModule(new NoWeb());				//0 ok
		regModule(new NoFall());			//0 ok
		regModule(new InvMove());			//0 ok
		//regModule(new SafeWalk());			//0 ok
		regModule(new FastLadder());		//0 dont work anymore
		//regModule(new ClickTeleport());	//0 dont work
		regModule(new Fly());				//R ok
		regModule(new Jesus());				//O ok
		//regModule(new SpeedHack());		//L ok
		//regModule(new SpeedNCP());		//0 ok
		regModule(new BHOP());				//P not very good
		regModule(new MysFly());			//M dont work anymore
		regModule(new Glide());				//H dont work anymore
		//regModule(new Spider());			//L ok with SKB
		regModule(new Phase());				//F dont work anymore
		//regModule(new PacketStep());		//0 almost ok
		regModule(new Step());				//0 dont work anymore :(
		regModule(new Sneak());				//0 ok
		regModule(new Speed_DD_B1());		//0 ok
		regModule(new Blink());				//0 ok
		regModule(new FakeLag());			//0 ok
		
		regModule(new LongJump());			//0 ok

		//Render
		regModule(new Fullbright());		//0 ok
		regModule(new Tracers());			//0 ok
		regModule(new Nametags());			//0 ok
		regModule(new NoFire());			//0 ok
		regModule(new MobESP());			//0 ok
		regModule(new ESP());				//0 ok
		//regModule(new ESP2D());			//0 ok
		regModule(new GlowESP());			//0 ok but can be better
		regModule(new ItemESP());			//0 ok
		regModule(new ChestESP());			//U ok
		regModule(new NoRenderItem());		//0 ok
		regModule(new Trajectories());		//0 ok
		regModule(new XRAY());				//X ok
		regModule(new CaveFinder());		//K ok
		regModule(new ArmorHud());			//0 ok
		regModule(new PotionHud());			//0 ok
		regModule(new PlayerInfo());		//0 ok
		//regModule(new PlayerInfo2());		//0 ok
		//regModule(new PlayerInfo3());		//0 ok
		regModule(new RegionRender());			//0 ok
		regModule(new PerformanceOverlay());	//performance overlay -_-

		//Other
		regModule(new GUI());				//~ ok
		regModule(new CreativeTP());		//0 OMFG WTF IS THIS?!??! WHY THIS WORKING???!?

		//regModule(new IRC_DD());			//0 ?
		//regModule(new PlayerPull());		//0 work in progress
		//regModule(new ClickTeleport());	//0 dont work
		//regModule(new DebugStuff());		//0 wtf
		//regModule(new Blink());			//0 dont work
		//regModule(new GlowESP2());		//0 lags and awful
		//regModule(new SKB());				//0 what the fuck is this?
		
		//regModule(new BHOP_SP());			//0 ?
		//regModule(new AntiBan());			//0 ok
		//regModule(new LatestFlyNCP());		//0 ?
		//regModule(new Spider());			//0 ?
		//regModule(new SpiderClimb());		//0 ?
		regModule(new AutoWorldTP());		//0 ok
		
		//regModule(new Step10());			//0 ?
		//regModule(new Strafe());			//0 ?
		//regModule(new AutoDupe());		//not needed
		//regModule(new SKB());				//0 ??
		
		//dont work anymore
		//regModule(new PacketSKB());			//0 ??

		//regModule(new GuiScreenGame());		//0 :thinking:
		
		regModule(new BotControl());
		regModule(new SchemModLoad());
		
		regModule(new Nuker());
		regModule(new FakeCreative());
		regModule(new AutoTool());
		regModule(new Lagometr());
		
		regModule(new BlockESP());
		
		regModule(new SlowHit());
		
		regModule(new DonateDumper());
		//regModule(new Spectate()); //сломанная помойка
		regModule(new RainbowHead());
		regModule(new Radar());
		
		regModule(new ChokePackets());		//0 ok
		regModule(new PlayerPull());		//0 work in progress
		//regModule(new AutoPath());		//0 work in progress
		regModule(new AutoPath2());			//0 work in progress
		regModule(new MoneyBot());			//0 work in progress
		regModule(new BotSyncSpam());		//0 work in progress
		
		//regModule(new Meme());		//0 work in progress
		//regModule(new Test());		//0 work in progress
		regModule(new AutoBuild());
		regModule(new AutoBank());
		
		//regModule(new SpamClick());
		
		//изменение ника
		changeName("IWasTesting47");

		gui = new GuiClick();
		gui.initGui();

		ConfigManager.init();

		System.out.println("Client initialization: done");
		System.gc();
		Client.realNickname = mc.session.username;
	}

	public RotationUtils getRotationUtils() {
		return rotationutils;
	}

	public void onStart() {
		register();
	}

	public void changeName(String name) {
		if(mc != null)
			if(mc.session != null)
				mc.session.username = name;
	}

	public void regModule(Module module) {
		modules.add(module);
	}

	public void onSendChat(String str) {
		if (!str.startsWith(cmdutils.getPrefix())) {
			//str = PlayerList.sub_5(str);
			String str2 = str.toLowerCase();
			if(!str2.contains("setpass"))
				mc.getNetHandler().addToSendQueue(new Packet3Chat(str));
			return;
		}
		
		cmdutils.onCommand(str);

		onHelp(str);
		onGetIP(str); //get server ip

		onFakePM(str); //[from] [msg]

		onKickChest(str); //make kick chest
		onRainbowGui(str);
		onItemDamageToggle(str); //toggle bit value item damage
		onChangeName(str); //change nickname

		onAdd(str); //add friend to friendlist
		onDel(str); //delete friend from friendlist
		onList(str); //show friend list

		onPlayerList(str); //show player list
		onNextGUIStyle(str); //switch gui style
		onPlayerListCheck(str); //toggle check player in server player list for gettarget functions
		
		onDropInventory(str);
		
		onBanCommand(str);
		
		onSel(str); //damage
	}

	public void onHelp(String str) {
		if (str.toLowerCase().startsWith(".help")) {
			MiscUtils.sendChatClient("§e---Команды---");

			MiscUtils.sendChatClient("§a.ban §e<ник> - забанить ник (работает только на старой версии BungeeCoord)");
			MiscUtils.sendChatClient("§a.uban §e<ник> - забанить все комбинации ника (на длинных никах будет лагать) (работает только на старой версии BungeeCoord)");
			MiscUtils.sendChatClient("§a.getip §e- показать IP сервера");
			MiscUtils.sendChatClient("§a.nextstyle §e- поменять стиль меню чита");
			MiscUtils.sendChatClient("§a.rainbowgui §e- радужное меню");
			MiscUtils.sendChatClient("§a.add §e<ник> - добавить друга");
			MiscUtils.sendChatClient("§a.del §e<ник> - удалить друга");
			MiscUtils.sendChatClient("§a.flist §e- вывести список друзей");
			MiscUtils.sendChatClient("§a.plist §e- скопировать в буфер обмена список игроков сервера");
			MiscUtils.sendChatClient("§a.plc <on/off> §e- вкл/выкл анти-бот");
			MiscUtils.sendChatClient("§a.dropinv §e- выкинуть все вещи из инвентаря");
			MiscUtils.sendChatClient("§a.c <сообщение> §e- написать в общий чат чита");
			MiscUtils.sendChatClient("§a.fakepm §e<от кого> <текст> §e- фейковое сообщение в личку");
			MiscUtils.sendChatClient("§e---");
			
			for(Command cmd : this.cmdutils.commands) {
				MiscUtils.sendChatClient(cmd.getColor() + cmd.getSyntax() + " | " + cmd.getDesc());
			}
			
			MiscUtils.sendChatClient("§e---");

			MiscUtils.sendChatClient("§a.help §e- информация о командах чита");

			MiscUtils.sendChatClient("§a§lUltimate§6§lHack§e - by RawFish2D");
			MiscUtils.sendChatClient("§eили на странице в вк https://vk.com/rawfish2d");
		}
	}
/*
	public void onSelfBan(String str) {
		String[] split = str.split(" ");

		//.selfkick ddos1337 RawFish2D капс в лс
		//.selfban ddos1337 RawFish2D 3,10

		if (str.toLowerCase().startsWith(".selfban")) {
			//System.out.println("ip: " + String.valueOf(GuiConnecting.IP));
			//System.out.println("port: " + Integer.parseInt(GuiConnecting.PORT));
			//Client.mc.getNetHandler().addToSendQueue(new Packet2ClientProtocol(61, "kek.", String.valueOf(GuiConnecting.IP), Integer.parseInt(GuiConnecting.PORT)));

			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e навсегда забанил §c" + split[2] + "§a: §b" + reason);
			MiscUtils.selfKick();

			//mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.disconnected", "disconnect.genericReason", new Object[] {reason}));
		}
	}

	public void onSelfKick(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".selfkick")) {
			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e кикнул §c" + split[2] + "§a: §b" + reason);
			//MiscUtils.selfKick();
			mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.disconnected", "disconnect.genericReason", new Object[] {reason}));
		}
	}
	*/
	/*
	public void onXAdd(String str) {
		String []split = str.split(" ");

		if(str.startsWith(".xadd")) {
			if(split.length >= 2) {
				int id = 0;
				System.out.println("onXAdd");

				try {
					id = Integer.parseInt(split[1]);
					if(!XRAY.blocks.contains(new Integer(id))) {
						MiscUtils.sendChatClient("§dID " + id + " добавлен в XRAY");
						XRAY.blocks.add(id);
					}
					else
						MiscUtils.sendChatClient("§cID " + id + " уже есть в XRAY");
				}catch(NumberFormatException ex) {
					MiscUtils.sendChatClient("§cОшибка. Неверный id блока");
					ex.printStackTrace();
					return;
				}
			}
		}
	}

	public void onXDel(String str) {
		String []split = str.split(" ");

		if(str.startsWith(".xdel")) {
			if(split.length >= 2) {
				int id = 0;
				System.out.println("onXDel");

				try {
					id = Integer.parseInt(split[1]);
					if(XRAY.blocks.contains(id)) {
						MiscUtils.sendChatClient("§dID " + id + " удалён из XRAY");
						XRAY.blocks.removeByValue(id);
					}
					else
						MiscUtils.sendChatClient("§cID " + id + " отсутствует в XRAY");
				}catch(NumberFormatException ex) {
					MiscUtils.sendChatClient("§cОшибка. Неверный id блока");
					ex.printStackTrace();
					return;
				}
			}
		}
	}

	public void onXList(String str) {
		String []split = str.split(" ");

		if(str.startsWith(".xlist")) {
			System.out.println("onXList");
			int size = XRAY.blocks.size();

			String ids = "§9";
			for(int a = 0; a < size; ++a) {
				if(a != size - 1)
					ids += XRAY.blocks.get(a) + ", ";
				else
					ids += XRAY.blocks.get(a) + ".";
				//MiscUtils.sendChatClient();
			}
			MiscUtils.sendChatClient("§dXRAY блоки (количество - " + size +"):");
			MiscUtils.sendChatClient(ids);
		}
	}
*/
	public void onGetIP(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".getip")) {
			if(GuiConnecting.IP != null && GuiConnecting.PORT != null) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(GuiConnecting.IP + ":" + GuiConnecting.PORT), null);
				MiscUtils.sendChatClient("§aServer IP: " + GuiConnecting.IP + ":" + GuiConnecting.PORT);
				MiscUtils.sendChatClient("§aIP сервера скопирован в буфер обмена");
			}
			else
				MiscUtils.sendChatClient("§cWTF! IP and PORT is NULL!");
		}
	}

	public void onNextGUIStyle(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".nextstyle")) {
			GUIStyle_Legacy.nextStyle();
		}
	}
/*
	public void onDMG(String str) {
		//from DD B1
		if(str.startsWith(".dmg")) {
			String[] split = str.split(" ");
			if(split.length == 2) {
				try {
					double damage = Double.parseDouble(split[1]);

					mc.getNetHandler().addToSendQueue(
							new Packet13PlayerLookMove(
									mc.thePlayer.posX,
									mc.thePlayer.boundingBox.minY + 0.2f,
									mc.thePlayer.posY + 0.2f,
									mc.thePlayer.posZ,
									getRotationUtils().getYaw(),
									getRotationUtils().getPitch(),
									false));

					mc.getNetHandler().addToSendQueue(
							new Packet11PlayerPosition(
									mc.thePlayer.posX,
									mc.thePlayer.boundingBox.minY - 3.0f - damage,
									mc.thePlayer.posY - 3.0f - damage,
									mc.thePlayer.posZ,
									false));
				}catch(Exception ex) {ex.printStackTrace();}
			}
		}
	}
*/
	public void onSel(String str) {
		if(str.startsWith(".sel")) {
			RegionRender.sel();
		}
	}
/*
	public void onSpamAmount(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".spamamount")) {
			if(split.length < 2)
				return;

			try {
				Spam.amount = Integer.parseInt(split[1]);
				MiscUtils.sendChatClient("§aPay amount set to " + split[1]);
			}
			catch (NumberFormatException e) {
				MiscUtils.sendChatClient("§cError");
			}
		}
	}

	public void onSpamMsg(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".spammsg")) {
			if(split.length < 2)
				return;

			Spam.msg = split[1];
			MiscUtils.sendChatClient("§aPay msg set to " + split[1]);
		}
	}
*/
	public void onPlayerListCheck(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".plc")) {
			if(split.length < 2) {
				MiscUtils.sendChatClient("§a.plc <on/off> §e- вкл/выкл проверку игрока в списке игроков сервера для выбора цели (для всех киллаур, ESP, Tracer, Aimbot и т.д). Без проверки, киллауры будут бить и настоящих игроков и ботов.");
				return;
			}

			if( split[1].equalsIgnoreCase("on") ) {
				PlayerListCheck = true;
				MiscUtils.sendChatClient("§eПроверка подлинности игрока §aвключена");
			}
			else if( split[1].equalsIgnoreCase("off") ) {
				PlayerListCheck = false;
				MiscUtils.sendChatClient("§eПроверка подлинности игрока §cвыключена");
			}
			else
				MiscUtils.sendChatClient("§cОшибка");
		}
	}

	public void onAdd(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".add")) {
			if(split.length < 2) {
				MiscUtils.sendChatClient("§a.add <name> §e- добавить друга в список друзей");
				return;
			}

			String name = split[1];
			if( !PlayerUtils.isFriend(name) ) {
				PlayerUtils.friendListAdd(name);
				MiscUtils.sendChatClient("§aДобавлен новый друг : " + name);
			}
			else
				MiscUtils.sendChatClient("§cТакой друг в списке уже есть!");
		}
	}

	public void onDel(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".del")) {
			if(split.length < 2) {
				MiscUtils.sendChatClient("§a.del <name> §e- удалить друга из списка друзей");
				return;
			}

			String name = split[1];
			if( PlayerUtils.isFriend(name) ) {
				PlayerUtils.friendListRemove(name);
				MiscUtils.sendChatClient("§aДруг удалён : " + name);
			}
			else
				MiscUtils.sendChatClient("§cТакого друга в списке не было!");
		}
	}

	public void onList(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".flist")) {
			String friends = "";
			for(int a = 0; a < PlayerUtils.getFriendList().size(); ++a) {
				friends += PlayerUtils.getFriendList().get(a) + ", ";
			}
			MiscUtils.sendChatClient("§a" + friends);
		}
	}

	public void onPlayerList(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".plist")) {
			String players = "";

			Iterator it = mc.getNetHandler().playerInfoMap.values().iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				GuiPlayerInfo gui = (GuiPlayerInfo)obj;

				if(gui.name != null)
					players += gui.name + " , ";
			}
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(players), null);
			MiscUtils.sendChatClient(players);
			MiscUtils.sendChatClient("§eСписок игроков скопирован в буффер обмена");
		}
	}

	public void onDropInventory(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".dropinv")) {
			mc.thePlayer.dropOneItem(true);
			
			for(int a = 0; a < mc.thePlayer.inventory.getSizeInventory(); ++a) {
				ItemStack item = mc.thePlayer.inventory.getStackInSlot(a);
				if(item != null)
				{
					mc.playerController.windowClick(0, a, 0, 0, mc.thePlayer);
					MiscUtils.sleep(3L);
					mc.playerController.windowClick(0, -999, 0, 0, mc.thePlayer);
					MiscUtils.sleep(3L);
				}
			}
		}
	}
/*
	public void onCreativeTP(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".tp")) {
			if(split.length == 2) {
				if(split[1].equals("toggle")) {
					moduleToggle("CreativeTP");
					if(CreativeTP.toggled) {
						MiscUtils.sendChatClient("§eCreativeTP §aEnabled");
						MiscUtils.sendChatClient("§eТеперь постарайтесь взлететь прямо и вверх. Попытка телепортации происходит когда вы двигаетесь, если попробуете взлететь вверх и прямо, скорее всего телепортация произойдёт сразу же.");
						MiscUtils.sendChatClient("§eЧтобы выключить телепорт, напишите §a.tp toggle §eещё раз");
					}
					else
						MiscUtils.sendChatClient("§eCreativeTP §cDisabled");
					return;
				}
			}
			else if(split.length == 1) {
				MiscUtils.sendChatClient("§aCreativeTP:");
				MiscUtils.sendChatClient("§a.tp toggle §e- включить/отключить");
				MiscUtils.sendChatClient("§a.tp packets <value> §e- сколько пакетов спамить для телепорта (чем больше тем лучше, но много не надо)");
				MiscUtils.sendChatClient("§a.tp <x> <z> §e- установка координат для телепорта");
				return;
			}

			if(split.length < 3) {
				MiscUtils.sendChatClient("§cCreativeTP Error");
				return;
			}

			if(split.length == 3) {
				if(split[1].equals("packets"))
				{
					try
					{
						CreativeTP.packets = Integer.parseInt(split[2]);
						MiscUtils.sendChatClient("§aCreativeTP packets se to §e" + CreativeTP.packets);
						return;
					}
					catch(NumberFormatException ex) {
						ex.printStackTrace();
						MiscUtils.sendChatClient("§cCreativeTP Error");
						return;
					}
				}
			}

			try
			{
				CreativeTP.x = Double.parseDouble(split[1]);
				CreativeTP.z = Double.parseDouble(split[2]);
				MiscUtils.sendChatClient("§aCreativeTP coordinates set to x:" + CreativeTP.x + " z:" + CreativeTP.z);
				return;
			}
			catch(NumberFormatException ex) {
				ex.printStackTrace();
				MiscUtils.sendChatClient("§cCreativeTP Error");
				return;
			}
		}
	}
	*/
	public void onFakePM(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".fakepm")) {
			if(split.length < 3) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String msg = "";
			for(int a = 2; a < split.length; ++a)
				msg += split[a] + " ";

			MiscUtils.sendChatClient("§6[" + split[1] + " -> Я] §r" + msg);
		}
	}
/*
	public void onFakeBan(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".fakeban")) {
			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e навсегда забанил §c" + split[2] + "§a: §b" + reason);
			//MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e разбанил §c" + split[2]);

			//MiscUtils.sendChatClient("§cfakeban");
		}

		//tempban
		//§eИгрок §cvulgar_molly2 §eзабанил §cNeXus_Xephon §eна §d9 сек§a: §bя решил дать банчик на шушуть подольше,но я тебя любя же забанил)
		
		//ultimate fake ban
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".fakeban")) {
			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			int size = Client.mc.getNetHandler().playerInfoMap.values().size();
			Iterator it = Client.mc.getNetHandler().playerInfoMap.values().iterator();

			while(it.hasNext()) {
				Object obj = it.next();
				GuiPlayerInfo gui = (GuiPlayerInfo)obj;
				if(gui.name != null)
					MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e навсегда забанил §c" + gui.name + "§a: §b" + reason);
			}
		}
		
	}
*/
	/*
	public void onFakeKick(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".fakekick")) {
			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e кикнул §c" + split[2] + "§a: §b" + reason);

			//MiscUtils.sendChatClient("§cfakekick");
		}
	}

	public void onFakeMute(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".fakemute")) {
			if(split.length < 4) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			String reason = "";
			for(int a = 3; a < split.length; ++a)
				reason += split[a] + " ";

			MiscUtils.sendChatClient("§eИгрок §c" + split[1] + "§e навсегда замутил §c" + split[2] + "§a: §b" + reason);

			//MiscUtils.sendChatClient("§cfakemute");
		}
	}
*/
	public void onKickChest(String str) {
		if (str.toLowerCase().startsWith(".kickchest")) {
			if (!mc.playerController.isInCreativeMode()) {
				MiscUtils.sendChatClient("Player must be in creative mode");
				return;
			}

			if(mc.thePlayer.inventory.getCurrentItem() != null) {
				ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
				String newname = "§c*** §d|§aKick §eChest §6By §aRawFish2D§d|§c ***";
				item.setItemName(newname);
				mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
			}
			else
				MiscUtils.sendChatClient("§cNo item in hand found");
		}
	}
	
	public void onRainbowGui(String str) {
		if (str.toLowerCase().startsWith(".rainbowgui")) {
			Window.rainbow = !Window.rainbow;
		}
	}
	
	public static int toggleBitValue(final int input, final int value)
	{
		int a = 0;
		int ret = input;
		int bit = 0;

		if(value == 1)				bit = 0;
		else if(value == 2)			bit = 1;
		else if(value == 4)			bit = 2;
		else if(value == 8)			bit = 3;
		else if(value == 16)		bit = 4;
		else if(value == 32)		bit = 5;
		else if(value == 64)		bit = 6;
		else if(value == 128)		bit = 7;
		else if(value == 256)		bit = 8;
		else if(value == 512)		bit = 9;
		else if(value == 1024)		bit = 10;
		else if(value == 2048)		bit = 11;
		else if(value == 4096)		bit = 12;
		else if(value == 8192)		bit = 13;
		else if(value == 16384)		bit = 14;
		else if(value == 32768)		bit = 15;
		else return -1;

		a = input & value;
		if(a == 0)
			ret |= value;
		else
			ret &= ~value;
			//ret &= ~(1 << bit);
		return ret;
	}

	public void onItemDamageToggle(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".itemdamagetoggle")) {
			if(mc.thePlayer.inventory.getCurrentItem() != null)
			{
				ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
				MiscUtils.sendChatClient("§aItem damage :§e " + item.itemDamage);
			}
			else
				MiscUtils.sendChatClient("§cNo item in hand found");

			if(split.length < 2) {
				MiscUtils.sendChatClient("§a.itemdamagetoggle §e[damage] §a- (возможные значения: 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768) §e- переключить бит повреждённости предмета §c(creative only)");
				return;
			}

			if (!mc.playerController.isInCreativeMode()) {
				MiscUtils.sendChatClient("Player must be in creative mode");
				return;
			}

			try {
				if(mc.thePlayer.inventory.getCurrentItem() != null)
				{
					int bit = Integer.parseInt(split[1]);
					ItemStack item = mc.thePlayer.inventory.getCurrentItem().copy();
					int damage = toggleBitValue(item.itemDamage, bit);

					if(damage < 0)
					{
						MiscUtils.sendChatClient("§cInvalid bit value");
						return;
					}

					item.itemDamage = damage;
					mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(mc.thePlayer.inventory.currentItem + 36, item));
					MiscUtils.sendChatClient("§aItem damage set to §e" + item.itemDamage);
				}
				else
					MiscUtils.sendChatClient("§cNo item in hand found");
			}
			catch(NumberFormatException ex) {
				MiscUtils.sendChatClient("§cError");
				return;
			}
		}
	}

	public void onChangeName(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".name")) {
			if(split.length < 2) {
				MiscUtils.sendChatClient("§cError");
				return;
			}

			changeName(split[1]);
			MiscUtils.sendChatClient("§aНик изменён на : §e" + split[1] + "§a. Переподключитесь к серверу чтобы применить изменения.");
		}
	}
/*
	public void onShopDupeDelay(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".shopdupedelay")) {
			if(split.length < 2)
				return;

			try {
				ShopDupe.delay = Integer.parseInt(split[1]);
				MiscUtils.sendChatClient("§aDelay set to " + split[1]);
			}
			catch (NumberFormatException e) {
				MiscUtils.sendChatClient("§cError");
			}
		}
	}
*/
	public void onBanCommand(String str) {
		String[] split = str.split(" ");

		if (str.toLowerCase().startsWith(".ban")) {
			if(split.length <= 1)
				return;

			if( !PlayerUtils.isAuthor(split[1]) && !PlayerUtils.isAuthorFriend(split[1])) {
				try {
					uBanThread.ban(split[1], true);
				}
				catch (Exception e) {e.printStackTrace();}
			}
			else
				MiscUtils.sendChatClient("§cYou cant ban this player.");
		}
		else if (str.toLowerCase().startsWith(".uban")) {
			if(split.length <= 1)
				return;

			if( !PlayerUtils.isAuthor(split[1]) && !PlayerUtils.isAuthorFriend(split[1])) {
				Thread th = new Thread(new uBanThread(split[1]));
				th.start();
			}
			else
				MiscUtils.sendChatClient("§cYou cant ban this player.");
		}

		else if (str.toLowerCase().startsWith(".showuban")) {
			if(split.length < 2)
				return;

			if(split[1].equals("on"))
				uBanThread.show = true;
			else if(split[1].equals("off"))
				uBanThread.show = false;

			MiscUtils.sendChatClient("ShowMsg for .uban set to " + uBanThread.show);
		}
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public Module GetModule(String mod) {
		for(Module module : getModules()) {
			if( module.getName().equalsIgnoreCase(mod) )
				return module;
		}
		return null;
	}

	public void onChatMessage(String msg) {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onChatMessage(msg);
		}
	}

	public void moduleOnKeyPress(String name, int key) {
		for(Module m : getModules()) {
			if(m.getName() == name) {
				m.onKeyPress(key);
			}
		}
	}

	public void moduleToggle(String name) {
		for(Module m : getModules()) {
			if(m.getName().equalsIgnoreCase(name)) {
				m.toggle();
			}
		}
	}
	
	public GuiClick getGUI() {
		if(gui == null)
			gui = new GuiClick();
		return gui;
	}

	public void onKeyPressed(int keyCode) {
		for(Module module : getModules()) {
			if(module.getKeyCode() == keyCode && keyCode != 0) {
				module.toggle();
			}
		}
		ChatHotkeys.onKeyPressed(keyCode);
	}
	
	public void onRenderHand() {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onRenderHand();
		}
	}

	public void onRender() {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onRender();
		}
	}

	public void onRenderOverlay(int mx, int my) {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onRenderOverlay();
		}
		
		Client.getInstance().getGUI().drawScreen(mx, my);
		Client.getInstance().st.renderScreen();
	}

	public void preMotionUpdate() {
		if(AutoEnable.first_enable == false)
			AutoEnable.ToggleModules();

		for(Module module : getModules()) {
			if(module.isToggled())
				module.preMotionUpdate();
		}
	}

	public void postMotionUpdate() {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.postMotionUpdate();
		}
	}
	
	public void callAlways() {
		for(Module module : getModules()) {
			module.callAlways();
		}
	}

	public void onUpdate() {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onUpdate();
		}
		
		callAlways();
		
		if(mc.renderGlobal.countEntitiesTotal > 2000) {
			if(!NoRenderItem.instance.isToggled()) {
				NoRenderItem.instance.toggle();
				MiscUtils.sendChatClient("§eTotal entities > 2000. §a§lNoRenderItem §eenabled.");
			}
		}
	}

	public boolean onAddPacketToQueue(Packet packet) {
		//System.out.println("[Отправленный пакет]: " + packet.toString());
		
		boolean doSend = true;
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.onAddPacketToQueue(packet);
				if(module.getDoNotSendNextPacket() == true) {
					module.doNotSendNextPacket(false);
					doSend = false;
				}
				/*
				if(module.getDoNotSendNextPacket() == true) {
					module.doNotSendNextPacket(false);
					return false;
				}
				*/
			}
		}
		return doSend;
	}

	public boolean afterOnAddPacketToQueue(Packet packet) {
		for(Module module : getModules()) {
			if(module.isToggled()) {
				module.afterOnAddPacketToQueue(packet);
			}
		}
		return true;
	}

	public boolean DontProcessPacket(Packet packet) {
		for(Module module : getModules()) {
			if(module.isToggled() == true) {
				if(module.DontProcessPacket(packet) == true) {
					return true;
				}
			}
		}

		return false;
	}

	public void onPacket(Packet packet) {
		for(Module module : getModules()) {
			if(module.isToggled())
				module.onPacket(packet);
		}
		
		//System.out.println("[Принятый пакет]: " + packet.toString());
		
		//Packet25EntityPainting
		//Packet23VehicleSpawn
		/*
		if(packet instanceof Packet23VehicleSpawn) {
			Packet23VehicleSpawn p = (Packet23VehicleSpawn)packet;
			System.out.println("id:" +  p.entityId + " x:" + p.xPosition + " y:" + p.yPosition + " z:" + p.zPosition);
		}
		
		if(packet instanceof Packet25EntityPainting) {
			Packet25EntityPainting p = (Packet25EntityPainting)packet;
			System.out.println("id:" +  p.entityId + " title:" + p.title + " x:" + p.xPosition + " y:" + p.yPosition + " z:" + p.zPosition);
		}
		*/
		/*
		if(		packet.getPacketId() != 0 &&
				packet.getPacketId() != 3 &&
				packet.getPacketId() != 4 &&
				packet.getPacketId() != 5 &&
				packet.getPacketId() != 22 &&
				packet.getPacketId() != 28 &&
				packet.getPacketId() != 29 &&
				packet.getPacketId() != 31 &&
				packet.getPacketId() != 32 &&
				packet.getPacketId() != 33 &&
				packet.getPacketId() != 34 &&
				packet.getPacketId() != 35 &&
				packet.getPacketId() != 30 &&
				packet.getPacketId() != 40 &&
				packet.getPacketId() != 42 &&
				packet.getPacketId() != 53 &&
				packet.getPacketId() != 54 &&
				packet.getPacketId() != 62 &&
				packet.getPacketId() != 64 &&
				packet.getPacketId() != 201 &&
				packet.getPacketId() != 207 &&
				packet.getPacketId() != 209)
		System.out.println(packet.toString());
		*/
		
		/*
		if(		packet.getPacketId() != 0 &&
				packet.getPacketId() != 53 &&
				packet.getPacketId() != 42 &&
				packet.getPacketId() != 201 &&
				packet.getPacketId() != 31 &&
				packet.getPacketId() != 4)
		//MiscUtils.sendChatClient(packet.toString());
		*/
		
		/*
		if(		packet.getPacketId() == 51 ||
				packet.getPacketId() == 56)
		System.out.println(packet.toString());
		*/
		//Packet63WorldParticles
		//Packet52MultiBlockChange
		
		//Packet20NamedEntitySpawn
		/*
		if(packet.getPacketId() == 20) {
			Packet20NamedEntitySpawn p20 = (Packet20NamedEntitySpawn)packet;
			System.out.println("======Packet20NamedEntitySpawn======");
			System.out.println("id: " + p20.entityId);
			System.out.println("name: " + p20.name);
			System.out.println("x: " + p20.xPosition);
			System.out.println("y: " + p20.yPosition);
			System.out.println("z: " + p20.zPosition);
			
			double dist = 0;
			dist = mc.thePlayer.getDistanceSq(p20.xPosition, p20.yPosition, p20.zPosition);
			System.out.println("distance: " + dist);
		}
		*/
		/*
		if(packet.getPacketId() == 0) {
			MiscUtils.sendChatClient("Received 0");
		}
		*/
	}
	
	public static boolean isFocused() {
		return Client.mcFrame.isFocused();
	}
}
