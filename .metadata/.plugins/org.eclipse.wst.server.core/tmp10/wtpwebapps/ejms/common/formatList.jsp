<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식목록
 * 설명: <td valign="top" height="172" id="formFrameTD"><iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="양식목록"></iframe></td>
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
String formseq = "";

if (request.getParameter("formseq") != null) {
	formseq = request.getParameter("formseq");
}
%>

<html>
<head>
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script>
try {
	parent.formFrame.document.body.onscroll = function() {
		try {
			formTitle.style.pixelTop = parent.formFrame.document.body.scrollTop;
		} catch (e) {}
	}
} catch (e) {}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<div id="formTitle" style="position:absolute">
	<table bgcolor="white" width="800" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="4" class="list_bg"></td>
		</tr>
		<tr>
			<td colspan="4" height="1"></td>
		</tr>
		<tr>
			<td class="list_t" width="30">&nbsp;</td>
			<td class="list_t" width="50">연번</td>
			<td class="list_t">양식제목</td>
			<td class="list_t" width="150">양식유형</td>
		</tr>
		<tr>
			<td colspan="4" height="1"></td>
		</tr>
		<tr>
			<td colspan="4" class="list_bg"></td>
		</tr>
	</table>
</div>
<!--검색 리스트 테이블 시작-->
<table width="800" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" class="list_bg"></td>
	</tr>
	<tr>
		<td colspan="4" height="1"></td>
	</tr>
	<tr>
		<td class="list_t" width="30">&nbsp;</td>
		<td class="list_t" width="50">연번</td>
		<td class="list_t">양식제목</td>
		<td class="list_t" width="150">양식유형</td>
	</tr>
	<tr>
		<td colspan="4" height="1"></td>
	</tr>
	<tr>
		<td colspan="4" class="list_bg"></td>
	</tr>
<logic:notEmpty name="formatForm" property="listform">
<logic:iterate id="list" name="formatForm" property="listform" indexId="i">
	<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		<td align="right"><logic:equal name="list" property="formseq" value="<%=formseq%>"><img src="/images/common/arrow.gif" width="19" height="13" alt=""></logic:equal></td>
		<td class="list_no"><bean:write name="list" property="seqno"/></td>
		<td class="list_s"><a class="list_s2" href="javascript:parent.viewForm('<bean:write name="list" property="formkind"/>', '<bean:write name="list" property="sysdocno"/>', '<bean:write name="list" property="formseq"/>', 'frmScrollTop='+parent.formFrame.document.body.scrollTop)"><bean:write name="list" property="formtitle"/></a></td>
		<td class="list_s" align="center"><bean:write name="list" property="formkindname"/></td>
	</tr>
	<tr>
		<td colspan="4" class="list_bg2"></td>
	</tr>
</logic:iterate>
</logic:notEmpty>
<logic:empty name="formatForm" property="listform">
	<tr>
		<td colspan="4" class="list_s">등록된 양식이 없습니다</td>
	</tr>
	<tr>
		<td colspan="4" class="list_bg2"></td>
	</tr>
</logic:empty>
</table>
</body>
</html>