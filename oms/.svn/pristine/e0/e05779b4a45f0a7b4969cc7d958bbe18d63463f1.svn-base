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
			
			$("#update").on("click", function(e){ //수정처리 버튼
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_updateOfficeworkBasic();
				}
				else{
					alert('권한이 없습니다.');
				}				
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			$("#basic").on("click", function(e){ //위탁사무기본현황
				e.preventDefault();				
				fn_officeworkBasicDetail();
			});	
			
			$("#sutak").on("click", function(e){ //수정하기 버튼
				e.preventDefault();
				fn_consignStatusList();
			});
			
			
			$("#mng").on("click", function(e){ //운영현황
				e.preventDefault();				
				fn_superviseStatusList();
			});
			
			$("#delib").on("click", function(e){ //심의현황
				e.preventDefault();				
				fn_qualiDelibStatusList();
			});	
					
			$("#OW_DEPT").val("${map.OW_DEPT_CD}");
			$("#OW_TYPE1").val("${map.OW_TYPE1}");
			$("#OW_TYPE2").val("${map.OW_TYPE2}");
			$("#OW_TYPE3").val("${map.OW_TYPE3}");
			
			
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
		});
		
		
		function fn_officeworkBasicDetail(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("/oms/officework/officeworkBasicDetail.do");
			comSubmit.addParam("ID", $("#BID").val());			
			comSubmit.submit();
		}

		
		function fn_consignStatusList(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/consignStatusList.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.submit();
		}
						
		function fn_superviseStatusList(obj){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");
			comSubmit.addParam("ID", id);			
			comSubmit.submit();
		}
		
		function fn_qualiDelibStatusList(obj){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusList.do' />");
			comSubmit.addParam("ID", id);			
			comSubmit.submit();
		}
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegList.do' />");
			comSubmit.submit();
		}
		
		function fn_updateOfficeworkBasic(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
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
			$('#OW_DEPT_CD').val($("#OW_DEPT option:selected").val());
			
			
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
			
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.addParam("ID", id);
				comSubmit.setUrl("<c:url value='/officework/updateOfficeworkBasic.do' />");
				comSubmit.submit();
			}
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
					str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME + "</option>";
					i++;
			});		
			
			$("#OW_DEPT").append(str);			
		}	
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${map.INS_DEPT}">
<input type="hidden" id="SAUP_DEPT" name="SAUP_DEPT" value="${param.SAUP_DEPT}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

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
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
        
        <div class="LblockTab" style="position: relative;">
		<ul class="Lclear">
			<li class="Lcurrent" id='property'><span><a href="#this" class="btn" id="basic">위탁사무기본현황</a></span></li>
			<li id='maindoc1'><span><a href="#this" class="btn" id="sutak">수탁·예산현황</a></span></li>			
			<li id='public'><span><a href="#this" class="btn" id="mng">운영현황</a></span></li>	
			<li id='manage'><span><a href="#this" class="btn" id="delib">심의현황</a></span></li>			
		</ul>
		</div>
                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
		
	
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">위탁사무명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="OW_NAME" name="OW_NAME" value="${map.OW_NAME}" class="input" style="width:557;">	               
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
	                <input type="text" id="OW_BASE_RULES" name="OW_BASE_RULES" value="${map.OW_BASE_RULES}" class="input" style="width:557;">	                
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">위탁내용 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <textarea rows="10" cols="76" id="OW_CONTENTS" name="OW_CONTENTS" class="input" style="width:557;">${map.OW_CONTENTS}</textarea>					 
	            </td>
	          </tr>	
			  
			  <tr>
	            <td width="120" class="tbl_field">시설위치</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="SISEL_POSITION" name="SISEL_POSITION" value="${map.SISEL_POSITION}" class="input" style="width:557;">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">시설규모</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="SISEL_SCALE" name="SISEL_SCALE" value="${map.SISEL_SCALE}" class="input" style="width:557;">	                
	            </td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">시설준공일</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="SISEL_CMPT_DATE" name="SISEL_CMPT_DATE" value="<fmt:parseDate value="${map.SISEL_CMPT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:174;">                
	            </td>
				<td width="120" class="tbl_field">최초위탁일 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="FIRST_TRUST_DATE" name="FIRST_TRUST_DATE" value="<fmt:parseDate value="${map.FIRST_TRUST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:174;">
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용대상</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="USE_OBJECT" name="USE_OBJECT" value="${map.USE_OBJECT}" class="input" style="width:557;">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용자수</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="USE_CNT" name="USE_CNT" value="${map.USE_CNT}" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">위탁중지일</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="TRUST_STOP_DATE" name="TRUST_STOP_DATE" value="<fmt:parseDate value="${map.TRUST_STOP_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:174;">
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">팀장 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CHG_CHIEF" name="CHG_CHIEF" value="${map.CHG_CHIEF}" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CHG_CHIEF_TEL" name="CHG_CHIEF_TEL" value="${map.CHG_CHIEF_TEL}" class="input" style="width:174;">
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당자 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CHG_PRSN" name="CHG_PRSN" value="${map.CHG_PRSN}" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CHG_PRSN_TEL" name="CHG_PRSN_TEL" value="${map.CHG_PRSN_TEL}" class="input" style="width:174;">
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정처리</font></a></td>
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
