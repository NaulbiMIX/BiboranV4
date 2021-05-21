package hack.rawfish2d.client.ModUnused;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3f;
import hack.rawfish2d.client.utils.Vector4f;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ESP2D extends Module {
	
	/*
	FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
	FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
	IntBuffer viewPort = BufferUtils.createIntBuffer(4);
	FloatBuffer win_pos = BufferUtils.createFloatBuffer(3);
	*/
	static List<Vector4f> pos_list;
	
	int count = 1000;
	
	public ESP2D() {
		super("ESP2D", 0, ModuleType.RENDER);
		setDescription("ESP как в CS:GO (жаль но могло быть лучше)");
		pos_list = new ArrayList<Vector4f>();
	}
	
	@Override
	public void onRender() {
		R3DUtils.GetModelMatrix();
		R3DUtils.GetProjectionMatrix();
	}
	
	@Override
	public void onRenderOverlay() {
		/*
		if(mc.gameSettings.showDebugInfo == true)
			return;
		*/
		
		for(Object theObject : mc.theWorld.loadedEntityList) {
			
			if( !(theObject instanceof EntityLiving && theObject instanceof EntityPlayer) )
				continue;
			
			if(PlayerUtils.isPlayer((Entity)theObject) == false)
				continue;
			
			EntityPlayer ent = (EntityPlayer)theObject;
			Vector3f ppos = new Vector3f(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
			
			if(ent instanceof EntityPlayer) {
				if(ent != mc.thePlayer) {
					if(mc.thePlayer.getDistanceToEntity(ent) > 2.5f) {
						Vector4f vec = GetPos(ent);
						render(vec);
					}
				}
				continue;
			}
		}
	}
	
	public Vector4f GetPos(EntityPlayer enemy) {
		Vector4f ret = null;
		if(enemy == null)
			return ret;
		
		double expos = 0;
		double eypos = 0;
		double ezpos = 0;
		
		expos = (enemy.lastTickPosX + (enemy.posX - enemy.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
		eypos = (enemy.lastTickPosY + (enemy.posY - enemy.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
		ezpos = (enemy.lastTickPosZ + (enemy.posZ - enemy.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
		
		Vector3f enemy_pos = new Vector3f(expos, eypos, ezpos);
		
		float w = enemy.width;
		float h = enemy.height;
		float eye = enemy.getEyeHeight();
		
		//BOX 2D
		Vector2f lp = new Vector2f(enemy_pos.x - w / 2, enemy_pos.z - w / 2);
		Vector2f rp = new Vector2f(enemy_pos.x + w / 2, enemy_pos.z + w / 2);
		/*
		System.out.println("minX : " + enemy.boundingBox.minX);
		System.out.println("minZ : " + enemy.boundingBox.minZ);
		
		System.out.println("maxX : " + enemy.boundingBox.maxX);
		System.out.println("maxZ : " + enemy.boundingBox.maxZ);
		*/	
		Vector2f center2D = new Vector2f(enemy_pos.x, enemy_pos.z);
		//BOX 2D
		
		float []angles = MiscUtils.getYawAndPitch(enemy);
		angles[0] = MiscUtils.normalizeYaw(angles[0]);
		
		lp = MiscUtils.rotatePoint2D(lp, center2D, angles[0]);
		rp = MiscUtils.rotatePoint2D(rp, center2D, angles[0]);
		
		Vector3f eye_pos = new Vector3f(lp.x, enemy_pos.y + eye, lp.y);
		Vector3f origin_pos = new Vector3f(rp.x, enemy_pos.y - 0.2, rp.y);
		
		Vector2f w2s_eye = R3DUtils.WorldToScreen(mc, new Vector3f(0, 0, 0), eye_pos);
		Vector2f w2s_origin = R3DUtils.WorldToScreen(mc, new Vector3f(0, 0, 0), origin_pos);
		
		ret = new Vector4f(w2s_eye.x, w2s_eye.y, w2s_origin.x, w2s_origin.y);
		return ret;
	}
	
	public static Vector3f MakeStuff(Vector3f origin_pos, Vector3f player_pos, Vector3f enemy_pos)
	{
		Vector3f diff = enemy_pos.sub(player_pos);
		diff.normalize();
		double angle = Math.acos(diff.Dot(origin_pos));
		Vector3f corrsspro = diff.Cross(origin_pos); 
		return corrsspro;
	}
	
	public void render(Vector4f pos)
	{		
		if(pos == null)
			return;
		
		//R3DUtils.drawBetterRect(pos.x, pos.y, pos.z, pos.w, new Color(0f, 255f, 0f, 255f), new Color(255f, 0f, 0f, 0f));
		
		if(pos.x > -10 && pos.y > -10) {
			R2DUtils.drawBetterRect(pos.x, pos.y, pos.z, pos.w, 2, 0x400000FF, 0xFFFF0000);
		}
	}
	//MaIospaal enemy
}
