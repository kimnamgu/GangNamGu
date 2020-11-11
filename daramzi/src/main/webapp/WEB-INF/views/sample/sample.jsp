<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="ko">
<head>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
</head>
<body >

<form id="upForm1" action="/sample/sampleinsert" method="post" enctype="multipart/form-data">
	<ul>
	  <li>
	    <label for="title">제목</label>
	    <input type="text" id="title" name="title" size="20" maxlength="50" placeholder="제목입력" autofocus>
	  <li>
	    <label for="file1">파일선택</label>
	    <input type="file" id="file1" name="file1">
	  <li>    
	    <input type="submit" id="upPro1" value="단일 파일 업로드">
	</ul>
</form>
</body>
</html>
