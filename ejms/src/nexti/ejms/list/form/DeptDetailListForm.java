package nexti.ejms.list.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class DeptDetailListForm  extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(DeptDetailListForm.class);
	private String orggbn 		= null;
	private String rep_dept 	= null;
	private String dept_depth 	= null;
	private String user_id 		= null;
	private String all_fl		= "";
	private String title		= "전체";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;

    		String rootid = appInfo.getRootid();
    		if(rootid.equals(rep_dept)){
    			dept_depth = "1";
    		}else{
    			dept_depth = "2";
    		}
    		
    		try {
    			StringBuffer sql = new StringBuffer();
    			sql.append("SELECT	A.DEPT_ID, \n");
				sql.append("NVL( TRIM( REPLACE( DEPT_FULLNAME, (SELECT DEPT_FULLNAME FROM DEPT WHERE DEPT_ID = '" + rootid + "'), '' ) ), DEPT_NAME) DEPT_NAME \n");  
				sql.append("FROM DEPT A \n");
				sql.append("WHERE A.USE_YN LIKE 'Y' \n");
				sql.append("AND ORGGBN LIKE ? \n");
    			if ( orggbn.equals("001") ) {
    				sql.append("START WITH  DEPT_ID = '"+rootid+"' \n");     
    				sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n");     
    				sql.append("ORDER SIBLINGS BY DEPT_RANK \n"); 
				} else {
					sql.append("START WITH DEPT_ID = (SELECT DEPT_ID FROM DEPT \n");
					sql.append("                      WHERE DEPT_DEPTH = '"+dept_depth+"' \n");
					sql.append("                      START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_ID = '"+user_id+"') \n");
					sql.append("                      CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) \n");
	    			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n");
	    			sql.append("ORDER SIBLINGS BY DEPT_RANK \n");
				}
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
    			pstmt.setString(1, orggbn);
    			if ( "".equals(orggbn) ) {
    				pstmt.setString(1, "%");
    			} else {
    				pstmt.setString(1, orggbn);
    			}

    			rs = pstmt.executeQuery();
    		
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {
	    			entries.add(new LabelValueBean(rs.getString("DEPT_NAME"), rs.getString("DEPT_ID")));    				
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

	/**
	 * @param orggbn the orggbn to set
	 */
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	/**
	 * @param rep_dept the rep_dept to set
	 */
	public void setRep_dept(String rep_dept) {
		this.rep_dept = rep_dept;
	}

	/**
	 * @param dept_depth the dept_depth to set
	 */
	public void setDept_depth(String dept_depth) {
		this.dept_depth = dept_depth;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}  
}