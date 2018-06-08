package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class GetChannel implements IHandler {
	
	String channel = "";
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_CHANNEL";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		/*
		 * 
		 * GET_CHANNEL{
 filters=1[
 FILTER{
 values=1[0]
 type=0
 attr_name=channel_type
 }
 ]
}


		 */
		
		Message msg = new Message(getName());
		
		Message filter = new Message("FILTER");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "name");
		
		Vector<String> vValues = new Vector<String>();
		vValues.add(channel);
		
		filter.setVector("values", vValues);
		
		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);
		
		msg.setVector("filters", vFilters);
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		channel = msg.getString("channel");
		
	}

}
