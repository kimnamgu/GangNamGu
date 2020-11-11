/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���մ��μ� �Է´���� ���
 * ����:
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

public class InputUsrListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private static Logger logger = Logger.getLogger(InputUsrListForm.class);
	private String all_fl	= "";
	private String dept_id	= "";
	private int    sysdocno = 0;
	private int	   chrguncd = 0;
	private String title	= "��ü";

	public Collection getBeanCollection() throws SQLException {
        if (beanCollection == null) {
            Vector entries = new Vector();
            
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			StringBuffer selectQuery = new StringBuffer();
    			/*[USR_EXT] ���̺� ����
    			selectQuery.append("\n SELECT INPUTUSRID,  T1.INPUTUSRNM 				");
    			selectQuery.append("\n   FROM INPUTUSR T1, 			       				");
    			selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
    			selectQuery.append("\n        		   B.CHRGUNITCD,     				"); 		
    			selectQuery.append("\n        		   A.GRADE_ID						");
    			selectQuery.append("\n            FROM USR A,							"); 
    			selectQuery.append("\n        		   USR_EXT B						");
    			selectQuery.append("\n           WHERE A.USER_ID = B.USER_ID			");
    			selectQuery.append("\n         )T2										"); 		
    			selectQuery.append("\n  WHERE T1.INPUTUSRID = T2.USER_ID               	");
    			selectQuery.append("\n    AND T1.SYSDOCNO = ?                    		");
    			selectQuery.append("\n    AND T1.TGTDEPT = ?							");
    			if(chrguncd != 0){
    				selectQuery.append("\n    AND T1.CHRGUNITCD = ?						");
    			}
    			selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
    			*/
    			

    			selectQuery.append("\n SELECT INPUTUSRID,  T1.INPUTUSRNM 				");
    			selectQuery.append("\n   FROM INPUTUSR T1, 			       				");
    			selectQuery.append("\n        ( SELECT USER_ID, CHRGUNITCD, GRADE_ID	");
    			selectQuery.append("\n          FROM USR								"); 
    			selectQuery.append("\n         )T2										"); 		
    			selectQuery.append("\n  WHERE T1.INPUTUSRID = T2.USER_ID               	");
    			selectQuery.append("\n    AND T1.SYSDOCNO = ?                    		");
    			selectQuery.append("\n    AND T1.TGTDEPT = ?							");
    			if(chrguncd != 0){
    				selectQuery.append("\n    AND T1.CHRGUNITCD = ?						");
    			}
    			selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
    	
    			con = ConnectionManager.getConnection();
    			pstmt = con.prepareStatement(selectQuery.toString());
    			pstmt.setInt(1, sysdocno);
    			pstmt.setString(2, dept_id);
    			
    			if(chrguncd !=0){
    				pstmt.setInt(3, chrguncd);
    			}

    			rs = pstmt.executeQuery();
    			
    			if("ALL".equals(all_fl)){
    				//��ü �����϶�
    				entries.add(new LabelValueBean(title.toString(), ""));  
    			}
    			
    			while (rs.next()) {    				
    			
	    			entries.add(new LabelValueBean(rs.getString("INPUTUSRNM"), rs.getString("INPUTUSRID")));    				
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
	
	public void setChrguncd(int chrguncd) {
		this.chrguncd = chrguncd;
	}

	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
}
