<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			fn_selectAllSimList(1);
			
			fn_initial_setting();
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");			
				fn_selectAllSimList();
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();				
				$("#form1").each(function() {				 
			       this.reset();  
			    });
				
				fn_initial_setting();
			});
		
			$("input:radio[name=INCDNT_GB]").click(function(){
				$("#PAGE_INDEX").val("1");			
				fn_selectAllSimList();
				
			});
			
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectAllSimList();
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
				
		function fn_initial_setting(){
			$('input:radio[id=INCDNT_GB4]').prop("checked", true);
		}
				
		function fn_print(){     
			$('#divMemoMainBody1').css("display","none");
			$('#divMemoMainBody2').css("display","none");
			$('#PAGE_NAVI').css("display","none");
			
			//factory.printing.header = "머릿글" //머릿말 설정 
			//factory.printing.footer = "바닥글"  //꼬릿말 설정 
			factory.printing.portrait = false;                    //출력방향 설정: true-세로, false-가로
			factory.printing.leftMargin = 0.0;                //왼쪽 여백 설정 
			factory.printing.topMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.rightMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.bottomMargin = 0.0;            //왼쪽 여백 설정		
			factory.printing.Print(false, window);		
		}
		
		function fn_incidentBasicDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.submit();
		}
				
		
		function fn_selectAllSimList(pageNo){
			var comAjax = new ComAjax();
			//alert(pageNo);
			comAjax.setUrl("<c:url value='/incident/selectAllSimList.do' />");
			comAjax.setCallback("fn_selectAllSimListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("ICDNT_NO", $("#ICDNT_NO").val());
			comAjax.addParam("STDATE", $("#STDATE").val());
			
			if($("#EDDATE").val() == "" || $("#EDDATE").val() == null)
				comAjax.addParam("EDDATE", $.datepicker.formatDate($.datepicker.ATOM, new Date())); //오늘일자
			else
				comAjax.addParam("EDDATE", $("#EDDATE").val());
		
			comAjax.addParam("GN_GB", "${param.GNGB}");
			
			if(user_right == 'A') {//관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			}
			else{
				$("#DEPT_NAME").val("${sessionScope.userinfo.deptName}");
				$("#DEPT_NAME").attr("readonly", true);
				comAjax.addParam("DEPT_NAME","${sessionScope.userinfo.deptName}");
				
			}
		
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comAjax.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());
				
			}		
			comAjax.ajax();					
		}
		
		
		function fn_selectAllSimListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
								"<td colspan=\"9\">조회된 결과가 없습니다.</td>" +
						  "</tr>";
											 
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectAllSimList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var tmp;
				var tmp2;
				var tr_class = "";
				
				$.each(data.list, function(key, value){
					
					tmp = "";
				    tmp2 = "";
				    
			        if (value.INCDNT_GB == "1")
						tmp = "행정";	
			        else if (value.INCDNT_GB == "2")  
				    	tmp = "민사";
				    	
				    if(value.ICDNT_TRIAL_NO == '11')
				    	tmp = tmp + " 1심";
				    else if(value.ICDNT_TRIAL_NO == '21')
				    	tmp = tmp + " 2심";
				    else if(value.ICDNT_TRIAL_NO == '31')
				    	tmp = tmp + " 3심";
				    
				    if (value.DONE_YN == "1" )
				    {
				    	tmp2 = "진행중";
				    }
				    else if (value.DONE_YN == "2")
				    {
				    	if(value.JUDGE_CONT_GB1 == "" || value.JUDGE_CONT_GB1 == null)
				    		tmp2 = value.JUDGE_CONTENT;
				    	else
				    		tmp2 = value.JUDGE_CONT_GB1;
				    }
				    
				    if ((value.RNUM % 2) == 0 )
						tr_class = "tr1";
					else
						tr_class = "tr2";
				    
					str += "<tr class=\"" + tr_class + "\">" +
					   	   "<td>" + value.RNUM + "</td>" +	
					   	   "<td>" + 
						   "<a href='#this' name='title' >" + NvlStr(value.ICDNT_NO) + "</a>" + 									  
						   "<input type='hidden' name='data1' id='ID' value=" + value.ICDNT_NO + ">" +
						   "<input type='hidden' name='data2' id='MAST_ID' value=" + value.MAST_ID + ">" +
						   "<input type='hidden' name='data3' id='ICDNT_TRIAL_NO' value=" + value.ICDNT_TRIAL_NO + ">" +
						   "<input type='hidden' name='data4' id='INCDNT_GB' value=" + value.INCDNT_GB + ">" +
						   "</td>" +					   	  		                   						   	              
		                   "<td>" + NvlStr(value.INCDNT_NAME) + "</td>" + 									 
						   "<td>" + NvlStr(value.DEPT_NAME) +  "</td>" +
						   "<td>" + formatDate(NvlStr(value.ACCEPT_DATE),".") +  "</td>" +
						   "<td>" + NvlStr(value.CHRG_LAWYER) +  "</td>" +
						   "<td>" + NvlStr(tmp2) + "</td>" +									 
						   "<td>" + formatDate(NvlStr(value.JUDGE_DATE),".") + "</td>" +							    
						   "<td>" + tmp + "</td>" +
						   "</tr>";												
							
				});				
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_indTrialDetail($(this));
				});
				
				$("a[name='title2']").on("click", function(e){ //제목					
					e.preventDefault();
					fn_thirdTrial3Detail($(this));
				});
			}
		}
		
		
		function fn_exceldown(){			
			var comSubmit = new ComSubmit();			
			
			comSubmit.setUrl("<c:url value='/incident/excelAllSimList.do' />");
			comSubmit.addParam("STDATE", $("#STDATE").val());
			comSubmit.addParam("EDDATE", $("#EDDATE").val());
			comSubmit.addParam("GN_GB", "${param.GNGB}");
			comSubmit.addParam("ICDNT_NO", $("#ICDNT_NO").val());
			
			if(user_right == 'A') {//관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comSubmit.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			}
			else{
				$("#DEPT_NAME").val("${sessionScope.userinfo.deptName}");
				$("#DEPT_NAME").attr("readonly", true);
				comSubmit.addParam("DEPT_NAME","${sessionScope.userinfo.deptName}");
				
			}
			
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comSubmit.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());
				
			}		
			
			comSubmit.submit();
		}
		
		
		function fn_indTrialDetail(obj){			
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/indTrialDetail.do' />");
			
			comSubmit.addParam("MID", obj.parent().find("#MAST_ID").val());
			comSubmit.addParam("ICDNT_NO", obj.parent().find("#ID").val());
			comSubmit.addParam("ICDNT_TRIAL_NO", obj.parent().find("#ICDNT_TRIAL_NO").val());
			comSubmit.addParam("INCDNT_GB", obj.parent().find("#INCDNT_GB").val());
			comSubmit.addParam("URL_GB", "1"); //메뉴 어디에서 연결되었는가? 1: 소송현황  2: 관리자 알림대상조회
			comSubmit.addParam("TRNO_GR_CD", "15");
			comSubmit.addParam("GNGB", "${param.GNGB}");
			comSubmit.submit();
		}
		
	
		function fn_thirdTrial3Detail(obj){			
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/thirdTrial3Detail.do' />");
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_NO", obj.parent().find("#ID2").val());
			comSubmit.addParam("ICDNT_TRIAL_NO", "33");
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.addParam("GNGB", "${param.GNGB}");
			comSubmit.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<object id="factory" style="display:none"  classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"  codebase="http://98.23.7.110:8099/sosong/common/smsx.cab#Version=7,7,0,20"></object>
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
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">                 
        <c:set var="dgb" value="${param.GNGB}" />
		<c:choose>							
		<c:when test="${dgb eq '1'}">
		구청 제소사건 현황
		</c:when>
		<c:otherwise>
		소송현황
		</c:otherwise>
		</c:choose>  
        </td>
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
           <td width="100" class="tbl_field">소송구분</td>
           <td colspan="3" width="660" class="tbl_list_left">           	                 
               <input type="radio" id="INCDNT_GB4" name="INCDNT_GB" value="" class="input" onfocus="this.blur()"> 전체&nbsp;<input type="radio" id="INCDNT_GB1" name="INCDNT_GB" value="1" class="input" onfocus="this.blur()"> 행정소송 &nbsp;<input type="radio" id="INCDNT_GB2" name="INCDNT_GB" value="2" class="input" onfocus="this.blur()"> 민사소송&nbsp;<input type="radio" id="INCDNT_GB3" name="INCDNT_GB" value="3" class="input" onfocus="this.blur()"> 국가소송                  	                	               
           </td>
      </tr>         
      <tr>
        <td width="100" class="tbl_field">기간</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="STDATE" name="STDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, STDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a> ~
            <input type="text" id="EDDATE" name="EDDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, EDDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a>	                          
        </td>
      </tr>
	  <tr>
        <td width="100" class="tbl_field">담당부서</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="DEPT_NAME" name="DEPT_NAME" class="input" style="width:300;">	                          
        </td>
      </tr>
       <tr>
        <td width="100" class="tbl_field">사건번호</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="ICDNT_NO" name="ICDNT_NO" class="input" style="width:300;">	                          
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
	               <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
	               <td><img src="/sosong/images/btn_type0_end.gif"></td>
	             </tr>
	           </table>                
	           </td>		
	         </tr>
	       </table>
	       </td>
	     </tr>
	     <tr>
	       <td align="left"><b>Total : <span id="tcnt"></span></b> &nbsp; 
	       <select id="DISP_CNT" name="DISP_CNT" class="input">
	       <option value="10">10</option>
	       <option value="20">20</option>
	       <option value="30">30</option>
	       <option value="40">40</option>
	       <option value="50">50</option>
	   	   </select>
	       </td>
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
    
		<table class="tbl1">		
			<thead>	
			<tr>
				<th></th>
				<th>사건번호</th>
				<th>사건명</th>
				<th>소송주관부서</th>
				<th>접수일자</th>
				<th>변호사명</th>	
				<th>소송결과</th>
				<th>판결일자</th>
				<th>비고</th>
			</tr>
			</thead>			
			<tbody id='mytable'>				
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