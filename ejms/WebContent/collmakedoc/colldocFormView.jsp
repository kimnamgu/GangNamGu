<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합양식자료
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

<script language="javascript">
menu1.onmouseover();
menu11.onmouseover();

	function click_move(actionpath) {
		document.location.href = actionpath;
	}

	function click_submit(actionpath) {
		document.forms[0].action = actionpath;
		document.forms[0].submit();
	}
	
	function click_infoview(actionpath) {
		var mode = document.forms[0].mode;
		var sysdocno = document.forms[0].sysdocno;
		
		click_move(actionpath + "?mode=" + mode.value + "&sysdocno=" + sysdocno.value);
	}

	function click_delete(actionpath) {
		if(confirm("삭제 하시겠습니까?") == true)
			click_submit(actionpath);
	}
	
	function click_apprep(actionpath) {
		var enddt_date = document.forms[0].enddt_date;	//읽기전용
		var tgtdeptnm = document.forms[0].tgtdeptnm;
		var sancusrnm1 = document.forms[0].sancusrnm1;
		var sancusrnm2 = document.forms[0].sancusrnm2;
		var mode = document.forms[0].mode;
		var formcount = document.forms[0].formcount;
	
		if(enddt_date.value.trim() == "") {
			alert("마감시한를 지정하세요.");
			click_infoview("/colldocInfoView.do");
			return;
		}
		
		if(tgtdeptnm.value.trim() == "") {
			alert("취합대상을 지정하세요.");
			click_infoview("/colldocInfoView.do");
			return;
		}
		
		if(sancusrnm1.value.trim() != "" && sancusrnm2.value.trim() == "") {
			alert("검토자 지정시 승인자도 지정하여야 합니다.");
			click_infoview("/colldocInfoView.do");
			return;
		}
		
		if(formcount.value == 0) {
			alert("양식이 등록되어 있지 않습니다.");
			return;
		}

				
		if(sancusrnm1.value.trim() == "" && sancusrnm2.value.trim() == "") {
//			if(confirm("결재선 지정없이 담당자 전결로 처리 하시겠습니까?") == false) {
//				return;
//			}
		}
		
		if(confirm("취합문서발송을 하시겠습니까?") == true) {
			if(mode.value == 2) {
				mode.value = 4;
				if(click_submit("/colldocSave.do?action=APPREP&formapprep=1") == false) {
					mode.value = 2;
				}
			} else if(mode.value == 3) {
				mode.value = 4;
				if(click_submit("/colldocSave.do?action=APPREP&formapprep=1") == false) {
					mode.value = 3;
				}
			}
		}
	}
	
	function click_popup(actionpath, width, height) {
		var left = (screen.width - width) / 2;
		var top = (screen.height - height) / 2;
		var option = 
			"dialogWidth:" + width + "px; " +
			"dialogHeight:" + height + "px; " +
			"dialogLeft:" + left + "px; " +
			"dialogTop:" + top + "px; " +
			"resizable:yes; scroll:yes; status:yes; center:no; " +
			"dialogHide:no; edge:raised; help:no; unadorned:no";

		try {
			window.showModalDialog(actionpath , window, option);
		} catch(exception) {
			alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
						"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
						"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
						"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
			try {processingHide();} catch(ex) {}
			try {parent.processingHide();} catch(ex) {}
		}
	}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/colldocFormView" method="post">
<html:hidden property="mode"/><html:hidden property="sysdocno"/><html:hidden property="formcount"/>
<html:hidden property="enddt_date"/><html:hidden property="tgtdeptnm"/><html:hidden property="sancusrnm1"/><html:hidden property="sancusrnm2"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_01.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_01.gif" width="94" height="41" alt="취합문서정보" style="cursor:hand" onclick="click_infoview('/colldocInfoView.do')"></td>
                            <td width="94"><img src="/images/collect/s_m_02_over_2.gif" width="94" height="41" alt="취합양식자료"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0; visibility: hidden">
                            	<img src="/images/common/btn_report.gif" width="106" height="24" alt="취합문서발송" style="cursor:hand" onclick="click_apprep()"> 
                              <img src="/images/common/btn_list.gif" width="86" height="24" alt="목록보기" style="cursor:hand" onclick="click_move('/colldocList.do')">
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
                  <!--양식 리스트 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="6" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" height="1"></td>
										</tr>
										<tr>
								      <td width="80" class="list_t" align="center">연번</td>
								      <td width="520" class="list_t" align="center">양식제목</td>
								      <td width="120" class="list_t" align="center">양식유형</td>
								      <td width="100" class="list_t" align="center">삭제</td>
								    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>		          	
								<logic:notEmpty name="colldocForm" property="listcolldocform">
								  <logic:iterate id="list" name="colldocForm" property="listcolldocform" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
								      <td align="center" class="list_no"><bean:write name="list" property="seqno"/></td>
								      <td class="list_s" style="padding-left:5px">
											<logic:equal name="list" property="formkind" value="01">
		                  	<a href="#" class="list_s2" onclick="click_popup('/formatLineDef.do?type=5&sysdocno=<bean:write name="colldocForm" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
		                  </logic:equal>
		                  <logic:equal name="list" property="formkind" value="02">
		                  	<a href="#" class="list_s2" onclick="click_popup('/formatFixedDef.do?type=5&sysdocno=<bean:write name="colldocForm" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
		                  </logic:equal>
		                  <logic:equal name="list" property="formkind" value="03">
		                  	<a href="#" class="list_s2" onclick="click_popup('/formatBookDef.do?load=1&type=5&sysdocno=<bean:write name="colldocForm" property="sysdocno"/>&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
		                  </logic:equal>
											</td>
								      <td align="center" class="list_s"><bean:write name="list" property="formkindname"/></td>
								      <td align="center" class="list_s"><img src="/images/common/btn_del.gif" alt="삭제" style="cursor:hand" onclick="click_delete('/formatDel.do?type=1&formseq=<bean:write name="list" property="formseq"/>')"></td>
                    </tr>                  
                    <tr> 
                      <td colspan="4" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="colldocForm" property="listcolldocform">
										<tr>
											<td colspan="4" align="center" class="list_s">검색된 목록이 없습니다</td>
										</tr>
										<tr>
								      <td colspan="4" class="list_bg2"></td>
								    </tr>
								</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
                </td>
              </tr>
              <tr> 
                <td height="30">&nbsp;</td>
              </tr>
              <tr>
                <td height="45" align="right"><img src="/images/common/btn_add2.gif" alt="양식추가" style="cursor:hand" onclick="click_popup('/formatMake.do?formseq=0&sysdocno=<bean:write name="colldocForm" property="sysdocno"/>', 780, 550)"></td>
            	</tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"><img src="/images/common/step_01.gif" style="position:absolute; margin:50 0 0 5; z-index:2;" alt=""></td>
  </tr>
</html:form>
</table>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>