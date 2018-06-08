package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GetRemoteServerChannel implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_REMOTE_CHANNEL";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msg = new Message("GET_CHANNEL");

		Vector<Message> vFilters = new Vector<Message>();

		Message msgFilter = new Message("FILTER");
		msgFilter.setString("attr_name", "channel_type");

		Vector<String> vChannelTypes = new Vector<String>();
		vChannelTypes.add("11");

		msgFilter.setVector("values", vChannelTypes);

		vFilters.add(msgFilter);

		msg.setVector("filters", vFilters);

		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
