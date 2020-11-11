<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_consignStatusList();
			});
			
			$("#mod").on("click", function(e){ //작성하기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_ConsignStatusUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
								
			});
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_ConsignStatusDelete();
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
			
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
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
		
		function fn_ConsignStatusUpdate(){
			var comSubmit = new ComSubmit("form1");
			
			comSubmit.addParam("ID", "${map.ID}");
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.setUrl("<c:url value='/officework/consignStatusUpdate.do' />");
			comSubmit.submit();
		}
		
		function fn_ConsignStatusDelete(){
			
			var comSubmit = new ComSubmit("form1");
			
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/deleteConsignStatus.do' />");
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
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
	</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="3">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
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
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">수탁현황</td>
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
	                <c:set var="mthd" value="${map.CS_CHOICE_METHOD}" />
					<c:choose>
				    <c:when test="${mthd eq '01'}">
			                   공개모집
			    	</c:when>
				    <c:when test="${mthd eq '02'}">
			                  수의계약
			    	</c:when>
			    	<c:when test="${mthd eq '03'}">
			                  재계약
			    	</c:when>
			    	<c:when test="${mthd eq '04'}">
			                  기타
			    	</c:when>
					</c:choose>              
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">사유 </td>
	            <td colspan="3" width="607" class="tbl_list_left">	               
	               ${map.CS_REASON}	                            
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">수탁기관명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                ${map.CS_INST_NAME}	                
	            </td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 대표자 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                ${map.CS_HEADER_NAME}                
	            </td>
				<td width="120" class="tbl_field">수탁기관 정규직</td>
	            <td width="244" class="tbl_list_left">
	                ${map.CS_INST_REGLR}
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 주요경력</td>
	            <td width="243" class="tbl_list_left">
	                ${map.CS_HEADER_CAREER}                 
	            </td>
				<td width="120" class="tbl_field">수탁기관 비정규직</td>
	            <td width="244" class="tbl_list_left">
	                ${map.CS_INST_NONREGLR}
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">현재위탁기간 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">	               
	                <fmt:parseDate value="${map.CUR_TRUST_STDATE}" var="dateFmt1" pattern="yyyyMMdd"/>
	                <fmt:formatDate value="${dateFmt1}"  pattern="yyyy.MM.dd"/> ~ 
	                <fmt:parseDate value="${map.CUR_TRUST_EDDATE}" var="dateFmt2" pattern="yyyyMMdd"/>
	                <fmt:formatDate value="${dateFmt2}"  pattern="yyyy.MM.dd"/>
	            </td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">재계약횟수</td>
	            <td width="243" class="tbl_list_left">
	                ${map.RESIGN_NUMBER}                 
	            </td>
				<td width="120" class="tbl_field">재개약심의여부</td>
	            <td width="244" class="tbl_list_left">	             
	                <c:set var="resignyn" value="${map.RESIGN_DELIB_YN}" />
					<c:choose>
				    <c:when test="${resignyn eq '1'}">
			        Y
			    	</c:when>				   
			    	<c:when test="${resignyn eq '2'}">
			        N
			    	</c:when>
					</c:choose>
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	               <c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>   	                
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">수탁기관 담당자</td>
	            <td width="243" class="tbl_list_left">
	                ${map.CS_INST_CHG}	                
	            </td>
				<td width="120" class="tbl_field">연락처</td>
	            <td width="244" class="tbl_list_left">
	                ${map.CS_INST_CHGTEL}
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this"  class="btn" id="del"><font color="white">삭제</font></a></td>
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
