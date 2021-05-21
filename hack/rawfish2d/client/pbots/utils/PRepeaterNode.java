package hack.rawfish2d.client.pbots.utils;

import net.minecraft.src.Packet;

public class PRepeaterNode {
	private Packet packet;
	private long delay_before_sent;
	private boolean onGround;
	
	public PRepeaterNode(Packet packet, long delay, boolean onGround) {
		this.packet = packet;
		this.delay_before_sent = delay;
		this.onGround = onGround;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public long getDelay() {
		return delay_before_sent;
	}
	
	public boolean getOnGround() {
		return onGround;
	}
}
