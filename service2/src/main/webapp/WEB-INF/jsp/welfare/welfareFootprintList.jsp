<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var dept_name = "${sessionScope.userinfo.deptName}";
// 		var user_id = "${sessionScope.userinfo.userId}";
		var user_id = "${sessionScope.userinfo.userId}";
		var ws;
	    var messages = document.getElementById("message");
	    var r_jsonData = null;
	        		
		$(document).ready(function(){
						
			fn_initial_setting();
			//openSocket();
			fn_selectDocIssueReserveList(1);
			
			//서류발급 선택옵션 항목명 가져오기
// 			fn_getDocuOptionKindList();
			
			$("#message").on("DOMSubtreeModified propertychange", function() {
                //$('#summary_total_price').text( $(this).text() );
                
                if($("#message").text() != 'Socket open!!!')
                {
                	fn_selectDocIssueReserveList(1);                	
                }	
            });
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
					fn_initial_setting();
			    });
			});
			
			
			$("#APPLY_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectDocIssueReserveList(1);
			});
			
						
// 			$("#APPLY_UNAME").keydown(function (key) {

// 				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
// 					$("#PAGE_INDEX").val("1");
// 					fn_selectDocIssueReserveList(1);
// 				}

// 			});
			
			$("#APPLY_DATE").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectDocIssueReserveList(1);
				}

			});
			
			/* 팝업 닫기 버튼 */
			$('#close2').click(function() {
				$('#pop1').hide();
		    });
			
			/* 로그아웃 */
			$("#logout").on("click", function(e){
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
			
		
			$("#DISP_CNT").change(function(){
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectDocIssueReserveList(1);
			});	
 			
			
			$("#SAUP_DEPT_CD").change(function(){
				fn_selectDocIssueReserveList(1);
			});	
			
		});
		
		
		function fn_initial_setting(){
			$("#ID").val("");
			$("#APPLY_DATE").val("");
// 			$("#APPLY_UNAME").val("");
			
		    $("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
			$("#PAGE_INDEX").val("1");
			$('input:radio[id=LANGUAGE1_GB]').prop("checked", true);
			$('input:radio[id=ISSUE1_YN]').prop("checked", true);		
			$('input:radio[id=DISPOSAL2_YN]').prop("checked", true);
			$('input:radio[id=DONE1_YN]').prop("checked", true);
		}
		
		//차트 그리기
	  function chartDrow(statisticsList){
        var chartData = '';
 
        //날짜형식 변경하고 싶으시면 이 부분 수정하세요.
        var chartDateformat     = 'yyyy년MM월dd일';
        //라인차트의 라인 수
        var chartLineCount    = 10;
        //컨트롤러 바 차트의 라인 수
        var controlLineCount    = 10;
 
 
        function drawDashboard() {
 
          var data = new google.visualization.DataTable();
          //그래프에 표시할 컬럼 추가
          data.addColumn('datetime' , '날짜');
          data.addColumn('number'   , '전체');
          data.addColumn('number'   , '발급');
          data.addColumn('number'   , '서손');
          data.addColumn('number'   , '완료');
          data.addColumn('number'   , '취소');
 
          //그래프에 표시할 데이터
          var dataRow = [];
    		   
    	$.each(statisticsList,function(index,item){
		    dataRow = [new Date(item.process_date.substring(0,4), item.process_date.substring(4,6)-1, item.process_date.substring(6,8) ,'00' ),item.total_cnt, item.issue_yn_cnt, item.disposal_yn_cnt , item.done_yn_cnt, item.cancel_yn_cnt];
		    data.addRow(dataRow);
        });
    
            var chart = new google.visualization.ChartWrapper({
              chartType   : 'LineChart',
              containerId : 'lineChartArea', //라인 차트 생성할 영역
              options     : {
                              isStacked   : 'percent',
                              focusTarget : 'category',
                              height          : 170,
                              width              : '100%',
                              legend          : { position: "top", textStyle: {fontSize: 13}},
                              pointSize        : 5,
                              tooltip          : {textStyle : {fontSize:12}, showColorCode : true,trigger: 'both'},
                              hAxis              : {format: chartDateformat, gridlines:{count:chartLineCount,units: {
                                                                  years : {format: ['yyyy년']},
                                                                  months: {format: ['MM월']},
                                                                  days  : {format: ['dd일']},
                                                                  hours : {format: ['HH시']}}
                                                                },textStyle: {fontSize:12}},
                vAxis              : {minValue: 1,viewWindow:{min:0},gridlines:{count:-1},textStyle:{fontSize:12}},
                animation        : {startup: true,duration: 1000,easing: 'in' },
                annotations    : {pattern: chartDateformat,
                                textStyle: {
                                fontSize: 15,
                                bold: true,
                                italic: true,
                                color: '#871b47',
                                auraColor: '#d799ae',
                                opacity: 0.8,
                                pattern: chartDateformat
                              }
                            }
              }
            });
 
            var control = new google.visualization.ControlWrapper({
              controlType: 'ChartRangeFilter',
              containerId: 'controlsArea',  //control bar를 생성할 영역
              options: {
                  ui:{
                        chartType: 'LineChart',
                        chartOptions: {
                        chartArea: {'width': '60%','height' : 40},
                          hAxis: {'baselineColor': 'none', format: chartDateformat, textStyle: {fontSize:12},
                            gridlines:{count:controlLineCount,units: {
                                  years : {format: ['yyyy년']},
                                  months: {format: ['MM월']},
                                  days  : {format: ['dd일']},
                                  hours : {format: ['HH시']}}
                            }}
                        }
                  },
                    filterColumnIndex: 0
                }
            });
 
            var date_formatter = new google.visualization.DateFormat({ pattern: chartDateformat});
            date_formatter.format(data, 0);
 
            var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
            window.addEventListener('resize', function() { dashboard.draw(data); }, false); //화면 크기에 따라 그래프 크기 변경
            dashboard.bind([control], [chart]);
            dashboard.draw(data);
 
        }
          google.charts.setOnLoadCallback(drawDashboard);
 
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
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			//data = $.parseJSON(data);
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value='" + value.DEPT_ID + "'>" + value.DEPT_NAME + "</option>";
					i++;
			});
			
			$("#SAUP_DEPT_CD").append(str);			
		}
		
		
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		
		function fn_popDetail(obj){
			
			var json = $.parseJSON(obj.parent().find("#UID1").val());
			var body = $("#mytable2");
			var str = "";
			var i = 1;
					
			$('#pop1').show();
			body.empty();	
		
			$.each(json, function(key, value){
				str += "<tr>" +
				   "<td align='center' style='padding:5px 10px;'>" + i + "</td>" +
				   "<td style='padding:5px 10px;'>" + getOptionName(key) + "</td>";
				   
				  //주민번호 뒷자리 한글로 변환
				  if(NvlStr(key) =="resident_back_number"){
						  switch (value){
						    case "M" :
						    	value = "본인";
						        break;
						    case "F" :
						    	value = "세대원";
						        break;
						    case "N" :
						    	value = "미포함";
						        break;
						}
				  }
				  
				   
	   			   if(NvlStr(value) == "Y"){
	   					str += "<td align='center' style='padding:5px 10px;'><span class='status--process'>" + NvlStr(value) + "</span></td>";
	   			   }else{
	   					str += "<td align='center' style='padding:5px 10px;'><span class='status--denied'>" + NvlStr(value) + "</span></td>";
	   			   }
				   
                   str += "</tr>";
				   i++;					
			});
						
			body.append(str);			
		}
		
		
	    function getOptionName(str){
	    	console.log(r_jsonData);
	    	var jsonobj = $.parseJSON(r_jsonData);
	    	var tmp= "";
	    	
	    	$.each(jsonobj.list, function(key, value){										
	    		if(str == value.item_bg)
	    		{	
	    			tmp = value.item_nm;
	    			return false;
	    		}
	    	});
	    	
	    	return tmp;
	    }
		
		
		function fn_getDocuOptionKindList(){
	    	
	    	var jsnobj = new Object();
	    	
	    	jsnobj.item_gb = "D";
	    	jsnobj.page_index = 1;
	    	jsnobj.page_row = 20;
	   	
			var jsonData = JSON.stringify(jsnobj);
			r_jsonData = null;
			
			$.ajax({
			        type:"POST",			        
			        url:"/service/api/v1/getDocumentKindList.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
			        	r_jsonData = JSON.stringify(data);			     											
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});									
	    }
		
		
		/* 버튼액션 (발급,완료,취소,미완료) */
		function fn_processDocIssueReserve(obj, workgb){
	    	
	    	var jsnobj = new Object();
	    	var userId = $("#USER_ID").val();
// 	    	alert("aa : " + userId);
	    	jsnobj.work_gb = workgb;
	    	jsnobj.id = obj.parent().find("#UID2").val();
	    	jsnobj.userId = userId;
	    	
	    	if(workgb == "D")
	    	{
	    		jsnobj.phone_id = obj.parent().find("#UPHONEID").val();
	    		jsnobj.document_gb = obj.parent().find("#UDOCGB").val();
	    		
	    	}
			var jsonData = JSON.stringify(jsnobj);
			//alert(jsonData);
			
			$.ajax({
			        type:"POST",
			        url:"/service/welfare/processDocIssueReserve.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
						//alert("success " + "data=[" + JSON.stringify(data) + "]" );
						//alert("success!!");
						fn_selectDocIssueReserveList(1);
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

	    }
		
		
		/* 리스트 조회 */
		function fn_selectDocIssueReserveList(pageNo){	
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/api/v1/welfareViewList.do' />");
			comAjax.addParam("daoGubunCode","01");
			comAjax.addParam("deptName",dept_name);
			comAjax.addParam("userId",user_id);
			comAjax.addParam("PAGE_INDEX",pageNo);
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());	
			comAjax.addParam("id", $("#ID").val());
// 			comAjax.addParam("apply_uname", $("#APPLY_UNAME").val());
			comAjax.addParam("apply_date", $("#APPLY_DATE").val().replace(/-/gi, ""));
			comAjax.setCallback("fn_selectDocIssueReserveListCallback");
			
			if( $("input:radio[name='LANGUAGE_GB']").is(":checked") == true){				
				comAjax.addParam("language_gb", $("input:radio[name='LANGUAGE_GB']:checked").val());			
			}
						
			if( $("input:radio[name='ISSUE_YN']").is(":checked") == true){				
				comAjax.addParam("issue_yn", $("input:radio[name='ISSUE_YN']:checked").val());			
			}			
			if( $("input:radio[name='DISPOSAL_YN']").is(":checked") == true){				
				comAjax.addParam("disposal_yn", $("input:radio[name='DISPOSAL_YN']:checked").val());			
			}
			if( $("input:radio[name='DONE_YN']").is(":checked") == true){				
				comAjax.addParam("done_yn", $("input:radio[name='DONE_YN']:checked").val());			
			}
			comAjax.ajax();
		}
		
		/* 리스트 조회 callback */
		function fn_selectDocIssueReserveListCallback(data){			
			//data = $.parseJSON(data);			
			var total = data.total;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			//구글차트 그리기
// 			google.charts.load('current', {'packages':['line','controls']});
// 			chartDrow(data.statisticsList); //chartDrow() 실행
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan='21' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectDocIssueReserveList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var tr_class = "";
				var tot_amt = 0;
				var jsonData = null;
				var jsonStr = null;
				
				// 사유컬럼 추가한다음 바꿔야함
				var testStr = "";

				var userAddress = "";
				var userSex = "";
				var userConfirmYn = "";
				
				
				$.each(data.list, function(key, value){
						testStr = value.DEL_ID;
						userSex = value.SEX;
						userConfirmYn = value.CONFIRM_YN;
						userAddress = value.ADDRESS;
						
						if(testStr == '' || testStr == null ){
							testStr = '';
						}
						if(userSex == '' || userSex == null ){
							userSex = '';
						}
						if(userConfirmYn == '' || userConfirmYn == null ){
							userConfirmYn = '';
						}
						if(userAddress == '' || userAddress == null ){
							userAddress = '';
						}
					
					       if ((value.rnum % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					      
					       
					       
// 					       jsonData = $.parseJSON(value.apply_option);
// 					       jsonstr = 	JSON.stringify(jsonData);
						   
					       str += "<tr class='" + tr_class + "'>" + 
// 									   "<td align='center' style='color:#222222;'>" + value.UUID + "</td>" +
									   "<td align='center' style='color:#222222;'>" + value.NAME + "</td>" +
									   "<td align='center' style='color:#222222;'>" + NvlStr(value.INS_DATE) + "</td>" +
									   "<td align='center' style='color:#222222;'>" + value.BIRTH + "</td>" +
									   "<td align='center' style='color:#444444;'>" + value.PHONE + "</td>" +	
									   "<td align='center' style='color:#444444;'>" + userAddress + "</td>" +	
									   "<td align='center' style='color:#444444;'>" + value.DONG + "</td>" +	
									   "<td align='center' style='color:#444444;'>" + value.GUBUN_NAME + "</td>" +	
									   "<td align='center' style='color:#444444;'>" + userSex + "</td>" +
									   "<td align='center' style='color:#444444;'>" + userConfirmYn + "</td>" +
									   "<td align='center' style='color:#444444;'>" +
									   "<a href='#this' name='title4' class='btn btn-success btn-sm' >완   료</a> " + 
									   "<a href='#this' name='title3' class='btn btn-secondary btn-sm' >미완료</a> " +
									   "</td>" +
									   "<td align='center' style='color:#444444;>'" + 
									   "<td align='center' style='color:#444444;'>" + testStr + "</td>" +
									   "</td>"

				});
				body.append(str);
				
				$("#t_amt").text(comma(tot_amt));
				
				$("a[name='title1']").on("click", function(e){ //제목 
					e.preventDefault();					
					fn_popDetail($(this));
					fn_prvCnrtPlanUpdate($(this));
				});
								
				$("a[name='title4']").on("click", function(e){ //완료
					e.preventDefault();
					confirmBox('완료처리로 변경 하시겠습니까?',fn_processDocIssueReserve,$(this), "C");
				});
				
				$("a[name='title3']").on("click", function(e){ //완료
					e.preventDefault();
					confirmBox('미완료처리로 변경 하시겠습니까?',fn_processDocIssueReserve,$(this), "I");
				});
				
			}
		}
	        
	        
		function fn_prvCnrtPlanUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanUpdateTe.do' />");			
			comSubmit.submit();
		}
		
		
        //자동 새로고침
//         var playAlert = setInterval("pagereload()", 30000);
        
// 		function pagereload() {
// 			$.ajax({
// 		        type:"POST",
// 		        url:"/service/api/v1/getDocIssueReserveRecent.do",
// 		        data : "",
// 		        dataType : "json",
// 		        contentType : "application/json; charset=UTF-8",			        
// 		        success : function(data) {
// 					if(data.INS_DATE > $("#recentData_date").val()){
// 						clearInterval(playAlert);
// 						alertBox2("새로운 입력데이터가 있습니다.새로고침을 수행합니다.");
// 					}
// 				},
// 		        error: function(xhr, status, error) {
// 		            alert("err: " + error);
// 		        }  
//    			});
			
// 		}
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
	</a>복지급여 신청관리
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/service/docIssueStatisticsList.do" class="btn btn-success" id="statistics"><i class="fa fa-bar-chart-o"></i> 통계정보확인</a>
	</div>
</div>

<div style="width:100%;padding-left:20px;padding-right:10px;">
	<div class="card" style="width:680px;display: inline-block">
		<div class="card-header" style="background-color:#0B2161;color:white;margin-bottom:10px;">검색</div>
		
	<form action="" method="post">
		<div class="card-body card-block" style="padding:5px;">
				<div style="float:left;">
<!-- 					<div class="form-group" style="margin-bottom:5px;"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div class="input-group-addon" style="width:80px;">ID</div> -->
<!-- 							<input type="text" id="ID" name="ID" value="" class="form-control"> -->
<!-- 							<div class="input-group-addon"> -->
<!-- 								<i class="fa fa-user"></i> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="form-group" style="margin-bottom:5px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;">신청일</div>
							<input type="text" id="APPLY_DATE" name="APPLY_DATE" value="" class="form-control">
							<div class="input-group-addon">
								<i class="fa fa-calendar-o"></i>
							</div>
						</div>
					</div>
<!-- 					<div class="form-group" style="margin-bottom:5px;"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div class="input-group-addon" style="width:80px;">이름</div> -->
<!-- 							<input type="text" id="APPLY_UNAME" name="APPLY_UNAME" value="" class="form-control"> -->
<!-- 							<div class="input-group-addon"> -->
<!-- 								<i class="fa fa-user"></i> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="form-actions form-group">
					<div class="form-group" style="margin-bottom:15px;">
						<div class="input-group">
							<div class="input-group-addon" style="width:80px;padding:3px;">완료여부</div>&nbsp;&nbsp;&nbsp;
				          	<div class="form-check-inline form-check">
								<label for="DONE1_YN" class="form-check-label ">
					            	<input type="radio" id="DONE1_YN" name="DONE_YN" value="" class="form-check-input" onfocus="this.blur()"> 전체 &nbsp;
					           	</label>
					           	<label for="DONE2_YN" class="form-check-label "> 
					            	<input type="radio" id="DONE2_YN" name="DONE_YN" value="N" class="form-check-input" onfocus="this.blur()"> 미완료 &nbsp;
					            </label>
					            <label for="DONE3_YN" class="form-check-label ">
					            	<input type="radio" id="DONE3_YN" name="DONE_YN" value="Y" class="form-check-input" onfocus="this.blur()"> 완료 &nbsp;
					           	</label>
				           	</div>
						</div>
					</div>
					<div style="padding-left:5px;padding-bottom:10px;float:left;">
						<a href="#this" class="btn btn-danger" id="initial"><i class="fa fa-ban"></i> 검색초기화</a>
						<a href="#this" class="btn btn-primary" id="search"><i class="fa fa-search"></i> 검색</a>
					</div>
				</div>
				</div>
				<div style="padding-left:15px;float:left;">
<!-- 					<div class="form-group" style="margin-bottom:5px;"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div class="input-group-addon" style="width:80px;padding:3px;">담당지정</div>&nbsp;&nbsp;&nbsp; -->
<!-- 							<div class="form-check-inline form-check"> -->
<!-- 				                 <label for="LANGUAGE1_GB" class="form-check-label "> -->
<!-- 				                     <input type="radio" id="LANGUAGE1_GB" name="LANGUAGE_GB" value="" class="form-check-input">전체 &nbsp;&nbsp; -->
<!-- 				                 </label> -->
<!-- 				                 <label for="LANGUAGE2_GB" class="form-check-label "> -->
<!-- 				                     <input type="radio" id="LANGUAGE2_GB" name="LANGUAGE_GB" value="CAA" class="form-check-input">지정 &nbsp;&nbsp; -->
<!-- 				                 </label> -->
<!-- 				                 <label for="LANGUAGE3_GB" class="form-check-label "> -->
<!-- 				                     &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="LANGUAGE3_GB" name="LANGUAGE_GB" value="CAB" class="form-check-input">&nbsp;미지정 &nbsp;&nbsp; -->
<!-- 				                 </label> -->
<!-- 				             </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="form-group" style="margin-bottom:5px;"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div class="input-group-addon" style="width:80px;padding:3px;">완료구분</div>&nbsp;&nbsp;&nbsp; -->
<!-- 				        	<div class="form-check-inline form-check"> -->
<!-- 								<label for="ISSUE1_YN" class="form-check-label "> -->
<!-- 					            	<input type="radio" id="ISSUE1_YN" name="ISSUE_YN" value="" class="form-check-input" onfocus="this.blur()"> 전체 &nbsp; -->
<!-- 					           	</label> -->
<!-- 					           	<label for="ISSUE2_YN" class="form-check-label ">  -->
<!-- 					            	<input type="radio" id="ISSUE2_YN" name="ISSUE_YN" value="N" class="form-check-input" onfocus="this.blur()"> 완료 &nbsp; -->
<!-- 					            </label> -->
<!-- 					            <label for="ISSUE3_YN" class="form-check-label "> -->
<!-- 					            	<input type="radio" id="ISSUE3_YN" name="ISSUE_YN" value="Y" class="form-check-input" onfocus="this.blur()"> 미완료 &nbsp; -->
<!-- 					           	</label> -->
<!-- 				           	</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="form-group" style="margin-bottom:5px;"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<div class="input-group-addon" style="width:80px;padding:3px;">서손여부</div>&nbsp;&nbsp;&nbsp; -->
<!-- 				           	<div class="form-check-inline form-check"> -->
<!-- 								<label for="DISPOSAL1_YN" class="form-check-label "> -->
<!-- 					            	<input type="radio" id="DISPOSAL1_YN" name="DISPOSAL_YN" value="" class="form-check-input" onfocus="this.blur()"> 전체 &nbsp; -->
<!-- 					           	</label> -->
<!-- 					           	<label for="DISPOSAL2_YN" class="form-check-label ">  -->
<!-- 					            	<input type="radio" id="DISPOSAL2_YN" name="DISPOSAL_YN" value="N" class="form-check-input" onfocus="this.blur()"> 미서손 &nbsp; -->
<!-- 					            </label> -->
<!-- 					            <label for="DISPOSAL3_YN" class="form-check-label "> -->
<!-- 					            	<input type="radio" id="DISPOSAL3_YN" name="DISPOSAL_YN" value="Y" class="form-check-input" onfocus="this.blur()"> 서손 &nbsp; -->
<!-- 					           	</label> -->
<!-- 				           	</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
				</div>
			</div>
				
			</form>
	</div>
	
	<!-- 조회시 최근 입력값 확인후 그것 보다 큰값이 db 에 있을경우 새로 고침 수행 -->	
	<input type="hidden" value="${recentData.INS_DATE}" id="recentData_date">
	
<!-- 	<div style="display: inline-block;width:900px;height:200px;padding-left:40px;"> -->
<!-- 	    <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;민원 접수 현황</h4> -->
 
<!--     	<div id="Line_Controls_Chart"> -->
<!--       		라인 차트 생성할 영역 -->
<!--           <div id="lineChartArea"></div> -->
<!--       		컨트롤바를 생성할 영역 -->
<!--           <div id="controlsArea"></div> -->
<!--         </div> -->
<!-- 	</div> -->
</div>



<table>
  <tr>
    <td class="pupup_frame" style="padding-left:20px">	
	
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">     
     <tr style="height:50px;">
       <td align="left">
	       <div style="float: left;">
				<b>Total : <span id="tcnt"></span> 건</b> &nbsp;
	       </div> 
			<div style="float: left;">
	            <select id="DISP_CNT" name="DISP_CNT" class="form-control" style="width:60px!important;height:30px;padding:3px;">
	            	<option value="10">10</option>
	            	<option value="20">20</option>
					<option value="50">50</option>
				</select>
			</div>
<!-- 			<div style="float: left;">						 -->
<!-- 		   	   &nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 		   	   <b><font color=#ff0000>총액 : <span id="t_amt"></span></font></b> -->
<!-- 	   	   </div> -->
       </td>
     </tr>
     <tr>
       <td class="margin_btn" colspan="2"></td>
     </tr>
   </table>
    

	
	</td>
	</tr>
</table>
<div class="col-md-12" align="center">
	<div class="table-responsive m-b-40">
		 <table class="table table-borderless table-data3">
	     <thead style="background-color:#0B2161;">
	        <tr align="center">
							<th>성명</th>
							<th>신청날짜</th>
							<th>생년월일</th>
							<th>연락처</th>
							<th>주소</th>
							<th>행정동</th>
							<th>구분명</th>
							<th>성별</th>
							<th>완료여부</th>
							<th>완료처리</th>
							<th>사유</th>
<!-- 							<th>금액</th> -->
<!-- 							<th>방문시간</th> -->
<!-- 							<th>신청시간</th> -->
<!-- 							<th>발급여부</th> -->
<!-- 							<th>발급시간</th> -->
<!-- 							<th>서손여부</th> -->
<!-- 							<th>서손시간</th> -->
<!-- 							<th>완료여부</th> -->
<!-- 							<th>완료시간</th> -->
<!-- 							<th>처리</th> -->
						</tr>
	      </thead>
	      <tbody id='mytable'>     
	      </tbody>
	      </table>
      </div>
</div>

</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	

	
<!-- POPUP --> 
<div id="pop1" style="position:absolute;left:550px;top:200;z-index:200;display:none;background-color:#e5e5e5;">
	<table width=500 height=250  border="1" cellpadding=2 cellspacing=0 class="table table-data2">
		<thead>
			<tr class="list_tit_tr">
				<th style="padding:5px 10px;" class="list_tit"></th>
				<th style="padding:5px 10px;" class="list_tit_bar">항목명</th>
				<th style="padding:5px 10px;" class="list_tit_bar">값</th>
			</tr>
		</thead>
		<tbody id='mytable2'>
		</tbody>
		<tr>
			<td colspan="3" style="padding:5px" align=right bgcolor=white><a href="#" id="close2"><B>[닫기]</B></a>
			</td>
		</tr>
	</table>
</div>
</body>
</html>