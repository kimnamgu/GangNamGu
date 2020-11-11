/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 제본자료형 manager
 * 설명:
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
	 * 양식파일 체크
	 * @param String fileDir
	 * @param int sysdocno
	 * @param int formseq(생략시 0)
	 * @return List 없는파일목록(없을시 null)
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
	 * 공통양식파일 체크
	 * @param String fileDir
	 * @param int formseq
	 * @return List 없는파일목록(없을시 null)
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
	 * 사용했던양식파일 체크
	 * @param String fileDir
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 없는파일목록(없을시 null)
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
	 * 카테고리 목록 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListCategory(int sysdocno, int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListCategory(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 공통양식 카테고리 목록 가져오기
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListCommCategory(int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListCommCategory(formseq);
		
		return result;
	}
	
	/**
	 * 사용했던양식 카테고리 목록 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListUsedCategory(int sysdocno, int formseq) throws Exception {
		
		String[] result = null;
		
		result = getFormatBookDAO().getListUsedCategory(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 파일명 가져오기
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String 파일명
	 * @throws Exception 
	 */
	public String getFilename(Connection conn, FileBookBean fbbean) throws Exception {
		
		String result = "";
		
		result = getFormatBookDAO().getFilename(conn, fbbean);
		
		return result;
	}
	
	/**
	 * 공통파일명 가져오기
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String 파일명
	 * @throws Exception 
	 */
	public String getCommFilename(Connection conn, FileBookBean fbbean) throws Exception {
		String result = "";
		
		result = getFormatBookDAO().getCommFilename(conn, fbbean);
		
		return result;
	}
	
	/**
	 * 취합문서 전체양식 파일명리스트 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListAllFilename(Connection conn, int sysdocno) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListAllFilename(conn, sysdocno);
		
		return result;
	}
	
	/**
	 * 사용했던양식 파일명리스트 가져오기
	 * @param Connection conn
	 * @param int usedsysdocno
	 * @param int usedformseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFilename(Connection conn, int usedsysdocno, int usedformseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListUsedFilename(conn, usedsysdocno, usedformseq);
		
		return result;
	}
	
	/**
	 * 공통양식 파일명리스트 가져오기
	 * @param int commformseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFilename(Connection conn, int commformseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListCommFilename(conn, commformseq);
		
		return result;
	}
	
	/**
	 * 제본자료형 추가
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
	 * 제본자료형 공통양식 추가
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
	 * 제본자료형 수정
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
	 * 공통제본자료형 수정
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
	 * 새로운 파일일련번호 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일일련번호
	 * @throws Exception 
	 */
	public int getNewFileSeq(Connection conn, int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewFileSeq(conn, sysdocno, formseq);
		
		return result;	
	}
	
	/**
	 * 새로운 파일일련번호 가져오기
	 * @param Connection conn
	 * @param int formseq
	 * @return int 파일일련번호
	 * @throws Exception 
	 */
	public int getNewCommFileSeq(Connection conn, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewCommFileSeq(conn, formseq);
		
		return result;	
	}
	
	/**
	 * 새로운 파일정렬순서 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일정렬순서
	 * @throws Exception 
	 */
	public int getNewFileOrd(Connection conn, int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatBookDAO().getNewFileOrd(conn, sysdocno, formseq);
		
		return result;	
	}
	
	/**
	 * 양석첨부파일 추가
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
	 * 공통양식첨부파일 추가
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
	 * 양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListFileBook(int sysdocno, int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListFileBook(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 공통양식첨부파일 목록가져오기(작성)
	 * @param int formseq
	 * @return List 공통양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFileBook(int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListCommFileBook(formseq);
		
		return result;
	}
	
	/**
	 * 공통양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 공통양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFileBook(int sysdocno, int formseq) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getListUsedFileBook(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 양식첨부파일 삭제
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
	 * 공통양식첨부파일 삭제
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
	 * 선택한양식 첨부파일 전체삭제
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
	 * 선택한양식 첨부파일 전체삭제
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
	 * 선택한양식 최종자료첨부파일 전체삭제
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
	 * 선택한 공통양식첨부파일 전체삭제
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
	 * 선택한 공통양식첨부파일 전체삭제
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
	 * 양식폼 내용 가져오기
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public FormatBookBean getFormatFormView(int sysdocno, int formseq) throws Exception{
		return getFormatBookDAO().getFormatFormView(sysdocno, formseq);
	}
	
	/**
	 * 공통 양식자료 보기 - 제본형 샘플파일 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getFileBookFrm(int sysdocno, int formseq) throws Exception {
		return getFormatBookDAO().getFileBookFrm(sysdocno, formseq);
	}
	
	/** 
	 * 제본형 입력 첨부파일 추가
	 * 
	 * @param dataBean : 입력할 데이터
	 * @param fileBean : 입력할 파일 데이터
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
	 * 제본형 첨부파일 보기 - 제본형 첨부파일 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getDataBookFrm(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean) throws Exception {
		return getFormatBookDAO().getDataBookFrm(sysdocno, formseq, idsbbean);
	}
	
	/** 
	 * 제본형 입력 첨부파일 삭제
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param fileseq : 파일일련번호
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DeleteDataBookFrm(int sysdocno, int formseq, String deptcd, String usrid, int fileseq) throws Exception {
		return getFormatBookDAO().DeleteDataBookFrm(sysdocno, formseq, deptcd, usrid, fileseq);
	}
	
	/**
	 * 양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, String rdb_sort, boolean isIncludeNotSubmitData) throws Exception {
		
		List result = null;
		
		result = getFormatBookDAO().getFormDataList(sysdocno, formseq, idsbbean, rdb_sort, isIncludeNotSubmitData);
		
		return result;
	}
	
	/** 
	 * 제본형 순서적용 
	 * 
	 * @param dataList : 입력할 데이터
	 * @param sessionId : 입력자
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookOrdUpdate(String ord_gubun, List dataList) throws Exception {
		return getFormatBookDAO().DataBookOrdUpdate(ord_gubun, dataList);
	}
	
	/** 
	 * 제본형 입력 최종자료 첨부파일 추가
	 * 
	 * @param dataBean : 입력할 데이터
	 * @param fileBean : 입력할 파일 데이터
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookCompFrm(DataBookBean dataBean, FileBean fileBean) throws Exception {
		return getFormatBookDAO().DataBookCompFrm(dataBean, fileBean);
	}
	
	/**
	 * 최종제본자료 내용 가져오기
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public DataBookBean getDataBookCompView(int sysdocno, int formseq) throws Exception{
		return getFormatBookDAO().getDataBookCompView(sysdocno, formseq);
	}
	
	/** 
	 * 최종제본자료 첨부파일 삭제
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param fileseq : 파일일련번호
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookCompDelete(int sysdocno, int formseq) throws Exception {
		return getFormatBookDAO().DataBookCompDelete(sysdocno, formseq);
	}	
	
	/**
	 * 확장자 체크  가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String 확장자건수
	 * @throws Exception 
	 */
	public int getExtchk(int sysdocno, int formseq) throws Exception {
		int result = 0;
		
		result = getFormatBookDAO().getExtchk(sysdocno, formseq);
		
		return result;
	}	
}