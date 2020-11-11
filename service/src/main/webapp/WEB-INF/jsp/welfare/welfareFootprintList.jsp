<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var dept_name = "${sessionScope.userinfo.deptName}";
		var user_id = "${sessionScope.userinfo.userId}";
		var ws;
	    var messages = document.getElementById("message");
	    var r_jsonData = null;
	        		
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
			
			
			
			
						
			fn_initial_setting();
			fn_selectWelfareViewList(1);
			
			
			$("#message").on("DOMSubtreeModified propertychange", function() {
                if($("#message").text() != 'Socket open!!!')
                {
                	fn_selectWelfareViewList(1);                	
                }	
            });
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
					fn_initial_setting();
			    });
			});
			
			
			$("#APPLY_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectWelfareViewList(1);
			});
			
			$("#APPLY_DATE").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectWelfareViewList(1);
				}

			});
			
			/* 팝업 닫기 버튼 */
			$('#close2').click(function() {
				$('#pop1').hide();
		    });
			
			/* 로그아웃 */
			$("#logout").on("click", function(e){
				e.preventDefault();				
				fn_logout();
			});
			
			
			$(window).bind("beforeunload", function ()
			{
				if (event.clientY < 0) {
					
					var comAjax = new ComAjax();
					//alert( '로그아웃 됩니다.' );
					comAjax.setUrl("<c:url value='/logout.do' />")
					comAjax.ajax();			
				}
									
			});
			
		
			$("#DISP_CNT").change(function(){
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectWelfareViewList(1);
			});	
 			
			
			$("#SAUP_DEPT_CD").change(function(){
				fn_selectWelfareViewList(1);
			});	
			
		});
		
		
		function fn_initial_setting(){
			$("#ID").val("");
			//From의 초기값을 오늘 날짜로 설정
		    $('#FROM_DATE').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
		    //To의 초기값을 내일로 설정
		    $('#TO_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
			
		    $("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
			$("#PAGE_INDEX").val("1");
			$('input:radio[id=DONE1_YN]').prop("checked", true);
		}
		
		//입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	       
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	 
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			//data = $.parseJSON(data);
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value='" + value.DEPT_ID + "'>" + value.DEPT_NAME + "</option>";
					i++;
			});
			
			$("#SAUP_DEPT_CD").append(str);			
		}
		
		
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		
		function fn_popDetail(obj){
			
			var json = $.parseJSON(obj.parent().find("#UID1").val());
			var body = $("#mytable2");
			var str = "";
			var i = 1;
					
			$('#pop1').show();
			body.empty();	
		
			$.each(json, function(key, value){
				str += "<tr>" +
				   "<td align='center' style='padding:5px 10px;'>" + i + "</td>" +
				   "<td style='padding:5px 10px;'>" + getOptionName(key) + "</td>";
				   
				  //주민번호 뒷자리 한글로 변환
				  if(NvlStr(key) =="resident_back_number"){
						  switch (value){
						    case "M" :
						    	value = "본인";
						        break;
						    case "F" :
						    	value = "세대원";
						        break;
						    case "N" :
						    	value = "미포함";
						        break;
						}
				  }
				  
				   
	   			   if(NvlStr(value) == "Y"){
	   					str += "<td align='center' style='padding:5px 10px;'><span class='status--process'>" + NvlStr(value) + "</span></td>";
	   			   }else{
	   					str += "<td align='center' style='padding:5px 10px;'><span class='status--denied'>" + NvlStr(value) + "</span></td>";
	   			   }
				   
                   str += "</tr>";
				   i++;					
			});
						
			body.append(str);			
		}
		
		
	    function getOptionName(str){
	    	console.log(r_jsonData);
	    	var jsonobj = $.parseJSON(r_jsonData);
	    	var tmp= "";
	    	
	    	$.each(jsonobj.list, function(key, value){										
	    		if(str == value.item_bg)
	    		{	
	    			tmp = value.item_nm;
	    			return false;
	    		}
	    	});
	    	
	    	return tmp;
	    }
		
		
		function fn_getDocuOptionKindList(){
	    	
	    	var jsnobj = new Object();
	    	
	    	jsnobj.item_gb = "D";
	    	jsnobj.page_index = 1;
	    	jsnobj.page_row = 20;
	   	
			var jsonData = JSON.stringify(jsnobj);
			r_jsonData = null;
			
			$.ajax({
			        type:"POST",			        
			        url:"/service/api/v1/getDocumentKindList.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
			        	r_jsonData = JSON.stringify(data);			     											
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});									
	    }
		
		/* 상태값 변경 */
		function fn_welfareUpdate(seq_no, confirm_yn){
	    	var jsnobj = new Object();
	    	var userId = $("#USER_ID").val();
	    	var sayu = $("#sayu_"+ seq_no).val();
	    	var dong = $("#dong_"+ seq_no).val();
	    	var name = document.getElementById("name_"+ seq_no).innerText;
	    	var phone = document.getElementById("phone_"+ seq_no).innerText;
	    	var gubun = document.getElementById("gubun_"+ seq_no).innerText;

	    	
	    	if(confirm_yn == 'N' && sayu == ''){
	    		alert("미완료시 사유를 작성해주셔야 합니다.")
	    		$("#sayu_"+ seq_no).focus();
	    		return false;
	    	}
	    	
	    	jsnobj.seq_no = seq_no;
	    	jsnobj.confirm_yn = confirm_yn;
	    	jsnobj.userId = userId;
	    	jsnobj.sayu = sayu;
	    	
	    	jsnobj.dong = dong;
	    	jsnobj.name = name;
	    	jsnobj.phone = phone;
	    	jsnobj.gubun = gubun;
	    	
			var jsonData = JSON.stringify(jsnobj);
			
			$.ajax({
			        type:"POST",
			        url:"/service/welfare/processDocIssueReserve.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
						fn_selectWelfareViewList(1);
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

	    }
		
		
		/* 정보 변경 */
		function fn_welfareInfoUpdate(seq_no,confirm_yn){
	    	var jsnobj = new Object();
	    	var userId = $("#USER_ID").val();
	    	var dong = $("#dong_"+ seq_no).val();
	    	var name = document.getElementById("name_"+ seq_no).innerText;
	    	var gubun = document.getElementById("gubun_"+ seq_no).innerText;
	    	var sayu = $("#sayu_"+ seq_no).val();

	    	jsnobj.seq_no = seq_no;
	    	jsnobj.userId = userId;
	    	jsnobj.dong = dong;
	    	jsnobj.name = name;
	    	jsnobj.gubun = gubun;
	    	jsnobj.sayu = sayu;
	    	jsnobj.confirm_yn = confirm_yn;
	    	
			var jsonData = JSON.stringify(jsnobj);
			
			$.ajax({
			        type:"POST",
			        url:"/service/welfare/updateWelfareInfo.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
			        	alert("수정되었습니다.");
						fn_selectWelfareViewList(1);
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

	    }
		
		/* 리스트 조회 */
		function fn_selectWelfareViewList(pageNo){	
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/welfare/selectWelfareViewList.do' />");
			comAjax.addParam("deptName",dept_name);
			comAjax.addParam("userId",user_id);
			comAjax.addParam("PAGE_INDEX",pageNo);
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());	
			comAjax.addParam("id", $("#ID").val());
			
			comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
			comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
			
			
			if( $("input:radio[name='DONE_YN']").is(":checked") == true){				
				comAjax.addParam("done_yn", $("input:radio[name='DONE_YN']:checked").val());			
			}	
			
			comAjax.setCallback("fn_selectWelfareViewListCallback");
			
			comAjax.ajax();
		}
		
		/* 리스트 조회 callback */
		function fn_selectWelfareViewListCallback(data){			
			//data = $.parseJSON(data);			
			var total = data.total;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan='21' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectWelfareViewList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var tr_class = "";
				var tot_amt = 0;
				var jsonData = null;
				var jsonStr = null;
				
				// 사유컬럼 추가한다음 바꿔야함
				var testStr = "";

				var userAddress = "";
				var userSex = "";
				var sayu = "";
				
				
				$.each(data.list, function(key, value){
						testStr = value.DEL_ID;
						userSex = value.SEX;
						userAddress = value.ADDRESS;
						sayu = value.SAYU;
						
						if(testStr == '' || testStr == null ){
							testStr = '';
						}
						if(userSex == '' || userSex == null ){
							userSex = '';
						}
						if(userAddress == '' || userAddress == null ){
							userAddress = '';
						}
						
						if(userAddress == '' || userAddress == null ){
							userAddress = '';
						}
						
						
					
					       if ((value.rnum % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					      
						   
					       str += "<tr class='" + tr_class + "'>" + 
									   "<td align='center' style='color:#222222;' id='name_"+value.SEQ_NO+"'>" + value.NAME + "</td>" +
									   "<td align='center' style='color:#222222;'>" + NvlStr(value.INS_DATE) + "</td>" +
									   "<td align='center' style='color:#222222;'>" + value.BIRTH + "</td>" +
									   "<td align='center' style='color:#444444;' id='phone_"+value.SEQ_NO+"'>" + value.PHONE + "</td>" +	
									   "<td align='center' style='color:#444444;'>" + userAddress + "</td>";	
									   
						   if(user_id == 'epvmzhs233' || user_id == '18025190' ){
							   str += "<td align='center' style='color:#444444;' width='120px;'>" +
									   " <input class='form-control' id='dong_"+value.SEQ_NO+"' value='"+NvlStr(value.DONG)+"' type='text' >" +
									   "</td>" ;
							   str += "<td align='center' style='color:#444444;' id='gubun_"+value.SEQ_NO+"' width='120px;'>" + NvlStr(value.GUBUN_NAME) + "</td>";
							   str += "<td align='center' style='color:#444444;'>" + 
									   "<a href='javascript:void(0);' class='btn btn-primary btn-sm' onclick='fn_welfareInfoUpdate(" +value.SEQ_NO+ ",\""+value.CONFIRM_YN+"\");'>수정</a>"+
									   "</td>";	
						   }else{
							   str += "<td align='center' style='color:#444444;' width='120px;'>" + NvlStr(value.DONG) +"</td>" ;
							   str += "<td align='center' style='color:#444444;' id='gubun_"+value.SEQ_NO+"' width='120px;'>" + NvlStr(value.GUBUN_NAME) + "</td>";
							   str += "<td align='center' style='color:#444444;'></td>";	
						   }
						   
							if(value.CONFIRM_YN == '' || value.CONFIRM_YN == null ){
								str +=  "<td align='center' style='color:#444444;'>"+
									   	"<a href='javascript:void(0);' class='btn btn-success btn-sm' onclick='fn_welfareUpdate(" +value.SEQ_NO+ ",\"Y\");'>완   료</a>"+
									   	"&nbsp;<a href='javascript:void(0);' class='btn btn-warning btn-sm' onclick='fn_welfareUpdate(" +value.SEQ_NO+ ",\"N\");'>미완료</a>"+
									   	"</td>" ;
							}		
							
							if(value.CONFIRM_YN == 'Y'){
								str +=  "<td align='center' style='color:#444444;'>완료</td>" ;
							}
							if(value.CONFIRM_YN == 'N'){
								str +=  "<td align='center' style='color:#444444;'>미완료</td>" ;
							}	
									   
									   

							str +=  "<td align='center' style='color:#444444;' >" +
								   " <input class='form-control' id='sayu_"+value.SEQ_NO+"' value='"+NvlStr(value.SAYU)+"' type='text' >" +
								   "</td>" +
								   "</td>"

				});
				body.append(str);
				
				$("#t_amt").text(comma(tot_amt));
				
				$("a[name='title1']").on("click", function(e){ //제목 
					e.preventDefault();					
					fn_popDetail($(this));
					fn_prvCnrtPlanUpdate($(this));
				});
				
			}
		}
	        
	        
		function fn_prvCnrtPlanUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanUpdateTe.do' />");			
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
	</a>복지급여 신청관리
</div>

<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:680px;display: inline-block">
		<div class="card-header" style="background-color:#0B2161;color:white;margin-bottom:10px;">검색</div>
		
	<form action="" method="post">
		<div class="card-body card-block" style="padding:5px;">
				<div style="float:left;">
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
					<div class="form-actions form-group">
					<div class="form-group" style="margin-bottom:15px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;padding:3px;">완료여부</div>&nbsp;&nbsp;&nbsp;
				          	<div class="form-check-inline form-check">
								<label for="DONE1_YN" class="form-check-label ">
					            	<input type="radio" id="DONE1_YN" name="DONE_YN" value="ALL" class="form-check-input" onfocus="this.blur()"> 전체 &nbsp;
					           	</label>
								<label for="DONE1_YN" class="form-check-label ">
					            	<input type="radio" id="DONE1_YN" name="DONE_YN" value="ING" class="form-check-input" onfocus="this.blur()"> 처리중 &nbsp;
					           	</label>
					           	<label for="DONE2_YN" class="form-check-label "> 
					            	<input type="radio" id="DONE2_YN" name="DONE_YN" value="N" class="form-check-input" onfocus="this.blur()"> 미완료 &nbsp;
					            </label>
					            <label for="DONE3_YN" class="form-check-label ">
					            	<input type="radio" id="DONE3_YN" name="DONE_YN" value="Y" class="form-check-input" onfocus="this.blur()"> 완료 &nbsp;
					           	</label>
				           	</div>
						</div>
					</div>
					<div style="padding-left:5px;padding-bottom:10px;float:left;">
						<a href="#this" class="btn btn-danger" id="initial"><i class="fa fa-ban"></i> 검색초기화</a>
						<a href="#this" class="btn btn-primary" id="search"><i class="fa fa-search"></i> 검색</a>
					</div>
				</div>
				</div>
				<div style="padding-left:15px;float:left;">
					
				</div>
			</div>
				
			</form>
	</div>
</div>

<table>
  <tr>
    <td class="pupup_frame" style="padding-left:20px">	
	
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">     
     <tr style="height:50px;">
       <td align="left">
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
       </td>
     </tr>
     <tr>
       <td class="margin_btn" colspan="2"></td>
     </tr>
   </table>
    

	
	</td>
	</tr>
</table>
<div class="col-md-12" align="center">
	<div class="table-responsive m-b-40">
		 <table class="table table-borderless table-data3">
	     <thead style="background-color:#0B2161;">
	        <tr align="center">
							<th>성명</th>
							<th>신청날짜</th>
							<th>생년월일</th>
							<th>연락처</th>
							<th>주소</th>
							<th>행정동</th>
							<th>구분명</th>
							<th></th>
							<th>처리구분</th>
							<th>사유</th>
						</tr>
	      </thead>
	      <tbody id='mytable'>     
	      </tbody>
	      </table>
      </div>
</div>

</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	

	
<!-- POPUP --> 
<div id="pop1" style="position:absolute;left:550px;top:200;z-index:200;display:none;background-color:#e5e5e5;">
	<table width=500 height=250  border="1" cellpadding=2 cellspacing=0 class="table table-data2">
		<thead>
			<tr class="list_tit_tr">
				<th style="padding:5px 10px;" class="list_tit"></th>
				<th style="padding:5px 10px;" class="list_tit_bar">항목명</th>
				<th style="padding:5px 10px;" class="list_tit_bar">값</th>
			</tr>
		</thead>
		<tbody id='mytable2'>
		</tbody>
		<tr>
			<td colspan="3" style="padding:5px" align=right bgcolor=white><a href="#" id="close2"><B>[닫기]</B></a>
			</td>
		</tr>
	</table>
</div>
</body>
</html>