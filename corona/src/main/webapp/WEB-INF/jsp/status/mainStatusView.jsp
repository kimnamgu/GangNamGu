<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
			//dday 계산
			var now = new Date();
			var then = new Date("january 26,2020");
			var gap = now.getTime() - then.getTime();
			gap = Math.floor(gap / (1000 * 60 * 60 * 24));
			$('#dday').text("- 코로나 19 대응 D+" + gap + " -");
			
			
			printClock();
			
	});
	
	/**
	 *  yyyyMMdd 포맷으로 반환
	 */
	function getFormatDate(date){
	    var year = date.getFullYear();              //yyyy
	    var month = (1 + date.getMonth());          //M
	    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
	    var day = date.getDate();                   //d
	    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	    return  year + '' + month + '' + day;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
	}
	
	function printClock() {
	    
	    var clock = document.getElementById("clock");            // 출력할 장소 선택
	    var currentDate = new Date();                                     // 현재시간
	    
	    var calendar = currentDate.getFullYear() + "." + (currentDate.getMonth()+1) + "." + currentDate.getDate() // 현재 날짜
	    var currentHours = addZeros(currentDate.getHours(),2); 
	    var currentMinute = addZeros(currentDate.getMinutes() ,2);
	    var currentSeconds =  addZeros(currentDate.getSeconds(),2);

	    clock.innerHTML = "<"+calendar +" "+ currentHours+":"+currentMinute+":"+currentSeconds + " 질병관리과>&nbsp;&nbsp;"; //날짜를 출력해 줌
	    
	    setTimeout("printClock()",1000);         // 1초마다 printClock() 함수 호출
	}

	function addZeros(num, digit) { // 자릿수 맞춰주기
		  var zero = '';
		  num = num.toString();
		  if (num.length < digit) {
		    for (i = 0; i < digit - num.length; i++) {
		      zero += '0';
		    }
		  }
		  return zero + num;
	}
	
	
	//총괄 중간요약-사용
	function go_totalAllMiddle(WRITE_DATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleTotalView.do' />");
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.submit();
	}
	
	//확진자 통계 중간요약
	function go_confirmMiddle(INFECT_GUBUN,INFECT_DAE,WRITE_DATE,DEPART){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleView.do' />");
		comSubmit.addParam("INFECT_GUBUN", INFECT_GUBUN);
		comSubmit.addParam("INFECT_DAE", INFECT_DAE);
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.addParam("DEPART", DEPART);
		comSubmit.submit();
	}
	
	//확진자 일자용 중간요약-사용
	function go_confirmAllMiddle(INFECT_GUBUN,INFECT_DAE,WRITE_DATE,DEPART){
		
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleConfirmView.do' />");
		comSubmit.addParam("INFECT_GUBUN", INFECT_GUBUN);
		comSubmit.addParam("INFECT_DAE", INFECT_DAE);
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.addParam("DEPART", DEPART);
		comSubmit.submit();
	}
	
	
	//자가격리자 미들
	function go_isoMiddle(SELDATE){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleIsoView.do' />");
		comSubmit.addParam("SELDATE", SELDATE);
		comSubmit.submit();
	}
	
	//확진자 비고 통계 상세
	function go_confirmMiddleRmk(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION){
 		var comSubmit = new ComSubmit();
		
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("INFECT_GUBUN", INFECT_GUBUN);
		comSubmit.addParam("INFECT_DAE", INFECT_DAE);
		comSubmit.addParam("INFECT_SO", INFECT_SO);
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.addParam("ETC_GUBUN", ETC_GUBUN);
		comSubmit.addParam("ETC_GUBUN_CONDITION", ETC_GUBUN_CONDITION);
		comSubmit.submit();
	}

	
	//자가격리자 통계 상세-해외입국자
	function go_overseaIsoDtl(GUBUN,POSITION,CONDITION,SELDATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleView.do' />");
		comSubmit.addParam("GUBUN", GUBUN);
		comSubmit.addParam("POSITION", POSITION);
		comSubmit.addParam("CONDITION", CONDITION);
		comSubmit.addParam("SELDATE", SELDATE);
		comSubmit.submit();
	}
	
	//자가격리자 통계 상세-국내접촉자
	function go_domesticIsoDtl(GUBUN,POSITION,CONDITION,SELDATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleView.do' />");
		comSubmit.addParam("GUBUN", GUBUN);
		comSubmit.addParam("POSITION", POSITION);
		comSubmit.addParam("CONDITION", CONDITION);
		comSubmit.addParam("SELDATE", SELDATE);
		comSubmit.submit();
	}
	
	//상담민원 상세
	function go_consultAllMiddle(SELDATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleConsultView.do' />");
		comSubmit.addParam("SELDATE", SELDATE);
		comSubmit.submit();
	}
	
	//상담민원 중간요약
	function go_clinicAllMiddle(SELDATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusMiddleClinicView.do' />");
		comSubmit.addParam("SELDATE", SELDATE);
		comSubmit.submit();
	}
	
	//선별진료소 케이스상세
	function go_clinicCaseDtl(GUBUN,CLINIC_CASE_CONDITION,CLINIC_CASE_SELDATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("CLINIC_CASE_GUBUN", GUBUN);
		comSubmit.addParam("CLINIC_CASE_CONDITION", CLINIC_CASE_CONDITION);
		comSubmit.addParam("CLINIC_CASE_SELDATE", CLINIC_CASE_SELDATE);
		comSubmit.submit();
	}
</script>

<style type="text/css">
 u {
    text-decoration: none;
    border-bottom: 10px solid black;
  }​
</style>
</head>

<body>

<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/corona/images/popup_title.gif" align="absmiddle">
	</a>코로나19 관리 현황
</div>

<table style="width:100%;">
<tr>
<td align="center">
		<div style="width:900px;margin-bottom:20px;border:2px solid gray;">
			<table style="width:100%;margin-top:20px;margin-bottom:20px;">	
				<tr>
					<td id="dday" align="center" style="font-size: 22px;font-weight:700;">
						<!-- - 코로나 19 대응 D+172 - -->
					</td>
				</tr>			
				<tr>
					<td align="center">
						<h1><u>&nbsp;&nbsp;『코로나19』관리 현황&nbsp;&nbsp;</u></h1>
					</td>
				</tr>	 
			</table>
		
		
			<table style="width:100%;margin-top:40px;margin-bottom:20px;">				
				<tr>
					<td align="right" style="font-size: 20px;color:gray;font-family:굴림,monospace;font-weight: bold; ">
					<%-- < <%= sf.format(nowTime) %> 질병관리과 >&nbsp;&nbsp;&nbsp;&nbsp; --%>
						<div id="clock"></div>
					</td>
				</tr>	 
			</table>
			
			
			<table style="width:100%;padding:0px 20px 5px 0px;">	
				<tr>
					<td align="left" style="padding:0px 0px 10px 20px;" >
						<h3 style="display:inline;">□ 총괄</h3><h4 style="display:inline;">(실거주지 기준)</h4>
					</td>
				</tr>	 
			</table>
				
				<div class="col-md-12" align="center">
					<div class="table-responsive m-b-40">
 						 <table class="table table-bordered table-data3">
					     <thead style="background-color:#0B2161;">
					        <tr align="center">
					        				<th rowspan='2' style="font-size:20px;width:17%">구분</th>
											<th rowspan='2' style="font-size:20px;width:15%">합계</th>
											<th colspan='5' width='35%'><font style="font-size:15px;">강남구거주자(확진자)</font></th>
											<th rowspan='2'><font style="font-size:15px;">타시구거주자</font><br>(우리구 선별진료소에서 확진판정)</th>
											<th rowspan='2'>비고</th>
							</tr>
					        <tr align="center">
					        				<th colspan='2'>소계</th>
											<th>완치</th>
											<th>치료중</th>
											<th>사망</th>
							</tr>
					      </thead>
					      <tbody>
						      <tr>
						      	<td align='center' style='vertical-align:middle;color:#0099FF;CURSOR:hand;' onclick="go_totalAllMiddle(${totalMap.SELDATE})">${totalMap.SHOWDATE}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.TODAY_GANGNAM_CURED_P+totalMap.TODAY_GANGNAM_CURING_P+totalMap.TODAY_GANGNAM_DEATH_P +totalMap.TODAY_TASIGU_P}</td>
						      	
						      	<td align='center' style='vertical-align:middle;' colspan='2'>${totalMap.TODAY_GANGNAM_CURED_P+totalMap.TODAY_GANGNAM_CURING_P+totalMap.TODAY_GANGNAM_DEATH_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.TODAY_GANGNAM_CURED_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.TODAY_GANGNAM_CURING_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.TODAY_GANGNAM_DEATH_P}</td>
						      	
						      	<td align='center' style='vertical-align:middle;'>${totalMap.TODAY_TASIGU_P}</td>
						      	<td align='center' style='vertical-align:middle;' rowspan='4'></td>

						      </tr>
						      
						      
						      
					   	      <tr>
						      	<td align='center' style='vertical-align:middle;' rowspan='3'>누계</td>
						      	<td align='center' style='vertical-align:middle;' rowspan='3'>${totalMap.GANGNAM_CURED_P+totalMap.GANGNAM_CURING_P+totalMap.GANGNAM_DEATH_P + totalMap.GANGNAM_ICHUP_CURED_P+totalMap.GANGNAM_ICHUP_CURING_P+totalMap.GANGNAM_ICHUP_DEATH_P+totalMap.TASIGU_P}</td>
						      	
						      	<td align='center' style='vertical-align:middle;color:#0900FF;font-weight:bold;font-size:12px;'>강남구확진</td>
						      	<td align='center' style='vertical-align:middle;color:#0900FF;'>${totalMap.GANGNAM_CURED_P+totalMap.GANGNAM_CURING_P+totalMap.GANGNAM_DEATH_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_CURED_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_CURING_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_DEATH_P}</td>
						      	
						      	<td align='center' style='vertical-align:middle;' rowspan='3'>${totalMap.TASIGU_P}</td>
						      </tr>
						      <tr>
						      	<td align='center' style='vertical-align:middle;font-size:12px;'>타시구이첩</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_ICHUP_CURED_P+totalMap.GANGNAM_ICHUP_CURING_P+totalMap.GANGNAM_ICHUP_DEATH_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_ICHUP_CURED_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_ICHUP_CURING_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_ICHUP_DEATH_P}</td>
						      </tr>
						      <tr>
						      	<td align='center' style='vertical-align:middle;color:#FF4000;'>계</td>
						      	<td align='center' style='vertical-align:middle;color:#FF4000;'>${totalMap.GANGNAM_CURED_P+totalMap.GANGNAM_CURING_P+totalMap.GANGNAM_DEATH_P + totalMap.GANGNAM_ICHUP_CURED_P+totalMap.GANGNAM_ICHUP_CURING_P+totalMap.GANGNAM_ICHUP_DEATH_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_CURED_P + totalMap.GANGNAM_ICHUP_CURED_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_CURING_P + totalMap.GANGNAM_ICHUP_CURING_P}</td>
						      	<td align='center' style='vertical-align:middle;'>${totalMap.GANGNAM_DEATH_P + totalMap.GANGNAM_ICHUP_DEATH_P}</td>
						      </tr>
						      
						      
					      </tbody>
					      
					      </table>
				      </div>
				</div>

			<table style="width:100%;padding:0px 20px 5px 0px;">	
				<tr>
					<td align="left" style="padding:0px 0px 10px 20px;" >
						<h3>□ 확진자</h3>
					</td>
					<td align="right" style="padding:0px 20px 5px 0px;">
						<a href="/corona/manage/confirmManage.do" class="btn btn-secondary" id="statistics"><i class="fa fa-tag"></i> 확진자 관리</a>
					</td>
				</tr>	 
			</table>
			
				<div class="col-md-12" align="center">
					<div class="table-responsive m-b-40">
						 <table class="table table-bordered table-data3">
					     <thead style="background-color:#0B2161;">
					        <tr align="center">
  											<th style="font-size:20px;width:20%">구분</th>
											<th style="font-size:20px;width:8%">총계</th>
											<th>해외</th>
											<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
													<th><c:out value="${item}" /></th>
											</c:forEach>
											
	<!-- 										<th>신천지</th>
											<th>대구</th>
											<th>콜센터</th>
											<th>분당<br>제생<br>병원</th>
											<th>이태원</th>
											<th>삼성<br>서울<br>병원</th>
											<th>확진자<br>접촉자</th> -->
											<th>기타</th>
											<th>비고</th>
							</tr>
					      </thead>
					      <tbody>
					      <tr>
					      	<td align='center' style='color:#0099FF;CURSOR:hand;' onclick="go_confirmAllMiddle('ALL','', ${confirmMap.SELDATE}),''">${confirmMap.SHOWDATE}</td>
					      	<td align='center'>${confirmMap.CUR_TOTAL_CNT}</td>
					      	<td align='center'>${confirmMap.OVERSEA}</td>
					      	
							<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
								<c:set var="ti_dt">SHOW_${i.index}</c:set>
								<td align='center'>${confirmMap[ti_dt]}</td>
							</c:forEach>

					      	<td align='center'>${confirmMap.ETC}</td>
				      		<td rowspan='2' align='right' style='color:#222222;'>
							</td>
					      </tr>
				   	      <tr>
					      	<td align='center'>누계</td>
					      	<td align='center'>${confirmMap.ACCUM_CUR_TOTAL_CNT}</td>
					      	<td align='center'>${confirmMap.ACCUM_OVERSEA}</td>

							<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
								<c:set var="ti_dt">ACCUM_SHOW_${i.index}</c:set>
								<td align='center'>${confirmMap[ti_dt]}</td>
							</c:forEach>

					      	<td align='center'>${confirmMap.ACCUM_ETC}</td>
					      </tr>
					      </tbody>
					      
					      </table>
				      </div>
				</div>
				
				<!-- 자가격리자 현황  -->
				<table style="width:100%;padding:0px 20px 5px 0px;">	
					<tr>
						<td align="left" style="padding:0px 0px 10px 20px;" >
							<h3>□ 자가격리자</h3>
						</td>
						<td align="right" style="padding:0px 20px 5px 0px;">
							<a href="/corona/manage/overseaManage.do" class="btn btn-secondary" id="statistics"><i class="fa fa-tag"></i> 해외입국자 관리</a>
							<a href="/corona/manage/domesticContactManage.do" class="btn btn-secondary" id="statistics"><i class="fa fa-tag"></i> 국내접촉자 관리</a>
						</td>
					</tr>	 
				</table>
				
				<div class="col-md-12" align="center">
					<div class="table-responsive m-b-40">
						 <table class="table table-bordered table-data3">
					     <thead style="background-color:#0B2161;">
					        <tr align="center">
								<th style="font-size:20px;width:20%">구분</th>
								<th style="font-size:20px;width:20%;">합계</th>
								<th>해외 입국자</th>
								<th>국내접촉자</th>
							</tr>
					      </thead>
					      <tbody>
						      <tr>
						      	<td align='center' style='color:#0099FF;CURSOR:hand;' onclick="go_isoMiddle(${overseaIsoMap.SELDATE})">${overseaIsoMap.SHOWDATE}</td>
						      	<td align='center' style='color:#222222;'>${overseaIsoMap.OVERSEA_CUR_ISO+domIsoMap.DOM_CUR_ISO}</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','해외','cur',${overseaIsoMap.SELDATE})">${overseaIsoMap.OVERSEA_CUR_ISO}</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','국내','cur',${domIsoMap.SELDATE})">${domIsoMap.DOM_CUR_ISO}</td>
						      </tr>
					   	      <tr>
						      	<td align='center' style='color:#222222;'>자가격리(14일간)</td>
						      	<td align='center' style='color:#222222;'>${overseaIsoMap.OVERSEA_DUE_ISO+domIsoMap.DOM_DUE_ISO}</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','해외','due',${overseaIsoMap.SELDATE})">${overseaIsoMap.OVERSEA_DUE_ISO}</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','국내','due',${domIsoMap.SELDATE})">${domIsoMap.DOM_DUE_ISO}</td>
						      </tr>
						      <tr>
						      	<td align='center' style='color:#222222;'>해제(소계)</td>
						      	<td align='center' style='color:#222222;'>${overseaIsoMap.OVERSEA_FREE_ISO+domIsoMap.DOM_FREE_ISO}(${overseaIsoMap.OVERSEA_ACCUM_ISO+domIsoMap.DOM_ACCUM_ISO})</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','해외','free',${overseaIsoMap.SELDATE})">${overseaIsoMap.OVERSEA_FREE_ISO}(${overseaIsoMap.OVERSEA_ACCUM_ISO})</td>
						      	<td align='center' style='color:#222222;' onclick="go_overseaIsoDtl('iso','국내','free',${domIsoMap.SELDATE})">${domIsoMap.DOM_FREE_ISO}(${domIsoMap.DOM_ACCUM_ISO})</td>
						      </tr>
						      <tr>
						      	<td align='center' style='color:#222222;'>누계</td>
						      	<td align='center' style='color:#222222;'>${overseaIsoMap.OVERSEA_TOTAL_ISO+domIsoMap.DOM_TOTAL_ISO}</td>
						      	<td align='center' style='color:#222222;'>${overseaIsoMap.OVERSEA_TOTAL_ISO}</td>
						      	<td align='center' style='color:#222222;'>${domIsoMap.DOM_TOTAL_ISO}</td>
						      </tr>
					      </tbody>
					      </table>
				      </div>
				</div>
				
				<!-- 상담 민원 현황  -->
				<table style="width:100%;padding:0px 20px 5px 0px;">	
					<tr>
						<td align="left" style="padding:0px 0px 10px 20px;" >
							<h3>□ 상담 민원</h3>
						</td>
						<td align="right" style="padding:0px 20px 5px 0px;">
							<a href="/corona/manage/consultManage.do" class="btn btn-secondary" id="statistics"><i class="fa fa-tag"></i> 상담 민원 관리</a>
						</td>
					</tr>	 
				</table>				
				
				<div class="col-md-12" align="center">
					<div class="table-responsive m-b-40">
						 <table class="table table-bordered table-data3">
					     <thead style="background-color:#0B2161;">
					        <tr align="center">
								<th rowspan='2' style="font-size:20px;width:20%;">구분</th>
								<th rowspan='2' style="font-size:20px;width:20%;">합계</th>
								<th colspan='2'>재난안전대책본부</th>
							</tr>
					        <tr align="center">
								<th>주간(09:00~22:00)</th>
								<th>야간(22:00~09:00)</th>
							</tr>
							
					      </thead>
					      <tbody>
						      <tr>
						      	<td align='center' style='color:#0099FF;CURSOR:hand;' onclick="go_consultAllMiddle(${consultMap.SELDATE})">${consultMap.SHOWDATE}</td>
						      	<td align='center' style='color:#222222;'>${consultMap.CUR_ALL}</td>
						      	<td align='center' style='color:#222222;'>${consultMap.CUR_JU}</td>
						      	<td align='center' style='color:#222222;'>${consultMap.CUR_YA}</td>
						      </tr>
					   	      <tr>
						      	<td align='center' style='color:#222222;'>누계</td>
						      	<td align='center' style='color:#222222;'>${consultMap.TOTAL_ALL}</td>
						      	<td align='center' style='color:#222222;'>${consultMap.TOTAL_JU}</td>
						      	<td align='center' style='color:#222222;'>${consultMap.TOTAL_YA}</td>
						      </tr>
					      </tbody>
					      </table>
				      </div>
				</div>
				
				
				<!-- 선별진료소 검사 현황  -->
				<table style="width:100%;padding:0px 20px 5px 0px;">	
					<tr>
						<td align="left" style="padding:0px 0px 10px 20px;" >
				    		<h3>□ 선별진료소 검사</h3>
						</td>
						<td align="right" style="padding:0px 20px 5px 0px;">
							<a href="/corona/manage/clinicManage.do" class="btn btn-secondary" id="statistics"><i class="fa fa-tag"></i> 선별진료소 검사 관리</a>
						</td>
					</tr>	 
				</table>	

				<div class="col-md-12" align="center">
					<div class="table-responsive m-b-40">
						 <table class="table table-bordered table-data3">
					     <thead style="background-color:#0B2161;">
					        <tr align="center">
								<th rowspan='2' colspan='2' style='width:20%;font-size:20px;'>구분</th>
								<th rowspan='2' style="font-size:20px;width:15%;">합계</th>
								<th colspan='3'>국내</th>
								<th colspan='4'>해외</th>
								<th rowspan='2'>양성<br>반응자</th>

							</tr>
					        <tr align="center">
					        	<th>소계</th>
								<th>강남구</th>
								<th>타시군구</th>
								<th>소계</th>
								<th>유럽</th>
								<th>미국</th>
								<th>기타국가</th>
							</tr>
							
					      </thead>
					      <tbody>
						      <tr>
						      	<td align='center' rowspan='3' style='vertical-align:middle;color:#0099FF;CURSOR:hand;' onclick="go_clinicAllMiddle(${clinicMap.SELDATE})">${clinicMap.SHOWDATE}</td>
						      	<td align='center'>신규</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_OVERSEA +clinicMap.CLINIC_DOMESTIC}</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.GANGNAM_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TA_CLINIC_DOMESTIC}</td>
						      	
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_OVERSEA}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_EU}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_UN}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_OVER_ETC}</td>
						      	
						      	<td align='center'  style='color:#222222;'>${clinicMap.CONFIRM_ISO_EX_Y}</td>
						      </tr>
						      <tr>
						      	<td align='center'>격리해제자</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_OVERSEA+clinicMap.ISO_CLINIC_DOMESTIC}</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_GANGNAM_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_TA_CLINIC_DOMESTIC}</td>
						      	
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_OVERSEA}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_EU}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_UN}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.ISO_CLINIC_OVER_ETC}</td>
						      	
						      	<td align='center'  style='color:#222222;'>${clinicMap.CONFIRM_ISO_Y}</td>
						      </tr>
   						      <tr>
						      	<td align='center' style='color:#8A084B;'>당일합계</td>
						      	<td align='center' style='color:#FF4000;'>${clinicMap.CLINIC_OVERSEA +clinicMap.CLINIC_DOMESTIC+clinicMap.ISO_CLINIC_OVERSEA+clinicMap.ISO_CLINIC_DOMESTIC}</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_DOMESTIC+clinicMap.ISO_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.GANGNAM_CLINIC_DOMESTIC+clinicMap.ISO_GANGNAM_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TA_CLINIC_DOMESTIC+clinicMap.ISO_TA_CLINIC_DOMESTIC}</td>
						      	
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_OVERSEA+clinicMap.ISO_CLINIC_OVERSEA}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_EU+clinicMap.ISO_CLINIC_EU}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_UN+clinicMap.ISO_CLINIC_UN}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.CLINIC_OVER_ETC+clinicMap.ISO_CLINIC_OVER_ETC}</td>
						      	
						      	<td align='center' style='color:#FF4000;'>${clinicMap.CONFIRM_ISO_EX_Y+clinicMap.CONFIRM_ISO_Y}</td>
						      </tr>
						      
					   	      <tr>
						      	<td align='center' style='color:#222222;' colspan=2>누계</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_OVERSEA +clinicMap.TOTAL_CLINIC_DOMESTIC}</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.GANGNAM_TOTAL_CLINIC_DOMESTIC}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TA_TOTAL_CLINIC_DOMESTIC}</td>
						      	
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_OVERSEA}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_EU}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_UN}</td>
						      	<td align='center' style='color:#222222;'>${clinicMap.TOTAL_CLINIC_OVER_ETC}</td>
						      	
						      	<td align='center' style='color:#222222;'>${clinicMap.CONFIRM_ACCUM_Y}</td>
						      </tr>
<!-- 						      <tr>
							      <td colspan="5">
							      	<font align="left" color="#147814">※ 검사결과 확진자는 확진자 기타란에 내역을 표기</font>
							      </td>
						      </tr> -->
					      </tbody>
					      </table>
					      	
					      	
				      </div>
				</div>
				
				<!-- 누계 -->
				<table style="width:100%;">	
					<tr>
						<td style="padding:0 20 20 20;">
							<table style="width:100%;border:1px solid gray;">	
								<tr>
									<td style="padding:20px;">
									    <h4>* 해외입국자  자가격리 해제자 검사(금일 / 누계) : <a href="#this" onclick="go_clinicCaseDtl('clinicCase','해제','${clinicCaseMap.SELDATE}')">${clinicCaseMap.OVERSEA_ISO_CLINC}/${clinicCaseMap.TOT_OVERSEA_ISO_CLINC}</a></h4>
									    <h4>* 학교 학생 및 교직원 검사 현황(금일 / 누계) : <a href="#this" onclick="go_clinicCaseDtl('clinicCase','학생교직원','${clinicCaseMap.SELDATE}')">${clinicCaseMap.STU_TEC_CLINC}/${clinicCaseMap.TOT_STU_TEC_CLINC}</a></h4>
									    <h4>* 운수업체 종사자 검사현황(금일 / 누계) : <a href="#this" onclick="go_clinicCaseDtl('clinicCase','운수업','${clinicCaseMap.SELDATE}')">${clinicCaseMap.DRIVER_CLINC}/${clinicCaseMap.TOT_DRIVER_CLINC}</a></h4>
								    </td>
							    </tr>
						    </table>
						</td>
					</tr>			
				</table>
				
		</div>
		

</td>
</tr>
</table>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>