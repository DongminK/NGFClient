package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SetChannel implements IHandler {

	private Message prevMsg = null;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_CHANNEL";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub

		Message msg = new Message(getName());

		msg.setInteger("operation", 0);

		Message entry = new Message("CHANNEL");

		entry.setInteger("suspend", 1);
		entry.setString("name", prevMsg.getString("name"));
		entry.setInteger("channel_id", -1);
		entry.setInteger("timeout", 10);
		entry.setInteger("channel_type", 50);
		entry.setInteger("pool_size", 3);

		Message property = new Message("Realtime Agent Channel");

		property.setString("agent_ip", prevMsg.getString("ip"));
		property.setInteger("agent_port", channelCheck(prevMsg.getString("ip"), prevMsg.getInteger("port") ));

		entry.setMessage("property", property);

		msg.setMessage("entry", entry);

		return msg;
	}

	private int channelCheck(String ip, int defaultPort) {

		int portList[] = {9977, 9999, 9988};
		int port = -1;

		String exceptionStr = "";
		try {

			boolean isConn = false;
			Socket s = new Socket();
			try {
				SocketAddress addr = new InetSocketAddress(ip, defaultPort);
				s.connect(addr, 1000); // 5 seconds timeout

				if (s.isConnected()) {
					isConn = true;
					port = defaultPort;
				}

			} catch(Exception e) {
				exceptionStr += " / " + e.toString();
			} finally {
				try {
					s.close();
				} catch(Exception e) {}

			}

			if (!isConn) {
				
				for (int i=0; i<portList.length; i++) {
					s = new Socket();
					try {
						SocketAddress addr = new InetSocketAddress(ip, portList[i]);
						s.connect(addr, 1000); // 5 seconds timeout

						if (s.isConnected()) {
							port = portList[i];
							break;
						}

					} catch(Exception e) {
						exceptionStr += " / " + e.toString();
					} finally {
						try {
							s.close();
						} catch(Exception e) {}

					}
				}
			}
		} catch(Exception e) {

		} finally {

			if (port == -1) {
				port = portList[0];
				
				FileWriter fw = null;
				try {
					fw = new FileWriter("AgentFail.txt", true);
					fw.append("Not Connection Agent IP : " + ip + ", default port = " + defaultPort +  exceptionStr + "\n");
					fw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						fw.close();
					} catch (IOException e) {}
				}
				System.out.println("Not Connection Agent IP : " + ip + ", default port = " + defaultPort +  exceptionStr);
			} else {
				System.out.println("Agnet IP : " + ip + ", Port : " + port);
				
			}

		}

		return port;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
	}

}
