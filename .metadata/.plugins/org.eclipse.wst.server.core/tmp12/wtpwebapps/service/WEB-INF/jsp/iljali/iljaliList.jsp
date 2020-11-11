<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>

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
 	fn_selectTermOfficialViewList(1);
	fn_selectTermWorkerViewList(1);
	fn_selectPublicBusinessViewList(1); 
	
	
	$("#search").on("click", function(e){ //검색 버튼
		e.preventDefault();
		fn_selectTermOfficialViewList(1);
		fn_selectTermWorkerViewList(1);
		fn_selectPublicBusinessViewList(1);
	});
	
	$("#DISP_CNT").change(function(){
		$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
		fn_selectTermOfficialViewList(1);
		fn_selectTermWorkerViewList(1);
		fn_selectPublicBusinessViewList(1);
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

/* 임기제 공무원 */
function fn_selectTermOfficialViewList(pageNo){	
	var comAjax = new ComAjax();
	comAjax.setUrl("<c:url value='/iljali/selectTermOfficialViewList.do' />");
	comAjax.addParam("PAGE_INDEX",pageNo);
	comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
	comAjax.addParam("name", $("#NAME").val());
	comAjax.addParam("gonggo_nm", $("#GONGGO_NM").val());
	
	
	comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
	comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
	comAjax.setCallback("fn_selectTermOfficialViewListCallback");
	
	comAjax.ajax();
}


function fn_selectTermOfficialViewListCallback(data){
	var termOfficial_total = data.termOfficial_total;	
	
	var termOfficial_table = $("#termOfficial_table");
	
	termOfficial_table.empty();

	var str_termOfficial = "";

	var recordcnt = $("#RECORD_COUNT").val();
	
	var tr_class = "";
	
	
	$("#tcnt").text(comma(termOfficial_total));
	
		if(termOfficial_total == 0){
			str_termOfficial = "<tr>" + 
				  "<td colspan=\"10\">조회된 결과가 없습니다.</td>" +
				  "</tr>";
		}
		else{
			var params = {
				divId : "PAGE_TERM_OFFICIAL_NAVI",
				pageIndex : "PAGE_TERM_OFFICIAL_INDEX",
				totalCount : termOfficial_total,
				recordCount : recordcnt,
				eventName : "fn_selectTermOfficialViewList"
			};
			gfn_renderPaging_multi(params);
			
			/* 임기제 공무원 */
			$.each(data.termOfficialList, function(key, value){
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
				       str_termOfficial += "<tr class='" + tr_class + "'>" + 
				       						"<td align='center' style='color:#222222;'>" + value.RNUM + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.BUSEO_NO) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.HIRE_GUBUN) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLICATION_SEQ) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NO) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NM) + "</td>" +
									       "<td align='center' style='color:#444444;'><a href='javascript:void(0);' onclick='fn_iljaliTermOfficialDtl(" +value.NOT_ANCMT_MGT_NO+ ",\"" +value.JUMIN_NO + "\");'>" + NvlStr(value.NAME) + "</a></td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.JUMIN_NO) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.MULTI_NATION_YN) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS_DETAIL) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.POST_NO) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.PHONE) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.PAYMENT) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.PAYMENT_YN) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_CLASS) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_DATE) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_STATE) + "</td>" +
									       "<td align='center' style='color:#444444;'>" + NvlStr(value.MASTER_NM) + "</td>" +
											"</tr>";
											
			});
		}
		
		termOfficial_table.append(str_termOfficial);
}

/* 기간제 근로자 */
function fn_selectTermWorkerViewList(pageNo){
	var comAjax = new ComAjax();
	comAjax.setUrl("<c:url value='/iljali/selectTermWorkerViewList.do' />");
	comAjax.addParam("PAGE_INDEX",pageNo);
	comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
	comAjax.addParam("name", $("#NAME").val());
	comAjax.addParam("gonggo_nm", $("#GONGGO_NM").val());
	
	
	comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
	comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
	comAjax.setCallback("fn_selectTermWorkerViewListCallback");
	
	comAjax.ajax();
}
 
 
function fn_selectTermWorkerViewListCallback(data){
	var termWorker_total = data.termWorker_total;	
	
	var termWorker_table = $("#termWorker_table");
	
	termWorker_table.empty();

	var str_termWorker = "";

	var recordcnt = $("#RECORD_COUNT").val();
	
	var tr_class = "";
	
	$("#tcnt").text(comma(termWorker_total));
	
		if(termWorker_total == 0){
			str_termWorker = "<tr>" + 
							  "<td colspan=\"10\">조회된 결과가 없습니다.</td>" +
							  "</tr>";
		}
		else{
			var params = {
				divId : "PAGE_TERM_WORKER_NAVI",
				pageIndex : "PAGE_TERM_WORKER_INDEX",
				totalCount : termWorker_total,
				recordCount : recordcnt,
				eventName : "fn_selectTermWorkerViewList"
			};
			gfn_renderPaging_multi(params);
			
			/* 임기제 공무원 */
			$.each(data.termWorkerList, function(key, value){
				
			       if ((value.RNUM % 2) == 0 )
						tr_class = "tr1";
				   else
						tr_class = "tr2";
				   
				   str_termWorker +=  "<tr class='" + tr_class + "'>" + 
				   						"<td align='center' style='color:#222222;'>" + value.RNUM + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.BUSEO_NO) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.HIRE_GUBUN) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLICATION_SEQ) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NO) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NM) + "</td>" +
									   "<td align='center' style='color:#444444;'><a href='javascript:void(0);' onclick='fn_iljaliTermWorkerDtl(" +value.NOT_ANCMT_MGT_NO+ ",\"" +value.JUMIN_NO + "\");'>" + NvlStr(value.NAME) + "</a></td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.JUMIN_NO) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS_DETAIL) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.POST_NO) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.PHONE) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NATIONAL_MERIT_YN) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.DRIVE_YN) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.DRIVE_CLASS_JONG) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.DRIVE_CLASS_GUBUN) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_DATE) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_STATE) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.MASTER_NM) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.MASTER_COMMENT) + "</td>" +
										"</tr>";
					});
		}
		
		termWorker_table.append(str_termWorker);
}



/* 공공근로사업참여자 */
function fn_selectPublicBusinessViewList(pageNo){
	var comAjax = new ComAjax();
	comAjax.setUrl("<c:url value='/iljali/selectPublicBusinessViewList.do' />");
	comAjax.addParam("PAGE_INDEX",pageNo);
	comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
	comAjax.addParam("name", $("#NAME").val());
	comAjax.addParam("gonggo_nm", $("#GONGGO_NM").val());
	
	
	comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
	comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
	comAjax.setCallback("fn_selectPublicBusinessViewListCallback");
	
	comAjax.ajax();
}

function fn_selectPublicBusinessViewListCallback(data){
	var publicBusiness_total = data.publicBusiness_total;	
	
	var publicBusiness_table = $("#publicBusiness_table");
	
	publicBusiness_table.empty();

	var str_publicBusiness = "";

	var recordcnt = $("#RECORD_COUNT").val();
	
	var tr_class = "";
	
	$("#tcnt").text(comma(publicBusiness_total));
	
		if(publicBusiness_total == 0){
			str_publicBusiness = "<tr>" + 
								  "<td colspan=\"10\">조회된 결과가 없습니다.</td>" +
								  "</tr>";
		}
		else{
			var params = {
				divId : "PAGE_PUBLIC_BUSINESS_NAVI",
				pageIndex : "PAGE_PUBLIC_BUSINESS_INDEX",
				totalCount : publicBusiness_total,
				recordCount : recordcnt,
				eventName : "fn_selectPublicBusinessViewList"
			};
			gfn_renderPaging_multi(params);
			
			/* 공공근로사업참여자 */
			$.each(data.publicBusinessList, function(key, value){
				
			       if ((value.RNUM % 2) == 0 )
						tr_class = "tr1";
				   else
						tr_class = "tr2";
				   
				   str_publicBusiness += "<tr class='" + tr_class + "'>" + 
				   						"<td align='center' style='color:#222222;'>" + value.RNUM + "</td>" +
				   						"<td align='center' style='color:#444444;'>" + NvlStr(value.BUSEO_NO) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.HIRE_GUBUN) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLICATION_SEQ	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NO	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NOTIFICATION_NM	) + "</td>" +
									   "<td align='center' style='color:#444444;'><a href='javascript:void(0);' onclick='fn_iljaliPublicBusinessDtl(" +value.NOT_ANCMT_MGT_NO+ ",\"" +value.JUMIN_NO + "\");'>" + NvlStr(value.NAME) + "</a></td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.JUMIN_NO	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ADDRESS_DETAIL	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.POST_NO	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.PHONE	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.HOPE_BUSINESS	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ETC	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.OBSTACLE	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.OBSTACLE_DEGREE	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.OBSTACLE_CLASS	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUPPORT_YN	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.ONE_PARRENT_YN	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.HOMELESS	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.MARRIAGE_MIG_YN	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.NORTH_ESCAPE_YN	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.LICENSE_INFO	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_CLASS	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_DATE	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.APPLY_STATE	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.MASTER_NM	) + "</td>" +
									   "<td align='center' style='color:#444444;'>" + NvlStr(value.MASTER_COMMENT	) + "</td>" +
							   			"</tr>";
		   			
			});
		}
		publicBusiness_table.append(str_publicBusiness);
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
	$("#GONGGO_NM").val("");
	$('input:radio[id=SEX_ALL_GB]').prop("checked", true);
	$("#INS_DATE").val("");
}

//엑셀 다운로드
function doExcelDownloadProcess(mappingValue){
	$("input[name=name]", "form[name=excelDown]").val($("#NAME").val());
	$("input[name=gonggo_nm]", "form[name=excelDown]").val($("#GONGGO_NM").val());
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
             fn_selectIljaliList(1);
        }
}

//로그아웃
function fn_logout(){
	var comSubmit = new ComSubmit();			
	comSubmit.setUrl("<c:url value='/logoutVisitor.do' />");
	comSubmit.submit();
}

/* 임기직 공무원 응시자 상세 정보 조회 */
function fn_iljaliTermOfficialDtl(not_ancmt_mgt_no,jumin_no){
	
	var comSubmit = new ComSubmit();
	
	comSubmit.setUrl("<c:url value='/iljali/iljaliTermOfficialDtl.do' />");
	comSubmit.addParam("not_ancmt_mgt_no", not_ancmt_mgt_no);
	comSubmit.addParam("jumin_no", jumin_no);
	comSubmit.submit();
}

/* 기간제 근로자 응시자 상세 정보 조회 */
function fn_iljaliTermWorkerDtl(not_ancmt_mgt_no,jumin_no){
	
	var comSubmit = new ComSubmit();
	
	comSubmit.setUrl("<c:url value='/iljali/iljaliTermWorkerDtl.do' />");
	comSubmit.addParam("not_ancmt_mgt_no", not_ancmt_mgt_no);
	comSubmit.addParam("jumin_no", jumin_no);
	comSubmit.submit();
}

/* 공공근로사업참여자 응시자 상세 정보 조회 */
function fn_iljaliPublicBusinessDtl(not_ancmt_mgt_no,jumin_no){
	
	var comSubmit = new ComSubmit();
	
	comSubmit.setUrl("<c:url value='/iljali/iljaliPublicBusinessDtl.do' />");
	comSubmit.addParam("not_ancmt_mgt_no", not_ancmt_mgt_no);
	comSubmit.addParam("jumin_no", jumin_no);
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
	</a>일자리 컨텐츠 조회
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
							<div class="input-group-addon" style="width:80px;">공고명</div>
							<input type="text" id="GONGGO_NM" name="GONGGO_NM" value="" class="form-control" onkeyup="enterkey();">
							<div class="input-group-addon">
								<i class="fa fa-list-alt"></i>
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
		<input type="hidden" name="gonggo_nm">
		<input type="hidden" name="from_date">
		<input type="hidden" name="to_date">
	
		<input type="image" src="/service/images/common/btn_excel.gif" style="border:0;" onclick="doExcelDownloadProcess('/service/iljali/downloadExcelFile.do')" alt="엑셀다운로드">
	</form>
</div>

<br>
<div class="card-body">
	<div class="custom-tab">

		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-item nav-link active" id="custom-nav-termOfficial-tab" data-toggle="tab" href="#custom-nav-termOfficial" role="tab" aria-controls="custom-nav-termOfficial"
				 aria-selected="true">임기제 공무원</a>
				<a class="nav-item nav-link" id="custom-nav-termWorker-tab" data-toggle="tab" href="#custom-nav-termWorker" role="tab" aria-controls="custom-nav-termWorker"
				 aria-selected="false">기간제 근로자</a>
				<a class="nav-item nav-link" id="custom-nav-publicBusiness-tab" data-toggle="tab" href="#custom-nav-publicBusiness" role="tab" aria-controls="custom-nav-publicBusiness"
				 aria-selected="false">공공근로사업참여자</a>
			</div>
		</nav>
		<div class="tab-content pl-3 pt-2" id="nav-tabContent">
			<div class="tab-pane fade show active" id="custom-nav-termOfficial" role="tabpanel" aria-labelledby="custom-nav-termOfficial-tab">
			
				<div class="col-md-12" align="center">
						<div class="table-responsive m-b-40">
							 <table class="table table-borderless table-data3">
						     <thead style="background-color:#0B2161;">
						        <tr align="center">
						        		<th>NO</th>
										<th>부서코드</th>
										<th>채용구분</th>
										<th>응시번호</th>
										<th>고시공고번호</th>
										<th>고시공고명</th>
										<th>성명</th>
										<th>주민번호</th>
										<th>복수국적해당여부</th>
										<th>주소</th>
										<th>주소상세</th>
										<th>우편번호</th>
										<th>전화번호</th>
										<th>결제금액</th>
										<th>결제여부</th>
										<th>응시분야</th>
										<th>응시날짜</th>
										<th>응시상태</th>
										<th>담당자</th>
										<th>담당자코멘트</th>
									</tr>
						      </thead>
						      <tbody id='termOfficial_table'>     
						      </tbody>
						      </table>
					      </div>
					</div>
					
						<div id="PAGE_TERM_OFFICIAL_NAVI" align='center'></div>
						<input type="hidden" id="PAGE_TERM_OFFICIAL_INDEX" name="PAGE_TERM_OFFICIAL_INDEX"/>
			</div>
			<div class="tab-pane fade" id="custom-nav-termWorker" role="tabpanel" aria-labelledby="custom-nav-termWorker-tab">
						<div class="col-md-12" align="center">
							<div class="table-responsive m-b-40">
								 <table class="table table-borderless table-data3">
							     <thead style="background-color:#0B2161;">
							        <tr align="center">
							        	<th>NO</th>
										<th>부서코드</th>
										<th>채용구분</th>
										<th>응시번호</th>
										<th>고시공고번호</th>
										<th>고시공고명</th>
										<th>성명</th>
										<th>성별</th>
										<th>주민번호</th>
										<th>주소</th>
										<th>주소상세</th>
										<th>우편번호</th>
										<th>전화번호</th>
										<th>국가유공자해당여부</th>
										<th>운전가능여부</th>
										<th>운전면허종류(종별)</th>
										<th>운전면허종류(구분)</th>
										<th>응시날짜</th>
										<th>응시상태</th>
										<th>담당자</th>
										<th>담당자코멘트</th>
									</tr>
							      </thead>
							      <tbody id='termWorker_table'>     
							      </tbody>
							      </table>
						      </div>
						</div>
						
						<div id="PAGE_TERM_WORKER_NAVI" align='center'></div>
						<input type="hidden" id="PAGE_TERM_WORKER_INDEX" name="PAGE_TERM_WORKER_INDEX"/>
			</div>
			<div class="tab-pane fade" id="custom-nav-publicBusiness" role="tabpanel" aria-labelledby="custom-nav-publicBusiness-tab">
						<div class="col-md-12" align="center">
							<div class="table-responsive m-b-40">
								 <table class="table table-borderless table-data3">
							     <thead style="background-color:#0B2161;">
							        <tr align="center">
							        	<th>NO</th>
										<th>부서코드</th>
										<th>채용구분</th>
										<th>응시번호</th>
										<th>고시공고번호</th>
										<th>고시공고명</th>
										<th>성명</th>
										<th>주민번호</th>
										<th>주소</th>
										<th>주소상세</th>
										<th>우편번호</th>
										<th>전화번호</th>
										<th>참여희망사업</th>
										<th>기타사항</th>
										<th>장애인(본인,가족여부)</th>
										<th>장애종류(장애정도)</th>
										<th>장애종류(장애종류)</th>
										<th>취업보호.지원대상자(국가유공자)</th>
										<th>한부모가정</th>
										<th>노숙자</th>
										<th>결혼이주여성</th>
										<th>북한탈주민</th>
										<th>자격증정보</th>
										<th>응시분야</th>
										<th>응시날짜</th>
										<th>응시상태</th>
										<th>담당자</th>
										<th>담당자코멘트</th>
									</tr>
							      </thead>
							      <tbody id='publicBusiness_table'>
							      </tbody>
							      </table>
						      </div>
						</div>
						
						<div id="PAGE_PUBLIC_BUSINESS_NAVI" align='center'></div>
						<input type="hidden" id="PAGE_PUBLIC_BUSINESS_INDEX" name="PAGE_PUBLIC_BUSINESS_INDEX"/>
			</div>
			
		</div>
	</div>
</div>

	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
</body>
</html>