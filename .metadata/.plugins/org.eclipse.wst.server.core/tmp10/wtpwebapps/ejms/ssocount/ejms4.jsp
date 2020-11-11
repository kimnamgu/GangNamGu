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

<table width="230" height="11" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11"><img src="/ssocount/images/info_left_3.gif"></td>
		<td width="220" background="/ssocount/images/info_bg_3.gif" valign="middle">
			<table border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td height="20" valign="top" align="left" style="padding-left:5"><a href="javascript:goEJMS('')"><img src="/ssocount/images/info_title_04.gif" alt="" border="0"></a></td>
				</tr>
				<tr>
					<td height="8" style="padding-left:5">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr valign="middle">
								<td width="55">
									<table border="0" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td height="3" align="center"><a href="javascript:goEJMS('deliv')"><img src="/ssocount/images/info_10.gif" width="31" height="24" alt="배부" border="0"></a></td>
										</tr>
										<tr>
											<td height="2" align="center" class="style2"><a href="javascript:goEJMS('deliv')">배부하기</a></td>
										</tr>
										<tr>
											<td height="2" align="center"><a href="javascript:goEJMS('deliv')"><b id="deliCount">0</b></a></td>
										</tr>
									</table>
								</td>
								<td width="55">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td height="3" align="center"><a href="javascript:goEJMS('input')"><img src="/ssocount/images/info_11.gif" width="31" height="24" alt="입력" border="0"></a></td>
										</tr>
										<tr>
											<td height="2" align="center" class="style2"><a href="javascript:goEJMS('input')">입력하기</a></td>
										</tr>
										<tr>
											<td height="2" align="center"><a href="javascript:goEJMS('input')"><b id="inputCount">0</b></a></td>
										</tr>
									</table>
								</td>
								<td width="55">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td height="3" align="center"><a href="javascript:goEJMS('rchlist')"><img src="/ssocount/images/info_12.gif" width="31" height="24" alt="설문" border="0"></a></td>
										</tr>
										<tr>
											<td height="2" align="center" class="style2"><a href="javascript:goEJMS('rchlist')">설문조사</a></td>
										</tr>
										<tr>
											<td height="2" align="center"><a href="javascript:goEJMS('rchlist')"><b id="rchCount">0</b></a></td>
										</tr>
									</table>
								</td>
								<td width="55">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
										<tr>
											<td height="3" align="center"><a href="javascript:goEJMS('reqlist')"><img src="/ssocount/images/info_12.gif" width="31" height="24" alt="신청" border="0"></a></td>
										</tr>
										<tr>
											<td height="2" align="center" class="style2"><a href="javascript:goEJMS('reqlist')">신청서</a></td>
										</tr>
										<tr>
											<td height="2" align="center"><a href="javascript:goEJMS('reqlist')"><b id="reqCount">0</b></a></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td width="11"><img src="/ssocount/images/info_right_3.gif"></td>
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