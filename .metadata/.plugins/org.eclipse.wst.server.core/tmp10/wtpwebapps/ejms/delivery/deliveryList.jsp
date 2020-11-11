<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 목록
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
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
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

<html:form action="/deliveryList" method="post">
<html:hidden property="sch_old_deptcd"/>
<html:hidden property="sch_old_userid"/>
<html:hidden property="sch_usernm"/>
<html:hidden property="initentry"/>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/delivery/title_01.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <% if (session.getAttribute("isSysMgr").equals("001")){ %>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--리스트 검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">부서</font></td>
                            <td width="200"><html:text property="sch_deptnm" style="width:200;background-color:white" readonly="true"/></td>
                            <td width="25"><img src="/images/common/search_ico.gif" width="21" height="19" style="cursor:hand" onclick="fTree(document.forms[0], '<%=request.getAttribute("user_gbn")%>', '<%=request.getAttribute("orgid")%>')" alt=""></td>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="getSearch(document.forms[0], '<%=session.getAttribute("isSysMgr")%>')" align="absmiddle" alt=""></td>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        <!--리스트 검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <% } %>
              <tr> 
                <td class="result">* 총 <font class="result_no"><%=request.getAttribute("totalCount")%>건</font>이 검색되었습니다</td>
              </tr>
              <tr> 
                <td> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
										</tr> 
										<tr>
								      <td width="35" class="list_t" align="center">연번</td>
								      <td width="295" class="list_t" align="center">문서제목</td>
								      <td width="100" class="list_t" align="center">접수일자</td>
								      <td width="100" class="list_t" align="center">취합부서</td>
								      <td width="70" class="list_t" align="center">취합담당자</td>
								      <td width="140" class="list_t" align="center">마감시한</td>
								      <td width="80" class="list_t" align="center">남은시간</td>
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
								<logic:notEmpty name="deliveryForm" property="deliList">
								  <logic:iterate id="list" name="deliveryForm" property="deliList" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
								      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s" style="padding-left:5px"><a class="list_s2" href="javascript:location.href='/deliveryCollDocInfoView.do?formkind=<bean:write name="list" property="formkind"/>&sysdocno=<bean:write name="list" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>&initentry='+document.forms[0].initentry.value+'&originuserid=<bean:write name="list" property="dept_member_id"/>'"><bean:write name="list" property="doctitle"/></a></td>
								      <td align="center" class="list_s"><bean:write name="list" property="deliverydt"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="coldeptnm"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="chrgusrnm"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="enddt"/></td>
								      <td align="center" class="list_s">
											<logic:equal name="list" property="remaintime" value="마감시한 초과">
			              		<font color="#ff0000"><bean:write name="list" property="remaintime"/></font>
			              	</logic:equal>
			              	<logic:notEqual name="list" property="remaintime" value="마감시한 초과">
			              		<bean:write name="list" property="remaintime"/>
			              	</logic:notEqual>
											</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="deliveryForm" property="deliList">
										<tr>
											<td colspan="7" align="center" class="list_s">검색된 목록이 없습니다</td>
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
                <td height="30" valign="bottom">
                	<table width="100%">
                		<tr>
                			<td align="right">
			                	<% if (request.getAttribute("initentry").equals("second")){ %>
												<a href="/deliveryList.do"><img src="/images/common/btn_distribution.gif" width="125" height="26" border="0" alt="배부문서가기"></a>
                				<% } %>
                			</td>
                		</tr>
                	</table>
                </td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="initentry" name="deliveryForm" property="initentry"/>
                	<bean:define id="sch_old_deptcd" name="deliveryForm" property="sch_old_deptcd"/>
                	<bean:define id="sch_deptnm" name="deliveryForm" property="sch_deptnm"/>
                	<%=nexti.ejms.util.commfunction.procPage_AddParam("/deliveryList.do","initentry="+(String)initentry+"&sch_old_deptcd="+(String)sch_old_deptcd+"&sch_deptnm="+(String)sch_deptnm,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
                </td>
            	</tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
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