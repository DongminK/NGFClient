package insoft.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class GetSendSmsList implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GET_SMSLIST";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Message msg = new Message("SEND_SMS_LIST");
		
		String startTime = Util.readCommand("StartTime");
		String endTime = Util.readCommand("EndTime");
		String sendNumber = Util.readCommand("Mobile Number");
		String recvNumber = Util.readCommand("Recv Name");
		
		if (startTime.compareTo("") == 0)
			startTime = sdf.format(Calendar.getInstance().getTime()) + "000000";
		
		if (endTime.compareTo("") == 0)
			endTime = sdf.format(Calendar.getInstance().getTime()) + "235959";
		
		msg.setString("start_date", startTime);
		msg.setString("end_date", endTime);
		msg.setString("mobile_number", sendNumber);
		msg.setString("recv_name", recvNumber);
		msg.setInteger("send_type", 2);
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
