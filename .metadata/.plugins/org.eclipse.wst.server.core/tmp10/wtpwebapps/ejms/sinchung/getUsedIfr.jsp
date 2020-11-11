<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ������û���������� ���
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
<%@ page import="java.util.Calendar" %>
<%
Calendar today = Calendar.getInstance();

int currentyear = today.get(Calendar.YEAR);
String selyear = "";

if(request.getAttribute("currentyear") != null) {
	selyear = request.getAttribute("currentyear").toString();
}
%>

<html>
<head>
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="580" border="0" cellspacing="0" cellpadding="0">
<html:form method="post" action="/getUsedIfr">
<html:hidden property="deptcd"/>
	<tr> 
	  <td> 
	    <table width="580" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
	      <tr> 
	        <td bgcolor="#FFFFFF"> 
	          <!--������û������Ʈ ���̺� ����-->
	          <table width="570" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
	            <tr> 
	              <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
	              <td width="55"><font color="#4F4F4F">�ۼ��⵵</font></td>
	              <td width="85">
									<html:select property="syear" styleClass="select" style="width:75px;background-color:white" value="<%=selyear%>" onchange="forms[0].submit()">
										<jsp:useBean id="yearselect" class="nexti.ejms.list.form.YearListForm">
											<jsp:setProperty name="yearselect" property="currentyear" value="<%=currentyear%>"/>
											<jsp:setProperty name="yearselect" property="gap" value="1"/>
										</jsp:useBean>
										<html:optionsCollection name="yearselect" property="beanCollection"/>
									</html:select>
	              </td>
	              <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
	              <td width="35"><font color="#4F4F4F">����</font></td>
	              <td width="210"><html:text property="search_title" style="width:200px;background-color:white"/></td>
	              <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="forms[0].submit()" alt=""></td>
	              <td>&nbsp;</td>
	            </tr>
	          </table>
	          <!--������û������Ʈ ���̺� ��-->
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
      <table width="580" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="4" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="4" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="50">����</td>
          <td class="list_t">��û������</td>
          <td class="list_t" width="70">��û�Ⱓ</td>
          <td class="list_t" width="100"><% if (nexti.ejms.common.appInfo.isOutside()){ %>��û����<% } %></td>
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
        
 		<logic:notEmpty name="sinchungListForm" property="sinchungList">
      <logic:iterate id="list" name="sinchungListForm" property="sinchungList" indexId="i">
	      <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
          <td class="list_no" align="center"><%=Integer.parseInt(request.getAttribute("tbunho").toString())-i.intValue() %></td>
          <td class="list_s"><a class="list_s2" href="#" onclick="parent.showModalPopup('/preview.do?reqformno=<bean:write name="list" property="reqformno"/>&usedFL=Y&viewfl=Y',705,650,0,0);"><bean:write name="list" property="title"/></a></td>
          <td class="list_s" align="center"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></td>
          <td class="list_s" align="center">
          	<% if (nexti.ejms.common.appInfo.isOutside()){ %>
							<logic:equal name="list" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%><bean:define id="rangedetail" name="list" property="rangedetail"/>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)</logic:equal>
	           	<logic:notEqual name="list" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%></logic:notEqual>
	          <% } %>
					</td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
      </logic:iterate>
		</logic:notEmpty>
			<logic:empty name="sinchungListForm" property="sinchungList">
				<tr>
          <td colspan="4" class="list_l">�˻��� ����� �����ϴ�</td>
        </tr>
        <tr> 
          <td colspan="4" class="list_bg2"></td>
        </tr>
			</logic:empty>
      </table>
    </td>
  </tr>
  <tr>
  	<td align="right"><br><a href="/formList.do" target="_parent"><img src=/images/common/btn_myresearch2.gif border="0" alt="��û�������ΰ���"></a></td>
  </tr>
  <tr> 
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/getUsedIfr.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
</html:form>
</table>
</body>
</html>
