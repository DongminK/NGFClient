package insoft.client;

import insoft.handler.CheckWatchThreshold;
import insoft.handler.GetThreshold;
import insoft.handler.RecoveryThreshold;

import java.util.HashMap;

public class HandlerManager {

	private HashMap<String, IHandler> hHandler = new HashMap<String, IHandler>();
	private static HandlerManager manager = new HandlerManager();
	private HandlerManager() {
		init();
	}
	
	public static HandlerManager getInstance()
	{
		return manager;
	}
	
	private void init()
	{
		IHandler[] handlers = {
				new CheckWatchThreshold(),
				new RecoveryThreshold(), 
//				new GetRemoteServerChannel(),
//				new GetVirtualChannel(),
//				new DeleteVirtualChannel()
//				new DirOperation(),
//				new GetInitConfig(),
//				new QueryFault(),
//				new GetFault(),
//				new GetUser(),
//				new GetOrg(),
//				new GenerateReport(),
//				new GetSendSmsList(),
//				new SendSMS(),
//				new GetReport(),
//				new SetReport(),
//				new SetConfig(),
//				new QueryStatisticsSlice(),
//				new GetClay(),
//				new SetClay(),
//				new SetChannel(),
//				new GetConfig(),
//				new GetChannel(),
//				new SetWatch(),
//				new GetWatch(),
//				new QueryStatisticsSliceFilter(),
//				new GetClientHandlerList(),
//				new RepositoryList(),
//				new QueryExecute(),
//				new CallProcedure(),
				new GetThreshold(),
//				new SetThreshold(),
//				new GetDefaultWatchList(),
//				new Command(),
//				new RemoteExecute(),
//				new GetRemoteExecuteHistory(),
//				new IomcRemoteExecute(),
//				new Login(),
//				new GetUsers(),
//				new GetTopologyMap(),
//				new GetTopologyMapList(),
//				new SetTopologyMap(),
//				new GetDashboard(),
//				new SetDashboard(),
//				new IOMCFileCheckType(),
//				new GetConfigUser(),
//				new UnsetCommandContinuous(),
//				new QueryFaultSlice(),
//				new GetUptime()
		};
		
		try {
			
			for (IHandler handler : handlers) {
				hHandler.put(handler.getName(), handler);
			}
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void list()
	{
		System.out.println("## HANDLER LIST ##");
		for (String name : hHandler.keySet())
		{
			System.out.println(name);
		}
	}
	
	public IHandler getHandler(String name)
	{
		return hHandler.get(name);
	}
}
