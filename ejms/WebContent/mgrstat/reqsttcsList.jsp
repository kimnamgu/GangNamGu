<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�������Ȳ
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
<jsp:include page="/include/header_admin.jsp" flush="true"/>
<script src="/script/Calendar.js"></script>
<script>
menu2.onmouseover();
menu22.onmouseover();

function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.search_frdate;			//���Ⱓ����
	var b = frm.search_todate;			//���Ⱓ��
	
	if (a.onpropertychange == null) {
		a.onpropertychange = setSearchDate;
		b.onpropertychange = setSearchDate;
	} else if (event.propertyName.toLowerCase() == "value") {
		var src = event.srcElement;
		if (src == a) {
			if (a.value > b.value) {
				b.onpropertychange = null;
				b.value = a.value;
				b.onpropertychange = setSearchDate;
			}
		} else if (src == b) {
			if (a.value > b.value || a.value == "") {
				a.onpropertychange = null;
				a.value = b.value;
				a.onpropertychange = setSearchDate;
			}
		}
	}
}
	function changeOrg() {
		var frm = document.forms[0];
		try{
			frm.orggbn_dt.value = "";
		} catch (Exception) { }
		frm.submit();
	}
</script>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_10.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
                        <!--��������/�˸��� ���̺� ����-->
                        <html:form method="POST" action="/reqsttcsList">
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                          	<td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="60" height="20"><font color="#4F4F4F">��������</font></td>
                            <td height="20" width="370">
															<% if ( "001".equals(session.getAttribute("user_gbn")) && nexti.ejms.common.appInfo.isSidoldap() ) { %>
															<html:select property="orggbn" style="width:100px;background-color:white" onchange="changeOrg();">
																<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
																	<bean:define id="user_gbn" name="user_gbn" scope="session"/>
																	<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
																	<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
																	<jsp:setProperty name="orggbn" property="all_fl" value="ALL"/>
																</jsp:useBean>
																<html:optionsCollection name="orggbn" property="beanCollection" />
															</html:select>&nbsp;
															<% } else { %>
															<html:hidden property="orggbn"/>
															<% ((nexti.ejms.statistics.form.ReqsttcsForm)request.getAttribute("reqsttcsForm")).setOrggbn((String)session.getAttribute("user_gbn")); %>
															<% } %>
															<logic:notEqual name="reqsttcsForm" property="orggbn" value="">
																<html:select property="orggbn_dt" style="width:250px;background-color:white" onchange="submit()">
																	<jsp:useBean id="orggbn_dt" class="nexti.ejms.list.form.DeptDetailListForm">
																		<bean:define id="orggbn" name="reqsttcsForm" property="orggbn"/>
																		<bean:define id="rep_dept" name="rep_dept" scope="session"/>
																		<bean:define id="user_id" name="user_id" scope="session"/>
																		<jsp:setProperty name="orggbn_dt" property="orggbn" value="<%=(String)orggbn%>"/>	
																		<jsp:setProperty name="orggbn_dt" property="rep_dept" value="<%=(String)rep_dept%>"/>
																		<jsp:setProperty name="orggbn_dt" property="user_id" value="<%=(String)user_id%>"/>
																		<jsp:setProperty name="orggbn_dt" property="all_fl" value="ALL"/>	
																	</jsp:useBean>
																	<html:optionsCollection name="orggbn_dt" property="beanCollection" />
																</html:select>
															</logic:notEqual>
                            </td>
                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="60" height="20"><font color="#4F4F4F">��豸��</font></td>
                            <td height="20" width="100">
                            	<html:select property="gbn" style="width:80;background-color:white" onchange="submit()">
																<html:option value="0">�μ�</html:option>
																<html:option value="1">�����</html:option>
																<html:option value="2">��û���</html:option>
                            	</html:select>
                            </td>
                            <td></td>
                       		</tr>
                       		<tr>
                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="60" height="20"><font color="#4F4F4F">���Ⱓ</font></td>
                            <td height="20" width="220">
															<html:text property="search_frdate" style="width:70;background-color:white" readonly="false"/>
					                  	<img src="/images/common/icon_date.gif" align="absmiddle" alt="��¥" style="cursor:hand" onclick="Calendar_D(forms[0].search_frdate);"> ~
					                		<html:text property="search_todate" style="width:70;background-color:white" readonly="false"/>
					                   	<img src="/images/common/icon_date.gif" align="absmiddle" alt="��¥" style="cursor:hand" onclick="Calendar_D(forms[0].search_todate);">
                            </td>
                            <% if ( nexti.ejms.common.appInfo.isOutside() ) { %>
	                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
	                            <td width="60" height="20"><font color="#4F4F4F">��������</font></td>
	                            <td height="20" width="100">
																<html:select property="search_range" style="width:80px;background-color:white">
																	<jsp:useBean id="rangeselect" class="nexti.ejms.list.form.RangeListForm">
																		<jsp:setProperty name="rangeselect" property="all_fl" value="ALL"/>
																	</jsp:useBean>
																	<html:optionsCollection name="rangeselect" property="beanCollection"/>
							                 	</html:select>
	                            </td>
	                        	<% } else { %>
	                        		<td colspan="3"></td>
	                        	<% } %>
                            <td height="20"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" align="absmiddle" alt="�˻�"></a></td>
                          </tr>
                        </table>
                        </html:form>
                        <!--��������/�˸��� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--�˻� ����Ʈ ���̺� ����-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0" id="tbl">
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr>
                      <logic:equal name="reqsttcsForm" property="gbn" value="0">
                    		<td class="list_t" width="100">����</td>
	                      <td class="list_t" width="240">�μ���</td>
	                      <td class="list_t" width="240">��û����ȸ��</td>
	                      <td class="list_t" width="240">����ȸ��</td>
                    	</logic:equal>
                    	<logic:equal name="reqsttcsForm" property="gbn" value="1">
                    		<td class="list_t" width="100">����</td>
	                      <td class="list_t" width="240">�μ���</td>
	                      <td class="list_t" width="240">����ڸ�</td>
	                      <td class="list_t" width="240">��û����ȸ��</td>
                    	</logic:equal>
                    	<logic:equal name="reqsttcsForm" property="gbn" value="2">
                    		<td class="list_t" width="100">�����μ�</td>
	                      <td class="list_t" width="200">��û����</td>
	                      <td class="list_t" width="280">��û����</td>
	                      <td width="240">
		                      <table width="240" border="0" cellspacing="0" cellpadding="0">
		                      	<tr>
		                      		<td class="list_t" width="80">������</td>
		                      		<td class="list_t" width="80">������</td>
		                      		<td class="list_t" width="80">����ȸ��</td>
		                      	</tr>
		                      </table>
	                      </td>
                    	</logic:equal>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
                    </tr>
                    <logic:notEmpty name="reqsttcsForm" property="list">
											<logic:iterate id="list" name="reqsttcsForm" property="list" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <logic:equal name="reqsttcsForm" property="gbn" value="0">
		                    		<td class="list_no"><bean:write name="list" property="seqno"/></td>
			                      <td class="list_l"><bean:write name="list" property="deptnm"/></td>
			                      <td class="list_l"><bean:write name="list" property="reqcount"/></td>
			                      <td class="list_l"><bean:write name="list" property="anscount"/></td>
		                    	</logic:equal>
		                    	<logic:equal name="reqsttcsForm" property="gbn" value="1">
		                    		<td class="list_no"><bean:write name="list" property="seqno"/></td>
			                      <td class="list_l"><bean:write name="list" property="deptnm"/></td>
			                      <td class="list_l"><bean:write name="list" property="usernm"/></td>
			                      <td class="list_l"><bean:write name="list" property="reqcount"/></td>
		                    	</logic:equal>
	                      	<logic:equal name="reqsttcsForm" property="gbn" value="2">               	
	                      		<td class="list_l" width="100"><bean:write name="list" property="deptnm"/></td>
			                      <td class="list_l" width="200"><bean:write name="list" property="title"/></td>
			                      <td class="list_l" width="280">
			                      	<logic:notEmpty name="list" property="summary">
					                    	<bean:define id="summary" name="list" property="summary"/>
					                    	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString()) %>
				                    	</logic:notEmpty>
			                      </td>
			                      <td width="240">
				                      <table width="240" border="0" cellspacing="0" cellpadding="0">
				                      	<tr>
				                      		<td class="list_l" width="80"><bean:write name="list" property="strdt"/></td>
				                      		<td class="list_l" width="80"><bean:write name="list" property="enddt"/></td>
				                      		<td class="list_l" width="80"><bean:write name="list" property="anscount"/></td>
				                      	</tr>
				                      </table>
			                      </td>
		                    	</logic:equal>
		                    </tr>
		                    <tr> 
		                      <td colspan="4" class="list_bg2"></td>
		                    </tr>
                    	</logic:iterate>
                    	<bean:define id="list" name="reqsttcsForm" property="list" type="java.util.ArrayList"/>
                    	<tr <% if (list.size() % 2 != 0 & false) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
	                      <td class="list_no"><b>�� ��</b></td>
	                      <td class="list_l">&nbsp;</td>
	                      <logic:equal name="reqsttcsForm" property="gbn" value="0">
	                      	<td class="list_l"><bean:write name="reqsttcsForm" property="totreqcnt"/></td>
	                      	<td class="list_l"><bean:write name="reqsttcsForm" property="totanscnt"/></td>
	                      </logic:equal>
	                      <logic:equal name="reqsttcsForm" property="gbn" value="1">
		                      <td class="list_l"></td>
		                      <td class="list_l"><bean:write name="reqsttcsForm" property="totreqcnt"/></td>
	                      </logic:equal>
	                      <logic:equal name="reqsttcsForm" property="gbn" value="2">
		                      <td class="list_l">��û����ȸ�� : <bean:write name="reqsttcsForm" property="totreqcnt"/></td>
	                      	<td class="list_l">����ȸ�� : <bean:write name="reqsttcsForm" property="totanscnt"/></td>
	                      </logic:equal>	                    
	                    </tr>
	                    <tr> 
	                      <td colspan="4" class="list_bg2"></td>
	                    </tr>
                    </logic:notEmpty>
                    <logic:empty name="reqsttcsForm" property="list">
                    	<tr> 
	                      <td colspan="4" class="list_l" align="center">��ȸ�� ����� �����ϴ�.</td>
	                    </tr>
	                    <tr> 
	                      <td colspan="4" class="list_bg2"></td>
	                    </tr>
                    </logic:empty>
                  </table>
                  <!--�˻� ����Ʈ ���̺� ��-->
                </td>
              </tr>
              <tr> 
                <td height="40" align="right">
                	<form name="formatDataForm" method="post" target="actionFrame">
										<input type="hidden" name="formatData">
										<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="�����ٿ�ε�">
									</form>
								</td>
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
<script>
setSearchDate();	//���Ⱓ ��ȿ��üũ �̺�Ʈ ����
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>