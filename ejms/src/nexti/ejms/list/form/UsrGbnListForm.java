package nexti.ejms.list.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import nexti.ejms.common.ConnectionManager;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class UsrGbnListForm  extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(UsrGbnListForm.class);
	private String ccd_cd 	= null;
	private String user_gbn = null;
	private String all_fl	= "";
	private String excludeCode = "";
	private String title	= "전체";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer Query = new StringBuffer();
    			Query.append(" SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = ? AND CCDSUBCD != '...' \n");
    			if(!user_gbn.equals("001")) Query.append(" AND CCDSUBCD = '"+user_gbn+"' \n");
    			if(!excludeCode.equals("")) Query.append(" AND CCDSUBCD NOT IN ("+excludeCode+") \n");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(Query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
    			pstmt.setString(1, ccd_cd);

    			rs = pstmt.executeQuery();
    		
    			if("ALL".equals(all_fl)){
    				//전체 포함일때
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
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
    
	public void setAll_fl(String all_fl) {
		this.all_fl = all_fl;
	}

	public void setCcd_cd(String ccd_cd) {
		this.ccd_cd = ccd_cd;
	}

	public void setUser_gbn(String user_gbn) {
		this.user_gbn = user_gbn;
	}

	public void setExcludeCode(String excludeCode) {
		this.excludeCode = excludeCode;
	}    	
}