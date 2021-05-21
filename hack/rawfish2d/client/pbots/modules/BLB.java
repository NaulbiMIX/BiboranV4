package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.ModTest.BotControl;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.pbots.utils.PRepeaterNode;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet130UpdateSign;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet3Chat;

public class BLB extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("BLB", "gToggled", true);
	private static PBotVar walkYaw = new PBotVar("BLB", "walkYaw", 90.D);
	private static PBotVar walkSpeed = new PBotVar("BLB", "walkSpeed", 0.20D);
	//private static PBotVar buttonLobbyStr = new PBotVar("BLB", "buttonLobbyStr", "��� ����� �� ������ ������� �� ������".toLowerCase());
	private static PBotVar buttonLobbyStr = new PBotVar("BLB", "message", "�������".toLowerCase());
	
	private TimeHelper walk_delay = new TimeHelper();
	private TimeHelper stop_delay = new TimeHelper();
	private TimeHelper look_delay = new TimeHelper();
	private boolean inButtonLobby = true;
	private boolean placed = false;
	private boolean looked = false;
	private int index = 0;
	
	public CopyOnWriteArrayList<Packet130UpdateSign> signs = new CopyOnWriteArrayList<Packet130UpdateSign>();
	
	public BLB(PBotThread bot) {
		super(bot, "BLB");
		//ButtonLobbyBypass
		toggled = gToggled.get(false);
		//toggled = false;
		
		inButtonLobby = false;
		placed = false;
		looked = false;
		stop_delay.reset();
		look_delay.reset();
		walk_delay.reset();
	}
	
	@Override
	public void onEnable() {
		inButtonLobby = false;
		placed = false;
		looked = false;
		stop_delay.reset();
		look_delay.reset();
		walk_delay.reset();
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(packet instanceof Packet255KickDisconnect) {
			reset();
		}
		
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			
			String message = p.message.toLowerCase();
			if(message.contains(BPM.lost_connection.get(""))) {
				String reason = message.substring(new String(BPM.lost_connection.get("")).length());
				kickedInButtonLobby();
			}
		}
		
		if(!toggled) {
			return;
		}
		
		if(packet instanceof Packet130UpdateSign) {
			Packet130UpdateSign p = (Packet130UpdateSign)packet;
			signs.add(p);
		}
		
		if(packet instanceof Packet10Flying && !inButtonLobby && !bot.onSurvivalServer) {
			reset();
			inButtonLobby = true;
			bot.sendPacket(new Packet12PlayerLook(0, 90, bot.onGround));
		}
		
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			
			String message = p.message.toLowerCase();
			
			if(message.contains(buttonLobbyStr.get(""))) {
				inButtonLobby = true;
				placed = false;
				looked = false;
				stop_delay.reset();
				look_delay.reset();
				walk_delay.reset();
				index = 0;
				bot.sendPacket(new Packet12PlayerLook(0, 90, bot.onGround));
			}
			else if(message.contains("�������� ��������".toLowerCase())) {
				bot.onGround = false;
				bot.sendPacket(new Packet10Flying(true));
				MiscUtils.sleep(50L);
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.5, 55.12000000476837, 456.5, false));
				MiscUtils.sleep(50L);
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.40199999809265, 55.02200000286102, 456.5, false));
				MiscUtils.sleep(50L); //0,09800000190735
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.3039999961853, 54.92400000095367, 456.5, false));
				MiscUtils.sleep(50L); //0,098000001907354
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.205999994277946, 54.82599999904632, 456.5, false));
				MiscUtils.sleep(50L); //0,098000001907352
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.107999992370594, 54.727999997138966, 456.5, false));
				MiscUtils.sleep(50L); //0,098000001907354
				bot.sendPacket(new Packet11PlayerPosition(92.5, 53.00999999046324, 54.629999995231614, 456.5, false));
				MiscUtils.sleep(50L); //0,17444000526428
				bot.sendPacket(new Packet11PlayerPosition(92.5, 52.83555998519896, 54.455559989967334, 456.5, false));
				MiscUtils.sleep(50L);
				bot.sendPacket(new Packet13PlayerLookMove(-54.518871459593235, 53.0, 54.62000000476837, -69.51198400146404, -180.0f, 0.0f, false));
				MiscUtils.sleep(50L);
				bot.onGround = true;
				
				inButtonLobby = false;
				bot.inLobby = true;
				bot.onGround = true;
				toggle();
			}
		}
	}
	
	public static int getOffsetBasedOnY(double boty) {
		int offset = 0;
		int ypos = 0;
		for(int a = 89; a != 23; a -= 11) {
			if(a == boty) {
				ypos = a;
				break;
			}
			offset++;
		}
		int zpos = 471;
		
		if(ypos == 0) {
			offset = -1;
		}
		
		return offset;
	}
	
	public static int getYBasedOnOffset(int offset) {
		int ret = 89;
		ret = ret - (offset * 11);
		return ret;
	}
	
	public int getBlockId(int x, int y, int z) {
		//Block block = MiscUtils.getBlock(x, y, z);
		Block block = bot.chunks.getBlock(x, y, z);
		if(block == null) {
			return 0;
		}
		else {
			return block.blockID;
		}
	}
	
	public void printIfNotNull(int x, int y, int z) {
		int id = getBlockId(x, y, z); //lamp
		if(id != 0) {
			bot.sendChatClient("�a�lID: " + id + " x:" + x + " y:" + y + " z:" + z);
			int meta = bot.chunks.getBlockMetadata(x, y, z);
			bot.sendChatClient("�a�lMETA: " + meta);
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		//bot.sendChatClient("START");
		
		if(BotControl.instance.isToggled()) {
			toggle();
			return;
		}
		
		if(bot.onSurvivalServer || bot.inLobby) {
			inButtonLobby = false;
			return;
		}
		else {
			inButtonLobby = true;
		}
		
		if(!stop_delay.hasReached(2500L) && inButtonLobby && !placed && !looked) {
			walk();
			look_delay.reset();
		}
		else if(!looked && !placed && look_delay.hasReached(200L) && inButtonLobby) {
			this.lookAtButton();
			look_delay.reset();
			looked = true;
		}
		else if(looked && !placed && look_delay.hasReached(1000L) && inButtonLobby){
			woolBypass();
			
			inButtonLobby = false;
			placed = true;
			look_delay.reset();
		}
	}
	
	private void woolBypass() {
		//700, (int)(bot.y + 2), 465
		int xpos = 700;
		int ypos = (int)(bot.y + 2);
		int zpos = 0;
		
		int id = 0;
		int meta = 0;
		
		for(int z = 465; z <= 471; ++z) {
			id = getBlockId(xpos, ypos, z);
			if(id != 0) {
				meta = bot.chunks.getBlockMetadata(xpos, ypos, z);
				if(meta != 14 && id == 35) {
					zpos = z;
					break;
				}
			}
		}
		
		if(zpos == 0) {
			bot.sendChatClient("�a�lBot: " + bot.instance.name + " �c�lfailed.");
			for(int z = 465; z <= 471; ++z) {
				id = getBlockId(xpos, ypos, z); //lamp
				if(id != 0) {
					meta = bot.chunks.getBlockMetadata(xpos, ypos, z);
					//bot.sendChatClient("�a�lID: " + id + " x:" + xpos + " y:" + ypos + " z:" + z);
					//bot.sendChatClient("�a�lMETA: " + meta);
				}
			}
			bot.stopBot();
		}
		
		int xplace = xpos + 1;
		int yplace = ypos - 1;
		int zplace = zpos;
		
		bot.sendChatClient("�eBot �8[�a" + bot.instance.name + "�8] �9Wool lobby passed successfully!");
		
		//bot.sendChatClient("�a[WOOL] �ex:" + xpos + " y:" + ypos + " z:" + zpos);
		/*
		bot.sendChatClient("�a[WOOL] �ex:" + xpos + " y:" + ypos + " z:" + zpos);
		bot.sendChatClient("�9[LOOK] �ePITCH:" + bot.pitch + " �a�lYAW:" + bot.yaw);
		bot.sendChatClient("�4[PLACE] �ex:" + (xplace) + " �a�ly:" + (yplace) + " �ez:" + (zplace));
		bot.sendChatClient("�d[POS] �ex:" + bot.x + " �a�ly:" + bot.y + " �ez:" + bot.z);
		*/
		
		bot.sendPacket(new Packet15Place(xplace, yplace, zplace, 5, null, 0.125f, 0.125f, 0.125f));
		MiscUtils.sleep(4L);
		bot.sendPacket(new Packet18Animation());
		MiscUtils.sleep(4L);
		
		inButtonLobby = false;
		placed = true;
		look_delay.reset();
	}
	
	private void signBypass() {
		List<Packet130UpdateSign> near = new ArrayList<Packet130UpdateSign>();
		
		for(Packet130UpdateSign s : this.signs) {
			if(bot.y == s.yPosition - 2)
				near.add(s);
		}
		
		Packet130UpdateSign unique = null;
		
		for(Packet130UpdateSign s : near) {
			if(		s.signLines[0].contains("�a") ||
					s.signLines[1].contains("�a") ||
					s.signLines[2].contains("�a") ||
					s.signLines[3].contains("�a")) {
				unique = s;
				break;
			}
		}
		
		if(unique == null) {
			return;
		}
		
		int xpos = (int) unique.xPosition;
		int ypos = (int) unique.yPosition - 1;
		int zpos = (int) unique.zPosition;
		
		Vector2f vec2 = MiscUtils.getBlockAngles(xpos + (0.125), ypos + (0.5), zpos + (0.5), bot.x, bot.y, bot.z);
		bot.pitch = vec2.y;
		bot.yaw = vec2.x;
		
		bot.sendChatClient("�5[SIGN] �ex:" + xpos + " �ey:" + ypos + " �a�lz:" + zpos);
		bot.sendChatClient("�9[LOOK] �ePITCH:" + bot.pitch + " �a�lYAW:" + bot.yaw);
		bot.sendChatClient("�4[PLACE] �ex:" + (xpos) + " �ey:" + (ypos) + " �a�lz:" + (zpos));
		bot.sendChatClient("�D[POS] �ex:" + bot.x + " �a�ly:" + bot.y + " �ez:" + bot.z);
		bot.sendChatClient("line[0]:" + unique.signLines[0]);
		bot.sendChatClient("line[1]:" + unique.signLines[1]);
		bot.sendChatClient("line[2]:" + unique.signLines[2]);
		bot.sendChatClient("line[3]:" + unique.signLines[3]);
		bot.sendChatClient("���� �� �� ������ ������ ��� ���������, �������� �������:");
		bot.sendChatClient(".pbot varset this showChat false");
		
		bot.sendPacket(new Packet12PlayerLook(vec2.y, vec2.x, bot.onGround));
		MiscUtils.sleep(4L);
		
		bot.sendPacket(new Packet15Place(xpos, ypos, zpos, 5, null, 0.125f, 0.125f, 0.125f));
		MiscUtils.sleep(4L);
		bot.sendPacket(new Packet18Animation());
		MiscUtils.sleep(4L);
		
		inButtonLobby = false;
		placed = true;
		look_delay.reset();
	}
	
	private void paintingBypass() {
		List<LiteEntity> arr = new ArrayList<LiteEntity>();
		
		for(LiteEntity ent : bot.getEntityes()) {
			if(ent.getType() == 3 && bot.y == ent.getPos().y - 2)
				arr.add(ent);
		}
		
		LiteEntity unique = null;
		
		for(LiteEntity ent : arr) {
			if(ent.getName().equalsIgnoreCase("plant") && bot.y == ent.getPos().y - 2) {
				unique = ent;
				break;
			}
		}
		
		if(unique == null) {
			return;
		}
		
		Vector3f pos = unique.getPos();
		int xpos = (int) pos.x + 1;
		int ypos = (int) pos.y - 1;
		int zpos = (int) pos.z;
		
		bot.sendChatClient("�6[PAINTING] �ex:" + xpos + " y:" + ypos + " z:" + zpos);
		bot.sendChatClient("�9[LOOK] �ePITCH:" + bot.pitch + " �a�lYAW:" + bot.yaw);
		bot.sendChatClient("�4[PLACE] �ex:" + (xpos) + " �a�ly:" + (ypos) + " �ez:" + (zpos));
		bot.sendChatClient("�d[POS] �ex:" + bot.x + " �a�ly:" + bot.y + " �ez:" + bot.z);
		
		bot.sendPacket(new Packet15Place(xpos, ypos, zpos, 5, null, 0.125f, 0.125f, 0.125f));
		MiscUtils.sleep(4L);
		bot.sendPacket(new Packet18Animation());
		MiscUtils.sleep(4L);
		
		inButtonLobby = false;
		placed = true;
		look_delay.reset();
	}
	
	private void frameBypassGood() {
		
	}
	
	private void frameBypassBad() {
		int zpos = 471;
		int offset = getOffsetBasedOnY(bot.y);
		
		if(offset == -1)
			return;
		
		zpos -= offset;
		int ypos = getYBasedOnOffset(offset) + 1;
		int xpos = 701;
		
		bot.sendChatClient("�9[LOOK] �ePITCH:" + bot.pitch + " �a�lYAW:" + bot.yaw);
		bot.sendChatClient("�4[PLACE] �ex:" + (xpos) + " �a�ly:" + (ypos) + " �ez:" + (zpos));
		bot.sendChatClient("�d[POS] �ex:" + bot.x + " �a�ly:" + bot.y + " �ez:" + bot.z);
		
		bot.sendPacket(new Packet15Place(xpos, ypos, zpos, 5, null, 0.125f, 0.125f, 0.125f));
		MiscUtils.sleep(5L);
		bot.sendPacket(new Packet18Animation());
		MiscUtils.sleep(5L);
		
		bot.sendPacket(new Packet15Place(xpos, ypos, zpos, 5, null, 0.125f, 0.125f, 0.125f));
		MiscUtils.sleep(5L);
		bot.sendPacket(new Packet18Animation());
		MiscUtils.sleep(5L);
		
		inButtonLobby = false;
		placed = true;
		look_delay.reset();
	}
	
	private void lookAtButton() {
		/*
		Vector2D vec2 = new Vector2D(0F, 0F);
		int offset = getOffsetBasedOnY(bot.y);
		if(offset == 0) { //89
			vec2.x = 22.29F; //yaw
			vec2.y = 2.305193F; //pitch
		}
		else if(offset == 1) { //78
			vec2.x = 30.343254F; //yaw
			vec2.y = 3.1187892F; //pitch
		}
		else if(offset == 2) { //67
			vec2.x = 50.909172F; //yaw
			vec2.y = 4.067988F; //pitch
		}
		else if(offset == 3) { //56
			vec2.x = 90.0071F; //yaw
			vec2.y = 1.0395961F; //pitch
		}
		else if(offset == 4) { //45
			vec2.x = 129.37607F; //yaw
			vec2.y = 4.203587F; //pitch
		}
		else if(offset == 5) { //34
			vec2.x = 149.71619F; //yaw
			vec2.y = 2.5311918F; //pitch
		}
		else if(offset == 6) { //23
			vec2.x = 157.07F; //yaw
			vec2.y = 1.853194F; //pitch
		}
		
		bot.pitch = vec2.y;
		bot.yaw = vec2.x;
		bot.sendPacket(new Packet12PlayerLook(vec2.y, vec2.x, bot.onGround));
		look_delay.reset();
		looked = true;
		*/
		/*
		int zpos = 471;
		int offset = getOffsetBasedOnY(bot.y);
		if(offset == -1)
			return;
		zpos -= offset;
		int ypos = getYBasedOnOffset(offset);
		int xpos = 701;
		
		Vector2D vec2 = MiscUtils.getBlockAngles(xpos + (0.125), ypos + (0.5), zpos + (0.5), bot.x, bot.y, bot.z);
		
		bot.pitch = vec2.y;
		bot.yaw = vec2.x;
		bot.sendPacket(new Packet12PlayerLook(vec2.y, vec2.x, bot.onGround));
		look_delay.reset();
		looked = true;
		*/
	}
	
	private void kickedInButtonLobby() {
		reset();
		inButtonLobby = true;
		
		if(!toggled) {
			toggle();
		}
	}
	
	private void reset() {
		inButtonLobby = false;
		bot.inLobby = false;
		bot.onSurvivalServer = false;
		placed = false;
		looked = false;
		stop_delay.reset();
		look_delay.reset();
		walk_delay.reset();
		signs.clear();
	}
	
	private void walk() {
		if(!bot.dead && inButtonLobby) {
			float offset = (float) walkSpeed.get(0.D);
			
			bot.yaw = (float) walkYaw.get(0D);
			if(bot.yaw == 0)
				bot.z += offset;
			else if(bot.yaw == -180)
				bot.z -= offset;
			
			if(bot.yaw == 90)
				bot.x -= offset;
			else if(bot.yaw == 270)
				bot.x += offset;
			
			bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, bot.onGround));
			MiscUtils.sleep(4L);
			bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.onGround));
			walk_delay.reset();
		}
	}
}
