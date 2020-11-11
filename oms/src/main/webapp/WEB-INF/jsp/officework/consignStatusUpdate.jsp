<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = '${fn:length(list)+1}';
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_consignStatusList();
			});
			
			$("#mod").on("click", function(e){ //수정처리 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_updateConsignStatus();
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
			
			$("a[name^='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$("#CUR_TRUST_STDATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
			    changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
			    showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#CUR_TRUST_EDDATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
			    changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
			    showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#CS_CHOICE_METHOD").val("${map.CS_CHOICE_METHOD}");			
			$('input:radio[name=RESIGN_DELIB]:input[value=' + "${map.RESIGN_DELIB_YN}" + ']').attr("checked", true);
			
		});
		
		function fn_consignStatusList(){
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
		
		function fn_updateConsignStatus(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			if($("#CS_CHOICE_METHOD option:selected").val() == "00")
			{
				alert('수탁기관 선정방법을 선택해주세요.');
				$('#CS_CHOICE_METHOD').focus();
				return false();
			}
			
			if($('#CS_INST_NAME').val() == "")
			{
				alert('수탁기관명을 입력하세요.');
				$('#CS_INST_NAME').focus();
				return false();
			}
						
			if($('#CS_HEADER_NAME').val() == "")
			{
				alert('수탁기관 대표자를 입력하세요.');
				$('#CS_HEADER_NAME').focus();
				return false();
			}
			
			if($('#CUR_TRUST_STDATE').val() == "")
			{
				alert('현재위탁기간을 입력하세요.');
				$('#CUR_TRUST_STDATE').focus();
				return false();
			}
			
			
			if($('#CUR_TRUST_EDDATE').val() == "")
			{
				alert('현재위탁기간을 입력하세요.');
				$('#CUR_TRUST_EDDATE').focus();
				return false();
			}
			
			
			if($(":input:radio[name=RESIGN_DELIB]:checked").val() != undefined )
			{
				$('#RESIGN_DELIB_YN').val($(":input:radio[name=RESIGN_DELIB]:checked").val());
			}
		
			
			tmp = $('#CUR_TRUST_STDATE').val().replace(/\-/g,''); 
			tmp = tmp.replace(/\./g,'');
			tmp = tmp.replace(/\//g,'');
			$('#CUR_TRUST_STDATE').val(tmp);
			
			
			tmp = $('#CUR_TRUST_EDDATE').val().replace(/\-/g,''); 
			tmp = tmp.replace(/\./g,'');
			tmp = tmp.replace(/\//g,'');
			$('#CUR_TRUST_EDDATE').val(tmp);
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/updateConsignStatus.do' />");
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
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'> <a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
	</script>
</head>

<body>

<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="3">
<input type="hidden" id="ID" name="ID" value="${map.ID}">
<input type="hidden" id="BD_LST_ID" name="BD_LST_ID" value="${map.ID}">
<input type="hidden" id="BDID" name="BDID" value="3">
<input type="hidden" id="RESIGN_DELIB_YN" name="RESIGN_DELIB_YN" value="">
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
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">수탁현황등록</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>					
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">수탁기관 선정방법 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <select id="CS_CHOICE_METHOD" name="CS_CHOICE_METHOD" class="input">
	                	<option value="00">----------</option>
	                	<option value="01">공개모집</option>
	                	<option value="02">수의계약</option>
 					    <option value="03">재계약</option>
 					    <option value="04">기타</option>	                		                	
	                </select>	               
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">사유 </td>
	            <td colspan="3" width="607" class="tbl_list_left">	               
	                <input type="text" id="CS_REASON" name="CS_REASON" value="${map.CS_REASON}" class="input" style="width:557;">	              
	                <input type="hidden" id="OW_ID" name="OW_ID" value="${map.OW_ID}">              
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">수탁기관명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="CS_INST_NAME" name="CS_INST_NAME" value="${map.CS_INST_NAME}" class="input" style="width:557;">	                
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 대표자 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CS_HEADER_NAME" name="CS_HEADER_NAME" value="${map.CS_HEADER_NAME}" class="input" style="width:174;">                
	            </td>
				<td width="120" class="tbl_field">수탁기관 정규직</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CS_INST_REGLR" name="CS_INST_REGLR" value="${map.CS_INST_REGLR}" class="input" style="width:174;"> (인원수)
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 주요경력</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CS_HEADER_CAREER" name="CS_HEADER_CAREER" value="${map.CS_HEADER_CAREER}" class="input" style="width:174;">                
	            </td>
				<td width="120" class="tbl_field">수탁기관 비정규직</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CS_INST_NONREGLR" name="CS_INST_NONREGLR" value="${map.CS_INST_NONREGLR}" class="input" style="width:174;"> (인원수)
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">현재위탁기간 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="CUR_TRUST_STDATE" name="CUR_TRUST_STDATE" value="${map.CUR_TRUST_STDATE}" class="input" style="width:100;"> ~ <input type="text" id="CUR_TRUST_EDDATE" name="CUR_TRUST_EDDATE" value="${map.CUR_TRUST_EDDATE}" class="input" style="width:100;">
	            </td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">재계약횟수</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="RESIGN_NUMBER" name="RESIGN_NUMBER" value="${map.RESIGN_NUMBER}" class="input" style="width:174;">                
	            </td>
				<td width="120" class="tbl_field">재개약심의여부</td>
	            <td width="244" class="tbl_list_left">	                
	               <input type="radio" id="RESIGN_DELIB1" name="RESIGN_DELIB" value="1" class="input">  Y&nbsp; <input type="radio" id="RESIGN_DELIB2" name="RESIGN_DELIB" value="2" class="input"> N
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">				
						<c:forEach var="row" items="${list }" varStatus="var">
							<p>
								<input type="hidden" id="ID" name="ID_${var.index }" value="${row.ID }">
								<a href="#this" id="name_${var.index }" name="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index }" name="file_${var.index }"> 
								(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a>
							</p>
						</c:forEach>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a>   	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 담당자</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="CS_INST_CHG" name="CS_INST_CHG" value="${map.CS_INST_CHG}" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">연락처</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="CS_INST_CHGTEL" name="CS_INST_CHGTEL" value="${map.CS_INST_CHGTEL}" class="input" style="width:174;">
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정처리</font></a></td>
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