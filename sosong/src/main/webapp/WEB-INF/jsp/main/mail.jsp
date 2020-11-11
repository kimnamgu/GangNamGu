<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
		
		
			
			$("#print").on("click", function(e){ //프린트 버튼
				e.preventDefault();
				//fn_incidentBasicWrite();
				alert('준비중입니다.');
			});	
			
			
			$("#excel").on("click", function(e){ //엑셀다운 버튼
				e.preventDefault();
				fn_exceldown();
			});
			

 
		});
		
	
	</script>
</head>
<body bgcolor="#FFFFFF">


  <c:forEach var="row" items="${list}" varStatus="status">
				${row.ICDNT_NO} &nbsp; ${row.PERFORM_USERID}<br> 							
					  
			
			
				  </c:forEach>				
</body>
</html>