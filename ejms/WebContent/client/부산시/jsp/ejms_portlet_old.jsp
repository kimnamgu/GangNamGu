<%@ page contentType="text/html;charset=euc-kr" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
	String ejmsurl = nexti.ejms.common.appInfo.getWeb_address();
	String sso_lnk_url = ejmsurl + "sso/Login/App/SSOAppConnect.jsp?SG=LNK";
	String sso_cnt_url = ejmsurl + "sso/Login/App/SSOAppConnect.jsp?SG=CNT";
	//테스트
	//String id = "A0010180";
	//ejmsurl = "http://127.0.0.1:7001/";
	//sso_lnk_url = ejmsurl + "ssocount.do?type=COUNT&act=mycount&userid=" + id;
	//sso_cnt_url = ejmsurl + "ssocount.do?type=COUNT&act=mycount&userid=" + id;
%>

<html>
<head>
<title>자료취합</title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 10px;
	background-color: #ffffff;
	font-size:12px;
	font-family: "dotum", "tahoma"; 
	line-height: 14px;
	color: #555555;	
};

td{font-size:12px; color: #606060; text-decoration: none; }

a:visited { color:#555555; text-decoration: none; letter-spacing:-1px;}
a:link    { color:#555555; text-decoration: none; letter-spacing:-1px;}
a:active  { color:#555555; text-decoration: none; letter-spacing:-1px;}
a:hover   { color:#555555; text-decoration: none; letter-spacing:-1px;}

img { border:0;}

span { font-size:11px; letter-spacing:0;}

/* 위 간격 */
.left {margin-top:3px}
.middle {margin-top:6px}
.right {margin-top:6px}

/* 온나라 */
.on {border-collapse: collapse; border-bottom:1px solid #F3F3F3; }
.on td { border-bottom:1px solid #DDDDDD; padding:4px 0 3px 4px;}
.on td a:visited { text-decoration: none;}
.on td a:link    { text-decoration: none;}
.on td a:active  { text-decoration: none;}
.on td a:hover   { text-decoration: none;}
</style>
<script>
var popupWindow = null;
var popupWindowStatus = null;
function controlPopup(url, attrib, checkInterval) {
	popupWindow = document.connectFrame.window.open(url, "pop", attrib);
	popupWindowStatus = setInterval(function() {
		try {
			if (popupWindow.closed == true) {
				if(popupWindowStatus != null) {
					clearInterval(popupWindowStatus);
					popupWindowStatus = null;
				}
				popupCallback();
				popupWindow = null;
			}
		} catch (exception) {
			popupCallback();
			popupWindow = null;
			popupWindowStatus = null;
		}
	}, checkInterval);
}
function popupCallback() {
	try {
		location.reload();
	} finally {
	}
}

var isSSOLogin = false;
var SSOInfo = "";
var openLink = "";

function goEJMS(act) {
	if ( openLink != "" || !isSSOLogin || SSOInfo.replace(/(^\s*)|(\s*$)/gi, "") == "" ) return;
	document.connectFrame.window.location.href="about:blank";
	
	openLink = "<%=ejmsurl%>" + act;
	frameLoadComplete();
}

function goLink() {
	if ( openLink != "" ) {
		//controlPopup(openLink, '', 3000);
		document.connectFrame.window.open(openLink, "pop");
		openLink = "";
	}
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
			document.connectFrame.window.location.href = "<%=sso_lnk_url%>";
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
</head>

<body>
<iframe name="connectFrame" onload="goLink()" src="about:blank" width="0" height="0"></iframe>
<iframe name="countFrame" src="<%=sso_cnt_url%>" onload="frameLoadComplete()" width="0" height="0"></iframe><br>
<!-- start 자료취합 -->
<table width="180" border="0" cellspacing="0" cellpadding="0" class="left">
	<tr>
		<td height="28" style="padding:0 7px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/client/jsp/images/title_data.gif" onclick="javascript:goEJMS('main.do');" style="cursor:hand"></td>
				<td align="right"><img src="/client/jsp/images/ref2.gif" onclick="javascript:location.reload();" style="cursor:hand"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td><table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:url(/client/jsp/images/box_bg.gif) bottom left;">
			<tr>
				<td width="5" height="5"><img src="/client/jsp/images/box_t_l.gif"></td>
				<td style="background:url(/client/jsp/images/box_t.gif) repeat-x;"></td>
				<td width="5"><img src="/client/jsp/images/box_t_r.gif"></td>
			</tr>
			<tr>
				<td style="background:url(/client/jsp/images/box_l.gif) repeat-y;"></td>
				<td style="padding:0 3px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="35"><img src="/client/jsp/images/img_data.gif"></td>
						<td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="on">
							<tr>
								<td width="50%">&nbsp;&nbsp;<a href="javascript:goEJMS('inputingList.do')">입력<span>(<strong id="inputCount">0</strong>)</span></a></td>
								<td width="50%">&nbsp;&nbsp;<a href="javascript:goEJMS('deliveryList.do')">배부<span>(<strong id="deliCount">0</strong>)</span></a></td>
							</tr>
							<tr>
								<td>&nbsp;&nbsp;<a <% if ( "005".equals(session.getAttribute("user_gbn"))) out.print("style='visibility: hidden'"); %> href="javascript:goEJMS('doList.do')">신청<span>(<strong id="reqCount">0</strong>)</span></a></td>
								<td>&nbsp;&nbsp;<a style="visibility: hidden" href="javascript:goEJMS('researchParticiList.do')">설문<span>(<strong id="rchCount">0</strong>)</span></a></td>
							</tr>
						</table></td>
						</tr>
				</table></td>
				<td style="background:url(/client/jsp/images/box_r.gif) repeat-y;"></td>
			</tr>
			<tr>
				<td height="5"><img src="/client/jsp/images/box_b_l.gif"></td>
				<td style="background:url(/client/jsp/images/box_b.gif) repeat-x;"></td>
				<td><img src="/client/jsp/images/box_b_r.gif"></td>
			</tr>
		</table></td>
	</tr>
</table>
<!-- end 자료취합 -->
</body>
</html>