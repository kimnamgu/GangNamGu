/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용했던양식 작성년도 목록
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

public class UsedFormYearListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(UsedFormYearListForm.class);
	private String all_fl	= "";
	private String dept_id	= "";
	private String title	= "전체";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			
    			selectQuery.append("SELECT DISTINCT SUBSTR(A.UPTDT, 0, 4) ");
    			selectQuery.append("FROM FORMMST A, DOCMST B ");
    			selectQuery.append("WHERE A.SYSDOCNO = B.SYSDOCNO "); 
    			selectQuery.append("  AND B.DOCSTATE = '06' "); 
    			selectQuery.append("  AND B.COLDEPTCD IN (SELECT DEPT_ID "); 
    			selectQuery.append("                      FROM DEPT "); 
    			selectQuery.append("                      START WITH DEPT_ID LIKE ? ");
    			selectQuery.append("                      CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) "); 
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setString(1, dept_id);

    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				entries.add(new LabelValueBean(title.toString(), "%"));  
    			}
    			
    			while (rs.next()) {    				
	    			entries.add(new LabelValueBean(rs.getString(1), rs.getString(1)));    				
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
}
