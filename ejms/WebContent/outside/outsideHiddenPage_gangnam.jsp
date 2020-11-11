<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 백그라운드 프로세스
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
<meta name="author" content="">
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="stylesheet" type="text/css" href="http://gangnam.go.kr/css/sub_common.css">
<html:messages id="msg" message="true">
<body class="survey_bg">
    <div class="survey_box">
        <div class="top"><a href="javascript:top.window.close()" class="close">닫기</a></div>
        <div class="cont">
            <ul>
                <bean:write name="msg" filter="false"/>
            </ul>
        </div>
    </div>
</body>


</html:messages>
