<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결과 엑셀 다운로드
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="nexti.ejms.util.commfunction"/>
<jsp:directive.page import="nexti.ejms.util.Utils"/>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setContentType("application/vnd.ms-excel;charset=euc-kr");
	response.setHeader("Content-Disposition", "attachment; filename=" + commfunction.fileNameFix(((String)request.getAttribute("title") + ".xls").replaceAll(";", ":")));
	response.setHeader("Content-Description", "JSP Generated Data");
	response.getWriter().println("<meta HTTP-EQUIV=\"Content-Type\" content=\"application/vnd.ms-excel;charset=euc-kr\" charset=\"euc-kr\">");
%>
<html>
<body>
<table border="1">
	<tr>
		<td>직급</td>
		<%
			int formCount = ((Integer)request.getAttribute("formCount")).intValue();
			for ( int i = 0; i < formCount; i++) {
		%>	
			<td><%=i+1%>번문항</td>
		<%
			}
		%>
	</tr>
	<%
		StringBuffer cont = new StringBuffer();
		List rList = (List)request.getAttribute("resultList");
		for ( int i = 0; rList != null && i < rList.size(); i++) {
			HashMap uHm = (HashMap)rList.get(i);
	%>
		<tr>
			<td><%=(String)uHm.get("CLASS_NAME")%></td>
			<%
				for ( int j = 0; j < formCount; j++) {
					HashMap aHm = (HashMap)((List)uHm.get("ANSLIST")).get(j);
					String anscont = (String)aHm.get("ANSCONT");
					String other = (String)aHm.get("OTHER");
								
					cont.delete(0, cont.capacity());
					if ( "".equals(Utils.nullToEmptyString(anscont).trim()) ) {
						cont.append(other);
					} else if ( "".equals(Utils.nullToEmptyString(other).trim()) ) {
						cont.append(anscont);
					} else {
						cont.append(anscont); cont.append("("); cont.append(other); cont.append(")");
					}
			%>	
				<td><%=cont%></td>
			<%
				}
			%>
		</tr>
	<%
		}
	%>
</table>
</body>
</html>