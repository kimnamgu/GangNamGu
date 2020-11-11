<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수내역 보기
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
<title>접수내역</title>
<base target="_self">
<script src="/script/common.js"></script>
<script src="/script/sinchung.js"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function procJupsu(gbn){
	if(gbn == "1"){
		document.forms[0].gbn.value="1";				
	} else {
		document.forms[0].gbn.value="2";				
	}
	
	var oldTarget = document.forms[0].target;
	document.forms[0].target = "actionFrame";
	document.forms[0].submit();
	document.forms[0].target = oldTarget;
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="return;fullSizeWindow()">
<form method="POST" action="/procJupsu.do">
	<input type="hidden" name="reqformno" value="<bean:write name="dataForm" property="reqformno"/>">
	<input type="hidden" name="reqseq" value="<bean:write name="dataForm" property="reqseq"/>">
	<input type="hidden" name="gbn">
</form>
<table width="660" border="0" cellspacing="0" cellpadding="0" style="padding:0 10 10 10">
	<tr>
		<td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_31.gif" alt=""></td>
	</tr>
	<tr> 
	  <td> 
	    <table width="660" border="0" cellspacing="0" cellpadding="0">
	      <tr> 
	        <td colspan="3" class="list_bg2"></td>
	      </tr>
	      <tr> 
	        <td width="160" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">신청서명</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataForm" property="title"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">접수번호</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataForm" property="reqseq"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">신청일시</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataForm" property="crtdt"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">최종승인자(결재시)</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataForm" property="lastsanc"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">처리상태</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataForm" property="statenm"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="list_bg2"></td>
	      </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="660" border="0" cellspacing="0" cellpadding="0" style="word-break:break-all;word-wrap:break-word">
				<tr> 
	        <td colspan="2" class="list_bg2"></td>
	      </tr>
	      <tr> 
	        <td colspan="2" class="s1" align="center"><b>신&nbsp;&nbsp;&nbsp;청&nbsp;&nbsp;&nbsp;내&nbsp;&nbsp;&nbsp;역</b></td>
	      </tr>
	      <tr> 
	        <td colspan="2" class="list_bg2"></td>
	      </tr>
				<bean:define id="btype" name="dataForm" property="basictype"/>
				<bean:define id="pData" name="dataForm"/>
				<bean:define id="pForm" name="dataForm" property="articleList"/>
				<%=nexti.ejms.util.commfunction.dataView(btype.toString(),(nexti.ejms.sinchung.form.DataForm)pData,(java.util.List)pForm, "mgr", nexti.ejms.common.appInfo.getAp_address())%>
	      <tr> 
	        <td colspan="2" class="list_bg2"></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<logic:notEmpty name="dataForm" property="sancusrinfo">
		<tr>
			<td>
				<table width="660" border="0" cellspacing="0" cellpadding="0" style="word-break:break-all;word-wrap:break-word">
					<tr> 
		        <td colspan="2" class="list_bg2"></td>
		      </tr>
		      <tr> 
		        <td width="160" class='s1'><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt=""><b>전자결재현황</b></td>
						<td width="500" class='t1'><bean:write name="dataForm" property="sancusrinfo"/></td>
		      <tr> 
		      	<td colspan="2" class="list_bg2"></td>
		      </tr>
		    </table>
		  </td>
		</tr>
	</logic:notEmpty>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr> 
    <td>
    	<table width="660" border="0" cellspacing="0" cellpadding="0">
	      <tr> 
	        <td>
	        	<a href="javascript:procJupsu('1')"><img src="/images/common/btn_jupsucomp.gif" alt="접수완료"></a>
           	<a href="javascript:procJupsu('2')"><img src="/images/common/btn_jupsuhold.gif" alt="접수보류"></a>
          </td>
	        <td align="right"><a href="javascript:window.close()"><img src="/images/common/btn_close2.gif" alt="닫기"></a>
	        </td>
	      </tr>
	    </table>
		</td>
  </tr>
</table>
<% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
<script>
try {
	var tbl = document.getElementsByTagName("table")[2].tBodies[0];
	for ( var i = 0; i < tbl.rows.length; i++ ) {
		if ( tbl.rows[i].cells[0].innerText == "첨부파일" ) {
			tbl.moveRow(i, tbl.rows.length - 2);
			tbl.moveRow(i - 1, tbl.rows.length - 3);
			break
		}
	}
} catch ( e ) {}
</script>
<% } %>
<jsp:include page="/include/tail.jsp" flush="true"/>