<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� ���ڵ����
 * ����:
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
<title>�Ӽ���� �󼼼���</title>
<script src="/script/common.js"></script>	
<link href="/css/style.css" rel="stylesheet" type="text/css">	
<script language=javascript>
<!--
	var opener = window.dialogArguments;

	var doClose = false;
	function listReload() {
		if ( doClose == true ) {
			var ppos = opener.parentdiv.scrollTop;	 //�θ�â��ũ��(����)
			var cpos = opener.childdiv.scrollTop;	 //�ڽ�â��ũ��(����)			
			opener.location.href = "/attrList.do?gbn=" + document.forms[0].gbn.value +
															"&listcd=" + document.forms[0].listcd.value +
															'&posi='+ppos+'&cposi='+cpos;
			window.close();
		}
	}
	
	function EntSumbit_sub()
	{		
		if (document.forms[0].listdtlnm.value.replace(" ","")=="")
		{
			alert("��Ī�� �Է��Ͽ� �ֽʽÿ�.")
			document.forms[0].listdtlnm.focus();
			return;
		}		

		doClose = true;
		document.forms[0].target = "actionFrame";
		document.forms[0].submit();
	}	
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form method="POST" action="/attrDB.do">
<input type="hidden" name="mode" value="sub_u">
<html:hidden property="gbn"/>
<html:hidden property="listcd"/>
<html:hidden property="seq"/>
<input type="hidden" name="posi">
<input type="hidden" name="cposi">
<!--680*290������� ��â ���� -->
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
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/admin/title_22.gif" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td valign="top"> 
                  <!--�����͵�� ���̺�-->
                  <table width="644" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�Ӽ��ڵ�</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="attrForm" property="listcd"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�Ӽ���Ī</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="attrForm" property="listnm"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr> 
                    	<td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�Ϸù�ȣ</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="attrForm" property="seq"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr>
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��Ī</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="listdtlnm" maxlength="50" style="width:95%"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
										<tr>
                      <td width="120" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">����</td>
                      <td class="bg2"></td>
                      <td class="t1"><html:text property="attr_desc" maxlength="50" style="width:95%"/></td>
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
<!--��ư ���� ���̺�-->
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
                	<a href="#" onclick="EntSumbit_sub()"><img src="/images/common/btn_save2.gif" alt="����"></a>
                  <a href="#" onclick="window.close()"><img src="/images/common/btn_close2.gif" alt="�ݱ�"></a>
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