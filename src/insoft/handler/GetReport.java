package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GetReport implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_REPORT";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		/*
		Message msgFilter = new Message("FILTER");
		
//		String strReportID = Util.readCommand("REPORT ID");
		String strReportID = Util.readCommand("USER ID");
		
		Vector<String> vValues = new Vector<String>();
		vValues.add(strReportID);
		
		msgFilter.setVector("values", vValues);
//		msgFilter.setString("attr_name", "report_id");
		msgFilter.setString("attr_name", "user_id");
		
		Vector<Message> vFilters = new Vector<Message>();
		
		vFilters.add(msgFilter);
		
		msg.setVector("filters", vFilters);
		*/
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
