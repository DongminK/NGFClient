package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class RemoteExecute implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "REMOTE_EXECUTE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
        
        Message reqMsg = new Message(getName());
        
        reqMsg.setInteger("channel_id", 1);
        reqMsg.setInteger("type", 0);
        reqMsg.setInteger("timeout", 30);
        
        Message msgExecute = new Message("EXECUTE");
        msgExecute.setString("path", "ls -al");
        
        reqMsg.setMessage("command", msgExecute);
        
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
