<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������μ���������
 * ����:
--%>
<%@page import="nexti.ejms.user.model.UserBean"%>
<%@page import="nexti.ejms.dept.model.DeptManager"%>
<%@page import="nexti.ejms.user.model.UserManager"%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>
<script>
menu5.onmouseover();
menu55.onmouseover();
</script>

 <% 
              //Ư������� ����, ���� � ���� �������� ���� ������ ���� �ӽ� ���� 2016.06.10
             
               //��� ���� ��ȣ
               String  com_rchno="4504";
         
               //���ǿ��� �α����� ���̵��� ������
               String s_id = session.getAttribute("user_id").toString();
             
              	UserManager mgrtempuser = UserManager.instance();
            	DeptManager mgrtempdept = DeptManager.instance();
            	nexti.ejms.user.model.UserBean loginuser = mgrtempuser.getUserInfo(s_id);
            	
            	// �ش� id���� classid, gradid, deptid, positionid ���� ������
            	String str_Class_id = loginuser.getClass_id();
            	String str_Class_name = loginuser.getClass_name();
            	String str_Dept_id = loginuser.getDept_id();
            	String str_Dept_name = loginuser.getDept_name();
            	String str_Grade_id = loginuser.getGrade_id();
            	String str_Grade_name = loginuser.getGrade_name();
            	String str_Position_id = loginuser.getPosition_id();
            	String str_Position_name = loginuser.getPosition_name();
            	
            	/*
                System.out.println("str_Class_id:"+str_Class_id);
            	System.out.println("str_Dept_id:"+str_Dept_id);
            	System.out.println("str_Grade_id:"+str_Grade_id);
            	System.out.println("str_Position_id:"+str_Position_id);
            	*/
            	
               %>
              

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/researchParticiList" method="post">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_36.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--����Ʈ �˻� ���̺� ����-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr>
														<% if ( "����3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
														<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="55"><font color="#4F4F4F">��������</font></td>
                            <td width="85"> 
                              <html:select property="groupyn" style="width:75px;background-color:white" onchange="submit();">
                              	<html:option value="">��ü</html:option>
				                        <html:option value="N">�Ϲݼ���</html:option>
				                        <html:option value="Y">�׷켳��</html:option>
			                        </html:select>
                            </td>
                            <% } %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">����</font></td>
                            <td width="210"><html:text property="sch_title" style="width:200;background-color:white"/></td>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="submit()" alt=""></td>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        <!--����Ʈ �˻� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td class="result">* �� <font class="result_no"><%=request.getAttribute("totalCount")%>��</font>�� �˻��Ǿ����ϴ�</td>
              </tr>
              <tr> 
              
             
                <td> 
                  <!--�˻� ����Ʈ ���̺� ����-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
										</tr> 
										<tr>
								      <td width="40" class="list_t" align="center">����</td>
								      <td class="list_t" align="center">����������</td>
								      <td width="200" class="list_t" align="center">����μ�</td>
								      <td width="100" class="list_t" align="center">����Ⱓ</td>
								    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
								<logic:notEmpty name="researchForm" property="listrch">
								  <logic:iterate id="list" name="researchForm" property="listrch" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
								      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s" style="padding-left:5px">

										 <%String ss = session.getAttribute("dept_code").toString();%>
										
								      	<logic:equal name="list" property="groupyn" value="N">
								      		<%	
								      		//if (ss.equals( "3220108")|ss.equals( "3220164")|ss.equals( "3220165")|ss.equals( "3220166")|ss.equals( "3220168")|ss.equals( "3220173")|ss.equals( "3220177"))
								      		if  (!str_Class_id.equals("00911") && !str_Class_id.equals("00909")  &&  !str_Position_id.equals("0036100") && !str_Position_id.equals("0013600"))
								      		 { 
								      			
								      		%>
								      		
								      			<logic:equal name="list" property="rchno" value="<%=com_rchno %>">
											       [�������� ��� �ƴ�]													   		
												</logic:equal>
													 
												<logic:notEqual name="list" property="rchno" value="<%=com_rchno %>">
										      		
										      		<a class="list_s2" href="#" onclick="showPopup2('/research.do?rchno=<bean:write name="list" property="rchno"/>', 700, 680, 0, 0)"><bean:write name="list" property="title"/></a>
												      	
										      	</logic:notEqual>
										      	<%}else {%>
										      	
										      		<a class="list_s2" href="#" onclick="showPopup2('/research.do?rchno=<bean:write name="list" property="rchno"/>', 700, 680, 0, 0)"><bean:write name="list" property="title"/></a>									      	
										      	
										      	<%} %>
									      					      		
											</logic:equal>
											
											<logic:equal name="list" property="groupyn" value="Y">
											
								      	<%	
								      		//if (ss.equals( "3220108")|ss.equals( "3220164")|ss.equals( "3220165")|ss.equals( "3220166")|ss.equals( "3220168")|ss.equals( "3220173")|ss.equals( "3220177")) {
								      		if  (!str_Class_id.equals("00911") && !str_Class_id.equals("00909")  &&  !str_Position_id.equals("0036100") && !str_Position_id.equals("0013600"))
								      		 { 
								      		%>
								      		
											<logic:equal name="list" property="rchno" value="4504">
											   [�������� ��� �ƴ�]													   		
											</logic:equal>
														 
											<logic:notEqual name="list" property="rchno" value="4504">
														
												<a class="list_s2" href="#" onclick="showPopup2('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="rchgrpno"/>&mode=research', 700, 680, 0, 0)"><bean:write name="list" property="title"/></a>
															
											</logic:notEqual>
									   	<%}else {%>
										      	
										      	<a class="list_s2" href="#" onclick="showPopup2('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="rchgrpno"/>&mode=research', 700, 680, 0, 0)"><bean:write name="list" property="title"/></a>									      	
										      	
									  	<%} %>
											</logic:equal>
								      </td>
								      <td align="center" class="list_s"><bean:write name="list" property="coldeptnm"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="researchForm" property="listrch">
								 <%String ss1 = session.getAttribute("dept_code").toString();%>
										<tr>
											<td colspan="4" align="center" class="list_s">�˻��� ����� �����ϴ�</td>
										</tr>
										<tr>
								      <td colspan="4" class="list_bg2"></td>
								    </tr>
								</logic:empty>
                  </table>
                  <!--�˻� ����Ʈ ���̺� ��-->
                </td>
              </tr>
              <tr> 
                <td height="30">&nbsp;</td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="groupyn" name="researchForm" property="groupyn"/>
                	<bean:define id="sch_title" name="researchForm" property="sch_title"/>
                	<%=nexti.ejms.util.commfunction.procPage_AddParam("/researchParticiList.do","groupyn="+(String)groupyn+"&sch_title="+(String)sch_title,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
                </td>
            	</tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>