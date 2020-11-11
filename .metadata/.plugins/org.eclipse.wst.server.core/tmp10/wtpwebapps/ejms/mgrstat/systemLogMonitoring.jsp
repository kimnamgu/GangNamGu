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
<jsp:include page="/include/header_admin.jsp" flush="true" />
<script src="/script/Calendar.js"></script>
<script language="javascript">
menu2.onmouseover();
menu22.onmouseover();

function changeOrg() {
	var frm = document.forms[0];
	try{
		frm.orggbn_dt.value = "";
	} catch (Exception) { }
	frm.submit();
}
function showDetailOrg(gubun, ccdSubCd)	
{
  window.open('/systemLogDetailMonitoringList.do?sch_gubun='+gubun+'&ccdSubCd='+ccdSubCd,'popup_SysMonitor','menubars=no, scrollbars=no, width=680,height=500');
}
</script>
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/systemLogMonitoringList">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_26.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                  	<tr>
                  		<td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
												<table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
													<tr> 
														<td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
														<td width="60" height="20"><font color="#4F4F4F">조직구분</font></td>
														<td width="370" height="20">
															<% if ( "001".equals(session.getAttribute("user_gbn")) && nexti.ejms.common.appInfo.isSidoldap() ) { %>
															<html:select property="orggbn" style="width:100px;background-color:white" onchange="changeOrg();">
																<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
																	<bean:define id="user_gbn" name="user_gbn" scope="session"/>
																	<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
																	<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
																	<jsp:setProperty name="orggbn" property="all_fl" value="ALL"/>
																</jsp:useBean>
																<html:optionsCollection name="orggbn" property="beanCollection" />
															</html:select>&nbsp;
															<% } else { %>
															<html:hidden property="orggbn"/>
															<% ((nexti.ejms.systemLogMonitoring.form.SystemLogMonitoringForm)request.getAttribute("systemLogMonitoringForm")).setOrggbn((String)session.getAttribute("user_gbn")); %>
															<% } %>
															<logic:notEqual name="systemLogMonitoringForm" property="orggbn" value="">
																<html:select property="orggbn_dt" style="width:250px;background-color:white" onchange="submit()">
																	<jsp:useBean id="orggbn_dt" class="nexti.ejms.list.form.DeptDetailListForm">
																		<bean:define id="orggbn" name="systemLogMonitoringForm" property="orggbn"/>
																		<bean:define id="rep_dept" name="rep_dept" scope="session"/>
																		<bean:define id="user_id" name="user_id" scope="session"/>
																		<jsp:setProperty name="orggbn_dt" property="orggbn" value="<%=(String)orggbn%>"/>	
																		<jsp:setProperty name="orggbn_dt" property="rep_dept" value="<%=(String)rep_dept%>"/>
																		<jsp:setProperty name="orggbn_dt" property="user_id" value="<%=(String)user_id%>"/>
																		<jsp:setProperty name="orggbn_dt" property="all_fl" value="ALL"/>	
																	</jsp:useBean>
																	<html:optionsCollection name="orggbn_dt" property="beanCollection" />
																</html:select>
															</logic:notEqual>
			             					</td>
														<td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
														<td width="60" height="20"><font color="#4F4F4F">접속일자</font></td>
                            <td width="220" height="20">
                            	<html:text property="search_frdate" style="width:70;background-color:white" readonly="true"/>
					                  	<img src="/images/common/icon_date.gif" align="absmiddle" alt="날짜" style="cursor:hand" onclick="Calendar_D(forms[0].search_frdate);"> ~
					                		<html:text property="search_todate" style="width:70;background-color:white" readonly="true"/>
					                   	<img src="/images/common/icon_date.gif" align="absmiddle" alt="날짜" style="cursor:hand" onclick="Calendar_D(forms[0].search_todate);">
														</td>
			             					<td height="20"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" align="absmiddle" alt="검색"></a></td>
			           					</tr>
							         </table>
							       </td>
							     </tr>
							   </table>
							 </td>
						</tr>
            <tr> 
              <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalcount")%>건</font>이 검색되었습니다</td>
            </tr>
            <tr> 
              <td valign="top"> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="3" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="270">부서명</td>
                      <td class="list_t" width="420">회수(회)</td>
                      <td class="list_t" width="130">상세보기</td>
                    </tr>
                    <tr> 
                      <td colspan="3" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="3" height="1"></td>
                    </tr>
                    <logic:notEmpty name="systemLogMonitoringForm" property="systemLogMonitoringList">
											<logic:iterate id="systemLogMonitoringList"  name="systemLogMonitoringForm" property="systemLogMonitoringList" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td class="list_l" width="270"><bean:write name="systemLogMonitoringList" property="ccdName"/></td>
		                      <td class="list_s" width="420"><bean:write name="systemLogMonitoringList" property="cnt"/>&nbsp;(회)</td>
		                      <td class="list_l" width="130">
	                     			<bean:define id="ccdSubCd" name="systemLogMonitoringList" property="ccdSubCd"/>
                     					<logic:equal name="systemLogMonitoringForm" property="orggbn" value="">
	                     				<font color="#3095C9"><b><a href="#" onclick="showDetailOrg('0','<%=ccdSubCd%>');">[상세보기]</a></b></font>
	                     				</logic:equal>
															<logic:notEqual name="systemLogMonitoringForm" property="orggbn" value="">
	                     				<font color="#3095C9"><b><a href="#" onclick="showDetailOrg('1', '<%=ccdSubCd%>');">[상세보기]</a></b></font>
	                     				</logic:notEqual>
	                     				
	                      	</td>
		                    </tr>
		                    <tr> 
		                      <td colspan="3" class="list_bg2"></td>
		                    </tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="systemLogMonitoringForm" property="systemLogMonitoringList">
											<tr> 
	                      <td colspan="3" class="list_l" align="center">조회된 결과가 없습니다.</td>
	                    </tr>
											<tr> 
	                      <td colspan="3" class="list_bg2"></td>
	                    </tr>
										</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
                </td>
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
      <table width="100%" align="center">
			  <tr> 
			    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/systemLogMonitoringList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalpage").toString(),request.getAttribute("currpage").toString())%></td>
			  </tr>
			</table>
    </td>
  </tr>
  <tr>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table>
<jsp:include page="/include/tail.jsp" flush="true"/>