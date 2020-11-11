<%--
 * 작성일: 2013.03.04
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리 시행실적목록
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

<html:messages id="msg" message="true">
<script><bean:write name="msg" filter="false"/></script>
</html:messages>

<body style="margin: 0, 0, 0, 0">
<table width="830" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td id="workFrameTD2">
			<iframe id="workFrame2" name="workFrame2" width="100%" height="100%" frameborder="0" scrolling="auto" title="목록" onload="frameResize('workFrameTD2', 'workFrame2');parent.frameResize('workFrameTD1', 'workFrame1');"></iframe>
		</td>
	</tr>
</table>

<%--
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/workplan/title_04.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top">
--%>
          	<table width="820" border="0" cellspacing="0" cellpadding="0">
          	<html:form action="/workResultList" method="post">
						<html:hidden property="planno"/>
              <tr>
              	<td align="right"></td>
              </tr>
<%--
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF">
                        <!--리스트 검색 테이블 시작-->
                        <table width="820" border="0" cellspacing="0" cellpadding="0">
			                    <tr>
														<td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>사 업 명</b></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><bean:write name="workplanForm" property="title"/></td>
			                    </tr>
			                    <tr> 
			                      <td colspan="3" class="bg1"></td>
			                    </tr>
			                    <tr> 
			                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>담 당 자</b></td>
			                      <td class="bg2"></td>
			                      <td class="t1" width="715">
			                      	<bean:write name="workplanForm" property="deptnm"/>&nbsp;&nbsp;
			                      	<logic:notEmpty name="workplanForm" property="chrgunitnm">
			                      		<bean:write name="workplanForm" property="chrgunitnm"/>&nbsp;&nbsp;
			                      	</logic:notEmpty>
			                      	<bean:write name="workplanForm" property="chrgusrnm"/>&nbsp;&nbsp;
			                      	<logic:notEmpty name="workplanForm" property="user_tel">
				                      	전화 : <bean:write name="workplanForm" property="user_tel"/>
			                      	</logic:notEmpty>
			                      </td>
			                    </tr>
			                    <tr> 
			                      <td colspan="3" class="bg1"></td>
			                    </tr>
			                    <tr> 
			                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>사업기간</b></td>
			                      <td class="bg2"></td>
			                      <td class="t1"><bean:write name="workplanForm" property="strdt"/> ~ <bean:write name="workplanForm" property="enddt"/></td>
			                    </tr>
			                  </table>
                        <!--리스트 검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
--%>
              <tr> 
                <td class="result">* 총 <font class="result_no"><bean:write name="totalCount"/>건</font>이 검색되었습니다</td>
              </tr>
              <tr> 
                <td> 
                  <!--검색 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="6" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" height="1"></td>
										</tr> 
										<tr>
								    	<td width="50" class="list_t" align="center">번호</td>
								      <td class="list_t" align="center">실적요약</td>
								      <td width="70" class="list_t" align="center">작성자</td>
								      <td width="80" class="list_t" align="center">작성일</td>
								      <td width="80" class="list_t" align="center">첨부</td>
								      <td width="50" class="list_t" align="center">조회</td>
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
								<logic:notEmpty name="workplanForm" property="listWork">
								  <logic:iterate id="list" name="workplanForm" property="listWork" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
                      <td align="center" class="list_no"><bean:write name="list" property="seqno" format="#,###"/><html:hidden name="list" property="resultno"/></td>
								      <td class="list_s" style="padding-left:5px"><a class="list_s2" target="workFrame2" href="/workResultView.do?planno=<bean:write name="list" property="planno"/>&resultno=<bean:write name="list" property="resultno"/>"><bean:write name="list" property="title"/></a></td>
								      <td align="center" class="list_s"><bean:write name="list" property="chrgusrnm"/></td>
								      <td align="center" class="list_s"><script>document.write("<bean:write name="list" property="crtdt"/>".substring(0,10))</script></td>
								      <td align="center" class="list_s">
								      	<logic:notEmpty name="list" property="ext1">
								      		<logic:match name="list" property="ext1" value="hwp">
									      		<img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>'"/>
									      	</logic:match>
									      	<logic:match name="list" property="ext1" value="xls">
									      		<img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>'"/>
									      	</logic:match>
									      	<logic:notMatch name="list" property="ext1" value="hwp">
									      		<logic:notMatch name="list" property="ext1" value="xls">
										      		<img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm1"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm1"/>&fileName=<bean:write name="list" property="orgfilenm1"/>'"/>
									      		</logic:notMatch>
									      	</logic:notMatch>
								      	</logic:notEmpty>
								      	<logic:notEmpty name="list" property="ext2">
								      		<logic:match name="list" property="ext2" value="hwp">
									      		<img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>'"/>
									      	</logic:match>
									      	<logic:match name="list" property="ext2" value="xls">
									      		<img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>'"/>
									      	</logic:match>
									      	<logic:notMatch name="list" property="ext2" value="hwp">
									      		<logic:notMatch name="list" property="ext2" value="xls">
										      		<img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm2"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm2"/>'"/>
									      		</logic:notMatch>
									      	</logic:notMatch>
								      	</logic:notEmpty>
								      	<logic:notEmpty name="list" property="ext3">
								      		<logic:match name="list" property="ext3" value="hwp">
									      		<img src="/images/common/file_hwp.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm3"/>'"/>
									      	</logic:match>
									      	<logic:match name="list" property="ext3" value="xls">
									      		<img src="/images/common/file_excel.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm3"/>'"/>
									      	</logic:match>
									      	<logic:notMatch name="list" property="ext3" value="hwp">
									      		<logic:notMatch name="list" property="ext3" value="xls">
										      		<img src="/images/common/file_normal.jpg" alt="<bean:write name="list" property="orgfilenm3"/>" style="cursor:hand" onclick="actionFrame.location.href='/download.do?tempFileName=<bean:write name="list" property="filenm2"/>&fileName=<bean:write name="list" property="orgfilenm3"/>'"/>
									      		</logic:notMatch>
									      	</logic:notMatch>
								      	</logic:notEmpty>
								      </td>
								      <td align="center" class="list_s"><bean:write name="list" property="readcnt" format="#,###"/></td>
                    </tr>
                    <tr> 
                      <td colspan="6" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="workplanForm" property="listWork">
										<tr>
											<td colspan="6" align="center" class="list_s">검색된 목록이 없습니다</td>
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
                <td height="30" valign="middle">
                	<table width="100%" style="display:none">
                		<tr>
                			<td align="right">
                				<bean:define id="auth" type="java.lang.String" value="N"/>
                    		<bean:define id="did" type="java.lang.String" name="workplanForm" property="deptcd"/>
                    		<logic:equal name="dept_code" value="<%=did%>" scope="session">
                    		  <bean:define id="auth" type="java.lang.String" value="Y"/>
                    		</logic:equal>
                    		<logic:equal name="auth" value="Y">
                    			<img src="/images/workplan/btn_wrkadd.gif" width="89" height="26" alt="실적등록" style="cursor:hand" onclick="workFrame2.location.href='/workResultWrite.do?planno=<bean:write name="workplanForm" property="planno"/>'">
                    		</logic:equal>
                			</td>
                		</tr>
                	</table>
                </td>
              </tr>
              <tr>
                <td align="center"><%=nexti.ejms.util.commfunction.procPage("/workResultList.do",(java.util.HashMap)request.getAttribute("searchCondition"),request.getAttribute("totalPage").toString(),request.getAttribute("currPage").toString())%></td>
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
</body>
<jsp:include page="/include/tail.jsp" flush="true"/>