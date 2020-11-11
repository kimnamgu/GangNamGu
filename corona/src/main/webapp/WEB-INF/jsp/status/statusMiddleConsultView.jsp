<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		//공통
		var WRITE_DATE="${WRITE_DATE}";
		
		//구글차트 그리기
		google.charts.load('current', {'packages':['line','controls']});
		chartConsultDrow(); //chartDrow() 실행
		
		//구글 구분차트 그리기
		chartConsultGubunDrow(); //chartDrow() 실행
	});
	
	//상담상세
	function go_consultDtl(CONSULT_DEPART,CONSULT_JUYA_GUBUN,CONSULT_CONSULT_GUBUN,WRITE_DATE){
		
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("CONSULT_DEPART", CONSULT_DEPART);
		comSubmit.addParam("CONSULT_JUYA_GUBUN", CONSULT_JUYA_GUBUN);
		comSubmit.addParam("CONSULT_CONSULT_GUBUN", CONSULT_CONSULT_GUBUN);
		
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.submit();
	}
	
	//상담민원차트 그리기
	  function chartConsultDrow(){
	    var chartData = '';

	    //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
	    var chartDateformat     = 'yyyy년MM월dd일';
	    //라인차트의 라인 수
	    var chartLineCount    = 15;
	    //컨트롤러 바 차트의 라인 수
	    var controlLineCount    = 10;

	    function drawDashboard() {
	      var data = new google.visualization.DataTable();
	      //그래프에 표시할 컬럼 추가
	      data.addColumn('datetime' , '날짜');
	      data.addColumn('number'   , '전체');
	      data.addColumn('number'   , '주간상담');
	      data.addColumn('number'   , '야간상담');
	      //그래프에 표시할 데이터
	      var dataRow = [];
	  	
	  	<c:forEach items="${consultStatisticList}" var="info">
	  	var CUR_TOTAL =parseInt("${info.CUR_TOTAL}");
	  	var CUR_JU =parseInt("${info.CUR_JU}"); 
	  	var CUR_YA =parseInt("${info.CUR_YA}"); 
	  		
	  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ),CUR_TOTAL, CUR_JU, CUR_JU];
		    data.addRow(dataRow); 
	  	</c:forEach>
	  	

	        var chart = new google.visualization.ChartWrapper({
	          chartType   : 'LineChart',
	          containerId : 'lineChartArea', //라인 차트 생성할 영역
	          options     : {
	                          isStacked   : 'percent',
	                          focusTarget : 'category',
	                          height          : 300,
	                          width              : '100%',
	                          legend          : { position: "top", textStyle: {fontSize: 13}},
	                          pointSize        : 5,
	                          tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
	                          hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
	                                                              years : {format: ['yyyy년']},
	                                                              months: {format: ['MM월']},
	                                                              days  : {format: ['dd일']},
	                                                              hours : {format: ['HH시']}}
	                                                            },textStyle: {fontSize:12}},
							vAxis              : {
	                                   	             				minValue: 1,
	                                   	             				viewWindow:{min:0},
	                                   	             				gridlines:{count:8},
	                                   	             				textStyle:{fontSize:12}
	                                   	             				},
	            animation        : {startup: true,duration: 1000,easing: 'in' },
	            annotations    : {pattern: chartDateformat,
	                            textStyle: {
	                            fontSize: 15,
	                            bold: true,
	                            italic: true,
	                            color: '#871b47',
	                            auraColor: '#d799ae',
	                            opacity: 0.8,
	                            pattern: chartDateformat
	                          }
	                        }
	          }
	        });

	        var control = new google.visualization.ControlWrapper({
	          controlType: 'ChartRangeFilter',
	          containerId: 'controlsArea',  //control bar를 생성할 영역
	          options: {
	              ui:{
	                    chartType: 'LineChart',
	                    chartOptions: {
	                    chartArea: {'width': '60%','height' : 40},
	                      hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
	                        gridlines:{count:controlLineCount,units: {
	                              years : {format: ['yyyy년']},
	                              months: {format: ['MM월']},
	                              days  : {format: ['dd일']},
	                              hours : {format: ['HH시']}}
	                        }}
	                    }
	              },
	                filterColumnIndex: 0
	            }
	        });

	        var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
	        date_formatter.format(data, 0);

	        var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
	        window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
	        dashboard.bind([control], [chart]);
	        dashboard.draw(data);

	    }
	      google.charts.setOnLoadCallback(drawDashboard);

	  }
	
	
	//상담민원차트 그리기
	  function chartConsultGubunDrow(){
	    var chartData = '';

	    //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
	    var chartDateformat     = 'yyyy년MM월dd일';
	    //라인차트의 라인 수
	    var chartLineCount    = 15;
	    //컨트롤러 바 차트의 라인 수
	    var controlLineCount    = 10;

	    function drawDashboard() {
	      var data = new google.visualization.DataTable();
	      //그래프에 표시할 컬럼 추가
	      data.addColumn('datetime' , '날짜');
	      data.addColumn('number'   , '전체');
	      data.addColumn('number'   , '1. 검사결과문의');
	      data.addColumn('number'   , '2. 검사관련문의');
	      data.addColumn('number'   , '3. 자가격리(자)관련 문의');
	      data.addColumn('number'   , '4. 선별진료소(시간)관련 문의');
	      data.addColumn('number'   , '5. 확진자 관련 문의');
	      data.addColumn('number'   , '6. 역학조사 관련문의');
	      data.addColumn('number'   , '7. 임시생활시설관련 문의');
	      data.addColumn('number'   , '8. 사회적 거리두기 관련 문의');
	      data.addColumn('number'   , '9. 방역관련 문의');
	      data.addColumn('number'   , '10. 기타 문의');
	      //그래프에 표시할 데이터
	      var dataRow = [];
	  	
	  	<c:forEach items="${consultGubunStatisticList}" var="info">
	  	var GUBUN_TOTAL =parseInt("${info.GUBUN_TOTAL}");
	  	var GUBUN_1 =parseInt("${info.GUBUN_1}"); 
	  	var GUBUN_2 =parseInt("${info.GUBUN_2}");
	  	var GUBUN_3 =parseInt("${info.GUBUN_3}");
	  	var GUBUN_4 =parseInt("${info.GUBUN_4}");
	  	var GUBUN_5 =parseInt("${info.GUBUN_5}");
	  	var GUBUN_6 =parseInt("${info.GUBUN_6}");
	  	var GUBUN_7 =parseInt("${info.GUBUN_7}");
	  	var GUBUN_8 =parseInt("${info.GUBUN_8}");
	  	var GUBUN_9 =parseInt("${info.GUBUN_9}");
	  	var GUBUN_10 =parseInt("${info.GUBUN_10}");
	  	
	  		
	  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ),GUBUN_TOTAL, GUBUN_1, GUBUN_2, GUBUN_3, GUBUN_4, GUBUN_5, GUBUN_6, GUBUN_7, GUBUN_8, GUBUN_9, GUBUN_10];
		    data.addRow(dataRow); 
	  	</c:forEach>
	  	

	        var chart = new google.visualization.ChartWrapper({
	          chartType   : 'LineChart',
	          containerId : 'lineChartGubunArea', //라인 차트 생성할 영역
	          options     : {
	                          isStacked   : 'percent',
	                          focusTarget : 'category',
	                          height          : 300,
	                          width              : '100%',
	                          legend          : { position: "top", textStyle: {fontSize: 13}},
	                          pointSize        : 5,
	                          tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
	                          hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
	                                                              years : {format: ['yyyy년']},
	                                                              months: {format: ['MM월']},
	                                                              days  : {format: ['dd일']},
	                                                              hours : {format: ['HH시']}}
	                                                            },textStyle: {fontSize:12}},
							vAxis              : {
	                                   	             				minValue: 1,
	                                   	             				viewWindow:{min:0},
	                                   	             				gridlines:{count:8},
	                                   	             				textStyle:{fontSize:12}
	                                   	             				},
	            animation        : {startup: true,duration: 1000,easing: 'in' },
	            annotations    : {pattern: chartDateformat,
	                            textStyle: {
	                            fontSize: 15,
	                            bold: true,
	                            italic: true,
	                            color: '#871b47',
	                            auraColor: '#d799ae',
	                            opacity: 0.8,
	                            pattern: chartDateformat
	                          }
	                        }
	          }
	        });

	        var control = new google.visualization.ControlWrapper({
	          controlType: 'ChartRangeFilter',
	          containerId: 'controlsGubunArea',  //control bar를 생성할 영역
	          options: {
	              ui:{
	                    chartType: 'LineChart',
	                    chartOptions: {
	                    chartArea: {'width': '60%','height' : 40},
	                      hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
	                        gridlines:{count:controlLineCount,units: {
	                              years : {format: ['yyyy년']},
	                              months: {format: ['MM월']},
	                              days  : {format: ['dd일']},
	                              hours : {format: ['HH시']}}
	                        }}
	                    }
	              },
	                filterColumnIndex: 0
	            }
	        });

	        var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
	        date_formatter.format(data, 0);

	        var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Gubun_Chart'));
	        window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
	        dashboard.bind([control], [chart]);
	        dashboard.draw(data);

	    }
	      google.charts.setOnLoadCallback(drawDashboard);

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
	</a>현황 요약 정보
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/status/mainStatusView.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 코로나19 현황</a>
	</div>
	
</div>

<style>
  table {
    width: 1500px;
    border: 1px solid #444444;
  }
  th, td {
    border: 1px solid #444444;
    padding:10px;
  }
</style>
		<!-- 재난안전과 요청 테이블 -->
		<div class="table-responsive m-b-40" style="padding:40px;">
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td style="width:200px;text-align:center;font-weight:bold;" rowspan='2'>주야간 구분</td>
					<td style="width:200px;text-align:center;font-weight:bold;" rowspan='2'>주간(09:00~22:00)</td>
					<td style="text-align:right;">${consultMap.TOTAL_JU}건</td>
					<td style="width:200px;text-align:center;font-weight:bold;" rowspan='2'>야간(22:00~09:00)</td>
					<td style="text-align:right;">${consultMap.TOTAL_YA}건</td>

				</tr>
				<tr>
					<td style="text-align:right;">${consultMap.TOTAL_JU_PER}%</td>
					<td style="text-align:right;">${consultMap.TOTAL_YA_PER}%</td>
				</tr>
			</table>
			<table style='border:1px solid;margin-left: auto; margin-right: auto;'>
				<tr>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">1. 검사결과 문의</span> </td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">2. 검사관련 문의</span><br>(증상, 검사가능여부, 검사시기, 검사가능병원, 확인서발급, 검사요청 등)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">3. 자가격리(자)관련 문의</span><br>(앱설치관련, 쓰레기처리, 격리장소변경, 입국후절차 등)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">4. 선별진료소(시간)관련 문의</span><br>(진료시간,장소 등)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">5. 확진자 관련 문의</span><br>(발생장소, 확진자동성, 긴급재난문자 등)</td>
				</tr>
				<tr>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','1. 검사결과 문의','${SELDATE}')">${consultMap.DAY_1}건(${consultMap.DAY_1_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','2. 검사관련 문의','${SELDATE}')">${consultMap.DAY_2}건(${consultMap.DAY_2_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','3. 자가격리(자)관련 문의','${SELDATE}')">${consultMap.DAY_3}건(${consultMap.DAY_3_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','4. 선별진료소(시간)관련 문의','${SELDATE}')">${consultMap.DAY_4}건(${consultMap.DAY_4_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','5. 확진자 관련 문의','${SELDATE}')">${consultMap.DAY_5}건(${consultMap.DAY_5_PER}%)</td>
				</tr>
				<tr>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','1. 검사결과 문의','${SELDATE}')">${consultMap.ACCUM_1}건(${consultMap.ACCUM_1_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','2. 검사관련 문의','${SELDATE}')">${consultMap.ACCUM_2}건(${consultMap.ACCUM_2_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','3. 자가격리(자)관련 문의','${SELDATE}')">${consultMap.ACCUM_3}건(${consultMap.ACCUM_3_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','4. 선별진료소(시간)관련 문의','${SELDATE}')">${consultMap.ACCUM_4}건(${consultMap.ACCUM_4_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','5. 확진자 관련 문의','${SELDATE}')">${consultMap.ACCUM_5}건(${consultMap.ACCUM_5_PER}%)</td>
				</tr>
				
				<tr>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">6. 역학조사 관련문의</span></td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">7. 임시생활시설관련 문의</span><br>(입소가능시설, 시설격리가능여부 등)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">8. 사회적 거리두기 관련 문의</span><br>(행사·전시회·집회·결혼식 등 가능여부, 다단계 등 신고)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">9. 방역관련 문의</span><br>(방역가능여부, 마스크지급 등)</td>
					<td colspan='2' style="width:300px;text-align:center;background-color:#04B4AE;color:black;"><span style="font-weight:bold;">10. 기타 문의</span><br>(보건소업부,전화미수신,담당자연락처 문의 등)</td>
				</tr>
				<tr>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','6. 역학조사 관련문의','${SELDATE}')">${consultMap.DAY_6}건(${consultMap.DAY_6_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','7. 임시생활시설관련 문의','${SELDATE}')">${consultMap.DAY_7}건(${consultMap.DAY_7_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','8. 사회적 거리두기 관련 문의','${SELDATE}')">${consultMap.DAY_8}건(${consultMap.DAY_8_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','9. 방역관련 문의','${SELDATE}')">${consultMap.DAY_9}건(${consultMap.DAY_9_PER}%)</td>
					<td style="text-align:center;">금일</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('TODAY','','10. 기타 문의','${SELDATE}')">${consultMap.DAY_10}건(${consultMap.DAY_10_PER}%)</td>
				</tr>
				<tr>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','6. 역학조사 관련문의','${SELDATE}')">${consultMap.ACCUM_6}건(${consultMap.ACCUM_6_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','7. 임시생활시설관련 문의','${SELDATE}')">${consultMap.ACCUM_7}건(${consultMap.ACCUM_7_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','8. 사회적 거리두기 관련 문의','${SELDATE}')">${consultMap.ACCUM_8}건(${consultMap.ACCUM_8_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','9. 방역관련 문의','${SELDATE}')">${consultMap.ACCUM_9}건(${consultMap.ACCUM_9_PER}%)</td>
					<td style="text-align:center;">누계</td>
					<td style="text-align:right;CURSOR:hand;color:#0099FF;" onclick="go_consultDtl('ACCUM','','10. 기타 문의','${SELDATE}')">${consultMap.ACCUM_10}건(${consultMap.ACCUM_10_PER}%)</td>
				</tr>
			</table>
			
		</div>

	<br>
	
	
	
	<!-- 그래프 -->
<!-- 	<div style="width:100%;;height:500px;padding-left:40px;" id="chartArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[상담민원 일일 주야 현황]</h4>
 
    	<div id="Line_Controls_Chart">
      		라인 차트 생성할 영역
          <div id="lineChartArea"></div>
      		컨트롤바를 생성할 영역
          <div id="controlsArea" style="height:100px;"></div>
        </div>
	</div> -->
	
	<!--  구분 그래프 -->
	<div style="width:100%;;height:500px;padding-left:40px;" id="chartGubunArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[상담민원 일일 구분 현황]</h4>
 
    	<div id="Line_Controls_Gubun_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartGubunArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsGubunArea" style="height:100px;"></div>
        </div>
	</div>
<br>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
