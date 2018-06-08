package insoft.handler;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

import java.util.Vector;

public class GenerateReport implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GENERATE_REPORT";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message(getName());
		
		msg.setString("task_begin_datetime", "20121231000000");
		msg.setString("task_end_datetime", "20130107235959");
		msg.setInteger("schedule_id", -1);
		msg.setString("template_repository", "stat_template");
		msg.setString("report_name", "333");
		msg.setInteger("report_id", 3);
		msg.setString("working_repository", "stat_template");
		msg.setInteger("ignore_history", 0);
		msg.setString("template_filename", "ReportCpu.xls");
		msg.setInteger("file_type", 3);
		
		
		Message query = new Message();
		
		query.setString("watch_type", "OS");
		query.setInteger("interval", 0);
		query.setInteger("term", 60);
		
		Vector<Integer> watchIds = new Vector<Integer>();
		watchIds.add(21);
		query.setVector("watch_ids", watchIds);
		query.setInteger("type", 0);
		query.setString("message_name", "OS");
		query.setString("excel_sheet", "aa");
		Vector<Message> vQuery = new Vector<Message>();
		vQuery.add(query);
		
		msg.setVector("queries", vQuery);
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
