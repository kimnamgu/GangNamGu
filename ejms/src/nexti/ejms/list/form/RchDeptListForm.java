/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 응답자 소속부서 목록
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

public class RchDeptListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(DeptListForm.class);
	private String 	sch_orggbn	= "";
	private String 	all_fl		= "";
	private String 	title		= "전체";
	private int 	rchno 		= 0;

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append("\n SELECT DISTINCT T3.DEPT_ID, T3.DEPT_NAME, T3.DEPT_RANK 	");
    			selectQuery.append("\n   FROM RCHANS T1, USR T2, DEPT T3			");
    			selectQuery.append("\n  WHERE T1.CRTUSRID = T2.USER_ID(+) 			");
    			selectQuery.append("\n    AND T2.DEPT_ID = T3.DEPT_ID 				");
    			selectQuery.append("\n    AND RCHNO = ? 							");
    			if(!sch_orggbn.equals(""))
        		selectQuery.append("\n    AND T3.ORGGBN  LIKE ?						");
    			selectQuery.append("\n  ORDER BY TO_NUMBER(T3.DEPT_RANK)			");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setInt(1, rchno);
    			if(!sch_orggbn.equals("")) pstmt.setString(2, "%"+sch_orggbn+"%");

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

    public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
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

	public int getRchno() {
		return rchno;
	}

	public void setRchno(int rchno) {
		this.rchno = rchno;
	}    
}
