package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetUsers implements IHandler {

	Message prevMsg = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_USERS";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		int sessionId = 0;
		if (prevMsg != null)
			sessionId = prevMsg.getInteger("session_id");
		
		Message msg = new Message(getName());
		
		msg.setInteger("session_id", sessionId);
		msg.setString("user_id", Util.readCommand("ID"));
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
	}

}
