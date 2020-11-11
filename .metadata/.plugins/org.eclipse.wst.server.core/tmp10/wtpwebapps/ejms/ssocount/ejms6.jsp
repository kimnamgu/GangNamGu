<%@ page language="java" pageEncoding="EUC-KR"%>
<%
	String ejmsurl = nexti.ejms.common.appInfo.getWeb_address() + "ssocount.do";
	String usersn = (String)request.getParameter("usersn");
	String userid = (String)request.getParameter("userid");
%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/ssocount/css/main.css">
<script type="text/javascript" src="/script/prototype.js"></script>
</head>

<body>
<form name="ejmsForm" method="post" action="<%=ejmsurl%>" target="_blank">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="usersn" value="<%=usersn%>">
	<input type="hidden" name="userid" value="<%=userid%>">
</form>

<table cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="8px"><img src="/ssocount/images/left_6.gif" alt="" /></td>
		<td width="152px" background="/ssocount/images/bg_6.gif" align="center">
			<table cellpadding="0" cellspacing="0" border="0" style="border-collapse: collapse;">
				<tr>
					<td height="32px" align="left"><a href="javascript:goEJMS('')"><img src="/ssocount/images/e_title_6.gif" border="0" alt=""/></a></td>
				</tr>
				<tr>
					<td align="center" height="45px">
						<table cellpadding="0" cellspacing="0" border="0" style="border-collapse: collapse;">
							<tr height="18px">
								<td width="55px"><img src="/ssocount/images/icon_6.gif" style="vertical-align: middle;" alt=""/>&nbsp;<a href="javascript:goEJMS('deliv')"><font color="#434343">배부하기</font></a></td>
								<td width="15px" align="center"><a href="javascript:goEJMS('deliv')"><font color="#e30000" id="deliCount">0</font></a></td>
								<td width="5px"></td>
								<td width="55px"><img src="/ssocount/images/icon_6.gif" style="vertical-align: middle;" alt=""/>&nbsp;<a href="javascript:goEJMS('input')"><font color="#434343">입력하기</font></a></td>
								<td width="15px" align="center"><a href="javascript:goEJMS('input')"><font color="#e30000" id="inputCount">0</font></a></td>
							</tr>
							<tr height="18px">
								<td><img src="/ssocount/images/icon_6.gif" style="vertical-align: middle;" alt=""/>&nbsp;<a href="javascript:goEJMS('reqlist')"><font color="#434343" style="letter-spacing:6px">신청서</font></a></td>
								<td align="center"><a href="javascript:goEJMS('reqlist')"><font color="#e30000" id="reqCount">0</font></a></td>
								<td></td>
								<td><img src="/ssocount/images/icon_6.gif" style="vertical-align: middle;" alt=""/>&nbsp;<a href="javascript:goEJMS('rchlist')"><font color="#434343">설문조사</font></a></td>
								<td align="center"><a href="javascript:goEJMS('rchlist')"><font color="#e30000" id="rchCount">0</font></a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td width="8px"><img src="/ssocount/images/right_6.gif" alt=""/></td>
	</tr>
</table>

<script>
function goEJMS(act) {
	document.ejmsForm.act.value = act;
	document.ejmsForm.submit();
}

function ejmsCount() {
	try {
		var url = "<%=ejmsurl%>?type=COUNT&act=mycount&usersn=<%=usersn%>&userid=<%=userid%>";
		var callBack = function(resultXML) {
			var xmlDoc = resultXML.responseXML;
			var resultNode = xmlDoc.getElementsByTagName('result');

			try {
				var uid = document.getElementById("uid");
				uid.innerHTML = resultNode[0].childNodes[0].text;
			} catch(e) {}
			try {
				var collprocCount = document.getElementById("collprocCount");
				collprocCount.innerHTML = resultNode[0].childNodes[1].text;
			} catch(e) {}
			try {
				var deliCount = document.getElementById("deliCount");
				deliCount.innerHTML = resultNode[0].childNodes[2].text;
			} catch(e) {}
			try {
				var inputCount = document.getElementById("inputCount");
				inputCount.innerHTML = resultNode[0].childNodes[3].text;
			} catch(e) {}
			try {
				var rchCount = document.getElementById("rchCount");
				rchCount.innerHTML = resultNode[0].childNodes[4].text;
			} catch(e) {}
			try {
				var reqCount = document.getElementById("reqCount");
				reqCount.innerHTML = resultNode[0].childNodes[5].text;
			} catch(e) {}
			try {
				var reqProcWaitCount = document.getElementById("reqProcWaitCount");
				reqProcWaitCount.innerHTML = resultNode[0].childNodes[6].text;
			} catch(e) {}
		}
		new Ajax.Request(url, {onComplete:callBack});
	} catch (exception) {}
}

setInterval("ejmsCount()", 1000*60*1);
ejmsCount();
</script>

</body>
</html>