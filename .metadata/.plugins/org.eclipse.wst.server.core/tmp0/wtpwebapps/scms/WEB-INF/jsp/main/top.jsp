<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700" rel="stylesheet">
<style type="text/css">
    .top_box {width: 100%; position: relative;}
    .logo {position: absolute; left: 30px; top: 20px;}
    .main_title {font-size: 32px; font-weight: 700; letter-spacing: -1px;position: absolute; left: 230px; top: 40px;}
    .main_title:before {content: ""; display: inline-block; width: 4px; height: 25px; background-color: red; margin-right: 10px; position: relative; top: 2px;}
    .main_info {width: 100%; text-align: right; padding-right: 120px;}
    .login_box {position: absolute; right: 40px;top: 12px;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#logout").on("click", function(e){ //글쓰기 버튼
			e.preventDefault();
			fn_logout();
		});	
				
	});
	
	$(window).bind("beforeunload", function ()
	{
		if (event.clientY < 0) {
			
			var comAjax = new ComAjax();
			//alert( '로그아웃 됩니다.' );
			comAjax.setUrl("<c:url value='/logout.do' />")
			comAjax.ajax();			
		}
			
	});
	
	
	function fn_logout(){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/logout.do' />");
		comSubmit.submit();
	}
	

</script>
</head>
<body>
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
<tr>
  <td class="pupup_frame" style="padding-right:12px">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr class="top_box"> 
<td class="logo"><img src="/scms/images/main/main_logo.jpg" width="140" height="50"></td>
<td class="main_title">수의계약 관리시스템</td>
<td class="main_info">${sessionScope.userinfo.deptName} <b>${sessionScope.userinfo.userName}</b> 님 안녕하세요.</td>
<td>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>    
    <td> 
    <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
       <tr>
         <td><img src="/scms/images/btn_type1_head.gif"></td>
         <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="logout">로그아웃</a></td>
         <td><img src="/scms/images/btn_type1_end.gif"></td>
       </tr>
     </table>                               
    </td>		
  </tr>
</table>
</td>
</tr> 
</table>
</td>
</tr>
</table>
<%@ include file="/WEB-INF/include/include-body.jspf" %>	
</body>
</html>