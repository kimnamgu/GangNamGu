<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �α��� ��������,��û��
 * ����:
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
<script language="javascript">
<!--
document.cookie = "test=cookiesEnabled";
if ( document.cookie.indexOf("test=") == -1 ) {
	alert("�������� ��Ű�� �������� �ʽ��ϴ�. \r\n �ͽ��÷η��� �����޴��� ���ͳݿɼ�>> ����������>> ��������>> ��޿ɼ��� �ڵ����ó�� üũó�� �Ͽ� �ֽʽÿ�.");
}

function autotab(original,destination){
    if (original.getAttribute&&original.value.length==original.getAttribute("maxlength"))
        destination.focus()
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<html:form action="/loginRchReqOK">
<%
	String retUrl = "";
	if(request.getAttribute("retUrl") != null){
		retUrl = request.getAttribute("retUrl").toString();
	}
%>
<html:hidden property="retUrl" value="<%=retUrl%>"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr> 
    <td> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        	<td align="center">����ȭ������ �Ѿ�� �ʰ� ����ȭ���� �ݺ��ɰ�� ���������� �����Ͽ� �ֽʽÿ�. <br>(�������� �����޴� &gt; ���ͳݿɼ� &gt; �������� &gt; ���������̵�(����) &gt; ���  &gt; �ڵ�������Űó������ üũ����  &gt; Ȯ�ι�ư)</td>
        </tr>
        <tr> 
          <td height="268" bgcolor="#F5F4F3"> 
            <table width="821" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td width="481"><img src="/images/login/img.jpg" alt="���վ����� �����Ѽ��� ������ȭ�� ����� ǥ��ȭ�Ͽ� �� �μ��� ������ �ִ� �����͸� ���� ��� Ȱ���� �ش�ȭ�մϴ�."></td>
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
                            <td colspan="3" align="center"><img src="/client/images/logo.gif" alt="�ΰ�"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="55" valign="bottom"><img src="/images/login/login_title.gif" alt="�α��� Ÿ��Ʋ"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="8"></td>
                          </tr>
                          <tr> 
                            <td colspan="3"> 
                              <table width="280" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/id.gif" alt="����� ���̵�"></td>
                                  <td width="90" height="22"> 
                                    <html:text property="userId" style="width:99%;" onkeydown="javascript:enterKey_focus('forms[0].password');" tabindex="1"/>
                                  </td>
                                  <td rowspan="2" width="105" align="center">
                                  	<a href="javascript:check_submit();"><img src="/images/login/login_btn.gif" alt="�α���" align="absmiddle"></a>
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="85" height="22"><img src="/images/login/pw.gif" alt="��й�ȣ"></td>
                                  <td width="90" height="22"> 
                                    <html:password property="password" style="width:99%;" onkeydown="javascript:if (event.keyCode == 13) {check_submit();}" redisplay="false" tabindex="2"/>
                                  </td>
                                </tr>
                                <tr id="researchOption" style="padding-top:3">
                                	<td width="85">&nbsp;</td>
                                  <td width="115" colspan="2">
													        	<html:select name="loginForm" property="usersex" title="����" tabindex="3">
													            <option value="M" <logic:equal name="loginForm" property="usersex" value="M">selected</logic:equal>>����</option>
													            <option value="F" <logic:equal name="loginForm" property="usersex" value="F">selected</logic:equal>>����</option>
													        	</html:select>
													        	<html:select name="loginForm" property="userage" title="����" tabindex="4">
													            <option value="20" <logic:equal name="loginForm" property="userage" value="20">selected</logic:equal>>20��</option>	
																			<option value="30" <logic:equal name="loginForm" property="userage" value="30">selected</logic:equal>>30��</option>	
																			<option value="40" <logic:equal name="loginForm" property="userage" value="40">selected</logic:equal>>40��</option>	
																			<option value="50" <logic:equal name="loginForm" property="userage" value="50">selected</logic:equal>>50��</option>	
																			<option value="60" <logic:equal name="loginForm" property="userage" value="60">selected</logic:equal>>60��</option>	
																			<option value="70" <logic:equal name="loginForm" property="userage" value="70">selected</logic:equal>>70��</option>	
																			<option value="80" <logic:equal name="loginForm" property="userage" value="80">selected</logic:equal>>80��</option>	
													          </html:select>
													          <script>if(location.href.indexOf("research")==-1){researchOption.style.display="none";}</script>
													      	</td>
                                </tr>
                                <tr> 
                                  <td width="85">&nbsp;</td>
                                  <td width="115" colspan="2"><html:multibox property="setfl" style="border:0;background-color:transparent;" value="1"/>��������</td>
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
</html:form>

<script language="javascript">
	loadFocus('<bean:write name="loginForm" property="userId"/>');
</script>
<html:messages id="msg" message="true">
  <script language="javascript"><bean:write name="msg"/></script>
</html:messages>
</body>
</html>