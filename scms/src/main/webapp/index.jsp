<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			
			if(NvlStr($("#userid").val()) != "")
			{	
				document.location.href = "/scms/body.do";
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
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">수의계약 관리시스템</td>
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
 
   
     <table width="600" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">	
      <tr>
        <td width="120" class="tbl_field">아이디</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="USER_ID" name="USER_ID" class="input" style="width:300;">	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">패스워드</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="password" id="PASS_WD" name="PASS_WD" class="input" style="width:300;">	                          
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
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="login"><font color="white">로그인</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
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
