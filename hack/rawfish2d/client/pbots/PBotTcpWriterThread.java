package hack.rawfish2d.client.pbots;

import java.io.IOException;

import hack.rawfish2d.client.Ban.BanClass;
import net.minecraft.src.TcpConnection;

//ULTRA KEK
//this shit wasnot public
public class PBotTcpWriterThread extends Thread
{
	   PBotConnection theTcpConnection;

		//ULTRA KEK
		//this shit wasnot public
	    public PBotTcpWriterThread(PBotConnection par1TcpConnection, String par2Str)
	    {
	        super(par2Str);
	        this.theTcpConnection = par1TcpConnection;
	    }

	    public void run()
	    {
	    	theTcpConnection.field_74469_b.getAndIncrement();

	        try
	        {
	            while (theTcpConnection.isRunning())
	            {
	                boolean var1;

	                for (var1 = false; theTcpConnection.sendNetworkPacket(); var1 = true)
	                {
	                    ;
	                }

	                try
	                {
	                    if (var1 && theTcpConnection.getOutputStream() != null)
	                    {
	                    	theTcpConnection.getOutputStream().flush();
	                    }
	                }
	                catch (IOException var8)
	                {
	                    if (!theTcpConnection.isTerminating())
	                    {
	                    	theTcpConnection.sendError(var8);
	                    }

	                    var8.printStackTrace();
	                }

	                try
	                {
	                    sleep(4L);
	                }
	                catch (InterruptedException var7)
	                {
	                    ;
	                }
	            }
	        }
	        finally
	        {
	        	theTcpConnection.field_74469_b.getAndDecrement();
	        }
	    }
}
