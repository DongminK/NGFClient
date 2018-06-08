package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class ClientLiteQueryFaultSlice {

	public static void main(String[] args) {

		String serverIP = "192.168.1.29"; // args[0];//"192.168.1.127";
		int serverPort = 9100; // Integer.parseInt(args[1]); //9100;
		String id = "root"; // args[2];
		String pw = "root"; // args[3];

		System.out.println("## IP : " + serverIP);
		System.out.println("## PORT : " + serverPort);

		try {

			Connector conn = new Connector(serverIP, serverPort, id, pw);
			HandlerManager manager = HandlerManager.getInstance();

			int objectID = -1;
			String endDateTime = "";
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

						if (msgResponse != null && msgResponse.getInteger("end_data") == 0) {
							msgResponse.setInteger("object_id", objectID);
							msgResponse.setString("end_datetime", endDateTime);
						} else {
							msgResponse = null;
						}

						handler.setPrevMessage(msgResponse);
						Message msgRequest = handler.requestMessage();

						objectID = msgRequest.getInteger("object_id");
						endDateTime = msgRequest.getString("end_datetime");

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
	}

}
