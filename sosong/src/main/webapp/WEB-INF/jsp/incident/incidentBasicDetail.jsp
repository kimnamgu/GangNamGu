<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_allIncidentList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_IncidentBasicUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicDetail();
			});
			
			$("#1sim").on("click", function(e){ //1심
				e.preventDefault();								
				fn_indIncidentList("11");
			});
			
			$("#1sim-2").on("click", function(e){ //신청사건
				e.preventDefault();				
				fn_indIncidentList("12");
			});
			
			$("#1sim-3").on("click", function(e){ //신청사건(즉시항고)
				e.preventDefault();				
				fn_indIncidentList("13");
			});
			
			$("#2sim").on("click", function(e){ //2심
				e.preventDefault();				
				fn_indIncidentList("21");
			});
			
			$("#3sim").on("click", function(e){ //3심
				e.preventDefault();				
				fn_indIncidentList("31");
			});
			
			$("#3sim-2").on("click", function(e){ //환송후 사건
				e.preventDefault();		
				fn_indIncidentList("32");
			});
			
			$("#3sim-3").on("click", function(e){ //소송비용 확정 결정
				e.preventDefault();		
				fn_indIncidentList("33");
			});
		
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_deleteIncidentBasic();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
		
		});
		
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_incidentBasicDetail(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicDetail.do' />");
			comSubmit.addParam("ID", id);				
			comSubmit.submit();
		}
		
		function fn_indIncidentList(dgb){
			var id = "${map.ID}";
			var hm_gb = "${map.INCDNT_GB}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("ICDNT_TRIAL_NO", dgb);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.submit();
		}
		
	
		
		function fn_IncidentBasicUpdate(){			
			var comSubmit = new ComSubmit("form1");
			
			comSubmit.addParam("ID", "${map.ID}");
			comSubmit.setUrl("<c:url value='/incident/incidentBasicUpdate.do' />");
			comSubmit.submit();
		}
		
		
		
		function fn_deleteIncidentBasic(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/incident/deleteIncidentBasic.do' />");
				comSubmit.addParam("ID", id);
				comSubmit.addParam("MID", id);
				comSubmit.addParam("ICDNT_TRIAL_NO", "");
				comSubmit.addParam("ICDNT_NO", "");
				comSubmit.submit();
			}
		}
		
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">소송사건</td>
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
			<li class="Lcurrent" id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>			
			<li id='tab2'><span><a href="#this" class="btn" id="1sim">1심</a></span></li>					
	 		<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
	 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>
	 		<li id='tab5'><span><a href="#this" class="btn" id="2sim">2심</a></span></li>	 		
	 		<li id='tab6'><span><a href="#this" class="btn" id="3sim">3심</a></span></li>	 	
	 		<li id='tab7'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 		
	 		<li id='tab8'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li>	 		
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
	            <td width="120" class="tbl_field">구분</td>
	            <td width="243" class="tbl_list_left">
	            <c:set var="incdnt_gb" value="${map.INCDNT_GB}" />
				<c:choose>				
				<c:when test="${incdnt_gb eq '1'}">
	                            행정소송
	            </c:when>
	            <c:when test="${incdnt_gb eq '2'}">
	                            민사소송
	            </c:when>
	            <c:when test="${incdnt_gb eq '3'}">
	                            국가소송
	            </c:when>
	           	</c:choose>
	            </td>
				<td width="120" class="tbl_field">사건명</td>
	            <td width="244" class="tbl_list_left">${map.INCDNT_NAME}</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">원고</td>
	            <td width="243" class="tbl_list_left">${map.CMPLN_NAME}</td>
				<td width="120" class="tbl_field">특이사항</td>
	            <td width="244" class="tbl_list_left">${map.NOTE}</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">피고</td>
	            <td width="243" class="tbl_list_left">${map.DFNDNT_NAME}</td>
				<td width="120" class="tbl_field">소관부서</td>
	            <td width="244" class="tbl_list_left">${map.DEPT_NAME}</td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">수행자</td>
	            <td width="243" class="tbl_list_left">${map.PERFORM_PERSON}</td>
				<td width="120" class="tbl_field">소송대리인</td>
	            <td width="244" class="tbl_list_left">${map.CHRG_LAWYER}</td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">소가</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.LAWSUIT_VALUE}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">최종결과</td>
	            <td width="244" class="tbl_list_left">
	            <c:set var="rslt_gb" value="${map.LAST_RSLT_GB}" />
	            <c:set var="rslt_cont" value="${map.LAST_RSLT_CONT}" />
				<c:choose>				
				<c:when test="${rslt_gb eq '1'}">
	                            승소
	            </c:when>
	            <c:when test="${rslt_gb eq '2'}">
	                            일부승소
	            </c:when>
	            <c:when test="${rslt_gb eq '3'}">
	                            소취하
	            </c:when>
	            <c:when test="${rslt_gb eq '4'}">
	                            응소전 소취하
	            </c:when>
	            <c:when test="${rslt_gb eq '5'}">
	                            취하간주
	            </c:when>
	            <c:when test="${rslt_gb eq '6'}">
	                            화해권고결정
	            </c:when>
	            <c:when test="${rslt_gb eq '7'}">
				패소	                            
	            </c:when>
	            <c:when test="${rslt_gb eq '8'}">
	                            기타
	            </c:when>
	            <c:otherwise>
       			${rslt_cont}
       			</c:otherwise>
	            </c:choose>
	            </td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="243" class="tbl_list_left">${map.BIGO}</td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>	            						
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
                <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
				<c:choose>
	 			<c:when test="${us_rt eq 'A'}">
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                </c:when>
                </c:choose>                		
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/sosong/images/btn_type1_head.gif"></td>
                    <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/sosong/images/btn_type1_end.gif"></td>
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
