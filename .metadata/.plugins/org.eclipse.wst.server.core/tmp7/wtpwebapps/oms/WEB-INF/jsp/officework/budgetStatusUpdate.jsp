<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<//% session.invalidate(); %>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_budgetStatusList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() ||  $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_updateBudgetStatus();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() ||  $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_deleteBudgetStatus();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});
			
			$("#basic").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_officeworkBasicDetail();
			});
			
			$("#mng").on("click", function(e){ //운영현황
				e.preventDefault();				
				fn_superviseStatusList();
			});
			
			$("#delib").on("click", function(e){ //심의현황
				e.preventDefault();				
				fn_qualiDelibStatusList();
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$('input:radio[name=O_CONT_EVALU_YN]:input[value=' + "${map.CONT_EVALU_YN}" + ']').attr("checked", true);
			$("#WON_CHOICE_METHOD").val("${map.WON_CHOICE_METHOD}");
			$('input:radio[name=O_WON_CHOICE_STANDARD]:input[value=' + "${map.WON_CHOICE_STANDARD}" + ']').attr("checked", true);
		});
		
	
		
		function fn_budgetStatusList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/consignStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		
		function fn_officeworkBasicDetail(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicDetail.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.submit();
		}
		
		
		function fn_superviseStatusList(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		
		function fn_qualiDelibStatusList(obj){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_updateBudgetStatus(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			if($('#BG_YEAR').val() == "")
			{
				alert('구분(년도)를 입력하세요.');
				$('#BG_YEAR').focus();
				return false();
			}
			
			if($('#BG_YEAR').val().length > 4 )
			{
				alert('4자리 이하로 입력하세요.');
				$('#BG_YEAR').focus();
				return false();
			}
						
			if($('#BG_TOTAL_COST').val() == "")
			{
				alert('총계를  입력하세요.');
				$('#BG_TOTAL_COST').focus();
				return false();
			}
			
			
			if($('#BG_GUK_COST').val() == "")
			{
				alert('국비를  입력하세요.');
				$('#BG_GUK_COST').focus();
				return false();
			}
			
			if($('#BG_SI_COST').val() == "")
			{
				alert('시비를  입력하세요.');
				$('#BG_SI_COST').focus();
				return false();
			}
			
			if($('#BG_GU_COST').val() == "")
			{
				alert('구비를  입력하세요.');
				$('#BG_GU_COST').focus();
				return false();
			}
			
			if($(":input:radio[name=O_CONT_EVALU_YN]:checked").val() != undefined )
			{
				$('#CONT_EVALU_YN').val($(":input:radio[name=O_CONT_EVALU_YN]:checked").val());
			}
			
			if($(":input:radio[name=O_WON_CHOICE_STANDARD]:checked").val() != undefined )
			{
				$('#WON_CHOICE_STANDARD').val($(":input:radio[name=O_WON_CHOICE_STANDARD]:checked").val());
			}
			
						
			tmp = $('#BG_TOTAL_COST').val().replace(/,/g, '');			
			$('#BG_TOTAL_COST').val(tmp);
			
			tmp = $('#BG_GUK_COST').val().replace(/,/g, '');			
			$('#BG_GUK_COST').val(tmp);
			
			tmp = $('#BG_SI_COST').val().replace(/,/g, '');			
			$('#BG_SI_COST').val(tmp);
			
			tmp = $('#BG_GU_COST').val().replace(/,/g, '');			
			$('#BG_GU_COST').val(tmp);
			
						
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/updateBudgetStatus.do' />");
				comSubmit.addParam("ID", "${map.ID}");
				comSubmit.addParam("OW_ID", "${map.OW_ID}");
				comSubmit.addParam("WDID", "${map.INS_DEPT}");
				comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
				comSubmit.submit();
			}
		}
		
		function fn_deleteBudgetStatus(){
			var comSubmit = new ComSubmit("form1");
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/deleteBudgetStatus.do' />");
				comSubmit.addParam("ID", "${map.ID}");
				comSubmit.addParam("OW_ID", "${map.OW_ID}");
				comSubmit.addParam("WDID", "${map.INS_DEPT}");
				comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
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
<input type="hidden" id="CONT_EVALU_YN" name="CONT_EVALU_YN" value="">
<input type="hidden" id="WON_CHOICE_STANDARD" name="WON_CHOICE_STANDARD" value="">
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
				<li id='property'><span><a href="#this" class="btn" id="basic">위탁사무기본현황</a></span></li>
				<li class="Lcurrent" id='maindoc1'><span><a href="#this" class="btn" id="sutak">수탁·예산현황</a></span></li>			
				<li id='public'><span><a href="#this" class="btn" id="mng">운영현황</a></span></li>	
				<li id='manage'><span><a href="#this" class="btn" id="delib">심의현황</a></span></li>			
			</ul>
		</div>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">예산현황등록</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
		
		<br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	          
			  <tr>
	            <td rowspan="2" width="80" class="tbl_field_center">구분 <font class="font_star">*</font></td>
	            <td colspan="4" width="320" class="tbl_field_center">예산현황 </td>	            
	            <td rowspan="2" width="120" class="tbl_field_center">재무과<br>계약심사 </td>
	            <td colspan="2" width="200" class="tbl_field_center">운영원가산정</td>	           
	          </tr>
	          <tr>	            
	            <td width="80" class="tbl_field_center">총계 <font class="font_star">*</font></td>
	            <td width="80" class="tbl_field_center">국비 <font class="font_star">*</font></td>
	            <td width="80" class="tbl_field_center">시비 <font class="font_star">*</font></td>
	            <td width="80" class="tbl_field_center">구비 <font class="font_star">*</font></td>	            
	            <td width="100" class="tbl_field_center">산정방법</td>
	            <td width="100" class="tbl_field_center">산정 기준 유무</td>
	          </tr>			  
			  <tr>
	            <td width="80" class="tbl_list_left"><input type="text" id="BG_YEAR" name="BG_YEAR" value=${map.BG_YEAR} class="input" style="width:80;"></td>
	            <td width="80" class="tbl_list_left"><input type="text" id="BG_TOTAL_COST" name="BG_TOTAL_COST" value="<fmt:formatNumber value="${map.BG_TOTAL_COST}" pattern="###,###,###" />" class="input" style="width:80;"></td>
	            <td width="80" class="tbl_list_left"><input type="text" id="BG_GUK_COST" name="BG_GUK_COST" value="<fmt:formatNumber value="${map.BG_GUK_COST}" pattern="###,###,###" />" class="input" style="width:80;"></td>
	            <td width="80" class="tbl_list_left"><input type="text" id="BG_SI_COST" name="BG_SI_COST" value="<fmt:formatNumber value="${map.BG_SI_COST}" pattern="###,###,###" />" class="input" style="width:80;"></td>
	            <td width="80" class="tbl_list_left"><input type="text" id="BG_GU_COST" name="BG_GU_COST" value="<fmt:formatNumber value="${map.BG_GU_COST}" pattern="###,###,###" />" class="input" style="width:80;"></td>
	            <td width="120" class="tbl_list_left"><input type="radio" id="CONT_EVALU_YN1" name="O_CONT_EVALU_YN" value="1" class="input"> Y&nbsp; <input type="radio" id="CONT_EVALU_YN2" name="O_CONT_EVALU_YN" value="2" class="input"> N </td>
	            <td width="100" class="tbl_list_left"><select id="WON_CHOICE_METHOD" name="WON_CHOICE_METHOD" class="input">
	                	<option value="00">----------</option>
	                	<option value="01">내부산정</option>
	                	<option value="02">외부산정</option></select></td>
	            <td width="100" class="tbl_list_left"><input type="radio" id="WON_CHOICE_STANDARD1" name="O_WON_CHOICE_STANDARD" value="1" class="input"> O&nbsp; <input type="radio" id="WON_CHOICE_STANDARD2" name="O_WON_CHOICE_STANDARD" value="2" class="input"> X</td>
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정</font></a></td>
                    <td><img src="/oms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/oms/images/btn_type0_head.gif"></td>
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/oms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
				
			
                <td>
                
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
