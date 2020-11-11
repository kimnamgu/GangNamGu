/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� manager
 * ����:
 */
package nexti.ejms.workplan.model;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class WorkplanManager {
    private static Logger logger = Logger.getLogger(WorkplanManager.class);
    
    private static WorkplanManager instance = null;
    private static WorkplanDAO dao = null;
    
    public static WorkplanManager instance() {
        if (instance==null) instance = new WorkplanManager(); 
        return instance;
    }
    
    private WorkplanDAO getWorkplanDAO(){
        if (dao==null) dao = new WorkplanDAO(); 
        return dao;
    }
    
    private WorkplanManager() {
    }
    
    /**
     * ������ȹ ������ ����
     * @param planno
     * @return 
     * @throws Exception 
     */
    public WorkplanBean viewWorkplan(int planno) throws Exception {
        return getWorkplanDAO().viewWorkplan(planno);
    }
    /**
     * ������ȹ ÷������ ����
     * @param conn
     * @param wpbean
     * @param context
     * @param savrDir
     * @return
     * @throws Exception
     */
    public int saveWorkplanFile(Connection con, WorkplanBean wBean, ServletContext sContext, String saveDir) throws Exception {
        int result = 0;
        
        //÷������ 3���� �����Ǿ� �־� ���� ó����
		if ( wBean.getAttachFileYN1().equals("Y") == true ||
				( wBean.getAttachFile1() != null && !"".equals(wBean.getAttachFile1().getFileName().trim()) ) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 1);
		}
		if ( wBean.getAttachFileYN1().equals("Y") == false &&
				wBean.getAttachFile1() != null && !"".equals(wBean.getAttachFile1().getFileName().trim()) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 1);
			result += addWorkplanFile(con, sContext, saveDir, wBean.getPlanno(), 1, wBean.getAttachFile1());
		}
		
		if ( wBean.getAttachFileYN2().equals("Y") == true ||
				( wBean.getAttachFile2() != null && !"".equals(wBean.getAttachFile2().getFileName().trim()) ) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 2);
		}
		if ( wBean.getAttachFileYN2().equals("Y") == false &&
				wBean.getAttachFile2() != null && !"".equals(wBean.getAttachFile2().getFileName().trim()) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 2);
			result += addWorkplanFile(con, sContext, saveDir, wBean.getPlanno(), 2, wBean.getAttachFile2());
		}
		
		if ( wBean.getAttachFileYN3().equals("Y") == true ||
				( wBean.getAttachFile3() != null && !"".equals(wBean.getAttachFile3().getFileName().trim()) ) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 3);
		}
		if ( wBean.getAttachFileYN3().equals("Y") == false &&
				wBean.getAttachFile3() != null && !"".equals(wBean.getAttachFile3().getFileName().trim()) ) {
			deleteWorkFile(con, sContext, wBean.getPlanno(), 3);
			result += addWorkplanFile(con, sContext, saveDir, wBean.getPlanno(), 3, wBean.getAttachFile3());
		}
        
        return result;
    }
    /**
     * ������ȹ ÷������ �߰�
     * @param conn
     * @param wpbean
     * @param context
     * @param saveDir
     * @param fileno
     * @return 
     * @throws Exception
     */
    public int addWorkplanFile(Connection conn, ServletContext context, String saveDir, int planno, int fileno, FormFile file) throws Exception {
        
        int result = 0;
        
        File saveFolder = new File(context.getRealPath(saveDir));
        
        if(!saveFolder.exists()) {
            saveFolder.mkdirs();
        }
        
        //���Ͼ��ε�
        FileBean fileBean = new FileBean();
        fileBean = FileManager.doFileUpload(file, context, saveDir);
        if(fileBean != null) {
            fileBean.setSeq(fileno);
            result = getWorkplanDAO().addWorkplanFile(conn, fileBean, planno);
        }        
        
        return result;
    }
    
    /**
     * ������ȹ ÷������ ����
     * @param conn
     * @param planno
     * @param fileno
     * @param context
     * @return 
     * @throws Exception
     */
    public int deleteWorkFile(Connection conn, ServletContext context, int planno, int fileno) throws Exception {
        
        int result = 0;
        
        WorkplanFileBean wpfbean = getWorkplanFile(conn, planno, fileno);

        if ( wpfbean != null ) {
            result += getWorkplanDAO().delWorkplanFile(conn, planno, fileno);
            
            String delFile = wpfbean.getFilenm();
            if ( delFile != null && delFile.trim().equals("") == false) {
                FileManager.doFileDelete(context.getRealPath(delFile));
            }
        }
        
        return result;
    }
    
    /**
     * ������ȹ ÷������ ���� 
     * @param conn
     * @param planno
     * @param fileno
     * @return
     * @throws Exception
     */
    public WorkplanFileBean getWorkplanFile(Connection conn, int planno, int fileno) throws Exception {
        
        WorkplanFileBean result = null;
        
        result = getWorkplanDAO().getWorkplanFile(conn, planno, fileno);
            
        return result;
    }
    
    
    
    
    /**
     * ������ȹ ����
     * @param wpbean
     * @param filedir
     * @param context
     * @param saveDir
     * @param mode
     * @return
     * @throws Exception
     */
    public int saveWorkplan(WorkplanBean wpbean, String filedir, ServletContext context, String saveDir, String mode) throws Exception {
        
        int planno = 0;
        
        Connection conn = null;
        try{
            conn = ConnectionManager.getConnection(false);
            
            wpbean = getWorkplanDAO().getUpperDept(conn, wpbean);
            
            if("modify".equals(mode)){
            	wpbean.setStatus(viewWorkplan(wpbean.getPlanno()).getStatus());
            	wpbean.setUseyn("Y");
                planno = getWorkplanDAO().modifyWorkplan(conn,wpbean);
            } else if("delete".equals(mode)){
                wpbean.setUseyn("N");
                planno = getWorkplanDAO().modifyWorkplan(conn, wpbean);
            } else if("end".equals(mode)){
                wpbean.setStatus("2");
                planno = getWorkplanDAO().modifyWorkplan(conn, wpbean);
            } else if("start".equals(mode)){
                wpbean.setStatus("1");
                planno = getWorkplanDAO().modifyWorkplan(conn, wpbean);
            } else {
                planno = getWorkplanDAO().newWorkplan(conn, wpbean);
            }
            
            wpbean.setPlanno(planno);
            
            if(context != null && saveDir != null){
                saveWorkplanFile(conn, wpbean, context, saveDir);
            }
            
            conn.commit();
        } catch(Exception e){
            try{
                conn.rollback();
            } catch(Exception ex){
                logger.error("ERROR", ex);
            }
            ConnectionManager.close(conn);
            throw e;
        }finally{
            ConnectionManager.close(conn);
        }
        
        return planno;
    }
    /**
     * ������ȹ ���
     * @param searchBean
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getWorkplanList(WorkplanSearchBean searchBean, int start, int end) throws Exception {
        return getWorkplanDAO().getWorkplanList(searchBean, start, end);
    }
    /**
     * ������ȹ ��� �Ǽ�
     * @param searchBean
     * @return
     * @throws Exception
     */
    public int getWorkplanTotCnt(WorkplanSearchBean searchBean) throws Exception {
        return getWorkplanDAO().getWorkplanTotCnt(searchBean);
    }
    
    /**
     * ������ȹ���� ��������
     * @param planno
     * @param resultno
     * @return
     * @throws Exception
     */
    public WorkplanBean getWorkResult(int planno, int resultno) throws Exception {
    	return getWorkplanDAO().getWorkResult(planno, resultno);
    }
    
    /**
     * ������ȹ ��ȸ�� ����
     * @param planno
     * @return
     * @throws Exception
     */
    public int workplanReadCount(int planno) throws Exception {
        return getWorkplanDAO().workplanReadCount(planno);
    }
    
    /**
     * ������ȹ���� ��ȸ�� ����
     * @param planno
     * @param resultno
     * @return
     * @throws Exception
     */
    public int readCount(int planno, int resultno) throws Exception {
    	return getWorkplanDAO().readCount(planno, resultno);
    }
    
    /**
     * ������ȹ������� ������������
     * @param planno
     * @return
     * @throws Exception
     */
    public int getWorkResultListCount(int planno) throws Exception {
    	return getWorkplanDAO().getWorkResultListCount(planno);
    }
    
    /**
     * ������ȹ������� ��������
     * @param planno
     * @return
     * @throws Exception
     */
    public List getWorkResultList(int planno, int start, int end) throws Exception {
    	return getWorkplanDAO().getWorkResultList(planno, start, end);
    }
    
    /**
     * ������ȹ���� �����ϱ�
     * @param wBean
     * @return
     * @throws Exception
     */
    public int saveWorkResult(WorkplanBean wBean, ServletContext sContext, String saveDir) throws Exception {
    	Connection con = null;
    	int result = 0;
    	
    	try {
    		con = ConnectionManager.getConnection(false);
    		
    		UserManager uMgr = UserManager.instance();
        	UserBean uBean = uMgr.getUserInfo(wBean.getChrgusrcd());
        	
        	DeptManager dMgr = DeptManager.instance();
        	DeptBean dBean = dMgr.getDeptInfo(uBean.getDept_id());
        	
        	wBean.setChrgusrnm(uBean.getUser_name());
        	
        	int resultno = 0;
        	if ( wBean.getResultno() == 0 ) {
        		wBean.setReadcnt(0);
        		wBean.setUpperdeptcd(dBean.getUpper_dept_id());
        		wBean.setUpperdeptnm(dMgr.getDeptInfo(dBean.getUpper_dept_id()).getDept_name());
        		wBean.setDeptcd(dBean.getDept_id());
        		wBean.setDeptnm(dBean.getDept_name());
        		wBean.setChrgunitnm(dMgr.getChrgunitnm(dBean.getDept_id(), wBean.getChrgunitcd()));
        		wBean.setChrgusrcd(uBean.getUser_id());
        		wBean.setChrgusrnm(uBean.getUser_name());
        		wBean.setCrtusrid(uBean.getUser_id());
        		
        		resultno = getWorkplanDAO().insertWorkResult(con, wBean);
        	} else {
        		wBean.setChrgunitnm(dMgr.getChrgunitnm(dBean.getDept_id(), wBean.getChrgunitcd()));
        		
        		resultno = getWorkplanDAO().updateWorkResult(con, wBean);
        	}
        	
        	if ( resultno > 0 && sContext != null && saveDir != null) {
    			//÷������ 3���� �����Ǿ� �־� ���� ó����
    			if ( wBean.getAttachFileYN1().equals("Y") == true ||
    					( wBean.getAttachFile1() != null && !"".equals(wBean.getAttachFile1().getFileName().trim()) ) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 1);
    			}
    			if ( wBean.getAttachFileYN1().equals("Y") == false &&
    					wBean.getAttachFile1() != null && !"".equals(wBean.getAttachFile1().getFileName().trim()) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 1);
    				addWorkResultFile(con, sContext, saveDir, wBean.getPlanno(), resultno, 1, wBean.getAttachFile1());
    			}
    			
    			if ( wBean.getAttachFileYN2().equals("Y") == true ||
    					( wBean.getAttachFile2() != null && !"".equals(wBean.getAttachFile2().getFileName().trim()) ) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 2);
    			}
    			if ( wBean.getAttachFileYN2().equals("Y") == false &&
    					wBean.getAttachFile2() != null && !"".equals(wBean.getAttachFile2().getFileName().trim()) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 2);
    				addWorkResultFile(con, sContext, saveDir, wBean.getPlanno(), resultno, 2, wBean.getAttachFile2());
    			}
    			
    			if ( wBean.getAttachFileYN3().equals("Y") == true ||
    					( wBean.getAttachFile3() != null && !"".equals(wBean.getAttachFile3().getFileName().trim()) ) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 3);
    			}
    			if ( wBean.getAttachFileYN3().equals("Y") == false &&
    					wBean.getAttachFile3() != null && !"".equals(wBean.getAttachFile3().getFileName().trim()) ) {
    				deleteWorkResultFile(con, sContext, wBean.getPlanno(), resultno, 3);
    				addWorkResultFile(con, sContext, saveDir, wBean.getPlanno(), resultno, 3, wBean.getAttachFile3());
    			}
    		}
        	
        	result = resultno;
        	
        	con.commit();
    	} catch(Exception e) {
    		con.rollback();
			logger.error("ERROR", e);
            ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
    	
    	return result;
    }
    
    /**
     * ��������÷������ ����
     * @param conn
     * @param sContext
     * @param planno
     * @param resultno
     * @param fileno
     * @return
     * @throws Exception
     */
    public int deleteWorkResultFile(Connection conn, ServletContext sContext, int planno, int resultno, int fileno) throws Exception {
		int result = 0;
		
		WorkplanFileBean wBean = getWorkplanDAO().getWorkResultFile(conn, planno, resultno, fileno);

		if ( wBean != null ) {
			result += getWorkplanDAO().deleteWorkResultFile(conn, planno, resultno, fileno);
			
			String delFile = wBean.getFilenm();
			if ( delFile != null && !"".equals(delFile.trim()) ) {
				FileManager.doFileDelete(sContext.getRealPath(delFile));
			}
		}
		
		return result;
	}
    
    /**
     * ��������÷������ �߰�
     * @param conn
     * @param sContext
     * @param saveDir
     * @param planno
     * @param resultno
     * @param fileno
     * @param file
     * @return
     * @throws Exception
     */
    public int addWorkResultFile(Connection conn, ServletContext sContext, String saveDir, int planno, int resultno, int fileno, FormFile file) throws Exception {
		int result = 0;
		
		File saveFolder = new File(sContext.getRealPath(saveDir));
		
		if(!saveFolder.exists()) {
			saveFolder.mkdirs();
		}
		
		//���Ͼ��ε�
		FileBean fileBean = new FileBean();
		fileBean = FileManager.doFileUpload(file, sContext, saveDir);
		if(fileBean != null) {
			fileBean.setSeq(fileno);
			result = getWorkplanDAO().addWorkResultFile(conn, fileBean, planno, resultno);
		}
		
		return result;
	}
}