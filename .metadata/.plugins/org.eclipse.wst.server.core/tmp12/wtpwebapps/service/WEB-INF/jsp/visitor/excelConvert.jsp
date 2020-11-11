<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ page import="java.text.SimpleDateFormat" %>
<%
	//파일명에 다운로드 날짜 붙여주기 위해 작성
	Date date=new Date();
	SimpleDateFormat dayformat=new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
	SimpleDateFormat hourformat=new SimpleDateFormat("hhmmss",Locale.KOREA);
	
	String day=dayformat.format(date);
	String hour=hourformat.format(date);
	String fileName="visitrecord_" + day+"_"+hour;
	
	//필수선언부분
	//.getBytes("KSC5601"),"8859_1")을 통한 한글파일명 깨짐 방지
    response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls"); 
    response.setHeader("Content-Description", "JSP Generated Data"); 
    response.setContentType("application/vnd.ms-excel");
%>

<!DOCTYPE html>
<html lang="ko" style="overflow:hidden">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-saclable=no">
<title></title>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">

<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
<thead>
	<tr align="center">
		<th scope="col" bgcolor="CDCDCD">NO</th>
		<th scope="col" bgcolor="CDCDCD">방문시간</th>
		<th scope="col" bgcolor="CDCDCD">고유번호</th>
		<th scope="col" bgcolor="CDCDCD">이름</th>
		<th scope="col" bgcolor="CDCDCD">성별</th>
		<th scope="col" bgcolor="CDCDCD">내외국인</th>
		<th scope="col" bgcolor="CDCDCD">통신사</th>
		<th scope="col" bgcolor="CDCDCD">연락처</th>
		<th scope="col" bgcolor="CDCDCD">SITECODE</th>
		<th scope="col" bgcolor="CDCDCD">업소명</th>
		<th scope="col" bgcolor="CDCDCD">업소주소</th>
		<th scope="col" bgcolor="CDCDCD">업소연락처</th>
	</tr>
</thead>
<tbody>

			<c:forEach var="resultlist" items="${resultlist}">
				<tr>
					<td style="text-align:left;">${resultlist.RNUM} </td>
					<td style="text-align:left;">${resultlist.INS_DATE} </td>
					<td style="text-align:left;">${resultlist.UUID} </td>
					<td style="text-align:left;">${resultlist.NAME} </td>
					<td style="text-align:left;">${resultlist.SEX} </td>
					<td style="text-align:left;">${resultlist.NATIONAL_INFO} </td>
					<td style="text-align:left;">${resultlist.AGENCY} </td>
					<td style="text-align:left;mso-number-format:'\@';">${resultlist.PHONE} </td>
					<td style="text-align:left;">${resultlist.SITECODE} </td>
					<td style="text-align:left;">${resultlist.GIGWAN_NAME} </td>
					<td style="text-align:left;">${resultlist.ADDRESS_NAME} </td>
					<td style="text-align:left;">${resultlist.PHONE_NO} </td>
					
				</tr>
			</c:forEach>




</tbody>

</table>
</body>
</html>