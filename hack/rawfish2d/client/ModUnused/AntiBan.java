package hack.rawfish2d.client.ModUnused;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class AntiBan extends Module{
	public static Module instance = null;

	public static double x1 = 0;
	public static double y1 = 0;
	public static double z1 = 0;

	public static double x2 = 0;
	public static double y2 = 0;
	public static double z2 = 0;

	private long prevMs;

	public AntiBan() {
		super("AntiFucking", 0, ModuleType.MISC);
		setDescription("Íà ìóò èëè áàí â Âàøó ñòîðîíó, îòâå÷àåò áàíîì");
		instance = this;
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void preMotionUpdate() {
	}

	@Override
	public void onUpdate() {
		/*
		//long var1 = (long)(1000.0 / 1);
		long var1 = 5000;

		//if (System.nanoTime() / 1000000L - prevMs >= var1) {
		if (System.nanoTime() / 1000000L - prevMs >= var1) {
			prevMs = System.nanoTime() / 1000000L;
			x1 = 0;
			y1 = 0;
			z1 = 0;

			x2 = 0;
			y2 = 0;
			z2 = 0;

			mc.thePlayer.sendChatToPlayer("asd");
		}
		*/
	}

	@Override
	public void postMotionUpdate() {
	}

	@Override
	public void onRenderHand() {
	}

	@Override
	public void onRender() {
	}

	public static boolean isMyAcc(String name) {
		List<String> list = new ArrayList<String>();
		list.add("ddos1337");
		list.add("ilyapods");
		list.add("Supermanlolz64");
		list.add("FOXGANSTA123MLG");
		list.add("Number_13");
		list.add("Epic_PVP");
		list.add("shumamak");
		list.add("_noisygames_");
		list.add("_fox");
		list.add("Catycat14");
		list.add("ermarice");
		list.add("DiamondFarm");
		list.add("Slava74241");
		list.add("Diahi");
		list.add("_Mino_");
		list.add("Dfyzzzzzzzz");
		list.add("Black_Flash");
		list.add("jimi");

		for(int a = 0; a < list.size(); ++a) {
			if(PlayerUtils.strEqual(list.get(a), name)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onChatMessage(String str) {
		//§eÈãðîê §cFKDIE§e íàâñåãäà çàìóòèë §cRawFish2D§a: §bXUY
		//§eÈãðîê §cFKDIE§e íàâñåãäà çàáàíèë §cRawFish2D§a: §bXUY
		//§eÈãðîê §cFKDIE§e êèêíóë §cRawFish2D§a: §bXUY

		//§eÈãðîê §cvulgar_molly2 §eçàìóòèë §cRawFish2D §eíà §d19 ñåê§a: §bÒåñò (ñàì ïîïðîñèë)
		//§eÒåáÿ çàìóòèëè íà: §c19 ñåê§e.
		//§eÈãðîê §cvulgar_molly2 §eçàáàíèë §cRawFish2D §eíà §d19 ñåê§a: §bÒåñò (ñàì ïîïðîñèë)

		/*
		if(str.contains("tempban")) {
			mc.thePlayer.sendChatToPlayer("§eÈãðîê §cvulgar_molly2 §eçàáàíèë §cRawFish2D §eíà §d23 ÷ 59 ìèí 59 ñåê§a: §bÒåñò (ñàì ïîïðîñèë)");
		}
		if(str.contains("ban")) {
			mc.thePlayer.sendChatToPlayer("§eÈãðîê §cvulgar_molly2§e íàâñåãäà çàáàíèë §cRawFish2D§a: §bÒåñò (ñàì ïîïðîñèë)");
		}

		if(str.contains("tempmute")) {
			mc.thePlayer.sendChatToPlayer("§eÈãðîê §cvulgar_molly2 §eçàìóòèë §cRawFish2D §eíà §d23 ÷ 59 ìèí 59 ñåê§a: §bÒåñò (ñàì ïîïðîñèë)");
		}
		if(str.contains("mute")) {
			mc.thePlayer.sendChatToPlayer("§eÈãðîê §cvulgar_molly2§e íàâñåãäà çàìóòèë §cRawFish2D§a: §bÒåñò (ñàì ïîïðîñèë)");
		}

		if(str.contains("kick")) {
			mc.thePlayer.sendChatToPlayer("§eÈãðîê §cvulgar_molly2§e êèêíóë §cRawFish2D§a: §bÒåñò (ñàì ïîïðîñèë)");
		}

		BanMuteKick bmk = MiscUtils.getBanMessage(str);
		if(bmk != null)
			bmk.chat();
		*/
		//MiscUtils.getMuteMessage(str);
		//MiscUtils.getKickMessage(str);

		if (str.startsWith("§eÈãðîê §c")) {
			String []split = str.split(" ");

			for(int a = 0; a < split.length; ++a) {
				split[a] = split[a].replaceAll("§[0-9]", "");
				split[a] = split[a].replaceAll("§[a-f]", "");

				split[a] = split[a].replaceAll("§[k-l]", "");
				split[a] = split[a].replaceAll("§r", "");

				split[a] = split[a].replaceAll(":", "");
			}

			//split[0] = split[0].substring(2, 7);

			if(split.length >= 5) {
				if(split[2].equalsIgnoreCase("íàâñåãäà")) {
					//split[1] = split[1].substring(2, split[1].length() - 2);
					//split[4] = split[4].substring(2, split[4].length() - 3);
					//split[5] = split[5].substring(2, split[5].length());

					if(PlayerUtils.isAuthor(split[4]) || isMyAcc(split[4])) {
						if(split[3].equalsIgnoreCase("çàìóòèë")) {
							System.out.println(split[4] + " has been permanently muted by " + split[1]);
							mc.thePlayer.sendChatMessage("/ban " + split[1] + " 5h ëîæ ìóò");
							mc.thePlayer.sendChatMessage("/hub");
						}
						else if(split[3].equalsIgnoreCase("çàáàíèë")) {
							System.out.println(split[4] + " has been permanently banned by " + split[1]);
							mc.thePlayer.sendChatMessage("/ban " + split[1] + " 5h ëîæ áàí");
							mc.thePlayer.sendChatMessage("/hub");
						}
					}
				}
				else if(split[2].equalsIgnoreCase("çàìóòèë")) {
					//split[1] = split[1].substring(2);
					//split[2] = split[2].substring(2);
					//split[3] = split[3].substring(2);
					//split[5] = split[5].substring(2);

					if(PlayerUtils.isAuthor(split[3]) || isMyAcc(split[3])) {
						System.out.println(split[3] + " has been muted by " + split[1]);
						mc.thePlayer.sendChatMessage("/ban " + split[1] + " 5h ëîæ ìóò");
						mc.thePlayer.sendChatMessage("/hub");
					}
				}
				else if(split[2].equalsIgnoreCase("çàáàíèë")) {
					//split[1] = split[1].substring(2);
					//split[2] = split[2].substring(2);
					//split[3] = split[3].substring(2);
					//split[5] = split[5].substring(2);

					if(PlayerUtils.isAuthor(split[3]) || isMyAcc(split[3])) {
						System.out.println(split[3] + " has been banned by " + split[1]);
						mc.thePlayer.sendChatMessage("/ban " + split[1] + " 5h ëîæ áàí");
						mc.thePlayer.sendChatMessage("/hub");
					}
				}
			}
			if(split.length >= 6) {
				//split[0] = split[0].substring(2, 7);
				//split[1] = split[1].substring(2, split[1].length() - 2);
				//split[4] = split[4].substring(2, split[4].length() - 3);
				//split[5] = split[5].substring(2, split[5].length());
			}

			for(int a = 0; a < split.length; ++a) {
				System.out.println("split[" + a + "] : " + split[a]);
			}
		}
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
	}

	@Override
	public void onRenderOverlay() {
	}

	public static void static_sub1() {
	}

	public static void static_sub2() {
	}

	public static void sub1() {
	}

	public static void sub2() {
	}
}
