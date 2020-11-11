<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			fn_deptList();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_insertBoard();
			});
			
			$("#write2").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_consignStatusWrite();
			});
			
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$("#SISEL_CMPT_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#FIRST_TRUST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#TRUST_STOP_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#OW_DEPT").val("${sessionScope.userinfo.deptId}"+ "|" + "${sessionScope.userinfo.deptRank}");
		});
		
		function fn_consignStatusWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/consignStatusWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegList.do' />");
			comSubmit.submit();
		}
		
		function fn_insertBoard(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			if($('#OW_NAME').val() == "")
			{
				alert('위탁사무명을 입력하세요.');
				$('#OW_NAME').focus();
				return false();
			}
			
			if($("#OW_DEPT option:selected").val() == "0000000")
			{
				alert('부서를 선택해주세요.');
				$('#OW_DEPT').focus();
				return false();
			}
			
			if($("#OW_TYPE1 option:selected").val() == "00")
			{
				alert('위탁유형1 을 선택해주세요.');
				$('#OW_TYPE1').focus();
				return false();
			}
			
			if($("#OW_TYPE2 option:selected").val() == "00")
			{
				alert('위탁유형2 을 선택해주세요.');
				$('#OW_TYPE2').focus();
				return false();
			}
			
			if($("#OW_TYPE3 option:selected").val() == "00")
			{
				alert('위탁유형3 을 선택해주세요.');
				$('#OW_TYPE3').focus();
				return false();
			}
			
			$('#OW_DEPT_NM').val($("#OW_DEPT option:selected").text());
			$('#OW_DEPT_CD').val($("#OW_DEPT option:selected").val().substring(0, 7));
			$('#OW_DEPT_RANK').val($("#OW_DEPT option:selected").val().substring(8, 13));
			
			//alert($("#OW_DEPT option:selected").val());
						
			if($('#OW_BASE_RULES').val() == "")
			{
				alert('위탁근거 법규를 입력하세요.');
				$('#OW_BASE_RULES').focus();
				return false();
			}
			
			
			if($('#OW_CONTENTS').val() == "")
			{
				alert('위탁내용을 입력하세요.');
				$('#OW_CONTENTS').focus();
				return false();
			}
			
			if($('#FIRST_TRUST_DATE').val() == "")
			{
				alert('최초위탁일을 입력하세요.');
				$('#FIRST_TRUST_DATE').focus();
				return false();
			}
			
			tmp = $('#SISEL_CMPT_DATE').val().replace(/\-/g,''); 
			tmp = tmp.replace(/\./g,'');
			tmp = tmp.replace(/\//g,'');
			$('#SISEL_CMPT_DATE').val(tmp);
			
			tmp = $('#FIRST_TRUST_DATE').val().replace(/\-/g,''); 
			tmp = tmp.replace(/\./g,'');
			tmp = tmp.replace(/\//g,'');
			$('#FIRST_TRUST_DATE').val(tmp);
			
			tmp = $('#TRUST_STOP_DATE').val().replace(/\-/g,''); 
			tmp = tmp.replace(/\./g,'');
			tmp = tmp.replace(/\//g,'');
			$('#TRUST_STOP_DATE').val(tmp);
			
			tmp = $('#USE_CNT').val().replace(/,/g, '');			
			$('#USE_CNT').val(tmp);
			
			
			if($('#CHG_CHIEF').val() == "")
			{
				alert('팀장님 성함을 입력하세요.');
				$('#CHG_CHIEF').focus();
				return false();
			}
			
			if($('#CHG_CHIEF_TEL').val() == "")
			{
				alert('팀장님 연락처를 입력하세요.');
				$('#CHG_CHIEF_TEL').focus();
				return false();
			}
			
			if($('#CHG_PRSN').val() == "")
			{
				alert('담당자 이름를 입력하세요.');
				$('#CHG_PRSN').focus();
				return false();
			}
			
			if($('#CHG_PRSN_TEL').val() == "")
			{
				alert('담당자 연락처를 입력하세요.');
				$('#CHG_PRSN_TEL').focus();
				return false();
			}
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/insertofficeworkBasic.do' />");
				comSubmit.submit();
			}
		}
		
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
		
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.DEPT_ID + "|" + value.DEPT_RANK + "\">" + value.DEPT_NAME + "</option>";
					i++;
			});
			
			$("#OW_DEPT").append(str);			
		}	
	</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">위탁사무등록</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="100%">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">위탁사무기본현황</td>
			</tr>
		</table>
		</td>				
	</tr>
	</table>
	
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">위탁사무명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="OW_NAME" name="OW_NAME" class="input" style="width:557;">	               
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">담당부서 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <select id="OW_DEPT" name="OW_DEPT" class="input">
	                	<option value="0000000">----------</option>	                	
	                </select>
	                <input type="hidden" id="OW_DEPT_CD" name="OW_DEPT_CD" class="input">
	                <input type="hidden" id="OW_DEPT_NM" name="OW_DEPT_NM" class="input">
	                <input type="hidden" id="OW_DEPT_RANK" name="OW_DEPT_RANK" class="input">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁유형 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">	                
	                <select id="OW_TYPE1" name="OW_TYPE1" class="input">
	                <option value="00">-------</option>
	                <option value="01">민간위탁</option>
	                <option value="02">용역</option>
	                <option value="03">대행·공공위탁</option>
	                <option value="04">연구용역</option>
	                </select> &nbsp;
	             
	                <select id="OW_TYPE2" name="OW_TYPE2" class="input">
	                <option value="00">-----</option>
	                <option value="01">사무</option>
	                <option value="02">시설</option>	                
	                </select> &nbsp;
	                
	                <select id="OW_TYPE3" name="OW_TYPE3" class="input">
	                <option value="00">-------</option>
	                <option value="01">수익창출형</option>
	                <option value="02">예산지원형</option>	                
	                </select> 	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁근거 법규 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="OW_BASE_RULES" name="OW_BASE_RULES" class="input" style="width:557;">	                
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">위탁내용 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <textarea rows="10" cols="76" id="OW_CONTENTS" name="OW_CONTENTS" class="input" style="width:557;"></textarea>					 
	            </td>
	          </tr>	
			  
			  <tr>
	            <td width="120" class="tbl_field">시설위치</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="SISEL_POSITION" name="SISEL_POSITION" class="input" style="width:557;">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">시설규모</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="SISEL_SCALE" name="SISEL_SCALE" class="input" style="width:557;">	                
	            </td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">시설준공일</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="SISEL_CMPT_DATE" name="SISEL_CMPT_DATE" class="input" style="width:160;"> (8자리로)                
	            </td>
				<td width="120" class="tbl_field">최초위탁일 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="FIRST_TRUST_DATE" name="FIRST_TRUST_DATE" class="input" style="width:160;"> (8자리로)
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용대상</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="USE_OBJECT" name="USE_OBJECT" class="input" style="width:557;">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용자수</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="USE_CNT" name="USE_CNT" class="input" style="width:160;"> (숫자만입력)	                
	            </td>
				<td width="120" class="tbl_field">위탁중지일</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="TRUST_STOP_DATE" name="TRUST_STOP_DATE" class="input" style="width:160;"> (8자리로)
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">팀장 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CHG_CHIEF" name="CHG_CHIEF" class="input" style="width:160;">	                
	            </td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CHG_CHIEF_TEL" name="CHG_CHIEF_TEL" class="input" style="width:160;">
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당자 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CHG_PRSN" name="CHG_PRSN" class="input" style="width:160;">	                
	            </td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CHG_PRSN_TEL" name="CHG_PRSN_TEL" class="input" style="width:160;">
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/oms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/oms/images/btn_type1_head.gif"></td>
                    <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
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
        <!-- -------------- 버튼 끝 --------------  -->
        </td>
      </tr>
    </table>
    
    </td>
    
  </tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
