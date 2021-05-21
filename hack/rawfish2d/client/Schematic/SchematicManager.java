package hack.rawfish2d.client.Schematic;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.utils.Vector3i;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;

public class SchematicManager {
	//https://www.spigotmc.org/threads/load-schematic-file-and-paste-at-custom-location.167277/
	
	private Schematic sc = null;
	public float hrotation = 0f;
	public float vrotation = 0f;
	
	public double origin_x = 0;
	public double origin_y = 0;
	public double origin_z = 0;
	
	public List<BlockData> list = new ArrayList<BlockData>();
	//public BlockData [][][]list2;
	
	public SchematicManager() {}
	
	public boolean isOk() {
		if(sc == null) 
			return false;
		else
			return true;
	}
	
	public void rotate(float angle) {
		if(hrotation == angle) {
			return;
		}

		while(hrotation != angle) {
			rotate();
		}
	}
	
	private void rotate() {
		hrotation += 90f;
		vrotation = 0f;
		
		if(hrotation > 360) {
			hrotation = 0f;
		}
		
		if(vrotation > 360) {
			vrotation = 0f;
		}
		
		for(BlockData bd : list) {
			int tx = bd.pos.x;
			int tz = bd.pos.z;
			bd.pos.x = tz;
			bd.pos.z = -tx;
			
			bd.worldPos.x = (int) (bd.pos.x + origin_x);
			bd.worldPos.y = (int) (bd.pos.y + origin_y);
			bd.worldPos.z = (int) (bd.pos.z + origin_z);
		}
	}
	
	public int getXSize() {
		return sc.width;
	}
	
	public int getYSize() {
		return sc.height;
	}
	
	public int getZSize() {
		return sc.length;
	}
	
	public BlockData getBlockWorld(int x, int y, int z) {
		return getBlock((int)(x - origin_x), (int)(y - origin_y), (int)(z - origin_z));
	}
	
	public BlockData getBlock(int x, int y, int z) {
		BlockData bd = new BlockData();
		
		//x -> z -> y

		//sc.width = x
		//sc.height = y
		//sc.length = z
		int index = x + (z * sc.width) + (y * sc.length * sc.width);
		if(index >= sc.height * sc.width * sc.length || index < 0)
			return null;
		
		bd.data = sc.data[index];
		bd.id = sc.blocks[index];
		bd.pos = new Vector3i(x, y, z);
		bd.worldPos = new Vector3i(
				(int) (x + origin_x),
				(int) (y + origin_y),
				(int) (z + origin_z));
		return bd;
	}
	
	public void load(String name) {
		loadSchematic(name);
		if(sc != null) {
			
			/*
			list2 = new BlockData[sc.width][sc.height][sc.length];
			
			for(int y = 0; y < sc.height; ++y) {
				for(int z = 0; z < sc.length; ++z) {
					for(int x = 0; x < sc.width; ++x) {
						BlockData bd = getBlock(x, y, z);
						if(bd != null)
							list2[x][y][z] = bd;
					}
				}
			}
			*/
			
			
			list.clear();
			for(int y = 0; y < sc.height; ++y) {
				for(int z = 0; z < sc.length; ++z) {
					for(int x = 0; x < sc.width; ++x) {
						BlockData bd = getBlock(x, y, z);
						if(bd != null)
							list.add(bd);
					}
				}
			}
		}
	}
	
	private void loadSchematic(String name) {
		if (!name.endsWith(".schematic"))
			name = name + ".schematic";
		
		File file = new File("schematics/" + name);
		
		if (!file.exists()) {
			sc = null;
			return;
		}
		
		try {
			FileInputStream stream = new FileInputStream(file);
			NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(stream);

			short width = nbtdata.getShort("Width");
			short height = nbtdata.getShort("Height");
			short length = nbtdata.getShort("Length");

			byte[] blocks = nbtdata.getByteArray("Blocks");
			byte[] data = nbtdata.getByteArray("Data");

			byte[] addId = new byte[0];

			if (nbtdata.hasKey("AddBlocks")) {
				addId = nbtdata.getByteArray("AddBlocks");
			}

			short[] sblocks = new short[blocks.length];
			for (int index = 0; index < blocks.length; index++) {
				if ((index >> 1) >= addId.length) {
					sblocks[index] = (short) (blocks[index] & 0xFF);
				} else {
					if ((index & 1) == 0) {
						sblocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blocks[index] & 0xFF));
					} else {
						sblocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blocks[index] & 0xFF));
					}
				}
			}

			stream.close();
			sc = new Schematic(name, sblocks, data, width, length, height);
			return;
		} catch (Exception e) {
			sc = null;
			e.printStackTrace();
		}
		
		sc = null;
		return;
	}
}

