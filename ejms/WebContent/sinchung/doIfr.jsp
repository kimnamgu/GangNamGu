<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청하기목록
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
<script src="/script/sinchung.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="580" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/doIfr">
<html:hidden property="deptcd"/>
	<tr> 
	  <td> 
	    <table width="580" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
	      <tr> 
	        <td bgcolor="#FFFFFF"> 
	          <!--기존신청서리스트 테이블 시작-->
	          <table width="570" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
	            <tr> 
	              <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
	              <td width="35"><font color="#4F4F4F">제목</font></td>
	              <td width="210"><html:text property="search_title" style="width:200;background-color:white"/></td>
	              <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="forms[0].submit()" alt=""></td>
	              <td>&nbsp;</td>
	            </tr>
	          </table>
	          <!--기존신청서리스트 테이블 끝-->
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
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
          <td class="list_t" width="35">연번</td>
          <td class="list_t" >신청서제목</td>
          <td class="list_t" width="180">접수부서</td>
          <td class="list_t" width="75">신청기간</td>
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
 		<logic:notEmpty name="sinchungListForm" property="sinchungList">
      <logic:iterate id="list" name="sinchungListForm" property="sinchungList" indexId="i">
	      <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
          <td class="list_no" align="center"><%=Integer.parseInt(request.getAttribute("tbunho").toString()) + i.intValue() + 1 %></td>
          <td class="list_s"><a class="list_s2" href="#" onclick="parent.showPopup('/reqForm.do?reqformno=<bean:write name="list" property="reqformno"/>', 700, 680, 0, 0);"><bean:write name="list" property="title"/></a></td>
          <td class="list_s" align="center"><bean:write name="list" property="coldeptnm"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
      </logic:iterate>
		</logic:notEmpty>
			<logic:empty name="sinchungListForm" property="sinchungList">
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
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/doIfr.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
</html:form>
</table>
</body>
</html>