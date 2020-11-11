/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 에이전트실행 부모클래스
 * 설명:
 */
package nexti.ejms.agent;

import org.apache.log4j.Logger;

public class ControlRunThread extends Thread {
	private static Logger logger = Logger.getLogger(ControlRunThread.class);
	private TaskBase runTask = null;
	
	public ControlRunThread(TaskBase task) {
		runTask = task;
	}
	
	public void run() {
		try {
			runTask.run();
		} catch (Exception e) {
			logger.error("running error: "+ e);
		}
	}
}
