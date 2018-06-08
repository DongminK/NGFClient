package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class QueryFaultSlice implements IHandler {

	private Message msgPrev = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "QUERY_FAULT_SLICE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		
		int objectId = 0;
		
		String startTime = "";
		String endTime = "";
		
		if (msgPrev != null) {
			objectId = msgPrev.getInteger("object_id");
			startTime = msgPrev.getString("begin_datetime");
			endTime = msgPrev.getString("end_datetime");
		} else {
			objectId = Integer.parseInt(Util.readCommand("Object ID"));
			startTime = Util.readCommand("START TIME");
			endTime = Util.readCommand("END TIME");
		}
		
		msg.setString("begin_datetime", startTime);
		msg.setString("end_datetime", endTime);
		msg.setInteger("object_id", objectId);
		msg.setInteger("row_per_page", 1000);
		
		return msg;
		
		
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg == null) {
			msgPrev = null;
			return;
		}
		
		int endData = msg.getInteger("end_data");
		if (endData == 0) 
			msgPrev = msg;
	}

}
