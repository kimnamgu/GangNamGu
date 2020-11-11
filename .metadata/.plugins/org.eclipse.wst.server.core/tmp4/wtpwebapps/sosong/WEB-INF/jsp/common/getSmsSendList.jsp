<!doctype html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>http://www.blueb.co.kr</title>

<style rel="stylesheet">
body {
	font-family: "Helvetica Neue", Helvetica, Arial;
	font-size: 14px;
	line-height: 20px;
	font-weight: 400;
	color: #3b3b3b;
	-webkit-font-smoothing: antialiased;
	font-smoothing: antialiased;
	background: #ffffff;
}

.wrapper {
	margin: 0 auto;
	padding: 40px;
	max-width: 800px;
}

.table {
	margin: 0 0 40px 0;
	width: 100%;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
	display: table;
}

@media screen and (max-width: 580px) {
	.table {
		display: block;
	}
}

.row {
	display: table-row;
	background: #f6f6f6;
}

.row:nth-of-type(odd) {
	background: #e9e9e9;
}

.row.header {
	font-weight: 900;
	color: #ffffff;
	background: #ea6153;
}

.row.green {
	background: #27ae60;
}

.row.blue {
	background: #2980b9;
}

@media screen and (max-width: 580px) {
	.row {
		padding: 8px 0;
		display: block;
	}
}

.cell {
	padding: 6px 12px;
	display: table-cell;
}

@media screen and (max-width: 580px) {
	.cell {
		padding: 2px 12px;
		display: block;
	}
}
</style>

<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {

	
		//fn_allSmsSendListCallback();

	  

	});

	
	$(window).load(function(){
		
		fn_allSmsSendListCallback();
    }); 

		
	
	function fn_initial_setting() {
		$('input:radio[id=INCDNT_GB4]').prop("checked", true);
	}

	

	function fn_allSmsSendList(pageNo) {
	
		
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/common/getSmsSendList.do' />");
		alert("fn =[" + $("#PAGE_INDEX").val() + "]");
		comSubmit.addParam("PAGE_INDEX", $("#PAGE_INDEX").val());
		comSubmit.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
		comSubmit.addParam("INSERT_TIME", "20170701"); //6월이전에는 테스트로 보낸거라서 제외시킴
		
		comSubmit.submit();
		
		//fn_allSmsSendListCallback();
	}

	function fn_allSmsSendListCallback() {	
		var t_cnt = new Array();
		
		<c:forEach var="slist" items="${list}" varStatus="status">
		t_cnt[Number("${status.count}")] = Number("${slist.TOTAL_COUNT}");		
		</c:forEach>
		
		var total = t_cnt[1];
		
		var recordcnt = $("#RECORD_COUNT").val();
		$("#tcnt").text(comma(total));
		var pindx =   $("#PAGE_INDEX").val();
		//var pindx =   "4";
		alert("pindx=[" + pindx + "]");
		
		var params = {
			divId : "PAGE_NAVI",
			pageIndex : "PAGE_INDEX",
			totalCount : total,
			recordCount : recordcnt,
			eventName : "fn_allSmsSendList"
		};
		gfn_renderPaging(params);
		
	}

	
</script>
</head>
<body>
	<form id="form1" name="form1">
		<input type="hidden" id="USER_ID" name="USER_ID"
			value="${sessionScope.userinfo.userId}"> <input type="hidden"
			id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
		<input type="hidden" id="USER_RIGHT" name="USER_RIGHT"
			value="${sessionScope.userinfo.userright}"> <input
			type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
		<div class="wrapper">

			<div class="table">

				<div class="row header blue">
					<div class="cell">번호</div>
					<div class="cell">핸드폰</div>
					<div class="cell">발신번호</div>
					<div class="cell">보낸시간</div>
					<div class="cell">수신시간</div>
					<div class="cell">결과</div>
					<div class="cell">내용</div>
				</div>
				<c:forEach var="row" items="${list}" varStatus="status" end="9">
					<div class="row">
						<div class="cell">${row.RNUM}</div>
						<div class="cell">${row.DSTADDR}</div>
						<div class="cell">${row.CALLBACK}</div>
						<div class="cell">${row.SEND_TIME}</div>
						<div class="cell">${row.REPORT_TIME}</div>
						<div class="cell">${row.RESULT}</div>
						<div class="cell">${row.RESULT}</div>
					</div>

				</c:forEach>

			</div>

		</div>


		</div>
	</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX" />
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10" />
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
</body>
</html>
