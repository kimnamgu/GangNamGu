<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tri_no = "${param.ICDNT_TRIAL_NO}";
		var hm_gb = "${param.INCDNT_GB}";

		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_indIncidentList('33');
			});
			
			
			$("#ins").on("click", function(e){ //입력하기
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_thirdTrial3Write();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_thirdTrial3Update();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_deleteThirdTrial3();
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
				fn_indIncidentList('11');
			});
			
			$("#1sim-2").on("click", function(e){ //신청사건
				e.preventDefault();				
				fn_indIncidentList('12');
			});
			
			$("#1sim-3").on("click", function(e){ //신청사건(즉시항고)
				e.preventDefault();				
				fn_indIncidentList('13');
			});
			
			$("#2sim").on("click", function(e){ //2심
				e.preventDefault();								
				fn_indIncidentList('21');			
			});
			
			$("#3sim").on("click", function(e){ //3심
				e.preventDefault();								
				fn_indIncidentList('31');				
			});
			
			$("#3sim-2").on("click", function(e){ //환송후 사건
				e.preventDefault();								
				fn_indIncidentList('32');			
			});
			
			$("#3sim-3").on("click", function(e){ //소송비용 확정 결정
				e.preventDefault();								
				fn_thirdTrial3Detail();		
			});			
		
		});
		
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
			comSubmit.submit();
		}
		
		function fn_indIncidentList(dgb){
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", dgb);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.submit();
		}
		
		function fn_incidentBasicDetail(){
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicDetail.do' />");
			comSubmit.addParam("ID", mid);				
			comSubmit.submit();
		}
		
		function fn_thirdTrial3Detail(){			
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/thirdTrial3Detail.do' />");
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", "33");
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.submit();
		}
		
		function fn_thirdTrial3Update(){
			
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			var icdnt_no = "${map.ICDNT_NO}";
			
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", "33");
			comSubmit.addParam("ICDNT_NO", icdnt_no);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.setUrl("<c:url value='/incident/thirdTrial3Update.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_deleteThirdTrial3(){
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			var icdnt_no = "${map.ICDNT_NO}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/incident/deleteIndTrial.do' />");
				
				comSubmit.addParam("MID", mid);				
				comSubmit.addParam("ICDNT_TRIAL_NO", "33");
				comSubmit.addParam("ICDNT_NO", icdnt_no);
				comSubmit.addParam("INCDNT_GB", hm_gb);
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
			<li id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>			
			<li id='tab2'><span><a href="#this" class="btn" id="1sim">1심</a></span></li>
			<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
	 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>	 		
	 		<li id='tab5'><span><a href="#this" class="btn" id="2sim">2심</a></span></li>	 		
	 		<li id='tab6'><span><a href="#this" class="btn" id="3sim">3심</a></span></li>	 		
	 		<li id='tab7'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 	
	 		<li id='tab8' class="Lcurrent"><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li>	 		
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
	            <td width="120" class="tbl_field">사건번호</td>
	            <td width="243" class="tbl_list_left">${map.ICDNT_NO}</td>
				<td width="120" class="tbl_field">비용확정액</td>
	            <td width="244" class="tbl_list_left"><fmt:formatNumber value="${map.DEPOSIT_AMT}" pattern="###,###,###" /></td>							
	          </tr>	         
			  <tr>
	            <td width="120" class="tbl_field">결정일자</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.JUDGE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">비용회수내역</td>
	            <td width="244" class="tbl_list_left">${map.ETC1}</td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="243" class="tbl_list_left">${map.ETC2}</td>
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
                <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
                <c:set var="data_gb" value="${map.MAST_ID}" />
				<c:choose>
	 			<c:when test="${us_rt eq 'A' and data_gb eq NULL}">
	 			<td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="ins"><font color="white">입력</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                </c:when>
                </c:choose>
                <c:choose>
	 			<c:when test="${us_rt eq 'A' and data_gb ne NULL}">
                <td>
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
