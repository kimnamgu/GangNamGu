<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 등록
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
<title>Agent Runtime 등록</title>
<script src="/script/common.js"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var doClose = false;
	function listReload() {
		if ( doClose == true ) {
			opener.location.href = "/systemAgentRuntime.do?p_id=" + document.forms[0].p_id.value;
			window.close();
		}
	}
	
	function writeSumbit(frm)
	{
		var radioVal=false;
		for(var i=0;i<frm.p_type.length; i++)
		{ 
			 if (frm.p_type[i].checked == true) 
			 { 
				 radioVal = true; 
				 break; 
			 } 
		} 
		if (!radioVal)
		{
			alert("스케줄타입을 선택하여 주십시오.")
			return;
		}
	
		doClose = true;
		frm.target = "actionFrame";
		frm.submit();
	}
	
	function showLayer(val)
	{ 
		if (val=="001")	//일별
		{
			document.getElementById("layer_t1").style.display = ""
			document.getElementById("layer_t2").style.display = "none"
			document.getElementById("layer_t3").style.display = "none"
			document.getElementById("layer_t4").style.display = "none"
			document.getElementById("layer_t2").style.disabled = true
			document.getElementById("layer_t3").style.disabled = true
			document.getElementById("layer_t4").style.disabled = true
		}
		else if (val=="002")		//주간별
		{
			document.getElementById("layer_t1").style.display = "none"
			document.getElementById("layer_t2").style.display = ""
			document.getElementById("layer_t3").style.display = "none"
			document.getElementById("layer_t4").style.display = "none"
			document.getElementById("layer_t1").style.disabled = true
			document.getElementById("layer_t3").style.disabled = true
			document.getElementById("layer_t4").style.disabled = true
		}
		else if (val=="003")	//월별
		{
			document.getElementById("layer_t1").style.display = "none"
			document.getElementById("layer_t2").style.display = "none"
			document.getElementById("layer_t3").style.display = ""
			document.getElementById("layer_t4").style.display = "none"
			document.getElementById("layer_t1").style.disabled = true
			document.getElementById("layer_t2").style.disabled = true
			document.getElementById("layer_t4").style.disabled = true
		}
		else if (val=="004")	//연도별
		{
			document.getElementById("layer_t1").style.display = "none"
			document.getElementById("layer_t2").style.display = "none"
			document.getElementById("layer_t3").style.display = "none"
			document.getElementById("layer_t4").style.display = ""
			document.getElementById("layer_t1").style.disabled = true
			document.getElementById("layer_t2").style.disabled = true
			document.getElementById("layer_t3").style.disabled = true
		}
	}		
</script>
</head>
<bean:define id="type" name="systemAgentForm" property="p_type"/>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="showLayer('<%=type%>');fullSizeWindow();">
<html:form method="POST" action="/systemAgentRuntimeWrite.do">
<html:hidden name="systemAgentForm" property="mode"/>
<html:hidden name="systemAgentForm" property="p_id"/>
<html:hidden name="systemAgentForm" property="p_seq"/>
<table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr bgcolor="DDC89D">
		<td colspan="3" height="1"></td>
	</tr>
	<tr>
		<td width="120" height="23" bgcolor="F8F4EC" ><div align="center"><strong>프로그램명</strong></div></td>
		<td width="1" bgcolor="DDC89D"></td>
		<td>&nbsp;<bean:write name="systemAgentForm" property="p_nm"/></td>
	</tr>
	<tr bgcolor="DDC89D">
		<td height="1" colspan="3"></td>
	</tr>
	<tr>
		<td height="23" align="center" bgcolor="F8F4EC"><strong>스케줄타입</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;
			<html:radio name="systemAgentForm" property="p_type" value="001" onclick="showLayer(this.value);" style="border:0;background-color:transparent;">매일</html:radio>&nbsp;&nbsp;&nbsp;
			<html:radio name="systemAgentForm" property="p_type" value="002" onclick="showLayer(this.value);" style="border:0;background-color:transparent;">매주</html:radio>&nbsp;&nbsp;&nbsp;
			<html:radio name="systemAgentForm" property="p_type" value="003" onclick="showLayer(this.value);" style="border:0;background-color:transparent;">매월</html:radio>&nbsp;&nbsp;&nbsp;
			<html:radio name="systemAgentForm" property="p_type" value="004" onclick="showLayer(this.value);" style="border:0;background-color:transparent;">매년</html:radio>&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr bgcolor="DDC89D">
		<td height="1" colspan="3"></td>
	</tr>
	<tr id="layer_t1" style="display:none">
		<td height="23" bgcolor="F8F4EC" align="center"><strong>매일</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;							
			<html:select name="systemAgentForm" property="p_t1_1" style="width:50px">
				<jsp:useBean id="p_t1_1" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t1_1" property="ccd_cd" value="008"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t1_1" property="beanCollection"/>
			</html:select>&nbsp;					  	
			<html:select name="systemAgentForm" property="p_t1_2" style="width:50px">
				<jsp:useBean id="p_t1_2" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t1_2" property="ccd_cd" value="009"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t1_2" property="beanCollection"/>
			</html:select>
		</td>
	</tr>
	<tr id="layer_t2" style="display:none">
		<td height="23" bgcolor="F8F4EC" align="center"><strong>일시(주)</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;
			<html:select name="systemAgentForm" property="p_t2_1" style="width:70px">
				<jsp:useBean id="p_t2_1" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t2_1" property="ccd_cd" value="011"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t2_1" property="beanCollection"/>
			</html:select>&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t2_2" style="width:50px">
				<jsp:useBean id="p_t2_2" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t2_2" property="ccd_cd" value="008"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t2_2" property="beanCollection"/>
			</html:select>&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t2_3" style="width:50px">
				<jsp:useBean id="p_t2_3" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t2_3" property="ccd_cd" value="009"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t2_3" property="beanCollection"/>
			</html:select>	
		</td>
	</tr>
	<tr id="layer_t3" style="display:none">
		<td height="23" bgcolor="F8F4EC" align="center"><strong>일시(월)</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;
			<html:select name="systemAgentForm" property="p_t3_1" style="width:50px">
				<jsp:useBean id="p_t3_1" class="nexti.ejms.list.form.DayListForm"/>
				<html:optionsCollection name="p_t3_1" property="beanCollection"/>
			</html:select>일&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t3_2" style="width:50px">
				<jsp:useBean id="p_t3_2" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t3_2" property="ccd_cd" value="008"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t3_2" property="beanCollection"/>
			</html:select>&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t3_3" style="width:50px">
				<jsp:useBean id="p_t3_3" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t3_3" property="ccd_cd" value="009"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t3_3" property="beanCollection"/>
			</html:select>
		</td>
	</tr>
	<tr id="layer_t4" style="display:none">
		<td height="23" bgcolor="F8F4EC" align="center"><strong>일시(년)</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;
			<html:select name="systemAgentForm" property="p_t4_1" style="width:50px">
				<jsp:useBean id="p_t4_1" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t4_1" property="ccd_cd" value="010"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t4_1" property="beanCollection"/>
			</html:select>월&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t4_2" style="width:50px">
				<jsp:useBean id="p_t4_2" class="nexti.ejms.list.form.DayListForm"/>
				<html:optionsCollection name="p_t4_2" property="beanCollection"/>
			</html:select>일&nbsp;												  	
			<html:select name="systemAgentForm" property="p_t4_3" style="width:50px">
				<jsp:useBean id="p_t4_3" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t4_3" property="ccd_cd" value="008"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t4_3" property="beanCollection"/>
			</html:select>	
			<html:select name="systemAgentForm" property="p_t4_4" style="width:50px">
				<jsp:useBean id="p_t4_4" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t4_4" property="ccd_cd" value="009"/>
				</jsp:useBean>
				<html:optionsCollection name="p_t4_4" property="beanCollection"/>
			</html:select>&nbsp;		
		</td>
	</tr>
	<tr bgcolor="DDC89D">
		<td height="1" colspan="3"></td>
	</tr>
	<tr>
		<td height="23" bgcolor="F8F4EC" align="center"><strong>상태</strong></td>
		<td bgcolor="DDC89D"></td>
		<td>&nbsp;
			<html:select name="systemAgentForm" property="p_use" style="width:50px">
				<jsp:useBean id="p_use" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_use" property="ccd_cd" value="019"/>
				</jsp:useBean>
				<html:optionsCollection name="p_use" property="beanCollection"/>
			</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="red"><b>※ 실행제외 : <b>
			<html:select name="systemAgentForm" property="p_t5" style="width:70px">
				<jsp:useBean id="p_t5" class="nexti.ejms.list.form.CcdListForm">
					<jsp:setProperty name="p_t5" property="ccd_cd" value="011"/>
				</jsp:useBean>
				<html:option value="">제외없음</html:option>
				<html:optionsCollection name="p_t5" property="beanCollection"/>
			</html:select>
		</td>
	</tr>
	<tr bgcolor="DDC89D">
		<td height="1" colspan="3"></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="3" align="center">
			<a href="javascript:writeSumbit(document.forms[0])"><img src="/images/common/btn_save2.gif" alt="저장"></a>
			<a href="javascript:window.close()"><img src="/images/common/btn_close2.gif" alt="닫기"></a>
		</td>
	</tr>
</table>
</html:form>
<html:messages id="msg" message="true">
  <script language="javascript">
  	alert('<bean:write name="msg"/>');
  	window.close();
  </script>
</html:messages>
<iframe name="actionFrame" width="0" height="0" title="" onload="listReload()"></iframe>
</body>
</html>