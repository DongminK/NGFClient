package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ClientLiteServer {

	public static void main(String[] args) throws Exception {

		if (args.length == 4) {
			String serverIP = "192.168.182.56"; // args[0];//"192.168.1.127";
			int serverPort = 9100; // Integer.parseInt(args[1]); //9100;
			String id = "root"; // args[2];
			String pw = "root"; // args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			ArrayList<Message> ltServerData = new ArrayList<Message>();
			fileLoad(ltServerData);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				int size = ltServerData.size();
				int count = 0;

				String[] commands = { "GET_CONFIG", "GET_CHANNEL", "SET_CONFIG" };

				while (size > count) {
					Message loadMsg = ltServerData.get(count++);

					System.out.println("#######" + loadMsg);
					for (int i = 0; i < commands.length; i++) {

						String command = commands[i];

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

								Message msgResponse = null;
								handler.setPrevMessage(loadMsg);

								try {
									Message msgRequest = handler.requestMessage();

									if (msgRequest == null)
										System.out.println("Not Found : " + loadMsg);

									msgResponse = conn.send(msgRequest);

								} catch (Exception e) {
									System.out.println("Exception " + loadMsg);
									break;
								}

								if (command.equals("GET_CONFIG")) {
									try {
										loadMsg.setInteger("owner_id",
												((Message) msgResponse.getVector("entries").get(0)).getInteger("object_id"));
									} catch (Exception e) {
										System.out.println("Not found group :  " + loadMsg);

										FileWriter fw = null;
										try {
											fw = new FileWriter("ServerFail.txt", true);
											fw.append("Not found GROUP:" + loadMsg.getString("server") + " / CHANNEL:"
													+ loadMsg.getString("channel") + " / SERVER" + loadMsg.getString("name")
													+ "\n");
											fw.flush();
										} catch (IOException ee) {
											// TODO Auto-generated catch block
											ee.printStackTrace();
										} finally {
											try {
												fw.close();
											} catch (IOException eee) {
											}
										}

										break;
									}
								}

								if (command.equals("GET_CHANNEL")) {
									try {
										loadMsg.setInteger("channel_id",
												((Message) msgResponse.getVector("entries").get(0)).getInteger("channel_id"));
									} catch (Exception e) {
									}
								}

							}
						}
						System.out.println("");

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

	private static void fileLoad(ArrayList<Message> ltServerData) {

		String fileName = "server.txt";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

			String tmp = "";
			while ((tmp = br.readLine()) != null) {

				if (tmp.startsWith("#") || tmp.trim().length() == 0)
					continue;

				StringTokenizer st = new StringTokenizer(tmp, ",");

				Message msg = new Message();

				msg.setString("server", st.nextToken().trim());
				msg.setString("channel", st.nextToken().trim());
				msg.setString("name", st.nextToken().trim());

				ltServerData.add(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
