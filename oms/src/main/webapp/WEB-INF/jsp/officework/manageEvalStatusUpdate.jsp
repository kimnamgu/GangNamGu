<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		
		$(document).ready(function(){
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_manageEvalStatusList();
			});
			
			$("#mod").on("click", function(e){ //작성하기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_updateManageEvalStatus();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});
			
			$("#del").on("click", function(e){ //삭제하기 버튼
								
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_deleteManageEvalStatus();
				}
				else{
					alert('권한이 없습니다.');
				}
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
			
			
			$("#ME_ILJA").datepicker({
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
			
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicDetail.do' />");
			comSubmit.addParam("ID","${map.OW_ID}");		
			comSubmit.submit();
		}
		
		function fn_consignStatusList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/consignStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_superviseStatusList(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");	
			comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_qualiDelibStatusList(obj){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_manageEvalStatusList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");
			comSubmit.addParam("ID", "${map.OW_ID}");
			comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
	
		function fn_updateManageEvalStatus(){
			var comSubmit = new ComSubmit("form1");
			
			if($('#ME_ILJA').val() == "")
			{
				alert('년월일을 입력하세요.');
				$('#ME_ILJA').focus();
				return false();
			}
			
			if($('#ME_EVAL_CONTENTS').val() == "")
			{
				alert('평가내용을 입력하세요.');
				$('#ME_EVAL_CONTENTS').focus();
				return false();
			}
			
			if($('#ME_EVAL_RESULT').val() == "")
			{
				alert('평가결과를 입력하세요.');
				$('#ME_EVAL_RESULT').focus();
				return false();
			}
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/updateManageEvalStatus.do' />");
				comSubmit.addParam("ID", "${map.ID}");
				comSubmit.addParam("OW_ID", "${map.OW_ID}");
				comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
				comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
				comSubmit.submit();
			}
		}
		
		function fn_deleteManageEvalStatus(){
			var comSubmit = new ComSubmit("form1");
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/deleteManageEvalStatus.do' />");
				comSubmit.addParam("ID", "${map.ID}");
				comSubmit.addParam("OW_ID", "${map.OW_ID}");
				comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
				comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
				comSubmit.submit();
			}
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
				<li id='property'><span><a href="#this" class="btn" id="basic">위탁사무기본현황</a></span></li>
				<li id='maindoc1'><span><a href="#this" class="btn" id="sutak">수탁·예산현황</a></span></li>			
				<li class="Lcurrent" id='public'><span><a href="#this" class="btn" id="mng">운영현황</a></span></li>	
				<li id='manage'><span><a href="#this" class="btn" id="delib">심의현황</a></span></li>			
			</ul>
		</div>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">경영평가현황 등록</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	         
			  <tr>
	            <td width="120" class="tbl_field">년월일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="ME_ILJA" name="ME_ILJA" value="<fmt:parseDate value="${map.ME_ILJA}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:174;">                
	            </td>								
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">평가내용</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="ME_EVAL_CONTENTS" name="ME_EVAL_CONTENTS" value="${map.ME_EVAL_CONTENTS}" class="input" style="width:557;">	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">평가결과</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="ME_EVAL_RESULT" name="ME_EVAL_RESULT" value="${map.ME_EVAL_RESULT}" class="input" style="width:557;">	                
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