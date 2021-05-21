package hack.rawfish2d.client.ModMisc;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.WindowItemsScroll;
import net.minecraft.src.Packet;

public class PacketFilter extends Module{
	
	public static Module instance = null;

	public PacketFilter() {
		super("RedstoneLagFix", 0, ModuleType.MISC);
		setDescription("От лаго машин будет лагать намного меньше");
		instance = this;
	}

	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean DontProcessPacket(Packet packet) {
		if(		packet.getPacketId() == 52
				|| packet.getPacketId() == 53
				//|| packet.getPacketId() == 103 //setslot
				//|| packet.getPacketId() == 100 //OpenWindow
				)
			return true;
		else
			return false;
	}
}
