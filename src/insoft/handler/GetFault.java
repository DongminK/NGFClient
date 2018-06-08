package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetFault implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_FAULT";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message("GET_FAULT");
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
