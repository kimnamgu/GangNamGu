<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경 팝업
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
<title>담당자 선택</title>
<base target="_self">
<link href="/css/style.css" rel="stylesheet" type="text/css">
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script type="text/javascript" src="/script/commdeptuser.js"></script>
<script src="/script/common.js"></script>
</head>


<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<!--780*400 사이즈로 새창 오픈-->
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" width="13" height="13" alt=""></td>
    <td width="744" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" height="364" valign="top"> 
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/admin/title_14.gif" alt="담당자선택" /></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="332" height="260" valign="top"> 
                  <!--조직도 테이블-->
                  <form name="orggbnForm">
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="76"><img src="/images/collect/tab_02.gif" width="74" height="28" alt="조직도"></td>
                      <td width="100">&nbsp;</td>
                      <td width="244"><b><font color="#FF5656">
											<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
											<html:select name="managerForm" property="orggbn" style="width:150px" onchange="orggbnChange()">
												<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
													<bean:define id="user_gbn" name="user_gbn" scope="session"/>
													<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
													<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
													<jsp:setProperty name="orggbn" property="all_fl" value="ALL"/>
												</jsp:useBean>
												<html:optionsCollection name="orggbn" property="beanCollection"/>
											</html:select>&nbsp;
											<% } else { %>
											<html:hidden name="managerForm" property="orggbn"/>
											<% } %>
											</font></b></td>
                    </tr>
                    <tr> 
                      <td colspan="3"> 
                        <table width="320" border="0" cellspacing="1" cellpadding="0" height="300" class="bg5">
                          <tr> 
                            <td valign="top" class="bg4" style="padding:0 0 0 0"><div id="formationLayer" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto; background-color:#F5F5F5;"></div></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                  </form>
                </td>
                <td width="412" valign="top"> 
                  <table width="410" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
                      <td height="28">
                      	<form name="searchForm" onsubmit="return searchUser()">
                      	<b><font color="444444">직원명</font></b>
                      	<input type="text" name="userName" style="width:150">
                        <a href="#" onclick="searchUser()"><img src="/images/common/btn_search.gif" align="absmiddle" alt="검색"></a>
                        </form>
                      </td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="390" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="3" class="list_bg"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="1"></td>
                          </tr>
                          <tr> 
                            <td class="list_t" width="40"><input type="checkbox" name="all" id="all" style="border:0;background-color:transparent;" onclick="allCheck(this)"></td>
                            <td class="list_t" width="200">부서</td>
                            <td class="list_t" width="100">직원명</td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="1"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" class="list_bg"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="1"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td valign="top" width="390"><div id="userList" style="position:absolute; width:388; height:275; overflow:auto;"></div></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="2" height="10"></td>
              </tr>
              <tr> 
                <td colspan="2"> 
                  <!--버튼 들어갈 테이블-->
                  <table width="744" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="634"></td>
                      <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
                    </tr>
                    <tr bgcolor="#F6F6F6"> 
                      <td bgcolor="#F6F6F6" width="5"></td>
                      <td bgcolor="#F6F6F6" align="right" height="35">
                      	<a href="javascript:addUser()"><img src="/images/common/btn_ok.gif" alt="확인"></a>
                        <a href="javascript:cancel()"><img src="/images/common/btn_cancel.gif" alt="취소"></a></td>
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
    <td width="744" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
<%
	String user_gbn = "";	
	String rep_dept = "";
	if(!session.getAttribute("user_gbn").equals("001")){
	 	user_gbn = (String)session.getAttribute("user_gbn");
	 	rep_dept = (String)session.getAttribute("rep_dept");
	}
%>
<script language="Javascript" type="text/javascript">
document.forms[0].orggbn.value = "<%=user_gbn%>";
loadFormation('<%=rep_dept%>');	//조직도 조회

</script>
</body>
</html>