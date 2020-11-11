/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������Ʈ���� �θ�Ŭ����
 * ����:
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
