/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ��� manager
 * ����:
 */
package nexti.ejms.colldoc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class ColldocManager {
	
	private static Logger logger = Logger.getLogger(ColldocManager.class);
	
	private static ColldocManager instance = null;
	private static ColldocDAO dao = null;
	
	private ColldocManager() {
		
	}
	
	public static ColldocManager instance() {
		
		if(instance == null)
			instance = new ColldocManager();
		return instance;
	}

	private ColldocDAO getColldocDAO() {
		
		if(dao == null)
			dao = new ColldocDAO(); 
		return dao;
	}
	
	/**
	 * �ۼ��ߴܵ� �ý��۹�����ȣ ���
	 * @return String[] �ý��۹�����ȣ��
	 * @throws Exception 
	 */
	public String[] getListCancelColldoc() throws Exception {
		
		String[] result = null;
		
		result = getColldocDAO().getListCancelColldoc();
		
		return result;
	}
	
	/**
	 * ��Ͽ��� ���̱�/�Ⱥ��̱� ����
	 * @param Connection conn
	 * @param int sysdocno
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int setDelyn(Connection conn, int sysdocno, int delyn) throws Exception {
		int result = 0;
		
		result = getColldocDAO().setDelyn(conn, sysdocno, delyn);
		
		return result;
	}
	
	/**
	 * ��Ͽ��� ���̱�/�Ⱥ��̱� ����
	 * @param int sysdocno
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int setDelyn(int sysdocno, int delyn) throws Exception {

		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = setDelyn(conn, sysdocno, delyn);

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
	 * ��������ȣ ��������
	 * @param Connection conn
	 * @param String deptcd
	 * @return String ��������ȣ
	 * @throws Exception 
	 */
	public String getDocno(Connection conn, String deptcd) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		
		String year = Integer.toString(cal.get(Calendar.YEAR));
		
		int docno = getColldocDAO().getNextDocno(conn, year, deptcd);
		
		DeptManager dmgr = DeptManager.instance();
		
		DeptBean dbean = dmgr.getDeptInfo(deptcd);
		
		String deptnm = dbean.getDept_name();
		
		String result = "[����]" + deptnm + "-" + docno;
		
		setDocno(conn, deptcd);
		
		return result;
	}
	
	/**
	 * ������ȣ ä��
	 * @param Connection conn
	 * @param String deptcd
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int setDocno(Connection conn, String deptcd) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		
		String year = Integer.toString(cal.get(Calendar.YEAR));
		
		int seq = getColldocDAO().getNextDocno(conn, year, deptcd);
		
		DeptManager dmgr = DeptManager.instance();
		
		DeptBean dbean = dmgr.getDeptInfo(deptcd);
		
		String deptnm = dbean.getDept_name();
		
		int result = 0;
			
		result = getColldocDAO().setDocno(conn, year, deptcd, seq, deptnm);
		
		return result;
	}
	
	/**
	 * ���չ������� ��������
	 * @param String user_id
	 * @param String searchvalue
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int getCountColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		int result = 0;
		
		result = getColldocDAO().getCountColldoc(user_id, initentry, isSysMgr, searchvalue, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	/**
	 * ���չ����� ��İ��� ��������
	 * @param int sysdocno
	 * @return int ��İ���
	 * @throws Exception 
	 */
	public int getCountFormat(int sysdocno) throws Exception {
		int result = 0;
		
		result = getColldocDAO().getCountFormat(sysdocno);
		
		return result;
	}
	
	/**
	 * ���չ������ ��������
	 * @param String user_id
	 * @param String searchvalue
	 * @param int start
	 * @param int end
	 * @return List ���չ������(ArrayList)
	 * @throws Exception 
	 */
	public List getListColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		
		List result = null;
		
		result = getColldocDAO().getListColldoc(user_id, initentry, isSysMgr, searchvalue, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * ���վ���ڷ��� ��������
	 * @param int sysdocno
	 * @return List ���վ���ڷ���(ArrayList)
	 * @throws Exception 
	 */
	public List getListFormat(int sysdocno) throws Exception {

		Connection conn = null;
		
		List list = null;
		
		try {			
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			list = getListFormat(conn, sysdocno);
			
			conn.commit();
		} catch(Exception e) {	
			try {conn.rollback();} catch(Exception ex) {}
			ConnectionManager.close(conn);
			throw e;
	    } finally {
	    	ConnectionManager.close(conn);
	    }
	
		return list;
	}
	
	/**
	 * ���վ���ڷ��� ��������
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List ���վ���ڷ���(ArrayList)
	 * @throws Exception 
	 */
	public List getListFormat(Connection conn, int sysdocno) throws Exception {
		
		List result = null;
		
		result = getColldocDAO().getListFormat(conn, sysdocno);
		
		return result;		
	}
	
	/**
	 * ������ ���չ��� ����
	 * @param int sysdocno
	 * @return ColldocBean ���չ���������
	 * @throws Exception 
	 */
	public ColldocBean getColldoc(int sysdocno) throws Exception {
		
		ColldocBean result = null;
		
		result = getColldocDAO().getColldoc(sysdocno);
		
		return result;
	}
	
	public int saveColldocFile(Connection conn, ColldocBean cdbean, ServletContext context, String savrDir) throws Exception {
		int result = 0;
		
		if ( cdbean.getAttachFileYN().equals("N") == true ||
				(cdbean.getAttachFile() != null && cdbean.getAttachFile().getFileName().trim().equals("") == false) ) {
			result += delColldocFile(conn, cdbean.getSysdocno(), 1, context);
		}
		if ( cdbean.getAttachFileYN().equals("N") == false &&
				cdbean.getAttachFile() != null && cdbean.getAttachFile().getFileName().equals("") == false ) {
			delColldocFile(conn, cdbean.getSysdocno(), 1, context);	//÷�������� �Ѱ� �̻��� �� �ʿ����
			result += addColldocFile(conn, cdbean, context, savrDir);
		}
		
		return result;
	}
	
	/**
	 * �� ���չ��� �߰�
	 * @param DataTransferBean cdbean
	 * @param String filedir
	 * @param String sessi
	 * @param int savemode
	 * @return int ���ý��۹�����ȣ
	 */
	public int saveColldoc(ColldocBean cdbean, String filedir, String sessi, int savemode, ServletContext context, String saveDir) throws Exception {
		
		String user_id = cdbean.getUptusrid();
		int sysdocno = cdbean.getSysdocno();
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//1:��������, 2:�������� ����, 3:����, 4:�����ϰ�纻����, 5:�ӽ�����, 6:����
			//mode=5�� �����ۼ� ���� ������ �ӽ�����(��Ͽ��� �Ⱥ���)�ϱ� ���� ���
			//mode=6�� ���� ���� ������ ������������ ���� �ϵ��� �ϱ� ���� ���(�����)
			if(savemode == 1 || savemode == 5) {			//��������
				
				sysdocno = getColldocDAO().newColldoc(conn, cdbean);
				
				cdbean.setSysdocno(sysdocno);
				saveColldocFile(conn, cdbean, context, saveDir);
				
				addColldocTempData_TGT_SANC(conn, sessi, sysdocno);
	
				delColldocTempData_TGT_SANC(conn, sessi);
				
			} else if(savemode == 2) {	//�������� ����
				
				sysdocno = getColldocDAO().newColldoc(conn, cdbean);
				
				copyColldocData(conn, user_id, cdbean.getSysdocno(), sysdocno);
				
				copyColldocFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
				
				copyFormatFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
				
				cdbean.setSysdocno(sysdocno);
				saveColldocFile(conn, cdbean, context, saveDir);
				
			} else if(savemode == 3 || savemode == 6) {	//����
				
				getColldocDAO().saveColldoc(conn, cdbean);
				
				saveColldocFile(conn, cdbean, context, saveDir);
				
			} else if(savemode == 4) {	//�����ϰ� �纻����
				
				getColldocDAO().saveColldoc(conn, cdbean);
				
				saveColldocFile(conn, cdbean, context, saveDir);
				
				sysdocno = getColldocDAO().copyColldoc(conn, cdbean);
				
				copyColldocData(conn, user_id, cdbean.getSysdocno(), sysdocno);
				
				copyColldocFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
				
				copyFormatFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
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
		
		return sysdocno;
	}
	
	/**
	 * ������� ���� ����
	 * @param Connection conn
	 * @param String filedir
	 * @param int sysdocno
	 * @param int newsysdocno
	 * @throws Exception 
	 */
	public int copyFormatFile(Connection conn, String filedir, int sysdocno, int newsysdocno) throws Exception {
		
		int result = 0;
		
		Calendar cal = Calendar.getInstance();
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		String srcFile, descFile;
		
		List filenameList = fbmgr.getListAllFilename(conn, sysdocno);
		
		for(int i = 0; i < filenameList.size(); i++) {
			
			FileBookBean fbbean = (FileBookBean)filenameList.get(i);
			
			fbbean.setSysdocno(sysdocno);

			String filenm = fbbean.getFilenm();
			
			srcFile = filedir + filenm;
			
			descFile = FileManager.doFileCopy(srcFile);
			
			descFile = appInfo.getBookFrmSampleDir() + cal.get(Calendar.YEAR) + "/" + descFile;
			
			result += getColldocDAO().copyFormatFile(conn, fbbean, descFile, newsysdocno);
		}
		
		return result;
	}
	
	/**
	 * ���չ��� ÷������ ����
	 * @param Connection conn
	 * @param String filedir
	 * @param int sysdocno
	 * @param int newsysdocno
	 * @throws Exception 
	 */
	public int copyColldocFile(Connection conn, String filedir, int sysdocno, int newsysdocno) throws Exception {
		
		int result = 0;
		
		Calendar cal = Calendar.getInstance();
		
		String srcFile, descFile;
		
		List filenameList = getListColldocFile(conn, sysdocno);
		
		for(int i = 0; i < filenameList.size(); i++) {
			
			ColldocFileBean cdfbean = (ColldocFileBean)filenameList.get(i);
			
			cdfbean.setSysdocno(sysdocno);

			String filenm = cdfbean.getFilenm();
			
			srcFile = filedir + filenm;
			
			try {
				descFile = FileManager.doFileCopy(srcFile);
			
				descFile = appInfo.getColldocSampleDir() + cal.get(Calendar.YEAR) + "/" + descFile;
				
				result += getColldocDAO().copyColldocFile(conn, cdfbean, descFile, newsysdocno);
			} catch (FileNotFoundException fnfe) {
				logger.error("������ ã�� �� �����ϴ� : " + srcFile);
			}
		}
		
		return result;
	}
	
	/**
	 * ���մ��μ�,���缱 ���� ����
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int newsysdocno
	 * @throws Exception 
	 */
	public void copyColldocData(Connection conn, String user_id, int sysdocno, int newsysdocno) throws Exception {
		
		getColldocDAO().copyColldocData(conn, user_id, sysdocno, newsysdocno);
	}

	/**
	 * ���չ��� ������ ���·� �����ϱ�
	 * @param String user_id
	 * @param String dept_code
	 * @param int sysdocno
	 * @param String docstate
	 * @throws Exception 
	 */
	public int appovalColldoc(String user_id, String dept_code, int sysdocno, String docstate) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			ColldocManager cdmgr = ColldocManager.instance();
			
			String docno = "";
			if(docstate.equals("04") == true) {			
				docno = cdmgr.getDocno(conn, dept_code);
			}
			
			result = getColldocDAO().appovalColldoc(conn, user_id, sysdocno, docstate, docno);
			if ( result > 0 ) {
				setDelyn(conn, sysdocno, 1);
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
	 * �����ѹ��� ����
	 * @param String[] deletelist
	 * @param ServletContext context
	 * @throws Exception 
	 */
	public void delColldoc(String[] deletelist, ServletContext context) throws Exception {
		
		if(deletelist == null) return;
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			getColldocDAO().delColldoc(conn, deletelist);
			
			FormatManager fmgr = FormatManager.instance();
			
			for(int i = 0; i < deletelist.length; i++) {
				
				int sysdocno = Integer.parseInt(deletelist[i], 10);
				
				delColldocFile(conn, sysdocno, 1, context);	//���� ÷�������� �Ѱ��� �߰���
			
				List listform = getListFormat(conn, sysdocno);
				
				for(int j = 0; j < listform.size(); j++) {
					
					int formseq = ((ColldocBean)listform.get(j)).getFormseq();
				
					fmgr.delFormat(conn, sysdocno, formseq, context);
				}
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
	}
	
	/**
	 * ���չ��� ÷������ �߰�
	 * @param conn
	 * @param cdbean
	 * @param context
	 * @param saveDir
	 * @return
	 * @throws Exception
	 */
	public int addColldocFile(Connection conn, ColldocBean cdbean, ServletContext context, String saveDir) throws Exception {
		
		int result = 0;
		
		File saveFolder = new File(context.getRealPath(saveDir));
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		//���Ͼ��ε�
		FileBean fileBean = new FileBean();
		fileBean = FileManager.doFileUpload(cdbean.getAttachFile(), context, saveDir);
		if(fileBean != null) {
			fileBean.setSeq(cdbean.getSysdocno());
			result = getColldocDAO().addColldocFile(conn, fileBean);
		}
		
		return result;
	}
	
	/**
	 * ���չ��� ÷������ ����
	 * @param conn
	 * @param sysdocno
	 * @param fileseq
	 * @param context
	 * @throws Exception
	 */
	public int delColldocFile(Connection conn, int sysdocno, int fileseq, ServletContext context) throws Exception {
		
		int result = 0;
		
		ColldocFileBean cdfbean = getColldocFile(conn, sysdocno, fileseq);

		if ( cdfbean != null ) {
			result += getColldocDAO().delColldocFile(conn, sysdocno, fileseq);
			
			String delFile = cdfbean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		return result;
	}
	
	/**
	 * ���չ��� ÷������ ���� 
	 * @param conn
	 * @param sysdocno
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public ColldocFileBean getColldocFile(Connection conn, int sysdocno, int fileseq) throws Exception {
		
		ColldocFileBean result = null;
		
		result = getColldocDAO().getColldocFile(conn, sysdocno, fileseq);
			
		return result;
	}
	
	/**
	 * ���չ��� ÷������ ���� 
	 * @param conn
	 * @param sysdocno
	 * @return
	 * @throws Exception
	 */
	public List getListColldocFile(Connection conn, int sysdocno) throws Exception {
		
		List result = null;
		
		result = getColldocDAO().getListColldocFile(conn, sysdocno);
			
		return result;
	}
	
	/**
	 * ���չ������� üũ
	 * @param String fileDir
	 * @param int sysdocno
	 * @return List �������ϸ��(������ null)
	 * @throws Exception
	 */
	public List getExistColldocFile(String fileDir, int sysdocno) throws Exception {
		Connection conn = null;
		List result = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			List fileList = getColldocDAO().getListColldocFile(conn, sysdocno);
			
			if(fileList != null) {
				for(int i = 0; i < fileList.size(); i++) {
					ColldocFileBean cdbean = (ColldocFileBean)fileList.get(i);
					
					File file = new File(fileDir + cdbean.getFilenm());
					if(file.exists() == false) {
						if(result == null) {
							result = new ArrayList();
						}
						result.add(cdbean);
					}
				}
			}
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
	 * ���������� ���մ��μ�,���缱 �ӽ����̺� ������ �������̺�� ����
	 * @param Connection conn
	 * @param String sessi
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public void addColldocTempData_TGT_SANC(Connection conn, String sessi, int sysdocno) throws Exception {
		
		getColldocDAO().addColldocTempData_TGT_SANC(conn, sessi, sysdocno);
	}
	
	/**
	 * ���������� ���մ��μ�,���缱 �ӽ����̺� ������ ����
	 * @param Connection conn
	 * @param String sessi
	 * @throws Exception 
	 */
	public void delColldocTempData_TGT_SANC(Connection conn, String sessi) throws Exception {
		
		getColldocDAO().delColldocTempData_TGT_SANC(conn, sessi);
	}
	
	/**
	 * ���������� ���մ��μ�,���缱 �ӽ����̺� ������ ����
	 * @param String sessi
	 * @throws Exception 
	 */
	public void delColldocTempData_TGT_SANC(String sessi) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
		
			delColldocTempData_TGT_SANC(conn, sessi);
		
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
	}
	
	/**
	 * �����������չ��� ���
	 * @param String sessionId
	 * @param int start
	 * @param int end
	 * @return List ���չ������(ArrayList)
	 * @throws Exception 
	 */
	public List getCollProcList(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getColldocDAO().getCollProcList(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * �������� ���չ��� ��� ���� ��������
	 * @param String sessionId
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int getCollProcTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		int result = 0;
		
		result = getColldocDAO().getCollProcTotCnt(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}

	/**
	 * �������� ���չ��� ���վ�� ���� ������ ����
	 * @param int sysdocno
	 * @return List ���չ���
	 * @throws Exception 
	 */
	public int collprocClose(CollprocBean bean, String tgtdeptcd, String sessionId ) throws Exception{
		CollprocBean collprocBean = new CollprocBean();
		
		if(bean.getRadiochk().equals("1")){	//����
			collprocBean.setClosedate("1900-01-01");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else if(bean.getRadiochk().equals("2")){	//�����
			collprocBean.setClosedate("9999-12-31");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else if(bean.getRadiochk().equals("4")){	//����/�Է��ڿ��԰���
			collprocBean.setClosedate("1900-01-04");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else{	//���������
			try {
				BeanUtils.copyProperties(collprocBean, bean);
				if ( "".equals(Utils.nullToEmptyString(collprocBean.getClosedate())) ) collprocBean.setClosedate("1900-01-01");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return getColldocDAO().collprocClose(collprocBean, tgtdeptcd, sessionId);
	}
	
	
	/**
	 * �������� ���չ��� ������ ���μ���
	 * @param int sysdocno
	 * @param String sessionId
	 * @param String tgtdeptcd
	 * @return int ó���Ǽ�
	 */
	public int collprocProcess(int sysdocno, String sessionId, String tgtdeptcd ) throws Exception{
		
		return getColldocDAO().collprocProcess(sysdocno, sessionId, tgtdeptcd);
		
	}
	
	/**
	 * ���չ����Ϸ� ���
	 * @param String deptcd
	 * @param String sessionId
	 * @param int start
	 * @param int end
	 * @return List ���չ������(ArrayList)
	 * @throws Exception 
	 */
	public List getCollcompList(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getColldocDAO().getCollcompList(usrid, deptcd, searchvalue, selyear, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * �������� ���չ��� ��� ���� ��������
	 * @param String deptcd
	 * @param String sessionId
	 * @return int ���չ�������
	 * @throws Exception 
	 */
	public int getCollcompTotCnt(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		int result = 0;
		
		result = getColldocDAO().getCollcompTotCnt(usrid, deptcd, searchvalue, selyear, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	
	/**
	 * ���տϷṮ�� ������� ���μ���
	 * @param int sysdocno
	 * @param String sessionId
	 * @return int ���չ�������
	 */
	public int collCompProcess(int sysdocno, String user_id) throws Exception{
		
		return getColldocDAO().collCompProcess(sysdocno, user_id);
		
	}
	
	/**
	 * �������� ��������
	 * @param int sysdocno
	 * @return CollprocBean ��������
	 * @throws Exception 
	 */
	public CollprocBean  getDocState(int sysdocno) throws Exception{
		
		return getColldocDAO().getDocState(sysdocno);
			 
	}	
	
	/**
	 * ������Ȳ �Ǽ� �������� -(����ȭ��)
	 * stepGbn : ��������(1), ��������(2), �������(3)
	 * @throws Exception 
	 */
	public int procCount(String stepGbn, String user_id, String deptcd) throws Exception {	
		int result = 0;
		
		result = getColldocDAO().procCount(stepGbn, user_id, deptcd);
		
		return result;
	}
	
	/**
	 * �������� ��������  ��������
	 * @param int sysdocno
	 * @return CollprocBean ��������
	 * @throws Exception 
	 */
	public CollprocBean  getCloseView(int sysdocno) throws Exception{
		
		return getColldocDAO().getCloseView(sysdocno);
			 
	}	
	
	/**
	 * �ֱ����չ��� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getSearchDoc(String searchvalue, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchDoc(searchvalue, sch_deptcd, sch_userid);
	}
	
	/**
	 * �����������չ��� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getSearchProc( String docstate, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchProc(docstate, sch_deptcd, sch_userid);
	}
	
	/**
	 * ���տϷ� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getSearchComp(String searchvalue, String selyear, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchComp(searchvalue, selyear, sch_deptcd, sch_userid);
	}
}