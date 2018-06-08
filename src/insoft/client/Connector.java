package insoft.client;

import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

public class Connector extends Thread {

	private SocketChannel channel = null;
	private Selector readableSelector = null;
	private int sessionId = 0;
	private Object sleeper = new Object();
	private long lLastIoTime = System.currentTimeMillis();
	private long lIdleTimeout = 60000;
	private HashMap<Integer, Message> mapResponse = new HashMap<Integer, Message>();

	public Connector(String ip, int port, String id, String pw) throws Exception {
		SocketAddress addr = new InetSocketAddress(ip, port);
		channel = SocketChannel.open(addr);
		channel.configureBlocking(false);
		channel.socket().setSendBufferSize(1000 * 1024);
		channel.socket().setReceiveBufferSize(1000 * 1024);
		channel.socket().setTcpNoDelay(true);
		readableSelector = Selector.open();
		channel.finishConnect();
		channel.register(readableSelector, SelectionKey.OP_READ);

		Message msg = new Message("AUTHENTICATE");
		msg.setString("user_id", id);
		msg.setString("password", pw);
		msg.setString("group_code", "ITSMTS");

		start();
		System.out.println(msg);

		int reqId = write(msg);
		Message msgResponse = read(reqId);

		System.out.println(msgResponse);
		sessionId = msg.getInteger("session_id");
	}

	public void run() {

		String msgName = "";

		while (true) {

			Iterator<SelectionKey> it = null;

			try {
				readableSelector.select(10);
				it = readableSelector.selectedKeys().iterator();
			} catch (Exception e) {

				try {
					synchronized (sleeper) {
						sleeper.wait(5000);
					}
				} catch (Exception e1) {
					// do nothing...
				}

			}

			if (System.currentTimeMillis() - lLastIoTime > lIdleTimeout) {
				Message msgEcho = new Message("ECHO");
				msgEcho.setString("signature", "OpenManager");
				try {
					write(msgEcho);
				} catch (Exception e) {
				}
			}

			if (it == null)
				continue;

			try {
				while (it.hasNext()) {
					SocketChannel currentChannel = null;

					SelectionKey key = it.next();
					if (!key.isValid()) {
						continue;
					}

					currentChannel = (SocketChannel) key.channel();

					if (key.isReadable()) {
						lLastIoTime = System.currentTimeMillis();

						ClientPacket packet = new ClientPacket();
						Message msgReceived = packet.readClientPacket(currentChannel);
						int nRecvReqId = packet.getRequestId();

						if (msgReceived == null)
							throw new Exception("[RSP_FAIL] Read message is null...");

						mapResponse.put(nRecvReqId, msgReceived);

						msgName = msgReceived.getMessageName();
						// System.out.println("[RSP][" + msgName + "][sid:" +
						// sessionId + "][rid:" + nRecvReqId + "] " +
						// msgReceived);
					}

					it.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public Message send(Message msg) throws Exception {

		int reqId = write(msg);
		return read(reqId);
	}

	public int write(Message msg) throws Exception {

		int reqId = Util.getNextRequestId();

		if (sessionId == 0 && !msg.getMessageName().equals("AUTHENTICATE")) {

			while (sessionId == 0) {
				Thread.sleep(1000);
			}
		}

		ClientPacket packet = new ClientPacket(sessionId, reqId);
		packet.writeClientPacket(channel, msg);
		String msgName = msg.getMessageName();

		if (msgName.compareTo("AUTHENTICATE") == 0) {
			System.out.print("Server connection......");
		} else if (msgName.compareTo("ECHO") != 0) {
			// System.out.println("[REQ][" + msgName + "][sid:" + sessionId +
			// "][rid:" + reqId + "] " + msg);
		}

		return reqId;
	}

	public Message read(int reqId) throws Exception {

		Message msgResponse = mapResponse.get(reqId);

		if (msgResponse != null) {
			return msgResponse;
		}

		int waitCount = 1000;
		int count = 0;
		while (true && waitCount > count) {

			msgResponse = mapResponse.get(reqId);

			if (msgResponse != null) {
				return msgResponse;
			}

			Thread.sleep(10);
			count++;

		}

		return msgResponse;

	}

}
