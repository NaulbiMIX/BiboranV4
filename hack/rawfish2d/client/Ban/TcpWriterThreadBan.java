package hack.rawfish2d.client.Ban;

import java.io.IOException;

import hack.rawfish2d.client.Ban.BanClass;
import net.minecraft.src.TcpConnection;

//ULTRA KEK
//this shit wasnot public
public class TcpWriterThreadBan extends Thread
{
	   final BanClass theTcpConnection;

		//ULTRA KEK
		//this shit wasnot public
	    public TcpWriterThreadBan(BanClass par1TcpConnection, String par2Str)
	    {
	        super(par2Str);
	        this.theTcpConnection = par1TcpConnection;
	    }

	    public void run()
	    {
	        TcpConnection.field_74469_b.getAndIncrement();

	        try
	        {
	            while (BanClass.isRunning(this.theTcpConnection))
	            {
	                boolean var1;

	                for (var1 = false; BanClass.sendNetworkPacket(this.theTcpConnection); var1 = true)
	                {
	                    ;
	                }

	                try
	                {
	                    if (var1 && BanClass.getOutputStream(this.theTcpConnection) != null)
	                    {
	                    	BanClass.getOutputStream(this.theTcpConnection).flush();
	                    }
	                }
	                catch (IOException var8)
	                {
	                    if (!BanClass.isTerminating(this.theTcpConnection))
	                    {
	                    	BanClass.sendError(this.theTcpConnection, var8);
	                    }

	                    var8.printStackTrace();
	                }

	                try
	                {
	                    sleep(2L);
	                }
	                catch (InterruptedException var7)
	                {
	                    ;
	                }
	            }
	        }
	        finally
	        {
	            TcpConnection.field_74469_b.getAndDecrement();
	        }
	    }
}
