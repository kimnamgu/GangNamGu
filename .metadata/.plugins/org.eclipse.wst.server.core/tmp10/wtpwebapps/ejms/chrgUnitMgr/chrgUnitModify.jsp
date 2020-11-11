<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리 담당단위수정
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
<title>담당단위수정</title>
<script src="/script/common.js"></script>	
<link href="/css/style.css" rel="stylesheet" type="text/css">	
<script language=javascript>
<!--
	var opener = window.dialogArguments;
	
	var doClose = false;
	function listReload() {
		if ( doClose == true ) {
			opener.location.href = "chrgUnitList.do?orggbn=" + document.forms[0].orggbn.value +
																							"&dept_id=" + document.forms[0].dept_id.value;
			window.close();
		}
	}
	
	function EntSumbit()
	{		
		if (document.forms[0].chrgunitnm.value.replace(" ","")=="")
		{
			alert("담당단위 명칭을 입력하여 주십시오.")
			document.forms[0].chrgunitnm.focus();
			return;
		}		
		var ppos = opener.parentdiv.scrollTop;	 //부모창스크롤(좌측)
		document.forms[0].posi.value = ppos;

		var cpos = opener.childdiv.scrollTop;	 //자식창스크롤(우측)
		document.forms[0].cposi.value = cpos;

		doClose = true;
		document.forms[0].target = "actionFrame";
		document.forms[0].submit();
	}	
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form method="POST" action="/chrgUnitDB.do">
<input type="hidden" name="mode" value="u">				
<html:hidden property="orggbn"/>
<html:hidden property="dept_id"/>
<html:hidden property="chrgunitcd"/>
<input type="hidden" name="posi">
<input type="hidden" name="cposi">
<!--680*290사이즈로 새창 오픈 -->
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
    <td width="644" valign="top" height="199"> 
      <table width="644" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/admin/title_12.gif" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td valign="top"> 
                  <!--마스터등록 테이블-->
                  <table width="644" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서코드</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="chrgUnitForm" property="dept_id"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">부서명칭</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="chrgUnitForm" property="dept_name"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr> 
                    	<td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">담당단위코드</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="chrgUnitForm" property="chrgunitcd"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr>
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">담당단위명칭</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="chrgunitnm" maxlength="100" style="width:95%" maxlength="15"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr>
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">정렬순서</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="ord" maxlength="100" style="width:95%" maxlength="3" onkeydown="key_num()"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
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
</table>
<!--버튼 들어가는 테이블-->
<table width="680" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="644" valign="top"> 
      <table width="644" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <table width="644" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="634"></td>
                <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#F6F6F6"> 
                <td bgcolor="#F6F6F6" width="5"></td>
                <td bgcolor="#F6F6F6" align="right" height="35">
                	<a href="#" onclick="EntSumbit()"><img src="/images/common/btn_save2.gif" alt="저장"></a>
                  <a href="#" onclick="window.close()"><img src="/images/common/btn_close2.gif" alt="닫기"></a>
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
<iframe name="actionFrame" width="0" height="0" title="" onload="listReload()"></iframe>
</body>
</html>