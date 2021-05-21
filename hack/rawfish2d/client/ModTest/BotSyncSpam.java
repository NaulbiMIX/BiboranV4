package hack.rawfish2d.client.ModTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.modules.Spam;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector3d;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.PathEntity;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class BotSyncSpam extends Module {
	public static Module instance = null;
	public static DoubleValue delay;
	
	public static int sendMsgIndex = 0;
	public static int sendBotIndex = 0;
	public static TimeHelper time = new TimeHelper();
	public static CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<String>();
	
	public BotSyncSpam() {
		super("BotSyncSpam", 0, ModuleType.TEST);
		setDescription("Последовательный спам ботами");
		instance = this;
		delay = new DoubleValue(450, 10, 5000);
		
		this.elements.add(new NewSlider(this, "Delay", delay, 0, 0, true));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		ConfigManager.loadBotSpam();
		messages = Spam.msgs;
		
		sendBotIndex = 0;
		sendMsgIndex = 0;
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void onRender() {
		syncSpam();
		/*
		float range = 4.9f;
		float angle = 360f;
		LiteEntity target = null;
		
		PBot pbot = PBotCmd.bots.get(0);
		PBotThread bot = pbot.pbotth;
		
		for(LiteEntity ent : bot.getEntityes()) {
			double mindistance = 99;
			if(ent.getType() == 1) {
				
				boolean isOk = true;
				for(PBot otherbot : PBotCmd.bots) {
					if(otherbot.pbotth != null) {
						if(otherbot.pbotth.entityId == ent.getId()) {
							isOk = false;
							break;
						}
					}
				}
				
				if(!isOk)
					continue;
				
				double distance = MiscUtils.getDistance(ent.getPos(), new Vector3f(bot.x, bot.y, bot.z));
				distance = Math.sqrt(distance);
				
				if(mindistance > distance) {
					mindistance = distance;
				}
				
				if(distance < range && mindistance == distance) {
					target = ent;
				}
			}
		}
		
		if(target != null) {
			float x = target.getPos().x;
			float y = target.getPos().y;
			float z = target.getPos().z;
				
			Vector3d renderVec = new Vector3d(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
			
			R3DUtils.drawBlockESP(renderVec, 0x22FF0000, 0xFF00FF00, 2f);
			
			//=====================================
			FontRenderer fontRenderer = mc.fontRenderer;
			
			//=====================================
			final float translateDist = 3.0f;
			final float scaleDist = 6.5f;
			
			RenderManager renderManager = RenderManager.instance;
			double camX = renderVec.x;
			double camY = renderVec.y;
			double camZ = renderVec.z;
			double distance = 8d;
			String entName = target.getName();
			//MiscUtils.sendChatClient(entName);
			
			//if (distance > translateDist)
			//	GL11.glTranslatef((float)camX + 0.0f, (float)(camY + 2.299999952316284 + distance / 100.0 - 0.2), (float)camZ);
			//else
			//	GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.3f, (float)camZ);
			
			//GL11.glTranslatef((float)camX + 0.0f, (float)camY + 2.4f, (float)camZ);
			GL11.glTranslatef((float)camX + 0.0f, (float)camY + 1.62f + 0.50f, (float)camZ);
			GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			
			float sc = 0.026f;
			
			if (distance > scaleDist) {
				GL11.glScaled((double)(-sc) * distance / scaleDist, (double)(-sc) * distance / scaleDist, (double)sc * distance / scaleDist);
			} else {
				GL11.glScaled(-sc, -sc, sc);
			}
			//=====================================
			int strw = fontRenderer.getStringWidth(entName) / 2;
			Gui.drawRect(-strw -2, -3, strw + 2, 11, 0x80000000);
			
			int color = 0xFF045FB4;
			
			//ent.renderDistanceWeight = 10.0; //no render players
			
			fontRenderer.drawStringWithShadow(entName, -strw, 0, color);
		}
		*/
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		/*
		for(PBot bot : PBotCmd.instance.bots) {
			if(bot.pbotth != null) {
				if(bot.pbotth.ready) {
					for(int a = 0; a < 10; ++a) {
						bot.pbotth.send("/perms");
					}
				}
			}
		}
		*/
	}
	
	@Override
	public void onPacket(Packet packet) {
		
	}
	
	public static void syncSpam() {		
		
		try {
			if(time.hasReached(delay.getValue())) {
				
				PBot bot = getNextBot();
				String msg = getNextMessage();
				
				if(bot != null && msg != null) {
					//bot.pbotth.send("/g " + msg);
					bot.pbotth.send(msg);
				}
				
				time.reset();
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
	}
	
	
	public static PBot getNextBot() {
		/*
		if(sendBotIndex >= PBotCmd.bots.size()) {
			sendBotIndex = 0;
		}
		
		PBot bot = PBotCmd.bots.get(sendBotIndex);
		
		if(sendBotIndex < PBotCmd.bots.size() - 1) {
			sendBotIndex++;
		}
		else {
			sendBotIndex = 0;
		}
		*/
		
		if(sendBotIndex >= PBotCmd.bots.size()) {
			sendBotIndex = 0;
		}
		
		PBot bot = PBotCmd.bots.get(sendBotIndex);
		
		while(true) {
			if(sendBotIndex >= PBotCmd.bots.size()) {
				sendBotIndex = 0;
			}
			
			bot = PBotCmd.bots.get(sendBotIndex);
			
			if(sendBotIndex < PBotCmd.bots.size() - 1) {
				sendBotIndex++;
			}
			else {
				sendBotIndex = 0;
			}
			
			if(bot.pbotth.ready)
				break;
		}
		
		/*
		PBot bot;
		
		while(true) {
			if(sendBotIndex >= PBotCmd.bots.size()) {
				sendBotIndex = 0;
			}
			
			bot = PBotCmd.bots.get(sendBotIndex);
			
			if(!bot.pbotth.ready) {
				if(sendBotIndex < PBotCmd.bots.size() - 1) {
					sendBotIndex++;
				}
				else {
					sendBotIndex = 0;
				}
			}
			else
				break;
		}
		*/
		
		/*
		while(!bot.pbotth.ready) {
			if(sendBotIndex >= PBotCmd.bots.size()) {
				sendBotIndex = 0;
			}
			
			bot = PBotCmd.bots.get(sendBotIndex);
			
			if(sendBotIndex < PBotCmd.bots.size() - 1) {
				sendBotIndex++;
			}
			else {
				sendBotIndex = 0;
			}
		}
		*/
		return bot;
	}
	
	public static String getNextMessage() {
		String msg = messages.get(sendMsgIndex);
		//msg = "я тебя бум бум бум ты меня бум бум бум я тебя бум бум бум ты меня бум бум бум";
		//String msg = "! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk";
		//String msg = "! tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk tk";
		
		if(sendMsgIndex < messages.size() - 1) {
			sendMsgIndex++;
		}
		else {
			sendMsgIndex = 0;
		}
		
		return msg;
	}
}
