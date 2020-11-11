<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_initial_setting();
			
			fn_selectPrvCnrtCompInsList(1);
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#insert").on("click", function(e){ //입력
				e.preventDefault();			
				fn_insert();				
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
				fn_selectPrvCnrtCompInsList();
			});
			
		    
			$("#inquiry").on("click", function(e){ //조회
				e.preventDefault();				
				fn_inquiry();
			});
			
					
			$("#COMP_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtCompInsList();
				}

			});
						
			$("#COMP_SAUP_NO").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtCompInsList();
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
				fn_selectPrvCnrtCompInsList();
			});	
 			
			
			$("#UH4").on("click", function(e){ //체크박스
				if($('input:checkbox[id="UH4"]').is(":checked") == true )
				{		
					$('input:checkbox[id="UH1"]').attr("checked", true);
					$('input:checkbox[id="UH2"]').attr("checked", true);
					$('input:checkbox[id="UH3"]').attr("checked", true);
				}
				else
				{
					$('input:checkbox[id="UH1"]').attr("checked", false);
					$('input:checkbox[id="UH2"]').attr("checked", false);
					$('input:checkbox[id="UH3"]').attr("checked", false);					
				}	
			});
			
			
			$("#UH27").on("click", function(e){ //체크박스
				if($('input:checkbox[id="UH27"]').is(":checked") == true )
				{		
					$('input:checkbox[id="UH21"]').attr("checked", true);
					$('input:checkbox[id="UH22"]').attr("checked", true);
					$('input:checkbox[id="UH23"]').attr("checked", true);
					$('input:checkbox[id="UH24"]').attr("checked", true);
					$('input:checkbox[id="UH25"]').attr("checked", true);
					$('input:checkbox[id="UH26"]').attr("checked", true);
				}
				else
				{
					$('input:checkbox[id="UH21"]').attr("checked", false);
					$('input:checkbox[id="UH22"]').attr("checked", false);
					$('input:checkbox[id="UH23"]').attr("checked", false);
					$('input:checkbox[id="UH24"]').attr("checked", false);
					$('input:checkbox[id="UH25"]').attr("checked", false);
					$('input:checkbox[id="UH26"]').attr("checked", false);
				}
			});
		});
		
		function fn_initial_setting(){
			//$('input:radio[name=COMP_FIELD2_GB]:input[value=""]').attr("checked", true);
			$("#COMP_FIELD2_GB5").attr('checked', true);
		}
		
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
		
	    

	   	    	  
	    function fn_ServiceContractBasicInfo(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "5");  //실적 1, 평가 2 발주계획 3 기술자보유 4 업체등록 5 현장점검 6
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompInfo.do' />");			
			comSubmit.submit();
		}
				
		function fn_selectPrvCnrtCompInsList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			var tmp1 = "";
			var param1 = "";
			var param2 = "";
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtCompInsList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtCompInsListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());					
			
			tmp1 = get_check_val();			
			param1 = gb_Query(tmp1);
			
			tmp1 = get_check_val2();
			//param2 = gb_Query2(tmp1);
			
			comAjax.addParam("COMP_FIELD1_GB", param1);
			comAjax.addParam("COMP_FIELD2_GB", tmp1);
			comAjax.addParam("COMP_NM",$("#COMP_NM").val());
			comAjax.addParam("COMP_SAUP_NO",$("#COMP_SAUP_NO").val());
			
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtCompInsListCallback(data){			
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
					eventName : "fn_selectPrvCnrtCompInsList"
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
						   			   "<td>" + value.RNUM + "</td>" +
						   			 "<td>" + out_UH(NvlStr(value.COMP_FIELD1_GB)) + "</td>" +
						   			   "<td class=\"list_left\">" + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.COMP_NM) + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + NvlStr(value.COMP_HEAD_NM) + "</td>" +						   			  
						   			   "<td>" + "&nbsp; " + "</td>" +
						   			   "<td>" + "&nbsp; " + "</td>" +
						   			   "<td>" + "&nbsp; " + "</td>" +
						   			   "<td>" + "&nbsp; " + "</td>" +
					                   "</tr>"; 
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_ServiceContractBasicInfo($(this));
				});
				
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
				
				
				$("a[name='no1']").on("click", function(e){ //번호
					e.preventDefault();
					//fn_basicInfo($(this));
				});
				
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompWrite.do' />");
			comSubmit.submit();
		}

		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
	
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("DGNS_NO", idx);
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
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>업체등록</td>
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
           <td width="120" class="tbl_field">유형</td>
           <td colspan="3" width="660" class="tbl_list_left">           	                 
               <input type="checkbox" id="UH4" name="UH4" class="input"> 전체 &nbsp; <input type="checkbox" id="UH1" name="UH1" class="input"> 공사 &nbsp; <input type="checkbox" id="UH2" name="UH2" class="input"> 용역 &nbsp; <input type="checkbox" id="UH3" name="UH3" class="input"> 물품               	                	               
           </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">세부유형</td>
        <td colspan="3" width="660" class="tbl_list_left">
        	<input type="checkbox" id="UH27" name="UH27" class="input"> 전체 &nbsp; <input type="checkbox" id="UH21" name="UH21" class="input"> 디자인 &nbsp; <input type="checkbox" id="UH22" name="UH22" class="input"> 인쇄물 &nbsp; <input type="checkbox" id="UH23" name="UH23" class="input"> 동영상                       
            <input type="checkbox" id="UH24" name="UH24" class="input"> 행사기획 &nbsp; <input type="checkbox" id="UH25" name="UH25" class="input"> 종합홍보 &nbsp; <input type="checkbox" id="UH26" name="UH26" class="input"> 기타
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">업체명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="COMP_NM" name="COMP_NM" class="input" style="width:300;">	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">사업자번호</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="COMP_SAUP_NO" name="COMP_SAUP_NO" class="input" style="width:300;">	                          
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
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="insert"><font color="white">신규등록</font></a></td>
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
        <tr>
          <th>&nbsp;</th><th>업무유형</th><th>업체명</th>
          <th>대표자명</th><th>&nbsp; </th><th>&nbsp; </th><th>&nbsp; </th><th>&nbsp; </th> 
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