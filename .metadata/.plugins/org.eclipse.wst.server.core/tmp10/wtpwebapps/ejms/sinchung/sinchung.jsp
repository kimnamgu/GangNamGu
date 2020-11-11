<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청하기
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
<html>
<head>
<title>신청하기</title>
<!-- <link rel="stylesheet" href="/css/style.css" type="text/css"> -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/sinchung.js"></script>
<script type="text/javascript">	
	function save(type, frm) {
		if ( type == 1 ) {
			if( check_submit() != false ) {
				frm.action = "/dataSave.do";
				frm.submit();
			}
		} else if ( type == 2 ) {
			if ( confirm("신청사항없음 처리 후 다시 신청할 수 없습니다\n\n신청사항없음 처리하시겠습니까?") ) {
				frm.action = "/dataSave.do?type=99";
				frm.submit();
			}
		}
	}
	
	function send(frm) {
		if(confirm("기안문생성은 신청 후 가능합니다.\n신청하시겠습니까?")) {
			if(check_submit() != false) {
				frm.action = "/dataSave.do?type=2";
				frm.submit();
			}
		}
	}
</script>	
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px;overflow:hidden;" onload="return;fullSizeWindow(-1, (screen.availHeight * 0.8))">
<div class="wrap" style="width:690px;height:685px; overflow-y: scroll;">
		<div class="container">
			<div class="survey-wrap">
				<h2>신청하기111</h2>
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
					<html:hidden property="mode" value="s"/>
					<input type="hidden" name="listcnt" value="<bean:write name="sinchungForm" property="acnt"/>">
		          	<input type="hidden" name="reqformno" value="<bean:write name="sinchungForm" property="reqformno"/>">	          	
		          	<html:hidden property="reqseq"/>
					<div class="write-area">				
					 			<bean:define id="btype" name="sinchungForm" property="basictype"/>	
		          				<bean:define id="rbean" name="sinchungForm" property="rbean"/>          
			 					<%=nexti.ejms.util.commfunction.basicInput_v01(btype.toString(), (nexti.ejms.sinchung.model.ReqMstBean)rbean) %>
					</div>
					
					<div class="survey-list">
						<ol>			
			 					<bean:define id="pform" name="sinchungForm" property="articleList"/>
								<%=nexti.ejms.util.commfunction.addInput_v01((java.util.List)pform, null, nexti.ejms.common.appInfo.getAp_address()) %>
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
					<!-- <a href="javascript:save(document.forms[0])" class="btn-join">신청하기</a>
					<a href="javascript:window.close();" class="btn-join">닫기</a> -->
					<logic:notEqual name="sinchungForm" property="mode" value="s">
			           		<logic:equal name="sinchungForm" property="range" value="1">
			           			<% if ( nexti.ejms.common.appInfo.isElecappr() ) { %>
			           				<logic:notEqual value="005" name="user_gbn" scope="session">
			           					<% if ( "동작3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
			           						<bean:define name="sinchungForm" property="title" type="java.lang.String" id="title1"/>
			           						<% if ( !(title1.indexOf("권한신청") != -1 || title1.indexOf("권한 신청") != -1
			           										|| title1.indexOf("전출입신청") != -1 || title1.indexOf("전출입 신청") != -1) ) { %>
			           							<a href="javascript:send(document.forms[0])" class="btn-join">기안문생성</a>
			           						<% } %>
				           				<% } else { %>
					           				<a href="javascript:send(document.forms[0])" class="btn-join">기안문생성</a>
				           				<% } %>
				           			</logic:notEqual>
				           		<% } %>
			              </logic:equal>
			            </logic:notEqual>
			            <bean:define name="sinchungForm" property="title" type="java.lang.String" id="title2"/>
			            <% if ( title2.indexOf("행정전자") != -1 || title2.indexOf("행정 전자") != -1 ) { %>
			            	<% if ( "동작3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1
													&& "성동3030000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1) { %>
					            	<a href="javascript:save(1, document.forms[0])" class="btn-join">신청하기</a>
			                	<a href="javascript:save(2, document.forms[0])" class="btn-join">신청사항없음</a>
					          <% } %>
	                <% }else if( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) == -1) { %>
	               <a href="javascript:save(1, document.forms[0])" class="btn-join">신청하기</a>
                	<a href="javascript:save(2, document.forms[0])" class="btn-join">신청사항없음</a>
	                <%} else { %>
	                <%--<a href="javascript:save(1, document.forms[0])"><img src="/images/common/btn_application.gif" alt="신청하기"></a>
                	<a href="javascript:save(2, document.forms[0])"><img src="/images/common/btn_no_application.gif" alt="신청사항없음"></a>--%>
                	<% } %>
				</div>
			</div>
		</div>
	</div>
<% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
<script>
try {
	var tbl = document.getElementsByTagName("table")[8].tBodies[0];
	for ( var i = 0; i < tbl.rows.length; i++ ) {
		if ( tbl.rows[i].cells[0].innerText == "첨부파일" ) {
			tbl.moveRow(i, tbl.rows.length - 1);
			tbl.moveRow(i - 1, tbl.rows.length - 2);
			break;
		}
	}
} catch ( e ) {}
</script>
<% } else if ( "중구30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
<script>
try{
    var tbl = document.getElementsByTagName("table")[8].tBodies[0];
    var str, jumin, basic;
    for ( var i = 0; i < tbl.rows.length; i++ ) {
        str = tbl.rows[i].cells[0].innerText;
        
        if ( str == "주민등록번호(*)" ) {
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