/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상부서 입력담당자 담당단위조직 목록
 * 설명:
 */
package nexti.ejms.list.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import nexti.ejms.common.ConnectionManager;

public class InputChrgUsrListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(InputChrgUsrListForm.class);
	private String all_fl	= "";
	private String dept_id	= "";
	private int    sysdocno = 0;
	private String title	= "전체";

	public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			/*[USR_EXT] 테이블 삭제
    			selectQuery.append("\n SELECT CHRGUNITCD,									");
    			selectQuery.append("\n        NVL(CHRGUNITNM, '[담당지정안됨]') CHRGUNITNM		");
    			selectQuery.append("\n   FROM INPUTUSR T1, 			       					");
    			selectQuery.append("\n        (SELECT A.USER_ID, A.GRADE_ID					"); 		
    			selectQuery.append("\n           FROM USR A, USR_EXT B						"); 
    			selectQuery.append("\n          WHERE A.USER_ID = B.USER_ID)T2				"); 		
    			selectQuery.append("\n  WHERE T1.INPUTUSRID = T2.USER_ID       	        	");
    			selectQuery.append("\n    AND T1.SYSDOCNO = ?                  		  		");
    			selectQuery.append("\n    AND T1.TGTDEPT = ?								");
    			selectQuery.append("\n  ORDER BY T2.GRADE_ID 								");
    			*/

    			selectQuery.append("\n SELECT DISTINCT * FROM (								");
    			selectQuery.append("\n SELECT CHRGUNITCD,									");
    			selectQuery.append("\n        NVL(CHRGUNITNM, '[담당지정안됨]') CHRGUNITNM		");
    			selectQuery.append("\n   FROM INPUTUSR T1, 			       					");
    			selectQuery.append("\n        (SELECT USER_ID, GRADE_ID						"); 		
    			selectQuery.append("\n           FROM USR)T2								"); 		
    			selectQuery.append("\n  WHERE T1.INPUTUSRID = T2.USER_ID       	        	");
    			selectQuery.append("\n    AND T1.SYSDOCNO = ?                  		  		");
    			selectQuery.append("\n    AND T1.TGTDEPT = ?								");
    			selectQuery.append("\n  ORDER BY T2.GRADE_ID 								");
    			selectQuery.append("\n )                                                    ");
    			
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setInt(1, sysdocno);
    			pstmt.setString(2, dept_id);

    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {    				
    			
	    			entries.add(new LabelValueBean(rs.getString("CHRGUNITNM"), rs.getString("CHRGUNITCD")));    				
    			}

    			beanCollection = entries;
    		} catch(SQLException e){
    			logger.error("ERROR", e);
    		} finally {
    			ConnectionManager.close(con,pstmt,rs);
    		}            
        }
        return (beanCollection);
    }

    public void setBeanCollection(Collection beanCollection) {
        this.beanCollection = beanCollection;
    }
    
	public void setAll_fl(String all_fl) {
		this.all_fl = all_fl;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public void setTitle(String title) {
		this.title = title;
	} 

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
}
