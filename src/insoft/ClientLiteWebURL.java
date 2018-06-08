package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class ClientLiteWebURL {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		if (args.length == 4) {

			// NGFServer KISTI
			// String serverIP = "203.250.231.230"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// NGFServer
			String serverIP = "192.168.2.140"; // args[0];//"192.168.1.127";
			int serverPort = 9100; // Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.29"; //args[0];//"192.168.1.127";
			// int serverPort = 9500; //Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.139"; //args[0];//"192.168.1.127";
			// int serverPort = 9300; //Integer.parseInt(args[1]); //9100;

			// IOMCServer
			// String serverIP =
			// "127.0.0.1";//137";		//args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

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

				String command = "GET_WATCH";

				IHandler handler = manager.getHandler(command);

				Message requestMsg = handler.requestMessage();
				Message msgResponse = conn.send(requestMsg);
				
				Vector<Message> vEntries = msgResponse.getVector("entries");

				for (Message msgEntry : vEntries) {
					int srcId = msgEntry.getInteger("source_id");
					int watchId = msgEntry.getInteger("watch_id");

					if (srcId == watchId) {
						System.out.println("Found config id :" + msgEntry.getInteger("owner_id") + " / watch id : " + watchId);
					}
				}
				System.out.println("");

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
