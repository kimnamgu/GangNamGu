<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���մ������ �˾�
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>���մ�� ����</title>
<base target="_self">
<jsp:include page="/include/processing.jsp" flush="true"/>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/formation/xtree/xtree.css">
<script type="text/javascript" src="/formation/xtree/xtree_ejms.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/xtreeFormation.js"></script>
<script type="text/javascript" src="/script/commsubdept.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/commsubdept" method="post">
<html:hidden property="sysdocno"/>
<html:hidden property="processchk"/>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" width="13" height="13" alt=""></td>
    <td width="744" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" height="464" valign="top"> 
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_19.gif" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="744"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td width="325" height="360" valign="top">
                	<div id="div_display0" style="display:block;"> 
	                  <table width="320" border="0" cellspacing="0" cellpadding="0">
	                    <tr> 
	                      <td width="76"><img src="/images/collect/tab_02.gif" width="74" height="28" alt="������"></td>
	                      <td width="94"><img src="/images/collect/tab_03_off.gif" width="74" height="28" onclick="divDisplay(1);addButton.onclick=addDept1;loadDistribute();" style="cursor:hand" alt="�������"></td>
	                      <td width="150">
												<% if ( (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap()) || "��õ6280000".equals(nexti.ejms.common.appInfo.getRootid()) ){  //��õ�� ����ڱ��� ������� �� ���̵���%>
												<html:select name="commsubdeptForm" property="sch_orggbn" style="width:100px" onchange="orggbnChange(0)">
													<jsp:useBean id="sch_orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
														<bean:define id="user_gbn" name="user_gbn" scope="session"/>
														<jsp:setProperty name="sch_orggbn" property="ccd_cd" value="023"/>
                                                    <% if ( "��õ6280000".equals(nexti.ejms.common.appInfo.getRootid()) ) { //��õ�� ����ڱ��� ������� �� ���̵���%>
                                                        <jsp:setProperty name="sch_orggbn" property="user_gbn" value="001"/>
                                                    <% } else { %>
														<jsp:setProperty name="sch_orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
                                                    <% }  %>
														<jsp:setProperty name="sch_orggbn" property="all_fl" value="ALL"/>
													</jsp:useBean>
													<html:optionsCollection name="sch_orggbn" property="beanCollection"/>
												</html:select>&nbsp;
												<% } else { %>
												<html:hidden property="sch_orggbn"/>
												<% } %>
	                      </td>
	                    </tr>
	                    <tr> 
	                      <td colspan="3"> 
	                        <table width="320" border="0" cellspacing="1" cellpadding="0" height="300" class="bg5">
	                          <tr> 
                              <td class="bg4" style="padding:0 0 0 0"><div id="treebox_formation" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto;"></div></td>
	                          </tr>
	                        </table>
	                        <table width="320" border="0" cellspacing="0" cellpadding="0" height="28" class="bg5" align="center">
	                          <tr align="middle"> 
	                          	<td width="5"></td>
	                            <td width="60"><select name="searchType" style="width:98%;"><option value="2">����</option><option value="1">�μ�</option></td>
	                            <td width="205"><input type="text" name="searchValue" style="width:99%;" onkeypress="if(event.keyCode==13){event.returnValue=false;searchFormation();}"/></td>
	                            <td width="45"><a href="javascript:searchFormation()"><img src="/images/common/btn_s_search.gif" border="0" alt="�˻�"></a></td>
	                            <td width="5"></td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </table>
	              	</div>
                	<div id="div_display1" style="display:none;"> 
	                  <table width="320" border="0" cellspacing="0" cellpadding="0">
	                    <tr> 
	                      <td width="76"><img src="/images/collect/tab_02_off.gif" width="74" height="28" onclick="divDisplay(0);addButton.onclick=addDept;loadFormation();" style="cursor:hand" alt="������"></td>
	                      <td width="94"><img src="/images/collect/tab_03.gif" width="74" height="28" alt="�������"></td>
	                      <td width="150">
												<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
												<html:select name="commsubdeptForm" property="grp_orggbn" style="width:100px" onchange="orggbnChange(1)">
													<jsp:useBean id="grp_orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
														<bean:define id="user_gbn" name="user_gbn" scope="session"/>
														<jsp:setProperty name="grp_orggbn" property="ccd_cd" value="023"/>
														<jsp:setProperty name="grp_orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
														<jsp:setProperty name="grp_orggbn" property="all_fl" value="ALL"/>
													</jsp:useBean>
													<html:optionsCollection name="grp_orggbn" property="beanCollection"/>
												</html:select>&nbsp;
												<% } else { %>
												<html:hidden property="grp_orggbn"/>
												<% } %>
												<a href="javascript:showModalPopup('/grpPopup.do?mode=sub_i&userModifyMode=y',680,400,0,0)"><img src="/images/common/btn_s_modify.gif" align="absmiddle" alt="����"></a>
	                    </tr>
	                    <tr> 
	                      <td colspan="3"> 
	                        <table width="320" border="0" cellspacing="1" cellpadding="0" height="250" class="bg5">
	                          <tr> 
                              <td class="bg4" style="padding:0 0 0 0"><div id="treebox_distribute" style="width:100%; height:100%; position:absolute; padding:5 5 5 5; overflow:auto;"></div></td>
	                          </tr>
	                        </table>
	                        <table width="320" border="0" cellspacing="0" cellpadding="0" height="80">
	                          <tr valign="bottom"> 
                              <td><select id="grpdetail" name="grpdetail" class="text_input" style="width:100%;" size="5"></select></td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </table>
	              	</div>
                </td>
                <td width="94"> 
                  <!--�߰� ���� ���̺�-->
                  <table width="94" border="0" cellspacing="0" cellpadding="0" align="center">
                  	<tr id="subdeptChk"> 
                      <td><input type="checkbox" style="border:0;background-color:transparent;" id="subdeptId"><label for="subdeptId">�����μ����� </label></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td align="center"><img src="/images/common/btn_add.gif" style="cursor:hand" onclick="addDept()" name="addButton" alt=""></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td align="center"><img src="/images/common/btn_del.gif" style="cursor:hand" onclick="delDept()" alt=""></td>
                    </tr>
                  </table>
                </td>
                <td width="325" valign="top" align="right"> 
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="76"><img src="/images/collect/tab_04_off.gif" width="74" height="28" alt=""></td>
                      <td width="244" valign="bottom"></td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <table width="320" border="0" cellspacing="0" cellpadding="0"class="bg5" align="center">
                        	<tr>
                        		<td colspan="5"><select id="deptList" name="deptList" class="text_input" style="width:100%;height:300" size="14" multiple="multiple" ondblclick="delDept();"></select></td>
                        	</tr>
                          <tr align="middle" height="28"> 
                          	<td width="5"></td>
                            <td width="60"><b style="color:darkslategray">�������</b></td>
                            <td width="205"><input type="text" name="groupName" style="width:98%;" onkeypress="if(event.keyCode==13){event.returnValue=false;saveDistribute('insert', document.forms[0].groupName)}"/></td>
                            <td width="45"><a href="javascript:saveDistribute('insert', document.forms[0].groupName)"><img src="/images/common/btn_s_save.gif" border="0" alt="����"></a></td>
                            <td width="5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="11"></td>
              </tr>
              <tr> 
                <td colspan="3"> 
                  <!--��ư �� ���̺�-->
                  <table width="744" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="500"></td>
                      <td bgcolor="#F6F6F6" height="5" width="134"></td>
                      <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
                    </tr>
                    <tr bgcolor="#F6F6F6"> 
                      <td bgcolor="#F6F6F6" width="5"></td>
                      <td bgcolor="#F6F6F6" width="500" style="padding-left:5"><b style="color:#FF5656">1. ���մ�� �ߺ������� �ڵ����� ����ó�� �� ��ϵ˴ϴ�.<br>2. ����ڸ� �����ϸ� �ش� ����ڰ� �Է´���ڷ� �����Ǿ� �ڵ� ��ε˴ϴ�.</b></td>
                      <td bgcolor="#F6F6F6" align="center" height="35">
                      	<a href="javascript:validation()"><img src="/images/common/btn_ok.gif" alt="Ȯ��"></a>
                      	<a href="javascript:cancel()"><img src="/images/common/btn_cancel.gif" alt="���"></a>
                      </td>
                      <td width="5"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
                      <td bgcolor="#F6F6F6" height="5" width="500"></td>
                      <td bgcolor="#F6F6F6" height="5" width="134"></td>
                      <td><img src="/images/common/btn_r_04.gif" width="5" height="5" alt=""></td>
                    </tr>
                  </table>
                </td>
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
    <td width="13"><img src="/images/common/popup_r_03.gif" width="13" height="13" alt=""></td>
    <td width="744" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
<iframe name="bgProcFrame" width="0" height="0" title=""></iframe>
<%
	String user_gbn = "";	
	String rep_dept = "";
	if(!session.getAttribute("user_gbn").equals("001")){
	 	user_gbn = (String)session.getAttribute("user_gbn");
        if ( !("��õ6280000".equals(nexti.ejms.common.appInfo.getRootid())) ) { //��õ�� ����ڱ��� ������� �� ���̵���
            rep_dept = (String)session.getAttribute("rep_dept");
        }
	}
%>
<script language="Javascript" type="text/javascript">
document.forms[0].sch_orggbn.value = "<%=user_gbn%>";
loadFormation('<%=rep_dept%>');	//������ ��ȸ
viewDept();
</script>
</body>
</html>