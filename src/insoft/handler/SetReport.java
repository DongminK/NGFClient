package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class SetReport implements IHandler {

	private Message prevMsg = null;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_REPORT";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub

		Vector<Message> vReport = prevMsg.getVector("entries");

		Message reportMsg = vReport.get(0);
		String reportName = Util.readCommand("Report name");
		String term = Util.readCommand("Time (1=1min, 10=10min, 60=1hour 1440=1day)");

		if (reportName.trim().compareTo("") != 0)
			reportMsg.setString("report_name", reportName);

		Vector<Message> vQuery = reportMsg.getVector("queries");

		if (term.trim().compareTo("") != 0) {
			int iTerm = Integer.parseInt(term);

			for (Message query : vQuery) {
				query.setInteger("term", iTerm);
			}
		}

		Message msg = new Message(getName());

		msg.setMessage("entry", reportMsg);
		msg.setInteger("operation", 0);

		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		prevMsg = msg;
	}
}
