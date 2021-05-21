package hack.rawfish2d.client.Schematic;

import hack.rawfish2d.client.utils.Vector3d;
import hack.rawfish2d.client.utils.Vector3f;
import hack.rawfish2d.client.utils.Vector3i;
import net.minecraft.src.RenderManager;

public class BlockData {
	public short id;
	public byte data;
	public Vector3i pos;
	public Vector3i worldPos;
	
	public BlockData() {}
	
	public BlockData(short id, byte data, Vector3i pos, Vector3i worldPos) {
		this.id = id;
		this.data = data;
		this.pos = pos;
		this.worldPos = worldPos;
	}
	
	public Vector3d getRenderPos() {
		return new Vector3d(worldPos.x - RenderManager.renderPosX, worldPos.y - RenderManager.renderPosY, worldPos.z - RenderManager.renderPosZ);
	}
	
	public double getRenderX() {
        return worldPos.x - RenderManager.renderPosX;
    }
    
    public double getRenderY() {
        return worldPos.y - RenderManager.renderPosY;
    }
    
    public double getRenderZ() {
        return worldPos.z - RenderManager.renderPosZ;
    }
}
