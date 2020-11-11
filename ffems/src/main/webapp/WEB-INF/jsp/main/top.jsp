<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script language="Javascript">
var today = new Date();
var year = today.getFullYear();

	$(document).ready(function(){
		
		$("#logout").on("click", function(e){ //글쓰기 버튼
			e.preventDefault();
			fn_logout();
		});
		
		$("#home1").on("click", function(e){ //home
			e.preventDefault();
			fn_home();
		});
		
		$("#home2").on("click", function(e){ //home
			e.preventDefault();
			fn_home();
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
	
	
	function fn_home(){
		
		parent.mbody.location.href="/ffems/daejang/ffemsDataDetail.do";
	
	}
</script>
</head>
<body>
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
<tr>
  <td class="pupup_frame" style="padding-right:12px">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr class="top_box"> 
<td class="logo"><a href="#this" class="btn" id="home1"><img src="/ffems/images/main/main_logo.png" alt="홈으로"></a></td>
<td>
<td class="main_info">[${sessionScope.userinfo.deptName}] &nbsp;<b>${sessionScope.userinfo.userName}</b> 님 안녕하세요.</td>		
</tr>
</table>
</td>
</tr> 
</table>
<%@ include file="/WEB-INF/include/include-body.jspf" %>	
</body>
</html>		