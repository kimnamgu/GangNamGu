<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���չ����ۼ� ���չ�������
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
<jsp:include page="/include/header_user.jsp" flush="true"/>
<jsp:include page="/include/processing.jsp" flush="true"/>

<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/Calendar.js"></script>
<script src="/script/common.js"></script>
<script language="javascript">
function click_move(actionpath) {
    document.location.href = actionpath;
}
function resetFile(objName) {
    document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}

function click_apprep() {
    var frm = document.forms[0];
    var title = frm.title;
    var strdt = frm.strdt;
    var enddt = frm.enddt;
    var chrgusr = frm.chrgusrcd;
    
    if(title.value.trim() == "") {
        alert("������� �Է��ϼ���.");
        title.focus();
        return;
    }
    
    if(strdt.value.trim() == "") {
        alert("����������� �Է��ϼ���.");
        return;
    }
    if(enddt.value.trim() == "") {
        alert("����������� �Է��ϼ���.");
        return;
    }
    if(chrgusr.value.trim() == ""){
        alert("����ڸ� �������ּ���.");
        return;
    }
    
		//HTML�����Ϳ��� [�̸�����->�ҽ�����]�� ��ȯ�� �߸��� �±׺κ� �ڵ� ������
    var fck = FCKeditorAPI.GetInstance("content");
    if(fck.EditMode == 0) {
			fck.SwitchEditMode();		//EditMode=0(�̸�����)�� �� SwitchEditMode()�� ȣ������ ������ ���� �߻�
		}
		frm.content.value = fck.GetXHTML(true);

    frm.submit();
}
function setSearchDate() {
	var frm = document.forms[0];
	var strdt = frm.strdt;          //�Ⱓ����
	var enddt = frm.enddt;         //�Ⱓ����
        
	if (strdt.onpropertychange == null) {
	   strdt.onpropertychange = setSearchDate;
	   enddt.onpropertychange = setSearchDate;
	} else if (event.propertyName.toLowerCase() == "value") {
	   var src = event.srcElement;
	   if (src == strdt) {
	       if (strdt.value > enddt.value) {
	           enddt.onpropertychange = null;
	           enddt.value = strdt.value;
	           enddt.onpropertychange = setSearchDate;
	       }
	   } else if (src == enddt) {
	       if (strdt.value > enddt.value || strdt.value == "") {
	           strdt.onpropertychange = null;
	           strdt.value = enddt.value;
	           strdt.onpropertychange = setSearchDate;
	       }
	   }
	}
}
/* ���� �˻������� ��Ϻ��� (JHKim, 13.05.14) */
function back2list(){
    var searchyear = 'searchyear='+document.forms[0].searchyear.value;
    var searchstrdt = 'searchstrdt='+document.forms[0].searchstrdt.value;
    var searchenddt = 'searchenddt='+document.forms[0].searchenddt.value;
    var searchstatus = 'searchstatus='+document.forms[0].searchstatus.value;
    var searchdeptcd = 'searchdeptcd='+document.forms[0].searchdeptcd.value;
    var searchchrgunitcd = 'searchchrgunitcd='+document.forms[0].searchchrgunitcd.value;
    var searchgubun = 'searchgubun='+document.forms[0].searchgubun.value;
    var searchtitle = 'searchtitle='+document.forms[0].searchtitle.value;
    var dept = 'searchdept='+document.forms[0].searchdeptcd.value;
    var orggbn = 'orggbn='+document.forms[0].orggbn.value;
    var page = 'page='+document.forms[0].page.value;
    
    var path = '/workplanList.do?'+searchyear+'&'+searchstrdt+'&'+searchenddt+'&'+searchstatus+'&'+searchdeptcd+'&'+searchchrgunitcd+'&'+searchgubun+'&'+searchtitle+'&'+dept+'&'+orggbn+'&'+page;
    
    click_move(path);
}

</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/workplanSave" method="post" enctype="multipart/form-data">
<html:hidden property="planno"/>
<html:hidden property="mode"/>
<script>
</script>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/workplan/title_02.gif" alt=""></td>
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
                            <td width="94"><img src="/images/workplan/s_m_01.gif" width="94" height="41" alt="�����ȹ"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;">
                            	<img src="/images/workplan/btn_plnsave.gif" width="86" height="24" alt="��ȹ����" style="cursor:hand" onclick="click_apprep()"> 
                            	<img src="/images/workplan/btn_back.gif" width="86" height="24" alt="���ư���" style="cursor:hand" onclick="history.back()">
                              <img src="/images/common/btn_list.gif" width="86" height="24" alt="��Ϻ���" style="cursor:hand" onclick="back2list();"></td>
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
                  <!--�����չ��� �ۼ��ϱ� ���̺�-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�� �� ��</td>
                      <td class="bg2"></td>
                      <td class="t1" width="715"><html:text property="title" maxlength="100" style="width:99%;"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">�� �� ��</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<bean:write name="workplanForm" property="upperdeptnm"/>&nbsp;&nbsp;
                      	<bean:write name="workplanForm" property="deptnm"/>&nbsp;&nbsp;
                      	<logic:notEqual name="workplanForm" property="chrgunitcd" value="-1">
			                  	<html:select property="chrgunitcd" style="width:120px">
														<jsp:useBean id="chgunitselect" class="nexti.ejms.list.form.ChgUnitListForm">
															<bean:define id="deptcd" name="workplanForm" property="deptcd"/>
															<jsp:setProperty name="chgunitselect" property="dept_id" value="<%=(String)deptcd%>"/>
															<jsp:setProperty name="chgunitselect" property="all_fl" value="ALL"/>
															<jsp:setProperty name="chgunitselect" property="title" value="����������"/>
														</jsp:useBean>
														<html:optionsCollection name="chgunitselect" property="beanCollection"/>
				                 	</html:select>&nbsp;&nbsp;
                      	</logic:notEqual>
                        <html:select property="chrgusrcd" style="width:80px">
                            <jsp:useBean id="chrgusrselect" class="nexti.ejms.list.form.DeptUserListForm">
                                <bean:define id="deptcd" name="workplanForm" property="deptcd"/>
                                <jsp:setProperty name="chrgusrselect" property="dept_id" value="<%=(String)deptcd %>"/>
                                <jsp:setProperty name="chrgusrselect" property="all_fl" value="ALL"/>
                                <jsp:setProperty name="chrgusrselect" property="title" value="����ڼ���"/>
                            </jsp:useBean>
                            <html:optionsCollection name="chrgusrselect" property="beanCollection"/>
                        </html:select>&nbsp;&nbsp;
                      	<logic:notEmpty name="workplanForm" property="user_tel">
	                      	��ȭ : <bean:write name="workplanForm" property="user_tel"/>
                      	</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">����Ⱓ</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1">
                        <html:text property="strdt" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="�޷�" style="cursor:hand" onclick="Calendar_D(forms[0].strdt);">&nbsp;~
                        <html:text property="enddt" style="text-align:center;width:100;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="�޷�" style="cursor:hand" onclick="Calendar_D(forms[0].enddt);">&nbsp;<img src="/images/common/btn_icon_del.gif" style="cursor:hand" align="absmiddle" onclick="strdt.value=enddt.value=''" alt="">
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr>
                    	<td colspan="3">
                    		<html:textarea property="content" style="width:100%;"/>
												<script>
													// Automatically calculates the editor base path based on the _samples directory.
													// This is usefull only for these samples. A real application should use something like this:
													// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
													var oFCKeditor = new FCKeditor('content');
													oFCKeditor.ToolbarSet = 'ejms_basic';
													oFCKeditor.Height = 400;
													oFCKeditor.ReplaceTextarea();
												</script>
                    	</td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">÷������1</td>
                      <td class="bg2"></td>
                      <td class="t1">
	                     	<input type="file" id="attachFile1" name="attachFile1" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile1')" style="height:18;" value="÷�����">&nbsp;
												<logic:notEmpty name="workplanForm" property="orgfilenm1">
													<br>÷������ : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm1"/>&fileName=<bean:write name="workplanForm" property="orgfilenm1"/>"><bean:write name="workplanForm" property="orgfilenm1"/></a>
													<input type="checkbox" name="attachFileYN1" style="border:0;background-color:transparent;" value="Y">÷�����ϻ���
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">÷������2</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<input type="file" id="attachFile2" name="attachFile2" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile2')" style="height:18;" value="÷�����">&nbsp;
												<logic:notEmpty name="workplanForm" property="orgfilenm2">
													<br>÷������ : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm2"/>&fileName=<bean:write name="workplanForm" property="orgfilenm2"/>"><bean:write name="workplanForm" property="orgfilenm2"/></a>
													<input type="checkbox" name="attachFileYN2" style="border:0;background-color:transparent;" value="Y">÷�����ϻ���
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">÷������3</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<input type="file" id="attachFile3" name="attachFile3" style="width:88%" contentEditable="false"><input type="button" onclick="resetFile('attachFile3')" style="height:18;" value="÷�����">&nbsp;
												<logic:notEmpty name="workplanForm" property="orgfilenm3">
													<br>÷������ : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm3"/>&fileName=<bean:write name="workplanForm" property="orgfilenm3"/>"><bean:write name="workplanForm" property="orgfilenm3"/></a>
													<input type="checkbox" name="attachFileYN3" style="border:0;background-color:transparent;" value="Y">÷�����ϻ���
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                  </table>
                  <!--�����չ��� �ۼ��ϱ� ���̺�-->
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
  </tr>
</html:form>
</table>
<script>
</script>
<html:messages id="msg" message="true">
    <script>
    alert('<bean:write name="msg"/>');
    </script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>