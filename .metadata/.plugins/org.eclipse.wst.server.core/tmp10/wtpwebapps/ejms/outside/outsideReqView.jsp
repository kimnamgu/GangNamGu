<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망신청서 신청
 * 설명:
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>신청하기</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<!-- <link rel="stylesheet" type="text/css" href="/css/style.css"> -->
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/sinchung.js"></script>
<script type="text/javascript">	
function save(frm){
	if(check_submit() != false) {
		frm.action = "/outsideReqAns.do?uid=<bean:write name="sinchungForm" property="crtusrid"/>";
		frm.submit();	
	}
}
</script>	
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px;"  onload="window.resizeTo(725, screen.availHeight * 0.88)">
<!-- <div class="wrap" style="width:690px;height:746px; overflow-y: scroll;"> -->
<div class="wrap" style="width:690px; ">
		<div class="container">
			<div class="survey-wrap">
				<h2>신청하기</h2>
				<div class="survey-info">
					<table class="table">
						<caption></caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="2" class="text-center survey-title">
								<bean:write name="sinchungForm" property="title"/>
								</th>
							</tr>
							<tr>
								<th scope="row">신청개요</th>
								<td>
									<logic:notEmpty name="sinchungForm" property="summary">
												        	<bean:define id="summary" name="sinchungForm" property="summary"/>
												        	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString())%>
												        </logic:notEmpty>
								</td>
							</tr>
							<tr>
								<th scope="row">신청담당자</th>
								<td>
									[<bean:write name="sinchungForm" property="coldeptnm"/>]<bean:write name="sinchungForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="sinchungForm" property="coltel"/>
								</td>
							</tr>
							<tr>
								<th scope="row">신청기간</th>
								<td>
									<strong class="text-blue">
										<logic:present name="sinchungForm" property="enddt">
                            			<bean:write name="sinchungForm" property="strdt"/>&nbsp;부터&nbsp;&nbsp;&nbsp;<bean:write name="sinchungForm" property="enddt"/>&nbsp;까지
										        		</logic:present>
										        		<logic:notPresent name="sinchungForm" property="enddt">
										        			<bean:write name="sinchungForm" property="strdt"/>&nbsp;부터&nbsp;&nbsp;&nbsp;제한없음
										        		</logic:notPresent>
								</strong>
								</td>
							</tr>
							<!-- <tr>
								<th scope="row">참여대상</th>
								<td>
									인터넷(비회원)<span class="bar">l</span><strong class="text-red">총 참여자수 : 226명</strong>
								</td>
							</tr> -->
						</tbody>
					</table>
				</div>
				<div class="survey-write-top-area">
					<div class="write-info">
						  <logic:notEmpty name="sinchungForm" property="headcont">
			        		<bean:define id="headcont" name="sinchungForm" property="headcont"/>
			    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>	
						</logic:notEmpty>
					</div>
				</div>	
				<html:form method="POST" action="/dataSave" enctype="multipart/form-data">
				<bean:define id="pform" name="sinchungForm" property="articleList"/>
					<html:hidden property="mode" value="s"/>
		          	<input type="hidden" name="reqformno" value="<bean:write name="sinchungForm" property="reqformno"/>">
		          	<input type="hidden" name="listcnt" value="<%=((java.util.List)pform).size() %>">
		          	<html:hidden property="reqseq"/>
					<div class="write-area">				
					 	<bean:define id="btype" name="sinchungForm" property="basictype"/>	
					 	 <bean:define id="rbean" name="sinchungForm" property="rbean"/>
			 					<%=nexti.ejms.util.commfunction.basicInput_v01(btype.toString(), (nexti.ejms.sinchung.model.ReqMstBean)rbean) %>
					</div>
					
					<div class="survey-list">
						<ol class="ol_style">
			 					<%-- <bean:define id="pform" name="sinchungForm" property="articleList"/> --%>
								<%=nexti.ejms.util.commfunction.addInput_v01((java.util.List)pform, null, request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()) %>
						</ol>
					</div>
					<%=nexti.ejms.util.commfunction.makeSubmitFunc(btype.toString(), (java.util.List)pform) %>
				</html:form>
				<div class="survey-sp-txt" style="margin-bottom:10px;">
					<logic:notEmpty name="sinchungForm" property="tailcont">
							<bean:define id="tailcont" name="sinchungForm" property="tailcont"/>
							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(tailcont.toString())%> 
						</logic:notEmpty>	
				</div>
				<div class="text-center mb20">
					<a href="javascript:save(document.forms[0])" class="btn-join">신청하기</a>
					<a href="javascript:window.close();" class="btn-join">닫기</a>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="/include/tail.jsp" flush="true"/>