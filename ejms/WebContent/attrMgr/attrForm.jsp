<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ���
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
<script src="/script/common.js"></script>
<script language="javascript">
menu1.onmouseover();
menu11.onmouseover();

	var Posi = <bean:write name="attrForm" property="posi"/>;
	var cPosi = <bean:write name="attrForm" property="cposi"/>;
	
	function main_submit()
	{
		if (document.forms[1].listcd.value.replace(" ","")=="")
		{
			alert("�Ӽ��ڵ带 �Է��Ͽ� �ֽʽÿ�.")
			document.forms[1].listcd.focus();
			return;
		}
		if (document.forms[1].listnm.value.replace(" ","")=="")
		{
			alert("�Ӽ���Ī�� �Է��Ͽ� �ֽʽÿ�.")
			document.forms[1].listnm.focus();
			return;
		}
		
		var ppos = parentdiv.scrollTop;	 //�θ�â��ũ��(����)
		var cpos = childdiv.scrollTop;	 //�ڽ�â��ũ��(����)
		document.forms[1].action += '?posi='+ppos+'&cposi='+cpos
		document.forms[1].submit();
	}

	function sub_submit()
	{
		if (document.forms[2].listcd.value.replace(" ","")=="")
		{
			alert("�Ӽ������ ��� �Ǵ� �����Ͽ� �ֽʽÿ�.")
			document.forms[1].listcd.focus();
			return;
		}
	
		if (document.forms[2].seq.value.replace(" ","")=="")
		{
			alert("�Ϸù�ȣ�� �Է��Ͽ� �ֽʽÿ�.")
			document.forms[2].seq.focus();
			return;
		}
		if (document.forms[2].listdtlnm.value.replace(" ","")=="")
		{
			alert("��Ī�� �Է��Ͽ� �ֽʽÿ�.")
			document.forms[2].listdtlnm.focus();
			return;
		}

		var ppos = parentdiv.scrollTop;	 //�θ�â��ũ��(����)
		var cpos = childdiv.scrollTop;	 //�ڽ�â��ũ��(����)
		document.forms[2].action += '?posi='+ppos+'&cposi='+cpos
		document.forms[2].submit();
	}
	
	function hyperlink(CCD_CD)
	{
			var ppos = parentdiv.scrollTop;	 //�θ�â��ũ��(����)
			var cpos = childdiv.scrollTop;	 //�ڽ�â��ũ��(����)
	    var today = new Date();
			window.open('/attrList.do?gbn=<bean:write name="attrForm" property="gbn"/>&listcd='+CCD_CD+'&posi='+ppos+'&cposi='+cpos+'&time='+today,'_self')
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
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_06.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="830"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="830"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3"> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3x 0;"> 
                        <!--�˻� ���̺� ����-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                            <td width="5"></td>
                            <td height="20" width="250"> 
			                      	<html:form method="POST" action="/attrList">
				                      	<html:select property="gbn" style="width:130px;background-color:white" onchange="submit();">
																	<html:option value="a">�����ڿ� �ڵ���</html:option>
																	<html:option value="s">�ý��ۿ� �ڵ���</html:option>
																</html:select>
															</html:form>
                            </td>
                            <td width="500"></td>
                          </tr>
                        </table>
                        <!--�˻� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <td width="379" valign="top"> 
                  <table width="377" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="t3">�Ӽ����</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t2" width="80">�Ӽ��ڵ�</td>
                            <td class="save_t2">�Ӽ���Ī</td>
                            <td class="save_t2" width="95">����</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--�Ӽ���� ����Ʈ ���̺�-->
                        <div id="parentdiv" style="position:absolute; width:376; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          	<logic:notEmpty name="attrForm" property="mainlist">
															<logic:iterate id="mainattr" name="attrForm" property="mainlist">
																<tr> 
		                              <td class="save_s" width="80"><bean:define id="code2" name="mainattr" property="listcd" type="java.lang.String"/><%=code2.substring(2)%></td>
		                              <td class="save_s2"><a class="saveL" href="javascript:hyperlink('<bean:write name="mainattr" property="listcd"/>')"><bean:write name="mainattr" property="listnm"/></a></td>
		                              <td class="save_s" width="95">
		                              	<logic:equal name="attrForm" property="gbn" value="s">
																			<a href="javascript:if(confirm('�ý��ۿ� �ڵ��Դϴ�.\n������ �ý��ۿ� ������ ���� �� �ֽ��ϴ�.\n\n�׷��� �����Ͻðڽ��ϱ�?')){showModalPopup('/attrPopup.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=main_s&listcd=<bean:write name="mainattr" property="listcd"/>',680,200,0,0)}"><img src="/images/common/btn_s_modify.gif" alt="����" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('�ý��ۿ� �ڵ��Դϴ�.\n������ �ý��ۿ� ������ ���� �� �ֽ��ϴ�.\n������ �����ڵ尡 ��� �����˴ϴ�.\n\n�׷��� �����Ͻðڽ��ϱ�?')){window.open('/attrDB.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=main_d&listcd=<bean:write name="mainattr" property="listcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" alt="����" align="absmiddle"></a>
						                        </logic:equal>
						                        <logic:notEqual name="attrForm" property="gbn" value="s">
						                        	<a href="javascript:showModalPopup('/attrPopup.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=main_s&listcd=<bean:write name="mainattr" property="listcd"/>',680,200,0,0)"><img src="/images/common/btn_s_modify.gif" alt="����" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('�����ڵ尡 ��� �����˴ϴ�.\n�׷��� �����Ͻðڽ��ϱ�?')){window.open('/attrDB.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=main_d&listcd=<bean:write name="mainattr" property="listcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" alt="����" align="absmiddle"></a>
						                       	</logic:notEqual>	
		                              </td>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="attrForm" property="mainlist">
				                  		<tr> 
	                              <td class="save_s2" colspan="3" align="center">��ϵ� �ڷᰡ �����ϴ�.</td>
	                            </tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td> 
                        <!--�Ӽ���� ���� ���̺�-->
                        <html:form method="POST" action="/attrDB.do">
	                      <input type="hidden" name="gbn" value="<bean:write name="attrForm" property="gbn"/>">
	                      <input type="hidden" name="mode" value="main_i">
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t" width="80">�Ӽ��ڵ�</td>
                            <td class="save_t">�Ӽ���Ī</td>
                            <td class="save_t" width="95">����</td>
                          </tr>
                          <tr> 
                            <td class="save_s"><input type="text" name="listcd" style="width:95%" maxlength="3" onkeydown="key_num()"></td>
                            <td class="save_s"><input type="text" name="listnm" style="width:95%" maxlength="25" onkeydown="key_entertotab()"><input type="text" style="width:0" onfocus="main_submit()"></td>
                            <td class="save_s"><a href="javascript:main_submit()"><img src="/images/common/btn_s_save.gif" alt="����" align="absmiddle"></a>
                          </tr>
                        </table>
                        </html:form>
                      </td>
                    </tr>
                  </table>
                </td>
                <td background="/images/admin/sero_line.gif" width="1"></td>
                <td width="440" valign="top"> 
                  <table width="420" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
                      <td class="t3">
                      	<logic:notEmpty name="attrForm" property="listnm">
			                		<font color="#9933CC">[<bean:write name="attrForm" property="listnm"/>(<bean:define id="code1" name="attrForm" property="listcd" type="java.lang.String"/><%=code1.substring(2)%>))]</font> �Ӽ���� ��
			                	</logic:notEmpty>
			                	<logic:empty name="attrForm" property="listnm">
			                		�Ӽ���� ��
			                	</logic:empty>
                      </td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t2" width="60">�Ϸù�ȣ</td>
                            <td class="save_t2">��Ī</td>
                            <td class="save_t2" width="100">����</td>
                            <td class="save_t2" width="95">����</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--�Ӽ���ϻ� ����Ʈ ���̺�-->
                        <div id="childdiv" style="position:absolute; width:436; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table id="HeaderTable" width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          	<logic:notEmpty name="attrForm" property="sublist">
															<logic:iterate id="subattr" name="attrForm" property="sublist">
																<tr> 
		                              <td class="save_s" width="60"><bean:write name="subattr" property="seq"/></td>
		                              <td class="save_s2"><bean:write name="subattr" property="listdtlnm"/></td>
		                              <td class="save_s2" width="100"><bean:write name="subattr" property="attr_desc"/></td>
		                              <td class="save_s" width="95">
		                              	<logic:equal name="attrForm" property="gbn" value="s">
						                        	<a href="javascript:if(confirm('�ý��ۿ� �ڵ��Դϴ�.\n������ �ý��ۿ� ������ ���� �� �ֽ��ϴ�.\n\n�׷��� �����Ͻðڽ��ϱ�?')){showModalPopup('/attrPopup.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=sub_s&listcd=<bean:write name="attrForm" property="listcd"/>&seq=<bean:write name="subattr" property="seq"/>',680,290,0,0)}"><img src="/images/common/btn_s_modify.gif" alt="����" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('�ý��ۿ� �ڵ��Դϴ�.\n������ �ý��ۿ� ������ ���� �� �ֽ��ϴ�.\n\n�׷��� �����Ͻðڽ��ϱ�?')){window.open('/attrDB.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=sub_d&listcd=<bean:write name="attrForm" property="listcd"/>&seq=<bean:write name="subattr" property="seq"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" alt="����" align="absmiddle"></a>
						                        </logic:equal>
						                        <logic:notEqual name="attrForm" property="gbn" value="s">
						                        	<a href="javascript:showModalPopup('/attrPopup.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=sub_s&listcd=<bean:write name="attrForm" property="listcd"/>&seq=<bean:write name="subattr" property="seq"/>',680,290,0,0)"><img src="/images/common/btn_s_modify.gif" alt="����" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('�����Ͻðڽ��ϱ�?')){window.open('/attrDB.do?gbn=<bean:write name="attrForm" property="gbn"/>&mode=sub_d&listcd=<bean:write name="attrForm" property="listcd"/>&seq=<bean:write name="subattr" property="seq"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" alt="����" align="absmiddle"></a>
						                       	</logic:notEqual>	
		                              </td>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="attrForm" property="sublist">
				                  		<tr> 
	                              <td class="save_s2" colspan="4" align="center">��ϵ� �ڷᰡ �����ϴ�.</td>
	                            </tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td> 
                        <!--�Ӽ���ϻ� ���� ���̺�-->
                        <html:form method="POST" action="/attrDB.do">
	                      <input type="hidden" name="gbn" value="<bean:write name="attrForm" property="gbn"/>">
	                      <input type="hidden" name="listcd" value="<bean:write name="attrForm" property="listcd"/>">
	                      <input type="hidden" name="mode" value="sub_i">
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t" width="60">�Ϸù�ȣ</td>
                            <td class="save_t">��Ī</td>
                            <td class="save_t" width="100">����</td>
                            <td class="save_t" width="95">����</td>
                          </tr>
                          <tr> 
                            <td class="save_s"><input type="text" name="seq" style="width:95%" maxlength="5" onkeydown="key_num()"></td>
                            <td class="save_s"><input type="text" name="listdtlnm" style="width:95%" maxlength="25" onkeydown="key_entertotab()"></td>
                            <td class="save_s"><input type="text" name="attr_desc" style="width:95%" maxlength="25" onkeydown="key_entertotab()"><input type="text" style="width:0" onfocus="sub_submit()">
                            <td class="save_s"><a href="javascript:sub_submit()"><img src="/images/common/btn_s_save.gif" alt="����" align="absmiddle"></a>
                          </tr>
                        </table>
                        </html:form>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
			  			<tr> 
                <td colspan="3" height="20"></td>
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