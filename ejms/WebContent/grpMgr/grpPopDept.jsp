<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록 세부부서코드등록
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
<title>세부부서지정</title>
<base target="_self">
<jsp:include page="/include/processing.jsp" flush="true"/>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script type="text/javascript" src="/script/grppopdept.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form method="POST" action="/grpDB.do">
<!--680*400사이즈로 새창 오픈 -->
<table width="680" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" width="13" height="13" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="644" height="364" valign="top"> 
      <table width="644" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/admin/title_05.gif" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="282" height="260" valign="top"> 
                  <table width="270" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="76"><img src="/images/collect/tab_02.gif" width="74" height="28" alt=""></td>
                      <td width="54">&nbsp;</td>
                      <td width="144">
											<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
											<html:select name="groupForm" property="sch_orggbn" style="width:130px" onchange="orggbnChange()">
												<jsp:useBean id="sch_orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
													<bean:define id="user_gbn" name="user_gbn" scope="session"/>
													<jsp:setProperty name="sch_orggbn" property="ccd_cd" value="023"/>
													<jsp:setProperty name="sch_orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
													<jsp:setProperty name="sch_orggbn" property="all_fl" value="ALL"/>
												</jsp:useBean>
												<html:optionsCollection name="sch_orggbn" property="beanCollection"/>
											</html:select>&nbsp;
											<% } else { %>
											<html:hidden property="sch_orggbn"/> 
											<% } %>
											</td>
                    </tr>
                    <tr> 
                      <td colspan="3"> 
                        <table width="270" border="0" cellspacing="1" cellpadding="0" height="230" class="bg5">
                          <tr> 
                            <td valign="top" class="bg4" style="padding:0 0 0 0"><div id="treebox_formation" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto;"></div></td>
                          </tr>
                        </table>
                        <table width="270" border="0" cellspacing="0" cellpadding="0" height="28" class="bg5" align="center">
                          <tr align="middle"> 
                          	<td width="5"></td>
                            <td width="60"><select name="searchType" style="width:99%;"><option value="2">성명</option><option value="1">부서</option></td>
                            <td width="155"><input type="text" name="searchValue" style="width:99%;" onkeypress="if(event.keyCode==13){event.returnValue=false;searchFormation();}"/></td>
                            <td width="45"><a href="javascript:searchFormation()"><img src="/images/common/btn_s_search.gif" border="0" alt="검색"></a></td>
                            <td width="5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="80"> 
                  <!--추가 삭제 테이블-->
                  <table width="50" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr> 
                      <td><a href="#" onclick="addDept()"><img src="/images/common/btn_add.gif" alt="추가"></a></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td><a href="#" onclick="delDept()"><img src="/images/common/btn_del.gif" alt="삭제"></a></td>
                    </tr>
                  </table>
                </td>
                <td width="282" valign="top" align="right"> 
                  <table width="270" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="28"><b><font color="444444">배포목록명</font></b> 
                        <html:select property="sch_grplist" style="width:74%" onchange="javascript:getDetailList(this.value);">
													<jsp:useBean id="grpselect" class="nexti.ejms.list.form.GrpListForm">
														<bean:define id="user_gbn" name="user_gbn" scope="session"/>
														<bean:define id="user_id" name="user_id" scope="session"/>
														<jsp:setProperty name="grpselect" property="all_fl" value="ALL"/>
														<jsp:setProperty name="grpselect" property="user_gbn" value="<%=(String)user_gbn%>"/>
														<% if ( "y".equals(request.getParameter("userModifyMode")) ) { %>
															<jsp:setProperty name="grpselect" property="crtusrid" value="<%=(String)user_id%>"/>
															<jsp:setProperty name="grpselect" property="crtusrgbn" value="1"/>
														<% } %>
													</jsp:useBean>
													<html:optionsCollection name="grpselect" property="beanCollection"/>
				              	</html:select>
                      </td>
                    </tr>
                    <tr> 
                      <td><select id="deptList" name="deptList" style="width:270;height:230;" multiple="multiple" ondblclick="delDept();"></select></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="10"></td>
              </tr>
              <tr> 
                <td colspan="3"> 
                  <!--버튼 들어갈 테이블-->
                  <table width="644" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="634"></td>
                      <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
                    </tr>
                    <tr bgcolor="#F6F6F6"> 
                      <td bgcolor="#F6F6F6" width="5"></td>
                      <td bgcolor="#F6F6F6" align="right" height="35">
                      	<a href="#" onclick="validation()"><img src="/images/common/btn_ok.gif" alt="확인"></a>
                        <a href="#" onclick="cancel()"><img src="/images/common/btn_cancel.gif" alt="취소"></a>
                      <td width="5"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="634"></td>
                      <td><img src="/images/common/btn_r_04.gif" width="5" height="5" alt=""></td>
                    </tr>
                  </table>
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
    <td width="13"><img src="/images/common/popup_r_03.gif" width="13" height="13" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
<%
	String user_gbn = "";	
	String rep_dept = "";
	if(!session.getAttribute("user_gbn").equals("001")){
	 	user_gbn = (String)session.getAttribute("user_gbn");
	 	rep_dept = (String)session.getAttribute("rep_dept");
	}
%>
<script language="Javascript" type="text/javascript">
document.forms[0].sch_orggbn.value = "<%=user_gbn%>";
loadFormation('<%=rep_dept%>');	//조직도 조회
</script>
</body>
</html>