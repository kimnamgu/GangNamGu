<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں���
 * ����:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<jsp:include page="/include/header_admin.jsp" flush="true"/>

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_13.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
                        <!--��������/�˸��� ���̺� ����-->
                        <html:form method="POST" action="/chgMgrSave">
												<html:hidden property="gbn" value="1"/>
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="50" height="20"><font color="#4F4F4F">������ID</font></td>
                            <td width="120" height="20"><html:text property="user_id" style="width:110%;background-color:white"/></td>
                            <td width="500" height="20" align="left"> 													
                            	<a href="javascript:check_submit(document.forms[0])"><img src="/images/common/btn_record.gif" alt="���" align="absmiddle"></a>
                            	<a href="javascript:showModalPopup('/commdeptuser.do',782,503,0,0)"><img src="/images/common/btn_chart.gif" align="absmiddle" alt="���������� ����"></a>
                            </td>
                          </tr>
                        </table>
                        </html:form>
                        <!--��������/�˸��� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--�˻� ����Ʈ ���̺� ����-->
                  <html:form method="POST" action="/chgMgrSave">
										<html:hidden property="user_id1"/>
										<html:hidden property="gbn" value="0"/>
									</html:form>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr><td colspan="4" class="list_bg"></td></tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <tr> 
                      <td class="list_t" width="205">������ID</td>
                      <td class="list_t" width="205">����</td>
                      <td class="list_t" width="305">�μ�</td>
                      <td class="list_t" width="105">����</td>
                    </tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <tr><td colspan="4" class="list_bg"></td></tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <logic:notEmpty name="managerForm" property="mgrList">
											<logic:iterate id="list" name="managerForm" property="mgrList" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td class="list_no"><bean:write name="list" property="user_id"/></td>
		                      <td class="list_l"><bean:write name="list" property="user_name"/></td>
		                      <td class="list_l"><bean:write name="list" property="dept_fullname"/></td>
		                      <td class="list_l"><a href="javascript:check_del('<bean:write name="list" property="user_id"/>')"><img src="/images/common/btn_del2.gif" alt="����"></a></td>
		                    </tr>
		                    <tr> 
		                      <td colspan="4" class="list_bg2"></td>
		                    </tr>
		              		</logic:iterate>
		              	</logic:notEmpty>
		              	<logic:empty name="managerForm" property="mgrList">
		              		<tr> 
	                      <td colspan="4" class="list_l" align="center">��ϵ� �����ڰ� �����ϴ�.</td>
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
                <td>&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</table>
<script language="javascript">
menu3.onmouseover();
menu33.onmouseover();

	document.forms[0].user_id.value = "";
	
	function check_submit(frm){
		if (frm.user_id.value.trim() == '')
		{
			alert("�߰��ϰ��� �ϴ� ������� ���̵� �Է��Ͽ� �ֽʽÿ�.")
			frm.user_id.focus();
			return;
		}
		frm.submit();
	}
	
	function check_del(p_user_id){
		if (p_user_id == '')
		{
			alert("�����ϰ����ϴ� ����ڸ� �����Ͽ� �ֽʽÿ�.")
			return;
		}
		if (confirm("������ ����ڸ� �����Ͻðڽ��ϱ�?"))
		{	
			document.forms[1].user_id1.value=p_user_id;		
			document.forms[1].submit();
		} 
	}
</script>
<html:messages id="msg" message="true">
	<script>
	document.onreadystatechange = function() {
		if(document.readyState.toLowerCase() == "complete") {
			alert('<bean:write name="msg"/>');
		}
	}
	</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>