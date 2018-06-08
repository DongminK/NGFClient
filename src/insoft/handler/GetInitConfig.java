package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetInitConfig implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_INIT_CONFIG";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		return new Message(getName());
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
