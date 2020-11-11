<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 취합양식자료
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
String enddt = "";
String endcomment = "";
String modifyyn = "0";
String issanc = "";
String submitstate = "";
String processchk = "";
String procImg = "";

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

if(request.getAttribute("enddt") != null) {
	enddt = request.getAttribute("enddt").toString();
}

if(request.getAttribute("endcomment") != null) {
	endcomment = request.getAttribute("endcomment").toString();
}

if(request.getAttribute("modifyyn") != null) {
	modifyyn = request.getAttribute("modifyyn").toString();
}

if(request.getAttribute("issanc") != null) {
	issanc = request.getAttribute("issanc").toString();
}

if(request.getAttribute("submitstate") != null) {
	submitstate = request.getAttribute("submitstate").toString();
}

if(request.getAttribute("processchk") != null) {
	processchk = request.getAttribute("processchk").toString();
}

if (processchk.equals("02")) {
	procImg = "/images/common/step_06.gif";
} else if (processchk.equals("03") || processchk.equals("04")) {
	procImg = "/images/common/step_07.gif";
} else if (processchk.equals("05")) {
	procImg = "/images/common/step_08.gif";
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
function iframeScroll() {
	var frmDoc = formFrame.document;
	if(frmDoc.readyState == "complete") {
		frmDoc.body.scrollTop = parseInt("0<%=(String)request.getParameter("frmScrollTop")%>", 10);
		
		var sh = frmDoc.body.scrollHeight;
		if (sh < formFrameTD.height) {	//양식다섯개
			formFrameTD.height = sh;
		}
	}
}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/inputing/title_04.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_01.gif" width="94" height="41" alt="취합문서정보" style="cursor:hand" onclick="location.href='/inputCompCollDocInfoView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop"></td>
                            <td width="94"><img src="/images/collect/s_m_02_over.gif" width="94" height="41" alt="취합양식자료"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;">
                              <img src="/images/common/btn_list.gif" alt="목록보기" style="cursor:hand" onclick="location.href='/inputCompleteList.do'">
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
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--마감시한/알림말 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="250"><font color="#4F4F4F"><b>마감시한 : </b></font><b><font color="#FF5656"><%=enddt%></font></b></td>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="510"><font color="#4F4F4F"><b>마감알림말 : </b></font><font color="#4F4F4F"><%=endcomment%></font></td>
                          </tr>
                        </table>
                        <!--마감시한/알림말 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top" height="172" id="formFrameTD"><iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="양식목록" onload="iframeScroll()"></iframe></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
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
    <td width="2" valign="top"><img src="<%=procImg%>" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>

<logic:notEmpty name="formatLineForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatLineForm" property="formcomment">
										<bean:define id="formcomment" name="formatLineForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td>
  		<b style="position:absolute;left:350;padding-top:10;color:red;">※ 한글다운로드는 저장만 가능합니다.</b>
	 		<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="엑셀다운로드">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="한글다운로드">
				<logic:equal name="modifyyn" value="1">
					<img src="/images/common/btn_modify.gif" style="cursor:hand;" align="absmiddle" onclick="viewModifyForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=' + formFrame.document.body.scrollTop)" alt="">
				</logic:equal>
			</form>
  	</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
	<tr>
		<td><!-- 양식데이터 나타낼 부분 시작-->
			<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
			<logic:notEmpty name="formatLineForm" property="listform">
				<logic:iterate id="list" name="formatLineForm" property="listform">
					<bean:write name="list" property="formbodyhtml" filter="false" />							                
	  		</logic:iterate>
			</logic:notEmpty>
			<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
		</td><!-- 양식데이터 나타낼 부분 끝-->
	</tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatFixedForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatFixedForm" property="formcomment">
										<bean:define id="formcomment" name="formatFixedForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td>
  		<b style="position:absolute;left:350;padding-top:10;color:red;">※ 한글다운로드는 저장만 가능합니다.</b>
	 		<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="엑셀다운로드">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="한글다운로드">
				<logic:equal name="modifyyn" value="1">
					<img src="/images/common/btn_modify.gif" style="cursor:hand;" align="absmiddle" onclick="viewModifyForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=' + formFrame.document.body.scrollTop)" alt="">
				</logic:equal>
			</form>
  	</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
	<tr>
		<td><!-- 양식데이터 나타낼 부분 시작-->
			<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formbodyhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
		</td><!-- 양식데이터 나타낼 부분 끝-->
	</tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatBookForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatBookForm" property="formcomment">
										<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">제출양식</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:notEmpty name="fileBookForm" property="listfilebook">
										<logic:iterate id="list" name="fileBookForm" property="listfilebook">
											<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
										</logic:iterate>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td>
			<logic:equal name="modifyyn" value="1">
				<img src="/images/common/btn_modify.gif" style="cursor:hand;" onclick="viewModifyForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=' + formFrame.document.body.scrollTop)" alt="">
			</logic:equal>
  	</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
	<tr>
		<td><!-- 양식데이터 나타낼 부분 시작-->
		  <table width="820" border="0" cellspacing="0" cellpadding="0">
		    <tr> 
		      <td colspan="4" class="list_bg4"></td>
		    </tr>
		    <tr> 
		      <td width="200" class="s3">카테고리</td>
		      <td width="250" class="s3">제목</td>
		      <td class="s3">첨부파일</td>
		      <td width="100" class="s3">파일크기</td>
		    </tr>
		    <tr> 
		      <td colspan="4" class="list_bg4"></td>
		    </tr>
		<logic:notEmpty name="dataForm" property="dataList">
     	<logic:iterate id="list" name="dataForm" property="dataList" indexId="i">
				<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'"> 
		      <td class="list_l"><bean:write name="list" property="categorynm"/></td>
		      <td class="list_l"><bean:write name="list" property="formtitle"/></td>
		      <td class="list_l"><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a></td>
		      <td class="list_l" style="padding-right:10;text-align:right"><script>convertSize(<bean:write name="list" property="filesize"/>)</script></td>
		    </tr>
		    <tr> 
		      <td colspan="4" class="list_bg4"></td>
		    </tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="dataForm" property="dataList">
				<tr>
					<td class="list_l" colspan="6">검색된 목록이 없습니다</td>
				</tr>
				<tr> 
		      <td colspan="4" class="list_bg4"></td>
		    </tr>
		</logic:empty>
			</table>
		</td><!-- 양식데이터 나타낼 부분 끝-->
	</tr>
</table><br>
</logic:notEmpty>

<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>