<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			fn_initial_setting();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_idApproveList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( user_right == 'A')
				{	
					e.preventDefault();
					fn_updateRight();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
		
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( user_right == 'A')
				{	
					e.preventDefault();				
					fn_deleteIdApproveList();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
		
		});
		
		
		function fn_initial_setting(){
			var rad_val = "${map.USER_RIGHT}";
			
			$('input:radio[name=USER_RIGHT]:input[value=' + rad_val + ']').attr("checked", true);
		}
		
		function fn_idApproveList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/idApproveList.do' />");
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
		
	
		
		function fn_updateRight(){			
			var comSubmit = new ComSubmit("form1");
			var userid = "${map.USER_ID}";
			
			if(confirm('권한을 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/common/updateIdApproveList.do' />");
				comSubmit.addParam("USER_ID", userid);
				comSubmit.addParam("USER_STATUS", "${param.USER_STATUS}");
				comSubmit.submit();
			}
		}
		
		
		
		function fn_deleteIdApproveList(){
			var comSubmit = new ComSubmit("form1");
			var userid = "${map.USER_ID}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/common/deleteIdApproveList.do' />");
				comSubmit.addParam("USER_ID", userid);
				comSubmit.addParam("USER_STATUS", "${param.USER_STATUS}");
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


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">주요사업관리시스템</td>
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
			<li class="Lcurrent" id='tab1'><span><a href="#this" class="btn" id="basic">사용자정보</a></span></li>
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
	            <td width="120" class="tbl_field">아이디</td>
	            <td width="243" class="tbl_list_left">${map.USER_ID}</td>
				<td width="120" class="tbl_field">이름</td>
	            <td width="244" class="tbl_list_left">${map.USER_NAME}</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">부서</td>
	            <td width="243" class="tbl_list_left">${map.DEPT_NAME}</td>
				<td width="120" class="tbl_field">직급</td>
	            <td width="244" class="tbl_list_left">${map.CLASS_NAME}</td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">권한</td>
	            <td width="243" class="tbl_list_left"><input type="radio" id="USER_RIGHT1" name="USER_RIGHT" value="U" class="input" onfocus="this.blur()"> 일반 &nbsp;<input type="radio" id="USER_RIGHT2" name="USER_RIGHT" value="A" class="input" onfocus="this.blur()"> 관리자&nbsp;</td>
				<td width="120" class="tbl_field">신청사유</td>
	            <td width="244" class="tbl_list_left">${map.APPLY_REASON}</td>	            						
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
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                </c:when>
                </c:choose>                		
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type1_head.gif"></td>
                    <td background="/bims/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/bims/images/btn_type1_end.gif"></td>
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
