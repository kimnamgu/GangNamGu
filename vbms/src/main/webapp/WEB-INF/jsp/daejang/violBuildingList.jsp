<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_selectViolBuildingList(1);
			
			$("#juso").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();				
				
				if(user_right == 'A')
				{
					fn_juso();
				}
				else
				{
					alert('권한이 없습니다.')
				}				
			});
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#insert").on("click", function(e){ //dlqfur
				e.preventDefault();
			
				if(user_right == 'A')
				{
					fn_insert();
				}
				else
				{
					alert('권한이 없습니다.')
				}	
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
			
			
			$("#down").on("click", function(e){ //dlqfur
				e.preventDefault();
			
				if(user_right == 'A')
				{
					fn_down();
				}
				else
				{
					alert('권한이 없습니다.')
				}	
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
				fn_selectViolBuildingList();
			});
			
		    		
			$("#BLD_DONG").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectViolBuildingList();
				}

			});
			
			$("#PERFORM_PERSON").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectViolBuildingList();
				}

			});
			
			$("#BLD_ADDR1").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectViolBuildingList();
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
				fn_selectViolBuildingList();
			});
			
			$("#sample").on("click", function(e){ //다운로드
				e.preventDefault();						
				//window.location.assign('http://98.23.7.109:8099/bimsfile/img/sample11.xlsx');
				location.href = 'http://98.23.7.109:8099/bimsfile/img/sample11.xlsx';
				/*$.fileDownload('/Download.html?filePath=http://98.23.7.109:8099/bimsfile/img/jucha0315.jpg')
					.done(function() {alert('다운로드 성공');})
					.fail(function() {alert('다운로드 실패');});
				  	return false;*/
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
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		function fn_juso(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/sample.do' />");							
			comSubmit.submit();
		}
		
		function fn_printList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftPrintList.do' />");
			comSubmit.submit();
		}
		
		function fn_upload(){
			var comSubmit = new ComSubmit();
			//comSubmit.setUrl("<c:url value='/daejang/violBuildingUpload.do' />");
			comSubmit.setUrl("<c:url value='/daejang/violBuildingUpList.do' />");
			comSubmit.submit();
		}
		
	    function fn_inquiry(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/violBuildingList.do' />");			
			comSubmit.submit();
		}
		
	    
	
	    	  
	    function fn_violBuildingContent(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.setUrl("<c:url value='/daejang/violBuildingContent.do' />");			
			comSubmit.submit();
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
		
		function fn_selectViolBuildingList(pageNo){
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectViolBuildingList.do' />");
			comAjax.setCallback("fn_selectViolBuildingListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			comAjax.addParam("BLD_DONG", $("#BLD_DONG").val());
			comAjax.addParam("PERFORM_PERSON", $("#PERFORM_PERSON").val());
			comAjax.addParam("BLD_ADDR1", $("#BLD_ADDR1").val());
			
			comAjax.ajax();
		}
		
		
		function fn_selectViolBuildingListCallback(data){			
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
					eventName : "fn_selectViolBuildingList"
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
								
						   if ( NvlStr(value.STATE) == "01" || NvlStr(value.STATE) == "02" || NvlStr(value.STATE) == "03")
						   {
								//td_class = "<td class=\"info\">";
								td_class = "<td>";
						   }
						   else{
								td_class = "<td>";	
						   }
					       tmp = "";
												
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + ((total - value.RNUM)+1) + "</a>" + 
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" +
						   			   "</td>" +									     					                   
						   			   td_class + NvlStr(value.BLD_DONG) + "</td>" +	                   
						   			   td_class + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.BLD_ADDR1) + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   td_class + NvlStr(value.BLD_ADDR_ROAD) + "</td>" +
						   			   td_class + "<p class=\"ellipsis\" title=\"" + NvlStr(value.BLD_OWNER) + "\">" + NvlStr(value.BLD_OWNER) + "</p></td>" +								   			 
						   			   td_class + "<p class=\"ellipsis\" title=\"" + NvlStr(value.MBLD_ADDR_ROAD) + "\">" + NvlStr(value.MBLD_ADDR_ROAD) + "</p></td>" +						   			   
						   			   td_class + "<p class=\"ellipsis\" title=\"" + NvlStr(value.S_BLD_STRUCTURE) + "\">" + NvlStr(value.S_BLD_STRUCTURE) + "</p></td>" +
						   			   td_class + NvlStr(value.VIOL_AREA) + "</td>" +
						   			   td_class + NvlStr(value.S_PURPOSE) + "</td>" +						   			  
						   			   td_class + NvlStr(value.LOCATION) + "</td>" +					                  
						   			   td_class + NvlStr(value.EXPOSURE_DETAILS) + "</td>" +						   			   
						   			   td_class + formatDate(NvlStr(value.PRE_CORRTN_ORDER),".") + "</td>" +					                   
						   			   td_class + formatDate(NvlStr(value.CORRTN_ORDER),".") + "</td>" +						   			   						   			  						   			  
						   			   td_class + formatDate(NvlStr(value.OPINION_STATE_LIMIT),".") + "</td>" +
						   			   td_class + formatDate(NvlStr(value.EXEC_IMPOSE_DATE),".") + "</td>" +
						   			   td_class + comma(NvlStr(value.EXEC_IMPOSE_AMT)) + "</td>" +						   			  
						   			   td_class + NvlStr(value.S_STATE) + "</td>" +
						   			   td_class + NvlStr(value.BIGO) + "</td>"; 
						   			   
									   
					                 str += tmp + "</tr>"; 
					                   
							      
								    
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_violBuildingContent($(this));
				});
				
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
				
				
				$("a[name='no1']").on("click", function(e){ //번호
					e.preventDefault();
					fn_basicInfo($(this));
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
<input type="hidden" id="US_ID" name="US_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="DGNS_NO" name="DGNS_NO" value="">
<input type="hidden" id="SBJ_SEQ" name="SBJ_SEQ" value="">
<input type="hidden" id="BOT_IMG" name="BOT_IMG" value="">
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
        <td width="120" class="tbl_field">법정동</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="BLD_DONG" name="BLD_DONG" class="input" style="width:300;">               
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">담당자</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="PERFORM_PERSON" name="PERFORM_PERSON" class="input" style="width:300;">	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">주소</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="BLD_ADDR1" name="BLD_ADDR1" class="input" style="width:300;">    	                          
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
          <td colspan="5">          
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
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="upload"><font color="white">엑셀업로드</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="down"><font color="white">엑셀다운</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="insert"><font color="white">입력</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>         
           <td>          
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
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
          <th>&nbsp;</th><th>법정동</th><th>주소</th>
          <th>도로명</th><th>건축주</th><th>건축주주소</th><th>구조</th><th>면적</th>
          <th>용도</th><th>층</th><th>적발방법</th>
          <th>시정명령 사전예고</th><th>시정명령</th>
          <th>이행강제금부과 사전예고</th><th>이행강제금부과일</th>
          <th>이행강제금</th><th>처리결과</th><th>비고</th>    
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