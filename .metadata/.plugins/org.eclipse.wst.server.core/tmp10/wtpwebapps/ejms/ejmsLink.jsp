<%@ page language="java" pageEncoding="EUC-KR"%>
<%
String _URL_ = "106.2.10.6:9400";
String _SSO_ORG_ID_ = (String)session.getAttribute("SSO_ORG_ID");
response.sendRedirect("http://"+_URL_+"/ssocount.do?userid="+_SSO_ORG_ID_);
%>