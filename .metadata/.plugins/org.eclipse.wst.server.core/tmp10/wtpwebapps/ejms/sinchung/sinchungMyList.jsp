<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ����û�� ���
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

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/sinchung.js"></script>
<script>
menu6.onmouseover();
menu66.onmouseover();
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/myList" method="post">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_18.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_04.gif" width="94" height="41" alt="��û�ϱ�" style="cursor:hand" onclick="location='/doList.do'"></td>
                            <td width="94"><img src="/images/collect/s_m_05_over.gif" width="94" height="41" alt="����û����"></td>
                            <td>&nbsp;</td>
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
                <td>&nbsp;</td>
              </tr>
							<tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--����Ʈ �˻� ���̺� ����-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">����</font></td>
                            <td width="210"><html:text property="title" style="width:200;background-color:white"/></td>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="forms[0].submit()" alt=""></td>
                            <td width="130"><html:checkbox property="gbn" value="0" style="border:0;background-color:transparent;" onclick="forms[0].submit()"/>&nbsp;��û���ΰ͸� ����</td>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        <!--����Ʈ �˻� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td class="result">* �� <font class="result_no"><%=request.getAttribute("totalCount")%>��</font>�� �˻��Ǿ����ϴ�</td>
              </tr>
              <tr> 
                <td> 
                  <!--�˻� ����Ʈ ���̺� ����-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="4" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" height="1"></td>
										</tr> 
										<tr>
								      <td width="50" class="list_t" align="center">����</td>
								      <td class="list_t" align="center">��û�����</td>
								      <td width="80" class="list_t" align="center">��û�Ͻ�</td>
								      <td width="80" class="list_t" align="center">����</td>
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
								<logic:notEmpty name="dataListForm" property="dataList">
								  <logic:iterate id="list" name="dataListForm" property="dataList" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
                      <td align="center" class="list_no"><%=Integer.parseInt(request.getAttribute("tbunho").toString())-i.intValue()%></td>
								      <td class="list_s"><a class="list_s2" href="javascript:showPopup('/reqModify.do?reqformno=<bean:write name="list" property="reqformno"/>&reqseq=<bean:write name="list" property="reqseq"/>', 700, 680, 0, 0);"><bean:write name="list" property="title"/> [������ȣ:<bean:write name="list" property="reqseq"/>]</a></td>
								      <td align="center" class="list_s"><bean:write name="list" property="crtdt"/></td>
								      <td align="center" class="list_s"><bean:write name="list" property="statenm"/></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="dataListForm" property="dataList">
										<tr>
											<td colspan="4" align="center" class="list_s">�˻��� ����� �����ϴ�</td>
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
                <td height="30">&nbsp;</td>
              </tr>
              <tr>
                <td height="45" align="center"><%=nexti.ejms.util.commfunction.procPage("/myList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
            	</tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>