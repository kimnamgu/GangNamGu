<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 목록
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
<jsp:include page="/include/header_user.jsp" flush="true"/>

<script src="/script/prototype.js"></script>
<script src="/script/collproc.js"></script>
<script>
menu1.onmouseover();
menu11.onmouseover();
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collprocList" method="post">
<html:hidden property="sch_old_deptcd"/>
<html:hidden property="sch_old_userid"/>
<html:hidden property="initentry"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_03.gif" alt=""></td>
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
                            <td width="55"><font color="#4F4F4F">문서상태</font></td>
                            <td width="85"> 
                              <html:select property="docstate" style="width:75px;background-color:white" onchange="submit();">
				                        <html:option value="0">전체</html:option>
				                        <!--<html:option value="1">결재진행</html:option>-->
				                        <html:option value="2">취합진행</html:option>
				                        <html:option value="3">마감대기</html:option>
			                        </html:select>
                            </td>
                					  <% if (session.getAttribute("isSysMgr").equals("001")){ %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">부서</font></td>
                            <td width="200"><html:text property="sch_deptnm" style="width:200;background-color:white" onkeydown="sch_old_deptcd.value=''"/></td>
                            <td width="25"><img src="/images/common/search_ico.gif" width="21" height="19" style="cursor:hand" onclick="fTree(document.forms[0], '<%=request.getAttribute("user_gbn")%>', '<%=request.getAttribute("orgid")%>')" alt=""></td>
                           	<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="45"><font color="#4F4F4F">담당자</font></td>
                            <td width="90"><html:text property="sch_usernm" style="width:80;background-color:white" onkeydown="sch_old_userid.value=''"/></td>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="getSearch(document.forms[0], '<%=session.getAttribute("isSysMgr")%>')" align="absmiddle" alt=""></td>
                            <% } %>
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
											<td width="30" class="list_t" align="center"><input type="checkbox" name="checkall" style="border:0;background-color:transparent;" onclick="check_all(this, 'listdelete[]')"></td>
								      <td width="35" class="list_t" align="center">연번</td>
								      <td width="305" class="list_t" align="center">문서제목</td>
								      <td width="60" class="list_t" align="center">진행상태</td>
								      <td width="100" class="list_t" align="center">배부일자</td>
								      <td width="140" class="list_t" align="center">마감시한</td>
								      <td width="80" class="list_t" align="center">남은시간</td>
								      <td width="70" class="list_t" align="center">제출/배부</td>
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
								<logic:notEmpty name="colldocForm" property="listcolldoc">
								  <logic:iterate id="list" name="colldocForm" property="listcolldoc" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
											<td align="center"><input type="checkbox" name="listdelete[]" value="<bean:write name="list" property="sysdocno"/>" style="border:0;background-color:transparent;"></td>
								      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s" style="padding-left:5px"><a class="list_s2" href="javascript:location.href='/collprocInfoView.do?formkind=<bean:write name="list" property="formkind"/>&sysdocno=<bean:write name="list" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>&originuserid=<bean:write name="list" property="chrgusrcd"/>'"><bean:write name="list" property="doctitle"/></a></td>
								      <td align="center" class="list_s"><bean:write name="list" property="docstate"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="deliverydt"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="enddt"/></td>
								      <td align="center" class="list_s">
											<logic:equal name="list" property="enddt_date" value="마감시한 초과">
												<font color="#ff0000"><bean:write name="list" property="enddt_date"/></font>
			              	</logic:equal>
			              	<logic:notEqual name="list" property="enddt_date" value="마감시한 초과">
												<bean:write name="list" property="enddt_date"/>
			              	</logic:notEqual>
             					</td>
								      <td align="center" class="list_s"><bean:write name="list" property="cnt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="8" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="colldocForm" property="listcolldoc">
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
	                		<td align="left">
	                			<logic:equal name="docDeleteYN" value='y' scope="request">
		                			<img src="/images/common/btn_del.gif" alt="삭제" style="cursor:hand" onclick="click_delete('listdelete[]', '/colldocDel.do?type=2')">
	                			</logic:equal>
	                		</td>
                			<td align="right">
			                	<% if (request.getAttribute("initentry").equals("second")){ %>
												<a href="/collprocList.do?docstate=0"><img src="/images/common/btn_combination.gif" width="125" height="26" border="0" alt="취합문서가기"></a>
                				<% } %>
                			</td>
                		</tr>
                	</table>
                </td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="initentry" name="colldocForm" property="initentry"/>
                	<bean:define id="docstate" name="colldocForm" property="docstate"/>
                	<bean:define id="sch_old_deptcd" name="colldocForm" property="sch_old_deptcd"/>
                	<bean:define id="sch_old_userid" name="colldocForm" property="sch_old_userid"/>
                	<bean:define id="sch_deptnm" name="colldocForm" property="sch_deptnm"/>
                	<bean:define id="sch_usernm" name="colldocForm" property="sch_usernm"/>
                	<%=nexti.ejms.util.commfunction.procPage_AddParam("/collprocList.do","initentry="+(String)initentry+"&docstate="+(String)docstate+"&sch_old_deptcd="+(String)sch_old_deptcd+"&sch_old_userid="+(String)sch_old_userid+"&sch_deptnm="+(String)sch_deptnm+"&sch_usernm="+(String)sch_usernm,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
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