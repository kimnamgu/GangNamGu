/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 에이전트작업 부모클래스
 * 설명:
 */
package nexti.ejms.agent;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

public class TaskBase extends TimerTask {

	private String lastRunDateTime = "";
	private String lastRunStat = "";
	private long lSeq = 0L;
	private boolean isRun = false;
	
	public String getLastRunStat() {
		return lastRunStat;
	}
	public void setLastRunStat(String lastRunStat) {
		this.lastRunStat = lastRunStat;
	}
	public String getLastRunDateTime() {
		return lastRunDateTime;
	}
	public void setLastRunDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		this.lastRunDateTime = df.format(new java.util.Date());
		setLastRunStat("");
	}
	public long getLSeq() {
		return lSeq;
	}
	public void setLSeq(long seq) {
		lSeq = seq;
	}
	public boolean getIsRun() {
		return isRun;
	}
	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
	}
	
	public void run() {
		
	}
}
