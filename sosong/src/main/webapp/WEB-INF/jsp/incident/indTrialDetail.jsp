<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tab_gb = "${param.ICDNT_TRIAL_NO}";
		var hm_gb = "${param.INCDNT_GB}";		
		var url_gb = "${param.URL_GB}";		//메뉴 어디에서 연결되었는가? 1: 소송현황  2: 관리자 알림대상조회
		var chk_lgb = "";
		
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				chk_lgb = "1";
				fn_indIncidentList(tab_gb);
			});
			
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_indTrialUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_deleteIndTrial();
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
				fn_indIncidentList('33');				
			});
		
		
			
			
			fn_tab_setting();
		});
		
		
		
		function fn_tab_setting(){
			var tab_gb = "${param.ICDNT_TRIAL_NO}";
			
			if (tab_gb == '11'){
				$("#tab2").attr('class','Lcurrent');
				$("#APPEAL_TIT").html("답변서<br><br>제출일자");
				$("#ARGUE_TIT").html("변론기일");
				$("#SUBMIT_TIT").html("항소장 제출일자");
				$("#alrim_date1").css("display","block");
				$("#alrim_date2").css("display","block");
			}	
			else if (tab_gb == '12'){
				$("#tab3").attr('class','Lcurrent');
				$("#APPEAL_TIT").html("답변서<br><br>제출일자");
				$("#ARGUE_TIT").html("심문기일");
				$("#SUBMIT_TIT").html("");
				$("#alrim_date1").css("display","block");				
			}	
			else if (tab_gb == '13'){
				$("#tab4").attr('class','Lcurrent');
			}	
			else if (tab_gb == '21'){
				$("#tab5").attr('class','Lcurrent');
				$("#APPEAL_TIT").html("답변서/항소이유서<br><br>제출일자");
				$("#ARGUE_TIT").html("변론기일");
				$("#SUBMIT_TIT").html("상고장 제출일자");
				$("#alrim_date1").css("display","block");
				$("#alrim_date2").css("display","block");
			}	
			else if (tab_gb == '31'){
				$("#tab6").attr('class','Lcurrent');
				$("#APPEAL_TIT").html("답변서/상고이유서<br><br>제출일자");
				$("#ARGUE_TIT").html("");				
				$("#alrim_date1").css("display","block");
			}	
			else if (tab_gb == '32'){
				$("#tab7").attr('class','Lcurrent');
			}	
			else if (tab_gb == '33'){
				$("#tab8").attr('class','Lcurrent');
			}
		
		}
				
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
			comSubmit.submit();
		}		
		
		function fn_indIncidentList(dgb){
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			var gngb = "${param.GNGB}";
			
			if(url_gb == "1" )
			{	
				if( chk_lgb == "1")
				{
					if(gngb == "1")
						comSubmit.setUrl("<c:url value='/incident/allSimList.do?GNGB=1' />");
					else							
						comSubmit.setUrl("<c:url value='/incident/allSimList.do' />");
				}
				else{
					comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
				}
			}
			else if(url_gb == "2"){
				if( chk_lgb == "1")
				{
					comSubmit.setUrl("<c:url value='/common/allSmsSendList.do' />");
				}
				else{
					comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
				}				
			}
			else{
				comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
			}
			
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
		
		function fn_indTrialUpdate(){			
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			var icdnt_no = "${map.ICDNT_NO}";
			
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", tab_gb);
			comSubmit.addParam("ICDNT_NO", icdnt_no);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			
			if(tab_gb == "12" || tab_gb == "13") //신청사건 , 신청사건 (즉시항고)이면
				comSubmit.addParam("TRNO_GR_CD", "16"); //신청사건
			else
				comSubmit.addParam("TRNO_GR_CD", "15");
			
			comSubmit.setUrl("<c:url value='/incident/indTrialUpdate.do' />");
			comSubmit.submit();
		}
	
		function fn_deleteIndTrial(){
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			var icdnt_no = "${map.ICDNT_NO}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/incident/deleteIndTrial.do' />");
				
				comSubmit.addParam("MID", mid);				
				comSubmit.addParam("ICDNT_TRIAL_NO", tab_gb);
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
<input type="hidden" id="GNGB" name="GNGB" value="${param.GNGB}">

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
	            <td width="120" class="tbl_field">사건번호</td>
	            <td width="243" class="tbl_list_left">${map.ICDNT_NO}</td>
				<td width="120" class="tbl_field">중요소송여부</td>
	            <td width="244" class="tbl_list_left">${map.IMPORTANT_GB1}</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">직접수행여부</td>
	            <td width="243" class="tbl_list_left">${map.DIRECT_GB1}</td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
	          </tr>
	          <tr>
	          	<td width="120" class="tbl_field">수행자</td>
	            <td width="243" class="tbl_list_left">${map.PERFORM_PERSON}</td>
	            <td width="120" class="tbl_field">소송대리인</td>
	            <td width="244" class="tbl_list_left">${map.CHRG_LAWYER}</td>											
	          </tr>
	          <tr>
	          	<td width="120" class="tbl_field">진행완료여부</td>
	            <td width="243" class="tbl_list_left">${map.DONE_YN1}</td>
	            <td width="120" class="tbl_field">승패여부</td>
	            <td width="244" class="tbl_list_left">${map.WIN_LOSE_GB1}</td>					
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">제소일</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.SUE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">접수일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.ACCEPT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>							
	          </tr>
	          <tr id="alrim_date1" style="display:none;">
	            <td width="120" class="tbl_field"><span id="APPEAL_TIT"></span></td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.APPEAL_RESPONSE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/> &nbsp;/ 
	            <fmt:parseDate value="${map.APPEAL_REASON_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>
	            </td>
				<td width="120" class="tbl_field"><span id="ARGUE_TIT"></span></td>
	            <td width="244" class="tbl_list_left">
	            <c:set var="sday" value="${fn:split(map.ARGUE_SET_DATE,';')}" />
				<c:forEach var="setday" items="${sday}" varStatus="i">
					${i.count}차 <fmt:parseDate value="${setday}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/><br>
				</c:forEach>
	            </td>							
	          </tr>	        
	          <tr id="alrim_date2" style="display:none;">
	            <td width="120" class="tbl_field"><span id="SUBMIT_TIT"></span></td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.APPEAL_SUBMIT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">판결내역</td>
	            <td width="243" class="tbl_list_left">
	            <c:set var="cont_gb1" value="${map.JUDGE_CONT_GB1}" />
	            <c:set var="cont" value="${map.JUDGE_CONTENT}" />
				<c:choose>							
	            <c:when test="${cont_gb1 eq NULL}">
	            ${cont}
	            </c:when>
	            <c:otherwise>
	            ${cont_gb1}
	            </c:otherwise>
	            </c:choose>
	            
	            </td>
				<td width="120" class="tbl_field">판결내역 상세</td>
	            <td width="244" class="tbl_list_left">${map.JUDGE_CONT_NOTES}</td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">판결일자</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.JUDGE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">접수일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.JG_ACCPT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">착수금</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.DEPOSIT_AMT}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">착수금 지급일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.DEPOSIT_PAY_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">사례금</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.REWARD_AMT}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">사례금 지급일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.REWARD_PAY_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">인지대</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.STAMP_AMT}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">지급일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.STAMP_PAY_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">송달료</td>
	            <td width="243" class="tbl_list_left"><fmt:formatNumber value="${map.TRANSMIT_FEE}" pattern="###,###,###" /></td>
				<td width="120" class="tbl_field">지급일자</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.TRANSMIT_PAY_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">기타</td>
	            <td width="243" class="tbl_list_left">${map.ETC1}</td>
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
