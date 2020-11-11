/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 마감시한처리
 * 설명:
 */
package nexti.ejms.agent;

import java.util.Timer;

import org.apache.log4j.Logger;

import nexti.ejms.common.appInfo;

public class ControlDeadlineTimer extends ControlBase {
	private static Logger logger = Logger.getLogger(ControlDeadlineTimer.class);
	private static ControlDeadlineTimer _instance;
	private Timer runTimer = null;
	{
		runTask = (TaskBase)TaskDeadline. getInstance();
	}

	public static ControlDeadlineTimer getInstance() {
		if(_instance == null) { _instance = new ControlDeadlineTimer(); }
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
				(long)appInfo.getDeadlineInterval()*1000, 
				(long)appInfo.getDeadlineInterval()*1000);
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
