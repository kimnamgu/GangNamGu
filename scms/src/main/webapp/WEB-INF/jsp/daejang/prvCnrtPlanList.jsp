<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_deptList();
			
			fn_saupMonthList();
			
			fn_initial_setting();
			
			fn_selectPrvCnrtPlanList(1);
			
			
			
			$("#insert").on("click", function(e){ //신규입력
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
				fn_selectPrvCnrtPlanList();
			});
			
		   
			/*
			$("#SAUP_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtPlanList();
				}

			});*/
			
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
				fn_selectPrvCnrtPlanList();
			});	
 
		});
		
		
		
		function fn_deptList(){
		
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/common/deptList.do' />");
		comAjax.setCallback("fn_deptListCallback");			
		comAjax.ajax();
		}
	
		function fn_deptListCallback(data){
		data = $.parseJSON(data);
		var str = "";
		var i = 1;
				
		$.each(data.list, function(key, value){										
				str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME + "</option>";
				i++;
		});
		
		$("#SAUP_DEPT_CD").append(str);			
		}
		
		function fn_initial_setting(){
			
			$('input:radio[id=SAUP_FIELD1_GB1]').prop("checked", true);		
			//$("#SAUP_DEPT_CD").val($("#DEPT_ID").val());
		}
		
		function fn_saupMonthList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("/scms/common/dutyMonthList.do");
			comAjax.setCallback("fn_dutyMonthListCallback");			
			comAjax.ajax();
		}
		
		function fn_dutyMonthListCallback(data){
			data = $.parseJSON(data);
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.MONTHS + "\">" + dv_yrmonth(value.MONTHS, '.') + "</option>";
					i++;
			});
			
			$("#SAUP_YEAR").append(str);			
		}
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
	
	
	    	  
	    function fn_prvCnrtPlanUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "3");  //실적 1, 평가 2 발주계획 3
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanUpdate.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_selectPrvCnrtPlanList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtPlanList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtPlanListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());	
			
			if( $("input:radio[name='SAUP_FIELD1_GB']").is(":checked") == true){				
				comAjax.addParam("SAUP_FIELD1_GB", $("input:radio[name='SAUP_FIELD1_GB']:checked").val());			
			}
			comAjax.addParam("SAUP_DEPT_CD",$("#SAUP_DEPT_CD").val());
			comAjax.addParam("SAUP_YEAR", $("#SAUP_YEAR").val());			
			comAjax.addParam("SAUP_NM", $("#SAUP_NM").val());		      
			comAjax.addParam("WHETHER_YN", $("#WHETHER_YN").val());		      
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtPlanListCallback(data){			
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
					eventName : "fn_selectPrvCnrtPlanList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
					       tmp = "";
						
					       testSt = "";
					       if(value.DISP_YN == "N")
		   				   {
// 					    	   testSt = "<td><a href='#this' name='testBtA'><input type='hidden' name='title' id='UID' value=" + value.ID + "><input type='button' name='title' id='testM' value='승인'></a></td>"; 
					    	   testSt = "<td></td>" ;
		   				   }else{
					    	   testSt = "<td>승인완료</td>" ;
		   				   }
					       
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       
					       if(value.ATTACH == 'Y')
					    	   tmp = "<a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
					    	         "<input type='hidden' name='FID' id='FID' value=" + value.FID + ">";
												
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.RNUM  + "</td>" +						   			                
						   			   "<td>" + 
						   			   "<a href='#this' name='title'>" + dv_yrmonth(NvlStr(value.SAUP_YEAR), '.') + "</a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + out_gubun1(NvlStr(value.SAUP_FIELD1_GB)) + "</td>" +						   			
						   			   "<td class=\"list_center_sht\">" +
						   			   "<a href='#this' name='title'><font title=\"" + NvlStr(value.SAUP_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.SAUP_NM), 18) +  "</font></a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + comma(NvlStr(value.SAUP_BUDGET_AMT)) + "</td>" +
						   			   "<td>" + NvlStr(value.SAUP_DEPT_NM) + "</td>" +
						   			   "<td>" + formatDate(NvlStr(value.ACCEPT_ST_DATE), ".") + "</td>" +
						   			   "<td>" + formatDate(NvlStr(value.ACCEPT_ED_DATE), ".") + "</td>" +
						   				testSt + 
					                   "</tr>";					    
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtPlanUpdate($(this));
				});
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
											
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanWrite.do' />");
			comSubmit.submit();
		}

		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();		
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>발주계획</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  
  <tr>
  <td class="bodyframe_n">* 수의계약 관리시스템 입력 기준금액 : 330만원 ~ 2,200만원(부가세 포함)</td>
  </tr>

  <tr>
    <td class="pupup_frame" style="padding-right:12px">	
 
   
     <table width="600" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
	  <tr>
           <td width="120" class="tbl_field">유형</td>
           <td colspan="3" width="660" class="tbl_list_left">           	                 
               <input type="radio" id="SAUP_FIELD1_GB1" name="SAUP_FIELD1_GB" value="" class="input" onfocus="this.blur()"> 전체 &nbsp; <input type="radio" id="SAUP_FIELD1_GB2" name="SAUP_FIELD1_GB" value="1" class="input" onfocus="this.blur()"> 공사 &nbsp;<input type="radio" id="SAUP_FIELD1_GB3" name="SAUP_FIELD1_GB" value="2" class="input" onfocus="this.blur()"> 용역 &nbsp;<input type="radio" id="SAUP_FIELD1_GB4" name="SAUP_FIELD1_GB" value="3" class="input" onfocus="this.blur()"> 물품 &nbsp;               	                	               
           </td>
      </tr>     
	  <tr>
        <td width="120" class="tbl_field">발주부서</td>
        <td colspan="3" width="660" class="tbl_list_left">            
            <select id="SAUP_DEPT_CD" name="SAUP_DEPT_CD" class="input">
	        <option value="">--------</option>
	        </select> &nbsp;	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">발주시기</td>
        <td colspan="3" width="660" class="tbl_list_left">            
            <select id="SAUP_YEAR" name="SAUP_YEAR" class="input">
	           <option value="">--------</option>
	        </select>                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">사업명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="SAUP_NM" name="SAUP_NM" class="input" style="width:300;">	                          
        </td>
      </tr>      
       <tr>
        <td width="120" class="tbl_field">승인</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
           <select name="WHETHER_YN" id="WHETHER_YN">
		     <option value=""  selected>--------</option>
		     <option value="Y">승인완료</option>
		     <option value="N">미승인</option>
		   </select>
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
          <th>&nbsp;</th><th>발주시기</th><th>유형</th>
          <th>사업명</th><th>예산액(원)</th><th>소관부서</th><th>접수시작일</th>
          <th>접수마감일</th>    
          <th>승인</th>    
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