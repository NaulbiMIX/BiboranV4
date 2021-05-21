package hack.rawfish2d.client.cmd.base;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.cmd.AutoPathCmd;
import hack.rawfish2d.client.cmd.AutoWarpCmd;
import hack.rawfish2d.client.cmd.HotkeysCmd;
import hack.rawfish2d.client.cmd.ItemCmd;
import hack.rawfish2d.client.cmd.JessicaCrash;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.utils.MiscUtils;

public class CommandUtils {
	public List<Command> commands = new ArrayList<Command>();
	private String prefix = ".";

	public CommandUtils() {
		addCommand(new PBotCmd("§f", "pbot", "Приватные боты", ".pbot help"));
		addCommand(new AutoWarpCmd("§f", "Настройка АвтоВарпа", "", ".autowarp help"));
		addCommand(new HotkeysCmd("§f", "hotkey", "Бинды модулей и чата", ".hotkey help"));
		addCommand(new ItemCmd("§f", "item", "Команды связанные с предметами", ".item help"));
		addCommand(new JessicaCrash("§f", "jc", "JessicaClient book meta crash", ".jc"));
		addCommand(new AutoPathCmd("§f", "autopath", "", ".autopath help"));
	}

	public void addCommand(Command cmd) {
		commands.add(cmd);
	}

	public void removeCommand(Command cmd) {
		commands.remove(cmd);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix2) {
		prefix = prefix2;
	}

	public void onCommand(String msg) {
		//prefix = "."
		if(msg.startsWith(prefix)) {
			
			//remove prefix from message
			msg = msg.substring(prefix.length());
			String []split = msg.split(" ");

			for(int a = 0; a < commands.size(); ++a) {
				Command cmd = commands.get(a);

				if(cmd.getCmd().equalsIgnoreCase(split[0])) {
					try {
						//функция обработки команды
						cmd.run(split[0], split);
					}
					catch(Exception ex) {
						MiscUtils.sendChatClient("§cException message: " + ex.getMessage());
						ex.printStackTrace();
					}
				}
			}
		}
	}
}
