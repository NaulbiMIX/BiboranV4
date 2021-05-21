package hack.rawfish2d.client.pbots;

import hack.rawfish2d.client.Ban.BanClass;
import net.minecraft.src.TcpConnection;

//ULTRA KEK
//this shit wasnot public
public class PBotTcpReaderThread extends Thread
{
	PBotConnection theTcpConnection;

	//ULTRA KEK
	//this shit wasnot public
	public PBotTcpReaderThread(PBotConnection par1TcpConnection, String par2Str) {
		super(par2Str);
		this.theTcpConnection = par1TcpConnection;
	}

	public void run() {
		theTcpConnection.field_74471_a.getAndIncrement();

		try
		{
			while (theTcpConnection.isRunning() && !theTcpConnection.isServerTerminating())
			{
				//while (true)
				//{
					if (!theTcpConnection.readNetworkPacket())
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
				//}
			}
		}
		finally
		{
			theTcpConnection.field_74471_a.getAndDecrement();
		}
	}
}
