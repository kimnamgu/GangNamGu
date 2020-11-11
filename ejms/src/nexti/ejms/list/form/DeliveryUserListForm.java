/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부담당관리 목록
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

public class DeliveryUserListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(DeliveryUserListForm.class);
	private String all_fl	= "";
	private String dept_id	= "";
	private String title	= "사용자선택";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append("SELECT USER_ID, USER_NAME || '(' || USER_ID || ') - ' || CLASS_NAME USER_NAME ");
    			selectQuery.append("FROM USR ");
    			selectQuery.append("WHERE NVL(DELIVERY_YN, 'N') != 'Y' ");    			
    			selectQuery.append("AND DEPT_ID = ? ");    			
    			selectQuery.append("ORDER BY USR_RANK ");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setString(1, dept_id);

    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {    				
	    			entries.add(new LabelValueBean(rs.getString("USER_NAME"), rs.getString("USER_ID")));    				
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
