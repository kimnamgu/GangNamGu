package nexti.ejms.agent;

import org.apache.log4j.Logger;

public class ControlSidoLdapGetting extends ControlBase {
	private static Logger logger = Logger.getLogger(ControlSidoLdapGetting.class);
	private static ControlSidoLdapGetting _instance ;
	{
		runTask = (TaskBase)TaskSidoLdapGetting.getInstance();
	}

	public static ControlSidoLdapGetting getInstance() {
		if(_instance == null) { _instance = new ControlSidoLdapGetting(); }
		return _instance;
	}
	
	public void run() {
		runTask.setLSeq(getlSeq());
		runTask.setLastRunStat("");
		(new ControlRunThread(runTask)).start();
	}

	public void start() {
		if (getIsRun()) return;
		logger.info(runTask.getClass().getName()+" start.");
		super.start();
	}
	
	public void stop() {
		if (!getIsRun()) return;
		logger.info(runTask.getClass().getName()+" stop.");
		super.stop();
	}
}