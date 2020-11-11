<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">

		var tri_no = "${param.ICDNT_TRIAL_NO}";
		var hm_gb = "${param.INCDNT_GB}";

		$(document).ready(function(){
			
			fn_tab_setting();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_indIncidentList('33');
			});
			
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_updateThirdTrial3();
				}
				else{
					alert('권한이 없습니다.');
				}
			});			
		
		});
		
		
		
		function fn_tab_setting(){
		
			if(hm_gb != "")
				$('input:radio[name=INCDNT_GB]:input[value=' + hm_gb + ']').attr("checked", true);
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
		
		
		
		function fn_updateThirdTrial3(){
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			
			if($('#ICDNT_NO').val() == "")
			{
				alert('사건명을 입력하세요!!');
				$('#ICDNT_NO').focus();
				return false();
			}
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{	
				comSubmit.setUrl("<c:url value='/incident/updateIndTrial.do' />");			
				comSubmit.addParam("MID", mid);		
				comSubmit.submit();
			}
		}
		
		
	
		
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="ICDNT_TRIAL_NO" name="ICDNT_TRIAL_NO" value="33">
<input type="hidden" id="SUE_DATE" name="SUE_DATE" value="">
<input type="hidden" id="ACCEPT_DATE" name="ACCEPT_DATE" value="">
<input type="hidden" id="IMPORTANT_GB" name="IMPORTANT_GB" value="">
<input type="hidden" id="DIRECT_GB" name="DIRECT_GB" value="">
<input type="hidden" id="PERFORM_USERID" name="PERFORM_USERID" value="">
<input type="hidden" id="PERFORM_PERSON" name="PERFORM_PERSON" value="">
<input type="hidden" id="CHRG_LAWYER" name="CHRG_LAWYER" value="">
<input type="hidden" id="DONE_YN" name="DONE_YN" value="">
<input type="hidden" id="WIN_LOSE_GB" name="WIN_LOSE_GB" value="">
<input type="hidden" id="JUDGE_CONT_GB" name="JUDGE_CONT_GB" value="">
<input type="hidden" id="JUDGE_CONTENT" name="JUDGE_CONTENT" value="">
<input type="hidden" id="JUDGE_CONT_NOTES" name="JUDGE_CONT_NOTES" value="">
<input type="hidden" id="JG_ACCPT_DATE" name="JG_ACCPT_DATE" value="">
<input type="hidden" id="DEPOSIT_PAY_DATE" name="DEPOSIT_PAY_DATE" value="">
<input type="hidden" id="REWARD_AMT" name="REWARD_AMT" value="">
<input type="hidden" id="REWARD_PAY_DATE" name="REWARD_PAY_DATE" value="">
<input type="hidden" id="STAMP_AMT" name="STAMP_AMT" value="">
<input type="hidden" id="STAMP_PAY_DATE" name="STAMP_PAY_DATE" value="">
<input type="hidden" id="TRANSMIT_FEE" name="TRANSMIT_FEE" value="">
<input type="hidden" id="TRANSMIT_PAY_DATE" name="TRANSMIT_PAY_DATE" value="">


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
	 		<li class="Lcurrent" id='tab6'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li>
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
	            <td width="243" class="tbl_list_left"><input type="radio" id="INCDNT_GB1" name="INCDNT_GB" value="1" class="input" onfocus="this.blur()"> 행정소송 &nbsp;<input type="radio" id="INCDNT_GB2" name="INCDNT_GB" value="2" class="input" onfocus="this.blur()"> 민사소송&nbsp;<input type="radio" id="INCDNT_GB3" name="INCDNT_GB" value="3" class="input" onfocus="this.blur()"> 국가소송</td>
				<td width="120" class="tbl_field">사건번호</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="ICDNT_NO" name="ICDNT_NO" value="${map.ICDNT_NO}" class="input" style="width:200;"></td>							
	          </tr>         
	          <tr>
	            <td width="120" class="tbl_field">비용확정액</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="DEPOSIT_AMT" name="DEPOSIT_AMT" value="<fmt:formatNumber value="${map.DEPOSIT_AMT}" pattern="###,###,###" />" class="input" style="width:100;"></td>
				<td width="120" class="tbl_field">결정일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="JUDGE_DATE" name="JUDGE_DATE" value="<fmt:parseDate value="${map.JUDGE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, JUDGE_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>							
	          </tr>			  
	          <tr>
	            <td width="120" class="tbl_field">비용회수내역</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="ETC1" name="ETC1" value="${map.ETC1}" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field">비고</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="ETC2" name="ETC2" value="${map.ETC2}" class="input" style="width:200;"></td>	            						
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
