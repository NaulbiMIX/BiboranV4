package hack.rawfish2d.client.ModMisc;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.Vector3d;
import net.minecraft.src.EntityFishHook;
import net.minecraft.src.ItemEditableBook;
import net.minecraft.src.ItemFishingRod;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.Packet62LevelSound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class AutoFish extends Module {
	
	public AutoFish() {
		super("AutoFish", 0, ModuleType.MISC);
		setDescription("Автоматически ловит рыбу");
		//
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
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		//autoFish();
	}
	
	@Override
	public void onRender() {
		
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		
	}
	
	@Override
	public void onPacket(Packet packet) {
		if(packet instanceof Packet62LevelSound) {
			
			Packet62LevelSound p = (Packet62LevelSound)packet;
			//MiscUtils.sendChatClient(":" + p.soundName);
			
			if((p.soundName.equalsIgnoreCase("random.splash") || p.soundName.equalsIgnoreCase("random.pop"))) {
				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFishingRod) {
						ItemStack is = mc.thePlayer.inventory.getCurrentItem();
						
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, is);
						
						MiscUtils.sleep(500L);
						
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, is);
					}
				}
			}
		}
	}
	
	/*
	public void autoFish() {
		if(isFishing == false) {
			x = 0;
			y = 0;
			z = 0;
		}
		
		EntityFishHook ent = null;
		for(int a = 0; a < mc.theWorld.loadedEntityList.size(); ++a) {
			Object obj =  mc.theWorld.loadedEntityList.get(a);
			if(obj instanceof EntityFishHook) {
				ent = (EntityFishHook)obj;
				isFishing = true;
				break;
			}
			else
				continue;
		}
		
		if(isFishing == true && ent == null) {
			if(mc.thePlayer.inventory.getCurrentItem() != null) {
				if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFishingRod) {
					ItemStack item = mc.thePlayer.inventory.getCurrentItem();
					mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, item);
					isFishing = true;
				}
			}
		}
		
		if(ent != null && x == 0 && y == 0 && z == 0 && isFishing) {
			if(ent.prevPosX != 0 && ent.prevPosY != 0 && ent.prevPosZ != 0) {
				if(		Math.abs(ent.prevPosX - ent.posX) < 0.010D &&
						Math.abs(ent.prevPosY - ent.posY) < 0.010D &&
						Math.abs(ent.prevPosZ - ent.posZ) < 0.010D) {
					
					x = ent.posX;
					y = ent.posY;
					z = ent.posZ;
				}
			}
		}
		
		if(ent == null)
			isFishing = false;
		
		if(ent != null && isFishing && x != 0 && y != 0 && z != 0) {
			if(Math.abs(y - ent.posY) > 0.155D) {
				if (mc.thePlayer.inventory.getCurrentItem() != null) {
					if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFishingRod) {
						ItemStack item = mc.thePlayer.inventory.getCurrentItem();
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, item);
						System.out.println("==== [ diff ] ==== y : " + Math.abs(y - ent.posY));
						x = 0;
						y = 0;
						z = 0;
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, item);
						isFishing = true;
					}
				}
			}
		}
	}
	*/
}
