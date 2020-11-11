<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 백그라운드 프로세스
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
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<!-- 
<link rel="stylesheet" href="/css/survey/bootstrap.min.css">
<link rel="stylesheet" href="/css/survey/style.css">
<link rel="stylesheet" href="/css/survey/font-awesome.min.css">
-->

<script src="/script/common.js"></script>

<html:messages id="msg" message="true">
<body onload="window.resizeTo(750, screen.availHeight * 0.38)">
<div class="wrap">
		<div class="container">
			<div class="survey-wrap">
				<h2>설문조사</h2>
				<div class="survey-info survey-end">
					<bean:write name="msg" filter="false"/>
				</div>
				<div class="text-center mb20">
					<a href="javascript:self.close()" class="btn-close">닫기</a>
				</div>
			</div>
		</div>
	</div>
	</body>
</html:messages>