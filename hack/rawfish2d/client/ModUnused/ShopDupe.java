package hack.rawfish2d.client.ModUnused;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet7UseEntity;

public class ShopDupe extends Module {
	public static Module instance = null;
	private long time = 0;
	private long cur_time;
	public static int delay = 2001;
	
	public ShopDupe() {
		super("ShopDupe", Keyboard.KEY_LBRACKET, ModuleType.MISC);
		setDescription("ƒюп денег на warp shop у  узнеца (работает только с /gm 1)");
		instance = this;
	}
	
	@Override
	public void onUpdate() {
		cur_time = System.currentTimeMillis();
		
		if(time <= cur_time && time != 0) {
			shopDupe();
			time = 0;
		}
		else if(time == 0)
			time = cur_time + (delay / 2);
	}
	
	@Override
	public void onDisable() {
		time = 0;
	}
	
	@Override
	public void onEnable() {
		time = 0;
	}
	
	public static EntityPlayer getShopEntity() {
		for (Object obj : mc.theWorld.playerEntities) {
			if (obj != null) {
				if (obj == mc.thePlayer) {
					continue;
				}

				EntityPlayer ent = (EntityPlayer)obj;
				String name = ent.username;

				//if(name.equals(" узнец") && mc.thePlayer.getDistanceToEntity(ent) < 6.5f) {
				if(name.equals(" узнец")) {
					return ent;
				}
			}
		}
		return null;
	}
	
	public void shopDupe() {
		/*
		mc.thePlayer.sendChatMessage("/gm 1");
		
		ItemStack itemClock = new ItemStack(347, 64, 0);
		itemClock.stackSize = 64;
		for(int a = 9; a < 45; ++a) {
			mc.getNetHandler().addToSendQueue(new Packet107CreativeSetSlot(a, itemClock));
		}
		*/
		EntityPlayer shop_ent = getShopEntity();
		if(shop_ent == null) {
			mc.thePlayer.sendChatMessage("/warp shopdupe");
			return;
		}
		
		//String name = shop_ent.username;
		//System.out.println("Final name : " + name);
		
		//mc.thePlayer.sendChatMessage("/gm 0");
		mc.getNetHandler().addToSendQueue(new Packet7UseEntity(mc.thePlayer.entityId, shop_ent.entityId, 0));
		
		for(int a = 54; a < 90; ++a) {
			mc.getNetHandler().addToSendQueue(new Packet102WindowClick(Client.getInstance().window_id + 1, a, 0, 0, (ItemStack)null, (short)1));
		}
		
		mc.getNetHandler().addToSendQueue(new Packet101CloseWindow(Client.getInstance().window_id + 1));
		
		mc.thePlayer.sendChatMessage("/warp shopdupe2");
	}
}
