/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 제본자료형 카테고리 목록
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

public class CategoryListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(CategoryListForm.class);
	private int sysdocno 	= 0;
	private int formseq 	= 0;

	public Collection getBeanCollection() {
		if(beanCollection == null) {
			Vector entries = new Vector();
			
			Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			selectQuery.append("SELECT B.CATEGORYNM ");
    			selectQuery.append("  FROM FORMMST A, ATTBOOKFRM B ");
    			selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO ");    			
    			selectQuery.append("   AND A.FORMSEQ  = B.FORMSEQ ");
    			selectQuery.append("   AND A.SYSDOCNO = ? ");
    			selectQuery.append("   AND A.FORMKIND = '03' ");
    			selectQuery.append("   AND A.FORMSEQ  = ? ");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setInt(1, sysdocno);
    			pstmt.setInt(2, formseq);
    			
    			rs = pstmt.executeQuery();
    			
    			while (rs.next()) {    				
	    			entries.add(new LabelValueBean(rs.getString("CATEGORYNM"), rs.getString("CATEGORYNM")));    				
    			}

    			beanCollection = entries;
    		} catch(SQLException e){
    			logger.error("ERROR", e);
    		} finally {
    			ConnectionManager.close(con,pstmt,rs);
    		}
		}
		return beanCollection;
	}

	public void setBeanCollection(Collection beanCollection) {
		this.beanCollection = beanCollection;
	}

	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
}