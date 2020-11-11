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
	String fileName="minwon_" + day+ "_" +hour;
	
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
		<tbody>
				<tr>
					<td colspan="3" style="text-align:center"><font size="8" color="green">발급통계</font></td>
				</tr>
		</tbody>
	</table>
	<br>

	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">년도</th>
				<th scope="col" bgcolor="CDCDCD">완료건수</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${issueStatisticsListYear}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_cnt} </td>
							<td style="text-align:left;">${resultlist.baa_cnt} </td>
							<td style="text-align:left;">${resultlist.bab_cnt} </td>
							<td style="text-align:left;">${resultlist.bba_cnt} </td>
							<td style="text-align:left;">${resultlist.bbb_cnt} </td>
							<td style="text-align:left;">${resultlist.bbc_cnt} </td>
							<td style="text-align:left;">${resultlist.bbd_cnt} </td>
							<td style="text-align:left;">${resultlist.bca_cnt} </td>
							<td style="text-align:left;">${resultlist.bcb_cnt} </td>
						</tr>
					</c:forEach>
		</tbody>
	</table>

	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">월</th>
				<th scope="col" bgcolor="CDCDCD">완료건수</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${issueStatisticsListMonth}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_cnt} </td>
							<td style="text-align:left;">${resultlist.baa_cnt} </td>
							<td style="text-align:left;">${resultlist.bab_cnt} </td>
							<td style="text-align:left;">${resultlist.bba_cnt} </td>
							<td style="text-align:left;">${resultlist.bbb_cnt} </td>
							<td style="text-align:left;">${resultlist.bbc_cnt} </td>
							<td style="text-align:left;">${resultlist.bbd_cnt} </td>
							<td style="text-align:left;">${resultlist.bca_cnt} </td>
							<td style="text-align:left;">${resultlist.bcb_cnt} </td>
						</tr>
					</c:forEach>
		</tbody>
	</table>
	
	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">일</th>
				<th scope="col" bgcolor="CDCDCD">완료건수</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${issueStatisticsListDay}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_cnt} </td>
							<td style="text-align:left;">${resultlist.baa_cnt} </td>
							<td style="text-align:left;">${resultlist.bab_cnt} </td>
							<td style="text-align:left;">${resultlist.bba_cnt} </td>
							<td style="text-align:left;">${resultlist.bbb_cnt} </td>
							<td style="text-align:left;">${resultlist.bbc_cnt} </td>
							<td style="text-align:left;">${resultlist.bbd_cnt} </td>
							<td style="text-align:left;">${resultlist.bca_cnt} </td>
							<td style="text-align:left;">${resultlist.bcb_cnt} </td>
							
						</tr>
					</c:forEach>
		</tbody>
	</table>
	
	
	<br>
	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td colspan="2" style="text-align:center"><font size="8" color="blue">수수료통계</font></td>
			</tr>
		</tbody>
	</table>
	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">년도</th>
				<th scope="col" bgcolor="CDCDCD">완료수수료합계</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${peeStatisticsListYear}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_price} </td>
							<td style="text-align:left;">${resultlist.baa_price} </td>
							<td style="text-align:left;">${resultlist.bab_price} </td>
							<td style="text-align:left;">${resultlist.bba_price} </td>
							<td style="text-align:left;">${resultlist.bbb_price} </td>
							<td style="text-align:left;">${resultlist.bbc_price} </td>
							<td style="text-align:left;">${resultlist.bbd_price} </td>
							<td style="text-align:left;">${resultlist.bca_price} </td>
							<td style="text-align:left;">${resultlist.bcb_price} </td>
							
						</tr>
					</c:forEach>
		</tbody>
	</table>

	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">월</th>
				<th scope="col" bgcolor="CDCDCD">완료수수료합계</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${peeStatisticsListMonth}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_price} </td>
							<td style="text-align:left;">${resultlist.baa_price} </td>
							<td style="text-align:left;">${resultlist.bab_price} </td>
							<td style="text-align:left;">${resultlist.bba_price} </td>
							<td style="text-align:left;">${resultlist.bbb_price} </td>
							<td style="text-align:left;">${resultlist.bbc_price} </td>
							<td style="text-align:left;">${resultlist.bbd_price} </td>
							<td style="text-align:left;">${resultlist.bca_price} </td>
							<td style="text-align:left;">${resultlist.bcb_price} </td>
						</tr>
					</c:forEach>
		</tbody>
	</table>
	
	<br>
	
	<table width="784" border="1" bordercolor="#A2AFCC" bordercolorlight="#ffffff" bordercolordark="#6C717D" cellspacing="0" cellpadding="0">
		<thead>
			<tr align="center">
				<th scope="col" bgcolor="CDCDCD">일</th>
				<th scope="col" bgcolor="CDCDCD">완료수수료합계</th>
				<th scope="col" bgcolor="CDCDCD">주민등록등본</th>
				<th scope="col" bgcolor="CDCDCD">주민등록초본</th>
				<th scope="col" bgcolor="CDCDCD">가족관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">기본증명서</th>
				<th scope="col" bgcolor="CDCDCD">혼인관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">입양관계증명서</th>
				<th scope="col" bgcolor="CDCDCD">제적등본</th>
				<th scope="col" bgcolor="CDCDCD">제적초본</th>
			</tr>
		</thead>
		<tbody>
		
					<c:forEach var="resultlist" items="${peeStatisticsListDay}">
						<tr>
							<td style="text-align:left;">${resultlist.process_date} </td>
							<td style="text-align:left;">${resultlist.done_yn_price} </td>
							<td style="text-align:left;">${resultlist.baa_price} </td>
							<td style="text-align:left;">${resultlist.bab_price} </td>
							<td style="text-align:left;">${resultlist.bba_price} </td>
							<td style="text-align:left;">${resultlist.bbb_price} </td>
							<td style="text-align:left;">${resultlist.bbc_price} </td>
							<td style="text-align:left;">${resultlist.bbd_price} </td>
							<td style="text-align:left;">${resultlist.bca_price} </td>
							<td style="text-align:left;">${resultlist.bcb_price} </td>
						</tr>
					</c:forEach>
		</tbody>
	</table>
	
</body>
</html>