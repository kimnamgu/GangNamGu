<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����������չ��� ���չ�������
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
String formseq = "";
String formkind = "";

if(request.getParameter("sysdocno") != null) {
	sysdocno = request.getParameter("sysdocno");
} else if(request.getAttribute("sysdocno") != null) {
	sysdocno = request.getAttribute("sysdocno").toString();
}

if(request.getParameter("formseq") != null) {
	formseq = request.getParameter("formseq");
} else if(request.getAttribute("formseq") != null) {
	formseq = request.getAttribute("formseq").toString();
}

if(request.getParameter("formkind") != null) {
	formkind = request.getParameter("formkind");
}
%>

<bean:define id="docstate" name="commCollDocInfoForm" property="docstate"/>
<%
String procImg = "";
if (docstate.equals("02") || docstate.equals("03")) {
	procImg = "/images/common/step_02.gif";
} else if (docstate.equals("04") || docstate.equals("05")) {
	procImg = "/images/common/step_03.gif";
}
%>

<jsp:include page="/include/header_user.jsp" flush="true"/>

<script src="/script/prototype.js"></script>
<script src="/script/collproc.js"></script>
<script>
menu1.onmouseover();
menu11.onmouseover();

function modify(){
	location.href = '/collprocInfoModify.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=<%=request.getParameter("frmScrollTop")%>';
}
</script>

<form name="frm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_05.gif" alt=""></td>
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
                  <!--��� ���ø޴� ���̺�-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/collect/s_m_01_over.gif" width="94" height="41" alt="���չ�������""></td>
                            <td width="94"><img src="/images/collect/s_m_02.gif" width="94" height="41" alt="���վ���ڷ�" style="cursor:hand" onclick="viewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=<%=request.getParameter("frmScrollTop")%>')"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon">
                           	<%  if( docstate.equals("06")){ %>
					                  	<img src="/images/common/btn_release.gif" alt="��������" style="cursor:hand" onclick="chkCancelProc1(<%=sysdocno%>)">
						                <%  } else if( docstate.equals("05")|| docstate.equals("04")){ %>
						                	<img src="/images/common/btn_end.gif" alt="���ո���" style="cursor:hand" onclick="popup_open(<%=sysdocno%>)">
						                <%	} else if( docstate.equals("02") || docstate.equals("03")){ %>        	
						                  <img src="/images/common/btn_cancel2.gif" alt="���չ����߼����" style="cursor:hand" onclick="chkCancelProc(<%=sysdocno%>, <%=formseq%>, '<%=formkind%>', 0, 'collprocdataview')">
						                <%  } %>
                              <img src="/images/common/btn_list.gif" width="86" height="24" alt="��Ϻ���" style="cursor:hand" onclick="location.href='/collprocList.do?docstate=0'">
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="5"><img src="/images/common/select_right.gif" width="5" height="41" alt=""></td>
                    </tr>
                  </table>
                  <!--��� ���ø޴� ���̺�-->
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr><!--ó����Ȳ ���̺� ����-->
                <td>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">����</td>
                      <td class="bg2"></td>
                      <td class="t1" width="390"><bean:write name="commCollDocInfoForm" property="doctitle"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�ڷ������</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="commCollDocInfoForm" property="basicdate1"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���չ�����ȣ</td>
                      <td class="bg2"></td>
                      <td class="t1" width="390"><bean:write name="commCollDocInfoForm" property="docno"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���չ����߼�����</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="commCollDocInfoForm" property="submitdt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���մ����</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><bean:write name="commCollDocInfoForm" property="coldeptnm"/>&nbsp;&nbsp;&nbsp;<% if (request.getAttribute("chrgunitnm") != null){ %><bean:write name="commCollDocInfoForm" property="chrgunitnm"/>&nbsp;&nbsp;&nbsp;<% } %><bean:write name="commCollDocInfoForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;������ȭ:<bean:write name="commCollDocInfoForm" property="coldepttel"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���ñٰ�</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><bean:write name="commCollDocInfoForm" property="basis"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���հ���</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1">
	                      <logic:notEmpty name="commCollDocInfoForm" property="summary">
		                    	<bean:define id="summary" name="commCollDocInfoForm" property="summary"/>
		                    	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString()) %>
	                    	</logic:notEmpty>
	                    </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><font color="#FF5656">
                      	<b><bean:write name="commCollDocInfoForm" property="enddt"/></b></font>&nbsp;&nbsp;&nbsp;
						                        �����˸��� : <bean:write name="commCollDocInfoForm" property="endcomment"/>
						          </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���մ��</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><bean:write name="commCollDocInfoForm" property="targetdeptnm"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��Ÿ�ɼ�</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:checkbox name="commCollDocInfoForm" property="openinput" value="Y" style="border:0;background-color:transparent;" onclick="this.checked=!this.checked"/>�Է½� Ÿ�μ� �ڷẸ��</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr  valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">÷������</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1">
                      	<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="commCollDocInfoForm" property="filenm"/>&fileName=<bean:write name="commCollDocInfoForm" property="originfilenm"/>"><bean:write name="commCollDocInfoForm" property="originfilenm"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--ó����Ȳ ���̺� �� -->
              <tr>
              	<td>&nbsp;</td>
              </tr>
              <tr><!--ó����Ȳ ���̺� ����-->
                <td>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="11" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td class="s1" colspan="3"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�������</td>
                      <td class="bg2"></td>
                      <td class="t1" width="200"><bean:write name="commCollDocInfoForm" property="docstatenm"/></td>
                      <td class="bg2"></td>
                      <td class="s1" width="100"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">����Ͻ�</td>
                      <td class="bg2"></td>
                      <td class="t1"><bean:write name="commCollDocInfoForm" property="deliverydt"/></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr> 
                      <td class="bg1"" colspan="11"></td>
                    </tr>
                    <tr> 
                      <td class="s1" rowspan="5" width="72">
                      	<img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">������Ȳ
	                      <% if ( nexti.ejms.common.appInfo.isMessanger() ) { %><br>/�޼�������<% } %>
                      </td>
                      <td class="bg2" rowspan="5"></td>
                      <td class="s1" width="75" style="padding:0 0 0 0;text-align:center"><a href="javascript:showModalPopup('/collprocDeptStatus.do?sysdocno=<%=sysdocno%>&viewmode=all',680,590,0,0)"><img src="/images/common/btn_status_all.gif" width="70" height="19" alt="������"></a></td> 
                      <td class="bg2"></td>
                      <td class="t1" colspan="7"><bean:write name="commCollDocInfoForm" property="tcnt"/>�� �μ�</td>
                    </tr>
                    <tr> 
                      <td class="bg1"" colspan="10"></td>
                    </tr>
                    <tr> 
                      <td class="s1" width="75" style="padding:0 0 0 0;text-align:center"><a href="javascript:showModalPopup('/collprocDeptStatus.do?sysdocno=<%=sysdocno%>&viewmode=comp',680,590,0,0)"><img src="/images/common/btn_status_comp.gif" width="70" height="19" alt="����Ϸ�"></a></td>
                      <td class="bg2"></td>
                      <td class="t1" colspan="7"><bean:write name="commCollDocInfoForm" property="scnt"/>�� �μ�</td>
                    </tr>
                    <tr> 
                      <td class="bg1"" colspan="9"></td>
                    </tr>
                    <tr> 
                      <td class="s1" width="75" style="padding:0 0 0 0;text-align:center"><a href="javascript:showModalPopup('/collprocDeptStatus.do?sysdocno=<%=sysdocno%>&viewmode=nocomp',680,590,0,0)"><img src="/images/common/btn_status_nocomp.gif" width="70" height="19" alt="������"></a></td>
                      <td class="bg2"></td>
                      <td class="t1" colspan="7"><bean:write name="commCollDocInfoForm" property="fcnt"/>�� �μ�</td>
                    </tr>
                    <tr> 
                      <td colspan="11" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--ó����Ȳ ���̺� �� -->
              <tr> 
                <td height="50" align="right" valign="bottom"><img src="/images/common/btn_modify.gif" alt="�����ϱ�" style="cursor:hand" onclick="modify()"></td>
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
    <td width="2" valign="top"><img src="<%=procImg%>" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>
</form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>