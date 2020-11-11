<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ����
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
<title>��û�ϱ�</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- <link rel="stylesheet" href="/css/style_gangnam.css" type="text/css"> -->
<link rel="stylesheet" href="/css/survey/bootstrap.min.css">
<link rel="stylesheet" href="/css/survey/style.css">
<link rel="stylesheet" href="/css/survey/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/sinchung.js"></script>
<script>
function send(frm) {
	if(confirm("��ȹ��� �����Ͻðڽ��ϱ�?")) {
		if(check_submit() != false) {
			frm.action = "/dataSave.do?type=2";
			frm.submit();	
		}
	}
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px;overflow:hidden;" onload="return;fullSizeWindow()">
<div class="wrap" style="width:690px;height:685px; overflow-y: scroll;">
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
								</th>
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
									<% if ( "����3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) { %>
                                    ��û������� : ������Ű� <bean:write name="sinchungForm" property="chrgusrnm"/> �� <bean:write name="sinchungForm" property="coltel"/><br>
                                    ��ȱ� : ���������� ������ �� 228-5300, �Ǽ��� : ���������� ����� �� 228-6300<br>
                                    �ȴޱ� : ���������� ���海 �� 228-7500, ���뱸 : ���������� ������ �� 228-8300
                                <% } else { %>
                              	  [<bean:write name="sinchungForm" property="coldeptnm"/>]<bean:write name="sinchungForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ȭ : <bean:write name="sinchungForm" property="coltel"/>
                                <% } %>
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
				 <% if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1 ) { %>
              		<font color="#B04300">[ ������ȣ : <bean:write name="sinchungForm" property="reqseq"/> ]</font><br><br>
        		<% } %>
					<div class="write-info">
						<logic:notEmpty name="sinchungForm" property="headcont">
			        	<bean:define id="headcont" name="sinchungForm" property="headcont"/>
			    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>	
							</logic:notEmpty>
					</div>
				</div>	
				<html:form method="POST" action="/dataSave" enctype="multipart/form-data">
					<html:hidden property="mode" value="u"/>
		          	<input type="hidden" name="reqformno" value="<bean:write name="sinchungForm" property="reqformno"/>">	
		          	<input type="hidden" name="reqseq" value="<bean:write name="sinchungForm" property="reqseq"/>">	      
					<div class="write-area">				
					 			<bean:define id="btype" name="sinchungForm" property="basictype"/>	
		          				<bean:define id="rbean" name="sinchungForm" property="rbean"/>         
			 					<%=nexti.ejms.util.commfunction.basicInput_v01(btype.toString(), (nexti.ejms.sinchung.model.ReqMstBean)rbean) %>
					</div>
					
					<div class="survey-list">
						<ol>			
			 					<bean:define id="pform" name="sinchungForm" property="articleList"/>
								<%=nexti.ejms.util.commfunction.addInput_v01((java.util.List)pform, (nexti.ejms.sinchung.model.ReqMstBean)rbean, nexti.ejms.common.appInfo.getAp_address()) %>
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
				 <logic:notEmpty name="sinchungForm" property="sancusrinfo">
				<div class="survey-sp-txt" style="margin-bottom:10px;">
				���ڰ�����Ȳ
					<bean:write name="sinchungForm" property="sancusrinfo"/>
				</div>
				</logic:notEmpty>
				<div class="text-center mb20">
					<% if ( "T".equals(request.getAttribute("sanc").toString()) ) { %>
                		<% if ( nexti.ejms.common.appInfo.isElecappr() ) { %>
                			<a href="javascript:cancel_check('<bean:write name="sinchungForm" property="reqformno"/>','<bean:write name="sinchungForm" property="reqseq"/>', 2);" <% if ( "��õ6280000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %> class="btn-join">�������</a>
                		<% } %>
                	<% } %>
                	<% if ( "T".equals(request.getAttribute("modify").toString()) ) { %>
                		<logic:equal name="sinchungForm" property="range" value="1">
			           			<% if ( nexti.ejms.common.appInfo.isElecappr() ) { %>
			           				<logic:notEqual value="005" name="user_gbn" scope="session">
			           					<% if ( "����3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
				           					<bean:define name="sinchungForm" property="title" type="java.lang.String" id="title1"/>
			           						<% if ( !(title1.indexOf("���ѽ�û") != -1 || title1.indexOf("���� ��û") != -1
			           										|| title1.indexOf("�����Խ�û") != -1 || title1.indexOf("������ ��û") != -1) ) { %>
			           							<a href="javascript:send(document.forms[0])" class="btn-join">��ȹ�����</a>
			           						<% } %>
				           				<% } else { %>
					           				<a href="javascript:send(document.forms[0])" class="btn-join">��ȹ�����</a>
				           				<% } %>
                				</logic:notEqual>
               				<% } %>
			              </logic:equal>
	                  <a href="javascript:cancel_check('<bean:write name="sinchungForm" property="reqformno"/>','<bean:write name="sinchungForm" property="reqseq"/>', 1);" class="btn-join">��û���</a>
	                  <a href="javascript:document.forms[0].submit();" class="btn-join">�����Ϸ�</a>
                 	<% } %>
                 	<a href="javascript:window.close()" class="btn-join">�ݱ�</a>
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
<% } else if ( "�߱�30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
<script>
try{
    var tbl = document.getElementsByTagName("table")[8].tBodies[0];
    var str, jumin, basic;
    for ( var i = 0; i < tbl.rows.length; i++ ) {
        str = tbl.rows[i].cells[0].innerText;
        
        if ( str == "�ֹε�Ϲ�ȣ(*)" ) {
            jumin = i;
        }
        if ( str.substr(str.length - 3) == "(*)" ) {
            basic = i;
        }
    }
    tbl.moveRow(jumin, basic);
    tbl.moveRow(jumin - 1, basic - 1);
} catch ( e ) {}
</script>
<% } %>
<jsp:include page="/include/tail.jsp" flush="true"/>