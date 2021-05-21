package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hack.rawfish2d.client.ModRender.ChestESP;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    /** The Ender Chest Chest's model. */
    private ModelChest theEnderChestModel = new ModelChest();

    /**
     * Helps to render Ender Chest.
     */
    public void renderEnderChest(TileEntityEnderChest par1TileEntityEnderChest, double par2, double par4, double par6, float par8)
    {
        int var9 = 0;

        if (par1TileEntityEnderChest.func_70309_m())
        {
            var9 = par1TileEntityEnderChest.getBlockMetadata();
        }

        this.bindTextureByName("/item/enderchest.png");
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //ultrakek
        //if(!Client.getManager().getMod("chestesp").isEnabled() && ChestESP.mode.getStringValue().equals("Outline") && ChestESP.chestender){
        
        if(ChestESP.instance.isToggled()) {
            GL11.glColor4f(0.6F, 0.0F, 0.6F, 1.0F);
        }
        
        GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short var10 = 0;

        if (var9 == 2)
        {
            var10 = 180;
        }

        if (var9 == 3)
        {
            var10 = 0;
        }

        if (var9 == 4)
        {
            var10 = 90;
        }

        if (var9 == 5)
        {
            var10 = -90;
        }

        GL11.glRotatef((float)var10, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float var11 = par1TileEntityEnderChest.prevLidAngle + (par1TileEntityEnderChest.lidAngle - par1TileEntityEnderChest.prevLidAngle) * par8;
        var11 = 1.0F - var11;
        var11 = 1.0F - var11 * var11 * var11;
        this.theEnderChestModel.chestLid.rotateAngleX = -(var11 * (float)Math.PI / 2.0F);
        this.theEnderChestModel.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
    	//ultrakek
    	//if(Client.getManager().getMod("chestesp").isEnabled() && ChestESP.mode.getStringValue().equals("Outline") && ChestESP.chestender){
    	if(ChestESP.instance.isToggled()) {
			GL11.glPushMatrix();
			GL11.glPushAttrib((int)-1);
			GL11.glPolygonMode((int)1032, (int)6913);
			GL11.glDisable((int)3553);
			GL11.glDisable((int)2896);
			GL11.glDisable((int)2929);
			GL11.glEnable((int)2848);
			GL11.glEnable((int)3042);
			GL11.glBlendFunc((int)770, (int)32772);
			//GL11.glLineWidth((float)ChestESP.width);
			GL11.glLineWidth((float)2);
			GL11.glColor3f(0.6F, 0F, 0.6F);
			this.renderEnderChest((TileEntityEnderChest)par1TileEntity, par2, par4, par6, par8);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
    	}else{
    		this.renderEnderChest((TileEntityEnderChest)par1TileEntity, par2, par4, par6, par8);
    	}
    }
}
