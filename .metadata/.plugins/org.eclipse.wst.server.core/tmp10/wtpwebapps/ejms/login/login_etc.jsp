<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="java.sql.PreparedStatement"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="nexti.ejms.common.ConnectionManager"/>
<jsp:directive.page import="nexti.ejms.util.Utils"/>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html lang="ko">
<head>
<title><%=nexti.ejms.common.appInfo.getAppName()%></title>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/login.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/loginOK">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="268" bgcolor="#F5F4F3"> 
            <table width="821" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="481"><img src="/images/login/img.jpg" alt="취합업무의 현명한선택 비정형화된 양식을 표준화하여 각 부서에 산재해 있는 데이터를 쉽게 모아 활용을 극대화합니다."></td>
                <td width="340"> 
                  <table width="320" border="0" cellspacing="0" cellpadding="0" align="center" height="248">
                    <tr> 
                      <td width="5" height="5"><img src="/images/login/login_r_01.gif" alt=""></td>
                      <td bgcolor="#FFFFFF" height="5" width="310"></td>
                      <td width="5" height="5"><img src="/images/login/login_r_02.gif" alt=""></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td width="5"></td>
                      <td bgcolor="#FFFFFF" width="310" height="238" valign="middle"> 
                        <table width="280" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td colspan="3" align="center"><img src="/client/images/logo.gif" alt="로고"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="55" valign="bottom"><img src="/images/login/login_title.gif" alt="로그인 타이틀"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="8"></td>
                          </tr>
                          <tr> 
                            <td colspan="3"> 
                              <table width="280" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/id.gif" alt="사용자 아이디"></td>
                                  <td width="140" height="22"> 
                                    <select name="deptid" style="width:99%;" onchange="this.form.action='/loginYN.do';submit();">
                                    	<option value="">===부서선택===</option>
	                                    <%
	                                    	String deptid = Utils.nullToEmptyString((String)request.getParameter("deptid"));
	                                    
																				Connection con = null;
	                                    	PreparedStatement pstmt = null;
	                                    	ResultSet rs = null;
	                                    	StringBuffer sql = null;
	                                    	
	                                    	try {	                                    		
	                                    		sql = new StringBuffer();
	                                    		sql.append("SELECT DEPT_ID, DEPT_NAME \n");
	                                    		sql.append("FROM DEPT \n");
	                                    		sql.append("WHERE DEPT_ID IN (SELECT DEPT_ID FROM USR) \n");
	                                    		con = ConnectionManager.getConnection();
	                                    		pstmt = con.prepareStatement(sql.toString());
	                                    		rs = pstmt.executeQuery();
	                                    		
	                                    		sql = new StringBuffer();
	                                    		while ( rs.next() ) {
	                                    			sql.append("<option value='" + rs.getString(1) + "'");
	                                    			if ( deptid.equals(rs.getString(1)) ) sql.append(" selected");
																						sql.append(">" + rs.getString(2) + "</option>");
	                                    		}
	                                    		out.print(sql);
	                                    	} finally {
	                                    		ConnectionManager.close(con, pstmt, rs);
	                                    	}
	                                    %>
                                    </select>
                                    <select name="userId" style="width:99%;" onchange="password.value=this[this.selectedIndex].pw.trim();">
                                    	<option value="%">===사용자선택===</option>
                                    	<%	                                    	
	                                    	try {	                                    		
	                                    		sql = new StringBuffer();
	                                    		sql.append("SELECT USER_ID, USER_NAME || DECODE(MGRYN, 'Y', '(관리자)'), PASSWD \n");
	                                    		sql.append("FROM USR U \n");
	                                    		sql.append("WHERE DEPT_ID = ? \n");
	                                    		con = ConnectionManager.getConnection();
	                                    		pstmt = con.prepareStatement(sql.toString());
	                                    		pstmt.setString(1, deptid);
	                                    		rs = pstmt.executeQuery();
	                                    		
	                                    		sql = new StringBuffer();
	                                    		while ( rs.next() ) {
	                                    			sql.append("<option value='" + rs.getString(1) + "' pw='" + rs.getString(3) + "'>" + rs.getString(2) + "</option>");
	                                    		}
	                                    		out.print(sql);
	                                    	} finally {
	                                    		ConnectionManager.close(con, pstmt, rs);
	                                    	}
	                                    %>
                                    </select>
                                  </td>
                                  <td rowspan="2" width="55" align="center">
                                  	<a href="javascript:document.forms[0].submit();"><img src="/images/login/login_btn.gif" alt="로그인" align="absmiddle"></a>
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/pw.gif" alt="비밀번호"></td>
                                  <td width="140" height="22">
                                    <html:password property="password" style="width:99%;" onkeydown="javascript:if (event.keyCode == 13) {document.forms[0].submit();}" redisplay="false"/>
                                    <html:multibox property="setfl" style="border:0;background-color:transparent;display:none;" value="0"/>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="5"></td>
                    </tr>
                    <tr> 
                      <td width="5" height="5"><img src="/images/login/login_r_03.gif" alt=""></td>
                      <td bgcolor="#FFFFFF" height="5" width="310"></td>
                      <td width="5" height="5"><img src="/images/login/login_r_04.gif" alt=""></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>
<html:messages id="msg" message="true">
  <script language="javascript"><bean:write name="msg"/></script>
</html:messages>
</body>
</html>