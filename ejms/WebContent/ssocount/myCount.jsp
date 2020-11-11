<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 건수연계
 * 설명:
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
 body, table, th, td, p, div, span, a { font-size: 9pt; font-family: "굴림", "돋움", "Arial";}
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
          <td width="166" height="95" id="table1" valign="top">
            <table width="144" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td width="20"><img src="/ssocount/images/dot.gif" width="13" height="13" alt=""></td>
                <td width="124" style="padding:3 0 0 0;"><b><a href='<%=url%>' target="ejms_popup" style="text-decoration:none"><font color="#565656"><%=nexti.ejms.common.appInfo.getAppName()%></font></a></b></td>
              </tr>
            </table>
            <table width="130" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td height="5"></td>
              </tr>
              <tr>
                <td bgcolor="#F4F4F4" height="65" id="table2">
                  <table width="105" border="0" cellspacing="0" cellpadding="0" align="center">
                  <% if ( sso_user_sn != null || sso_user_id != null ) { %>
                    <tr>
                      <td bgcolor="#F4F4F4" width="85" style="padding:3 0 0 0;" height="20"><a href='<%=url%>deliv' target="ejms_popup" style="text-decoration:none"><font color="#565656">-&nbsp;배부대기</font></a></td>
                      <td bgcolor="#F4F4F4" width="20" align="right" style="padding:3 0 0 0;"><a href='<%=url%>deliv' target="ejms_popup" style="text-decoration:none"><font color="#FE0000"><%=request.getAttribute("deliCount").toString()%></font></a></td>
                    </tr>
                    <tr>
                      <td bgcolor="#F4F4F4" width="85" style="padding:3 0 0 0;" height="20"><a href='<%=url%>input' target="ejms_popup" style="text-decoration:none"><font color="#565656">-&nbsp;입력대기</font></a></td>
                      <td bgcolor="#F4F4F4" width="20" align="right" style="padding:3 0 0 0;"><a href='<%=url%>input' target="ejms_popup" style="text-decoration:none"><font color="#FE0000"><%=request.getAttribute("inputCount").toString()%></font></a></td>
                    </tr>
                    <tr>
                      <td bgcolor="#F4F4F4" width="85" style="padding:3 0 0 0;" height="20"><a href='<%=url%>reqlist' target="ejms_popup" style="text-decoration:none"><font color="#565656">-&nbsp;신청대기</font></a></td>
                      <td bgcolor="#F4F4F4" width="20" align="right" style="padding:3 0 0 0;"><a href='<%=url%>reqlist' target="ejms_popup" style="text-decoration:none"><font color="#FE0000"><%=request.getAttribute("reqCount").toString()%></font></a></td>
                    </tr>
                  <% } else { %>
                  	<tr>
                      <td bgcolor="#F4F4F4" width="85" style="padding:3 0 0 0;" height="20"><font color="#565656">- 해당없음</font><script>document.getElementById("table1").height-=40;document.getElementById("table2").height-= 40;</script></td>
                    </tr>
                  <% } %>
                  </table>
                </td>
              </tr>
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