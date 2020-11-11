/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����Ȳ manager
 * ����:
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
	 * ���հǼ���Ȳ ��ȸ
	 */
	public List getCollsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		List result = null;
		result = getStatisticsDAO().getCollsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		return result;		
	}
	
	/**
	 * ���հǼ���Ȳ �ѰǼ�
	 */
	public long getCollsttcsTotalCount(String frDate, String toDate) throws Exception {
		long totcount = 0;		
		totcount = getStatisticsDAO().getCollsttcsTotalCount(frDate, toDate);
		return totcount;		
	}
	
	/**
	 * �Է°Ǽ���Ȳ ��ȸ
	 */
	public List getInputsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate) throws Exception {
		List result = null;
		result = getStatisticsDAO().getInputsttcs(gbn, orggbn, orggbn_dt, frDate, toDate);
		return result;		
	}
	
	/**
	 * �Է°Ǽ���Ȳ �ѰǼ�
	 */
	public long getInputsttcsTotalCount(String frDate, String toDate) throws Exception {
		long totcount = 0;	
		totcount = getStatisticsDAO().getInputsttcsTotalCount(frDate, toDate);		
		return totcount;		
	}
	
	/**
	 * ������������Ȳ ��ȸ
	 */
	public List getRchsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		List result = null;		
		result = getStatisticsDAO().getRchsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);		
		return result;		
	}
	
	/**
	 * ������������Ȳ �ѰǼ�
	 */
	public RchsttcsBean getRchsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		return getStatisticsDAO().getRchsttcsTotalCount(frDate, toDate, range);
	}	

	/**
	 * ��û�������Ȳ ��ȸ
	 */
	public List getReqsttcs(int gbn, String orggbn, String orggbn_dt, String frDate, String toDate, String range) throws Exception {
		List result = null;		
		result = getStatisticsDAO().getReqsttcs(gbn, orggbn, orggbn_dt, frDate, toDate, range);		
		return result;		
	}
	
	/**
	 * ��û�������Ȳ �ѰǼ�
	 */
	public ReqsttcsBean getReqsttcsTotalCount(String frDate, String toDate, String range) throws Exception {
		return getStatisticsDAO().getReqsttcsTotalCount(frDate, toDate, range);
	}	
	
}
