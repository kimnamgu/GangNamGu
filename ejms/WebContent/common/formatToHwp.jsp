<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 한글다운로드
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
<%
	String filename = "";

	if(request.getAttribute("filename") != null) {
		filename = (String)request.getAttribute("filename");
	}
%>
<html>
<head>
<script language="javascript">

function window::onload(){
	onStart();
}
function _GetBasePath(){
	//BasePath를 구한다.
	var loc = unescape(document.location.href);
	var lowercase = loc.toLowerCase(loc);
	if (lowercase.indexOf("http://") == 0) // Internet
	{
		//return loc.substr(0,loc.indexOf("/",8) + 1); //BasePath 생성
		return "<%=nexti.ejms.common.appInfo.getAp_address()%>";
	}
	else // local
	{
		var path;
		path = loc.replace(/.{2,}:\/{2,}/, ""); // file:/// 를 지워버린다.
		return path.substr(0,path.lastIndexOf("/") + 1);//BasePath 생성
	}
}
function onStart(){
	var act = null;
	var set = null;
	
	var filenm = _GetBasePath() + "<%=filename%>";
	if( !HwpControl.HwpCtrl.Open(filenm, "HTML") ) {
		alert("실패");
		return;
	}
	
	var act = HwpControl.HwpCtrl.CreateAction('PageSetup');
	var set = act.CreateSet();
	act.GetDefault(set);
	set.SetItem("ApplyTo", 3);
	var _set = set.CreateItemSet ("PageDef", "PageDef");
	// 1mm = 283.465
	_set.SetItem("PaperWidth", 84189);
	_set.SetItem("PaperHeight", 119055);
	_set.SetItem("TopMargin", 1417);
	_set.SetItem("BottomMargin", 1417);
	_set.SetItem("LeftMargin", 1417);
	_set.SetItem("RightMargin", 1417);
	_set.SetItem("HeaderLen", 0);
	_set.SetItem("FooterLen", 0);
	_set.SetItem("GutterLen", 0);
	if(!act.Execute(set)) {
		alert("실패");
		return;
	}

	//한글다운방식 수정
	HwpControl.HwpCtrl.Run("HwpCtrlFileSaveAs");	
	<%--
	act = HwpControl.HwpCtrl.CreateAction("HwpCtrlFileSaveAs");
	set = act.CreateSet();
	if( !act.Execute(set) ) {
		alert("실패");
		return;
	}
	--%>
	location.href = "/hwpDownload.do?deletefile=<%=filename%>";
	top.document.location.reload();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<form name="HwpControl">
<OBJECT id=HwpCtrl style="LEFT: 0px; TOP: 0px" height=450 width=650 align=middle classid=CLSID:BD9C32DE-3155-4691-8972-097D53B10052 title="한글ActiveX">
	<PARAM NAME="_Version" VALUE="65536">
	<PARAM NAME="_ExtentX" VALUE="21167">
	<PARAM NAME="_ExtentY" VALUE="15875">
	<PARAM NAME="_StockProps" VALUE="0">
	<PARAM NAME="FILENAME" VALUE="">
</OBJECT>
</form>
</body>
</html>