<%--
 * 작성일: 2010.10.12
 * 작성자: 대리 서동찬
 * 모듈명: 고령군 행정포털 - 설문조사 연계
 * 설명: http://localhost/client/jsp/onlinePoll.jsp?userid=admin
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="nexti.ejms.research.model.ResearchManager"/>
<jsp:directive.page import="nexti.ejms.user.model.UserManager"/>
<jsp:directive.page import="nexti.ejms.user.model.UserBean"/>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String userid = (String)request.getParameter("userid");
	String deptcd = "";
	
	UserBean uBean = UserManager.instance().getUserInfo(userid);
	if ( uBean != null ) {
		deptcd = uBean.getDept_id();
	}
	
	pageContext.setAttribute("rchList", ResearchManager.instance().getRchParticiList(userid, deptcd, "", 1, 5));
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css" />
<script src="/script/common.js"></script>
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="155" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
	<logic:notEmpty name="rchList" scope="page">
		<logic:iterate id="list" name="rchList" scope="page">
			<tr>
				<td width="100%" height="20" class="link01" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap">
					<a href="javascript:showPopup('/research.do?rchno=<bean:write name="list" property="rchno"/>', 700, 680, 0, 0)" title="<bean:write name="list" property="title"/>"><img src="/images/common/dot_04.gif" border="0">&nbsp;<bean:write name="list" property="title"/></a>
				</td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="rchList" scope="page">
		<tr>
			<td width="100%" height="20" class="link01" align="center">설문조사가 없습니다.</td>
		</tr>
	</logic:empty>
</table>
</body>
</html>