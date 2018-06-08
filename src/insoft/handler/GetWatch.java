package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GetWatch implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_WATCH";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message(getName());

		Message filter = new Message("");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "owner_id");

		Vector<String> vValues = new Vector<String>();
		vValues.add(Util.readCommand("CONFIG_ID"));

		filter.setVector("values", vValues);

		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);

		msg.setVector("filters", vFilters);

		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
