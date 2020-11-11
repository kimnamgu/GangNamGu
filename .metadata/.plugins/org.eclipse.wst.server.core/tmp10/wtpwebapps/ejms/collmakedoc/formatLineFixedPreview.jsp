<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형,고정양식형 미리보기
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
	function click_inputPreview() {
		input.style.display = "block";
		collcomp.style.display = "none";
	
		document.inputImage.src = "/images/collect/tab_13.gif";
		document.collcompImage.src = "/images/collect/tab_14_off.gif";
	}

	function click_collcompPreview() {
		input.style.display = "none";
		collcomp.style.display = "block";
		
		document.inputImage.src = "/images/collect/tab_13_off.gif";
		document.collcompImage.src = "/images/collect/tab_14.gif";
	}

	function click_select(actionpath) {
		if(confirm("양식을 선택하시겠습니까?") == true) {
			document.forms[0].action = actionpath;
			processingShow();
			document.forms[0].submit();
		}
	}

	function click_close() {
		window.dialogArguments.processingHide();
		window.close();
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatLinePreview" method="post">
<html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formseq"/><html:hidden property="formkind"/>
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
					<td><br><a href="javascript:click_inputPreview()"><img src="/images/collect/tab_13.gif" name="inputImage" border="0" alt="입력양식"></a><a href="javascript:click_collcompPreview()"><img src="/images/collect/tab_14_off.gif" name="collcompImage" border="0" alt="최종취합양식"></a></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0">
			     		<logic:equal name="formatLineForm" property="formkind" value="01">
			          <tr>
			          	<td>
			       				<div id="input" style="display:block; width:744; height:380; overflow:auto; border:lightgrey 1 solid">
					          	<bean:write name="formatLineForm" property="formbodyhtml_ext" filter="false"/>
											<bean:write name="formatLineForm" property="formbodyhtml" filter="false"/>
											<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
										</div>
			          		<div id="collcomp" style="display:none; width:744; height:380; overflow:auto; border:lightgrey 1 solid">
				          		<!-- 최종취합자료 양식 헤더를 formbodyhtml_ext에 담아서 넘김 -->
											<bean:write name="formatLineForm" property="formbodyhtml_ext" filter="false"/>
											<logic:iterate id="list" name="formatLineForm" property="listform">
												<bean:write name="list" property="formbodyhtml" filter="false" />
				     					</logic:iterate>
											<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
										</div>
									</td>
			          </tr>
			        </logic:equal>
			        <logic:equal name="formatLineForm" property="formkind" value="02">
			          <tr>
			          	<td>
			       				<div id="input" style="display:block; width:744; height:380; overflow:auto; border:lightgrey 1 solid">
					          	<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
											<bean:write name="formatLineForm" property="formbodyhtml" filter="false"/>
											<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
										</div>
			          		<div id="collcomp" style="display:none; width:744; height:380; overflow:auto; border:lightgrey 1 solid">
				          		<!-- 최종취합자료 양식 헤더를 formbodyhtml_ext에 담아서 넘김 -->
											<bean:write name="formatLineForm" property="formbodyhtml_ext" filter="false"/>
											<logic:iterate id="list" name="formatLineForm" property="listform">
												<bean:write name="list" property="formbodyhtml" filter="false" />
			       					</logic:iterate>
											<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
										</div>
									</td>
			          </tr>
			        </logic:equal>
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
			      <logic:equal name="formatLineForm" property="type" value="2">
				      <logic:equal name="formatLineForm" property="formkind" value="01">
				      	<a href="javascript:click_select('/formatLineGetComm.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			        </logic:equal>
			        <logic:equal name="formatLineForm" property="formkind" value="02">
								<a href="javascript:click_select('/formatFixedGetComm.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			        </logic:equal>
			      </logic:equal>
			      <logic:equal name="formatLineForm" property="type" value="3">
				      <logic:equal name="formatLineForm" property="formkind" value="01">
				      	<a href="javascript:click_select('/formatLineGetUsed.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			        </logic:equal>
			        <logic:equal name="formatLineForm" property="formkind" value="02">
								<a href="javascript:click_select('/formatFixedGetUsed.do')"><img src="/images/common/btn_select4.gif" border="0" alt="선택"></a>
			        </logic:equal>
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