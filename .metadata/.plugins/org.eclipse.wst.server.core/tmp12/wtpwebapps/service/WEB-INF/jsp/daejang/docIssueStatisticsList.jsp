<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
	        		
		$(document).ready(function(){
					
			//from
			$("#FROM_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//to
			$("#TO_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});


			fn_initial_setting();
			fn_selectDocIssueStaticsList();
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
			
				if($('#FROM_DATE').val() == "")
				{
					alertBox("시작일을 입력해주세요!!");
					$('#FROM_DATE').focus();
					return false();
				}
				
				if($('#TO_DATE').val() == "")
				{
					alertBox("종료일을 입력해주세요!!");
					$('#TO_DATE').focus();
					return false();
				}
			
				fn_selectDocIssueStaticsList();
			});
			
			$("#logout").on("click", function(e){ //로그아웃				
				e.preventDefault();				
				fn_logout();
			});
			
			
			$(window).bind("beforeunload", function ()
			{
				if (event.clientY < 0) {
					
					var comAjax = new ComAjax();
					//alert( '로그아웃 됩니다.' );
					comAjax.setUrl("<c:url value='/logout.do' />")
					comAjax.ajax();			
				}
									
			});
			
		});
		
		/* 초기화 */
		function fn_initial_setting(){
			//From의 초기값을 오늘 날짜로 설정
		    $('#FROM_DATE').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
		    //To의 초기값을 내일로 설정
		    $('#TO_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
		}
		
		/* 로그아웃 */
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		/* 조회 */
		function fn_selectDocIssueStaticsList(){
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/selectStatisticsList.do' />");
			comAjax.addParam("from_date", $("#FROM_DATE").val().replace(/-/gi, ""));
			comAjax.addParam("to_date", $("#TO_DATE").val().replace(/-/gi, ""));
			comAjax.setCallback("fn_selectStatisticsListCallback");
			
			comAjax.ajax();
		}
		
		/* 조회 callback  */
		function fn_selectStatisticsListCallback(data){			
			var issue_table_year = $("#issue_table_year");
			var issue_table_month = $("#issue_table_month");
			var issue_table_day = $("#issue_table_day");
			
			var pee_table_year = $("#pee_table_year");
			var pee_table_month = $("#pee_table_month");
			var pee_table_day = $("#pee_table_day");
			
			issue_table_year.empty();
			issue_table_month.empty();
			issue_table_day.empty();
			
			pee_table_year.empty();
			pee_table_month.empty();
			pee_table_day.empty();
			

			var str_issue_year = "";
			var str_issue_month = "";
			var str_issue_day = "";
			
			var str_pee_year = "";
			var str_pee_month = "";
			var str_pee_day = "";
			
			var tr_class = "";
			
			
			
			/* 발급 통계 - 연별 */
			$.each(data.issueStatisticsListYear, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
				       str_issue_year += "<tr class='" + tr_class + "'>" + 
								   			   "<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";
								   			   
						   			   if(NvlStr(value.done_yn_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='process'>" + NvlStr(value.done_yn_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.done_yn_cnt + "</td>";
						   			   }
						   			   
						   			   /* 주민등록등본 */
						   			   if(NvlStr(value.baa_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.baa_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.baa_cnt + "</td>";
						   			   }
						   			   
										/* 주민등록초본 */
						   			   if(NvlStr(value.bab_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bab_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bab_cnt + "</td>";
						   			   }
						   				/* 가족관계증명서 */
						   			   if(NvlStr(value.bba_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bba_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bba_cnt + "</td>";
						   			   }
						   			   
						   				/* 기본증명서 */
						   			   if(NvlStr(value.bbb_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bbb_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bbb_cnt + "</td>";
						   			   }
						   				/* 혼인관계증명서 */
						   			   if(NvlStr(value.bbc_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bbc_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bbc_cnt + "</td>";
						   			   }
						   				/* 입양관계증명서 */
						   			   if(NvlStr(value.bbd_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bbd_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bbd_cnt + "</td>";
						   			   }
						   				/* 제적등본 */
						   			   if(NvlStr(value.bca_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bca_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bca_cnt + "</td>";
						   			   }
						   				/* 제적초본 */
						   			   if(NvlStr(value.bcb_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.bcb_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.bcb_cnt + "</td>";
						   			   }
						   				
						   				/* 수입증지 */
						   			   if(NvlStr(value.job_cnt) != "0"){
						   					str_issue_year += "<td align='center' class='denied'>" + NvlStr(value.job_cnt) + "</td>";
						   			   }else{
						   					str_issue_year += "<td align='center'>" + value.job_cnt + "</td>";
						   			   }
												   			   
						str_issue_year += "</tr>";
			});
			issue_table_year.append(str_issue_year);
			
			/* 발급 통계 - 월별 */
			$.each(data.issueStatisticsListMonth, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
					   str_issue_month += "<tr class='" + tr_class + "'>" + 
		   			   "<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";
		   			   
					   			   
					   			   if(NvlStr(value.done_yn_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='process'>" + NvlStr(value.done_yn_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.done_yn_cnt + "</td>";
					   			   }

					   			/* 주민등록등본 */
					   			   if(NvlStr(value.baa_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.baa_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.baa_cnt + "</td>";
					   			   }
					   			   
									/* 주민등록초본 */
					   			   if(NvlStr(value.bab_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bab_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bab_cnt + "</td>";
					   			   }
					   				/* 가족관계증명서 */
					   			   if(NvlStr(value.bba_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bba_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bba_cnt + "</td>";
					   			   }
					   			   
					   				/* 기본증명서 */
					   			   if(NvlStr(value.bbb_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bbb_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bbb_cnt + "</td>";
					   			   }
					   				/* 혼인관계증명서 */
					   			   if(NvlStr(value.bbc_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bbc_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bbc_cnt + "</td>";
					   			   }
					   				/* 입양관계증명서 */
					   			   if(NvlStr(value.bbd_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bbd_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bbd_cnt + "</td>";
					   			   }
					   				/* 제적등본 */
					   			   if(NvlStr(value.bca_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bca_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bca_cnt + "</td>";
					   			   }
					   				/* 제적초본 */
					   			   if(NvlStr(value.bcb_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.bcb_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.bcb_cnt + "</td>";
					   			   }
					   				
					   				/* 수입증지 */
					   			   if(NvlStr(value.job_cnt) != "0"){
					   					str_issue_month += "<td align='center' class='denied'>" + NvlStr(value.job_cnt) + "</td>";
					   			   }else{
					   					str_issue_month += "<td align='center'>" + value.job_cnt + "</td>";
					   			   }
											   			   
			   			str_issue_month += "</tr>";
			});
			issue_table_month.append(str_issue_month);
			
			
			/* 발급 통계 - 일별 */
			$.each(data.issueStatisticsListDay, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
					   str_issue_day += "<tr class='" + tr_class + "'>" + 
		   			   						"<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";
		   			   
					   			   if(NvlStr(value.done_yn_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='process'>" + NvlStr(value.done_yn_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.done_yn_cnt + "</td>";
					   			   }

					   			/* 주민등록등본 */
					   			   if(NvlStr(value.baa_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.baa_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.baa_cnt + "</td>";
					   			   }
					   			   
									/* 주민등록초본 */
					   			   if(NvlStr(value.bab_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bab_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bab_cnt + "</td>";
					   			   }
					   				/* 가족관계증명서 */
					   			   if(NvlStr(value.bba_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bba_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bba_cnt + "</td>";
					   			   }
					   			   
					   				/* 기본증명서 */
					   			   if(NvlStr(value.bbb_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bbb_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bbb_cnt + "</td>";
					   			   }
					   				/* 혼인관계증명서 */
					   			   if(NvlStr(value.bbc_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bbc_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bbc_cnt + "</td>";
					   			   }
					   				/* 입양관계증명서 */
					   			   if(NvlStr(value.bbd_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bbd_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bbd_cnt + "</td>";
					   			   }
					   				/* 제적등본 */
					   			   if(NvlStr(value.bca_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bca_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bca_cnt + "</td>";
					   			   }
					   				/* 제적초본 */
					   			   if(NvlStr(value.bcb_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.bcb_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.bcb_cnt + "</td>";
					   			   }
					   				
					   				/* 수입증지 */
					   			   if(NvlStr(value.job_cnt) != "0"){
					   					str_issue_day += "<td align='center' class='denied'>" + NvlStr(value.job_cnt) + "</td>";
					   			   }else{
					   					str_issue_day += "<td align='center'>" + value.job_cnt + "</td>";
					   			   }
											   			   
			   			str_issue_day += "</tr>";
			   			
			});
			issue_table_day.append(str_issue_day);
			
			
			
			
			/* 수수료 통계 - 연별 */
			$.each(data.peeStatisticsListYear, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
					   str_pee_year += "<tr class='" + tr_class + "'>" + 
	   			   						"<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";
		   			   
				   			   
					   			   if(NvlStr(value.done_yn_price) != "0"){
					   					str_pee_year += "<td align='center' class='process'>" + NvlStr(value.done_yn_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.done_yn_price + "</td>";
					   			   }
					   			   

					   			/* 주민등록등본 */
					   			   if(NvlStr(value.baa_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.baa_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.baa_price + "</td>";
					   			   }
					   			   
									/* 주민등록초본 */
					   			   if(NvlStr(value.bab_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bab_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bab_price + "</td>";
					   			   }
					   				/* 가족관계증명서 */
					   			   if(NvlStr(value.bba_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bba_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bba_price + "</td>";
					   			   }
					   			   
					   				/* 기본증명서 */
					   			   if(NvlStr(value.bbb_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bbb_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bbb_price + "</td>";
					   			   }
					   				/* 혼인관계증명서 */
					   			   if(NvlStr(value.bbc_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bbc_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bbc_price + "</td>";
					   			   }
					   				/* 입양관계증명서 */
					   			   if(NvlStr(value.bbd_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bbd_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bbd_price + "</td>";
					   			   }
					   				/* 제적등본 */
					   			   if(NvlStr(value.bca_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bca_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bca_price + "</td>";
					   			   }
					   				/* 제적초본 */
					   			   if(NvlStr(value.bcb_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.bcb_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.bcb_price + "</td>";
					   			   }
					   				
					   				/* 수입증지 */
					   			   if(NvlStr(value.job_price) != "0"){
					   					str_pee_year += "<td align='center' class='denied'>" + NvlStr(value.job_price) + "</td>";
					   			   }else{
					   					str_pee_year += "<td align='center'>" + value.job_price + "</td>";
					   			   }
											   			   
			   			str_pee_year += "</tr>";
			});
			pee_table_year.append(str_pee_year);
			
			/* 수수료 통계 - 월별 */
			$.each(data.peeStatisticsListMonth, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
					   str_pee_month += "<tr class='" + tr_class + "'>" + 
		   			   					"<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";
		   			   
					   			   if(NvlStr(value.done_yn_price) != "0"){
					   					str_pee_month += "<td align='center' class='process'>" + NvlStr(value.done_yn_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.done_yn_price + "</td>";
					   			   }
					   			   
					   			/* 주민등록등본 */
					   			   if(NvlStr(value.baa_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.baa_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.baa_price + "</td>";
					   			   }
					   			   
									/* 주민등록초본 */
					   			   if(NvlStr(value.bab_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bab_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bab_price + "</td>";
					   			   }
					   				/* 가족관계증명서 */
					   			   if(NvlStr(value.bba_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bba_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bba_price + "</td>";
					   			   }
					   			   
					   				/* 기본증명서 */
					   			   if(NvlStr(value.bbb_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bbb_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bbb_price + "</td>";
					   			   }
					   				/* 혼인관계증명서 */
					   			   if(NvlStr(value.bbc_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bbc_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bbc_price + "</td>";
					   			   }
					   				/* 입양관계증명서 */
					   			   if(NvlStr(value.bbd_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bbd_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bbd_price + "</td>";
					   			   }
					   				/* 제적등본 */
					   			   if(NvlStr(value.bca_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bca_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bca_price + "</td>";
					   			   }
					   				/* 제적초본 */
					   			   if(NvlStr(value.bcb_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.bcb_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.bcb_price + "</td>";
					   			   }
					   				
					   				/* 제적초본 */
					   			   if(NvlStr(value.job_price) != "0"){
					   					str_pee_month += "<td align='center' class='denied'>" + NvlStr(value.job_price) + "</td>";
					   			   }else{
					   					str_pee_month += "<td align='center'>" + value.job_price + "</td>";
					   			   }
											   			   
			   			str_pee_month += "</tr>";
			});
			pee_table_month.append(str_pee_month);
			
			
			/* 수수료 통계 - 일별 */
			$.each(data.peeStatisticsListDay, function(key, value){
					
				       if ((value.rnum % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
					   
					   str_pee_day += "<tr class='" + tr_class + "'>" + 
		   			   					"<td align='center' style='color:#444444;'>" + formatDate(NvlStr(value.process_date), ".") + "</td>";

					   			   if(NvlStr(value.done_yn_price) != "0"){
					   					str_pee_day += "<td align='center' class='process'>" + NvlStr(value.done_yn_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.done_yn_price + "</td>";
					   			   }
					   			   
					   			/* 주민등록등본 */
					   			   if(NvlStr(value.baa_price) != "0"){
					   				str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.baa_price) + "</td>";
					   			   }else{
					   				str_pee_day += "<td align='center'>" + value.baa_price + "</td>";
					   			   }
					   			   
									/* 주민등록초본 */
					   			   if(NvlStr(value.bab_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bab_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bab_price + "</td>";
					   			   }
					   				/* 가족관계증명서 */
					   			   if(NvlStr(value.bba_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bba_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bba_price + "</td>";
					   			   }
					   			   
					   				/* 기본증명서 */
					   			   if(NvlStr(value.bbb_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bbb_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bbb_price + "</td>";
					   			   }
					   				/* 혼인관계증명서 */
					   			   if(NvlStr(value.bbc_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bbc_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bbc_price + "</td>";
					   			   }
					   				/* 입양관계증명서 */
					   			   if(NvlStr(value.bbd_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bbd_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bbd_price + "</td>";
					   			   }
					   				/* 제적등본 */
					   			   if(NvlStr(value.bca_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bca_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bca_price + "</td>";
					   			   }
					   				/* 제적초본 */
					   			   if(NvlStr(value.bcb_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.bcb_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.bcb_price + "</td>";
					   			   }
					   				
					   				/* 수입증지 */
					   			   if(NvlStr(value.job_price) != "0"){
					   					str_pee_day += "<td align='center' class='denied'>" + NvlStr(value.job_price) + "</td>";
					   			   }else{
					   					str_pee_day += "<td align='center'>" + value.job_price + "</td>";
					   			   }
											   			   
			   			str_pee_day += "</tr>";
									   
			});
			pee_table_day.append(str_pee_day);
			
		}
		
		
		//엑셀 다운로드
		function doExcelDownloadProcess(mappingValue){
			$("input[name=from_date]", "form[name=excelDown]").val($("#FROM_DATE").val().replace(/-/gi, ""));
			$("input[name=to_date]", "form[name=excelDown]").val($("#TO_DATE").val().replace(/-/gi, ""));
			
		    var f = document.excelDown;
		    f.action = mappingValue;
		    f.submit();
		}
		
</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>

<!-- 타이틀 -->
<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/service/images/popup_title.gif" align="absmiddle">
	</a>민원서류발급 사전예약신청 통계현황
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/service/docIssueReserveList.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 발급화면</a>
	</div>
</div>

<div style="width:1080px;padding-left:20px;">
	<div class="form-group" style="margin-bottom:5px;">
			<div class="input-group">
				<form action="" method="post">
						<div class="input-group-addon" style="width:80px;height:40px;">From</div>
						<input type="text" id="FROM_DATE" name="FROM_DATE" value="" class="form-control" style="height:40px;">
						<div class="input-group-addon" style="height:40px;">
							<i class="fa fa-calendar-o"></i>
						</div>
						&nbsp;&nbsp;~&nbsp;&nbsp;
						<div class="input-group-addon" style="width:80px;height:40px;">To</div>
						<input type="text" id="TO_DATE" name="TO_DATE" value="" class="form-control" style="height:40px;">
						<div class="input-group-addon" style="height:40px;">
							<i class="fa fa-calendar-o"></i>
						</div>
						
						<div style="padding-left:5px;padding-bottom:10px;float:right;">
							<a href="#this" class="btn btn-primary" id="search"><i class="fa fa-search"></i> 조회</a>
						</div>
					
		
				</form>
			
				<div style="float:right;margin-left:10px;margin-bottom:10px;">
					<!-- 엑셀 다운용 폼 -->
					<form id="excelDown" name="excelDown" method="post" enctype="multipart/form-data">
						<input type="hidden" name="from_date">
						<input type="hidden" name="to_date">
					
						<input type="image" src="/service/images/common/btn_excel.gif" style="border:0;" onclick="doExcelDownloadProcess('/service/daejang/downloadExcelFile.do')" alt="엑셀다운로드">
					</form>
				</div>
		
		</div>
	
	</div>	
</div>
</form>

									<div class="card-body">
										<div class="custom-tab">

											<nav>
												<div class="nav nav-tabs" id="nav-tab" role="tablist">
													<a class="nav-item nav-link active" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home"
													 aria-selected="true">발급통계</a>
													<a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile"
													 aria-selected="false">수수료통계</a>
												</div>
											</nav>
											<div class="tab-content pl-3 pt-2" id="nav-tabContent">
												<div class="tab-pane fade show active" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
												
											<nav>
												<div class="nav nav-tabs" id="nav-tab" role="tablist">
													<a class="nav-item nav-link active" id="custom-nav-issue-year-tab" data-toggle="tab" href="#custom-nav-issue-year" role="tab" aria-controls="custom-nav-issue-year"
													 aria-selected="true">년</a>
													<a class="nav-item nav-link" id="custom-nav-issue-month-tab" data-toggle="tab" href="#custom-nav-issue-month" role="tab" aria-controls="custom-nav-issue-month"
													 aria-selected="false">월</a>
 													<a class="nav-item nav-link" id="custom-nav-issue-day-tab" data-toggle="tab" href="#custom-nav-issue-day" role="tab" aria-controls="custom-nav-issue-day"
													 aria-selected="false">일</a>
												</div>
											</nav>
											
											<div class="tab-content pl-3 pt-2" id="nav-tabContent">
												<div class="tab-pane fade show active" id="custom-nav-issue-year" role="tabpanel" aria-labelledby="custom-nav-issue-year-tab">
													<div class="col-md-12" align="center">
															<div class="table-responsive m-b-40">
																 <table class="table table-borderless table-data3">
															     <thead style="background-color:#0B2161;">
															        <tr align="center">
																					<th>년도</th>
																					<th>전체완료건수</th>
																					<th>주민등록등본</th>
																					<th>주민등록초본</th>
																					<th>가족관계증명서</th>
																					<th>기본증명서</th>
																					<th>혼인관계증명서</th>
																					<th>입양관계증명서</th>
																					<th>제적등본</th>
																					<th>제적초본</th>
																					<th>수입증지</th>
																				</tr>
															      </thead>
															      <tbody id='issue_table_year'>     
															      </tbody>
															      </table>
														      </div>
														</div>
												</div>
												<div class="tab-pane fade" id="custom-nav-issue-month" role="tabpanel" aria-labelledby="custom-nav-issue-month-tab">
													<div class="col-md-12" align="center">
															<div class="table-responsive m-b-40">
																 <table class="table table-borderless table-data3">
															     <thead style="background-color:#0B2161;">
															        <tr align="center">
																					<th>월</th>
																					<th>전체완료건수</th>
																					<th>주민등록등본</th>
																					<th>주민등록초본</th>
																					<th>가족관계증명서</th>
																					<th>기본증명서</th>
																					<th>혼인관계증명서</th>
																					<th>입양관계증명서</th>
																					<th>제적등본</th>
																					<th>제적초본</th>
																					<th>수입증지</th>
																				</tr>
															      </thead>
															      <tbody id='issue_table_month'>     
															      </tbody>
															      </table>
														      </div>
														</div>
												</div>
												<div class="tab-pane fade" id="custom-nav-issue-day" role="tabpanel" aria-labelledby="custom-nav-issue-day-tab">
													<div class="col-md-12" align="center">
															<div class="table-responsive m-b-40">
																 <table class="table table-borderless table-data3">
															     <thead style="background-color:#0B2161;">
															        <tr align="center">
																					<th>일</th>
																					<th>전체완료건수</th>
																					<th>주민등록등본</th>
																					<th>주민등록초본</th>
																					<th>가족관계증명서</th>
																					<th>기본증명서</th>
																					<th>혼인관계증명서</th>
																					<th>입양관계증명서</th>
																					<th>제적등본</th>
																					<th>제적초본</th>
																					<th>수입증지</th>
																				</tr>
															      </thead>
															      <tbody id='issue_table_day'>     
															      </tbody>
															      </table>
														      </div>
														</div>
												</div>
											</div>
										</div>
										<div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
											<nav>
												<div class="nav nav-tabs" id="nav-tab" role="tablist">
													<a class="nav-item nav-link active" id="custom-nav-pee-year-tab" data-toggle="tab" href="#custom-nav-pee-year" role="tab" aria-controls="custom-nav-pee-year"
													 aria-selected="true">년</a>
													<a class="nav-item nav-link" id="custom-nav-pee-month-tab" data-toggle="tab" href="#custom-nav-pee-month" role="tab" aria-controls="custom-nav-pee-month"
													 aria-selected="false">월</a>
 													<a class="nav-item nav-link" id="custom-nav-pee-day-tab" data-toggle="tab" href="#custom-nav-pee-day" role="tab" aria-controls="custom-nav-pee-day"
													 aria-selected="false">일</a>
												</div>
											</nav>
											
											<div class="tab-content pl-3 pt-2" id="nav-tabContent">
												<div class="tab-pane fade show active" id="custom-nav-pee-year" role="tabpanel" aria-labelledby="custom-nav-pee-year-tab">
													<div class="col-md-12" align="center">
														<div class="table-responsive m-b-40">
															 <table class="table table-borderless table-data3">
														     <thead style="background-color:#0B2161;">
														        <tr align="center">
																				<th>년도</th>
																				<th>완료수수료합계</th>
																				<th>주민등록등본</th>
																				<th>주민등록초본</th>
																				<th>가족관계증명서</th>
																				<th>기본증명서</th>
																				<th>혼인관계증명서</th>
																				<th>입양관계증명서</th>
																				<th>제적등본</th>
																				<th>제적초본</th>
																				<th>수입증지</th>
																			</tr>
														      </thead>
														      <tbody id='pee_table_year'>     
														      </tbody>
														      </table>
													      </div>
													</div>
												</div>
												<div class="tab-pane fade" id="custom-nav-pee-month" role="tabpanel" aria-labelledby="custom-nav-pee-month-tab">
													<div class="col-md-12" align="center">
														<div class="table-responsive m-b-40">
															 <table class="table table-borderless table-data3">
														     <thead style="background-color:#0B2161;">
														        <tr align="center">
																				<th>월</th>
																				<th>완료수수료합계</th>
																				<th>주민등록등본</th>
																				<th>주민등록초본</th>
																				<th>가족관계증명서</th>
																				<th>기본증명서</th>
																				<th>혼인관계증명서</th>
																				<th>입양관계증명서</th>
																				<th>제적등본</th>
																				<th>제적초본</th>
																				<th>수입증지</th>
																			</tr>
														      </thead>
														      <tbody id='pee_table_month'>     
														      </tbody>
														      </table>
													      </div>
													</div>
												</div>
												<div class="tab-pane fade" id="custom-nav-pee-day" role="tabpanel" aria-labelledby="custom-nav-pee-day-tab">
													<div class="col-md-12" align="center">
														<div class="table-responsive m-b-40">
															 <table class="table table-borderless table-data3">
														     <thead style="background-color:#0B2161;">
														        <tr align="center">
																				<th>일</th>
																				<th>완료수수료합계</th>
																				<th>주민등록등본</th>
																				<th>주민등록초본</th>
																				<th>가족관계증명서</th>
																				<th>기본증명서</th>
																				<th>혼인관계증명서</th>
																				<th>입양관계증명서</th>
																				<th>제적등본</th>
																				<th>제적초본</th>
																				<th>수입증지</th>
																			</tr>
														      </thead>
														      <tbody id='pee_table_day'>     
														      </tbody>
														      </table>
													      </div>
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>
							</div>


	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
</body>
</html>