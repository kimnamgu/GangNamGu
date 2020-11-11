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
<%@ page import="java.util.Calendar" %>
<%
Calendar today = Calendar.getInstance();

int currentyear = today.get(Calendar.YEAR);
int baseyear = currentyear;
String searchyear = "";

if(request.getAttribute("currentyear") != null) {
    searchyear = request.getAttribute("currentyear").toString()+"년";
    currentyear = Integer.parseInt(request.getAttribute("currentyear").toString().substring(0, 4));
}
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


function form_submit() {
    var frm = document.forms[0];
    var searchyear = frm.searchyear;
    var searchdeptcd = frm.searchdeptcd;
    var searchchrgunitcd = frm.searchchrgunitcd;
    var searchstatus = frm.searchstatus;
    var searchstrdt = frm.searchstrdt;
    var searchenddt = frm.searchenddt;
    var searchgubun = frm.searchgubun;
    var searchtitle = frm.searchtitle;
    
    searchstrdt.onpropertychange = null;
    searchenddt.onpropertychange = null;
    searchyear.onpropertychange = null;
    
    searchyear.value = searchyear.value.trim();
    searchdeptcd.value = searchdeptcd.value.trim();
    searchchrgunitcd.value = searchchrgunitcd.value.trim();
    searchstatus.value = searchstatus.value.trim();
    searchstrdt.value = searchstrdt.value.trim();
    searchenddt.value = searchenddt.value.trim();
    searchgubun.value = searchgubun.value.trim();
    
    frm.currentyear.value = searchyear.value;
    if(searchyear.value != ""){
        searchstrdt.value = searchyear.value.substring(0, 4) + searchstrdt.value.substr(4);
        searchenddt.value = searchyear.value.substring(0, 4) + searchenddt.value.substr(4);
    }
    
    searchstrdt.onpropertychange = setSearchDate;
    searchenddt.onpropertychange = setSearchDate;
    
    frm.action = "/workplanIframeList.do";
    frm.target = "iframe1";
    frm.submit();
}

function setSearchDate() {
    var frm = document.forms[0];
    var strdt = frm.searchstrdt;            //업무시작일
    var enddt = frm.searchenddt;            //업무종료일
    
    if (strdt.onpropertychange == null) {
        strdt.onpropertychange = setSearchDate;
        enddt.onpropertychange = setSearchDate;
    } else if (event.propertyName.toLowerCase() == "value") {
        var src = event.srcElement;
        if (src == strdt) {
            if (strdt.value > enddt.value) {
                enddt.onpropertychange = null;
                enddt.value = strdt.value;
                enddt.onpropertychange = setSearchDate;
            }
        } else if (src == enddt) {
            if (strdt.value > enddt.value || strdt.value == "") {
                strdt.onpropertychange = null;
                strdt.value = enddt.value;
                strdt.onpropertychange = setSearchDate;
            }
        }
        
    }
    
    if(strdt.value.substring(0,4) != enddt.value.substring(0,4)){
        frm.searchyear.value="";
    } else {
        if(frm.searchyear.value != "") {
            frm.searchyear.value = strdt.value.substring(0,4)+'년';
        }
    }
}
/**
 * 조회취소
 */
function search_reset() {
    document.location="/workplanList.do";
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
function orggbnChange(frm){
	//loadFormation();
	frm.action="/workplanList.do";
	frm.target="";
	frm.submit();
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	var treeFormation = document.getElementById("formationLayer");
	document.forms[0].treescroll.value = treeFormation.scrollTop;
	document.forms[0].searchdept.value = orgid;
	document.forms[0].searchdeptcd.value = orgid;
	document.forms[0].searchdeptcd.onchange();
}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/workplanIframeList">
<html:hidden property="searchdept"/>
<html:hidden property="treescroll"/>
<html:hidden property="currentyear"/>
<html:hidden property="page"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/workplan/title_01.gif" alt=""></td>
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
                      <td width="80"><img src="/images/collect/tab_02.gif" width="74" height="28" alt=""></td>
                      <td width="140">
                      <% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
                        <html:select name="workplanListForm" property="orggbn" style="width:130px" onchange="orggbnChange(document.forms[0])">
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
                        <% ((nexti.ejms.workplan.form.WorkplanListForm)request.getAttribute("workplanListForm")).setOrggbn((String)session.getAttribute("user_gbn")); %>
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
                                  <td width="20" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td width="35"><font color="#4F4F4F">년 도</font></td>
                                  <td width="80">
                                    <html:select property="searchyear" styleClass="select" style="width:95%;background-color:white" value="<%=searchyear%>" onchange="form_submit();">
                                      <jsp:useBean id="yearselect" class="nexti.ejms.list.form.YearListForm">
                                        <jsp:setProperty name="yearselect" property="currentyear" value="<%=baseyear%>"/>
                                        <jsp:setProperty name="yearselect" property="gap" value="1"/>
                                        <jsp:setProperty name="yearselect" property="all_fl" value="ALL"/>
                                      </jsp:useBean>
                                      <html:optionsCollection name="yearselect" property="beanCollection"/>
                                    </html:select>
                                  </td>
                                  <td width="20" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td width="35"><font color="#4F4F4F">날 짜</font></td>
                                  <td width="210">
                                  	<html:text property="searchstrdt" style="text-align:center;;background-color:white" size="9" readonly="true"/><img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchstrdt);"> ~
                                    <html:text property="searchenddt" style="text-align:center;background-color:white" size="9" readonly="true"/><img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].searchenddt);"><img src="/images/common/btn_icon_del.gif" style="cursor:hand" align="absmiddle" onclick="searchstrdt.value=searchenddt.value=''" alt="">
                                  </td>
                                  <td width="20" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td width="50"><font color="#4F4F4F">진행상태</font></td>
                                  <td width="100">
                                    <html:select property="searchstatus" style="width:95%;background-color:white">
                                      <html:option value="">전체</html:option>
                                      <html:option value="1">업무진행</html:option>
                                      <html:option value="2">업무종료</html:option>
                                    </html:select>
                                  </td>
                                </tr>
                                <tr>
                                  <td align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td><font color="#4F4F4F">부 서</font></td>
                                  <td colspan="4">  
                                    <html:select property="searchdeptcd" style="width:95%;background-color:white" onchange="form_submit();">
																			<jsp:useBean id="orggbn_dt" class="nexti.ejms.list.form.DeptDetailListForm">
																				<bean:define id="orggbn" name="workplanListForm" property="orggbn"/>
																				<bean:define id="rep_dept" name="rep_dept" scope="session"/>
																				<bean:define id="user_id" name="user_id" scope="session"/>
																				<jsp:setProperty name="orggbn_dt" property="orggbn" value="<%=(String)orggbn%>"/>	
																				<jsp:setProperty name="orggbn_dt" property="rep_dept" value="<%=(String)rep_dept%>"/>
																				<jsp:setProperty name="orggbn_dt" property="user_id" value="<%=(String)user_id%>"/>
																				<jsp:setProperty name="orggbn_dt" property="all_fl" value="ALL"/>	
																			</jsp:useBean>
																			<html:optionsCollection name="orggbn_dt" property="beanCollection" />
																		</html:select>
                                  </td> 
                                  <td align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td><font color="#4F4F4F">담당업무</font></td>
                                  <td>
                                    <html:select property="searchchrgunitcd" style="width:95%;background-color:white" onchange="form_submit();">
                                    	<jsp:useBean id="chrgunitselect" class="nexti.ejms.list.form.ChgUnitListForm">
	                                      <bean:define id="orggbn" name="workplanListForm" property="orggbn"/>
										  <jsp:setProperty name="chrgunitselect" property="orggbn" value="<%=(String)orggbn%>"/>
	                                      <jsp:setProperty name="chrgunitselect" property="dept_id" value="%"/>
	                                      <jsp:setProperty name="chrgunitselect" property="all_fl" value="ALL"/>
	                                      <jsp:setProperty name="chrgunitselect" property="title" value="전체"/>
                                     	</jsp:useBean>
                                    	<html:optionsCollection name="chrgunitselect" property="beanCollection"/>
                                    </html:select>
                                  </td>
                                </tr>
                                <tr>
                                  <td align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                                  <td><font color="#4F4F4F">검 색</font></td>
                                  <td>
                                    <html:select property="searchgubun" style="width:95%;background-color:white">
                                      <html:option value="1">담당자</html:option>
                                      <html:option value="2">사업명</html:option>
                                    </html:select>
                                  <td colspan="3"><html:text property="searchtitle" style="width:95%;background-color:white" onkeypress="if(event.keyCode==13){form_submit();}"/></td>
                                  <td colspan="3" align="center">
                                    <img src="/images/workplan/btn_retrieve.gif" width="43" height="20" align="absmiddle" style="cursor:hand" onclick="form_submit()" alt="">
                                    <img src="/images/workplan/btn_cancel.gif" width="73" height="20" align="absmiddle" style="cursor:hand" onclick="search_reset()" alt="">
                                  </td>
                                </tr>
                              </table> 
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
         dept_id = (String)session.getAttribute("dept_code");
    } else {
        dept_id = (String)session.getAttribute("dept_code");
        rep_dept = nexti.ejms.common.appInfo.getRootid(); 
    }
    //dept_id = (String)session.getAttribute("dept_code");
    //기관구분없이 무조건 전체조직도 표시
    //user_gbn = "";
    //dept_id = rep_dept = nexti.ejms.common.appInfo.getRootid();
    //dept_id = nexti.ejms.common.appInfo.getRootid();
    //rep_dept = (String)session.getAttribute("dept_code");
%>
setSelectedItem('<%=rep_dept%>');
loadFormation('<%=rep_dept%>');	//조직도 조회
setSearchDate();	//문서작성일,자료기준일 유효성체크 이벤트 세팅
form_submit();

var dept = document.forms[0].searchdept.value.trim();
var deptcd = document.forms[0].searchdeptcd.value.trim();
if ( dept == "" ) {
    document.forms[0].searchdept.value = "<%=dept_id%>";
}
if( deptcd == "" ) {
    document.forms[0].searchdeptcd.value = "<%=dept_id%>";
}
document.forms[0].searchdeptcd.onchange();
</script>
<html:messages id="msg" message="true">
<script><bean:write name="msg" filter="false"/></script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>