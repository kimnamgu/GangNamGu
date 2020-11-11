<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
	
		$(document).ready(function(){
			$('input:radio[name=TRUST_CONTINUE_YN]:input[value=""]').attr("checked", true);
			$('input:radio[name=BG_YEAR]:input[value="01"]').attr("checked", true);
			
			fn_selectOfficeworkRegList(1);
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();				
				$("#PAGE_INDEX").val("1");
				fn_selectOfficeworkRegList();
				//fn_srchTrustOworkStatusList();
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();				
				$("#form1").each(function() {				 
			       this.reset();  
			    });  
			});
			
					
			$("#print").on("click", function(e){ //프린트 버튼
				e.preventDefault();				
				fn_print();
			});
			
			
			$("#excel").on("click", function(e){ //엑셀다운 버튼
				e.preventDefault();
				fn_exceldown();
			});
		
			$("#IN_ST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#IN_ED_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#TRUST_STOP_STDATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#TRUST_STOP_EDDATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			
			
		});
		
		function fn_print(){     
			var initBody = document.body.innerHTML;
			
			window.onbeforeprint = function(){
				document.body.innerHTML = document.getElementById('printarea').innerHTML;    
			}
			window.onafterprint = function(){     
				document.body.innerHTML = initBody;    
			}    
			window.print();  
		}
		
		function fn_exceldown(){			
			var comSubmit = new ComSubmit();
			var user_right = $("#USER_RIGHT").val();
			
			comSubmit.setUrl("<c:url value='/officework/excelTrustOworkStatusList.do' />");
			
			comSubmit.addParam("OW_NAME",$("#OW_NAME").val());
			comSubmit.addParam("OW_DEPT_NM",$("#OW_DEPT_NM").val());
			comSubmit.addParam("IN_ST_DATE",$("#IN_ST_DATE").val());
			comSubmit.addParam("IN_ED_DATE",$("#IN_ED_DATE").val());
			comSubmit.addParam("OW_TYPE1",$("#OW_TYPE1").val());
			comSubmit.addParam("OW_TYPE2",$("#OW_TYPE2").val());
			comSubmit.addParam("OW_TYPE3",$("#OW_TYPE3").val());
			comSubmit.addParam("CS_INST_NAME",$("#CS_INST_NAME").val());
			comSubmit.addParam("CS_CHOICE_METHOD",$("#CS_CHOICE_METHOD").val());			
			comSubmit.addParam("TRUST_STOP_STDATE",$("#TRUST_STOP_STDATE").val().replace(/-/g, ''));
			comSubmit.addParam("TRUST_STOP_EDDATE",$("#TRUST_STOP_EDDATE").val().replace(/-/g, ''));
			comSubmit.addParam("BUDGET_AMT1",$("#BUDGET_AMT1").val().replace(/,/g, ''));
			comSubmit.addParam("BUDGET_AMT2",$("#BUDGET_AMT2").val().replace(/,/g, ''));
			
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comSubmit.addParam("OW_DEPT_CD", '');
			else
				comSubmit.addParam("OW_DEPT_CD", $("#DEPT_ID").val());
			
			
			if( $("input:radio[name='TRUST_CONTINUE_YN']").is(":checked") == true){				
				comSubmit.addParam("TRUST_CONTINUE_YN", $("input:radio[name='TRUST_CONTINUE_YN']:checked").val());				
			}
			
			if( $("input:radio[name='BG_YEAR']").is(":checked") == true){				
				comSubmit.addParam("BG_YEAR", $("input:radio[name='BG_YEAR']:checked").val());				
			}			
						
			comSubmit.addParam("PAGE_ROW", $("#XLS_RECORD_COUNT").val());			
			comSubmit.submit();
		}
		
		
		
		function fn_selectOfficeworkRegList(pageNo){  //fn_srchTrustOworkStatusList
			
			var comAjax = new ComAjax();
			var user_right = $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/officework/selectTrustOworkStatusList.do' />");
			comAjax.setCallback("fn_selectTrustOworkStatusListCallback");
			comAjax.addParam("OW_NAME",$("#OW_NAME").val());
			comAjax.addParam("OW_DEPT_NM",$("#OW_DEPT_NM").val());
			comAjax.addParam("IN_ST_DATE",$("#IN_ST_DATE").val());
			comAjax.addParam("IN_ED_DATE",$("#IN_ED_DATE").val());
			comAjax.addParam("OW_TYPE1",$("#OW_TYPE1").val());
			comAjax.addParam("OW_TYPE2",$("#OW_TYPE2").val());
			comAjax.addParam("OW_TYPE3",$("#OW_TYPE3").val());
			comAjax.addParam("CS_INST_NAME",$("#CS_INST_NAME").val());
			comAjax.addParam("CS_CHOICE_METHOD",$("#CS_CHOICE_METHOD").val());			
			comAjax.addParam("TRUST_STOP_STDATE",$("#TRUST_STOP_STDATE").val().replace(/-/g, ''));
			comAjax.addParam("TRUST_STOP_EDDATE",$("#TRUST_STOP_EDDATE").val().replace(/-/g, ''));
			comAjax.addParam("BUDGET_AMT1",$("#BUDGET_AMT1").val().replace(/,/g, ''));
			comAjax.addParam("BUDGET_AMT2",$("#BUDGET_AMT2").val().replace(/,/g, ''));
			
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("OW_DEPT_CD", '');
			else
				comAjax.addParam("OW_DEPT_CD", $("#DEPT_ID").val());
			
			if( $("input:radio[name='TRUST_CONTINUE_YN']").is(":checked") == true){				
				comAjax.addParam("TRUST_CONTINUE_YN", $("input:radio[name='TRUST_CONTINUE_YN']:checked").val());				
			}
						
			if( $("input:radio[name='BG_YEAR']").is(":checked") == true){				
				comAjax.addParam("BG_YEAR", $("input:radio[name='BG_YEAR']:checked").val());			
			}
			
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());			
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.ajax();		
		}
		
		function fn_officeworkBasicDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());			
			comSubmit.submit();
		}
		
		/*function fn_selectOfficeworkRegList(pageNo){			
			var comAjax = new ComAjax();
			var user_right = $("#USER_RIGHT").val();

			comAjax.setUrl("<c:url value='/officework/selectTrustOworkStatusList.do' />");
			comAjax.setCallback("fn_selectTrustOworkStatusListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("IDX_FE", $("#IDX_FE").val());
			
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("OW_DEPT_CD", '');
			else
				comAjax.addParam("OW_DEPT_CD", $("#DEPT_ID").val());
			
			comAjax.ajax();
		}*/
		
		function fn_sesiondelete(){
			alert('bb');
			$.session.clear();
		}
		
		function fn_selectTrustOworkStatusListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(total);
			
			body.empty();			
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"10\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"10\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectOfficeworkRegList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var ow_type1 = "";
				var ow_type2 = "";
				var ch_method = "";
				$.each(data.list, function(key, value){
						
					    ow_type1 = "";
						if (value.OW_TYPE1 == "01")
							ow_type1 = "민간위탁";
						else if (value.OW_TYPE1 == "02")
							ow_type1 = "용역";
						else if (value.OW_TYPE1 == "03")
							ow_type1 = "대행·공공위탁";
						else if (value.OW_TYPE1 == "04")
							ow_type1 = "연구용역";
					 
						ow_type2 = "";
						if (value.OW_TYPE2 == "01")
							ow_type2 = "사무";
						else if (value.OW_TYPE2 == "02")
							ow_type2 = "시설";
						
						
						ch_method = "";
						if (value.CS_CHOICE_METHOD == "01")
							ch_method = "공개모집";
						else if (value.CS_CHOICE_METHOD == "02")
							ch_method = "수의계약";
						else if (value.CS_CHOICE_METHOD == "03")
							ch_method = "재계약";
						else if (value.CS_CHOICE_METHOD == "04")
							ch_method = "기타";
					
						str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + value.OW_DEPT_NM + "</td>" + 
									   "<td class=\"list_left\"'>" + 
									   "<a href='#this' name='title' >" + value.OW_NAME  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" + 
									   "<td class=\"list_center\" nowrap>" + ow_type1 +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + ow_type2 +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + comma(NvlStr(value.BG_TOTAL_COST)) +  "</td>" +
									   "<td class=\"list_center22\" nowrap><font title=\"" + NvlStr(value.CS_INST_NAME) +  "\" style=\"CURSOR:hand;\">" + NvlStr(value.CS_INST_NAME) +  "</font></td>" +
									   "<td class=\"list_center\" nowrap>" + ch_method +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.FIRST_TRUST_DATE),".") +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.CUR_TRUST_STDATE),".") + " ~ " +  formatDate(NvlStr(value.CUR_TRUST_EDDATE),".") +  "</td>" +
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"10\" class=\"list_line\"></td>" + 
									   "</tr>";
							
							
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_officeworkBasicDetail($(this));
				});
			}
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">위탁현황조회</td>
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
    
    	<table width="720" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          
	          <tr>
	            <td width="100" class="tbl_field">기준년도</td>
	            <td colspan="3" width="660" class="tbl_list_left">	               
	                <input type="radio" id="BG_YEAR1" name="BG_YEAR" value="01" class="input" onfocus="this.blur()"> 당해년도 &nbsp;<input type="radio" id="BG_YEAR2" name="BG_YEAR" value="" class="input" onfocus="this.blur()"> 전체	                                      
	            </td>
	          </tr>
	          <tr>
	            <td width="100" class="tbl_field">기간</td>
	            <td colspan="3" width="660" class="tbl_list_left">	               
	                <input type="text" id="IN_ST_DATE" name="IN_ST_DATE" class="input" style="width:100;"> ~
	                <input type="text" id="IN_ED_DATE" name="IN_ED_DATE" class="input" style="width:100;"> &nbsp; (입력일 기준)                         
	            </td>
	          </tr>
			  <tr>
	            <td width="100" class="tbl_field">담당부서</td>
	            <td colspan="3" width="660" class="tbl_list_left">	               
	                <input type="text" id="OW_DEPT_NM" name="OW_DEPT_NM" class="input" style="width:300;">	                          
	            </td>
	          </tr>			  
			  <tr>
	            <td width="100" class="tbl_field">위탁사무명</td>
	            <td width="260" class="tbl_list_left">
	                <input type="text" id="OW_NAME" name="OW_NAME" class="input" style="width:180;">	                
	            </td>
	            <td width="110" class="tbl_field">위탁유형</td>
	            <td width="290" class="tbl_list_left">	               
	                <select id="OW_TYPE1" name="OW_TYPE1" class="input">
	                <option value="">-------</option>
	                <option value="01">민간위탁</option>
	                <option value="02">용역</option>
	                <option value="03">대행·공공위탁</option>
	                <option value="04">연구용역</option>
	                </select>
	                <select id="OW_TYPE2" name="OW_TYPE2" class="input">
	                <option value="">-----</option>
	                <option value="01">사무</option>
	                <option value="02">시설</option>	                
	                </select>
	                <select id="OW_TYPE3" name="OW_TYPE3" class="input">
	                <option value="">-------</option>
	                <option value="01">수익창출형</option>
	                <option value="02">예산지원형</option>	                
	                </select>
	            </td>
	          </tr>
	           <tr>
	            <td width="100" class="tbl_field">수탁기관명</td>
	            <td width="260" class="tbl_list_left">
	                <input type="text" id="CS_INST_NAME" name="CS_INST_NAME" class="input" style="width:180;">	                
	            </td>
	            <td width="110" class="tbl_field">수탁기관선정방법</td>
	            <td width="290" class="tbl_list_left">	               
	                <select id="CS_CHOICE_METHOD" name="CS_CHOICE_METHOD" class="input">
	                	<option value="">----------</option>
	                	<option value="01">공개모집</option>
	                	<option value="02">수의계약</option>
 					    <option value="03">재계약</option>
 					    <option value="04">기타</option>	                		                	
	                </select>
	             </td>
	             </tr>
	            <tr>
	            <td width="100" class="tbl_field">위탁만료일자</td>
	            <td colspan="3" width="660" class="tbl_list_left">
	                <input type="text" id="TRUST_STOP_STDATE" name="TRUST_STOP_STDATE" class="input" style="width:100;"> ~
                    <input type="text" id="TRUST_STOP_EDDATE" name="TRUST_STOP_EDDATE" class="input" style="width:100;">	                	                
	            </td>
	            </tr>
	            <tr>
	            <td width="100" class="tbl_field">예산금액</td>
	            <td width="260" class="tbl_list_left">
	                <input type="text" id="BUDGET_AMT1" name="BUDGET_AMT1" class="input" style="width:80;"> ~
                    <input type="text" id="BUDGET_AMT2" name="BUDGET_AMT2" class="input" style="width:80;">&nbsp;(단위:천원)                	                
	            </td>
	            <td width="110" class="tbl_field">위탁지속여부</td>
	            <td width="290" class="tbl_list_left">	                  
	                <input type="radio" id="TRUST_CONTINUE_YN1" name="TRUST_CONTINUE_YN" value="01" class="input" onfocus="this.blur()"> 중지 &nbsp;<input type="radio" id="TRUST_CONTINUE_YN2" name="TRUST_CONTINUE_YN" value="02" class="input" onfocus="this.blur()"> 지속 &nbsp;<input type="radio" id="TRUST_CONTINUE_YN3" name="TRUST_CONTINUE_YN" value="" class="input" onfocus="this.blur()"> 전체                	                	               
	            </td>
	            </tr> 
    	</table>
    
    
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/oms/images/btn_type0_head.gif"></td>
               <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/oms/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>		
         </tr>
       </table>
       </td>
     </tr>
     <tr>
       <td align="left"><b>Total : <span id="tcnt"></span></b></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/oms/images/btn_type1_head.gif"></td>
               <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/oms/images/btn_type1_end.gif"></td>
             </tr>
           </table>
           </td>				
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/oms/images/btn_type1_head.gif"></td>
               <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="print">출력</a></td>
               <td><img src="/oms/images/btn_type1_end.gif"></td>
             </tr>
           </table>
           </td>							
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
             <tr>
               <td><img src="/oms/images/btn_type1_head.gif"></td>
               <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="excel">엑셀다운로드</a></td>
               <td><img src="/oms/images/btn_type1_end.gif"></td>
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
    	
<div id='printarea'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="90"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="80"></td>				
				<td class="list_tit_line_s" width="70"></td>
				<td class="list_tit_line_s" width="90"></td>
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="70"></td>
				<td class="list_tit_line_s" width="90"></td>
				<td class="list_tit_line_s" width="140"></td>				
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit">번호</td>
				<td nowrap class="list_tit_bar">부서명</td>
				<td nowrap class="list_tit_bar">위탁사무명</td>
				<td nowrap class="list_tit_bar">위탁유형</td>
				<td nowrap class="list_tit_bar">위탁유형2</td>	
				<td nowrap class="list_tit_bar">예산액</td>
				<td nowrap class="list_tit_bar">수탁기관명</td>
				<td nowrap class="list_tit_bar">산정방법</td>
				<td nowrap class="list_tit_bar">최초위탁일</td>
				<td nowrap class="list_tit_bar">현위탁기간</td>				
			</tr>
			<tr>
				<td colspan="10" class="list_tit_line_e"></td>
			</tr>
		
		<tbody id='mytable'>
			
		</tbody>
	</table>
	
	</td>
	</tr>
</table>
</div>
</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>
	<input type="hidden" id="XLS_RECORD_COUNT" name="XLS_RECORD_COUNT" value="100000"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
		
</body>
</html>