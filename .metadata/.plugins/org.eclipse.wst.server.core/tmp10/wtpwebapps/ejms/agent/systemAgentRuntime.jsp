<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 변경
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

	//agent runtime 추가
	function agentRuntimeWriteForm(p_id)	
	{
	  window.open('/systemAgentRuntimeWriteForm.do?p_id='+p_id+'&mode=','popup_sar','menubars=no, scrollbars=no, width=500,height=170');
	}
		
	//agent runtime 수정
	function agentRuntimeModifyForm(p_id, p_seq)	
	{
	  window.open('/systemAgentRuntimeWriteForm.do?p_id='+p_id+'&p_seq='+p_seq+'&mode=m','popup_sar','menubars=no, scrollbars=no, width=500,height=170');
	}
		
	//agent runtime 삭제
	function agentRuntimeDelete(frm, p_id, p_seq)	
	{
		if (confirm("삭제하시겠습니까?")) {
			frm.p_id.value=p_id;
			frm.p_seq.value=p_seq;
			
			frm.action="/systemAgentRuntimeDelete.do";
			frm.submit();
		} else {
			return;
		}
	}
</script>

<html:form method="POST" action="/systemAgentRuntime.do">
<html:hidden name="systemAgentForm" property="p_id"/>
<html:hidden name="systemAgentForm" property="p_seq"/>
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
                <td align="right" height="25" valign="top"><a href="/systemAgent.do"><img src="/images/common/btn_list.gif" alt="목록보기"></a></td>
              </tr>
              <tr>
              	<td height="10"></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="6" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="130">Runtime번호</td>
                      <td class="list_t" width="170">프로그램명</td>
                      <td class="list_t" width="130">Schedule유형</td>
                      <td class="list_t" width="130">실행시각</td>
                      <td class="list_t" width="130">현재상태</td>
                      <td class="list_t" width="130">관리</td>
                    </tr>
                    <tr> 
                      <td colspan="6" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" height="1"></td>
                    </tr>
                    <logic:notEmpty name="systemAgentForm" property="saLists">
											<logic:iterate id="saLists" name="systemAgentForm" property="saLists" indexId="i">
												<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
													<td class="list_no"><bean:write name="saLists" property="p_seq"/></td>
		                      <td class="list_l"><bean:write name="systemAgentForm" property="p_nm"/></td>
		                      <td class="list_l"><bean:write name="saLists" property="p_typenm"/></td>
		                      <td class="list_l">
		                      	<logic:equal name="saLists" property="p_type" value="001">
															<bean:define id="p_t1" name="saLists" property="p_t1"/>
															<%=nexti.ejms.util.commfunction.dateFormatForRuntime("001",p_t1.toString())%>													
														</logic:equal>
														<logic:equal name="saLists" property="p_type" value="002">
															<bean:define id="p_t2" name="saLists" property="p_t2"/>
															<%=nexti.ejms.util.commfunction.dateFormatForRuntime("002",p_t2.toString())%>												
														</logic:equal>
														<logic:equal name="saLists" property="p_type" value="003">
															<bean:define id="p_t3" name="saLists" property="p_t3"/>
															<%=nexti.ejms.util.commfunction.dateFormatForRuntime("003",p_t3.toString())%>											
														</logic:equal>
														<logic:equal name="saLists" property="p_type" value="004">
															<bean:define id="p_t4" name="saLists" property="p_t4"/>										
															<%=nexti.ejms.util.commfunction.dateFormatForRuntime("004",p_t4.toString())%>										
														</logic:equal>
		                      </td>
		                      <td class="list_l"><bean:write name="saLists" property="p_use"/></td>
		                      <td class="list_l">
		                      	<a href="javascript:agentRuntimeModifyForm('<bean:write name="saLists" property="p_id"/>','<bean:write name="saLists" property="p_seq"/>')"><img src="/images/common/btn_modify2.gif" alt="수정"></a>
														<a href="javascript:agentRuntimeDelete(document.forms[0], '<bean:write name="saLists" property="p_id"/>', <bean:write name="saLists" property="p_seq"/>)"><img src="/images/common/btn_del.gif" alt="삭제"></a>
		                      </td>
												</tr>
												<tr> 
		                      <td colspan="6" class="list_bg2"></td>
		                    </tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="systemAgentForm" property="saLists">
											<tr> 
	                      <td colspan="6" class="list_l" align="center">등록된 Agent Runtime 이  없습니다.</td>
	                    </tr>
											<tr> 
	                      <td colspan="6" class="list_bg2"></td>
	                    </tr>
										</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
                </td>
              </tr>
              <tr>
              	<td height="10"></td>
              </tr>
              <tr> 
                <td align="right" height="25" valign="top"><a href="javascript:agentRuntimeWriteForm('<bean:write name="systemAgentForm" property="p_id"/>')"><img src="/images/common/btn_record.gif" alt="등록"></a></td>
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
<jsp:include page="/include/tail.jsp" flush="true" />