<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {

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
		
		//기입일
		$("#INS_DATE").datepicker({
			
			dateFormat: 'yy-mm-dd',
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    dayNamesMin: ['일','월','화','수','목','금','토'],
			changeMonth: true, //월변경가능
		    changeYear: true, //년변경가능
			showMonthAfterYear: true //년 뒤에 월 표시				
		});
		
		//검색초기화
		fn_initial_setting();
		
		fn_selectClinicList(1);
		
		$("#NAME").keydown(function (key) {

			if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$("#PAGE_INDEX").val("1");
				fn_selectClinicList(1);
			}
		});
		
		
		$("#search").on("click", function(e){ //검색 버튼
			e.preventDefault();
			fn_selectClinicList(1);
		});
		
		$("#DISP_CNT").change(function(){
			$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
			fn_selectClinicList(1);
		});	
		
		$("#initial").on("click", function(e){ //초기화 버튼
			e.preventDefault();
			$("#form1").each(function() {				 
				fn_initial_setting();
		    });
		});
		
		//전체 선택시
		$('input[name=_selected_all_]').on('change', function(){ 
			$('input[name=_selected_]').prop('checked', this.checked); 
		}); 
		
		
		$("#delete").on("click", function(e){ //초기화 버튼
			e.preventDefault();
			delChkRow();
		});
			

	});
	
	function fromDate_icon(){
		//from
		$("#FROM_DATE").focus();

	}
	
	function toDate_icon(){
		//to
		$("#TO_DATE").focus();
	}
	
	function insDate_icon(){
		//from
		$("#INS_DATE").focus();

	}
	
	
	//선택 로우 삭제
    function delChkRow() {
		
    	var arr = $('input[name=_selected_]:checked').serializeArray().map(function(item) { return item.value });
		
		var checkedValue = [];
		$('input[name=_selected_]:checked').each(function(index, item){
			checkedValue.push($(item).val());   
		});
   	

		if (arr.length ==0 ) {
			alert("선택된 로우가 없습니다.");
			return false;
		} 
		
		if (confirm("선택 항목을 삭제 하시겠습니까?")) {

			//견적서 파일 삭제
			
			$.ajax({
		        type: "post",
		        url : "/corona/manage/deleteClinicChk.do",
		        data: {
		        	val:checkedValue
		        },
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("삭제가 완료되었습니다.");
		        	fn_selectClinicList(1);
	            },

		        error: function (data) {//추천실패
		        	alert("삭제가 실패 하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
	}
	
	//수정화면가기
	function fn_updateClinic(UID) {
		var comSubmit = new ComSubmit();
		
		comSubmit.setUrl("<c:url value='/write/updateClinicView.do' />");
		comSubmit.addParam("ID", UID);
		comSubmit.submit();
	}
	
	
	//검색초기화
	function fn_initial_setting(){
		//From의 초기값을 오늘 날짜로 설정
	    $('#FROM_DATE').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    //To의 초기값을 내일로 설정
	    $('#TO_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    $('#INS_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    
	    $("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
		$("#PAGE_INDEX").val("1");

		$("#NAME").val("");
	}
	
	
	function fn_selectClinicList(pageNo){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/manage/selectClinicList.do' />");
		comAjax.addParam("PAGE_INDEX",pageNo);
		comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
		comAjax.addParam("name", $("#NAME").val());
		
		
		comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
		comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
		
		
		comAjax.addParam("write_id", $("#WRITE_ID").val());
		comAjax.addParam("ins_date", $("#INS_DATE").val().replace(/-/gi, ""));
		
		comAjax.setCallback("fn_selectClinicListCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_selectClinicListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
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
				eventName : "fn_selectClinicList"
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
				       			   "<td><input type='checkbox' name='_selected_' value='"+value.ID+"'></td>" +
								   "<td align='center' style='color:#444444;'>" + value.RNUM + "</td>" +
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CLINIC_VISIT_DATE) + "</td>"  +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='javascript:void(0);' onclick='fn_updateClinic("+ value.ID +")' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INSPECTION_CASE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SARAE_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_DAE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_SO) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.REMARK) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INS_ID) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INS_DATE) + "</td>" +
							   "</tr>";
								   
			});
			body.append(str);
		}
	}	
	

	//엑셀 업로드
	function check() {
		var file = $("#excelFile").val();

		if (file == "" || file == null) {
			alert("파일을 선택해주세요.");
			return false;
		} else if (!checkFileType(file)) {
			alert("엑셀 파일만 업로드 가능합니다.");
			return false;
		}

		if (confirm("업로드시 병합된 셀이 있으면 해제해 주셔야 합니다.\n업로드 하시겠습니까?")) {

			var options = {
				success : function(data) {
					if(data.resultMap.rtn == 1){
						alert("모든 데이터가 업로드 되었습니다.");
						fn_selectClinicList(1);
					}else{
						alert(data.resultMap.err_msg+"\n\n" + data.resultMap.err_msg_dtl);
					}
				},
				type : "POST"
			};

			$("#excelUploadForm").ajaxSubmit(options);

		}
	}
	
	//엑셀 형식인지 확인
	function checkFileType(filePath) {
		var fileFormat = filePath.split(".");

		if (fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1) {
			return true;
		} else {
			return false;
		}
	}
	
	//엑셀 다운로드
	function doExcelDownloadProcess(mappingValue){
		alert("다운로드 되는 엑셀은 조회조건에 영향을 받습니다.");
		
		$("input[name=name]", "form[name=excelDown]").val($("#NAME").val());
		$("input[name=from_date]", "form[name=excelDown]").val($("#FROM_DATE").val().replace(/-/gi, ""));
		$("input[name=to_date]", "form[name=excelDown]").val($("#TO_DATE").val().replace(/-/gi, ""));
		$("input[name=write_id]", "form[name=excelDown]").val($("#WRITE_ID").val());
		$("input[name=ins_date]", "form[name=excelDown]").val($("#INS_DATE").val());
		
	    var f = document.excelDown;
	    f.action = mappingValue;
	    f.submit();
	}
	
	//샘플다운로드
	function fn_sampleDownload(gubun){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/common/downloadSampleFile.do' />");
		comSubmit.addParam("gubun", gubun);
		comSubmit.submit();
	}	
	
	//강남구 데이터 정비
	function fn_gangnamgu_arrange(){
		if (confirm("강남구 입력 데이터의 동을 맞추는 작업입니다.\n수행 하시겠습니까?")) {

			//견적서 파일 삭제
			
			$.ajax({
		        type: "post",
		        url : "/corona/manage/gangnamguArrange.do",
		        data: {},
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("강남구 데이터가 정비되었습니다.");
	            },

		        error: function (data) {//추천실패
		        	alert("강남구 데이터 정비가 실패하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
		
	}	
	
	//타시구 데이터 정비
	function fn_tasigu_arrange(){
		if (confirm("타지역 입력 데이터의 시와 구를 맞추는 작업입니다.\n수행 하시겠습니까?")) {

			//견적서 파일 삭제
			
			$.ajax({
		        type: "post",
		        url : "/corona/manage/tasiguArrange.do",
		        data: {},
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("타시구 데이터가 정비되었습니다.");
	            },

		        error: function (data) {//추천실패
		        	alert("타시구 데이터 정비가 실패하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
		
	}	
</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>


<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/corona/images/popup_title.gif" align="absmiddle">
	</a>선별진료소 검사 관리
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/status/mainStatusView.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 코로나19 현황</a>
	</div>
	
</div>



<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:680px;display: inline-block">
		<div class="card-header" style="background-color:#0B2161;color:white;margin-bottom:10px;">검색</div>
		
	<form action="" method="post">
		<div class="card-body card-block" style="padding:5px;">
				<div style="float:left;">
					<div class="form-group" style="margin-bottom:5px;width:660;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">검사자명</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">From</div>
							<input type="text" id="FROM_DATE" name="FROM_DATE" value="" style="width:50;" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o" onclick="fromDate_icon()" style="CURSOR:hand;"></i>
							</div>
							&nbsp;&nbsp;~&nbsp;&nbsp;
							<div class="input-group-addon" style="width:80px;">To</div>
							<input type="text" id="TO_DATE" name="TO_DATE" value="" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o" onclick="toDate_icon()" style="CURSOR:hand;"></i>
							</div>
						</div>
					</div>
					
					<div class="form-group" style="margin-bottom:5px;width:660;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">입력자ID</div>
							<input type="text" id="WRITE_ID" name="WRITE_ID" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">기입일</div>
							<input type="text" id="INS_DATE" name="INS_DATE" value="" style="width:50;" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o" onclick="insDate_icon()" style="CURSOR:hand;"></i>
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
			<input type="hidden" name="from_date">
			<input type="hidden" name="to_date">
			<input type="hidden" name="write_id">
			<input type="hidden" name="ins_date">
		
			<input type="image" src="/corona/images/common/btn_excel.gif" style="border:0;" onclick="doExcelDownloadProcess('/corona/manage/downloadClinicExcelFile.do')" alt="엑셀다운로드">
		</form>
	</div>
	
	<!-- 샘플다운로드 -->
	<div style="float:left;margin-left:10px;margin-bottom:10px;">
		<input class="btn btn-info btn-sm" type="button" id="sample_download" onClick="fn_sampleDownload('clinic')" value="엑셀샘플"> 
	</div>
	
	<!-- 강남구 입력 주소에 따른 거주지 대,소 정비 -->
	<div style="float:left;margin-left:10px;margin-bottom:10px;">
		<input class="btn btn-info btn-sm" type="button" id="gangnamgu_arrange" onClick="fn_gangnamgu_arrange()" value="강남구 데이터 정리"> 
	</div>
	
	<!-- 타지역 입력 주소에 따른 거주지 대,소 정비 -->
	<div style="float:left;margin-left:10px;margin-bottom:10px;">
		<input class="btn btn-info btn-sm" type="button" id="tasigu_arrange" onClick="fn_tasigu_arrange()" value="타시구 데이터 정리"> 
	</div>


<div class="contents" style="float:right;margin-right:22px;">
	<a href="/corona/write/clinicWrite.do" class="btn btn-primary btn-sm" id="insert">입력</a>
	<a href="#this" class="btn btn-primary btn-sm" id="delete"><i class="fa fa-eraser"></i> 선택삭제</a>	
</div>

<div class="contents" style="float:right;margin: 0 5 0 5;">	
	<a href="#this" class="btn btn-success btn-sm" id="addExcelImpoartBtn" onclick="check()">
		엑셀 업로드
	</a>
</div>	
<div class="contents" style="float:right;">

	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="/corona/manage/clinicExcelUp.do">
		<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}"> 
		<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
		<dl class="vm_name">
			<dd>
				<input id="excelFile" type="file" name="excelFile" />
			</dd>
		</dl>
	</form>
</div>

<div align="left" style="width:100%;padding-right:20px;">
	<div  style="margin-left:20px;" align="center">
		<div class="table-responsive m-b-40">
			 <table class="table table-borderless table-data3" id="selectable">
			     <thead style="background-color:#0B2161;">
			        <tr align="center">
			        		<th><input type="checkbox" name="_selected_all_"></th>
							<th style="width: 80px;">NO</th>
							<th>입력일자</th>
							<th>방문일자</th>
							<th>핸드폰번호</th>
							<th>이름</th>
							<th>성별</th>
							<th>생년월일</th>
							<th>직업</th>
							<th>검사케이스</th>
							<th>사례분류</th>
							<th>경로구분</th>
							<th>거주지대구분</th>
							<th>거주지소구분</th>
							<th>거주지주소</th>
							<th>거주지주소상세</th>
							<th>비고</th>
							<th>입력자ID</th>
							<th>기입일</th>
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
</div>	

	<%@ include file="/WEB-INF/include/include-body.jspf" %>




</body>
</html>