<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 백그라운드 프로세스
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
<logic:notEmpty name="retpage">
<%
int sysdocno = 0;
int formseq = 0;
String formkind = "";

if(request.getAttribute("sysdocno") != null) {
	sysdocno = Integer.parseInt(request.getAttribute("sysdocno").toString());
}

if(request.getAttribute("formseq") != null) {
	formseq = Integer.parseInt(request.getAttribute("formseq").toString());
}

if(request.getParameter("formkind") != null) {
	formkind = request.getParameter("formkind");
}
%>

<logic:equal name="retpage" value="formatbook">
	<script language="javascript">
		parent.location.href="/collcomp/collcompDataBookModify.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
																														"&formkind=<%=formkind%>" +
																														"&frmScrollTop=<%=request.getParameter("frmScrollTop")%>";
	</script>
</logic:equal>

<logic:equal name="retpage" value="formatline">
	<html:messages id="msg" message="true">
		<script language="javascript">alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/collcomp/formatLineView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
																										"&formkind=<%=formkind%>" +
																										"&frmScrollTop=<%=request.getParameter("frmScrollTop")%>";
	</script>
</logic:equal>

<logic:equal name="retpage" value="formatfixed">
	<html:messages id="msg" message="true">
		<script language="javascript">alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/collcomp/formatFixedView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
																											"&formkind=<%=formkind%>" +
																											"&frmScrollTop=<%=request.getParameter("frmScrollTop")%>";
	</script>
</logic:equal>
</logic:notEmpty>