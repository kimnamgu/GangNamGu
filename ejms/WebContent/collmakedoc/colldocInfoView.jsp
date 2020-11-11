<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합문서정보
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

<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/Calendar.js"></script>
<script language="javascript">
menu1.onmouseover();
menu11.onmouseover();

	function resetFile(objName) {
		document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
	}

	function setSearchDate() {
		var frm = document.forms[0];
		var a = frm.basicdate;			//기간시작
		var b = frm.enddt_date;			//기간종료
		
		if (a.onpropertychange == null) {
			a.onpropertychange = setSearchDate;
			b.onpropertychange = setSearchDate;
		} else if (event.propertyName.toLowerCase() == "value") {
			var src = event.srcElement;
			if (src == a) {
				if (a.value > b.value) {
					b.onpropertychange = null;
					b.value = a.value;
					b.onpropertychange = setSearchDate;
				}
			} else if (src == b) {
				if (a.value > b.value || a.value == "") {
					a.onpropertychange = null;
					a.value = b.value;
					a.onpropertychange = setSearchDate;
				}
			}
		}
	}
	
	function click_move(actionpath) {
		document.location.href = actionpath;
	}

	function click_submit(actionpath) {
		document.forms[0].action = actionpath;
		document.forms[0].submit();
	}
	
	function click_save(actionpath, savemode) {
		var mode = document.forms[0].mode;
		var doctitle = document.forms[0].doctitle;
		var chrgunitcd = document.forms[0].chrgunitcd;
		
		//1:새로저장, 2:새문서로 저장, 3:저장, 4:저장하고사본생성, 5:임시저장, 6:저장
		//mode=5는 새로작성 중인 문서를 임시저장(목록에서 안보임)하기 위해 사용
		//mode=6은 수정 중인 문서를 새문서로저장 가능 하도록 하기 위해 사용(결재용)
		if(savemode == "MAKE") {
			mode.value = 1;
		} else if(savemode == "NEWSAVE") {
			mode.value = 2;
		} else if(savemode == "SAVE") {
			mode.value = 3;
		}

		if(doctitle.value.trim() == "") {
			alert("제목을 입력하세요.");
			doctitle.focus();
			return false;
		}
		
//	if(chrgunitcd.value == "") {
//		alert("작성자 담당을 선택하세요.");
//		chrgunitcd.focus();
//		return;
//	}
		
		click_submit(actionpath);
		return true;
	}
	
	function click_formview() {
		var mode = document.forms[0].mode;
		
		if(mode.value == 1) {
			mode.value = 5;
			if(click_save("/colldocSave.do?action=TEMPFORMVIEW") == false) {
				mode.value = 1;
			}
		} else if(mode.value == 2) {
			mode.value = 6;
			if(click_save("/colldocSave.do?action=FORMVIEW") == false) {
				mode.value = 2;
			}
		} else if(mode.value == 3) {
			click_save("/colldocSave.do?action=FORMVIEW");
		}
	}
	
	function click_apprep() {
		var doctitle = document.forms[0].doctitle;
		var chrgunitcd = document.forms[0].chrgunitcd;
		var enddt_date = document.forms[0].enddt_date;	//읽기전용
		var tgtdeptnm = document.forms[0].tgtdeptnm;
		var sancusrnm1 = document.forms[0].sancusrnm1;
		var sancusrnm2 = document.forms[0].sancusrnm2;
		var mode = document.forms[0].mode;
		var formcount = document.forms[0].formcount;
		
		if(doctitle.value.trim() == "") {
			alert("제목을 입력하세요.");
			doctitle.focus();
			return;
		}
		
//		if(chrgunitcd.value == "") {
//			alert("작성자 담당을 선택하세요.");
//			chrgunitcd.focus();
//			return;
//		}
		
		if(enddt_date.value.trim() == "") {
			alert("마감시한를 지정하세요.");
			return;
		}
		
		if(tgtdeptnm.value.trim() == "") {
			alert("취합대상을 지정하세요.");
			return;
		}
		
		if(sancusrnm1.value.trim() != "" && sancusrnm2.value.trim() == "") {
			alert("검토자 지정시 승인자도 지정하여야 합니다.");
			return;
		}
		
		if(formcount.value == 0) {
			alert("양식이 등록되어 있지 않습니다.");
			if(mode.value == 1) {
				mode.value = 5;
				if(click_save("/colldocSave.do?action=TEMPFORMVIEW") == false) {
					mode.value = 1;
				}
			} else if(mode.value == 2) {
				mode.value = 6;
				if(click_save("/colldocSave.do?action=FORMVIEW") == false) {
					mode.value = 2;
				}
			} else if(mode.value == 3) {
				click_save("/colldocSave.do?action=FORMVIEW");
			}
			return;
		}
				
		if(sancusrnm1.value.trim() == "" && sancusrnm2.value.trim() == "") {
//			if(confirm("결재선 지정없이 담당자 전결로 처리 하시겠습니까?") == false) {
//				return;
//			}
		}
		
		if(confirm("취합문서발송을 하시겠습니까?") == true) {
			if(mode.value == 2) {
				document.forms[0].failmode.value = 2;
				mode.value = 4;
				if(click_save("/colldocSave.do?action=APPREP") == false) {
					mode.value = 2;
				}
			} else if(mode.value == 3) {
				document.forms[0].failmode.value = 3;
				mode.value = 4;
				if(click_save("/colldocSave.do?action=APPREP") == false) {
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
<html:form action="/colldocInfoView" method="post" enctype="multipart/form-data">
<html:hidden property="mode"/>
<html:hidden property="sysdocno"/>
<html:hidden property="fileseq"/>
<html:hidden property="formcount"/>
<input type="hidden" name="failmode" value="0">
<script>
setTimeout(function() {
	if (document.forms[0].failmode.value != 0) {
		document.forms[0].mode.value = document.forms[0].failmode.value;
		document.forms[0].failmode.value = 0;
	}
}, 0);
</script>
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
                            <td width="94"><img src="/images/collect/s_m_01_over.gif" width="94" height="41" alt="취합문서정보"></td>
                            <td width="94"><img src="/images/collect/s_m_02_2.gif" width="94" height="41" alt="취합양식자료" style="cursor:hand" onclick="click_formview()"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;"><img src="/images/common/btn_report.gif" width="106" height="24" alt="취합문서발송" style="cursor:hand" onclick="click_apprep()"> 
                              <img src="/images/common/btn_list.gif" width="86" height="24" alt="목록보기" style="cursor:hand" onclick="click_move('/colldocList.do')"></td>
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
                  <!--새취합문서 작성하기 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>제목</b></td>
                      <td class="bg2"></td>
                      <td class="t1" width="390"><html:text property="doctitle" maxlength="50" style="width:95%"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">자료기준일</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="basicdate" readonly="true" style="width:65;text-align:center"/>
												<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].basicdate)">
											</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합담당자</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><b><font color="#FF5656"><bean:write name="colldocForm" property="coldeptnm"/></font></b>&nbsp;
                      	<% if (!"0".equals(request.getAttribute("chrgunit_cnt"))){ %>
		                  	<html:select property="chrgunitcd" style="width:120px">
													<jsp:useBean id="chgunitselect" class="nexti.ejms.list.form.ChgUnitListForm">
														<bean:define id="coldeptcd" name="colldocForm" property="coldeptcd"/>
														<jsp:setProperty name="chgunitselect" property="dept_id" value="<%=(String)coldeptcd%>"/>
														<jsp:setProperty name="chgunitselect" property="all_fl" value="ALL"/>
														<jsp:setProperty name="chgunitselect" property="title" value="담당단위없음"/>
													</jsp:useBean>
													<html:optionsCollection name="chgunitselect" property="beanCollection"/>
			                 	</html:select>&nbsp;
			                 	<% } %>
		                    <bean:write name="colldocForm" property="chrgusrnm"/>&nbsp;&nbsp;문의전화 :<bean:write name="colldocForm" property="d_tel"/>
		                    <html:hidden property="coldeptcd"/><html:hidden property="coldeptnm"/><html:hidden property="chrgusrnm"/><html:hidden property="d_tel"/>
                    	</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">관련근거</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:text property="basis" maxlength="100" style="width:98%;"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr  valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">취합개요</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1" height="110"><html:textarea property="summary" onkeyup="maxlength_check(this, 2000)" style="width:98%;height:100"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>마감시한</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:text property="enddt_date" readonly="true" style="width:65;"/>
												<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].enddt_date)">&nbsp;
												<html:select property="enddt_hour"  style="width:40px">
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
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>취합대상</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:text property="tgtdeptnm" readonly="true" style="width:85%" styleId="tgtdeptnm"/><script for="tgtdeptnm" event="onpropertychange">maxlength_check(tgtdeptnm, 2000)</script>
                        <img src="/images/common/btn_select.gif" width="80" height="19" align="absmiddle" alt="취합대상지정" style="cursor:hand" onclick="click_popup('/commsubdept.do?sysdocno=' + sysdocno.value, 782, 503)"> 
                      </td>
                    </tr>
                    <tr style="display:none"> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr style="display:none">
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>제출자료전결</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:radio property="sancrule" value="01" style="border:0;background-color:transparent;"/>담당자전결&nbsp;&nbsp;
                  			<html:radio property="sancrule" value="02" style="border:0;background-color:transparent;"/>담당전결&nbsp;&nbsp;
                  			<html:radio property="sancrule" value="03" style="border:0;background-color:transparent;"/>부서장전결
                  		</td>
                    </tr>
										<tr style="display:none"> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr style="display:none"> 
                      <td colspan="7" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">결재선지정하기 
                        <img src="/images/common/btn_click.gif" width="39" height="13" align="absmiddle" alt="결재선지정하기" style="cursor:hand"
                        		onclick="sancInfo.style.display = (sancInfo.style.display == 'none') ? 'block' : 'none';"> 
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">기타옵션</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:checkbox property="openinput" value="Y" style="border:0;background-color:transparent;"/>입력시 타부서 자료보기</td>
                    </tr>
                    <tr>
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1">
                      	<input type="file" id="attachFile" name="attachFile" style="width:72%" contentEditable="false"><input type="button" onclick="resetFile('attachFile')" style="height:18;" value="첨부취소">&nbsp;
												<logic:notEmpty name="colldocForm" property="originfilenm">
													<input type="checkbox" name="attachFileYN" style="border:0;background-color:transparent;" value="N">첨부파일삭제
													<br>첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="colldocForm" property="filenm"/>&fileName=<bean:write name="colldocForm" property="originfilenm"/>"><bean:write name="colldocForm" property="originfilenm"/></a>
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                  </table>
                  <!--새취합문서 작성하기 테이블-->
                </td>
              </tr>
              <tr> 
                <td> 
                  <!--결재선 지정 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0" id="sancInfo" style="display:none">
                    <tr> 
                      <td width="60" class="s1" rowspan="3"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">결재선</td>
                      <td class="bg2" width="1" rowspan="3"></td>
                      <td width="48" class="s1">승 인</td>
                      <td class="bg2" width="1" rowspan="3"></td>
                      <td class="t1"><html:text property="sancusrnm2" readonly="true" style="width:99%"/></td>
                      <td class="bg2" rowspan="3" width="1"></td>
                      <td rowspan="3" align="left" width="90">&nbsp;<img src="/images/common/btn_select2.gif" width="74" height="47" align="absmiddle" alt="결재선지정" style="cursor:hand" onclick="click_popup('/commapproval/designate.do?isMaking=1&type=1&sysdocno=' + sysdocno.value, 680, 402)"></td>
                    </tr>
                    <tr> 
                      <td width="48" class="bg1"></td>
                      <td class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="48" class="s1">검 토</td>
                      <td class="t1"><html:text property="sancusrnm1" readonly="true" style="width:99%"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                  </table>
                  <!--결재선 지정 테이블-->
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
              <tr> 
                <td align="right"> 
                  <!--저장버튼-->
                  <logic:equal name="colldocForm" property="mode" value="1">
										<img src="/images/common/btn_temp.gif" alt="임시저장" style="cursor:hand" onclick="click_save('/colldocSave.do?action=INFOVIEW', 'MAKE')">
	                </logic:equal>
	                <logic:equal name="colldocForm" property="mode" value="2">
	                	<img src="/images/common/btn_new_file.gif" alt="새문서로저장" style="cursor:hand" onclick="click_save('/colldocSave.do?action=INFOVIEW', 'NEWSAVE')">&nbsp;&nbsp;
	                	<img src="/images/common/btn_temp.gif" alt="임시저장" style="cursor:hand" onclick="click_save('/colldocSave.do?action=INFOVIEW', 'SAVE')">
	                </logic:equal>
	                <logic:equal name="colldocForm" property="mode" value="3">
	                	<img src="/images/common/btn_temp.gif" alt="임시저장" style="cursor:hand" onclick="click_save('/colldocSave.do?action=INFOVIEW', 'SAVE')">
	                </logic:equal>
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
<script>
setSearchDate();	//기간 유효성체크 이벤트 세팅
document.forms[0].doctitle.focus();
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>