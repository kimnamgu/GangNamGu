<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var i = 0;
		$(document).ready(function(){
			
			if($("#USER_ID").val() == '' || $("#USER_ID").val() == null)
			{
				alert('직원정보에 없는 아이디 입니다.');
			}
			
			$("#write").on("click", function(e){ //작성하기 버튼
				
				if($("#USER_ID").val() == '' || $("#USER_ID").val() == null)
				{
					alert('직원정보에 없는 아이디 입니다.');
					return false;
				}
							
				if($("#USER_TEL").val() == null || $("#USER_TEL").val() == "")
				{
					alert('전화번호를 입력하세요.');
					$("#USER_TEL").focus();
					return false();
				}	
				
				if($("#USER_PASS").val() == null || $("#USER_PASS").val() == "")
				{
					alert('패스워드를 입력하세요.');
					$("#USER_PASS").focus();
					return false();
				}
				
				
				if($("#USER_PASS").val().length < 6 )
				{
					alert('패스워드를  6자리 이상 입력하세요.');
					$("#USER_PASS").focus();
					return false();
				}
				
				
				if($("#USER_PASS_C").val() == null || $("#USER_PASS_C").val() == "")
				{
					alert('패스워드 확인을 입력하세요.');
					$("#USER_PASS_C").focus();
					return false();
				}
				
				
				if($("#USER_PASS").val() !=  $("#USER_PASS_C").val())
				{
					alert('패스워드와 패스워드 확인이 동일하지 않습니다.');
					$("#USER_PASS_C").focus();
					return false();
				}
				
				if($("#APPLY_REASON").val() == null || $("#APPLY_REASON").val() == "")
				{
					alert('사유를  입력하세요.')
					$("#APPLY_REASON").focus();
					return false();
				}
								
				e.preventDefault();							
				fn_insertUserInfo();
			});
			
			//changeURL();
			    
		});
		
		function changeURL(){			
	        parent.location.hash = "image"+i;
	        i++;
	    }
		
		function fn_insertUserInfo(){
			var comSubmit = new ComSubmit("form1");
					
			comSubmit.setUrl("<c:url value='/common/insertUserinfo.do' />");
			comSubmit.submit();
		}	
	
	</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${map.USER_ID}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="U">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${map.DEPT_ID}">
<input type="hidden" id="DEPT_RANK" name="DEPT_RANK" value="${map.DEPT_RANK}">
<input type="hidden" id="CLASS_ID" name="CLASS_ID" value="${map.CLASS_ID}">
<input type="hidden" id="CLASS_NAME" name="CLASS_NAME" value="${map.CLASS_NAME}">
<input type="hidden" id="POSITION_ID" name="POSITION_ID" value="${map.POSITION_ID}">
<input type="hidden" id="POSITION_NAME" name="POSITION_NAME" value="${map.POSITION_NAME}">
<input type="hidden" id="USER_STATUS" name="USER_STATUS" value="Z">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">사용자신청</td>
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
				
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/sosong/images/title_ico.gif" align="absmiddle">사용자등록</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	         
			 <tr>
	            <td width="120" class="tbl_field">이름</td>
	            <td width="243" class="tbl_list_left">
	               ${map.USER_NAME}
	               <input type="hidden" id="USER_NAME" name="USER_NAME" value="${map.USER_NAME}">             
	            </td>
				<td width="120" class="tbl_field">부서</td>
	            <td width="244" class="tbl_list_left">
	               ${map.DEPT_NAME}
	               <input type="hidden" id="DEPT_NAME" name="DEPT_NAME" value="${map.DEPT_NAME}"> 
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="USER_TEL" name="USER_TEL" value="${map.TEL}" class="input" style="width:557;">	                
	            </td>
	          </tr>	          
			  <tr>
	            <td width="120" class="tbl_field">패스워드</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="password" id="USER_PASS" name="USER_PASS" class="input" style="width:557;">	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">패스워드 확인</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="password" id="USER_PASS_C" name="USER_PASS_C" class="input" style="width:557;">	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">신청사유</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="APPLY_REASON" name="APPLY_REASON" class="input" style="width:557;">	                
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
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
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
