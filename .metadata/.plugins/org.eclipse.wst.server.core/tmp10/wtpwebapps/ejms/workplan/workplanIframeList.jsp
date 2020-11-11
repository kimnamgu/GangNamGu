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
/* 검색값 유지 (JHKim, 13.05.14) */
function click_list(actionpath) {
    var searchyear = 'searchyear='+parent.document.getElementById('searchyear').value;
    var searchstrdt = 'searchstrdt='+parent.document.getElementById('searchstrdt').value;
    var searchenddt = 'searchenddt='+parent.document.getElementById('searchenddt').value;
    var searchstatus = 'searchstatus='+parent.document.getElementById('searchstatus').value;
    var searchdeptcd = 'searchdeptcd='+parent.document.getElementById('searchdeptcd').value;
    var searchchrgunitcd = 'searchchrgunitcd='+parent.document.getElementById('searchchrgunitcd').value;
    var searchgubun = 'searchgubun='+parent.document.getElementById('searchgubun').value;
    var searchtitle = 'searchtitle='+parent.document.getElementById('searchtitle').value;
    var orggbn = 'orggbn='+parent.document.getElementById('orggbn').value;
    var page = 'page='+<%=request.getAttribute("currpage")%>;
    
    var searchstr = '&'+searchyear+'&'+searchstrdt+'&'+searchenddt+'&'+searchstatus+'&'+searchdeptcd+'&'+searchchrgunitcd+'&'+searchgubun+'&'+searchtitle+'&'+orggbn+'&'+page;
    
    path_move(actionpath+searchstr);
}
function path_move(path){
    parent.location.href = path;
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="563" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/workplanList">
<html:hidden property="searchdept"/>
<html:hidden property="treescroll"/>
	<tr>
  <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalCount")%>건</font>이 검색되었습니다</td>
  </tr>
  <tr> 
    <td> 
      <table width="563" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="7" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="35">번호</td>
          <td class="list_t" width="243">사 업 명</td>
          <td class="list_t" width="50">담당자</td>
          <td class="list_t" width="80">부서</td>
          <td class="list_t" width="75">작성일</td>
          <td class="list_t" width="60">첨부</td>
          <td class="list_t" width="35">조회</td>
          
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr>
        <tr> 
          <td colspan="7" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr>
        <logic:notEmpty name="workplanListForm" property="worklist">
            <logic:iterate id="list" name="workplanListForm" property="worklist" indexId="i">	      
              <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
                <td class="list_no"><bean:write name="list" property="seqno"/><html:hidden name="list" property="planno"/></td>
                <td class="list_s"><a class="list_s2" href="javascript:click_list('/workplanInfoView.do?planno=<bean:write name="list" property="planno"/>&mode=view')"><bean:write name="list" property="title"/> (<bean:write name="list" property="resultcnt"/>)</td>
                <td class="list_s" align="center"><bean:write name="list" property="chrgusrnm"/></td>
                <td class="list_s" align="center"><bean:write name="list" property="deptnm"/></td>
                <td class="list_s" align="center"><script>document.write("<bean:write name="list" property="crtdt"/>".substring(0,10))</script></td></td>
                <td class="list_s" align="center">
                  <logic:notEmpty name="list" property="ext1">
                    <logic:match name="list" property="ext1" value="hwp">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>">
                          <img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:match name="list" property="ext1" value="xls">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>">
                          <img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:notMatch name="list" property="ext1" value="hwp">
                          <logic:notMatch name="list" property="ext1" value="xls">
                              <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>">
                              <img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand"/>
                          </logic:notMatch>
                      </logic:notMatch>
                  </logic:notEmpty>
                  <logic:notEmpty name="list" property="ext2">
                    <logic:match name="list" property="ext2" value="hwp">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>">
                          <img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:match name="list" property="ext2" value="xls">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>">
                          <img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:notMatch name="list" property="ext2" value="hwp">
                          <logic:notMatch name="list" property="ext2" value="xls">
                              <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>">
                              <img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand"/>
                          </logic:notMatch>
                      </logic:notMatch>
                  </logic:notEmpty>
                  <logic:notEmpty name="list" property="ext3">
                      <logic:match name="list" property="ext3" value="hwp">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm3"/>&fileName=<bean:write name="list" property="orgfilenm3"/>">
                          <img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:match name="list" property="ext3" value="xls">
                          <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm3"/>&fileName=<bean:write name="list" property="orgfilenm3"/>">
                          <img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand"/>
                      </logic:match>
                      <logic:notMatch name="list" property="ext3" value="hwp">
                          <logic:notMatch name="list" property="ext3" value="xls">
                              <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm3"/>&fileName=<bean:write name="list" property="orgfilenm3"/>">
                              <img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand"/>
                          </logic:notMatch>
                      </logic:notMatch>
                  </logic:notEmpty>
                </td>
                <td class="list_no"><bean:write name="list" property="readcnt" format="#,###"/></td>
              </tr>
              <tr> 
                <td colspan="7" class="list_bg2"></td>
              </tr>
            </logic:iterate>
        </logic:notEmpty>
            <logic:empty name="workplanListForm" property="worklist">
                <tr> 
          <td colspan="7" class="list_l">검색된 목록이 없습니다</td>
        </tr>
        <tr> 
          <td colspan="7" class="list_bg2"></td>
        </tr>
        </logic:empty>
      </table>
    </td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
  </tr>
  <tr>
  	<td align="right">
			<bean:define id="auth" type="java.lang.String" value="N"/>
   		<bean:define id="did" type="java.lang.String" name="workplanListForm" property="searchdept"/>
   		<logic:equal name="dept_code" value="<%=did%>" scope="session">
   		  <bean:define id="auth" type="java.lang.String" value="Y"/>
   		</logic:equal>
   		<logic:equal name="auth" value="Y">
   			<img src="/images/workplan/btn_plnadd.gif" alt="업무등록" style="cursor:hand" onclick="path_move('/workplanMake.do')">
   		</logic:equal>
		</td>
  </tr>
  <tr> 
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/workplanIframeList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
</html:form>
</table>
</body>
</html>
