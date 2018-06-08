package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetRemoteExecuteHistory implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_REMOTE_EXECUTE_HISTORY";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msgRequest = new Message("GET_REMOTE_EXECUTE_HISTORY");

		msgRequest.setString("login_id", "");
		msgRequest.setString("user_name", "");
		msgRequest.setInteger("request_id", 0);
		msgRequest.setString("begin_date", "20160630000000");
		msgRequest.setString("end_date", "20160630235959");
		msgRequest.setString("command", "");

		return msgRequest;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
