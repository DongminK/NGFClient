package insoft.handler;

import insoft.client.IHandler;
import insoft.client.Util;
import insoft.openmanager.message.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SetTopologyMap implements IHandler {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SET_TOPOLOGYMAP";
	}

	@Override
	public Message requestMessage() {
		// TODO Auto-generated method stub
		
		File f = new File("C:/NGFWorkspace/DMK - NGF_CLIENT/1.map");
		
		byte[] bData = new byte[(int)f.length()];
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(f);
			fis.read(bData);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {}
		}
		
		Message msg = new Message(getName());
		
		msg.setString("repository_name", "topologymap");
		msg.setBytes("data", bData);
		msg.setString("map_name", Util.readCommand("MAP NAME"));
		msg.setInteger("offset", 0);
		msg.setInteger("user_object_id", Integer.parseInt(Util.readCommand("USER OBJECT ID")));
		msg.setInteger("config_id", Integer.parseInt(Util.readCommand("CONFIG ID")));
		msg.setInteger("visible", Integer.parseInt(Util.readCommand("VISIBLE")));
		msg.setInteger("operation", Integer.parseInt(Util.readCommand("OPERATION")));
		msg.setInteger("map_id", Integer.parseInt(Util.readCommand("MAP ID")));
		msg.setInteger("force_execute", Integer.parseInt(Util.readCommand("FORCE")));
		
		return msg;
	}

	@Override
	public void setPrevMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
