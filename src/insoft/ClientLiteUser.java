package insoft;

import insoft.client.Connector;
import insoft.client.HandlerManager;
import insoft.client.IHandler;
import insoft.client.Util;
import insoft.info.UserInfo;
import insoft.openmanager.message.Message;

import java.util.HashMap;
import java.util.Vector;

public class ClientLiteUser {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		if (args.length == 4) {

			// NGFServer KISTI
			// String serverIP = "203.250.231.230"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// NGFServer
			// String serverIP = "192.168.1.137"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.29"; //args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// String serverIP = "192.168.1.139"; //args[0];//"192.168.1.127";
			// int serverPort = 9300; //Integer.parseInt(args[1]); //9100;

			// IOMCServer
			// String serverIP =
			// "192.168.1.29";//137";		//args[0];//"192.168.1.127";
			// int serverPort = 9500; //Integer.parseInt(args[1]); //9100;

			// Myon
			// String serverIP = "192.168.1.103"; //args[0];//"192.168.1.127";
			// int serverPort = 7777; //Integer.parseInt(args[1]); //9100;

			// Localhost
			// String serverIP =
			// "127.0.0.1";//137";		//args[0];//"192.168.1.127";
			// int serverPort = 9100; //Integer.parseInt(args[1]); //9100;

			// String id = "root"; //args[2];
			// String pw = "root"; //args[3];

			String serverIP = args[0];
			int serverPort = Integer.parseInt(args[1]);

			String id = args[2];
			String pw = args[3];

			System.out.println("## IP : " + serverIP);
			System.out.println("## PORT : " + serverPort);

			try {

				Connector conn = new Connector(serverIP, serverPort, id, pw);
				HandlerManager manager = HandlerManager.getInstance();

				String resolve = Util.readCommand("RESOLVE FAULT (y/n)");
				boolean isResolve = false;
				if (resolve.trim().toLowerCase().compareTo("y") == 0)
					isResolve = true;

				String command = Util.readCommand("CONFIG ID");
				String strGrade = "-1";

				if (!isResolve)
					strGrade = Util.readCommand("GRADE (1/2/3)");

				if ("".compareTo(command) == 0)
					System.exit(1);

				HashMap<Integer, UserInfo> mapUserInfo = new HashMap<Integer, UserInfo>();

				IHandler handler = manager.getHandler("GET_CONFIG_USER");

				Message prevMsg = new Message();
				prevMsg.setInteger("object_id", Integer.parseInt(command));
				Vector<Message> vTotalManagers = new Vector<Message>();

				while (prevMsg.getInteger("object_id") > 0) {
					handler.setPrevMessage(prevMsg);
					Message requestMsg = handler.requestMessage();
					Message msgResult = conn.send(requestMsg);

					if (msgResult.getVector("entries").size() > 0) {
						msgResult = (Message) msgResult.getVector("entries").get(0);
						Vector<Message> vManagers = msgResult.getVector("managers");
						for (Message msg : vManagers) {
							msg.setInteger("config_id", prevMsg.getInteger("object_id"));
						}
						vTotalManagers.addAll(vManagers);
						prevMsg.setInteger("object_id", msgResult.getInteger("owner_id"));
					}
				}

				if (vTotalManagers.size() > 0) {
					Vector<String> vUserIds = new Vector<String>();
					Vector<String> vGroupIds = new Vector<String>();

					for (Message msgManager : vTotalManagers) {
						String managerId = msgManager.getByString("manager_id");
						int configId = msgManager.getInteger("config_id");
						boolean isNotifyMinor = false;
						boolean isNotifyMajor = false;
						boolean isNotifyCritical = false;
						boolean isNotifyResolve = false;
						Vector<Message> vNotifyFault = msgManager.getVector("notify_faults");

						if (msgManager.getInteger("notify_resolve") == 1)
							isNotifyResolve = true;

						for (Message msgNotify : vNotifyFault) {
							int notify = msgNotify.getInteger("notify");
							int grade = msgNotify.getInteger("fault_grade");

							if (grade == 1 && notify == 1)
								isNotifyMinor = true;
							else if (grade == 2 && notify == 1)
								isNotifyMajor = true;
							else if (grade == 3 && notify == 1)
								isNotifyCritical = true;
						}
						int manageId = msgManager.getInteger("manager_id");
						UserInfo userInfo = new UserInfo(configId, managerId, manageId, isNotifyMinor, isNotifyMajor,
								isNotifyCritical, isNotifyResolve);
						mapUserInfo.put(manageId, userInfo);

						if (msgManager.getInteger("type") == 4000) {
							vUserIds.add(managerId);
						} else {
							vGroupIds.add(managerId);
							userInfo.setGroup(true);
						}
					}

					if (vGroupIds.size() > 0) {
						handler = manager.getHandler("GET_ORG");
						prevMsg.setVector("object_id", vGroupIds);

						handler.setPrevMessage(prevMsg);
						Message requestMsg = handler.requestMessage();
						Message msgResult = conn.send(requestMsg);

						if (msgResult.getVector("entries").size() > 0) {

							Vector<Message> vOrgs = msgResult.getVector("entries");

							for (Message msgOrg : vOrgs) {

								int objectId = msgOrg.getInteger("object_id");
								UserInfo userInfo = mapUserInfo.get(objectId);

								Vector<Integer> vMembers = msgOrg.getVector("members");
								userInfo.setMembers(vMembers);

								for (Integer userId : vMembers) {
									vUserIds.add("" + userId);
								}
							}

						}

					}

					if (vUserIds.size() > 0) {
						handler = manager.getHandler("GET_USER");
						prevMsg.setVector("object_id", vUserIds);

						handler.setPrevMessage(prevMsg);
						Message requestMsg = handler.requestMessage();
						Message msgResult = conn.send(requestMsg);

						if (msgResult.getVector("entries").size() > 0) {

							Vector<Message> vUsers = msgResult.getVector("entries");

							for (Message msgUser : vUsers) {

								int objectId = msgUser.getInteger("object_id");
								int ownerId = msgUser.getInteger("owner_id");
								String name = msgUser.getString("name");
								String phone = msgUser.getString("mobile_phone");
								String email = msgUser.getString("email");

								UserInfo userInfo = mapUserInfo.get(objectId);
								if (userInfo != null) {
									userInfo.setName(name);
									userInfo.setPhone(phone);
									userInfo.setEmail(email);
								} else {
									userInfo = mapUserInfo.get(ownerId).clone();
									userInfo.setUserId(objectId);
									userInfo.setName(name);
									userInfo.setPhone(phone);
									userInfo.setEmail(email);
								}
							}

						}
					}

					System.out.println("\n####### USER SEARCH RESULT #######\n");
					HashMap<Integer, UserInfo> mapUser = new HashMap<Integer, UserInfo>();
					int grade = Integer.parseInt(strGrade);
					for (UserInfo userInfo : mapUserInfo.values()) {

						if (grade == 1 && userInfo.isNotifyMinor()) {
							System.out.println("[MINOR] " + userInfo);
							if (!userInfo.isGroup())
								mapUser.put(userInfo.getUserId(), userInfo);
						} else if (grade == 2 && userInfo.isNotifyMajor()) {
							System.out.println("[MAJOR] " + userInfo);
							if (!userInfo.isGroup())
								mapUser.put(userInfo.getUserId(), userInfo);
						} else if (grade == 3 && userInfo.isNotifyCritical()) {
							System.out.println("[CRITICAL] " + userInfo);
							if (!userInfo.isGroup())
								mapUser.put(userInfo.getUserId(), userInfo);
						} else if (isResolve && userInfo.isNotifyResolve()) {
							System.out.println("[RESOLVE] " + userInfo);
							if (!userInfo.isGroup())
								mapUser.put(userInfo.getUserId(), userInfo);
						}

						if (userInfo.isGroup()) {
							Vector<Integer> vMembers = userInfo.getMembers();

							for (Integer memberId : vMembers) {
								UserInfo user = mapUserInfo.get(memberId);
								if (user != null) {
									System.out.println("===> " + user.getInfo());
									mapUser.put(memberId, user);
								}
							}
						}
					}

					System.out.println("\n####### NOTIFY USER LIST #######\n");

					for (UserInfo userInfo : mapUser.values()) {
						System.out.println(userInfo.getInfo());
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.exit(1);
			}

		} else {
			System.out.println("USAGE: ClientLiteUser [server ip] [port] [id] [pw]");
			System.exit(1);
		}
	}

}
