<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 목록
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
<%@ page import="java.util.Calendar" %>
<%
Calendar today = Calendar.getInstance();

int currentyear = today.get(Calendar.YEAR);
String selyear = "";

if(request.getAttribute("currentyear") != null) {
	selyear = request.getAttribute("currentyear").toString();
}
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/prototype.js"></script>
<script src="/script/inputing.js"></script>
<script>
menu3.onmouseover();
menu33.onmouseover();

var inputComplete = true;
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/inputCompleteList" method="post">
<html:hidden property="sch_old_deptcd"/>
<html:hidden property="sch_old_userid"/>
<html:hidden property="initentry" value="notfirst"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/inputing/title_02.gif" alt=""></td>
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
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--리스트 검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="55"><font color="#4F4F4F">입력년도</font></td>
                            <td width="85"> 
                              <html:select property="selyear" styleClass="select" style="width:75px;background-color:white" value="<%=selyear%>" onchange="forms[0].submit()">
																<jsp:useBean id="yearselect" class="nexti.ejms.list.form.YearListForm">
																	<jsp:setProperty name="yearselect" property="currentyear" value="<%=currentyear%>"/>
																	<jsp:setProperty name="yearselect" property="gap" value="1"/>
																</jsp:useBean>
																<html:optionsCollection name="yearselect" property="beanCollection"/>
					                 		</html:select>
                            </td>
														<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">제목</font></td>
                            <td width="110"><html:text property="searchvalue" style="width:100px;background-color:white"/></td>
                					  <% if (session.getAttribute("isSysMgr").equals("001")){ %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">부서</font></td>
                            <td width="150"><html:text property="sch_deptnm" style="width:150;background-color:white" onkeydown="sch_old_deptcd.value=''"/></td>
                            <td width="25"><img src="/images/common/search_ico.gif" width="21" height="19" style="cursor:hand" onclick="fTree(document.forms[0], '<%=request.getAttribute("user_gbn")%>', '<%=request.getAttribute("orgid")%>')" alt=""></td>
                           	<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="45"><font color="#4F4F4F">담당자</font></td>
                            <td width="90"><html:text property="sch_usernm" style="width:80;background-color:white" onkeydown="sch_old_userid.value=''"/></td>
                            <% } %>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="getSearch(document.forms[0], '<%=session.getAttribute("isSysMgr")%>')" align="absmiddle" alt=""></td>
                            <!-- <td><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="forms[0].submit()" alt=""></td> -->
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        <!--리스트 검색 테이블 끝-->
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
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="8" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
										</tr> 
										<tr>
								      <td width="30" class="list_t" align="center">연번</td>
								      <td width="285" class="list_t" align="center">문서제목</td>
								      <td width="55" class="list_t" align="center">진행상태</td>
								      <td width="110" class="list_t" align="center">입력완료처리일시</td>
								      <td width="80" class="list_t" align="center">입력완료구분</td>
								      <td width="100" class="list_t" align="center">취합부서</td>
								      <td width="65" class="list_t" align="center">취합담당자</td>
								      <td width="95" class="list_t" align="center">접수일자</td>
								    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
                    </tr>
								<logic:notEmpty name="inputCompleteForm" property="completeList">
								  <logic:iterate id="list" name="inputCompleteForm" property="completeList" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
								      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s" style="padding-left:5px"><a class="list_s2" href="javascript:location.href='/inputCompCollDocInfoView.do?formkind=<bean:write name="list" property="formkind"/>&sysdocno=<bean:write name="list" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>&originuserid=<bean:write name="list" property="inputusrid"/>'"><bean:write name="list" property="doctitle"/></a></td>
								      <td align="center" class="list_s"><bean:write name="list" property="submistate"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="inusrenddt"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="inputendreason"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="coldeptnm"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="chrgusrnm"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="deliverydt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="8" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="inputCompleteForm" property="completeList">
										<tr>
											<td colspan="8" align="center" class="list_s">검색된 목록이 없습니다</td>
										</tr>
										<tr>
								      <td colspan="8" class="list_bg2"></td>
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
												<a href="/inputingList.do"><img src="/images/common/btn_enter.gif" width="125" height="26" border="0" alt="입력문서가기"></a>
                				<% } %>
                			</td>
                		</tr>
                	</table>
                </td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="initentry" name="inputCompleteForm" property="initentry"/>
	               	<bean:define id="searchvalue" name="inputCompleteForm" property="searchvalue"/>
	               	<bean:define id="sch_old_deptcd" name="inputCompleteForm" property="sch_old_deptcd"/>
	               	<bean:define id="sch_old_userid" name="inputCompleteForm" property="sch_old_userid"/>
	               	<bean:define id="sch_deptnm" name="inputCompleteForm" property="sch_deptnm"/>
	               	<bean:define id="sch_usernm" name="inputCompleteForm" property="sch_usernm"/>
               		<%=nexti.ejms.util.commfunction.procPage_AddParam("/inputCompleteList.do","initentry="+(String)initentry+"&selyear="+(String)selyear+"&searchvalue="+(String)searchvalue+"&sch_old_deptcd="+(String)sch_old_deptcd+"&sch_old_userid="+(String)sch_old_userid+"&sch_deptnm="+(String)sch_deptnm+"&sch_usernm="+(String)sch_usernm,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
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
</html:form>
</table>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>