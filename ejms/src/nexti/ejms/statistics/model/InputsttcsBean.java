/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력업무사용현황 bean
 * 설명:
 */
package nexti.ejms.statistics.model;

public class InputsttcsBean {
	
	private int seqno 				= 0;			//연번
	private String inputDeptNm		= "";			//입력부서명
	private String inputUsrNm		= "";			//입력담당자명
	private long inputCount			= 0;			//입력건수
	private String title			= "";
	private String summary			= "";
	private String strdt			= "";
	private String enddt			= "";

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public String getStrdt() {
		return strdt;
	}

	public void setStrdt(String strdt) {
		this.strdt = strdt;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getInputCount() {
		return inputCount;
	}

	public void setInputCount(long inputCount) {
		this.inputCount = inputCount;
	}

	public String getInputDeptNm() {
		return inputDeptNm;
	}

	public void setInputDeptNm(String inputDeptNm) {
		this.inputDeptNm = inputDeptNm;
	}

	public String getInputUsrNm() {
		return inputUsrNm;
	}

	public void setInputUsrNm(String inputUsrNm) {
		this.inputUsrNm = inputUsrNm;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	
}