<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div class="panel-body">
    <div class="row">
        <div class="col-lg-6">
            <form:form role="form" commandName="registerRequest" action="/register/step3" method="post">
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-check"></i></span>
                    <form:input type="text" class="form-control" placeholder="ID" path="id"/>
                    <form:errors path="id"/>
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                    <form:input type="email" class="form-control" placeholder="Email" path="email"/>
                    <form:errors path="email"/>
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                    <form:input type="text" class="form-control" placeholder="Name" path="name"/>
                    <form:errors path="name"/>
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <form:password class="form-control" placeholder="Password" path="pw"/>
                    <form:errors path="pw"/>
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <form:password class="form-control" placeholder="Password Check" path="checkPw"/>
                    <form:errors path="checkPw"/>
                </div>
                <button type="submit" class="btn btn-default">가입하기</button>
                <button type="reset" class="btn btn-default">취소하기</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>