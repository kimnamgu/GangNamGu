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
<body >
    <!-- <div class="wrap" style="width:690px;height:220px; overflow-y: scroll;"> -->
		<div class="container">
			<div class="survey-wrap">
				<h2>신청서</h2>
				<div class="survey-info survey-end" style="width:100%; text-align: center; ">
					<p id="cm" class="vc" ><bean:write name="msg" filter="false"/></p>
					<%-- <bean:write name="msg" filter="false"/>  --%>
					<!-- <img src="/images/request_comp.gif" alt="신청서가 등록 되었습니다"> -->
					<!-- <img src="/images/request_comp2.gif" alt="접수하신 신청서입니다"> -->
				</div>
				<div class="text-center mb20">
					<a href="javascript:top.window.close()" class="btn-close">닫기</a>
				</div>
			</div>
		</div>
	</div>
	</body>
</html:messages>
<script>
window.onload  = function() { 
    if(get_version_of_IE() == '11'){
    	window.resizeTo(710, screen.availHeight * 0.38);
    }else{
		window.resizeTo(710, screen.availHeight * 0.38);
    }
} 

function get_version_of_IE () { 
	 var word; 
	 var agent = navigator.userAgent.toLowerCase(); 
	 // IE old version ( IE 10 or Lower ) 
	 if ( navigator.appName == "Microsoft Internet Explorer" ) word = "msie "; 
	 // IE 11 
	 else if ( agent.search( "trident" ) > -1 ) word = "trident/.*rv:"; 
	 // Microsoft Edge  
	 else if ( agent.search( "edge/" ) > -1 ) word = "edge/"; 
	 // 그외, IE가 아니라면 ( If it's not IE or Edge )  
	 else return -1; 
	 var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" ); 
	 if (  reg.exec( agent ) != null  ) return parseFloat( RegExp.$1 + RegExp.$2 ); 
	 return -1; 
} 

</script>