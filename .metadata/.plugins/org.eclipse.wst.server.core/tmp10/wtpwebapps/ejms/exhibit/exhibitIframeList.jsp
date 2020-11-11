<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유 목록
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
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script language="JavaScript">
function goResultView(sysdocno)
{
	parent.document.location= "/researchResult.do?mode=local&rchno=" + sysdocno;
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="563" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/exhibitList">
<html:hidden property="searchdept"/>
<html:hidden property="treescroll"/>
	<tr> 
  <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalCount")%>건</font>이 검색되었습니다</td>
  </tr>
  <tr> 
    <td> 
      <table width="563" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="4" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="35">연번</td>
          <td class="list_t">문서제목</td>
          <td class="list_t" width="180">취합부서</td>
          <td class="list_t" width="50">담당자</td>
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
        
 		<logic:notEmpty name="exhibitListForm" property="doclist">
      <logic:iterate id="list" name="exhibitListForm" property="doclist" indexId="i">	      
	      <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
          <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
				<logic:equal name="list" property="gubun" value="1">
					<td class="list_s"><a class="list_s2" href="javascript:parent.viewForm('<bean:write name='list' property='formkind'/>', '<bean:write name='list' property='sysdocno'/>', '<bean:write name='list' property='formseq'/>')">[취합]<bean:write name="list" property="doctitle"/></a></td>
				</logic:equal>
				<logic:equal name="list" property="gubun" value="2">
					<td class="list_s"><a class="list_s2" href="javascript:showModalPopup('/researchResult.do?rchno=<bean:write name="list" property="sysdocno"/>&range=<bean:write name="list" property="formkind"/>', 700, 680, 0, -40)">[설문]<bean:write name="list" property="doctitle"/></a></td>
				</logic:equal>
				<logic:equal name="list" property="gubun" value="3">
					<td class="list_s"><a class="list_s2" href="javascript:showPopup('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="sysdocno"/>&range=<bean:write name="list" property="formkind"/>&mode=result', 700, 680, 0, -40)">[설문]<bean:write name="list" property="doctitle"/></a></td>
				</logic:equal>
          <td align="center" class="list_l"><bean:write name="list" property="coldeptnm"/></td>
          <td align="center" class="list_l"><bean:write name="list" property="chrgusrnm"/></td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
      </logic:iterate>
		</logic:notEmpty>
			<logic:empty name="exhibitListForm" property="doclist">
				<tr> 
          <td colspan="4" class="list_l">검색된 목록이 없습니다</td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
			</logic:empty>
      </table>
    </td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/exhibitIframeList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
</html:form>
</table>
</body>
</html>
