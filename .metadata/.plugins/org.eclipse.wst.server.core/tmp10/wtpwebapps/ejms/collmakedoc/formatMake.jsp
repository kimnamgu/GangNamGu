<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성
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
<title>작성형태</title>
<base target="_self">
<link rel="stylesheet" href="/css/style.css" type="text/css">
<jsp:include page="/include/processing.jsp" flush="true"/>
<script src="/script/common.js"></script>
<script language="javascript">
	function click_next(actionpath1, actionpath2, actionpath3) {
		var type = document.forms[0].type;
		var actionpath;
	
		if(type[0].checked == true)
			actionpath = actionpath1;
		else if(type[1].checked == true)
			actionpath = actionpath2;
		else if(type[2].checked == true)
			actionpath = actionpath3;

		document.forms[0].action = actionpath;
		processingShow();
		document.forms[0].submit();
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatMake" method="post">
	<html:hidden property="sysdocno"/><html:hidden property="formseq"/>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5" rowspan="2"></td>
    <td width="13" rowspan="2"></td>
    <td width="744" valign="top" height="469">
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr>
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_23.gif" alt="작성형태"></td>
        </tr>
        <tr>
          <td height="11" width="644"></td>
        </tr>
        <tr>
          <td height="400">
            <table width="595" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td width="168"><a href="javascript:document.forms[0].type[0].checked=true;click_next('/formatLineDef.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/collect/form_01.gif" width="168" height="242" alt="새로만들기" border="0"></a></td>
                <td align="center"><a href="javascript:document.forms[0].type[1].checked=true;click_next('/formatLineDef.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/collect/form_02.gif" width="168" height="242" alt="공통양식가져오기" border="0"></a></td>
                <td width="168"><a href="javascript:document.forms[0].type[2].checked=true;click_next('/formatLineDef.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/collect/form_03.gif" width="168" height="242" alt="사용했던 양식 가져오기" border="0"></a></td>
              </tr>
              <tr>
                <td width="168" height="35" align="center"><html:radio property="type" value="1" style="border:0;background-color:transparent;display:none;"/></td>
                <td align="center" height="35"><html:radio property="type" value="2" style="border:0;background-color:transparent;display:none;"/></td>
                <td width="168" height="35" align="center"><html:radio property="type" value="3" style="border:0;background-color:transparent;display:none;"/></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="13" rowspan="2"></td>
    <td bgcolor="#0098E6" width="5" rowspan="2"></td>
  </tr>
  <tr>
    <td width="744" valign="top" height="45">
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
          <td bgcolor="#F6F6F6" height="5" width="734"></td>
          <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
        </tr>
        <tr bgcolor="#F6F6F6">
          <td bgcolor="#F6F6F6" width="5"></td>
          <td bgcolor="#F6F6F6" align="right" height="35">
          	<a href="javascript:click_next('/formatLineDef.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/common/btn_next.gif" alt="다음" align="absmiddle"></a>
          	<a href="javascript:window.close()"><img src="/images/common/btn_cancel.gif" alt="취소" align="absmiddle"></a>
          </td>
          <td width="5"></td>
        </tr>
        <tr>
          <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
          <td bgcolor="#F6F6F6" height="5" width="734"></td>
          <td><img src="/images/common/btn_r_04.gif" width="5" height="5" alt=""></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_03.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
</body>
</html>