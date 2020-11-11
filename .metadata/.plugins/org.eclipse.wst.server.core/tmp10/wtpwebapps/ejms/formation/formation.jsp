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
<title>Á¶Á÷µµ</title>
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script>
var tree1;
function loadFormation(isNew, orggbn, orgid) {
	setTree("tree1");
	setUrl("/formation.do");
	setType("DEPTUSER");
	setViewObjectId(formationLayer);
	getFormation(isNew, orggbn, orgid);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<div id="formationLayer" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto; background-color:#F5F5F5;"></div>
<script>
loadFormation(true, "<%=nexti.ejms.util.Utils.nullToEmptyString((String)request.getParameter("orggbn"))%>",
									"<%=nexti.ejms.util.Utils.nullToEmptyString((String)request.getParameter("orgid"))%>");
</script>
</body>
</html>