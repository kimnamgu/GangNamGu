<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ϰ���
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
<jsp:include page="/include/header_admin.jsp" flush="true" />
<script language="javascript">
menu1.onmouseover();
menu11.onmouseover();

	var Posi = <bean:write name="groupForm" property="posi"/>;
	var cPosi = <bean:write name="groupForm" property="cposi"/>;
	
	function main_submit()
	{
		if (document.forms[1].grplistcd.value.replace(" ","")=="")
		{
			alert("���ڵ带 �Է��Ͽ� �ֽʽÿ�.")
			document.forms[1].grplistcd.focus();
			return;
		}
		if (document.forms[1].grplistnm.value.replace(" ","")=="")
		{
			alert("���ڵ�� �Է��Ͽ� �ֽʽÿ�.")
			document.forms[1].grplsitnm.focus();
			return;
		}

		document.forms[1].submit();
	}
	
	function hyperlink(GRPLISTCD)
	{
			var ppos = parentdiv.scrollTop;	 //�θ�â��ũ��(����)
			var cpos = childdiv.scrollTop;	 //�ڽ�â��ũ��(����)
	    var today = new Date();
			window.open('/grpList.do?grplistcd='+GRPLISTCD+'&posi='+ppos+'&cposi='+cpos+'&time='+today,'_self')
	}
	
	function SetScrollPos(tagDIV)
	{
		var positionTop = 0;
		if (tagDIV != null)
		{
			positionTop = parseInt(tagDIV.scrollTop, 10);
			document.getElementById("HeaderTable").style.top = positionTop;
		}
	}	

</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_02.gif" alt="������ϰ���" /></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="830"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="830"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="379" valign="top"> 
                  <table width="377" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="t3">������� ������</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">����ڵ�</td>
                            <td class="save_t2">������ϸ�Ī</td>
                            <td class="save_t2" width="95">����</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--������� ������ ����Ʈ ���̺�-->
                        <div id="parentdiv" style="position:absolute; width:376; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="groupForm" property="mainlist">
															<logic:iterate id="mainCode" name="groupForm" property="mainlist">
		                            <tr> 
		                              <td class="save_s" width="80"><bean:write name="mainCode" property="grplistcd"/></td>
		                              <td class="save_s2"><a class="saveL" href="javascript:hyperlink('<bean:write name="mainCode" property="grplistcd"/>')"><bean:write name="mainCode" property="grplistnm"/></a></td>
		                              <td class="save_s" width="95">
		                                <a href="javascript:showModalPopup('/grpPopup.do?mode=main_m&grplistcd=<bean:write name="mainCode" property="grplistcd"/>',680,230,0,0)"><img src="/images/common/btn_s_modify.gif" align="absmiddle" alt="����"></a>
		                                <a href="javascript:if(confirm('�����ڵ尡 ��� �����˴ϴ�.\n�׷��� �����Ͻðڽ��ϱ�?')){window.open('/grpDB.do?mode=main_d&grplistcd=<bean:write name="mainCode" property="grplistcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" align="absmiddle" alt="����"></a>
		                              </td>
		                            </tr>
	                            </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="groupForm" property="mainlist">
				                 			<tr>
				                 				<td colspan="3" class="save_s2" align="center">��ϵ� �ڷᰡ �����ϴ�.</td>
				                			</tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
                <td background="/images/admin/sero_line.gif" width="1"></td>
                <td width="440" valign="top"> 
                  <table width="420" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
                      <td class="t3"><font color="#9933CC">[<bean:write name="groupForm" property="grplistnm"/>(<bean:write name="groupForm" property="grplistcd"/>)]</font> �����ڵ�</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">�μ��ڵ�</td>
                            <td class="save_t2">�μ���</td>
                            <td class="save_t2" width="60">����</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top">                       
                        <!--�����ڵ� ����Ʈ ���̺�-->
                        <div id="childdiv" style="position:absolute; width:436; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table id="HeaderTable" width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="groupForm" property="sublist">
															<logic:iterate id="subCode" name="groupForm" property="sublist">
																<tr> 
		                              <td class="save_s" width="80"><bean:write name="subCode" property="code"/></td>
		                              <td class="save_s2"><bean:write name="subCode" property="displayName"/></td>
		                              <td class="save_s" width="60"><a href="javascript:if(confirm('�����Ͻðڽ��ϱ�?')){window.open('/grpDB.do?mode=sub_d&grplistcd=<bean:write name="groupForm" property="grplistcd"/>&seq=<bean:write name="subCode" property="seq"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" align="absmiddle" alt="����"></a>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="groupForm" property="sublist">
				                 			<tr>
				                 				<td colspan="3" class="save_s2" align="center">��ϵ� �ڷᰡ �����ϴ�.</td>
				                			</tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <td colspan="3" height="1" bgcolor="#E8E8E8"></td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <!--�ϴ� ��ư -->
                <td colspan="3" height="2">
                	<a href="javascript:showModalPopup('/grpPopup.do?mode=main_i',680,200,0,0)"><img src="/images/common/btn_master.gif" alt="�����͵��"></a>
                  <a href="javascript:showModalPopup('/grpPopup.do?mode=sub_i',680,400,0,0)"><img src="/images/common/btn_code.gif" alt="���κμ��ڵ���"></a>
                </td>
              </tr>
              <tr> 
                <td colspan="3">&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</table>
<script language="javascript">
	parentdiv.scrollTop = Posi;
	childdiv.scrollTop = cPosi;
</script>
<html:messages id="msg" message="true">
	<script>//alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>