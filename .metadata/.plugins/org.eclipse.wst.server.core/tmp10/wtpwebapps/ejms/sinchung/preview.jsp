<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �̸�����
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
<title>��û�ϱ� �̸�����</title>
<base target="_self">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<!-- <link rel="stylesheet" href="/css/style.css" type="text/css"> -->
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
		<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/sinchung.js"></script>
<script type="text/javascript">
</script>	
</head>

<!-- <body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px"  onload="fullSizeWindow(710, (screen.availHeight * 0.8))">-->
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<div class="wrap" style="width:690px;height:746px; overflow-y: scroll;">
		<div class="container">
			<div class="survey-wrap">
				<h2>��û�ϱ�</h2>
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
								</td>
							</tr>
							<tr>
								<th scope="row">��û����</th>
								<td>
									<logic:notEmpty name="sinchungForm" property="summary">
												        	<bean:define id="summary" name="sinchungForm" property="summary"/>
												        	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString())%>
												        </logic:notEmpty>
								</td>
							</tr>
							<tr>
								<th scope="row">��û�����</th>
								<td>
                                    [<bean:write name="sinchungForm" property="coldeptnm"/>]<bean:write name="sinchungForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ȭ : <bean:write name="sinchungForm" property="coltel"/>
								</td>
							</tr>
							<tr>
								<th scope="row">��û�Ⱓ</th>
								<td>
									<strong class="text-blue">
										<logic:present name="sinchungForm" property="enddt">
                            			<bean:write name="sinchungForm" property="strdt"/>&nbsp;����&nbsp;&nbsp;&nbsp;<bean:write name="sinchungForm" property="enddt"/>&nbsp;����
										        		</logic:present>
										        		<logic:notPresent name="sinchungForm" property="enddt">
										        			<bean:write name="sinchungForm" property="strdt"/>&nbsp;����&nbsp;&nbsp;&nbsp;���Ѿ���
										        		</logic:notPresent>
								</strong>
								</td>
							</tr>
							<!-- <tr>
								<th scope="row">�������</th>
								<td>
									���ͳ�(��ȸ��)<span class="bar">l</span><strong class="text-red">�� �����ڼ� : 226��</strong>
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
				<form method="POST">
				<input type="hidden" name="listcnt" value="<bean:write name="sinchungForm" property="acnt"/>">
					<div class="write-area">				
					 	<bean:define id="btype" name="sinchungForm" property="basictype"/>		          
			 					<%=nexti.ejms.util.commfunction.basicInput_v01(btype.toString(), null) %>
					</div>
					<div class="survey-list">
						<ol class="ol_style">	
			 					<bean:define id="pform" name="sinchungForm" property="articleList"/>
								<%=nexti.ejms.util.commfunction.addInput_v01((java.util.List)pform, null, nexti.ejms.common.appInfo.getAp_address()) %>
						</ol>
					</div>
				</form>
				<div class="survey-sp-txt">
					<logic:notEmpty name="sinchungForm" property="tailcont">
							<bean:define id="tailcont" name="sinchungForm" property="tailcont"/>
							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(tailcont.toString())%> 
						</logic:notEmpty>	
				</div>
				<div class="text-center mb20">
				  <logic:equal name="sinchungForm" property="usedFL" value="Y">
					<a href="/selectUsed.do?reqformno=<bean:write name="sinchungForm" property="reqformno"/>" class="btn-join">�����ϱ�</a>
					</logic:equal>
					<a href="javascript:window.close();" class="btn-join">�ݱ�</a>
				</div>
			</div>
		</div>
	</div>
<% if ( "�λ�6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
<script>
try {
	var tbl = document.getElementsByTagName("table")[8].tBodies[0];
	for ( var i = 0; i < tbl.rows.length; i++ ) {
		if ( tbl.rows[i].cells[0].innerText == "÷������" ) {
			tbl.moveRow(i, tbl.rows.length - 1);
			tbl.moveRow(i - 1, tbl.rows.length - 2);
			break;
		}
	}
} catch ( e ) {}
</script>
<% } %>
<jsp:include page="/include/tail.jsp" flush="true"/>