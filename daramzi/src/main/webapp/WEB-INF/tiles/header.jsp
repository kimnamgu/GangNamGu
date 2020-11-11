  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="javascript" type="text/javascript" src="/resources/js/user/login/login.js"></script>

<!-- Fontfaces CSS-->
<link href="/resources/bootstrap/css/font-face.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

<!-- Bootstrap CSS-->
<link href="/resources/bootstrap/vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">

<!-- Vendor CSS-->
<link href="/resources/bootstrap/vendor/animsition/animsition.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/wow/animate.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/slick/slick.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/select2/select2.min.css" rel="stylesheet" media="all">
<link href="/resources/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">

<!-- Main CSS-->
<link href="/resources/bootstrap/css/theme.css" rel="stylesheet" media="all">



<!-- Jquery JS-->
<script src="/resources/bootstrap/vendor/jquery-3.2.1.min.js"></script>
<!-- Bootstrap JS-->
<script src="/resources/bootstrap/vendor/bootstrap-4.1/popper.min.js"></script>
<script src="/resources/bootstrap/vendor/bootstrap-4.1/bootstrap.min.js"></script>
<!-- Vendor JS       -->
<script src="/resources/bootstrap/vendor/slick/slick.min.js"></script>
<script src="/resources/bootstrap/vendor/wow/wow.min.js"></script>
<script src="/resources/bootstrap/vendor/animsition/animsition.min.js"></script>
<script src="/resources/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
</script>
<script src="/resources/bootstrap/vendor/counter-up/jquery.waypoints.min.js"></script>
<script src="/resources/bootstrap/vendor/counter-up/jquery.counterup.min.js"></script>
<script src="/resources/bootstrap/vendor/circle-progress/circle-progress.min.js"></script>
<script src="/resources/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="/resources/bootstrap/vendor/chartjs/Chart.bundle.min.js"></script>
<script src="/resources/bootstrap/vendor/select2/select2.min.js"></script>

<!-- Main JS-->
<script src="/resources/bootstrap/js/main.js"></script>

</head>
<body>
<h4 class="pb-2 display-5" style="display:inline;">다람쥐에 오신걸 환영합니다!!</h4>
<button type="button" class="btn btn-warning" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/'">홈</button>

<c:catch>
		    <c:choose>
		        <c:when test="${empty authInfo }">
						<button type="button" class="btn btn-success" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/login'">로그인</button>
						<button type="button" class="btn btn-info" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/register/step1'">회원가입</button>
		                 
		                 <div id="login">
		                 	아이디 : <input type="text" class="au-input au-input--xl" style="min-width:100px!important;width:200px;height:40px;" id="loginID"/>
		                 	pw : <input type="password" class="au-input au-input--xl" style="min-width:100px!important;width:200px;height:40px;" id="loginPW"/>
		                 	<button type="button" class="btn btn-success" style="height:30px;padding:1px;" onclick="login()">로그인</button>
	                 	</div>
		        </c:when>
		        <c:otherwise>
		            <c:choose>
		                <c:when test="${authInfo.grade eq '1' }">
		                       	<h4 class="pb-2 display-5" style="display:inline;">관리자 ${authInfo.name }님, 환영합니다.</h4>
		                       <button type="button" class="btn btn-secondary" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/logout'">로그아웃</button>
		                       <button type="button" class="btn btn-primary" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/admin'">관리자페이지</button>
		                </c:when>
		                <c:otherwise>
		                       <h4 class="pb-2 display-5" style="display:inline;">${authInfo.name }님, 반갑습니다!</h4>
		                       <button type="button" class="btn btn-secondary" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/logout'">로그아웃</button>
		                       <button type="button" class="btn btn-primary" style="height:30px;padding:1px;margin:3px;" onclick="location.href='/admin'">관리자페이지</button>
		                </c:otherwise>
		            </c:choose>
		        </c:otherwise>
		    </c:choose>
		</c:catch>
</body>
</html>
 