<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 부서목록 프레임
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
<html>
<head>
<title>부서 리스트</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body style="margin-top:0;margin-left:0;">
<html:form method="POST" action="/deptExtModify">
<html:hidden property="orggbn" name="usrMgrForm"/>	
<table width="308" border="0" cellspacing="0" cellpadding="0">
	<tr><td height="2" bgcolor="DDC89D" colspan="6"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm9"><div align="center">부서명</div></td>
		<td align="center" bgcolor="DEDEDE" width="1"></td>
		<td width="30" bgcolor="#FFF7EF" class="sm9"><div align="center">대상<br>부서</div></td>
		<td width="30" bgcolor="#FFF7EF" class="sm9"><div align="center">사용<br>여부</div></td>
    <td width="30" bgcolor="#FFF7EF" class="sm9"><div align="center">연계<br>여부</div></td>
    <td width="20" bgcolor="#FFF7EF" class="sm9"></td>
	</tr>
	<tr><td height="2" bgcolor="DDC89D" colspan="6"></td></tr>
</table>
<div style="OVERFLOW-Y: auto; OVERFLOW: auto; WIDTH: 306px; HEIGHT: 430px">
<table width="290" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td></td>
		<td width="1"></td>
		<td width="30"></td>
		<td width="30"></td>
    <td width="30"></td>
	</tr>
		<logic:notEmpty name="usrMgrForm" property="deptBeanList">
		<logic:iterate id="deptlist" name="usrMgrForm" property="deptBeanList">
		<tr><td height="1" bgcolor="DDC89D" colspan="5"></td></tr>
		<logic:equal name="deptlist" property="con_yn" value="Y">
			<logic:equal name="deptlist" property="use_yn" value="Y">
				<tr bgcolor="#FFFFFF" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFFFFF'">
			</logic:equal>
			<logic:notEqual name="deptlist" property="use_yn" value="Y">
				<tr bgcolor="#FFE3C8" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFE3C8'">
			</logic:notEqual>
		</logic:equal>
		<logic:notEqual name="deptlist" property="con_yn" value="Y">
			<tr bgcolor="#E6F0FA" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#E6F0FA'">
		</logic:notEqual>
			<td height="21">&nbsp;	
	      <bean:define id="dept_level" name="deptlist" property="dept_level"/>
				<logic:greaterEqual name="deptlist" property="dept_level" value="2">
					<%=nexti.ejms.util.Utils.fillCh("&nbsp;&nbsp;", dept_level.toString())%>
				</logic:greaterEqual>
				<logic:greaterEqual name="deptlist" property="dept_level" value="1">
					<img src="/dhtmlxtree/imgs/folderOpen.gif" width="18" height="18" alt="" align="absmiddle">
				</logic:greaterEqual>
				<a onclick="parent.click_deptname('<bean:write name="deptlist" property="dept_id"/>','<bean:write name="deptlist" property="dept_name"/>','<bean:write name="deptlist" property="dept_level"/>')" style="cursor:hand"><font color="blue"><bean:write name="deptlist" property="dept_name"/></font></a>				
			</td>
			<td align="center" bgcolor="DEDEDE"></td>
			<td align="center">
	      <logic:equal name="deptlist" property="main_yn" value="Y">
	        <input type="checkbox" name="main_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>" checked>
	      </logic:equal>
	      <logic:notEqual name="deptlist" property="main_yn" value="Y">
	        <input type="checkbox" name="main_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>">
	      </logic:notEqual>
	   	</td>		
	   	<td align="center">
				<logic:equal name="deptlist" property="use_yn" value="Y">
					<input type="checkbox" name="use_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>" checked>
				</logic:equal>
				<logic:notEqual name="deptlist" property="use_yn" value="Y">
					<input type="checkbox" name="use_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>">
				</logic:notEqual>
			</td>
          	<td align="center">
				<logic:equal name="deptlist" property="con_yn" value="Y">
					<input type="checkbox" name="con_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>" checked>
				</logic:equal>
				<logic:notEqual name="deptlist" property="con_yn" value="Y">
					<input type="checkbox" name="con_yn" style="border:0;background-color:transparent;" value="<bean:write name="deptlist" property="dept_id"/>">
				</logic:notEqual>
			</td>
		</tr>
		</logic:iterate>
		</logic:notEmpty>
    <tr><td colspan="5" bgcolor="DDC89D"></td></tr>
	</table>
</div>
	<table width="288" border="0" cellspacing="0" cellpadding="0">
		<tr><td height="2" bgcolor="DDC89D" colspan="4"></td></tr>
		<tr><td height="10"></td></tr>
		<tr>
			<td>&nbsp;</td>
			<td width="1"></td>
			<td width="60" align="center">
				<img src="/images/usermgr/btn_save.gif" width="40" height="20" style="cursor:hand" onclick="submit();" alt="">			
			</td>
		</tr>
	</table>
</html:form>
</body>
</html>