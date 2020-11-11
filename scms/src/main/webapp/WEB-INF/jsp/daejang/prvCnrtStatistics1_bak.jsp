<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var setCnt = 0;
		var varStDate = "";
		var varEdDate = "";
		
		$(document).ready(function(){
			
			fn_selectPrvCnrtStatistics1(1);
				
			$("#insert").on("click", function(e){ //신규입력
				e.preventDefault();			
				fn_insert();				
			});
			
						
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();
			    });				
			});
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtStatistics1();
			});
					 
			
			$("#TITLE").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtStatistics1();
				}

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
						
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtStatistics1();
			});
			
			
			$("#ST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#ED_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#ST_DATE").val(formatDate(varStDate,'-'));
			$("#ED_DATE").val(formatDate(varEdDate,'-'));
		});
		
		
		
		function getToday(){
		    var now = new Date();
		    var year = now.getFullYear();
		    var month = now.getMonth() + 1;    //1월이 0으로 되기때문에 +1을 함.
		    var date = now.getDate();  

		    if((month + "").length < 2){        //2자리가 아니면 0을 붙여줌.
		        month = "0" + month;
		    }else{
		         // ""을 빼면 year + month (숫자+숫자) 됨.. ex) 2018 + 12 = 2030이 리턴됨.
		        month = "" + month;   
		    }
		    return today = year + month + date; 
		}
		
		
	
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		    	  
		function fn_prvCnrtBoardDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.addParam("PARENT_ID", obj.parent().find("#ID").val());
			comSubmit.addParam("BOARD_GB", $("#BOARD_GB").val());
			comSubmit.addParam("BOARD_ID", $("#BOARD_ID").val());
			
			comSubmit.submit();
		}
		
		
		
		function fn_selectPrvCnrtStatistics1(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtStatistics1.do' />");
			comAjax.setCallback("fn_selectPrvCnrtStatistics1Callback");
			
			varStDate = $("#ST_DATE").val().replace(/-/g, '');
			varEdDate = $("#ED_DATE").val().replace(/-/g, '');
			
			if(varStDate == null || varStDate == "")
				varStDate = "20190101";
			if(varEdDate == null || varEdDate == "")
				varEdDate = getToday();
						
			comAjax.addParam("ST_DATE", varStDate);
			comAjax.addParam("ED_DATE", varEdDate);
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtStatistics1Callback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"8\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				
				var str = "";				
				var tr_class = "";
				var td_class = "";
				var tmp = "";
		
				$.each(data.list, function(key, value){					       
					       tmp = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       					    			
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.CNT  + "</td>" +							   			  	
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + NvlStr(value.TOT)  + "</a>" + 									  
									   "<input type='hidden' name='title' id='CNT' value=" + "tot" + ">" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title1' >" + NvlStr(value.COL1)  + "</a>" + 									  
									   "<input type='hidden' name='title1' id='CNT' value='1'>" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title2' >" + NvlStr(value.COL2)  + "</a>" + 									  
									   "<input type='hidden' name='title2' id='CNT' value='2'>" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title2' >" + NvlStr(value.COL3)  + "</a>" + 									  
									   "<input type='hidden' name='title2' id='CNT' value='3'>" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title2' >" + NvlStr(value.COL4)  + "</a>" + 									  
									   "<input type='hidden' name='title2' id='CNT' value='4'>" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title2' >" + NvlStr(value.COL5)  + "</a>" + 									  
									   "<input type='hidden' name='title2' id='CNT' value='5'>" +								  						 
									   "</td>" +
									   "<td>" + 
									   "<a href='#this' name='title2' >" + NvlStr(value.COL6)  + "</a>" + 									  
									   "<input type='hidden' name='title2' id='CNT' value='0'>" +								  						 
									   "</td>" +						   			 
					                   "</tr>";													              					                   
					                   
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtBoardDetail($(this));
				});
				
				$("a[name='title1']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_selectPrvCnrtStatistics1List($(this));
				});
				
				$("a[name='title2']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_selectPrvCnrtStatistics1List($(this));
				});
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
											
			}
		}
		
		
		
		
		
		
		
		
		function fn_selectPrvCnrtStatistics1ListPage(pageNo){
			
			var comAjax = new ComAjax();
								
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtStatistics1List.do' />");
			comAjax.setCallback("fn_selectPrvCnrtStatistics1ListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			comAjax.addParam("CNT", setCnt);
			comAjax.addParam("ST_DATE", varStDate);
			comAjax.addParam("ED_DATE", varEdDate);
			comAjax.ajax();
		}
		
		
		 function fn_selectPrvCnrtStatistics1List(obj){
			
			var comAjax = new ComAjax();
			
			$("#PAGE_INDEX").val("1");					
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtStatistics1List.do' />");
			comAjax.setCallback("fn_selectPrvCnrtStatistics1ListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			comAjax.addParam("CNT", obj.parent().find("#CNT").val());
			setCnt = obj.parent().find("#CNT").val();
			comAjax.addParam("ST_DATE", varStDate);
			comAjax.addParam("ED_DATE", varEdDate);			
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtStatistics1ListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable2");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"8\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtStatistics1ListPage"
				};
				gfn_renderPaging(params);
				
				var str = "";				
				var tr_class = "";
				var td_class = "";
				var tmp = "";
		
				
				$.each(data.list, function(key, value){					       
					       tmp = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					  										
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.ID  + "</td>" +							   			  							   			  						   			  
						   			   "<td>" + value.SAUP_NM + "</td>" +						   									   			  
						   			   "<td>" + value.SAUP_DEPT_NM + "</td>" +
						   			   "<td>" + value.SAUP_BUDGET_AMT + "</td>" +
						   			   "<td>" + value.CNT + "</td>" +						   			
					                   "</tr>";													              					                   
					                   
				});
				body.append(str);
											
			}
		}
		
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();		
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>톻계조회</td>
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
 
   
     <table width="600" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">	  
      <tr>
        <td width="120" class="tbl_field">기간</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="ST_DATE" name="ST_DATE" class="input" style="width:100;"> ~
            <input type="text" id="ED_DATE" name="ED_DATE" class="input" style="width:100;">	                          
        </td>
      </tr>       
      </table>
    
    
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">    
     <tr>
       <td align="left">&nbsp;</td>       
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type1_head.gif"></td>
               <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/scms/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
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
    
	 <table class="tbl1">
     <thead>
        <tr>
          <th>&nbsp;</th><th>합계</th>
          <th>1건접수</th><th>2건접수</th><th>3건접수</th><th>4건접수</th><th>5건접수</th><th>미접수</th>          
        </tr>
      </thead>
      <tbody id='mytable'>     
      </tbody>
      </table>
	
	
	<!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">    
     <tr>
       <td align="left">&nbsp;</td>       
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td>&nbsp;</td>
               <td class="btn_type1" nowrap>&nbsp;</td>
               <td>&nbsp;</td>
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
	
	
	
	
	 <table class="tbl1">
     <thead>
        <tr>
          <th>&nbsp;</th>
          <th>사업명</th><th>사업부서</th><th>예산금액</th><th>건수갯수</th>         
        </tr>
      </thead>
      <tbody id='mytable2'>     
      </tbody>
      </table>	
	</td>
	</tr>
</table>
</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
			
</body>
</html>