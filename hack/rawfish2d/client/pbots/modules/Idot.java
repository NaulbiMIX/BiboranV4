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
import net.minecraft.src.Packet203AutoComplete;
import net.minecraft.src.Packet3Chat;

public class Idot extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Idot", "gToggled", false);
	
	private TimeHelper time = new TimeHelper();
	private static CopyOnWriteArrayList<String> arr = new CopyOnWriteArrayList<String>();
	private String []nicks = null;
	private Random rnd = new Random();
	private boolean initialized = false;
	
	public Idot(PBotThread bot) {
		super(bot, "Idot");
		toggled = gToggled.get(false);
		
		time.reset();
		
		//initialized = false;
		//arr.clear();
		if(initialized == false) {
			arr.add("! даи мне яаца мопов");
			arr.add("! атмин ты допры");
			arr.add("! пачиму этот кактус называетца сервер да?");
			arr.add("! как аткрыть приват для друга пж он не может вайти в сервер");
			arr.add("! дайте бан пж [player] гриферит через приват на модерке!!!");
			arr.add("! ищу родитилей 8 лет в скайп");
			arr.add("! ищу девушку или парня тп");
			arr.add("! поафывапывпра офывпа огнфыпвша фпывшг апф");
			arr.add("! как называеца чит на ломание севера скажите пж");
			arr.add("! даите айпи этово севера не могу зайти пж");
			arr.add("! пж как скачат чит называетца FallOut Shelter оч надо пж");
			arr.add("! знаеш шо у меня мильон хромосомов это болше чем у тебя");
			arr.add("! как писать в общий таб?");
			arr.add("! дайте вещи а то мой друг админ вас всех забанит");
			arr.add("! я иногда прамахиваюсь когда писаю в туалет");
			arr.add("! што делать у меня закончилось icq");
			arr.add("! хелпер тп помоги пж оч важно дай ресов на дом пж я дам 100$");
			arr.add("! а я всигда гатоф, долбить катоф, в попу в перед - лублю катоф");
			arr.add("! маё любимое число алфавита это зелёный");
			arr.add("! помогите пж я закрыл глаза и стало темно щшто делать");
			arr.add("! скажите пж какая фамилия у путина ");
			arr.add("! как стать овнером скажите пж");
			arr.add("! как купить дом на спавне");
			arr.add("! дабавте в спавн я аркитектор");
			arr.add("! ДАЙте баН ИГРОКУ [player] он грифер он меня бёт");
			arr.add("! север гомно даже хелпера не дают");
			arr.add("! атмин кактус лучший даже 1000 уровен не дал");
			arr.add("! аааааааааааааааааааааааааааааааааааааааааааааааааааа");
			arr.add("! ааа пауки аааа помогите пауки со всех старон лезут ааа а а");
			arr.add("! rfrjq rfvfyljq cltkfn ct,t ,jkmie. gbc. crf;bnt g;");
			arr.add("! бот убил маево друга читом дайте бан пж");
			arr.add("! разбаните маево друга пж он болше не будет писать админ пидор");
			arr.add("! как зарегистроватца");
			arr.add("! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk");
			arr.add("! админы побаньте клонов");
			arr.add("! я сосу");
			arr.add("! памагите на севере вирус немагу крутить мышку");
			arr.add("! атмины приватят мне ресы и я немогу вайти в север");
			arr.add("! атмины [player] читер он бижал по моему привату даите бан пж");
			arr.add("! [player] чит он миня убил рукой");
			arr.add("! [player] читер забанте пж он меня бёт в доме аааааааааааа!!!!!");
			arr.add("! [player] бан за чит");
			arr.add("! [player] не надо пж я не буду боше прости миня я не буду это пизвени");
			arr.add("! [player] ты читер ты меня убивал вчера я помню да бан");
			arr.add("! [player] приюти меня на територию которую ты мне проддал");
			arr.add("! как установить мод на тлаунчер на чит чтоб было 1 5 2 ?");
			arr.add("! админы помогите у нас отключили свет!");
			arr.add("! пж кто купит мне донат или админку");
			arr.add("! кто в лс писать в дискорд");
			arr.add("! кто знает такой чит думсдей б2");
			arr.add("! ултиматж хак самий луший чит в мири");
			arr.add("! дунсдеи в2 самий луший чит в мири");
			arr.add("! в какои стране находится америка?");
			arr.add("! [player] а как ти заходиш на север если тут стоит bungee coord ?");
			arr.add("! админы поставте сохронение вешей я полсе вайпа потерял дом и жену!");
			//не надо пж я не буду боше прости миня я не буду это пизвени
			initialized = true;
		}
	}
	
	private String getRandomNickname() {
		bot.sendPacket(new Packet203AutoComplete("/call "));
		while(nicks == null) {
			MiscUtils.sleep(50L);
		}
		String str = nicks[rnd.nextInt(nicks.length)];
		nicks = null;
		return str;
	}
	
	private String getMessage() {
		String str = arr.get(rnd.nextInt(arr.size()));
		return str;
	}
	
	private String replaceNicknames(String str, String player) {
		str = str.replaceAll("\\[player]", player);
		
		String test = str.replaceAll("[0-9]", "");
		if(test.length() < str.length() - 5) {
			str = str.replaceAll("[0-9]", "");
		}
		return str;
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
		
		if(packet instanceof Packet203AutoComplete) {
			Packet203AutoComplete p = (Packet203AutoComplete)packet;
			nicks = p.getText().split("\u0000");
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(time.hasReached(60000L)) {
			String msg = getMessage();
			String nick = getRandomNickname();
			String final_msg = replaceNicknames(msg, nick);
			bot.send(final_msg);
			time.reset();
		}
	}
}
