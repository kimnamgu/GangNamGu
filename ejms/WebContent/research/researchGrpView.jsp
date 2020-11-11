<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사그룹 작성
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
	String basePath = nexti.ejms.common.appInfo.getAp_address();
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/research.js"></script>
<script src="/script/Calendar.js"></script>
<script type="text/javascript">
menu5.onmouseover();
menu55.onmouseover();

<!--
	//삭제시에 확인해야 할 사항
	function mst_grpdelete(frm){	
		if(confirm("삭제하시겠습니까")){
			frm.action = "/researchGrpDelete.do";	
			frm.submit();
		}
	}
//-->
</script>
<bean:define id="rchgrpno" name="researchForm" property="rchgrpno"/>
<html:form action="/researchGrpSave" enctype="multipart/form-data">
<html:hidden property="mode"/>
<html:hidden property="rchgrpno"/>
<html:hidden property="mdrchgrpno"/>
<html:hidden property="sessionId"/>
<html:hidden property="delseq"/>
<html:hidden property="posscroll"/>
<html:hidden property="coldeptcd"/>
<html:hidden property="coldeptnm"/>
<html:hidden property="chrgusrid"/>
<html:hidden property="chrgusrnm"/>
<html:hidden property="coldepttel"/>
<table width="865" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20">&nbsp;</td>
		<td width="843" valign="top">
			<table width="843" border="0" cellspacing="0" cellpadding="0">
				<!--타이틀-->
				<tr>
					<td background="/images/common/title_bg.gif"><logic:notEqual name="researchForm" property="mdrchgrpno" value="0"><img src="/images/collect/title_47.gif" alt=""></logic:notEqual><logic:equal name="researchForm" property="mdrchgrpno" value="0"><img src="/images/collect/title_45.gif" alt=""></logic:equal></td>
					<td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" alt=""></td>
				</tr>
				<tr>
					<td height="11"></td>
				</tr>
				<tr>
					<td height="6" valign="top">
						<table width="820" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="10"></td>
							</tr>
							<tr>
								<td>
									<!--처리현황 테이블-->
									<table width="820" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td colspan="3" class="list_bg2"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>제목</b></td>
											<td class="bg2"></td>
											<td class="t1"><html:text property="title" style="width:99%;" maxlength="50"/></td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>설문기간</b></td>
											<td class="bg2"></td>
											<td class="t1"><html:text property="strdt" readonly="true" size="10" style="text-align:center;"/>&nbsp;<a href="javascript:Calendar_D(document.forms[0].strdt)"><img src="/images/common/icon_date.gif" align="absmiddle" alt="달력"></a>&nbsp;~
																			<html:text property="enddt" readonly="true" size="10" style="text-align:center;"/>&nbsp;<a href="javascript:Calendar_D(document.forms[0].enddt)"><img src="/images/common/icon_date.gif" align="absmiddle" alt="달력"></a></td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">설문담당자</td>
											<td class="bg2"></td>											
											<td class="t1">
												<logic:notEmpty name="researchForm" property="coldeptnm">
													<b><font color="#FF5656">[<bean:write name="researchForm" property="coldeptnm"/>]</font></b>&nbsp;<bean:write name="researchForm" property="chrgusrnm"/>
													<logic:notEmpty name="researchForm" property="coldepttel">
														&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="researchForm" property="coldepttel"/>
													</logic:notEmpty>
												</logic:notEmpty>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">설문개요</td>
											<td class="bg2"></td>
											<td class="t1" height="100"><html:textarea property="summary" rows="6" style="width:99%;" onkeyup="maxlength_check(this, 2000)"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>결과공개여부</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:radio property="exhibit" value="1" style="border:0;background-color:transparent;"/>결과공개&nbsp;&nbsp;&nbsp;
												<html:radio property="exhibit" value="2" style="border:0;background-color:transparent;"/>결과비공개
											</td>
										</tr>
										<tr <% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>style="display:none"<% } %>>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr <% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>style="display:none"<% } %>>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>설문범위</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>
				                	<html:radio property="range" value="1" style="border:0;background-color:transparent;" onclick="showOpenType('s')"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
				               	<% } else { %>
					    					 	<logic:notEqual name="researchForm" property="mdrchgrpno" value="0">
					                	<html:hidden name="researchForm" property="range"/>
					                	<html:radio property="range" value="1" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                	<html:radio property="range" value="2" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%>
					                	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
															(<html:radio property="rangedetail" value="21" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","21")%>
															<html:radio property="rangedetail" value="22" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","22")%>
															<html:radio property="rangedetail" value="23" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","23")%>
															<html:radio property="rangedetail" value="24" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","24")%>)
														<% } %>
					                </logic:notEqual>                
					                <logic:equal name="researchForm" property="mdrchgrpno" value="0">
					                	<logic:equal name="researchForm" property="mode" value="choice">
					                		<html:radio property="range" value="1" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                	<html:radio property="range" value="2" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%>
						                	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
																(<html:radio property="rangedetail" value="21" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","21")%>
																<html:radio property="rangedetail" value="22" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","22")%>
																<html:radio property="rangedetail" value="23" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","23")%>
																<html:radio property="rangedetail" value="24" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","24")%>)
															<% } %>
					                	</logic:equal>
					                	<logic:notEqual name="researchForm" property="mode" value="choice">
						                	<html:radio property="range" value="1" style="border:0;background-color:transparent;" onclick="showOpenType(1)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                	<html:radio property="range" value="2" style="border:0;background-color:transparent;" onclick="showOpenType(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%>
						                	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
																(<html:radio property="rangedetail" value="21" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","21")%>
																<html:radio property="rangedetail" value="22" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","22")%>
																<html:radio property="rangedetail" value="23" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","23")%>
																<html:radio property="rangedetail" value="24" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","24")%>)
															<% } %>
														</logic:notEqual>
					                </logic:equal> 
				                <% } %>
												</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>중복답변체크</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:radio property="duplicheck" value="0" style="border:0;background-color:transparent;"/>없음&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="duplicheck" value="1" style="border:0;background-color:transparent;"/>쿠키&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="duplicheck" value="2" style="border:0;background-color:transparent;"/>아이디&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												목표응답수 : <html:text property="limitcount" size="5" style="text-align:center"/>&nbsp;명<font color="#FF5656">&nbsp;('0'명은 제한없음)</font>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr id="opentype1" style="display:block">
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>조사방법</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:radio property="opentype" value="1" style="border:0;background-color:transparent;" onclick="showTgtType('s')"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("012","1")%>&nbsp;&nbsp;&nbsp;
												<html:radio property="opentype" value="2" style="border:0;background-color:transparent;" onclick="showTgtType('h')"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("012","2")%>
											</td>
										</tr>
										<tr id="opentype2" style="display:block">
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr id="tgtdept1" style="display:block">
											<td width="100" class="s1" rowspan="2"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">설문대상</td>
											<td class="bg2" rowspan="2"></td>
											<td class="t1" height="20"><font color="#FF5656">※ 지정하지 않으면 <%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%> 전체를 대상으로 합니다.</font></td>
										</tr>
										<tr id="tgtdept2" style="display:block">
											<td class="t1" height="25">
												<html:text property="tgtdeptnm" readonly="true" style="width:87%;" styleId="tgtdeptnm"/><script for="tgtdeptnm" event="onpropertychange">maxlength_check(tgtdeptnm, 2000)</script>
												<a href="javascript:showModalPopup('/commrchdept.do?rchgrpno=<%=rchgrpno %>',782,503,0,0)"><img src="/images/common/btn_select3.gif" align="absmiddle" alt="설문대상지정"></a>
											</td>												
										</tr>
										<tr id="tgtdept3" style="display:block">
											<td colspan="3" class="bg1"></td>
										</tr>
										<% if ( nexti.ejms.common.appInfo.isUsrdept() ) { %>
											<tr id="tgtdept4" style="display:block">
												<td width="100" class="s1" rowspan="2"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">기타대상(직급)</td>
												<td class="bg2" rowspan="2"></td>
												<td class="t1" height="20"><font color="#FF5656">※ 지정하지 않으면 직급 전체를 대상으로 합니다.</font></td>
											</tr>
											<tr id="tgtdept5" style="display:block">
												<td class="t1" height="25">
													<html:text property="othertgtnm" readonly="true" style="width:87%;"/>
													<a href="javascript:showModalPopup('/commrchothertarget.do?rchgrpno=<%=rchgrpno %>',680,402,0,0)"><img src="/images/common/btn_select5.gif" align="absmiddle" alt="대상지정"></a>
												</td>												
											</tr>
											<tr id="tgtdept6" style="display:block">
												<td colspan="3" class="bg1"></td>
											</tr>
										<% } else { %>
											<tr id="tgtdept4" style="display:block"><td colspan="3"></td></tr>
											<tr id="tgtdept5" style="display:block"><td colspan="3"></td></tr>
											<tr id="tgtdept6" style="display:block"><td colspan="3"></td></tr>
										<% } %>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>설문지정</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:hidden property="rchnolist"/>
												<html:text property="rchname" readonly="true" style="width:87%;"/>
													<logic:notEqual name="researchForm" property="mdrchgrpno" value="0">
														<a href="javascript:showModalPopup('/commrchgrpchoice.do?rchgrpno=<%=rchgrpno %>',680,402,0,0)"><img src="/images/common/btn_select6.gif" align="absmiddle" alt="설문지정"></a>
													</logic:notEqual>
													<logic:equal name="researchForm" property="mdrchgrpno" value="0">
														<a href="javascript:if(confirm('새 설문조사그룹 작성시 저장 후 설문지정을 할 수 있습니다.\n\n저장하시겠습니까?')){check_grpcomplete(document.forms[0],'temp')}"><img src="/images/common/btn_select6.gif" align="absmiddle" alt="설문지정"></a>
													</logic:equal>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="list_bg2"></td>
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
<table width="840" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" rowspan="2">&nbsp;</td>
		<td>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="stitle2"><img src="/images/common/dot_04.gif" align="absmiddle" alt="">&nbsp;머리말</td>
				</tr>
				<tr>
					<td><html:textarea property="headcont" rows="5" style="width:100%;" onkeyup="maxlength_check(this, 1000)"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table width="840" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" rowspan="2">&nbsp;</td>
		<td>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="stitle2"><img src="/images/common/dot_04.gif" align="absmiddle" alt="">&nbsp;꼬리말</td>
				</tr>
				<tr>
					<td><html:textarea property="tailcont" rows="5" style="width:100%;" onkeyup="maxlength_check(this, 1000)"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
</table>
<table width="840" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" rowspan="2">&nbsp;</td>
		<td>
			<table width="820" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<logic:notEqual name="researchForm" property="mdrchgrpno" value="0">
						<td>
							<a href="javascript:mst_grpdelete(document.forms[0])"><img src="/images/common/btn_poll_del.gif" alt="설문삭제"></a>
							<a href="javascript:close_grpcheck('<bean:write name="researchForm" property="mdrchgrpno"/>', '<bean:write name="researchForm" property="range"/>')"><img src="/images/common/btn_poll_complete.gif" alt="설문완료"></a>
						</td>
					</logic:notEqual>
					<td align="right">
							<a href="javascript:check_grppreview(document.forms[0])"><img src="/images/common/btn_view3.gif" alt="미리보기"></a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:check_grpcomplete(document.forms[0],'temp')"><img src="/images/common/btn_temp.gif" alt="임시저장"></a>
							<a href="javascript:check_grpcomplete(document.forms[0],'comp')"><img src="/images/common/btn_reg_save.gif" alt="저장하기"></a>
							<a href="javascript:gogrplist()"><img src="/images/common/btn_b_list.gif" alt="목록보기"></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
</table>
</html:form>
<script language="javascript">
setSearchDate();	//기간 유효성체크 이벤트 세팅
document.body.scrollTop = <bean:write name="researchForm" property="posscroll"/>;
if ( <bean:write name="researchForm" property="range"/> == '2' ) {
	showOpenType(2);
}
</script>   
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>