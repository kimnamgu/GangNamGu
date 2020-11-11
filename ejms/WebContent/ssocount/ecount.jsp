<%@page contentType="text/html; charset=EUC-KR" %>
<%
	String serverURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String user_id = (String)session.getAttribute("SSO_USER");
%>

<link href="http://100.6.65.25:3100/ntis/athena/pegasus/stylesheet/body_main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=serverURL%>/script/prototype.js"></script>
<script>
document.onreadystatechange = function() {
	if(document.readyState == "complete") {
		try {
			var uid = document.ejmsForm.userid.value;
			var url = "<%=serverURL%>/ssocount.do?type=COUNT&act=mycount&userid=" + uid;
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
}

function goEJMS(act) {
	document.ejmsForm.act.value = act;
	document.ejmsForm.submit();
}
</script>

<form name="ejmsForm" method="post" action="<%=serverURL%>/ssocount.do" target="_blank">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="userid" value="<%=user_id%>">
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dce5ef">
	<tr>
		<td width="65" height="18" align="left">&nbsp;<span style="font-size:11px; color:#565656 ;"><a href="javascript:goEJMS('deliv')">배부대기</a></span></td>
		<td width="25" height="18" align="left"><span style="font-size:11px; color:red;" id="deliCount">0</span></td>
		<td width="65" height="18" align="left">&nbsp;<span style="font-size:11px; "><a href="javascript:goEJMS('input')">입력대기</a></span></td>
		<td width="25" height="18" align="left"><span style="font-size:11px; color:red;" id=inputCount>0</span></td>
		<td width="65" height="18" align="left">&nbsp;<span style="font-size:11px; "><a href="javascript:goEJMS('reqlist')">신청대기</a></span></td>
		<td width="25" height="18" align="left"><span style="font-size:11px; color:red;" id="reqCount">0</span></td>
	</tr>
</table>