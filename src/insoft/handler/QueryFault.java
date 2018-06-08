package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class QueryFault implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "QUERY_FAULT";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		/*
		Message msg = new Message(getName());
		
		int objectId = Integer.parseInt(Util.readCommand("Object ID"));
		String startTime = Util.readCommand("START TIME");
		String endTime = Util.readCommand("END TIME");
		
		msg.setString("begin_datetime", startTime);
		msg.setString("end_datetime", endTime);
		msg.setInteger("object_id", objectId);
		msg.setInteger("current_page", 1);
		msg.setInteger("row_per_page", 1000);
		
		return msg;
		*/
		
		
		Vector<String> ltFaultIds = new Vector<String>();
		ltFaultIds.add("73742");
		
		 Message msgQueryFault = new Message(getName());
	     msgQueryFault.setVector("fault_ids", ltFaultIds);
	        
		return msgQueryFault;
		
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
