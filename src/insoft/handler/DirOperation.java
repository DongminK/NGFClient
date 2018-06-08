package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class DirOperation implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DIR_OPERATION";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msgRequest = new Message(getName());

		msgRequest.setString("repository_name", "remote_execute");
		msgRequest.setString("path", Util.readCommand("PATH"));
		msgRequest.setString("rename_path", Util.readCommand("RENAME_PATH"));
		msgRequest.setInteger("operation", Integer.parseInt(Util.readCommand("OPERATION")));

		return msgRequest;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
