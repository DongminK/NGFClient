package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

public class RepositoryList implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "REPOSITORY_LIST";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub

		/*
		 * 
		 * String repositoryName = reqMsg.getString("repository_name"); String
		 * directory = reqMsg.getString("directory"); String extention =
		 * reqMsg.getString("extention"); int includeSubdir =
		 * reqMsg.getInteger("include_subdir");
		 */
		Message reqMsg = new Message(getName());

		reqMsg.setString("extension", "");
		reqMsg.setString("repository_name", "remote_execute");
		reqMsg.setString("directory", Util.readCommand("Directory"));
		reqMsg.setInteger("includeSubdir", 1);

		return reqMsg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
