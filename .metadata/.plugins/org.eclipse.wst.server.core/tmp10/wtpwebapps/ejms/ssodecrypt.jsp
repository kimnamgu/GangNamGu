<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import ="java.io.*, java.util.*, java.net.*, java.text.*"%>
<%@ page import="com.api.crypt" %>
<%
String encrypted = "1EglM5TfiuaqeNVFPPXjtqo9MVQ5REoCrBHsQLM399douixU2CRzWG0bPCnAEPna";
out.println("��ȣȭ�� ������ = [" + crypt.dec(encrypted) + "]<br/>");
%>
