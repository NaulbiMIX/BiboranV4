package hack.rawfish2d.client.pbots;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.PacketRepeaterAdd;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.modules.ModuleBase;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.pbots.utils.ProxyManager;
import hack.rawfish2d.client.utils.MiscUtils;
import net.minecraft.src.CryptManager;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.ILogAgent;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet252SharedKey;
import net.minecraft.src.Packet253ServerAuthData;
import net.minecraft.src.Packet254ServerPing;
import net.minecraft.src.Packet2ClientProtocol;
import net.minecraft.src.PacketCount;
import net.minecraft.src.TcpConnection;

public class PBotConnection {
	public AtomicInteger field_74471_a;
	public AtomicInteger field_74469_b;
	public Socket networkSocket;
	public Thread writeThread;
	public Thread readThread;
	public volatile DataInputStream inputStream;
	private volatile DataOutputStream outputStream;
	private SecretKey sharedKeyForEncryption;
	boolean isInputBeingDecrypted;
	boolean isOutputEncrypted;
	private String terminationReason;
	private int sendQueueByteLength;
	private boolean isServerTerminating;
	private List list;
	private volatile boolean isRunning;
	private volatile boolean isTerminating;
	private final Object sendQueueLock;
	public String name;
	public String ip;
	public int port;
	public PBotThread pbotth = null;
	public String proxy_addr = null;

	public List readPackets;
	public int field_74490_x;

	public PBotConnection(String nickname, String address) {
		this.sendQueueLock = new Object();
		try {
			init(nickname, address, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PBotConnection(String nickname, String address, PBotThread pbotth) {
		this.sendQueueLock = new Object();
		try {
			init(nickname, address, pbotth);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(String nickname, String address, PBotThread pbotth) throws Exception {
		field_74471_a = new AtomicInteger();
		field_74469_b = new AtomicInteger();

		String[] split = address.split("[:]");
		ip = split[0];
		port = (split.length > 1) ? Integer.parseInt(split[1]) : 25565;

		this.pbotth = pbotth;

		this.name = nickname;
		this.isRunning = true;
		this.isTerminating = false;
		this.list = new ArrayList<Packet>();
		this.isServerTerminating = false;
		this.terminationReason = "";
		this.sendQueueByteLength = 0;
		this.isInputBeingDecrypted = false;
		this.isOutputEncrypted = false;
		this.sharedKeyForEncryption = null;
		this.field_74490_x = 0;
		this.readPackets = Collections.synchronizedList(new ArrayList());

		if (ProxyManager.proxy_enabled) {
			proxy_addr = ProxyManager.getNextAddress(this);
		} else {
			proxy_addr = null;
		}
		
		boolean socks4enabled = true;

		if (proxy_addr != null) {
			split = proxy_addr.split("[:]");
			String proxy_ip = split[0];
			int proxy_port = Integer.parseInt(split[1]);

			SocketAddress addr = new InetSocketAddress(proxy_ip, proxy_port);
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
			this.networkSocket = new Socket(proxy);
			
			//socks4 enable
			ProxyManager.enableSocks4(networkSocket);

		} else
			this.networkSocket = new Socket();

		this.networkSocket.setSoTimeout(30000);
		this.networkSocket.setTrafficClass(24);
	}

	public void connect() {
		try {
			this.networkSocket.connect(new InetSocketAddress(ip, port));
			this.inputStream = new DataInputStream(networkSocket.getInputStream());
			this.outputStream = new DataOutputStream(new BufferedOutputStream(networkSocket.getOutputStream(), 5120));
		} catch (Exception e) {
			pbotth.sendChatClient(
					"§eBot §8[§a" + pbotth.instance.name + "§8] " + "§c§lError: §f" + e.getLocalizedMessage());
			pbotth.delBot();
		}
		this.readThread = new PBotTcpReaderThread(this, name + " READ THREAD");
		this.writeThread = new PBotTcpWriterThread(this, name + " WRITE THREAD");
		this.readThread.start();
		this.writeThread.start();
		this.addToSendQueue(new Packet2ClientProtocol(61, name, ip, port));
	}

	public void addToSendQueue(Packet packet) {
		if (!this.isServerTerminating) {
			Object sendQueueLock = this.sendQueueLock;
			synchronized (this.sendQueueLock) {
				sendQueueByteLength += packet.getPacketSize() + 1;
				list.add(packet);
			}
		}
	}

	public void closeConnections() {
		this.wakeThreads();
		this.isRunning = false; // new
		this.writeThread = null;
		this.readThread = null;
	}

	public static void Packet_writePacket(Packet par0Packet, DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.write(par0Packet.getPacketId());
		int id = par0Packet.getPacketId();
		par0Packet.writePacketData(par1DataOutputStream);
	}

	private boolean sendPacket() {
		boolean b = false;
		try {
			if (list != null) {
				if (list.size() > 0) {
					Packet var2 = ((Packet) list.get(0));
					if (var2 != null) {
						if ((!list.isEmpty() && System.currentTimeMillis() - var2.creationTimeMillis >= 0)) {
							Packet packet = func_74460_a(false);
							if (packet != null) {
								Packet_writePacket(packet, outputStream);

								if (packet instanceof Packet252SharedKey && !isOutputEncrypted) {
									sharedKeyForEncryption = ((Packet252SharedKey) packet).getSharedKey();
									encryptOutputStream();
								}
								b = true;
							}
						}
					}
				}
			}
			return b;
		} catch (Exception ex) {
			if (!isTerminating) {
				onNetworkError(ex);
			}
			return false;
		}
	}

	private Packet func_74460_a(final boolean b) {
		Packet packet = null;
		final List list = this.list;
		final Object sendQueueLock = this.sendQueueLock;
		synchronized (this.sendQueueLock) {
			while (!list.isEmpty() && packet == null) {
				packet = (Packet) list.remove(0);
				this.sendQueueByteLength -= packet.getPacketSize() + 1;
			}
			// monitorexit(this.sendQueueLock)
			return packet;
		}
	}

	public void wakeThreads() {
		if (this.readThread != null) {
			this.readThread.interrupt();
		}
		if (this.writeThread != null) {
			this.writeThread.interrupt();
		}
		if (this.networkSocket != null) {
			try {
				this.networkSocket.close();
			} catch (IOException e) {
				MiscUtils.sendChatClient("§c§lSocket close error!");
				e.printStackTrace();
			}
		}
	}

	public static Packet Packet_getNewPacket(ILogAgent par0ILogAgent, int par1) {
		try {
			Class var2 = (Class) Packet.packetIdToClassMap.lookup(par1);
			return var2 == null ? null : (Packet) var2.newInstance();
		} catch (Exception var3) {
			var3.printStackTrace();
			par0ILogAgent.logSevere("Skipping packet with id " + par1);
			return null;
		}
	}

	public static Packet Packet_readPacket(ILogAgent par0ILogAgent, DataInputStream par1DataInputStream, boolean par2,
			Socket par3Socket) throws IOException {
		if (par3Socket.isClosed())
			return null;

		boolean var4 = false;
		Packet var5 = null;
		int var6 = par3Socket.getSoTimeout();
		int var9;

		try {
			var9 = par1DataInputStream.read();

			if (var9 == -1) {
				return null;
			}

			if (par2 && !Packet.serverPacketIdList.contains(Integer.valueOf(var9))
					|| !par2 && !Packet.clientPacketIdList.contains(Integer.valueOf(var9))) {
				throw new IOException("Bad packet id " + var9);
			}

			var5 = Packet_getNewPacket(par0ILogAgent, var9);

			if (var5 == null) {
				throw new IOException("Bad packet id " + var9);
			}

			if (var5 instanceof Packet254ServerPing) {
				par3Socket.setSoTimeout(1500);
			}

			var5.readPacketData(par1DataInputStream);
		} catch (EOFException var8) {
			par0ILogAgent.logSevere("Reached end of stream");
			return null;
		}

		par3Socket.setSoTimeout(var6);
		return var5;
	}

	private boolean readPacket() {
		boolean b = false;
		try {
			Packet packet = Packet_readPacket(Client.getInstance().mc.getLogAgent(), this.inputStream, false,
					this.networkSocket);
			if (packet != null) {
				if (packet instanceof Packet252SharedKey && !this.isInputBeingDecrypted) {
					this.decryptInputStream();
					this.addToSendQueue(new Packet205ClientCommand(0));
					// i am not sure
					// this.addToSendQueue(new Packet204ClientInfo("ru_RU", 1, 1, true, 0, false));
				}
				if (!this.isServerTerminating) {
					if (packet instanceof Packet253ServerAuthData) {
						Packet253ServerAuthData packet253ServerAuthData = (Packet253ServerAuthData) packet;
						packet253ServerAuthData.getServerId().trim();
						this.addToSendQueue(new Packet252SharedKey(CryptManager.createNewSharedKey(),
								packet253ServerAuthData.getPublicKey(), packet253ServerAuthData.getVerifyToken()));
					}

					/*
					 * if (packet.canProcessAsync()) { field_74490_x = 0; if(packet.getPacketId() ==
					 * 51 || packet.getPacketId() == 56) System.out.println("1. Async: " +
					 * packet.toString());
					 * 
					 * pbotth.readPacket(packet); } else { if(packet.getPacketId() == 51 ||
					 * packet.getPacketId() == 56) System.out.println("2. Sync: " +
					 * packet.toString());
					 * 
					 * this.readPackets.add(packet); }
					 */

					if (packet.getPacketId() == 51 || packet.getPacketId() == 56) {
						this.readPackets.add(packet);
					} else {
						field_74490_x = 0;
						pbotth.readPacket(packet);
					}
				}
				b = true;
			} else {
				this.networkShutdown("disconnect.endOfStream");
			}
			return b;
		} catch (Exception ex) {
			if (!this.isTerminating) {
				this.onNetworkError(ex);
			}
			return false;
		}
	}

	public void processReadPackets() {
		if (this.sendQueueByteLength > 2097152) {
			this.networkShutdown("disconnect.overflow");
		}

		if (this.readPackets.isEmpty()) {
			if (this.field_74490_x++ == 1200) {
				this.networkShutdown("disconnect.timeout");
			}
		} else {
			this.field_74490_x = 0;
		}

		int var1 = 1000;

		while (!this.readPackets.isEmpty() && var1-- >= 0) {
			Packet var2 = (Packet) this.readPackets.remove(0);
			// var2.processPacket(this.theNetHandler);
			pbotth.readPacket(var2);
		}

		// this.wakeThreads();

		if (this.isTerminating && this.readPackets.isEmpty()) {
			// this.theNetHandler.handleErrorMessage(this.terminationReason,
			// this.field_74480_w);
			this.pbotth.disconnect();
		}
	}

	private void onNetworkError(final Exception ex) {
		ex.printStackTrace();
		this.networkShutdown("disconnect.genericReason");
	}

	public void networkShutdown(final String terminationReason) {
		if (this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = terminationReason;
			this.isRunning = false;
			try {
				this.inputStream.close();
			} catch (Throwable t) {
			}
			try {
				this.outputStream.close();
			} catch (Throwable t2) {
			}
			try {
				this.networkSocket.close();
			} catch (Throwable t3) {
			}
			this.inputStream = null;
			this.outputStream = null;
			this.networkSocket = null;
		}
	}

	public void serverShutdown() {
		if (!this.isServerTerminating) {
			this.wakeThreads();
			this.isServerTerminating = true;
			// this.readThread.interrupt();
		}
	}

	private void decryptInputStream() throws IOException {
		this.isInputBeingDecrypted = true;
		this.inputStream = new DataInputStream(
				CryptManager.decryptInputStream(this.sharedKeyForEncryption, this.networkSocket.getInputStream()));
	}

	private void encryptOutputStream() throws IOException {
		this.outputStream.flush();
		this.isOutputEncrypted = true;
		this.outputStream = new DataOutputStream(new BufferedOutputStream(
				CryptManager.encryptOuputStream(this.sharedKeyForEncryption, this.networkSocket.getOutputStream()),
				5120));
	}

	public boolean isRunning() {
		return isRunning;
	}

	public boolean isServerTerminating() {
		return isServerTerminating;
	}

	public boolean readNetworkPacket() {
		return readPacket();
	}

	public boolean sendNetworkPacket() {
		return sendPacket();
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public boolean isTerminating() {
		return isTerminating;
	}

	public void sendError(final Exception ex) {
		onNetworkError(ex);
	}

	public Thread getReadThread() {
		return readThread;
	}

	public Thread getWriteThread() {
		return writeThread;
	}
}
