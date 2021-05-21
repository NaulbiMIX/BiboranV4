package hack.rawfish2d.client.cmd.base;

import java.util.List;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;

public class Command {
	protected Minecraft mc = Minecraft.getMinecraft();
	protected String cmd;
	protected String desc;
	protected String syntax;
	protected String color = "§a";
	protected String errColor = "§c";

	public Command(String color, String cmd, String desc, String syntax) {
		this.color = color;
		this.cmd = cmd;
		this.desc = desc;
		this.syntax = syntax;
	}

	protected void run(String chat, String []args) {}

	public String getColor() { return this.color; }
	
	public String getCmd() { return this.cmd; }

	public String getDesc() { return this.desc; }

	public String getSyntax() { return this.syntax; }

	protected void error() {
		MiscUtils.sendChatClient(errColor + "Error, invalid syntax");
		MiscUtils.sendChatClient(color + "Usage: " + syntax);
	}
}
