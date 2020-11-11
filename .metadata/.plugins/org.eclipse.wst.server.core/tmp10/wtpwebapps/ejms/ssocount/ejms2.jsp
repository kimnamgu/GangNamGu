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

<table width="195" height="111" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="/ssocount/images/info_left.gif" width="5" height="111"></td>
		<td>
			<table width="185" height="111" border="0" cellspacing="0" cellpadding="0">
			  <tr> 
			    <td colspan="3" height="25" align="center" background="/ssocount/images/info_top_bg.gif"><a href="javascript:goEJMS('')"><img src="/ssocount/images/info_title_04.gif" width="61" height="13" alt="" border="0"></a></td>
			  </tr>
			  <tr> 
			    <td><img src="/ssocount/images/info_r_01.gif" width="2" height="2"></td>
			    <td height="2" width="181"></td>
			    <td><img src="/ssocount/images/info_r_02.gif" width="2" height="2"></td>
			  </tr>
			  <tr> 
			    <td width="2"></td>
			    <td width="181" height="76"> 
			      <table width="180" height="76" border="0" cellspacing="0" cellpadding="0" align="center">
			        <tr> 
			          <td width="45"> 
			            <table border="0" cellspacing="0" cellpadding="0" align="center">
			              <tr> 
			                <td height="30" align="center"><a href="javascript:goEJMS('deliv')"><img src="/ssocount/images/info_10.gif" width="31" height="24" alt="���" border="0"></a></td>
			              </tr>
			              <tr> 
			                <td height="20" align="center" class="style2"><a href="javascript:goEJMS('deliv')">����ϱ�</a></td>
			              </tr>
			              <tr>
			                <td height="20" align="center"><a href="javascript:goEJMS('deliv')"><b><font color="#FF0024" id="deliCount">0</font></b></a></td>
			              </tr>
			            </table>
			          </td>
			          <td width="45"> 
			            <table border="0" cellspacing="0" cellpadding="0" align="center">
			              <tr> 
			                <td height="30" align="center"><a href="javascript:goEJMS('input')"><img src="/ssocount/images/info_11.gif" width="31" height="24" alt="�Է�" border="0"></a></td>
			              </tr>
			              <tr> 
			                <td height="20" align="center" class="style2"><a href="javascript:goEJMS('input')">�Է��ϱ�</a></td>
			              </tr>
			              <tr>
			                <td height="20" align="center"><a href="javascript:goEJMS('input')"><b><font color="#FF0024" id="inputCount">0</font></b></a></td>
			              </tr>
			            </table>
			          </td>
			          <td width="45"> 
			            <table border="0" cellspacing="0" cellpadding="0" align="center">
			              <tr> 
			                <td height="30" align="center"><a href="javascript:goEJMS('rchlist')"><img src="/ssocount/images/info_12.gif" width="31" height="24" alt="����" border="0"></a></td>
			              </tr>
			              <tr> 
			                <td height="20" align="center" class="style2"><a href="javascript:goEJMS('rchlist')">��������</a></td>
			              </tr>
			              <tr>
			                <td height="20" align="center"><a href="javascript:goEJMS('rchlist')"><b><font color="#FF0024" id="rchCount">0</font></b></a></td>
			              </tr>
			            </table>
			          </td>
			          <td width="45"> 
			            <table border="0" cellspacing="0" cellpadding="0" align="center">
			              <tr> 
			                <td height="30" align="center"><a href="javascript:goEJMS('reqlist')"><img src="/ssocount/images/info_12.gif" width="31" height="24" alt="��û" border="0"></a></td>
			              </tr>
			              <tr> 
			                <td height="20" align="center" class="style2"><a href="javascript:goEJMS('reqlist')">��û��</a></td>
			              </tr>
			              <tr>
			                <td height="20" align="center"><a href="javascript:goEJMS('reqlist')"><b><font color="#FF0024" id="reqCount">0</font></b></a></td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			      </table>
			    </td>
			    <td width="2"></td>
			  </tr>
			  <tr> 
			    <td><img src="/ssocount/images/info_r_03.gif" width="2" height="2"></td>
			    <td height="2" width="181"></td>
			    <td><img src="/ssocount/images/info_r_04.gif" width="2" height="2"></td>
			  </tr>
			  <tr> 
			    <td colspan="3" height="6" align="center" background="/ssocount/images/info_bottom_bg.gif"></td>
			  </tr>
			</table>
		</td>
		<td><img src="/ssocount/images/info_right.gif" width="5" height="111"></td>
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