package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class ClientLite {

	public static void main(String[] args) throws Exception {

		if (args.length == 4) {

			// NGFServer KISTI
			// String serverIP = "203.250.231.230"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// NGFServer
			// String serverIP = "192.168.2.119"; //args[0];//"192.168.1.127";
			// int serverPort = 9500; //Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.29"; //args[0];//"192.168.1.127";
			// int serverPort = 9500; //Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.139"; //args[0];//"192.168.1.127";
			// int serverPort = 9300; //Integer.parseInt(args[1]); //9100;

			// IOMCServer
			String serverIP = "123.212.42.13";// 137";		//args[0];//"192.168.1.127";
			int serverPort = 6007; // Integer.parseInt(args[1]); //9100;

			// Myon
			// String serverIP = "192.168.1.103"; //args[0];//"192.168.1.127";
			// int serverPort = 7777; //Integer.parseInt(args[1]); //9100;

			String id = "root"; // args[2];
			String pw = "root"; // args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();
				Message msgResponse = null;

				while (true) {
					System.out.print(">> ");
					String command = Util.readCommand();

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
							handler.setPrevMessage(msgResponse);
							Message msgRequest = handler.requestMessage();
							msgResponse = conn.send(msgRequest);
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
	}

}
