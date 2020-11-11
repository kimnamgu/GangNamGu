<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html lang="ko">
<head>
<title><%=nexti.ejms.common.appInfo.getAppName()%></title>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/login.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/loginOK">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="268" bgcolor="#F5F4F3"> 
            <table width="821" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="481"><img src="/images/login/img.jpg" alt="취합업무의 현명한선택 비정형화된 양식을 표준화하여 각 부서에 산재해 있는 데이터를 쉽게 모아 활용을 극대화합니다."></td>
                <td width="340"> 
                  <table width="320" border="0" cellspacing="0" cellpadding="0" align="center" height="248">
                    <tr> 
                      <td width="5" height="5"><img src="/images/login/login_r_01.gif" alt=""></td>
                      <td bgcolor="#FFFFFF" height="5" width="310"></td>
                      <td width="5" height="5"><img src="/images/login/login_r_02.gif" alt=""></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td width="5"></td>
                      <td bgcolor="#FFFFFF" width="310" height="238" valign="middle"> 
                        <table width="280" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td colspan="3" align="center"><img src="/client/images/logo.gif" alt="로고"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="55" valign="bottom"><img src="/images/login/login_title.gif" alt="로그인 타이틀"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="8"></td>
                          </tr>
                          <tr> 
                            <td colspan="3"> 
                              <table width="280" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/id.gif" alt="사용자 아이디" onclick="if(location.href.indexOf('test')>-1){usrid.style.display=(usrid.style.display=='block')?'none':'block'}" onkeypress=""></td>
                                  <td width="90" height="22"> 
                                    <html:text property="userId" style="width:99%;" onkeydown="javascript:enterKey_focus('loginForm.password');" tabindex="1"/>
                                    <select id="usrid" name="id" tabindex="3" style="width:99%;display:none" onchange="this.onclick()" onclick="userId.value=this[this.selectedIndex].id;password.value=this[this.selectedIndex].pw" onkeypress="">
                                    	<option id="" pw="">사용자선택</option>
                                    </select>
                                  </td>
                                  <td rowspan="2" width="105" align="center">
                                  	<a href="javascript:check_submit();"><img src="/images/login/login_btn.gif" alt="로그인" align="absmiddle"></a>
                                  	<a href="javascript:gpkiLogin(document.loginForm, 'login', '/certOK.do');"><img src="/images/login/logingpki_btn.gif" alt="GPKI로그인" align="absmiddle"></a>
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/pw.gif" alt="비밀번호"></td>
                                  <td width="90" height="22"> 
                                    <html:password property="password" style="width:99%;" onkeydown="javascript:if (event.keyCode == 13) {check_submit();}" redisplay="false" tabindex="2"/>
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="85">&nbsp;</td>
                                  <td width="115" colspan="2"><html:multibox property="setfl" style="border:0;background-color:transparent;" value="1"/>설정유지</td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="5"></td>
                    </tr>
                    <tr> 
                      <td width="5" height="5"><img src="/images/login/login_r_03.gif" alt=""></td>
                      <td bgcolor="#FFFFFF" height="5" width="310"></td>
                      <td width="5" height="5"><img src="/images/login/login_r_04.gif" alt=""></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<!-- GPKI 로그인-서버정보입력란 시작 -->
<input type="hidden" name="challenge">
<input type="hidden" name="type">
<!-- GPKI 로그인-서버정보입력란 끝 -->

</html:form>

<!-- GPKI 로그인-인증처리페이지 시작 -->
<%@ include file="/include/gpki.jsp" %>
<!-- GPKI 로그인-인증처리페이지 끝 -->

<script language="javascript">
	//document.loginForm.usrid.style.display='block'
	loadFocus('<bean:write name="loginForm" property="userId"/>');
</script>
<html:messages id="msg" message="true">
  <script language="javascript"><bean:write name="msg"/></script>
</html:messages>
</body>
</html>