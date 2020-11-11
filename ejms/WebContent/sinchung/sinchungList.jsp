<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ���
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
	String basePath = nexti.ejms.common.appInfo.getWeb_address();
	String outsideUrl = nexti.ejms.common.appInfo.getOutsideweburl();
%>

<%
	if ( "����3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		String user_id = (String)session.getAttribute("user_id");
		String delivery_yn = nexti.ejms.user.model.UserManager.instance().getUserInfo(user_id).getDelivery_yn();
		delivery_yn = nexti.ejms.util.Utils.nullToEmptyString(delivery_yn);
		
		if ( !("Y".equals(delivery_yn) || "001".equals((String)session.getAttribute("isSysMgr"))) ) {
			pageContext.setAttribute("����3740000", "style='display:none'"); 
		}
	}
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script language="javascript">
menu6.onmouseover();
menu66.onmouseover();

function copy_clip(strData, rchno){
	try {
		var result = false;
		if ( strData == "reqForm.do" ) {
			result = clipboardData.setData("Text", '<%=basePath%>'+strData+'?reqformno='+rchno);
		} else {
			result = clipboardData.setData("Text", '<%=outsideUrl%>'+strData+'?reqformno='+rchno);
		}
		if ( result == true ) {
			alert("URL�� ����Ǿ����ϴ�.\n\n���ϴ� ���� �ٿ��ֱ� �ϼ���.");
		} else {
			throw "";
		}
	} catch (exception) {
		try {
			var winName = String(new Date().getMilliseconds());
			var msg = "������� ���������� ��û�ּҺ��� ����� ����� �� �����ϴ�.<br>�Ʒ� �ּҸ� �����ϼ���.(Ctrl+C)<p>";
			var width = 500;
			var height = 100;
			var left = (screen.width - width) / 2;
			var top = (screen.height - height) / 2;
			var option =
				"width=" + width + ", height="  + height + ", left=" + left + ", top=" + top + "," +
				"location=no, menubar=no, resizable=no, scrollbars=no, status=no, " +
				"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";
	
			if(strData == "reqForm.do"){
				window.open("about:blank",winName,option).document.write(msg + '<%=basePath%>'+strData+'?reqformno='+rchno);
			} else {
				window.open("about:blank",winName,option).document.write(msg + '<%=outsideUrl%>'+strData+'?reqformno='+rchno);
			}
		} catch (exception) {}
	}
}	

function click_submit(actionpath) {
	document.forms[0].action = actionpath;
	document.forms[0].submit();
}

//�˻� ��ư Ŭ����
function research(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//�˻� ��ư Ŭ����
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//������ ������ ȣ���
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}
 
//���������� �Ѿ���� ������ : ������ ����Ŭ���� ȣ�� �޼ҵ� 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";
		
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/formList" method="POST">
<html:hidden property="sch_old_deptcd"/>
<html:hidden property="sch_old_userid"/>
<html:hidden property="initentry"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_12.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
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
                            <td width="110"><html:text property="search_title" style="width:100px;background-color:white"/></td>
                					  <% if (session.getAttribute("isSysMgr").equals("001")){ %>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="35"><font color="#4F4F4F">�μ�</font></td>
                            <td width="200"><html:text property="sch_deptnm" style="width:200;background-color:white" onkeydown="sch_old_deptcd.value=''"/></td>
                            <td width="25"><img src="/images/common/search_ico.gif" width="21" height="19" style="cursor:hand" onclick="fTree(document.forms[0], '<%=request.getAttribute("user_gbn")%>', '<%=request.getAttribute("orgid")%>')" alt=""></td>
                           	<td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="45"><font color="#4F4F4F">�����</font></td>
                            <td width="90"><html:text property="sch_usernm" style="width:80;background-color:white" onkeydown="sch_old_userid.value=''"/></td>
                            <% } %>
                            <td width="45"><img src="/images/common/btn_search.gif" width="42" height="19" style="cursor:hand" onclick="getSearch(document.forms[0], '<%=session.getAttribute("isSysMgr")%>')" align="absmiddle" alt=""></td>
                            <td width="130"><html:checkbox property="procGbn" value="0" style="border:0;background-color:transparent;" onclick="sinchungListForm.submit()"/>&nbsp;��ó���Ǹ� �˻�</td>
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
                      <td colspan="8" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
										</tr> 
										<tr>
								      <td width="50" class="list_t" align="center">����</td>
								      <td class="list_t" align="center">��û���</td>
								      <td width="70" class="list_t" align="center">��û�Ⱓ</td>
								      <td width="100" class="list_t" align="center"><% if (nexti.ejms.common.appInfo.isOutside()){ %>��û����<% } %></td>
								      <td width="55" class="list_t" align="center">��ó����</td>
								      <td width="80" class="list_t" align="center" ondblclick="prompt('�ܺθ� ��û�� ����Ʈ','<%=outsideUrl%>outsideReqList.do?uid=tester&resident=y&committee=y')">��û�ּ�</td>
								      <td width="70" class="list_t" align="center">-</td>
								      <td width="70" class="list_t" align="center">-</td>
								    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="8" height="1"></td>
                    </tr>
								<logic:notEmpty name="sinchungListForm" property="sinchungList">
								  <logic:iterate id="list" name="sinchungListForm" property="sinchungList" indexId="i">
										<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
                      <td align="center" class="list_no"><%=Integer.parseInt(request.getAttribute("tbunho").toString())-i.intValue()%></td>
								      <td class="list_s"><a class="list_s2" href="javascript:click_submit('/formView.do?reqformno=<bean:write name="list" property="reqformno"/>&viewfl=Y&originuserid=<bean:write name="list" property="chrgusrid"/>')"><bean:write name="list" property="title"/></a></td>
								      <td align="center" class="list_s">
											<logic:greaterEqual name="list" property="tday" value="0">
			              		<bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/>
			              	</logic:greaterEqual>
			              	<logic:lessThan name="list" property="tday" value="0">
			              		<font color="color:ff0000">����</font>
			              	</logic:lessThan>
											</td>
								      <td align="center" class="list_s">
								      <% if (nexti.ejms.common.appInfo.isOutside()){ %>
								      	<logic:equal name="list" property="range" value="1">
								      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
								      	</logic:equal>
								      	<logic:equal name="list" property="range" value="2">
								      		<bean:define id="rangedetail" name="list" property="rangedetail"/>
								      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", "2")%>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)
								      	</logic:equal>
								      <% } %>
								      </td>
								      <td align="center" class="list_no"><bean:write name="list" property="notproc"/></td>
								      <td align="center" class="list_s">
											<logic:equal name="list" property="range" value="1">
	                   		<a class="list_s2" href="javascript:copy_clip('reqForm.do','<bean:write name="list" property="reqformno"/>')">��û�ּҺ���</a>
	                   	</logic:equal>
	                   	<logic:notEqual name="list" property="range" value="1">
	                   		<a class="list_s2" href="javascript:copy_clip('outsideReqView.do','<bean:write name="list" property="reqformno"/>')">��û�ּҺ���</a>
	                   	</logic:notEqual>
											</td>
								      <td align="center" class="list_s"><a class="list_s2" href="javascript:showPopup('/preview.do?reqformno=<bean:write name="list" property="reqformno"/>&viewfl=Y',705,650,0,0)">�̸�����</a></td>
								      <td align="center" class="list_s"><a class="list_s2" href="javascript:showPopup('/jupsuList.do?reqformno=<bean:write name="list" property="reqformno"/>',705,650,0,0)">��������</a></td>
                    </tr>
                    <tr> 
                      <td colspan="8" class="list_bg2"></td>
                    </tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="sinchungListForm" property="sinchungList">
										<tr>
											<td colspan="8" align="center" class="list_s">�˻��� ����� �����ϴ�</td>
										</tr>
										<tr>
								      <td colspan="8" class="list_bg2"></td>
								    </tr>
								</logic:empty>
                  </table>
                  <!--�˻� ����Ʈ ���̺� ��-->
                </td>
              </tr>
              <tr> 
                <td height="30" align="right" valign="bottom"><br>
                	<% if ( "����3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
                		<a href="/getUsedList.do" <%=pageContext.getAttribute("����3740000")%>><img src="/images/common/btn_form.gif" border="0" alt="���� ��û��� ��������"></a>
										<a href="/formWrite.do" <%=pageContext.getAttribute("����3740000")%>><img src="/images/common/btn_new_form.gif" border="0" alt="�� ��û��� �ۼ�"></a>
                	<% } else { %>
	                	<% if ( request.getAttribute("initentry").equals("second") ) { %>
											<a href="/formList.do"><img src="/images/common/btn_myresearch2.gif" border="0" alt="��û�������ΰ���"></a>
	                	<% } else { %>
		                	<a href="/getUsedList.do" <%=pageContext.getAttribute("����3740000")%>><img src="/images/common/btn_form.gif" border="0" alt="���� ��û��� ��������"></a>
											<a href="/formWrite.do" <%=pageContext.getAttribute("����3740000")%>><img src="/images/common/btn_new_form.gif" border="0" alt="�� ��û��� �ۼ�"></a>
										<% } %>
									<% } %>
								</td>
              </tr>
              <tr>
                <td height="45" align="center">
                	<bean:define id="initentry" name="sinchungListForm" property="initentry"/>
                	<bean:define id="search_title" name="sinchungListForm" property="search_title"/>
                	<bean:define id="procGbn" name="sinchungListForm" property="procGbn"/>
                	<bean:define id="sch_old_deptcd" name="sinchungListForm" property="sch_old_deptcd"/>
                	<bean:define id="sch_old_userid" name="sinchungListForm" property="sch_old_userid"/>
                	<bean:define id="sch_deptnm" name="sinchungListForm" property="sch_deptnm"/>
                	<bean:define id="sch_usernm" name="sinchungListForm" property="sch_usernm"/>
                	<%=nexti.ejms.util.commfunction.procPage_AddParam("/formList.do","initentry="+(String)initentry+"&search_title="+(String)search_title+"&procGbn="+(String)procGbn+"&sch_old_deptcd="+(String)sch_old_deptcd+"&sch_old_userid="+(String)sch_old_userid+"&sch_deptnm="+(String)sch_deptnm+"&sch_usernm="+(String)sch_usernm,null,request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%>
                </td>
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