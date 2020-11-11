/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포그룹 목록
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

public class GrpListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(GrpListForm.class);
	private String all_fl	= "";
	private String user_gbn	= "";
	private String title	= "==선택==";
	private String crtusrid = "%";
	private String crtusrgbn = "0";
	

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append(" SELECT GRPLISTCD, GRPLISTNM		\n");
    			selectQuery.append(" FROM GROUPMST A, USR B, DEPT C     \n");
    			selectQuery.append(" WHERE A.CRTUSRID = B.USER_ID(+)    \n");
    			selectQuery.append(" AND B.DEPT_ID = C.DEPT_ID(+)       \n");
    			selectQuery.append(" AND A.CRTUSRID LIKE ?				\n");
    			selectQuery.append(" AND CRTUSRGBN LIKE ?				\n");
    			if(!user_gbn.equals("001")){
    			selectQuery.append(" AND ORGGBN = '"+user_gbn+"' 	    \n");
    			}
    			selectQuery.append(" ORDER BY ORD 						\n");
    			
    			/*
    			selectQuery.append("\n SELECT GRPLISTCD, GRPLISTNM 	");
    			selectQuery.append("\n   FROM GROUPMST 				");
    			selectQuery.append("\n  WHERE CRTUSRGBN LIKE ?		");
    			selectQuery.append("\n  ORDER BY ORD 				");
    			*/
    			
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());

    			pstmt.setString(1, crtusrid);
    			pstmt.setString(2, crtusrgbn);
    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {    				
    			
	    			entries.add(new LabelValueBean(rs.getString("GRPLISTNM"), rs.getString("GRPLISTCD")));    				
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
    
    public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}

	public void setUser_gbn(String user_gbn) {
		this.user_gbn = user_gbn;
	}

	public void setCrtusrgbn(String crtusrgbn) {
		this.crtusrgbn = crtusrgbn;
	}

	public void setBeanCollection(Collection beanCollection) {
        this.beanCollection = beanCollection;
    }
    
	public void setAll_fl(String all_fl) {
		this.all_fl = all_fl;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
}