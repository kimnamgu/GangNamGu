/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 부서 목록
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

public class DeptListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(DeptListForm.class);
	private String all_fl	= "";
	private String title	= "전체";
	private int sysdocno = 0;

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append("\n SELECT TGTDEPTCD, TGTDEPTNM			");
    			selectQuery.append("\n   FROM TGTDEPT T, DEPT D				");
    			selectQuery.append("\n  WHERE T.TGTDEPTCD = D.DEPT_ID(+)	");
    			selectQuery.append("\n    AND SYSDOCNO = ?					");
    			selectQuery.append("\n  ORDER BY DEPT_RANK					");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setInt(1, sysdocno);

    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {    				
    			
	    			entries.add(new LabelValueBean(rs.getString("TGTDEPTNM"), rs.getString("TGTDEPTCD")));    				
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

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSysdocno() {
		return sysdocno;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}    
}
