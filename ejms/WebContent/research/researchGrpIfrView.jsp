<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 기존설문그룹가져오기 목록
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
function goDataView(rchgrpno)
{
	parent.document.location= "/researchGrpView.do?rchgrpno=" + rchgrpno;
}

function goGrpMyList(){
	parent.document.location= "/researchGrpMyList.do";
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="580" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/exhibitList">
<html:hidden property="searchdept"/>
<html:hidden property="treescroll"/>
	<tr> 
  <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalCount")%>건</font>이 검색되었습니다</td>
  </tr>
  <tr> 
    <td> 
      <table width="580" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="4" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="50">연번</td>
          <td class="list_t">설문그룹제목</td>
          <td class="list_t" width="70">설문기간</td>
          <td class="list_t" width="100"><% if (nexti.ejms.common.appInfo.isOutside()){ %>설문범위<% } %></td>
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
        
 		<logic:notEmpty name="researchForm" property="listrch">
      <logic:iterate id="list" name="researchForm" property="listrch" indexId="i">	      
	      <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
          <td class="list_no" align="center"><bean:write name="list" property="seqno"/></td>
          <td class="list_s"><a class="list_s2" href="javascript:parent.showModalPopup('/researchGrpPreview.do?mode=getused&rchgrpno=<bean:write name="list" property="rchgrpno"/>',700,750,0,0)"><bean:write name="list" property="title"/></a></td>
          <td class="list_s" align="center"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></td>
          <td class="list_s" align="center">
          	<% if (nexti.ejms.common.appInfo.isOutside()){ %>
							<logic:equal name="list" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%><bean:define id="rangedetail" name="list" property="rangedetail"/>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)</logic:equal>
	           	<logic:notEqual name="list" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%></logic:notEqual>
	          <% } %>
					</td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
      </logic:iterate>
		</logic:notEmpty>
			<logic:empty name="researchForm" property="listrch">
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
  	<td align="right"><br><a href="javascript:goGrpMyList()"><img src="/images/common/btn_grpmyresearch.gif" border="0" alt="내설문조사그룹가기"></a></td>
  </tr>
  <tr> 
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/researchGrpGetUsed.do",(java.util.HashMap)request.getAttribute("sch"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
</html:form>
</table>
</body>
</html>