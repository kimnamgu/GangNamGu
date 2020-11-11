<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 부서정보 프레임
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
<title>상세(부서)</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body style="margin-top:0;">
<div style="OVERFLOW-Y: auto; OVERFLOW: auto; WIDTH: 276px; HEIGHT: 500px">
<html:form method="POST" action="/deptManageDB">
<% if (request.getAttribute("isNew").equals("0")){ %>
<html:hidden property="mode" value="dept_i"/>	
<% } else { %>
<html:hidden property="mode" value="dept_u"/>	
<% } %>
<table width="258" border="0" cellspacing="0" cellpadding="0">
	<tr bgcolor="DDC89D"><td height="2" colspan="4"></td></tr>
	<tr>
		<td width="100" height="25" align="center" bgcolor="#FFF7EF" class="sm14">상위부서</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="upper_dept_id" styleClass="input2" style="width:100px" readonly="true" onclick="window.open('/searchDept.do?s_data=upper_dept_id&s_data1=upper_dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>				
			<img src="/images/usermgr/btn_jo2.gif" width="36" height="20" align="absmiddle" style="cursor:hand" onclick="window.open('/searchDept.do?s_data=upper_dept_id&s_data1=upper_dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')" alt="">
			&nbsp;<html:text property="upper_dept_name" styleClass="input2" style="width:100px" readonly="true" onclick="window.open('/searchDept.do?s_data=upper_dept_id&s_data1=upper_dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>	
			
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">부서코드<font color="#FF0000">*</font></div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<% if (request.getAttribute("isNew").equals("0")){ %>
			&nbsp;<html:text property="dept_id" styleClass="input2" style="width:150px;ime-mode:active;" onkeydown="javascript:enterKey_focus('forms[0].dept_name');" maxlength="40"/>
			<% } else { %>
			&nbsp;<bean:write name="usrMgrForm" property="dept_id"/>
			<html:hidden property="dept_id" styleClass="input2"/>
			<% } %>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">부서명<font color="#FF0000">*</font></div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="dept_name" styleClass="input2" style="width:150px;ime-mode:active;" onkeydown="javascript:enterKey_focus('forms[0].dept_rank');" />
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">조직구분</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<html:select name="usrMgrForm" property="orggbn" style="width:150px">
				<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
					<bean:define id="user_gbn" name="user_gbn" scope="session"/>
					<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
					<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
				</jsp:useBean>
				<html:optionsCollection name="orggbn" property="beanCollection"/>
			</html:select>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<% } else { %>
		<% if ( nexti.ejms.common.appInfo.isSidoldap() ){ %>
		<html:hidden property="orggbn" value="001"/>
		<% } else { %>
		<html:hidden property="orggbn"/>
		<% } %>
	<% } %>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">조직서열</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="dept_rank" styleClass="input2" onkeydown="key_num();" maxlength="5"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">대표전화</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="dept_tel" styleClass="input2" maxlength="50"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">사용여부</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<html:checkbox name="usrMgrForm" property="use_yn_one" style="border:0;background-color:transparent;" value="Y"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">연계여부</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<html:checkbox name="usrMgrForm" property="con_yn_one" style="border:0;background-color:transparent;" value="Y"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
</table>
<br>
<table width="270" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
			<img src="/images/usermgr/btn_save_03.gif" width="36" height="20" style="cursor:hand" onclick="parent.DeptSumbit();" alt="">&nbsp;&nbsp;
			<img src="/images/usermgr/btn_cancel.gif" width="36" height="20" onclick="window.open('about:blank','_self'); parent.ifrm2_refresh();" style="cursor:hand;" alt="">
		</td>
	</tr>
</table>
</html:form>
<p><br>
- 상위부서는 선택하실 수 없습니다.<br>
- 부서순번은 같은 레벨에 순서를 정합니다.<br>
<font color="#FFFFFF">-</font> 입력하지 않을때 최고값을 가집니다.<br>
- <font color="#FF0000">'*'는 반드시 입력하셔야 합니다.</font>
</p>
</div>
</body>
<html:messages id="msg" message="true">
  <script language="javascript">alert('<bean:write name="msg"/>');</script>
</html:messages>
</html>