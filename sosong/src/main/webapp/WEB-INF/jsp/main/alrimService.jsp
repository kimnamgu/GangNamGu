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

<table>
  <c:forEach var="row" items="${list}" varStatus="status">
  	<tr>
		<td>${row.ICDNT_NO}</td>
		<td>${row.PERFORM_USERID}</td>
		<td>${row.APPEAL_RESPONSE_DATE}</td>
		<td>${row.PERFORM_USERHNO}</td>
	</tr>		
  </c:forEach>
  </table>				
</body>
</html>