package net.minecraft.src;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMovement.NoSlowdown;

public class BlockSoulSand extends Block
{
    public BlockSoulSand(int par1)
    {
        super(par1, Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double)par2, (double)par3, (double)par4, (double)(par2 + 1), (double)((float)(par3 + 1) - var5), (double)(par4 + 1));
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	/*
    	 * COPY AFTER!!! SEREXA SKOPIRYI POTOM NA DRYGIE MODYLI KAK SPEED (Ladders, Drygoe)
    	 */
    		
    	/*
    	if(Client.getManager().getMod("noslowdown").isEnabled() && (boolean)NoSlowDown.soulsand.getValue()){
    		return;
    	}
    	*/
    	if(NoSlowdown.instance.isToggled())
    		return;
    	
    	par5Entity.motionX *= 0.4D;
    	par5Entity.motionZ *= 0.4D;
    }
}
