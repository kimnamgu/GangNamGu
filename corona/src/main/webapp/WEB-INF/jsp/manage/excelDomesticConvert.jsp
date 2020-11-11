<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ page import="java.text.SimpleDateFormat" %>
<%
	//파일명에 다운로드 날짜 붙여주기 위해 작성
	Date date=new Date();
	SimpleDateFormat dayformat=new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
	
	String day=dayformat.format(date);
	String fileName="domesticRecord_" + day;
	
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
				<th scope="col" bgcolor="CDCDCD">핸드폰번호</th>
				<th scope="col" bgcolor="CDCDCD">이름</th>
				<th scope="col" bgcolor="CDCDCD">성별</th>
				<th scope="col" bgcolor="CDCDCD">생년월일</th>
				<th scope="col" bgcolor="CDCDCD">직업</th>
				<th scope="col" bgcolor="CDCDCD">자가격리지동</th>
				<th scope="col" bgcolor="CDCDCD">자가격리지</th>
				<th scope="col" bgcolor="CDCDCD">자가격리지상세</th>
				<th scope="col" bgcolor="CDCDCD">환자번호</th>
				<th scope="col" bgcolor="CDCDCD">환자명</th>
				<th scope="col" bgcolor="CDCDCD">접촉자번호</th>
				<th scope="col" bgcolor="CDCDCD">접촉자명</th>
				<th scope="col" bgcolor="CDCDCD">최종접촉일</th>
				<th scope="col" bgcolor="CDCDCD">격리시작일</th>
				<th scope="col" bgcolor="CDCDCD">격리해제일</th>
				<th scope="col" bgcolor="CDCDCD">접촉장소</th>
				<th scope="col" bgcolor="CDCDCD">접촉유형</th>
				<th scope="col" bgcolor="CDCDCD">접촉자 구분</th>
				<th scope="col" bgcolor="CDCDCD">환자여부</th>
				<th scope="col" bgcolor="CDCDCD">통지서 발급 여부</th>
				<th scope="col" bgcolor="CDCDCD">소속</th>
				<th scope="col" bgcolor="CDCDCD">직위</th>
				<th scope="col" bgcolor="CDCDCD">직급</th>
				<th scope="col" bgcolor="CDCDCD">전담공무원명</th>
				<th scope="col" bgcolor="CDCDCD">내선번호</th>

	</tr>
</thead>
<tbody>

			<c:forEach var="resultlist" items="${resultlist}">
				<tr>
					<td style="text-align:left;">${resultlist.ID} </td>
					<td style="text-align:left;">${resultlist.WRITE_DATE} </td>
					<td style="text-align:left;mso-number-format:'\@';">${resultlist.CELL_NUM} </td>
					
					<td style="text-align:left;">${resultlist.NAME} </td>
					<td style="text-align:left;">${resultlist.SEX} </td>
					<td style="text-align:left;">${resultlist.BIRTH} </td>
					<td style="text-align:left;">${resultlist.JOB} </td>
					
					<td style="text-align:left;">${resultlist.SELF_ISO_AREA_DONG} </td>
					<td style="text-align:left;">${resultlist.SELF_ISO_AREA_ADDRESS} </td>
					<td style="text-align:left;">${resultlist.SELF_ISO_AREA_ADDRESS_DTL} </td>
					<td style="text-align:left;">${resultlist.PATIENT_NUM} </td>
					<td style="text-align:left;">${resultlist.PATIENT_NAME} </td>
					<td style="text-align:left;">${resultlist.CONTACT_NUM} </td>
					<td style="text-align:left;">${resultlist.CONTACT_NAME} </td>
					<td style="text-align:left;">${resultlist.FINAL_CONTACT} </td>
					<td style="text-align:left;">${resultlist.MONITOR_START} </td>
					<td style="text-align:left;">${resultlist.MONITOR_END} </td>
					<td style="text-align:left;">${resultlist.CONTACT_ADDRESS} </td>
					<td style="text-align:left;">${resultlist.CONTACT_TYPE} </td>
					<td style="text-align:left;">${resultlist.CONTACT_GUBUN} </td>
					<td style="text-align:left;">${resultlist.PATIENT_YN} </td>
					<td style="text-align:left;">${resultlist.BALGUB_YN} </td>
					<td style="text-align:left;">${resultlist.DAMDANG_DEPART} </td>
					<td style="text-align:left;">${resultlist.DAMDANG_CLASS} </td>
					<td style="text-align:left;">${resultlist.DAMDANG_POSITION} </td>
					<td style="text-align:left;">${resultlist.DAMDANG_NAME} </td>
					<td style="text-align:left;">${resultlist.DAMDANG_CALL} </td>
					
				</tr>
			</c:forEach>




</tbody>

</table>
</body>
</html>