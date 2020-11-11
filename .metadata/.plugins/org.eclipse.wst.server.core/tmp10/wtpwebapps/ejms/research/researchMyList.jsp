<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 내설문조사목록
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
	String basePath = nexti.ejms.common.appInfo.getWeb_address();
	String outsideUrl = nexti.ejms.common.appInfo.getOutsideweburl();
%>

<%
	if ( "수원3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		String user_id = (String)session.getAttribute("user_id");
		String delivery_yn = nexti.ejms.user.model.UserManager.instance().getUserInfo(user_id).getDelivery_yn();
		delivery_yn = nexti.ejms.util.Utils.nullToEmptyString(delivery_yn);
		
		if ( !("Y".equals(delivery_yn) || "001".equals((String)session.getAttribute("isSysMgr"))) ) {
			pageContext.setAttribute("수원3740000", "style='display:none'"); 
		}
	}
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/research.js"></script>

<script type="text/javascript">
menu5.onmouseover();
menu55.onmouseover();

function copy_clip(strData, rchno){
	try {
		var result = false;
		if ( strData == "research.do") {
			result = clipboardData.setData("Text", '<%=basePath%>'+strData+'?rchno='+rchno);
		} else {
			result = clipboardData.setData("Text", '<%=outsideUrl%>'+strData+'?rchno='+rchno);
		}
		if ( result == true ) {
			alert("URL이 복사되었습니다.\n\n원하는 곳에 붙여넣기 하세요.");
		} else {
			throw "";
		}
	} catch (exception) {
		try {
			var winName = String(new Date().getMilliseconds());
			var msg = "사용중인 웹브라우저로 설문주소복사 기능을 사용할 수 없습니다.<br>아래 주소를 복사하세요.(Ctrl+C)<p>";
			var width = 500;
			var height = 100;
			var left = (screen.width - width) / 2;
			var top = (screen.height - height) / 2;
			var option =
				"width=" + width + ", height="  + height + ", left=" + left + ", top=" + top + "," +
				"location=no, menubar=no, resizable=no, scrollbars=no, status=no, " +
				"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";
	
			if(strData == "research.do"){
				window.open("about:blank",winName,option).document.write(msg + '<%=basePath%>'+strData+'?rchno='+rchno);
			} else {
				window.open("about:blank",winName,option).document.write(msg + '<%=outsideUrl%>'+strData+'?rchno='+rchno);
			}
		} catch (exception) {}
	}
}	

function rchmake(){
	location.href = "researchView.do";
}

</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/researchMyList" method="POST">
<html:hidden property="sch_old_deptcd"/>
<html:hidden property="sch_old_userid"/>
<html:hidden property="initentry"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_08.gif" alt=""></td>
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
                          	<% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
														<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="55"><font color="#4F4F4F">설문형태</font></td>
                            <td width="85"> 
                              <html:select property="groupyn" style="width:75px;background-color:white" onchange="submit();">
                              	<html:option value="">전체</html:option>
				                        <html:option value="N">일반설문</html:option>
				                        <html:option value="Y">그룹설문</html:option>
			                        </html:select>
                            </td>
                            <% } %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">제목</font></td>
                            <td width="110"><html:text property="sch_title" style="width:100;background-color:white"/></td>
                					  <% if (session.getAttribute("isSysMgr").equals("001")){ %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">부서</font></td>
                            <td width="150"><html:text property="sch_deptnm" style="width:150;background-color:white" onkeydown="sch_old_deptcd.value=''"/></td>
                            <td width="25"><img src="/images/common/search_ico.gif" width="21" height="19" style="cursor:hand" onclick="fTree(document.forms[0], '<%=request.getAttribute("user_gbn")%>', '<%=request.getAttribute("orgid")%>')" alt=""></td>
                           	<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="45"><font color="#4F4F4F">담당자</font></td>
                            <td width="90"><html:text property="sch_usernm" style="width:80;background-color:white" onkeydown="sch_old_userid.value=''"/></td>
                            <% } %>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="getSearch(document.forms[0],'<%=session.getAttribute("isSysMgr")%>')" align="absmiddle" alt="">&nbsp;</td>
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
                      <td colspan="7" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
										</tr> 
										<tr>
								      <td width="50" class="list_t" align="center">연번</td>
								      <td class="list_t" align="center">설문조사제목</td>
								      <td width="70" class="list_t" align="center">설문기간</td>
								      <td width="100" class="list_t" align="center"><% if (nexti.ejms.common.appInfo.isOutside()){ %>설문범위<% } %></td>
								      <td width="80" class="list_t" align="center" ondblclick="prompt('외부망 설문조사 리스트','<%=outsideUrl%>outsideRchList.do?uid=tester&resident=y&committee=y')">설문주소</td>
								      <td width="70" class="list_t" align="center">-</td>
								      <td width="70" class="list_t" align="center">-</td>
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
								<logic:notEmpty name="researchForm" property="listrch">
								  <logic:iterate id="list" name="researchForm" property="listrch" indexId="i">
		     					<input type="hidden" name="sch_deptcd<%=i.intValue()%>" value="<bean:write name="list" property="sch_deptcd"/>">
		     					<input type="hidden" name="sch_userid<%=i.intValue()%>" value="<bean:write name="list" property="sch_userid"/>">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
                      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s"><a class="list_s2" href="/researchChoice.do?rchno=<bean:write name="list" property="rchno"/>&range=<bean:write name="list" property="range"/>&originuserid=<bean:write name="list" property="chrgusrid"/>"><bean:write name="list" property="title"/></a></td>
								      <td align="center" class="list_s">
											<logic:greaterEqual name="list" property="tday" value="0">
			              		<bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/>
			              	</logic:greaterEqual>
			              	<logic:lessThan name="list" property="tday" value="0">
			              		<font color="color:ff0000">종료</font>
			              	</logic:lessThan>
											</td>
								      <td align="center" class="list_s">
								      <% if (nexti.ejms.common.appInfo.isOutside()){ %>
								      	<logic:equal name="list" property="range" value="1">
								      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
								      	</logic:equal>
								      	<logic:equal name="list" property="range" value="2">
								      		<bean:define id="rangedetail" name="list" property="rangedetail"/>
								      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", "2")%>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)
								      	</logic:equal>
								      <% } %>
								      </td>
								      <td align="center" class="list_s">
								      	<logic:notEqual name="list" property="groupyn" value="Y">
													<logic:equal name="list" property="range" value="1">
			                 			<a class="list_s2" href="javascript:copy_clip('research.do','<bean:write name="list" property="rchno"/>')">설문주소복사</a>
			                   	</logic:equal>
			                   	<logic:notEqual name="list" property="range" value="1">
			                   		<a class="list_s2" href="javascript:copy_clip('outsideRchView.do','<bean:write name="list" property="rchno"/>')">설문주소복사</a>
			                   	</logic:notEqual>
	                   		</logic:notEqual>
											</td>
								      <td align="center" class="list_s"><a class="list_s2" href="javascript:showPopup('/researchPreview.do?rchno=<bean:write name="list" property="rchno"/>', 700, 680, 0, 0)">미리보기</a></td>
								      <td align="center" class="list_s"><a class="list_s2" href="javascript:showModalPopup('/researchResult.do?rchno=<bean:write name="list" property="rchno"/>&range=<bean:write name="list" property="range"/>', 700, 680, 0, 0)">결과보기</a></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="researchForm" property="listrch">
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
                <td height="30" align="right" valign="bottom"><br>
                	<% if (request.getAttribute("initentry").equals("second")){ %>
									<a href="/researchMyList.do"><img src="/images/common/btn_myresearch.gif" border="0" alt="내설문조사 가기"></a>
                	<% } else { %>
									<a href="/researchGetUsed.do" <%=pageContext.getAttribute("수원3740000")%>><img src="/images/common/btn_poll.gif" border="0" alt="기존 설문조사 가져오기"></a>
									<a href="/researchWrite.do" <%=pageContext.getAttribute("수원3740000")%>><img src="/images/common/btn_new_poll.gif" border="0" alt="새 설문조사 작성"></a>
									<% } %>
								</td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="initentry" name="researchForm" property="initentry"/>
                	<bean:define id="groupyn" name="researchForm" property="groupyn"/>
                	<bean:define id="sch_title" name="researchForm" property="sch_title"/>
                	<bean:define id="sch_old_deptcd" name="researchForm" property="sch_old_deptcd"/>
                	<bean:define id="sch_old_userid" name="researchForm" property="sch_old_userid"/>
                	<bean:define id="sch_deptnm" name="researchForm" property="sch_deptnm"/>
                	<bean:define id="sch_usernm" name="researchForm" property="sch_usernm"/>
                	<%=nexti.ejms.util.commfunction.procPage_AddParam("/researchMyList.do","groupyn="+(String)groupyn+"&initentry="+(String)initentry+"&sch_title="+(String)sch_title+"&sch_old_deptcd="+(String)sch_old_deptcd+"&sch_old_userid="+(String)sch_old_userid+"&sch_deptnm="+(String)sch_deptnm+"&sch_usernm="+(String)sch_usernm,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
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