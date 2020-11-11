<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 하위부서목록 프레임
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
	String dept_id = request.getAttribute("dept_id").toString();
%>
<html>
<head>
<title>하위부서 리스트</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body style="margin-top:0;margin-left:0;">
<DIV style="OVERFLOW-Y: auto; OVERFLOW: auto; WIDTH: 276px; HEIGHT: 480px">
<table width="260" border="0" cellspacing="0" cellpadding="0">
	<tr bgcolor="DDC89D"><td height="2" colspan="3"></td></tr>
	<tr bgcolor="#FFF7EF"><td height="25" colspan="3" class="sm13"><div align="center">하위부서명</div></td></tr>	
	<logic:notEmpty name="usrMgrForm" property="childList">
		<logic:iterate id="clist" name="usrMgrForm" property="childList">
			<tr bgcolor="DDC89D"><td height="1" colspan="3"></td></tr>
	
			<logic:equal name="clist" property="con_yn" value="Y">
				<logic:equal name="clist" property="use_yn" value="Y">
					<tr bgcolor="#FFFFFF" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFFFFF'">
				</logic:equal>
				<logic:notEqual name="clist" property="use_yn" value="Y">
					<tr bgcolor="#FFE3C8" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFE3C8'">
				</logic:notEqual>
			</logic:equal>
			<logic:notEqual name="clist" property="con_yn" value="Y">
				<tr bgcolor="#E6F0FA" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#E6F0FA'">
			</logic:notEqual>

				<td width="80" height="21" align="center">
					<a onclick="parent.click_edit_dept('<bean:write name="clist" property="dept_id"/>','<bean:write name="clist" property="dept_name"/>')" style="cursor:hand"><font color="blue"><bean:write name="clist" property="dept_id"/></font></a></td>
				<td width="1" align="center" bgcolor="DEDEDE"></td>
				<td>&nbsp;<bean:write name="clist" property="dept_name"/></td>
			</tr>				
		</logic:iterate>	
	</logic:notEmpty>
	<tr bgcolor="DDC89D"><td height="2" colspan="3"></td></tr>	
	<tr bgcolor="#FFF7EF"><td height="25" colspan="3" class="sm13"><div align="center">하위사용자명</div></td></tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="3"></td></tr>
		<logic:notEmpty name="usrMgrForm" property="userList">
			<logic:iterate id="ulist" name="usrMgrForm" property="userList">
		
				<logic:equal name="ulist" property="con_yn" value="Y">
					<logic:equal name="ulist" property="use_yn" value="Y">
						<tr bgcolor="#FFFFFF" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFFFFF'">
					</logic:equal>
					<logic:notEqual name="ulist" property="use_yn" value="Y">
						<tr bgcolor="#FFE3C8" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFE3C8'">
					</logic:notEqual>
				</logic:equal>
				<logic:notEqual name="ulist" property="con_yn" value="Y">
					<tr bgcolor="#E6F0FA" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#E6F0FA'">
				</logic:notEqual>
				
					<td width="80" height="21" align="center">
						<a onclick="parent.click_edit_usr('<bean:write name="ulist" property="user_id"/>','<bean:write name="ulist" property="user_name"/>','<%=dept_id%>')" style="cursor:hand"><font color="blue"><bean:write name="ulist" property="user_id"/></font></a></td>
					<td width="1" align="center" bgcolor="DEDEDE"></td>
					<td>&nbsp;<bean:write name="ulist" property="user_name"/>(<bean:write name="ulist" property="class_name"/>)</td>
				</tr>
				<tr bgcolor="DDC89D"><td height="1" colspan="3"></td></tr>
			</logic:iterate>
		</logic:notEmpty>		
</table>	
</div>
</body>
</html>