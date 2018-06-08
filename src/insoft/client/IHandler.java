package insoft.client;

import insoft.openmanager.message.Message;

public interface IHandler {

	public String getName();
	public Message requestMessage();
	public void setPrevMessage(Message msg);
	
}
