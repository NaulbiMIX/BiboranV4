package hack.rawfish2d.client.WDL;

public class WDLSaveAsync implements Runnable
{
	public void run()
	{
		WDL.saveEverything();
		WDL.saving = false;
		WDL.onSaveComplete();
	}
}
