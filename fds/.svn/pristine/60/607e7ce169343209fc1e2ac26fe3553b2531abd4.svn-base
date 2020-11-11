<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>확정일자 수기대장 열람시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			
			if(NvlStr($("#userid").val()) != "")
			{	
				document.location.href = "/fds/daejang/fixedDateList.do";
			}
			
			$("#login").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_login();
			});
			
			
		    $("input").keydown(function (event) {
	                if (event.which == 13) {    //enter
	                	fn_login();
	                }
	        });

			
		});
		
		
		
		
		function fn_login(){
			var comSubmit = new ComSubmit("form1");
									
			comSubmit.setUrl("<c:url value='/common/login.do' />");
			comSubmit.submit();
		}
		
		
	</script>
</head>

<body>

<form id="form1" name="form1" method="post">
<input type="hidden" id="userid" name="userid" value="${sessionScope.userinfo.userId}">
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_fixed">
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

		<table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
	     <tr>
	        <td><!-- <img src="/fds/images/main/main_logo.jpg"> --></td>
	        <td width="30">&nbsp;</td>	        
	      </tr>
	     </table>
	     
	     <table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
		  <tr>
		    <td class="bodyframe">
		    <!--페이지 타이틀 시작 -->
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td class="pupup_title"><img src="/fds/images/popup_title.gif" align="absmiddle"><b>확정일자 수기대장 열람시스템</b></td>
		      </tr>
		      <tr>
		        <td class="margin_title"></td>
		      </tr>
		    </table>
		    <!--페이지 타이틀 끝 -->
		    </td>
		  </tr>
		
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="tbl_line_s"></td>
          </tr>
        </table>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">아이디<font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="USER_ID" name="USER_ID" class="input" style="width:150;">	               
	            </td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">패스워드 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	             	<input type="password" id="PASS_WD" name="PASS_WD" class="input" style="width:150;">	                
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
                    <td><img src="/fds/images/btn_type0_head.gif"></td>
                    <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="login"><font color="white">로그인</font></a></td>
                    <td><img src="/fds/images/btn_type0_end.gif"></td>
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
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
