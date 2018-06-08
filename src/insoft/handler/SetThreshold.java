package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class SetThreshold implements IHandler {

	private Message prevMsg = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_THRESHOLD";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message reqMsg = new Message(getName());
		
		reqMsg.setInteger("operation", 2);
		reqMsg.setMessage("entry", new Message());
		
		Vector<Message> vEntries = prevMsg.getVector("entries");
		
		Message filterWatchId = new Message("FILTER");
		filterWatchId.setInteger("type", 0);
		filterWatchId.setString("attr_name", "watch_id");
		Vector<String> vWatchIdValues = new Vector<String>();
		vWatchIdValues.add("" + prevMsg.getInteger("watch_id"));
		filterWatchId.setVector("values", vWatchIdValues);
		
		Message filterWatchType = new Message("FILTER");
		filterWatchType.setInteger("type", 0);
		filterWatchType.setString("attr_name", "watch_type");
		
		Vector<String> vWatchTypeValues = new Vector<String>();
		vWatchTypeValues.add(prevMsg.getString("watch_type"));
		filterWatchType.setVector("values", vWatchTypeValues);
		
		
		Message filterMsgName = new Message("FILTER");
		filterMsgName.setInteger("type", 0);
		filterMsgName.setString("attr_name", "message_name");
		
		Vector<String> vMsgNameValues = new Vector<String>();
		filterMsgName.setVector("values", vMsgNameValues);
		
		Message filterType = new Message("FILTER");
		filterType.setInteger("type", 0);
		filterType.setString("attr_name", "type");
		
		Vector<String> vTypeValues = new Vector<String>();
		filterType.setVector("values", vTypeValues);
		
		for (Message threshold : vEntries) {
			
			if (threshold.getString("message_name").startsWith("WEB")) {
				vMsgNameValues.add(threshold.getString("message_name"));
				vTypeValues.add(threshold.getString("type"));
			}
		}
		
		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filterWatchId);
		vFilters.add(filterWatchType);
		vFilters.add(filterMsgName);
		vFilters.add(filterType);
		
		reqMsg.setVector("filters", vFilters);
		
		if (vMsgNameValues.size() == 0)
			return null;
		
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
	}

}
