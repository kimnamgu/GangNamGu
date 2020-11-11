<%@ page language="java" pageEncoding="EUC-KR"%>
<%
	String ejmsurl = nexti.ejms.common.appInfo.getWeb_address() + "ssocount.do";
	String usersn = (String)request.getParameter("usersn");
	String userid = (String)request.getParameter("userid");
%>
<html>
 <head>
  <title> New Document </title>
  <meta name="Generator" content="EditPlus">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <style>
	  table,tr,td,input,textarea,select,form,option,body,th,p,div,span,a {
			font-family: "굴림", "돋움", "Arial";
			font-size: 9pt; 
			color:#606060;
			line-height:18px;
		}
		body {margin: 0px;}/* 마진 */
		img	{border: 0;}/* 이미지 */
		A:link { text-decoration:none; color:#606060}
		A:visited { text-decoration:none; color:#606060}
		A:active { text-decoration:none; color:#606060}
		A:hover {text-decoration:none; color:#00709A}
		.margin_top01 {margin-top:0px}
		.text11 {color: #565656; font-weight:bold;}
		.text12 {color: #565656;}
		.text16 {color: #64C8FF; font-family: "바탕"}
		.box12_top {background-image:url(../images/box12_topbg.gif); height: 5px;}
		.box12_bottom {background-image:url(../images/box12_bottombg.gif); height: 5px;}
		.box12_left {background-image:url(../images/box12_leftbg.gif); width: 5px;}
		.box12_right {background-image:url(../images/box12_rightbg.gif); width: 5px;}
		.box12_bg {background-image:url(../images/box12_bg.gif);}
	</style>
 </head>

 <body>
    <!-- e-자료모아 시작 -->
    <table width="168" border="0" cellpadding="0" cellspacing="0" class="margin_top01">
      <tr>
        <td><img src="../images/box12_lefttop.gif" /></td>
        <td class="box12_top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30" align="center"><img src="../images/ico_20.gif" width="15" height="14" align="absbottom"></td>
            <td class="text11" style="padding-top:3px;"><a href="javascript:goEJMS('')">e-자료모아</a></td>
          </tr>
        </table>
          </td>
        <td><img src="../images/box12_righttop.gif" /></td>
      </tr>
      <tr>
        <td class="box12_left"></td>
        <td class="box12_bg" style="padding-top:3px;"><table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top:2px;">
          <tr height="22" valign="middle">
            <td width="14"><img src="../images/ico_21.gif" width="14" height="3"></td>
            <td width="48" class="text12"><a href="javascript:goEJMS('input')">입력대기</a></td>
            <td width="12" align="center" class="text16" id="inputCount">-</td>
            <td width="4"></td>
            <td width="14"><img src="../images/ico_21.gif" width="14" height="3"></td>
            <td width="48" class="text12"><a href="javascript:goEJMS('deliv')">배부대기</a></td>
            <td width="12" align="center" class="text16" id="deliCount">-</td>
          </tr>

          <tr>
            <td colspan="7" height="1" background="../images/line_dot_01.gif"></td>
          </tr>
          <tr>
            <td colspan="7" height="3"></td>
          </tr>

          <tr height="19" valign="middle">
            <td><img src="../images/ico_21.gif" width="14" height="3"></td>
            <td class="text12"><a href="javascript:goEJMS('rchlist')">설문조사</a></td>
            <td align="center" class="text16" id="rchCount">-</td>
            <td width="4"></td>
            <td><img src="../images/ico_21.gif" width="14" height="3"></td>
            <td class="text12" style="letter-spacing:6px"><a href="javascript:goEJMS('reqlist')">신청서</a></td>
            <td align="center" class="text16" id="reqCount">-</td>
          </tr>

        </table></td>
        <td class="box12_right"></td>
      </tr>
      <tr>
        <td><img src="../images/box12_leftbottom.gif" /></td>
        <td class="box12_bottom"></td>
        <td><img src="../images/box12_rightbottom.gif" /></td>
      </tr>
    </table>
    <!-- e-자료모아 끝 -->
 </body>

<script type="text/javascript" src="/script/prototype.js"></script>

<form name="ejmsForm" method="post" action="<%=ejmsurl%>" target="_blank">
	<input type="hidden" name="act" value="">
	<input type="hidden" name="usersn" value="<%=usersn%>">
	<input type="hidden" name="userid" value="<%=userid%>">
</form>
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
</html>