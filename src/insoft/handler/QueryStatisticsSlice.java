package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class QueryStatisticsSlice implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "QUERY_STATISTICS_SLICE";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		/*
		QUERY_STATISTICS_SLICE{
			 request_id=0
			 watch_type=OS
			 begin_time=20130503120000
			 end_time=20130503145959
			 profile_name=
			 watch_ids=1[18]
			 xml_entity=96776936
			 message_name=OS
			 statistics=1
			}
		*/
		
		Message reqMsg = new Message(getName());
		
		reqMsg.setInteger("request_id", 0);
		reqMsg.setString("watch_type", "ETS_EXIF");
		reqMsg.setString("begin_time", Util.readCommand("Begin Time"));
		reqMsg.setString("end_time", Util.readCommand("End Time"));
		reqMsg.setString("profile_name", "");
		
		/*
		Vector<String> category1 = new Vector<String>();
		category1.add("ABC317");
		Vector<String> category2 = new Vector<String>();
		category2.add("HDV");
		Vector<String> category3 = new Vector<String>();
		category3.add("REGI");
		Vector<String> category4 = new Vector<String>();
		category4.add("MSS1");
		Vector<String> category5 = new Vector<String>();
		category5.add("IN");
		
		reqMsg.setVector("category1", category1);
		reqMsg.setVector("category2", category2);
		reqMsg.setVector("category3", category3);
		reqMsg.setVector("category4", category4);
		reqMsg.setVector("category5", category5);
		*/
		
		Vector<Integer> ltWatchIds = new Vector<Integer>();
		ltWatchIds.add(1935);
		
		reqMsg.setVector("watch_ids", ltWatchIds);
		reqMsg.setString("message_name", "ETS_EXIF_CATEGORY");
		reqMsg.setInteger("xml_entity", 97500224);
		reqMsg.setInteger("statistics", Integer.parseInt(Util.readCommand("Stat Type")));
		/*
		Message reqMsg = new Message(getName());
		
		reqMsg.setInteger("request_id", 0);
		reqMsg.setString("watch_type", "OS");
		reqMsg.setString("begin_time", Util.readCommand("Begin Time"));
		reqMsg.setString("end_time", Util.readCommand("End Time"));
		reqMsg.setString("profile_name", "");
		
		Vector<Integer> ltWatchIds = new Vector<Integer>();
		ltWatchIds.add(18);
		
		reqMsg.setVector("watch_ids", ltWatchIds);
		reqMsg.setString("message_name", "OS");
		reqMsg.setInteger("statistics", Integer.parseInt(Util.readCommand("Stat Type")));
		*/
		
		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
