<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �������� �����ٿ�ε�
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
response.setHeader("Content-Disposition", "attachment; filename=" + nexti.ejms.util.commfunction.fileNameFix(((String)request.getAttribute("reqTitle") + ".xls").replaceAll(";", ":")));
response.setHeader("Content-Description", "JSP Generated Data"); 
%>
<table border="1">	
	<tr align="center">
	  <td>������ȣ</td>
	  <td>��û��</td>
	  <td>��û�Ͻ�</td>
	  <td>����������</td>
	  <td>����</td>
	  <logic:notEmpty name="dataListForm" property="dataList">
	 		<%
				java.util.List dfList1 = (java.util.List)request.getAttribute("dataFormList");
				nexti.ejms.sinchung.form.DataForm dform1 = (nexti.ejms.sinchung.form.DataForm)dfList1.get(0);
				String btype1 = dform1.getBasictype();
				java.util.List pform1 = dform1.getArticleList();
				out.print(nexti.ejms.util.commfunction.dataViewForXls("head",btype1,dform1,pform1));
	 		%>
		</logic:notEmpty>
	</tr>	
	<logic:notEmpty name="dataListForm" property="dataList">
		<logic:iterate id="list" name="dataListForm" property="dataList" indexId="idx">
			<tr align="center">
			  <td><bean:write name="list" property="reqseq"/></td>
			 	<td><bean:write name="list" property="presentnm"/></td>
			 	<td><bean:write name="list" property="crtdt"/></td>
			 	<td><bean:write name="list" property="lastsanc"/></td>
			 	<td><bean:write name="list" property="statenm"/></td>
		 		<%
		 			java.util.List dfList2 = (java.util.List)request.getAttribute("dataFormList");
		 			nexti.ejms.sinchung.form.DataForm dform2 = (nexti.ejms.sinchung.form.DataForm)dfList2.get(idx.intValue());
		 			String btype2 = dform2.getBasictype();
		 			java.util.List pform2 = dform2.getArticleList();
					out.print(nexti.ejms.util.commfunction.dataViewForXls("body",btype2,dform2,pform2));
		 		%>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="dataListForm" property="dataList">
		<tr align="center">
		 	<td colspan="5">������ ����� �����ϴ�.</td>			                
		</tr>			
	</logic:empty>    
</table>