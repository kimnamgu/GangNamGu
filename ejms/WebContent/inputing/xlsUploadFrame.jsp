<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ�/�Է¿Ϸ� �������ε�
 * ����:
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
		alert("���ε��� ���������� �����ϼ���.");
		return;
	} else if ( inputFile.substring(inputFile.length - 4) != ".xls" ) {
		alert("[Excel 97 - 2003 ���� ���� (*.xls)]�� �ƴմϴ�. �ٽ� �������ּ���");
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
			format1.replaceData(findIndex2, 0, "�ڵ����");
			format1.replaceData(findIndex1, "value=\"autocal\"".length, "");
			continue;
		} else if ( (findIndex1 = format2.indexOf("value=autocal")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
			format1.replaceData(findIndex2, 0, "�ڵ����");
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

	obj2.innerHTML  =          "<b>�� �������ε� ���</b>";
	obj2.innerHTML += "<br>" + "1. ��Ĵٿ�ε� �� �ٿ�ε��� �������� ���⸦ �մϴ�.";
	obj2.innerHTML += "<br>" + "2. �ٿ�ε��� ��Ŀ� �°� �ڷḦ �Է��մϴ�.";
	obj2.innerHTML += "<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;<b style='color:red'>�� �Է¶� �Ӽ��� ��ġ���� �ʴ� �Է��ڷ�� �������ε�� �ڵ����� ���õ˴ϴ�.</b>";
	obj2.innerHTML += "<br>" + "3. �Է¿Ϸ� �� <b style='color:red'>[F12]</b>Ű�� ������ <b style='color:red'>[�ٸ� �̸����� ����]</b>â�� ��Ÿ���ϴ�.";
	obj2.innerHTML += "<br>" + "4. <b style='color:red'>[��������(T)]</b>�� <b style='color:red'>[Excel 97 - 2003 ���� ���� (*.xls)]</b>�� ���� �� �����մϴ�.";
	obj2.innerHTML += "<br>" + "5. �������Ϸ� ����Ǹ� �Է�ȭ�鿡�� �������ε带 �Ͻø�˴ϴ�.";
	
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
		<td>&nbsp;<a href="javascript:xlsUpload()"><img src="/images/common/btn_excelup.gif" align="absmiddle" alt="�������ε�"/></a>&nbsp;</td>
		<td>
			<form name="formatDataForm" method="post" target="downFrame">
				<input type="hidden" name="sysdocno" value="<bean:write name="dataBookForm" property="sysdocno"/>">
				<input type="hidden" name="formseq" value="<bean:write name="dataBookForm" property="formseq"/>">
				<input type="hidden" name="formatData">
			</form>
			<a href="javascript:xlsDownload('/xlsDownload.do','tbl')" onmouseover="commentView('comment','msg','xlsUpload')" onmouseout="commentView('comment','msg','xlsUpload')"><img src="/images/common/btn_exceldown.gif" align="absmiddle" alt="�����ٿ�ε�"></a>
		</td>
  </tr>
</table>
<iframe name="downFrame" width="0" height="0" title=""></iframe>
</body>
</html>