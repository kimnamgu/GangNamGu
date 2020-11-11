/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ý��ۿ�����Ʈ �ܺθ� ��û����������
 * ����:
 */
package nexti.ejms.agent;

import java.util.Timer;

import org.apache.log4j.Logger;

import nexti.ejms.common.appInfo;

public class ControlOutsideReqGetting extends ControlBase {
	private static Logger logger = Logger.getLogger(ControlOutsideReqGetting.class);
	private static ControlOutsideReqGetting _instance;
	private Timer runTimer = null;
	{
		runTask = (TaskBase)TaskOutsideReqGetting. getInstance();
	}

	public static ControlOutsideReqGetting getInstance() {
		if(_instance == null) { _instance = new ControlOutsideReqGetting(); }
		return _instance;
	}
	
	public void run() {
		runTask.setLastRunStat("");
		(new ControlRunThread(runTask)).start();
	}
	
	public void start() {
		if (getIsRun()) return;
		if (runTimer==null) create();
		logger.info(runTask.getClass().getName()+" start.");
		super.start();
	}
	
	public void stop() {
		if (!getIsRun()) return;
		logger.info(runTask.getClass().getName()+" stop.");
		super.stop();
	}
	
	protected void create() {
		runTimer = new Timer();
		runTimer.schedule(runTask, 
				((long)appInfo.getReqresultInterval()+30)*1000 , 
				(long)appInfo.getReqresultInterval()*1000);
		logger.info(runTask.getClass().getName()+" ceate.");
	}
	
	public void terminate() {
		if(!getIsRun()) return;
		try { runTimer.cancel(); } catch (Exception e) {}
		runTimer = null;
		logger.info(runTask.getClass().getName()+" terminate.");
		super.stop();
	}	
}