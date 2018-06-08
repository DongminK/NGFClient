package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class CallProcedure implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CALL_PROCEDURE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message reqMsg = new Message(getName());
		
		reqMsg.setString("channel_name", Util.readCommand("Channel Name"));
		reqMsg.setString("procedure", Util.readCommand("Procedure Name"));
		
		String param = "";
		Vector<String> vParam = new Vector<String>();
		int index = 0;
		
		do {
			
			param = Util.readCommand("[" + (++index) + "] Parameter");
			
			if (param.trim().length() > 0)
				vParam.add(param);
			
		} while(param.trim().length() > 0);
			
		reqMsg.setVector("params", vParam);
		
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
