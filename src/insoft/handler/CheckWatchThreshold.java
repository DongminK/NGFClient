package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class CheckWatchThreshold implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CHECK_WATCH_THRESHOLD";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		Message msgRequest = new Message("GET_WATCH");

		Message filter = new Message("");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "watch_type");

		Vector<String> vValues = new Vector<String>();
		vValues.add(Util.readCommand("Check watch type (OS, DISK, etc...)"));
		
		if (vValues.get(0).equals("all")) {
			vValues.clear();
		}

		filter.setVector("values", vValues);

		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);

		msgRequest.setVector("filters", vFilters);

		return msgRequest;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
