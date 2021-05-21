package hack.rawfish2d.client.cmd;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModOther.ChatHotkeys;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.utils.MiscUtils;

public class HotkeysCmd extends Command {
	public HotkeysCmd(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
	}

	@Override
	public void run(String msg, String []args) {
		if(args.length < 2) {
			error();
			return;
		}

		if(args[1].equalsIgnoreCase("help")) {
			MiscUtils.sendChatClient(color + cmd + " bind <кнопка> <название модуля> - забиндить модуль на кнопку.");
			MiscUtils.sendChatClient(color + cmd + " unbind <название модуля> - убрать бинд с модуля.");
			MiscUtils.sendChatClient(color + cmd + " unbindall - убрать бинд со всех модулей.");
			
			MiscUtils.sendChatClient(color + cmd + " cbind <кнопка> <сообщение> - забиндить сообщение чата на кнопку.");
			MiscUtils.sendChatClient(color + cmd + " cunbind <кнопка> - убрать бинд с кнопки.");
			MiscUtils.sendChatClient(color + cmd + " cunbindall - убрать бинд со всех кнопок.");
			MiscUtils.sendChatClient(color + cmd + " clist - показать список биндов.");
			MiscUtils.sendChatClient(color + "Чтобы вернуть стандартные бинды, нужно удалить файл %appdata%/.minecraft/UltimateHack/modbinds.txt и перезапустить майнкрафт");
			return;
		}

		//module binds
		if(args[1].equalsIgnoreCase("bind") && args.length == 4) {
			Module m = Client.getInstance().GetModule(args[3]);
			if(m != null) {
				int index = Keyboard.getKeyIndex(args[2].toUpperCase());
				if(index != 0) {
					m.setKeyCode(index);
					mc.thePlayer.sendChatToPlayer("§a" + args[3] + " binded to key " + args[2]);
				}
				else
					mc.thePlayer.sendChatToPlayer("§cError. " + args[2] + " is invalid key");
			}
			else
				mc.thePlayer.sendChatToPlayer("§cError. " + args[3] + " is invalid module name");
			return;
		}
		else if(args[1].equalsIgnoreCase("unbind") && args.length == 3) {
			Module m = Client.getInstance().GetModule(args[2]);
			if(m != null) {
				m.setKeyCode(0);
				mc.thePlayer.sendChatToPlayer("§a" + args[2] + " unbinded");
			}
			else
				mc.thePlayer.sendChatToPlayer("§cError. \"" + args[2] + "\" is invalid module name");
			return;
		}
		else if(args[1].equalsIgnoreCase("unbindall") && args.length == 2) {
			for(Module mod : Client.getInstance().getModules()) {
				mod.setKeyCode(0);
			}
			mc.thePlayer.sendChatToPlayer("§aAll modules unbinded!");
			return;
		}
		//module binds
		
		//chat binds
		if(args[1].equalsIgnoreCase("cbind") && args.length >= 3) {
			int index = Keyboard.getKeyIndex(args[2].toUpperCase());
			//.hotkey cbind C /warp pvp
			String message = "";
			for(int a = 3; a < args.length; ++a) {
				message += args[a] + " ";
			}
			
			if(index != 0) {
				ChatHotkeys.bind(args[2], message);
			}
			else
				mc.thePlayer.sendChatToPlayer("§cError. " + args[2] + " is invalid key");
			
			return;
		}
		else if(args[1].equalsIgnoreCase("cunbind") && args.length == 3) {
			int index = Keyboard.getKeyIndex(args[2].toUpperCase());
			//.hotkey cunbind C
			
			if(index != 0) {
				ChatHotkeys.unbind(args[2]);
			}
			else
				mc.thePlayer.sendChatToPlayer("§cError. " + args[2] + " is invalid key");
			
			return;
		}
		else if(args[1].equalsIgnoreCase("cunbindall") && args.length == 2) {
			ChatHotkeys.unbindAll();
			return;
		}
		else if(args[1].equalsIgnoreCase("clist") && args.length == 2) {
			ChatHotkeys.printAllHotkeys();
			return;
		}
		//chat binds
		mc.thePlayer.sendChatToPlayer("§cInvalid syntax!");
	}
}
