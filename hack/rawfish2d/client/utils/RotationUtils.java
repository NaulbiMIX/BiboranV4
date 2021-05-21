package hack.rawfish2d.client.utils;

import hack.rawfish2d.client.Client;
import net.minecraft.src.MathHelper;

public class RotationUtils {
    private float pitch;
    private float yaw;
    private float oldPitch;
    private float oldYaw;

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getOldYaw() {
        return this.oldYaw;
    }

    public float getOldPitch() {
        return this.oldPitch;
    }

    public void setPitch(float var1) {
        this.pitch = var1;
    }

    public void setYaw(float var1) {
        this.yaw = var1;
    }

    public void setOldPitch(float var1) {
        this.oldPitch = var1;
    }

    public void setOldYaw(float var1) {
        this.oldYaw = var1;
    }

    public void preUpdate() {
        this.yaw = Client.getInstance().mc.thePlayer.rotationYaw;
        this.pitch = Client.getInstance().mc.thePlayer.rotationPitch;
    }

    public void postUpdate() {
        this.oldPitch = this.pitch;
        this.oldYaw = this.yaw;
    }

    public static Object[] updateRotation(float var0, float var1, int var2) {
        float var3 = MathHelper.wrapAngleTo180_float(var1 - var0);
        boolean var4 = true;
        if (var3 > (float)var2) {
            var3 = var2;
            var4 = false;
        }
        if (var3 < (float)(- var2)) {
            var3 = - var2;
            var4 = false;
        }
        return new Object[]{Float.valueOf(var0 + var3), var4};
    }
}
