package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;

public class AutoChat extends Module{
	
	public static String msg;
	
	public AutoChat() {
		super("AutoChat", 0, ModuleType.MISC);
		setDescription("������������� �������� �� ��������� (�������� ������ �� CactusCraft)");
	}
	
	@Override
	public void onChatMessage(String str) {
		if (str.contains("�7������� � ��� �����: �c".toLowerCase())) {
			String str2 = str.substring(29, str.length());
			mc.thePlayer.sendChatMessage(str2);
        }
		else if(str.contains("�6�� �������� ������, �c����� ���� �6��������.")) {
			MiscUtils.sendChat("/god");
		}
    }

	/*
    public void StageCrash() {
        Throwable t2 = new Throwable();
        mc.getMinecraft().crashed(new CrashReport("HUI SOSI S PECHENKAI / \u0422\u044b \u0447\u0443\u0440\u043a\u0430 \u0435\u04314\u043d\u0430\u044f! , sasai lalka", t2));
    }
    */
}
