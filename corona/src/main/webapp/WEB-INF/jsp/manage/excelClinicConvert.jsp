<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ page import="java.text.SimpleDateFormat" %>
<%
	//파일명에 다운로드 날짜 붙여주기 위해 작성
	Date date=new Date();
	SimpleDateFormat dayformat=new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
	
	String day=dayformat.format(date);
	String fileName="clinicRecord_" + day;
	
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
		<th scope="col" bgcolor="CDCDCD">입력일자</th>
		<th scope="col" bgcolor="CDCDCD">방문일자</th>
		<th scope="col" bgcolor="CDCDCD">핸드폰번호</th>
		<th scope="col" bgcolor="CDCDCD">이름</th>
		<th scope="col" bgcolor="CDCDCD">성별</th>
		<th scope="col" bgcolor="CDCDCD">생년월일</th>
		<th scope="col" bgcolor="CDCDCD">직업</th>
		<th scope="col" bgcolor="CDCDCD">검사케이스</th>
		<th scope="col" bgcolor="CDCDCD">사례분류</th>
		<th scope="col" bgcolor="CDCDCD">경로구분</th>
		<th scope="col" bgcolor="CDCDCD">거주지대구분</th>
		<th scope="col" bgcolor="CDCDCD">거주지소구분</th>
		<th scope="col" bgcolor="CDCDCD">거주지주소</th>
		<th scope="col" bgcolor="CDCDCD">거주지주소상세</th>
		<th scope="col" bgcolor="CDCDCD">비고</th>
		<th scope="col" bgcolor="CDCDCD">입력자ID</th>
		<th scope="col" bgcolor="CDCDCD">기입일</th>

	</tr>
</thead>
<tbody>

			<c:forEach var="resultlist" items="${resultlist}">
				<tr>
					<td style="text-align:left;">${resultlist.ID} </td>
					<td style="text-align:left;">${resultlist.WRITE_DATE} </td>
					<td style="text-align:left;">${resultlist.CLINIC_VISIT_DATE} </td>
					<td style="text-align:left;mso-number-format:'\@';">${resultlist.CELL_NUM} </td>
					
					<td style="text-align:left;">${resultlist.NAME} </td>
					<td style="text-align:left;">${resultlist.SEX} </td>
					<td style="text-align:left;">${resultlist.BIRTH} </td>
					
					<td style="text-align:left;">${resultlist.JOB} </td>
					<td style="text-align:left;">${resultlist.INSPECTION_CASE} </td>
					<td style="text-align:left;">${resultlist.SARAE_GUBUN} </td>
					<td style="text-align:left;">${resultlist.SUSPICION_GUBUN} </td>
					
					<td style="text-align:left;">${resultlist.SUSPICION_DAE} </td>
					<td style="text-align:left;">${resultlist.SUSPICION_SO} </td>
					<td style="text-align:left;">${resultlist.ORG_ADDRESS} </td>
					<td style="text-align:left;">${resultlist.ORG_ADDRESS_DTL} </td>
					<td style="text-align:left;">${resultlist.REMARK} </td>
					<td style="text-align:left;">${resultlist.INS_ID} </td>
					<td style="text-align:left;">${resultlist.INS_DATE} </td>
					
				</tr>
			</c:forEach>




</tbody>

</table>
</body>
</html>