<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����İ��� ���
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
<jsp:include page="/include/processing.jsp" flush="true"/>

<script language="javascript">
function click_submit(actionpath) {
	document.forms[0].action = actionpath;
	parent.processingShow();
	document.forms[0].submit();
}

function click_popup(actionpath, width, height) {
	var left = (screen.width - width) / 2 + 30;
	var top = (screen.height - height) / 2 + 30;
	var option = 
		"dialogWidth:" + width + "px; " +
		"dialogHeight:" + height + "px; " +
		"dialogLeft:" + left + "px; " +
		"dialogTop:" + top + "px; " +
		"resizable:yes; scroll:yes; status:yes; center:no; " +
		"dialogHide:no; edge:raised; help:no; unadorned:no";

	try {
		window.showModalDialog(actionpath , window, option);
	} catch(exception) {
		alert("�̸����� ����� ����Ͻ÷��� �˾� ���� ����� ���ֽðų�\n" +
					"�˾� ��� ����Ʈ�� ������ֽñ� �ٶ��ϴ�.\n\n" +
					"������ �� ���ͳݿɼ� �� �������� �� �˾����ܻ��(üũ����)\n" +
					"�Ǵ� ����(��ưŬ��) �� ����� �� ����Ʈ �ּ� �Է� �� �߰�");
		try {processingHide();} catch(ex) {}
	}
}

function click_delete(chk, actionpath)	{
	document.forms[0].type.value = "4";
	var chklst = document.getElementsByName(chk);
	
	for(var i = 0; i < chklst.length; i++)
		if(chklst[i].checked == true) {
			if(confirm("���� �Ͻðڽ��ϱ�?") == true)
				click_submit(actionpath);
				
			return;
		}
	alert("������ ������ �����ϼ���.");
}


function check_all(chk_parent, chk_child)	{
	var chkall = chk_parent;
	var chklst = document.getElementsByName(chk_child);
	
	for(var i = 0; i < chklst.length; i++)
		chklst[i].checked = chkall.checked;
}
		
function setUsable() {
	try {
		if(parent.tree.lastSelected.parentObject.childNodes.length > 0
						|| parent.tree.getSelectedItemId() == parent.tree.htmlNode.childNodes[0].id) {
			msg.innerHTML = "�� �μ��� �����Ͻø� ��� �߰�/����/������ �Ͻ� �� �ֽ��ϴ�.";
			proc.innerHTML = "�μ���";
			add.innerHTML = "";
			
			var pagesize = document.forms[0].ps.value;		
			var currpage = document.forms[0].cp.value;
			var dept = document.getElementsByName("dept");
			for(var i = pagesize * (currpage-1); i < dept.length + pagesize * (currpage-1); i++) {
				//eval("del" + (i + 1)).align = "left"
				eval("del" + (i + 1)).innerHTML = dept[i - pagesize * (currpage-1)].value;
				
				var linkTag = eval("modify" + (i + 1)).outerHTML
				var hrefStart = linkTag.indexOf("href");
				var hrefEnd = linkTag.indexOf("\"", linkTag.indexOf("\"", hrefStart + 1) + 1) + 1;
				eval("modify" + (i + 1)).outerHTML = linkTag.substring(0, hrefStart) + " style='text-decoration:none' " + linkTag.substring(hrefEnd, linkTag.length);
			}
		}
	} catch (exception) {}
}
</script>

<link href="/css/style.css" rel="stylesheet" type="text/css">
<html:form action="/formatCommFrameMgr" method="post">
<input type="hidden" name="ps" value="<%=request.getAttribute("pagesize")%>">
<input type="hidden" name="cp" value="<%=request.getAttribute("currpage")%>">
<html:hidden property="sysdocno"/><html:hidden property="type"/>
<html:hidden property="searchdept"/>
<table width="580" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<!-- �˻� ���--> 
  	<td id="msg" colspan="2">
	    <table width="580" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
	        <td width="15" align="center"><img src="/images/common/dot.gif" alt=""></td>
	        <td width="60" align="center"><font color="#4F4F4F">�������</font></td>
	        <td width="120"><html:text property="searchtitle" style="width:95%" onkeypress="if(event.keyCode==13)click_submit('/formatCommFrameMgr.do');"/></td>
	        <td width="15" align="center"><img src="/images/common/dot.gif" alt=""></td>
	        <td width="60" align="center"><font color="#4F4F4F">��İ���</font></td>
	        <td width="160"><html:text property="searchcomment" style="width:95%" onkeypress="if(event.keyCode==13)click_submit('/formatCommFrameMgr.do');"/></td>
	        <td width="70"><a href="javascript:click_submit('/formatCommFrameMgr.do')"><img src="/images/common/btn_search.gif" border="0" alt="�˻�"></a></td>
	        <td width="80" align="right"><font class="result_no">* �� <%=request.getAttribute("totalcount")%>��</font></td>
	     	</tr>
	  	</table>
		</td>
  </tr>
  <tr> 
    <td height="10" colspan="2"></td>
  </tr>
  <tr> 
    <td colspan="2"> 
      <table width="580" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="6" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="6" height="1"></td>
        </tr>
        <tr> 
		      <td class="list_t" width="30" align="center"><input type="checkbox" name="checkall" style="border:0;background-color:transparent;" onclick="check_all(this, 'listdelete[]')"></td>
					<td class="list_t" width="40">����</td>
          <td class="list_t" width="180">�������</td>
          <td class="list_t" width="80">�������</td>
          <td class="list_t" width="100">�ۼ�����</td>
          <td class="list_t" width="180" id="proc">�μ���</td>
        </tr>
        <tr> 
          <td colspan="6" height="1"></td>
        </tr>
        <tr> 
          <td colspan="6" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="6" height="1"></td>
        </tr>
        <logic:notEmpty name="formatForm" property="listform">
		    	<logic:iterate id="list" name="formatForm" property="listform" indexId="i">
		    		<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		    			<td align="center"><input type="checkbox" name="listdelete[]" value="<bean:write name="list" property="formseq"/>" style="border:0;background-color:transparent;"></td>
							<td width="40" class="list_no"><bean:write name="list" property="seqno"/><input type="hidden" name="dept" value="<bean:write name="list" property="deptcd"/>"></td>
		          <td class="list_s" width="180">
		          	<logic:equal name="list" property="formkind" value="01">
			          	<a id="modify<bean:write name="list" property="seqno"/>" class="list_s2" href="javascript:click_popup('/formatLineDef.do?type=6&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
			          </logic:equal>
			          <logic:equal name="list" property="formkind" value="02">
			          	<a id="modify<bean:write name="list" property="seqno"/>" class="list_s2" href="javascript:click_popup('/formatFixedDef.do?type=6&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
			          </logic:equal>
			          <logic:equal name="list" property="formkind" value="03">
			          	<a id="modify<bean:write name="list" property="seqno"/>" class="list_s2" href="javascript:click_popup('/formatBookDef.do?load=1&type=6&formseq=<bean:write name="list" property="formseq"/>', 780, 550)"><bean:write name="list" property="formtitle"/></a>
			          </logic:equal>
		          </td>
		          <td width="80" class="list_l"><bean:write name="list" property="formkindname"/></td>
		          <td width="100" class="list_l"><bean:write name="list" property="uptdt"/></td>
		          <td width="180" class="list_l"><bean:write name="list" property="deptcd"/></td>
		    		</tr>
		    		<tr> 
		          <td colspan="6" class="list_bg2"></td>
		        </tr>
		  		</logic:iterate>
		  	</logic:notEmpty>
		  	<logic:empty name="formatForm" property="listform">
		  		<tr>
		  			<td colspan="6" class="list_l" align="center">��ϵ� ����� �����ϴ�.</td>
		  		</tr>
		  		<tr>
		  			<td colspan="6" class="list_bg2"></td>
		  		</tr>
		  	</logic:empty>
      </table>
    </td>
  </tr>
  <tr> 
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="2"> 
      <table width="580" border="0" cellspacing="0" cellpadding="0">
        <tr> 
        	<td align="left"><img src="/images/common/btn_del.gif" alt="����" style="cursor:hand" onclick="click_delete('listdelete[]', '/formatDel.do')"></td>
          <td align="right" id="add"><a href="javascript:click_popup('/formatLineDef.do?type=4&formseq=0&deptcd=<bean:write name="formatForm" property="searchdept"/>', 800, 600)"><img src="/images/common/btn_add2.gif" alt="����߰�"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="30" align="center" colspan="2">
    	<bean:define id="fform" name="formatForm"/>
			<jsp:directive.page import="nexti.ejms.format.form.FormatForm"/>
			<%
			String param = "sysdocno=" + ((FormatForm)fform).getSysdocno() +
										 "&type=" + ((FormatForm)fform).getType() +
										 "&searchdept=" + ((FormatForm)fform).getSearchdept() +
										 "&searchtitle=" + ((FormatForm)fform).getSearchtitle() +
										 "&searchcomment=" + ((FormatForm)fform).getSearchcomment();
			%>
			<%= nexti.ejms.util.commfunction.procPage_AddParam("/formatCommFrameMgr.do",param,null,request.getAttribute("totalpage").toString(),request.getAttribute("currpage").toString()) %>
		</td>
	</tr>
</table>
</html:form>
<script>
setUsable();
parent.processingHide();
</script>