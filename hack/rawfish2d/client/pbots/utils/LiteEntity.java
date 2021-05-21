package hack.rawfish2d.client.pbots.utils;

import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet25EntityPainting;

public class LiteEntity {
	private int type;
	private String name;
	private int id;
	
	private float pitch;
	private float yaw;
	
	private Vector3f pos = null;
	
	public LiteEntity() {
		pos = new Vector3f(0, 0, 0);
	}
	
	public LiteEntity(Packet20NamedEntitySpawn packet) {
		name = packet.name;
		id = packet.entityId;
		
		pitch = packet.pitch;
		yaw = packet.rotation;
		type = 1;
		
		pos = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
	}
	
	public LiteEntity(Packet23VehicleSpawn packet) {
		name = "drop";
		id = packet.entityId;
		
		pitch = packet.pitch;
		yaw = packet.yaw;
		type = 2;
		
		pos = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
	}

	public LiteEntity(Packet25EntityPainting packet) {
		name = packet.title;
		id = packet.entityId;
		
		pitch = packet.direction;
		yaw = packet.direction;
		type = 3;
		
		pos = new Vector3f(packet.xPosition, packet.yPosition, packet.zPosition);
	}
	
	public int getType() {
		return type;
	}
/*
	public void setPainting(boolean painting) {
		this.painting = painting;
	}
*/
	public Vector3f getPos() {
		return pos;
	}
	
	public float X() {
		return pos.x;
	}
	
	public float Y() {
		return pos.y;
	}
	
	public float Z() {
		return pos.z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPos(Vector3f newpos) {
		this.pos = newpos;
	}
	
	public void setPos(float x1, float y1, float z1) {
		this.pos = new Vector3f(x1, y1, z1);
	}
	
	public void setAngles(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
}
