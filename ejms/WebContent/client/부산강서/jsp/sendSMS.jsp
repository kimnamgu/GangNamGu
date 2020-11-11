<%--
 * 작성일: 2012.12.12
 * 작성자: 대리 서동찬
 * 모듈명: 부산 강서구청 - 신청하기/수정/취소 시 문자 발송
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="java.sql.DriverManager"/>
<jsp:directive.page import="java.sql.PreparedStatement"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.FrmBean"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.ReqMstBean"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.ReqSubBean"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.SampleBean"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.SinchungManager"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.SinchungDAO"/>
<jsp:directive.page import="nexti.ejms.user.model.UserManager"/>
<jsp:directive.page import="nexti.ejms.util.Utils"/>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
Connection con = null;
PreparedStatement pstmt = null;

try {
	String param = (String)request.getParameter("param");
	int reqformno = Integer.parseInt(param.split("[,]")[0]);
	int reqseq = Integer.parseInt(param.split("[,]")[1]);
	String action = param.split("[,]")[2];
	
	String dbType	= "oracle.jdbc.driver.OracleDriver";
	String dbIp		= "jdbc:oracle:thin:@99.13.8.130:1521:ineedyou";
	String dbUser	= "edata";
	String dbPass	= "edata1";
	
	String PHONE = "보낼번호";
	String CALLBACK = "회신번호";
	String MSG = "메세지";
	
	boolean isSend = false;
	UserManager uMgr = UserManager.instance();
	SinchungManager sMgr = SinchungManager.instance();
	FrmBean frmBean = sMgr.reqFormInfo(reqformno);
	ReqMstBean rmBean = sMgr.reqDataInfo(reqformno, reqseq);
	
	if ( "INSERT".equals(action) ) {	//신청
		if ( 3 == reqformno ) {	//전산장비 장애처리 신청
			//문자예시 : "부서명/성명/장애장비구분/장애내용(80자 이내)"
			String tmpStr = "기타";
			try {
				int tmpInt = Integer.parseInt("0" + ((ReqSubBean)rmBean.getAnscontList().get(0)).getAnscont());
				tmpStr = ((SampleBean)(new SinchungDAO().sampleList(reqformno, 1).get(tmpInt))).getExamcont();
			} catch ( Exception e ) {}
			
			isSend = true;
			PHONE = "01065885404";	//오경 양만석 대리
			CALLBACK = rmBean.getTel();
			MSG = uMgr.getDeptName(rmBean.getPresentid()) + "/" +
						rmBean.getPresentnm() + "/" +
						tmpStr + "/" +
						((ReqSubBean)rmBean.getAnscontList().get(2)).getAnscont();
		} else if ( 1 == reqformno ) {	//행정전자서명 발급 신청
			//문자예시 : "부서명/성명/e-자료모아의 행정전자서명발급신청서 확인바랍니다"				
			isSend = true;
			PHONE = "0168403689";	//행정정보화과 김안나 주무관
			CALLBACK = rmBean.getTel();
			MSG = uMgr.getDeptName(rmBean.getPresentid()) + "/" +
						rmBean.getPresentnm() + "/" +
						"e-자료모아의 행정전자서명발급신청서 확인바랍니다";
		}
	} else if ( "UPDATE".equals(action) ) {	//변경
		
	} else if ( "DELETE".equals(action) ) {	//취소
		
	}
	
	if ( isSend == true ) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO SMS_MSG(MSGKEY, ID, PHONE, CALLBACK, REQDATE, MSG, ETC1, ETC2, ETC3, ETC4) \n");
		sql.append("VALUES(SMS_MSG_SEQ.NEXTVAL, 'edata', ?, ?, SYSDATE, ?, 'e-자료모아', '3360107', '020000', '1') \n");
		
		Class.forName(dbType);
		con = DriverManager.getConnection(dbIp, dbUser, dbPass);
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, PHONE);
		pstmt.setString(2, CALLBACK);
		pstmt.setString(3, Utils.subString(MSG, 80, ".."));
		
		if ( pstmt.executeUpdate() > 0 ) {
			System.out.println("SMS 발송완료 : " + PHONE + "," + CALLBACK + "," + Utils.subString(MSG, 80, ".."));
		} else {
			throw new Exception();
		}
	}
} catch ( Exception e ) {
		System.out.println("SMS 발송실패 :");
		e.printStackTrace();
} finally {
	try { pstmt.close(); } catch ( Exception e ) {}
	try { con.close(); } catch ( Exception e ) {}
}
%>