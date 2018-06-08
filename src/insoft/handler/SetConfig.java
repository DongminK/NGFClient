package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class SetConfig implements IHandler {

	private int ownerId = -1;
	private int channelId = -1;
	private String name = "";
	private int prevObjectId = -1;
	
	private Message prevMsg = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_CONFIG";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		
		prevMsg.setInteger("object_id", Integer.parseInt(Util.readCommand("OBJECT_ID")));
		
		msg.setMessage("entry", prevMsg);
		
		
		Message filter = new Message("FILTER");
		
		filter.setInteger("type", 0);
		filter.setString("attr_name", "object_id");
		
		Vector<String> vValues = new Vector<String>();
		vValues.add(Integer.toString(prevObjectId));
		
		filter.setVector("values", vValues);
		
		
		Vector<Message> vFilter = new Vector<Message>();
		vFilter.add(filter);
		msg.setVector("filters", vFilter);
		
		msg.setInteger("operation", 1);
		msg.setInteger("type", 1001);
		
		/*
		 SET_CONFIG{
 operation=0
 entry=
	 CONFIG{
		 life_state=1
		 detail=
		 DETAIL{
			 ha_status=-1
			 sound=
			 connect_port=0
			 connect_ip=0.0.0.0
		 }
		 desc=
		 name=aaaaa
		 object_id=0
		 owner_id=1
		 type=1001
		 channel_id=49
	 }
 type=1001
}
		
		
		if (ownerId == -1)
			return null;
		
		Message reqMsg = new Message(getName());
		
		
		reqMsg.setInteger("operation", 0);
		reqMsg.setInteger("type", 1001);
		
		
		Message entry = new Message("CONFIG");
		
		entry.setInteger("life_state", 1);
		entry.setString("desc", "");
		entry.setString("name", name);
		entry.setInteger("object_id", 0);
		entry.setInteger("owner_id", ownerId);
		entry.setInteger("type", 1001);
		entry.setInteger("channel_id", channelId);
		
		Message detail = new Message("DETAIL");
		
		detail.setInteger("ha_status", 1);
		detail.setString("sount", "");
		detail.setInteger("connect_port", 0);
		detail.setString("connect_ip", "0.0.0.0");
		
		entry.setMessage("detail", detail);
		reqMsg.setMessage("entry", entry);
		
		
		reqMsg.setInteger("operation", 1);
		reqMsg.setInteger("type", 1200);
		
		Message manager = new Message("MANAGING");
		
		manager.setInteger("manager_id", 5);
		manager.setInteger("notify", 1);
		manager.setInteger("notify_resolve", 0);
		manager.setInteger("exception_alert", 1);
		
		Vector<Message> vFault = new Vector<Message>();
		for (int i=1; i<=3; i++) {
			Message faultMsg = new Message("GRADE_NOTIFY");
			faultMsg.setInteger("notify", 1);
			faultMsg.setInteger("fault_grade", i);
			
			vFault.add(faultMsg);
		}
		
		manager.setVector("notify_faults", vFault);
		manager.setString("class", "ADMIN");
		manager.setVector("auth_codes", new Vector());
		
		
		Message configMsg = new Message("CONFIG");
		
		Vector<Message> vConfig = new Vector<Message>();
		vConfig.add(manager);
		configMsg.setVector("managers", vConfig);
		
		reqMsg.setMessage("entry", configMsg);
		
		
		
		Message filter = new Message("FILTER");
		
		filter.setInteger("type", 0);
		filter.setString("attr_name", "object_id");
		
		Vector<String> vValues = new Vector<String>();
		vValues.add("1");
		
		filter.setVector("values", vValues);
		
		
		Vector<Message> vFilter = new Vector<Message>();
		vFilter.add(filter);
		reqMsg.setVector("filters", vFilter);
		*/
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
		prevMsg = (Message)msg.getVector("entries").get(0);
		prevObjectId = prevMsg.getInteger("object_id");
		
		/*
		ownerId = msg.getInteger("owner_id", -1);
		channelId = msg.getInteger("channel_id", -1);
		name = msg.getString("name");
		*/

	}

}
