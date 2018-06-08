package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class RecoveryThreshold implements IHandler {

	Message msgPrev = null;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RECOVERY_THRESHOLD";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub

		Vector<Message> vCheckInfo = msgPrev.getVector("check_info");

		Message msgCheckInfo = vCheckInfo.get(0);
		msgPrev.setVector("criteria", msgCheckInfo.getVector("criteria"));
		msgPrev.setVector("check_info", new Vector<Message>());

		Message msgRequest = new Message("SET_THRESHOLD");

		msgRequest.setInteger("operation", 1);
		msgRequest.setMessage("entry", msgPrev);

		Message filter = new Message("FILTER");
		filter.setInteger("type", 0);
		filter.setString("attr_name", "threshold_id");

		Vector<String> vFilterThresholdId = new Vector<String>();
		vFilterThresholdId.add("" + msgPrev.getInteger("threshold_id"));
		filter.setVector("values", vFilterThresholdId);

		Vector<Message> vFilters = new Vector<Message>();
		vFilters.add(filter);

		msgRequest.setVector("filters", vFilters);

		return msgRequest;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		this.msgPrev = msg;
	}

}
