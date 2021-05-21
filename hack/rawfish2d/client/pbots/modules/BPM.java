package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.NoRotate;
import hack.rawfish2d.client.ModTest.BotControl;
import hack.rawfish2d.client.ModUnused.BanMuteKick;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.PBotWorld;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet103SetSlot;
import net.minecraft.src.Packet104WindowItems;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet207SetScore;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet25EntityPainting;
import net.minecraft.src.Packet29DestroyEntity;
import net.minecraft.src.Packet30Entity;
import net.minecraft.src.Packet34EntityTeleport;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet41EntityEffect;
import net.minecraft.src.Packet42RemoveEntityEffect;
import net.minecraft.src.Packet51MapChunk;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.Packet56MapChunks;
import net.minecraft.src.Packet6SpawnPosition;
import net.minecraft.src.Packet8UpdateHealth;
import net.minecraft.src.Packet9Respawn;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.Scoreboard;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldProviderSurface;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

public class BPM extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("BPM", "gToggled", true);
	private static PBotVar str_in_survival = new PBotVar("BPM", "survival", "Ñåññèÿ àêòèâíà".toLowerCase());
	private static PBotVar str_in_lobby1 = new PBotVar("BPM", "lobby1", "Âûáåðèòå èãðîâîé ðåæèì âîéäÿ â ïîðòàë!".toLowerCase());
	private static PBotVar str_in_lobby2 = new PBotVar("BPM", "lobby2", "Äîáðî ïîæàëîâàòü".toLowerCase());
	public static PBotVar lost_connection = new PBotVar("BPM", "lost_connection", "Ñîåäèíåíèå ïîòåðÿíî §b>> ".toLowerCase());
	
	private static PBotVar handleEntity = new PBotVar("BPM", "handleEntity", true);
	private static PBotVar handleChunks = new PBotVar("BPM", "handleChunks", true);
	
	private static PBotVar positionSendDelay = new PBotVar("BPM", "positionSendDelay", 1000);
	private static PBotVar flySendDelay = new PBotVar("BPM", "flySendDelay", 50);
	
	private TimeHelper packet10fly = new TimeHelper();
	private TimeHelper packet0keepalive = new TimeHelper();
	private TimeHelper packet11playerposition = new TimeHelper();
	
	public static PBot lastBot;
	
	public BPM(PBotThread bot) {
		//BasicPacketManager
		super(bot, "BPM");
		toggled = gToggled.get(false);
		
		packet10fly.reset();
		packet0keepalive.reset();
		packet11playerposition.reset();
	}
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		if(BotControl.instance != null) {
			if(BotControl.instance.isToggled()) {
				PBot bot = ((BotControl)BotControl.instance).bot;
				//if(bot != null) {
				if(bot == this.bot.instance) {
					return;
				}
			}
		}
		/*
		if(	packet.getPacketId() != 54 &&
			packet.getPacketId() != 56 &&
			packet.getPacketId() != 51 &&
			packet.getPacketId() != 130) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§c: " + packet.toString());
		}
		*/
		/*
		if(bot.onSurvivalServer) {
			bot.send("/unban mr_nurbek");
			MiscUtils.sleep(1000L);
			bot.send("/tempban BLAFO15 70s doki doki top slish ebat'");
			MiscUtils.sleep(20L);
			bot.delBot();
		}
		*/
		/*
		if(packet instanceof Packet201PlayerInfo) {
			Packet201PlayerInfo p = (Packet201PlayerInfo) packet;
			
			if(p.playerName.equalsIgnoreCase(bot.instance.name))
				return;
			
			// ýëèòó ìîæíî áàíèòü íàâñåãäà
			
			//  //replace 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 20
			//  /undo
			
			
			List<String> enemy = new ArrayList<String>();
			enemy.clear();
			enemy.add("_Filippok_");
			enemy.add("ProSkillValentin");
			enemy.add("Deway_");
			//enemy.add("Potroshitel228");
			//enemy.add("artemo");
			//enemy.add("Vlad222888222");
			//enemy.add("Hest_bd");
			//enemy.add("darrirrov");
			//enemy.add("vopros999");
			//enemy.add("marten");
			//enemy.add("Snake");
			//enemy.add("Logicall_");
			
			if(p.isConnected && bot.onSurvivalServer) {
				//if(autoBan.getValue()) {
					for(String name : enemy) {
						if(name.equalsIgnoreCase(p.playerName)) {
							//MiscUtils.sendChat("/tempban " + name + " 7h ïîêà ïóñÿ íå àáèæàéñÿ))");
							String cmd = "/tempban " + name + " 60s ïîêà ïóñÿ <3";
							bot.send(cmd);
							bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§a:" + cmd);
							MiscUtils.sleep(30L);
							bot.delBot();
							//toggle();
							//bot
							//MiscUtils.sleep(200L);
							//MiscUtils.selfKick();
							return;
						}
					}
				//}
			}
			
		}
		*/
		
		if(packet instanceof Packet3Chat) {
			handlePacket3Chat(bot, (Packet3Chat)packet);
		}
		else if(packet instanceof Packet0KeepAlive) {
			bot.sendPacket(new Packet0KeepAlive());
		}
		else if(packet instanceof Packet10Flying) {
			handlePacket10Flying(bot, (Packet10Flying)packet);
		}
		else if(packet instanceof Packet1Login && bot.onSurvivalServer == false) {
			Packet1Login p = (Packet1Login)packet;
			bot.entityId = p.clientEntityId;
			GameSettings gs = Client.getInstance().mc.gameSettings;
			/*
			renderDistance
			1 - normal
			3 - tiny
			
			*/
			bot.sendPacket(new Packet204ClientInfo(gs.language, 3, gs.chatVisibility, gs.chatColours, gs.difficulty, gs.showCape));
		}
		else if(packet instanceof Packet103SetSlot) {
			handlePacket103SetSlot((Packet103SetSlot)packet);
		}
		else if(packet instanceof Packet104WindowItems) {
			handlePacket104WindowItems((Packet104WindowItems)packet);
		}
		else if(packet instanceof Packet100OpenWindow) {
			handlePacket100OpenWindow((Packet100OpenWindow)packet);
		}
		else if(packet instanceof Packet8UpdateHealth) {
			handlePacket8UpdateHealth(bot, (Packet8UpdateHealth)packet);
		}
		else if(packet instanceof Packet207SetScore && bot.onSurvivalServer) {
			Packet207SetScore p = (Packet207SetScore)packet;
			if(p.itemName.contains("Äåíåã")) {
				System.out.println("Bot " + bot.instance.name + " money: " + p.value + " delta: " + (p.value - bot.money));
				bot.money = p.value;
			}
		}
		else if(packet instanceof Packet255KickDisconnect) {
			Packet255KickDisconnect p = (Packet255KickDisconnect)packet;
			//bot.sendChatClient("§c§lBot " + bot.instance.name + " kicked: " + p.reason);
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§ckicked: " + p.reason);
			bot.disconnected = true;
		}
		else if(packet instanceof Packet6SpawnPosition) {
			Packet6SpawnPosition p = (Packet6SpawnPosition)packet;
			bot.ready = true;
			lastBot = bot.instance;
			
			if(BotControl.autoEnable.getValue()) {
				if(!BotControl.instance.isToggled()) {
					BotControl.instance.toggle();
				}
			}
		}
		else if(packet instanceof Packet9Respawn) {
			Packet9Respawn p = (Packet9Respawn)packet;
		}
		else if(packet instanceof Packet16BlockItemSwitch) {
			Packet16BlockItemSwitch p = (Packet16BlockItemSwitch)packet;
			if (p.id >= 0 && p.id < 9)
			{
				bot.curSlot = p.id;
			}
		}
		
		if(handleEntity.get(false)) {
		
			if(packet instanceof Packet20NamedEntitySpawn) {
				handlePacket20NamedEntitySpawn(bot, (Packet20NamedEntitySpawn)packet);
			}
			else if(packet instanceof Packet23VehicleSpawn) {
				handlePacket23VehicleSpawn(bot, (Packet23VehicleSpawn)packet);
			}
			else if(packet instanceof Packet25EntityPainting) {
				handlePacket25EntityPainting(bot, (Packet25EntityPainting)packet);
			}
			else if(packet instanceof Packet41EntityEffect) {
				Packet41EntityEffect p = (Packet41EntityEffect)packet;
				
				if(p.entityId == bot.entityId)
				{
					PotionEffect var3 = new PotionEffect(p.effectId, p.duration, p.effectAmplifier);
					var3.setPotionDurationMax(p.isDurationMax());
					bot.addPotionEffect(var3);
				}
			}
			else if(packet instanceof Packet42RemoveEntityEffect) {
				Packet42RemoveEntityEffect p = (Packet42RemoveEntityEffect)packet;
				
				if(p.entityId == bot.entityId)
				{
					bot.removePotionEffect(p.effectId);
				}
			}
			else if(packet instanceof Packet29DestroyEntity) {
				Packet29DestroyEntity p = (Packet29DestroyEntity)packet;
				for (int var2 = 0; var2 < p.entityId.length; ++var2) {
					bot.chunks.world.removeEntityFromWorld(p.entityId[var2]);
				}
			}
			else if(packet instanceof Packet34EntityTeleport) {
				Packet34EntityTeleport p = (Packet34EntityTeleport)packet;
				LiteEntity var2 = null;
				var2 = bot.getEntityByID(p.entityId);
				
				if (var2 != null)
				{
					double x = p.xPosition / 32.D;
					double y = p.yPosition / 32.D;
					double z = p.zPosition / 32.D;
					float yaw = (float)(p.yaw * 360) / 256.0F;
					float pitch = (float)(p.pitch * 360) / 256.0F;
					
					var2.setPos((float)x, (float)y, (float)z);
					var2.setAngles(yaw, pitch);
				}
			}
			else if(packet instanceof Packet30Entity) {
				Packet30Entity p = (Packet30Entity)packet;
				LiteEntity var2 = bot.getEntityByID(p.entityId);
	
				if (var2 != null)
				{
					double offsetx = p.xPosition / 32.D;
					double offsety = p.yPosition / 32.D;
					double offsetz = p.zPosition / 32.D;
					
					float x0 = (float) (var2.X() + offsetx);
					float y0 = (float) (var2.Y() + offsety);
					float z0 = (float) (var2.Z() + offsetz);
					
					var2.setPos((float)(x0), (float)(y0), (float)(z0));
				}
			}
		}
		
		if(handleChunks.get(false)) {
			if(packet instanceof Packet51MapChunk) {
				handlePacket51MapChunk((Packet51MapChunk)packet);
			}
			else if(packet instanceof Packet56MapChunks) {
				handlePacket56MapChunks((Packet56MapChunks)packet);
			}
			else if(packet instanceof Packet53BlockChange) {
				Packet53BlockChange p = (Packet53BlockChange)packet;
				bot.chunks.world.setBlockAndMetadataAndInvalidate(p.xPosition, p.yPosition, p.zPosition, p.type, p.metadata);
			}
		}
	}
	
	private void handlePacket103SetSlot(Packet103SetSlot packet) {
		//bot.sendChatClient("Packet103SetSlot");
		
		if(packet.itemSlot < 0) {
			return;
		}
		
		//bot.sendChatClient("Packet103SetSlot: " + packet.itemSlot + " id:" + packet.windowId + " bot.win:" + bot.windowId);
		
		if(bot.windowInventory == null) {
			bot.windowInventory = new ItemStack[54];
		}
		
		boolean var3 = false;
		if(packet.windowId == -1 || packet.windowId == bot.windowId) {
			bot.inventory[packet.itemSlot] = packet.myItemStack;
			return;
		}
		else {
			if (packet.windowId == 0 && packet.itemSlot >= 36 && packet.itemSlot < 45) {
				ItemStack var5 = bot.windowInventory[packet.itemSlot];
	
				if (packet.myItemStack != null && (var5 == null || var5.stackSize < packet.myItemStack.stackSize))
				{
					packet.myItemStack.animationsToGo = 5;
				}
				bot.windowInventory[packet.itemSlot] = packet.myItemStack;
			}
			else if (packet.windowId == bot.ContainerWindowId && (packet.windowId != 0 || !var3))
			{
				bot.windowInventory[packet.itemSlot] = packet.myItemStack;
			}
		}
	}

	private void handlePacket104WindowItems(Packet104WindowItems packet) {
		if (packet.windowId == 0)
		{
			bot.inventory = new ItemStack[packet.itemStack.length];
			
			for(int a = 0; a < packet.itemStack.length; ++a) {
				bot.inventory[a] = packet.itemStack[a];
			}
		}
		else if (packet.windowId == bot.ContainerWindowId)
		{
			bot.windowInventory = new ItemStack[packet.itemStack.length];
			
			for(int a = 0; a < packet.itemStack.length; ++a) {
				bot.windowInventory[a] = packet.itemStack[a];
			}
		}
	}

	private void handlePacket100OpenWindow(Packet100OpenWindow packet) {
		bot.ContainerWindowId = packet.windowId;
	}

	public void handlePacket51MapChunk(Packet51MapChunk par1Packet51MapChunk)
	{
		if (par1Packet51MapChunk.includeInitialize)
		{
			if (par1Packet51MapChunk.yChMin == 0)
			{
				bot.chunks.world.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, false);
				return;
			}
	
			bot.chunks.world.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, true);
		}
	
		bot.chunks.world.invalidateBlockReceiveRegion(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);
		Chunk var2 = bot.chunks.world.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);
	
		if (par1Packet51MapChunk.includeInitialize && var2 == null)
		{
			bot.chunks.world.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, true);
			var2 = bot.chunks.world.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);
		}
	
		if (var2 != null)
		{
			var2.fillChunk(par1Packet51MapChunk.getCompressedChunkData(), par1Packet51MapChunk.yChMin, par1Packet51MapChunk.yChMax, par1Packet51MapChunk.includeInitialize);
			bot.chunks.world.markBlockRangeForRenderUpdate(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);
	
			if (!par1Packet51MapChunk.includeInitialize || !(bot.chunks.world.provider instanceof WorldProviderSurface))
			{
				var2.resetRelightChecks();
			}
		}
	}
	
	public void handlePacket56MapChunks(Packet56MapChunks par1Packet56MapChunks)
	{
		for (int var2 = 0; var2 < par1Packet56MapChunks.getNumberOfChunkInPacket(); ++var2)
		{
			int var3 = par1Packet56MapChunks.getChunkPosX(var2);
			int var4 = par1Packet56MapChunks.getChunkPosZ(var2);
			bot.chunks.world.doPreChunk(var3, var4, true);
			bot.chunks.world.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
			Chunk var5 = bot.chunks.world.getChunkFromChunkCoords(var3, var4);

			if (var5 == null)
			{
				bot.chunks.world.doPreChunk(var3, var4, true);
				var5 = bot.chunks.world.getChunkFromChunkCoords(var3, var4);
			}

			if (var5 != null)
			{
				var5.fillChunk(par1Packet56MapChunks.getChunkCompressedData(var2), par1Packet56MapChunks.field_73590_a[var2], par1Packet56MapChunks.field_73588_b[var2], true);
				bot.chunks.world.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);

				if (!(bot.chunks.world.provider instanceof WorldProviderSurface))
				{
					var5.resetRelightChecks();
				}
			}
		}
	}
	
	public void handlePacket25EntityPainting(PBotThread bot, Packet25EntityPainting packet) {
		LiteEntity ent = bot.getEntityByID(packet.entityId);
		
		//if(ent == null) {
			bot.addEntity(new LiteEntity(packet));
		//}
	}

	public void handlePacket23VehicleSpawn(PBotThread bot, Packet23VehicleSpawn packet) {
		LiteEntity ent = bot.getEntityByID(packet.entityId);
		
		//if(ent == null) {
			bot.addEntity(new LiteEntity(packet));
		//}
	}
	
	public void handlePacket20NamedEntitySpawn(PBotThread pbotth, Packet20NamedEntitySpawn packet) {
		LiteEntity ent = bot.getEntityByID(packet.entityId);
		
		//if(ent == null) {
			LiteEntity lent = new LiteEntity(packet);
			
			lent.setPos((float)(lent.X() / 32.D), (float)(lent.Y() / 32.D), (float)(lent.Z() / 32.D));
			
			bot.addEntity(lent);
		//}
	}

	public static int GetCompassSlot(PBotThread bot) {
		if(bot.inventory == null) {
			return -1;
		}
		
		for(int a = 0; a < bot.inventory.length; ++a) {
			ItemStack item = bot.inventory[a];
			
			if(item != null) {
				String itemname = item.getItemName().toLowerCase();
				String searchname = "item.compass".toLowerCase();
				if(itemname.contains(searchname)) {
					int slot = InvSlot2HotbarSlot(bot.inventory.length, a);
					if(slot < 0 || slot > 8)
						slot = 0;						
					return slot;
				}
			}
		}
		return -1;
	}
	
	public static int InvSlot2HotbarSlot(int invsize, int slot) {
		if(invsize == 45) {
			if(slot < 36 || slot > 44) {
				return -1;
			}
			else
				return (slot - 36);
		}
		else if(invsize == 63) {
			if(slot < 54 || slot > 62) {
				return -1;
			}
			else
				return (slot - 54);
		}
		return -1;
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(BotControl.instance != null) {
			if(BotControl.instance.isToggled()) {
				PBot bot = ((BotControl)BotControl.instance).bot;
				//if(bot != null) {
				//11.02.10 fix LOL
				if(bot == this.bot.instance) {
					return;
				}
			}
		}
		
		for(ModuleBase mod : bot.modules) {
			if(mod.name.equalsIgnoreCase("Repeater")) {
				if(mod.toggled) {
					return;
				}
			}
		}
		
		if(bot.isMoving())
			return;
		
		if(packet10fly.hasReached(this.flySendDelay.get(0))) { //50
			
			//bot.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
			//bot.changeSlot(8);
			//bot.sendPacket(new Packet14BlockDig(5, 0, 0, 0, 65281));
			//MiscUtils.sleep(50);
			//bot.sendPacket(new Packet101CloseWindow());
			//bot.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
			
			packet10fly.reset();
			if(!bot.dead) {
				bot.sendPacket(new Packet10Flying(bot.onGround));
				//bot.sendChatClient("send fly: " + bot.onGround);
				//MiscUtils.sleep(10);
				//bot.sendPacket(new Packet18Animation(bot.entityId, 1));
				//bot.sendPacket(new Packet19EntityAction(bot.entityId, 5)); //unsprint
			}
		}
		
		if(packet11playerposition.hasReached(positionSendDelay.get(0))) {
			//bot.sendPacket(new Packet14BlockDig(5, 0, 0, 0, 65281)); //sword unblock
			
			//bot.sendPacket(new Packet14BlockDig(5, 0, 0, 0, 255)); //bow shot
			//bot.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
			
			packet11playerposition.reset();
			//bot.optimizeNearEntityes();
			/*
			for(int a = 0; a < 9; ++a) {
				bot.sendPacket(new Packet16BlockItemSwitch(a));
				MiscUtils.sleep(20);
				bot.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0.0f, 0.0f, 0.0f));
				MiscUtils.sleep(20);
				bot.sendPacket(new Packet14BlockDig(3, 0, 0, 0, 0));
				MiscUtils.sleep(20);
			}
			*/
			if(!bot.dead) {
				bot.sendPacket(new Packet19EntityAction(bot.entityId, 4)); //sprint
				bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.onGround));
				//bot.sendChatClient("send pos: " + bot.onGround);
			}
		}
	}
	
	public void handlePacket3Chat(PBotThread bot, Packet3Chat packet) {
		String message = packet.message.toLowerCase();
		
		if((message.contains(str_in_lobby1.get("")) || message.contains(str_in_lobby2.get(""))) && !bot.onSurvivalServer) {
			bot.inLobby = true;
			bot.onSurvivalServer = false;
		}
		if(message.contains(str_in_survival.get("")) || message.contains("íà ñ÷åòó âàøåãî áàíêà".toLowerCase())) {
			bot.inLobby = false;
			bot.onSurvivalServer = true;
			bot.loggined = true;
			bot.respawn();
		}
		if(message.contains(lost_connection.get(""))) {
			String reason = message.substring(new String(lost_connection.get("")).length());
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§ckicked to lobby for: " + reason);
		}
		
		if(message.contains("áàëàíñ:")) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§a:" + message);
		}
		
		if(message.contains("çàøåë íà ñåðâåð") && message.contains(bot.instance.name.toLowerCase())) {
			String donate = "";
			int pos1 = message.indexOf("§8[");
			int pos2 = message.indexOf("§8]");
			donate = message.substring(pos1, pos2 + 3);
			
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§a:" + donate);
		}
		
		if(!message.contains("Âû êóïèëè/ïðîäàëè ïðåäìåò!".toLowerCase())) {
			//if(!bot.onSurvivalServer)
			//	System.out.println("§eBot §8[§a" + bot.instance.name + "§8] " + "§a:" + message);
		}
		
		if(message.contains("§bêîìàíäà áóäåò äîñòóïíà ÷åðåç".toLowerCase())) {
			//System.out.println("§eBot §8[§a" + bot.instance.name + "§8] " + "§ais spamming!");
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§ais spamming!");
		}
		
		if(message.contains("âû ïðèãëàøåíû â êëàí".toLowerCase())) {
			bot.send("/clan accept");
		}
		
		if(message.contains("/marry accept".toLowerCase())) {
			bot.send("/marry accept");
		}
		
		//§eÒåáÿ çàìóòèë §cMrRutek §eíà §d13 ñåê§e ïðè÷èíà§a: §b1§e.
		//§eÒåáÿ íàâñåãäà çàãëóøèë §cMrRutek§e ïðè÷èíà§a: §b1§e.
		if(message.contains("òåáÿ çàìóòèë".toLowerCase())) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + message);
		}
		
		if(message.contains("òåáÿ íàâñåãäà".toLowerCase())) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + message);
		}
		
		//if(message.contains("]") && !message.contains("testing"))
		/*
		if(bot.ready)
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + message);
		*/
		/*
		Bot: IWasTesting10 - 
		§ckicked whilst connecting to cactus1: 
		§a?
		§a? §eçàáàíåí èãðîêîì: §csirmark§e.
		§a? §eïðè÷èíà: §bbot§e.
		§a? §eâðåìÿ: §díàâñåãäà§e.
		§a? §eðàçáàí íà ñàéòå §4cactusc.ru
		§a?
		*/
		
		if(message.contains("§ckicked whilst connecting to cactus") && message.contains("çàáàíåí")) {
			bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + message);
			bot.delBot();
			//PBotCmd.instance.delBot(bot.instance.name);
		}
	}
	
	public void handlePacket10Flying(PBotThread bot, Packet10Flying packet) {
		bot.x = packet.xPosition;
		bot.y = packet.yPosition - bot.yOffset;
		bot.z = packet.zPosition;
		
		bot.pitch = packet.pitch;
		bot.yaw = packet.yaw;
		
		bot.motionX = 0;
		bot.motionY = 0;
		bot.motionZ = 0;
		
		bot.onGround = packet.onGround;
		
		bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, bot.onGround));
	}
	
	public void handlePacket8UpdateHealth(PBotThread bot, Packet8UpdateHealth packet) {
		bot.food = packet.food;
		bot.hp = packet.healthMP;
		
		if(bot.hp <= 0)
			bot.dead = true;
		else
			bot.dead = false;
		
		if(bot.dead) {
			bot.respawn();
		}
	}
}
