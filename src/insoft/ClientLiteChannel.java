package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ClientLiteChannel {

	public static void main(String[] args) throws Exception {

		if (args.length == 4) {
			String serverIP = "192.168.182.56"; // args[0];//"192.168.1.127";
			int serverPort = 9100; // Integer.parseInt(args[1]); //9100;
			String id = "root"; // args[2];
			String pw = "root"; // args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			ArrayList<Message> ltChannelData = new ArrayList<Message>();
			fileLoad(ltChannelData);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				int size = ltChannelData.size();
				int count = 0;
				while (size > count) {
					String command = "SET_CHANNEL";

					if ("".compareTo(command) == 0)
						continue;

					if ("list".compareTo(command) == 0)
						manager.list();
					else if ("exit".compareTo(command) == 0)
						System.exit(1);
					else {
						IHandler handler = manager.getHandler(command);

						if (handler == null)
							System.out.println("Not find handler " + command);
						else {
							handler.setPrevMessage(ltChannelData.get(count++));
							Message msgRequest = handler.requestMessage();
							conn.send(msgRequest);
						}
					}
					System.out.println("");

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				System.exit(1);
			}

		} else {
			System.out.println("USAGE: ClientLite [server ip] [port] [id] [pw]");
			System.exit(1);
		}

		System.out.println("####### Set Channel End ########");
	}

	private static void fileLoad(ArrayList<Message> ltChannelData) {

		String fileName = "agent.txt";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

			String tmp = "";
			while ((tmp = br.readLine()) != null) {

				if (tmp.startsWith("#") || tmp.trim().length() == 0)
					continue;

				StringTokenizer st = new StringTokenizer(tmp, ",");

				Message msg = new Message();

				msg.setString("name", st.nextToken().trim());
				msg.setString("ip", st.nextToken().trim());
				msg.setInteger("port", Integer.parseInt(st.nextToken().trim()));

				ltChannelData.add(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
