package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class SetWatch implements IHandler {

	int ownerId = -1;
	int channelId = -1;
	String watchName = "";
	String watchType = "";

	Message prevMsg = null;
	int prevObjectId = -1;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_WATCH";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());

		msg.setMessage("entry", prevMsg);
		Message filter = new Message("FILTER");

		filter.setInteger("type", 0);
		filter.setString("attr_name", "watch_id");

		Vector<String> vValues = new Vector<String>();
		vValues.add(Integer.toString(prevObjectId));

		filter.setVector("values", vValues);

		Vector<Message> vFilter = new Vector<Message>();
		vFilter.add(filter);
		msg.setVector("filters", vFilter);

		msg.setInteger("operation", 1);

		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
		prevObjectId = prevMsg.getInteger("watch_id");
		prevMsg.setInteger("owner_id", 50);
	}

}
