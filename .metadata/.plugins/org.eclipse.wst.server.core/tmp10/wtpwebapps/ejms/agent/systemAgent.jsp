<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트관리
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
<script language="javascript">
menu4.onmouseover();
menu44.onmouseover();

	function dtlSubmit(frm, p_nm, p_id) {
		frm.p_id.value=p_id;
		frm.p_nm.value=p_nm;
		
		frm.action="/systemAgentRuntime.do";
		frm.submit();
	}

	function ctrSubmit(frm, p_nm, p_id, p_ctr) {
		var msg="";
		if (p_ctr=="001") msg="실행"; 
    else if (p_ctr=="002") msg="즉시실행"; 
		else msg="중지";
		
		if (confirm("["+p_nm+"]을(를) ["+msg+"] 하시겠습니까?")) {
			frm.p_id.value=p_id;
			frm.p_ctr.value=p_ctr;
			
			frm.action="/systemAgentControl.do";
			frm.submit();
		} else {
			return;
		}
	}	
</script>

<bean:size id="tot_cnt" name="systemAgentForm" property="saLists"/>

<html:form method="POST" action="/systemAgent.do">
<input type="hidden" name="p_id">
<input type="hidden" name="p_nm">
<input type="hidden" name="p_ctr">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_17.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="right" height="25" valign="top"><a href="/systemAgent.do"><img src="/images/common/btn_reset.gif" width="60" height="19" alt="새로고침"></a></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="40">Agent<br>번호</td>
                      <td class="list_t">프로그램명</td>
                      <td class="list_t" width="30">수행<br>주기</td>
                      <td class="list_t" width="120">마지막수행일시</td>
                      <td class="list_t">처리로그</td>
                      <td class="list_t" width="45">상태</td>
                      <td class="list_t" width="140">관리</td>
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
                    <logic:notEmpty name="systemAgentForm" property="saLists">
											<logic:iterate id="saLists" name="systemAgentForm" property="saLists" indexId="i">
												<logic:equal name="saLists" property="validTF" value="true">
			                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
			                      <td class="list_no"><%=i.intValue()%></td>
			                      <td class="list_l"><bean:write name="saLists" property="agentName"/></td>
			                      <td class="list_l">
			                      	<logic:equal name="saLists" property="runtimeTF" value="true">
						                    <a href="/systemAgentRuntime.do?p_id=<bean:write name="saLists" property="tableID"/>"><font color="blue">설정</font></a>
						                  </logic:equal>
						                  <logic:notEqual name="saLists" property="runtimeTF" value="true">
						                  	<bean:define id="interval" name="saLists" property="interval"/>
						                  	<%=nexti.ejms.util.commfunction.dateFormat(interval.toString())%>		                  	                  	               
						                  </logic:notEqual>
			                      </td>
			                      <td class="list_l">
			                      	<bean:define id="lastRuntime" name="saLists" property="lastRuntime"/>
						                	<%=nexti.ejms.util.commfunction.dateFormat(lastRuntime.toString())%>
		                				</td>
			                      <td class="list_l">
			                      	<bean:define id="lastRunstat" name="saLists" property="lastRunstat"/>
						                	<logic:equal name="saLists" property="runTF" value="true">
							                	<%=nexti.ejms.util.commfunction.dateFormat(lastRunstat.toString())%>
							                </logic:equal>
														</td>
														<logic:notEmpty name="saLists" property="control">
															<td class="list_l">
																<logic:equal name="saLists" property="runTF" value="true">실행중</logic:equal>
																<logic:notEqual name="saLists" property="runTF" value="true">중지</logic:notEqual>
															</td>
															<td class="list_l">
																<logic:equal name="saLists" property="runTF" value="true">
																	<!-- 즉시실행 -->
																	<font color="blue"><a href="javascript:ctrSubmit(document.forms[0], '<bean:write name="saLists" property="agentName"/>', '<bean:write name="saLists" property="tableID"/>', '002')">[RUN]</a></font>
																	<!-- 시작 -->
																	<font color="DDDDDD">[START]</font>
																	<font color="red"><a href="javascript:ctrSubmit(document.forms[0], '<bean:write name="saLists" property="agentName"/>', '<bean:write name="saLists" property="tableID"/>', '000')">[STOP]</a></font>
																</logic:equal>
																<logic:notEqual name="saLists" property="runTF" value="true">
																	<!-- 일시중지-->
																	<font color="DDDDDD">[RUN]</font>
																	<font color="red"><a href="javascript:ctrSubmit(document.forms[0], '<bean:write name="saLists" property="agentName"/>', '<bean:write name="saLists" property="tableID"/>', '001')">[START]</a></font>
																	<font color="DDDDDD">[STOP]</font>
																</logic:notEqual>
															</td>
														</logic:notEmpty>
														<logic:empty name="saLists" property="control">
				                      <td colspan="2"></td>
				                  	</logic:empty>
			                    </tr>
			                    <tr> 
			                      <td colspan="7" class="list_bg2"></td>
			                    </tr>
			            			</logic:equal>
					           		<logic:notEmpty name="saLists" property="etcjoblist">
													<logic:iterate id="etcjoblist" name="saLists" property="etcjoblist">
														<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
				                      <td></td>
				                      <td class="list_l"><bean:write name="etcjoblist" property="name"/></td>
				                      <td colspan="3"></td>
				                      <td class="list_l"><bean:write name="etcjoblist" property="lastRunStat"/></td>
				                      <td></td>
				                    </tr>
				                    <tr> 
				                      <td colspan="7" class="list_bg2"></td>
				                    </tr>
													</logic:iterate>
												</logic:notEmpty>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="systemAgentForm" property="saLists">
											<tr> 
	                      <td colspan="7" class="list_l" align="center">등록된 Agent가 없습니다.</td>
	                    </tr>
											<tr> 
	                      <td colspan="7" class="list_bg2"></td>
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
    </td>
    <td width="2" valign="top"></td>
  </tr>
</table>
</html:form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>