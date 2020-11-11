<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유
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
<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script type="text/javascript" src="/script/exhibit.js"></script>
<script src="/script/Calendar.js"></script>
<script>
menu4.onmouseover();
menu44.onmouseover();

function form_submit() {
	var frm = document.forms[0];
	var searchdoctitle = frm.searchdoctitle;
	var searchformtitle = frm.searchformtitle;
	var searchkey = frm.searchkey;
	var searchchrgusrnm = frm.searchchrgusrnm;
	var searchinputusrnm = frm.searchinputusrnm;

	searchdoctitle.value = searchdoctitle.value.trim();
	searchformtitle.value = searchformtitle.value.trim();
	searchkey.value = searchkey.value.trim();
	searchchrgusrnm.value = searchchrgusrnm.value.trim();
	searchinputusrnm.value = searchinputusrnm.value.trim();

	frm.target = "iframe1";
	frm.submit();
}

function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.searchcrtdtfr;			//문서작성일시작
	var b = frm.searchcrtdtto;			//문서작성일끝
	var c = frm.searchbasicdatefr;	//자료기준일시작
	var d = frm.searchbasicdateto;	//자료기준일끝
	
	if (a.onpropertychange == null) {
		a.onpropertychange = setSearchDate;
		b.onpropertychange = setSearchDate;
		c.onpropertychange = setSearchDate;
		d.onpropertychange = setSearchDate;
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
		} else if (src == c) {
			if (c.value > d.value) {
				d.onpropertychange = null;
				d.value = c.value;
				d.onpropertychange = setSearchDate;
			}
		} else if (src == d) {
			if (c.value > d.value || c.value == "") {
				c.onpropertychange = null;
				c.value = d.value;
				c.onpropertychange = setSearchDate;
			}
		}
	}
}

/**
 * 상세조건을 표시한다.
 */
function divDisplay(){
	var frm = document.forms[0];
	var searchformtitle = frm.searchformtitle;
	var searchkey = frm.searchkey;
	var searchcrtdtfr = frm.searchcrtdtfr;
	var searchcrtdtto = frm.searchcrtdtto;
	var searchbasicdatefr = frm.searchbasicdatefr;
	var searchbasicdateto = frm.searchbasicdateto;
	var searchchrgusrnm = frm.searchchrgusrnm;
	var searchinputusrnm = frm.searchinputusrnm;
	var check_detail = frm.check_detail;

	var div = eval("div_display0.style");
	if(check_detail.checked == true){
		div.display ="block";
		div_display0_tr.style.display="none";
	}else{
		if (searchformtitle.value || searchkey.value
				//|| searchcrtdtfr.value || searchcrtdtto.value
				|| searchbasicdatefr.value || searchbasicdateto.value
				|| searchchrgusrnm.value || searchinputusrnm.value) {
			check_detail.checked = true;
			divDisplay();
			//alert("입력된 상세검색 조건이 있습니다");
			return;
		}
		div.display ="none";
		div_display0_tr.style.display="block";
	}
}

/**
 * 조직도를 표시한다.
 */
function loadFormation(orgid){
	var orggbn = document.forms[0].orggbn.value;
	setUrl("/formation.do");
	setType("DEPT");
	setViewObjectId(formationLayer);
	getFormation(true, orggbn, orgid);
}

/**
 * 콤보박스에서 선택 
 */
function orggbnChange(){
	loadFormation();
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	selectDept(orgid);
}

/**
 * 부서를 선택한다.
 */
function selectDept(deptId){
	var treeFormation = document.getElementsByTagName("div")[2];

	document.forms[0].searchdept.value = deptId;
	//document.forms[0].treescroll.value = treeFormation.scrollTop;

	form_submit();
}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/exhibitIframeList">
<html:hidden property="searchdept"/>
<html:hidden property="treescroll"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/exhibit/title_01.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr valign="top"> 
                <td width="240"> 
                  <table width="220" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="80"><img src="/images/collect/tab_10.gif" width="74" height="28" alt=""></td>
                      <td width="140">
											<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
											<html:select name="exhibitListForm" property="orggbn" style="width:130px" onchange="orggbnChange(document.forms[0])">
												<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
													<bean:define id="user_gbn" name="user_gbn" scope="session"/>
													<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
													<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
													<jsp:setProperty name="orggbn" property="all_fl" value="ALL"/>
												</jsp:useBean>
												<html:optionsCollection name="orggbn" property="beanCollection"/>
											</html:select>&nbsp;
											<% } else { %>
											<html:hidden property="orggbn"/>
											<% } %>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <table width="220" border="0" cellspacing="1" cellpadding="0" height="500" class="bg5">
                          <tr> 
                            <td valign="top"><div id="formationLayer" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto; background-color:#F5F5F5;"></div></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
                <td> 
                  <table width="580" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="28">&nbsp;<font color="red">※ 상세검색에서 문서작성일 미입력시 <script>document.write(new Date().getYear())</script>년을 기준으로 검색됩니다</font></td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="580" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9">
                          <tr> 
                            <td bgcolor="#FFFFFF"> 
                              <!--검색조건 테이블 시작-->
                              <table width="570" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7">
       					  							<tr>
								  								<td height="3" colspan="4" bgcolor="white"></td>
								  							</tr>
												        <tr> 
												          <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												          <td width="70"><font color="#4F4F4F">문서제목</font></td>
												          <td width="215"><html:text property="searchdoctitle" style="width:208;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
												          <td width="260"><img src="/images/common/btn_search.gif" width="42" height="19" align="absmiddle" style="cursor:hand" onclick="form_submit()" alt="">&nbsp;&nbsp;&nbsp;
												          	<html:checkbox property="check_detail" onclick="divDisplay()" style="border:0;background-color:transparent;"/><font color="#4F4F4F">상세검색</font>
												          </td>
												        </tr>
												        <tr id="div_display0_tr">
								  								<td height="3" colspan="4" bgcolor="white"></td>
								  							</tr>
												      </table>
														<div id="div_display0" style="display:none;">
															<table width="570" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7">
												        <tr> 
												          <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												          <td width="70"><font color="#4F4F4F">양식제목</font></td>
												          <td width="475" colspan="2"><html:text property="searchformtitle" style="width:208;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
												        </tr>
												        <tr> 
												          <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												          <td width="70"><font color="#4F4F4F">검색키워드</font></td>
												          <td colspan="2"><html:text property="searchkey" style="width:208;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
												        </tr>
												        <tr> 
												          <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												          <td width="70"><font color="#4F4F4F">문서작성일</font></td>
												          <td colspan="2">
												          	<html:text property="searchcrtdtfr" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchcrtdtfr);">&nbsp;~
												          	<html:text property="searchcrtdtto" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchcrtdtto);">&nbsp;<img src="/images/common/btn_icon_del.gif" style="cursor:hand" align="absmiddle" onclick="searchcrtdtfr.value=searchcrtdtto.value=''" alt="">
												          </td>
												        </tr>
												        <tr> 
												          <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												          <td width="70"><font color="#4F4F4F">자료기준일</font></td>
												          <td colspan="2"> 
												          	<html:text property="searchbasicdatefr" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchbasicdatefr);">&nbsp;~
												          	<html:text property="searchbasicdateto" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchbasicdateto);">&nbsp;<img src="/images/common/btn_icon_del.gif" style="cursor:hand" align="absmiddle" onclick="searchbasicdatefr.value=searchbasicdateto.value=''" alt="">
												          </td>
												        </tr>
												        <tr> 
												          <td colspan="4"> 
												            <table width="570" border="0" cellspacing="0" cellpadding="0">
												              <tr> 
												                <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												                <td width="70"><font color="#4F4F4F">취합담당자</font></td>
												                <td width="130"><html:text property="searchchrgusrnm" style="width:100;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
												                <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
												                <td width="70"><font color="#4F4F4F">입력담당자</font></td>
												                <td><html:text property="searchinputusrnm" style="width:100;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
												              </tr>
												            </table>
												          </td>
												        </tr>
												        <tr>
								  								<td height="3" colspan="4" bgcolor="white"></td>
								  							</tr>
                              </table>
											      </div>
                              <!--검색조건 테이블 끝-->
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><iframe frameborder="0" name="iframe1" width="100%" height="500" title="양식목록"></iframe></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"><img src="/images/common/step_01.gif" style="display:none; position:absolute; margin:50 0 0 5; z-index:2;" alt=""></td>
  </tr>
</html:form>  
</table>
<script language="Javascript" type="text/javascript">
<%
	String user_gbn = "";	
	String rep_dept = "";
	String dept_id = "";
	if(!session.getAttribute("user_gbn").equals("001")){
	 	user_gbn = (String)session.getAttribute("user_gbn");
	 	rep_dept = (String)session.getAttribute("rep_dept");
	}
	dept_id = (String)session.getAttribute("dept_code");
	
	//기관구분없이 무조건 전체조직도 표시
	user_gbn = "";
	dept_id = rep_dept = nexti.ejms.common.appInfo.getRootid();
%>
document.forms[0].searchdept.value = "<%=dept_id%>";
document.forms[0].orggbn.value = "<%=user_gbn%>";
setSelectedItem('<%=dept_id%>');
loadFormation('<%=rep_dept%>');	//조직도 조회
divDisplay();		//상세조건
form_submit();
setSearchDate();	//문서작성일,자료기준일 유효성체크 이벤트 세팅
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>