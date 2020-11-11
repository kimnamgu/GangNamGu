<%@ page contentType="text/html;charset=euc-kr" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
	String ejmsurl = nexti.ejms.common.appInfo.getWeb_address();
	//String sso_lnk_url = ejmsurl + "sso/Login/App/SSOAppConnect.jsp?SG=LNK";
	String sso_cnt_url = ejmsurl + "sso/Login/App/SSOAppConnect.jsp?SG=CNT";
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
	popupWindow = window.open(url, "ejms", attrib);
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
		location.href="<%=sso_cnt_url%>";
	} finally {}
}
</script>
</head>

<body>
<!-- start 자료취합 -->
<table width="180" border="0" cellspacing="0" cellpadding="0" class="left">
	<tr>
		<td height="28" style="padding:0 7px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/client/jsp/images/title_data.gif" onclick="controlPopup('/main.do','',5000)" style="cursor:hand"></td>
				<td align="right"><img src="/client/jsp/images/ref2.gif" onclick="popupCallback()" style="cursor:hand"></a></td>
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
						<td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="on">
							<tr>
								<td width="50%">&nbsp;&nbsp;<a onclick="controlPopup('/inputingList.do','',5000)" style="cursor:hand">입력<span>(<b><%=request.getAttribute("inputCount").toString()%></b>)</span></a></td>
								<td width="50%">&nbsp;&nbsp;<a onclick="controlPopup('/deliveryList.do','',5000)" style="cursor:hand">배부<span>(<b><%=request.getAttribute("deliCount").toString()%></b>)</span></a></td>
							</tr>
							<tr>
								<td>&nbsp;&nbsp;<a <% if ( "005".equals(session.getAttribute("user_gbn"))) out.print("style='visibility: hidden'"); %> onclick="controlPopup('/doList.do','',5000)" style="cursor:hand">신청<span>(<b><%=request.getAttribute("reqCount").toString()%></b>)</span></a></td>
								<td>&nbsp;&nbsp;<a style="visibility: hidden" onclick="controlPopup('/researchParticiList.do','',5000)" style="cursor:hand">설문<span>(<b><%=request.getAttribute("rchCount").toString()%></b>)</span></a></td>
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