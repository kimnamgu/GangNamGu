<%@ page contentType="text/html; charset=euc-kr" %>
<%@ include file="a.jsp" %> <!-- dbCon.jsp ������ �ҷ����� �κ��Դϴ�. -->
<%@ page import="java.util.*,java.text.*"%>

<html>
<head>
<title>test</title>
<meta http-equiv="Content-Type" content="text/html; charset=8859_1">
<link title=menustyle href="../adminstyle.css" type="text/css" rel="stylesheet">
<script language="JavaScript">
<!--
 function MM_openBrWindow(theURL,winName,features){
   window.open(theURL,winName,features);
 }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="630" border="0" cellspacing="0" cellpadding="0">
 <%
   sql="select distinct count(*) a from usr"; //���� �κ�, ���� ���� ������ ���� �Ǿ� �ִ� table�� �Է��ؾ� �մϴ�.

   stmt = con.createStatement();  
   rs = stmt.executeQuery(sql);

   while(rs.next()) {
   out.print("������������ :"+rs.getInt("a")); // ��ºκ�
 }

   if(rs != null) rs.close();
   if(stmt != null)stmt.close();
   if(con != null)con.close();
 %>

</body>

</html>