package hack.rawfish2d.client.Ban;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;

import hack.rawfish2d.client.Client;
import net.minecraft.src.CryptManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet205ClientCommand;
import net.minecraft.src.Packet252SharedKey;
import net.minecraft.src.Packet253ServerAuthData;
import net.minecraft.src.Packet2ClientProtocol;
import net.minecraft.src.TcpConnection;

public class BanClass {
	public static AtomicInteger atom1;
	public static AtomicInteger atom2;
	public Socket networkSocket;
	public Thread writeThread;
	public Thread readThread;
	public volatile DataInputStream inputStream;
	private volatile DataOutputStream outputStream;
	private int ivar1;
	private SecretKey sharedKeyForEncryption;
	boolean isInputBeingDecrypted;
	boolean bvar2;
	private String terminationReason;
	private int ivar2;
	private int sendQueueByteLength;
	private boolean isServerTerminating;
	private List list;
	private volatile boolean isRunning;
	private volatile boolean isTerminating;
	public int ivar4;
	private final Object sendQueueLock;
	public boolean bvar6;
	public String nickname;
	
	static {
		BanClass.atom1 = new AtomicInteger();
		BanClass.atom2 = new AtomicInteger();
	}
	
	public BanClass(String address, String nickname) throws IOException {
		String[] split = address.split("[:]");
		String ip = split[0];
		int port = (split.length > 1) ? Integer.parseInt(split[1]) : 25565;
		Socket networkSocket = new Socket(ip, port);
		
		this.nickname = nickname;
		this.sendQueueLock = new Object();
		this.isRunning = true;
		this.isTerminating = false;
		this.list = new ArrayList<Packet>();
		this.isServerTerminating = false;
		this.terminationReason = "";
		this.ivar2 = 0;
		this.ivar4 = 0;
		this.sendQueueByteLength = 0;
		this.isInputBeingDecrypted = false;
		this.bvar2 = false;
		this.sharedKeyForEncryption = null;
		this.ivar1 = 50;
		this.networkSocket = networkSocket;
		try {
			this.networkSocket.setSoTimeout(30000);
			this.networkSocket.setTrafficClass(24);
		}
		catch (SocketException ex) {
			System.err.println(ex.getMessage());
		}
		this.inputStream = new DataInputStream(networkSocket.getInputStream());
		this.outputStream = new DataOutputStream(new BufferedOutputStream(networkSocket.getOutputStream(), 5120));
		this.readThread = new TcpReaderThreadBan(this, String.valueOf(nickname) + " read thread");
		this.writeThread = new TcpWriterThreadBan(this, String.valueOf(nickname) + " write thread");
		this.readThread.start();
		this.writeThread.start();
		this.addToSendQueue(new Packet2ClientProtocol(61, String.valueOf(nickname) + ".", ip, port));
	}
	
	public void addToSendQueue(Packet packet) {
		if (!this.isServerTerminating) {
			Object sendQueueLock = this.sendQueueLock;
			synchronized (this.sendQueueLock) {
				sendQueueByteLength += packet.getPacketSize() + 1;
				list.add(packet);
			}
			// monitorexit(this.sendQueueLock);
		}
	}
	
	public void closeConnections() {
		this.wakeThreads();
		this.writeThread = null;
		this.readThread = null;
	}
	
	private boolean sendPacket() {
		boolean b = false;
		try {
			if (ivar4 == 0 || (!list.isEmpty() && System.currentTimeMillis() - ((Packet)list.get(0)).creationTimeMillis >= ivar4)) {
				Packet packet = func_74460_a(false);
				if (packet != null) {
					Packet.writePacket(packet, outputStream);
					if (packet instanceof Packet252SharedKey && !bvar2) {
						if (!Client.getInstance().mc.thePlayer.sendQueue.isServerHandler()) {
							sharedKeyForEncryption = ((Packet252SharedKey)packet).getSharedKey();
						}
						encryptOutputStream();
					}
					b = true;
				}
			}
			return b;
		}
		catch (Exception ex) {
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
			//this.readThread.destroy(); //new
		}
		if (this.writeThread != null) {
			this.writeThread.interrupt();
			//this.writeThread.destroy(); //new
		}
	}
	
	private boolean readPacket() {
		boolean b = false;
		try {
			Packet packet = Packet.readPacket(Client.getInstance().mc.getLogAgent(), this.inputStream, Client.getInstance().mc.thePlayer.sendQueue.isServerHandler(), this.networkSocket);
			if (packet != null) {
				if (packet instanceof Packet252SharedKey && !this.isInputBeingDecrypted) {
					if (Client.getInstance().mc.thePlayer.sendQueue.isServerHandler()) {
						this.sharedKeyForEncryption = ((Packet252SharedKey)packet).getSharedKey();
					}
					this.decryptInputStream();
					this.addToSendQueue(new Packet205ClientCommand(0));
					this.bvar6 = true;
				}
				if (!this.isServerTerminating) {
					this.ivar2 = 0;
					if (packet instanceof Packet253ServerAuthData) {
						Packet253ServerAuthData packet253ServerAuthData = (Packet253ServerAuthData)packet;
						packet253ServerAuthData.getServerId().trim();
						this.addToSendQueue(new Packet252SharedKey(CryptManager.createNewSharedKey(), packet253ServerAuthData.getPublicKey(), packet253ServerAuthData.getVerifyToken()));
					}
				}
				b = true;
			}
			else {
				this.networkShutdown("disconnect.endOfStream", new Object[0]);
			}
			return b;
		}
		catch (Exception ex) {
			if (!this.isTerminating) {
				this.onNetworkError(ex);
			}
			return false;
		}
	}
	
	private void onNetworkError(final Exception ex) {
		ex.printStackTrace();
		this.networkShutdown("disconnect.genericReason", "Internal exception: " + ex.toString());
	}
	
	public void networkShutdown(final String terminationReason, final Object... array) {
		if (this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = terminationReason;
			this.isRunning = false;
			try {
				this.inputStream.close();
			}
			catch (Throwable t) {}
			try {
				this.outputStream.close();
			}
			catch (Throwable t2) {}
			try {
				this.networkSocket.close();
			}
			catch (Throwable t3) {}
			this.inputStream = null;
			this.outputStream = null;
			this.networkSocket = null;
		}
	}
	
	public void serverShutdown() {
		if (!this.isServerTerminating) {
			this.wakeThreads();
			this.isServerTerminating = true;
			this.readThread.interrupt();
		}
	}
	
	private void decryptInputStream() throws IOException {
		this.isInputBeingDecrypted = true;
		this.inputStream = new DataInputStream(CryptManager.decryptInputStream(this.sharedKeyForEncryption, this.networkSocket.getInputStream()));
	}
	
	private void encryptOutputStream() throws IOException {
		this.outputStream.flush();
		this.bvar2 = true;
		this.outputStream = new DataOutputStream(new BufferedOutputStream(CryptManager.encryptOuputStream(this.sharedKeyForEncryption, this.networkSocket.getOutputStream()), 5120));
	}
	
	public static boolean isRunning(final BanClass banClass) {
		return banClass.isRunning;
	}
	
	public static boolean isServerTerminating(final BanClass banClass) {
		return banClass.isServerTerminating;
	}
	
	public static boolean readNetworkPacket(final BanClass banClass) {
		return banClass.readPacket();
	}
	
	public static boolean sendNetworkPacket(final BanClass banClass) {
		return banClass.sendPacket();
	}
	
	public static DataOutputStream getOutputStream(final BanClass banClass) {
		return banClass.outputStream;
	}
	
	public static boolean isTerminating(final BanClass banClass) {
		return banClass.isTerminating;
	}
	
	public static void sendError(final BanClass banClass, final Exception ex) {
		banClass.onNetworkError(ex);
	}
	
	static Thread getReadThread(final BanClass banClass) {
		return banClass.readThread;
	}
	
	static Thread getWriteThread(final BanClass banClass) {
		return banClass.writeThread;
	}
}
