  <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
</head>
<body>
<span>다람쥐에 오신걸 환영합니다!!</span>
<a href="/">홈</a>
<c:catch>
		    <c:choose>
		        <c:when test="${empty authInfo }">
		                 <a href="/login">로그인</a>
		                 <a href="/register/step1">회원가입</a>
		        </c:when>
		        <c:otherwise>
		            <c:choose>
		                <c:when test="${authInfo.grade eq '1' }">
		                       	관리자 ${authInfo.name }님, 환영합니다.
		                       <a href="/logout"><i class="fa fa-sign-out"></i> 로그아웃</a>
		                       <a href="/admin"><i class="fa fa-sign-out"></i> 관리자페이지</a>
		                </c:when>
		                <c:otherwise>
		                       ${authInfo.name }님, 반갑습니다!
		                       <a href="/logout"><i class="fa fa-sign-out"></i> 로그아웃</a>
		                </c:otherwise>
		            </c:choose>
		        </c:otherwise>
		    </c:choose>
		</c:catch>
		<!-- <marquee width="500">♡&lt;다람쥐에 오신걸 환영합니다.&gt;♡</marquee><font size="5"></font> -->
</body>
</html>
 