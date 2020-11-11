<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���ո���
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
<%
	String sysdocno = "";
	
	if(request.getParameter("sysdocno") != null) {
		sysdocno = request.getParameter("sysdocno");
	}else if(request.getAttribute("sysdocno") != null) {
		sysdocno = request.getAttribute("sysdocno").toString();
	}
%>

<html>
<head>
<title>����ó��</title>
<base target="_self">
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/Calendar.js"></script>
<script language="javascript">
	function closeCheck()
	{	
		if(document.collprocForm.radiochk[3].checked){
			if(document.collprocForm.closedate.value == ""){
					alert("����� ���ڰ� �Էµ��� �ʾҽ��ϴ�.");
					return;
			}
		}
		var cnf = window.confirm("����ó���Ͻðڽ��ϱ�?");
		
		if(cnf) {
			window.document.collprocForm.submit();		
		} else return;
	}
	
	function cancel(){
		self.close();
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/collprocClose.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<table width="680" border="0" cellspacing="0" cellpadding="0">
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
    <td width="644" valign="top"> 
      <table width="644" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_39.gif" width="83" height="38" alt="����ó��"></td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3" class="list_bg2"></td>
              </tr>
              <tr> 
                <td width="150" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�����ڷ��������</td>
                <td class="bg2"></td>
                <td class="t1">
                  <html:radio property="radiochk" value="1" style="border:0;background-color:transparent;" styleId="r1"/><label for="r1">����</label>&nbsp;&nbsp;
                  <html:radio property="radiochk" value="4" style="border:0;background-color:transparent;" styleId="r4"/><label for="r4">����/�Է��ڿ��԰���</label>&nbsp;&nbsp;
                  <html:radio property="radiochk" value="2" style="border:0;background-color:transparent;" styleId="r2"/><label for="r2">�����</label>&nbsp;&nbsp;
                  <html:radio property="radiochk" value="3" style="border:0;background-color:transparent;" styleId="r3"/><label for="r3">���������</label>
                  <html:text property="closedate" readonly="true" style="width:65;"/>
                  <a href="javascript:Calendar_D(document.forms[0].closedate)"><img src="/images/common/icon_date.gif" align="absmiddle" alt="��¥����"></a>
                </td>
              </tr>
              <tr> 
                <td colspan="3" class="bg1"></td>
              </tr>
              <tr> 
                <td width="150" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�����ڷ�˻�Ű����</td>
                <td class="bg2"></td>
                <td class="t1"><html:text property="searchkey" style="width:98%;"/></td>
              </tr>
              <tr> 
                <td colspan="3" class="bg1"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" width="644"></td>
        </tr>
        <tr> 
          <td height="10" width="644"></td>
        </tr>
        <tr> 
          <td valign="top" height="45"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="634"></td>
                <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#F6F6F6"> 
                <td bgcolor="#F6F6F6" width="5"></td>
                <td bgcolor="#F6F6F6" height="30" align="center">����ó���� �Ͻðڽ��ϱ�?&nbsp;&nbsp;&nbsp;
                	<a href="javascript:closeCheck()"><img src="/images/common/btn_yes.gif" align="absmiddle" alt="��"></a>
                  <a href="javascript:cancel()"><img src="/images/common/btn_no2.gif" align="absmiddle" alt="�ƴϿ�"></a>
                </td>
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
</body>
</html>