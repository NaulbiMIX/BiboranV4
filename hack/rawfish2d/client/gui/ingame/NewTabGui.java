package hack.rawfish2d.client.gui.ingame;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Score;
import net.minecraft.src.ScoreObjective;
import net.minecraft.src.ScorePlayerTeam;

public class NewTabGui {
	private static Minecraft mc = Client.getInstance().mc;

	public static void renderTabGui() {
		ScoreObjective scoreobj = mc.theWorld.getScoreboard().func_96539_a(1);

		if (mc.gameSettings.keyBindPlayerList.pressed && (!mc.isIntegratedServerRunning() || mc.thePlayer.sendQueue.playerInfoList.size() > 1 || scoreobj != null))
		{
			mc.mcProfiler.startSection("playerList");

			int counter;
			FontRenderer fr = mc.fontRenderer;
			CopyOnWriteArrayList list = mc.thePlayer.sendQueue.playerInfoList;
			
			//no fake players
			/*
			for(Object obj : list) {
				GuiPlayerInfo gpi = (GuiPlayerInfo)obj;
				if(gpi.responseTime < 30) {
					list.remove(obj);
				}
			}
			*/
			//no fake players
			
			int maxPlayers = mc.thePlayer.sendQueue.currentServerMaxPlayers;
			int maxPlayers2 = maxPlayers;

			ScaledResolution sr = MiscUtils.getScaledResolution();
			int scaledW = sr.getScaledWidth();
			int scaledH = sr.getScaledHeight();
			int player_count = list.size();

			for (counter = 1; maxPlayers2 > 20; maxPlayers2 = (maxPlayers + counter - 1) / counter) {
				++counter;
			}

			int var17 = 300 / counter;

			if (var17 > 150) {
				var17 = 150;
			}
			var17 = 140;

			int tab_xpos = (scaledW - counter * var17) / 2;
			int tab_ypos = 10;
			//drawRect(tab_xpos - 1, tab_ypos - 1, tab_xpos + var17 * counter, tab_ypos + 9 * maxPlayers2, 0x80000000); //orig

			//ultrakek
			double hf = ((player_count / 3) + 1);
			int h = (int) Math.floor(hf);
			Gui.drawRect(tab_xpos - 1, tab_ypos - 1, tab_xpos + var17 * counter, tab_ypos + 9 * h, 0x80000000);

			//for (counter2 = 0; counter2 < maxPlayers; ++counter2)
			for (int counter2 = 0; counter2 < player_count; ++counter2)
			{
				int xrect = tab_xpos + counter2 % counter * var17;
				int yrect = tab_ypos + counter2 / counter * 9;
				Gui.drawRect(xrect, yrect, xrect + var17 - 1, yrect + 8, 0x20FFFFFF);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);

				if (counter2 < player_count)
				{
					GuiPlayerInfo gui_player_info = (GuiPlayerInfo)list.get(counter2);
					ScorePlayerTeam score_player_team = mc.theWorld.getScoreboard().getPlayersTeam(gui_player_info.name);
					String tab_nickname = ScorePlayerTeam.func_96667_a(score_player_team, gui_player_info.name);
					/*
					if(tab_nickname.contains("]")) {
						int index = tab_nickname.lastIndexOf("]");

						tab_nickname = tab_nickname.substring(index + 1, tab_nickname.length());
						tab_nickname = tab_nickname.replace("§a", "");
						tab_nickname = tab_nickname.replace("§b", "");
						tab_nickname = tab_nickname.replace("§e", "");
						tab_nickname = tab_nickname.replace(" ", "");
					}
					tab_nickname = "§8[§eÕåëïåð§8]§a" + tab_nickname;
					*/
					/*
					if(tab_nickname.contains("]")) {
						int index = tab_nickname.lastIndexOf("]");

						tab_nickname = tab_nickname.substring(index + 1, tab_nickname.length());
						tab_nickname = tab_nickname.replace("§a", "");
						tab_nickname = tab_nickname.replace("§b", "");
						tab_nickname = tab_nickname.replace("§e", "");
						tab_nickname = tab_nickname.replace(" ", "");
						tab_nickname = "§8[§eÏåðåáàíùèê§8]§a" + tab_nickname;
					}
					*/
					fr.drawStringWithShadow(tab_nickname, xrect, yrect, 0xFFFFFF);
					/*
					if (scoreobj != null) {
						int xstr = xrect + fr.getStringWidth(tab_nickname) + 5;
						int ystr = xrect + var17 - 12 - 5;

						if (ystr - xstr > 5) {
							Score score = scoreobj.getScoreboard().func_96529_a(gui_player_info.name, scoreobj);
							tab_nickname = EnumChatFormatting.YELLOW + "" + score.func_96652_c();
							fr.drawStringWithShadow(tab_nickname, ystr - fr.getStringWidth(tab_nickname), yrect, 0xFFFFFF);
						}
					}
					*/

					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture("/gui/icons.png");

					int ping = 0;
					if (gui_player_info.responseTime < 0) {
						ping = 5;
					}
					else if (gui_player_info.responseTime < 150) {
						ping = 0;
					}
					else if (gui_player_info.responseTime < 300) {
						ping = 1;
					}
					else if (gui_player_info.responseTime < 600) {
						ping = 2;
					}
					else if (gui_player_info.responseTime < 1000) {
						ping = 3;
					}
					else {
						ping = 4;
					}

					mc.ingameGUI.zLevel += 100.0F;
					mc.ingameGUI.drawTexturedModalRect(xrect + var17 - 12, yrect, 0, 176 + ping * 8, 10, 8);
					mc.ingameGUI.zLevel -= 100.0F;
				}
			}
		}
	}

	private static void renderTabBackground() {
	}

	private static void renderSlot(String name, int x, int y) {
	}

	public static void renderTabGui2() {
	}
}
