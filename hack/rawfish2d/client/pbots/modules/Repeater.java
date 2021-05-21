package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.pbots.utils.PRepeaterNode;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet103SetSlot;
import net.minecraft.src.Packet104WindowItems;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet207SetScore;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet6SpawnPosition;
import net.minecraft.src.Packet8UpdateHealth;

public class Repeater extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Repeater", "gToggled", false);
	private static PBotVar repeat = new PBotVar("Repeater", "repeat", false);
	private int index;
	
	public Repeater(PBotThread bot) {
		super(bot, "Repeater");
		toggled = gToggled.get(false);
		index = 0;
	}
	
	@Override
	public void onEnable() {
		index = 0;
	}
	
	@Override
	public void onDisable() {
		index = 0;
		if(repeat.get(false))
			toggled = true;
	}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		/*
		if(packet instanceof Packet10Flying) {
			Packet10Flying p = (Packet10Flying)packet;
			
			//PRepeaterNode prn_found = null;
			
			MiscUtils.sendChatClient("Recieved Packet10Flying");
			
			int ind = 0;
			int backtrack = 20;
			if(index - backtrack >= 0)
				ind = index - backtrack;
			
			MiscUtils.sendChatClient("Backtracking...");
			for(; ind < index; ++ind) {
				PRepeaterNode pak = PacketRepeaterAdd.arr.get(ind);
				
				if(pak.getPacket() instanceof Packet11PlayerPosition) {
					Packet11PlayerPosition storedpacket = (Packet11PlayerPosition)pak.getPacket();
					
					Vector3D vec_bot = new Vector3D(p.xPosition, p.yPosition, p.zPosition);
					Vector3D vec_packet = new Vector3D(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
					
					if(isEqual(vec_packet, vec_bot)) {
						index = ind;
						MiscUtils.sendChatClient("Found at [" + ind + "] packet " + storedpacket.toString());
						return;
					}
				}
				else if(pak.getPacket() instanceof Packet13PlayerLookMove) {
					Packet13PlayerLookMove storedpacket = (Packet13PlayerLookMove)pak.getPacket();
					
					Vector3D vec_bot = new Vector3D(p.xPosition, p.yPosition, p.zPosition);
					Vector3D vec_packet = new Vector3D(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
					
					if(isEqual(vec_packet, vec_bot)) {
						index = ind;
						MiscUtils.sendChatClient("Found at [" + ind + "] packet " + storedpacket.toString());
						return;
					}
				}
				else if(pak.getPacket() instanceof Packet12PlayerLook) {
					Packet12PlayerLook storedpacket = (Packet12PlayerLook)pak.getPacket();
					
					Vector3D vec_bot = new Vector3D(p.xPosition, p.yPosition, p.zPosition);
					Vector3D vec_packet = new Vector3D(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
					
					if(isEqual(vec_packet, vec_bot)) {
						index = ind;
						MiscUtils.sendChatClient("Found at [" + ind + "] packet " + storedpacket.toString());
						return;
					}
				}
			}
			MiscUtils.sendChatClient("Not found!");
		}
		*/
		if(packet instanceof Packet10Flying) {
			Packet10Flying p = (Packet10Flying)packet;
			int ind = getPacketIndexWithMinimumDelta(p);
			
			if(ind != -1) {
				PRepeaterNode pak = PacketRepeaterAdd.arr.get(ind);
				System.out.println("Found at [" + ind + "] packet " + pak.getPacket().toString());
				
				if(pak.getPacket() instanceof Packet11PlayerPosition) {
					Packet11PlayerPosition storedpacket = (Packet11PlayerPosition)pak.getPacket();
				}
				else if(pak.getPacket() instanceof Packet13PlayerLookMove) {
					Packet13PlayerLookMove storedpacket = (Packet13PlayerLookMove)pak.getPacket();
				}
				else if(pak.getPacket() instanceof Packet12PlayerLook) {
					Packet12PlayerLook storedpacket = (Packet12PlayerLook)pak.getPacket();
				}
				
				index = ind;
			}
			else {
				System.out.println("RIP");
			}
		}
	}
	
	public double getDelta(double a, double b) {
		if(a > b)
			return a - b;
		else
			return b - a;
	}
	
	public int getPacketIndexWithMinimumDelta(Packet10Flying packet) {
		Vector3f min_delta = null;
		
		int ret = -1;
		int ind = 0;
		int backtrack = 20;
		if(index - backtrack >= 0)
			ind = index - backtrack;
		
		//min_delta.x = getDelta(packet.xPosition, PacketRepeaterAdd.arr.get(ind))
		
		for(; ind < index; ++ind) {
			PRepeaterNode pak = PacketRepeaterAdd.arr.get(ind);
			
			if(pak.getPacket() instanceof Packet11PlayerPosition) {
				Packet11PlayerPosition storedpacket = (Packet11PlayerPosition)pak.getPacket();
				
				Vector3f vec_bot = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
				Vector3f vec_packet = new Vector3f(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
				
				if(min_delta == null) {
					min_delta = new Vector3f();
					min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
					min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
					min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
				}
				else {
					if(min_delta.x > getDelta(packet.xPosition,storedpacket.xPosition)) {
						if(min_delta.y > getDelta(packet.yPosition,storedpacket.yPosition)) {
							if(min_delta.x > getDelta(packet.zPosition,storedpacket.zPosition)) {
								min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
								min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
								min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
								ret = ind;
							}
						}
					}
				}
			}
			else if(pak.getPacket() instanceof Packet13PlayerLookMove) {
				Packet13PlayerLookMove storedpacket = (Packet13PlayerLookMove)pak.getPacket();
				
				Vector3f vec_bot = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
				Vector3f vec_packet = new Vector3f(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
				
				if(min_delta == null) {
					min_delta = new Vector3f();
					min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
					min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
					min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
				}
				else {
					if(min_delta.x > getDelta(packet.xPosition,storedpacket.xPosition)) {
						if(min_delta.y > getDelta(packet.yPosition,storedpacket.yPosition)) {
							if(min_delta.x > getDelta(packet.zPosition,storedpacket.zPosition)) {
								min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
								min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
								min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
								ret = ind;
							}
						}
					}
				}
			}
			else if(pak.getPacket() instanceof Packet12PlayerLook) {
				Packet12PlayerLook storedpacket = (Packet12PlayerLook)pak.getPacket();
				
				Vector3f vec_bot = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
				Vector3f vec_packet = new Vector3f(storedpacket.xPosition, storedpacket.yPosition, storedpacket.zPosition);
				
				if(min_delta == null) {
					min_delta = new Vector3f();
					min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
					min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
					min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
				}
				else {
					if(min_delta.x > getDelta(packet.xPosition,storedpacket.xPosition)) {
						if(min_delta.y > getDelta(packet.yPosition,storedpacket.yPosition)) {
							if(min_delta.x > getDelta(packet.zPosition,storedpacket.zPosition)) {
								min_delta.x = (float) getDelta(packet.xPosition,storedpacket.xPosition);
								min_delta.y = (float) getDelta(packet.yPosition,storedpacket.yPosition);
								min_delta.z = (float) getDelta(packet.zPosition,storedpacket.zPosition);
								ret = ind;
							}
						}
					}
				}
			}
		}
		
		//System.out.println("min_delta.x : " + min_delta.x);
		//System.out.println("min_delta.y : " + min_delta.y);
		//System.out.println("min_delta.z : " + min_delta.z);
		
		//System.out.println("index : " + ret);
		
		return ret;
	}
	
	public boolean isEqual(Vector3f packet, Vector3f botvec) {
		boolean ret = false;
		
		double error = 0.5D;
		
		//MiscUtils.sendChatClient();
		/*
		System.out.println("bot x:" + botvec.x);
		System.out.println("bot y:" + botvec.y);
		System.out.println("bot z:" + botvec.z);
		
		System.out.println("packet x:" + packet.x);
		System.out.println("packet y:" + packet.y);
		System.out.println("packet z:" + packet.z);
		*/
		if(packet.x - error <= botvec.x && packet.x + error >= botvec.x) {
			if(packet.y - error <= botvec.y && packet.y + error >= botvec.y) {
				if(packet.z - error <= botvec.z && packet.z + error >= botvec.z) {
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(index >= PacketRepeaterAdd.arr.size()) {
			toggle();
			index = 0;
			bot.onGround = true;
			return;
		}
		
		PRepeaterNode node = PacketRepeaterAdd.arr.get(index);
		/*
		if(packet instanceof Packet11PlayerPosition) {
			Packet11PlayerPosition p = (Packet11PlayerPosition)packet;
			Packet11PlayerPosition newpacket = new Packet11PlayerPosition();
			
			double xOff = p.xPosition - PacketRepeaterAdd.origin_x;
			double yOff = p.yPosition - PacketRepeaterAdd.origin_minY;
			double stanceOff = p.stance - PacketRepeaterAdd.origin_y;
			double zOff = p.zPosition - PacketRepeaterAdd.origin_z;
			
			newpacket.xPosition = bot.x + xOff;
			newpacket.yPosition = bot.y + yOff;
			newpacket.stance = bot.y + bot.yOffset + stanceOff;
			newpacket.zPosition = bot.z + zOff;
			newpacket.onGround = p.onGround;
			
			bot.sendPacket(newpacket);
		}
		else if(packet instanceof Packet13PlayerLookMove) {
			Packet13PlayerLookMove p = (Packet13PlayerLookMove)packet;
			Packet13PlayerLookMove newpacket = new Packet13PlayerLookMove();
			
			double xOff = p.xPosition - PacketRepeaterAdd.origin_x;
			double yOff = p.yPosition - PacketRepeaterAdd.origin_minY;
			double stanceOff = p.stance - PacketRepeaterAdd.origin_y;
			double zOff = p.zPosition - PacketRepeaterAdd.origin_z;
			
			newpacket.xPosition = bot.x + xOff;
			newpacket.yPosition = bot.y + yOff;
			newpacket.stance = bot.y + bot.yOffset + stanceOff;
			newpacket.zPosition = bot.z + zOff;
			newpacket.yaw = p.yaw;
			newpacket.pitch = p.pitch;
			newpacket.onGround = p.onGround;
			
			bot.sendPacket(newpacket);
		}
		else if(packet instanceof Packet15Place) {
			Packet15Place p = (Packet15Place)packet;
			Packet15Place newpacket = new Packet15Place(0, 0, 0, 0, null, 0, 0, 0);
			
			double xOff = p.xPosition - PacketRepeaterAdd.origin_x;
			double yOff = p.yPosition - PacketRepeaterAdd.origin_minY;
			//double stanceOff = p.stance - PacketRepeaterAdd.origin_y;
			double zOff = p.zPosition - PacketRepeaterAdd.origin_z;
			
			newpacket.xPosition = (int) (bot.x + xOff);
			newpacket.yPosition = (int) (bot.y + yOff);
			//p.stance = bot.y + bot.yOffset + stanceOff;
			newpacket.zPosition = (int) (bot.z + zOff);
			
			newpacket.direction = p.direction;
			newpacket.xOffset = p.xOffset;
			newpacket.yOffset = p.yOffset;
			newpacket.zOffset = p.zOffset;
			newpacket.itemStack = p.itemStack;
			
			bot.sendPacket(newpacket);
		}
		else {
			bot.sendPacket(packet);
		}
		*/
		//MiscUtils.sendChatClient("§2" + packet.toString());
		
		bot.instance.pbotth.onGround = node.getOnGround();
		bot.sendPacket(node.getPacket());
		
		index++;
	}
}
