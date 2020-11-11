<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_selectViolBuildingUpList(1);
						
			$("#list").on("click", function(e){ //dlqfur
				e.preventDefault();				
				fn_selectViolBuildingList();					
			});
						
			$("#upload").on("click", function(e){ //dlqfur
				e.preventDefault();
			
				if(user_right == 'A')
				{
					fn_upload();
				}
				else
				{
					alert('권한이 없습니다.')
				}	
			});
			
			
			$("#sample").on("click", function(e){ //다운로드
				e.preventDefault();						
				window.location.assign('http://98.23.7.109:8099/bimsfile/img/sample11.xlsx');
				// window.location.href = 'http://98.23.7.109:8099/bimsfile/img/jucha0315.jpg';
				/*$.fileDownload('/Download.html?filePath=http://98.23.7.109:8099/bimsfile/img/jucha0315.jpg')
					.done(function() {alert('다운로드 성공');})
					.fail(function() {alert('다운로드 실패');});
				  	return false;*/
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();			       			       
			    });				
				//fn_initial_setting();
			});
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectViolBuildingUpList();
			});
					    
			$("#UP_DATE").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectViolBuildingUpList();
				}
			});
			
			$("#FILE_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectViolBuildingUpList();
				}
			});
			
						
			$('#close1').click(function() {
		        $('#pop1').hide();
		    });
			
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
						
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectViolBuildingUpList();
			});
						
			$("#UP_DATE").datepicker({				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});			 
		});
		
		
		
		function fn_down(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/exceldown.do' />");
			//comSubmit.addParam("COMP_FIELD1_GB",param1);
			//comSubmit.addParam("COMP_FIELD2_GB", tmp1);
			comSubmit.addParam("PAGE_INDEX","1");
			comSubmit.addParam("PAGE_ROW", "1000000");
			comSubmit.submit();
		}
		
		function fn_upload(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/violBuildingUpload.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_selectViolBuildingList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/violBuildingList.do' />");
			comSubmit.submit();
		}
		
	    function fn_inquiry(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/violBuildingList.do' />");			
			comSubmit.submit();
		}
		
	    
	
	    	  
	    function fn_deleteViolBuildingUpload(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("DEL_ID", "${sessionScope.userinfo.userId}");
			comSubmit.addParam("DEL_DEPT", "${sessionScope.userinfo.deptId}");
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
						
			if(confirm('데이터를 삭제하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/deleteViolBuildingUpload.do' />");								
				comSubmit.submit();
			}
		}
		
		
		function fn_fixedDateDataUpdate(obj){
			var comSubmit = new ComSubmit();
			var dep_id = obj.parent().find("#DEP_ID").val();
			
			if( dep_id == $('#DEPT_ID').val() || user_right == "A")
			{
				comSubmit.setUrl("<c:url value='/daejang/fixedDateDataUpdate.do' />");
				comSubmit.addParam("ID", obj.parent().find("#ID").val());
				comSubmit.addParam("DEP_ID", dep_id);
				comSubmit.submit();
			}
			else{
				alert('권한이 없습니다.');
			}
		}
		
		function fn_selectViolBuildingUpList(pageNo){
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectViolBuildingUpList.do' />");
			comAjax.setCallback("fn_selectViolBuildingUpListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			comAjax.addParam("UP_DATE", $("#UP_DATE").val());
			comAjax.addParam("FILE_NM", $("#FILE_NM").val());
			
			
			comAjax.ajax();
		}
		
		
		function fn_selectViolBuildingUpListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"21\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectViolBuildingUpList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
													      
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + ((total - value.RNUM)+1)  + "</td>" +
						   			   "<td>" + formatDate(NvlStr(value.INS_DATE),'.') + "</td>" +
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.ORIGINAL_FILE_NAME) + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.ID) + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +						   			  
					                   "</tr>"; 				    
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_deleteViolBuildingUpload($(this));
				});			
				
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/violBuildingWrite.do' />");
			comSubmit.submit();
		}

		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
						
			/*var cn = $.cookie('down'+idx);
			alert(cn + " " + idx);
			if(cn == null || cn == undefined)
			{
			$.cookie('down'+idx, 'Y',  { expires : 1, path : '/' });
			*/
				comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
				comSubmit.addParam("DGNS_NO", idx);
				comSubmit.submit();
			//}
		}			
		
		
		function fn_basicInfo(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("DGNS_NO", obj.parent().find("#NNO").val());						
			comSubmit.setUrl("<c:url value='/daejang/selfDgnsBasicInfo.do' />");			
			comSubmit.submit();
		}

	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<!-- <input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">-->
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/vbms/images/popup_title.gif" align="absmiddle"></a>위반건축물 관리시스템</td>
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
 
   
    <table width="520" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
      <tr>
        <td width="120" class="tbl_field">일자</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="UP_DATE" name="UP_DATE" class="input" style="width:300;">               
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">파일명</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="FILE_NM" name="FILE_NM" class="input" style="width:300;">	                          
        </td>
      </tr>
    </table>
    
    
      
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
   	   &nbsp;&nbsp;&nbsp;&nbsp;[${sessionScope.userinfo.deptName} &nbsp;${sessionScope.userinfo.userName}]
       </td>       
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
       	 <tr>
       	   <td colspan="4">          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="sample"><font color="white">엑셀샘플양식 다운로드</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
       	 </tr>
       
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/vbms/images/btn_type1_head.gif"></td>
               <td background="/vbms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/vbms/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td>            
           <c:choose>
           <c:when test="${chkAdmin eq 'A'}">      
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="upload"><font color="white">엑셀등록</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           </c:when>
           </c:choose>
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list"><font color="white">목록</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
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
    
	 <table class="tbl1">
     <thead>
        <tr>
          <th>&nbsp;</th><th>일자</th><th>파일명</th><th>아이디</th>          
        </tr>
      </thead>
      <tbody id='mytable'>     
      </tbody>
      </table>
	
	</td>
	</tr>
</table>
</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
			
</body>
</html>