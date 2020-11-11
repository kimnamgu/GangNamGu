<%--
 * 작성일: 2010.05.26
 * 작성자: 사원 신종영
 * 모듈명: System 접속 Log 모니터링 목록
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
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script language="javascript">
</script>
<title><%=request.getAttribute("deptName")%></title>
<table width="665" border="0" cellspacing="0" cellpadding="0">
<html:form action="/systemLogDetailMonitoringList">
<html:hidden property="sch_gubun"/>
<html:hidden property="ccdSubCd"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="643" valign="top"> 
      <table width="643" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"> 
                  <table width="620" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                  	<tr>
                  		<td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
												<table width="610" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
													<tr> 
														<td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
														<td width="40" height="20"><font color="#4F4F4F">성명</font></td>
                            <td width="165"><html:text property="sch_usernm" style="width:150;background-color:white;"/></td>
			             					<td height="20"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" align="absmiddle" alt="검색"></a></td>
			           					</tr>
							        	</table>
							       	</td>
							     	</tr>
							   	</table>
							 	</td>
							</tr>
						</table>
					</td>
				</tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="620" border="0" cellspacing="0" cellpadding="0">
	            <tr> 
	            	<td width="200">&nbsp;</td>
	              <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalcount")%>건</font>이 검색되었습니다</td>
	            </tr>
	            <tr> 
	              <td valign="top" colspan="2"> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="620" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="320">사용자명</td>
                      <td class="list_t">로그인일시</td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="2" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="1"></td>
                    </tr>
                    <logic:notEmpty name="systemLogMonitoringForm" property="systemLogMonitoringList">
											<logic:iterate id="systemLogMonitoringList"  name="systemLogMonitoringForm" property="systemLogMonitoringList" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td class="list_l"><bean:write name="systemLogMonitoringList" property="ccdName"/></td>
		                      <td class="list_l"><bean:write name="systemLogMonitoringList" property="loginTime"/></td>
		                    </tr>
		                    <tr><td colspan="2" class="list_bg2"></td></tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="systemLogMonitoringForm" property="systemLogMonitoringList">
											<tr> 
	                      <td colspan="2" class="list_l" align="center">조회된 결과가 없습니다.</td>
	                    </tr>
											<tr> 
	                      <td colspan="2" class="list_bg2"></td>
	                    </tr>
										</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
                </td>
              </tr>
              <tr> 
                <td colspan="2">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table width="100%" align="center">
			  <tr> 
          <bean:define id="sch_gubun" name="systemLogMonitoringForm" property="sch_gubun"/>
         	<bean:define id="sch_usernm" name="systemLogMonitoringForm" property="sch_usernm"/>
			    <td height="20" align="center">
           <%=nexti.ejms.util.commfunction.procPage_AddParam("/systemLogDetailMonitoringList.do","sch_gubun="+(String)sch_gubun+"&sch_usernm="+sch_usernm,(java.util.HashMap)request.getAttribute("search"), request.getAttribute("totalpage").toString(),request.getAttribute("currpage").toString())%>
          </td>
			  </tr>
			</table>
      <table width="95%" align="center">
				<tr>
					<td class="result"><a href="javascript:window.close()"><img src="/images/common/btn_close2.gif" alt="닫기"></a></td>
				</tr>
			</table>
    </td>
  </tr>
</html:form>
</table>
<jsp:include page="/include/tail.jsp" flush="true"/>