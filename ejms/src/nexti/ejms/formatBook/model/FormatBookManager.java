/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ڷ��� manager
 * ����:
 */
package nexti.ejms.formatBook.model;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;

public class FormatBookManager {
	
	private static Logger logger = Logger.getLogger(FormatBookManager.class);
	
	private static FormatBookManager instance = null;
	private static FormatBookDAO dao = null;
	
	private FormatBookManager() {
		
	}
	
	public static FormatBookManager instance() {
		
		if(instance == null)
			instance = new FormatBookManager();
		return instance;
	}

	private FormatBookDAO getFormatBookDAO() {
		
		if(dao == null)
			dao = new FormatBookDAO(); 
		return dao;
	}
	
	/**
	 * ������� üũ
	 * @param String fileDir
	 * @param int sysdocno
	 * @param int formseq(������ 0)
	 * @return List �������ϸ��(������ null)
	 * @throws Exception
	 */
	public List getExistBookFile(String fileDir, int sysdocno, int formseq) throws Exception {
		
		List result = null;
		List fileList = null;
		
		fileList = getFormatBookDAO().getExistBookFile(sysdocno, formseq);
		
		if(fileList != null) {
			for(int i = 0; i < fileList.size(); i++) {
				DataBookBean dbbean = (DataBookBean)fileList.get(i);
				
				File file = new File(fileDir + dbbean.getFilenm());
				if(file.exists() == false) {
					if(result == null) {
						result = new ArrayList();
					}
					result.add(dbbean);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * ���������� üũ
	 * @param String fileDir
	 * @param int formseq
	 * @return List �������ϸ��(������ null)
	 * @throws Exception
	 */
	public List getExistCommBookFile(String fileDir, int formseq) throws Exception {
		
		List result = null;
		List fileList = null;
		
		fileList = getFormatBookDAO().getExistCommBookFile(formseq);
		
		if(fileList != null) {
			for(int i = 0; i < fileList.size(); i++) {
				DataBookBean dbbean = (DataBookBean)fileList.get(i);
				
				File file = new File(fileDir + dbbean.getFilenm());
				if(file.exists() == false) {
					if(result == null) {
						result = new ArrayList();
					}
					result.add(dbbean);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * ����ߴ�������� üũ
	 * @param String fileDir
	 * @param int sysdocno
	 * @param int formseq
	 * @return List �������ϸ��(������ null)
	 * @throws Exception
	 */
	public List getExistUsedBookFile(String fileDir, int sysdocno, int formseq) throws Exception {
		
		List result = null;
		List fileList = null;
		
		fileList = getFormatBookDAO().getExistUsedBookFile(sysdocno, formseq);
		
		if(fileList != null) {
			for(int i = 0; i < fileList.size(); i++) {
				DataBookBean dbbean = (DataBookBean)fileList.get(i);
				
				File file = new File(fileDir + dbbean.getFilenm());
				if(file.exists() == false) {
					if(result == null) {
						result = new ArrayList();
					}
					result.add(dbbean);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * ī�װ� ��� ��������
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] ī�װ����
	 * @throws Exception 
	 */
	public String[] getListCategory(int sysdocno, int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListCategory(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * ������ ī�װ� ��� ��������
	 * @param int formseq
	 * @return String[] ī�װ����
	 * @throws Exception 
	 */
	public String[] getListCommCategory(int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListCommCategory(formseq);
		
		return result;
	}
	
	/**
	 * ����ߴ���� ī�װ� ��� ��������
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] ī�װ����
	 * @throws Exception 
	 */
	public String[] getListUsedCategory(int sysdocno, int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListUsedCategory(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * ���ϸ� ��������
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String ���ϸ�
	 * @throws Exception 
	 */
	public String getFilename(Connection conn, FileBookBean fbbean) throws Exception {
		
		String result = "";
		
		result = getFormatBookDAO().getFilename(conn, fbbean);
		
		return result;
	}
	
	/**
	 * �������ϸ� ��������
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String ���ϸ�
	 * @throws Exception 
	 */
	public String getCommFilename(Connection conn, FileBookBean fbbean) throws Exception {
		String result = "";
		
		result = getFormatBookDAO().getCommFilename(conn, fbbean);
		
		return result;
	}
	
	/**
	 * ���չ��� ��ü��� ���ϸ���Ʈ ��������
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List ���ϸ�(ArrayList)
	 * @throws Exception 
	 */
	public List getListAllFilename(Connection conn, int sysdocno) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListAllFilename(conn, sysdocno);
		
		return result;
	}
	
	/**
	 * ����ߴ���� ���ϸ���Ʈ ��������
	 * @param Connection conn
	 * @param int usedsysdocno
	 * @param int usedformseq
	 * @return List ���ϸ�(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFilename(Connection conn, int usedsysdocno, int usedformseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListUsedFilename(conn, usedsysdocno, usedformseq);
		
		return result;
	}
	
	/**
	 * ������ ���ϸ���Ʈ ��������
	 * @param int commformseq
	 * @return List ���ϸ�(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFilename(Connection conn, int commformseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListCommFilename(conn, commformseq);
		
		return result;
	}
	
	/**
	 * �����ڷ��� �߰�
	 * @param FormatBookBean ffbean
	 * @param FormatBean fbean
	 * @throws Exception 
	 */
	public int addFormatBook(FormatBookBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = ffbean.getSysdocno();
			int formseq = ffbean.getFormseq();
			String[] category = ffbean.getListcategorynm1();
			
			for(int i = 0; i < category.length; i++)
				result += getFormatBookDAO().addFormatBook(conn, sysdocno, formseq, category[i]);
			
			fmgr.addFormat(conn, fbean);			
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * �����ڷ��� ������ �߰�
	 * @param FormatBookBean ffbean
	 * @param FormatBean fbean
	 * @throws Exception 
	 */
	public int addCommFormatBook(FormatBookBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = ffbean.getFormseq();
			String[] category = ffbean.getListcategorynm1();
			
			for(int i = 0; i < category.length; i++)
				result += getFormatBookDAO().addCommFormatBook(conn, formseq, category[i]);
			
			fmgr.addCommFormat(conn, fbean);			
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * �����ڷ��� ����
	 * @param FormatBookBean ffbean
	 * @param FormatBean fbean
	 * @throws Exception 
	 */
	public int modifyFormatBook(FormatBookBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = ffbean.getSysdocno();
			int formseq = ffbean.getFormseq();
			String[] category = ffbean.getListcategorynm1();
			
			getFormatBookDAO().delFormatBook(conn, sysdocno, formseq);
			
			for(int i = 0; i < category.length; i++)
				result += getFormatBookDAO().addFormatBook(conn, sysdocno, formseq, category[i]);
			
			fmgr.modifyFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * ���������ڷ��� ����
	 * @param FormatBookBean ffbean
	 * @param FormatBean fbean
	 * @throws Exception 
	 */
	public int modifyCommFormatBook(FormatBookBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = ffbean.getFormseq();
			String[] category = ffbean.getListcategorynm1();
			
			getFormatBookDAO().delCommFormatBook(conn, formseq);
			
			for(int i = 0; i < category.length; i++)
				result += getFormatBookDAO().addCommFormatBook(conn, formseq, category[i]);
			
			fmgr.modifyCommFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * ���ο� �����Ϸù�ȣ ��������
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int �����Ϸù�ȣ
	 * @throws Exception 
	 */
	public int getNewFileSeq(Connection conn, int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewFileSeq(conn, sysdocno, formseq);
		
		return result;	
	}
	
	/**
	 * ���ο� �����Ϸù�ȣ ��������
	 * @param Connection conn
	 * @param int formseq
	 * @return int �����Ϸù�ȣ
	 * @throws Exception 
	 */
	public int getNewCommFileSeq(Connection conn, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewCommFileSeq(conn, formseq);
		
		return result;	
	}
	
	/**
	 * ���ο� �������ļ��� ��������
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int �������ļ���
	 * @throws Exception 
	 */
	public int getNewFileOrd(Connection conn, int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewFileOrd(conn, sysdocno, formseq);
		
		return result;	
	}
	
	/**
	 * �缮÷������ �߰�
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void addFileBook(FileBookBean fbbean) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			int sysdocno = fbbean.getSysdocno();
			int formseq = fbbean.getFormseq();
			
			fbbean.setFileseq(getNewFileSeq(conn, sysdocno, formseq));
			fbbean.setOrd(getNewFileOrd(conn, sysdocno, formseq));
			
			getFormatBookDAO().addFileBook(conn, fbbean);
			
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
	 * ������÷������ �߰�
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void addCommFileBook(FileBookBean fbbean) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			int formseq = fbbean.getFormseq();
			
			fbbean.setFileseq(getNewCommFileSeq(conn, formseq));
			
			getFormatBookDAO().addCommFileBook(conn, fbbean);
			
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
	 * ���÷������ ��ϰ�������(�ۼ�)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List ���÷�����ϸ��(ArrayList)
	 * @throws Exception 
	 */
	public List getListFileBook(int sysdocno, int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListFileBook(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * ������÷������ ��ϰ�������(�ۼ�)
	 * @param int formseq
	 * @return List ������÷�����ϸ��(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFileBook(int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListCommFileBook(formseq);
		
		return result;
	}
	
	/**
	 * ������÷������ ��ϰ�������(�ۼ�)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List ������÷�����ϸ��(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFileBook(int sysdocno, int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListUsedFileBook(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * ���÷������ ����
	 * @param FileBookBean fbbean
	 * @param ServletContext context
	 */
	public void delFileBook(FileBookBean fbbean, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			if(context != null) {
				String filename = getFilename(conn, fbbean);
				String delFile = filename;
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
			
			getFormatBookDAO().delFileBook(conn, fbbean);
			
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
	 * ������÷������ ����
	 * @param FileBookBean fbbean
	 * @param ServletContext context
	 */
	public void delCommFileBook(FileBookBean fbbean, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			if(context != null) {
				String filename = getCommFilename(conn, fbbean);
				String delFile = filename;
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
			
			getFormatBookDAO().delCommFileBook(conn, fbbean);
			
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
	 * �����Ѿ�� ÷������ ��ü����
	 * @param int sysdocno
	 * @param int formseq
	 * @param ServletContext context
	 */
	public void delAllFileBook(int sysdocno, int formseq, ServletContext context) throws Exception{
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			delAllFileBook(conn, sysdocno, formseq, context);
			
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
	 * �����Ѿ�� ÷������ ��ü����
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @param ServletContext context
	 */
	public void delAllFileBook(Connection conn, int sysdocno, int formseq, ServletContext context) throws Exception{
		
		List filenameList = getFormatBookDAO().getListFilename(conn, sysdocno, formseq);
		
		getFormatBookDAO().delAllFileBook(conn, sysdocno, formseq);
		
		for(int i = 0; i < filenameList.size(); i++) {
			String delFile = filenameList.get(i).toString();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
	}
	
	/**
	 * �����Ѿ�� �����ڷ�÷������ ��ü����
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @param ServletContext context
	 */
	public void delAllFileCompBook(Connection conn, int sysdocno, int formseq, ServletContext context) throws Exception{
		
		List filenameList = getFormatBookDAO().getListCompFilename(conn, sysdocno, formseq);
		
		getFormatBookDAO().delAllFileCompBook(conn, sysdocno, formseq);
		
		for(int i = 0; i < filenameList.size(); i++) {
			String delFile = filenameList.get(i).toString();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
	}
	
	/**
	 * ������ ������÷������ ��ü����
	 * @param int formseq
	 * @param ServletContext context
	 */
	public void delAllCommFileBook(int formseq, ServletContext context) throws Exception{
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			delAllCommFileBook(conn, formseq, context);
			
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
	 * ������ ������÷������ ��ü����
	 * @param Connection conn
	 * @param int formseq
	 * @param ServletContext context
	 */
	public void delAllCommFileBook(Connection conn, int formseq, ServletContext context) throws Exception{
		
		List filenameList = getFormatBookDAO().getListCommFilename(conn, formseq);
		
		getFormatBookDAO().delAllCommFileBook(conn, formseq);
		
		for(int i = 0; i < filenameList.size(); i++) {
			
			FileBookBean fbbean = (FileBookBean)filenameList.get(i);
			
			String delFile = fbbean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
	}
	
	/**
	 * ����� ���� ��������
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public FormatBookBean getFormatFormView(int sysdocno, int formseq) throws Exception{
		return getFormatBookDAO().getFormatFormView(sysdocno, formseq);
	}
	
	/**
	 * ���� ����ڷ� ���� - ������ �������� ��������
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getFileBookFrm(int sysdocno, int formseq) throws Exception {
		return getFormatBookDAO().getFileBookFrm(sysdocno, formseq);
	}
	
	/** 
	 * ������ �Է� ÷������ �߰�
	 * 
	 * @param dataBean : �Է��� ������
	 * @param fileBean : �Է��� ���� ������
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int InsertDataBookFrm(DataBookBean dataBean, FileBean fileBean, String mode) throws Exception {
		int result = 0;
		
		result = getFormatBookDAO().InsertDataBookFrm(dataBean, fileBean, mode);
		
		return result; 
		
	}
	
	/**
	 * ������ ÷������ ���� - ������ ÷������ ��������
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getDataBookFrm(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean) throws Exception {
		return getFormatBookDAO().getDataBookFrm(sysdocno, formseq, idsbbean);
	}
	
	/** 
	 * ������ �Է� ÷������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param fileseq : �����Ϸù�ȣ
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DeleteDataBookFrm(int sysdocno, int formseq, String deptcd, String usrid, int fileseq) throws Exception {
		return getFormatBookDAO().DeleteDataBookFrm(sysdocno, formseq, deptcd, usrid, fileseq);
	}
	
	/**
	 * ���÷������ ��ϰ�������(�ۼ�)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List ���÷�����ϸ��(ArrayList)
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, String rdb_sort, boolean isIncludeNotSubmitData) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getFormDataList(sysdocno, formseq, idsbbean, rdb_sort, isIncludeNotSubmitData);
		
		return result;
	}
	
	/** 
	 * ������ �������� 
	 * 
	 * @param dataList : �Է��� ������
	 * @param sessionId : �Է���
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookOrdUpdate(String ord_gubun, List dataList) throws Exception {
		return getFormatBookDAO().DataBookOrdUpdate(ord_gubun, dataList);
	}
	
	/** 
	 * ������ �Է� �����ڷ� ÷������ �߰�
	 * 
	 * @param dataBean : �Է��� ������
	 * @param fileBean : �Է��� ���� ������
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookCompFrm(DataBookBean dataBean, FileBean fileBean) throws Exception {
		return getFormatBookDAO().DataBookCompFrm(dataBean, fileBean);
	}
	
	/**
	 * ���������ڷ� ���� ��������
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public DataBookBean getDataBookCompView(int sysdocno, int formseq) throws Exception{
		return getFormatBookDAO().getDataBookCompView(sysdocno, formseq);
	}
	
	/** 
	 * ���������ڷ� ÷������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param fileseq : �����Ϸù�ȣ
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookCompDelete(int sysdocno, int formseq) throws Exception {
		return getFormatBookDAO().DataBookCompDelete(sysdocno, formseq);
	}	
	
	/**
	 * Ȯ���� üũ  ��������
	 * @param int sysdocno
	 * @param int formseq
	 * @return String Ȯ���ڰǼ�
	 * @throws Exception 
	 */
	public int getExtchk(int sysdocno, int formseq) throws Exception {
		int result = 0;
		
		result = getFormatBookDAO().getExtchk(sysdocno, formseq);
		
		return result;
	}	
}