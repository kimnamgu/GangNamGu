<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: GPKI테스트
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr"%>
<jsp:directive.page import="com.gpki.gpkiapi.GpkiApi"/>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ page import="com.gpki.gpkiapi.cert.X509Certificate"%>
<%@ page import="com.gpki.gpkiapi.cms.SignedData"%>
<%@ page import="com.gpki.gpkiapi.util.Base64"%>

<%@ include file="/include/gpki.jsp" %>

<%
	GpkiApi.init("D:/Workspace/ejms/WebRoot/WEB-INF/");

	X509Certificate cert = null;
	byte[] signData = null;
	byte[] privatekey_random = null;
	String signType = "";
	String queryString = "";

	cert = gpkirequest.getSignerCert();
	Logger.debug.println(this, "cert : " + cert);

	String subDN = cert.getSubjectDN();
	Logger.debug.println(this, "subDN : " + subDN);

	int message_type = gpkirequest.getRequestMessageType();
	Logger.debug.println(this, "message_type : " + message_type);

	if (message_type == gpkirequest.ENCRYPTED_SIGNDATA ||
			message_type == gpkirequest.LOGIN_ENVELOP_SIGN_DATA ||
			message_type == gpkirequest.ENVELOP_SIGNDATA ||
			message_type == gpkirequest.SIGNED_DATA) {
		
		signData = gpkirequest.getSignedData();

		if (privatekey_random != null) {
			privatekey_random = gpkirequest.getSignerRValue();
		}

		signType = gpkirequest.getSignType();
		Logger.debug.println(this, "signType : " + signType);		

	}
	
	queryString = gpkirequest.getQueryString();
	Logger.debug.println(this, "queryString : " + queryString);
%>

<%
	if (cert != null || !cert.equals("")) {
%>

	로그인한 인증서 :<br>
	<ENCRYPT_DATA>
	<%=subDN%>
	</ENCRYPT_DATA>
	<p>

<%
	}

	byte[] signContent; // 원본메세지
	SignedData signedData = new SignedData();

	signedData.verify(signData); // signedData를 가지고 sign 검증 실행
	Logger.debug.println(this, "signedData.verify(signData) 완료");

	signContent = signedData.getMessage(); // signedData에서 원본 메세지를 꺼낸다. 
	Logger.debug.println(this, "signContent : " + signContent);
%>
	
	서명값 :<br>
	<ENCRYPT_DATA>
	<%=new Base64().encode(signData)%>
	</ENCRYPT_DATA>	
	<p>
	
	서명값 원본 :<br>
	<ENCRYPT_DATA>
	<%=signContent%>
	</ENCRYPT_DATA>	
	<p>
<%
	if (privatekey_random != null) {
%>
	privatekey_random :<br>
	<ENCRYPT_DATA>
	<%=new Base64().decode(privatekey_random.toString())%>
	</ENCRYPT_DATA>	
	<p>
<%
	}
%>
	QueryString :<br>
	<ENCRYPT_DATA>
	<%=gpkirequest.getQueryString()%>
	</ENCRYPT_DATA>	