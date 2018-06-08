package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetClientHandlerList implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_CLIENT_HANDLER_LIST";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
