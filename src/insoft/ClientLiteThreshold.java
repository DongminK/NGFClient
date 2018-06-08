package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class ClientLiteThreshold {

	public static void main(String[] args) throws Exception {

		if (args.length == 4) {

			// KISTI 에서 ACCESSLOG임계치 값이 잘못들어간 부분 삭제
			// NGFServer KISTI
			String serverIP = "203.250.231.230"; // args[0];//"192.168.1.127";
			int serverPort = 9100; // Integer.parseInt(args[1]); //9100;

			// NGFServer
			// String serverIP = "192.168.1.137"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// IOMCServer
			// String serverIP =
			// "192.168.1.29";//137";		//args[0];//"192.168.1.127";
			// int serverPort = 9500; //Integer.parseInt(args[1]); //9100;

			String id = "root"; // args[2];
			String pw = "root"; // args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				IHandler handler = manager.getHandler("GET_WATCH");
				Message msgResponse = null;

				handler.setPrevMessage(msgResponse);
				Message msgRequest = handler.requestMessage();
				msgResponse = conn.send(msgRequest);

				Vector<Message> vWatchMsg = msgResponse.getVector("entries");

				for (Message watchMsg : vWatchMsg) {

					handler = manager.getHandler("GET_THRESHOLD");
					handler.setPrevMessage(watchMsg);
					msgRequest = handler.requestMessage();
					Message prevMsg = conn.send(msgRequest);

					prevMsg.setInteger("watch_id", watchMsg.getInteger("watch_id"));
					prevMsg.setString("watch_type", watchMsg.getString("watch_type"));

					handler = manager.getHandler("SET_THRESHOLD");
					handler.setPrevMessage(prevMsg);
					msgRequest = handler.requestMessage();

					if (msgRequest == null)
						continue;

					System.out.println(msgRequest);

					// conn.write(requestMsg);
					// conn.read();

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
