<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� �����ڷ����
 * ����:
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
<html>
<head>
<title>�����ڷ����</title>
<script src="/script/base64.js"></script>
<script src="/script/common.js"></script>
<script type="text/javascript">

	var pHwpCtrl;
	
	function window::onload(){
		onStart();
	}
	
	function onStart(){
		pHwpCtrl = HwpControl.HwpCtrl;
		var BasePath  = _GetBasePath();
		var filenmlist = "<%=new String(request.getParameter("filenmlist"))%>";
		var contents = "<%=new String(request.getParameter("contents"))%>";
		var splitFilenm = filenmlist.split(",");
		var Filenm = "";
		
		contents = contents.replace(/\\r/g,"\r").replace(/\\n/g,"\r");

		//ù��° ���� ���� �� ���� ����
		Filenm = BasePath + splitFilenm[0];
		if(!pHwpCtrl.Open(Filenm)) {
			alert("����");
			return;
		}
			
		pHwpCtrl.MovePos(2);
		pHwpCtrl.Run("BreakPage");
		<%-- Action ���� �ٲ�
		var act = pHwpCtrl.CreateAction("BreakPage");
		var set = act.CreateSet();
		if(!act.Execute(set)) {
			alert("����");
			return;
		}
		--%>
		pHwpCtrl.MovePos(2);
		
		
		var act = pHwpCtrl.CreateAction("ParagraphShape");
		var set =  act.CreateSet();
		act.GetDefault(set);
		set.SetItem("AlignType", 1);
		if(!act.Execute(set)) {
			alert("����");
			return;
		}
		var act = pHwpCtrl.CreateAction("CharShape");
		var set =  act.CreateSet();
		act.GetDefault(set);
		set.SetItem("Height", 1500);
		set.SetItem("TextColor", 0x000000);
		if(!act.Execute(set)) {
			alert("����");
			return;
		}
		
		var act = pHwpCtrl.CreateAction("InsertText");
        var set = act.CreateSet();
        set.SetItem("Text", contents);
		if(!act.Execute(set)) {
			alert("����");
			return;
		}		
		
		//�ι�° ���Ϻ��� �Ǹ������忡 ����		
		for ( var i = 1; i < splitFilenm.length; i++ ) {
			pHwpCtrl.MovePos(3);
			pHwpCtrl.Run("BreakPage");
			<%-- Action ���� �ٲ�
			var act = pHwpCtrl.CreateAction("BreakPage");
			var set = act.CreateSet();
			if(!act.Execute(set)) {
				alert("����");
				return;
			}
			--%>
			
			Filenm = BasePath + splitFilenm[i];
			if(!pHwpCtrl.Insert(Filenm, "", "code:ks"))
			{
				alert("����");
				return;
			}
	  }
	  		
		pHwpCtrl.MovePos(2);	
	}
	
	function _GetBasePath(){
		//BasePath�� ���Ѵ�.
		var loc = unescape(document.location.href);
		var lowercase = loc.toLowerCase(loc);

		if (lowercase.indexOf("http://") == 0) // Internet
		{
			//return loc.substr(0,loc.indexOf("/",8) + 1);//BasePath ����
			return "<%=nexti.ejms.common.appInfo.getAp_address()%>";
		}
		else // local
		{
			var path;
			path = loc.replace(/.{2,}:\/{2,}/, ""); // file:/// �� ����������.
			return path.substr(0,path.lastIndexOf("/") + 1);//BasePath ����
		}
	}
	
	function savecom() {
		var pHwpCtrl = HwpControl.HwpCtrl;
		pHwpCtrl.Run("HwpCtrlFileSaveAs");
		<%--
		var act = pHwpCtrl.CreateAction("HwpCtrlFileSaveAs");
		var set = act.CreateSet();
		if(!act.Execute(set)) {
			alert("����");
			return;
		}
		--%>
	}

	function cancel(){
		self.close();
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<form name = "HwpControl">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
    		<OBJECT id="HwpCtrl" width="100%" height="700" classid="CLSID:BD9C32DE-3155-4691-8972-097D53B10052" title="�ѱ�ActiveX">
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="21167">
				<PARAM NAME="_ExtentY" VALUE="15875">
				<PARAM NAME="_StockProps" VALUE="0">
				<PARAM NAME="FILENAME" VALUE="">
			</OBJECT>
		</td>
  	</tr>
	<tr>
  		<td height="5"></td>
  	</tr>
	<tr>
		<td align="right">
			<a href="javascript:savecom()"><img src="/images/common/btn_down.gif" border="0" alt=""></a>
			<a href="javascript:cancel()"><img src="/images/common/btn_close.gif" border="0" alt=""></a>&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>