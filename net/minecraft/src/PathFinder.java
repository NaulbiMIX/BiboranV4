package net.minecraft.src;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.utils.MiscUtils;

public class PathFinder
{
    /** Used to find obstacles */
    private IBlockAccess worldMap;

    /** The path being generated */
    private Path path = new Path();

    /** The points in the path */
    private IntHashMap pointMap = new IntHashMap();

    /** Selection of path points to add to the path */
    private PathPoint[] pathOptions = new PathPoint[32];

    /** should the PathFinder go through wodden door blocks */
    private boolean isWoddenDoorAllowed;

    /**
     * should the PathFinder disregard BlockMovement type materials in its path
     */
    private boolean isMovementBlockAllowed;
    private boolean isPathingInWater;

    /** tells the FathFinder to not stop pathing underwater */
    private boolean canEntityDrown;

    public PathFinder(IBlockAccess par1IBlockAccess, boolean par2, boolean par3, boolean par4, boolean par5)
    {
        this.worldMap = par1IBlockAccess;
        this.isWoddenDoorAllowed = par2;
        this.isMovementBlockAllowed = par3;
        this.isPathingInWater = par4;
        this.canEntityDrown = par5;
    }

    /**
     * Creates a path from one entity to another within a minimum distance
     */
    public PathEntity createEntityPathTo(Entity par1Entity, Entity par2Entity, float par3)
    {
        return this.createEntityPathTo(par1Entity, par2Entity.posX, par2Entity.boundingBox.minY, par2Entity.posZ, par3);
    }

    /**
     * Creates a path from an entity to a specified location within a minimum distance
     */
    public PathEntity createEntityPathTo(Entity par1Entity, int par2, int par3, int par4, float par5)
    {
        return this.createEntityPathTo(par1Entity, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), par5);
    }

    /**
     * Internal implementation of creating a path from an entity to a point
     */
    private PathEntity createEntityPathTo(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        this.path.clearPath();
        this.pointMap.clearMap();
        boolean var9 = this.isPathingInWater;
        int var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5D);

        if (this.canEntityDrown && par1Entity.isInWater())
        {
            var10 = (int)par1Entity.boundingBox.minY;

            for (int var11 = this.worldMap.getBlockId(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ)); var11 == Block.waterMoving.blockID || var11 == Block.waterStill.blockID; var11 = this.worldMap.getBlockId(MathHelper.floor_double(par1Entity.posX), var10, MathHelper.floor_double(par1Entity.posZ)))
            {
                ++var10;
            }

            var9 = this.isPathingInWater;
            this.isPathingInWater = false;
        }
        else
        {
            var10 = MathHelper.floor_double(par1Entity.boundingBox.minY + 0.5D);
        }

        PathPoint var15 = this.openPoint(MathHelper.floor_double(par1Entity.boundingBox.minX), var10, MathHelper.floor_double(par1Entity.boundingBox.minZ));
        PathPoint var12 = this.openPoint(MathHelper.floor_double(par2 - (double)(par1Entity.width / 2.0F)), MathHelper.floor_double(par4), MathHelper.floor_double(par6 - (double)(par1Entity.width / 2.0F)));
        PathPoint var13 = new PathPoint(MathHelper.floor_float(par1Entity.width + 1.0F), MathHelper.floor_float(par1Entity.height + 1.0F), MathHelper.floor_float(par1Entity.width + 1.0F));
        PathEntity var14 = this.addToPath(par1Entity, var15, var12, var13, par8);
        this.isPathingInWater = var9;
        return var14;
    }

    /**
     * Adds a path from start to end and returns the whole path (args: unused, start, end, unused, maxDistance)
     */
    private PathEntity addToPath(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
    {
        par2PathPoint.totalPathDistance = 0.0F;
        par2PathPoint.distanceToNext = par2PathPoint.func_75832_b(par3PathPoint);
        par2PathPoint.distanceToTarget = par2PathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(par2PathPoint);
        PathPoint var6 = par2PathPoint;

        while (!this.path.isPathEmpty())
        {
            PathPoint var7 = this.path.dequeue();

            if (var7.equals(par3PathPoint))
            {
                return this.createEntityPath(par2PathPoint, par3PathPoint);
            }

            if (var7.func_75832_b(par3PathPoint) < var6.func_75832_b(par3PathPoint))
            {
                var6 = var7;
            }

            var7.isFirst = true;
            int var8 = this.findPathOptions(par1Entity, var7, par4PathPoint, par3PathPoint, par5);

            for (int var9 = 0; var9 < var8; ++var9)
            {
                PathPoint var10 = this.pathOptions[var9];
                float var11 = var7.totalPathDistance + var7.func_75832_b(var10);

                if (!var10.isAssigned() || var11 < var10.totalPathDistance)
                {
                    var10.previous = var7;
                    var10.totalPathDistance = var11;
                    var10.distanceToNext = var10.func_75832_b(par3PathPoint);

                    if (var10.isAssigned())
                    {
                        this.path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
                    }
                    else
                    {
                        var10.distanceToTarget = var10.totalPathDistance + var10.distanceToNext;
                        this.path.addPoint(var10);
                    }
                }
            }
        }

        if (var6 == par2PathPoint)
        {
            return null;
        }
        else
        {
            return this.createEntityPath(par2PathPoint, var6);
        }
    }

    /**
     * populates pathOptions with available points and returns the number of options found (args: unused1, currentPoint,
     * unused2, targetPoint, maxDistance)
     */
    /*
	private int findPathOptions(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
	{
		int var6 = 0;
		byte var7 = 0;
	
		if (this.getVerticalOffset(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord + 1, par2PathPoint.zCoord, par3PathPoint) == 1)
		{
			var7 = 1;
		}
	
		PathPoint var8 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord + 1, par3PathPoint, var7);
		PathPoint var9 = this.getSafePoint(par1Entity, par2PathPoint.xCoord - 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
		PathPoint var10 = this.getSafePoint(par1Entity, par2PathPoint.xCoord + 1, par2PathPoint.yCoord, par2PathPoint.zCoord, par3PathPoint, var7);
		PathPoint var11 = this.getSafePoint(par1Entity, par2PathPoint.xCoord, par2PathPoint.yCoord, par2PathPoint.zCoord - 1, par3PathPoint, var7);
	
		if (var8 != null && !var8.isFirst && var8.distanceTo(par4PathPoint) < par5)
		{
			this.pathOptions[var6++] = var8;
		}
	
		if (var9 != null && !var9.isFirst && var9.distanceTo(par4PathPoint) < par5)
		{
			this.pathOptions[var6++] = var9;
		}
	
		if (var10 != null && !var10.isFirst && var10.distanceTo(par4PathPoint) < par5)
		{
			this.pathOptions[var6++] = var10;
		}
	
		if (var11 != null && !var11.isFirst && var11.distanceTo(par4PathPoint) < par5)
		{
			this.pathOptions[var6++] = var11;
		}
	
		return var6;
	}
*/
    
    private int findPathOptions(Entity par1Entity, PathPoint par2PathPoint, PathPoint par3PathPoint, PathPoint par4PathPoint, float par5)
    {
    	int index = 0;
		byte var7 = 0;
		
		int x = par2PathPoint.xCoord;
		int y = par2PathPoint.yCoord;
		int z = par2PathPoint.zCoord;
		
		if (this.getVerticalOffset(par1Entity, x, y + 1, z, par3PathPoint) == 1)
		{
			var7 = 1;
		}
		
		//ultrakek
		//if(MiscUtils.getBlockId(x, y + 1, z + 1) == 0 && MiscUtils.getBlockId(x + 1, y + 1, z) == 0 && MiscUtils.getBlockId(x, y + 2, z + 1) == 0 && MiscUtils.getBlockId(x + 1, y + 2, z) == 0) {
			PathPoint pathxFzF = this.getSafePoint(par1Entity, x + 1, y, z + 1, par3PathPoint, var7);
			if (pathxFzF != null && !pathxFzF.isFirst && pathxFzF.distanceTo(par4PathPoint) < par5) {
				this.pathOptions[index++] = pathxFzF;
			}
		//}
		
		//ultrakek
		//if(MiscUtils.getBlockId(x, y + 1, z + 1) == 0 && MiscUtils.getBlockId(x - 1, y + 1, z) == 0 && MiscUtils.getBlockId(x, y + 2, z + 1) == 0 && MiscUtils.getBlockId(x - 1, y + 2, z) == 0) {
			PathPoint pathxBzF = this.getSafePoint(par1Entity, x - 1, y, z + 1, par3PathPoint, var7);
			if (pathxBzF != null && !pathxBzF.isFirst && pathxBzF.distanceTo(par4PathPoint) < par5) {
				this.pathOptions[index++] = pathxBzF;
			}
		//}
		
		//ultrakek
		//if(MiscUtils.getBlockId(x, y + 1, z - 1) == 0 && MiscUtils.getBlockId(x - 1, y + 1, z) == 0 && MiscUtils.getBlockId(x, y + 2, z - 1) == 0 && MiscUtils.getBlockId(x - 1, y + 2, z) == 0) {
			PathPoint pathxBzB = this.getSafePoint(par1Entity, x - 1, y, z - 1, par3PathPoint, var7);
			if (pathxBzB != null && !pathxBzB.isFirst && pathxBzB.distanceTo(par4PathPoint) < par5) {
				this.pathOptions[index++] = pathxBzB;
			}
		//}
		
		//ultrakek
		//if(MiscUtils.getBlockId(x, y + 1, z - 1) == 0 && MiscUtils.getBlockId(x + 1, y + 1, z) == 0 && MiscUtils.getBlockId(x, y + 2, z - 1) == 0 && MiscUtils.getBlockId(x + 1, y + 2, z) == 0) {
			PathPoint pathxFzB = this.getSafePoint(par1Entity, x + 1, y, z - 1, par3PathPoint, var7);
			if (pathxFzB != null && !pathxFzB.isFirst && pathxFzB.distanceTo(par4PathPoint) < par5) {
				this.pathOptions[index++] = pathxFzB;
			}
		//}
		
		
		
		PathPoint pathzF = this.getSafePoint(par1Entity, x, y, z + 1, par3PathPoint, var7);
		if (pathzF != null && !pathzF.isFirst && pathzF.distanceTo(par4PathPoint) < par5) {
			this.pathOptions[index++] = pathzF;
		}
		
		PathPoint pathxB = this.getSafePoint(par1Entity, x - 1, y, z, par3PathPoint, var7);
		if (pathxB != null && !pathxB.isFirst && pathxB.distanceTo(par4PathPoint) < par5) {
			this.pathOptions[index++] = pathxB;
		}
	
		PathPoint pathxF = this.getSafePoint(par1Entity, x + 1, y, z, par3PathPoint, var7);
		if (pathxF != null && !pathxF.isFirst && pathxF.distanceTo(par4PathPoint) < par5) {
			this.pathOptions[index++] = pathxF;
		}
	
		PathPoint pathzB = this.getSafePoint(par1Entity, x, y, z - 1, par3PathPoint, var7);
		if (pathzB != null && !pathzB.isFirst && pathzB.distanceTo(par4PathPoint) < par5) {
			this.pathOptions[index++] = pathzB;
		}
	
		return index;
    }
    
    /**
     * Returns a point that the entity can safely move to
     */
    private PathPoint getSafePoint(Entity par1Entity, int x, int y, int z, PathPoint par5PathPoint, int par6)
    {
        PathPoint point = null;
        int vOff = this.getVerticalOffset(par1Entity, x, y, z, par5PathPoint);
        
        if (vOff == 2)
        {
            return this.openPoint(x, y, z);
        }
        else
        {
            if (vOff == 1)
            {
            	point = this.openPoint(x, y, z);
            }

            //if (point == null && par6 > 0 && vOff != -3 && vOff != -4 && this.getVerticalOffset(par1Entity, x, y + par6, z, par5PathPoint) == 1)
            if (point == null && par6 > 0 && this.getVerticalOffset(par1Entity, x, y + par6, z, par5PathPoint) == 1)
            {
            	point = this.openPoint(x, y + par6, z);
                y += par6;
            }

            if (point != null)
            {
                int fallDistance = 0;
                int climbDistance = 0;

                while (y > 0)
                {
                	climbDistance = this.getVerticalOffset(par1Entity, x, y - 1, z, par5PathPoint);

                    if (this.isPathingInWater && climbDistance == -1)
                    {
                        return null;
                    }

                    if (climbDistance != 1) {
                        break;
                    }
                    
                    //if(par1Entity.onGround)
                    //	break;
                    
                    //if falling distance >= 3
                    
                    //if (fallDistance++ >= par1Entity.func_82143_as()) {
                    if (fallDistance++ > 0) {
                        return null;
                    }
                    
                    --y;

                    if (y > 0)
                    {
                    	point = this.openPoint(x, y, z);
                    }
                }

                //если следующая точка находится на высоте в 2 блока
                if (climbDistance == -2)
                {
                    return null;
                }
            }

            return point;
        }
    }

    /**
     * Returns a mapped point or creates and adds one
     */
    private final PathPoint openPoint(int par1, int par2, int par3)
    {
        int var4 = PathPoint.makeHash(par1, par2, par3);
        PathPoint var5 = (PathPoint)this.pointMap.lookup(var4);

        if (var5 == null)
        {
            var5 = new PathPoint(par1, par2, par3);
            this.pointMap.addKey(var4, var5);
        }

        return var5;
    }

    /**
     * Checks if an entity collides with blocks at a position. Returns 1 if clear, 0 for colliding with any solid block,
     * -1 for water(if avoiding water) but otherwise clear, -2 for lava, -3 for fence, -4 for closed trapdoor, 2 if
     * otherwise clear except for open trapdoor or water(if not avoiding)
     */
    public int getVerticalOffset(Entity par1Entity, int par2, int par3, int par4, PathPoint par5PathPoint)
    {
        return func_82565_a(par1Entity, par2, par3, par4, par5PathPoint, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
    }

    public static int func_82565_a(Entity par0Entity, int par1, int par2, int par3, PathPoint par4PathPoint, boolean par5, boolean par6, boolean par7)
    {
        boolean var8 = false;

        for (int x = par1; x < par1 + par4PathPoint.xCoord; ++x)
        {
            for (int y = par2; y < par2 + par4PathPoint.yCoord; ++y)
            {
                for (int z = par3; z < par3 + par4PathPoint.zCoord; ++z)
                {
                    int id = par0Entity.worldObj.getBlockId(x, y, z);

                    if (id > 0)
                    {
                        if (id == Block.trapdoor.blockID)
                        {
                            var8 = true;
                        }
                        else if (id != Block.waterMoving.blockID && id != Block.waterStill.blockID)
                        {
                            if (!par7 && id == Block.doorWood.blockID)
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            if (par5)
                            {
                                return -1;
                            }

                            var8 = true;
                        }

                        Block block = Block.blocksList[id];
                        int var14 = block.getRenderType();

                        if (par0Entity.worldObj.blockGetRenderType(x, y, z) == 9)
                        {
                            int x2 = MathHelper.floor_double(par0Entity.posX);
                            int y2 = MathHelper.floor_double(par0Entity.posY);
                            int z2 = MathHelper.floor_double(par0Entity.posZ);

                            if (par0Entity.worldObj.blockGetRenderType(x2, y2, z2) != 9 && par0Entity.worldObj.blockGetRenderType(x2, y2 - 1, z2) != 9)
                            {
                                return -3;
                            }
                        }
                        else if (!block.getBlocksMovement(par0Entity.worldObj, x, y, z) && (!par6 || id != Block.doorWood.blockID))
                        {
                            if (var14 == 11 || id == Block.fenceGate.blockID || var14 == 32)
                            {
                                return -3;
                            }

                            if (id == Block.trapdoor.blockID)
                            {
                                return -4;
                            }

                            Material material = block.blockMaterial;

                            if (material != Material.lava)
                            {
                                return 0;
                            }

                            if (!par0Entity.handleLavaMovement())
                            {
                                return -2;
                            }
                        }
                    }
                }
            }
        }

        return var8 ? 2 : 1;
    }

    /**
     * Returns a new PathEntity for a given start and end point
     */
    private PathEntity createEntityPath(PathPoint par1PathPoint, PathPoint par2PathPoint)
    {
        int var3 = 1;
        PathPoint var4;

        for (var4 = par2PathPoint; var4.previous != null; var4 = var4.previous)
        {
            ++var3;
        }

        PathPoint[] var5 = new PathPoint[var3];
        var4 = par2PathPoint;
        --var3;

        for (var5[var3] = par2PathPoint; var4.previous != null; var5[var3] = var4)
        {
            var4 = var4.previous;
            --var3;
        }

        return new PathEntity(var5);
    }
}
