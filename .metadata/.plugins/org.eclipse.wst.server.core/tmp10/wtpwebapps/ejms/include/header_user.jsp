<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: JSP페이지 헤더(사용자)
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
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<%
	if ( "수원3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		String user_id = (String)session.getAttribute("user_id");
		String delivery_yn = nexti.ejms.user.model.UserManager.instance().getUserInfo(user_id).getDelivery_yn();
		delivery_yn = nexti.ejms.util.Utils.nullToEmptyString(delivery_yn);
		
		if ( !("Y".equals(delivery_yn) || "001".equals((String)session.getAttribute("isSysMgr"))) ) {
			pageContext.setAttribute("수원3740000", "style='display:none'"); 
			
			//설문조사, 신청서 양식 보유여부
			java.sql.Connection con = null;
			java.sql.PreparedStatement pstmt = null;
			java.sql.ResultSet rs = null;
			try {
				pageContext.setAttribute("수원3740000-RCH", "style='display:none'");
				pageContext.setAttribute("수원3740000-REQ", "style='display:none'");
				String dept_code = (String)session.getAttribute("dept_code");
				String sql = "SELECT \n" +
				             "  (SELECT COUNT(*) FROM RCHMST WHERE COLDEPTCD LIKE '" + dept_code + "' AND CHRGUSRID LIKE '" + user_id + "'), \n" +
				             "  (SELECT COUNT(*) FROM REQFORMMST WHERE COLDEPTCD LIKE '" + dept_code + "' AND CHRGUSRID LIKE '" + user_id + "') \n" +
				             "FROM DUAL";
				con = nexti.ejms.common.ConnectionManager.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					if ( rs.getInt(1) > 0 ) pageContext.removeAttribute("수원3740000-RCH");
					if ( rs.getInt(2) > 0 ) pageContext.removeAttribute("수원3740000-REQ");
				}
			} finally {
				nexti.ejms.common.ConnectionManager.close(con, pstmt, rs);
			}
		}
	}
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
<script language="JavaScript">
MM_reloadPage(true);

function showHelp(helpObj) {
	helpObj.style.display=(helpObj.style.display=='none')?'block':'none';
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onLoad="MM_preloadImages('/images/admin_over.gif','/images/logout_over.gif','/images/m_01_over.gif','/images/m_02_over.gif','/images/m_03_over.gif','/images/m_04_over.gif','/images/m_05_over.gif','/images/s_m_01_over.gif','/images/s_m_02_over.gif','/images/s_m_03_over.gif','/images/s_m_04_over.gif','/images/s_m_05_over.gif','/images/s_m_06_over.gif','/images/s_m_07_over.gif','/images/s_m_08_over.gif','/images/s_m_09_over.gif','/images/s_m_10_over.gif','/images/s_m_11_over.gif','/images/write_btn_over.gif','/images/info_01_over.gif','/images/info_02_over.gif','/images/info_03_over.gif','/images/info_04_over.gif','/images/info_05_over.gif','/images/info_06_over.gif','/images/info_07_over.gif','/images/poll_btn_on.gif','/images/more_on.gif','/images/myfile_on.gif')">
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
								<% if ("001".equals(session.getAttribute("isSysMgr")) == true){ %>
	                <td width="60"><a href="/noticeList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image77','','/images/admin_over.gif',1)"><img name="Image77" border="0" src="/images/admin.gif" width="51" height="15" alt="관리자"></a></td>
								<% } %>           
	              <td width="60">
	              	<table title="도움말을 보시려면 Adobe Reader 9 를 설치하세요" id="helpDownload" width="130" height="100" bgcolor="white" border="3" cellspacing="0" cellpadding="0" style="border-collapse:collapse;position:absolute;top:30;display:none">
	              		<tr><td onmouseover="this.style.background='#FFFAD1'" onmouseout="this.style.background=''"><a href="http://121.150.30.41:8001/ejms-help/video/help.html" target="_blank" onclick="showHelp(helpDownload)">&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;<b>자료취합</b>&nbsp;<b>동영상</b></a></td></tr>
	              		<tr><td onmouseover="this.style.background='#FFFAD1'" onmouseout="this.style.background=''"><a href="/help/help-user.pdf" target="_blank" onclick="showHelp(helpDownload)">&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;자료취합&nbsp;도움말</a></td></tr>
	              		<tr><td onmouseover="this.style.background='#FFFAD1'" onmouseout="this.style.background=''"><a href="/help/help-exam1.pdf" target="_blank" onclick="showHelp(helpDownload)">&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;인감증명 취합예시</a></td></tr>
	              		<tr><td onmouseover="this.style.background='#FFFAD1'" onmouseout="this.style.background=''"><a href="/help/help-exam2.pdf" target="_blank" onclick="showHelp(helpDownload)">&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;일일결산 취합예시</a></td></tr>
	              		<tr><td onmouseover="this.style.background='#FFFAD1'" onmouseout="this.style.background=''"><a href="/help/help-exam3.pdf" target="_blank" onclick="showHelp(helpDownload)">&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;주간보고 취합예시</a></td></tr>
	              	</table>
	              	<a href="javascript:showHelp(helpDownload)"><img name="Image85" src="/images/help.gif" alt="도움말" align="absmiddle" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image85','','/images/help_over.gif',1)"></a>
	              </td>
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
                <td width="85" align="center"><a id="menu1" href="/colldocList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/m_01_over.gif',1)"><img id="menu11" name="Image37" border="0" src="/images/m_01.gif" width="26" height="16" onMouseOver="MM_showHideLayers('menu01','','show','menu02','','hide','menu03','','hide','menu04','','hide','menu05','','hide','menu06','','hide','menu07','','hide')" alt="취합"></a></td>
                <td width="85" align="center"><a id="menu2" href="/deliveryList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image38','','/images/m_02_over.gif',1)"><img id="menu22" name="Image38" border="0" src="/images/m_02.gif" width="25" height="15" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','show','menu03','','hide','menu04','','hide','menu05','','hide','menu06','','hide','menu07','','hide')" alt="배부"></a></td>
                <td width="85" align="center"><a id="menu3" href="/inputingList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image39','','/images/m_03_over.gif',1)"><img id="menu33" name="Image39" border="0" src="/images/m_03.gif" width="24" height="15" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','show','menu04','','hide','menu05','','hide','menu06','','hide','menu07','','hide')" alt="입력"></a></td>
                <td width="85" align="center"><a id="menu4" href="/exhibitList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image41','','/images/m_05_over.gif',1)"><img id="menu44" name="Image41" border="0" src="/images/m_05.gif" width="52" height="15" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','hide','menu05','','show','menu06','','hide','menu07','','hide')" alt="자료공유"></a></td>
                <td width="85" align="center" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %>><a id="menu5" <% if ( pageContext.getAttribute("수원3740000-RCH") != null ) out.print("href='#'"); %> href="/researchMyList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image42','','/images/m_06_over.gif',1)"><img id="menu55" name="Image42" border="0" src="/images/m_06.gif" width="52" height="15" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','hide','menu05','','hide','menu06','','show','menu07','','hide')" alt="설문조사"></a></td>
                <td width="85" align="center"><a id="menu6" <% if ( pageContext.getAttribute("수원3740000-REQ") != null ) out.print("href='#'"); %> <% if ( "인천6280000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("href='/doList.do'");%> href="/formList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image43','','/images/m_07_over.gif',1)"><img id="menu66" name="Image43" border="0" src="/images/m_07.gif" width="38" height="15" onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','hide','menu05','','hide','menu06','','hide','menu07','','show')" alt="신청서"></a></td>
                <td width="100" align="center" <%// if ( "부산강서3360000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 ) out.print("style='display:none'"); %>><a id="menu7" href="/workplanList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image44','','/images/m_08_over.gif',1)"><img id="menu77" name="Image44" border="0" src="/images/m_08.gif" width="85" height="15"onMouseOver="MM_showHideLayers('menu01','','hide','menu02','','hide','menu03','','hide','menu04','','hide','menu05','','hide','menu06','','hide','menu07','','hide')" alt="주요업무관리"></a></td>
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
                	<td width="66"><a href="/colldocMake.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image66','','/images/s_m_00_over.gif',1)"><img name="Image66" border="0" src="/images/s_m_00.gif" width="58" height="11" alt=""></a></td>
                  <td width="75"><a href="/colldocList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image63','','/images/s_m_01_over.gif',1)"><img name="Image63" border="0" src="/images/s_m_01.gif" width="67" height="11" alt=""></a></td>
                  <td width="92"><a href="/collprocList.do?docstate=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image64','','/images/s_m_02_over.gif',1)"><img name="Image64" border="0" src="/images/s_m_02.gif" width="84" height="11" alt=""></a></td>
                  <td width="55"><a href="/collcompList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image65','','/images/s_m_03_over.gif',1)"><img name="Image65" border="0" src="/images/s_m_03.gif" width="47" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
            <div id="menu02" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr>
                  <td width="105"></td>
                  <td width="53"><a href="/deliveryList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image78','','/images/s_m_06_over.gif',1)"><img name="Image78" border="0" src="/images/s_m_06.gif" width="45" height="11" alt=""></a></td>
                  <td width="104"><a href="/deliveryCompList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image79','','/images/s_m_07_over.gif',1)"><img name="Image79" border="0" src="/images/s_m_07.gif" width="104" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
            <div id="menu03" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr>
                  <td width="190"></td>
                  <td width="53"><a href="/inputingList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image75','','/images/s_m_08_over.gif',1)"><img name="Image75" border="0" src="/images/s_m_08.gif" width="45" height="11" alt=""></a></td>
                  <td width="54"><a href="/inputCompleteList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image76','','/images/s_m_09_over.gif',1)"><img name="Image76" border="0" src="/images/s_m_09.gif" width="46" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
            <div id="menu05" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr>
                  <td width="270"></td>
                  <td width="54"><a href="/exhibitList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image71','','/images/s_m_12_over.gif',1)"><img name="Image71" border="0" src="/images/s_m_12.gif" width="46" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
            <div id="menu06" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr>
                  <td width="345"></td>
                  <td width="70" <%=pageContext.getAttribute("수원3740000-RCH")%>><a href="/researchMyList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image80','','/images/s_m_13_over.gif',1)"><img name="Image80" border="0" src="/images/s_m_13.gif" height="11" alt=""></a></td>
                  <td width="75"><a href="/researchParticiList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image81','','/images/s_m_14_over.gif',1)"><img name="Image81" border="0" src="/images/s_m_14.gif" height="11" alt=""></a></td>
                  <td width="100" <%=pageContext.getAttribute("수원3740000-RCH")%> <% if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 ) out.print("style='display:none'"); %>><a href="/researchGrpMyList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image87','','/images/s_m_18_over.gif',1)"><img name="Image87" border="0" src="/images/s_m_18.gif" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
            <div id="menu07" style="position:absolute; z-index:1; visibility: hidden"> 
              <table border="0" cellspacing="0" cellpadding="0" height="25">
                <tr>
                  <td width="435" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='width:350'"); %>></td>
                  <td width="65" <%=pageContext.getAttribute("수원3740000-REQ")%>><a href="/formList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image82','','/images/s_m_15_over.gif',1)"><img name="Image82" border="0" src="/images/s_m_15.gif" height="11" alt=""></a></td>
                  <td width="56"><a href="/doList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image83','','/images/s_m_16_over.gif',1)"><img name="Image83" border="0" src="/images/s_m_16.gif" height="11" alt=""></a></td>
                  <td width="56"><a href="/myList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image84','','/images/s_m_17_over.gif',1)"><img name="Image84" border="0" src="/images/s_m_17.gif" height="11" alt=""></a></td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>