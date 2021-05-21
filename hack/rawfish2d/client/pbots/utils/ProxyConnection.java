package hack.rawfish2d.client.pbots.utils;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.pbots.PBotConnection;

public class ProxyConnection {
	public String address;
	public List<PBotConnection> connections_list = new ArrayList<PBotConnection>();
	public boolean ok = true;
	
	public ProxyConnection(String address) {
		this.address = address;
		this.ok = false;
	}
	
	public boolean isOk() {
		for(PBotConnection bot : connections_list) {
			if(bot.pbotth != null) {
				if(bot.pbotth.ready && bot.pbotth.enabled) {
					ok = true;
					break;
				}
				else
					ok = false;
			}
			else
				ok = false;
		}
		return ok;
	}
	
	public void addConnection(PBotConnection connection) {
		if(connections_list.size() < ProxyManager.connections_per_proxy)
			connections_list.add(connection);
	}
	
	public void clear() {
		this.connections_list.clear();
	}
}
