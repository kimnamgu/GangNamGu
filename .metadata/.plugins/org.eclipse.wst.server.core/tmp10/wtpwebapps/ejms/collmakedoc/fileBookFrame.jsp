<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 첨부파일 목록
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
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script src="/script/common.js"></script>
<script language="javascript">		
	function click_delete(actionpath, fileseq) {
		document.forms[0].fileseq.value = fileseq;
		
		if(confirm("삭제하시겠습니까?") == true) {
			window.parent.document.forms[0].filecount.value--;
			document.forms[0].action = actionpath;
			document.forms[0].submit();
		} else
			document.forms[0].fileseq.value = "";
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/fileBookFrame" method="post">
<html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formseq"/><html:hidden property="fileseq"/>
<table width="530" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="2" colspan="2" bgcolor="D2A9C7"></td>
  </tr>
  <tr>
    <td width="350" height="23" align="center" bgcolor="F6EEF4"><span class="style4">양식파일</span></td>
    <td width="100" align="center" bgcolor="F6EEF4"><span class="style4">크기</span></td>
    <td width="80" align="center">&nbsp;</td>
  </tr>
<logic:notEmpty name="fileBookFrameForm" property="listfilebook">
	<logic:iterate id="list" name="fileBookFrameForm" property="listfilebook">
  <tr>
    <td height="1" colspan="2" bgcolor="D2A9C7"></td>
  </tr>
  <tr>
    <td height="23" align="center" class="style3"><a target="downloadProcFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a></td>
    <td align="right"><script>convertSize(<bean:write name="list" property="filesize"/>)</script>&nbsp;&nbsp;</td>
    <td align="center"><a href="javascript:click_delete('/formatBookFileDel.do', '<bean:write name="list" property="fileseq"/>')"><img src="/images/common/btn_del.gif" align="absmiddle" alt=""></a></td>
  </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="fileBookFrameForm" property="listfilebook">
	<tr>
    <td height="1" colspan="2" bgcolor="D2A9C7"></td>
  </tr>
  <tr>
    <td height="23" colspan="2" align="center" class="style3">등록된 첨부파일이 없습니다</td>
    <td></td>
  </tr>
</logic:empty>
  <tr>
    <td height="2" colspan="2" bgcolor="D2A9C7"></td>
  </tr>
</table>
</html:form>
<iframe name="downloadProcFrame" width="0" height="0" title=""></iframe>
</body>
</html>