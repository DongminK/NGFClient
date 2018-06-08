package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetDefaultWatchList implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_DEFAULT_WATCHLIST";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message reqMsg = new Message(getName());
		
		reqMsg.setInteger("type", Integer.parseInt(Util.readCommand("CONFIG TYPE")));
		reqMsg.setString("os", Util.readCommand("OS"));
		reqMsg.setString("platform", Util.readCommand("PLATFORM"));
		
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
