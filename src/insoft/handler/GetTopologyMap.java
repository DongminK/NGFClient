package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetTopologyMap implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_TOPOLOGYMAP";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());
		
		msg.setInteger("user_object_id", Integer.parseInt(Util.readCommand("USER OBJECT ID")));
		msg.setInteger("config_id", Integer.parseInt(Util.readCommand("CONFIG ID")));
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
