<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� ��������� ������
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
<html>
<head>
<% if (request.getParameter("userinfo") == null){ //������������� �˾� �ڵ�%>
<title>��(�����)</title>
<% } else { %>
<title>��������</title><base target="_self"><br><center>
<% } %>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
</head>
<body style="margin:0 0 0 0;">
<div style="OVERFLOW-Y: auto; OVERFLOW-X: visable; WIDTH: 270px; HEIGHT: 500px">
<html:form method="POST" action="/deptManageDB">
<% if (request.getAttribute("isNew").equals("0")){ %>
<html:hidden property="mode" value="usr_i"/>	
<% } else { %>
<html:hidden property="mode" value="usr_u"/>	
<% } %>
<table width="258" border="0" cellspacing="0" cellpadding="0">
	<tr bgcolor="DDC89D"><td height="2" colspan="4"></td></tr>
	<tr>
		<td width="115" height="25" bgcolor=#FFF7EF class="sm14" align="center">&nbsp;����ھ��̵�<font color="red">*</font></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2" width="170">
			<% if (request.getAttribute("isNew").equals("0")){ %>
			&nbsp;<html:text property="user_id" styleClass="input2" style="width:150px;ime-mode:disabled;" onkeydown="javascript:enterKey_focus('forms[0].user_name');" maxlength="40"/>
			<% } else { %>
			&nbsp;<bean:write name="usrMgrForm" property="user_id"/>
			<html:hidden property="user_id" styleClass="input2"/>
			<% } %>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;����ڸ�<font color="red">*</font></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="user_name" styleClass="input2" style="width:90px;ime-mode:active;" onkeydown="javascript:enterKey_focus('forms[0].user_sn');" maxlength="80"/>
		</td>
	</tr>
	<tr style="display:none" bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr style="display:none">
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;����Ű</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="user_sn" styleClass="input2" style="width:150px;" onkeydown="javascript:key_num();enterKey_focus('forms[0].dept_id');" maxlength="13"/></td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;�Ҽ������ڵ�<font color="red">*</font></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="dept_id" styleClass="input2" style="width:90px;color:gray" readonly="true" onclick="window.open('/searchDept.do?s_data=dept_id&s_data1=dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>
			<img name="dept_search" src="/images/usermgr/btn_jo2.gif" width="36" height="20" align="absmiddle" style="cursor:hand" onclick="window.open('/searchDept.do?s_data=dept_id&s_data1=dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')" alt="">
			<br>&nbsp;<html:text property="dept_name" styleClass="input2" style="width:90px;color:gray" readonly="true" onclick="window.open('/searchDept.do?s_data=dept_id&s_data1=dept_name','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;������</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="position_name" styleClass="input2" style="width:150px" onkeydown="javascript:enterKey_focus('forms[0].grade_id');" maxlength="50"/></td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;���޸�</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="class_id" styleClass="input2" style="width:35px;color:gray" readonly="true" onclick="window.open('/searchRole.do','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>
			<html:text property="class_name" styleClass="input2" style="width:75px;color:gray" readonly="true" onclick="window.open('/searchRole.do','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')"/>
		  <img name="class_search" src="/images/usermgr/btn_jo2.gif" width="36" height="20" align="absmiddle" style="cursor:hand" onclick="window.open('/searchRole.do','pop','width=300, height=300, location=no, menubar=no, scrollbars=yes, resizable=yes')" alt=""></td>
	</tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %>bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %>>
		<td height="25" bgcolor="#FFF7EF" class="sm14"><div align="center">����</div></td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:text property="usr_rank" styleClass="input2" onkeydown="key_num();" maxlength="5"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;�̸����ּ�</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="email" styleClass="input2" style="width:150px;ime-mode:disabled;" onkeydown="javascript:enterKey_focus('forms[0].tel');" maxlength="50"/></td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;�Ϲ���ȭ��ȣ</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="tel" styleClass="input2" style="width:90px;" onkeydown="javascript:key_num();enterKey_focus('forms[0].mobile');" maxlength="40"/></td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;�޴���ȭ��ȣ</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="mobile" styleClass="input2" style="width:90px;" onkeydown="javascript:key_num();enterKey_focus('forms[0].passwd');" maxlength="40"/></td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;��ȣ<font color="red">*</font>
		</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
		&nbsp;<html:password property="passwd" styleClass="input2" style="width:80px;ime-mode:disabled;" onkeydown="javascript:enterKey_focus('forms[0].ea_id');" maxlength="40"/>
		<% if (request.getAttribute("isNew").equals("1")){ %>
			<% if (request.getParameter("userinfo") == null){ //������������� �˾��� �ڵ�%>
		<img src="/images/usermgr/btn_default.gif" width="74" height="20" border="0" onclick="return parent.UsrDefaultPasswdSumbit();" style="cursor:hand" align="absmiddle" alt="">
			<% } %>
		<% } %>
		</td>
	</tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %> bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %>>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;���ڰ�����̵�</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">&nbsp;<html:text property="ea_id" styleClass="input2" style="width:150px" onkeydown="javascript:enterKey_focus('forms[0].gpki_id');"/></td>
	</tr>
	<tr <% if (nexti.ejms.common.appInfo.isGpkilogin() == false){ %>style="display:none"<% } %> bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr <% if (nexti.ejms.common.appInfo.isGpkilogin() == false){ %>style="display:none"<% } %>>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;GPKI���������</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			&nbsp;<html:password property="gpki_id" styleClass="input2" style="width:70px;color:gray" readonly="true"/>
			<img src="/images/usermgr/btn_gpkiadd.gif" align="absmiddle" style="cursor:hand" onclick="bgProcFrame.location.href='/gpkiRegistration.do'" alt="">
			<img src="/images/usermgr/btn_gpkidel.gif" align="absmiddle" style="cursor:hand" onclick="if(confirm('��ϵ� �������� �����Ͻðڽ��ϱ�?')){document.forms[0].gpki_id.value='';}" alt="">
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %>>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;��뿩��</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<html:checkbox name="usrMgrForm" property="use_yn_one" style="border:0;background-color:transparent;" value="Y"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="1" colspan="4"></td></tr>
	<tr <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>style="display:none"<% } %>>
		<td height="25" bgcolor="#FFF7EF" class="sm14" align="center">&nbsp;���迩��</td>
		<td width="1" align="center" bgcolor="DDC89D"></td>
		<td colspan="2">
			<html:checkbox name="usrMgrForm" property="con_yn_one" style="border:0;background-color:transparent;" value="Y"/>
		</td>
	</tr>
	<tr bgcolor="DDC89D"><td height="2" colspan="4"></td></tr>
	<tr><td height="10" colspan="4"></td></tr>
	<tr>
		<td colspan="4">
	    - �űԵ�Ͻ� ��ȣ�� �ݵ�� �Է��Ͽ��� �մϴ�<br>
	    - �����ID�� �����Ͻ� �� �����ϴ�<br>
	    <% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>
	    - ����ڸ�, �����ڵ�, ������, ���޸�, ������<br>&nbsp;&nbsp;&nbsp;�����Ͻ� �� �����ϴ�.<br>
	    - <font color="red"><b>����� ������ ��α��ν� ����˴ϴ�.</b></font>
	    <% } %>
		</td>
	</tr>
	<tr><td height="10"></td></tr>
</table>
<table width="258" border="0" cellspacing="0" cellpadding="0">
	<tr bgcolor="DDC89D"><td height="2"></td></tr>
	<tr><td height="7"></td></tr>
	<tr>
		<td align="center">
			<% if (request.getParameter("userinfo") == null){ //������������� �˾��� �ڵ�%>
			<img src="/images/usermgr/btn_save_03.gif" width="36" height="20" onclick="parent.UsrSumbit();" style="cursor:hand" alt="����">&nbsp;&nbsp;
			<img src="/images/usermgr/btn_cancel.gif" width="36" height="20" onclick="window.open('about:blank','_self'); parent.ifrm2_refresh();" style="cursor:hand;" alt="���">
			<% } else { %>
			<script>
			function UsrSumbit(){
				if(document.forms[0].user_id.value.trim() == "") {
					alert("����� ���̵� �����ϴ�.");
					document.forms[0].user_id.focus();
					return;
				}
				if(getCharLen(document.forms[0].user_id.value.trim())>40) {
					alert("����� ���̵� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 40��, �ѱ� 20�ڱ��� �Է°����մϴ�.");
					document.forms[0].user_id.focus();
					return;
				}
				if(document.forms[0].user_name.value.trim() == "") {
					alert("����ڸ��� �����ϴ�.");
					document.forms[0].user_name.focus();
					return;
				}
				if(getCharLen(document.forms[0].user_name.value.trim())>80) {
					alert("����ڸ� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 80��, �ѱ� 40�ڱ��� �Է°����մϴ�.");
					document.forms[0].user_name.focus();
					return;
				}
				if(document.forms[0].dept_id.value.trim() == "") {
					alert("�μ��� �������� �ʾҽ��ϴ�.");
					document.forms[0].dept_id.focus();
					return;
				}
				
				//�űԻ���ڵ���� ��� �н����� �ݵ�� �Է�, ������ ��� ����θ� ���� �н����� ����
				if(document.forms[0].passwd.value.trim() == "") {
					alert("��ȣ�� �Է��� �ֽñ� �ٶ��ϴ�.");
					document.forms[0].passwd.focus();
					return;
				}
				document.forms[0].action += "?userinfo=true";
				document.forms[0].submit();
			}
			</script>
			<img src="/images/usermgr/btn_save_03.gif" width="36" height="20" onclick="UsrSumbit();" style="cursor:hand" alt="����">&nbsp;&nbsp;
			<img src="/images/usermgr/btn_cancel.gif" width="36" height="20" onclick="window.close()" style="cursor:hand;" alt="���">
			<% } %>
		</td>
	</tr>
</table>
</html:form>
<iframe id="bgProcFrame" name="bgProcFrame" width="0" height="0" title=""></iframe>
</div>
<% if (request.getParameter("userinfo") != null){ //������������� �˾��� �ڵ�%>
<script>
document.forms[0].user_name.contentEditable = "false";
document.forms[0].position_name.contentEditable = "false";
document.forms[0].usr_rank.contentEditable  ="false";
document.forms[0].dept_id.onclick = null;
document.forms[0].dept_name.onclick = null;
document.dept_search.style.display = "none";
document.forms[0].class_id.onclick = null;
document.forms[0].class_name.onclick = null;
document.class_search.style.display = "none";
</script>
<% } %>
</body>
<html:messages id="msg" message="true">
	<script language="javascript">alert('<bean:write name="msg"/>');</script>
</html:messages>
</html>