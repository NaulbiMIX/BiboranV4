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
			arr.add("! ��� ��� ���� �����");
			arr.add("! ����� �� �����");
			arr.add("! ������ ���� ������ ���������� ������ ��?");
			arr.add("! ��� ������� ������ ��� ����� �� �� �� ����� ����� � ������");
			arr.add("! ����� ��� �� [player] �������� ����� ������ �� �������!!!");
			arr.add("! ��� ��������� 8 ��� � �����");
			arr.add("! ��� ������� ��� ����� ��");
			arr.add("! ������������� ������ ��������� ������ ���");
			arr.add("! ��� ��������� ��� �� ������� ������ ������� ��");
			arr.add("! ����� ���� ����� ������ �� ���� ����� ��");
			arr.add("! �� ��� ������ ��� ���������� FallOut Shelter �� ���� ��");
			arr.add("! ����� �� � ���� ������ ���������� ��� ����� ��� � ����");
			arr.add("! ��� ������ � ����� ���?");
			arr.add("! ����� ���� � �� ��� ���� ����� ��� ���� �������");
			arr.add("! � ������ ������������ ����� ����� � ������");
			arr.add("! ��� ������ � ���� ����������� icq");
			arr.add("! ������ �� ������ �� �� ����� ��� ����� �� ��� �� � ��� 100$");
			arr.add("! � � ������ �����, ������� �����, � ���� � ����� - ����� �����");
			arr.add("! �� ������� ����� �������� ��� ������");
			arr.add("! �������� �� � ������ ����� � ����� ����� ���� ������");
			arr.add("! ������� �� ����� ������� � ������ ");
			arr.add("! ��� ����� ������� ������� ��");
			arr.add("! ��� ������ ��� �� ������");
			arr.add("! ������� � ����� � ����������");
			arr.add("! ����� ��� ������ [player] �� ������ �� ���� ��");
			arr.add("! ����� ����� ���� ������� �� ����");
			arr.add("! ����� ������ ������ ���� 1000 ������ �� ���");
			arr.add("! ����������������������������������������������������");
			arr.add("! ��� ����� ���� �������� ����� �� ���� ������ ����� ��� � �");
			arr.add("! rfrjq rfvfyljq cltkfn ct,t ,jkmie. gbc. crf;bnt g;");
			arr.add("! ��� ���� ����� ����� ����� ����� ��� ��");
			arr.add("! ��������� ����� ����� �� �� ����� �� ����� ������ ����� �����");
			arr.add("! ��� ���������������");
			arr.add("! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk");
			arr.add("! ������ �������� ������");
			arr.add("! � ����");
			arr.add("! �������� �� ������ ����� ������ ������� �����");
			arr.add("! ������ �������� ��� ���� � � ������ ����� � �����");
			arr.add("! ������ [player] ����� �� ����� �� ����� ������� ����� ��� ��");
			arr.add("! [player] ��� �� ���� ���� �����");
			arr.add("! [player] ����� ������� �� �� ���� �� � ���� ������������!!!!!");
			arr.add("! [player] ��� �� ���");
			arr.add("! [player] �� ���� �� � �� ���� ���� ������ ���� � �� ���� ��� �������");
			arr.add("! [player] �� ����� �� ���� ������ ����� � ����� �� ���");
			arr.add("! [player] ������ ���� �� ��������� ������� �� ��� �������");
			arr.add("! ��� ���������� ��� �� �������� �� ��� ���� ���� 1 5 2 ?");
			arr.add("! ������ �������� � ��� ��������� ����!");
			arr.add("! �� ��� ����� ��� ����� ��� �������");
			arr.add("! ��� � �� ������ � �������");
			arr.add("! ��� ����� ����� ��� ������� �2");
			arr.add("! �������� ��� ����� ����� ��� � ����");
			arr.add("! ������� �2 ����� ����� ��� � ����");
			arr.add("! � ����� ������ ��������� �������?");
			arr.add("! [player] � ��� �� ������� �� ����� ���� ��� ����� bungee coord ?");
			arr.add("! ������ �������� ���������� ����� � ����� ����� ������� ��� � ����!");
			//�� ���� �� � �� ���� ���� ������ ���� � �� ���� ��� �������
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
