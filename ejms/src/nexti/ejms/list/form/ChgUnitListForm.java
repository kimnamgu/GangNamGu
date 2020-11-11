/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위조직 목록
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

public class ChgUnitListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(ChgUnitListForm.class);
	private String all_fl	= "";
	private String dept_id	= "";
	private String orggbn	= "";
	private String title	= "담당단위없음";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append("SELECT CHRGUNITCD, CHRGUNITNM ");
    			selectQuery.append("FROM CHRGUNIT C, DEPT D ");
    			selectQuery.append("WHERE C.DEPT_ID = D.DEPT_ID(+) ");
    			selectQuery.append("AND C.DEPT_ID LIKE ? ");    			
    			selectQuery.append("AND NVL(ORGGBN, ' ') LIKE ? ");    			
    			selectQuery.append("ORDER BY ORD ");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setString(1, dept_id);
    			
    			if ( !"".equals(orggbn) ) {
    				pstmt.setString(2, orggbn);
    			} else {
    				pstmt.setString(2, "%");
    			}

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

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
}