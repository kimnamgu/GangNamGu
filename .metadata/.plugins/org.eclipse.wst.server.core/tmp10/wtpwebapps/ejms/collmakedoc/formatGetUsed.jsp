<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 사용했던양식가져오기
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
<html>
<head>
<title>공통양식가져오기</title>
<base target="_self">

<link rel="stylesheet" href="/css/style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<jsp:include page="/include/processing.jsp" flush="true"/>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script language="javascript">
function form_submit() {
	var oldTarget = document.forms[0].target;
	document.forms[0].target = "iframe1";
	document.forms[0].submit();
	document.forms[0].target = oldTarget;
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

//콤보박스에서 선택 
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
	document.forms[0].treescroll.value = treeFormation.scrollTop;

	form_submit();
}

function click_prev(actionpath) {
	document.forms[0].action = actionpath;
	processingShow();
	document.forms[0].submit();
}

function click_cancel() {
	if(confirm("작업을 취소하시겠습니까?") == true)
		window.close();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/formatGetUsedFrame" method="post">
<html:hidden property="sysdocno"/><html:hidden property="type"/>
<html:hidden property="searchdept"/><html:hidden property="treescroll"/>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top" height="514">
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr>
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_26.gif" alt="사용했던양식가져오기"></td>
        </tr>
        <tr>
          <td height="11" width="644"></td>
        </tr>
        <tr>
          <td valign="top">
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td valign="top" width="250" rowspan="2">
                  <table width="235" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="80"><img src="/images/collect/tab_11.gif" alt="공통양식함"></td>
                      <td width="170" align="right">
											<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
											<html:select name="formatForm" property="orggbn" style="width:130px" onchange="orggbnChange(document.forms[0])">
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
                        <table width="235" height="430" border="0" cellspacing="1" cellpadding="0" class="bg5">
                          <tr>
                            <td valign="top"><div id="formationLayer" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto; background-color:#F5F5F5;"></div></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
                <td valign="top" height="400"><iframe name="iframe1" src="/formatGetUsedFrame.do" frameborder="0" width="100%" height="100%" scrolling="auto" marginwidth=0 marginheight=0 title="양식목록"></iframe></td>
              </tr>
              <tr>
                <td align="right" valign="bottom">
                	<a href="javascript:click_prev('/formatMake.do?formseq=0&sysdocno=<bean:write name="formatForm" property="sysdocno"/>')"><img src="/images/common/btn_back.gif" border="0" alt="이전"></a>
                	<a href="javascript:click_cancel()"><img src="/images/common/btn_cancel.gif" border="0" alt="취소"></a>
              	</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="13"></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_03.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr>
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
<iframe name="bgProcFrame" width="0" height="0" title=""></iframe>
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
</body>
</html>