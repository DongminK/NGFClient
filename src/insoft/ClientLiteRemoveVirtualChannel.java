package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;
import insoft.util.LogWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class ClientLiteRemoveVirtualChannel {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		if (args.length == 4) {

			// IOMCServer
			String serverIP = args[0];// "192.168.1.127";
			int serverPort = Integer.parseInt(args[1]); // 9100;

			String id = args[2];
			String pw = args[3];

			LogWriter.write("");
			LogWriter.write("## START ##");

			LogWriter.write("## IP : " + serverIP);
			LogWriter.write("## PORT : " + serverPort);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				IHandler handler = manager.getHandler("GET_REMOTE_CHANNEL");

				Message msgRequest = handler.requestMessage();
				Message msgResponse = conn.send(msgRequest);

				Vector<Message> vEntries = msgResponse.getVector("entries");

				HashSet<Integer> setChannelIds = new HashSet<Integer>();

				LogWriter.write("");
				LogWriter.write("## Remote channel list ##");
				int idx = 1;
				for (Message msgEntry : vEntries) {
					String name = msgEntry.getString("name");
					int channelId = msgEntry.getInteger("channel_id");

					LogWriter.write((idx++) + ". " + name + " (" + channelId + ")");
					setChannelIds.add(channelId);
				}

				if (setChannelIds.size() == 0) {
					LogWriter.write("Not found remote channel");
					System.exit(1);
				}

				handler = manager.getHandler("GET_VIRTUAL_CHANNEL");
				msgRequest = handler.requestMessage();
				msgResponse = conn.send(msgRequest);

				vEntries = msgResponse.getVector("entries");

				LogWriter.write("");
				LogWriter.write("## Check remove virtual channel list ##");
				idx = 1;

				HashMap<String, Vector<String>> mapChannelId = new HashMap<String, Vector<String>>();

				for (Message msgEntry : vEntries) {
					String name = msgEntry.getString("name");
					String systemCodeName = msgEntry.getString("real_system_code");
					int serverChannelId = msgEntry.getInteger("server_channel_id");
					String channelId = msgEntry.getByString("channel_id");

					if (!setChannelIds.contains(serverChannelId)) {
						LogWriter.write((idx++) + ". [" + systemCodeName + "] " + name + " (" + channelId + ")");
						Vector<String> vChannelIds = mapChannelId.get(systemCodeName);

						if (vChannelIds == null) {
							vChannelIds = new Vector<String>();
							mapChannelId.put(systemCodeName, vChannelIds);
						}

						vChannelIds.add(channelId);
					}
				}

				if (idx > 1) {

					LogWriter.write("");
					String command = Util.readCommand("Collect channel list? (Y/N)");

					if (command.trim().toUpperCase().compareTo("Y") != 0) {
						LogWriter.write("Bye.");
						System.exit(1);
					}

					LogWriter.write("");
					command = Util.readCommand("Delete channel list? (Y/N)");

					if (command.trim().toUpperCase().compareTo("Y") != 0) {
						LogWriter.write("Bye.");
						System.exit(1);
					}

					handler = manager.getHandler("DELETE_VIR_CHANNEL");

					for (String key : mapChannelId.keySet()) {
						Vector<String> vChannelIds = mapChannelId.get(key);

						Message msgChannelIds = new Message();
						msgChannelIds.setVector("channel_ids", vChannelIds);

						handler.setPrevMessage(msgChannelIds);
						msgRequest = handler.requestMessage();
						msgResponse = conn.send(msgRequest);

						String returnMsg = msgResponse.getString("return_msg");
						int returnCode = msgResponse.getInteger("return_code");

						if (returnCode == 1) {
							LogWriter.write("Delete channel [" + key + "] " + vChannelIds);
						} else {
							LogWriter.write("Error delete channel - " + returnMsg);
						}
					}

				} else {
					LogWriter.write("Not found remove virtual channel");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.exit(1);
				LogWriter.close();
			}

		} else {
			LogWriter.write("USAGE: ClientLite [server ip] [port] [id] [pw]");
			System.exit(1);
			LogWriter.close();
		}
	}

}
