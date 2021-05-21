package hack.rawfish2d.client.Ban;

import hack.rawfish2d.client.Ban.BanClass;
import net.minecraft.src.TcpConnection;

//ULTRA KEK
//this shit wasnot public
public class TcpReaderThreadBan extends Thread
{
	final BanClass theTcpConnection;

	//ULTRA KEK
	//this shit wasnot public
	public TcpReaderThreadBan(BanClass par1TcpConnection, String par2Str)
	{
		super(par2Str);
		this.theTcpConnection = par1TcpConnection;
	}

	public void run()
	{
		TcpConnection.field_74471_a.getAndIncrement();

		try
		{
			while (BanClass.isRunning(this.theTcpConnection) && !BanClass.isServerTerminating(this.theTcpConnection))
			{
				while (true)
				{
					if (!BanClass.readNetworkPacket(this.theTcpConnection))
					{
						try
						{
							sleep(2L);
						}
						catch (InterruptedException var5)
						{
							;
						}
					}
				}
			}
		}
		finally
		{
			TcpConnection.field_74471_a.getAndDecrement();
		}
	}
}
