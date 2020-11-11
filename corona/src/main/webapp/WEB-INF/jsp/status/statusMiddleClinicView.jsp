<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		var clinicStatisticList="${clinicStatisticList}";
	
		//구글차트 그리기
		google.charts.load('current', {'packages':['line','controls']});
		chartClinicDrow(clinicStatisticList); //chartDrow() 실행
	});
	
	
	function fn_updateClinicDae(){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/updateClinicDae.do' />");
		comAjax.addParam("SUSPICION_GUBUN", $("#SUSPICION_GUBUN").val());
		comAjax.addParam("BF_DAE", $("#BF_DAE").val());
		comAjax.addParam("AF_DAE", $("#AF_DAE").val());
		
		comAjax.setCallback("fn_updateClinicDaeCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_updateClinicDaeCallback(){
       alert("저장되었습니다.");
	}
	
	function fn_updateClinicSo(){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/updateClinicSo.do' />");
		comAjax.addParam("SUSPICION_GUBUN", $("#SUSPICION_GUBUN").val());
		comAjax.addParam("BF_SO", $("#BF_SO").val());
		comAjax.addParam("AF_SO", $("#AF_SO").val());
		
		comAjax.setCallback("fn_updateClinicSoCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_updateClinicDaeCallback(){
       alert("저장되었습니다.");
	}
	
	

	//총괄차트 그리기
  function chartClinicDrow(totalStatisticList){
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
      data.addColumn('number'   , '강남구');
      data.addColumn('number'   , '타시구');
      data.addColumn('number'   , '해외');
      //그래프에 표시할 데이터
      var dataRow = [];
  	
  	<c:forEach items="${clinicStatisticList}" var="info">
  	var CLINIC_TOTAL =parseInt("${info.CLINIC_TOTAL}");
  	var CLINIC_OVERSEA =parseInt("${info.CLINIC_OVERSEA}"); 
  	var CLINIC_GANGNAM =parseInt("${info.CLINIC_GANGNAM}"); 
  	var CLINIC_TASIGU =parseInt("${info.CLINIC_TASIGU}"); 
  		
  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ),CLINIC_TOTAL, CLINIC_OVERSEA, CLINIC_GANGNAM, CLINIC_TASIGU];
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
	
	//선별진료소 양성자 dtl
	function go_clinicConfirmDtl(CLINIC_CONFIRM_DEPART,CLINIC_CONFIRM_SUSPICION_GUBUN,CLINIC_CONFIRM_SUSPICION_DAE,CLINIC_CONFIRM_SUSPICION_SO,WRITE_DATE,CONFIRM_ISO){
		
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("CLINIC_CONFIRM_DEPART", CLINIC_CONFIRM_DEPART);
		comSubmit.addParam("CLINIC_CONFIRM_SUSPICION_GUBUN", CLINIC_CONFIRM_SUSPICION_GUBUN);
		comSubmit.addParam("CLINIC_CONFIRM_SUSPICION_DAE", CLINIC_CONFIRM_SUSPICION_DAE);
		comSubmit.addParam("CLINIC_CONFIRM_SUSPICION_SO", CLINIC_CONFIRM_SUSPICION_SO);
		comSubmit.addParam("CONFIRM_ISO", CONFIRM_ISO);
		
		comSubmit.addParam("WRITE_DATE", WRITE_DATE);
		comSubmit.submit();
	}
	
	//상담상세
	function go_clinicDtl(CLINIC_DEPART,CLINIC_SUSPICION_GUBUN,CLINIC_SUSPICION_DAE,CLINIC_SUSPICION_SO,WRITE_DATE,ISO){
		
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("CLINIC_DEPART", CLINIC_DEPART);
		comSubmit.addParam("CLINIC_SUSPICION_GUBUN", CLINIC_SUSPICION_GUBUN);
		comSubmit.addParam("CLINIC_SUSPICION_DAE", CLINIC_SUSPICION_DAE);
		comSubmit.addParam("CLINIC_SUSPICION_SO", CLINIC_SUSPICION_SO);
		comSubmit.addParam("ISO", ISO);
		
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

	<!-- <div align="left"  style='overflow:scroll;padding-left:20px;width:1200px;height:2000px;float:left;margin-bottom:30px;' id="clinic_table"> -->
	<div align="left"  style='padding-left:20px;margin-bottom:30px;' id="clinic_table">
		<!-- 당일 테이블 -->
		<div class="table-responsive m-b-40">
			<table class="table table-bordered table-data3" style='width: 310;float:left;' id="clinic_table_gangnam">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='2' style='cursor:hand;' onclick="go_clinicDtl('TODAY','','','','${SELDATE}','ISO_EX')">신규(${sumMap.CUR_CLINIC_TOTAL})<br>${SHOWDATE}</th>
						<th colspan='2' style='cursor:hand;' onclick="go_clinicConfirmDtl('TODAY','','','','${SELDATE}','ISO_EX')">양성자수전체(${sumMap.CUR_CONFIRM_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_clinicDtl('TODAY','강남구','','','${SELDATE}','ISO_EX')">강남구거주자(${sumMap.CUR_CLINIC_GANGNAM_TOTAL})</th>
						<th rowspan=2 style='CURSOR:hand;background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','강남구','','','${SELDATE}','ISO_EX')">양성자수(${sumMap.CUR_CONFIRM_GANGNAM})</th>  
					</tr>
					<tr align='center'>
						<th width="30%">강남구동</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicGangnamMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','강남구','${item.SUSPICION_DAE}','','${SELDATE}','ISO_EX')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','강남구','${item.SUSPICION_DAE}','','${SELDATE}','ISO_EX')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_tasigu">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='3' style='CURSOR:hand;font-weight:bold;' onclick="go_clinicDtl('TODAY','타지역','','','${SELDATE}','ISO_EX')">타지역거주자(${sumMap.CUR_CLINIC_TASIGU_TOTAL})</th>
						<th width="30%" rowspan=2 style='CURSOR:hand;background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','타지역','','','${SELDATE}','ISO_EX')">양성자수(${sumMap.CUR_CONFIRM_TASIGU})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<th width="20%">소분류</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicTasiguMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<td><c:out value="${item.SUSPICION_SO}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','타지역','${item.SUSPICION_DAE}','${item.SUSPICION_SO}','${SELDATE}','ISO_EX')"><c:out value="${item.SUSPICION_SO_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','타지역','${item.SUSPICION_DAE}','${item.SUSPICION_SO}','${SELDATE}','ISO_EX')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='2' style='font-weight:bold;'>해외입국자(${sumMap.CUR_CLINIC_OVERSEA_TOTAL})</th>
						<th width="30%" rowspan=2 style='background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','해외','','','${SELDATE}','ISO_EX')">양성자수(${sumMap.CUR_CONFIRM_OVERSEA})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<!-- <th width="20%">소분류</th> -->
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicOverseaMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<%-- <td><c:out value="${item.SUSPICION_SO}" /></td> --%>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','해외','${item.SUSPICION_DAE}','${SELDATE}','ISO_EX')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td  style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','해외','${item.SUSPICION_DAE}','','${SELDATE}','ISO_EX')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 당일 테이블  - 격리해제자-->
		<div class="table-responsive m-b-40">
			<table class="table table-bordered table-data3" style='width: 310;float:left;' id="clinic_table_gangnam">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='2' onclick="go_clinicDtl('TODAY','','','','${SELDATE}','ISO')">격리해제 검사자(${sumMap.CUR_CLINIC_ISO_TOTAL})<br>${SHOWDATE}</th>
						<th colspan='2' onclick="go_clinicConfirmDtl('TODAY','','','','${SELDATE}','ISO')">양성자수전체(${sumMap.CUR_CONFIRM_ISO_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_clinicDtl('TODAY','강남구','','','${SELDATE}','ISO')">강남구거주자(${sumMap.CUR_CLINIC_ISO_GANGNAM_TOTAL})</th>
						<th rowspan=2 style='background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','강남구','','','${SELDATE}','ISO')">양성자수(${sumMap.CUR_CONFIRM_ISO_GANGNAM})</th>  
					</tr>
					<tr align='center'>
						<th width="30%">강남구동</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicGangnamIsoMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','강남구','','${item.SUSPICION_DAE}','${SELDATE}','ISO')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','강남구','${item.SUSPICION_DAE}','','${SELDATE}','ISO')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_tasigu">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='3' style='CURSOR:hand;font-weight:bold;' onclick="go_clinicDtl('TODAY','타지역','','','${SELDATE}','ISO')">타지역거주자(${sumMap.CUR_CLINIC_ISO_TASIGU_TOTAL})</th>
						<th width="30%" rowspan=2 style='CURSOR:hand;background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','타지역','','','${SELDATE}','ISO')">양성자수(${sumMap.CUR_CONFIRM_ISO_TASIGU})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<th width="20%">소분류</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicTasiguIsoMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<td><c:out value="${item.SUSPICION_SO}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','타지역','${item.SUSPICION_DAE}','${item.SUSPICION_SO}','${SELDATE}','ISO')"><c:out value="${item.SUSPICION_SO_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','타지역','${item.SUSPICION_DAE}','${item.SUSPICION_SO}','${SELDATE}','ISO')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='2' style='font-weight:bold;'>해외입국자(${sumMap.CUR_CLINIC_ISO_OVERSEA_TOTAL})</th>
						<th width="30%" rowspan=2 style='background-color:#29088A;' onclick="go_clinicConfirmDtl('TODAY','해외','','','${SELDATE}','ISO')">양성자수(${sumMap.CUR_CONFIRM_ISO_OVERSEA})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<!-- <th width="20%">소분류</th> -->
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicOverseaIsoMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<%-- <td><c:out value="${item.SUSPICION_SO}" /></td> --%>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('TODAY','해외','${item.SUSPICION_DAE}','${SELDATE}','ISO')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','해외','${item.SUSPICION_DAE}','','${SELDATE}','ISO')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 누적테이블 -->
		<div class="table-responsive m-b-40">
			<table class="table table-bordered table-data3" style='width: 310;float:left;' id="clinic_table_gangnam">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#ffc314;'>
						<th colspan='2' style='color:#ff0000;CURSOR:hand;font-weight:bold;' onclick="go_clinicDtl('ACCUM','','','','','')">누적(${sumMap.CUR_ACCUM_CLINIC_TOTAL})</th>
						<th colspan='2' style='color:#ff0000;CURSOR:hand;font-weight:bold;' onclick="go_clinicConfirmDtl('ACCUM','','','','${SELDATE}','')">양성자수전체(${sumMap.CUR_CONFIRM_ACCUM_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;'  onclick="go_clinicDtl('ACCUM','강남구','','','','')">강남구거주자(${sumMap.CUR_ACCUM_CLINIC_GANGNAM_TOTAL})</th>
						<th rowspan=2 style='CURSOR:hand;background-color:#29088A;' onclick="go_clinicConfirmDtl('ACCUM','강남구','','','${SELDATE}','')">양성자수(${sumMap.CUR_CONFIRM_ACCUM_GANGNAM})</th>  
					</tr>
					<tr align='center'>
						<th width="30%">강남구동</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectClinicGangnamAccumMiddleList}" var="item" varStatus="i">
						<tr>
							<td><c:out value="${item.SUSPICION_DAE}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('ACCUM','강남구','${item.SUSPICION_DAE}','','','')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('ACCUM','강남구','${item.SUSPICION_DAE}','','','')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_tasigu">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='3' style='CURSOR:hand;font-weight:bold;'  onclick="go_clinicDtl('ACCUM','타지역','','','','')">타지역거주자(${sumMap.CUR_ACCUM_CLINIC_TASIGU_TOTAL})</th>
						<th width="30%" rowspan=2 style='background-color:#29088A;' onclick="go_clinicConfirmDtl('ACCUM','타지역','','','','')">양성자수(${sumMap.CUR_CONFIRM_ACCUM_TASIGU})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<th width="20%">소분류</th>
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="dae_notice" value="" />
					<c:forEach items="${selectClinicTasiguAccumMiddleList}" var="item" varStatus="i">
						<tr>
						
						<c:choose>
							<c:when  test="${dae_notice != item.SUSPICION_DAE}">
							    <c:set var="dae_notice" value="${item.SUSPICION_DAE}" />
							    <td><c:out value="${item.SUSPICION_DAE}" /></td>
							</c:when >
							
							<c:when  test="${dae_notice eq item.SUSPICION_DAE}">
							    <td></td>
							</c:when >
						</c:choose>
							<td><c:out value="${item.SUSPICION_SO}" /></td>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('ACCUM','타지역','${item.SUSPICION_DAE}','${item.SUSPICION_SO}','','')"><c:out value="${item.SUSPICION_SO_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('TODAY','타지역','','','${SELDATE}','ISO')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 370;float:left;' id="clinic_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='4'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th width="70%" colspan='2' style='font-weight:bold;'>해외입국자(${sumMap.CUR_ACCUM_CLINIC_OVERSEA_TOTAL})</th>
						<th width="30%" rowspan=2 style='CURSOR:hand;background-color:#29088A;' onclick="go_clinicConfirmDtl('ACCUM','해외','','','','')">양성자수(${sumMap.CUR_CONFIRM_ACCUM_OVERSEA})</th>  
					</tr>
					<tr align='center'>
						<th width="20%">대분류</th>
						<!-- <th width="20%">소분류</th> -->
						<th width="30%">검사자수</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="dae_notice" value="" />
					<c:forEach items="${selectClinicOverseaAccumMiddleList}" var="item" varStatus="i">
						<tr>
						<c:choose>
							<c:when  test="${dae_notice != item.SUSPICION_DAE}">
							    <c:set var="dae_notice" value="${item.SUSPICION_DAE}" />
							    <td><c:out value="${item.SUSPICION_DAE}" /></td>
							</c:when >
							
							<c:when  test="${dae_notice eq item.SUSPICION_DAE}">
							    <td></td>
							</c:when >
						</c:choose>

							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicDtl('ACCUM','해외','${item.SUSPICION_DAE}','','')"><c:out value="${item.SUSPICION_DAE_CNT}" /></td> 
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_clinicConfirmDtl('ACCUM','해외','${item.SUSPICION_DAE}','','','')"><c:out value="${item.CONFIRM_CHECK_SUM}" /></td>  
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
<!-- 	<div style="float:left;">
			<div class="form-group" style="width:660;">
				<div class="input-group" style="margin:10px;">
					<div class="input-group-addon" style="width:250px;">구분(강남구,타지역,해외)</div>
					<input type="text" id="SUSPICION_GUBUN" name="SUSPICION_GUBUN" value="" class="form-control">
				</div>
				<div class="input-group" style="margin:10px;">
					<div class="input-group-addon" style="width:80px;">대분류</div>
					<input type="text" id="BF_DAE" name="BF_DAE" value="" class="form-control">
					<i class="fa fa-arrow-right" style="margin:10px;"></i>
					<input type="text" id="AF_DAE" name="AF_DAE" value="" class="form-control">
					<a href='javascript:void(0);' onclick='fn_updateClinicDae()' class="btn btn-warning"><i class="fa fa-check"></i> 저장</a>
				</div>
				<div class="input-group" style="margin:10px;">
					<div class="input-group-addon" style="width:80px;">소분류</div>
					<input type="text" id="BF_SO" name="BF_SO" value="" class="form-control">
					<i class="fa fa-arrow-right" style="margin:10px;"></i>
					<input type="text" id="AF_SO" name="AF_SO" value="" class="form-control">
					<a href='javascript:void(0);' onclick='fn_updateClinicSo()' class="btn btn-warning"><i class="fa fa-check"></i> 저장</a>
				</div>
			</div>
	</div> -->

	<!-- 그래프 -->
	<div style="display:inline-block;width:100%;height:500px;padding-left:40px;" id="chartArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[선별진료소 일일변동 현황]</h4>
 
    	<div id="Line_Controls_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea"></div>
        </div>
	</div>

<br>
<br>
<br>
<br>
	
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>