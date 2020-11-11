<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_selectPrvCnrtContractList(1);
			
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();

			    });
			});
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtContractList();
			});
			
		    
			$("#inquiry").on("click", function(e){ //조회
				e.preventDefault();				
				fn_inquiry();
			});
			
		
			$("#COMP_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtContractList();
				}

			});
			
			$("#CNRT_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtContractList();
				}

			});
			
			$("#CNRT_DEPT_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtContractList();
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
				fn_selectPrvCnrtContractList();
			});	
 
		});
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		
		function fn_printList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftPrintList.do' />");
			comSubmit.submit();
		}
		
	    function fn_inquiry(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/serviceContractList.do' />");			
			comSubmit.submit();
		}
		
		
		
		function fn_selectPrvCnrtContractList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtContractList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtContractListCallback");
			comAjax.addParam("WGB", "${param.wgb}");
			comAjax.addParam("COMP_NM", $("#COMP_NM").val());			
			comAjax.addParam("CNRT_NM", $("#CNRT_NM").val());
			comAjax.addParam("CNRT_DEPT_NM", $("#CNRT_DEPT_NM").val());
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());					
		
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtContractListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			var dgb = "${param.wgb}";
			
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"8\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtContractList"
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
							
					       if (dgb == '1') { 
					    	   
					    	   str += "<tr class=\"" + tr_class + "\">" + 
				   			   		  "<td>" + value.RNUM  + "</td>" +						   			  
				   			          "<td>" + 
				   			   		  "<a href='#this' name='title' >" + NvlStr(value.COMP_NM) + "</a>" +
				   			   		  "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" +
				   			   		  "</td>" +
				   			          "<td>" + out_UH((NvlStr(value.COMP_FIELD1_GB))) + "</td>" +				   			  
				   			          "<td class=\"list_center_sht\"><font title=\"" + NvlStr(value.CNRT_AGENCY_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_AGENCY_NM), 18) + "</font></td>" +
						   			  "<td>" + dv_yrmonth(NvlStr(value.CNRT_YEAR), '.') + "</td>" + 
						   			  "<td class=\"list_center_sht\"><font title=\"" + NvlStr(value.CNRT_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_NM), 18) + "</font></td>" +
						   			  "<td>" + comma(NvlStr(value.CNRT_AMT)) + "</td>" +
						   			  "<td>" + "&nbsp;" + "</td>" +						    	   					   			 
					                  "</tr>"; 
					       }
					       else{
						   		str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.RNUM + "</td>" +						   			  
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.COMP_NM) + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" +
						   			   "</td>" +
						   			   "<td>" + out_UH((NvlStr(value.COMP_FIELD1_GB))) + "</td>" +
					   				   "<td>" + dv_yrmonth(NvlStr(value.CNRT_YEAR), '.') + "</td>" +						   			     
					   				   "<td class=\"list_center_sht\"><font title=\"" + NvlStr(value.CNRT_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_NM), 18) + "</font></td>" +
						   			   "<td>" + comma(NvlStr(value.CNRT_AMT)) + "</td>" +
						   			   "<td>" + out_eval(NvlStr(value.CNRT_EVAL)) + "</td>" +
						   			   "<td>" + NvlStr(value.CNRT_DEPT_NM) + "</td>" +						   				   						   			   						   			   
					                   "</tr>"; 
					       }    
							      
								    
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtTotalCompInfo($(this));
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
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
								
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("DGNS_NO", idx);
			comSubmit.submit();			
		}			
		
		
		function fn_prvCnrtTotalCompInfo(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "5");  //실적 1, 평가 2 발주계획 3 기술자보유 4 업체등록 5
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtTotalCompInfo.do' />");			
			comSubmit.submit();
		}

	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="US_ID" name="US_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>과업실적</td>
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
        <td width="120" class="tbl_field">업체명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="COMP_NM" name="COMP_NM" class="input" style="width:300;">	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">계약명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="CNRT_NM" name="CNRT_NM" class="input" style="width:300;">	                          
        </td>
      </tr>  
      <tr>
      	 <td width="120" class="tbl_field">부서명</td>
   		 <td colspan="3" width="660" class="tbl_list_left">	               
         	<input type="text" id="CNRT_DEPT_NM" name="CNRT_DEPT_NM" class="input" style="width:300;">	                          
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
       </td>       
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type1_head.gif"></td>
               <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/scms/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
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
    
	 <table class="tbl1">
     <thead>     
     	<c:choose> 
            <c:when test="${param.wgb eq '1'}">
        <tr>
          <th>&nbsp;</th><th>업체명</th><th>유형</th><th>발주처</th>
          <th>계약년월</th><th>계약명</th><th>계약금액(원)</th><th></th>         
        </tr>
      		</c:when>
      		<c:when test="${param.wgb eq '2'}">
        <tr>
          <th>&nbsp;</th><th>업체명</th><th>유형</th><th>계약년월</th>
          <th>계약명</th><th>계약금액(원)</th><th>평가</th><th>소관부서</th>          
        </tr>
        		</c:when>
        </c:choose>
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