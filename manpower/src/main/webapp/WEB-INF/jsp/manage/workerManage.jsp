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
		
		//검색초기화
		fn_initial_setting();
		
		fn_selectDomesticContactList(1);
		
		$("#NAME").keydown(function (key) {

			if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$("#PAGE_INDEX").val("1");
				fn_selectDomesticContactList(1);
			}
		});
		
		$("#search").on("click", function(e){ //검색 버튼
			e.preventDefault();
			fn_selectDomesticContactList(1);
		});
		
		$("#DISP_CNT").change(function(){
			$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
			fn_selectDomesticContactList(1);
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
		        url : "/manpower/manage/deleteDomesticChk.do",
		        data: {
		        	val:checkedValue
		        },
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("삭제가 완료되었습니다.");
		        	fn_selectDomesticContactList(1);
	            },

		        error: function (data) {//추천실패
		        	alert("삭제가 실패 하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
	}
	
	//수정화면가기
	function fn_updateDomestic(UID) {
		var comSubmit = new ComSubmit();
		
		comSubmit.setUrl("<c:url value='/write/updateDomesticView.do' />");
		comSubmit.addParam("ID", UID);
		comSubmit.submit();
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
	}
	
	
	function fn_selectDomesticContactList(pageNo){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/manage/selectDomesticContactList.do' />");
		comAjax.addParam("PAGE_INDEX",pageNo);
		comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
		comAjax.addParam("name", $("#NAME").val());
		
		comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
		comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
		comAjax.setCallback("fn_selectDomesticContactListCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_selectDomesticContactListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='32' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectDomesticContactList"
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
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='javascript:void(0);' onclick='fn_updateDomestic("+ value.ID +")' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.FINAL_CONTACT) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_START) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_END) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_TYPE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BALGUB_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_DEPART) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CLASS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_POSITION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CALL) + "</td>" +
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
						fn_selectDomesticContactList(1);
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
	
	//샘플다운로드
	function fn_sampleDownload(gubun){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/common/downloadSampleFile.do' />");
		comSubmit.addParam("gubun", gubun);
		comSubmit.submit();
	}
	
	//엑셀 다운로드
	function doExcelDownloadProcess(mappingValue){
		alert("다운로드 되는 엑셀은 조회조건에 영향을 받습니다.");
		
		$("input[name=name]", "form[name=excelDown]").val($("#NAME").val());
		$("input[name=from_date]", "form[name=excelDown]").val($("#FROM_DATE").val().replace(/-/gi, ""));
		$("input[name=to_date]", "form[name=excelDown]").val($("#TO_DATE").val().replace(/-/gi, ""));
		
	    var f = document.excelDown;
	    f.action = mappingValue;
	    f.submit();
	}
</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>


<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/manpower/images/popup_title.gif" align="absmiddle">
	</a>인력관리
</div>



<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:100%;display: inline-block;padding-top:10px;">
		<form action="" method="post">
			<div class="card-body card-block">
				<div>
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">사업명</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
	
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">사업부서</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">진행여부</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">차수</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">이름</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">생년월일</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
					
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">참여구분</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
				</div>
			</div>
			<div class="form-actions form-group">
				<div style="float: left;margin-left:15px;">
					<a href="#this" class="btn btn-danger" id="initial"><i class="fa fa-ban"></i> 검색초기화</a> 
					<a href="#this" class="btn btn-primary" id="search"><i class="fa fa-search"></i>검색</a>
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
		
			<input type="image" src="/manpower/images/common/btn_excel.gif" style="border:0;" onclick="doExcelDownloadProcess('/manpower/manage/downloadDomesticExcelFile.do')" alt="엑셀다운로드">
		</form>
	</div>

<div class="contents" style="float:right;margin-right:22px;margin-bottom:10px;">
	<a href="/manpower/write/workerWrite.do" class="btn btn-primary btn-sm" id="insert">등록</a>
	<a href="#this" class="btn btn-primary btn-sm" id="delete"><i class="fa fa-eraser"></i> 선택삭제</a>
</div>

<div class="contents" style="float:right;margin: 0 5 0 5;">	
	<a href="#this" class="btn btn-success btn-sm" id="addExcelImpoartBtn" onclick="check()">
		엑셀 업로드
	</a>
</div>	
<div class="contents" style="float:right;">

	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="/manpower/manage/workerExcelUp.do">
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
			        		<th style="width: 40px;"><input type="checkbox" name="_selected_all_"></th>
							<th style="width: 80px;">NO</th>
							<th>년도</th>
							<th>차수</th>
							<th>사업명</th>
							<th>사업부서</th>
							<th>이름</th>
							<th>생년월일</th>
							<th>가족</th>
							<th>참여구분</th>
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