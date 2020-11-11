<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";
	var sort_gb = "";

	$(document)
			.ready(
					function() {

						fn_deptList();

						fn_selectMainSaupList(1);

						$("#2019")
								.on(
										"click",
										function(e) { //2019년도 보기
											var comSubmit = new ComSubmit();

											comSubmit
													.setUrl("<c:url value='/daejang/mainSaupList.do?year=2019&sgb=${param.sgb}' />");
											comSubmit.submit();
										});

						$("#2020")
								.on(
										"click",
										function(e) { //2020년도 보기
											var comSubmit = new ComSubmit();

											comSubmit
													.setUrl("<c:url value='/daejang/mainSaupList.do?year=2020&sgb=${param.sgb}' />");
											comSubmit.submit();
										});

						$("#commit")
								.on(
										"click",
										function(e) { //공약역점
											var comSubmit = new ComSubmit();

											comSubmit
													.setUrl("<c:url value='/daejang/mainSaupList.do?year=2020&sgb=M' />");
											comSubmit.submit();
										});

						$("#smart")
								.on(
										"click",
										function(e) { //스마트도시
											var comSubmit = new ComSubmit();

											comSubmit
													.setUrl("<c:url value='/daejang/mainSaupList.do?year=2020&sgb=S' />");
											comSubmit.submit();
										});

						$("#write").on("click", function(e) { //글쓰기 버튼
							e.preventDefault();
							fn_mainSaupDataWrite();
						});

						$("#admin").on("click", function(e) { //관리자메뉴
							e.preventDefault();
							if (user_right == 'A') {
								fn_adminMenu();
							} else {
								alert('권한이 없습니다.');
							}
						});

						$("#initial").on("click", function(e) { //초기화 버튼
							e.preventDefault();
							sort_gb = "";
							$("#form1").each(function() {
								this.reset();
							});
						});

						$("#search").on("click", function(e) { //검색 버튼
							e.preventDefault();
							sort_gb = "";
							$("#PAGE_INDEX").val("1");
							fn_selectMainSaupList();
						});

						$("#SAUP_NM").keydown(function(key) {

							if (key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
								$("#PAGE_INDEX").val("1");
								fn_selectMainSaupList();
							}

						});

						$('#close1').click(function() {
							$('#pop1').hide();
						});

						$("#logout").on("click", function(e) { //로그아웃				
							e.preventDefault();
							fn_logout();
						});

						$(window).bind("beforeunload", function() {
							if (event.clientY < 0) {

								var comAjax = new ComAjax();
								//alert( '로그아웃 됩니다.' );
								comAjax.setUrl("<c:url value='/logout.do' />")
								comAjax.ajax();
							}

						});

						function fn_adminMenu() {
							var comSubmit = new ComSubmit();
							comSubmit
									.setUrl("<c:url value='/common/idApproveList.do?year=${param.year}&sgb=${param.sgb}' />");
							comSubmit.submit();
						}

						$("#DISP_CNT").change(
								function() {
									$("#RECORD_COUNT").val(
											$("#DISP_CNT option:selected")
													.val());
									$("#PAGE_INDEX").val("1");
									fn_selectMainSaupList();
								});

						$("#SAUP_DEPT_CD").change(function() {
							fn_selectMainSaupList();
						});

						$('#sam_nm1').click(function(e) {
							e.preventDefault();
							sort_gb = "A1";
							fn_selectMainSaupList();
						});

						$('#sam_nm2').click(function(e) {
							e.preventDefault();
							sort_gb = "A2";
							fn_selectMainSaupList();
						});

						$('#sam_amt1').click(function(e) {
							e.preventDefault();
							sort_gb = "B1";
							fn_selectMainSaupList();
						});

						$('#sam_amt2').click(function(e) {
							e.preventDefault();
							sort_gb = "B2";
							fn_selectMainSaupList();
						});

						$('#sam_pct1').click(function(e) {
							e.preventDefault();
							sort_gb = "C1";
							fn_selectMainSaupList();
						});

						$('#sam_pct2').click(function(e) {
							e.preventDefault();
							sort_gb = "C2";
							fn_selectMainSaupList();
						});

						$('#sam_sy2').click(function(e) {
							e.preventDefault();
							sort_gb = "D2";
							fn_selectMainSaupList();
						});

						$("#SAUP_AMT1").bind('keyup keydown', function() {
							inputNumberFormat(this);
						});

						$("#SAUP_AMT2").bind('keyup keydown', function() {
							inputNumberFormat(this);
						});

					});

	function fn_mainSaupUpdate(obj) {
		var comSubmit = new ComSubmit();

		comSubmit
				.setUrl("<c:url value='/daejang/mainSaupUpdate.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.addParam("ID", obj.parent().find("#UID4").val());
		comSubmit.addParam("SAUP_NM", obj.parent().find("#USNM4").val());
		comSubmit.addParam("SAUP_DEPT_NM", obj.parent().find("#UDNM3").val());
		comSubmit.submit();
	}

	//입력한 문자열 전달
	function inputNumberFormat(obj) {
		obj.value = comma(uncomma(obj.value));
	}

	//콤마찍기
	function comma(str) {
		str = String(str);
		return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}

	//콤마풀기
	function uncomma(str) {
		str = String(str);
		return str.replace(/[^\d]+/g, '');
	}

	function fn_deptList() {

		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/common/deptList.do' />");
		comAjax.setCallback("fn_deptListCallback");
		comAjax.ajax();
	}

	function fn_deptListCallback(data) {
		//data = $.parseJSON(data);
		var str = "";
		var i = 1;

		$.each(data.list, function(key, value) {
			str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME
					+ "</option>";
			i++;
		});

		$("#SAUP_DEPT_CD").append(str);
	}

	function fn_logout() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/logout.do' />");
		comSubmit.submit();
	}

	function fn_mainSaupDataWrite() {
		var comSubmit = new ComSubmit();
		comSubmit
				.setUrl("<c:url value='/daejang/mainSaupWrite.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.submit();
	}

	function fn_mainSaupDetail(obj) {
		var comSubmit = new ComSubmit();

		comSubmit
				.setUrl("<c:url value='/daejang/mainSaupDetail.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.addParam("ID", obj.parent().find("#UID").val());
		comSubmit.submit();
	}

	function fn_mainSaupSub(obj) {
		var comSubmit = new ComSubmit();

		comSubmit
				.setUrl("<c:url value='/daejang/mainSaupSubWrite.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.addParam("MAST_ID", obj.parent().find("#UID2").val());
		comSubmit.addParam("ID", obj.parent().find("#UID2").val());
		comSubmit.addParam("SAUP_NM", obj.parent().find("#USNM2").val());
		comSubmit.addParam("SAUP_DEPT_CD", obj.parent().find("#UDCD2").val());
		comSubmit.addParam("SAUP_DEPT_NM", obj.parent().find("#UDNM2").val());
		comSubmit.addParam("LST_GB", "M"); //메인리스트에서 등록 클릭한것인가 /서브리스트에서 등록 버튼 누른 것인가 구별
		comSubmit.submit();
	}

	function fn_mainSaupSubList(obj) {
		var comSubmit = new ComSubmit();

		comSubmit
				.setUrl("<c:url value='/daejang/mainSaupSubList.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.addParam("MAST_ID", obj.parent().find("#UID3").val());
		comSubmit.addParam("ID", obj.parent().find("#UID3").val());
		comSubmit.addParam("SAUP_NM", obj.parent().find("#USNM3").val());
		comSubmit.addParam("SAUP_DEPT_CD", obj.parent().find("#UDCD3").val());
		comSubmit.addParam("SAUP_DEPT_NM", obj.parent().find("#UDNM3").val());
		comSubmit.submit();
	}

	function fn_selectMainSaupList(pageNo) {
		var comAjax = new ComAjax();
		var user_right = $("#USER_RIGHT").val();

		comAjax.setUrl("<c:url value='/daejang/selectMainSaupList.do' />");
		comAjax.setCallback("fn_selectMainSaupListCallback");
		comAjax.addParam("SAUP_YEAR", "${param.year}");
		comAjax.addParam("SGB", "${param.sgb}");
		comAjax.addParam("PAGE_INDEX", $("#PAGE_INDEX").val());
		comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
		comAjax.addParam("SAUP_DEPT_CD", $("#SAUP_DEPT_CD").val());
		comAjax.addParam("SAUP_NM", $("#SAUP_NM").val());
		comAjax.addParam("SAUP_AMT1", $("#SAUP_AMT1").val());
		comAjax.addParam("SAUP_AMT2", $("#SAUP_AMT2").val());
		comAjax.addParam("SORT_GB", sort_gb);
		comAjax.ajax();
	}

	function fn_selectMainSaupListCallback(data) {
		//data = $.parseJSON(data);			
		var total = data.TOTAL;
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();

		$("#tcnt").text(comma(total));

		body.empty();
		if (total == 0) {
			var str = "<tr>" + "<td colspan=\"10\">조회된 결과가 없습니다.</td>"
					+ "</tr>";
			body.append(str);
		} else {
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectMainSaupList"
			};
			gfn_renderPaging(params);

			var str = "";
			var t_str = "";
			var tr_class = "";

			$
					.each(
							data.list,
							function(key, value) {
								t_str = "";

								if ((value.RNUM % 2) == 0)
									tr_class = "tr1";
								else
									tr_class = "tr2";

								str += "<tr class=\"" + tr_class + "\">";

								if (value.ID == null) {
									str += "<td class='cell1'>"
											+ NvlStr(value.RNUM) + "</td>"
											+ "<td class='cell2'>&nbsp;</td>"
											+ "<td class='cell3'>" + NvlStr(value.SAUP_NM)
											+ "</td>" + "<td class='cell4'>"
											+ NvlStr(value.SAUP_DEPT_NM)
											+ "</td>";
								} else {
									str += "<td class='cell1'>"
											+ NvlStr(value.RNUM)
											+ "</td>"
											+ "<td class='cell2'>"
											+ "<a href='#this' name='title3' >이력</a>"
											+ "<input type='hidden' name='title3' id='UID3' value=" + value.ID + ">"
											+ "<input type='hidden' name='title3' id='USNM3' value='" + value.SAUP_NM + "''>"
											+ "<input type='hidden' name='title3' id='UDCD3' value='" + value.SAUP_DEPT_CD + "''>"
											+ "<input type='hidden' name='title3' id='UDNM3' value='" + value.SAUP_DEPT_NM + "''>"
											+ "</td>"
											+ "<td class='cell3'>"
											+ "<a href='#this' name='title' >"
											+ NvlStr(value.SAUP_NM)
											+ "</a>"
											+ "<input type='hidden' name='title' id='UID' value=" + value.ID + ">"
											+ "</td>"
											+ "<td class='cell4'>"
											+ "<a href='#this' name='title4' >"
											+ NvlStr(value.SAUP_DEPT_NM)
											+ "</a>"
											+ "<input type='hidden' name='title4' id='UID4' value=" + value.ID + ">"
											+ "<input type='hidden' name='title4' id='USNM4' value=" + value.SAUP_NM + ">"
											+ "<input type='hidden' name='title4' id='UDNM4' value=" + value.SAUP_DEPT_NM + ">"
											+ "</td>";
								}
								str += "<td class='cell5'>"
										+ comma(NvlStr(value.SAUP_BUDGET_AMT))
										+ "</td>"
										+ "<td class='cell6'>"
										+ NvlStr(value.SAUP_OPEN_DATE)
												.substring(0, 4)
										+ "."
										+ NvlStr(value.SAUP_OPEN_DATE)
												.substring(4, 6)
										+ "</td>"
										+ "<td class='cell7'>"
										+ NvlStr(value.SAUP_PROCESS_PERCENT)
										+ "</td>"
										+ "<td class='cell8'>"
										+ NvlStr(value.SAUP_PROCESS_TOT_PERCENT)
										+ "</td>" + "<td class='cell9'>"
										+ NvlStr(value.SAUP_DAMDANG_NM)
										+ "</td>";

								if (value.ID == null) {
									str += "<td>&nbsp;</td>" + "</tr>";
								} else {
									str += "<td>"
											+ "<a href='#this' name='title2' >등록 </a>"
											+ "<input type='hidden' name='title2' id='UID2' value=" + value.ID + ">"
											+ "<input type='hidden' name='title2' id='USNM2' value='" + value.SAUP_NM + "'>"
											+ "<input type='hidden' name='title2' id='UDCD2' value='" + value.SAUP_DEPT_CD + "''>"
											+ "<input type='hidden' name='title2' id='UDNM2' value='" + value.SAUP_DEPT_NM + "''>"
											+ "</td>" + "</tr>";
								}

							});
			body.append(str);

			$("a[name='title']").on("click", function(e) { //제목 
				e.preventDefault();
				fn_mainSaupDetail($(this));
			});

			$("a[name='title2']").on("click", function(e) { //제목 
				e.preventDefault();
				fn_mainSaupSub($(this));
			});

			$("a[name='title3']").on("click", function(e) { //제목 
				e.preventDefault();
				fn_mainSaupSubList($(this));
			});

			$("a[name='title4']").on("click", function(e) { //부서명 
				e.preventDefault();
				fn_mainSaupUpdate($(this));
			});
		}
	}
</script>

<style>
/* css */
.tb_wrap {
	position: relative;
	width: 100%;
	padding-top: 25px;
}

.tb_box {
	max-height: 500px;
	overflow-y: scroll;
	border-bottom: 1px solid #dedede;
}

.cell1 {
	width: 20px;
}

.cell2 {
	width: 60px;;
}

.cell3 {
	width: 400px;;
}

.cell4 {
	width: 100px;;
}

.cell5 {
	width: 150px;;
}

.cell6 {
	width: 150px;;
}

.cell7 {
	width: 200px;;
}

.cell8 {
	width: 150px;;
}

.cell9 {
	width: 100px;;
}


.fixed_top {
	display: inline-table;
	position: absolute;
	top: 0;
	width: 100%;
	background: #eef7ff;
}

</style>

</head>
<body bgcolor="#FFFFFF">
	<form id="form1" name="form1">
		<input type="hidden" id="USER_ID" name="USER_ID"
			value="${sessionScope.userinfo.userId}"> <input type="hidden"
			id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
		<input type="hidden" id="USER_RIGHT" name="USER_RIGHT"
			value="${sessionScope.userinfo.userright}"> <input
			type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
		<c:set var="u_rt" value="${sessionScope.userinfo.userright}" />
		<c:set var="year" value="${param.year}" />
		<c:set var="sgb" value="${param.sgb}" />


		<table border="0" cellspacing="0" cellpadding="0"
			class="bodyframe_full">
			<tr>
				<td class="bodyframe">
					<!--페이지 타이틀 시작 -->
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="pupup_title"><a href="#this" class="btn"
								id="logout"><img src="/bims/images/popup_title.gif"
									align="absmiddle"></a> <c:choose>
									<c:when test="${sgb eq 'M'}">공약·역점 사업</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${sgb eq 'S'}">스마트도시 사업</c:when>
								</c:choose></td>
						</tr>
						<tr>
							<td class="margin_title"></td>
						</tr>

					</table> <!--페이지 타이틀 끝 -->
				</td>
			</tr>
			<tr>
				<td class="pupup_frame" style="padding-right: 12px">


					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="15">

								<div class="LblockTab" style="position: relative;">
									<ul class="Lclear">
										<c:choose>
											<c:when test="${sgb eq 'M'}">
												<li class="Lcurrent" id="lcommit"><span><a
														href="#this" class="btn" id="commit">공약·역점 사업</a></span></li>
												<li id="lsmart"><span><a href="#this"
														class="btn" id="smart">스마트도시 사업</a></span></li>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${sgb eq 'S'}">
												<li id="lcommit"><span><a href="#this"
														class="btn" id="commit">공약·역점 사업</a></span></li>
												<li class="Lcurrent" id="lsmart"><span><a
														href="#this" class="btn" id="smart">스마트도시 사업</a></span></li>
											</c:when>
										</c:choose>
									</ul>
								</div>

								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="margin_btn" colspan="2"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>


					<table width="520" border="0" cellspacing="1" cellpadding="0"
						class="tbl" id="divMemoMainBody1">
						<tr>
							<td width="120" class="tbl_field">년도</td>
							<td colspan="3" width="400" class="tbl_list_left">
								<h2>
									<font color="red"><c:out value="${year}" /></font>
								</h2>
							</td>
						</tr>
						<tr>
							<td width="120" class="tbl_field">부서명</td>
							<td colspan="3" width="400" class="tbl_list_left"><select
								id="SAUP_DEPT_CD" name="SAUP_DEPT_CD" class="input">
									<option value="">전체</option>
							</select> &nbsp;</td>
						</tr>
						<tr>
							<td width="120" class="tbl_field">사업명(내용)</td>
							<td colspan="3" width="400" class="tbl_list_left"><input
								type="text" id="SAUP_NM" name="SAUP_NM" class="input"
								style="width: 300;"></td>
						</tr>
						<tr>
							<td width="120" class="tbl_field">예산금액</td>
							<td colspan="3" width="400" class="tbl_list_left"><input
								type="text" id="SAUP_AMT1" name="SAUP_AMT1" class="input"
								style="width: 100;"> ~ <input type="text" id="SAUP_AMT2"
								name="SAUP_AMT2" class="input" style="width: 100;"></td>
						</tr>
					</table> <!-- -------------- 버튼 시작 --------------  -->
					<table width="100%" height="40" border="0" cellspacing="0"
						cellpadding="0" id="divMemoMainBody2">
						<tr>
							<td align="left"><b>Total : <span id="tcnt"></span></b>
								&nbsp; <select id="DISP_CNT" name="DISP_CNT" class="input">
									<option value="95">95</option>
									<option value="50">50</option>
									<option value="25">25</option>
									<option value="40">40</option>
									<option value="10">10</option>
							</select></td>
							<td align="right">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<c:choose>
											<c:when test="${u_rt eq 'A'}">
												<td>
													<table border="0" cellspacing="0" cellpadding="0"
														class="btn" id="changebtn2" style="display: block;">
														<tr>
															<td><img src="/bims/images/btn_type0_head.gif"></td>
															<td background="/bims/images/btn_type0_bg.gif"
																class="btn_type1" nowrap><a href="#this"
																class="btn" id="admin"><font color="white">관리자</font></a></td>
															<td><img src="/bims/images/btn_type0_end.gif"></td>
														</tr>
													</table>
												</td>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${year eq '2019'}">
												<td>
													<table border="0" cellspacing="0" cellpadding="0"
														class="btn" id="tblAddMemo21" style="filter: none;">
														<tr>
															<td><img src="/bims/images/btn_type1_head.gif"></td>
															<td background="/bims/images/btn_type1_bg.gif"
																class="btn_type1" nowrap><a href="#this"
																class="btn" id="2020">2020</a></td>
															<td><img src="/bims/images/btn_type1_end.gif"></td>
														</tr>
													</table>
												</td>
											</c:when>
											<c:when test="${year eq '2020'}">
												<td>
													<table border="0" cellspacing="0" cellpadding="0"
														class="btn" id="tblAddMemo21" style="filter: none;">
														<tr>
															<td><img src="/bims/images/btn_type1_head.gif"></td>
															<td background="/bims/images/btn_type1_bg.gif"
																class="btn_type1" nowrap><a href="#this"
																class="btn" id="2019">2019</a></td>
															<td><img src="/bims/images/btn_type1_end.gif"></td>
														</tr>
													</table>
												</td>
											</c:when>
										</c:choose>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" class="btn"
												id="tblAddMemo21" style="filter: none;">
												<tr>
													<td><img src="/bims/images/btn_type1_head.gif"></td>
													<td background="/bims/images/btn_type1_bg.gif"
														class="btn_type1" nowrap><a href="#this" class="btn"
														id="initial">검색초기화</a></td>
													<td><img src="/bims/images/btn_type1_end.gif"></td>
												</tr>
											</table>
										</td>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" class="btn"
												id="changebtn2" style="display: block;">
												<tr>
													<td><img src="/bims/images/btn_type0_head.gif"></td>
													<td background="/bims/images/btn_type0_bg.gif"
														class="btn_type1" nowrap><a href="#this" class="btn"
														id="write"><font color="white">사업등록</font></a></td>
													<td><img src="/bims/images/btn_type0_end.gif"></td>
												</tr>
											</table>
										</td>
										<td>
											<table border="0" cellspacing="0" cellpadding="0" class="btn"
												id="tblAddMemo21" style="filter: none;">
												<tr>
													<td><img src="/bims/images/btn_type0_head.gif"></td>
													<td background="/bims/images/btn_type0_bg.gif"
														class="btn_type1" nowrap><a href="#this" class="btn"
														id="search"><font color="white">검색</font></a></td>
													<td><img src="/bims/images/btn_type0_end.gif"></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="margin_btn" colspan="2"></td>
						</tr>
					</table>

				</td>
			</tr>
		</table>


		<!-- 리스트 -->
		<div class="tb_wrap">
			<div class="tb_box">
				<table class="tbl1">
					<thead>
						<tr class="fixed_top">
							<th class='cell1'>&nbsp;</th>
							<th class='cell2'>이력보기</th>
							<th class='cell3'>사업명 <a href="#this" class="btn" id="sam_nm1"> <fontcolor="white">▼</font></a><a'
								href="#this" class="btn" id="sam_nm2"> <font color="white">▲</font>
							</a>
							</th>
							<th class='cell4'>담당부서 <a href="#this" class="btn" id="sam_sy2"> <font
									color="white">▲</font>
							</a>
							</th>
							<th class='cell5'>예산금액(천원) <a href="#this" class="btn" id="sam_amt1">
									<font color="white">▼</font>
							</a> <a href="#this" class="btn" id="sam_amt2"> <font
									color="white">▲</font>
							</a>
							</th>
							<th class='cell6'>목표시기</th>
							<th class='cell7'>2020년 진척률(%) <a href="#this" class="btn" id="sam_pct1">
									<font color="white">▼</font>
							</a> <a href="#this" class="btn" id="sam_pct2"> <font
									color="white">▲</font>
							</a>
							</th>
							<th class='cell8'>최종목표 대비 진척률(%)</th>
							<th class='cell9'>담당자</th>
							<th></th>
						</tr>
					</thead>

					<tbody id='mytable'>
					</tbody>
				</table>
			</div>
		</div>

	</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX" />
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="95" />
	<%@ include file="/WEB-INF/include/include-body.jspf"%>


	<!-- POPUP -->
	<div id="pop1"
		style="position: absolute; left: 300px; top: 50; z-index: 200; display: none; border: 2px solid #c84637;">
		<table width=572 height=839 cellpadding=2 cellspacing=0>
			<tr>
				<td align=right bgcolor=white><a href="#" id="close1"><B>[닫기]</B></a>
				</td>
			</tr>
			<tr>
				<td style="border: 2px #ddd solid" height=809 align=center
					bgcolor=white>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody id='poptable1'>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>