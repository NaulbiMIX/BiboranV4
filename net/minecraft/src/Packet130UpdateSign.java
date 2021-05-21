package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public String[] signLines;

    public Packet130UpdateSign()
    {
        this.isChunkDataPacket = true;
    }

    public Packet130UpdateSign(int par1, int par2, int par3, String[] par4ArrayOfStr)
    {
        this.isChunkDataPacket = true;
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
        this.signLines = new String[] {par4ArrayOfStr[0], par4ArrayOfStr[1], par4ArrayOfStr[2], par4ArrayOfStr[3]};
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readShort();
        this.zPosition = par1DataInputStream.readInt();
        this.signLines = new String[4];

        for (int var2 = 0; var2 < 4; ++var2)
        {
            this.signLines[var2] = readString(par1DataInputStream, 15);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeShort(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);

        for (int var2 = 0; var2 < 4; ++var2)
        {
        	/*
        	this.signLines[0] = "&aÂåùè â";
        	this.signLines[1] = "&Añóíäóêàõ";
        	this.signLines[2] = "&e===";
        	this.signLines[3] = "&6ÁÅÑÏËÀÒÍÛÅ!";
        	*/
        	/*
        	this.signLines[0] = "&aÁåñïëàòíûõ";
        	this.signLines[1] = "&aÂåùåé";
        	this.signLines[2] = "&cÍÅ ÁÓÄÅÒ";
        	this.signLines[3] = "&6ÈÄÈÒÅ ÍÀÕÓÉ!";
        	*/
        	/*
        	this.signLines[0] = "&aÒóò ñèäèò";
        	this.signLines[1] = "&aÄèìàñ";
        	this.signLines[2] = "&cÃîíäóðàñ";
        	this.signLines[3] = "&6ÏÈÄÎÐÀÑ";
        	*/
        	/*
        	this.signLines[0] = "&cÎñòîðîæíî!";
        	this.signLines[1] = "&açà çåë¸íîé";
        	this.signLines[2] = "&aëèíèåé";
        	this.signLines[3] = "&cÇÎÍÀ ÏÂÏ!";
        	*/
            writeString(this.signLines[var2], par1DataOutputStream);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleUpdateSign(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        int var1 = 0;

        for (int var2 = 0; var2 < 4; ++var2)
        {
            var1 += this.signLines[var2].length();
        }

        return var1;
    }
}
