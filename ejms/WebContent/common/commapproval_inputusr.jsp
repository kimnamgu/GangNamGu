<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정 팝업
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
<%
	String sysdocno = "";
	String retid = "";
	String mode = "";
	
	if(request.getParameter("sysdocno") != null) {
		sysdocno = request.getParameter("sysdocno");
	} else if(request.getAttribute("sysdocno") != null) {
		sysdocno = request.getAttribute("sysdocno").toString();
	}
	
	if(request.getParameter("retid") != null) {
		retid = request.getParameter("retid");
	} else if(request.getAttribute("retid") != null) {
		retid = request.getAttribute("retid").toString();
	}
	
	if(request.getAttribute("mode") != null) {
		mode = request.getAttribute("mode").toString();
	}
%>
<head>
<title>입력담당자 지정</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/dhtmlxtree/dhtmlxtree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/dhtmlxtree/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxtree/dhtmlxtree.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/commapproval_inputusr.js"></script>
<script src="/script/common.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<form name="frm">
<input type="hidden" name="sysdocno" value="<%=sysdocno %>">
<input type="hidden" name="retid" value="<%=retid %>">
<input type="hidden" name="mode" value="<%=mode %>">
<input type="hidden" name="user_id" value="<bean:write name="user_id" scope="session"/>">
<input type="hidden" name="user_name" value="<bean:write name="user_name" scope="session"/>">
<input type="hidden" name="dept_code" value="<bean:write name="dept_code" scope="session"/>">
<input type="hidden" name="class_name" value="<bean:write name="class_name" scope="request"/>">
<input type="hidden" name="origindeptcd" value="<bean:write name="origindeptcd" scope="request"/>">

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
          <td background="/images/common/title_bg.gif" height="38" width="744"><img src="/images/collect/title_29.gif" width="90" height="38" alt="담당자지정"></td>
        </tr>
        <tr> 
          <td height="11" width="744"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="332" height="260" valign="top"> 
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="76"><img src="/images/collect/tab_02.gif" width="74" height="28" alt=""></td>
                      <td width="244" style="letter-spacing:-1;padding-top:6;"><b><font color="#FF5656"></font></b></td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <table width="320" border="0" cellspacing="1" cellpadding="0" height="300" class="bg5">
                          <tr> 
                             <td class="bg4" style="padding:5 5 5 0"><div id="treebox_user" style="width:100%; height:100%; background-color:transparent; overflow:auto;"></div></td>
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
                      <td><img src="/images/common/btn_add.gif" style="cursor:hand" onclick="addUser()" alt=""></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/common/btn_del.gif" style="cursor:hand" onclick="delUser()" alt=""></td>
                    </tr>
                  </table>
                </td>
                <td width="332" valign="top" align="right"> 
                  <!--입력담당자 테이블-->
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="76"><img src="/images/collect/tab_08_off.gif" width="74" height="28" alt=""></td>
                      <td width="244">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
	                      <select id="userList" name="userList" class="text_input" style="width:320; height:300;"  multiple="multiple" ondblclick="delUser()"></select>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="11"></td>
              </tr>
              <tr> 
                <td colspan="3"> 
                  <!--버튼 들어갈 테이블-->
                  <table width="744" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="634"></td>
                      <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
                    </tr>
                    <tr bgcolor="#F6F6F6"> 
                      <td bgcolor="#F6F6F6" width="5"></td>
                      <td bgcolor="#F6F6F6" align="right" height="35"> <img src="/images/common/btn_ok.gif" width="63" height="26" align="absmiddle" style="cursor:hand" onclick="validation()" alt=""> 
                        <img src="/images/common/btn_cancel.gif" width="63" height="26" align="absmiddle" style="cursor:hand" onclick="cancel()" alt=""></td>
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
</form>
<script>
loadUser();
viewUser();
</script>
</body>
</html>