<%@ page language="java" pageEncoding="EUC-KR"%>
<%
	//아이디 또는 주민번호 중 하나를 선택하여 사용
	String ejms_id = "";	//새올행정시스템 로그인 ID (새올행정시스템 세션명 : SSO_ORG_ID)
	String ejms_sn = "";	//사용자 주민등록번호
	String ejms_ip = "localhost:8080";	//이자료모아 주소
	String ejms_url = "http://"+ejms_ip+"/ssocount.do?type=COUNT&userid="+ejms_id+"&usersn="+ejms_sn+"&act=";
%>

<script>
function goEJMS(act) {
	window.open("<%=ejms_url%>" + act, "pop");
}
function ejmsCountFrameOnload() {
	var uidSTag = "<uid>";
	var collprocCountSTag = "<collprocCount>";
	var deliCountSTag = "<deliCount>";
	var inputCountSTag = "<inputCount>";
	var rchCountSTag = "<rchCount>";
	var reqCountSTag = "<reqCount>";
	var reqProcWaitCountSTag = "<reqProcWaitCount>";
	var uidETag = "</uid>";
	var collprocCountETag = "</collprocCount>";
	var deliCountETag = "</deliCount>";
	var inputCountETag = "</inputCount>";
	var rchCountETag = "</rchCount>";
	var reqCountETag = "</reqCount>";
	var reqProcWaitCountETag = "</reqProcWaitCount>";
	var cntData = document.ejmsCountFrame.document.body.innerText;
	var toNumber = function(num) {
		return ( isNaN(num) ) ? 0 : Number(num);
	}
	try {
		var uid = cntData.substring(cntData.indexOf(uidSTag) + uidSTag.length, cntData.indexOf(uidETag));
	} catch(e) {}
	try {
		var collprocCount = document.getElementById("collprocCount");
		var collproc = cntData.substring(cntData.indexOf(collprocCountSTag) + collprocCountSTag.length, cntData.indexOf(collprocCountETag));
		collprocCount.innerHTML = toNumber(collproc);
	} catch(e) {}
	try {
		var deliCount = document.getElementById("deliCount");
		var deli = cntData.substring(cntData.indexOf(deliCountSTag) + deliCountSTag.length, cntData.indexOf(deliCountETag));
		deliCount.innerHTML = toNumber(deli);
	} catch(e) {}
	try {
		var inputCount = document.getElementById("inputCount");
		var input = cntData.substring(cntData.indexOf(inputCountSTag) + inputCountSTag.length, cntData.indexOf(inputCountETag));
		inputCount.innerHTML = toNumber(input);
	} catch(e) {}
	try {
		var rchCount = document.getElementById("rchCount");
		var rch = cntData.substring(cntData.indexOf(rchCountSTag) + rchCountSTag.length, cntData.indexOf(rchCountETag));
		rchCount.innerHTML = toNumber(rch);
	} catch(e) {}
	try {
		var reqCount = document.getElementById("reqCount");
		var req = cntData.substring(cntData.indexOf(reqCountSTag) + reqCountSTag.length, cntData.indexOf(reqCountETag));
		reqCount.innerHTML = toNumber(req);
	} catch(e) {}
	try {
		var reqProcWaitCount = document.getElementById("reqProcWaitCount");
		var reqProcWait = cntData.substring(cntData.indexOf(reqProcWaitCountSTag) + reqProcWaitCountSTag.length, cntData.indexOf(reqProcWaitCountETag));
		reqProcWaitCount.innerHTML = toNumber(reqProcWait);
	} catch(e) {}
}
</script>
<iframe name="ejmsCountFrame" src="<%=ejms_url + "mycount"%>" onload="ejmsCountFrameOnload()" width="0" height="0"></iframe>
<table>
	<tr>
		<td>
			<a href="javascript:goEJMS('deliv')"><span id="deliCount">-</span></a><!-- 배부대기 건수 -->
			<a href="javascript:goEJMS('input')"><span id="inputCount">-</span></a><!-- 입력대기 건수 -->
		</td>
	</tr>
</table>