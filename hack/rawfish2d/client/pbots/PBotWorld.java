package hack.rawfish2d.client.pbots;

import hack.rawfish2d.client.Client;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderClient;
import net.minecraft.src.CrashReport;
import net.minecraft.src.CrashReportCategory;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ReportedException;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;

public class PBotWorld {
	public IChunkProvider chunkProvider;
	public WorldClient world;
	public PBot bot;
	
	public PBotWorld(PBot bot, WorldClient world) {
		this.bot = bot;
		this.world = world;
		this.chunkProvider = this.world.getChunkProvider();
	}
	
	public Chunk getChunkFromChunkCoords(int par1, int par2)
	{
		return this.chunkProvider.provideChunk(par1, par2);
	}
	
	public Block getBlock(int x, int y, int z) {
		return Block.blocksList[getBlockId(x, y, z)];
	}
	
	public int getBlockIdUnder() {
		return getBlockId((int)bot.pbotth.x, (int)bot.pbotth.y - 1, (int)bot.pbotth.z);
	}
	
	public int getBlockId(double par1, double par2, double par3) {
		return getBlockId((int)par1, (int)par2, (int)par3);
	}
	
	public int getBlockId(int par1, int par2, int par3)
	{
		if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if (par2 < 0)
			{
				return 0;
			}
			else if (par2 >= 256)
			{
				return 0;
			}
			else
			{
				Chunk var4 = null;

				try
				{
					var4 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
					return var4.getBlockID(par1 & 15, par2, par3 & 15);
				}
				catch (Throwable var8)
				{
					CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception getting block type in world");
					CrashReportCategory var7 = var6.makeCategory("Requested block coordinates");
					var7.addCrashSection("Found chunk", Boolean.valueOf(var4 == null));
					var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(par1, par2, par3));
					throw new ReportedException(var6);
				}
			}
		}
		else
		{
			return 0;
		}
		
	}
	
	public int getBlockMetadata(int par1, int par2, int par3)
	{
		if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000)
		{
			if (par2 < 0)
			{
				return 0;
			}
			else if (par2 >= 256)
			{
				return 0;
			}
			else
			{
				Chunk var4 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
				par1 &= 15;
				par3 &= 15;
				return var4.getBlockMetadata(par1, par2, par3);
			}
		}
		else
		{
			return 0;
		}
		
	}
}
