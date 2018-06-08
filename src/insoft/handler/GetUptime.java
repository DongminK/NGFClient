package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetUptime implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_UPTIME";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());
		msg.setInteger("object_id", Integer.parseInt(Util.readCommand("OBJECT_ID")));
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
