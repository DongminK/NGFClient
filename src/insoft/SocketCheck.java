package insoft;

import insoft.openmanager.message.AgentPacket;
import insoft.openmanager.message.ClientPacket;
import insoft.openmanager.message.Message;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SocketCheck {

	public static void main(String[] args) throws Exception {

		String hostIp = "192.168.112.41";
		int hostPort = 9977;
		/*
		Socket s = new Socket();
        SocketAddress addr = new InetSocketAddress(hostIp, hostPort);
        s.connect(addr, 2000); // 5 seconds timeout

		if (s.isConnected()) {

			System.out.println("GOOD");
			s.close();
		} else {
			System.out.println("BAD");
		}
		 */

		SocketChannel channel = null;
		Selector readableSelector = null;
		String reqMsgName = "";

		SocketAddress addr = new InetSocketAddress(hostIp, hostPort);
		channel = SocketChannel.open(addr);
		channel.configureBlocking(false);
		channel.socket().setSendBufferSize(1 * 1024);
		channel.socket().setReceiveBufferSize(1 * 1024);
		channel.socket().setTcpNoDelay(true);
		readableSelector = Selector.open();
		channel.finishConnect();
		channel.register(readableSelector, SelectionKey.OP_READ);

		Message msg = new Message("TEST");

		System.out.println(msg);
		write(channel, msg);
		read(channel, msg.getMessageName());




	}

	public static void write(SocketChannel channel, Message msg) throws Exception
	{
		AgentPacket packet = new AgentPacket(10000, 0);
		packet.writeAgentPacket(channel, msg);

		if (msg.getMessageName().compareTo("AUTHENTICATE") == 0) {
			System.out.print("Server connection......");
		} else if (msg.getMessageName().compareTo("ECHO") != 0) {
			System.out.println("## REQUEST ##");
			//System.out.println(msg);
		}
	}



	public static void read(SocketChannel channel, String reqMsgName) throws Exception 
	{

		AgentPacket packet = new AgentPacket(10000, 0);
		Message msg = null;
		int count = 0;

		do {
			while(count <= 5) {
				try {
					msg = packet.readAgentPacket(channel);
				} catch(Exception e) {
					Thread.sleep(1000);
					count++;
				}
			}
		}while(msg.getMessageName().compareTo(reqMsgName) != 0);

		if (msg.getMessageName().compareTo("AUTHENTICATE") == 0) {
			if (msg.getInteger("return_code") == 1)
				System.out.println("Success");
			else {
				System.out.println("Failed - " + msg.getString("return_msg"));
				System.exit(1);
			}
		} else {
			System.out.println("## RESPONSE ##");
			System.out.println("[" + msg.getInteger("return_code") + "] " + msg.getString("return_msg"));
		}
	}
}
