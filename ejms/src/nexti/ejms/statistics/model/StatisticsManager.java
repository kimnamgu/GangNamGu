/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용현황 manager
 * 설명:
 */
package nexti.ejms.statistics.model;

import java.util.List;

public class StatisticsManager {
	private static StatisticsManager instance = null;
	private static StatisticsDAO dao = null;
	
	private StatisticsManager() {
	}
	
	public static StatisticsManager instance() {
		if (instance==null) instance = new StatisticsManager(); 
		return instance;
	}
	
	private StatisticsDAO getStatisticsDAO(){
		if (dao==null) dao = new StatisticsDAO(); 
		return dao;
	}	
	
	/**
	 * 취합건수현황 조회
	 */
	public List getCollsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		List result = null;
		result = getStatisticsDAO().getCollsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		return result;		
	}
	
	/**
	 * 취합건수현황 총건수
	 */
	public long getCollsttcsTotalCount(String frDate, String toDate) throws Exception {
		long totcount = 0;		
		totcount = getStatisticsDAO().getCollsttcsTotalCount(frDate, toDate);
		return totcount;		
	}
	
	/**
	 * 입력건수현황 조회
	 */
	public List getInputsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		List result = null;
		result = getStatisticsDAO().getInputsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		return result;		
	}
	
	/**
	 * 입력건수현황 총건수
	 */
	public long getInputsttcsTotalCount(String frDate, String toDate) throws Exception {
		long totcount = 0;	
		totcount = getStatisticsDAO().getInputsttcsTotalCount(frDate, toDate);		
		return totcount;		
	}
	
	/**
	 * 설문조사사용현황 조회
	 */
	public List getRchsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		List result = null;		
		result = getStatisticsDAO().getRchsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);		
		return result;		
	}
	
	/**
	 * 설문조사사용현황 총건수
	 */
	public RchsttcsBean getRchsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		return getStatisticsDAO().getRchsttcsTotalCount(frDate, toDate, range);
	}	

	/**
	 * 신청서사용현황 조회
	 */
	public List getReqsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		List result = null;		
		result = getStatisticsDAO().getReqsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);		
		return result;		
	}
	
	/**
	 * 신청서사용현황 총건수
	 */
	public ReqsttcsBean getReqsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		return getStatisticsDAO().getReqsttcsTotalCount(frDate, toDate, range);
	}	
	
}
