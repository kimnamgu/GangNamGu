<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<title>소송업무 관리시스템</title>
<style type="text/css">
*html ul li a{height:1%;}
#dropdownNav{margin:0;padding:0;cursor:pointer;}
#dropdownNav a, #dropdownNav a:hover, #dropdownNav ul{font:11px arial;}
#dropdownNav ul{width:190px;background:#f0f0f0;list-style-type: none; border-bottom: 1px solid #777777;margin:0; padding:0;}
#dropdownNav li ul{border:none;padding-left:15px;}
#dropdownNav ul li a{margin:1px 0;border:1px solid #f0f0f0;padding:2px 4px; display:block;color:#333333;text-decoration:none;}
#dropdownNav ul li a:hover{color:#442222;background-color:#dddddd;border-color:#666666;}
#dropdownNav .caption{margin:0;border-top:1px solid #777777;color: #333333;font-weight:600; background-image:url('/data/201011/IJ12886133923544/arrow.gif');background-color:#c6c3bb; background-position:3px center;background-repeat: no-repeat;padding:5px 4px 5px 18px;}
#dropdownNav .show ul{display:block;}
#dropdownNav .hide ul{display:none;}
#dropdownNav .show div.caption{background-image:url('/data/201011/IJ12886133923544/down_arrow.gif');}
</style>

<script type="text/javascript" language="javascript">
<!--
var user_right = "${sessionScope.userinfo.userright}";

$(document).ready(function(){
	
	/*
	$("#initial").on("click", function(e){ //초기화 버튼
		e.preventDefault();				
		$("#form1").each(function() {				 
	       this.reset();  
	    });  
	});
	*/
	
	
	mn1.parentNode.className='show';
	mn2.parentNode.className='show';
	if(user_right == 'A')
		mn3.parentNode.className='show';
});


	function populate_menu(i){
		if(i.parentNode.className=='show'){
			i.parentNode.className='hide';
		}
		else{
			i.parentNode.className='show';
		}
	}
	
-->
</script>
</head>
<body>
<table>
<tr height = "12">
<td></td>
</tr>
</table>
<div id="dropdownNav">
	<ul id="nav">
		<li class="hide">
			<div id="mn1" class="caption" onclick="javascript:populate_menu(this)">소송업무</div>
			<ul>
				<li><a href="/sosong/incident/allIncidentList.do" target="main">소송리스트</a></li>
				<li><a href="/sosong/incident/allSimList.do" target="main">소송현황</a></li>
				<li><a href="/sosong/incident/allSimList.do?GNGB=1" target="main">구청 제소사건 현황 </a></li>	
				<li><a href="/sosong/incident/crimIncidentList.do" target="main">형사사건 리스트 </a></li>				
			</ul>
		</li>
		<li class="hide">
			<div id="mn2" class="caption" onclick="javascript:populate_menu(this)">통계화면</div>
			<ul>
				<li><a href="/sosong/incident/incidentStatisticsList1.do" target="main">소송내역 및 결과</a></li>
				<li><a href="/sosong/incident/incidentStatisticsList4.do" target="main">직접수행 현황</a></li>
				<li><a href="/sosong/incident/incidentStatisticsList3.do" target="main">소송 변호사별 소송현황</a></li>							
			</ul>
		</li>
		<c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
		<c:choose>
	 	<c:when test="${us_rt eq 'A'}">
		<li class="hide">
			<div id="mn3" class="caption" onclick="javascript:populate_menu(this)">관리자메뉴</div>
			<ul>				
				<li><a href="/sosong/incident/incidentStatisticsList2.do" target="main">변호사 수임료 지급현황</a></li>
				<li><a href="/sosong/common/idApproveList.do" target="main">사용자 승인</a></li>	
				<li><a href="/sosong/common/allSmsAlrimList.do" target="main">알림데이터 조회</a></li>
				<li><a href="/sosong/common/allSmsSendList.do" target="main">SMS발송내역 조회</a></li>
				<!-- 
				<li><a href="/sosong/common/getSmsSendList.do" target="main">SMS발송내역 조회2</a></li>
				<li><a href="/sosong/common/allSmsSendList22.do" target="main">SMS발송내역 조회3</a></li>
				 -->				
			</ul>
		</li>
		</c:when>
        </c:choose>	
	</ul>
</div>
</body>
</html>