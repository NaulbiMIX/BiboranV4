package hack.rawfish2d.client.pbots;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.ModMisc.Scaffold;
import hack.rawfish2d.client.ModMovement.Step;
import hack.rawfish2d.client.ModTest.BotControl;
import hack.rawfish2d.client.ModUnused.AntiBan;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.modules.ModuleBase;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.ModuleManager;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet104WindowItems;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet201PlayerInfo;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet207SetScore;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet6SpawnPosition;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Packet8UpdateHealth;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.TcpConnection;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

public class PBotThread extends Thread {
	private static PBotVar showChat = new PBotVar("this", "showChat", true);
	
	public boolean enabled = false;
	public boolean ready = false; //wait for Packet6SpawnPosition
	public boolean stop = false;
	public PBot instance = null;
	public short transactionID = 0;
	
	public CopyOnWriteArrayList<ModuleBase> modules = new CopyOnWriteArrayList<ModuleBase>();
	private CopyOnWriteArrayList<LiteEntity> near_entityes = new CopyOnWriteArrayList<LiteEntity>();
	
	public boolean onSurvivalServer;
	public boolean inLobby;
	public boolean loggined;
	
	public double prevX;
	public double prevY;
	public double prevZ;
	
	public double motionX;
	public double motionY;
	public double motionZ;
	
	public double x;
	public double y;
	public final static double yOffset = 1.62000000476837;
	public double z;
	public boolean onGround;
	
	public int money;
	
	public float pitch;
	public float yaw;
	
	public int curSlot;
	public int compassSlot;
	
	public int entityId;
	public int windowId;
	public int ContainerWindowId;
	
	public int hp;
	public int food;
	public boolean dead;
	public boolean sneaking;
	public boolean sprinting;
	public boolean creativemode;
	
	private String ip;
	private int port;
	private PBotConnection connection;
	public boolean disconnected;
	public PBotWorld chunks;
	
	public ItemStack []inventory = null;
	public ItemStack []windowInventory = null;
	
	public final float landMovementFactor = 0.1F;
	public final float jumpMovementFactor = 0.02F;
	
	private HashMap<Integer, PotionEffect> activePotionsMap = new HashMap<Integer, PotionEffect>();
	//public String password = null;
	private TimeHelper timer;
	
	int index = 0;
	double []ArrMotionY = new double[89];
	
	public int BruteIndex = 0;
	
	public PBotThread(String ip, PBot instance) {
		this.instance = instance;
		
		resetVars();
		
		this.ip = ip;
		this.port = 25565;
		
		//этот код видел некоторое дерьмо
		ArrMotionY[index++] = -0.07840000152589255D;
		ArrMotionY[index++] = -0.07683200299069881D;
		ArrMotionY[index++] = -0.07529536439636786D;
		ArrMotionY[index++] = -0.07378945854458152D;
		ArrMotionY[index++] = -0.07231367078111361D;
		ArrMotionY[index++] = -0.07086739874475256D;
		ArrMotionY[index++] = -0.0694500521215673D;
		ArrMotionY[index++] = -0.06806105240377747D;
		ArrMotionY[index++] = -0.06669983265385326D;
		ArrMotionY[index++] = -0.06536583727299217D;
		ArrMotionY[index++] = -0.06405852177428528D;
		ArrMotionY[index++] = -0.0627773525606301D;
		ArrMotionY[index++] = -0.06152180670676444D;
		ArrMotionY[index++] = -0.06029137174610355D;
		ArrMotionY[index++] = -0.059085545461130096D;
		ArrMotionY[index++] = -0.057903835678871474D;
		ArrMotionY[index++] = -0.056745760069702555D;
		ArrMotionY[index++] = -0.055610845950695875D;
		ArrMotionY[index++] = -0.054498630092325584D;
		ArrMotionY[index++] = -0.053408658530003095D;
		ArrMotionY[index++] = -0.052340486378056994D;
		ArrMotionY[index++] = -0.05129367764882886D;
		ArrMotionY[index++] = -0.050267805074184935D;
		ArrMotionY[index++] = -0.049262449931518404D;
		ArrMotionY[index++] = -0.04827720187245177D;
		ArrMotionY[index++] = -0.0473116587558593D;
		ArrMotionY[index++] = -0.046365426483106376D;
		ArrMotionY[index++] = -0.045438118837807906D;
		ArrMotionY[index++] = -0.044529357327718344D;
		ArrMotionY[index++] = -0.04363877103048708D;
		ArrMotionY[index++] = -0.04276599644222756D;
		ArrMotionY[index++] = -0.04191067732907072D;
		ArrMotionY[index++] = -0.04107246458187319D;
		ArrMotionY[index++] = -0.040251016073654D;
		ArrMotionY[index++] = -0.03944599651987346D;
		ArrMotionY[index++] = -0.03865707734186685D;
		ArrMotionY[index++] = -0.03788393653235289D;
		ArrMotionY[index++] = -0.03712625852429596D;
		ArrMotionY[index++] = -0.03638373406192841D;
		ArrMotionY[index++] = -0.03565606007464339D;
		ArrMotionY[index++] = -0.03494293955324679D;
		ArrMotionY[index++] = -0.03424408142865332D;
		ArrMotionY[index++] = -0.033559200453254334D;
		ArrMotionY[index++] = -0.032888017084275134D;
		ArrMotionY[index++] = -0.03223025736986074D;
		ArrMotionY[index++] = -0.031585652837208045D;
		ArrMotionY[index++] = -0.0309539403829433D;
		ArrMotionY[index++] = -0.030334862165659615D;
		ArrMotionY[index++] = -0.02972816550092716D;
		ArrMotionY[index++] = -0.02913360275795185D;
		ArrMotionY[index++] = -0.028550931258479295D;
		ArrMotionY[index++] = -0.02797991317785886D;
		ArrMotionY[index++] = -0.027420315447983512D;
		ArrMotionY[index++] = -0.026871909662020244D;
		ArrMotionY[index++] = -0.026334471981300567D;
		ArrMotionY[index++] = -0.025807783044001553D;
		ArrMotionY[index++] = -0.025291627875333234D;
		ArrMotionY[index++] = -0.024785795800255528D;
		ArrMotionY[index++] = -0.024290080356990984D;
		ArrMotionY[index++] = -0.02380427921312389D;
		ArrMotionY[index++] = -0.023328194082921527D;
		ArrMotionY[index++] = -0.02286163064619018D;
		ArrMotionY[index++] = -0.022404398469348052D;
		ArrMotionY[index++] = -0.021956310927265577D;
		ArrMotionY[index++] = -0.021517185127521543D;
		ArrMotionY[index++] = -0.021086841835369796D;
		ArrMotionY[index++] = -0.02066510540086597D;
		ArrMotionY[index++] = -0.02025180368698898D;
		ArrMotionY[index++] = -0.019846767999524673D;
		ArrMotionY[index++] = -0.01944983301811476D;
		ArrMotionY[index++] = -0.019060836728684194D;
		ArrMotionY[index++] = -0.018679620357687554D;
		ArrMotionY[index++] = -0.018306028306824373D;
		ArrMotionY[index++] = -0.01793990808984347D;
		ArrMotionY[index++] = -0.01758111027021414D;
		ArrMotionY[index++] = -0.017229488400147375D;
		ArrMotionY[index++] = -0.01688489896078238D;
		ArrMotionY[index++] = -0.016547201303609427D;
		ArrMotionY[index++] = -0.01621625759314327D;
		ArrMotionY[index++] = -0.01589193275059131D;
		ArrMotionY[index++] = -0.015574094398701277D;
		ArrMotionY[index++] = -0.015262612807759979D;
		ArrMotionY[index++] = -0.014957360842728917D;
		ArrMotionY[index++] = -0.01465821391116151D;
		ArrMotionY[index++] = -0.014365049912527184D;
		ArrMotionY[index++] = -0.014077749188260213D;
		ArrMotionY[index++] = -0.013796194473002288D;
		ArrMotionY[index++] = -0.013520270846697713D;
		ArrMotionY[index++] = -0.013249865687626539D;
		//это 89 тиков падения 
		//тогда я не знал как норм сделать физику падения поэтому просто записал дельту движения по Y
		//код говно, но зато работает :D
	}
	
	public void addPotionEffect(PotionEffect pe) {
		if (this.activePotionsMap.containsKey(Integer.valueOf(pe.getPotionID())))
		{
			//((PotionEffect)this.activePotionsMap.get(Integer.valueOf(pe.getPotionID()))).combine(pe);
			//onChangedPotionEffect((PotionEffect)this.activePotionsMap.get(Integer.valueOf(pe.getPotionID())));
		}
		else
		{
			this.activePotionsMap.put(Integer.valueOf(pe.getPotionID()), pe);
			//this.onNewPotionEffect(par1PotionEffect);
		}
	}
	
	public void removePotionEffect(int id) {
		activePotionsMap.remove(Integer.valueOf(id));
	}
	
	public boolean isPotionActive(Potion potion) {
		return activePotionsMap.containsKey(Integer.valueOf(potion.id));
	}
	
	public boolean isPotionActive(int potionId) {
		return activePotionsMap.containsKey(Integer.valueOf(potionId));
	}
	
	public String getProxyAddress() {
		if(connection != null) {
			return connection.proxy_addr;
		}
		else {
			return null;
		}
	}
	
	public void sendPacket(Packet packet) {
		if(!this.disconnected && connection != null) {
			//sendChatClient("§eBot §8[§a" + instance.name + "§8] " + "§c: " + packet.toString());
			
			connection.addToSendQueue(packet);
			//if(packet.getPacketId() != 10 && packet.getPacketId() != 11 && packet.getPacketId() != 0)
				//System.out.println(packet);
			
			/*
			if(packet instanceof Packet102WindowClick) {
				Packet102WindowClick p = (Packet102WindowClick)packet;
				System.out.print("[Packet102WindowClick]");
				System.out.print(" | par1: " + p.window_Id);
				System.out.print(" | par2: " + p.inventorySlot);
				System.out.print(" | par3: " + p.mouseClick);
				System.out.print(" | par4: " + p.holdingShift);
				System.out.print(" | var7: " + p.itemStack);
				System.out.println(" | var6: " + p.action);
			}
			*/
		}
	}
	
	public void changeSlot(int slot) {
		if(slot < 0) {
			slot = 8;
		}
		else if(slot > 8) {
			slot = 0;
		}
		
		curSlot = slot;
		sendPacket(new Packet16BlockItemSwitch(curSlot));
		MiscUtils.sleep(3L);
	}
	
	public void respawn() {
		sendPacket(new Packet205ClientCommand(1));
		this.transactionID = 0;
	}
	
	public void optimizeNearEntityes() {
		if(near_entityes.size() <= 1)
			return;
		
		for(LiteEntity ent : near_entityes) {
			double dist = MiscUtils.getDistance(ent.getPos(), new Vector3f(x, y, z));
			dist = Math.sqrt(dist);
			
			if(dist >= 40.0D && !ent.getName().toLowerCase().contains("кузнец")) {
				near_entityes.remove(ent);
			}
		}
	}
	
	public CopyOnWriteArrayList<LiteEntity> getEntityes() {
		return this.near_entityes;
	}
	
	public void addEntity(LiteEntity ent) {
		LiteEntity old = getEntityByID(ent.getId());
		if(old == null) {
			near_entityes.add(ent);
		}
		else {
			for(int a = 0; a < near_entityes.size(); ++a) {
				if(ent.getId() == old.getId()) {
					near_entityes.remove(a);
					near_entityes.add(ent);
					return;
				}
			}
		}
	}
	
	public LiteEntity getEntityByID(int id) {
		for(LiteEntity ent : near_entityes) {
			if(ent.getId() == id) {
				return ent;
			}
		}
		return null;
	}
	
	public LiteEntity getEntityByName(String name) {
		for(LiteEntity ent : near_entityes) {
			if(ent.getName().equalsIgnoreCase(name)) {
				return ent;
			}
		}
		return null;
	}
	
	public void sendChatClient(String str) {
		if(showChat.get(false)) {
			MiscUtils.sendChatClient(str);
		}
	}
	
	public short getTransactionID() {
		++transactionID;
		return transactionID;
	}
	
	public void resetVars() {
		this.timer = new TimeHelper();
		this.timer.reset();
		this.ready = false;
		
		this.onSurvivalServer = false;
		this.inLobby = false;
		this.loggined = false;
		
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		
		this.prevX = -1;
		this.prevY = -1;
		this.prevZ = -1;
		
		this.x = -1;
		this.y = -1;
		this.z = -1;
		this.onGround = true;
		
		this.money = 0;
		
		this.pitch = 0;
		this.yaw = 0;
		
		this.curSlot = 0;
		this.compassSlot = 8;
		
		this.entityId = -1;
		this.windowId = 0;
		this.ContainerWindowId = 0;
		
		this.hp = 0;
		this.food = 0;
		this.dead = true;
		this.sneaking = false; //make handler for this
		this.sprinting = false; //make handler for this
		this.creativemode = false; //make handler for this
		
		this.inventory = null; //FIX THIS HANDLER
		this.windowInventory = null; //FIX THIS HANDLER
		
		this.disconnected = false;
	}
	
	public void disconnect() {
		sendPacket(new Packet255KickDisconnect("Quitting"));
		this.disconnected = true;
	}
	
	public void stopBot() {
		
		//========================
		//Exception ex = new Exception();
		//this.sendChatClient(ex.getMessage());
		//ex.printStackTrace();
		//========================
		
		if(BotControl.instance.isToggled())
			BotControl.instance.toggle();
		
		//BotControl thing
		BotControl.list.removeByValue(instance.name);
		
		this.disconnect();
		stop = true;
		enabled = false;
		
		connection.networkShutdown("BotStop");
	}
	
	public void delBot() {
		stopBot();
		PBotCmd.instance.delBot(instance.name);
	}
	
	public void connect(String name) {
		resetVars();
		
		this.disconnected = false;
		
		if(name == null) {
			connection = new PBotConnection(instance.name, ip, this);
			
			Thread th = new Thread(new Runnable() {
				public void run() {
					connection.connect();
				}
			}, instance.name + " connect() #1");
			th.start();
		}
		else {
			connection = new PBotConnection(name, ip, this);
			
			Thread th = new Thread(new Runnable() {
				public void run() {
					connection.connect();
				}
			}, instance.name + " connect() #2");
			th.start();
		}
		ModuleManager.CreateModules(this);
		enabled = true;
		
		//if(BotControl.instance.isToggled()) {
		
			WorldSettings ws = new WorldSettings(0L, EnumGameType.ADVENTURE, false, false, WorldType.DEFAULT);
			WorldClient world = new WorldClient(null, ws, 0, 2, Client.getInstance().mc.mcProfiler, Client.getInstance().mc.getLogAgent());
			chunks = new PBotWorld(instance, world);
			chunks.world.isRemote = true;
		
		//}
	}
	
	public void readPacket(Packet packet) {
		if( !enabled ) {
			MiscUtils.sleep(100L);
			return;
		}
		
		if(!BotControl.packetBotReadToClient(packet, this))
			return;
		
		try {
			for(ModuleBase mod : modules) {
				mod.onReadPacket(packet);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void send(String message) {
		
		if( !enabled ) {
			return;
		}
		
		//if(onSurvivalServer) {
		if(message.length() <= 100)
			sendPacket(new Packet3Chat(message));
		else {
			String msg = message.substring(100);
			sendPacket(new Packet3Chat(msg));
			//MiscUtils.sendChatClient("Bot " + instance.name + " send too big message!");
		}
		
		//}
	}
	
	public boolean isMoving() {
		if(prevX != x || prevY != y || prevZ != z) {
			return true;
		}
		
		if(motionX != 0 || motionY != 0 || motionZ != 0) {
			return true;
		}
			
		return false;
	}
	
	public void processReadPackets()
	{
		if (!this.disconnected && this.connection != null)
		{
			this.connection.processReadPackets();
		}
		/*
		if (this.connection != null)
		{
			this.connection.wakeThreads();
		}
		*/
	}
	
	private void botMovement() {
		//int id = chunks.getBlockId(Math.round(x - 1f), Math.round(y - 1f), Math.round(z - 1f));
		int id = 0;
		Block bl = chunks.getBlock((int)Math.round(x - 1f), (int)Math.round(y - 1f), (int)Math.round(z - 1f));
		if(bl != null) {
			if(!bl.getBlocksMovement(this.chunks.world, (int)Math.round(x - 1f), (int)Math.round(y - 1f), (int)Math.round(z - 1f))) {
				id = 1;
			}
		}
		
		if(id == 0 && motionY <= 0) {
			motionY += ArrMotionY[index];
			
			if(index < ArrMotionY.length - 1)
				index++;
			
			onGround = false;
			y += motionY;
			//this.sendChatClient("fall");
		}
		else {
			index = 0;
			motionY = 0;
			onGround = true;
			y = Math.round(y);
		}
		
		x += motionX;
		z += motionZ;
		
		if(motionX != 0 || motionY != 0 || motionZ != 0) {
			this.sendPacket(new Packet11PlayerPosition(x, y, y + yOffset, z, onGround));
			MiscUtils.sleep(2L);
			this.sendPacket(new Packet13PlayerLookMove(x, y, y + yOffset, z, yaw, pitch, onGround));
			MiscUtils.sleep(2L);
		}
	}
	
	public void run() {
		timer.reset();
		boolean added = false;
		
		while(!stop) {
			if(enabled)
				processReadPackets();
			
			if(timer.hasReached(30000L) && !ready) {
				String name = this.instance.name;
				
				//sendChatClient("§eBot §8[§a" + name + "§8] " + "§c§lRemoved.");
				
				stop = true;
				enabled = false;
				break;
			}
			
			if(!enabled || !ready) {
				MiscUtils.sleep(1000L);
				continue;
			}
			
			this.prevX = this.x;
			this.prevY = this.y;
			this.prevZ = this.z;
			
			for(ModuleBase mod : modules) {
				mod.onTick();
			}
			
			if(!BotControl.instance.isToggled()) { 
				try {
					botMovement();
				}
				catch(Exception ex) {}
			}
			
			if(!added && ready) {
				String proxy = this.getProxyAddress();
				
				if(!PBotCmd.goodProxy.contains(proxy))
					PBotCmd.goodProxy.add(proxy);
				
				added = true;
			}
			
			MiscUtils.sleep(50L);
		}
		this.delBot();
	}
}
