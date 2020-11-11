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

public class LdapApplyDateListForm  extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(LdapApplyDateListForm.class);
	private String gbn 	= "002";

    public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer Query = new StringBuffer();
    			Query.append(" SELECT	APPLYDT 																	\n");
    			Query.append(" FROM (SELECT  DISTINCT TO_CHAR(TO_DATE(SUBSTR(APPLYDT, 0, 8)), 'YYYY-MM-DD') APPLYDT \n");
    			if(gbn.equals("0")) 
    				Query.append("   FROM  DEPT_TEMP_LDAP  															\n");
    			else if(gbn.equals("1"))
    				Query.append("   FROM  USR_TEMP_LDAP  															\n");
    			Query.append("       ORDER BY  APPLYDT DESC) 														\n");
    			Query.append(" WHERE  ROWNUM <= 10 																	\n");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(Query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );

    			rs = pstmt.executeQuery();
    			
    			entries.add(new LabelValueBean("=일자선택=", ""));
    			while (rs.next()) {
	    			entries.add(new LabelValueBean(rs.getString("APPLYDT"), rs.getString("APPLYDT")));    				
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

	/**
	 * @param orggbn the gbn to set
	 */
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

}