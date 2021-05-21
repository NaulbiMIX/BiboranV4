package hack.rawfish2d.client.cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModCombat.FastClick;
import hack.rawfish2d.client.ModOther.ChatHotkeys;
import hack.rawfish2d.client.ModTest.BotControl;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.pbots.PBotConnection;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.modules.Anouncer;
import hack.rawfish2d.client.pbots.modules.AntiCaptcha;
import hack.rawfish2d.client.pbots.modules.AutoJoin;
import hack.rawfish2d.client.pbots.modules.AutoLogin;
import hack.rawfish2d.client.pbots.modules.AutoReconnect;
import hack.rawfish2d.client.pbots.modules.AutoRegister;
import hack.rawfish2d.client.pbots.modules.BPM;
import hack.rawfish2d.client.pbots.modules.BLB;
import hack.rawfish2d.client.pbots.modules.Idot;
import hack.rawfish2d.client.pbots.modules.KillFarm;
import hack.rawfish2d.client.pbots.modules.ModuleBase;
import hack.rawfish2d.client.pbots.modules.Repeater;
import hack.rawfish2d.client.pbots.modules.ShopDupe_A2;
import hack.rawfish2d.client.pbots.modules.ShopDupe_B2;
import hack.rawfish2d.client.pbots.modules.Spam;
import hack.rawfish2d.client.pbots.modules.Walk;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.pbots.utils.VarType;
import hack.rawfish2d.client.pbots.utils.ProxyConnection;
import hack.rawfish2d.client.pbots.utils.ProxyManager;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.InventoryBasic;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;

public class PBotCmd extends Command {
	//public static List<PBotConnection> lobby_bots = new ArrayList<PBotConnection>();
	public static String ip = "164.132.233.222";
	public static int port = 25565;
	
	public static CopyOnWriteArrayList<PBotVar> pbot_vars = new CopyOnWriteArrayList<PBotVar>();
	public static PBotCmd instance = null;
	public static boolean joining = false;
	public static boolean stopCfg = false;
	
	public static CopyOnWriteArrayList<PBot> bots = new CopyOnWriteArrayList<PBot>();
	public static CopyOnWriteArrayList<String> goodProxy = new CopyOnWriteArrayList<String>();
	public static String password = "reg123reg123";
	
	public PBotCmd(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
		instance = this;
	}
	
	@Override
	public void run(String msg, final String []args) {
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("help")) {
				MiscUtils.sendChatClient("§a§lPBot Commands");
				//MiscUtils.sendChatClient("§eПри успешном заходе бота на выживание он идёт на /warp shopdupe и пытается дюпать деньги.");
				//MiscUtils.sendChatClient("§eКаждую минуту он отправляет Вам по 1 500 000 игровой валюты.");
				//MiscUtils.sendChatClient("§eЧтоб бот на дюп денег сработал, в момент его телепортации на варп вы должны стоять на этом варпе, затем можете идти куда захотите.");
				MiscUtils.sendChatClient("§e=======================================");
				MiscUtils.sendChatClient(color + cmd + " setip <ip> - ip на который будут заходить боты");
				MiscUtils.sendChatClient(color + cmd + " reload <файл> - загрузить список прокси из файла");
				MiscUtils.sendChatClient("§e.pbot load <название файла> - загрузить и выполнить команды из файла");
				MiscUtils.sendChatClient("§d.pbot stopcfg - остановить выполнение конфига");
				MiscUtils.sendChatClient("§e.pbot add <ник> - добавить бота");
				MiscUtils.sendChatClient("§e.pbot start <ник> - запустить бота");
				MiscUtils.sendChatClient("§e.pbot startall - запустить всех ботов");
				MiscUtils.sendChatClient("§e.pbot join <ник> - добавить и сразу запустить бота");
				MiscUtils.sendChatClient("§e.pbot join <количество> <ник> - заспамить сервер ботами");
				MiscUtils.sendChatClient("§e.pbot join <стартовое число> <конечное число> <ник> - заспамить сервер ботами");
				MiscUtils.sendChatClient("§e.pbot del <ник> - удалить бота");
				MiscUtils.sendChatClient("§e.pbot delall - удалить всех ботов");
				MiscUtils.sendChatClient("§e.pbot send <ник> <сообщение> - отправить сообщение от имени бота");
				MiscUtils.sendChatClient("§e.pbot sendall <сообщение> - отправить сообщение от имени всех ботов");
				MiscUtils.sendChatClient("§e.pbot setpass <пароль> - установить пароль для входа ботов");
				//MiscUtils.sendChatClient("§e.pbot setmode <ник> <режим> - установить режим для бота (ShopDupe, ShopSell, none - по умолчанию)");
				//MiscUtils.sendChatClient("§e.pbot setglobalmode <режим> - установить режим для ВСЕХ ботов (lobby1 - не логинится, отпавляет базовые пакеты, lobby2 - не логинится, не отправляет базовые пакеты, none - по умолчанию)");
				MiscUtils.sendChatClient("§e=======================================");
				MiscUtils.sendChatClient("§d.pbot dump - записать активные прокси в файл");
				//MiscUtils.sendChatClient("§e.pbot reloadcfg - перезагрузить файл сообщений спама ботов и файл с паролями для брута");
				MiscUtils.sendChatClient("§e.pbot count - выводит количество ботов в режиме выживания");
				MiscUtils.sendChatClient("§e.pbot list - показать список ботов");
				MiscUtils.sendChatClient("§e.pbot money - показать баланс ботов");
				MiscUtils.sendChatClient("§e.pbot pay - перевести все деньги с ботов к вам на счёт");
				MiscUtils.sendChatClient("§e.pbot proxyinfo - информация о прокси");
				MiscUtils.sendChatClient("§e.pbot brute - запуск брута");
				MiscUtils.sendChatClient("§e.pbot proxyon - включает использование прокси ботами");
				MiscUtils.sendChatClient("§e.pbot proxyoff - выключает использование прокси ботами");
				MiscUtils.sendChatClient("§e.pbot cpp <число> - задать количество подключений ботов на 1 прокси");
				MiscUtils.sendChatClient("§e.pbot varlist - выводит список переменных, их значений и названий модулей в которых они хранятся");
				MiscUtils.sendChatClient("§e.pbot varset <название модуля> <переменная> <значение> - изменить значение переменной");
				MiscUtils.sendChatClient("§e=======================================");
				MiscUtils.sendChatClient("§e.pbot mlist <ник> - выводит состояние всех модулей бота");
				MiscUtils.sendChatClient("§e.pbot mtoggle <ник> <название модуля> - включить/выключить модуль у бота");
				MiscUtils.sendChatClient("§e.pbot monall <название модуля> - включить всем ботам этот модуль");
				MiscUtils.sendChatClient("§e.pbot moffall <название модуля> - выключить всем ботам этот модуль");
				MiscUtils.sendChatClient("§e.pbot config <ник> <имя класса модуля> <переменная> <значение> - изменить значение переменной в модуле бота");
				
				MiscUtils.sendChatClient("§d.pbot look <* или ник бота> <pitch> <yaw> - повернуть голову");
				MiscUtils.sendChatClient("§d.pbot slot <* или ник бота> <номер слота от 0 до 8> - изменить слот в нижней панели");
				MiscUtils.sendChatClient("§d.pbot rclick <* или ник бота> - нажать правую кнопку мыши");
				if(!ProxyManager.proxy_enabled)
					MiscUtils.sendChatClient("§d§l[ВНИМАНИЕ] §eИспользование прокси §c§lвыключено");
				else
					MiscUtils.sendChatClient("§d§l[ВНИМАНИЕ] §eИспользование прокси §a§lвключено");
				MiscUtils.sendChatClient("§eIP:PORT - " + ip + ":" + port);
			}
			/*
			else if(args[1].equalsIgnoreCase("reload")) {
				ProxyManager.loadProxyList();
				ConfigManager.loadBotSpam();
				MiscUtils.sendChatClient("§eConfigs and proxy list reloaded!");
			}
			*/
			/*
			else if(args[1].equalsIgnoreCase("savegoodproxy")) {
				MiscUtils.sendChatClient("§cSaving good proxies...");
				ProxyManager.saveProxyList();
				MiscUtils.sendChatClient("§cGood proxies saved!");
			}
			*/
		}
		String subCmd = args[1];
		if(subCmd.equalsIgnoreCase("setip") && args.length == 3) {
			String []split = args[2].split(":");
			
			try {
				PBotCmd.ip = split[0];
				PBotCmd.port = Integer.parseInt(split[1]);
			}
			catch(Exception ex) {
				ex.printStackTrace();
				MiscUtils.sendChatClient("§cInvalid address");
				return;
			}
			
			MiscUtils.sendChatClient(color + "IP and PORT set to " + ip + ":" + port);
			return;
		}
		//==============================
		if(subCmd.equalsIgnoreCase("reload")) {
			if(args.length == 2) {
				ProxyManager.loadProxyList();
			}
			else if(args.length >= 3) {
				String filename = "";
				
				for(int a = 2; a < args.length; ++a) {
					filename += args[a];
					if(a != args.length - 1)
						filename += " ";
				}
				ConfigManager.proxy = filename;
				ProxyManager.loadProxyList();
			}
			return;
		}
		//==============================
		
		if(args.length >= 3) {
			if(args[1].equalsIgnoreCase("send")) {
				send(args[2], args);
			}
		}
		if(args.length >= 3) {
			if(args[1].equalsIgnoreCase("sendall")) {
				sendAll(args);
			}
		}
		if(args.length == 3) {
			if(args[1].equalsIgnoreCase("mlist")) {
				mlist(args);
			}
			else if(args[1].equalsIgnoreCase("add")) {
				addBot(args[2]);
			}
			else if(args[1].equalsIgnoreCase("start")) {
				startBot(args[2]);
			}
			else if(args[1].equalsIgnoreCase("join")) {
				addBot(args[2]);
				startBot(args[2]);
			}
			else if(args[1].equalsIgnoreCase("del")) {
				delBot(args[2]);
			}
			else if(args[1].equalsIgnoreCase("setpass")) {
				setPass(args[2]);
			}
		}
		
		//.pbot look IWasTesting0 0 0
		if(args.length == 5) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("look")) {
				String botName = args[2];
				float pitch = 0;
				float yaw = 0;
				
				try {
					pitch = Float.parseFloat(args[3]);
				}
				catch(Exception ex) {
					print("§cОшибка: §eЗначение " + args[3] + " не является числом!");
					return;
				}
				
				try {
					yaw = Float.parseFloat(args[4]);
				}
				catch(Exception ex) {
					print("§cОшибка: §eЗначение " + args[3] + " не является числом!");
					return;
				}
				
				if(!botName.equals("*")) {
					PBot bot = this.getBotByName(botName);
					if(bot.pbotth != null) {
						if(bot.pbotth.ready) {
							bot.pbotth.pitch = pitch;
							bot.pbotth.yaw = yaw;
							bot.pbotth.sendPacket(new Packet12PlayerLook(yaw, pitch, bot.pbotth.onGround));
						}
					}
				}
				else {
					for(PBot bot : this.bots) {
						if(bot.pbotth != null) {
							if(bot.pbotth.ready) {
								bot.pbotth.pitch = pitch;
								bot.pbotth.yaw = yaw;
								bot.pbotth.sendPacket(new Packet12PlayerLook(yaw, pitch, bot.pbotth.onGround));
							}
						}
					}
				}
			}
		}
		
		//.pbot slot IWasTesting0 0
		if(args.length == 4) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("slot")) {
				String botName = args[2];
				
				int slot = 0;
				
				try {
					slot = Integer.parseInt(args[3]);
				}
				catch(Exception ex) {
					print("§cОшибка: §eЗначение " + args[3] + " не является числом!");
					return;
				}
				
				if(!botName.equals("*")) {
					PBot bot = this.getBotByName(botName);
					if(bot.pbotth != null) {
						if(bot.pbotth.ready) {
							bot.pbotth.changeSlot(slot);
						}
					}
				}
				else {
					for(PBot bot : this.bots) {
						if(bot.pbotth != null) {
							if(bot.pbotth.ready) {
								bot.pbotth.changeSlot(slot);
							}
						}
					}
				}
			}
		}
		
		//.pbot rclick IWasTesting0
		if(args.length == 3) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("rclick")) {
				String botName = args[2];
				
				if(!botName.equals("*")) {
					PBot bot = this.getBotByName(botName);
					if(bot.pbotth != null) {
						if(bot.pbotth.ready) {
							bot.pbotth.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
						}
					}
				}
				else {
					for(PBot bot : this.bots) {
						if(bot.pbotth != null) {
							if(bot.pbotth.ready) {
								bot.pbotth.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
							}
						}
					}
				}
			}
		}
		
		if(args.length == 4) {
			if(args[1].equalsIgnoreCase("mtoggle")) {
				mtoggle(args);
			}
		}
		/*
		//.pbot invsee IWasTesting0
		if(args.length == 3) {
			String cmd = args[1];
			
			if(cmd.equalsIgnoreCase("invsee")) {
				String botname = args[2];
				PBot bot = this.getBotByName(botname);
				if(bot != null && Client.mc.thePlayer != null) {
					InventoryBasic invb = new InventoryBasic(botname, false, bot.pbotth.inventory.length);
					
					//Client.mc.thePlayer.displayGUIChest(invb);
					mc.displayGuiScreen(new GuiChest(Client.mc.thePlayer.inventory, invb));
					
					for(int a = 0; a < bot.pbotth.inventory.length; ++a) {
						invb.setInventorySlotContents(a, bot.pbotth.inventory[a]);
					}
				}
			}
		}
		*/
		if(args.length == 3) {
			boolean mod_exist = false;
			if(args[1].equalsIgnoreCase("monall")) {
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						for(ModuleBase mod : bot.pbotth.modules) {
							if(mod.name.equalsIgnoreCase(args[2])) {
								mod_exist = true;
								//mod.toggled = true;
								if(mod.toggled == false) {
									mod.toggle();
								}
								else {
									mod.toggle();
									mod.toggle();
								}
							}
						}
					}
				}
				if(mod_exist)
					print("§dModule §e" + args[2] + "§a enabled for ALL bots.");
			}
		}
		if(args.length == 3) {
			boolean mod_exist = false;
			if(args[1].equalsIgnoreCase("moffall")) {
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						for(ModuleBase mod : bot.pbotth.modules) {
							if(mod.name.equalsIgnoreCase(args[2])) {
								mod_exist = true;
								mod.toggled = false;
								mod.onDisable();
							}
						}
					}
				}
				if(mod_exist)
					print("§dModule §e" + args[2] + "§c disabled for ALL bots.");
			}
		}
		
		if(args.length == 2) {
			int count = 0;
			if(args[1].equalsIgnoreCase("count")) {
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						if(bot.pbotth.ready) {
							count++;
						}
					}
				}
				MiscUtils.sendChatClient("§eBots online: " + count);
				MiscUtils.sendChatClient("§eGoodProxy count: " + goodProxy.size());
				
			}
		}

		if(args.length == 2) {
			int count = 0;
			if(args[1].equalsIgnoreCase("pay")) {
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						if(bot.pbotth.onSurvivalServer) {
							bot.pbotth.send("/pay " + mc.thePlayer.username + " " + (bot.pbotth.money - 10));
						}
					}
				}
			}
		}
		
		if(args.length == 2) {
			int count = 0;
			if(args[1].equalsIgnoreCase("money")) {
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						if(bot.pbotth.onSurvivalServer) {
							bot.pbotth.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§aMoney: §e" + bot.pbotth.money);
						}
					}
				}
			}
		}
		
		if(args.length == 2) {
			int count = 0;
			
			//List<String> ips = new ArrayList<String>();
			
			if(args[1].equalsIgnoreCase("dump")) {
				/*
				for(PBot bot : bots) {
					if(bot.pbotth != null) {
						if(bot.pbotth.ready) {
							ips.add(bot.pbotth.getProxyAddress());
						}
					}
				}
				*/
				
				
				
				try {
					ConfigManager.createFile(ConfigManager.dirname + "/dumpgoodproxy.txt");
					
					File file = new File(ConfigManager.dirname, "dumpgoodproxy.txt");
					BufferedWriter out = new BufferedWriter(new FileWriter(file));
					
					for(String str : this.goodProxy) {
						out.write(str + "\r\n");
					}
					
					out.close();
					//ips.clear();
				}
				catch (Exception ex) {}
				
				MiscUtils.sendChatClient("§eРабочие прокси записаны в файл dumpgoodproxy.txt");
			}
		}
		
		//.pbot varlist
		if(args.length == 2) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("varlist")) {
				varlist(args);
			}
		}
		
		//.pbot varset <module> <var> <value>
		if(args.length >= 5) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("varset")) {
				varset(args);
			}
		}
		
		//.pbot config TestA_0 shopdupe_b2 warp_dupe "dupe"
		
		if(args.length == 6) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("config")) {
				config(args);
			}
		}
		
		//.pbot stopcfg
		if(args.length == 2) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("stopcfg")) {
				this.stopCfg = true;
				MiscUtils.sendChatClient("§eВыполнение конфига остановлено");
			}
		}
		
		if(args.length == 3) {
			String cmd = args[1];
			if(cmd.equalsIgnoreCase("load")) {
				stopCfg = false;
				
				final List<String> cmds = new ArrayList<String>();
				
				try {
					File file = new File(ConfigManager.dirname, args[2]);
					FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String line;
					cmds.clear();
					
					while ((line = br.readLine()) != null) {
						String curLine = line.trim();
						cmds.add(curLine);
					}
					fstream.close();
					in.close();
					br.close();
				}
				catch (Exception e) {
					e.printStackTrace();
					return;
				}
				
				Thread th = new Thread(new Runnable() {
					public void run() {
						int line = -1;
						
						for(String str : cmds) {
							line++;
							String str2 = str.toLowerCase();
							if(stopCfg)
								return;
							
							//====================SLEEP===========================
							if(str2.startsWith("sleep".toLowerCase())) {
								String []split = str2.split(" ");
								
								if(split.length < 2) {
									//MiscUtils.sendChatClient("Omg no");
									continue;
								}
								
								long delay = 10;
								try {
									delay = Long.parseLong(split[1]);
								}
								catch(Exception ex) {
									ex.printStackTrace();
									MiscUtils.sendChatClient("Error on line '" + line + "' in file '" + args[2] + "'");
									return;
								}
								MiscUtils.sleep(delay);
							}
							//====================SLEEP===========================
							//====================print===========================
							else if(str2.startsWith("print".toLowerCase())) {
								
								String []split = str2.split(" ");
								
								if(split.length < 2) {
									//MiscUtils.sendChatClient("Omg no");
									continue;
								}
								
								String msg = "";
								for(int a = 1; a < split.length; ++a) {
									split[a] = split[a].replace('&', '§');
									msg += split[a];
									if(a != split.length - 1)
										msg += " ";
								}
								MiscUtils.sendChatClient(msg);
							}
							//====================print===========================
							//====================WAIT===========================
							else if(str2.startsWith("wait".toLowerCase())) {
								
								String []split = str2.split(" ");
								
								if(split.length < 2) {
									//MiscUtils.sendChatClient("Omg no");
									continue;
								}
								
								String name = split[1];
								
								while(true) {
									if(stopCfg)
										return;
									
									PBot bot = getBotByName(name);
									if(bot != null) {
										if(bot.pbotth != null) {
											if(bot.pbotth.loggined && bot.pbotth.onSurvivalServer) {
												break;
											}
										}
									}
									MiscUtils.sleep(250L);
								}
								//MiscUtils.sendChatClient("Waiting for bot '" + name + "' done");
								print("§eBot §8[§a" + name + "§8] " + "§a: Waiting done.");
							}
							//====================WAIT===========================
							else {
								if(stopCfg)
									return;
								
								Client.getInstance().onSendChat(str);
							}
							
							//MiscUtils.sendChatClient(str2);
						}
					}
				}, "pbot load");
				th.start();
			}
		}
		
		if(args.length == 3) {
			if(args[1].equalsIgnoreCase("cpp")) {
				connectionsPerProxy(args);
			}
		}
		/*
		if(args.length == 4) {
			if(args[1].equalsIgnoreCase("mnew")) {
				mnew(args);
			}
		}
		*/
		if(args.length == 2) {
			if(args[1].equalsIgnoreCase("marry")) {
				Thread th = new Thread(new Runnable() {
					public void run() {
						for(int a = 0; a < bots.size(); ++a) {
							if(a < bots.size() - 1) {
								PBot bot1 = bots.get(a);
								PBot bot2 = bots.get(a + 1);
								bot1.instance.pbotth.send("/marry " + bot2.instance.name);
								MiscUtils.sleep(250L);
							}
						}
					}
				}, "marry Thread");
				th.start();
			}
		}
		
		/*
		if(args.length == 2) {
			if(args[1].equalsIgnoreCase("rg")) {
				for(int a = 0; a < bots.size(); ++a) {
					PBot bot1 = bots.get(a);
					bot1.instance.pbotth.send("/rg claim " + bot1.instance.name);
				}
			}
		}
		*/
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("brute")) {
				
				joining = true;
				bruteRunning = false;
				MiscUtils.sleep(100L);
				
				if(bruteTh != null) {
					bruteTh.interrupt();
					bruteTh.stop();
					bruteTh = null;
				}
				
				bruteTh = new Thread(new Runnable() {
					public void run() {
						List<String> nicknames = new ArrayList<String>();
						
						try {
							File file = new File(ConfigManager.dirname, "nicknames.txt");
							FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
							DataInputStream in = new DataInputStream(fstream);
							BufferedReader br = new BufferedReader(new InputStreamReader(in));
							String line;
							nicknames.clear();
							
							while ((line = br.readLine()) != null) {
								String curLine = line.trim();
								if(!nicknames.contains(curLine.toLowerCase()))
									nicknames.add(curLine.toLowerCase());
							}
							fstream.close();
							in.close();
							br.close();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						
						if(nicknames.size() <= 0)
							return;
						
						int indexFrom = 0;
						int indexTo = nicknames.size();
						
						if(args.length == 4) {
							try {
								indexFrom = Integer.parseInt(args[2]);
								indexTo = Integer.parseInt(args[3]);
							}
							catch(Exception ex) {
								MiscUtils.sendChatClient("§c§lError: " + ex.getMessage());
							}
						}
						
						TimeHelper time1 = new TimeHelper();
						time1.reset();
						TimeHelper time2 = new TimeHelper();
						time2.reset();
						
						while(bruteRunning) {
							if(!joining)
								return;
							
							int clientonline = 1;
							try {
								if(Client.mc.getNetHandler().disconnected) {
									clientonline = 0;
								}
							}
							catch(Exception ex) {
								//ex.printStackTrace();
								clientonline = 0;
							}
							
							if(bots.size() < ProxyManager.connections_per_proxy - clientonline) {
							//if(bots.size() < 999) {
								String nickname = nicknames.get(indexFrom);
								addBot(nickname);
								startBot(nickname);
								
								print("§a[BRUTE] joining " + nickname + "...");
								MiscUtils.sleep(joinDelay.get(0)); //4000L HappyOrange
								//MiscUtils.sleep(100); //4000L HappyOrange
								indexFrom++;
							}
							MiscUtils.sleep(250L);
						}
					}
				}, "join bots");
				
				bruteRunning = true;
				bruteTh.start();
			}
		}
		
		if(args.length == 2) {
			if(args[1].equalsIgnoreCase("startall")) {
				startAll();
			}
			else if(args[1].equalsIgnoreCase("delall")) {
				delAll();
			}
			else if(args[1].equalsIgnoreCase("list")) {
				list();
			}
			else if(args[1].equalsIgnoreCase("usedproxy")) {
				int count = ProxyManager.getUsedProxyCount();
				MiscUtils.sendChatClient("§cUsed proxyes: §d§l" + count);
			}
			else if(args[1].equalsIgnoreCase("proxyinfo")) {
				proxyInfo();
			}
			else if(args[1].equalsIgnoreCase("proxyoff")) {
				ProxyManager.proxy_enabled = false;
				print("§a[PBOTS] Proxy disabled");
			}
			else if(args[1].equalsIgnoreCase("proxyon")) {
				ProxyManager.proxy_enabled = true;
				print("§a[PBOTS] Proxy enabled");
			}
		}
		
		if(args.length == 5) {
			if(args[1].equalsIgnoreCase("join")) {
				massJoin2(args);
			}
		}
		
		if(args.length == 4) {
			if(args[1].equalsIgnoreCase("join")) {
				massJoin(args);
			}
		}
	}
	
	public static Thread bruteTh = null;
	public static boolean bruteRunning = false;
	
	public PBotVar getVar(String moduleName, String varName) {
		PBotVar ret = null;
		for(PBotVar var : PBotCmd.pbot_vars) {
			if(var.getModuleName().equalsIgnoreCase(moduleName) && var.getVarName().equalsIgnoreCase(varName)) {
				ret = var;
				break;
			}
		}
		
		return ret;
	}
	
	public void print(String msg) {
		PBotVar showChat = getVar("this", "showChat");
		if(showChat != null) {
			if(showChat.get(false)) {
				MiscUtils.sendChatClient(msg);
			}
		}
		else {
			MiscUtils.sendChatClient(msg);
		}
	}

	public void varlist(String[] args) {
		for(PBotVar var : pbot_vars) {
			if(var.getType() == VarType.BOOLEAN)
				MiscUtils.sendChatClient("§d" + var.getModuleName() + " §f| §a" + var.getVarName() + " §f| §9" + var.getType() + " §f| §e" + var.get(false));
			else if(var.getType() == VarType.INT)
				MiscUtils.sendChatClient("§d" + var.getModuleName() + " §f| §a" + var.getVarName() + " §f| §9" + var.getType() + " §f| §e" + var.get(0));
			else if(var.getType() == VarType.DOUBLE)
				MiscUtils.sendChatClient("§d" + var.getModuleName() + " §f| §a" + var.getVarName() + " §f| §9" + var.getType() + " §f| §e" + var.get(0D));
			else if(var.getType() == VarType.STRING)
				MiscUtils.sendChatClient("§d" + var.getModuleName() + " §f| §a" + var.getVarName() + " §f| §9" + var.getType() + " §f| §e" + var.get(""));
		}
	}

	public void varset(String[] args) {
		String modulename = args[2];
		String varname = args[3];
		for(PBotVar var : pbot_vars) {
			if(var.getVarName().equalsIgnoreCase(varname) && var.getModuleName().equalsIgnoreCase(modulename)) {
				String newvalue = "";
				for(int a = 4; a < args.length; ++a) {
					newvalue += args[a];
					if(a != args.length - 1)
						newvalue += " ";
				}
				
				if(var.getType() == VarType.BOOLEAN) {
					try {
						boolean value = Boolean.parseBoolean(newvalue);
						var.setBool(value);
						print("§eVar '" + varname + "' in module " + modulename + " set to: " + newvalue);
					}
					catch(Exception ex) {
						print("§cInvalid value, its should be BOOLEAN type (false/true)");
					}
				}
				else if(var.getType() == VarType.INT) {
					try {
						int value = Integer.parseInt(newvalue);
						var.setInt(value);
						print("§eNew value saved!");
					}
					catch(Exception ex) {
						print("§cInvalid value, its should be INT type (147)");
					}
				}
				else if(var.getType() == VarType.DOUBLE) {
					try {
						double value = Double.parseDouble(newvalue);
						var.setDouble(value);
						print("§eNew value saved!");
					}
					catch(Exception ex) {
						print("§cInvalid value, its should be DOUBLE type (3.14)");
					}
				}
				else if(var.getType() == VarType.STRING) {
					var.setString(newvalue);
					print("§eNew value saved!");
				}
				
				return;
			}
		}
	}

	public void mlist(String[] args) {
		//bot module list
		PBot bot = getBotByName(args[2]);
		if(bot != null) {
			MiscUtils.sendChatClient("§eBot: §c§l" + bot.name + " §e modules:");
			for(ModuleBase mod : bot.pbotth.modules) {
				if(mod.toggled)
					MiscUtils.sendChatClient("§aOn §e" + mod.name + "");
				else
					MiscUtils.sendChatClient("§cOff §e" + mod.name + "");
			}
		}
	}

	public ModuleBase getBotModule(String botname, String modulename) {
		ModuleBase module = null;
		PBot bot = getBotByName(botname);
		
		if(bot != null) {
			if(bot.pbotth != null) {
				for(ModuleBase m : bot.pbotth.modules) {
					if(m.name.equalsIgnoreCase(modulename)) {
						module = m;
						break;
					}
				}
			}
		}
		return module;
	}
	
	public void connectionsPerProxy(String[] args) {
		try {
			ProxyManager.connections_per_proxy = Integer.parseInt(args[2]);
		}
		catch(Exception ex) {ProxyManager.connections_per_proxy = 3;}
		MiscUtils.sendChatClient("§a[PBOTS] connections per proxy set to " + ProxyManager.connections_per_proxy);
	}

	public void proxyInfo() {
		for(ProxyConnection pcon : ProxyManager.addr_list) {
			pcon.isOk();
			String pref = "§a";
			if(!pcon.ok) {
				pref = "§c";
			}
			
			MiscUtils.sendChatClient(pref + "Proxy: §f§l" + pcon.address);
			MiscUtils.sendChatClient(pref + "Connections: §f§l" + pcon.connections_list.size());
			MiscUtils.sendChatClient(pref + "Online: §f§l" + pcon.ok);
			for(PBotConnection bcon : pcon.connections_list) {
				MiscUtils.sendChatClient(pref + "   Server address: §f§l" + bcon.ip + ("" + bcon.port));
				MiscUtils.sendChatClient(pref + "   Nickname: §f§l" + bcon.name);
			}
		}
	}

	public void config(String[] args) {
		String botname = args[2];
		String modulename = args[3];
		String variable = args[4];
		String newvalue = args[5];
		
		PBot bot = getBotByName(botname);
		if(bot != null) {
			if(bot.pbotth != null) {
				if(bot.pbotth.enabled && !bot.pbotth.stop) {
					for(ModuleBase botmodule : bot.pbotth.modules) {
						if(botmodule.name.equalsIgnoreCase(modulename)) {
							Field field = null;
							try {
								field = botmodule.getClass().getField(variable);
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
							boolean accessible = field.isAccessible();
							
							field.setAccessible(true);
							
							String obj = new String(newvalue);
							try {
								field.set(botmodule, obj);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
								return;
							} catch (IllegalAccessException e) {
								e.printStackTrace();
								return;
							}
							
							field.setAccessible(accessible);
							
							print("§eVar §a§l" + variable + " §ein §a§l" + modulename + " §eset to §a§l" + newvalue);
						}
					}
				}
			}
		}
	}

	public void mtoggle(String[] args) {
		//toggle bot module
		PBot bot = getBotByName(args[2]);
		if(bot != null) {
			if(bot.pbotth != null) {
				for(ModuleBase mod : bot.pbotth.modules) {
					if(mod.name.equalsIgnoreCase(args[3])) {
						mod.toggle();
						if(mod.toggled)
							print("§dModule §e" + mod.name + "§a enabled.");
						else
							print("§dModule §e" + mod.name + "§c disabled.");
					}
				}
			}
		}
	}
	
	private static PBotVar joinDelay = new PBotVar("this", "joinDelay", 500);

	public void massJoin2(final String[] args) {
		joining = true;
		final String name = args[4];
		
		Thread th = new Thread(new Runnable() {
			public void run() {
				int num_start = Integer.parseInt(args[2]);
				int num_end = Integer.parseInt(args[3]);
				for(int a = num_start; a < num_end + 1; ++a) {
					String str = name + a;
					
					if(!joining)
						return;
					
					addBot(str);
					startBot(str);
					
					MiscUtils.sleep(joinDelay.get(0));
				}
			}
		}, "join bots");
		th.start();
	}

	public void massJoin(final String[] args) {
		joining = true;
		Thread th = new Thread(new Runnable() {
			public void run() {
				int num = Integer.parseInt(args[2]);

				if(ProxyManager.proxy_enabled) {
					if(num > ProxyManager.addr_list.size() * ProxyManager.connections_per_proxy) {
						num = ProxyManager.addr_list.size() * ProxyManager.connections_per_proxy;
						MiscUtils.sendChatClient("§cBots count limited to: §d§l" + num);
						MiscUtils.sendChatClient("§cCanceled.");
						return;
					}
				}
				
				for(int a = 0; a < num; ++a) {
					String str = args[3] + a;
					if(!joining)
						return;
					
					addBot(str);
					startBot(str);
					
					MiscUtils.sleep(joinDelay.get(0)); //4000L HappyOrange
				}
				
				print("§a" + num + " bots joined!");
			}
		}, "join bots");
		th.start();
	}

	public void addBot(String name) {
		for(PBot bot : bots) {
			if(bot.name.equalsIgnoreCase(name)) {
				print("§cBot already exists!");
				return;
			}
		}
		PBot bot = new PBot(name);
		bots.add(bot);
	}

	public void setPass(String pass) {
		password = pass;
		print("§dPassword set!");
	}
	
	public void send(String name, String []args) {
		for(PBot bot : bots) {
			if(bot.pbotth != null) {
				if(bot.name.equalsIgnoreCase(name)) {
					if(bot.pbotth.enabled) {
						String msg = "";
						for(int a = 3; a < args.length; ++a) {
							msg += args[a] + " ";
						}
						bot.pbotth.send(msg);
					}
				}
			}
		}
	}
	
	public void sendAll(String []args) {
		for(PBot bot : bots) {
			if(bot.pbotth != null) {
				if(bot.pbotth.enabled && !bot.pbotth.stop) {
					String msg = "";
					for(int a = 2; a < args.length; ++a) {
						msg += args[a] + " ";
					}
					bot.pbotth.send(msg);
				}
			}
		}
	}
	
	public PBot getBotByName(String name) {
		for(PBot bot : bots) {
			if(bot.name.equalsIgnoreCase(name)) {
				return bot;
			}
		}
		return null;
	}

	public void startBot(String name) {
		PBot bot = getBotByName(name);
		if(bot != null) {
			if(bot.name.equalsIgnoreCase(name)) {
				try {
					//String ip = GuiConnecting.IP;
					//String port = GuiConnecting.PORT;
					String ip = PBotCmd.ip;
					int port = PBotCmd.port;
					bot.startBot(ip + ":" + port);
					
					//BotControl thing
					BotControl.list.add(bot.name);
					
				} catch (IOException e) {
					e.printStackTrace();
					MiscUtils.sendChatClient("§cStarting bot §a" + bot.name + " §cfailed!");
				}
			}
		}
	}

	public void startAll() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				for(PBot bot : bots) {
					startBot(bot.name);
					MiscUtils.sleep(500L);
				}
			}
		}, "startall bots");
	}

	public void stopBot(String name) {
		PBot bot = getBotByName(name);
		if(bot != null) {
			if(bot.name.equalsIgnoreCase(name)) {
				print("§dBot §a" + name + " §dstopped!");
				bot.stopBot();
			}
		}
	}

	public void stopAll() {
		print("§dStopping all bots...");
		for(PBot bot : bots) {
			bot.stopBot();
		}
		print("§d" + bots.size() + " bots stopped!");
	}

	public void delBot(String name) {
		PBot bot = getBotByName(name);
		if(bot != null) {
			if(bot.name.equalsIgnoreCase(name)) {
				//print("§dBot " + name + " destroyed!");
				print("§eBot §8[§a" + name + "§8] " + "§c§lRemoved.");
				bot.stopBot();
				bots.remove(bot);
			}
		}
	}

	public void delAll() {
		joining = false;
		print("§dDestroying all bots...");
		for(PBot bot : bots) {
			bot.stopBot();
		}
		print("§d" + bots.size() + " bots destroyed!");
		bots.clear();
		ProxyManager.clearBotConnections();
	}

	public void list() {
		for(PBot bot : bots) {
			if(bot.pbotth != null) {
				if(bot.pbotth.enabled)
					MiscUtils.sendChatClient("§eBot §a§l" + bot.name + " §eenabled: §a§lTRUE");
				else
					MiscUtils.sendChatClient("§eBot §a§l" + bot.name + " §eenabled: §c§lFALSE");
			}
			else
				MiscUtils.sendChatClient("§eBot §a§l" + bot.name + " §eenabled: §c§lnull");
		}
	}
}
