package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetClay implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_CLAY";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message reqMsg = new Message(getName());
		
		reqMsg.setString("ip", "30.30.30.4");
		reqMsg.setInteger("timeout", 2000);
		
		Vector<Message> vParam = new Vector<Message>();
		
		Message param1 = new Message();
		param1.setString("key", "id");
		param1.setString("value", Util.readCommand("id"));
		
		Message param2 = new Message();
		param2.setString("key", "section");
		param2.setString("value", "VSMSS.COMMON");
/*
		reqMsg.setString("ip", Util.readCommand("IP"));
		reqMsg.setInteger("timeout", Integer.parseInt(Util.readCommand("TIMEOUT")));
		
		Vector<Message> vParam = new Vector<Message>();
		
		Message param1 = new Message();
		param1.setString("key", "id");
		param1.setString("value", Util.readCommand("ID"));
		
		Message param2 = new Message();
		param2.setString("key", "section");
		param2.setString("value", Util.readCommand("SECTION"));
*/
		
		vParam.add(param1);
//		vParam.add(param2);
		
		reqMsg.setVector("params", vParam);
		
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
