package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import hack.rawfish2d.client.Client;

public class Packet15Place extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;

    /** The offset to use for block/item placement. */
    public int direction;
    public ItemStack itemStack;

    /** The offset from xPosition where the actual click took place */
    public float xOffset;

    /** The offset from yPosition where the actual click took place */
    public float yOffset;

    /** The offset from zPosition where the actual click took place */
    public float zOffset;


    public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, float par6, float par7, float par8)
    {
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
        this.direction = par4;
        this.itemStack = par5ItemStack != null ? par5ItemStack.copy() : null;
        this.xOffset = par6;
        this.yOffset = par7;
        this.zOffset = par8;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.read();
        this.zPosition = par1DataInputStream.readInt();
        this.direction = par1DataInputStream.read();
        this.itemStack = readItemStack(par1DataInputStream);
        this.xOffset = (float)par1DataInputStream.read() / 16.0F;
        this.yOffset = (float)par1DataInputStream.read() / 16.0F;
        this.zOffset = (float)par1DataInputStream.read() / 16.0F;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream dos) throws IOException
    {/*
    	//dos.writeByte(15);
        dos.writeInt(ThreadLocalRandom.current().nextInt(1000));
        dos.writeByte(ThreadLocalRandom.current().nextInt(128));
        dos.writeInt(ThreadLocalRandom.current().nextInt(1000));
        dos.writeByte(ThreadLocalRandom.current().nextInt(4));
        dos.writeShort(327);
        dos.writeByte(1);
        dos.writeShort(0);
        dos.writeShort(Client.nbt.getDataLength());
        dos.write(Client.nbt.getData());
        dos.writeByte(0);
        dos.writeByte(0);
        dos.writeByte(0);
        */
    	
        dos.writeInt(this.xPosition);
        dos.write(this.yPosition);
        dos.writeInt(this.zPosition);
        dos.write(this.direction);
        writeItemStack(this.itemStack, dos);
        
        //ultrakek
//        if(Client.nbt != null) {
//        	try {
//				Client.nbt.init(300);
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	par1DataOutputStream.writeShort(Client.nbt.getDataLength());
//        	par1DataOutputStream.write(Client.nbt.getData());
//        }
        
        dos.write((int)(this.xOffset * 16.0F));
        dos.write((int)(this.yOffset * 16.0F));
        dos.write((int)(this.zOffset * 16.0F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlace(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 19;
    }

    public int getXPosition()
    {
        return this.xPosition;
    }

    public int getYPosition()
    {
        return this.yPosition;
    }

    public int getZPosition()
    {
        return this.zPosition;
    }

    public int getDirection()
    {
        return this.direction;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    /**
     * Returns the offset from xPosition where the actual click took place
     */
    public float getXOffset()
    {
        return this.xOffset;
    }

    /**
     * Returns the offset from yPosition where the actual click took place
     */
    public float getYOffset()
    {
        return this.yOffset;
    }

    /**
     * Returns the offset from zPosition where the actual click took place
     */
    public float getZOffset()
    {
        return this.zOffset;
    }
}
