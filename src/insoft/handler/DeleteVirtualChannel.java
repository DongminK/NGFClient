package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class DeleteVirtualChannel implements IHandler {

	private Vector<String> vChannelIds = new Vector<String>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DELETE_VIR_CHANNEL";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub

		Message msg = new Message("SET_CHANNEL");

		msg.setInteger("operation", 2);

		Vector<Message> vFilters = new Vector<Message>();

		Message msgFilter = new Message("FILTER");
		msgFilter.setString("attr_name", "channel_id");

		msgFilter.setVector("values", vChannelIds);

		vFilters.add(msgFilter);

		msg.setVector("filters", vFilters);

		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		vChannelIds.clear();
		vChannelIds = msg.getVector("channel_ids");
	}

}
