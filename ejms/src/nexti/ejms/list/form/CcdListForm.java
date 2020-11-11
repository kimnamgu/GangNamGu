/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통코드 목록
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

public class CcdListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(CcdListForm.class);
	private String ccd_cd = null;

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer Query = new StringBuffer();
    			Query.append("SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD=? AND CCDSUBCD <> '...'");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(Query.toString(), 
    				ResultSet.TYPE_SCROLL_INSENSITIVE,
    				ResultSet.CONCUR_READ_ONLY );
    			pstmt.setString(1, ccd_cd);

    			rs = pstmt.executeQuery();
    			
    			while (rs.next()) {
	    			entries.add(new LabelValueBean(rs.getString("CCDNAME"), rs.getString("CCDSUBCD")));    				
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

	public void setCcd_cd(String ccd_cd) {
		this.ccd_cd = ccd_cd;
	}    
}
