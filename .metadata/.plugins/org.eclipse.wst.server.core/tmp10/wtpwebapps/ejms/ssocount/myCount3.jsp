<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ǽ�����
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
<%
	String sso_user_sn = (String)session.getAttribute("sso_user_sn");
	String sso_user_id = (String)session.getAttribute("sso_user_id");
	String url = nexti.ejms.common.appInfo.getWeb_address() + "ssocount.do?userid=" + sso_user_id + "&act=";
	session.removeAttribute("sso_user_id");
	session.removeAttribute("sso_user_sn");
%>

<html>
<head>
<style type="text/css">
 body, table, th, td, p, div, span, a {font-size: 9pt; font-family: "����", "����", "Arial";}
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table width="168" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="3"><img src="/ssocount/images/top.gif" width="168" height="6" alt=""></td>
        </tr>
        <tr>
          <td bgcolor="#D6B892" width="1"></td>
          <td width="166" valign="top">
            <table width="150" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td width="20"><img src="/ssocount/images/dot.gif"alt=""></td>
                <td width="130" style="padding:3 0 0 0;"><b><a href='<%=url%>' target="ejms_popup" style="text-decoration:none"><font color="#565656"><%=nexti.ejms.common.appInfo.getAppName()%></font></a></b></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="margin-left:16px; border-collapse: collapse">
            	<tr>
            		<td height="5" colspan="5"></td>
            	</tr>
							<% if ( sso_user_sn != null || sso_user_id != null ) { %>
								<tr height="18" align="center">
									<td height="15"><a href='<%=url%>input' target="ejms_popup" style="text-decoration:none"><font color="#565656"><img src="/ssocount/images/mycount_dot.gif" border="0" style="vertical-align: middle; margin-right: 4px">�Է´��</font></a></td>
									<td width="2"></td>
									<td width="12"><a href='<%=url%>input' target="ejms_popup" style="text-decoration:none"><font color="#64C8FF" face="����"><%=request.getAttribute("inputCount").toString()%></font></a></td>
									<td width="7"></td>
									<td><a href='<%=url%>deliv' target="ejms_popup" style="text-decoration:none"><font color="#565656"><img src="/ssocount/images/mycount_dot.gif" border="0" style="vertical-align: middle; margin-right: 4px">��δ��</font></a></td>
									<td width="2"></td>
									<td width="12"><a href='<%=url%>deliv' target="ejms_popup" style="text-decoration:none"><font color="#64C8FF" face="����"><%=request.getAttribute("deliCount").toString()%></font></a></td>
								</tr>
								<tr height="18" align="center">
									<td><a href='<%=url%>rchlist' target="ejms_popup" style="text-decoration:none"><font color="#565656"><img src="/ssocount/images/mycount_dot.gif" border="0" style="vertical-align: middle; margin-right: 4px">��������</font></a></td>
									<td></td>
									<td><a href='<%=url%>rchlist' target="ejms_popup" style="text-decoration:none"><font color="#64C8FF" face="����"><%=request.getAttribute("rchCount").toString()%></font></a></td>
									<td></td>
									<td><a href='<%=url%>reqlist' target="ejms_popup" style="text-decoration:none"><font color="#565656"><img src="/ssocount/images/mycount_dot.gif" border="0" style="vertical-align: middle; margin-right: 4px"><span style="letter-spacing:6px">��û��</span></font></a></td>
									<td></td>
									<td><a href='<%=url%>reqlist' target="ejms_popup" style="text-decoration:none"><font color="#64C8FF" face="����"><%=request.getAttribute("reqCount").toString()%></font></a></td>
								</tr>
							<% } else { %>
								<tr>
									<td colspan="5"><font color="#565656">&nbsp;&nbsp;<img src="/ssocount/images/mycount_dot.gif" border="0" style="vertical-align: middle;">�ش����</font></td>
								</tr>
							<% } %>
            </table>
          </td>
          <td bgcolor="#D6B892" width="1"></td>
        </tr>
        <tr>
          <td colspan="3"><img src="/ssocount/images/bottom.gif" width="168" height="6" alt=""></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>