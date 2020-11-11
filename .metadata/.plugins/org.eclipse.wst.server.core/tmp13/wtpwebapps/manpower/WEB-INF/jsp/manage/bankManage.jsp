<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		
		fn_selectbankList(1);
		
		$("#NAME").keydown(function (key) {

			if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
				$("#PAGE_INDEX").val("1");
				fn_selectbankList(1);
			}
		});
		
		$("#search").on("click", function(e){ //검색 버튼
			e.preventDefault();
			fn_selectbankList(1);
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
		        url : "/manpower/manage/deletebankChk.do",
		        data: {
		        	val:checkedValue
		        },
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("삭제가 완료되었습니다.");
		        	fn_selectbankList(1);
	            },

		        error: function (data) {//추천실패
		        	alert("삭제가 실패 하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
	}
	
	//수정화면가기
	function fn_updatebank(UID) {
		var comSubmit = new ComSubmit();
		
		comSubmit.setUrl("<c:url value='/write/updatebankView.do' />");
		comSubmit.addParam("ID", UID);
		comSubmit.submit();
	}
	
	function fn_selectbankList(pageNo){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/manage/selectbankList.do' />");
		comAjax.addParam("PAGE_INDEX",pageNo);
		comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
		comAjax.addParam("bank_name", $("#BANK_NAME").val());
		
		comAjax.setCallback("fn_selectbankListCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_selectbankListCallback(data){
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
				eventName : "fn_selectbankList"
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
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CODE)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='javascript:void(0);' onclick='fn_updatebank("+ value.ID +")' name='BANK_NAME'>" + NvlStr(value.BANK_NAME) + "</a>"+
								   "</td>" +
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
						fn_selectbankList(1);
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
	</a>은행코드관리
</div>



<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:100%;display: inline-block;padding-top:10px;">
		<form action="" method="post">
			<div class="card-body card-block">
				<div>
					<div class="form-group" style="width: 360;float:left;margin-left:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width: 80px;">은행명</div>
							<input type="text" id="NAME" name="NAME" value="" class="form-control">
						</div>
					</div>
				</div>
			</div>
			<div class="form-actions form-group">
				<div style="float: left;margin-left:15px;">
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

<div class="contents" style="float:right;margin-right:22px;margin-bottom:10px;">
	<a href="/manpower/write/bankWrite.do" class="btn btn-primary btn-sm" id="insert">등록</a>
	<a href="#this" class="btn btn-primary btn-sm" id="delete"><i class="fa fa-eraser"></i> 선택삭제</a>
</div>


<div class="contents" style="float:right;margin: 0 5 0 5;">	
	<a href="#this" class="btn btn-success btn-sm" id="addExcelImpoartBtn" onclick="check()">
		엑셀 업로드
	</a>
</div>	
<div class="contents" style="float:right;">

	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="/manpower/manage/bankExcelUp.do">
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
							<th style="width: 280px;">은행코드</th>
							<th>은행명</th>
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