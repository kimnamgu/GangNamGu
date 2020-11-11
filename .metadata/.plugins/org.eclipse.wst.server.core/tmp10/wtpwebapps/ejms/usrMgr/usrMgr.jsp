<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리
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

<jsp:include page="/include/header_admin.jsp" flush="true"/>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script src="/script/usrMgr.js"></script>
<script language="JavaScript">
menu3.onmouseover();
menu33.onmouseover();

//조회
function querySubmit(frm){	
	if (frm.s_word.value.trim()=="") {
		alert("성명을 입력한 후 검색버튼을 클릭하여 주세요.");
		frm.s_word.focus();
		return false;
	}
  	window.open('/searchUser.do?s_word='+frm.s_word.value,'search_mem','location=no, menubar=no, scrollbars=yes, resizable=yes, width=550,height=310');
}
</script>

<html:form action="/usrMgr" method="post">
<table width="995" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="973" valign="top"> 
      <table width="973" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_23.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="960"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="960"> 
						<table width="960" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
														<td colspan="5">
															<table width="960" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9">
						                    <tr> 
						                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
						                        <table width="950" border="0" cellspacing="5" cellpadding="0" align="center" bgcolor="#F7F7F7">
						                          <tr> 
						                            <td>
						                            	<img src="/images/common/dot.gif" width="7" height="7" alt="">
						                            	연계여부 체크 해제시 <span style="background-color:#E6F0FA"><font color="blue">푸른색으로</font></span> 나타나며, 	연계여부와 사용여부 체크 해제시 <span style="background-color:#E6F0FA"><font color="blue">푸른색으로</font></span> 나타납니다.<br>
																				</td>
																			</tr>
																			<tr>
																				<td>
																					<img src="/images/common/dot.gif" width="7" height="7" alt="">
																					사용여부 체크 해제시 <span style="background-color:#FFE3C8"><font color="blue">붉은색으로</font></span> 나타나며, 삭제처리는 사용여부 체크 해제와 동일합니다.<br>
																				</td>
																			<tr>
																				<td>
																					<img src="/images/common/dot.gif" width="7" height="7" alt="">
																					대상부서 체크 여부는 자료취합에 영향이 있으므로 변경이 필요한 경우 유지보수업체로 문의바랍니다.
						                            </td>
						                          </tr>
						                        </table>
						                      </td>
						                    </tr>
						                  </table>
														</td>
													</tr>
													<tr><td colspan="5" height="5"></td></tr>
													<tr>
														<td width="335">
														
															<table width="335" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="4"><html:img page="/images/usermgr/g_t_1.gif" width="4" height="4"/></td>
																	<td background="/images/usermgr/g_t_t.gif"></td>
																	<td width="4"><html:img page="/images/usermgr/g_t_2.gif" width="4" height="4"/></td>
																</tr>
																<tr>
																	<td background="/images/usermgr/g_t_l.gif"></td>
																	<td>
																		<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
																			<tr>
																				<td align="right">
																					<table width="100%" border="0" cellpadding="0" cellspacing="0">
																						<tr>
																							<td width="40" align="right"><html:img page="/images/usermgr/search_text.gif" width="37" height="14" align="absmiddle"/></td>
																							<td height="20">
																								<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
																									<b>조직구분</b>&nbsp;
																									<html:select name="usrMgrForm" property="orggbn" style="width:150px" onchange="orggbnChange(document.forms[0])">
																										<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
																											<bean:define id="user_gbn" name="user_gbn" scope="session"/>
																											<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
																											<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
																										</jsp:useBean>
																										<html:optionsCollection name="orggbn" property="beanCollection"/>
																									</html:select>&nbsp;
																								<% } else { %>
																									<% if ( nexti.ejms.common.appInfo.isSidoldap() ){ %>
																									<html:hidden property="orggbn" value="001"/>
																									<% } else { %>
																									<html:hidden property="orggbn"/>
																									<% } %>
																								<% } %>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td background="/images/usermgr/g_t_r.gif"></td>
																</tr>
																<tr>
																	<td><html:img page="/images/usermgr/g_t_3.gif" width="4" height="4"/></td>
																	<td background="/images/usermgr/g_t_b.gif"></td>
																	<td><html:img page="/images/usermgr/g_t_4.gif" width="4" height="4"/></td>
																</tr>
															</table>
														</td>
														<td width="8">&nbsp;</td>
														<td width="305" align="center">
														<html:form method="POST" action="/usrMgr.do">
															<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="4"><html:img page="/images/usermgr/g_t_1.gif" width="4" height="4"/></td>
																	<td background="/images/usermgr/g_t_t.gif"></td>
																	<td width="4"><html:img page="/images/usermgr/g_t_2.gif" width="4" height="4"/></td>
																</tr>
																<tr>
																	<td background="/images/usermgr/g_t_l.gif"></td>
																	<td>
																		<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
																			<tr>
																				<td align="right">	
																					<table width="100%" border="0" cellpadding="0" cellspacing="0">
																						<tr>
																							<td width="40" align="right"><html:img page="/images/usermgr/search_text.gif" width="37" height="14" align="absmiddle"/></td>
																							<td><b>사용자명</b>&nbsp;<html:text name="usrMgrForm" property="s_word" styleClass="input2" size="23" style="ime-mode:active;" onkeydown="javascript: if (event.keyCode == 13) {return querySubmit(document.forms[0]);}"/></td>
																							<td width="40" align="left"><html:img page="/images/usermgr/btn_search.gif" width="36" height="20" align="absmiddle" style="cursor:pointer;" onclick="return querySubmit(document.forms[0]);"/></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td background="/images/usermgr/g_t_r.gif"></td>
																</tr>
																<tr>
																	<td><html:img page="/images/usermgr/g_t_3.gif" width="4" height="4"/></td>
																	<td background="/images/usermgr/g_t_b.gif"></td>
																	<td><html:img page="/images/usermgr/g_t_4.gif" width="4" height="4"/></td>
																</tr>
															</table>
															</html:form>
														</td>
														<td width="8">&nbsp;</td>
														<td width="305">
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td align="right">
																		<a id="btn_del" style="display:none"></a>&nbsp;
																		<img src="/images/usermgr/btn_del.gif" width="40" height="20" align="absmiddle" onclick="click_del()" style="cursor:hand;" alt="">
																		<img src="/images/usermgr/btn_new_buseo.gif" width="63" height="20" align="absmiddle" onclick="click_new_dept();" style="cursor:hand;" alt="">
																		<img src="/images/usermgr/btn_new_user.gif" width="73" height="20" align="absmiddle" onclick="click_new_usr();" style="cursor:hand;" alt="">
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr><td height="5"></td></tr>
										<tr>
										  	<td>						
												<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
														<!-- 부서 목록 리스트  iframe -->
														<td width="300" height="330" valign="top">
															<table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="4"><img src="/images/usermgr/g_t_1.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_t.gif"></td>
																	<td width="4"><img src="/images/usermgr/g_t_2.gif" width="4" height="4" alt=""></td>
																</tr>
																<tr>
																	<td background="/images/usermgr/g_t_l.gif"></td>
																	<td style="padding:10px 10px 10px 10px">
																		<iframe frameborder="0" id="ifrm1" name="ifrm1" width="306" height="500" src="usrMgrFrame1.do" scrolling="no" title=""></iframe>
																	</td>
																	<td background="/images/usermgr/g_t_r.gif"></td>
																</tr>
																<tr>
																	<td><img src="/images/usermgr/g_t_3.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_b.gif"></td>
																	<td><img src="/images/usermgr/g_t_4.gif" width="4" height="4" alt=""></td>
																</tr>
															</table>							
												  		</td>
														<td width="15">&nbsp;</td>
														<!-- 하위부서와 하위 사용자  목록 리스트 iframe  -->
														<td width="300" valign="top">
															<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="4"><img src="/images/usermgr/g_t_1.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_t.gif"></td>
																	<td width="4"><img src="/images/usermgr/g_t_2.gif" width="4" height="4" alt=""></td>
																</tr>
																<tr>
																	<td background="/images/usermgr/g_t_l.gif"></td>
																	<td style="padding:10px 10px 10px 10px ">
																		<table width="100%" border="0" cellpadding="0" cellspacing="0">
																			<tr>
																				<td id="select_dept" height="20" style="padding-left:3">&nbsp;</td>
																			</tr>
																		</table>
																		<iframe frameborder="0" id="ifrm2" name="ifrm2" width="276" height="480" scrolling="no" title=""></iframe>
																	</td>
																	<td background="/images/usermgr/g_t_r.gif"></td>
																</tr>
																<tr>
																	<td><img src="/images/usermgr/g_t_3.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_b.gif"></td>
																	<td><img src="/images/usermgr/g_t_4.gif" width="4" height="4" alt=""></td>
																</tr>
															</table>
														</td>
														<td width="15">&nbsp;</td>
														<!-- 하위부서와 하위 사용자 수정 및 추가 iframe -->
														<td width="300" valign="top">
															<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="4"><img src="/images/usermgr/g_t_1.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_t.gif"></td>
																	<td width="4"><img src="/images/usermgr/g_t_2.gif" width="4" height="4" alt=""></td>
																</tr>
																<tr>
																	<td background="/images/usermgr/g_t_l.gif"></td>
																	<td style="padding:10px 10px 10px 10px ">
																		<iframe frameborder="0" id="ifrm3" name="ifrm3" width="276" height="500" scrolling="no" title=""></iframe>
																	</td>
																	<td background="/images/usermgr/g_t_r.gif"></td>
																</tr>
																<tr>
																	<td><img src="/images/usermgr/g_t_3.gif" width="4" height="4" alt=""></td>
																	<td background="/images/usermgr/g_t_b.gif"></td>
																	<td><img src="/images/usermgr/g_t_4.gif" width="4" height="4" alt=""></td>
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
<jsp:include page="/include/tail.jsp" flush="true" />