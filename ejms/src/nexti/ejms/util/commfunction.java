package nexti.ejms.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.List;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.commtreat.model.CommTreatBean;
import nexti.ejms.sinchung.model.ArticleBean;
import nexti.ejms.sinchung.model.SampleBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.ReqSubBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.sinchung.form.DataForm;

public class commfunction {
	
	// WAS�� ������� �ٿ�ε�� �ѱ۱��� ����
    public static String fileNameFix(String name) {
    	String result = null;
    	try {
    		result = ( !"JEUS".equals(ConnectionManager.WAS) ) ? Utils.decode(name) : name;
    	} catch ( Exception e ) {
    		result = name;
    	}
        return result; 
    }
	
	public static int getDeptFullNameSubstringIndex(String loginUserID) {						
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT SUM(LENGTH(DECODE(DEPT_ID, '" + appInfo.getRootid() + "', DEPT_FULLNAME, DEPT_NAME))) + COUNT(*) LEN \n");
			sql.append("FROM DEPT                                                                                             \n");
			sql.append("WHERE DEPT_DEPTH <= (SELECT DECODE(NVL(ORGGBN, '001'), '001', 1, 2)                                   \n");
			sql.append("                     FROM DEPT D, USR U                                                               \n");
			sql.append("                     WHERE D.DEPT_ID = U.DEPT_ID AND UPPER(U.USER_ID) = UPPER('" + loginUserID + "')) \n");
			sql.append("START WITH DEPT_ID = (SELECT DEPT_ID FROM USR WHERE UPPER(USER_ID) = UPPER('" + loginUserID + "'))    \n");
			sql.append("CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                              \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) result = rs.getInt(1);
		} catch (Exception e) {
			result = 0;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		return result;
	}
	
	/**
	 * ���ڿ� ä���
	 */
	public static String fillCh(String p_ch, int ln){		
		String result = "";
		
		for(int i=0;i<ln;i++){
			result = result + p_ch;
		}		
		return result;	
	}		

	/**
	 * ������ ó��
	 * condition - �˻�����
	 * path - forwarding ���
	 * totalPage - �� ������ ����
	 * currPage - ���� ������  
	 */
	public static String procPage(String path, HashMap condition, String tPage, String cPage){
		String result = "";
		String search = "";    //�˻����� ����		
		int pageBlock = 10;    //�ѹ��� ������ ������ ����
		int totalPage = Integer.parseInt(tPage.toString());
		int currPage = Integer.parseInt(cPage.toString());
		
		if(condition!=null){
		 	Iterator iterator = condition.entrySet().iterator();
	
	        while(iterator.hasNext()){
	            Entry entry = (Entry)iterator.next();
				search += entry.getKey() + "=" + entry.getValue()+"&";
	        }   
		}
       
        if(currPage==0){currPage = 1;}
        
        //���������� ���ؿ���
        int startPage = ((int)Math.floor((currPage-1)/pageBlock) * pageBlock) + 1;        
        if(startPage==0){startPage = 1;}                
      
        if(startPage > 1){
        	result = "<a href='"+path+"?"+search+"page=1'>[1]</a>";
        }
                
        if (startPage > pageBlock) {
        	result +=  ".. <font color=#0000ff><a href='"+path+"?"+search+"page="+String.valueOf(startPage-1)+"'><img src='/images/left-.gif' border='0' alt='PREV' align='absmiddle'></a></font>";
        }
     
        for(int i=startPage;i<=(startPage+pageBlock-1);i++){
        	if( i > totalPage) { break;}
        	
        	if(i == currPage) {
        		result += "<font color=red>[" +String.valueOf(i)+ "]</font>";
        	} else {
        		result += "<a href='"+path+"?"+search+"page="+String.valueOf(i)+"'>[" +i+ "]</a>";
        	}
        }      

        if((startPage+pageBlock) <= (totalPage)){
        	result += "<font color=#0000ff><a href='"+path+"?"+search+"page="+String.valueOf(startPage+pageBlock)+"'><img src='/images/right-.gif' border='0' alt='NEXT' align='absmiddle'></a></font>";
        }
       
        if((startPage+pageBlock) <= (totalPage-1)){
        	result += " ..<a href='"+path+"?"+search+"page="+String.valueOf(totalPage)+"'>[" + String.valueOf(totalPage)+ "]</a>";
        }
     		
		return result;
	}
	
	/**
	 * ������ ó��, �Ķ�����߰�
	 * condition - �˻�����
	 * path - forwarding ���
	 * totalPage - �� ������ ����
	 * currPage - ���� ������  
	 */
	public static String procPage_AddParam(String path, String param, HashMap condition, String tPage, String cPage){
		String result = "";
		String search = "";    //�˻����� ����		
		int pageBlock = 10;    //�ѹ��� ������ ������ ����
		int totalPage = Integer.parseInt(tPage.toString());
		int currPage = Integer.parseInt(cPage.toString());
		
		if(condition!=null){
		 	Iterator iterator = condition.entrySet().iterator();
	
	        while(iterator.hasNext()){
	            Entry entry = (Entry)iterator.next();
				search += entry.getKey() + "=" + entry.getValue()+"&";
	        }   
		}
       
        if(currPage==0){currPage = 1;}
        
        //���������� ���ؿ���
        int startPage = ((int)Math.floor((currPage-1)/pageBlock) * pageBlock) + 1;        
        if(startPage==0){startPage = 1;}                
      
        if(startPage > 1){
        	result = "<a href='"+path+"?"+search+"page=1&"+param+"'>[1]</a>";
        }
                
        if (startPage > pageBlock) {
        	result +=  ".. <font color=#0000ff><a href='"+path+"?"+search+"page="+String.valueOf(startPage-1)+"&"+param+"'><img src='/images/left-.gif' border='0' alt='PREV' align='absmiddle'></a></font>";
        }
     
        for(int i=startPage;i<=(startPage+pageBlock-1);i++){
        	if( i > totalPage) { break;}
        	
        	if(i == currPage) {
        		result += "<font color=red>[" +String.valueOf(i)+ "]</font>";
        	} else {
        		result += "<a href='"+path+"?"+search+"page="+String.valueOf(i)+"&"+param+"'>[" +i+ "]</a>";
        	}
        }      

        if((startPage+pageBlock) <= (totalPage)){
        	result += "<font color=#0000ff><a href='"+path+"?"+search+"page="+String.valueOf(startPage+pageBlock)+"&"+param+"'><img src='/images/right-.gif' border='0' alt='NEXT' align='absmiddle'></a></font>";
        }
       
        if((startPage+pageBlock) <= (totalPage-1)){
        	result += " ..<a href='"+path+"?"+search+"page="+String.valueOf(totalPage)+"&"+param+"'>[" + String.valueOf(totalPage)+ "]</a>";
        }
     		
		return result;
	}
	
	/**
	 * ����Ʈ���� ���� ������ ��ġ ��������
	 */
	public static int startIndex(int currPage, int pageSize){
		int result = 0;
		
		if(currPage==0){
			result = 1;
		} else {
			result = (currPage -1) * pageSize + 1;
		}
		
		return result;
	}
	
	/**
	 * ����Ʈ���� ������ ������ ��ġ ��������
	 */
	public static int endIndex(int currPage, int pageSize){
		int result = 0;

		if(currPage == 0){
			result = pageSize;
		} else {
			result = (currPage -1) * pageSize + pageSize;
		}
		
		return result;
	}		
	
	/**
	 * ��¥�� '-' �߰�
	 * 20070201 -> 2007-02-01
	 * 2007020100900 -> 2007-02-01 09:00
	 * 200702010090000 -> 2007-02-01 09:00:00
	 */
	public static String dateFormat(String p_date){	
		try {
			if (p_date.length()==8) {
				String s1=p_date.substring(0,4);
				String s2=p_date.substring(4,6);
				String s3=p_date.substring(6,8);
				return s1+"-"+s2+"-"+s3;
			} else if (p_date.length()==12) {
				String s1=p_date.substring(0,4);
				String s2=p_date.substring(4,6);
				String s3=p_date.substring(6,8);
				String s4=p_date.substring(8,10);
				String s5=p_date.substring(10,12);
				return s1+"-"+s2+"-"+s3+" "+s4+":"+s5;			
			} else if (p_date.length()==14) {
				String s1=p_date.substring(0,4);
				String s2=p_date.substring(4,6);
				String s3=p_date.substring(6,8);
				String s4=p_date.substring(8,10);
				String s5=p_date.substring(10,12);
				String s6=p_date.substring(12,14);
				return s1+"-"+s2+"-"+s3+" "+s4+":"+s5+":"+s6;			
			} else {
				return p_date;
			}
		} catch (Exception e) {
			return p_date;
		}
	}
	
	/**
	 * AGENT RUNTIME �� RUNTIME '-' �߰�ǥ��
	 * p_type:001(����) -> 00�� 00��
	 * p_type:002(����) -> 0���� 00�� 00��
	 * p_type:003(�ſ�) -> 0�� 00�� 00��
	 * p_type:004(�ų�) -> 0�� 0�� 00�� 00��
	 */
	public static String dateFormatForRuntime(String p_type, String p_value){		
		if ("001".equals(p_type)) {
			String s1=p_value.substring(0,2);
			String s2=p_value.substring(2,4);
			return s1+"�� "+s2+"��";
		} else if ("002".equals(p_type)) {
			String s1="";
			if ("01".equals(p_value.substring(0,2))) {
				s1="��";
	        } else if ("02".equals(p_value.substring(0,2))) {
				s1="��";
	        } else if ("03".equals(p_value.substring(0,2))) {
				s1="ȭ";
	        } else if ("04".equals(p_value.substring(0,2))) {
				s1="��";
	        } else if ("05".equals(p_value.substring(0,2))) {
				s1="��";
	        } else if ("06".equals(p_value.substring(0,2))) {
				s1="��";
	        } else if ("06".equals(p_value.substring(0,2))) {
				s1="��";
	        } 
			String s2=p_value.substring(2,4);
			String s3=p_value.substring(4,6);
			return s1+"���� "+s2+"�� "+s3+"�� ";			
		} else if ("003".equals(p_type)) {
			String s1=p_value.substring(0,2);
			String s2=p_value.substring(2,4);
			String s3=p_value.substring(4,6);
			return s1+"�� "+s2+"�� "+s3+"�� ";			
		} else if ("004".equals(p_type)) {
			String s1=p_value.substring(0,2);
			String s2=p_value.substring(2,4);
			String s3=p_value.substring(4,6);
			String s4=p_value.substring(6,8);
			return s1+"�� "+s2+"�� "+s3+"�� "+s4+"�� ";			
		} else {
			return p_value;
		}
	}	
	
	/**
	 * �Է� �⺻������ �����ϴ� �Լ�
	 */
	public static String basicSet(String basictype){
		String custom1 = "";
		String custom2 = "";
		if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			custom1 = " checked onclick='this.checked=true' ";
			custom2 = " checked ";
		}
		
		String result = "";
		
		String[] btype = new String[11];
		
		if(basictype != null && !"".equals(basictype)){
			String[] temp = basictype.split(",");
			for(int i=0;i<temp.length;i++){
				btype[Integer.parseInt(temp[i])-1]= "1";
			}
		}		
		
		StringBuffer htmlStr = new StringBuffer();
		htmlStr.append("<tr>");
		htmlStr.append("	<td width='120' align='left'>");
		htmlStr.append("        <input type='hidden' name='btype' value='1'>");
		htmlStr.append("		<input type='checkbox' name='btype' title='����' style='border:0px;background-color:transparent' checked disabled>");
        htmlStr.append("		����");
        htmlStr.append("	</td>");        
        // ���ֽ�û �������� �ּ�..start
        htmlStr.append("	<td width='120' align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='11' title='�������' style='border:0px;background-color:transparent'");
        if("1".equals(btype[10])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");        
        htmlStr.append("		�������");
        htmlStr.append("	</td>");
        // ���ֽ�û �������� �ּ�..end
//        htmlStr.append("	<td width='120' align='left' valign='top' style='display:none'>");
//        htmlStr.append("		<input type='checkbox' name='btype' value='2' title='�ֹε�Ϲ�ȣ' style='border:0px;background-color:transparent'");
//        if("1".equals(btype[1])){
//        	htmlStr.append(" checked ");
//        }
//        htmlStr.append(">");        
//        htmlStr.append("		�ֹε�Ϲ�ȣ");
//        htmlStr.append("	</td>");    
        htmlStr.append("	<td width='120' align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='3' title='�Ҽ�' style='border:0px;background-color:transparent'");
        if("1".equals(btype[2])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(custom1);
        htmlStr.append(">");
        htmlStr.append("		�Ҽ�");
        htmlStr.append("	</td>");
		htmlStr.append("	<td width='120' align='left' valign='top'>");
		htmlStr.append("		<input type='checkbox' name='btype' value='4' title='����' style='border:0px;background-color:transparent'");
		if("1".equals(btype[3])){
        	htmlStr.append(" checked ");
        }
		htmlStr.append(custom2);
        htmlStr.append(">");
        htmlStr.append("		����");
        htmlStr.append("	</td>");
        htmlStr.append("	<td width='120' align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='5' title='�ּ�' style='border:0px;background-color:transparent'");
        if("1".equals(btype[4])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");
        htmlStr.append("		�ּ�");
        htmlStr.append("	</td>");
        htmlStr.append("</tr>");
        htmlStr.append("<tr>");
        htmlStr.append("	<td align='left'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='6' title='�̸���' style='border:0px;background-color:transparent'"); 
        if("1".equals(btype[5])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");
        htmlStr.append("		E-Mail");
        htmlStr.append("	</td>");
        htmlStr.append("	<td align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='7' title='��ȭ��ȣ' style='border:0px;background-color:transparent'"); 
        if("1".equals(btype[6])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(custom2);
        htmlStr.append(">");
        htmlStr.append("		��ȭ��ȣ");
        htmlStr.append("	</td>");
        htmlStr.append("	<td align='left' valign='top'>");
		htmlStr.append("		<input type='checkbox' name='btype' value='8' title='�޴���ȭ��ȣ' style='border:0px;background-color:transparent'"); 
		if("1".equals(btype[7])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");
		htmlStr.append("		�޴���ȭ��ȣ");
        htmlStr.append("	</td>");
        htmlStr.append("	<td align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='9' title='�ѽ���ȣ' style='border:0px;background-color:transparent'");
        if("1".equals(btype[8])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");
        htmlStr.append("		Fax��ȣ");
        htmlStr.append("	</td>");
        htmlStr.append("	<td align='left' valign='top'>");
        htmlStr.append("		<input type='checkbox' name='btype' value='10' title='÷������' style='border:0px;background-color:transparent'");
        if("1".equals(btype[9])){
        	htmlStr.append(" checked ");
        }
        htmlStr.append(">");
        htmlStr.append("		÷������");
        htmlStr.append("	</td>");
        htmlStr.append("	<td align='left' valign='top'>&nbsp;</td>");
        htmlStr.append("	<td>&nbsp;</td>");
        htmlStr.append("	<td>&nbsp;</td>");
        htmlStr.append("</tr>");
        
        
        result = htmlStr.toString();
		return result;
	}
	
	/**
	 * �⺻ �Է��׸��� ����
	 */
	public static String basicInput(String basictype, ReqMstBean rbean ){		
		String result = "";
		
		String[] btype = new String[11];
		
		if(basictype != null && !"".equals(basictype)){
			String[] temp = basictype.split(",");
			for(int i=0;i<temp.length;i++){
				btype[Integer.parseInt(temp[i])-1]= "1";
			}
		}		
		
		StringBuffer htmlStr = new StringBuffer();
		
		//��û��
		if("1".equals(btype[0])){        	
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append(" <td align='center' class='s1' style='padding:0px 5px 0px 5px'>����<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>����<font color='red'>(*)</font></td>");
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("    	<input type='hidden' name='presentid'");    
			if(rbean != null && rbean.getPresentid() != null){
				htmlStr.append(" value='"+rbean.getPresentid()+"'");
			}
			htmlStr.append("><input name='presentnm' type='text' title='����' style='width:170px;' maxlength='10' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getPresentnm() != null){
				htmlStr.append(" value='"+rbean.getPresentnm()+"'");				
			}
			//Ư�� ��û���� ���� ������ �����Ұ��ϵ��� ����(JHkim, 14.02.17)
			if( "��õ6280000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
			    if( rbean != null && rbean.getReqformno() == 78 ){
			        htmlStr.append(" readonly ");
			    }
			}
			htmlStr.append("></td>");
			htmlStr.append("</tr>");
	        htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
        }
//		���ֽ�û ���� ����  .. �ּ� start
		//�������
		if("1".equals(btype[10])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//          htmlStr.append("    <td align='center' class='s1' style='padding:0px 5px 0px 5px'>�������<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
			if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
			htmlStr.append("' style='padding:0px 5px 0px 5px'>�������<font color='red'>(*)</font></td>");
			
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'><input name='presentbd' maxlength='8' title='�������' style='width:170px;ime-mode:disabled;' onkeydown='key_num();' ");
			if(rbean != null && rbean.getPresentbd() != null){
				htmlStr.append(" value='"+rbean.getPresentbd()+"'");				
			}
			htmlStr.append("> &nbsp;&nbsp; ���ڸ� �Է��ϼ��� (�� : 20111212)</td>");
			htmlStr.append("</tr>");
			htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
//		���ֽ�û ���� ����  .. �ּ� end	
	
		//�ֹε�Ϲ�ȣ
//		if("1".equals(btype[1])){
//			htmlStr.append("<tr>");
//			//������û ��û�� UI���� (JHkim, 14.03.28)
////			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>�ֹε�Ϲ�ȣ<font color='red'>(*)</font></td>");
//			htmlStr.append(" <td align='center' class='");
//            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
//            {
//                htmlStr.append("ptext");
//            }
//            else
//            {
//                htmlStr.append("s1");
//            }
//            htmlStr.append("' style='padding:0px 5px 0px 5px'>�ֹε�Ϲ�ȣ<font color='red'>(*)</font></td>");
//			
//			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'><input name='presentsn' type='password' title='�ֹε�Ϲ�ȣ' maxlength='13' style='width:170px;ime-mode:disabled;' onkeydown='key_num();' ");
//			if(rbean != null && rbean.getPresentsn() != null){
//				htmlStr.append(" value='"+rbean.getPresentsn()+"'");				
//			}
//			htmlStr.append("> &nbsp;&nbsp;��-������ ���ڸ� �Է��ϼ���</td>");
//			htmlStr.append("</tr>");
//			htmlStr.append("<tr>");
//			htmlStr.append("	<td colspan='2' class='bg1'></td>");
//			htmlStr.append("</tr>");
//		}
		
		//�Ҽ�
		if("1".equals(btype[2])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>�Ҽ�<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>�Ҽ�<font color='red'>(*)</font></td>");
			
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'><input name='position' type='text' title='�μ�' maxlength='50' style='width:170px;' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getPosition() != null){
				htmlStr.append(" value='"+rbean.getPosition()+"'");
			}
			htmlStr.append("></td>");
			htmlStr.append("</tr>");        
			htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//����
		if("1".equals(btype[3])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>����<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>����<font color='red'>(*)</font></td>");
			
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'><input name='duty' type='text' title='����' maxlength='50' style='width:170px;' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getDuty() != null){
				htmlStr.append(" value='"+rbean.getDuty()+"'");		
			}
			htmlStr.append("></td>");
			htmlStr.append("</tr>");
         	htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//�ּ�
		if("1".equals(btype[4])){
			htmlStr.append("<tr class='in_adr'>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>�ּ�<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>�ּ�<font color='red'>(*)</font></td>");
			/*���� �����ȣ �ּ� ó�� 2016.11.16*/
			/*htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("	<a href=\"javascript:showModalPopup('/addrFindList.do',460,500,0,0);\"><img src='/images/search_ad.gif' border='0' align='middle' alt='�ּ�ã��'></a>");
			htmlStr.append("	<input name='addr1' type='text' title='�ּ�' style='width:270px;color:#8C8C8C;' maxlength='59'");
			if(rbean != null && rbean.getAddr1() != null && rbean.getAddr1().length()>0){				
				htmlStr.append(" value='["+rbean.getZip()+"]"+rbean.getAddr1()+"'");
			}
			htmlStr.append(" readonly>");			
			htmlStr.append("	&nbsp;&nbsp;<input name='addr2' type='text' title='���ּ�' style='width:130px;' maxlength='50' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getAddr2() != null){
				htmlStr.append(" value='"+rbean.getAddr2()+"'");	
			}
			htmlStr.append("></td>");	*/
			/*2016 11 16 �����ȣ ��� ����*/
			htmlStr.append("<td class='t1' style='padding:0px 5px 0px 5px'>");
			//htmlStr.append("<input type='hidden' style='width:50px' id='zip' name='zip' placeholder='�����ȣ'>");
			htmlStr.append("<input class='btn_find_post' type='button' onclick='sample6_execDaumPostcode()' value='�����ȣ ã��'><br>");
			htmlStr.append("<input type='text' style='width:100%;' name='addr1' id='addr1' placeholder='�ּ�' ");
			if(rbean != null && rbean.getAddr1() != null && rbean.getAddr1().length()>0){				
				htmlStr.append(" value='["+rbean.getZip()+"]"+rbean.getAddr1()+"'");
			}
			htmlStr.append(" readonly ><br>");	
			htmlStr.append("<input type='text' style='width:100%;' name='addr2' id='addr2' placeholder='���ּ�'  ");
			if(rbean != null && rbean.getAddr2() != null){
				htmlStr.append(" value='"+rbean.getAddr2()+"'");	
			}
			htmlStr.append("></td>");
			
			htmlStr.append("<script src='http://dmaps.daum.net/map_js_init/postcode.v2.js'></script>");
			htmlStr.append("<script>");
			htmlStr.append("function sample6_execDaumPostcode() { ");
			htmlStr.append("new daum.Postcode({ ");
			htmlStr.append("oncomplete: function(data) { ");
			htmlStr.append("var fullAddr = '';  ");
			htmlStr.append("var extraAddr = '';  ");
			htmlStr.append("if (data.userSelectedType === 'R') {  ");
			htmlStr.append("fullAddr = data.roadAddress; ");
			htmlStr.append("} else { ");
			htmlStr.append("fullAddr = data.jibunAddress; ");
			htmlStr.append("} ");
			htmlStr.append("if(data.userSelectedType === 'R'){");
			htmlStr.append("if(data.bname !== ''){ ");
			htmlStr.append("extraAddr += data.bname; ");
			htmlStr.append("}");
			htmlStr.append("if(data.buildingName !== ''){ ");
			htmlStr.append("extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); ");
			htmlStr.append("}");
			htmlStr.append("fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : ''); ");
			htmlStr.append("} ");
			//htmlStr.append("document.getElementById('zip').value = data.zonecode; ");
			
			htmlStr.append("document.getElementById('addr1').value = '['+data.zonecode+']'+fullAddr; ");
			htmlStr.append("document.getElementById('addr2').focus();");
			htmlStr.append("} ");
			htmlStr.append("}).open(); ");
			htmlStr.append("} ");
			htmlStr.append("</script>");
			/* -- �����ȣ ����*/
			htmlStr.append("</tr>");
		  	htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//Email
		if("1".equals(btype[5])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>E-Mail<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>E-Mail<font color='red'>(*)</font></td>");
			
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("	<input name='email' type='text' title='�̸���' style='width:170px;' maxlength='50' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getEmail() != null){
				htmlStr.append(" value='"+rbean.getEmail()+"'");
			}
			htmlStr.append(">");
			if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			    htmlStr.append(" &nbsp;&nbsp;MailAddress@junggu.seoul.kr</td>");
			} else {
			    htmlStr.append("	&nbsp;&nbsp;MailAddress@korea.kr</td>");
			}
			htmlStr.append("</tr>");
		    htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//��ȭ��ȣ
		if("1".equals(btype[6])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>��ȭ��ȣ<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>��ȭ��ȣ<font color='red'>(*)</font></td>");
            
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("	<input name='tel' type='text' title='��ȭ��ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getTel() != null){
				htmlStr.append(" value='"+rbean.getTel()+"'");
			}
			htmlStr.append(">");
			if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			    htmlStr.append(" &nbsp;&nbsp;��) 02-3396-5678</td>");
			} else {
			    htmlStr.append("	&nbsp;&nbsp;��) 02-1234-5678</td>");
			}
			htmlStr.append("</tr>");
		    htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
        //�޴���ȭ��ȣ
		if("1".equals(btype[7])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>�޴���ȭ��ȣ<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>�޴���ȭ��ȣ<font color='red'>(*)</font></td>");
            
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("	<input name='cel' type='text' title='�޴���ȭ��ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getCel() != null){
				htmlStr.append(" value='"+rbean.getCel()+"'");
			}
			htmlStr.append(">");
			htmlStr.append("	&nbsp;&nbsp;��) 010-1234-5678</td>");
			htmlStr.append("</tr>");
			htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//FAX��ȣ
		if("1".equals(btype[8])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)			
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>FAX��ȣ<font color='red'>(*)</font></td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>FAX��ȣ<font color='red'>(*)</font></td>");
            
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("	<input name='fax' type='text' title='�ѽ���ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getFax() != null){
				htmlStr.append("value='"+rbean.getFax()+"'");
			}
			htmlStr.append(">");
			htmlStr.append("	&nbsp;&nbsp;��) 02-1234-5678</td>");
			htmlStr.append("</tr>");
			htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
		
		//÷������
		if("1".equals(btype[9])){
			htmlStr.append("<tr>");
			//������û ��û�� UI���� (JHkim, 14.03.28)
//			htmlStr.append("	<td align='center' class='s1' style='padding:0px 5px 0px 5px'>÷������</td>");
			htmlStr.append(" <td align='center' class='");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                htmlStr.append("ptext");
            }
            else
            {
                htmlStr.append("s1");
            }
            htmlStr.append("' style='padding:0px 5px 0px 5px'>÷������</td>");
			
			htmlStr.append("	<td class='t1' style='padding:0px 5px 0px 5px'>");
			htmlStr.append("		<input type='file' id='attachFile' name='attachFile' title='÷������' style='width:80%'><input type='button' title='÷�����' onclick='resetFile(\"attachFile\")' style='height:18px;' value='÷�����'>");
			if ( rbean != null && !"".equals(Utils.nullToEmptyString(rbean.getOriginfilenm())) ) {
				htmlStr.append("		<br>÷������(<input type='checkbox' name='attachFileYN' title='����' style='border:0px;background-color:transparent;' value='N'>����) : <a class='list_s2' target='actionFrame' href='/download.do?tempFileName=" + rbean.getFilenm() + "&fileName=" + rbean.getOriginfilenm() + "'>" + rbean.getOriginfilenm() + "</a>");
			}			
			htmlStr.append("	</td>");
			htmlStr.append("</tr>");
			htmlStr.append("<tr>");
			htmlStr.append("	<td colspan='2' class='bg1'></td>");
			htmlStr.append("</tr>");
		}
              
        result = htmlStr.toString();
        
		return result;
	}
	
	/**
	 * ��û�� �⺻ �Է��׸��� ����(2017.05.29) ������ ����
	 */
	public static String basicInput_v01(String basictype, ReqMstBean rbean ){		
		String result = "";
		//System.out.println("basicInput_v01 : "+nexti.ejms.common.appInfo.getRootid());
		String[] btype = new String[11];
		
		if(basictype != null && !"".equals(basictype)){
			String[] temp = basictype.split(",");
			for(int i=0;i<temp.length;i++){
				btype[Integer.parseInt(temp[i])-1]= "1";
			}
		}		
		
		StringBuffer htmlStr = new StringBuffer();
		
		//��û��
		if("1".equals(btype[0])){ 
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                //htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
                //htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>����<span class='required'>(*)</span></label>");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");
			htmlStr.append("    	<input type='hidden' name='presentid'");    
			if(rbean != null && rbean.getPresentid() != null){
				htmlStr.append(" value='"+rbean.getPresentid()+"'");
			}
			htmlStr.append("><input name='presentnm' type='text' title='����' style='width:170px;' maxlength='10' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getPresentnm() != null){
				htmlStr.append(" value='"+rbean.getPresentnm()+"'");				
			}
			//Ư�� ��û���� ���� ������ �����Ұ��ϵ��� ����(JHkim, 14.02.17)
			if( "��õ6280000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
			    if( rbean != null && rbean.getReqformno() == 78 ){
			        htmlStr.append(" readonly ");
			    }
			}
			htmlStr.append("></div>");
			htmlStr.append("</div>");
	       
        }
//		���ֽ�û ���� ����  .. �ּ� start
		//�������
		if("1".equals(btype[10])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
			if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
                //htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
                //htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
			htmlStr.append(" <label for=''>�������<span class='required'>(*)</span></label> ");
			htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");
			htmlStr.append("	<input name='presentbd' maxlength='8' title='�������' style='width:170px;ime-mode:disabled;' onkeydown='key_num();' ");
			if(rbean != null && rbean.getPresentbd() != null){
				htmlStr.append(" value='"+rbean.getPresentbd()+"'");				
			}
			htmlStr.append("> &nbsp;&nbsp; ���ڸ� �Է��ϼ��� (�� : 20111212)</td>");
			htmlStr.append("></div>");
			htmlStr.append("</div>");
		
		}
//		���ֽ�û ���� ����  .. �ּ� end	
	
		//�Ҽ�
		if("1".equals(btype[2])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	 //htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	 //htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>�Ҽ�<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");
			htmlStr.append("	<input name='position' type='text' title='�μ�' maxlength='50' style='width:170px;' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getPosition() != null){
				htmlStr.append(" value='"+rbean.getPosition()+"'");
			}
			htmlStr.append("></div>");
			htmlStr.append("</div>");   
			
		}
		
		//����
		if("1".equals(btype[3])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	 //htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>����<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");
			htmlStr.append("	<input name='duty' type='text' title='����' maxlength='50' style='width:170px;' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getDuty() != null){
				htmlStr.append(" value='"+rbean.getDuty()+"'");		
			}
			htmlStr.append("></div>");
			htmlStr.append("</div>"); 
         	
		}
		
		//�ּ�
		if("1".equals(btype[4])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>�ּ�<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10 address-area'>");			
			htmlStr.append(" <a href='javascript:;' class='btn-zipcode' title='��â����' onclick='sample6_execDaumPostcode()'>�����ȣã��</a> ");			
			htmlStr.append("<input type='text' style='width:100%;' name='addr1' id='addr1' placeholder='�ּ�' ");
			if(rbean != null && rbean.getAddr1() != null && rbean.getAddr1().length()>0){				
				htmlStr.append(" value='["+rbean.getZip()+"]"+rbean.getAddr1()+"'");
			}
			htmlStr.append(" readonly >");	
			htmlStr.append("<input type='text' style='width:100%;' name='addr2' id='addr2' placeholder='���ּ�'  ");
			if(rbean != null && rbean.getAddr2() != null){
				htmlStr.append(" value='"+rbean.getAddr2()+"'");	
			}
			htmlStr.append("></div>");
			
			htmlStr.append("<script src='http://dmaps.daum.net/map_js_init/postcode.v2.js'></script>");
			htmlStr.append("<script>");
			htmlStr.append("function sample6_execDaumPostcode() { ");
			htmlStr.append("new daum.Postcode({ ");
			htmlStr.append("oncomplete: function(data) { ");
			htmlStr.append("var fullAddr = '';  ");
			htmlStr.append("var extraAddr = '';  ");
			htmlStr.append("if (data.userSelectedType === 'R') {  ");
			htmlStr.append("fullAddr = data.roadAddress; ");
			htmlStr.append("} else { ");
			htmlStr.append("fullAddr = data.jibunAddress; ");
			htmlStr.append("} ");
			htmlStr.append("if(data.userSelectedType === 'R'){");
			htmlStr.append("if(data.bname !== ''){ ");
			htmlStr.append("extraAddr += data.bname; ");
			htmlStr.append("}");
			htmlStr.append("if(data.buildingName !== ''){ ");
			htmlStr.append("extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); ");
			htmlStr.append("}");
			htmlStr.append("fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : ''); ");
			htmlStr.append("} ");
			//htmlStr.append("document.getElementById('zip').value = data.zonecode; ");
			
			htmlStr.append("document.getElementById('addr1').value = '['+data.zonecode+']'+fullAddr; ");
			htmlStr.append("document.getElementById('addr2').focus();");
			htmlStr.append("} ");
			htmlStr.append("}).open(); ");
			htmlStr.append("} ");
			htmlStr.append("</script>");
			/* -- �����ȣ ����*/
			htmlStr.append("</div>");
		}
		
		//Email
		if("1".equals(btype[5])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>E-Mail<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");			
			htmlStr.append("	<input name='email' type='text' title='�̸���' style='width:170px;' maxlength='50' onkeydown='key_entertotab()'");
			if(rbean != null && rbean.getEmail() != null){
				htmlStr.append(" value='"+rbean.getEmail()+"'");
			}
			htmlStr.append(">");
			if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			    htmlStr.append(" &nbsp;&nbsp;MailAddress@junggu.seoul.kr</div>");
			} else {
			    htmlStr.append("	&nbsp;&nbsp;MailAddress@korea.kr</div>");
			}
			htmlStr.append("</div>");
		}
		
		//��ȭ��ȣ
		if("1".equals(btype[6])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>��ȭ��ȣ<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");		
			htmlStr.append("	<input name='tel' type='text' title='��ȭ��ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getTel() != null){
				htmlStr.append(" value='"+rbean.getTel()+"'");
			}
			htmlStr.append(">");
			if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			    htmlStr.append(" &nbsp;&nbsp;��) 02-3396-5678</div>");
			} else {
			    htmlStr.append("	&nbsp;&nbsp;��) 02-1234-5678</div>");
			}
			htmlStr.append("</div>");
		}
		
        //�޴���ȭ��ȣ
		if("1".equals(btype[7])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>�ڵ�����ȣ<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");		
			htmlStr.append("	<input name='cel' type='text' title='�޴���ȭ��ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getCel() != null){
				htmlStr.append(" value='"+rbean.getCel()+"'");
			}
			htmlStr.append(">");
			htmlStr.append("	&nbsp;&nbsp;��) 010-1234-5678</div>");
			htmlStr.append("</div>");
		}
		
		//FAX��ȣ
		if("1".equals(btype[8])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>FAX��ȣ<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");		
			htmlStr.append("	<input name='fax' type='text' title='�ѽ���ȣ' style='width:170px;ime-mode:disabled;' maxlength='20' onkeydown='key_num_minus()'");
			if(rbean != null && rbean.getFax() != null){
				htmlStr.append("value='"+rbean.getFax()+"'");
			}
			htmlStr.append(">");
			htmlStr.append("	&nbsp;&nbsp;��) 02-1234-5678</div>");
			htmlStr.append("</div>");
		}
		
		//÷������
		if("1".equals(btype[9])){
			htmlStr.append("<div class='row inner-form'>");
			htmlStr.append(" <div class='col-xs-2'>");
            if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
            {
            	//htmlStr.append("ptext");//ptext{color: #009BCE; height="35"; font-weight: bold; padding-left:5; letter-spacing:-1;}
            }
            else
            {
            	//htmlStr.append("s1");//s1{color: #328ED0; height="28";padding-top="3"; padding-left="20"; background-color="#F8FCFF";}
            }
            htmlStr.append(" <label for=''>÷������<span class='required'>(*)</span></label> ");
            htmlStr.append(" </div>");
			htmlStr.append("	<div class='col-xs-10'>");				
			//htmlStr.append("	<table style='border:0px solid #ccc;width:300px'>");	
			htmlStr.append("	<table style='border:0px solid #ccc;'>");	
			//htmlStr.append("	<colgroup><cols width='200' /><cols width='100' /></colgroup>	");	
			htmlStr.append("	<tr><td>	");				
			htmlStr.append("		<input type='file' id='attachFile' style='font-size:13px;' name='attachFile' title='÷������'>");
			htmlStr.append("	</td><td>	");	
			htmlStr.append("		<input type='button' title='÷�����' style='font-size:13px;margin-left:5px;' onclick='resetFile(\"attachFile\")'  value='÷�����'>");
			htmlStr.append("	</td></table>	");	
			if ( rbean != null && !"".equals(Utils.nullToEmptyString(rbean.getOriginfilenm())) ) {
				htmlStr.append("		<br>÷������(<input type='checkbox' name='attachFileYN' title='����' style='border:0px;background-color:transparent;' value='N'>����) : <a class='list_s2' target='actionFrame' href='/download.do?tempFileName=" + rbean.getFilenm() + "&fileName=" + rbean.getOriginfilenm() + "'>" + rbean.getOriginfilenm() + "</a>");
			}			
			htmlStr.append("	</div>");
			htmlStr.append("</div>");
		}
              
        result = htmlStr.toString();
        
		return result;
	}
	
	
	/**
	 * �߰� �Է��׸��� ����
	 */
	public static String addInput(List pform, ReqMstBean rbean, String basePath ){
		String result = "";
		
		if(pform != null){
			
			//��û�� �Է� ������
			List pdata = null;			
			if(rbean != null){
				pdata = rbean.getAnscontList();
			}
			
			StringBuffer htmlStr = new StringBuffer();			
			for(int i=0;i<pform.size();i++){
				ArticleBean abean = (ArticleBean)pform.get(i);	
				
				//��û ������(�߰��Է��׸�)�� �����´�.
				ReqSubBean subbean = null;
				if(pdata != null){
					subbean = (ReqSubBean)pdata.get(i);
				}
				
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
				{
    				htmlStr.append("<tr>");
                    htmlStr.append("<td colspan='2'>");
                    htmlStr.append("<table width='644' border='0' cellspacing='0' cellpadding='0'>");
				}
				
				if ( htmlStr.length() > 0 ) {
					htmlStr.append("<tr>");
					htmlStr.append("	<td colspan='2' class='bg1'></td>");
					htmlStr.append("</tr>");
				}
				
				htmlStr.append("<tr>");
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
				{
				    htmlStr.append("    <td class='ptext' style='padding:0px 0px 0px 0px'>"+abean.getFormseq()+". "+abean.getFormtitle()+"");
				}
				else
				{
				    htmlStr.append("    <td align='center' class='s1' style='padding:0px 5px 0px 5px'>"+abean.getFormtitle()+"");
				}
				
				htmlStr.append("	<input type='hidden' name='formseq' value='"+abean.getFormseq()+"'>");
				
				//�ʼ� �Է��׸� ǥ��
				if("Y".equals(abean.getRequire())){
					htmlStr.append("<font color='red'>(*)</font>");
				}
				
				if ( abean.getOriginfilenm() != null && abean.getOriginfilenm().equals("") == false ) {
				    //������û ��û�� UI���� (JHkim, 14.03.28)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
	                {
	                    htmlStr.append(" (÷������ : <a id='imgLink" + i + "' class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + ", 'imageView" + i + "', '" + basePath + abean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
                        htmlStr.append("<a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + abean.getFilenm() + "&fileName=" + abean.getOriginfilenm() + "\">" + abean.getOriginfilenm() + ")</a> ");
	                }
	                else
	                {
	                    htmlStr.append("<br>÷������ : <a id='imgLink" + i + "' class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + ", 'imageView" + i + "', '" + basePath + abean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a><br> ");
                        htmlStr.append("<a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + abean.getFilenm() + "&fileName=" + abean.getOriginfilenm() + "\">" + abean.getOriginfilenm() + "</a> ");
	                }
					
					htmlStr.append("<div id=\"imagePreview" + i + "\" style=\"position:absolute;display:none;z-index:999\"> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><iframe id=\"imagePreview" + i + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + ")\"><img src=\"\" id=\"imageView" + i + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("</div> ");
					htmlStr.append("<script type='text/javascript'>imagePreview" + i + ".style.left = imgLink" + i + ".offsetLeft + 20</script> ");
				}
				
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
				{
				    htmlStr.append("    </td>");
	                htmlStr.append("</tr>");
	                htmlStr.append("</table></td></tr>");
				}
				else
				{
				    htmlStr.append("    </td>");
	                htmlStr.append("    <td class='t1' style='padding:0px 5px 0px 5px'>");
				}
				
				//�Է����� : ���� ������
				if("1".equals(abean.getFormtype())){
					
					String value1 = "";
					String value2 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
	                {
	                    htmlStr.append("<tr>");
	                    htmlStr.append("    <td colspan=2>");
	                    htmlStr.append("    <table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
	                }
					
					if(subbean != null){
						if(subbean.getAnscont() != null) {value1 = subbean.getAnscont();}
						if(subbean.getOther() != null){value2 = subbean.getOther();}
					}
					
					for(int j=0;j<abean.getSampleList().size();j++){
						SampleBean sbean = (SampleBean)abean.getSampleList().get(j);
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
	                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
	                    {
	                        htmlStr.append("<tr><td class='ptext2'>");
	                    }
						
						if(!"".equals(sbean.getExamcont().trim())){
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {
								
								String imgpreview = null;
								try {
									if ( abean.getReqformno() > 1 ) {
										imgpreview = SinchungManager.instance().reqFormInfo(abean.getReqformno()).getImgpreview();
									} else {
										imgpreview = SinchungManager.instance().reqFormInfo_temp(abean.getSessi()).getImgpreview();
									}
								} catch ( Exception exception_imgpreview ) {}

	                        	if ( "1".equals(imgpreview) ) {
		                      		String filenm = Utils.nullToEmptyString(sbean.getFilenm());
		                      		String fileext = Utils.nullToEmptyString(sbean.getExt()).toLowerCase();
		                      		if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) {
		                      			htmlStr.append("<center><br>[����׸�-" + (i + 1) + (j + 1) + "]<br><div style=\"width:500px;overflow:auto\"><img src='" + basePath + filenm + "' alt='����׸�" + (j + 1) + "'></div></center>");
		                      		}
								}
							}
							
							htmlStr.append("<input type='radio' name='radioans["+i+"]' class='radioans"+i+"'  title='����" + (j + 1) + "' style='border:0px;background-color:transparent' value="+j);
							
							if(value1.equals(String.valueOf(j))){
								htmlStr.append(" checked ");
							} 
							htmlStr.append(">"+sbean.getExamcont());
	
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {								
								htmlStr.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + sbean.getFilenm() + "&fileName=" + sbean.getOriginfilenm() + "\">" + sbean.getOriginfilenm() + "</a> ");
								htmlStr.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + j + ", 'imageView" + i + j + "', '" + basePath + sbean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
								htmlStr.append("<div id=\"imagePreview" + i + j + "\" style=\"position:absolute;display:none;z-index:999\"> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><iframe id=\"imagePreview" + i + j + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + j + ")\"><img src=\"\" id=\"imageView" + i + j + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("</div> ");
							}
														
							if(abean.getSampleList().size() > j){
								htmlStr.append("<br>");     //�������� �ƴϸ� <br>�߰�
							}
						}
					}
					if ( htmlStr.length() > 0 && htmlStr.lastIndexOf("<br>") + 4 == htmlStr.length() ) htmlStr.delete(htmlStr.length() - 4, htmlStr.length());	//������ <br>�� ����
					
					if("Y".equals(abean.getExamtype())){
					    //������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("<tr><td class='ptext2'>");
                        }
					    
					    
						//��ŸYN�� ���� ���� ���
						htmlStr.append("<br>");		
						htmlStr.append("<input type='radio' name='radioans["+i+"]' class='radioans"+i+"' title='��Ÿ' style='border:0px;background-color:transparent' value='" + abean.getSampleList().size() + "'");
						if( Integer.toString(abean.getSampleList().size()).equals(value1) ) {
							htmlStr.append(" checked");
						}
						htmlStr.append("> ��Ÿ : ");
						htmlStr.append("<input type='text' name='other' title='��Ÿ' style='width:87%;' maxlength='500' value='"+value2+"' onkeydown='key_entertotab()'>");
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("</td></tr>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("    </table>");
                        htmlStr.append("    </td>");
                        htmlStr.append("</tr>");
                    }
				}
				
				//�Է����� : ���� ������
				if("2".equals(abean.getFormtype())){
					
					String[] value1 = {};
					String value2 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("<tr>");
                        htmlStr.append("    <td colspan=2>");
                        htmlStr.append("    <table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
                    }
					
					if(subbean != null){
						if(subbean.getAnscont() != null){
							value1 = subbean.getAnscont().split(",");
						}
						if(subbean.getOther() != null){value2 = subbean.getOther();}
					}
					
					for(int j=0;j<abean.getSampleList().size();j++){
						SampleBean sbean = (SampleBean)abean.getSampleList().get(j);
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
	                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
	                    {
	                        htmlStr.append("<tr><td class='ptext2'>");
	                    }
						
						if(!"".equals(sbean.getExamcont().trim())){
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {
								
								String imgpreview = null;
								try {
									if ( abean.getReqformno() > 1 ) {
										imgpreview = SinchungManager.instance().reqFormInfo(abean.getReqformno()).getImgpreview();
									} else {
										imgpreview = SinchungManager.instance().reqFormInfo_temp(abean.getSessi()).getImgpreview();
									}
								} catch ( Exception exception_imgpreview ) {}
								
	                        	if ( "1".equals(imgpreview) ) {
		                      		String filenm = Utils.nullToEmptyString(sbean.getFilenm());
		                      		String fileext = Utils.nullToEmptyString(sbean.getExt()).toLowerCase();
		                      		if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) {
		                      			htmlStr.append("<center><br>[����׸�-" + (i + 1) + (j + 1) + "]<br><div style=\"width:500px;overflow:auto\"><img src='" + basePath + filenm + "' alt='����׸�" + (j + 1) + "'></div></center>");
		                      		}
								}
							}
							
							htmlStr.append("<input type='checkbox' name='chkans' title='����" + (j + 1) + "' style='border:0px;background-color:transparent' value='"+i+","+j+"'");
							
							if(value1 != null){
								for(int k=0;k<value1.length;k++){
									if(value1[k].equals(String.valueOf(j))){
										htmlStr.append(" checked ");
									}
								}
							}
							htmlStr.append("> "+sbean.getExamcont());
							
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false ) {
								htmlStr.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + sbean.getFilenm() + "&fileName=" + sbean.getOriginfilenm() + "\">" + sbean.getOriginfilenm() + "</a> ");
								htmlStr.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + j + ", 'imageView" + i + j + "', '" + basePath + sbean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
								htmlStr.append("<div id=\"imagePreview" + i + j + "\" style=\"position:absolute;display:none;z-index:999\"> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><iframe id=\"imagePreview" + i + j + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + j + ")\"><img src=\"\" id=\"imageView" + i + j + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("</div> ");
							}
							
							htmlStr.append("<br>");
						}
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("</td></tr>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 )
                    {
                        //������û �ƴҶ��� (JHkim, 14.03.28)
                        if(htmlStr.length()>0){htmlStr.delete(htmlStr.length()-4, htmlStr.length());}   //������ <br>�� ����
                    }
					
					if("Y".equals(abean.getExamtype())){
					    //������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("<tr><td class='ptext2'>");
                        }
					    
						//��ŸYN�� ���� ���� ���
						htmlStr.append("<br>");	
						htmlStr.append("<input type='checkbox' name='chkans' title='��Ÿ' style='border:0px;background-color:transparent' value='"+i+"," + abean.getSampleList().size() + "'");
						
						if(value1 != null){
							for(int k=0;k<value1.length;k++){
								if( Integer.toString(abean.getSampleList().size()).equals(value1[k])){
									htmlStr.append(" checked ");
								}
							}
						}
						htmlStr.append("> ��Ÿ : ");
						htmlStr.append("<input type='text' name='other' title='��Ÿ' style='width:87%;' maxlength='500' value='"+value2+"' onkeydown='key_entertotab()'>");
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("</td></tr>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("    </table>");
                        htmlStr.append("    </td>");
                        htmlStr.append("</tr>");
                    }
				}
				
				//�Է����� : �ܴ���
				if("3".equals(abean.getFormtype())){
				    //������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("<tr>");
                        htmlStr.append("    <td colspan=2>");
                        htmlStr.append("    <table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
                    }
					
					String value1 = "";					
					if(subbean != null && subbean.getAnscont() != null){
						value1 = subbean.getAnscont();							
					}
										
					if(abean.getHelpexp() != null){
						htmlStr.append(abean.getHelpexp()+"<br>");				
					}
					if("Y".equals(abean.getSecurity())){
						//����ó��
						htmlStr.append("<input name='txtans' type='password' title='�ܹ��亯�Է�' style='width:99%;' maxlength='500' value='"+value1+"' onkeydown='key_entertotab()'>");	
					} else {
						htmlStr.append("<input name='txtans' type='text' title='�ܹ��亯�Է�' style='width:99%;' maxlength='500' value='"+value1+"' onkeydown='key_entertotab()'>");
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("    </table>");
                        htmlStr.append("    </td>");
                        htmlStr.append("</tr>");
                    }
				}
								
				//�Է����� : �幮��
				if("4".equals(abean.getFormtype())){
					
					String value1 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("<tr>");
                        htmlStr.append("    <td colspan=2>");
                        htmlStr.append("    <table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
                    }
					
					if(subbean != null && subbean.getAnscont() != null){
						value1 = subbean.getAnscont();						
					}
					
					if(abean.getHelpexp() != null){
						htmlStr.append(abean.getHelpexp()+"<br>");				
					}						
					htmlStr.append("<textarea name='areaans' rows='5' cols='0' title='�幮�亯�Է�' style='width:99%;' onkeyup='maxlength_check(this, 1000)' onkeydown='return;key_entertotab()'>"+value1+"</textarea>");
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                        htmlStr.append("    </table>");
                        htmlStr.append("    </td>");
                        htmlStr.append("</tr>");
                    }
				}
				
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 )
				{
				    //������û �ƴҶ��� (JHkim, 14.03.28)
				    htmlStr.append("    </td>");
	                htmlStr.append("</tr>");
				}
			}
			
			result = htmlStr.toString();
		}
        
		return result;	
	}
	
	/**
	 * ��û �߰� �Է��׸��� ����(2017.05.29) ������ ����
	 */
	public static String addInput_v01(List pform, ReqMstBean rbean, String basePath ){
		String result = "";
		//System.out.println("addInput_v01 : "+nexti.ejms.common.appInfo.getRootid());
		if(pform != null){
			
			//��û�� �Է� ������
			List pdata = null;			
			if(rbean != null){
				pdata = rbean.getAnscontList();
			}
			
			StringBuffer htmlStr = new StringBuffer();			
			int elistsize = pform.size();
			for(int i=0;i<pform.size();i++){
				ArticleBean abean = (ArticleBean)pform.get(i);	
				
				//��û ������(�߰��Է��׸�)�� �����´�.
				ReqSubBean subbean = null;
				if(pdata != null){
					subbean = (ReqSubBean)pdata.get(i);
				}
				
				htmlStr.append("	<input type='hidden' name='formseq"+i+"' value='"+abean.getFormseq()+"'>");
				htmlStr.append("	<input type='hidden' name='formtype"+i+"' value='"+abean.getFormtype()+"'>");
				htmlStr.append("	<input type='hidden' name='examtype"+i+"' value='"+abean.getExamtype()+"'>");
				htmlStr.append("	<input type='hidden' name='exsqs"+i+"' value='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"'>");
				htmlStr.append("	<input type='hidden' name='exfrsq"+i+"' value='"+abean.getEx_frsq()+"'>");
				htmlStr.append("	<input type='hidden' name='exexsq"+i+"' value='"+abean.getEx_exsq()+"'>");
				
				String[] exfrsq = abean.getEx_exsq().split("_");
				for(int j = 0 ; j < exfrsq.length ; j++){
					htmlStr.append("	<input type='hidden' name='ex1_"+abean.getEx_frsq()+"_"+exfrsq[j]+"' value='"+abean.getEx_exsq()+"'>");
				}
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
				{
					if(abean.getEx_frsq() > 0){
						htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' style='display:none' >");
						//htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' >");
					}else{
						htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' >");
					}
				}else{
					if(abean.getEx_frsq() > 0){
						htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' style='display:none' >");
						//htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' >");
					}else{
						htmlStr.append("<li class='ex_"+abean.getEx_frsq()+"_"+abean.getEx_exsq()+"' >");
					}
				}
				
				/*if ( htmlStr.length() > 0 ) {
					htmlStr.append("<tr>");
					htmlStr.append("	<td colspan='2' class='bg1'></td>");
					htmlStr.append("</tr>");
				}*/
				 
				//htmlStr.append("<tr>");
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
				{
				    htmlStr.append("    <div class='survey-list-title'><p class='detail-txt'>"+abean.getFormtitle());
				}
				else
				{
				    htmlStr.append("    <div class='survey-list-title'><p class='detail-txt'>"+abean.getFormtitle());
				}
				
				//�ʼ� �Է��׸� ǥ��
				if("Y".equals(abean.getRequire())){
					htmlStr.append("<font color='red'>(*)</font>");
				}
				
				htmlStr.append("	<input type='hidden' name='formseq' value='"+abean.getFormseq()+"'>");
				
				if ( abean.getOriginfilenm() != null && abean.getOriginfilenm().equals("") == false ) {
				    //������û ��û�� UI���� (JHkim, 14.03.28)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
	                {
	                    htmlStr.append(" (÷������ : <a id='imgLink" + i + "' class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + ", 'imageView" + i + "', '" + basePath + abean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
                        htmlStr.append("<a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + abean.getFilenm() + "&fileName=" + abean.getOriginfilenm() + "\">" + abean.getOriginfilenm() + ")</a> ");
	                }
	                else
	                {
	                    htmlStr.append(" (÷������ : <a id='imgLink" + i + "' class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + ", 'imageView" + i + "', '" + basePath + abean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
                        htmlStr.append("<a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + abean.getFilenm() + "&fileName=" + abean.getOriginfilenm() + "\">" + abean.getOriginfilenm() + ")</a> ");
	                }
					
					htmlStr.append("<div id=\"imagePreview" + i + "\" style=\"position:absolute;display:none;z-index:999\"> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><iframe id=\"imagePreview" + i + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + ")\"><img src=\"\" id=\"imageView" + i + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("</div> ");
					htmlStr.append("<script type='text/javascript'>imagePreview" + i + ".style.left = imgLink" + i + ".offsetLeft + 20</script> ");
				}
				htmlStr.append("</p>");
				if(abean.getHelpexp() != null){
					htmlStr.append(abean.getHelpexp());				
				}
				//������û ��û�� UI���� (JHkim, 14.03.28)
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
				{
				    htmlStr.append("    </div>");
				}
				else
				{
					htmlStr.append("    </div>");
	                //htmlStr.append("    <td class='t1' style='padding:0px 5px 0px 5px'>");
				}
				
				//�Է����� : ���� ������
				if("1".equals(abean.getFormtype())){
					
					String value1 = "";
					String value2 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
	                if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
	                {
	                    htmlStr.append("<div class='survey-list-form'>");
	                    htmlStr.append("    <ul>");
	                }else{
	                	htmlStr.append("<div class='survey-list-form'>");
		                htmlStr.append("    <ul>");
	                }
					
					if(subbean != null){
						if(subbean.getAnscont() != null) {value1 = subbean.getAnscont();}
						if(subbean.getOther() != null){value2 = subbean.getOther();}
					}
					
					for(int j=0;j<abean.getSampleList().size();j++){
						SampleBean sbean = (SampleBean)abean.getSampleList().get(j);
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
	                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
	                    {
	                        htmlStr.append("<li>");
	                    }else{
	                    	htmlStr.append("<li>");
	                    }
						
						if(!"".equals(sbean.getExamcont().trim())){
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {
								
								String imgpreview = null;
								try {
									if ( abean.getReqformno() > 1 ) {
										imgpreview = SinchungManager.instance().reqFormInfo(abean.getReqformno()).getImgpreview();
									} else {
										imgpreview = SinchungManager.instance().reqFormInfo_temp(abean.getSessi()).getImgpreview();
									}
								} catch ( Exception exception_imgpreview ) {}

	                        	if ( "1".equals(imgpreview) ) {
		                      		String filenm = Utils.nullToEmptyString(sbean.getFilenm());
		                      		String fileext = Utils.nullToEmptyString(sbean.getExt()).toLowerCase();
		                      		if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) {
		                      			htmlStr.append("<center><br>[����׸�-" + (i + 1) + (j + 1) + "]<br><div style=\"width:500px;overflow:auto\"><img src='" + basePath + filenm + "' alt='����׸�" + (j + 1) + "'></div></center>");
		                      		}
								}
							}
							
							htmlStr.append("<input type='radio' name='radioans["+i+"]' class='radioans"+i+"'  title='����" + (j + 1) + "' value='"+j+"'");
							
							if(value1.equals(String.valueOf(j))){
								htmlStr.append(" checked ");
							} 
							htmlStr.append(" onclick='ex_research("+abean.getFormseq()+","+ (j + 1)+","+abean.getEx_frsq()+","+elistsize+","+i+")' ");
							htmlStr.append(" > <label for=''>"+sbean.getExamcont()+"</label>");
	
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {								
								htmlStr.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + sbean.getFilenm() + "&fileName=" + sbean.getOriginfilenm() + "\">" + sbean.getOriginfilenm() + "</a> ");
								htmlStr.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + j + ", 'imageView" + i + j + "', '" + basePath + sbean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
								htmlStr.append("<div id=\"imagePreview" + i + j + "\" style=\"position:absolute;display:none;z-index:999\"> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><iframe id=\"imagePreview" + i + j + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + j + ")\"><img src=\"\" id=\"imageView" + i + j + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("</div> ");
							}
														
						}
						 if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
	                        {
	                            htmlStr.append("</li>");
	                        }else{
	                        	htmlStr.append("</li>");
	                        }
					}
					
					if("Y".equals(abean.getExamtype())){
					    //������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                        {
                            htmlStr.append("<li>");
                        }else{
                        	htmlStr.append("<li>");
                        }
					    
					    
						//��ŸYN�� ���� ���� ���
						//htmlStr.append("<br>");		
						htmlStr.append("<input type='radio' name='radioans["+i+"]' class='radioans"+i+"'  title='��Ÿ' style='border:0px;background-color:transparent;margin-top:6px;' value='" + abean.getSampleList().size() + "'");
						if( Integer.toString(abean.getSampleList().size()).equals(value1) ) {
							htmlStr.append(" checked");
						}
						htmlStr.append(">");
						htmlStr.append("<label for='' style='width:100%'> ��Ÿ : ");
						htmlStr.append("<input type='text' name='other' title='��Ÿ' style='width:87%;' maxlength='500' value='"+value2+"' onkeydown='key_entertotab()'>");
						htmlStr.append("</label>");
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                        {
                            htmlStr.append("</li>");
                        }else{
                        	htmlStr.append("</li>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                        htmlStr.append("    </ul>");
                        htmlStr.append("</div>");
                    }else{
                    	 htmlStr.append("    </ul>");
                         htmlStr.append("</div>");
                    }
				}
				
				//�Է����� : ���� ������
				if("2".equals(abean.getFormtype())){
					
					String[] value1 = {};
					String value2 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                    	htmlStr.append("<div class='survey-list-form'>");
 	                    htmlStr.append("    <ul>");
                    }else{
                    	htmlStr.append("<div class='survey-list-form'>");
 	                    htmlStr.append("    <ul>");
                    }
					
					if(subbean != null){
						if(subbean.getAnscont() != null){
							value1 = subbean.getAnscont().split(",");
						}
						if(subbean.getOther() != null){value2 = subbean.getOther();}
					}
					
					for(int j=0;j<abean.getSampleList().size();j++){
						SampleBean sbean = (SampleBean)abean.getSampleList().get(j);
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
	                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
	                    {
	                        htmlStr.append("<li>");
	                    }else{
	                    	htmlStr.append("<li>");
	                    }
						
						if(!"".equals(sbean.getExamcont().trim())){
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false) {
								
								String imgpreview = null;
								try {
									if ( abean.getReqformno() > 1 ) {
										imgpreview = SinchungManager.instance().reqFormInfo(abean.getReqformno()).getImgpreview();
									} else {
										imgpreview = SinchungManager.instance().reqFormInfo_temp(abean.getSessi()).getImgpreview();
									}
								} catch ( Exception exception_imgpreview ) {}
								
	                        	if ( "1".equals(imgpreview) ) {
		                      		String filenm = Utils.nullToEmptyString(sbean.getFilenm());
		                      		String fileext = Utils.nullToEmptyString(sbean.getExt()).toLowerCase();
		                      		if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) {
		                      			htmlStr.append("<center><br>[����׸�-" + (i + 1) + (j + 1) + "]<br><div style=\"width:500px;overflow:auto\"><img src='" + basePath + filenm + "' alt='����׸�" + (j + 1) + "'></div></center>");
		                      		}
								}
							}
							
							htmlStr.append("<input type='checkbox' name='chkans' title='����" + (j + 1) + "' style='border:0px;background-color:transparent;margin-top:8px;' value='"+i+","+j+"'");
							
							if(value1 != null){
								for(int k=0;k<value1.length;k++){
									if(value1[k].equals(String.valueOf(j))){
										htmlStr.append(" checked ");
									}
								}
							}
							htmlStr.append("> "+sbean.getExamcont());
							
							if ( sbean.getOriginfilenm() != null && sbean.getOriginfilenm().equals("") == false ) {
								htmlStr.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + sbean.getFilenm() + "&fileName=" + sbean.getOriginfilenm() + "\">" + sbean.getOriginfilenm() + "</a> ");
								htmlStr.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + j + ", 'imageView" + i + j + "', '" + basePath + sbean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
								htmlStr.append("<div id=\"imagePreview" + i + j + "\" style=\"position:absolute;display:none;z-index:999\"> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><iframe id=\"imagePreview" + i + j + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("	<table id=\"imagePreview" + i + j + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
								htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
								htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + j + ")\"><img src=\"\" id=\"imageView" + i + j + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
								htmlStr.append("		</tr> ");
								htmlStr.append("	</table> ");
								htmlStr.append("</div> ");
							}
							
						}
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("</li>");
                        }else{
                        	htmlStr.append("</li>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
					
					if("Y".equals(abean.getExamtype())){
					    //������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                        {
                            htmlStr.append("<li>");
                        }else{
                        	htmlStr.append("<li>");
                        }
					    
						//��ŸYN�� ���� ���� ���
						htmlStr.append("<input type='checkbox' name='chkans' title='��Ÿ' style='border:0px;background-color:transparent;margin-top:8px;' value='"+i+"," + abean.getSampleList().size() + "'");
						
						if(value1 != null){
							for(int k=0;k<value1.length;k++){
								if( Integer.toString(abean.getSampleList().size()).equals(value1[k])){
									htmlStr.append(" checked ");
								}
							}
						}						
						htmlStr.append("> ");
						htmlStr.append("<label for='' style='width:100%;margin-top:-25px;margin-left: 17px;padding-left:0px;'> ��Ÿ : ");
						htmlStr.append("<input type='text' name='other' title='��Ÿ' style='width:87%;' maxlength='500' value='"+value2+"' onkeydown='key_entertotab()'>");
						htmlStr.append("</label>");
						
						//������û ��û�� UI���� (JHkim, 14.03.28)
                        if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                        {
                            htmlStr.append("</li>");
                        }else{
                        	htmlStr.append("</li>");
                        }
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 )
                    {
                    	 htmlStr.append("    </ul>");
                         htmlStr.append("</div>");
                    }else{
                    	 htmlStr.append("    </ul>");
                         htmlStr.append("</div>");
                    }
				}
				
				//�Է����� : �ܴ���
				if("3".equals(abean.getFormtype())){
				    //������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                        htmlStr.append("<div class='survey-list-form'>");
                    }else{
                    	htmlStr.append("<div class='survey-list-form'>");
                    }
					
					String value1 = "";					
					if(subbean != null && subbean.getAnscont() != null){
						value1 = subbean.getAnscont();							
					}
										
					if("Y".equals(abean.getSecurity())){
						//����ó��
						htmlStr.append("<input name='txtans' id='txtans"+i+"' type='password' title='�ܹ��亯�Է�' style='width:99%;' maxlength='500' value='"+value1+"' onkeydown='key_entertotab()'>");	
					} else {
						htmlStr.append("<input name='txtans' id='txtans"+i+"' type='text' title='�ܹ��亯�Է�' style='width:99%;' maxlength='500' value='"+value1+"' onkeydown='key_entertotab()'>");
					}
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                        htmlStr.append("    </div>");
                    }else{
                    	htmlStr.append("    </div>");
                    }
				}
								
				//�Է����� : �幮��
				if("4".equals(abean.getFormtype())){
					
					String value1 = "";
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                    	  htmlStr.append("<div class='survey-list-form'>");
                    }else{
                    	
                    }htmlStr.append("<div class='survey-list-form'>");
					
					if(subbean != null && subbean.getAnscont() != null){
						value1 = subbean.getAnscont();						
					}
					
					htmlStr.append("<textarea name='areaans' id='areaans"+i+"' rows='5' cols='0' title='�幮�亯�Է�' style='width:99%;' onkeyup='maxlength_check(this, 1000)' onkeydown='return;key_entertotab()'>"+value1+"</textarea>");
					
					//������û ��û�� UI���� (JHkim, 14.03.28)
                    if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1)
                    {
                        htmlStr.append("    </div>");
                    }else{
                    	htmlStr.append("    </div>");
                    }
				}
				
				if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1)
				{
	                htmlStr.append("</li>");
				}else{
					htmlStr.append("</li>");
				}
			}
			
			result = htmlStr.toString();
		}
        
		return result;	
	}
	
	/**
	 * ��û�� Validation Check 
	 * javascript �Լ� ����
	 */
	public static String makeSubmitFunc(String basictype, List pform ){
		String result = "";

		StringBuffer htmlStr = new StringBuffer();
		
		htmlStr.append("<script type='text/javascript' language='javascript'>");
		htmlStr.append("	function check_submit(){ ");
		
		String[] formtitle = {"����","�ֹε�Ϲ�ȣ","�Ҽ�","����","�ּ�","E-Mail","��ȭ��ȣ","�޴���ȭ��ȣ","Fax��ȣ","÷������", "�������"};
		String[] inputName = {"presentnm","presentsn", "position","duty","addr1","email","tel","cel","fax","attachFile", "presentbd"};
		
		if(basictype != null && !"".equals(basictype)){	
			String[] temp = basictype.split(",");
			for(int i=0;i<temp.length;i++){
				if ( "10".equals(temp[i]) ) continue;	//÷������ ����
				htmlStr.append("if(document.forms[0]."+inputName[Integer.parseInt(temp[i])-1]+".value.trim() == ''){");
				htmlStr.append("	alert('"+formtitle[Integer.parseInt(temp[i])-1]+"��(��) �Է��ϼ���.');");
				htmlStr.append("    document.forms[0]."+inputName[Integer.parseInt(temp[i])-1]+".focus();");
				htmlStr.append("    return false;");
				htmlStr.append("}");
			}
		}
		
		//�߰��Է� �׸� Validation check
		if(pform != null){
			int txtcnt =0; int areacnt = 0;
			htmlStr.append("var idx_cnt = 0;");	
			htmlStr.append("var exsq_yn = 'N';");	
			htmlStr.append("var cnt=0;");	
			for(int i=0;i<pform.size();i++){
				ArticleBean abean = (ArticleBean)pform.get(i);	
				htmlStr.append("var exsq"+i+" = eval('document.forms[0].exsqs"+i+".value');");	
				htmlStr.append("var ex"+i+" = document.getElementsByClassName(exsq"+i+");");	
				htmlStr.append("if(ex"+i+"[0].style.display == 'none'){exsq_yn = 'Y';}else{exsq_yn = 'N';}");	
				if(abean != null && abean.getRequire() != null && "Y".equals(abean.getRequire())){
					if("1".equals(abean.getFormtype())){  //���ϼ�����
						htmlStr.append("var choice"+i+" = false;");	
						htmlStr.append("for(var i=0;i<document.getElementsByName('radioans["+i+"]').length;i++){");
						htmlStr.append("	if(document.getElementsByName('radioans["+i+"]')[i].checked){ ");
						htmlStr.append("		choice"+i+" = true; ");
						htmlStr.append("		break; ");
						htmlStr.append("	} ");
						htmlStr.append("}");
						htmlStr.append("if(!choice"+i+" && exsq_yn == 'N'){");
						htmlStr.append("	alert('"+abean.getFormtitle()+"��(��) �����ϼ���.');");
						htmlStr.append("    return false;");
						htmlStr.append("}");	
					}else if("2".equals(abean.getFormtype())){		// ���� ������
						htmlStr.append("var choice"+i+" = false;");						
						htmlStr.append("for(var i=0;i<document.getElementsByName('chkans').length;i++){");
						htmlStr.append("	if(document.getElementsByName('chkans')[i].checked){ ");
						htmlStr.append("		choice"+i+" = true; ");
						htmlStr.append("		break; ");
						htmlStr.append("	} ");
						htmlStr.append("}");
						htmlStr.append("if(!choice"+i+" && exsq_yn == 'N'){");
						htmlStr.append("	alert('"+abean.getFormtitle()+"��(��) �����ϼ���.');");
						htmlStr.append("    return false;");
						htmlStr.append("}");	
					}else{					
						String boxName = ""; 
						int cnt =0; 
						if("3".equals(abean.getFormtype())){  //�ܴ���
							boxName = "txtans"+i;  
							cnt = txtcnt;
							txtcnt = txtcnt + 1; 
						}   
						if("4".equals(abean.getFormtype())){  //�幮��
							boxName = "areaans"+i; 
							cnt= areacnt;
							areacnt = areacnt + 1; 
						}  
						
						if("3".equals(abean.getFormtype()) || "4".equals(abean.getFormtype())){
							htmlStr.append("if(typeof(document.getElementById('"+boxName+"').value) != 'undefined'  && exsq_yn == 'N'){");						
							htmlStr.append("	if(document.getElementById('"+boxName+"').value.trim() == ''){");
							htmlStr.append("		alert('"+abean.getFormtitle()+"��(��) �Է��ϼ���.');");
							htmlStr.append("		document.getElementById('"+boxName+"').value = '';");
							htmlStr.append("    	document.getElementById('"+boxName+"').focus();");
							htmlStr.append("    	return false;");
							htmlStr.append("	}");
							htmlStr.append("} else { ");
							htmlStr.append("	if(document.getElementById('"+boxName+String.valueOf(cnt)+"').value.trim() == ''  && exsq_yn == 'N'){");
							htmlStr.append("		alert('"+abean.getFormtitle()+"��(��) �Է��ϼ���.');");
							htmlStr.append("		document.getElementById('"+boxName+"').value = '';");
							htmlStr.append("    	document.getElementById('"+boxName+"').focus();");
							htmlStr.append("    	return false;");
							htmlStr.append("	}");
							htmlStr.append("} ");
						}else{							
							htmlStr.append("if(typeof(document.forms[0]."+boxName+".value) != 'undefined'  && exsq_yn == 'N'){");						
							htmlStr.append("	if(document.forms[0]."+boxName+".value.trim() == ''){");
							htmlStr.append("		alert('"+abean.getFormtitle()+"��(��) �Է��ϼ���.');");
							htmlStr.append("		document.forms[0]."+boxName+".value = '';");
							htmlStr.append("    	document.forms[0]."+boxName+".focus();");
							htmlStr.append("    	return false;");
							htmlStr.append("	}");
							htmlStr.append("} else { ");
							htmlStr.append("	if(document.forms[0]."+boxName+String.valueOf(cnt)+".value.trim() == ''  && exsq_yn == 'N'){");
							htmlStr.append("		alert('"+abean.getFormtitle()+"��(��) �Է��ϼ���.');");
							htmlStr.append("		document.forms[0]."+boxName+String.valueOf(cnt)+".value = '';");
							htmlStr.append("    	document.forms[0]."+boxName+String.valueOf(cnt)+".focus();");
							htmlStr.append("    	return false;");
							htmlStr.append("	}");
							htmlStr.append("} ");
						}
					
					}
				}				
			}
		}
		
		//htmlStr.append("	document.forms[0].submit();");
		htmlStr.append("	}");
		htmlStr.append("</script>");
        
		result = htmlStr.toString();
		return result;
	}
	
	/**
	 * ��û���� �� ����
	 * basictype:�Է±⺻����, pForm: ������� ��, pData: �׸�����
	 * gbn: mgr=>��İ�����, sanc=>������
	 */
	public static String dataView(String basictype, DataForm pData, List pForm, String gbn, String basePath){
		String WIDTH1 = "style='width:160px'";
		String WIDTH2 = "style='width:500px'";
		String result = "";		
		StringBuffer htmlStr = new StringBuffer();
				
		String zip = ""; String addr1 = ""; String addr2 = "";
		if(pData.getZip() != null){zip = "["+pData.getZip()+"]";}
		if(pData.getAddr1() != null){addr1 = pData.getAddr1();}
		if(pData.getAddr2() != null){addr2 = pData.getAddr2();}
		
		String attachFile = null;
		if ( !"".equals(Utils.nullToEmptyString(pData.getOriginfilenm())) ) {
			attachFile = "<a class='list_s2' target='actionFrame' href='/download.do?tempFileName=" + pData.getFilenm() + "&fileName=" + pData.getOriginfilenm() + "'>" + pData.getOriginfilenm() + "</a>";
		}
		
		String[] formtitle = {"��û��","�ֹε�Ϲ�ȣ","�Ҽ�","����","�ּ�","E-Mail","��ȭ��ȣ","�޴���ȭ��ȣ","Fax��ȣ","÷������", "�������"};
// ���ֽ�û ������ �迭������ 11->10
//		, "�������"}; 
		String[] datavalue = {pData.getPresentnm(),
							  pData.getPresentsn(),
							  pData.getPosition(),
							  pData.getDuty(),
							  zip+" "+addr1+" "+addr2,
							  pData.getEmail(),
							  pData.getTel(),
							  pData.getCel(),
							  pData.getFax(),
							  attachFile
							  ,pData.getPresentbd()
		};
// ���ֽ�û ������ �迭������ 11->10
//							  ,pData.getPresentbd()};
		
		if(basictype != null && !"".equals(basictype)){			
			String[] temp = basictype.split(",");
			for(int i=0;i<temp.length;i++){
				
				String ftitle = formtitle[Integer.parseInt(temp[i])-1];
				String dvalue = datavalue[Integer.parseInt(temp[i])-1];
				if(ftitle == null){ftitle = "";}
				if(dvalue == null){dvalue = "";}
				
				htmlStr.append("<tr>");
				htmlStr.append("   <td align='center' class='s1' style='padding:0px 5px 0px 5px'>"+ftitle+"</td>");
				
				if(i==1 && "sanc".equals(gbn)) {
					//�ֹε�Ϲ�ȣ ��ȣó��
					if ( dvalue.length() > 6 ) {
						dvalue = dvalue.substring(0, 6) + "-" + dvalue.substring(6);
					} else {
						dvalue = dvalue + "-";
					}
					htmlStr.append("   <td class='t1' style='padding:0px 5px 0px 5px'>" + dvalue +"</td>");
				}else if("�������".equals(ftitle)) {
					if ( dvalue.length() > 6 ) {
						dvalue = dvalue.substring(0, 4) + "-" + dvalue.substring(4,6) + "-" + dvalue.substring(6);
					} else {
						dvalue = dvalue + "-";
					}
					htmlStr.append("   <td class='t1' style='padding:0px 5px 0px 5px'>"+dvalue+"</td>");
				} else if("�ֹε�Ϲ�ȣ".equals(ftitle)) {
					if ( dvalue.length() > 6 ) {
						dvalue = dvalue.substring(0, 6) + "-" + dvalue.substring(6);
					} else {
						dvalue = dvalue + "-";
					}
					htmlStr.append("   <td class='t1' style='padding:0px 5px 0px 5px'>"+dvalue+"</td>");
				} else {
					htmlStr.append("   <td class='t1' style='padding:0px 5px 0px 5px'>"+dvalue+"</td>");
				}
				
				htmlStr.append("</tr>");
				htmlStr.append("<tr>");
				htmlStr.append("	<td colspan='2' class='bg1'></td>");
				htmlStr.append("</tr>");
			}
		}	
		
		//�߰� �׸� 
		if(pForm != null){
			for(int i=0;i<pForm.size();i++){				
				ArticleBean abean = (ArticleBean)pForm.get(i);                     //��û��� ������
				ReqSubBean subbean = (ReqSubBean)pData.getAnscontList().get(i);    //��û���� ������
				
				String ftitle = abean.getFormtitle();
				String dvalue = "";
				switch(Integer.parseInt(abean.getFormtype())){
					case 1:    //���� ������
						dvalue = setRadioValue(abean.getSampleList(), abean.getExamtype(), subbean);
						break;
					case 2:    //���� ������
						dvalue = setChkValue(abean.getSampleList(), abean.getExamtype(), subbean);
						break;
					case 3:    //�ܴ���
						if("sanc".equals(gbn) && "Y".equals(abean.getSecurity())){
							dvalue = "**********";
						} else {
							dvalue = subbean.getAnscont();
						}
						break;
					case 4:    //�幮��
						dvalue = nexti.ejms.util.Utils.convertHtmlBrNbsp(subbean.getAnscont());
						break;
				}
				
				if(dvalue == null){dvalue = "";}
				
				if ( i > 0 ) {
					htmlStr.append("<tr>");
					htmlStr.append("	<td colspan='2' class='bg1'></td>");
					htmlStr.append("</tr>");
				}
				htmlStr.append("<tr>");
				htmlStr.append("   <td align='center' class='s1' style='padding:0px 5px 0px 5px' " + WIDTH1 + ">"+ftitle);

				if ( basePath != null && abean.getOriginfilenm() != null && abean.getOriginfilenm().equals("") == false ) {
					htmlStr.append("<br>÷������ : <a id='imgLink" + i + "' class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + ", 'imageView" + i + "', '" + basePath + abean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a><br> ");
					htmlStr.append("<a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + abean.getFilenm() + "&fileName=" + abean.getOriginfilenm() + "\">" + abean.getOriginfilenm() + "</a> ");
					htmlStr.append("<div id=\"imagePreview" + i + "\" style=\"position:absolute;display:none;z-index:999\"> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><iframe id=\"imagePreview" + i + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("	<table id=\"imagePreview" + i + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
					htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
					htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + ")\"><img src=\"\" id=\"imageView" + i + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
					htmlStr.append("		</tr> ");
					htmlStr.append("	</table> ");
					htmlStr.append("</div> ");
					htmlStr.append("<script type='text/javascript'>imagePreview" + i + ".style.left = imgLink" + i + ".offsetLeft + 20</script> ");
				}
				
				htmlStr.append("   </td>");
				htmlStr.append("   <td class='t1' style='padding:0px 5px 0px 5px' " + WIDTH2 + ">"+dvalue);
				
				if ( basePath != null && "1".equals(abean.getFormtype()) ) {
					SampleBean spBean = null;
					try {
						spBean = SinchungManager.instance().getReqFormExamFile("", subbean.getReqformno(), subbean.getFormseq(), Integer.parseInt("0"+subbean.getAnscont())+1);
					} catch (Exception e) {}
					
					if ( spBean != null && spBean.getOriginfilenm() != null && spBean.getOriginfilenm().equals("") == false) {
						htmlStr.append("<br>÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + spBean.getFilenm() + "&fileName=" + spBean.getOriginfilenm() + "\">" + spBean.getOriginfilenm() + "</a> ");
						htmlStr.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + i + ", 'imageView" + i + i + "', '" + basePath + spBean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
						htmlStr.append("<div id=\"imagePreview" + i + i + "\" style=\"position:absolute;display:none;z-index:999\"> ");
						htmlStr.append("	<table id=\"imagePreview" + i + i + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
						htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
						htmlStr.append("			<td><iframe id=\"imagePreview" + i + i + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
						htmlStr.append("		</tr> ");
						htmlStr.append("	</table> ");
						htmlStr.append("	<table id=\"imagePreview" + i + i + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
						htmlStr.append("		<tr align=\"center\" valign=\"middle\"> ");
						htmlStr.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + i + ")\"><img src=\"\" id=\"imageView" + i + i + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
						htmlStr.append("		</tr> ");
						htmlStr.append("	</table> ");
						htmlStr.append("</div> ");
					}
				} else if ( basePath != null && "2".equals(abean.getFormtype()) ) {
					
					String[] anscont = null;
					if ( subbean != null && subbean.getAnscont() != null ) {
						anscont = subbean.getAnscont().split(",");
					}
					
					for ( int j = 0;  anscont != null && j < anscont.length; j++ ) {
						SampleBean spBean = null;
						try {
							spBean = SinchungManager.instance().getReqFormExamFile("", subbean.getReqformno(), subbean.getFormseq(), Integer.parseInt("0"+anscont[j])+1);
						} catch (Exception e) {}
						
						if ( spBean != null && spBean.getOriginfilenm() != null && spBean.getOriginfilenm().equals("") == false) {
							StringBuffer sb = new StringBuffer();
							sb.append("<br>÷������ : <a class=\"list_s2\" target=\"actionFrame\" href=\"/download.do?tempFileName=" + spBean.getFilenm() + "&fileName=" + spBean.getOriginfilenm() + "\">" + spBean.getOriginfilenm() + "</a> ");
							sb.append("<a class=\"list_s2\" href=\"javascript:previewImage(imagePreview" + i + j + ", 'imageView" + i + j + "', '" + basePath + spBean.getFilenm() + "', 150, 150)\"><u>�̸�����</u></a> ");
							sb.append("<div id=\"imagePreview" + i + j + "\" style=\"position:absolute;display:none;z-index:999\"> ");
							sb.append("	<table id=\"imagePreview" + i + j + "_t1\" border=\"0\" bgcolor=\"white\" style=\"position:absolute\"> ");
							sb.append("		<tr align=\"center\" valign=\"middle\"> ");
							sb.append("			<td><iframe id=\"imagePreview" + i + j + "_f\" width=\"100%\" height=\"100%\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" title='�̸�����'></iframe></td> ");
							sb.append("		</tr> ");
							sb.append("	</table> ");
							sb.append("	<table id=\"imagePreview" + i + j + "_t2\" border=\"1\" style=\"position:absolute;\"> ");
							sb.append("		<tr align=\"center\" valign=\"middle\"> ");
							sb.append("			<td><a href=\"javascript:previewImage(imagePreview" + i + j + ")\"><img src=\"\" id=\"imageView" + i + j + "\" alt=\"Ŭ���Ͻø� �����ϴ�\"></a></td> ");
							sb.append("		</tr> ");
							sb.append("	</table> ");
							sb.append("</div> ");
							
							int idx = htmlStr.length();
							for ( int k = 1; k < anscont.length - j; k++ ) {
								idx = htmlStr.lastIndexOf("<br>", idx - 1);
							}
							htmlStr.insert(idx, sb.toString());
						}
					}
				}
				
				htmlStr.append("   </td>");
				htmlStr.append("</tr>");
			}
		}
		
		result = htmlStr.toString();
		return result;
	}
	
	/**
	 * ��û���� �� ����(������ �ٿ�ε�� ���, ������ ���� ������¸� ���� ������·� ����)
	 * String type : head, body
	 * basictype:�Է±⺻����, pForm: ������� ��, pData: �׸�����
	 */
	public static String dataViewForXls(String type, String basictype, DataForm pData, List pForm) {
		StringBuffer headHtmlStr = new StringBuffer();
		StringBuffer bodyHtmlStr = new StringBuffer();
		
		try {			
			String zip = ""; String addr1 = ""; String addr2 = "";
			if(pData.getZip() != null){zip = "["+pData.getZip()+"]";}
			if(pData.getAddr1() != null){addr1 = pData.getAddr1();}
			if(pData.getAddr2() != null){addr2 = pData.getAddr2();}
			
			String url = "<a href='" + appInfo.getAp_address()+pData.getFilenm() + "'>" + pData.getOriginfilenm() + "</a>";
			if ( "".equals(Utils.nullToEmptyString(pData.getOriginfilenm())) ) url = "";
			String[] formtitle = {"��û��","�ֹε�Ϲ�ȣ","�Ҽ�","����","�ּ�","E-Mail","��ȭ��ȣ","�޴���ȭ��ȣ","Fax��ȣ","÷������"
				, "�������"
			};
//			���ֽ�û ���� ����	 �迭������ 11->10		
			//, "�������"};
			String[] datavalue = {pData.getPresentnm(),
								  pData.getPresentsn(),
								  pData.getPosition(),
								  pData.getDuty(),
								  zip+" "+addr1+" "+addr2,
								  pData.getEmail(),
								  pData.getTel(),
								  pData.getCel(),
								  pData.getFax(),
								  url
								  ,pData.getPresentbd()};
//			���ֽ�û ���� ����   �迭������ 11->10	
//								  ,pData.getPresentbd()};		
			
			if(basictype != null && !"".equals(basictype)){			
				String[] temp = basictype.split(",");
				for(int i=1;i<temp.length;i++){
					
					String ftitle = formtitle[Integer.parseInt(temp[i])-1];
					String dvalue = datavalue[Integer.parseInt(temp[i])-1];
					if(ftitle == null){ftitle = "";}
					if(dvalue == null){dvalue = "";}
					
					headHtmlStr.append("<td>"+ftitle+"</td>");
					
					if("�������".equals(ftitle)) {
						
						if ( dvalue.length() > 6 ) {
							dvalue = dvalue.substring(0, 4) + "-" + dvalue.substring(4,6)+ "-" + dvalue.substring(6);
						} else {
							dvalue = dvalue + "-";
						}
						bodyHtmlStr.append("<td>"+dvalue+"</td>");
					} else if("�ֹε�Ϲ�ȣ".equals(ftitle)){
						if ( dvalue.length() > 6 ) {
							dvalue = dvalue.substring(0, 6) + "-" + dvalue.substring(6);
						} else {
							dvalue = dvalue + "-";
						}
						bodyHtmlStr.append("<td>"+dvalue+"</td>");
					} else {
						bodyHtmlStr.append("<td>"+dvalue+"</td>");
					}
				}
			}	
			
			//�߰� �׸� 
			if(pForm != null){
				for(int i=0;i<pForm.size();i++){
					ArticleBean abean = (ArticleBean)pForm.get(i);                     //��û��� ������
					ReqSubBean subbean = (ReqSubBean)pData.getAnscontList().get(i);    //��û���� ������
					
					String ftitle = abean.getFormtitle();
					String dvalue = "";
					switch(Integer.parseInt(abean.getFormtype())){
						case 1:    //���� ������
							dvalue = setRadioValue(abean.getSampleList(), abean.getExamtype(), subbean);
							break;
						case 2:    //���� ������
							dvalue = setChkValue(abean.getSampleList(), abean.getExamtype(), subbean);
							break;
						case 3:    //�ܴ���
							dvalue = subbean.getAnscont();
							break;
						case 4:    //�幮��
							dvalue = subbean.getAnscont();
							break;
					}
					
					if(dvalue == null){dvalue = "";}
					
					headHtmlStr.append("<td>"+ftitle+"</td>");
					bodyHtmlStr.append("<td>"+dvalue+"</td>");
				}
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		if(type.equals("head") == true) {
			return headHtmlStr.toString();
		} else if(type.equals("body") == true) {
			return bodyHtmlStr.toString();
		} else {
			return headHtmlStr.toString();
		}
	} 
	
	/**
	 * ���� ��ư�� ���� ���� �����´�.	
	 */
	public static String setRadioValue(List sampleList, String examtype, ReqSubBean subbean){
		String result = "";
		
		if(sampleList != null && subbean != null){
			for(int j=0;j<sampleList.size();j++){
				SampleBean sbean = (SampleBean)sampleList.get(j);
				
				String examseq = String.valueOf(sbean.getExamseq()-1);	
				if(subbean.getAnscont() != null){
					if(subbean.getAnscont().equals(examseq)){
						result = sbean.getExamcont();														
						break;
					} 	
				}
			}	
			
			if(subbean.getAnscont() != null && Integer.toString(sampleList.size()).equals(subbean.getAnscont()) ){
				//��ŸYN�� ���� ������
				String value = "";
				if(subbean.getOther() != null){value=subbean.getOther();}
				result = " ��Ÿ : " + value;
			}
		}		
		return result;
	}
	
	/**
	 * üũ�ڽ��� ���� ���� �����´�.
	 */
	public static String setChkValue(List sampleList, String examtype, ReqSubBean subbean){
		String result = "";
		
		String[][] sample = new String[sampleList.size()][2];
		if(sampleList != null){
			for(int j=0;j<sampleList.size();j++){
				SampleBean sbean = (SampleBean)sampleList.get(j);
				
				sample[j][0] = String.valueOf(sbean.getExamseq()-1);
				if(sbean.getExamcont() != null){
					sample[j][1] = sbean.getExamcont();
				} else {
					sample[j][1] = "";
				}
			}
		}
					
		String[] temp = {};
		if(subbean != null && subbean.getAnscont() != null){
			temp = subbean.getAnscont().split(",");
		}
				
		if(temp != null){
			for(int k=0;k<temp.length;k++){
				try {
					if(temp[k] != null && Integer.toString(sampleList.size()).equals(temp[k])){
						//��ŸYN�� ���� ������
						String value = "";
						if(subbean.getOther() != null){value=subbean.getOther();}
						result = result + " ��Ÿ : " + value + "<br>";
					} else if(temp[k].equals(sample[Integer.parseInt(temp[k])][0])){
						result = result + sample[Integer.parseInt(temp[k])][1] + "<br>";							
					}
				} catch ( Exception e ) {
					result = result + "�߸��ȴ亯!!<br>";
				}
			}
		}
		
		if(result.length()>0){ result = result.substring(0,result.length()-4);}  //������ <br>�� ����					
		
		return result;
	}
	
	//������Ȳ ����
	public static String chartList(List chartList, String viewmode, String docstate) {	
		StringBuffer result = new StringBuffer();

		List lvl_mst = new ArrayList();

		int parent_level = 1;	
		int igroup_msti = 1;
		int checkcount = 0;
		
		//ArrayList �ʱ�ȭ
		for(int k=0;k<=10;k++){
			if(k==0){
				lvl_mst.add(0,"");
			} else {
				lvl_mst.add(k,"");
			}			
		}
		
		int level_len = 0;
		
		for ( int i = 0; i < chartList.size(); i++ ) {
			CommTreatBean ctbean = (CommTreatBean)chartList.get(i);
			int grpgbn = ctbean.getGrpgbn();
			String tgtdeptfullnm = ctbean.getTgtdeptfullnm();
			String appntusrnm = ctbean.getAppntusrnm();
			String submitdt = ctbean.getSubmitdt();
			String submitstate = ctbean.getSubmitstate();
			String submitstatenm = ctbean.getSubmitstatenm();
			String tgtdeptcd = ctbean.getTgtdeptcd();
			String inputstatnm = ctbean.getInputstatenm(); 
			String inputcomp = ctbean.getInputcomp();
			String returncomment = ctbean.getReturncomment();
			String modifyyn = ctbean.getModifyyn();
			String inputusrnm = ctbean.getInputusrnm();
			String inputTag = "";
			
			if ( "".equals(Utils.nullToEmptyString(inputstatnm)) ) inputstatnm = submitstatenm;
			
			int level = ctbean.getLevel();

			for(int j=level+1;j<=10;j++){
				lvl_mst.add(j,"");
			}
			
			if ( grpgbn == 1 || grpgbn == 2 ) {
				if(!tgtdeptfullnm.equals("00")){
					lvl_mst.add(level,"IGROUP_T_"+igroup_msti);

					if(level<=parent_level){
						for(int fii=1;fii<=parent_level-level+1;fii++){
							result.append("		</td>");
							result.append("	</tr>");
							result.append("</table>");
						}
					}
					
					result.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
					result.append("	<tr onMouseOver=\"this.style.background='#FFFAD1'\" onMouseOut=\"this.style.background='#ffffff'\" style='OVERFLOW-X: both;'>");
					if(i ==0 && grpgbn == 2 && level > 2){
					result.append("		<td class='list_s' width='430' valign='middle'>"+Utils.fillCh("&nbsp;&nbsp;", String.valueOf("1")));
					}else{
					result.append("		<td class='list_s' width='430' valign='middle'>"+Utils.fillCh("&nbsp;&nbsp;", String.valueOf(level-1)));
					}
					result.append("			<span onmouseover=\"style.cursor='hand'\" onclick=\"igroup_t_updown("+lvl_mst.get(level)+"_UP,img_"+lvl_mst.get(level)+")\">");
					
					
					if ( appInfo.isMessanger() && "01".equals(submitstate) ) inputTag = "(<input type='checkbox' checked name='state"+submitstate+"' value='"+tgtdeptcd+"' title='�޼�������' style='border:0px;background-color:transparent'>�޼�������)";
					if ( level > 1 ) {
						result.append("				<img id='img_"+lvl_mst.get(level)+"' src='/formation/xtree/images/Lplus.png' align='absmiddle'>");
						result.append("<font color='4F4F4F'><b>"+tgtdeptfullnm+inputTag+"</b></font></span>");
					} else {
						result.append("				<img id='img_"+lvl_mst.get(level)+"' src='/formation/xtree/images/Lminus.png' align='absmiddle'>");
						result.append("<font color='4F4F4F'><b>"+tgtdeptfullnm+inputTag+"</b></font></span>");
					}
	
					result.append("	</td>");
					result.append(" <td class='list_l' width='60'>"+appntusrnm+"</td>");
					result.append(" <td class='list_l' width='120'>"+submitdt+"</td>");
					result.append(" <td class='list_l' width='60'>");
					
					if(submitdt.equals("")){
						result.append(" <font color='blue'>"+inputstatnm+"</font>");
					}else{
						result.append(" <font color='red'>"+inputstatnm+"</font>");
					}
					result.append("	</td>");
					
					if(!viewmode.equals("nocomp")){
						result.append(" <td class='list_l'>");
						if ( "06".equals(submitstate) ) {
							result.append(" <u style='cursor:hand;' onclick='alert(\"[" + tgtdeptfullnm + "] �ݼۻ��� : \\n\\n" + returncomment.replaceAll("\r", "").replaceAll("\n", "\\\\n") + "\")'>�ݼۻ���</u>&nbsp;&nbsp;&nbsp;");
						} else {
							result.append(" <input type='checkbox' name=list[] value='"+modifyyn+"' title='��������' ");
							if(modifyyn.equals("1")) result.append(" checked ");
							result.append(" style='border:0px;background-color:transparent;' alt='"+tgtdeptcd+"' onclick=\"submit_modify(this.alt, this.checked)\" />");
						}
						result.append("</td> ");
					}else{
						result.append(" <td class='list_l'></td>");
						
					}
					
					result.append("	</tr>");
					result.append("	<tr><td colspan='5' class='list_bg2'></td></tr>");
					result.append("	<tr onMouseOver=\"this.style.background='#FFFAD1'\" onMouseOut=\"this.style.background='#ffffff'\" ");
					
					if(level > 1){
						result.append("	style=\"display:none\" up=0 ");
					}else {
						result.append("	style=\"display:block\" up=1 ");
					}
					
					result.append("			mst="+lvl_mst.get(level-1)+" name=\""+lvl_mst.get(level)+"_UP\" id=\""+lvl_mst.get(level)+"_UP\">");
					result.append("		<td class='' colspan='5'>");

					parent_level = level;
					igroup_msti = igroup_msti + 1;
					
				}
				
			} else {
				if ( appInfo.isMessanger() ) inputTag = "(<input type='checkbox' checked name='state"+submitstate+"' value='"+ctbean.getInputusersn()+"' title='�޼�������' style='border:0px;background-color:transparent'>�޼�������)";
				result.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
				result.append("	<tr>");
				result.append(" <td class='list_s' width='430'>");
				result.append(Utils.fillCh("&nbsp;&nbsp;&nbsp;",String.valueOf(level-1)) +"<img src='/formation/xtree/images/L.png'>"+inputusrnm+inputTag);
				result.append("	</td>");
				result.append(" <td class='list_l' width='60'>"+appntusrnm+"</td>");
				result.append(" <td class='list_l' width='120'>"+submitdt+"</td>");
				result.append(" <td class='list_l' width='60'>");
				if(inputcomp.equals("")){
					result.append(" <font color='blue'>"+inputstatnm+"</font>");
				}else{
					result.append(" <font color='red'>"+inputstatnm+"</font>");
				}
				result.append("	</td>");
				result.append(" <td class='list_l'></td>");
				result.append("	</tr>");
				result.append("	<tr><td colspan='5' class='list_bg2'></td></tr>");
				result.append("</table>");
				checkcount = checkcount + 1;	

			}	
			
			if(i ==0 && grpgbn == 2 && level > 2) level_len = 1;
			
		}
		
		if(level_len == 1) parent_level = parent_level -1;
		for(int fii=1;fii<=parent_level-1;fii++){
			result.append("	</td>");
			result.append("	</tr>");
			result.append("</table>");
		}		
		return result.toString();
	}
}