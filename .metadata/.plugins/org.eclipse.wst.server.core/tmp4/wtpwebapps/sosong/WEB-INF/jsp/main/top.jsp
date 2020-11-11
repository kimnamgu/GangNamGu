<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
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

<table>
<tr>
<td><img src="/sosong/images/main/main_title.jpg"></td>
<td>${sessionScope.userinfo.deptName} <b>${sessionScope.userinfo.userName}</b> 님 안녕하세요.</td>
<td><a href="/sosong/logout.do"><span></span></a>


<table border="0" cellspacing="0" cellpadding="0">
  <tr>    
    <td>
    <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
      <tr>
        <td><img src="/sosong/images/btn_type0_head.gif"></td>
        <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="logout"><font color="white">로그아웃</font></a></td>
        <td><img src="/sosong/images/btn_type0_end.gif"></td>
      </tr>
    </table>                
    </td>		
  </tr>
</table>

</td> 
</table>
<br>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>