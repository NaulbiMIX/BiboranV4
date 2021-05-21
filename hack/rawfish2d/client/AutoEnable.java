package hack.rawfish2d.client;

public class AutoEnable{
	public static boolean first_enable = false;

	public static void ToggleModules() {
		if(first_enable == false) {
			Client.getInstance().moduleToggle("ESP");
			Client.getInstance().moduleToggle("PlayerInfo");
			Client.getInstance().moduleToggle("FastUse");
			//Client.moduleToggle("FastLadder");
			Client.getInstance().moduleToggle("Fullbright");
			Client.getInstance().moduleToggle("Nametags");
			Client.getInstance().moduleToggle("NoHurtCam");
			Client.getInstance().moduleToggle("NoFire");
			Client.getInstance().moduleToggle("AntiPotion");
			Client.getInstance().moduleToggle("Sprint");
			Client.getInstance().moduleToggle("ArmorHud");
			Client.getInstance().moduleToggle("EnabledMods");
			//Client.moduleToggle("Trajectories");
			Client.getInstance().moduleToggle("ItemESP");
			Client.getInstance().moduleToggle("PotionHud");
			Client.getInstance().moduleToggle("InvMove");
			
			Client.getInstance().moduleToggle("BucketBug");
			Client.getInstance().moduleToggle("NoRotate");
			Client.getInstance().moduleToggle("Lagometr");
			Client.getInstance().moduleToggle("DonateDumper");
			first_enable = true;
		}
	}
}
