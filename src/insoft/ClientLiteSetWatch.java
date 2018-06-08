package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class ClientLiteSetWatch {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		if (args.length == 4) {
			String serverIP = args[0];// "192.168.1.127";
			int serverPort = Integer.parseInt(args[1]); // 9100;
			String id = args[2];
			String pw = args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				String command = "GET_WATCH";
				IHandler handler = manager.getHandler(command);
				Message msgResponse = null;

				if (handler == null)
					System.out.println("Not find handler " + command);
				else {

					try {
						Message msgRequest = handler.requestMessage();

						if (msgRequest == null)
							System.out.println("Not Found request msg");
						else {
							msgResponse = conn.send(msgRequest);
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
					}

				}

				Vector<Message> vEntries = msgResponse.getVector("entries");

				command = "SET_WATCH";
				handler = manager.getHandler(command);

				if (handler == null)
					System.out.println("Not find handler " + command);
				else {

					int size = vEntries.size();
					System.out.println("execute : " + size);

					for (int i = 0; i < size; i++) {
						Message watchMsg = vEntries.get(i);

						try {

							handler.setPrevMessage(watchMsg);
							Message msgRequest = handler.requestMessage();

							if (!msgRequest.hasVariable("exist")) {
								System.out.println("[SET_WATCH] wid : " + watchMsg.getInteger("watch_id"));
								conn.send(msgRequest);
							} else {
								System.out.println("[SKIP] Watch change already. (w:" + watchMsg.getInteger("watch_id") + ")");
							}

						} catch (Exception e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
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

		System.out.println("####### Set Server End ########");
	}

}
