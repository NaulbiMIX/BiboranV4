package hack.rawfish2d.client.cmd;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.cmd.base.Command;
import hack.rawfish2d.client.cmd.base.CommandUtils;
import hack.rawfish2d.client.utils.MiscUtils;

public class AutoWarpCmd extends Command {
	public AutoWarpCmd(String color, String cmd, String desc, String syntax) {
		super(color, cmd, desc, syntax);
	}

	@Override
	public void run(String msg, String []args) {
		if(args.length < 2) {
			error();
			return;
		}

		if(args[1].equalsIgnoreCase("help")) {
			MiscUtils.sendChatClient(color + "AutoWarp warp : §e" + AutoWarp.warp);
			return;
		}

		if(args.length < 3) {
			error();
			return;
		}

		if(args[1].equalsIgnoreCase("warp")) {
			AutoWarp.warp = args[2];
			MiscUtils.sendChatClient(color + "Warp set to " + args[2]);
		}
	}
}
