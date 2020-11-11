/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڵ���� manager
 * ����:
 */
package nexti.ejms.ccd.model;

import java.util.List;

public class CcdManager {
	private static CcdManager instance = null;
	private static CcdDAO dao = null;
	
	public static CcdManager instance() {
		if (instance==null) instance = new CcdManager(); 
		return instance;
	}
	
	private CcdDAO getCcdDAO(){
		if (dao==null) dao = new CcdDAO(); 
		return dao;
	}
	
	private CcdManager() {
	}
	
	/**
	 * �ڵ��Ī ��������
	 */
	public String getCcdName(String ccdcd, String ccdsubcd) throws Exception {
		String result = "";
		
		result = getCcdDAO().getCcdName(ccdcd, ccdsubcd);
		
		return result;
	}
	
	/** 
	 * �����ڵ� �߰�	
	 */
	public int insertCcd (CcdBean bean) throws Exception {
		return getCcdDAO().insertCcd(bean);
	}

	/** 
	 * �����ڵ� ����	
	 */
	public int modifyCcd (CcdBean bean) throws Exception {
		return getCcdDAO().modifyCcd(bean);
	}

	/** 
	 * �����ڵ� ����	
	 */
	public int deleteCcd (String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().deleteCcd(p_ccd_cd, p_ccd_sub_cd);
	}

	/** 
	 * �����ڵ� ��� - �˻����Ǿ���	
	 * param : gbn - �ý��ۿ�(s), �����ڿ�(a)����
	 * */
	public List mainCodeList (String gbn) throws Exception {
		return getCcdDAO().mainCodeList(gbn);
	}

	/** 
	 * �����ڵ� �󼼺���	
	 */
	public CcdBean detailCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().detailCcd(p_ccd_cd, p_ccd_sub_cd);
	}

	/** 
	 * �����ڵ� �������� 
	 * return true : ������
	 * return false : �������
	 * */
	public boolean existedCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().existedCcd(p_ccd_cd, p_ccd_sub_cd);
	}
	
	/** 
	 * Ư�� �������ڵ�(CCD_CD)�� ���� SUB List
	 */
	public List subCodeList(String ccd_cd) throws Exception {
		List subList = null;
		
		subList = getCcdDAO().subCodeList(ccd_cd);
		
		return subList;
	}

	/** 
	 * ���ڵ�� ��������
	 */
	public String getCcd_Name(String p_ccd_cd) throws Exception {
		return getCcdDAO().getCcd_Name(p_ccd_cd);
	}
	
	/** 
	 * ���ڵ�� ��������
	 */
	public String getCcd_SubName(String p_ccd_cd, String p_sub_ccd_cd) throws Exception {
		return getCcdDAO().getCcd_SubName(p_ccd_cd, p_sub_ccd_cd);
	}
}
