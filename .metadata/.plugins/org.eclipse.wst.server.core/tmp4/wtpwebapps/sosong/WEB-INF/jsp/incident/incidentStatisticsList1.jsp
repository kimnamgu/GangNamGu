<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		$(document).ready(function(){
			fn_incidentStatisticsList1(1);
			//fn_initial_setting();
			
			$("#inquery").on("click", function(e){ //검색 버튼
				e.preventDefault();			
				fn_incidentStatisticsList1();
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();				
				$("#form1").each(function() {				 
			       this.reset();  
			    });  
			});
			
			$("#mon").on("click", function(e){ //초기화 버튼
				//fn_day_setting();
			});
			
			$("#print").on("click", function(e){ //프린트 버튼
				e.preventDefault();
				fn_print();
			});	
			
			$("#excel").on("click", function(e){ //엑셀다운 버튼
				e.preventDefault();
				fn_exceldown();
			});
			
		});
		
		
		function fn_print(){     
					
			$('#divMemoMainBody1').css("display","none");
			$('#divMemoMainBody2').css("display","none");
			 
			factory.printing.portrait = false;                    //출력방향 설정: true-세로, false-가로
			factory.printing.leftMargin = 0.0;                //왼쪽 여백 설정 
			factory.printing.topMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.rightMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.bottomMargin = 0.0;            //왼쪽 여백 설정			
			factory.printing.Print(false, window);			
		}
		
		
		function dateToYYYYMMDD(date)
		{
		    function pad(num) {
		        num = num + '';
		        return num.length < 2 ? '0' + num : num;
		    }
		    return date.getFullYear() + '-' + pad(date.getMonth()+1) + '-' + pad(date.getDate());
		}
		
		function fn_initial_setting(){
			
			var fromdate = new Date();
			var todate = new Date();
			var tmpday = "";
			
			fromdate.setDate(fromdate.getDate()); //당일
			tmpday = dateToYYYYMMDD(fromdate);
			$("#EDDATE").val(tmpday); 
						
			todate.setYear(todate.getYear()-1); //1년전			
			tmpday = dateToYYYYMMDD(todate);
			$("#STDATE").val(tmpday); 
		}
		
		function fn_day_setting(){
			
			var fromdate = new Date();
			var todate = new Date();
			var tmpday = "";
			
			fromdate.setDate(fromdate.getDate()); //당일
			tmpday = dateToYYYYMMDD(fromdate);
			$("#EDDATE").val(tmpday); 
			
			todate.setYear(todate.getYear()-1); //1년전			
			tmpday = dateToYYYYMMDD(todate);
			$("#STDATE").val(tmpday);
			
		}
		
	
		
		function fn_incidentStatisticsList1(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/incident/selectIncidentStatisticsList1.do' />");
			comAjax.setCallback("fn_selectIncidentStatisticsList1Callback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			comAjax.addParam("STDATE", $("#STDATE").val());
			if($("#EDDATE").val() == "" || $("#EDDATE").val() == null)
				comAjax.addParam("EDDATE", $.datepicker.formatDate($.datepicker.ATOM, new Date())); //오늘일자
			else
				comAjax.addParam("EDDATE", $("#EDDATE").val());
			comAjax.ajax();					
		}
		
		
		function fn_selectIncidentStatisticsList1Callback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			body.empty();
			
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"11\">조회된 결과가 없습니다.</td>" +
						  "</tr>";
						
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_incidentStatisticsList1"
				};
				gfn_renderPaging(params);
				
				var str = "";
			
				$.each(data.list, function(key, value){
					
					str += "<tr>" +
						   "<td>" + NvlStr(value.TT1) + "</td>" +				                 							 		                  					              
		                   "<td>" + comma(NvlStr(value.MAXST)) + "</td>" + 									 
						   "<td>" + comma(NvlStr(value.MAXSD)) +  "</td>" +
						   "<td>" + comma(NvlStr(value.MAXSW)) +  "</td>" +
						   "<td>" + comma(NvlStr(value.WIN1)) +  "</td>" +
						   "<td>" + comma(NvlStr(value.WIN2)) + "</td>" +									 
						   "<td>" + comma(NvlStr(value.WIN3)) + "</td>" +									 										  
						   "<td>" + comma(NvlStr(value.WIN4)) + "</td>" +
						   "<td>" + comma(NvlStr(value.LOSE)) + "</td>" +
						   "<td>" + comma(NvlStr(value.WINP)) + "</td>" +
						   "<td>" + comma(NvlStr(value.ING)) + "</td>" +
						   "</tr>";
							
				});				
				body.append(str);
			}
		}
		
		function fn_exceldown(){			
			var comSubmit = new ComSubmit();	
			
			
			comSubmit.setUrl("<c:url value='/incident/excelIncidentStatistics1.do' />");			
			comSubmit.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			comSubmit.addParam("STDATE", $("#STDATE").val());
			if($("#EDDATE").val() == "" || $("#EDDATE").val() == null)
				comSubmit.addParam("EDDATE", $.datepicker.formatDate($.datepicker.ATOM, new Date())); //오늘일자
			else
				comSubmit.addParam("EDDATE", $("#EDDATE").val());
			
			comSubmit.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://98.23.7.110:8099/sosong/common/smsx.cab#Version=7,7,0,20"></object>
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">

 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">소송내역 및 결과</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  <tr>
    <td class="pupup_frame" style="padding-right:12px">
      <table width="720" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">	 
      <tr>
        <td width="100" class="tbl_field">기간</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="STDATE" name="STDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, STDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a> ~
            <input type="text" id="EDDATE" name="EDDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, EDDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a>
            <!-- <span id="mon">한달</span> <span id="year">1년</span> <span id="3year">3년</span> -->                        
        </td>
      </tr>
	  <tr>
        <td width="100" class="tbl_field">담당부서</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="DEPT_NAME" name="DEPT_NAME" class="input" style="width:300;">	                          
        </td>
      </tr>         
      </table>
    
	   <!-- ----------------------------------- 버튼 시작 ------------------------------------------>
	   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">
	     <tr>
	       <td></td>
	       <td align="right">
	       <table border="0" cellspacing="0" cellpadding="0">
	         <tr>
	          <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>
	           </td>
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
	             <tr>
	               <td><img src="/sosong/images/btn_type0_head.gif"></td>
	               <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="inquery"><font color="white">조회</font></a></td>
	               <td><img src="/sosong/images/btn_type0_end.gif"></td>
	             </tr>
	           </table>                
	           </td>		
	         </tr>
	       </table>
	       </td>
	     </tr>
	     <tr>
	       <td align="left"></td>
	       <td align="right">
	       <table border="0" cellspacing="0" cellpadding="0">
	         <tr>
	         <!-- 
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>
	           </td>
	           -->				
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="print">프린트</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>
	           </td>	           						
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="excel">엑셀다운로드</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
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
       <!-- ----------------------------------- 버튼 끝 ------------------------------------------>
    
		<table class="tbl2">			
			<tr>
	            <th rowspan=3 width="80">구분</th>
	            <th rowspan=3 width="80">총계</th>
				<th colspan=7>종결내역</td>
				<th rowspan=3 width="80">승소율</th>
	            <th rowspan=3 width="80">진행중</th>							
          	</tr>
          	<tr>
	            <th rowspan=2 width="80">소계</th>
				<th colspan=5>승소유형</th>
	            <th rowspan=2 width="80">패소</th>	            						
          	</tr>
          	<tr>
	            <th width="80">소계</th>
	            <th width="80">승소</th>
	            <th width="80">일부승소</th>
	            <th width="80">소취하</th>
	            <th width="80">기타</th>	            							
	        </tr>	       
			<tbody id='mytable'>				
			</tbody>
		</table>
	</td>
	</tr>
</table>
</form>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
		
</body>
</html>