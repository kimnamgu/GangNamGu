<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���ڰ����ȴ������
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
<title>���ڰ��� ��ȹ� �ۼ�</title>
<base target="_self">
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/common.js"></script>
<script language="javascript">
function click_cancel() {
	if ( confirm("����ۼ��� ����Ͻðڽ��ϱ�?") ) {
		window.close();
		window.dialogArguments.close();
	}
}

function click_send() {
	processingShow();
	
	setTimeout(function() {
		var fck = FCKeditorAPI.GetInstance("formhtml");
		var formhtml = document.frames[1];	//��������
		var formtitle = document.forms[0].formtitle;
		var formcomment = document.forms[0].formcomment;
		var ea_id = document.forms[0].ea_id;
		var attach = document.forms[0].attach;

		var content = fck.GetXHTML(true).replace(/&nbsp;/g, "");
		content = content.replace(/<br>/g, "").replace(/<\/br>/g, "");
		content = content.replace(/<p>/g, "").replace(/<\/p>/g, "");

		if (formtitle.value.trim() == "") {
			processingHide();
			alert("��ȸ��� �Է��ϼ���");
			formtitle.focus();
			return;
		}
		
		if (formcomment.value.trim() == "") {
			processingHide();
			alert("������ �Է��ϼ���");
			formcomment.focus();
			return;
		}
		
		if ( (ea_id.value = ea_id.value.trim()) == "") {
			processingHide();
			alert("���ڰ�����̵� �Է��ϼ���");
			ea_id.disabled = false;
			ea_id.focus();
			return;
		}

		if (content.trim() == "") {
			processingHide();
			if (confirm("���ӳ��� ���� �����Ͻðڽ��ϱ�?")) {
				ea_id.disabled = false;
				attach.value = "false";
				document.forms[0].action = "/exchangeSend.do";
				document.forms[0].submit();
			} else {
				formhtml.focus();
			}
			return;
		} else {
			processingHide();
			if (confirm("��ȴ��� �����Ͻðڽ��ϱ�?")) {
				ea_id.disabled = false;
				document.forms[0].action = "/exchangeSend.do";
				document.forms[0].submit();
			}
		}
	}, 0);
}

function back_opener(){
    window.dialogArguments.move_formatLineView();
    close();
}
</script>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/draftDocument">
	<html:hidden property="type"/><html:hidden property="reqformno"/><html:hidden property="reqseq"/>
	<html:hidden property="sysdocno"/><html:hidden property="attach"/>
<table width="780" border="0" cellspacing="0" cellpadding="0" height="600">
  <tr> 
    <td valign="top"> 
      <table width="750" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
        	<td height="15"></td>
        </tr>
        <tr> 
          <td> 
            <table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""> 
                </td>
                <td><b>��ȸ�</b></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td style="padding:0 0 0 20;"><html:text property="formtitle" style="width:100%"/></td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td><b>����</b></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td  style="padding:0 0 0 20;">
          	<textarea name="formcomment" style="width:100%; height:100">
<%
String formtitle = ((nexti.ejms.format.form.FormatForm)request.getAttribute("formatForm")).getFormtitle();
String comment = "";
comment += "\t1. \n";
comment += "\t2. [" + formtitle + "] ������ �����Ͽ� ���Ӱ� ���� �����մϴ�.\n";
comment += "\n";
comment += "\t����. �����ڷ� �� 1��. ��.\n";
if ( formtitle.indexOf("��������") != -1 || formtitle.indexOf("���� ����") != -1 ) {
	if ( "����3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t�ۡۡۡۡ� ����� ���Ͽ� �������ڼ��� �߱��� ��û�ϴ�, ó���Ͽ� �ֽñ� �ٶ��ϴ�.\n";
		comment += "\n";
		comment += "\t���� : �������ڼ��� ��û��. ��.\n";
	} else if ( "����3030000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t�ۡۡۡۡ� ������ Ȱ���ϰ��� �������� ���� ��û���� ���Ӱ� ���� �����Ͽ��� �߱��Ͽ� �ֽñ� �ٶ��ϴ�.\n";
		comment += "\n";
		comment += "\t���� : �������ڼ��� ��û�� 1��. ��.\n";
	} else if ( "��õ5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t[" + formtitle + "] ������ �����Ͽ� ���Ӱ� ���� �����մϴ�.\n";
		comment += "\n";
		if ( 2 == ((nexti.ejms.format.form.FormatForm)request.getAttribute("formatForm")).getType() ) {
			comment += "\t����. ��û�� 1��. ��.\n";
		} else {
			comment += "\t����. �����ڷ� 1��. ��.\n";
		}
	} else if ( "����347000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
        comment = "";
        comment += "\t �������ڼŸ��� ���Ӱ� ���� ��û�Ͽ��� ó���Ͽ� �ֽñ� �ٶ��ϴ�.\n";
        comment += "\n";
        comment += "����  �������ڼ����û��(���ο�) 1��. ��.";
    }
}
out.print(comment);
%>
          	</textarea>
          </td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td><b>���ӳ���</b></td>
                <% if ( "����4190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
                    <td width="5"></td>
                    <td><font color="#FF5656">
                      <b>- ��������� ��� �� �����ڷ�� �Ʒ� �����Ϳ��� ���� �����Ͻ��� ������.<br>
                      - [���վ���ڷ�]���� �ۼ��ϼ���. </b></font> <a href="javascript:back_opener()"><font class="result_no">(�̵��ϱ�)</font></a>
                    </td>
                <% } %>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td  style="padding:0 0 0 20;"><html:textarea property="formhtml"/></td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr>
        	<td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td <% if ( "��õ5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='visibility:hidden'"); %> width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td <% if ("�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %> style='visibility:none' width="250"><font color="red"><b>������� �ֹε�Ϲ�ȣ 13�ڸ�(-����)&nbsp;&nbsp;:&nbsp;&nbsp;</b></font></td>
                <td <% } else if ( "��õ5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %> style='visibility:hidden' width="105"><b>���ڰ�����̵� :<b></td>
								<td <% } else { %> width="105"><b>���ڰ�����̵� :<b></td>
								<% } %>
								<td <% if ( "��õ5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='visibility:hidden'"); %>>
                <% if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
									<input type="text" name="secret" style="width:35%;text-align:center" value="<bean:write name="formatForm" property="ea_id"/>">
									<script>
										try {
											var sec = document.forms[0].secret;
											sec.style.position = "absolute";
											sec.value = sec.value.substring(0,6) + "�ܡܡܡܡܡܡ�";
											sec.onchange = function() {
												sec.value = sec.value.replace(/��/g, '');
												document.forms[0].ea_id.value = sec.value;
											}
										} catch ( exception ) {}
									</script>
								<% } %>
                                    <% if ( "����6460000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) { %>
                                        <input type="text" name="ea_id" style="width:35%;text-align:center" value="<%=session.getAttribute("user_id") %>"/>
                                    <% } else { %>
									<logic:empty name="formatForm" property="ea_id">
										<input type="text" name="ea_id" style="width:35%;text-align:center">
									</logic:empty>
									<logic:notEmpty name="formatForm" property="ea_id">
										<input type="text" name="ea_id" style="width:35%;text-align:center" value="<bean:write name="formatForm" property="ea_id"/>" 
											<% if ( "��õ6280000, ����3780000, ����3210000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) 
												out.print("disabled"); 
											%>>
									</logic:notEmpty>
                                    <% } %>
									<font color="red">
										<% if ( "����".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
											<b>�� ���ڰ�����̵�� �ֹε�Ϲ�ȣ('-' ����)</b>
										<% } else if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
											<%--<b>�� ���� �� �ݵ�� Ȯ��</b>--%>
										<% } else { %>
											<b>�� ���� �� �ݵ�� Ȯ��</b>
										<% } %>
									</font>
                </td>
                <td width="185" align="right">
                	<a href="javascript:click_send()"><img src="/images/common/btn_sendyes.gif" align="absmiddle" alt="��ȴ������"></a>
			          	<a href="javascript:click_cancel()"><img src="/images/common/btn_sendno.gif" align="absmiddle" alt="������"></a>
								</td>
							</tr>
            </table>
          </td>
        </tr>
				<tr>
        	<td height="10"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>
</body>
<script>
<% if ( "��õ5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
	var fh = document.forms[0].formhtml;
	fh.value = fh.value.replace("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>", "");
	fh.value = fh.value.replace('<meta http-equiv="Content-Type" content="text/html;charset=euc-kr">', '');
<% } %>
var oFCKeditor = new FCKeditor('formhtml');
oFCKeditor.ToolbarSet = 'ejms_formMake';
oFCKeditor.Height = 450;
oFCKeditor.ReplaceTextarea();
</script>
</html>