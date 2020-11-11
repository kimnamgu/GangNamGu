<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 미리보기
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
<title>미리보기</title>
<base target="_self">

<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/inputing.js"></script>
<script language="javascript">
function click_select(actionpath) {
	if(confirm("양식을 선택하시겠습니까?") == true) {
		if("<%=request.getAttribute("msg")%>" != "") {
			if(confirm("<%=request.getAttribute("msg")%>") == true) {
				document.forms[0].action = actionpath + "?force=1";
				processingShow();
				document.forms[0].submit();
			}
		} else {
			document.forms[0].action = actionpath;
			processingShow();
			document.forms[0].submit();
		}
	}
}

function click_close() {
	window.close();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatBookPreview" method="post">
<html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formkind"/>
<html:hidden property="usedsysdocno"/><html:hidden property="usedformseq"/><html:hidden property="commformseq"/>
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
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_37.gif" alt="미리보기"></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td valign="top"> 
                  <!--추가양식 테이블-->
                  <table width="744" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">양식개요</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1">
                      	<logic:notEmpty name="formatBookForm" property="formcomment">
													<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
													<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%>
												</logic:notEmpty>
											</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">제출양식</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1">
	                      <logic:iterate id="list" name="formatBookForm" property="listfilebook">
													<a target="downloadProcFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
												</logic:iterate><iframe name="downloadProcFrame" width="0" height="0" title=""></iframe>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">카테고리</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1" height="85"> 
                      	<html:select property="listcategorynm1" size="5" style="width:200">
													<logic:notEmpty name="formatBookForm" property="listcategorynm2">
														<html:optionsCollection name="formatBookForm" property="listcategorynm2"/>
													</logic:notEmpty>
					            	</html:select>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="11"></td>
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
	          <logic:equal name="formatBookForm" property="type" value="2">
	        		<a href="javascript:click_select('/formatBookGetComm.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			      </logic:equal>
			      <logic:equal name="formatBookForm" property="type" value="3">
			      	<a href="javascript:click_select('/formatBookGetUsed.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			      </logic:equal>
						<a href="javascript:click_close()"><img src="/images/common/btn_close2.gif" border="0" alt="닫기"></a>
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
<script>
window.dialogArguments.processingHide();
window.dialogArguments.parent.processingHide();
</script>
</body>
</html>