package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class IOMCFileCheckType implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "IOMC_FILE_CHECK_TYPE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msgReq = new Message(getName());
		
		msgReq.setString("listfile_path", "/home/insoft/IOMCServer_IMS/config/file_check");
		msgReq.setString("checkfile_path", Util.readCommand("FILE_PATH"));
		
		return msgReq;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
