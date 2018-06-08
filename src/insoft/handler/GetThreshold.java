package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetThreshold implements IHandler {

	private Message prevMsg = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_THRESHOLD";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		
		Message filter = new Message("FILTER");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "watch_id");
		
		Vector<String> vValues = new Vector<String>();
		Vector<String> vWatchIds = prevMsg.getVector("watch_ids");
		vValues.addAll(vWatchIds);
		
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
