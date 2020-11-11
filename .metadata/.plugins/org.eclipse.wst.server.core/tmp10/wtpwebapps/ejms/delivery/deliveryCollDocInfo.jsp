<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 취합문서정보
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
<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/prototype.js"></script>
<script src="/script/delivery.js"></script>
<script>
menu2.onmouseover();
menu22.onmouseover();
</script>

<form name="frm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/delivery/title_05.gif" alt=""></td>
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
                  <!--상단 선택메뉴 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/collect/s_m_01_over.gif" width="94" height="41" alt="취합문서정보"></td>
                            <td width="94"><img src="/images/collect/s_m_02.gif" width="94" height="41" alt="취합양식자료" style="cursor:hand" onclick="viewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=<%=request.getParameter("frmScrollTop")%>')"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon">
                            	<logic:equal value="true" name="isTargetDept">
																<img src="/images/common/btn_return.gif" alt="반송" style="cursor:hand" onclick="showModalPopup('/deliveryRetDocView.do?sysdocno=<%=sysdocno%>',680,285,0,0)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<img src="/images/common/btn_baebu.gif" alt="배부" style="cursor:hand" onclick="chkDeliveryProc(<%=sysdocno%>)">
															</logic:equal>
                              <img src="/images/common/btn_list.gif" alt="목록보기" style="cursor:hand" onclick="location.href='/deliveryList.do'">
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="5"><img src="/images/common/select_right.gif" width="5" height="41" alt=""></td>
                    </tr>
                  </table>
                  <!--상단 선택메뉴 테이블-->
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr><!--처리현황 테이블 시작-->
                <td>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제목</td>
                      <td class="bg2"></td>
                      <td class="t1" width="350"><bean:write name="commCollDocInfoForm" property="doctitle"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">접수일</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1"><bean:write name="commCollDocInfoForm" property="deliverydt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합문서번호</td>
                      <td class="bg2"></td>
                      <td class="t1" width="350"><bean:write name="commCollDocInfoForm" property="docno"/></td>
                      <td colspan="6"></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합담당자</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="coldeptnm"/>&nbsp;&nbsp;&nbsp;<% if (request.getAttribute("chrgunitnm") != null){ %><bean:write name="commCollDocInfoForm" property="chrgunitnm"/>&nbsp;&nbsp;&nbsp;<% } %><bean:write name="commCollDocInfoForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;문의전화:<bean:write name="commCollDocInfoForm" property="coldepttel"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">관련근거</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="basis"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합개요</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      <logic:notEmpty name="commCollDocInfoForm" property="summary">
	                    	<bean:define id="summary" name="commCollDocInfoForm" property="summary"/>
	                    	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString()) %>
                    	</logic:notEmpty></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">마감시한</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><font color="#FF5656">
                      	<b><bean:write name="commCollDocInfoForm" property="enddt"/></b></font>&nbsp;&nbsp;&nbsp;
						                        마감알림말 : <bean:write name="commCollDocInfoForm" property="endcomment"/>
						          </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">수신대상</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="targetdeptnm"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">기타옵션</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><html:checkbox name="commCollDocInfoForm" property="openinput" value="Y" style="border:0;background-color:transparent;" onclick="this.checked=!this.checked"/>입력시 타부서 자료보기</td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr  valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="commCollDocInfoForm" property="filenm"/>&fileName=<bean:write name="commCollDocInfoForm" property="originfilenm"/>"><bean:write name="commCollDocInfoForm" property="originfilenm"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--처리현황 테이블 끝 -->
              <tr>
              	<td>&nbsp;</td>
              </tr>
              <tr><!--처리현황 테이블 시작-->
                <td>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
	                  <tr> 
	                    <td colspan="5" class="list_bg2"></td>
	                  </tr>
	                  
	                  <logic:equal value="true" name="isTargetDeptRoot">
	                  	<logic:equal value="true" name="isTargetDept">
	                  		<tr> 
		                      <td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서내 입력담당자</td>
		                      <td class="bg2"></td>
		                      <td class="t1" width="560"><input type="text" readonly name="appntusrnm" value="<bean:write name="commCollDocInfoForm" property="appntusrnm"/>" style="width:98%;border:0px;background:none"></td>
		                      <td class="bg2"></td>
		                      <td class="t1"><img src="/images/common/btn_people.gif" alt="담당자지정" style="cursor:hand" onclick="showModalPopup('/commapproval/inputusr.do?sysdocno=<%=sysdocno%>',680,402,0,0)"></td>
		                    </tr>
		                  	<% if ( nexti.ejms.common.appInfo.isMultipleDelivery() ) { %>
		                  		<tr> 
			                      <td colspan="5" class="bg1"></td>
			                    </tr>
			                  	<tr>  
			                    	<td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서외 취합대상</td>
			                      <td class="bg2"></td>
			                      <td class="t1" width="560"><input type="text" readonly name="tgtdeptnm" readonly="true" style="width:98%;border:0px;background:none" value="<bean:write name="commCollDocInfoForm" property="appntdeptnm"/>"></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><img src="/images/common/btn_select.gif" alt="취합대상지정" style="cursor:hand" onclick="click_popup('/commsubdept.do?sysdocno=<%=sysdocno%>&moveDeliComp=1', 782, 503)"></td>
			                    </tr>
			                  <% } %>
		                  </logic:equal>
	                  </logic:equal>
	                  
	                  <logic:equal value="true" name="isTargetDeptRoot">
	                  	<logic:equal value="false" name="isTargetDept">
	                  		<% if ( true || nexti.ejms.common.appInfo.isMultipleDelivery() ) { %>
	                    		<tr>  
			                    	<td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서외 취합대상</td>
			                      <td class="bg2"></td>
			                      <td class="t1" width="560"><input type="text" readonly name="tgtdeptnm" readonly="true" style="width:98%;border:0px;background:none" value="<bean:write name="commCollDocInfoForm" property="appntdeptnm"/>"></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><img src="/images/common/btn_select.gif" alt="취합대상지정" style="cursor:hand" onclick="click_popup('/commsubdept.do?sysdocno=<%=sysdocno%>&moveDeliComp=1', 782, 503)"></td>
			                    </tr>
		                   	<% } %>
	                  	</logic:equal>
	                  </logic:equal>
	                  
	                  <logic:equal value="false" name="isTargetDeptRoot">
		                  <logic:equal value="true" name="isTargetDept">
												<tr> 
		                      <td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서내 입력담당자</td>
		                      <td class="bg2"></td>
		                      <td class="t1" width="560"><input type="text" readonly name="appntusrnm" value="<bean:write name="commCollDocInfoForm" property="appntusrnm"/>" style="width:98%;border:0px;background:none"></td>
		                      <td class="bg2"></td>
		                      <td class="t1"><img src="/images/common/btn_people.gif" alt="담당자지정" style="cursor:hand" onclick="showModalPopup('/commapproval/inputusr.do?sysdocno=<%=sysdocno%>',680,402,0,0)"></td>
		                    </tr>
		                  	<% if ( nexti.ejms.common.appInfo.isMultipleDelivery() ) { %>
		                  		<tr> 
			                      <td colspan="5" class="bg1"></td>
			                    </tr>
			                  	<tr>  
			                    	<td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서외 취합대상</td>
			                      <td class="bg2"></td>
			                      <td class="t1" width="560"><input type="text" readonly name="tgtdeptnm" readonly="true" style="width:98%;border:0px;background:none" value="<bean:write name="commCollDocInfoForm" property="appntdeptnm"/>"></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><img src="/images/common/btn_select.gif" alt="취합대상지정" style="cursor:hand" onclick="click_popup('/commsubdept.do?sysdocno=<%=sysdocno%>&moveDeliComp=1', 782, 503)"></td>
			                    </tr>
			                  <% } %>
		                  </logic:equal>
	                  </logic:equal>
	                  
	                  <logic:equal value="false" name="isTargetDeptRoot">
                    	<logic:equal value="false" name="isTargetDept">
                    		<% if ( true || nexti.ejms.common.appInfo.isMultipleDelivery() ) { %>
	                    		<tr>  
			                    	<td class="s1" width="129"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서외 취합대상</td>
			                      <td class="bg2"></td>
			                      <td class="t1" width="560"><input type="text" readonly name="tgtdeptnm" readonly="true" style="width:98%;border:0px;background:none" value="<bean:write name="commCollDocInfoForm" property="appntdeptnm"/>"></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><img src="/images/common/btn_select.gif" alt="취합대상지정" style="cursor:hand" onclick="click_popup('/commsubdept.do?sysdocno=<%=sysdocno%>&moveDeliComp=1', 782, 503)"></td>
			                    </tr>
		                   	<% } %>
                    	</logic:equal>
                    </logic:equal>
                    
                  	<tr> 
                      <td colspan="5" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--처리현황 테이블 끝 -->
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
    <td width="2" valign="top"><img src="/images/common/step_05.gif" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>
</form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>