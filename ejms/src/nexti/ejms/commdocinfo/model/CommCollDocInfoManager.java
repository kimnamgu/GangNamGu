/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 manager
 * 설명:
 */
package nexti.ejms.commdocinfo.model;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commdocinfo.model.CommCollDocInfoDAO;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.Utils;

public class CommCollDocInfoManager {
	private static Logger logger = Logger.getLogger(CommCollDocInfoManager.class);
	
	private static CommCollDocInfoManager instance = null;
	private static CommCollDocInfoDAO dao = null;
	
	public static CommCollDocInfoManager instance() {
		if (instance==null) instance = new CommCollDocInfoManager(); 
		return instance;
	}
	
	private CommCollDocInfoDAO getCommDocInfoDAO(){
		if (dao==null) dao = new CommCollDocInfoDAO(); 
		return dao;
	}
	
	private CommCollDocInfoManager() {
	}
	
	/**
	 * 취합문서정보 데이터 보기
	 * @throws Exception 
	 */
	public CommCollDocInfoBean viewCommCollDocInfo(int sysdocno) throws Exception {
		CommCollDocInfoBean bean = getCommDocInfoDAO().viewCommCollDocInfo(sysdocno);
		
		try {
			if ("1900-01-01".equals(bean.getOpendt()) ) {
				bean.setOpennm("공개");
			} else if ("9999-12-31".equals(bean.getOpendt()) ) {
				bean.setOpennm("비공개");
			} else if ("1900-01-04".equals(bean.getOpendt()) ) {
				bean.setOpennm("취합/입력자에게공개");
			} else if (Utils.isNull(bean.getOpendt())) {
			} else {
				bean.setOpennm("기한부공개("+DateTime.chDateFormat(DateTime.getDate(bean.getOpendt(), "yyyy-MM-dd"), "yyyy년 MM월 dd일")+")");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return bean;
	}
	
	public int saveCommCollDocInfo(CommCollDocInfoBean bean, int sysdocno, String sessionId, ServletContext context, String saveDir) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			result = getCommDocInfoDAO().saveCommCollDocInfo(bean, sysdocno, sessionId);
			
			if ( result > 0 ) {
				ColldocBean cdbean = new ColldocBean();
				cdbean.setSysdocno(sysdocno);
				cdbean.setAttachFile(bean.getAttachFile());
				cdbean.setAttachFileYN(bean.getAttachFileYN());
				ColldocManager cdmgr = ColldocManager.instance();
				cdmgr.saveColldocFile(conn, cdbean, context, saveDir);
			}

			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 공유 목록
	 * @throws Exception 
	 */
	public List getExhibitList(CommCollDocSearchBean searchBean, int start, int end) throws Exception {
		return getCommDocInfoDAO().getExhibitList(searchBean, start, end);
	}

	/**
	 * 공유 목록 건수
	 * @throws Exception 
	 */
	public int getExhibitTotCnt(CommCollDocSearchBean searchBean) throws Exception {
		return getCommDocInfoDAO().getExhibitTotCnt(searchBean);
	}
	
}
