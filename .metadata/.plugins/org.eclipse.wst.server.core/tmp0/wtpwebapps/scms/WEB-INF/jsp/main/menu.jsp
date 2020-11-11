<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<title>수의계약 관리시스템</title>
<style type="text/css">
     *html ul li a{height:1%;}
    #dropdownNav{margin:0;padding:0;cursor:pointer;}
    #dropdownNav #nav{}
    #dropdownNav ul{width:190px;background:#2196F3;list-style-type: none;margin:0; padding:0;}
    #dropdownNav ul li a{margin:1px 0;padding:5px 0px 5px 40px; display:block; color:#fff; text-decoration:none; font-size: 13px}
    #dropdownNav ul li a:hover{background-color:#1d82d2;border-color:#666666;}
    #dropdownNav ul li>ul {padding: 5px 0; border-top: 1px solid #777;}
    /*#dropdownNav ul li>ul>li a::before {content: "-"; display: inline-block; color: #fff; position: relative; right: 5px;}*/
    #dropdownNav .caption{margin:0;color: #333333;font-weight:600; background-image:url('/data/201011/IJ12886133923544/arrow.gif');background-color:#fff; background-position:3px center;background-repeat: no-repeat;padding:15px 4px 15px 25px; font-size: 15px; border-top: 1px solid #777;}
    #dropdownNav .show ul{display:block;}
    #dropdownNav .hide ul{display:none;}
    #dropdownNav .show div.caption{background-image:url('/scms/images/left_icon.png'); background-position: 10px; padding-left: 30px;}
</style>

<script type="text/javascript" language="javascript">

var user_right = "${sessionScope.userinfo.userright}";
$(document).ready(function(){

	if(user_right == 'A'){
		$("#testA").show();
	}else{
		
		$("#testA").hide();
	}
	
	$("#logout").on("click", function(e){ //글쓰기 버튼
		e.preventDefault();
		fn_logout();
	});	
	
	mn1.parentNode.className='show';
	mn2.parentNode.className='show';
	mn3.parentNode.className='show';
	if(user_right == 'A'){
		mn4.parentNode.className='show';
		mn6.parentNode.className='show';
	}else{
		//mn4.parentNode.className='hide';
	}
	mn5.parentNode.className='show';
			
});

	function populate_menu(i){
		if(i.parentNode.className=='show'){
			i.parentNode.className='hide';
		}
		else{
			i.parentNode.className='show';
		}
	}
	
	
	function fn_logout(){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/logout.do' />");
		comSubmit.submit();
	}
	
</script>
</head>
<body>
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/>

<table>
<tr height = "12">
<td></td>
</tr>
</table>
<div id="dropdownNav">
	<ul id="nav">
		<li class="hide">
			<div id="mn1" class="caption" onclick="javascript:populate_menu(this)">발주계획</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtPlanList.do" target="main"><span>- </span> 발주계획</a></li>
				
				<li id="testA"><a href="/scms/daejang/orderPlanPriorApprovalList.do" target="main"><span>- </span> 발주계획 검토승인</a></li>
				
				<li><a href="/scms/daejang/prvCnrtAcceptList.do?wgb=1" target="main"><span>- </span> 사업접수현황(개인)</a></li>
				<li><a href="/scms/daejang/prvCnrtAcceptList.do?wgb=2" target="main"><span>- </span> 사업접수현황(전체)</a></li>						
			</ul>
		</li>
		<li class="hide">
			<div id="mn2" class="caption" onclick="javascript:populate_menu(this)">업체관리</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtCompList.do" target="main"><span>- </span> 업체조회</a></li>
				<li><a href="/scms/daejang/prvCnrtCompInsList.do" target="main"><span>- </span> 업체등록</a></li>
				<li><a href="/scms/daejang/prvCnrtStatistics2.do" target="main"><span>- </span> 만족도평가 조회</a></li>										
			</ul>
		</li>
		<li class="hide">
			<div id="mn3" class="caption" onclick="javascript:populate_menu(this)">업체별 과업실적</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtContractList.do?wgb=2" target="main"><span>- </span> 강남구 과업실적</a></li>
				<li><a href="/scms/daejang/prvCnrtContractList.do?wgb=1" target="main"><span>- </span> 타시군구 과업실적</a></li>										
			</ul>
		</li>
		<c:if test="${chkAdmin eq 'A'}">
		<li class="hide">
			<div id="mn4" class="caption" onclick="javascript:populate_menu(this)">통계조회</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtStatistics1.do" target="main"><span>- </span> 제안업체수 조회</a></li>		        															
			</ul>
		</li>
		</c:if>
		<li class="hide">
			<div id="mn5" class="caption" onclick="javascript:populate_menu(this)">공지사항</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtBoardList.do" target="main"><span>- </span> 공지사항</a></li>													
			</ul>
		</li>
		<li class="show">
			<div id="mn6" class="caption" onclick="javascript:populate_menu(this)">공사 발주 현황</div>
			<ul>
				<li><a href="/scms/daejang/prvCnrtCurrentBuildList.do" target="main"><span>- </span> 동 주민센터 </a></li>		        															
			</ul>
		</li>
	</ul>
</div>
</body>
</html>