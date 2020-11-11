/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서 manager
 * 설명:
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
	 * 작성중단된 시스템문서번호 목록
	 * @return String[] 시스템문서번호목
	 * @throws Exception 
	 */
	public String[] getListCancelColldoc() throws Exception {
		
		String[] result = null;
		
		result = getColldocDAO().getListCancelColldoc();
		
		return result;
	}
	
	/**
	 * 목록에서 보이기/안보이기 설정
	 * @param Connection conn
	 * @param int sysdocno
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int setDelyn(Connection conn, int sysdocno, int delyn) throws Exception {
		int result = 0;
		
		result = getColldocDAO().setDelyn(conn, sysdocno, delyn);
		
		return result;
	}
	
	/**
	 * 목록에서 보이기/안보이기 설정
	 * @param int sysdocno
	 * @return int 취합문서개수
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
	 * 새문서번호 가져오기
	 * @param Connection conn
	 * @param String deptcd
	 * @return String 새문서번호
	 * @throws Exception 
	 */
	public String getDocno(Connection conn, String deptcd) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		
		String year = Integer.toString(cal.get(Calendar.YEAR));
		
		int docno = getColldocDAO().getNextDocno(conn, year, deptcd);
		
		DeptManager dmgr = DeptManager.instance();
		
		DeptBean dbean = dmgr.getDeptInfo(deptcd);
		
		String deptnm = dbean.getDept_name();
		
		String result = "[취합]" + deptnm + "-" + docno;
		
		setDocno(conn, deptcd);
		
		return result;
	}
	
	/**
	 * 문서번호 채번
	 * @param Connection conn
	 * @param String deptcd
	 * @return int 수행개수
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
	 * 취합문서개수 가져오기
	 * @param String user_id
	 * @param String searchvalue
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCountColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		int result = 0;
		
		result = getColldocDAO().getCountColldoc(user_id, initentry, isSysMgr, searchvalue, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	/**
	 * 취합문서의 양식개수 가져오기
	 * @param int sysdocno
	 * @return int 양식개수
	 * @throws Exception 
	 */
	public int getCountFormat(int sysdocno) throws Exception {
		int result = 0;
		
		result = getColldocDAO().getCountFormat(sysdocno);
		
		return result;
	}
	
	/**
	 * 취합문서목록 가져오기
	 * @param String user_id
	 * @param String searchvalue
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListColldoc(String user_id, String initentry, String isSysMgr, String searchvalue, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		
		List result = null;
		
		result = getColldocDAO().getListColldoc(user_id, initentry, isSysMgr, searchvalue, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * 취합양식자료목록 가져오기
	 * @param int sysdocno
	 * @return List 취합양식자료목록(ArrayList)
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
	 * 취합양식자료목록 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List 취합양식자료목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListFormat(Connection conn, int sysdocno) throws Exception {
		
		List result = null;
		
		result = getColldocDAO().getListFormat(conn, sysdocno);
		
		return result;		
	}
	
	/**
	 * 선택한 취합문서 보기
	 * @param int sysdocno
	 * @return ColldocBean 취합문서데이터
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
			delColldocFile(conn, cdbean.getSysdocno(), 1, context);	//첨부파일이 한개 이상일 때 필요없음
			result += addColldocFile(conn, cdbean, context, savrDir);
		}
		
		return result;
	}
	
	/**
	 * 새 취합문서 추가
	 * @param DataTransferBean cdbean
	 * @param String filedir
	 * @param String sessi
	 * @param int savemode
	 * @return int 새시스템문서번호
	 */
	public int saveColldoc(ColldocBean cdbean, String filedir, String sessi, int savemode, ServletContext context, String saveDir) throws Exception {
		
		String user_id = cdbean.getUptusrid();
		int sysdocno = cdbean.getSysdocno();
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//1:새로저장, 2:새문서로 저장, 3:저장, 4:저장하고사본생성, 5:임시저장, 6:저장
			//mode=5는 새로작성 중인 문서를 임시저장(목록에서 안보임)하기 위해 사용
			//mode=6은 수정 중인 문서를 새문서로저장 가능 하도록 하기 위해 사용(결재용)
			if(savemode == 1 || savemode == 5) {			//새로저장
				
				sysdocno = getColldocDAO().newColldoc(conn, cdbean);
				
				cdbean.setSysdocno(sysdocno);
				saveColldocFile(conn, cdbean, context, saveDir);
				
				addColldocTempData_TGT_SANC(conn, sessi, sysdocno);
	
				delColldocTempData_TGT_SANC(conn, sessi);
				
			} else if(savemode == 2) {	//새문서로 저장
				
				sysdocno = getColldocDAO().newColldoc(conn, cdbean);
				
				copyColldocData(conn, user_id, cdbean.getSysdocno(), sysdocno);
				
				copyColldocFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
				
				copyFormatFile(conn, filedir, cdbean.getSysdocno(), sysdocno);
				
				cdbean.setSysdocno(sysdocno);
				saveColldocFile(conn, cdbean, context, saveDir);
				
			} else if(savemode == 3 || savemode == 6) {	//저장
				
				getColldocDAO().saveColldoc(conn, cdbean);
				
				saveColldocFile(conn, cdbean, context, saveDir);
				
			} else if(savemode == 4) {	//저장하고 사본생성
				
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
	 * 양식파일 정보 복사
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
	 * 취합문서 첨부파일 복사
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
				logger.error("파일을 찾을 수 없습니다 : " + srcFile);
			}
		}
		
		return result;
	}
	
	/**
	 * 취합대상부서,결재선 정보 복사
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int newsysdocno
	 * @throws Exception 
	 */
	public void copyColldocData(Connection conn, String user_id, int sysdocno, int newsysdocno) throws Exception {
		
		getColldocDAO().copyColldocData(conn, user_id, sysdocno, newsysdocno);
	}

	/**
	 * 취합문서 결재상신 상태로 지정하기
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
	 * 선택한문서 삭제
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
				
				delColldocFile(conn, sysdocno, 1, context);	//현재 첨부파일은 한개만 추가됨
			
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
	 * 취합문서 첨부파일 추가
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
		
		//파일업로드
		FileBean fileBean = new FileBean();
		fileBean = FileManager.doFileUpload(cdbean.getAttachFile(), context, saveDir);
		if(fileBean != null) {
			fileBean.setSeq(cdbean.getSysdocno());
			result = getColldocDAO().addColldocFile(conn, fileBean);
		}
		
		return result;
	}
	
	/**
	 * 취합문서 첨부파일 삭제
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
	 * 취합문서 첨부파일 정보 
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
	 * 취합문서 첨부파일 정보 
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
	 * 취합문서파일 체크
	 * @param String fileDir
	 * @param int sysdocno
	 * @return List 없는파일목록(없을시 null)
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
	 * 문서생성시 취합대상부서,결재선 임시테이블 데이터 원본테이블로 복사
	 * @param Connection conn
	 * @param String sessi
	 * @param int sysdocno
	 * @throws Exception 
	 */
	public void addColldocTempData_TGT_SANC(Connection conn, String sessi, int sysdocno) throws Exception {
		
		getColldocDAO().addColldocTempData_TGT_SANC(conn, sessi, sysdocno);
	}
	
	/**
	 * 문서생성시 취합대상부서,결재선 임시테이블 데이터 삭제
	 * @param Connection conn
	 * @param String sessi
	 * @throws Exception 
	 */
	public void delColldocTempData_TGT_SANC(Connection conn, String sessi) throws Exception {
		
		getColldocDAO().delColldocTempData_TGT_SANC(conn, sessi);
	}
	
	/**
	 * 문서생성시 취합대상부서,결재선 임시테이블 데이터 삭제
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
	 * 진행중인취합문서 목록
	 * @param String sessionId
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getCollProcList(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getColldocDAO().getCollProcList(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * 진행중인 취합문서 목록 갯수 가져오기
	 * @param String sessionId
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCollProcTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String docstate, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		int result = 0;
		
		result = getColldocDAO().getCollProcTotCnt(usrid, deptcd, initentry, isSysMgr, docstate, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}

	/**
	 * 진행중인 취합문서 취합양식 세부 데이터 보기
	 * @param int sysdocno
	 * @return List 취합문서
	 * @throws Exception 
	 */
	public int collprocClose(CollprocBean bean, String tgtdeptcd, String sessionId ) throws Exception{
		CollprocBean collprocBean = new CollprocBean();
		
		if(bean.getRadiochk().equals("1")){	//공개
			collprocBean.setClosedate("1900-01-01");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else if(bean.getRadiochk().equals("2")){	//비공개
			collprocBean.setClosedate("9999-12-31");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else if(bean.getRadiochk().equals("4")){	//취합/입력자에게공개
			collprocBean.setClosedate("1900-01-04");
			collprocBean.setSearchkey(bean.getSearchkey());
			collprocBean.setSysdocno(bean.getSysdocno());
		}else{	//비공개기한
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
	 * 진행중인 취합문서 기안취소 프로세서
	 * @param int sysdocno
	 * @param String sessionId
	 * @param String tgtdeptcd
	 * @return int 처리건수
	 */
	public int collprocProcess(int sysdocno, String sessionId, String tgtdeptcd ) throws Exception{
		
		return getColldocDAO().collprocProcess(sysdocno, sessionId, tgtdeptcd);
		
	}
	
	/**
	 * 취합문서완료 목록
	 * @param String deptcd
	 * @param String sessionId
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getCollcompList(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getColldocDAO().getCollcompList(usrid, deptcd, searchvalue, selyear, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * 진행중인 취합문서 목록 갯수 가져오기
	 * @param String deptcd
	 * @param String sessionId
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getCollcompTotCnt(String usrid, String deptcd, String searchvalue, String selyear, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		int result = 0;
		
		result = getColldocDAO().getCollcompTotCnt(usrid, deptcd, searchvalue, selyear, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	
	/**
	 * 취합완료문서 마감취소 프로세서
	 * @param int sysdocno
	 * @param String sessionId
	 * @return int 취합문서개수
	 */
	public int collCompProcess(int sysdocno, String user_id) throws Exception{
		
		return getColldocDAO().collCompProcess(sysdocno, user_id);
		
	}
	
	/**
	 * 문서상태 가져오기
	 * @param int sysdocno
	 * @return CollprocBean 문서상태
	 * @throws Exception 
	 */
	public CollprocBean  getDocState(int sysdocno) throws Exception{
		
		return getColldocDAO().getDocState(sysdocno);
			 
	}	
	
	/**
	 * 취합현황 건수 가져오기 -(메인화면)
	 * stepGbn : 결재진행(1), 취합진행(2), 마감대기(3)
	 * @throws Exception 
	 */
	public int procCount(String stepGbn, String user_id, String deptcd) throws Exception {	
		int result = 0;
		
		result = getColldocDAO().procCount(stepGbn, user_id, deptcd);
		
		return result;
	}
	
	/**
	 * 마감관련 문서내용  가져오기
	 * @param int sysdocno
	 * @return CollprocBean 문서상태
	 * @throws Exception 
	 */
	public CollprocBean  getCloseView(int sysdocno) throws Exception{
		
		return getColldocDAO().getCloseView(sysdocno);
			 
	}	
	
	/**
	 * 최근취합문서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchDoc(String searchvalue, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchDoc(searchvalue, sch_deptcd, sch_userid);
	}
	
	/**
	 * 진행중인취합문서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchProc( String docstate, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchProc(docstate, sch_deptcd, sch_userid);
	}
	
	/**
	 * 취합완료 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearchComp(String searchvalue, String selyear, String sch_deptcd, String sch_userid) throws Exception{
		return getColldocDAO().getSearchComp(searchvalue, selyear, sch_deptcd, sch_userid);
	}
}