<%--
 * 작성일: 2013.03.04
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리 시행실적보기
 * 설명:
--%>
<!-- 본문 내용 중 <%-- --%>로 된 주석 해제시 일반페이지로 구성됨, 현재 IFRAME용으로 처리됨 -->
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%--
<jsp:include page="/include/header_user.jsp" flush="true"/>
--%>
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/workPlan.js"></script>

<body style="margin: 0, 0, 0, 0">
<%--
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/workplan/title_05.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top">
--%>
            <table width="820" border="0" cellspacing="0" cellpadding="0">
            <html:form action="/workResultSave" method="post" enctype="multipart/form-data">
						<html:hidden property="planno"/>
						<html:hidden property="resultno"/>
              <tr> 
                <td> 
                  <!--상단 선택메뉴 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/workplan/s_m_02.gif" width="94" height="41" alt="시행실적"></td>
                            <td>&nbsp;</td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;">
                            	<logic:equal name="workplanForm" property="mode" value="write">
	                            	<img src="/images/workplan/btn_wrksave.gif" width="86" height="24" alt="실적저장" style="cursor:hand" onclick="workResultSave(document.forms[0])">
	                              <img src="/images/workplan/btn_back.gif" width="86" height="24" alt="돌아가기" style="cursor:hand" onclick="history.back()">
	                          	</logic:equal>
	                          	<logic:notEqual name="workplanForm" property="mode" value="write">
	                          		<bean:define id="auth" type="java.lang.String" value="N"/>
	                          		<bean:define id="uid" type="java.lang.String" name="workplanForm" property="chrgusrcd"/>
	                          		<logic:equal name="user_id" value="<%=uid%>" scope="session">
	                          		  <bean:define id="auth" type="java.lang.String" value="Y"/>
	                          		</logic:equal>
	                          		<logic:equal name="isSysMgr" value="001" scope="session">
	                          			<bean:define id="auth" type="java.lang.String" value="Y"/>
	                          		</logic:equal>
	                          		<logic:equal name="auth" value="Y">
	                          			<img src="/images/workplan/btn_wrkmod.gif" width="86" height="24" alt="실적수정" style="cursor:hand" onclick="location.href='/workResultView.do?planno=<bean:write name="workplanForm" property="planno"/>&resultno=<bean:write name="workplanForm" property="resultno"/>&mode=write'">
	       	                    		<img src="/images/workplan/btn_wrkdel.gif" width="86" height="24" alt="실적삭제" style="cursor:hand" onclick="workResultDelete('/workResultDelete.do?planno=<bean:write name="workplanForm" property="planno"/>&resultno=<bean:write name="workplanForm" property="resultno"/>')">
	                          		</logic:equal>
	                          	</logic:notEqual>
	                          	<img src="/images/workplan/btn_list.gif" width="86" height="24" alt="목록보기" style="cursor:hand" onclick="parent.parent.back2list()">
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
              <tr> 
                <td> 
                  <!--시행실적등록 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</td>
                      <td class="bg2"></td>
                      <td class="t1" width="715">
                      	<logic:equal name="workplanForm" property="mode" value="write">
													<html:text property="title" style="width:99%;"/>
												</logic:equal>
												<logic:notEqual name="workplanForm" property="mode" value="write">
													<bean:write name="workplanForm" property="title"/>
												</logic:notEqual>
                    	</td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">작 성 자</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<bean:write name="workplanForm" property="upperdeptnm"/>&nbsp;&nbsp;
                      	<bean:write name="workplanForm" property="deptnm"/>&nbsp;&nbsp;
                      	<logic:equal name="workplanForm" property="mode" value="write">
	                      	<logic:notEqual name="workplanForm" property="chrgunitcd" value="-1">
				                  	<html:select property="chrgunitcd" style="width:120px">
															<jsp:useBean id="chgunitselect" class="nexti.ejms.list.form.ChgUnitListForm">
																<bean:define id="deptcd" name="workplanForm" property="deptcd"/>
																<jsp:setProperty name="chgunitselect" property="dept_id" value="<%=(String)deptcd%>"/>
																<jsp:setProperty name="chgunitselect" property="all_fl" value="ALL"/>
																<jsp:setProperty name="chgunitselect" property="title" value="담당단위없음"/>
															</jsp:useBean>
															<html:optionsCollection name="chgunitselect" property="beanCollection"/>
					                 	</html:select>&nbsp;&nbsp;
	                      	</logic:notEqual>
	                      </logic:equal>
	                      <logic:notEqual name="workplanForm" property="mode" value="write">
	                      	<logic:notEmpty name="workplanForm" property="chrgunitnm">
	                      		<bean:write name="workplanForm" property="chrgunitnm"/>&nbsp;&nbsp;
	                      	</logic:notEmpty>
	                      </logic:notEqual>
                          <logic:equal name="workplanForm" property="mode" value="write">
                            <html:select property="chrgusrcd" style="width:80px">
                                <jsp:useBean id="chrgusrselect" class="nexti.ejms.list.form.DeptUserListForm">
                                    <bean:define id="deptcd" name="workplanForm" property="deptcd"/>
                                    <jsp:setProperty name="chrgusrselect" property="dept_id" value="<%=(String)deptcd %>"/>
                                    <jsp:setProperty name="chrgusrselect" property="title" value="담당자선택"/>
                                </jsp:useBean>
                                <html:optionsCollection name="chrgusrselect" property="beanCollection"/>
                            </html:select>
                        </logic:equal>
                        <logic:notEqual name="workplanForm" property="mode" value="write">
                            <bean:write name="workplanForm" property="chrgusrnm"/>
                        </logic:notEqual>&nbsp;&nbsp;
                      	<logic:notEmpty name="workplanForm" property="user_tel">
	                      	전화 : <bean:write name="workplanForm" property="user_tel"/>
                      	</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td colspan="3">
                      	<logic:equal name="workplanForm" property="mode" value="write">
													<html:textarea property="content" style="width:100%;"/>
													<script>
														// Automatically calculates the editor base path based on the _samples directory.
														// This is usefull only for these samples. A real application should use something like this:
														// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
														var oFCKeditor = new FCKeditor('content');
														oFCKeditor.ToolbarSet = 'ejms_basic';
														oFCKeditor.Height = 400;
														oFCKeditor.ReplaceTextarea();
													</script>
												</logic:equal>
												<logic:notEqual name="workplanForm" property="mode" value="write">
													<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	                      		<tr>
	                      			<td>
																<div style="width:810; margin:10 10 10 10; overflow:auto;"><bean:write name="workplanForm" property="content" filter="false"/></div>
															</td>
														</tr>
													</table>
												</logic:notEqual>
                    	</td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일1</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<logic:equal name="workplanForm" property="mode" value="write">
	                      	<input type="file" id="attachFile1" name="attachFile1" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile1')" style="height:18;" value="첨부취소">&nbsp;
													<logic:notEmpty name="workplanForm" property="orgfilenm1">
														<br>첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm1"/>&fileName=<bean:write name="workplanForm" property="orgfilenm1"/>"><bean:write name="workplanForm" property="orgfilenm1"/></a>
														<input type="checkbox" name="attachFileYN1" style="border:0;background-color:transparent;" value="Y">첨부파일삭제
													</logic:notEmpty>
												</logic:equal>
												<logic:notEqual name="workplanForm" property="mode" value="write">
													<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm1"/>&fileName=<bean:write name="workplanForm" property="orgfilenm1"/>"><bean:write name="workplanForm" property="orgfilenm1"/></a>
												</logic:notEqual>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일2</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<logic:equal name="workplanForm" property="mode" value="write">
	                      	<input type="file" id="attachFile2" name="attachFile2" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile2')" style="height:18;" value="첨부취소">&nbsp;
													<logic:notEmpty name="workplanForm" property="orgfilenm2">
														<br>첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm2"/>&fileName=<bean:write name="workplanForm" property="orgfilenm2"/>"><bean:write name="workplanForm" property="orgfilenm2"/></a>
														<input type="checkbox" name="attachFileYN2" style="border:0;background-color:transparent;" value="Y">첨부파일삭제
													</logic:notEmpty>
												</logic:equal>
												<logic:notEqual name="workplanForm" property="mode" value="write">
													<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm2"/>&fileName=<bean:write name="workplanForm" property="orgfilenm2"/>"><bean:write name="workplanForm" property="orgfilenm2"/></a>
												</logic:notEqual>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일3</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<logic:equal name="workplanForm" property="mode" value="write">
	                      	<input type="file" id="attachFile3" name="attachFile3" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile3')" style="height:18;" value="첨부취소">&nbsp;
													<logic:notEmpty name="workplanForm" property="orgfilenm3">
														<br>첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm3"/>&fileName=<bean:write name="workplanForm" property="orgfilenm3"/>"><bean:write name="workplanForm" property="orgfilenm3"/></a>
														<input type="checkbox" name="attachFileYN3" style="border:0;background-color:transparent;" value="Y">첨부파일삭제
													</logic:notEmpty>
												</logic:equal>
												<logic:notEqual name="workplanForm" property="mode" value="write">
													<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm3"/>&fileName=<bean:write name="workplanForm" property="orgfilenm3"/>"><bean:write name="workplanForm" property="orgfilenm3"/></a>
												</logic:notEqual>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                  </table>
                  <!--시행실적등록 테이블-->
                </td>
              </tr>
              </html:form>
            </table>
<%--
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</table>
--%>
<br><br>
<script>
try {
	document.forms[0].title.focus();
} catch ( exception ) {}
</script>
</body>
<jsp:include page="/include/tail.jsp" flush="true"/>