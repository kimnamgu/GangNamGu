<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 기존신청서가져오기
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
<script language="javascript">
menu6.onmouseover();
menu66.onmouseover();

function form_submit() {
	document.forms[0].target = "iframe1";
	document.forms[0].submit();
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

///콤보박스에서 선택 
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
function selectDept(deptId){;
	var treeFormation = document.getElementsByTagName("div")[2];
	
	document.forms[0].deptcd.value = deptId;
	document.forms[0].treescroll.value = treeFormation.scrollTop;

	form_submit();
}

</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/getUsedIfr">
<html:hidden property="deptcd"/>
<html:hidden property="treescroll"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_15.gif" alt=""></td>
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
                      <td width="80"><img src="/images/collect/tab_09.gif" width="74" height="28" alt=""></td>
                      <td width="140">
											<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
											<html:select name="sinchungListForm" property="orggbn" style="width:130px" onchange="orggbnChange(document.forms[0])">
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
                        <table width="220" border="0" cellspacing="1" cellpadding="0" height="400" class="bg5">
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
                      <td height="28">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td><iframe frameborder="0" name="iframe1" width="100%" height="500" title=""></iframe></td>
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
%>
document.forms[0].orggbn.value = "<%=user_gbn%>";
setSelectedItem('<%=dept_id%>');
loadFormation('<%=rep_dept%>');	//조직도 조회
form_submit();
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>