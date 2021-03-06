package net.minecraft.src;

import java.util.Calendar;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hack.rawfish2d.client.ModRender.ChestESP;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    /** The normal small chest model. */
    private ModelChest chestModel = new ModelChest();

    /** The large double chest model. */
    private ModelChest largeChestModel = new ModelLargeChest();

    /** If true, chests will be rendered with the Christmas present textures. */
    private boolean isChristmas;

    public TileEntityChestRenderer()
    {
        Calendar var1 = Calendar.getInstance();

        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26)
        {
            this.isChristmas = true;
        }
    }

    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityChestAt(TileEntityChest par1TileEntityChest, double par2, double par4, double par6, float par8)
    {
        int var9;

        if (!par1TileEntityChest.func_70309_m())
        {
            var9 = 0;
        }
        else
        {
            Block var10 = par1TileEntityChest.getBlockType();
            var9 = par1TileEntityChest.getBlockMetadata();

            if (var10 instanceof BlockChest && var9 == 0)
            {
                ((BlockChest)var10).unifyAdjacentChests(par1TileEntityChest.getWorldObj(), par1TileEntityChest.xCoord, par1TileEntityChest.yCoord, par1TileEntityChest.zCoord);
                var9 = par1TileEntityChest.getBlockMetadata();
            }

            par1TileEntityChest.checkForAdjacentChests();
        }

        if (par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null)
        {
            ModelChest var14;

            if (par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPosition == null)
            {
                var14 = this.chestModel;

                if (par1TileEntityChest.func_98041_l() == 1)
                {
                    this.bindTextureByName("/item/chests/trap_small.png");
                }
                else if (this.isChristmas)
                {
                    this.bindTextureByName("/item/xmaschest.png");
                }
                else
                {
                    this.bindTextureByName("/item/chest.png");
                }
            }
            else
            {
                var14 = this.largeChestModel;

                if (par1TileEntityChest.func_98041_l() == 1)
                {
                    this.bindTextureByName("/item/chests/trap_large.png");
                }
                else if (this.isChristmas)
                {
                    this.bindTextureByName("/item/largexmaschest.png");
                }
                else
                {
                    this.bindTextureByName("/item/largechest.png");
                }
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            
            //ultrakek
            if(!ChestESP.instance.isToggled()){
            //if(!Client.getManager().getMod("chestesp").isEnabled() && ChestESP.mode.getStringValue().equals("Outline")){
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short var11 = 0;

            if (var9 == 2)
            {
                var11 = 180;
            }

            if (var9 == 3)
            {
                var11 = 0;
            }

            if (var9 == 4)
            {
                var11 = 90;
            }

            if (var9 == 5)
            {
                var11 = -90;
            }

            if (var9 == 2 && par1TileEntityChest.adjacentChestXPos != null)
            {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (var9 == 5 && par1TileEntityChest.adjacentChestZPosition != null)
            {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)var11, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float var12 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
            float var13;

            if (par1TileEntityChest.adjacentChestZNeg != null)
            {
                var13 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            if (par1TileEntityChest.adjacentChestXNeg != null)
            {
                var13 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;

                if (var13 > var12)
                {
                    var12 = var13;
                }
            }

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;
            var14.chestLid.rotateAngleX = -(var12 * (float)Math.PI / 2.0F);
            var14.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		//ultrakek
		//if(Client.getManager().getMod("chestesp").isEnabled() && ChestESP.mode.getStringValue().equals("Outline")){
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
			GL11.glColor3f(10F, 0F, 0F);
			this.renderTileEntityChestAt((TileEntityChest)par1TileEntity, par2, par4, par6, par8);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}else{
			this.renderTileEntityChestAt((TileEntityChest)par1TileEntity, par2, par4, par6, par8);
		}
    }
}
