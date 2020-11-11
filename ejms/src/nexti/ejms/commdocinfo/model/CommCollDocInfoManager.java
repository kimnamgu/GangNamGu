/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� manager
 * ����:
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
	 * ���չ������� ������ ����
	 * @throws Exception 
	 */
	public CommCollDocInfoBean viewCommCollDocInfo(int sysdocno) throws Exception {
		CommCollDocInfoBean bean = getCommDocInfoDAO().viewCommCollDocInfo(sysdocno);
		
		try {
			if ("1900-01-01".equals(bean.getOpendt()) ) {
				bean.setOpennm("����");
			} else if ("9999-12-31".equals(bean.getOpendt()) ) {
				bean.setOpennm("�����");
			} else if ("1900-01-04".equals(bean.getOpendt()) ) {
				bean.setOpennm("����/�Է��ڿ��԰���");
			} else if (Utils.isNull(bean.getOpendt())) {
			} else {
				bean.setOpennm("���Ѻΰ���("+DateTime.chDateFormat(DateTime.getDate(bean.getOpendt(), "yyyy-MM-dd"), "yyyy�� MM�� dd��")+")");
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
	 * ���� ���
	 * @throws Exception 
	 */
	public List getExhibitList(CommCollDocSearchBean searchBean, int start, int end) throws Exception {
		return getCommDocInfoDAO().getExhibitList(searchBean, start, end);
	}

	/**
	 * ���� ��� �Ǽ�
	 * @throws Exception 
	 */
	public int getExhibitTotCnt(CommCollDocSearchBean searchBean) throws Exception {
		return getCommDocInfoDAO().getExhibitTotCnt(searchBean);
	}
	
}
