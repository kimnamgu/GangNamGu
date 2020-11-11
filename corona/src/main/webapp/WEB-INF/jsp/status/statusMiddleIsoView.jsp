<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		
		//자가격리자
		var isoStatisticList="${isoStatisticList}";
		
		//구글차트 그리기
		google.charts.load('current', {'packages':['line','controls']});
		chartIsoDrow(isoStatisticList); //chartDrow() 실행
		


		
	});
	
	
	//자가격리자차트 그리기
	  function chartIsoDrow(isoStatisticList){
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
	      data.addColumn('number'   , '자가격리중');
	      //그래프에 표시할 데이터
	      var dataRow = [];
	  	
	  	<c:forEach items="${isoStatisticList}" var="info">
	  	var DUE_ISO =parseInt("${info.DUE_ISO}"); 
	  		
	  	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ), DUE_ISO];
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
                       	             				gridlines:{count:10},
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
	
	//자가격리자 상세
	function go_isoDtl(ISO_GUBUN,ISO_TYPE,ISO_DONG,SELDATE){
		
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("ISO_GUBUN", ISO_GUBUN);
		comSubmit.addParam("ISO_TYPE", ISO_TYPE);
		comSubmit.addParam("ISO_DONG", ISO_DONG);
		comSubmit.addParam("SELDATE", SELDATE);
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

	<div align="left"  style='overflow:scroll;padding-left:20px;' id="iso_table">
		<!-- 당일 테이블 -->
		<div class="table-responsive m-b-40" style='width:1900;'>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="cur_iso_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='2'>${SELDATE}(${sumMap.CUR_ISO_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_isoDtl('CUR','OVERSEA','','${SELDATE}')">해외입국자(${sumMap.CUR_OVERSEA_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoOverseaMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('CUR','OVERSEA','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="cur_iso_table_dom">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_isoDtl('CUR','DOM','','${SELDATE}')">국내접촉자(${sumMap.CUR_DOM_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoDomMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('CUR','DOM','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 자가격리 -->
		<div class="table-responsive m-b-40" style='width:1900;'>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="due_iso_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='2'>자가격리중 인자(${sumMap.DUE_ISO_TOTAL})<br>(14일간)</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_isoDtl('DUE','OVERSEA','','${SELDATE}')">해외입국자(${sumMap.DUE_OVERSEA_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoOverseaDueMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('DUE','OVERSEA','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="due_iso_table_dom">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이<br>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_isoDtl('DUE','DOM','','${SELDATE}')">국내접촉자(${sumMap.DUE_DOM_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoDomDueMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('DUE','DOM','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 금일 해제 -->
		<div class="table-responsive m-b-40" style='width:1900;'>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="free_iso_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#088A85;'>
						<th colspan='2'>금일해제(${sumMap.FREE_ISO_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;' onclick="go_isoDtl('FREE','OVERSEA','','${SELDATE}')">해외입국자(${sumMap.FREE_OVERSEA_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoOverseaFreeMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('FREE','OVERSEA','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="cur_iso_table_dom">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;'  onclick="go_isoDtl('FREE','DOM','','${SELDATE}')">국내접촉자(${sumMap.FREE_DOM_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoDomFreeMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('FREE','DOM','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 누적 해제  -->
		<div class="table-responsive m-b-40" style='width:1900;'>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="iso_table_oversea">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:#ffc314;'>
						<th colspan='2' style='color:#ff0000;font-weight:bold;'>해제 누적인원(${sumMap.ISO_TOTAL})</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;'  onclick="go_isoDtl('ACCUM','OVERSEA','','${SELDATE}')">해외입국자(${sumMap.OVERSEA_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoOverseaAccumMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							<td style='CURSOR:hand;color:#0099FF;text-align:center;'  onclick="go_isoDtl('ACCUM','OVERSEA','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-bordered table-data3" style='width: 300;float:left;' id="cur_iso_table_dom">
				<thead style='background-color:#0B2161;'>
					<tr align='center' style='background-color:white;'>
						<th colspan='2'>높이</th>
					</tr>
					<tr align='center'>
						<th colspan='2' style='CURSOR:hand;font-weight:bold;'  onclick="go_isoDtl('ACCUM','DOM','','${SELDATE}')">국내접촉자(${sumMap.DOM_SUM})</th>
					</tr>
					<tr align='center'>
						<th width="60%">강남구동</th>
						<th width="40%">자가격리자수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${selectIsoDomAccumMiddleList}" var="item" varStatus="i">
						<tr>
							<c:if test="${item.SELF_ISO_AREA_DONG == '오장동' }">
							    <td>스카이파크 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG == '장안동' }">
							    <td>더 리센츠 호텔</td>
							</c:if>
							
							<c:if test="${item.SELF_ISO_AREA_DONG != '오장동' && item.SELF_ISO_AREA_DONG != '장안동'}">
							    <td><c:out value="${item.SELF_ISO_AREA_DONG}" /></td>
							</c:if>
							
							<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick="go_isoDtl('ACCUM','DOM','${item.SELF_ISO_AREA_DONG}','${SELDATE}')"><c:out value="${item.ISO_CNT}" /></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		
	</div>
	
	
	<br>
	
		<!-- 지도 -->
	<div style="display:inline-block;width:600px;height:500px;margin-left:100px;margin-bottm:100px;" id="mapArea">
	<h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[강남구 자가격리자 위치정보]</h4><br>
	<div id="map" style="width:600px;height:500px;"></div>
	</div>
		
	<br><br><br><br><br>
	<!-- 그래프 -->
	<div style="display:inline-block;width:100%;padding-left:40px;" id="chartArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[강남구 자가격리자 일일변동 현황]</h4>
 
    	<div id="Line_Controls_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea"></div>
        </div>
	</div>

<style>
.label {margin-bottom: 96px;}
.label * {display: inline-block;vertical-align: top;}
.label .left {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center {background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 12px;line-height: 24px;}
.label .right {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>


	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1c74f5b5abf3daa819be94239c175608&libraries=services,clusterer,drawing"></script>
	<!-- 자가격리자 -->
	<script>
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
		center: new kakao.maps.LatLng(37.513123, 127.042144), // 지도의 중심좌표
        level: 6 // 지도의 확대 레벨
    };

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	<c:forEach items="${isoMaplist}" var="info">
		pick_iso_area("${info.NAME}","${info.SEX}","${info.SELF_ISO_AREA_DONG}","${info.SELF_ISO_AREA_ADDRESS}","${info.SELF_ISO_AREA_ADDRESS_DTL}","${info.FREE_DATE}","${info.DAMDANG_CLASS}","${info.DAMDANG_NAME}","${info.DAMDANG_CALL}");
	</c:forEach>
	



	
	
	//확진지 정보 표시
	function pick_iso_area(NAME,SEX,SELF_ISO_AREA_DONG,SELF_ISO_AREA_ADDRESS,SELF_ISO_AREA_ADDRESS_DTL,FREE_DATE,DAMDANG_CLASS,DAMDANG_NAME,DAMDANG_CALL){
		
		var geocoder = new kakao.maps.services.Geocoder();
		
		// 마커 이미지의 이미지 크기 입니다

		// 마커 이미지를 생성합니다    
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(SELF_ISO_AREA_ADDRESS, function(result, status) {
			// 정상적으로 검색이 완료됐으면 
			if (status === kakao.maps.services.Status.OK) {
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
				
				var imageSrc = "/corona/images/common/100.gif";
				/* var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markers_sprites3.png"; */
				// 마커 이미지의 이미지 크기 입니다
				var imageSize = new kakao.maps.Size(10, 10);

				// 마커 이미지를 생성합니다    
				var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
				

				// 결과값으로 받은 위치를 마커로 표시합니다
				var marker = new kakao.maps.Marker({
					map : map,
					position : coords,
					image : markerImage,
					clickable: true
				});
				
				// 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
				var iwContent = '<div style="margin:15px;height:200;width:300px;">' +
									'<font color="black">이름 : </font>'+NAME+'</font>' + 
									'<br><font color="black">성별 : </font>'+SEX +
									'<br><font color="black">자가격리동 : </font><font color="#298A08">'+SELF_ISO_AREA_DONG+'</font>' +
									'<br><font color="black">자가격리주소 : </font><font color="#0431B4">'+SELF_ISO_AREA_ADDRESS+' '+ SELF_ISO_AREA_ADDRESS_DTL+'</font>'+
									'<br><font color="black">격리해제일 : </font><font color="red">'+FREE_DATE+'</font>';
									
									if(DAMDANG_NAME != 'undefined'){
										iwContent += '<br><font color="black">담당자명 : '+DAMDANG_CLASS+' '+DAMDANG_NAME;
										
										if(DAMDANG_CALL != ''){
											iwContent += '('+DAMDANG_CALL+')';
										}
										
										iwContent += '</font>';
										
									}else{
										iwContent += '<br><font color="black">담당자명 : 미배정</font>';
									}
									
					iwContent += '</div>'; 
								// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
								
								var  iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
				  
				// 인포윈도우를 생성합니다
				var infowindow = new kakao.maps.InfoWindow({
				    content : iwContent,
				    removable : iwRemoveable
				});
				// 마커에 클릭이벤트를 등록합니다
				kakao.maps.event.addListener(marker, 'click', function() {
				      // 마커 위에 인포윈도우를 표시합니다
				      infowindow.open(map, marker);  
				});
			}
		});
		
	}
	</script>


<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
