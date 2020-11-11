<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>다람쥐</title>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/templateView.css" />
</head>

<body>
	<div id="jb-container">
		<div id="jb-header"><tiles:insertAttribute name="header" /></div>

		<div id="jb-sidebar-left"><tiles:insertAttribute name="left" /></div>
		
		<div id="jb-content"><tiles:insertAttribute name="body" /></div>

		<div id="jb-footer"><tiles:insertAttribute name="footer" /></div>
	</div>
</body>
</html>
