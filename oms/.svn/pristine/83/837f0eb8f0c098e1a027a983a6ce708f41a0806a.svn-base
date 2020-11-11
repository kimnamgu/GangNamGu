<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_officeworkRegList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_officeworkBasicUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#basic").on("click", function(e){ //수정하기 버튼
				e.preventDefault();
				fn_officeworkBasicMod();
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
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A' )
				{	
					e.preventDefault();				
					fn_deleteOfficeworkBasic();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
		
		});
		
		function fn_officeworkRegList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_consignStatusList(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/consignStatusList.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", "${map.OW_DEPT_CD}");
			comSubmit.submit();
		}
		
		function fn_officeworkBasicUpdate(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicUpdate.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.addParam("SAUP_DEPT", "${map.OW_DEPT_CD}");
			comSubmit.submit();
		}
		
		function fn_superviseStatusList(obj){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", "${map.OW_DEPT_CD}");
			comSubmit.submit();
		}
		
		function fn_qualiDelibStatusList(obj){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusList.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.addParam("WDID", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT", "${map.OW_DEPT_CD}");
			comSubmit.submit();
		}
		
		function fn_deleteOfficeworkBasic(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/officework/deleteOfficeworkBasic.do' />");
				comSubmit.addParam("ID", id);
				comSubmit.submit();
			}
		}
		
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${map.INS_DEPT}">
<input type="hidden" id="SAUP_DEPT" name="SAUP_DEPT" value="${map.OW_DEPT_CD}">
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
	            <td colspan="3" width="607" class="tbl_list_left">${map.OW_NAME}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">담당부서 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.OW_DEPT_NM}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁유형 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	            <c:set var="type" value="${map.OW_TYPE1}" />
				<c:choose>
			    <c:when test="${type eq '01'}">
		                   민간위탁
		    	</c:when>
			    <c:when test="${type eq '02'}">
		                  용역
		    	</c:when>
		    	<c:when test="${type eq '03'}">
		                  대행·공공위탁
		    	</c:when>
		    	<c:when test="${type eq '04'}">
		                  연구용역
		    	</c:when>
				</c:choose>
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁근거 법규 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.OW_BASE_RULES}</td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">위탁내용 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${fn:replace(map.OW_CONTENTS, crcn, br)}</td>
	          </tr>	
			  
			  <tr>
	            <td width="120" class="tbl_field">시설위치</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.SISEL_POSITION}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">시설규모</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.SISEL_SCALE}</td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">시설준공일</td>
	            <td width="243" class="tbl_list_left">	            
	            <fmt:parseDate value="${map.SISEL_CMPT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/>
			    <fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>
	            </td>
				<td width="120" class="tbl_field">최초위탁일 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	            <fmt:parseDate value="${map.FIRST_TRUST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/>
			    <fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>
	            </td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용대상</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.USE_OBJECT}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용자수</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.USE_CNT}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">위탁중지일</td>
	            <td width="244" class="tbl_list_left">
	            <fmt:parseDate value="${map.TRUST_STOP_DATE}" var="dateFmt1" pattern="yyyyMMdd"/>
			    <fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">팀장 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">${map.CHG_CHIEF}</td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">${map.CHG_CHIEF_TEL}</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당자 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">${map.CHG_PRSN}</td>
				<td width="120" class="tbl_field">연락처 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">${map.CHG_PRSN_TEL}</td>							
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
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
