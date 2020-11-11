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
		
		//확진자
		var confirmAccumStatisticList="${confirmAccumStatisticList}";
		var confirmStatisticList="${confirmStatisticList}";
		var SHOW_LIST="${SHOW_LIST}";
		
		//개체 수에 따른 width 조정
		$('.table-responsive').width("${SHOW_LIST_LENGTH}"*200+400);
		
		<c:forEach var="i" items="${confirmSumList}"> // ${map} 에 앞부분에 sessionScope 생략가능하기 때문에 생략했음.
			$('#'+"${i.key}").text("${i.value}");
		</c:forEach>
			
		//구글차트 그리기
		google.charts.load('current', {'packages':['line','controls']});
		chartConfirmAccumDrow(confirmAccumStatisticList); //chartDrow() 실행
		chartDrow(confirmStatisticList); //chartDrow() 실행
  			
		// 구분자로 구성된 문자열 
		var arr = "${confirmShowList}".split(",");
  		 	
		// 확진
  		fn_selectConfirmMiddleOversea(WRITE_DATE,'');
		arr.forEach(function(item,index,arr2){
			fn_selectConfirmAllMiddle(index,arr2[index],WRITE_DATE,'');
		});
		fn_selectConfirmAllMiddle('etc','etc',WRITE_DATE,'');
		
		//병원 
 		fn_selectConfirmMiddleHospital('oversea','',WRITE_DATE,'');
		arr.forEach(function(item,index,arr2){
			fn_selectConfirmMiddleHospital(index,arr2[index],WRITE_DATE,'');
		});
		fn_selectConfirmMiddleHospital('etc','etc',WRITE_DATE,'');
		
		
		//확진 누적
  		fn_selectConfirmMiddleOversea(WRITE_DATE,'accum');
		arr.forEach(function(item,index,arr2){
			fn_selectConfirmAllMiddle(index,arr2[index],WRITE_DATE,'accum');
		});
		fn_selectConfirmAllMiddle('etc','etc',WRITE_DATE,'accum'); 

		//병원누적
		 fn_selectConfirmMiddleHospital('oversea','',WRITE_DATE,'accum');
 		arr.forEach(function(item,index,arr2){
			fn_selectConfirmMiddleHospital(index,arr2[index],WRITE_DATE,'accum');
		});
		 fn_selectConfirmMiddleHospital('etc','etc',WRITE_DATE,'accum'); 

	});
	
//확진자 누적차트 그리기
  function chartConfirmAccumDrow(confirmAccumStatisticList){
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
        data.addColumn('number'   , '강남구');

        //그래프에 표시할 데이터
        var dataRow = [];
    	<c:forEach items="${confirmAccumStatisticList}" var="info">
    	var CONFIRM_ACCUM_GANGNAM =parseInt("${info.CONFIRM_ACCUM_GANGNAM}"); 
    		
    	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ), CONFIRM_ACCUM_GANGNAM];
	    data.addRow(dataRow);
    	</c:forEach>

        var chart = new google.visualization.ChartWrapper({
          chartType   : 'LineChart',
          containerId : 'lineChartAccumArea', //라인 차트 생성할 영역
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
          containerId: 'controlsAccumArea',  //control bar를 생성할 영역
          options: {
              ui:{
                    chartType: 'LineChart',
                    chartOptions: {
                    chartArea: {'width': '60%','height' : '40'},
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
	
	
	//확진자 차트 그리기
  function chartDrow(statisticsList){
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
        data.addColumn('number'   , '강남구');
        //그래프에 표시할 데이터
        var dataRow = [];
    	
    	<c:forEach items="${confirmStatisticList}" var="info">
    	var GANGNAM_CNT =parseInt("${info.GANGNAM_CNT}"); 
    		
    	dataRow = [new Date("${info.ST_DAY}".substring(0,4), "${info.ST_DAY}".substring(4,6)-1, "${info.ST_DAY}".substring(6,8) ,'00' ), GANGNAM_CNT];
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
	
	
	
	
	//확진자 요약 상세
	function go_confirmDtl(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION){
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
	
	
	//확진자 요약 상세
	function go_confirmJipdanDtl(JIPDAN_DIVIDE,JIPDAN_INFECT_DAE){
 		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/status/statusTrackingView.do' />");
		comSubmit.addParam("JIPDAN_GUBUN", "JIPDAN");
		comSubmit.addParam("JIPDAN_DIVIDE", JIPDAN_DIVIDE);
		comSubmit.addParam("JIPDAN_INFECT_DAE", JIPDAN_INFECT_DAE);
		comSubmit.submit();
	}
	
	//확진자 해외 중간 요약
	function fn_selectConfirmMiddleOversea(WRITE_DATE,DEPART){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectConfirmMiddleOversea.do' />");
		comAjax.addParam("WRITE_DATE", WRITE_DATE);
		comAjax.addParam("DEPART", DEPART);
		comAjax.setCallback("fn_selectConfirmMiddleOverseaCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectConfirmMiddleOverseaCallback(data){
		var body = "";
		if(data.DEPART != 'accum'){
			body = $("#confirm_table_oversea");
		}else{
			body = $("#accum_confirm_table_oversea");
		}
			
		var str = "";
		var before_so = "";
		
		str += "<tbody>";
		
		$.each(data.list, function(key, value){
			
			if(typeof value.INFECT_DAE !='undefined' && typeof value.INFECT_SO !='undefined'){
				before_so += "<a href='#this'  onclick='go_confirmDtl(\""+value.INFECT_GUBUN+"\",\""+value.INFECT_DAE+"\", \""+value.INFECT_SO+"\",\""+value.WRITE_DATE+"\", \"\", \"\")'>" + value.INFECT_SO +"("+value.INFECT_SO_CNT+")</a><br>";
			}
			
			
			if(typeof value.INFECT_DAE !='undefined' && typeof value.INFECT_SO =='undefined'){
				str += 
					"<tr>                                    " +
					"	<td style='color:#a014a0;CURSOR:hand;' onclick='go_confirmDtl(\""+value.INFECT_GUBUN+"\",\""+value.INFECT_DAE+"\", \"\",\""+value.WRITE_DATE+"\"), \"\", \"\"'>"+value.INFECT_DAE+"("+value.INFECT_SO_CNT+")</td>                       " +
					"	<td>"+before_so+"</td>  " +
					"</tr>                                   "; 
				before_so = "";	
			}
		}); 
		
		str += "</tbody>";
		
		body.append(str);
	}
	
	//확진자 날짜 선택 요약
	function fn_selectConfirmAllMiddle(INDEX,INFECT_DAE,WRITE_DATE,DEPART){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectConfirmMiddle.do' />");
		comAjax.addParam("INDEX",INDEX);
		comAjax.addParam("INFECT_DAE",INFECT_DAE);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);
		comAjax.addParam("DEPART", DEPART);	
		comAjax.setCallback("fn_selectConfirmAllMiddleCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectConfirmAllMiddleCallback(data){
		
		var body;
		if(data.DEPART != 'accum'){
			body = $("#confirm_table_" + data.INDEX);
		}else{
			body = $("#accum_confirm_table_" + data.INDEX);
		}
		
		
		var body_child;
		var body_title;
		var title_append;
		var recordcnt = $("#RECORD_COUNT").val();
		
			
			var str = "";
			var even_color = "";

			str += "<tbody>";
		    	   
			$.each(data.list, function(key, value){
				if(typeof value.INFECT_DAE !='undefined' && typeof value.INFECT_SO !='undefined'){
					str += 
						"<tr>                                    " +
						"	<td>"+NvlStr2(value.INFECT_SO,'미입력')+"</td>                       " +
						"	<td style='color:#0099FF;CURSOR:hand;text-align:center;' onclick='go_confirmDtl(\""+value.INFECT_GUBUN+"\",\""+value.INFECT_DAE+"\", \""+NvlStr2(value.INFECT_SO,'미입력')+"\",\""+value.WRITE_DATE+"\"), \"\", \"\"'>"+value.INFECT_SO_CNT+"</td>                       " +
						"</tr>                                   "; 
					before_so = "";	
				}
			}); 
			
			str += "</tbody>";
			
			
			;
			
			body.append(str);
	}
	
 	
	//확진자 병원정보 요약
	function fn_selectConfirmMiddleHospital(INDEX,INFECT_DAE,WRITE_DATE,DEPART){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectConfirmMiddleHospital.do' />");
		comAjax.addParam("INDEX",INDEX);
		comAjax.addParam("INFECT_DAE",INFECT_DAE);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);
		comAjax.addParam("DEPART", DEPART);	
		comAjax.setCallback("fn_selectConfirmMiddleHospitalCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectConfirmMiddleHospitalCallback(data){
		var body;
		if(data.DEPART != 'accum'){
			body = $("#confirm_table_hos_" + data.INDEX);
		}else{
			body = $("#accum_confirm_table_hos_" + data.INDEX);
		}
		
		var INFECT_GUBUN = "";
		var INFECT_DAE = "";
		
		if(data.INDEX == 'oversea'){
			INFECT_GUBUN = "해외";
		}else{
			INFECT_GUBUN = "";
		}
		
		if(data.INFECT_DAE == 'etc'){
			INFECT_DAE = "other";
		}else{
			INFECT_DAE = data.INFECT_DAE;
		}
		
			
		var str = "";
		str += "<tbody>";
		
		$.each(data.list, function(key, value){
			
				str += 
					"<tr>                                    " +
					"	<td>"+NvlStr2(value.HOSPITAL,'미입력')+"</td>                       " +
					"	<td style='color:#0099FF;CURSOR:hand;' align='center' onclick='go_confirmDtl(\""+INFECT_GUBUN+"\",\""+INFECT_DAE+"\", \"\",\""+value.WRITE_DATE+"\", \"병원\", \""+NvlStr2(value.HOSPITAL,'미입력')+"\")'>"+value.HOSPITAL_CNT+"</td>  " +
					
					"</tr>                                   "; 
		}); 
		
			str += "</tbody>";
			
		body.append(str);
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
	<div align="left"  style='overflow:scroll;padding-left:20px;' id="confirm_table">
		<!-- 당일 테이블 -->
		<div class="table-responsive m-b-40" style='width:2500;' id="confirm_table_day">
				<!-- fix 해외 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="confirm_table_oversea">
					<thead style='background-color: #0B2161;'>
						<tr align='center'>
							<th colspan='2' id='title_day_total' style='background-color:gray;'>${WRITE_DATE_SHOW}(<span id="CONFIRM_DAY_TOTAL">0</span>)</th>
						</tr>
						<tr align='center' id="title_oversea">
							<th colspan='2' style='CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("해외","","","${WRITE_DATE}","", "")'>해외(<span id="CONFIRM_DAY_OVERSEA">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">대분류</th>
							<th width="40%">확진자수</th>
						</tr>
					</thead>
				</table>
				
				<!-- 실시간 변동 테이블-->
				<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
					<table class='table table-bordered table-data3' style='width: 200;float:left;' id='confirm_table_${i.index}'>
						<c:if test="${i.index%2 == 0 }">
						    <thead style='background-color: #08298A;'>
						</c:if>
						
						<c:if test="${i.index%2 != 0 }">
						    <thead style='background-color: #0B2161;'>
						</c:if>
					
							<tr align='center' style='background-color: white;'>
								<th colspan='2'>높이</th>
							</tr>
							<tr align='center' id='title_sincheonji'>
								<th colspan='2' style='CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","${item} }","","${WRITE_DATE}","", "")'>${item}(<span id="CONFIRM_DAY_${i.index}">0</span>)</th>
							</tr>
							<tr align='center'>
								<th width="60%">대분류</th>
								<th width="40%">확진자수</th>
							</tr>
						</thead>
					</table>
				</c:forEach>
			

				<!-- fix 기타 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="confirm_table_etc">
					<thead style='background-color: #0B2161;'>
						<tr align='center' style='background-color: white;'>
							<th colspan='2'>높이</th>
						</tr>
						<tr align='center' id='title_sincheonji'>
							<th colspan='2' style='CURSOR: hand; font-weight: bold;background-color:#08298A;' onclick='go_confirmDtl("","기타","","${WRITE_DATE}","", "")'>기타(<span id="CONFIRM_DAY_ETC">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%" style='background-color:#08298A;'>대분류</th>
							<th width="40%" style='background-color:#08298A;'>확진자수</th>
						</tr>
					</thead>
				</table>
			
		</div>
		
		
		<!-- 당일 입원병원-->
		<div class="table-responsive m-b-40" style='width:2500;'>
				<!-- fix 해외 병원 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="confirm_table_hos_oversea">
					<thead style='background-color: #0B2161;'>
						<tr align='center' id="title_hos_oversea">
							<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("해외","","","${WRITE_DATE}","", "")'>입원병원(<span id="CONFIRM_DAY_HOS_OVERSEA">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">병원명</th>
							<th width="40%">입원자수</th>
						</tr>
					</thead>
				</table>
			
				<!-- 실시간 변동 테이블-->
				<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
					<table class="table table-bordered table-data3" style='width: 200;float:left;' id="confirm_table_hos_${i.index}">
						<c:if test="${i.index%2 == 0 }">
						    <thead style='background-color: #08298A;'>
						</c:if>
						
						<c:if test="${i.index%2 != 0 }">
						    <thead style='background-color: #0B2161;'>
						</c:if>


							<tr align='center' id="title_hos_oversea">
								<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","${item} }","","${WRITE_DATE}","", "")'>입원병원(<span id="CONFIRM_DAY_HOS_${i.index}">0</span>)</th>
							</tr>
							<tr align='center'>
								<th width="60%">병원명</th>
								<th width="40%">입원자수</th>
							</tr>
						</thead>
					</table>
				</c:forEach>
				
				<!-- fix 기타 병원 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="confirm_table_hos_etc">
					<thead style='background-color: #08298A;'>
						<tr align='center' id="title_hos_oversea">
							<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","기타","","${WRITE_DATE}","", "")'>입원병원(<span id="CONFIRM_DAY_HOS_ETC">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">병원명</th>
							<th width="40%">입원자수</th>
						</tr>
					</thead>
				</table>
		</div>
		
		
		
		<!-- 누적테이블 -->
		<div class="table-responsive m-b-40" style='width:2500;'>
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="accum_confirm_table_oversea">
					<thead style='background-color: #0B2161;'>
						<tr align='center'>
							<th colspan='2' id='accum_title_total' style='background-color:#ffc314;color:#ff0000;font-weight:bold;'>누적(<span id="CONFIRM_ACCUM_TOTAL">0</span>)</th>
						</tr>
						<tr align='center' id="accum_title_oversea">
							<th colspan='2' style='CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("해외","","","","", "")'>해외(<span id="CONFIRM_ACCUM_OVERSEA">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">대분류</th>
							<th width="40%">확진자수</th>
						</tr>
					</thead>
				</table>
				
				<!-- 실시간 변동 테이블-->
				<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
					<table class='table table-bordered table-data3' style='width: 200;float:left;' id='accum_confirm_table_${i.index}'>

						<c:if test="${i.index%2 == 0 }">
						    <thead style='background-color: #08298A;'>
						</c:if>
						
						<c:if test="${i.index%2 != 0 }">
						    <thead style='background-color: #0B2161;'>
						</c:if>

							<tr align='center' style='background-color: white;'>
								<th colspan='2'>높이</th>
							</tr>
							<tr align='center' id='title_sincheonji'>
								<th colspan='2' style='CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","${item}","","","", "")'>${item}(<span id="CONFIRM_ACCUM_${i.index}">0</span>)</th>
							</tr>
							<tr align='center'>
								<th width="60%">대분류</th>
								<th width="40%">확진자수</th>
							</tr>
						</thead>
					</table>
				</c:forEach>
			

				<!-- fix 기타 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="accum_confirm_table_etc">
					<thead style='background-color: #0B2161;'>
						<tr align='center' style='background-color: white;'>
							<th colspan='2'>높이</th>
						</tr>
						<tr align='center' id='title_sincheonji'>
							<th colspan='2' style='CURSOR: hand; font-weight: bold;background-color:#08298A;' onclick='go_confirmDtl("","기타","","","", "")'>기타(<span id="CONFIRM_ACCUM_ETC">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%" style='background-color:#08298A;'>대분류</th>
							<th width="40%" style='background-color:#08298A;'>확진자수</th>
						</tr>
					</thead>
				</table>
		</div>
		
		<!-- 누적 입원병원-->
		<div class="table-responsive m-b-40" style='width:2500;'>
				<!-- fix 해외 병원 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="accum_confirm_table_hos_oversea">
					<thead style='background-color: #0B2161;'>
						<tr align='center' id="accum_title_hos_oversea">
							<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("해외","","","","", "")'>입원병원(<span id="CONFIRM_ACCUM_HOS_OVERSEA">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">병원명</th>
							<th width="40%">입원자수</th>
						</tr>
					</thead>
				</table>
				<!-- 실시간 변동 테이블-->
				<c:forEach items="${SHOW_LIST}" var="item" varStatus="i">
					<table class="table table-bordered table-data3" style='width: 200;float:left;' id="accum_confirm_table_hos_${i.index}">

						<c:if test="${i.index%2 == 0 }">
						    <thead style='background-color: #08298A;'>
						</c:if>
						
						<c:if test="${i.index%2 != 0 }">
						    <thead style='background-color: #0B2161;'>
						</c:if>

							<tr align='center' id="title_hos_oversea">
								<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","${item}","","","", "")'>입원병원(<span id="CONFIRM_ACCUM_HOS_${i.index}">0</span>)</th>
							</tr>
							<tr align='center'>
								<th width="60%">병원명</th>
								<th width="40%">입원자수</th>
							</tr>
						</thead>
					</table>
				</c:forEach>
				
				<!-- fix 기타 병원 테이블-->
				<table class="table table-bordered table-data3" style='width: 200;float:left;' id="accum_confirm_table_hos_etc">
					<thead style='background-color: #08298A;'>
						<tr align='center' id="title_hos_oversea">
							<th colspan='2' style='color:#ffc314;CURSOR: hand; font-weight: bold;' onclick='go_confirmDtl("","기타","","","", "")'>입원병원(<span id="CONFIRM_ACCUM_HOS_ETC">0</span>)</th>
						</tr>
						<tr align='center'>
							<th width="60%">병원명</th>
							<th width="40%">입원자수</th>
						</tr>
					</thead>
				</table>
		</div>
		
	</div>
	
	<br>
	

	<!-- 총괄 지도 -->
	<div style="width:600px;height:500px;margin-left:100px;" id="mapArea">
	<h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[코로나 강남구 총괄 확진 정보]</h4><br>
	<div id="map" style="width:600px;height:500px;"></div>
	</div>
		<br><br><br><br><br><br>
	
	<!-- 집단 지도 -->	
	<div style="width:700px;height:500px;margin-left:100px;float:left;" id="mapJipdanArea">
		<h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[코로나 강남구 집단감염지 정보]</h4><br>
		<div id="map_jipdan" style="width:600px;height:500px;float:left;margin-right:100px;"></div>
	
	</div>
	
			<div>
			<br>
					<table class="table table-bordered table-data3" style='width: 600;' id="accum_confirm_table_hos_etc">
					<thead style='background-color: #08298A;'>
						<tr align='center'>
							<th>집단감염지</th>
							<th style="width:100px;">동</th>
							<th style="cursor:hand;" onclick='go_confirmJipdanDtl("gannam","")'>강남구민(${sumJipdanMap.GANGANG_JIPDAN_TOTAL})</th>
							<th style="cursor:hand;" onclick='go_confirmJipdanDtl("tasigu","")'>타시구민(${sumJipdanMap.TASIGU_JIPDAN_TOTAL})</th>
							<th style="cursor:hand;" onclick='go_confirmJipdanDtl("taconfirm","")'>타지역 확진 판정자(${sumJipdanMap.TACONFIRM_JIPDAN_TOTAL})</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${confirmMapJipdanlist}" var="item" varStatus="i">
							<tr>
								<td><c:out value="${item.INFECT_DAE}" />(<c:out value="${item.GANGNAM_CNT+item.TASIGU_CNT+item.TA_CONFIRM_CNT}" />)</td>
								<td style="text-align:center;"><c:out value="${item.DONG}" /></td>
								<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmJipdanDtl("gannam","${item.INFECT_DAE}")'><c:out value="${item.GANGNAM_CNT}" /></td> 
								<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmJipdanDtl("tasigu","${item.INFECT_DAE}")'><c:out value="${item.TASIGU_CNT}" /></td> 
								<td style='CURSOR:hand;color:#0099FF;text-align:center;' onclick='go_confirmJipdanDtl("taconfirm","${item.INFECT_DAE}")'><c:out value="${item.TA_CONFIRM_CNT}" /></td>   
							</tr>
						</c:forEach>
					</tbody>
					
				</table>
		</div>

		<br><br><br><br><br><br>	
		
		
	<!-- 확진자 누계 그래프 -->
	<div>
	<div style="width:100%;padding-left:40px;" id="chartAccumArea">
	    <h4 id="graphAccumTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[강남구 누적 확진자 현황]</h4>
 
    	<div id="Line_Controls_Accum_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartAccumArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsAccumArea" style="height:100px;"></div>
        </div>
	</div>
	
	<!-- 그래프 -->
	<div style="width:100%;padding-left:40px;" id="chartArea">
	    <h4 id="graphTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[강남구 확진자 일일변동 현황]</h4>
 
    	<div id="Line_Controls_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea" style="height:100px;"></div>
        </div>
	</div>
	</div>

<br>
<br>
<br>
<br>


<style>
.label {margin-bottom: 96px;}
.label * {display: inline-block;vertical-align: top;}
.label .left {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center {background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 12px;line-height: 24px;}
.label .right {background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>


	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1c74f5b5abf3daa819be94239c175608&libraries=services,clusterer,drawing"></script>
	<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(37.513123, 127.042144), // 지도의 중심좌표
        /* center: new kakao.maps.LatLng(37.491657,127.065728), // 지도의 중심좌표 */
        level: 6 // 지도의 확대 레벨
    };

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	<c:forEach items="${confirmMaplist}" var="info">
	
		pick_confirm_area("${info.INDEX_SEQ}","${info.INFECT_DAE}","${info.INFECT_ASFECT_AREA}","${info.GANGNAM_CNT}","${info.TASIGU_CNT}","${info.TA_CONFIRM_CNT}");
	
	</c:forEach>

	
	//확진지 정보 표시
	function pick_confirm_area(INDEX_SEQ,INFECT_DAE,INFECT_ASFECT_AREA,GANGNAM_CNT,TASIGU_CNT,TA_CONFIRM_CNT){
		
		var geocoder = new kakao.maps.services.Geocoder();
		
		if(INDEX_SEQ > 20 && INDEX_SEQ < 100){
			INDEX_SEQ = 0;
		}
		
		var imageSrc = "/corona/images/common/"+INDEX_SEQ+".gif";
		/* var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markers_sprites3.png"; */
		// 마커 이미지의 이미지 크기 입니다
		var marker_size;
		
		if(INDEX_SEQ == 100 || INDEX_SEQ == 200){
			marker_size = 8;
			
		}else{
			marker_size = 10+ parseInt(GANGNAM_CNT) + parseInt(TASIGU_CNT) + parseInt(TA_CONFIRM_CNT);
		}
		
		var imageSize = new kakao.maps.Size(marker_size, marker_size);

		// 마커 이미지를 생성합니다    
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
		
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(INFECT_ASFECT_AREA, function(result, status) {

			// 정상적으로 검색이 완료됐으면 
			if (status === kakao.maps.services.Status.OK) {

				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
				// 결과값으로 받은 위치를 마커로 표시합니다
				var marker = new kakao.maps.Marker({
					map : map,
					position : coords,
					image : markerImage,
					clickable: true
				});
				
				// 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
				var iwContent = '<div style="margin:15px;height:150;width:300px;">' +
									'<font color="black">발생지 : </font><font color="red">'+INFECT_DAE+'</font>' + 
									'<br><font color="black">주소 : </font>'+INFECT_ASFECT_AREA +
									'<br><font color="black">강남구민 : </font><font color="#298A08">'+GANGNAM_CNT+'</font>' +
									'<br><font color="black">타시구민 : </font><font color="#0431B4">'+TASIGU_CNT+'</font>'+
									'<br><font color="black">타지역 확진 판정자 : </font><font color="#0431B4">'+TA_CONFIRM_CNT+'</font>'+
								'</div>', 
								// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
				    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

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
	
	
	var mapContainer_jipdan = document.getElementById('map_jipdan'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(37.507346, 127.045058), // 지도의 중심좌표
        /* center: new kakao.maps.LatLng(37.491657,127.065728), // 지도의 중심좌표 */
        level: 6 // 지도의 확대 레벨
    };

	var map_jipdan = new kakao.maps.Map(mapContainer_jipdan, mapOption); // 지도를 생성합니다
	
	<c:forEach items="${confirmMapJipdanlist}" var="info" varStatus="status"> 
	
		pick_confirm_jipdan_area("${status.index}","${info.INFECT_DAE}","${info.INFECT_ASFECT_AREA}","${info.GANGNAM_CNT}","${info.TASIGU_CNT}","${info.TA_CONFIRM_CNT}");
	
	</c:forEach>

	
	//확진지 정보 표시
	function pick_confirm_jipdan_area(INDEX,INFECT_DAE,INFECT_ASFECT_AREA,GANGNAM_CNT,TASIGU_CNT,TA_CONFIRM_CNT){
		var geocoder = new kakao.maps.services.Geocoder();
		
		if(INDEX > 20 && INDEX < 100){
			INDEX = 0;
		}
	
		var imageSrc = "/corona/images/common/"+INDEX+".gif";
		
		var marker_size;
		marker_size = 10+ parseInt(GANGNAM_CNT) + parseInt(TASIGU_CNT) + parseInt(TA_CONFIRM_CNT);

		/* var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markers_sprites3.png"; */
		// 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(marker_size, marker_size);

		// 마커 이미지를 생성합니다    
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(INFECT_ASFECT_AREA, function(result, status) {
			// 정상적으로 검색이 완료됐으면 
			if (status === kakao.maps.services.Status.OK) {
				
 				// 커스텀 오버레이에 표시할 내용입니다     
				// HTML 문자열 또는 Dom Element 입니다 
				
				var label_position = 30 + marker_size*2 ;
				var content = '<div class ="label" style="margin-bottom:'+label_position+'px;"><span class="left"></span><span class="center">'+INFECT_DAE+'</span><span class="right"></span></div>';

				// 커스텀 오버레이가 표시될 위치입니다 
				var position = new kakao.maps.LatLng(result[0].y, result[0].x);  

				// 커스텀 오버레이를 생성합니다
				var customOverlay = new kakao.maps.CustomOverlay({
				    position: position,
				    content: content   
				});

				// 커스텀 오버레이를 지도에 표시합니다
				customOverlay.setMap(map_jipdan);
				
				

				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
				// 결과값으로 받은 위치를 마커로 표시합니다
				var marker = new kakao.maps.Marker(
					{
					map : map_jipdan,
					position : coords,
					image : markerImage,
					clickable: true				
					}
					
					);
				
				// 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
				var iwContent = '<div style="margin:15px;height:150;width:300px;">' +
									'<font color="black">발생지 : </font><font color="red">'+INFECT_DAE+'</font>' + 
									'<br><font color="black">주소 : </font>'+INFECT_ASFECT_AREA +
									'<br><font color="black">강남구민 : </font><font color="#298A08">'+GANGNAM_CNT+'</font>' +
									'<br><font color="black">타시구민 : </font><font color="#0431B4">'+TASIGU_CNT+'</font>'+
									'<br><font color="black">타지역 확진 판정자 : </font><font color="#0431B4">'+TA_CONFIRM_CNT+'</font>'+
								'</div>', 
								// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
				    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

				// 인포윈도우를 생성합니다
				var infowindow = new kakao.maps.InfoWindow({
				    content : iwContent,
				    removable : iwRemoveable
				});

				// 마커에 클릭이벤트를 등록합니다
				kakao.maps.event.addListener(marker, 'click', function() {
				      // 마커 위에 인포윈도우를 표시합니다
				      infowindow.open(map_jipdan, marker);  
				});
				
			}
		});
	}
	
	

	</script>
	
	


<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
