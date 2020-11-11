<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 부서검
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
<title>부서 리스트</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script language="javascript">
function click_submit(tar_data,data,tar_data1, data1){
	eval("opener.document.forms[0]."+tar_data+".value='"+data+"';");			
	eval("opener.document.forms[0]."+tar_data1+".value='"+data1+"';");			
	window.close();
}
</script>
</head>
<body style="margin-top:0;margin-left:0;margin-right:0;" onload="fullSizeWindow()">
<table width="290" border="0" cellspacing="0" cellpadding="0">
	<logic:notEmpty name="usrMgrForm" property="deptBeanList">
	<logic:iterate id="deptlist" name="usrMgrForm" property="deptBeanList">
	<tr>
		<td height="1" bgcolor="DDC89D" colspan="5"></td>
	</tr>
	<tr bgcolor="#FFFFFF" onmouseover="style.backgroundColor='FFFFCC'" onmouseout="style.backgroundColor='#FFFFFF'">
		<td height="21">&nbsp;	
      <bean:define id="dept_level" name="deptlist" property="dept_level"/>
			<logic:greaterEqual name="deptlist" property="dept_level" value="2">
				<%=nexti.ejms.util.Utils.fillCh("&nbsp;&nbsp;", dept_level.toString())%>
			</logic:greaterEqual>
			<logic:greaterEqual name="deptlist" property="dept_level" value="1">
				<img src="/dhtmlxtree/imgs/folderOpen.gif" width="18" height="18" alt="" align="absmiddle">
			</logic:greaterEqual>
			<a onclick="click_submit('<bean:write name="usrMgrForm" property="s_data"/>','<bean:write name="deptlist" property="dept_id"/>','<bean:write name="usrMgrForm" property="s_data1"/>','<bean:write name="deptlist" property="dept_name"/>')" style="cursor:hand"><font color="blue"><bean:write name="deptlist" property="dept_name"/></font>(<bean:write name="deptlist" property="dept_id"/>)</a>
		</td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
</table>
</body>
</html>