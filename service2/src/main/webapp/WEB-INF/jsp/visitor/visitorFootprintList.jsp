<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<style type="text/css">
#selectable .ui-selected {background: yellow;color:black!important;}
</style>



<script type="text/javascript">

$(document).ready(function(){
	

	//from
	$("#FROM_DATE").datepicker({
		
		dateFormat: 'yy-mm-dd',
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    dayNamesMin: ['일','월','화','수','목','금','토'],
		changeMonth: true, //월변경가능
	    changeYear: true, //년변경가능
		showMonthAfterYear: true //년 뒤에 월 표시				
	});
	
	//to
	$("#TO_DATE").datepicker({
		
		dateFormat: 'yy-mm-dd',
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    dayNamesMin: ['일','월','화','수','목','금','토'],
		changeMonth: true, //월변경가능
	    changeYear: true, //년변경가능
		showMonthAfterYear: true //년 뒤에 월 표시				
	});
	
	//행선택 API
 	$(function(){
		$("#selectable").selectable();
	}) 
	
	//검색초기화
	fn_initial_setting();
	
	//초기기본조회
	fn_selectVisitorFootprintList(1);
	
	
	$("#search").on("click", function(e){ //검색 버튼
		e.preventDefault();
		fn_selectVisitorFootprintList(1);
	});
	
	$("#DISP_CNT").change(function(){
		$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
		fn_selectVisitorFootprintList(1);
	});	
	
	$("#initial").on("click", function(e){ //초기화 버튼
		e.preventDefault();
		$("#form1").each(function() {				 
			fn_initial_setting();
	    });
	});
	
	/* 로그아웃 */
	$("#logout").on("click", function(e){
		e.preventDefault();				
		fn_logout();
	});
});

function fn_selectVisitorFootprintList(pageNo){	
	var comAjax = new ComAjax();
	comAjax.setUrl("<c:url value='/visitor/selectVisitorFootprintList.do' />");
	comAjax.addParam("PAGE_INDEX",pageNo);
	comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
	comAjax.addParam("name", $("#NAME").val());
	comAjax.addParam("gigwan_name", $("#GIGWAN_NAME").val());
	
	
	comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
	comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
	comAjax.setCallback("fn_selectVisitorFootprintListCallback");
	
	comAjax.ajax();
}


function fn_selectVisitorFootprintListCallback(data){
	var total = data.total;			
	var body = $("#mytable");
	var recordcnt = $("#RECORD_COUNT").val();
	
	//구글차트 그리기
	google.charts.load('current', {'packages':['line','controls']});
	chartDrow(data.statisticsList); //chartDrow() 실행
	
	
	$("#tcnt").text(comma(total));
	
	body.empty();
	if(total == 0){
		var str = "<tr>" + 
			  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
			  "</tr>";
		body.append(str);
	}
	else{
		var params = {
			divId : "PAGE_NAVI",
			pageIndex : "PAGE_INDEX",
			totalCount : total,
			recordCount : recordcnt,
			eventName : "fn_selectVisitorFootprintList"
		};
		gfn_renderPaging(params);
		
		var str = "";
		var t_str = "";
		var tr_class = "";
		var jsonData = null;
		var jsonStr = null;
		
		$.each(data.list, function(key, value){
			       t_str = "";
				
			       if ((value.RNUM % 2) == 0 )
						tr_class = "tr1";
				   else
						tr_class = "tr2";
			      
			       
			       str += "<tr class='" + tr_class + "'>" + 
							   "<td align='center' style='color:#444444;'>" + value.RNUM + "</td>" +
							   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.INS_DATE), ".") + "</span></td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.UUID) + "</td>" +
							   "<td align='center' style='color:blue;'>" + NvlStr(value.NAME) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.NATIONAL_INFO) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.AGENCY) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.PHONE)) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SITECODE) + "</td>" +
							   "<td align='left' class='process'>" + NvlStr(value.GIGWAN_NAME) + "</td>" +
							   "<td align='left' style='color:#444444;'>" + NvlStr(value.ADDRESS_NAME) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.PHONE_NO) + "</td>" +
						   "</tr>";
							   
		});
		body.append(str);
	}
}

//차트 그리기
function chartDrow(statisticsList){
	
  var chartData = '';

  //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
  var chartDateformat     = 'yyyy년MM월dd일';
  //라인차트의 라인 수
  var chartLineCount    = 10;
  //컨트롤러 바 차트의 라인 수
  var controlLineCount    = 10;

  function drawDashboard() {

    var data = new google.visualization.DataTable();
    //그래프에 표시할 컬럼 추가
    data.addColumn('datetime' , '날짜');
    data.addColumn('number'   , '방문자수');

    //그래프에 표시할 데이터
    var dataRow = [];
		   
	$.each(statisticsList,function(index,item){
	    dataRow = [new Date(item.process_date.substring(0,4), item.process_date.substring(4,6)-1, item.process_date.substring(6,8) ,'00' ),item.total_cnt];
	    data.addRow(dataRow);
  });

      var chart = new google.visualization.ChartWrapper({
        chartType   : 'LineChart',
        containerId : 'lineChartArea', //라인 차트 생성할 영역
        options     : {
                        isStacked   : 'percent',
                        focusTarget : 'category',
                        height          : 130,
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
          vAxis              : {minValue: 1,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
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


//검색초기화
function fn_initial_setting(){
	//From의 초기값을 오늘 날짜로 설정
    $('#FROM_DATE').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
    //To의 초기값을 내일로 설정
    $('#TO_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
    
    $("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
	$("#PAGE_INDEX").val("1");

	$("#NAME").val("");
	$("#GIGWAN_NAME").val("");
	$('input:radio[id=SEX_ALL_GB]').prop("checked", true);
	$("#INS_DATE").val("");
}

//엑셀 다운로드
function doExcelDownloadProcess(mappingValue){
	$("input[name=name]", "form[name=excelDown]").val($("#NAME").val());
	$("input[name=gigwan_name]", "form[name=excelDown]").val($("#GIGWAN_NAME").val());
	$("input[name=from_date]", "form[name=excelDown]").val($("#FROM_DATE").val().replace(/-/gi, ""));
	$("input[name=to_date]", "form[name=excelDown]").val($("#TO_DATE").val().replace(/-/gi, ""));
	
    var f = document.excelDown;
    f.action = mappingValue;
    f.submit();
}

//엔터시 자동 검색
function enterkey() {
        if (window.event.keyCode == 13) {
             // 엔터키가 눌렸을 때 실행할 내용
             fn_selectVisitorFootprintList(1);
        }
}

//로그아웃
function fn_logout(){
	var comSubmit = new ComSubmit();			
	comSubmit.setUrl("<c:url value='/logoutVisitor.do' />");
	comSubmit.submit();
}

</script>


</head>
<body bgcolor="#FFFFFF">

<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>

<!-- 타이틀 -->
<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/service/images/popup_title.gif" align="absmiddle">
	</a>스마트 출입관리 출입내역 조회
</div>

<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:680px;display: inline-block">
		<div class="card-header" style="background-color:#0B2161;color:white;margin-bottom:10px;">검색</div>
		
	<form action="" method="post">
		<div class="card-body card-block" style="padding:5px;">
				<div style="float:left;">
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">이름</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control" onkeyup="enterkey();">
							<div class="input-group-addon">
								<i class="fa fa-user"></i>
							</div>
						</div>
					</div>
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">업소명</div>
							<input type="text" id="GIGWAN_NAME" name="GIGWAN_NAME" value="" class="form-control" onkeyup="enterkey();">
							<div class="input-group-addon">
								<i class="fa fa-home"></i>
							</div>
						</div>
					</div>
					
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">From</div>
							<input type="text" id="FROM_DATE" name="FROM_DATE" value="" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o"></i>
							</div>
							&nbsp;&nbsp;~&nbsp;&nbsp;
							<div class="input-group-addon" style="width:80px;">To</div>
							<input type="text" id="TO_DATE" name="TO_DATE" value="" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
				<div class="form-actions form-group">
					<div style="padding-left:5px;padding-bottom:10px;float:left;">
						<a href="#this" class="btn btn-danger" id="initial"><i class="fa fa-ban"></i> 검색초기화</a>
						<a href="#this" class="btn btn-primary" id="search"><i class="fa fa-search"></i> 검색</a>
					</div>
				</div>
			</form>
		</div>
		
	<div style="display: inline-block;width:900px;height:200px;padding-left:40px;">
	    <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;출입현황</h4>
 
    	<div id="Line_Controls_Chart">
      		<!-- 라인 차트 생성할 영역 -->
          <div id="lineChartArea"></div>
      		<!-- 컨트롤바를 생성할 영역 -->
          <div id="controlsArea" style="height:100px;"></div>
        </div>
	</div>

</div>
		
	<div style="float: left;margin-left:20px;">
		<div style="float: left;">
			<b>Total : <span id="tcnt"></span> 건</b> &nbsp;
		</div> 
		<div style="float: left;">
			<select id="DISP_CNT" name="DISP_CNT" class="form-control" style="width:60px!important;height:30px;padding:3px;">
	           	<option value="10">10</option>
	           	<option value="20">20</option>
				<option value="50">50</option>
			</select>
		</div>
	</div>

</form>

<div style="float:left;margin-left:10px;margin-bottom:10px;">
	<!-- 엑셀 다운용 폼 -->
	<form id="excelDown" name="excelDown" method="post" enctype="multipart/form-data">
		<input type="hidden" name="name">
		<input type="hidden" name="gigwan_name">
		<input type="hidden" name="from_date">
		<input type="hidden" name="to_date">
	
		<input type="image" src="/service/images/common/btn_excel.gif" style="border:0;" onclick="doExcelDownloadProcess('/service/visitor/downloadExcelFile.do')" alt="엑셀다운로드">
	</form>
</div>

<div align="left" style="width:100%;padding-right:20px;">
	<div  style="margin-left:20px;" align="center">
		<div class="table-responsive m-b-40">
			 <table class="table table-borderless table-data3" id="selectable">
			     <thead style="background-color:#0B2161;">
			        <tr align="center">
							<th style="width: 80px;">NO</th>
							<th style="text-align: center;">방문시간</th>
							<th>고유번호</th>
							<th>이름</th>
							<th>성별</th>
							<th>내외국인</th>
							<th>통신사</th>
							<th>연락처</th>
							<th>SITECODE</th>
							<th>업소명</th>
							<th style="width: 380px;">업소주소</th>
							<th>업소연락처</th>
						</tr>
			      </thead>
			      <tbody id='mytable'>     
			      </tbody>
		      </table>
	      </div>
	</div>

	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>

</div>	
	
</body>
</html>