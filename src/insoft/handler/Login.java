package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class Login implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "LOGIN";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());
		
		msg.setString("user_id", Util.readCommand("ID"));
		msg.setString("user_pw", Util.readCommand("PW"));
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
