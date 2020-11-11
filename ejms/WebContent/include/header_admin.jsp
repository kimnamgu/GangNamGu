<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: JSP페이지 헤더(관리자)
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
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<title><%=nexti.ejms.common.appInfo.getAppName()%></title>
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script type="text/javascript" src="/script/common.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td background="/images/top_bg.gif"> 
      <table width="930" border="0" cellspacing="0" cellpadding="0">
        <tr>
					<td rowspan="3" width="20" valign="top"><img src="/images/top_img.gif" alt=""></td>
          <td rowspan="3" width="190" background="/images/logo_bg.gif" align="center"><a href="/main.do"><img src="/client/images/logo.gif" border="0" alt="사용자 메인화면"></a></td>
          <td height="40" background="/images/top_bg2.gif">
            <!--상단메뉴 테이블 시작-->
            <table border="0" cellspacing="0" cellpadding="0" align="right">
              <tr> 
              	<td align="right" style="padding:0 10 0 0;"><b><font color="#EA4343">[<%=session.getAttribute("dept_name")%>]</font>&nbsp;<%=session.getAttribute("user_name")%></b></td>
              	<td width="70"><a href="javascript:javascript:showPopup('/usrMgrFrame3.do?userinfo=true&mode=usr_M&user_id=<%=session.getAttribute("user_id")%>&dept_id=<%=session.getAttribute("dept_code")%>',310,440,0,0)"><img name="Image86" src="/images/userinfo.gif" alt="정보수정" align="absmiddle" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image86','','/images/userinfo_over.gif',1)"></a></td>
              	<td width="60"><a href="/main.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image77','','/images/user_over.gif',1)"><img name="Image77" border="0" src="/images/user.gif" width="51" height="15" alt="사용자"></a></td>
                <td width="60"><a href="/help/help-admin.pdf" target="_blank" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image85','','/images/help_over.gif',1)"><img name="Image85" border="0" src="/images/help.gif" width="51" height="15" alt="도움말"></a></td>
                <td width="70"><a href="/logOut.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image74','','/images/logout_over.gif',1)"><img name="Image74" border="0" src="/images/logout.gif" width="61" height="15" alt="로그아웃"></a></td>
              </tr>
            </table>
            <!--상단메뉴 테이블 끝-->
          </td>
        </tr>
        <tr> 
          <td height="46"> 
            <!--메인메뉴 테이블 시작-->
            <table border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="100" align="center"><a id="menu1" href="/formatCommMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image16','','/images/admin/m_01_over.gif',1)"><img id="menu11" name="Image16" border="0" src="/images/admin/m_01.gif" onMouseOver="MM_showHideLayers('menu01','','show','menu02','','hide','menu03','','hide','menu04','','hide')" alt="양식관리" <% if ( "N".equals(session.getAttribute("getDept_YN"))) out.print("style='display:none'"); %>/></a></td>
                <td width="100" align="center"><a id="menu2" href="/collsttcsList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image17','','/images/admin/m_02_over.gif',1)"><img id="menu22" name="Image17" border="0" src="/images/admin/m_02.gif" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','show','menu03','','hide','menu04','','hide')" alt="통계현황"/></a></td>
                <td width="100" align="center"><a id="menu3" href="/usrMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image18','','/images/admin/m_03_over.gif',1)"><img id="menu33" name="Image18" border="0" src="/images/admin/m_03.gif" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','show','menu04','','hide')" alt="조직관리"/></a></td>
                <td width="100" align="center"><a id="menu4" href="/codeList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image19','','/images/admin/m_04_over.gif',1)"><img id="menu44" name="Image19" border="0" src="/images/admin/m_04.gif" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','show')" alt="시스템관리" <% if ( !"001".equals(session.getAttribute("user_gbn"))) out.print("style='display:none'"); %>/></a></td>
                <td width="100" align="center"><a id="menu5" href="/noticeList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image20','','/images/admin/m_05_over.gif',1)"><img id="menu55" name="Image20" border="0" src="/images/admin/m_05.gif" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','hide')" alt="공지사항"/></a></td>
              </tr>
            </table>
            <!--메인 메뉴 테이블 끝-->
          </td>
        </tr>
        <tr> 
          <td height="30" valign="top"> 
            <!--메인메뉴 오버시 서브메뉴 레이어 4개 표시-->
            <div id="menu01" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr> 
                  <td width="80"><a href="/formatCommMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image21','','/images/admin/s_m_01_over.gif',1)"><img name="Image21" border="0" src="/images/admin/s_m_01.gif" alt="공통양식관리" /></a></td>
                  <td width="80"><a href="/grpList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image22','','/images/admin/s_m_02_over.gif',1)"><img name="Image22" border="0" src="/images/admin/s_m_02.gif" alt="배포목록관리" /></a></td>
                  <td width="80"><a href="/attrList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','/images/admin/s_m_03_over.gif',1)"><img name="Image23" border="0" src="/images/admin/s_m_03.gif" alt="속성목록관리" /></a></td>
                </tr>
              </table>
            </div>
            <div id="menu02" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr> 
                  <td width="105"></td>
                  <td width="100" <% if ( "N".equals(session.getAttribute("getDept_YN"))) out.print("style='display:none'"); %>><a href="/collsttcsList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/admin/s_m_04_over.gif',1)"><img name="Image24" border="0" src="/images/admin/s_m_04.gif" alt="취합업무사용현황"/></a></td>
                  <td width="100"><a href="/inputsttcsList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','/images/admin/s_m_05_over.gif',1)"><img name="Image25" border="0" src="/images/admin/s_m_05.gif" alt="입력업무사용현황"/></a></td>
                  <td width="100" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %>><a href="/rchsttcsList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26','','/images/admin/s_m_06_over.gif',1)"><img name="Image26" border="0" src="/images/admin/s_m_06.gif" alt="설문조사사용현황" /></a></td>
                  <td width="90"><a href="/reqsttcsList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image27','','/images/admin/s_m_07_over.gif',1)"><img name="Image27" border="0" src="/images/admin/s_m_07.gif" alt="신청서사용현황"/></a></td>
                  <td width="100" <% if ( !"001".equals(session.getAttribute("user_gbn"))) out.print("style='display:none'"); %>><a href="/ldapMonitoringList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image98','','/images/admin/s_m_15_over.gif',1)"><img name="Image98" border="0" src="/images/admin/s_m_15.gif" alt="LDAP동기화현황"/></a></td>
                  <td width="100" <% if ( !"001".equals(session.getAttribute("user_gbn"))) out.print("style='display:none'"); %>><a href="/systemLogMonitoringList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image99','','/images/admin/s_m_16_over.gif',1)"><img name="Image99" border="0" src="/images/admin/s_m_16.gif" alt="시스템접속현황"/></a></td>
                </tr>
              </table>
            </div>
            <div id="menu03" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr> 
                  <td width="210"></td>
                  <td width="65"><a href="/usrMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/admin/s_m_13_over.gif',1)"><img name="Image28" border="0" src="/images/admin/s_m_13.gif" alt="조직관리" /></a></td>
                  <td width="80"><a href="/chrgUnitList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/admin/s_m_08_over.gif',1)"><img name="Image29" border="0" src="/images/admin/s_m_08.gif" alt="담당단위관리" /></a></td>
                  <td width="80"><a href="/deliveryUserMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/admin/s_m_17_over.gif',1)"><img name="Image34" border="0" src="/images/admin/s_m_17.gif" alt="배부담당관리" /></a></td>
                  <td width="75"><a href="/chgMgr.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/admin/s_m_09_over.gif',1)"><img name="Image30" border="0" src="/images/admin/s_m_09.gif" alt="관리자변경" /></a></td>
                  <td width="65" <% if ( "N".equals(session.getAttribute("getDept_YN"))) out.print("style='display:none'"); %>><a href="/dataTransferList.do?isFirst=true" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/admin/s_m_14_over.gif',1)"><img name="Image31" border="0" src="/images/admin/s_m_14.gif" alt="자료이관" /></a></td>
                </tr>
              </table>
            </div>
            <div id="menu04" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr> 
                  <td width="305"></td>
                  <td width="65"><a href="/codeList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','/images/admin/s_m_11_over.gif',1)"><img name="Image32" border="0" src="/images/admin/s_m_11.gif" alt="코드관리" /></a></td>
                  <td width="65"><a href="/systemAgent.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/admin/s_m_12_over.gif',1)"><img name="Image33" border="0" src="/images/admin/s_m_12.gif" alt="Agent 관리" /></a></td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>