<%@page contentType="text/html; charset=euc-kr" %>

<%
	String[] countNames = {"배부", "신청서", "입력", "설&nbsp;&nbsp;&nbsp;문"};
	String[] urls = {"/deliveryList.do", "/formList.do", "/inputingList.do", "/researchParticiList.do"};
	String[] counts = {"deliCount", "reqCount", "inputCount", "rchCount"};
%>

<%
	String ejmsurl = "http://101.0.1.160:7800";
	String sso_lnk_url = ejmsurl + "/sso/Login/App/SSOAppConnect.jsp?SG=LNK";
	String sso_cnt_url = ejmsurl + "/sso/Login/App/SSOAppConnect.jsp?SG=CNT";
	String EPIMAGE = "EP/image";
	String EPHTDOCS = "EP/htdocs";
%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<LINK rel="stylesheet" type="text/css" href="/<%=EPHTDOCS %>/main.css">
<style>
td     {font-size:9pt; font-family:돋움; color:#545454;}

A:link    {font-size:9pt; color:#545454; text-decoration:none}
A:visited {font-size:9pt; color:#545454; text-decoration:none}
A:active  {font-size:9pt; color:#545454; text-decoration:none}
A:hover   {font-size:9pt; color:#ef6622; text-decoration:none}
</style>

<script type="text/javascript" src="<%=ejmsurl%>/script/prototype.js"></script>
<script type="text/javascript" src="<%=ejmsurl%>/script/common.js"></script>
</HEAD>

<body style="margin: 0px 0px 0px 0px"  style="overflow:hidden;">

<table border="0" cellpadding="0" cellspacing="0" width="100%">  
    <tr>
		<td width="7"><img src="/<%=EPIMAGE%>/portletimg/r_01.gif" width="7" height="6"></td>
		<td background="/<%=EPIMAGE%>/portletimg/r_02.gif"></td>
		<td width="7"><img src="/<%=EPIMAGE%>/portletimg/r_03.gif" width="7" height="6"></td>
	</tr>
	<tr>
	    <td><img src="/<%=EPIMAGE%>/portletimg/r_09.gif" width="7" height="27"></td>
		<td background="/<%=EPIMAGE%>/portletimg/r_10.gif" valign="top" style="padding-top:5px">
			<!---- 타이틀 S ---->
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td valign="top" style="padding-left:2px"><font color="#660066"><b onclick="javascript:goEJMS('/main.do');" style="cursor:hand">e-자료모아</b></font></td>
					<td width="15" style="padding-top:2px"><a href="javascript:location.reload();" onfocus="this.blur();"><img src="/<%=EPIMAGE%>/portletimg/icon_01.gif "  alt="새로고침" width="13" height="13" border="0"></a></td>
				</tr>
			</table>
			<!---- 타이틀 E ---->
		</td>
		<td><img src="/<%=EPIMAGE%>/portletimg/r_11.gif" width="7" height="27"></td>
	 </tr>

	 <tr>
		<td valign="top"><img src="/<%=EPIMAGE%>/portletimg/r_12.gif" width="7" height="61"></td>
		<td bgcolor="#f6f5f5">
			<!---- 내용 S ---->
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
			    <td style="border:0px solid #d6d6d6; padding:8px" bgcolor="#ffffff" height="25">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-top:5px">
					<tr>

<%			
					for (int i=0; i < countNames.length ; i++) {
%>     
						<td width="6" height="14"></td>
						<td height="14"><%=countNames[i]%> : <span id="<%=counts[i]%>" onclick="javascript:goEJMS('<%=urls[i]%>')" style="cursor:hand">0</span>건</td>
						</td>
<%
						if (((i+1) % 2 == 0) && (i != countNames.length -1)) {
%>
					</tr>
					<!--tr><td height='1' colspan='6' background='/<%=EPIMAGE%>/dot_line.gif'></td></tr-->
					<tr>
<%
						}
					}
%>
					</tr>

				</table>
				</td>    
			</tr>
			</table>
			<!---- 내용 E ---->

		</td>
		<td valign="top"><img src="/<%=EPIMAGE%>/portletimg/r_13.gif" width="7" height="61"></td>
	</tr>
	<tr>
		<td><img src="/<%=EPIMAGE%>/portletimg/r_06.gif" width="7" height="6"></td>
		<td background="/<%=EPIMAGE%>/portletimg/r_07.gif"></td>
		<td><img src="/<%=EPIMAGE%>/portletimg/r_08.gif" width="7" height="6"></td>
	</tr> 
</table>

<iframe name="countFrame" src="<%=sso_cnt_url%>" onload="frameLoadComplete()" width="0" height="0"></iframe>
<iframe name="connectFrame" src="" width="0" height="0"></iframe>

<script>
var isSSOLogin = false;
var SSOInfo = "";

function goEJMS(act) {
	if ( !isSSOLogin || SSOInfo.trim() == "" ) return;
	document.connectFrame.window.location.href="about:blank";
	document.connectFrame.window.open("<%=ejmsurl%>" + act, "_blank");
}

function frameLoadComplete() {
	var uidSTag = "<uid>";
	var collprocCountSTag = "<collprocCount>";
	var deliCountSTag = "<deliCount>";
	var inputCountSTag = "<inputCount>";
	var rchCountSTag = "<rchCount>";
	var reqCountSTag = "<reqCount>";
	var reqProcWaitCountSTag = "<reqProcWaitCount>";
	var uidETag = "</uid>";
	var collprocCountETag = "</collprocCount>";
	var deliCountETag = "</deliCount>";
	var inputCountETag = "</inputCount>";
	var rchCountETag = "</rchCount>";
	var reqCountETag = "</reqCount>";
	var reqProcWaitCountETag = "</reqProcWaitCount>";
	var cntData = document.countFrame.document.body.innerText;
	var toNumber = function(num) {
		return ( isNaN(num) ) ? 0 : Number(num);
	}
	try {
		var uid = cntData.substring(cntData.indexOf(uidSTag) + uidSTag.length, cntData.indexOf(uidETag));
		if ( uid.replace(/(^\s*)|(\s*$)/gi, "") != "" ) {
			isSSOLogin = true;
			SSOInfo = uid;
			document.connectFrame.window.location.href = "<%=sso_cnt_url%>";
		}
	} catch(e) {}
	try {
		var collprocCount = document.getElementById("collprocCount");
		var collproc = cntData.substring(cntData.indexOf(collprocCountSTag) + collprocCountSTag.length, cntData.indexOf(collprocCountETag));
		collprocCount.innerHTML = toNumber(collproc);
	} catch(e) {}
	try {
		var deliCount = document.getElementById("deliCount");
		var deli = cntData.substring(cntData.indexOf(deliCountSTag) + deliCountSTag.length, cntData.indexOf(deliCountETag));
		deliCount.innerHTML = toNumber(deli);
	} catch(e) {}
	try {
		var inputCount = document.getElementById("inputCount");
		var input = cntData.substring(cntData.indexOf(inputCountSTag) + inputCountSTag.length, cntData.indexOf(inputCountETag));
		inputCount.innerHTML = toNumber(input);
	} catch(e) {}
	try {
		var rchCount = document.getElementById("rchCount");
		var rch = cntData.substring(cntData.indexOf(rchCountSTag) + rchCountSTag.length, cntData.indexOf(rchCountETag));
		rchCount.innerHTML = toNumber(rch);
	} catch(e) {}
	try {
		var reqCount = document.getElementById("reqCount");
		var req = cntData.substring(cntData.indexOf(reqCountSTag) + reqCountSTag.length, cntData.indexOf(reqCountETag));
		reqCount.innerHTML = toNumber(req);
	} catch(e) {}
	try {
		var reqProcWaitCount = document.getElementById("reqProcWaitCount");
		var reqProcWait = cntData.substring(cntData.indexOf(reqProcWaitCountSTag) + reqProcWaitCountSTag.length, cntData.indexOf(reqProcWaitCountETag));
		reqProcWaitCount.innerHTML = toNumber(reqProcWait);
	} catch(e) {}
}
</script>

</body>
</html>
