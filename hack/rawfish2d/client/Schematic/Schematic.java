package hack.rawfish2d.client.Schematic;

public class Schematic {
	public String name;
	public short[] blocks;
	public byte[] data;
	public short width;
	public short length;
	public short height;
	
	public Schematic(String name, short[] blocks, byte[] data, short width, short length, short height) {
		this.name = name;
		this.blocks = blocks;
		this.data = data;
		this.width = width;
		this.length = length;
		this.height = height;
	}
}
