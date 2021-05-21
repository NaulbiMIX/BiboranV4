package hack.rawfish2d.client.cmd;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.ModTest.AutoPath2;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.cmd.base.CommandUtils;
import hack.rawfish2d.client.utils.MiscUtils;

public class AutoPathCmd extends Command {
	public AutoPathCmd(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
	}

	@Override
	public void run(String msg, String []args) {
		if(args.length < 2) {
			error();
			return;
		}

		if(args[1].equalsIgnoreCase("help") && args.length == 2) {
			MiscUtils.sendChatClient(color + "AutoPath2 coords : §e" + AutoPath2.x + " " + AutoPath2.y + " " + AutoPath2.z + " ");
			MiscUtils.sendChatClient(color + ".autopath set [x coord] [y coord] [z coord] - установить финишную точку для поиска пути");
			MiscUtils.sendChatClient(color + ".autopath set - установить финишную точку для поиска пути по текущим координатам");
			return;
		}
		
		if(args[1].equalsIgnoreCase("set") && args.length == 2) {
			AutoPath2.x = (int) Math.floor(Client.mc.thePlayer.posX);
			AutoPath2.y = (int) Math.floor(Client.mc.thePlayer.posY - 2);
			AutoPath2.z = (int) Math.floor(Client.mc.thePlayer.posZ);
			MiscUtils.sendChatClient(color + "Finish point set to " + AutoPath2.x + " " + AutoPath2.y + " " + AutoPath2.z + " ");
		}

		//.AutoPath2 set 0 64 0
		if(args[1].equalsIgnoreCase("set") && args.length == 5) {
			try {
				AutoPath2.x = Integer.parseInt(args[2]);
				AutoPath2.y = Integer.parseInt(args[3]);
				AutoPath2.z = Integer.parseInt(args[4]);
			}
			catch(Exception ex) {
				error();
				return;
			}
			MiscUtils.sendChatClient(color + "Finish point set to " + AutoPath2.x + " " + AutoPath2.y + " " + AutoPath2.z + " ");
		}
	}
}
