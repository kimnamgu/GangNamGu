<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 타부서 취합양식자료
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
String sysdocno = "";
String formseq = "";
String formkind = "";

if(request.getParameter("sysdocno") != null) {
	sysdocno = request.getParameter("sysdocno");
} else if(request.getAttribute("sysdocno") != null) {
	sysdocno = request.getAttribute("sysdocno").toString();
}

if(request.getParameter("formseq") != null) {
	formseq = request.getParameter("formseq");
} else if(request.getAttribute("formseq") != null) {
	formseq = request.getAttribute("formseq").toString();
}

if(request.getParameter("formkind") != null) {
	formkind = request.getParameter("formkind");
}
%>

<head>
<title><%=nexti.ejms.common.appInfo.getAppName()%></title>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/prototype.js"></script>
<script src="/script/collproc.js"></script>
<script>
function viewForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "/collproc";
	var addQueryString = "";

	if (args[0] == "01")			{childURL = "/formatLineOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString;
}

function iframeScroll() {
	var frmDoc = formFrame.document;
	if(frmDoc.readyState == "complete") {
		frmDoc.body.scrollTop = parseInt("0<%=(String)request.getParameter("frmScrollTop")%>", 10);
		
		var sh = frmDoc.body.scrollHeight;
		if (sh < formFrameTD.height) {	//양식다섯개
			formFrameTD.height = sh;
		}
	}
}
function doSubmit() {
	var param = '?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop;
	document.forms[0].action += param;
	document.forms[0].submit();
}
</script>
</head>

<div id="comment" style="display:none; position:absolute;">
<table border="1" style="border-collapse:collapse" bgcolor="rgb(255, 255, 200)"><tr><td>
<font face="돋움" size="2">&nbsp;클릭하시면 정렬됩니다.&nbsp;</font>
</td></tr></table>
</div>

<table width="820" border="0" cellspacing="0" cellpadding="0" style="padding-left:10">
	<tr>
		<td><img src="/images/collect/title_43.gif" alt=""></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td valign="top" height="172" id="formFrameTD">
			<iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="양식목록" onload="iframeScroll()"></iframe>
		</td>
	</tr>
</table><br>

<logic:notEmpty name="formatLineForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collproc/formatLineOtherDeptDataView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="10">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatLineForm" property="formcomment">
										<bean:define id="formcomment" name="formatLineForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">입력정보</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatLineForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatLineForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">합계형태</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="totalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ts"/><label for="ts"><b>전체자료 총계 표시</b></label>
					      </td>
					    </tr>
					    <tr>
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">취합형태</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>입력진행자료 포함(미제출)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFF">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10">
  <tr> 
    <td>
    	<b style="position:absolute;left:290;padding-top:10;color:red;">※ 한글다운로드는 저장만 가능합니다.</b>
    	<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="엑셀다운로드">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="한글다운로드">
			</form>
		</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
  <tr>
  	<td><!-- 양식데이터 나타낼 부분 시작-->
	  	<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
			<logic:iterate id="list" name="formatLineForm" property="listform">
				<bean:write name="list" property="formbodyhtml" filter="false" />
			</logic:iterate>
			<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatFixedForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collproc/formatFixedOtherDeptDataView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="10">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatFixedForm" property="formcomment">
										<bean:define id="formcomment" name="formatFixedForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">입력정보</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatFixedForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatFixedForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">합계형태</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="totalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ts"/><label for="ts"><b>전체자료 총계 표시</b></label>
					      	<b>(</b><html:checkbox property="totalShowStringState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="tsss"/><label for="tsss"><b>문자 총계 표시</b></label><b>)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					      	<html:checkbox property="subtotalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="sts"/><label for="sts"><b>부서별 소계 표시</b></label>
									<b>(</b><html:checkbox property="subtotalShowStringState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="stsss"/><label for="stsss"><b>문자 소계 표시</b></label><b>)</b>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">취합형태</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>입력진행자료 포함(미제출)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFF">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10">
  <tr> 
    <td>
    	<b style="position:absolute;left:290;padding-top:10;color:red;">※ 한글다운로드는 저장만 가능합니다.</b>
    	<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="엑셀다운로드">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="한글다운로드">
			</form>
		</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
  <tr>
  	<td><!-- 양식데이터 나타낼 부분 시작-->
	  	<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
			<logic:iterate id="list" name="formatFixedForm" property="listform">
				<bean:write name="list" property="formbodyhtml" filter="false" />
			</logic:iterate>
			<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatBookForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collproc/formatBookOtherDeptDataView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="10">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatBookForm" property="formcomment">
										<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">제출양식</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:notEmpty name="fileBookForm" property="listfilebook">
										<logic:iterate id="list" name="fileBookForm" property="listfilebook">
											<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
										</logic:iterate>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">입력정보</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatBookForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatBookForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">취합형태</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>입력진행자료 포함(미제출)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFF">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:10">
  <tr> 
  	<td><!-- 양식데이터 나타낼 부분 시작-->
		  <table width="820" border="0" cellspacing="0" cellpadding="0">
		    <tr> 
		      <td colspan="6" class="list_bg4"></td>
		    </tr>
		    <tr> 
		      <td width="100" class="s3">카테고리</td>
		      <td width="100" class="s3">부서</td>
		      <td width="150" class="s3">제목</td>
		      <td width="60" class="s3">입력담당자</td>
		      <td class="s3">첨부파일</td>
		      <td width="80" class="s3">파일크기</td>
		    </tr>
		    <tr> 
		      <td colspan="6" class="list_bg4"></td>
		    </tr>
		<logic:notEmpty name="dataForm" property="dataList">
     	<logic:iterate id="list" name="dataForm" property="dataList" indexId="i">
				<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'"> 
		      <td class="list_l"><bean:write name="list" property="categorynm"/></td>
		      <td class="list_l"><bean:write name="list" property="tgtdeptnm"/></td>
		      <td class="list_l"><bean:write name="list" property="formtitle"/></td>
		      <td class="list_l"><bean:write name="list" property="inputusrnm"/></td>
		      <td class="list_l"><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a></td>
		      <td class="list_l" style="padding-right:10;text-align:right"><script>convertSize(<bean:write name="list" property="filesize"/>)</script></td>
		    </tr>
		    <tr> 
		      <td colspan="6" class="list_bg4"></td>
		    </tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="dataForm" property="dataList">
				<tr>
					<td class="list_l" colspan="6">검색된 목록이 없습니다</td>
				</tr>
				<tr> 
		      <td colspan="6" class="list_bg4"></td>
		    </tr>
		</logic:empty>
			</table>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
</table><br>
</logic:notEmpty>

<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>