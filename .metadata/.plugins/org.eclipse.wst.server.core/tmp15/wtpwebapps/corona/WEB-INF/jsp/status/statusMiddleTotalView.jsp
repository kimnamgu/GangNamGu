<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		//총괄
		var totalStatisticList="${totalStatisticList}";
		
		//구글차트 그리기
		google.charts.load('current', {'packages':['line','controls']});
		chartTotalDrow(totalStatisticList); //chartDrow() 실행
		
		chartTotalIchupDrow(totalStatisticList); //chartDrow() 실행
		
		chartTasiguTotalDrow(totalStatisticList); //chartDrow() 실행
	});
	
	//총괄차트 그리기
  function chartTotalDrow(totalStatisticList){
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
      data.addColumn('number'   , '누적 확진자');
      data.addColumn('number'   , '누적 완치자');
      data.addColumn('number'   , '치료중인자');
      data.addColumn('number'   , '사망자');
      //그래프에 표시할 데이터
      var dataRow = [];
  	
  	<c:forEach items="${totalStatisticList}" var="info">
  	var ACCUM_GANGNAM_P =parseInt("${info.ACCUM_GANGNAM_P}");
  	var ACCUM_GANGNAM_CURED_P =parseInt("${info.ACCUM_GANGNAM_CURED_P}"); 
  	var ACCUM_GANGNAM_CURING_P =parseInt("${info.ACCUM_GANGNAM_CURING_P}"); 
  	var ACCUM_GANGNAM_DEATH_P =parseInt("${info.ACCUM_GANGNAM_DEATH_P}"); 
  		
  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ),ACCUM_GANGNAM_P, ACCUM_GANGNAM_CURED_P, ACCUM_GANGNAM_CURING_P,ACCUM_GANGNAM_DEATH_P];
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
	
	
	//총괄 이첩 차트 그리기
  function chartTotalIchupDrow(totalStatisticList){
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
      data.addColumn('number'   , '누적 확진자');
      data.addColumn('number'   , '누적 완치자');
      data.addColumn('number'   , '치료중인자');
      data.addColumn('number'   , '사망자');
      //그래프에 표시할 데이터
      var dataRow = [];
  	
  	<c:forEach items="${totalStatisticList}" var="info">
  	var ACCUM_GANGNAM_ICHUP_P =parseInt("${info.ACCUM_GANGNAM_ICHUP_P}");
  	var ACCUM_GANGNAM_ICHUP_CURED_P =parseInt("${info.ACCUM_GANGNAM_ICHUP_CURED_P}"); 
  	var ACCUM_GANGNAM_ICHUP_CURING_P =parseInt("${info.ACCUM_GANGNAM_ICHUP_CURING_P}");
  	var ACCUM_GANGNAM_ICHUP_DEATH_P =parseInt("${info.ACCUM_GANGNAM_ICHUP_DEATH_P}"); 
  		
  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ),ACCUM_GANGNAM_ICHUP_P, ACCUM_GANGNAM_ICHUP_CURED_P, ACCUM_GANGNAM_ICHUP_CURING_P,ACCUM_GANGNAM_ICHUP_DEATH_P];
	    data.addRow(dataRow); 
  	</c:forEach>
  	

        var chart = new google.visualization.ChartWrapper({
          chartType   : 'LineChart',
          containerId : 'lineChartIchupArea', //라인 차트 생성할 영역
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
          containerId: 'controlsIchupArea',  //control bar를 생성할 영역
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

        var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Ichup_Chart'));
        window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
        dashboard.bind([control], [chart]);
        dashboard.draw(data);

    }
      google.charts.setOnLoadCallback(drawDashboard);

  }
	
	
//타시구 총괄차트 그리기
  function chartTasiguTotalDrow(totalStatisticList){
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
      data.addColumn('number'   , '누적 확진자');
      //그래프에 표시할 데이터
      var dataRow = [];
  	
  	<c:forEach items="${totalStatisticList}" var="info">
  	var ACCUM_TASIGU_P =parseInt("${info.ACCUM_TASIGU_P}"); 
  		
  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ), ACCUM_TASIGU_P];
	    data.addRow(dataRow); 
  	</c:forEach>
  	

        var chart = new google.visualization.ChartWrapper({
          chartType   : 'LineChart',
          containerId : 'lineChartTasiguArea', //라인 차트 생성할 영역
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
          containerId: 'controlsTasiguArea',  //control bar를 생성할 영역
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

        var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Tasigu_Chart'));
        window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
        dashboard.bind([control], [chart]);
        dashboard.draw(data);

    }
      google.charts.setOnLoadCallback(drawDashboard);

  }
	
	
	//확진자 요약 상세
	function go_confirmTotalDtl(TOTAL_GUBUN,TOTAL_CONDITION,TOTAL_ICHUP,TOTAL_DONG_GUBUN,WRITE_DATE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("TOTAL_GUBUN", TOTAL_GUBUN);
		comSubmit.addParam("TOTAL_CONDITION", TOTAL_CONDITION);
		comSubmit.addParam("TOTAL_ICHUP", TOTAL_ICHUP);
		comSubmit.addParam("TOTAL_DONG_GUBUN", TOTAL_DONG_GUBUN);
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
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
	</a>현황 요약 정보
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/status/mainStatusView.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 코로나19 현황</a>
	</div>
	
</div>

	
	<div align="left"  style='padding-left:20px;' id="total_table">
		<!-- 당일 테이블 -->
		<div class="table-responsive m-b-40">
			<table class="table table-bordered table-data3" style='width: 400;float:left;'>
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='4'>${WRITE_DATE}(${totalSum.TODAY_CNT})</th>
					</tr>
					<tr align='center'>
						<th colspan='4' style='CURSOR:hand;font-weight:bold;' onclick="go_confirmTotalDtl('강남구','','','','${WRITE_DATE}')">강남구거주자수(${totalSum.TODAY_GANGNAM_CNT})</th>
					</tr>
					<tr align='center'>
						<th>강남구동</th>
						<th>완치자수(${totalSum.TODAY_GANGNAM_CURED_CNT})</th>
						<th>치료자수(${totalSum.TODAY_GANGNAM_CURING_CNT})</th>
						<th>사망자수(${totalSum.TODAY_GANGNAM_DEATH_CNT})</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectTotalMiddle}" var="item" varStatus="i">
						<tr>
							<td style='CURSOR:hand;color:#a014a0' onclick='go_confirmTotalDtl("강남구","","","${item.ORG_DONG}","${WRITE_DATE}")'>${item.ORG_DONG}(${item.ORG_DONG_TOT})</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","Y","","${item.ORG_DONG}","${WRITE_DATE}")'>${item.CURE_Y_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","N","","${item.ORG_DONG}","${WRITE_DATE}")'>${item.CURE_N_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","DEATH","","${item.ORG_DONG}","${WRITE_DATE}")'>${item.DEATH_CNT}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 300;float:left;'>
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;background-color:#22741C;' onclick="go_confirmTotalDtl('타지역','','','','${WRITE_DATE}')">타구거주자수(${totalSum.TODAY_TASIGU_CNT})</th>
					</tr>
					<tr align='center'>
						<th style='background-color:#22741C;'>타시구</th>
						<th style='background-color:#22741C;'>확진자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectTotalExMiddle}" var="item" varStatus="i">
						<tr>
							<td>${item.ORG_DONG}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("타지역","","","${item.ORG_DONG}","${WRITE_DATE}")'>${item.ORG_DONG_CNT}</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 누적   -->
		
		<!-- 강남구민 -->
		<div class="table-responsive m-b-40">
			<table class="table table-bordered table-data3" style='width: 400;float:left;'>
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#ffc314;'>
						<th colspan='4' style='color:#ff0000;font-weight:bold;' onclick="go_confirmTotalDtl('','','','','')">누적(${totalSum.ACCUM_CNT})</th>
					</tr>
					<tr align='center'>
						<th colspan='4' style='CURSOR:hand;font-weight:bold;'  onclick="go_confirmTotalDtl('강남구','','N','','')">강남구확진판정자수(${totalSum.ACCUM_GANGNAM_CNT})</th>
					</tr>
					<tr align='center'>
						<th>강남구동</th>
						<th>완치자수(${totalSum.ACCUM_GANGNAM_CURED_CNT})</th>
						<th>치료자수(${totalSum.ACCUM_GANGNAM_CURING_CNT})</th>
						<th>사망자수(${totalSum.ACCUM_GANGNAM_DEATH_CNT})</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectAccumTotalMiddle}" var="item" varStatus="i">
						<tr>
							<td style='CURSOR:hand;color:#a014a0' onclick='go_confirmTotalDtl("강남구","","N","${item.ORG_DONG}","")'>${item.ORG_DONG}(${item.ORG_DONG_TOT})</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","Y","N","${item.ORG_DONG}","")'>${item.CURE_Y_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","N","N","${item.ORG_DONG}","")'>${item.CURE_N_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","DEATH","N","${item.ORG_DONG}","")'>${item.DEATH_CNT}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!-- 이첩 -->
			<table class="table table-bordered table-data3" style='width: 400;float:left;'>
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='4' style='CURSOR:hand;font-weight:bold;'  onclick="go_confirmTotalDtl('강남구','','Y','','')">타시구에서 이첩자수(${totalSum.ACCUM_GANGNAM_ICHUP_CNT})</th>
					</tr>
					<tr align='center'>
						<th>강남구동</th>
						<th>완치자수(${totalSum.ACCUM_GANGNAM_ICHUP_CURED_CNT})</th>
						<th>치료자수(${totalSum.ACCUM_GANGNAM_ICHUP_CURING_CNT})</th>
						<th>사망자수(${totalSum.ACCUM_GANGNAM_ICHUP_DEATH_CNT})</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectAccumTotalIchupMiddle}" var="item" varStatus="i">
						<tr>
							<td style='CURSOR:hand;color:#a014a0' onclick='go_confirmTotalDtl("강남구","","Y","${item.ORG_DONG}","")'>${item.ORG_DONG}(${item.ORG_DONG_TOT})</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","Y","Y","${item.ORG_DONG}","")'>${item.CURE_Y_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","N","Y","${item.ORG_DONG}","")'>${item.CURE_N_CNT}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("강남구","DEATH","Y","${item.ORG_DONG}","")'>${item.DEATH_CNT}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!-- 타시구민 -->
			<table class="table table-bordered table-data3" style='width: 300;float:left;'>
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;background-color:#22741C;'  onclick="go_confirmTotalDtl('타지역','','','','')">타시구거주자수(${totalSum.ACCUM_TASIGU_CNT})</th>
					</tr>
					<tr align='center'>
						<th style='background-color:#22741C;'>타시구</th>
						<th style='background-color:#22741C;'>확진자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectAccumTotalExMiddle}" var="item" varStatus="i">
						<tr>
							<td>${item.ORG_DONG}</td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmTotalDtl("타지역","","","${item.ORG_DONG}","")'>${item.ORG_DONG_CNT}</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	

	<br>
	
	
	
	<!-- 그래프 -->
	<div style="width:100%;height:450px;padding-left:40px;" id="chartArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[강남구민 확진판정자 변동 현황]</h4>
 
    	<div id="Line_Controls_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea" style="height:100px;"></div>
        </div>
	</div>
	
	<!-- 그래프 -->
	<div style="width:100%;height:450px;padding-left:40px;" id="chartIchupArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[타시구 이첩자 변동 현황]</h4>
 
    	<div id="Line_Controls_Ichup_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartIchupArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsIchupArea" style="height:100px;"></div>
        </div>
	</div>
	
	
	<!-- 그래프 -->
	<div style="width:100%;height:450px;padding-left:40px;" id="chartTasiguArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[타시구민 확진자 변동 현황]</h4>
 
    	<div id="Line_Controls_Tasigu_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartTasiguArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsTasiguArea" style="height:100px;"></div>
        </div>
	</div>
<br>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
