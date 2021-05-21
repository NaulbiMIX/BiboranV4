package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet7UseEntity;

public class AutoTpaccept extends Module {
	private BoolValue only_friends;
	
	public AutoTpaccept() {
		super("AutoTpaccept", 0, ModuleType.MISC);
		setDescription("������������� ��������� ������ �� ������������");
		
		only_friends = new BoolValue(false);
		elements.add(new CheckBox(this, "Accept Only Friends", only_friends, 0, 0));
	}
	
	@Override
	public void onChatMessage(String str) {
		//��������� ������ �� ������
		if(only_friends.getValue()) {
			if (str.contains("�6 �������� ������������ � ����.")) {
				int pos = str.lastIndexOf("�6");
				String nickname = str.substring(2, pos);
				
				//��������, ���� �� ��� �������� ������
				if( PlayerUtils.isFriend(nickname))
					mc.thePlayer.sendChatMessage("/tpaccept");
			}
		}
		//��������� �� ����
		else {
			if (str.contains("�6 �������� ������������ � ����.")) {
				mc.thePlayer.sendChatMessage("/tpaccept");
			}
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		
	}
}
