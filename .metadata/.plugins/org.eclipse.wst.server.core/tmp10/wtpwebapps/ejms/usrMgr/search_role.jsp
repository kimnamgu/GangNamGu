<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 직급검색
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<html>
<head>
<title>직급 리스트</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script language="javascript">
function click_submit(class_id, class_name){
	eval("opener.document.forms[0].class_id.value='"+class_id+"';");			
	eval("opener.document.forms[0].class_name.value='"+class_name+"';");			
	window.close();
}
</script>
</head>
<body style="margin-top:0;margin-left:0;margin-right:0;" onload="fullSizeWindow()">
<table width="280" cellspacing="1" cellpadding="1" border="0" bgcolor="#CCCCCC">		
	<tr><td height="25" bgcolor="F1F7FC" class="sm9" colspan="2"><div align="center">직급명</div></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14">&nbsp;직급코드</td>
		<td bgcolor="#FFF7EF" class="sm14">&nbsp;직급명</td>
	</tr>
	<logic:notEmpty name="usrMgrForm" property="roleList">
	<logic:iterate id="roleList" name="usrMgrForm" property="roleList">			
	<tr bgcolor="#ffffff" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='FFFFFF'">		
		<td><a onclick="click_submit('<bean:write name="roleList" property="class_id"/>','<bean:write name="roleList" property="class_name"/>')" style="cursor:hand"><bean:write name="roleList" property="class_id"/></a></td>
		<td><a onclick="click_submit('<bean:write name="roleList" property="class_id"/>','<bean:write name="roleList" property="class_name"/>')" style="cursor:hand"><bean:write name="roleList" property="class_name"/></a></td>
	</tr>
	</logic:iterate>				
	</logic:notEmpty>	
</table>
</body>
</html>