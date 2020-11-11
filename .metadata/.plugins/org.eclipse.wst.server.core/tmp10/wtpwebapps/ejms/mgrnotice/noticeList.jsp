<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 목록
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
<% if ("001".equals(session.getAttribute("isSysMgr"))){ %>
	<jsp:include page="/include/header_admin.jsp" flush="true"/>
<% } else { %>
	<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>
<script>
menu5.onmouseover();
menu55.onmouseover();
</script>
<html:form method="POST" action="/noticeList">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_18.gif" alt="공지사항목록" /></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
                        <!--검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                            <td width="10" align="center" height="20"></td>
                            <td height="20" width="250"> 
                            	<html:select property="gubun" style="width:50px;background-color:white">
							                 	<html:option value="1">제목</html:option>
							                 	<html:option value="2">내용</html:option>		                        
						                  </html:select>
						                  <html:text property="searchval" style="width:190;background-color:white"/>
                            </td>
                            <td height="20"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" alt="검색"></a></td>
                          </tr>
                        </table>
                        <!--검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td class="result"> 
                  <!-- 검색 결과-->
                  * 총 <font class="result_no"><%=request.getAttribute("totalCount")%>건</font>이 검색되었습니다</td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="80">번호</td>
                      <td class="list_t">제목</td>
                      <td class="list_t" width="130">날짜</td>
                      <td class="list_t" width="100">조회수</td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <logic:notEmpty name="noticeForm" property="noticeList">
											<logic:iterate id="list" name="noticeForm" property="noticeList" indexId="i">
		                    <tr> 
		                      <td colspan="4" height="1"></td>
		                    </tr>
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td width="80" class="list_no"><%=Integer.parseInt(request.getAttribute("tbunho").toString())-i.intValue() %></td>
		                      <td class="list_s">
		                      	<a class="list_s2" href="/noticeView.do?seq=<bean:write name="list" property="seq"/>"><bean:write name="list" property="title"/></a>
		                      	<logic:notEqual name="list" property="filenm" value=""><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><img src="/images/collect/icon_folder.gif" align="absmiddle" alt="<bean:write name="list" property="originfilenm"/>"></a>&nbsp;</logic:notEqual>
				                		<logic:equal name="list" property="newfl" value="1"><img src="/images/new.gif" align="absmiddle" alt="새로 등록된 글"></logic:equal>
													</td>
		                      <td width="130" class="list_l"><bean:write name="list" property="crtdt"/></td>
		                      <td width="100" class="list_l"><bean:write name="list" property="visitno"/></td>
		                    </tr>
		                    <tr> 
		                      <td colspan="4" class="list_bg2"></td>
		                    </tr>
				            	</logic:iterate>
										</logic:notEmpty>	
										<logic:empty name="noticeForm" property="noticeList">
											<tr> 
	                      <td colspan="4" class="list_l" align="center">조회된 내용이 없습니다.</td>
	                    </tr>
	                    <tr> 
	                      <td colspan="4" class="list_bg2"></td>
	                    </tr>
										</logic:empty>
                  </table>
                  <!--리스트 테이블 끝-->
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right">
                      	<% if ("001".equals(session.getAttribute("isSysMgr"))){ %>
			                    <a href="/noticeWrite.do"><img src="/images/common/btn_write.gif" alt="글쓰기"></a>
		                  	<% } %>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="50" align="center"><%=nexti.ejms.util.commfunction.procPage("/noticeList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
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
</table>
</html:form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>