<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ���� ���չ�������
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

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script type="text/javascript" src="/script/exhibit.js"></script>
<script>
menu4.onmouseover();
menu44.onmouseover();
</script>

<form name="frm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/exhibit/title_02.gif" alt=""></td>
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
                  <bean:define id="chrgusrcd" name="commCollDocInfoForm" property="chrgusrcd"/>	
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/collect/s_m_02.gif" width="94" height="41" alt="���վ���ڷ�" style="cursor:hand" onclick="viewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', 'frmScrollTop=<%=request.getParameter("frmScrollTop")%>')"></td>
                            <td><img src="/images/collect/s_m_01_over.gif" width="94" height="41" alt="���չ�������"></td>
                            <td align="right" valign="bottom"  style="padding:0 0 1 0;" id="changeicon" width="90">
                            	<% if (session.getAttribute("isSysMgr").equals("001")){ %>
                              <img src="/images/common/btn_shareclear.gif" alt="��������" style="cursor:hand" onclick="javascript:closeCheck('<%=sysdocno%>')">
                              <% } else { %>
                              	<% if (session.getAttribute("user_id").equals(chrgusrcd.toString())){ %>
                              	<img src="/images/common/btn_shareclear.gif" alt="��������" style="cursor:hand" onclick="javascript:closeCheck('<%=sysdocno%>')">
                              	<% } %>
                              <% } %>
                            </td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon" width="90">
                              <img src="/images/common/btn_list.gif" alt="��Ϻ���" style="cursor:hand" onclick="location.href='/exhibitList.do'">
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
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">����</td>
                      <td class="bg2"></td>
                      <td class="t1" width="390"><bean:write name="commCollDocInfoForm" property="doctitle"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�ڷ������</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1"><bean:write name="commCollDocInfoForm" property="basicdate1"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���չ�����ȣ</td>
                      <td class="bg2"></td>
                      <td class="t1" width="390"><bean:write name="commCollDocInfoForm" property="docno"/></td>
                      <td class="bg2"></td>
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���չ����߼�����</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1"><bean:write name="commCollDocInfoForm" property="submitdt"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���մ����</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="coldeptnm"/>&nbsp;&nbsp;&nbsp;<bean:write name="commCollDocInfoForm" property="chrgunitnm"/>&nbsp;&nbsp;&nbsp;<bean:write name="commCollDocInfoForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;������ȭ:<bean:write name="commCollDocInfoForm" property="coldepttel"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="opennm"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���ñٰ�</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><bean:write name="commCollDocInfoForm" property="basis"/></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���հ���</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      <logic:notEmpty name="commCollDocInfoForm" property="summary">
	                    	<bean:define id="summary" name="commCollDocInfoForm" property="summary"/>
	                    	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString()) %>
                    	</logic:notEmpty></td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><font color="#FF5656">
                      	<b><bean:write name="commCollDocInfoForm" property="enddt"/></b></font>&nbsp;&nbsp;&nbsp;
						                        �����˸��� : <bean:write name="commCollDocInfoForm" property="endcomment"/>
						          </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">���մ��</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<bean:define id="targetdeptnm" name="commCollDocInfoForm" property="targetdeptnm"/>
                      	<input type=text readonly style="width:98%;border:0px;background:none" name="tgtdeptnm" value="<%=targetdeptnm%>">	
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">��Ÿ�ɼ�</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1"><html:checkbox name="commCollDocInfoForm" property="openinput" value="Y" style="border:0;background-color:transparent;" onclick="this.checked=!this.checked"/>�Է½� Ÿ�μ� �ڷẸ��</td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="bg1"></td>
                    </tr>
                    <tr  valign="middle"> 
                      <td width="129" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">÷������</td>
                      <td class="bg2"></td>
                      <td colspan="7" class="t1">
                      	<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="commCollDocInfoForm" property="filenm"/>&fileName=<bean:write name="commCollDocInfoForm" property="originfilenm"/>"><bean:write name="commCollDocInfoForm" property="originfilenm"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="9" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr><!--ó����Ȳ ���̺� �� -->
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
</form>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>