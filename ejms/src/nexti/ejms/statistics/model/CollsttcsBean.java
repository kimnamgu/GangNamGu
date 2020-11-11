/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합업무사용현황 bean
 * 설명:
 */
package nexti.ejms.statistics.model;

public class CollsttcsBean {
	
	private int seqno 				= 0;			//연번
	private String colDeptNm		= "";			//취합부서명
	private String chrgUsrNm		= "";			//취합담당자명
	private long chrgCount			= 0;			//취합건수

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