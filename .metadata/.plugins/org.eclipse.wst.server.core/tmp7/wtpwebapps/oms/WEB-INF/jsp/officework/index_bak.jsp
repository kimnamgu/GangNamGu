<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:forward page="/officework/officeworkRegList.do"/>
<html>
<body>
<form action="/common/login.do" method="post">
	<input type="text" name="id"/>
	<input type="password" name="password"/>
	<input type="submit" value="전송" />
</form>
</body>
</html>