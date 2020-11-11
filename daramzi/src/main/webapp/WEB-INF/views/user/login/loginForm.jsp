<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <div class="page-wrapper">
        <div class="page-content--bge5">
            <div class="container">
                <div class="login-wrap">
                    <div class="login-content">
                        <div class="login-form">
                            <form role="form" name="loginCommand" action="/login" method="post" onsubmit="return validateLogin()" >
                                <div class="form-group">
                                    <label>아이디</label>
                                    <input class="au-input au-input--full" type="text" name="id" placeholder="id" style="height:40px;">
                                </div>
                                <div class="form-group">
                                    <label>Password</label>
                                    <input class="au-input au-input--full" type="password" name="pw" placeholder="Password" style="height:40px;">
                                </div>
                                <div class="login-checkbox">
                                    <label>
                                        <input type="checkbox" name="remember">Remember Me
                                    </label>
                                    <label>
                                        <a href="#">Forgotten Password?</a>
                                    </label>
                                </div>
                                <button class="au-btn au-btn--block au-btn--green m-b-20" type="submit">로그인</button>
                            </form>
                                <button class="btn btn-danger btn-lg btn-block" onclick="history.back()">취소</button>
                            <div class="register-link">
                                <p>
									회원이 아니신가요?
                                    <a href="#">회원가입 바로가기 </a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

<%-- 
<div class="panel-body">
   <form:form role="form" commandName="loginCommand" action="/login" method="post" onsubmit="return validateLogin()" >
        <fieldset>
            <div class="form-group">
                <form:input type="text" class="form-control" placeholder="ID" path="id"/>
                <form:errors path="id"/>
            </div>
            <div class="form-group">
                 <form:password class="form-control" placeholder="Password" path="pw"/>
                 <form:errors path="pw"/>
            </div>
            <div class="checkbox">
                <label>
                    <form:checkbox path="rememberId"/>아이디 기억
                </label>
            </div>
                <button type="submit" class="btn btn-lg btn-success btn-block">로그인</button>
        </fieldset>
    </form:form>
</div> --%>
</body>
</html>