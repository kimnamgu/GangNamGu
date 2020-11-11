<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var user_id = "${sessionScope.userinfo.userId}";
		
		$(document).ready(function(){
			
			fn_selectPrvCnrtCurrentBuildList();
			
			$("#logout").on("click", function(e){ //로그아웃				
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
			
			
			$("#excelUpload").on("click", function(e){ //엑셀 업로드
				e.preventDefault();
				checkForm();
			});
			
			//출력리스트 변경
			$("#DISP_CNT").change(function(){	
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtCurrentBuildList();
			});	
			
			//파일 다운
			$("a[name^='name']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
			$('input[name=_selected_all_]').on('change', function(){ 
				$('input[name=_selected_]').prop('checked', this.checked); 
			}); 
			
			//검색 클릭
			$("#search").on("click", function(e){ //파일 이름
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtCurrentBuildList();
			});
			
			$("#GONGSA_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtCurrentBuildList();
				}

			});

			
		});
		
		
		//로그아웃
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}

		//파일 다운로드
		function fn_downEstimateFile(fid){
			var idx = fid;		
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
		
		//리스트 조회
		function fn_selectPrvCnrtCurrentBuildList(){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtCurrentBuildList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtCurrentBuildListCallback");
			comAjax.addParam("GONGSA_NM", $("#GONGSA_NM").val());
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtCurrentBuildListCallback(data){	
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"15\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtCurrentBuildList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var tr_class = "";
				var td_class = "";
				
				$.each(data.list, function(key, value){
				       if ((value.RNUM % 2) == 0 ){
							tr_class = "tr1";
				       }else{
							tr_class = "tr2";
				       }
				       
			   			str += "<tr class=\"" + tr_class + "\">" +
			   					"<td><input type='checkbox' name='_selected_' value='"+value.ID+"'></td>" +
								"<td>" + value.SEQ  + "</td>" +						   			                
								"<td>" + value.YEAR + "</td>" +
								"<td style='text-align:left'>" + NvlStr(value.CONSTRUCT_NAME) + "</td>" +
								"<td style='text-align:left'>" + NvlStr(value.CONSTRUCT_LOCATION) + "</td>" +
								"<td style='text-align:left;width:400px;'><font title=\"" + NvlStr(value.IMPORTANT_CONSTRUCT) +  "\" style=\"CURSOR:hand;\">" + NvlStr(value.IMPORTANT_CONSTRUCT) + "</font></td>" +
								"<td style='text-align:right'>" + NvlStr(value.CONSTRUCT_COST) + "</td>";
								
								if(value.PARENT_ID != null){
									str +=  "<td style='text-align:center;width:150px;' id='estimate_"+value.ID+"'><a href='#this' onClick='fn_downEstimateFile("+value.FILE_ID+")'><font title=\"" + NvlStr(value.ORIGINAL_FILE_NAME) +  "\" style=\"CURSOR:hand;\"><img align=\"absmiddle\" src=\"/scms/images/ico_att.gif\"></font></a>";
									
									if(user_id =='wowjd' || user_id =='epvmzhs233'){//김재정 주임만 사용하도록 기능제어
										str += 	"&nbsp;&nbsp;<input type='button' value='삭제' onClick='fn_delEstimateFile("+value.PARENT_ID+","+value.ID+")' style='CURSOR:hand;'>";
									}
									
									str += "</td>";
									
									
								}else{
									str += 	"<td style='text-align:center;width:150px;' id='estimate_"+value.ID+"'>";
									
									if(user_id =='wowjd' || user_id =='epvmzhs233'){//김재정 주임만 사용하도록 기능제어
										str += 	"<input type='file' name='estimate' onChange='fn_selectEstimate(this,"+value.ID+")' />";
									}
									
									str += "</td>";
								}
					 
						str += 	"</tr>";	
							
				});
				body.append(str);
								
			}
		}
		
		//견적서 파일 삭제
		function fn_delEstimateFile(parent_id,id){
			var str = "";
			
			$.ajax({
		        type: "post",
		        url : "/scms/daejang/delEstFile.do",
		        data: {
		        	PARENT_ID : parent_id,
		        	BOARD_GB : 8
		        },
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	$('#estimate_'+id).empty();
		        
					if(user_id =='wowjd' || user_id =='epvmzhs233'){//김재정 주임만 사용하도록 기능제어
						str += 	"<input id='estimate_"+id+"' type='file' name='estimate' onChange='fn_selectEstimate(this,"+id+")' />";
					}
		        
		        	
		        	$('#estimate_'+id).append(str);
	            },

		        error: function (data) {//추천실패
		        	alert("견적서가 삭제되지 않았습니다.관리자에게 문의 바랍니다.");
	            }
	     	});
		}
		
		//견적서 파일 선택
		function fn_selectEstimate(obj,id){
			//파일 유효성 검사
			fileCheck(obj);
			
			var form = $('#form1')[0];
			var formData = new FormData(form);
			formData.append('SID', id);
			formData.append('BOARD_GB', 8);

			//ajax 통신으로 multipart form을 전송한다.
			$.ajax({
				type : 'POST',
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false,
				cache : false,
				timeout : 600000,
				url : '/scms/daejang/upEstFile.do',
				dataType : 'JSON',
				data : formData,
				success : function(data) {
						var str= "";
						$('#estimate_'+id).empty();
						str +=  "<a href='#this' onClick='fn_downEstimateFile("+data.FILE_ID+")'><font title=\"" + NvlStr(data.ORIGINAL_FILE_NAME) +  "\" style=\"CURSOR:hand;\"><img align=\"absmiddle\" src=\"/scms/images/ico_att.gif\"></font></a>";
						
						if(user_id =='wowjd' || user_id =='epvmzhs233'){//김재정 주임만 사용하도록 기능제어
							str += 	"&nbsp;&nbsp;<input type='button' value='삭제' onClick='fn_delEstimateFile("+data.PARENT_ID+","+id+")' style='CURSOR:hand;' >";
						}
						
						$('#estimate_'+id).append(str);
				},
		        error: function (data) {//추천실패
		        	alert("견적서 업로드가 실패하였습니다.\n급하게 업로드할경우 발생 가능한 오류 입니다.\n현상이 계속 발생할 경우 새브라우저를 시작하여 시도 부탁드립니다.");
	            }
			});
			

		}
		
		
		//파일 체크
		function fileCheck(input){
			var word;
            var version = "N/A";
            var agent = navigator.userAgent.toLowerCase();
            var name = navigator.appName;

		    if(input.value!=""){				
                // IE old version ( IE 10 or Lower )
                if ( name == "Microsoft Internet Explorer" ) word = "msie ";
                else {
                    // IE 11
                    if ( agent.search("trident") > -1 ) word = "trident/.*rv:";
                    // IE 12  ( Microsoft Edge )
                    else if ( agent.search("edge/") > -1 ) word = "edge/";
                }

                var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" );

                if (  reg.exec( agent ) != null  )
                    version = RegExp.$1 + RegExp.$2;

				if( version >= 10 ){
						
						fileSize = input.files[0].size;
					    var maxSize = 10 * 1024 * 1024;//10MB				 
					    if(fileSize > maxSize){					    
					    	alert("파일 사이즈가 10MB를 넘습니다!! [" + comma(fileSize) + " Byte]");
					    	fchk = 1;
					    }
					    else{
					    	fchk = 0;
					    }
			   	}
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
					alert("모든 데이터가 업로드 되었습니다.");
					fn_selectPrvCnrtCurrentBuildList();
				},
				type : "POST"
			};

			$("#excelUploadForm").ajaxSubmit(options);

		}
	}
	
	//엑셀 다운로드
    function doExcelDownloadProcess(mappingValue){
        var f = document.downloadExcelFile;
        f.action = mappingValue;
        f.submit();
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
		
		if (confirm("선택한 로우의 견적서 파일 까지 같이 삭제 됩니다.\n삭제 하시겠습니까?")) {

			//견적서 파일 삭제
			
			$.ajax({
		        type: "post",
		        url : "/scms/daejang/allListDel.do",
		        data: {
		        	val:checkedValue
		        },
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	fn_selectPrvCnrtCurrentBuildList();
	            },

		        error: function (data) {//추천실패
		        	alert("삭제가 실패 하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});

		} 
	}
	

</script>
</head>
<body bgcolor="#FFFFFF">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe" colspan="2">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="pupup_title">
				<a href="#this" class="btn" id="logout">
					<img src="/scms/images/popup_title.png" align="absmiddle">
				</a>
				동 주민센터
			</td>
		</tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  <tr>
  <td class="pupup_frame" style="padding-right:12px">	
   <table width="600" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
	 
      <tr>
        <td width="120" class="tbl_field">공사명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="GONGSA_NM" name="GONGSA_NM" class="input" style="width:300;">	                          
        </td>
      </tr>
    </table>
    
       <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">    
     <tr>
       <td align="left">
       </td>              
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>           
           <td>
           </td>          
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>           
                                                       
         </tr>
       </table>
       </td>
     </tr>
     <tr>
       <td class="margin_btn" colspan="2"></td>
     </tr>
   </table>
      
      
      
 </tr>
  <tr>
    <td class="pupup_frame" colspan="2" style="padding-right:12px">	
    
    <c:if test="${sessionScope.userinfo.userId eq 'wowjd' || sessionScope.userinfo.userId eq 'epvmzhs233'}"><!-- 김재정 주임만 사용하도록 기능제어 -->
	<!-- 엑셀 파일 폼 -->
	<div style="float:left;">
	   	<form id="downloadExcelFile" name="downloadExcelFile" method="post" enctype="multipart/form-data"></form>
	</div>
	
	
	<div class="contents" style="float:right;">
		<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="/scms/common/excelUploadAjax.do">
			<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}"> 
			<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
			<dl class="vm_name">
				<dd>
					<input id="excelFile" type="file" name="excelFile" />
				</dd>
			</dl>
		</form>
	</div>
	</c:if>

    
<!-- 테이블 폼 -->    
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
			
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">    
     <tr>
       <td align="left"><b>Total : <span id="tcnt"></span></b> &nbsp; 
       <select id="DISP_CNT" name="DISP_CNT" class="input">
       <option value="10">10</option>
       <option value="20">20</option>
       <option value="30">30</option>
       <option value="40">40</option>
       <option value="50">50</option>
   	   </select>
       </td> 
       
       <c:if test="${sessionScope.userinfo.userId eq 'wowjd' || sessionScope.userinfo.userId eq 'epvmzhs233'}"><!-- 김재정 주임만 사용하도록 기능제어 -->      
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
	   		<table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
             
				<td><img src="/scms/images/btn_type0_head.gif"></td>
				<td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap>
               		<a href="#this" class="btn" onclick="delChkRow()">
               			<font color="white">선택 삭제</font>
					</a>
				</td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
           		<td>&nbsp;&nbsp;</td>
           		
               <td><img src="/scms/images/btn_type0_head.gif"></td>
				<td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap>
               		<a href="#this" class="btn" id="addExcelDownBtn" onclick="doExcelDownloadProcess('/scms/common/downloadExcelFile.do')">
               			<font color="white">엑셀 다운로드</font>
					</a>
				</td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
           		<td>&nbsp;&nbsp;</td>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
				<td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap>
               		<a href="#this" class="btn" id="addExcelImpoartBtn" onclick="check()">
               			<font color="white">엑셀 업로드</font>
					</a>
				</td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>                                                  
         </tr>
       </table>
       </td>
       </c:if>
       
     </tr>
     <tr>
       <td class="margin_btn" colspan="2"></td>
     </tr>
   </table>
    
	 <table class="tbl1">
     <thead>
        <tr>
        	<th><input type="checkbox" name="_selected_all_"></th>
			<th>연번</th>
			<th>연도</th>
			<th>공사명</th>
			<th>공사위치</th>
			<th>주요 공사</th>
			<th>공사비(천원)</th>
			<th>견적서 파일</th>
		</tr>
      </thead>
      <tbody id='mytable'>     
      </tbody>
      </table>
</form>
	
	</td>
	</tr>
</table>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
			
</body>
</html>