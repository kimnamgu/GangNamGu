<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 백그라운드 프로세스
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

if(request.getAttribute("sysdocno") != null) {
	sysdocno = Integer.parseInt(request.getAttribute("sysdocno").toString());
}

if(request.getAttribute("formseq") != null) {
	formseq = Integer.parseInt(request.getAttribute("formseq").toString());
}
%>

<logic:equal name="retpage" value="booksavesuccess">
	<html:messages id="msg" message="true">
		<script language="javascript">//alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inpCompFrmBookModifyView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>";
	</script>
</logic:equal>
<logic:equal name="retpage" value="booksavefail">
	<html:messages id="msg" message="true">
		<script language="javascript">alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inpCompFrmBookModifyView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>";
	</script>
</logic:equal>
<logic:equal name="retpage" value="linesavesuccess">
	<html:messages id="msg" message="true">
		<script language="javascript">//alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inpCompFrmLineModifyView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" + 
													"&scrollTop=<%=(String)request.getParameter("scrollTop")%>" +
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>" +
													"&strIdx=<%=(String)request.getParameter("strIdx")%>" +
													"&endIdx=<%=(String)request.getParameter("endIdx")%>";
	</script>
</logic:equal>
<logic:equal name="retpage" value="linesavefail">
	<html:messages id="msg" message="true">
		<script language="javascript">alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inpCompFrmLineModifyView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" + 
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>" +
													"&strIdx=<%=(String)request.getParameter("strIdx")%>" +
													"&endIdx=<%=(String)request.getParameter("endIdx")%>";
	</script>
</logic:equal>
<logic:equal name="retpage" value="fixedsavesuccess">
	<html:messages id="msg" message="true">
		<script language="javascript">//alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inputCompFormatFixedView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>";
	</script>
</logic:equal>
<logic:equal name="retpage" value="fixedsavefail">
	<html:messages id="msg" message="true">
		<script language="javascript">alert('<bean:write name="msg"/>');</script>
	</html:messages>
	<script language="javascript">
		parent.location.href="/inpCompFrmFixedModifyView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
													"&frmScrollTop=<%=(String)request.getParameter("frmScrollTop")%>" +
													"&formkind=<%=(String)request.getParameter("formkind")%>";
	</script>
</logic:equal>
</logic:notEmpty>