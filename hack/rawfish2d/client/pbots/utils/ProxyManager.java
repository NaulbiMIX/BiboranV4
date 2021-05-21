package hack.rawfish2d.client.pbots.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.List;
import hack.rawfish2d.client.ModRender.XRAY;
import hack.rawfish2d.client.pbots.PBotConnection;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;

public class ProxyManager {
	public static int connections_per_proxy = 1;
	public static int proxy_used = 0;
	public static List<ProxyConnection> addr_list = new ArrayList<ProxyConnection>();
	private static List<String> good_proxy = new ArrayList<String>();
	public static boolean proxy_enabled = false;
	
	public static void addGoodProxy(String addr) {
		if(!good_proxy.contains(addr))
			good_proxy.add(addr);
	}
	
	public static void loadProxyList() {
		try {
			File file = new File(ConfigManager.dirname, ConfigManager.proxy);
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			addr_list.clear();
			int dup = 0;
			boolean bdup = false;
			while ((line = br.readLine()) != null) {
				String curLine = line.toLowerCase().trim();
				for(ProxyConnection prc : addr_list) {
					if(prc.address.equals(curLine)) {
						dup++;
						bdup = true;
						break;
					}
				}
				if(bdup) {
					bdup = false;
					continue;
				}
				addr_list.add(new ProxyConnection(curLine));
			}
			MiscUtils.sendChatClient("§cLoaded §d§l" + addr_list.size() + "§c unique proxies. §e'" + ConfigManager.proxy + "'");
			MiscUtils.sendChatClient("§d§l" + dup + "§c proxies are duplicate.");
			MiscUtils.sendChatClient("§d§l" + connections_per_proxy + "§c connections per proxy");
			fstream.close();
			in.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public static void saveProxyList() {
		try {
			ConfigManager.createFile(ConfigManager.dirname + "/" + "proxy_checked_ok.txt");
			
			FileWriter writer = new FileWriter(ConfigManager.dirname + "/" + "proxy_checked_ok.txt", false);
			for(int a = 0; a < good_proxy.size(); ++a) {
				String addr = good_proxy.get(a);
				writer.write(addr + "\r\n");
				writer.flush();
			}
			good_proxy.clear();
			writer.close();
		} catch(IOException ex) {
		    System.out.println(ex.getMessage());
		}
	}
	*/
	public static String getNextAddress(PBotConnection botcon) {
		for(ProxyConnection addr : addr_list) {
			if(addr.connections_list.size() < connections_per_proxy) {
				addr.addConnection(botcon);
				return addr.address;
			}
		}
		return null;
	}
	
	public static int getUsedProxyCount() {
		proxy_used = 0;
		for(ProxyConnection addr : addr_list) {
			if(addr.connections_list.size() > 0) {
				proxy_used++;
			}
		}
		return proxy_used;
	}
	
	public static void clearBotConnections() {
		for(ProxyConnection addr : addr_list) {
			addr.clear();
		}
	}
	
	public static void enableSocks4(Socket socket) {
		Class clazzSocks = socket.getClass();
		Method setSockVersion = null;
		Field sockImplField = null;
		SocketImpl socksimpl = null;
		try {
			sockImplField = clazzSocks.getDeclaredField("impl");
			sockImplField.setAccessible(true);
			socksimpl = (SocketImpl) sockImplField.get(socket);
			Class clazzSocksImpl = socksimpl.getClass();
			setSockVersion = clazzSocksImpl.getDeclaredMethod("setV4");
			setSockVersion.setAccessible(true);
			if (null != setSockVersion) {
				setSockVersion.invoke(socksimpl);
			}
			sockImplField.set(socket, socksimpl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
