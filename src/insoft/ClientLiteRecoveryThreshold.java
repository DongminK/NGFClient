package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;
import insoft.util.LogWriter;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class ClientLiteRecoveryThreshold {

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

				LogWriter.write("## Recovery threshold ##");
				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				while (true) {
					LogWriter.write("");
					IHandler handler = manager.getHandler("CHECK_WATCH_THRESHOLD");

					Message msgRequest = handler.requestMessage();
					Message msgResponse = conn.send(msgRequest);

					Vector<Message> vEntries = msgResponse.getVector("entries");
					TreeMap<String, Message> mapWatchInfos = new TreeMap<String, Message>();

					for (Message msgEntry : vEntries) {
						String watchId = msgEntry.getByString("watch_id");
						mapWatchInfos.put(watchId, msgEntry);
					}

					if (mapWatchInfos.size() == 0) {
						LogWriter.write("> Not found multi threshold.");
					} else {

						Vector<String> vWatchIds = new Vector<String>(mapWatchInfos.keySet());
						Message msgParam = new Message();
						msgParam.setVector("watch_ids", vWatchIds);

						// Get Threshold
						handler = manager.getHandler("GET_THRESHOLD");
						handler.setPrevMessage(msgParam);

						msgRequest = handler.requestMessage();
						msgResponse = conn.send(msgRequest);

						vEntries = msgResponse.getVector("entries");

						HashMap<String, HashMap<String, Message>> mapThresholds = new HashMap<String, HashMap<String, Message>>();

						for (Message msgEntry : vEntries) {

							String watchId = msgEntry.getByString("watch_id");

							if (msgEntry.getVector("check_info").size() > 0) {

								HashMap<String, Message> mapThreshold = mapThresholds.get(watchId);

								if (mapThreshold == null) {
									mapThreshold = new HashMap<String, Message>();
									mapThresholds.put(watchId, mapThreshold);
								}

								String thresholdId = msgEntry.getByString("threshold_id");
								mapThreshold.put(thresholdId, msgEntry);

							}

						}

						if (mapThresholds.size() == 0) {
							LogWriter.write("> Not found multi threshold.");
						} else {

							int totalCount = 0;

							LogWriter.write("\n## [WATCH_ID].[WATCH_NAME] - [THRESHOLD_ID].[TYPE]");

							for (String watchId : mapThresholds.keySet()) {

								HashMap<String, Message> mapThreshold = mapThresholds.get(watchId);

								for (Message msgThreshold : mapThreshold.values()) {
									LogWriter.write(watchId + ". " + mapWatchInfos.get(watchId).getString("watch_name") + " - "
											+ msgThreshold.getInteger("threshold_id") + ". " + msgThreshold.getString("type"));
									totalCount++;
								}
							}

							String isStart = Util.readCommand("\nRecovery threshold? (y/n)").trim().toLowerCase();

							if (isStart.equals("y")) {
								System.out.println();
								int procCount = 0;
								int succCount = 0;

								handler = manager.getHandler("RECOVERY_THRESHOLD");

								for (String watchId : mapThresholds.keySet()) {

									HashMap<String, Message> mapThreshold = mapThresholds.get(watchId);

									for (Message msgThreshod : mapThreshold.values()) {
										try {

											handler.setPrevMessage(msgThreshod);
											msgRequest = handler.requestMessage();
											msgResponse = conn.send(msgRequest);

											if (msgResponse.getInteger("return_code") == 1) {
												succCount++;
											} else {
												LogWriter.write("> Failed - wid:" + watchId + ", tid:"
														+ msgThreshod.getInteger("threshold_id") + ", type:"
														+ msgThreshod.getString("type"));
											}

										} catch (Exception e) {
											LogWriter.write("> Failed - wid:" + watchId + ", tid:"
													+ msgThreshod.getInteger("threshold_id") + ", type:"
													+ msgThreshod.getString("type"));
											break;
										} finally {
											procCount++;
										}

									}

									LogWriter.write("> Processing - succ:" + succCount + "/proc:" + procCount + "/total:"
											+ totalCount + " (" + (procCount * 100 / totalCount) + "%)");

									try {
										Thread.sleep(50);
									} catch (Exception e) {
									}

								}
							}
						}
					}
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
