package hack.rawfish2d.client.utils;

import net.minecraft.src.RenderManager;

public class Coordinates {
    private int x;
    private int y;
    private int z;
    
    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Vector3d getRenderPos() {
		return new Vector3d(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
	}
    
    public double getRenderX() {
        return this.getX() - RenderManager.renderPosX;
    }
    
    public double getRenderY() {
        return this.getY() - RenderManager.renderPosY;
    }
    
    public double getRenderZ() {
        return this.getZ() - RenderManager.renderPosZ;
    }
}