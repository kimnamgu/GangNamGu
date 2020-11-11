<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: "처리중입니다" 작업중표시
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
<script language="javascript">
function isProcessingShow() {
	return (processingLayer.style.display != "none") ? true : false;
}
function processingShow() {
	processingLayer.style.display = "block";
	
	var selectObj = document.getElementsByTagName("select");
	for(var i = 0; i < selectObj.length; i++) {
		selectObj[i].onresizestart = selectObj[i].onmousedown;
		selectObj[i].onresizeend = selectObj[i].ondblclick;
		selectObj[i].onmousedown = selectObj[i].ondblclick = function() {
			event.srcElement.disabled = true;
			setTimeout("var selectObj = document.getElementsByTagName(\"select\");" +
								 "for(var i = 0; i < selectObj.length; i++) {" +
								 "	selectObj[i].disabled = false;" +
								 "}", 0);
		}
		if(selectObj[i].onresizestart == null) {
			selectObj[i].onresizestart = "NO_EVENT";
		}
		if(selectObj[i].onresizeend == null) {
			selectObj[i].onresizeend = "NO_EVENT";
		}
	}
}
function processingHide() {
	processingLayer.style.display = "none";
	
	var selectObj = document.getElementsByTagName("select");
	for(var i = 0; i < selectObj.length; i++) {
		if(selectObj[i].onresizestart == "NO_EVENT") {
			selectObj[i].onmousedown = selectObj[i].onresizestart = null;
		} else if(selectObj[i].onresizestart != null) {
			selectObj[i].onmousedown = selectObj[i].onresizestart;
			selectObj[i].onresizestart = null;
		}
		if(selectObj[i].onresizeend == "NO_EVENT") {
			selectObj[i].ondblclick = selectObj[i].onresizeend = null;
		} else if(selectObj[i].onresizeend != null) {
			selectObj[i].ondblclick = selectObj[i].onresizeend;
			selectObj[i].onresizeend = null;
		}
	}
}
//flashShow(파일경로, 가로, 세로, 이름, 배경색, 윈도우모드)
function flashShow(url,w,h,name,bg,win){
	var flashStr=
		"<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='"+w+"' height='"+h+"' id='"+name+"' align='center' title='잠시만 기다리세요'>"+
		"<param name='movie' value='"+url+"' />"+
		"<param name='wmode' value='"+win+"' />"+
		"<param name='menu' value='false' />"+
		"<param name='quality' value='high' />"+
		"<param name='bgcolor' value='"+bg+"' />"+
		"</object>";
		//네스케이프
		//"<embed src='"+url+"' wmode='"+win+"' menu='false' quality='high' bgcolor='"+bg+"' width='"+w+"' height='"+h+"' name='"+name+"' align='center' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />";
	document.write(flashStr);
}
</script>

<div id="processingLayer" style="width:99%;height:99%;left:0;top:0;position:absolute;display:none;z-index:999">
<table width="100%" height="100%" border="0" style="left:0;top:0;position:absolute;">
	<tr align="center" valign="middle">
		<td><iframe name="processingFrame" width="150" height="150" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
	</tr>
</table>
<table width="100%" height="100%" border="0" style="left:0;top:0;position:absolute;">
	<tr align="center" valign="middle">
		<td><div id="content"><script language="javascript">flashShow("/images/loading.swf", 150, 150, "", "", "transparent")</script></div></td>
	</tr>
</table>
</div>