package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class UnsetCommandContinuous implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "UNSET_COMMAND_CONTINUOUS";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msgRequest = new Message(getName());
		msgRequest.setInteger("command_id", Integer.parseInt(Util.readCommand("COMMAND_ID")));
		
		return msgRequest;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
