<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� ����÷�� ��׶��� ���μ���
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
<logic:notEmpty name="savecheck">
<%
int sysdocno = 0;
int formseq = 0;
int retcode = 0;
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

if(request.getAttribute("savecheck") != null) {
	retcode = Integer.parseInt(request.getAttribute("savecheck").toString());
}
%>

<script language="javascript">
	function window::onload() {
		if(<%=retcode%> <= 0) {
			alert("�����Ͽ����ϴ�.");
		}
		parent.location.href="/collcomp/formatBookView.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" +
																										 "&formkind=<%=formkind%>" +
																										 "&frmScrollTop=<%=request.getParameter("frmScrollTop")%>";
	}
</script>
</logic:notEmpty>