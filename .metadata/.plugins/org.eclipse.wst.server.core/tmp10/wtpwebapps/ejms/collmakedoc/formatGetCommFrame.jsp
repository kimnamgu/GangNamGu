<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통양식가져오기 목록
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
<html>
<head>

<jsp:include page="/include/processing.jsp" flush="true"/>

<link href="/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function click_submit(actionpath) {
		document.forms[0].action = actionpath;
		parent.processingShow();
		document.forms[0].submit();
	}
	
	function click_preview(actionpath) {
		var oldTarget = document.forms[0].target;
		document.forms[0].target = "bgProcFrame";
		document.forms[0].action = actionpath;
		parent.processingShow();
		document.forms[0].submit();
		document.forms[0].target = oldTarget;
	}
	
	function click_popup(actionpath, width, height) {
		var left = (screen.width - width) / 2 + 30;
		var top = (screen.height - height) / 2 + 30;
		var option = 
			"dialogWidth:" + width + "px; " +
			"dialogHeight:" + height + "px; " +
			"dialogLeft:" + left + "px; " +
			"dialogTop:" + top + "px; " +
			"resizable:yes; scroll:yes; status:yes; center:no; " +
			"dialogHide:no; edge:raised; help:no; unadorned:no";

		try {
			window.showModalDialog(actionpath , window, option);
		} catch(exception) {
			alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
						"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
						"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
						"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
			try {processingHide();} catch(ex) {}
			try {parent.processingHide();} catch(ex) {}
		}
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/formatGetCommFrame" method="post">
<html:hidden property="sysdocno"/><html:hidden property="type"/>
<html:hidden property="searchdept"/>
<table width="474" border="0" cellspacing="0" cellpadding="0">
	<tr> 
	  <td> 
	    <table width="474" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr> 
	        <td width="15" align="center"><img src="/images/common/dot.gif" alt=""></td>
	        <td width="35"><font color="#4F4F4F">제목</font></td>
	        <td width="215"><html:text property="searchtitle" style="width:200"/></td>
	        <td><a href="javascript:click_submit('/formatGetCommFrame.do')"><img src="/images/common/btn_search.gif" border="0" alt="검색"></a></td>
	        <td class="result"><font class="result_no">* 총 <%=request.getAttribute("totalcount")%>건</font></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td> 
      <table width="474" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="4" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="50">연번</td>
          <td class="list_t">양식제목</td>
          <td class="list_t" width="100">양식유형</td>
          <td class="list_t" width="50">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <logic:notEmpty name="formatForm" property="listform">
					<logic:iterate id="list" name="formatForm" property="listform">
		        <tr onMouseOver="this.style.background='#FFFAD1'" onMouseOut="this.style.background='#ffffff'"> 
		          <td width="40" class="list_no"><bean:write name="list" property="seqno"/></td>
		          <td class="list_s" height="27"><bean:write name="list" property="formtitle"/></td>
		          <td class="list_l" width="100"><bean:write name="list" property="formkindname"/></td>
		          <td class="list_l" width="50">
		          	<logic:equal name="list" property="formkind" value="01">
									<a href="javascript:click_preview('/formatLineProcPreview.do?type=<bean:write name="formatForm" property="type"/>&sysdocno=<bean:write name="formatForm" property="sysdocno"/>&commformseq=<bean:write name="list" property="commformseq"/>')"><img src="/images/common/btn_view4.gif" border="0" alt="미리보기"></a>
								</logic:equal>
								<logic:equal name="list" property="formkind" value="02">
									<a href="javascript:click_preview('/formatFixedProcPreview.do?type=<bean:write name="formatForm" property="type"/>&sysdocno=<bean:write name="formatForm" property="sysdocno"/>&commformseq=<bean:write name="list" property="commformseq"/>')"><img src="/images/common/btn_view4.gif" border="0" alt="미리보기"></a>
								</logic:equal>
								<logic:equal name="list" property="formkind" value="03">
									<a href="javascript:click_popup('/formatBookPreview.do?type=<bean:write name="formatForm" property="type"/>&sysdocno=<bean:write name="formatForm" property="sysdocno"/>&commformseq=<bean:write name="list" property="commformseq"/>', 780, 550)"><img src="/images/common/btn_view4.gif" border="0" alt="미리보기"></a>
								</logic:equal>
		          </td>
		        </tr>
		        <tr> 
		          <td colspan="4" class="list_bg2"></td>
		        </tr>
		      </logic:iterate>
		    </logic:notEmpty>
        <logic:empty name="formatForm" property="listform">
	        <tr onMouseOver="this.style.background='#FFFAD1'" onMouseOut="this.style.background='#ffffff'"> 
	          <td colspan="4" height="27" class="list_s2" align="center">검색된 양식이 없습니다.</td>
	        </tr>
	        <tr> 
	          <td colspan="4" class="list_bg2"></td>
	        </tr>
        </logic:empty>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="30" align="center">
    	<bean:define id="fform" name="formatForm"/>
			<jsp:directive.page import="nexti.ejms.format.form.FormatForm"/>
			<%
				String param = "sysdocno=" + ((FormatForm)fform).getSysdocno() +
											 "&type=" + ((FormatForm)fform).getType() +
											 "&searchdept=" + ((FormatForm)fform).getSearchdept() +
											 "&searchtitle=" + ((FormatForm)fform).getSearchtitle();
			%>
			<%= nexti.ejms.util.commfunction.procPage_AddParam("/formatGetCommFrame.do",param,null,request.getAttribute("totalpage").toString(),request.getAttribute("currpage").toString()) %>
    </td>
  </tr>
</table>
</html:form>
<iframe name="bgProcFrame" width="0" height="0" title=""></iframe>
<script>parent.processingHide();</script>
</body>
</html>