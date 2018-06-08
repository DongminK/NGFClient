package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GetConfigUser implements IHandler {

	Message prevMsg = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_CONFIG_USER";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message("GET_CONFIG");
		
		Message filter = new Message("FILTER");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "object_id");
		
		Vector<String> vValues = new Vector<String>();
		vValues.add(prevMsg.getByString("object_id"));
		
		filter.setVector("values", vValues);
		
		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);
		
		msg.setVector("filters", vFilters);
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
	}

}
