<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합문서정보 수정
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
%>

<bean:define id="docstate" name="commCollDocInfoForm" property="docstate"/>
<%
String procImg = "";
if (docstate.equals("02") || docstate.equals("03")) {
	procImg = "/images/common/step_02.gif";
} else if (docstate.equals("04") || docstate.equals("05")) {
	procImg = "/images/common/step_03.gif";
}
%>

<jsp:include page="/include/header_user.jsp" flush="true"/>

<script src="/script/prototype.js"></script>
<script src="/script/collproc.js"></script>
<script src="/script/Calendar.js"></script>
<script>
menu1.onmouseover();
menu11.onmouseover();

function save_submit(){

	var doctitle = document.commCollDocInfoForm.doctitle;
	if(doctitle.value.trim() == "") {
		doctitle.focus();
		alert("제목을 입력하세요");
		return;
	}	
	document.forms[0].action += '?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=<%=request.getParameter("frmScrollTop")%>';
	document.forms[0].submit();
}

function cancel_submit(){
	location.href='/collprocInfoView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=<%=request.getParameter("frmScrollTop")%>';
}
</script>

<html:form action="/collprocInfoSave.do" method="post" enctype="multipart/form-data">
<html:hidden property="sysdocno"/>
<html:hidden property="fileseq"/>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_05.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_01_over.gif" width="94" height="41" alt="취합문서정보""></td>
                            <td width="94"><img src="/images/collect/s_m_02.gif" width="94" height="41" alt="취합양식자료" style="cursor:hand" onclick="viewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=<%=request.getParameter("frmScrollTop")%>')"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon">
                           	<%  if( docstate.equals("06")){ %>
					                  	<img src="/images/common/btn_release.gif" alt="마감해제" style="cursor:hand" onclick="chkCancelProc1(<%=sysdocno%>)">
						                <%  } else if( docstate.equals("05")|| docstate.equals("04")){ %>
						                	<img src="/images/common/btn_end.gif" alt="취합마감" style="cursor:hand" onclick="popup_open(<%=sysdocno%>)">
						                <%	} else if( docstate.equals("02") || docstate.equals("03")){ %>        	
						                  <img src="/images/common/btn_cancel2.gif" alt="취합문서발송취소" style="cursor:hand" onclick="chkCancelProc(<%=sysdocno%>, <%=formseq%>, '<%=formkind%>', formFrame.document.body.scrollTop, 'collprocdataview')">
						                <%  } %>
                              <img src="/images/common/btn_list.gif" width="86" height="24" alt="목록보기" style="cursor:hand" onclick="location.href='/collprocList.do?docstate=0'">
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
              <tr><!--처리현황 테이블 시작-->
                <td>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제목</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="doctitle" style="width:98%"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">자료기준일</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1">
                      	<html:text property="basicdate" readonly="true" style="width:65;text-align:center"/>
												<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].basicdate)">
											</td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합문서번호</td>
                      <td class="bg2"></td>
                      <td class="t1" width="350"><bean:write name="commCollDocInfoForm" property="docno"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합문서발송일자</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1"><bean:write name="commCollDocInfoForm" property="submitdt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합담당자</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
	                      <bean:write name="commCollDocInfoForm" property="coldeptnm"/>
                      	<% if (!"0".equals(request.getAttribute("chrgunit_cnt"))){ %>
												<html:select property="chrgunitcd" style="width:120px">
													<jsp:useBean id="chgunitselect" class="nexti.ejms.list.form.ChgUnitListForm">
														<bean:define id="coldeptcd" name="commCollDocInfoForm" property="coldeptcd"/>
														<jsp:setProperty name="chgunitselect" property="dept_id" value="<%=(String)coldeptcd%>"/>
														<jsp:setProperty name="chgunitselect" property="all_fl" value="ALL"/>
														<jsp:setProperty name="chgunitselect" property="title" value="==선택=="/>
													</jsp:useBean>
													<html:optionsCollection name="chgunitselect" property="beanCollection"/>
			                 	</html:select>&nbsp;
			                 	<% } else { %>
	                      <bean:write name="commCollDocInfoForm" property="chrgunitnm"/>
	                      <% } %>
		                    <bean:write name="commCollDocInfoForm" property="chrgusrnm"/>&nbsp;&nbsp;문의전화 :<bean:write name="commCollDocInfoForm" property="coldepttel"/>
		                    <html:hidden property="coldeptcd"/><html:hidden property="coldeptnm"/><html:hidden property="chrgusrnm"/>
											</td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">관련근거</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><html:text property="basis" style="width:98%;"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합개요</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><html:textarea property="summary" rows="5" style="width:98%;height:100"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">마감시한</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
												<html:text property="enddt_date" readonly="true" style="width:65;"/>
												<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].enddt_date)">&nbsp;
												<html:select property="enddt_hour" style="width:40px">
													<jsp:useBean id="timehourselect" class="nexti.ejms.list.form.TimeListForm">
														<jsp:setProperty name="timehourselect" property="type" value="HOUR"/>
														<jsp:setProperty name="timehourselect" property="gap" value="1"/>
													</jsp:useBean>
													<html:optionsCollection name="timehourselect" property="beanCollection"/>
		                 		</html:select>&nbsp;시&nbsp;
	                      <html:select property="enddt_min"  style="width:40px">
													<jsp:useBean id="timeminselect" class="nexti.ejms.list.form.TimeListForm">
														<jsp:setProperty name="timeminselect" property="type" value="MINUTE"/>
														<jsp:setProperty name="timeminselect" property="gap" value="10"/>
													</jsp:useBean>
													<html:optionsCollection name="timeminselect" property="beanCollection"/>
		                 		</html:select>&nbsp;분&nbsp;&nbsp;&nbsp;마감알림말&nbsp;<html:text property="endcomment" maxlength="50" style="width:355;"/>
						          </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합대상</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<bean:define id="targetdeptnm" name="commCollDocInfoForm" property="targetdeptnm"/>
                      	<input type=text readonly style="width:85%;" name="tgtdeptnm" value="<%=targetdeptnm%>">	
                      	<img align="absmiddle" src="/images/common/btn_select_modify.gif" alt="취합대상변경" style="cursor:hand" onclick="showModalPopup('/commsubdept.do?sysdocno=<%=sysdocno%>',782, 503,0,0)">
                      </td>
                    </tr>
                    <tr style="display:none"> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr style="display:none"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제출자료전결</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<html:radio property="sancrulecd" value="01" style="border:0;background-color:transparent;"/>담당자전결&nbsp;&nbsp;
	  	                	<html:radio property="sancrulecd" value="02" style="border:0;background-color:transparent;"/>담당전결&nbsp;&nbsp;
		                  	<html:radio property="sancrulecd" value="03" style="border:0;background-color:transparent;"/>부서장전결
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">기타옵션</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><html:checkbox property="openinput" value="Y" style="border:0;background-color:transparent;"/>입력시 타부서 자료보기</td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr  valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<input type="file" id="attachFile" name="attachFile" style="width:72%" contentEditable="false"><input type="button" onclick="resetFile('attachFile')" style="height:18;" value="첨부취소">&nbsp;
												<logic:notEmpty name="commCollDocInfoForm" property="originfilenm">
													<input type="checkbox" name="attachFileYN" style="border:0;background-color:transparent;" value="N">첨부파일삭제
													<br>첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="commCollDocInfoForm" property="filenm"/>&fileName=<bean:write name="commCollDocInfoForm" property="originfilenm"/>"><bean:write name="commCollDocInfoForm" property="originfilenm"/></a>
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--처리현황 테이블 끝 -->
              <tr> 
                <td height="50" align="right" valign="bottom">
                	<img src="/images/common/btn_b_save.gif" alt="저장하기" style="cursor:hand" onclick="save_submit()">&nbsp;
                	<img src="/images/common/btn_b_cancel.gif" alt="취소하기" style="cursor:hand" onclick="cancel_submit()">
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
    <td width="2" valign="top"><img src="<%=procImg%>" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>
</html:form>
<script>
setSearchDate();	//기간 유효성체크 이벤트 세팅
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>