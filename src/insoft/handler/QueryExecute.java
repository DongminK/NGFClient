package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class QueryExecute implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "QUERY_EXECUTE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		
		msg.setString("channel_name", "imstest");
		msg.setString("sql", "select * from TBL_IMS_10MIN_STAT");
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
