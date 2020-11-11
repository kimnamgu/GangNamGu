<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		
		var tab_gb = "${param.ICDNT_TRIAL_NO}";
		var hm_gb = "${param.INCDNT_GB}";
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_tab_setting();
			
			fn_selectIndIncidentList();
			
			
			$("#ins").on("click", function(e){ //글쓰기 버튼
				
				if(user_right == 'A') {
					
					e.preventDefault();				
					fn_indTrialWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});	
			
		
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicDetail();
			});
			
			$("#1sim").on("click", function(e){ //1심
				e.preventDefault();			
				fn_indIncidentList("11");
			});
			
			$("#1sim-2").on("click", function(e){ //신청사건
				e.preventDefault();				
				fn_indIncidentList("12");
			});
			
			$("#1sim-3").on("click", function(e){ //신청사건(즉시항고)
				e.preventDefault();				
				fn_indIncidentList("13");
			});
			
			$("#2sim").on("click", function(e){ //2심
				e.preventDefault();				
				fn_indIncidentList("21");
			});
			
			$("#3sim").on("click", function(e){ //3심
				e.preventDefault();				
				fn_indIncidentList("31");
			});
			
			$("#3sim-2").on("click", function(e){ //환송후 사건
				e.preventDefault();								
				fn_indIncidentList("32");
			});
			
			$("#3sim-3").on("click", function(e){ //소송비용 확정 결정
				e.preventDefault();				
				fn_indIncidentList("33");
			});			
			
			
			
			
		});
		
		
		function fn_tab_setting(){
			
			
			if (tab_gb == '11')
				$("#tab2").attr('class','Lcurrent');
			else if (tab_gb == '12')
				$("#tab3").attr('class','Lcurrent');
			else if (tab_gb == '13')
				$("#tab4").attr('class','Lcurrent');
			else if (tab_gb == '21')
				$("#tab5").attr('class','Lcurrent');
			else if (tab_gb == '31')
				$("#tab6").attr('class','Lcurrent');
			else if (tab_gb == '32')
				$("#tab7").attr('class','Lcurrent');
			else if (tab_gb == '33')
				$("#tab8").attr('class','Lcurrent');
						
			if (tab_gb == '33'){
				$("#data1").css('display','none');
				$("#data2").css('display','block');
			}
			else{
				$("#data1").css('display','block');
				$("#data2").css('display','none');
			}	
		}
		
		function fn_incidentBasicDetail(){
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicDetail.do' />");
			comSubmit.addParam("ID", mid);				
			comSubmit.submit();
		}
		
		function fn_indIncidentList(dgb){
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/indIncidentList.do' />");
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", dgb);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.submit();
		}
		
		
		function fn_indTrialDetail(obj){			
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/indTrialDetail.do' />");
			//comSubmit.addParam("MID", mid);
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_NO", obj.parent().find("#ID").val());
			comSubmit.addParam("ICDNT_TRIAL_NO", tab_gb);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			
			if(tab_gb == "12" || tab_gb == "13") //신청사건 , 신청사건 (즉시항고)이면
				comSubmit.addParam("TRNO_GR_CD", "16"); //신청사건
			else
				comSubmit.addParam("TRNO_GR_CD", "15");
			/*
			comSubmit.addParam("SIM11", "${param.SIM11}");			
			comSubmit.addParam("SIM12", "${param.SIM12}");
			comSubmit.addParam("SIM13", "${param.SIM13}");
			comSubmit.addParam("SIM21", "${param.SIM21}");
			comSubmit.addParam("SIM31", "${param.SIM31}");
			comSubmit.addParam("SIM32", "${param.SIM32}");
			comSubmit.addParam("SIM33", "${param.SIM33}");
			*/
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
			comSubmit.submit();
		}
		
		function fn_indTrialWrite(){			
			var mid = "${param.MID}";
			var comSubmit = new ComSubmit();
			
			if(tab_gb == '33') { //소송비용확정	
				comSubmit.setUrl("<c:url value='/incident/thirdTrial3Write.do' />");
			}
			else{
				comSubmit.setUrl("<c:url value='/incident/indTrialWrite.do' />");
			}
			comSubmit.addParam("MID", mid);
			comSubmit.addParam("ICDNT_TRIAL_NO", tab_gb);
			comSubmit.addParam("INCDNT_GB", hm_gb);
			comSubmit.submit();
		}
		
		
		function fn_selectIndIncidentList(){			
			var comAjax = new ComAjax();
			var mid = "${param.MID}";
			var tri_no = "${param.ICDNT_TRIAL_NO}";
			
			comAjax.setUrl("<c:url value='/incident/selectIndIncidentList.do' />");
			comAjax.setCallback("fn_selectIndIncidentListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());		
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			
			if(tab_gb == "12" || tab_gb == "13") //신청사건 , 신청사건 (즉시항고)이면			
				comAjax.addParam("TRNO_GR_CD", "16"); //신청사건
			else
				comAjax.addParam("TRNO_GR_CD", "15"); //기타
				
			comAjax.addParam("ICDNT_TRIAL_NO", tri_no);
			comAjax.addParam("MID", mid);
						
			comAjax.ajax();
		}
		
		function fn_selectIndIncidentListCallback(data){
			var total = data.TOTAL;				
			var body = $("#mytable1");
			var body2 = $("#mytable2");
			var recordcnt = $("#RECORD_COUNT").val();
			var str = "";
			var str_temp = "";
			
			body.empty();
			body2.empty();
			
			//alert(total);
			
			if(total == 0){
				if(tab_gb == '33') { //소송비용확정
				
					str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"6\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"6\" class=\"list_line\"></td>" + 
						  "</tr>";
					body.append("");	  
					body2.append(str);
					
				}
				else {
					str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
							"<td colspan=\"7\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"7\" class=\"list_line\"></td>" + 
						  "</tr>";
					body.append(str);
					body2.append("");
				}
								
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectIndIncidentList"
				};
				gfn_renderPaging(params);
				
				
				$.each(data.list, function(key, value){
							
							if(tab_gb == '33') { //소송비용확정	
															
								str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
				                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
				                   "<td class=\"list_center\"'>" + 
								   "<a href='#this' name='title2' >" + value.ICDNT_NO  + "</a>" + 								   
								   "<input type='hidden' name='title2' id='ID2' value=" + value.ICDNT_NO + ">" + 
								   "</td>" +								   
				                   "<td class=\"list_center\" nowrap>" + comma(NvlStr(value.DEPOSIT_AMT)) +  "</td>" +				                  
				                   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.JUDGE_DATE), ".") + "</td>" +				                   
								   "<td class=\"list_center\" nowrap>" + NvlStr(value.ETC1) +  "</td>" +
								   "<td class=\"list_center\" nowrap>" + NvlStr(value.ETC2) +  "</td>" +							   
								   "</tr>" +
								   "<tr>" + 
							       "<td colspan=\"6\" class=\"list_line\"></td>" + 
								   "</tr>";	
								   
							}
							else{
														
								str_temp = "";
								
								if(value.JUDGE_CONT_GB1 == "")
									str_temp = value.JUDGE_CONTENT;
								else
									str_temp = value.JUDGE_CONT_GB1;
																
								str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
				                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
				                   "<td class=\"list_center\"'>" + 
								   "<a href='#this' name='title' >" + NvlStr(value.ICDNT_NO) + "</a>" + 									  
								   "<input type='hidden' name='title' id='ID' value=" + value.ICDNT_NO + ">" + 
								   "</td>" +				                  
				                   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.SUE_DATE), ".") + "</td>" + 
				                   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.ACCEPT_DATE), ".") + "</td>" +				                
								   "<td class=\"list_center\" nowrap>" + NvlStr(value.PERFORM_PERSON) +  "</td>" +
								   "<td class=\"list_center\" nowrap>" + NvlStr(value.CHRG_LAWYER) +  "</td>" +									 
								   "<td class=\"list_center\" nowrap>" + NvlStr(str_temp) +  "</td>" +
								   "</tr>" +
								   "<tr>" + 
							       "<td colspan=\"7\" class=\"list_line\"></td>" + 
								   "</tr>";  
							}
							
				});
				
				if(tab_gb == '33') { //소송비용확정
					body.append("");
					body2.append(str);
				
				}
				else{				
					body.append(str);
					body2.append("");
				}
				
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
		
		
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="3">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${param.WDID}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">사건목록</td>
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

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
        
	        <div class="LblockTab" style="position: relative;">
			<ul class="Lclear">
				<li id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>			
				<li id='tab2'><span><a href="#this" class="btn" id="1sim">1심</a></span></li>					
		 		<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
		 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>	 		
		 		<li id='tab5'><span><a href="#this" class="btn" id="2sim">2심</a></span></li>	 		
		 		<li id='tab6'><span><a href="#this" class="btn" id="3sim">3심</a></span></li>
		 		<li id='tab7'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 		
		 		<li id='tab8'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li>			
			</ul>
			</div>    
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/sosong/images/title_ico.gif" align="absmiddle">사건목록</td>
					</tr>
				</table>
				</td>				
			</tr>
			</table>
						    
	        <!-- -------------- 버튼 시작 --------------  -->
	        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td></td>
	            <td align="right">
	            <table border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td>
	                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
	                  <tr>
	                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
	                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="ins"><font color="white">입력</font></a></td>
	                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
	                  </tr>
	                </table>                
	                </td>		
	              </tr>
	            </table>
	            </td>
	          </tr>
	        </table>
	        <!-- -------------- 버튼 끝 ---------------->	
		
			<div id="data1">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
				<tr>
					<td class="list_tit_line_s" width="48"></td>
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="200"></td>
					<td class="list_tit_line_s" width="100%"></td>
					<td class="list_tit_line_s" width="120"></td>				
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="100"></td>							
				</tr>
				<tr class="list_tit_tr">
					<td nowrap class="list_tit">번호</td>
					<td nowrap class="list_tit_bar">사건번호</td>
					<td nowrap class="list_tit_bar">제소일</td>
					<td nowrap class="list_tit_bar">접수일</td>
					<td nowrap class="list_tit_bar">수행자</td>
					<td nowrap class="list_tit_bar">담당변호사</td>					
					<td nowrap class="list_tit_bar">판결내역</td>					
				</tr>
				<tr>
					<td colspan="7" class="list_tit_line_e"></td>
				</tr>				
				<tbody id='mytable1'>				
				</tbody>
			</table>
			</div>
			
			
			<div id="data2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
				<tr>
					<td class="list_tit_line_s" width="48"></td>
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="200"></td>
					<td class="list_tit_line_s" width="100%"></td>
					<td class="list_tit_line_s" width="120"></td>				
					<td class="list_tit_line_s" width="100"></td>											
				</tr>
				<tr class="list_tit_tr">
					<td nowrap class="list_tit">번호</td>
					<td nowrap class="list_tit_bar">사건번호</td>
					<td nowrap class="list_tit_bar">비용확정액</td>
					<td nowrap class="list_tit_bar">결정일자</td>
					<td nowrap class="list_tit_bar">비용회수내역</td>
					<td nowrap class="list_tit_bar">비고</td>							
				</tr>
				<tr>
					<td colspan="6" class="list_tit_line_e"></td>
				</tr>				
				<tbody id='mytable2'>				
				</tbody>
			</table>
			</div>			
		</td>
		</tr>
	</table>

	<p></p>    
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="BID" name="BID" value="${param.ID}">
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>
	</td>
  </tr>
</table>	
<%@ include file="/WEB-INF/include/include-body.jspf" %>					
</body>
</html>