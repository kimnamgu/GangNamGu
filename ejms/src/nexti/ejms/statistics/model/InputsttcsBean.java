/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¾��������Ȳ bean
 * ����:
 */
package nexti.ejms.statistics.model;

public class InputsttcsBean {
	
	private int seqno 				= 0;			//����
	private String inputDeptNm		= "";			//�Էºμ���
	private String inputUsrNm		= "";			//�Է´���ڸ�
	private long inputCount			= 0;			//�Է°Ǽ�
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