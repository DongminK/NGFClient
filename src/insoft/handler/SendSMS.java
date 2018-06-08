package insoft.handler;

import java.util.Vector;

import insoft.client.IHandler;
import insoft.openmanager.message.Message;

public class SendSMS implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SEND_SMS";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		Message msg = new Message("SEND_SMS");
		
		msg.setInteger("fault_id", 1);
		msg.setString("send_message", "SMS TEST MESSAGE");
		msg.setInteger("send_type", 0);
		
		Message sender = new Message("SEND_INFO");
		sender.setString("send_number", "010-2302-2324");
		sender.setInteger("send_userid", 1);
		
		msg.setMessage("send_info", sender);
		
		Message receiver = new Message("RECV_INFO");
		receiver.setString("recv_name", "º€« »£");
		receiver.setString("recv_number", "010-2417-2453");
		receiver.setInteger("recv_userid", 2);
		
		Vector<Message> vRecv = new Vector<Message>();
		vRecv.add(receiver);
		
		msg.setVector("recv_infos", vRecv);
		
		return msg;
	}
	
	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
