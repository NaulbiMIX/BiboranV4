package hack.rawfish2d.client.ModTest;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMisc.Tower;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class AutoBuild extends Module {
	
	public List<AB> list = new ArrayList<AB>();
	public BoolValue record;
	public int index = 0;
	
	public AutoBuild() {
		super("AutoBuild", 0, ModuleType.TEST);
		setDescription("ur mom gay");
		record = new BoolValue(true);
		
		this.elements.add(new CheckBox(this, "Record", record, 0, 0));
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
		if(record.getValue())
			list.clear();
		
		index = 0;
	}

	@Override
	public void onUpdate() {
		//start 3393
		//end 3593
		TestUtils.dvar3 = mc.thePlayer.posX;
		
		if(mc.thePlayer.posY >= 220 && index == 0) {
			//Tower.instance.toggle();
			index = 1;
		}
		else if(index == 1) {
			MiscUtils.sendChat("/tppos " + (int)(TestUtils.dvar3 + 1) + " 250 -245");
			index = 2;
		}
		else if(index == 3 && mc.thePlayer.onGround) {
			index = 0;
			//Tower.instance.toggle();
		}
		
		if(index == 0) {
			if(mc.thePlayer.onGround && !mc.thePlayer.isJumping) {
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionY = 0;
				mc.thePlayer.motionZ = 0;
				mc.thePlayer.jump();
			}
			
			MiscUtils.setAngles(89f, mc.thePlayer.rotationYaw, false);
			
			int x = (int)mc.thePlayer.posX;
			int y = (int)mc.thePlayer.posY - 3;
			int z = (int)mc.thePlayer.posZ - 1;
			mc.thePlayer.swingItem();
			//mc.theWorld.setBlock(x, y, z, mc.thePlayer.inventory.getCurrentItem().itemID);
			mc.getNetHandler().addToSendQueue(new Packet15Place(x, y, z, 1, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
		}
		
		/*
		float motion = 0.18f;
		
		if(index == 0) {
			//motion = 0.10f;
		}
		else if(index == 5) {
			motion = -motion;
		}
		
		if(TestUtils.dvar3 >= 3592d && TestUtils.dvar3 <= 3593d && index == 0) {
			index = 1;
		}
		
		if(index == 4) {
			int x = (int)mc.thePlayer.posX;
			int y = (int)mc.thePlayer.posY - 1;
			int z = (int)mc.thePlayer.posZ;
			mc.thePlayer.swingItem();
			//mc.theWorld.setBlock(x, y, z, mc.thePlayer.inventory.getCurrentItem().itemID);
			mc.getNetHandler().addToSendQueue(new Packet15Place(x, y, z, 1, mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
		}
		
		if(index == 4 && time.hasReached(1000L) && mc.thePlayer.onGround && !mc.thePlayer.isJumping) {
			index = 0;
			time.reset();
			mc.thePlayer.rotationPitch = 0.f;
		}
		
		if(index == 3 && time.hasReached(2000L) && mc.thePlayer.onGround && !mc.thePlayer.isJumping) {
			time.reset();
			index = 4;
			mc.thePlayer.jump();
			mc.thePlayer.rotationPitch = 90.f;
		}
		
		if(index == 1) {
			MiscUtils.sendChat("/tppos 3393 250 -245");
			index = 2;
		}
		
		if(index == 0)
			mc.thePlayer.motionX = motion;
		*/
		
		/*
		if(record.getValue()) {
			
			AB ab = new AB(mc);
			
			ab.forward = mc.gameSettings.keyBindForward.pressed;
			ab.left = mc.gameSettings.keyBindLeft.pressed;
			ab.right = mc.gameSettings.keyBindRight.pressed;
			ab.back = mc.gameSettings.keyBindBack.pressed;
			
			ab.attack = mc.gameSettings.keyBindAttack.pressed;
			ab.use = mc.gameSettings.keyBindUseItem.pressed;
			
			ab.slot = mc.thePlayer.inventory.currentItem;
			
			if(ab.forward || ab.left || ab.right || ab.back || ab.attack || ab.use) {
				list.add(ab);
			}
			
			//list.add(new AB(mc));
		}
		else if(!list.isEmpty()){
			AB ab = list.get(index);
			ab.execute();
			
			if(index < list.size() - 1)
				index++;
			else
				index = 0;
		}
		*/
	}
	
	TimeHelper time = new TimeHelper();
	
	@Override
	public void onPacket(Packet packet) {
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			if(p.message.contains("Телепортирование...") && index == 2) {
				index = 3;
				time.reset();
			}
		}
	}
	
	@Override
	public void onRender() {
		
	}
	
	private class AB {
		public boolean forward;
		public boolean left;
		public boolean right;
		public boolean back;
		
		public boolean attack;
		public boolean use;
		
		public boolean shift;
		public boolean space;
		
		public int slot;
		
		public AB(Minecraft mc) {
			forward = mc.gameSettings.keyBindForward.pressed;
			left = mc.gameSettings.keyBindLeft.pressed;
			right = mc.gameSettings.keyBindRight.pressed;
			back = mc.gameSettings.keyBindBack.pressed;
			
			attack = mc.gameSettings.keyBindAttack.pressed;
			use = mc.gameSettings.keyBindUseItem.pressed;
			
			shift = mc.gameSettings.keyBindSneak.pressed;
			space = mc.gameSettings.keyBindJump.pressed;
			
			slot = mc.thePlayer.inventory.currentItem;
		}
		
		public void execute() {
			mc.gameSettings.keyBindForward.pressed = forward;
			mc.gameSettings.keyBindLeft.pressed = left;
			mc.gameSettings.keyBindRight.pressed = right;
			mc.gameSettings.keyBindBack.pressed = back;
			
			mc.gameSettings.keyBindAttack.pressed = attack;
			mc.gameSettings.keyBindUseItem.pressed = use;
			
			mc.gameSettings.keyBindSneak.pressed = shift;
			mc.gameSettings.keyBindJump.pressed = space;
			
			mc.thePlayer.inventory.currentItem = slot;
		}
	}
}
