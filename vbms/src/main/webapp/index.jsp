<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위반건축물 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			
			if(NvlStr($("#userid").val()) != "")
			{	
				document.location.href = "/vbms/daejang/violBuildingList.do";
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
    <table width="620" border="0" cellspacing="1" cellpadding="0" class="tbl"> 
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/vbms/images/popup_title.gif" align="absmiddle"></a>위반건축물  관리시스템</td>
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
 
    <table width="620" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">      
      <tr>
        <td width="120" class="tbl_field">아이디</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="USER_ID" name="USER_ID" class="input" style="width:150;">	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">비밀번호</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="password" id="PASS_WD" name="PASS_WD" class="input" style="width:150;">	                          
        </td>
      </tr>      
    </table>
    </td>
   </tr>
  <tr>
  <td class="pupup_frame" style="padding-right:12px">	  
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="620" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">     
     <tr>       
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>   	           
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/vbms/images/btn_type0_head.gif"></td>
               <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="login"><font color="white">로그인</font></a></td>
               <td><img src="/vbms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>                                       
         </tr>
       </table>
       </td>
     </tr>
     <tr>
       <td class="margin_btn"></td>
     </tr>
   </table>	
	</td>
	</tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
