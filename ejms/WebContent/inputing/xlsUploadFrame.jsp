<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기/입력완료 엑셀업로드
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<html>
<head>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script src="/script/common.js"></script>
<script>
function xlsUpload() {
	var inputFile = document.forms[0].inputFile.value.trim();

	if ( inputFile == "" ) {
		alert("업로드할 엑셀파일을 선택하세요.");
		return;
	} else if ( inputFile.substring(inputFile.length - 4) != ".xls" ) {
		alert("[Excel 97 - 2003 통합 문서 (*.xls)]가 아닙니다. 다시 선택해주세요");
		return;
	}
	
	document.forms[0].submit();
}
function xlsDownload(action, formatId) {
	var formatData = document.forms[1].formatData;
	var format1 = parent.document.getElementById(formatId).outerHTML;
	format1 = document.createTextNode(format1);
	
	while ( true ) {
		var findIndex1 = -1;
		var findIndex2 = -1;
		var format2 = format1.data.toLowerCase();
		if ( (findIndex1 = format2.indexOf("value=\"autocal\"")) != -1 ||
					(findIndex1 = format2.indexOf("value='autocal'")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
			format1.replaceData(findIndex2, 0, "자동계산");
			format1.replaceData(findIndex1, "value=\"autocal\"".length, "");
			continue;
		} else if ( (findIndex1 = format2.indexOf("value=autocal")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
			format1.replaceData(findIndex2, 0, "자동계산");
			format1.replaceData(findIndex1, "value=autocal".length, "");
			continue;
		} else if ( (findIndex1 = format2.indexOf("<br")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("<input")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("<select")) != -1 ) {
			findIndex2 = format2.indexOf("/select>", findIndex1) + "/select>".length;
		} else if ( (findIndex1 = format2.indexOf("<textarea")) != -1 ) {
			findIndex2 = format2.indexOf("/textarea>", findIndex1) + "/textarea>".length;
		}
		if ( findIndex1 != -1 && findIndex2 != -1 ) {
			format1.replaceData(findIndex1, findIndex2 - findIndex1, "");
		} else {
			break;
		}
	}
	
	var prtLocation = String(parent.location).toLowerCase();
	var queryString = "formkind=";
	var formkind = "02";
	var i, j;
	if ( (i = prtLocation.indexOf(queryString)) != -1 ) {
		formkind = prtLocation.substr(i + queryString.length, 2);
		if ( formkind == "01" ) {
			var format2 = format1.data.toLowerCase();
			i = format2.indexOf("<td", format2.indexOf("<tr"));
			j = format2.indexOf("/td>", i) + "/tr>".length;
			format1.replaceData(i, j - i , "");
			
			format2 = format1.data.toLowerCase();
			i = format2.indexOf("<td", format2.lastIndexOf("<tr"));
			j = format2.indexOf("/td>", i) + "/tr>".length;
			format1.replaceData(i, j - i , "");
			
			format2 = format1.data.toLowerCase();
			i = format2.lastIndexOf("<td", format2.lastIndexOf("<img"));
			j = format2.indexOf("/td>", i) + "/tr>".length;
			format1.replaceData(i, j - i , "");
		}
	}
	
	document.forms[1].action = action;
	formatData.value = format1.data;
	document.forms[1].submit();
}
function commentView(obj1, obj2, frm) {
	obj1 = parent.document.getElementById(obj1);
	obj2 = parent.document.getElementById(obj2);
	frm = parent.document.getElementById(frm);

	obj2.innerHTML  =          "<b>※ 엑셀업로드 방법</b>";
	obj2.innerHTML += "<br>" + "1. 양식다운로드 후 다운로드한 엑셀파일 열기를 합니다.";
	obj2.innerHTML += "<br>" + "2. 다운로드한 양식에 맞게 자료를 입력합니다.";
	obj2.innerHTML += "<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;<b style='color:red'>☞ 입력란 속성과 일치하지 않는 입력자료는 엑셀업로드시 자동으로 무시됩니다.</b>";
	obj2.innerHTML += "<br>" + "3. 입력완료 후 <b style='color:red'>[F12]</b>키를 누르면 <b style='color:red'>[다른 이름으로 저장]</b>창이 나타납니다.";
	obj2.innerHTML += "<br>" + "4. <b style='color:red'>[파일형식(T)]</b>에 <b style='color:red'>[Excel 97 - 2003 통합 문서 (*.xls)]</b>를 선택 후 저장합니다.";
	obj2.innerHTML += "<br>" + "5. 엑셀파일로 저장되면 입력화면에서 엑셀업로드를 하시면됩니다.";
	
	if ( obj1.style.display != "block" ) {
		obj1.style.display = "block";
	} else {
		obj1.style.display = "none";
	}
	var btnLeft = getAbsoluteLeft(event.srcElement);
	var btnTop = getAbsoluteTop(event.srcElement);
	var btnWidth = event.srcElement.clientWidth;
	var frmLeft = getAbsoluteLeft(frm);
	var frmTop = getAbsoluteTop(frm);
	var objWidth = obj1.clientWidth;
	var objHeight = obj1.clientHeight;
	obj1.style.posLeft = frmLeft + btnLeft - objWidth + btnWidth;
	obj1.style.posTop = frmTop + btnTop - objHeight;
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table border="0" cellspacing="0" cellpadding="0">
  <tr align="center">
		<td>
			<html:form action="/xlsUpload" method="post" enctype="multipart/form-data">
				<html:hidden property="sysdocno"/><html:hidden property="formseq"/>
				<input type="file" name="inputFile" style="width:440" contentEditable="false">
			</html:form>
		</td>
		<td>&nbsp;<a href="javascript:xlsUpload()"><img src="/images/common/btn_excelup.gif" align="absmiddle" alt="엑셀업로드"/></a>&nbsp;</td>
		<td>
			<form name="formatDataForm" method="post" target="downFrame">
				<input type="hidden" name="sysdocno" value="<bean:write name="dataBookForm" property="sysdocno"/>">
				<input type="hidden" name="formseq" value="<bean:write name="dataBookForm" property="formseq"/>">
				<input type="hidden" name="formatData">
			</form>
			<a href="javascript:xlsDownload('/xlsDownload.do','tbl')" onmouseover="commentView('comment','msg','xlsUpload')" onmouseout="commentView('comment','msg','xlsUpload')"><img src="/images/common/btn_exceldown.gif" align="absmiddle" alt="엑셀다운로드"></a>
		</td>
  </tr>
</table>
<iframe name="downFrame" width="0" height="0" title=""></iframe>
</body>
</html>