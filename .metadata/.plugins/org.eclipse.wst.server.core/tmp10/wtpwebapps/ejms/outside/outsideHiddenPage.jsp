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
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/sinchung.js"></script>
<style>
.vc { 		
		top: 0; 		
		bottom:0; 
		margin-top:auto; 
		margin-left:auto; 
		margin-right:auto; 
		padding-top: 30px;
		height:125px;
		width:505px;
		background-image:url(../../images/research_comp_bg.gif);
		background-repeat: no-repeat;
		color: #0040FF;
	} /* 세로 중앙 정렬 */ 
</style>
<html:messages id="msg" message="true">
    <body onload="window.resizeTo(710, screen.availHeight * 0.38)">
    <!-- <div class="wrap" style="width:690px;height:190px; overflow-y: scroll;"> -->
    <div class="wrap" >
		<div class="container">
			<div class="survey-wrap" ">
				<h2>설문조사</h2>
				<div class="survey-info survey-end" style="width:100%; text-align: center; ">
					<p id="cm" class="vc" ><bean:write name="msg" filter="false"/></p>
					<%-- <bean:write name="msg" filter="false"/> --%>
					<!-- <img alt="답변이 등록 되었습니다" src="/images/research_comp.gif"> -->
					<!-- <img src="/images/research_comp2.gif" alt="답변하신 설문조사입니다"> -->
				</div>
				<div class="text-center mb20">
					<a href="javascript:top.window.close()" class="btn-close">닫기</a>
				</div>
			</div>
		</div>
	</div>
	</body>
</html:messages>
