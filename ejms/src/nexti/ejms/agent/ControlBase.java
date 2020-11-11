/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������Ʈ��Ʈ�� �θ�Ŭ����
 * ����:
 */
package nexti.ejms.agent;

import java.text.SimpleDateFormat;

public class ControlBase {
	private boolean isRun = false;
	private String startDateTime = "";
	private String endDateTime = "";
	private long lSeq=0;
	public TaskBase runTask = null;
	
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
		if(runTask!=null) {
			runTask.setIsRun(isRun);
		}
	}
	
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public void setlSeq(long lSeq) {
		this.lSeq = lSeq;
	}
	public long getlSeq() { return this.lSeq; }
	public String getStartDateTime() { return this.startDateTime; }
	public String getEndDateTime() { return this.endDateTime; }
	public boolean getIsRun() { return this.isRun; }
	public String getLastRunDateTime() {
		if(runTask!=null) {
			return runTask.getLastRunDateTime();
		}	
		return "";
	}
	public String getLastRunStat() {
		if(runTask!=null) {
			return runTask.getLastRunStat();
		}	
		return "";
	}

	public void start() {
		setIsRun(true);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		setStartDateTime(df.format(new java.util.Date()));
		setEndDateTime("");
	}
	
	public void stop() {
		setIsRun(false);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		setStartDateTime("");
		setEndDateTime(df.format(new java.util.Date()));
	} 
	
	public void run() {
	}

	protected void create() {
	}
	
	public void terminate() {
	}
}
