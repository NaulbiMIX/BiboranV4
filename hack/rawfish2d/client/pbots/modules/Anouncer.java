package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.ModUnused.BanMuteKick;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet3Chat;

public class Anouncer extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Anouncer", "gToggled", false);
	
	private TimeHelper delay = new TimeHelper();
	private long delayMS;
	private long staticDelayMS;
	private String staticMsg;
	private List<String> remaining = new ArrayList<String>();
	
	private static CopyOnWriteArrayList<String> tempmute_arr = new CopyOnWriteArrayList<String>();
	private static CopyOnWriteArrayList<String> tempban_arr = new CopyOnWriteArrayList<String>();
	
	private static CopyOnWriteArrayList<String> kick_arr = new CopyOnWriteArrayList<String>();
	
	private static CopyOnWriteArrayList<String> mute_arr = new CopyOnWriteArrayList<String>();
	private static CopyOnWriteArrayList<String> ban_arr = new CopyOnWriteArrayList<String>();
	private Random rnd = new Random();
	
	private boolean initialized = false;
	
	public Anouncer(PBotThread bot) {
		super(bot, "Anouncer");
		toggled = gToggled.get(false);
		
		delay.reset();
		delayMS = 2000L;
		staticDelayMS = (1000L * 60L * 10L); //раз в 10 минут
		staticMsg = "Всем ку! Я 'annoying bot' и я отвечаю на баны/муты/кики смешными сообщениями.";
		
		if(initialized == false) {
			initialized = true;
			tempmute_arr.add("![player] наговорил много нехорошего, поэтому [admin] наказал его!");
			tempmute_arr.add("![player] стал немым как Жора Фридман! Но это не надолго");
			tempmute_arr.add("![player] наконец заткнулся ура! Жаль это ненадолго");
			tempmute_arr.add("!Игрока [player] замутили! Но где доказательства? Гррр!! БунД!");
			tempmute_arr.add("![admin] обиделся на слова [player] и решил его замутить");
			tempmute_arr.add("![player] не отдал сотку [admin]'у и был наказан!");
			tempmute_arr.add("!Что? Игрока [player] замутили? ... Ну и ладно");
			tempmute_arr.add("![admin] магией левой ноги приказал [player] заткнуться! Но это ненадолго");
			tempmute_arr.add("!Надеюсь [player] станет вести себя лучше, а пока пусть посидит в муте");
			
			tempban_arr.add("![player] был забанен! Так ему и надо... Вот уж редиска");
			tempban_arr.add("![player] забанили. Но ничего, теперь он будет играть только в фортнайт");
			tempban_arr.add("![player] забанили, ему же лучше, кампутер вреден для здоровья!");
			tempban_arr.add("!У [player] будет куча времени чтоб делать уроки так как его ЗАБАНИЛИ!");
			tempban_arr.add("![admin] зачем-то забанил [player]. Но это не наше дело, правда?");
			tempban_arr.add("![admin] зачем-то забанил [player]. Но это не наше дело, правда?");
			tempban_arr.add("!Кто-то сказал бан? [admin] жестокого забанил [player]");
			tempban_arr.add("!Не, ну эта бан. [player] вылетел с сервера красиво!");
			tempban_arr.add("![player] лови бан, пуся <3");
			tempban_arr.add("!Вы чо! Надо было банить [player] по aйпи! Не ну как так то а?");
			
			ban_arr.add("!Ой, кажется [player] был забанен навсегда. Ну и ладно");
			ban_arr.add("![admin] навсегда забанил [player]. Это что война?");
			ban_arr.add("!Ало, это Красти Крабс? Нет! Это [player] получил бан! Ха!");
			ban_arr.add("![player] получил бан, теперь он точно хочет сломать север!");
			ban_arr.add("![player] на жопной тяге улетел с сервер. Навсегда");
			ban_arr.add("!Перебан? [player] был забанен, добром это не кончится");
			ban_arr.add("!Кто-то получил пятёрку, кто-то тройку, а вот [player] получил бан");
			ban_arr.add("![player] получил бан и уже пишет жалобу на [admin]");
			ban_arr.add("![admin] снова забанил [player]. Впрочем ничего нового");
			ban_arr.add("!Последнее что сказал [player] перед баном было: NANI?");
			
			mute_arr.add("![player] наговорил много нехорошего, поэтому [admin] замутил его!");
			mute_arr.add("![player] поздравляю! Ты выиграл мут! И это совершенно бесплатно!");
			mute_arr.add("![player] словил мут, и стало даже как-то скучно");
			mute_arr.add("!Зачем вы замутили [player]? У него же лапки");
			mute_arr.add("![admin] обиделся на [player] и дал ему мут лол");
			mute_arr.add("![player] лови мут и не матюкайся, пуся <3");
			mute_arr.add("![player] ещё долго не будет писать в чат");
			mute_arr.add("![admin] магией пальца левой ноги приказал [player] заткнуться!");
			mute_arr.add("!Надеюсь [player] станет вести себя лучше, а пока пусть посидит в муте");
			
			kick_arr.add("!По щелчку пальцев админа [admin], игрок [player] пропадает с сервера!");
			kick_arr.add("![admin] пинком под попу выгнал [player] с сервера!");
			kick_arr.add("![player] втик с городу при помощи [admin]");
			kick_arr.add("![player] лучше бы играл в фортнайт, там его не смогут кикнуть! Ха!");
			kick_arr.add("!Игрока [player] вышвырнули с сервера, но он скоро вернётся и закамбечит!");
			kick_arr.add("!У [player] интернет за сорок грывень или его кикнули? Я нипанимаю");
			kick_arr.add("![player] так сильно рофлил что упал с сервера лол");
			kick_arr.add("![player] словил кик, но он щас вернётся, без паники");
			kick_arr.add("!Надеюсь [player] знает как зайти обратно");
			kick_arr.add("!Кто-то роняет запад, а [admin] уронил игрока [player]");
			kick_arr.add("!Игрока [player] кикнули только что а я уже по нему скучаю эх...");
			kick_arr.add("![player] выйди отсюда розбiйник");
		}
	}
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		if(packet instanceof Packet3Chat) {
			processPacket((Packet3Chat)packet);
		}
		
		if(packet instanceof Packet201PlayerInfo) {
			Packet201PlayerInfo p = (Packet201PlayerInfo) packet;
			
			if(!p.playerName.contains("dasmagichippo"))
				return;
			else {
				if(p.isConnected) {
					//bot.send("! Данил привет! как поживаешь?");
					bot.sendChatClient("§d[NameDetect] §e" + p.playerName + "§a detected. " + p.ping);
				}
				/*
				else if(!p.isConnected){
					MiscUtils.sendChat("! пока Данил(");
					MiscUtils.sendChatClient("§d[NameDetect] §e" + p.playerName + "§c left from the server/vanished");
				}
				*/
				return;
			}
		}
	}
	
	private String replaceNicknames(String str, BanMuteKick bmk) {
		//System.out.println("str 1: " + str);
		str = str.replaceAll("\\[player]", bmk.player);
		str = str.replaceAll("\\[admin]", bmk.admin);
		
		String test = str.replaceAll("[0-9]", "");
		if(test.length() < str.length() - 5) {
			str = str.replaceAll("[0-9]", "");
		}
		//System.out.println("str 2: " + str);
		return str;
	}
	
	private String getRandomTempBanMessage(BanMuteKick bmk) {
		String str = tempban_arr.get(rnd.nextInt(tempban_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomTempMuteMessage(BanMuteKick bmk) {
		String str = tempmute_arr.get(rnd.nextInt(tempmute_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomKickMessage(BanMuteKick bmk) {
		String str = kick_arr.get(rnd.nextInt(kick_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomBanMessage(BanMuteKick bmk) {
		String str = ban_arr.get(rnd.nextInt(ban_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private String getRandomMuteMessage(BanMuteKick bmk) {
		String str = mute_arr.get(rnd.nextInt(mute_arr.size()));
		str = replaceNicknames(str, bmk);
		return str;
	}
	
	private void processPacket(Packet3Chat packet) {
		if(!bot.instance.name.equalsIgnoreCase("1")) {
			//§e
			String msg = packet.message.toLowerCase();
			//msg = "§a§l§a? §cVladik §eвременно замутил §czombue§e.";
			
			if(msg.contains("§a§l§a")) {
				String []split = msg.split(" ");
				
				for(int a = 0; a < split.length; ++a) {
					split[a] = split[a].replaceAll("§[0-9]", "");
					split[a] = split[a].replaceAll("§[a-f]", "");
					
					split[a] = split[a].replaceAll("§[k-l]", "");
					split[a] = split[a].replaceAll("§r", "");
				}
				//remove dot from last str
				split[split.length - 1] = split[split.length - 1].substring(0, split[split.length - 1].length() - 1);
				
				String author_gets_punished = "!Ну всё, пришло время петь песню 'я роняю кактус уууу'! Призываю инопришеленцев!!!";
				String ddos_gets_punished = "!Розбiйник напав на призидента Украiни та почухав його дупу своiм олiвцем!";
				BanMuteKick bmk = new BanMuteKick();
				String sendmsg = null;
				
				if(split[2].contains("временно")) {
					if(split[3].contains("замутил")) {
						bmk.admin = split[1];
						bmk.action = split[2] + " " + split[3];
						bmk.player = split[4];
						
						sendmsg = this.getRandomTempMuteMessage(bmk);
					}
					else if(split[3].contains("забанил")) {
						bmk.admin = split[1];
						bmk.action = split[2] + " " + split[3];
						bmk.player = split[4];
						
						sendmsg = this.getRandomTempBanMessage(bmk);
					}
				}
				else if(split[2].contains("навсегда")) {
					bmk.admin = split[1];
					bmk.action = split[2] + " " + split[3];
					bmk.player = split[4];
					
					if(split[3].contains("забанил")) {
						sendmsg = this.getRandomBanMessage(bmk);
					}
					else if(split[3].contains("замутил")) {
						sendmsg = this.getRandomMuteMessage(bmk);
					}	
				}
				else if(split[2].contains("кикнул")) {
					bmk.admin = split[1];
					bmk.action = split[2];
					bmk.player = split[3];
					
					sendmsg = this.getRandomKickMessage(bmk);
				}
				
				if(sendmsg != null) {
					if(bmk.player.equalsIgnoreCase("RawFish2D")) {
						bot.send(author_gets_punished);
						return;
					}
					else if(bmk.player.equalsIgnoreCase("Ddos1337")) {
						bot.send(ddos_gets_punished);
						return;
					}
					
					bot.send(sendmsg);
				}
			}
		}
	}
}
