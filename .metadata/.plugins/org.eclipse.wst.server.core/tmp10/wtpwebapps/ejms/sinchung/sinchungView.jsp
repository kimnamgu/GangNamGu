<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 작성
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr"%>
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

<script src="/script/sinchung.js"></script>
<script src="/script/Calendar.js"></script>
<script> 
menu6.onmouseover();
menu66.onmouseover();
</script>

<html:form method="POST" action="/formSave" enctype="multipart/form-data">	
<html:hidden property="reqformno"/>
<input type="hidden" name="mode">
<input type="hidden" name="delseq">
<input type="hidden" name="posscroll">
<html:hidden property="frsqs"/>
<html:hidden property="exsqs"/>
<table width="865" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20">&nbsp;</td>
		<td width="843" valign="top">
			<table width="843" border="0" cellspacing="0" cellpadding="0">
				<!--타이틀-->
				<tr>
					<td background="/images/common/title_bg.gif"><logic:greaterThan name="sinchungForm" property="reqformno" value="0"><img src="/images/collect/title_13.gif" alt=""></logic:greaterThan><logic:equal name="sinchungForm" property="reqformno" value="0"><img src="/images/collect/title_35.gif" alt=""></logic:equal></td>
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
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>신청기간</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:text property="strdt" readonly="true" size="10" style="text-align:center;"/>&nbsp;<a href="javascript:Calendar_D(document.forms[0].strdt)"><img src="/images/common/icon_date.gif" align="absmiddle" alt="달력"></a>&nbsp;~
												<html:text property="enddt" readonly="true" size="10" style="text-align:center;"/>&nbsp;<a href="javascript:Calendar_D(document.forms[0].enddt)"><img src="/images/common/icon_date.gif" align="absmiddle" alt="달력"></a>&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="limitfl" style="border:0;background-color:transparent;" onclick="unlimiteTerm()" <logic:notPresent name="sinchungForm" property="enddt">checked</logic:notPresent>>제한없음
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">신청담당자</td>
											<td class="bg2"></td>											
											<td class="t1">
												<html:hidden property="coldeptcd"/>
			                	<html:hidden property="coldeptnm"/>
			                	<html:hidden property="chrgusrid"/>
			                	<html:hidden property="chrgusrnm"/>											
												<logic:notEmpty name="sinchungForm" property="coldeptnm">
													<b><font color="#FF5656">[<bean:write name="sinchungForm" property="coldeptnm"/>]</font></b>&nbsp;<bean:write name="sinchungForm" property="chrgusrnm"/>
													<logic:notEmpty name="sinchungForm" property="coltel">
														&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="sinchungForm" property="coltel"/>
													</logic:notEmpty>
												</logic:notEmpty>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">신청개요</td>
											<td class="bg2"></td>
											<td class="t1" height="100"><html:textarea property="summary" rows="6" style="width:99%;" onkeyup="maxlength_check(this, 2000)"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>첨부그림보기</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:radio property="imgpreview" value="1" style="border:0;background-color:transparent;"/>항상보기&nbsp;&nbsp;&nbsp;
												<html:radio property="imgpreview" value="2" style="border:0;background-color:transparent;"/>미리보기 클릭
											</td>
										</tr>
										<tr <% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>style="display:none"<% } %>>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr <% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>style="display:none"<% } %>>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>신청범위</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<% if ( !nexti.ejms.common.appInfo.isOutside() ) { %>
				                	<html:radio property="range" value="1" style="border:0;background-color:transparent;"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
				               	<% } else { %>
					    					 	<logic:greaterThan name="sinchungForm" property="reqformno" value="0"> 
					                	<html:hidden name="sinchungForm" property="range"/>
					                	<html:radio property="range" value="1" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                	<html:radio property="range" value="2" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%>
					                	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
						                	(<html:radio property="rangedetail" value="21" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","21")%>
															<html:radio property="rangedetail" value="22" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","22")%>
															<html:radio property="rangedetail" value="23" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","23")%>
															<html:radio property="rangedetail" value="24" style="border:0;background-color:transparent;" disabled="true"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","24")%>)
														<% } %>
					                </logic:greaterThan>   
				                	<logic:equal name="sinchungForm" property="reqformno" value="0">
					                	<html:radio property="range" value="1" style="border:0;background-color:transparent;" onclick="showOpenType(1)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                	<html:radio property="range" value="2" style="border:0;background-color:transparent;" onclick="showOpenType(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%>
					                	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
						                	(<html:radio property="rangedetail" value="21" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","21")%>
															<html:radio property="rangedetail" value="22" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","22")%>
															<html:radio property="rangedetail" value="23" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","23")%>
															<html:radio property="rangedetail" value="24" style="border:0;background-color:transparent;" onclick="showOpenTypeDetail(2)"/><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","24")%>)
														<% } %>
					                </logic:equal> 
				                <% } %>
												</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>중복신청체크</b></td>
											<td class="bg2"></td>
											<td class="t1">
												<html:radio property="duplicheck" value="0" style="border:0;background-color:transparent;"/>없음&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="duplicheck" value="1" style="border:0;background-color:transparent;"/>쿠키&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="duplicheck" value="2" style="border:0;background-color:transparent;"/>아이디&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												목표신청수 : <html:text property="limitcount" size="5" style="text-align:center"/>&nbsp;명<font color="#FF5656">&nbsp;('0'명은 제한없음)</font>
											</td>
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<logic:notEqual name="sinchungForm" property="range" value="2">
											<tr style="display:none">
												<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>신청시<br>&nbsp;&nbsp;&nbsp;결재필요여부</b></td>
												<td class="bg2"></td>
												<td class="t1"><html:checkbox name="sinchungForm" property="sancneed" style="border:0;background-color:transparent;" value="Y"/>결재하여 신청해야 함</td>
											</tr>
											<tr style="display:none">
												<td colspan="3" class="bg1"></td>
											</tr>
										</logic:notEqual>
										<tr>
											<td width="100" class="s1" rowspan="2"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>입력기본정보</b></td>
											<td class="bg2" rowspan="2"></td>
											<td class="t1" height="20"><font color="#FF5656">※ 입력받을 기본정보를 선택하여 주세요.</font></td>
										</tr>
										<tr>
											<td class="t1" height="25">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
				                	<bean:define id="btype" name="sinchungForm" property="basictype"/>
				                  <%=nexti.ejms.util.commfunction.basicSet(btype.toString()) %>
				             		</table>
											</td>												
										</tr>
										<tr>
											<td colspan="3" class="bg1"></td>
										</tr>
										<tr>
											<td width="100" class="s1" rowspan="2"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>추가입력문항</b></td>
											<td class="bg2" rowspan="2"></td>
											<td class="t1" height="20"><font color="#FF5656">※ 기본정보 외 추가로 입력받을 문항의 개수를 입력하세요.</font></td>
										</tr>
										<tr>
											<td class="t1" height="25">
												<logic:notEqual name="sinchungForm" property="sumcnt" value="0">
													<html:text property="acnt" maxlength="2" size="5" style="text-align:center" readonly="true"/>&nbsp;문항&nbsp;&nbsp;&nbsp;
													(보기&nbsp;<html:text property="examcount" maxlength="2" size="2" style="text-align:center"  readonly="true"/>&nbsp;개)
	                    	</logic:notEqual>
	                    	<logic:equal name="sinchungForm" property="sumcnt" value="0">
													<html:text property="acnt" maxlength="2" size="5" style="text-align:center" onkeydown="key_num();"/>&nbsp;문항&nbsp;&nbsp;&nbsp;
													(보기&nbsp;<html:text property="examcount" maxlength="2" size="2" style="text-align:center" onkeydown="key_num();"/>&nbsp;개)&nbsp;&nbsp;&nbsp;
													<a href="javascript:check_make(document.forms[0])"><img src="/images/common/btn_make.gif" align="absmiddle" alt="문항만들기"></a>
	                    	</logic:equal>
											</td>												
										</tr>
										<tr>
											<td colspan="3" class="list_bg2"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="25"><logic:greaterThan name="sinchungForm" property="reqformno" value="0"><font color="#6335D8">※ 현재 신청건수(미결재) : <%=request.getAttribute("cnt2")%> (<%=request.getAttribute("cnt1")%>) 건 ☞ 신청건수가 있을 경우 질문문항 수정이 제한됩니다.</font></logic:greaterThan></td>
							</tr>
							<tr>
								<td>
									<table width="820" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<logic:greaterThan name="sinchungForm" property="reqformno" value="0">
												<td>
													<a href="javascript:del_check('<bean:write name="sinchungForm" property="reqformno"/>')"><img src="/images/common/btn_file_del.gif" alt="양식삭제"></a>
													<a href="javascript:close_check('<bean:write name="sinchungForm" property="reqformno"/>','<bean:write name="sinchungForm" property="range"/>')"><img src="/images/common/btn_file_end.gif" alt="신청마감"></a>
												</td>
												<td align="right">
													<a href="javascript:check_preview(document.forms[0])"><img src="/images/common/btn_view3.gif" alt="미리보기"></a>&nbsp;&nbsp;&nbsp;
													<a href="javascript:check_complete(document.forms[0],'temp')"><img src="/images/common/btn_temp.gif" alt="임시저장"></a>
													<a href="javascript:check_complete(document.forms[0],'comp')"><img src="/images/common/btn_reg_save.gif" alt="저장하기"></a>
													<a href="javascript:golist()"><img src="/images/common/btn_b_list.gif" alt="목록보기"></a>
												</td>
											</logic:greaterThan>
											<logic:equal name="sinchungForm" property="reqformno" value="0">
												<td align="right">											
													<a href="javascript:check_preview(document.forms[0])"><img src="/images/common/btn_view3.gif" alt="미리보기"></a>&nbsp;&nbsp;&nbsp;
													<a href="javascript:check_complete(document.forms[0],'temp')"><img src="/images/common/btn_temp.gif" alt="임시저장"></a>
													<a href="javascript:check_complete(document.forms[0],'comp')"><img src="/images/common/btn_reg_save.gif" alt="저장하기"></a>
													<a href="javascript:cancel()"><img src="/images/common/btn_b_cancel.gif" alt="취소하기"></a>
												</td>
											</logic:equal>
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
<logic:notEmpty name="sinchungForm" property="articleList">
<bean:define id="examcount" name="sinchungForm" property="examcount" type="java.lang.Integer"/>
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
	<tr>
		<td height="20"></td>
	</tr>
</table>
<table width="840" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" rowspan="2">&nbsp;</td>
		<td>
			<table width="820" border="0" cellspacing="1" cellpadding="0" class="bg5">
				<tr>
					<td valign="top" class="bg4">
						<table width="790" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF">
							<tr>
								<td height="5"></td>
							</tr>
	  					<logic:iterate id="list" name="sinchungForm" property="articleList" indexId="idx">
	  						<html:hidden name="list" property="formseq"/>
								<tr>
									<td>
										<table width="770" border="0" cellspacing="0" cellpadding="0" align="center">
											<tr>
												<td class="ptext2" width="80" align="center">
													<select name="changeFormseq" style="width:95%;" oldvalue="<%=idx.intValue()+1%>" onchange="changeFormseqCheck(this);" <logic:greaterThan name="sinchungForm" property="sumcnt" value="0">disabled</logic:greaterThan>>
														<logic:iterate id="fcnt" name="sinchungForm" property="articleList" indexId="cnt">
															<option value="<%=cnt.intValue()+1%>" <% if (cnt.intValue()+1 == idx.intValue()+1){out.print("selected");}%>>[입력 <%=cnt.intValue()+1%>]</option>
														</logic:iterate>
													</select>
												</td>
												<td class="ptext2" width="690">
													<html:text name="list" property="formtitle" style="width:79%" maxlength="1000"/>
													<input type="checkbox" id="formtitleFileYN[<%=idx.intValue()%>]" name="formtitleFileYN[<%=idx.intValue()%>]" style="border:0;background-color:transparent;" onclick="showFileUpload('formtitleFile', '<%=idx.intValue()%>', 'formtitle', <%=idx.intValue()%>); if(this.checked==false){resetFile('formtitleFile[<%=idx.intValue()%>]')};" value="<%=idx.intValue()%>" <logic:notEmpty name="list" property="originfilenm">checked</logic:notEmpty>>파일첨부
													<logic:lessEqual name="list" property="formtype" value="2">
														<span id="req<%=idx.intValue()%>" style="visibility:hidden">
                       		</logic:lessEqual>
                        	<logic:greaterEqual name="list" property="formtype" value="3">
                        		<span id="req<%=idx.intValue()%>" style="visibility:visible">
                        	</logic:greaterEqual>
                          </span>
                          	<input type="checkbox" id="require_<%=idx.intValue()%>" name="require" style="border:0;background-color:transparent;" value="<%=idx.intValue()%>" <logic:equal name="list" property="require" value="Y">checked</logic:equal>>필수응답
													<logic:empty name="list" property="originfilenm">
														<div id="formtitleFile<%=idx.intValue()%>" style="display:none">
															<input type="file" id="formtitleFile[<%=idx.intValue()%>]" name="formtitleFile[<%=idx.intValue()%>]" style="width:79%" onchange="checkFileType(this);" contentEditable="false"><input type="button" onclick="resetFile('formtitleFile[<%=idx.intValue()%>]')" style="height:18;" value="첨부취소">
													</logic:empty>
													<logic:notEmpty name="list" property="originfilenm">
														<div id="formtitleFile<%=idx.intValue()%>" style="display:block">
															<input type="file" id="formtitleFile[<%=idx.intValue()%>]" name="formtitleFile[<%=idx.intValue()%>]" style="width:79%" onchange="checkFileType(this);" contentEditable="false"><input type="button" onclick="resetFile('formtitleFile[<%=idx.intValue()%>]')" style="height:18;" value="첨부취소"><br>
															첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a>
															<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%>, 'imageView<%=idx.intValue()%>', '<%=basePath%><bean:write name="list" property="filenm"/>', 50, 50)"><u>미리보기</u></a>
															<div id="imagePreview<%=idx.intValue()%>" style="position:absolute;display:none;z-index:999">
																<table id="imagePreview<%=idx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																	<tr align="center" valign="middle">
																		<td><iframe id="imagePreview<%=idx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																	</tr>
																</table>
																<table id="imagePreview<%=idx.intValue()%>_t2" border="1" bordercolor="gray" style="position:absolute;">
																	<tr align="center" valign="middle">
																		<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%>)"><img id="imageView<%=idx.intValue()%>" alt="클릭하시면 닫힘니다"></a></td>
																	</tr>
																</table>
															</div>
													</logic:notEmpty>
													</div>
												</td>
											</tr>
											<tr style="padding-bottom:5">
												<td class="ptext2" align="center">
													<logic:equal name="sinchungForm" property="sumcnt" value="0">
														<a href="javascript:check_delete(document.forms[0], '<%=idx.intValue()+1%>', '<bean:write name="list" property="formseq"/>')"><img src="/images/common/btn_s_delete.gif" align="absmiddle" alt="질문삭제"></a>
													</logic:equal>
												</td>
												<td class="ptext2">
													<logic:greaterThan name="sinchungForm" property="sumcnt" value="0">
														<input type="hidden" name="formtype[<%=idx.intValue()%>]" value="<bean:write name="list" property="formtype"/>">
														<input type="radio" style="border:0;background-color:transparent;" value="1" disabled <logic:equal name="list" property="formtype" value="1">checked</logic:equal>>단일선택형&nbsp;&nbsp;&nbsp;
														<input type="radio" style="border:0;background-color:transparent;" value="2" disabled <logic:equal name="list" property="formtype" value="2">checked</logic:equal>>복수선택형&nbsp;&nbsp;&nbsp;
														<input type="radio" style="border:0;background-color:transparent;" value="3" disabled <logic:equal name="list" property="formtype" value="3">checked</logic:equal>>단답형&nbsp;&nbsp;&nbsp;
														<input type="radio" style="border:0;background-color:transparent;" value="4" disabled <logic:equal name="list" property="formtype" value="4">checked</logic:equal>>장문형&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</logic:greaterThan>
													<logic:equal name="sinchungForm" property="sumcnt" value="0">
														<input type="radio" name="formtype[<%=idx.intValue()%>]" style="border:0;background-color:transparent;" value="1" onclick="showSwtichLayer('<%=idx.intValue()%>', '1')" <logic:equal name="list" property="formtype" value="1">checked</logic:equal>>단일선택형&nbsp;&nbsp;&nbsp;
														<input type="radio" name="formtype[<%=idx.intValue()%>]" style="border:0;background-color:transparent;" value="2" onclick="showSwtichLayer('<%=idx.intValue()%>', '2')" <logic:equal name="list" property="formtype" value="2">checked</logic:equal>>복수선택형&nbsp;&nbsp;&nbsp;
														<input type="radio" name="formtype[<%=idx.intValue()%>]" style="border:0;background-color:transparent;" value="3" onclick="showSwtichLayer('<%=idx.intValue()%>', '3')" <logic:equal name="list" property="formtype" value="3">checked</logic:equal>>단답형&nbsp;&nbsp;&nbsp;
														<input type="radio" name="formtype[<%=idx.intValue()%>]" style="border:0;background-color:transparent;" value="4" onclick="showSwtichLayer('<%=idx.intValue()%>', '4')" <logic:equal name="list" property="formtype" value="4">checked</logic:equal>>장문형&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</logic:equal>									
													<logic:lessEqual name="list" property="formtype" value="2">
                       			<span id="secu<%=idx.intValue()%>" style="visibility:hidden"><input type="checkbox" id="security" name="security" style="border:0;background-color:transparent;" value="<%=idx.intValue()%>" <logic:equal name="list" property="security" value="Y">checked</logic:equal>>보안처리</span>
                       		</logic:lessEqual>                        		
                         	<logic:greaterEqual name="list" property="formtype" value="3">
                         		<span id="secu<%=idx.intValue()%>" style="visibility:visible"><input type="checkbox" id="security" name="security" style="border:0;background-color:transparent;" value="<%=idx.intValue()%>" <logic:equal name="list" property="security" value="Y">checked</logic:equal>>보안처리</span>
                         	</logic:greaterEqual>
<%--연동 추가 START --%>
							<% nexti.ejms.sinchung.model.ArticleBean SinchungForm = (nexti.ejms.sinchung.model.ArticleBean)list; %>
		                      	<% 
		                      		 int ex_frsq = SinchungForm.getEx_frsq(); 
		                      		 String ex_exsq = SinchungForm.getEx_exsq(); 		         
		                      		String [] array_ex_exsq  = SinchungForm.getEx_exsq().split("_");
		                      	%>
		                      	연계질문 <select name="exFrsq_<bean:write name="list" property="formseq" />" id="exFrsq_<bean:write name="list" property="formseq" />" class="exFrsq ex_<%=idx.intValue()%>"  onchange="examSearch1(this, '<%=idx.intValue()%>');" />
								<option value="0" >질문</option>
								<logic:equal name="list" property="formtype" value="1">								
								<logic:iterate id="fcnt" name="sinchungForm" property="articleList" indexId="cnt">
								<%if (cnt.intValue()+1 < idx.intValue()+1){ %>
									<logic:equal name="fcnt" property="formtype" value="01">
										<option value="<%=cnt.intValue()+1%>" <% if (cnt.intValue()+1 == ex_frsq){out.print("selected");}%>>질문 <%=cnt.intValue()+1%></option>
									</logic:equal>
								<%}%>
								</logic:iterate>
								</logic:equal>
							</select>
							
                         	<select name="exExsq_<bean:write name="list" property="formseq" />" id="exExsq_<bean:write name="list" property="formseq" />" <%if(idx.intValue() == 0){ %> size="1" <%} %> multiple="multiple" class="exExsq ex_<%=idx.intValue()%>" onchange="examSearch2(this);" >
                         	<option value="0" >보기</option>
                         	<logic:equal name="list" property="formtype" value="1">
                         	<% if (idx.intValue() > 0){%>
                         	<%
                         	for(int eidx1 = 1 ; eidx1 <= examcount.intValue() ; eidx1++ ){                         		
                         	%>
                          <%-- <logic:iterate id="elist" name="sinchungForm" property="articleList" indexId="eidx1">  --%>
                         <option value="<%=eidx1%>" 
                         <% 
                         	for(int exi = 0 ; exi < array_ex_exsq.length ; exi++){
                         		if(Integer.parseInt(array_ex_exsq[exi]) == (eidx1)){
                         			out.print("selected");
                         			break;
                         		}
                         	}
                         %>
                         >보기<%=eidx1%></option>
                        <%--  </logic:iterate> --%>
                     	<%} %>    	
                     	
                     	<%} %>    	
                     	</logic:equal>
                         	</select>
                         	
<%-- 연동 추가 END --%>
												</td>
											</tr>
										</table>
										<logic:lessEqual name="list" property="formtype" value="2">
											<div id="sample<%=idx.intValue()%>" style="display:block">
										</logic:lessEqual>
										<logic:greaterEqual name="list" property="formtype" value="3">
											<div id="sample<%=idx.intValue()%>" style="display:none">
										</logic:greaterEqual>
											<table width="770" border="0" cellspacing="0" cellpadding="0" align="center">
												<logic:iterate id="elist" name="list" property="sampleList" indexId="eidx"> 
													<tr style="padding-bottom:5">
														<td class="ptext2" width="80" align="center">보기<%=eidx.intValue()+1%></td>
														<td class="ptext2" width="690">
															<html:text name="elist" property="examcont" style="width:89%" maxlength="1000"/>&nbsp;<input type="checkbox" id="examcontFileYN[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" name="examcontFileYN[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" style="border:0;background-color:transparent;" onclick="showFileUpload('examcontFile', '<%=idx.intValue()%><%=eidx.intValue()%>', 'examcont', <%=idx.intValue() * examcount.intValue() + eidx.intValue()%>); if(this.checked==false){resetFile('examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]')};" value="<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>" <logic:notEmpty name="elist" property="originfilenm">checked</logic:notEmpty>>파일첨부
															<logic:empty name="elist" property="originfilenm">
																<div id="examcontFile<%=idx.intValue()%><%=eidx.intValue()%>" style="display:none">
																	<input type="file" id="examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" name="examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" style="width:79%" onchange="checkFileType(this);" contentEditable="false"><input type="button" onclick="resetFile('examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]')" style="height:18;" value="첨부취소">
															</logic:empty>
															<logic:notEmpty name="elist" property="originfilenm">
																<div id="examcontFile<%=idx.intValue()%><%=eidx.intValue()%>" style="display:block">
																	<input type="file" id="examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" name="examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]" style="width:79%" onchange="checkFileType(this);" contentEditable="false"><input type="button" onclick="resetFile('examcontFile[<%=idx.intValue() * examcount.intValue() + eidx.intValue()%>]')" style="height:18;" value="첨부취소"><br>
																	첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
																	<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u>미리보기</u></a>
																	<div id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>" style="position:absolute;display:none;z-index:999">
																		<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																			<tr align="center" valign="middle">
																				<td><iframe id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																			</tr>
																		</table>
																		<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t2" border="1" bordercolor="gray" style="position:absolute;">
																			<tr align="center" valign="middle">
																				<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>)"><img id="imageView<%=idx.intValue()%><%=eidx.intValue()%>" alt="클릭하시면 닫힘니다"></a></td>
																			</tr>
																		</table>
																	</div>
																</logic:notEmpty>
															</div>
														</td>
													</tr>
												</logic:iterate>
												<tr>
													<td class="ptext2" align="center">&nbsp;</td>
													<td class="ptext2"><input type="checkbox" id="examtype" name="examtype" style="border:0;background-color:transparent;" value="<%=idx.intValue()%>" <logic:equal name="list" property="examtype" value="Y">checked</logic:equal>>기타 (기타의견을 입력받을 수 있습니다)</td>
												</tr>
											</table>
										</div>
										<logic:lessEqual name="list" property="formtype" value="2">
                   		<div id="addcont<%=idx.intValue()%>" style="display:none">
                   	</logic:lessEqual>
                   	<logic:greaterEqual name="list" property="formtype" value="3">
                   		<div id="addcont<%=idx.intValue()%>" style="display:block">
                   	</logic:greaterEqual>
	                 		<table width="770" border="0" cellspacing="0" cellpadding="0" align="center">
												<tr style="padding-bottom:5">
													<td class="ptext2" width="80" align="center">추가설명</td>
													<td class="ptext2" width="690"><html:text name="list" property="helpexp" style="width:100%;" maxlength="500"/></td>
												</tr>
											</table>
										</div>
										<table width="770" border="0" cellspacing="0" cellpadding="0" align="center">
											<tr>
												<td height="5"></td>
											</tr>
											<tr>
												<td class="pbg"></td>
											</tr>
											<tr>
												<td height="5"></td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:iterate>
							<tr>
								<td height="5"></td>
							</tr>
							<logic:equal name="sinchungForm" property="sumcnt" value="0">
								<tr>
									<td align="right" style="padding:0 10 0 0"><a href="javascript:check_add(document.forms[0])"><img src="/images/common/btn_s_item.gif" alt=""></a></td>
								</tr>
							</logic:equal>
						</table>
					</td><!-- 입력항목 끝 -->
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
					<td align="right">
						<logic:greaterThan name="sinchungForm" property="reqformno" value="0">
							<a href="javascript:check_preview(document.forms[0])"><img src="/images/common/btn_view3.gif" alt="미리보기"></a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:check_complete(document.forms[0],'temp')"><img src="/images/common/btn_temp.gif" alt="임시저장"></a>
							<a href="javascript:check_complete(document.forms[0],'comp')"><img src="/images/common/btn_reg_save.gif" alt="저장하기"></a>
							<a href="javascript:golist()"><img src="/images/common/btn_b_list.gif" alt="목록보기"></a>
						</logic:greaterThan>
						<logic:equal name="sinchungForm" property="reqformno" value="0">
							<a href="javascript:check_preview(document.forms[0])"><img src="/images/common/btn_view3.gif" alt="미리보기"></a>&nbsp;&nbsp;&nbsp;
							<a href="javascript:check_complete(document.forms[0],'temp')"><img src="/images/common/btn_temp.gif" alt="임시저장"></a>
							<a href="javascript:check_complete(document.forms[0],'comp')"><img src="/images/common/btn_reg_save.gif" alt="저장하기"></a>
							<a href="javascript:cancel()"><img src="/images/common/btn_b_cancel.gif" alt="취소하기"></a>
						</logic:equal>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
</table>
</logic:notEmpty>
</html:form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>
<script language="javascript">
setSearchDate();	//기간 유효성체크 이벤트 세팅
document.body.scrollTop = <bean:write name="sinchungForm" property="posscroll"/>;
</script>
<jsp:include page="/include/tail.jsp" flush="true"/>