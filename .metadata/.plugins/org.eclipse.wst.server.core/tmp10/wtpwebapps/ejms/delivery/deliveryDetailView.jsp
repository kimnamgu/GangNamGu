<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� ���վ���ڷ�
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
String enddt = "";
String endcomment = "";

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

if(request.getAttribute("enddt") != null) {
	enddt = request.getAttribute("enddt").toString();
}

if(request.getAttribute("endcomment") != null) {
	endcomment = request.getAttribute("endcomment").toString();
}
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/prototype.js"></script>
<script src="/script/delivery.js"></script>
<script>
menu2.onmouseover();
menu22.onmouseover();

function iframeScroll() {
	var frmDoc = formFrame.document;
	if(frmDoc.readyState == "complete") {
		frmDoc.body.scrollTop = parseInt("0<%=(String)request.getParameter("frmScrollTop")%>", 10);
		
		var sh = frmDoc.body.scrollHeight;
		if (sh < formFrameTD.height) {	//��Ĵټ���
			formFrameTD.height = sh;
		}
	}
}
</script>

<div id="comment" style="display:none; position:absolute;">
<table border="1" style="border-collapse:collapse" bgcolor="rgb(255, 255, 200)"><tr><td>
<font face="����" size="2">&nbsp;Ŭ���Ͻø� ���ĵ˴ϴ�.&nbsp;</font>
</td></tr></table>
</div>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/delivery/title_05.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_01.gif" width="94" height="41" alt="���չ�������" style="cursor:hand" onclick="location.href='/deliveryCollDocInfoView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop"></td>
                            <td width="94"><img src="/images/collect/s_m_02_over.gif" width="94" height="41" alt="���վ���ڷ�"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon">
                            	<logic:equal value="true" name="isTargetDept">
																<img src="/images/common/btn_return.gif" alt="�ݼ�" style="cursor:hand" onclick="showModalPopup('/deliveryRetDocView.do?sysdocno=<%=sysdocno%>',680,285,0,0)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<img src="/images/common/btn_baebu.gif" alt="���" style="cursor:hand" onclick="chkDeliveryProc(<%=sysdocno%>)">
															</logic:equal>
                              <img src="/images/common/btn_list.gif" alt="��Ϻ���" style="cursor:hand" onclick="location.href='/deliveryList.do'">
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
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--��������/�˸��� ���̺� ����-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="250"><font color="#4F4F4F"><b>�������� : </b></font><b><font color="#FF5656"><%=enddt%></font></b></td>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="510"><font color="#4F4F4F"><b>�����˸��� : </b></font><font color="#4F4F4F"><%=endcomment%></font></td>
                          </tr>
                        </table>
                        <!--��������/�˸��� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top" height="172" id="formFrameTD"><iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="��ĸ��" onload="iframeScroll()"></iframe></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
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
    <td width="2" valign="top"><img src="/images/common/step_05.gif" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>

<logic:notEmpty name="formatLineForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatLineForm" property="formcomment">
										<bean:define id="formcomment" name="formatLineForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
			<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
			<bean:write name="formatLineForm" property="formbodyhtml" filter="false"/>
			<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatFixedForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatFixedForm" property="formcomment">
										<bean:define id="formcomment" name="formatFixedForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
			<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formbodyhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatBookForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatBookForm" property="formcomment">
										<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">������</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:notEmpty name="fileBookForm" property="listfilebook">
										<logic:iterate id="list" name="fileBookForm" property="listfilebook">
											<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
										</logic:iterate>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
			&nbsp;
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
</logic:notEmpty>

<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>