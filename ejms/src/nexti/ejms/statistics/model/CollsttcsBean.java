/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���վ��������Ȳ bean
 * ����:
 */
package nexti.ejms.statistics.model;

public class CollsttcsBean {
	
	private int seqno 				= 0;			//����
	private String colDeptNm		= "";			//���պμ���
	private String chrgUsrNm		= "";			//���մ���ڸ�
	private long chrgCount			= 0;			//���հǼ�

	public long getChrgCount() {
		return chrgCount;
	}

	public void setChrgCount(long chrgCount) {
		this.chrgCount = chrgCount;
	}

	public String getChrgUsrNm() {
		return chrgUsrNm;
	}

	public void setChrgUsrNm(String chrgUsrNm) {
		this.chrgUsrNm = chrgUsrNm;
	}

	public String getColDeptNm() {
		return colDeptNm;
	}

	public void setColDeptNm(String colDeptNm) {
		this.colDeptNm = colDeptNm;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	
}