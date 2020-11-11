/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 사용자,조직연계
 * 설명:
 */
package nexti.ejms.agent;

import org.apache.log4j.Logger;

public class ControlUsrDeptGetting extends ControlBase {
	private static Logger logger = Logger.getLogger(ControlUsrDeptGetting.class);
	private static ControlUsrDeptGetting _instance ;
	{
		runTask = (TaskBase)TaskUsrDeptGetting.getInstance();
	}

	public static ControlUsrDeptGetting getInstance() {
		if(_instance == null) { _instance = new ControlUsrDeptGetting(); }
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
