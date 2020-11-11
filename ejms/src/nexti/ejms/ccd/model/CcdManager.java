/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리 manager
 * 설명:
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
	 * 코드명칭 가져오기
	 */
	public String getCcdName(String ccdcd, String ccdsubcd) throws Exception {
		String result = "";
		
		result = getCcdDAO().getCcdName(ccdcd, ccdsubcd);
		
		return result;
	}
	
	/** 
	 * 공통코드 추가	
	 */
	public int insertCcd (CcdBean bean) throws Exception {
		return getCcdDAO().insertCcd(bean);
	}

	/** 
	 * 공통코드 수정	
	 */
	public int modifyCcd (CcdBean bean) throws Exception {
		return getCcdDAO().modifyCcd(bean);
	}

	/** 
	 * 공통코드 삭제	
	 */
	public int deleteCcd (String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().deleteCcd(p_ccd_cd, p_ccd_sub_cd);
	}

	/** 
	 * 공통코드 목록 - 검색조건없음	
	 * param : gbn - 시스템용(s), 관리자용(a)구분
	 * */
	public List mainCodeList (String gbn) throws Exception {
		return getCcdDAO().mainCodeList(gbn);
	}

	/** 
	 * 공통코드 상세보기	
	 */
	public CcdBean detailCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().detailCcd(p_ccd_cd, p_ccd_sub_cd);
	}

	/** 
	 * 공통코드 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 * */
	public boolean existedCcd(String p_ccd_cd, String p_ccd_sub_cd) throws Exception {
		return getCcdDAO().existedCcd(p_ccd_cd, p_ccd_sub_cd);
	}
	
	/** 
	 * 특정 마스터코드(CCD_CD)에 대한 SUB List
	 */
	public List subCodeList(String ccd_cd) throws Exception {
		List subList = null;
		
		subList = getCcdDAO().subCodeList(ccd_cd);
		
		return subList;
	}

	/** 
	 * 주코드명 가져오기
	 */
	public String getCcd_Name(String p_ccd_cd) throws Exception {
		return getCcdDAO().getCcd_Name(p_ccd_cd);
	}
	
	/** 
	 * 부코드명 가져오기
	 */
	public String getCcd_SubName(String p_ccd_cd, String p_sub_ccd_cd) throws Exception {
		return getCcdDAO().getCcd_SubName(p_ccd_cd, p_sub_ccd_cd);
	}
}
