package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GetOrg implements IHandler {

	Message msgPrev = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_ORG";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());
		
		Message filter = new Message("FILTER");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "object_id");
		
		Vector<String> vValues = new Vector<String>();
		vValues.addAll(msgPrev.getVector("object_id"));
		
		filter.setVector("values", vValues);
		
		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);
		
		msg.setVector("filters", vFilters);
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		msgPrev = msg;
	}

}
