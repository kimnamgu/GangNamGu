<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tri_no = "${param.ICDNT_TRIAL_NO}";
		var hm_gb = "${param.INCDNT_GB}";
		var d_cnt = 2;
		
		$(document).ready(function(){
			
			fn_tab_setting();
			
			$("#list").on("click", function(e){ //목록으로 버튼				
				e.preventDefault();
				fn_indIncidentList(tri_no);				
			});
			
			$("#ins").on("click", function(e){ //입력하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_insertIndTrial();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1심
				e.preventDefault();				
				fn_indTrialWrite();
			});
			
			
			$('#clear').click(function() {
				
				$('#PERFORM_PERSON').val("");
				$('#PERFORM_USERID').val("");
				$('#PERFORM_USERHNO').val("");
		    });
			
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });
			
			$('#open').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop').show();
		        fn_jikwonlist();
		    });
			
			
			$("#DIRECT_GB").change(function(){
		           if( $("#DIRECT_GB option:selected").val() == "1"){
		        	   $("#LAWYER_GB").val("직접수행");
		        	   $("#CHRG_LAWYER").val($("#LAWYER_GB option:selected").val());
		        	   $("#CHRG_LAWYER").attr("readonly", true);
		           }	   
		           else{
		        	   $("#LAWYER_GB option:eq(0)").attr("selected", "selected");
		        	   $("#CHRG_LAWYER").val("");
		        	   $("#CHRG_LAWYER").attr("readonly", false);
		           }
			});
			
			
			$("#LAWYER_GB").change(function(){
		           if( $("#LAWYER_GB option").index($("#LAWYER_GB option:selected")) == 6)
		           {	   
		        	   $("#CHRG_LAWYER").attr("readonly", false);
		        	   $("#CHRG_LAWYER").val("");
		           }	   
		           else{		        	   
		           	   
		           	   $("#CHRG_LAWYER").val($("#LAWYER_GB option:selected").val());
		           	   $("#CHRG_LAWYER").attr("readonly", true);
		           }
		          
			});
			
			$("#addLawyer").on("click", function(e){
				alert('콤마로 구분값 입력후 입력 바랍니다.');
				$("#CHRG_LAWYER").attr("readonly", false);
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				if(d_cnt < 6)
					fn_addFile();
				else
					alert('더이상 추가 불가합니다.');
			});
			
		});
		
		
	    function fn_tab_setting(){
			
			
			/*if (tab_gb == '11')
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
			*/
			$("#PERFORM_PERSON").attr("readonly", true);
			$("#CHRG_LAWYER").attr("readonly", true);
			
			$("#ICDNT_TRIAL_NO").val(tri_no);
			if(hm_gb != "")
				$('input:radio[name=INCDNT_GB]:input[value=' + hm_gb + ']').attr("checked", true);
			
			
			if (tri_no == "11"){ //1심
				$("#APPEAL_TIT").html("답변서<br><br>제출일자");
				$("#ARGUE_TIT").html("변론기일");
				$("#SUBMIT_TIT").html("항소장 제출일자");
				
				appeal_html = "<input type=\"text\" id=\"APPEAL_RESPONSE_DATE\" name=\"APPEAL_RESPONSE_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_RESPONSE_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";
				$("#APPEAL_HTML").html(appeal_html);
				
				argue_html = "1차  <input type=\"text\" id=\"ARGUE_SET1_DATE\" name=\"ARGUE_SET1_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, ARGUE_SET1_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a><br>";
					            
				$("#ARGUE_HTML").html(argue_html);
				
				$("#add_date").html("<a href=\"#this\" class=\"btn\" id=\"addFile\">추가</a>");				
				
				submit_html = "<input type=\"text\" id=\"APPEAL_SUBMIT_DATE\" name=\"APPEAL_SUBMIT_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_SUBMIT_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";				
				$("#SUBMIT_HTML").html(submit_html);
				
				$("#alrim_date1").css("display","block");
				$("#alrim_date2").css("display","block");
				
	    	}
			else if (tri_no == "12"){ //신청사건
				$("#APPEAL_TIT").html("답변서<br><br>제출일자");
				$("#ARGUE_TIT").html("심문기일");	
				$("#SUBMIT_TIT").html("");
				
				appeal_html = "<input type=\"text\" id=\"APPEAL_RESPONSE_DATE\" name=\"APPEAL_RESPONSE_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_RESPONSE_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";
				$("#APPEAL_HTML").html(appeal_html);
				
				argue_html = "1차 <input type=\"text\" id=\"ARGUE_SET1_DATE\" name=\"ARGUE_SET1_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, ARGUE_SET1_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";
				$("#ARGUE_HTML").html(argue_html);
				
				$("#add_date").html("<a href=\"#this\" class=\"btn\" id=\"addFile\">추가</a>");
								
				$("#SUBMIT_HTML").html("");
				
				$("#alrim_date1").css("display","block");				
	    	}
			else if(tri_no == "21"){ //2심
				$("#APPEAL_TIT").html("답변서/항소이유서<br><br>제출일자");
				$("#ARGUE_TIT").html("변론기일");
				$("#SUBMIT_TIT").html("상고장 제출일자");
				
				appeal_html = "<input type=\"text\" id=\"APPEAL_RESPONSE_DATE\" name=\"APPEAL_RESPONSE_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_RESPONSE_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a> &nbsp;/<br>" + 
							  "<input type=\"text\" id=\"APPEAL_REASON_DATE\" name=\"APPEAL_REASON_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_REASON_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";
							  
				$("#APPEAL_HTML").html(appeal_html);
				
				argue_html = "1차  <input type=\"text\" id=\"ARGUE_SET1_DATE\" name=\"ARGUE_SET1_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, ARGUE_SET1_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a><br>";
				            
				$("#ARGUE_HTML").html(argue_html);
				
				$("#add_date").html("<a href=\"#this\" class=\"btn\" id=\"addFile\">추가</a>");
				
				submit_html = "<input type=\"text\" id=\"APPEAL_SUBMIT_DATE\" name=\"APPEAL_SUBMIT_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_SUBMIT_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";				
				$("#SUBMIT_HTML").html(submit_html);
				
				$("#alrim_date1").css("display","block");
				$("#alrim_date2").css("display","block");
			}	
			else if(tri_no == "31"){ //3심
				$("#APPEAL_TIT").html("답변서/상고이유서<br><br>제출일자");
				$("#ARGUE_TIT").html("");
				$("#SUBMIT_TIT").html("");			
				
				appeal_html = "<input type=\"text\" id=\"APPEAL_RESPONSE_DATE\" name=\"APPEAL_RESPONSE_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_RESPONSE_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a> &nbsp;/<br>" +
							  "<input type=\"text\" id=\"APPEAL_REASON_DATE\" name=\"APPEAL_REASON_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, APPEAL_REASON_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>";
				
				$("#APPEAL_HTML").html(appeal_html);
				
				$("#ARGUE_HTML").html("");
				$("#SUBMIT_HTML").html("");
				
				$("#alrim_date1").css("display","block");				
			}
			
			
			fn_judgmentList();
		}
		
	    
	    function fn_addFile(){
			var str = "<p>"+(d_cnt)+ "차  <input type=\"text\" id=\"ARGUE_SET" +(d_cnt)+"_DATE\" name=\"ARGUE_SET" +(d_cnt)+"_DATE\" class=\"input\" style=\"width:100;\"> <a href=\"#a\" onclick=\"popUpCalendar(this, ARGUE_SET" +(d_cnt)+"_DATE, 'yyyy-mm-dd');\"><img class=\"Lbtn\" src=\"/sosong/images/ic_cal.gif\" alt=\"달력\" /></a>"; //<a href='#this' class='btn' name='delete'>삭제</a></p>			
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				
				fn_deleteFile($(this));
			});			
			d_cnt++;
			
		}
	    
	    function fn_deleteFile(obj){	    	
			obj.parent().remove();
			//d_cnt--;
		}
	    
	    
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
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
		
		function fn_incidentBasicWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_indTrialWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/indTrialWrite.do' />");
			comSubmit.submit();
		}
		
		
		
		function fn_insertIndTrial(){			
			var comSubmit = new ComSubmit("form1");
			var mid = "${param.MID}";
			var argue_set_date = "";
			
			if($("#ICDNT_TRIAL_NO option:selected").val() == "" )
			{
				alert('사건구분을 선택하세요!!');
				$('#ICDNT_TRIAL_NO').focus();
				return false();
			}
			
			if($(":input:radio[name=INCDNT_GB]:checked").val() == "" || $(":input:radio[name=INCDNT_GB]:checked").val() == null)
			{
				alert('구분을 선택하세요!!');
				$('#INCDNT_GB').focus();
				return false();
			}
			
			
			if($("#IMPORTANT_GB option:selected").val() == "" )
			{
				alert('중요소송 여부를 선택하세요!!');
				$('#IMPORTANT_GB').focus();
				return false();
			}
			
			/*if($("#DIRECT_GB option:selected").val() == "" )
			{
				alert('직접수행 여부를 선택하세요!!');
				$('#DIRECT_GB').focus();
				return false();
			}*/
			
			if($('#ICDNT_NO').val() == "")
			{
				alert('사건번호 를 입력하세요!!');
				$('#ICDNT_NO').focus();
				return false();
			}
			
			/*
			if($('#PERFORM_PERSON').val() == "")
			{
				alert('수행자를 입력하세요!!');
				$('#PERFORM_PERSON').focus();
				return false();
			}
			
			
			if($("#LAWYER_GB option:selected").val() == "" )
			{
				alert('소송대리인을 선택하세요!!');
				$('#LAWYER_GB').focus();
				return false();
			}
			*/
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{	
				comSubmit.setUrl("<c:url value='/incident/insertIndTrial.do' />");
				
				if($("#JUDGE_CONT_GB > option:selected").text() == "----------")
					comSubmit.addParam("JUDGE_CONTENT", "");
				else
					comSubmit.addParam("JUDGE_CONTENT", $("#JUDGE_CONT_GB > option:selected").text());
				
				argue_set_date = $("#ARGUE_SET1_DATE").val();
				
				if($("#ARGUE_SET2_DATE").val() != "" && $("#ARGUE_SET2_DATE").val() != undefined)
				{
					argue_set_date += ";" + $("#ARGUE_SET2_DATE").val();
				}
				
				if($("#ARGUE_SET3_DATE").val() != "" && $("#ARGUE_SET3_DATE").val() != undefined)
				{
					argue_set_date += ";" + $("#ARGUE_SET3_DATE").val();
				}
				
				if($("#ARGUE_SET4_DATE").val() != "" && $("#ARGUE_SET4_DATE").val() != undefined)
				{
					argue_set_date += ";" + $("#ARGUE_SET4_DATE").val();
				}
				
				if($("#ARGUE_SET5_DATE").val() != "" && $("#ARGUE_SET5_DATE").val() != undefined)
				{
					argue_set_date += ";" + $("#ARGUE_SET5_DATE").val();
				}
				
				
				if(tri_no == "11"){ // 1심
					comSubmit.addParam("APPEAL_REASON_DATE", "");
				}
				if(tri_no == "12"){ //신청사건
					comSubmit.addParam("APPEAL_REASON_DATE", "");
					comSubmit.addParam("APPEAL_SUBMIT_DATE", "");
				}
				else if(tri_no == "13" || tri_no == "32"){ //신청사건(즉시항고), 환송후 사건
					comSubmit.addParam("APPEAL_RESPONSE_DATE", "");
					comSubmit.addParam("APPEAL_REASON_DATE", "");
					argue_set_date = "";
					comSubmit.addParam("APPEAL_SUBMIT_DATE", "");
				}
				else if(tri_no == "31"){ //3심					
					argue_set_date = "";
					comSubmit.addParam("APPEAL_SUBMIT_DATE", "");
				}
				
				comSubmit.addParam("ARGUE_SET_DATE", argue_set_date);
				
				comSubmit.addParam("MAST_ID", mid);				
				comSubmit.submit();
			}
		}
		
		
		
		function fn_set_user_info(obj, i){
			var tmp1 = "";
			var tmp2 = "";
			
			tmp1 = $("#PERFORM_PERSON").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1.replace(/ /g, '') + ",";
			tmp2 = obj.parent().find("#JNAME").val();
			
			//tmp1 = tmp1.replace(/ /g, '');
			
			
			$("#PERFORM_PERSON").val(tmp1 + tmp2);
			
			
			tmp1 = $("#PERFORM_USERID").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1.replace(/ /g, '') + ",";
			tmp2 = obj.parent().find("#JID").val();
			
			//tmp1 = tmp1.replace(/ /g, '');
			
			$("#PERFORM_USERID").val(tmp1 + tmp2);
			
			
			tmp1 = $("#PERFORM_USERHNO").val();
			if(tmp1.replace(/ /g, '') != ""){
				tmp1 = tmp1.replace(/ /g, '');
				tmp1 = tmp1.replace(/-/g, '') + ",";
			}
			tmp2 = obj.parent().find("#JHNO").val();
			//alert(tmp2);
			//alert("val=[" + obj.parent().find("#JHNO").val() + "]");
			if(tmp2 == '' || tmp2.length == 0)
			{				
				tmp2 = 	obj.parent().find("#I_JHNO").val();
				//alert(tmp2);
			}
			
			tmp2 = tmp2.replace(/-/g, '');
			
			$("#PERFORM_USERHNO").val(tmp1 + tmp2);			
			
			//alert($("#PERFORM_USERHNO").val());
			
			//$('#pop').hide();
		}
		
		function fn_jikwonlist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("USER_NAME",$("#USER_NAME").val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
			var str = "";
			var i = 1;
			var body = $("#mytable");
			
			body.empty();
					
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
	                   "<td class=\"list_center\" nowrap>" +      	                 
	                   "<a href='#this' name='title' >" + NvlStr(value.NDU_USER_NAME)  + "</a>" + 					
	                   "<input type='hidden' name='JDID' id='JDID' value=" + NvlStr(value.NDU_DEPT_ID) + ">" +
					   "<input type='hidden' name='JDEPT' id='JDEPT' value=" + NvlStr(value.NDU_DEPT_NAME) + ">" +
					   "<input type='hidden' name='JID' id='JID' value=" + NvlStr(value.NDU_USER_ID) + ">" +
					   "<input type='hidden' name='JNAME' id='JNAME' value=" + NvlStr(value.NDU_USER_NAME) + ">" +
					   "<input type='hidden' name='JCID' id='JCID' value=" + NvlStr(value.NDU_CLSS_NO) + ">" +
					   "<input type='hidden' name='JCLASS' id='JCLASS' value=" + NvlStr(value.NDU_CLSS_NM) + ">" +
					   "<input type='hidden' name='JHNO' id='JHNO' value=" + NvlStr(value.NDU_HP_NO) + ">" +
					   " &nbsp; <input type='text' name='I_JHNO' id='I_JHNO' value='" + NvlStr(value.NDU_HP_NO) +"' style='width:90;'>" +
					   "</td>" +	                   
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_DEPT_NAME) + "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_CLSS_NM) + "</td>" +
	                   "<td class=\"list\"'>" + NvlStr(value.NDU_POSIT_NM) + "</td>" +
	                   "<td class=\"list\"'></td>" +
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"5\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_set_user_info($(this));
			});
		}
		
		
		function fn_judgmentList(){
			
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/common/selectJudgmentList.do' />");
			comAjax.setCallback("fn_selectJudgmentListCallback");
			
			if (tri_no == "12" || tri_no == "13") {//신청사건, 신청사건(즉시항고)이면
				comAjax.addParam("GR_CD", "16");
				comAjax.addParam("SIM_GB", "");
			}
			else if (tri_no == "31") {//3심이면
				comAjax.addParam("GR_CD", "15");
				comAjax.addParam("SIM_GB", "");
			}
			else{
				comAjax.addParam("GR_CD", "15");
				comAjax.addParam("SIM_GB", "333");
			}
			comAjax.ajax();
		}
		
		function fn_selectJudgmentListCallback(data){			
			var str = "";
			var i = 1;
			
			str = "<option value=\"\">----------</option>";
			
			$.each(data.list, function(key, value){										
				str += "<option value=\"" + value.CODE + "\">" + value.NAME + "</option>";
				i++;
			});
			
			$("#JUDGE_CONT_GB").append(str);			
		}
		
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="PERFORM_USERID" name="PERFORM_USERID" value="">
<input type="hidden" id="PERFORM_USERHNO" name="PERFORM_USERHNO" value="">
<input type="hidden" id="ETC2" name="ETC2" value="">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">소송사건</td>
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
			<!-- <li id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li> -->			
			<li class="Lcurrent" id='tab2'><span><a href="#this" class="btn" id="indsim">사건등록</a></span></li>
			<!-- <li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
	 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>	 		
	 		<li id='tab5'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 		
	 		<li id='tab6'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li> -->
		</ul>
		</div>
                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">사건구분</td>
	            <td width="243" class="tbl_list_left">
	            	<select id="ICDNT_TRIAL_NO" name="ICDNT_TRIAL_NO" class="input">
	                	<option value="">--------------</option>
	                	<option value="11">1심</option>
	                	<option value="12">신청사건</option>
	                	<option value="13">신청사건(즉시항고)</option>	                	
	                	<option value="21">2심</option>
	                	<option value="31">3심</option>
                        <option value="32">환송후사건</option>                        	                			                    		               
	                </select>
				</td>
				<td width="120" class="tbl_field">구분</td>
	            <td width="244" class="tbl_list_left"><input type="radio" id="INCDNT_GB1" name="INCDNT_GB" value="1" class="input" onfocus="this.blur()"> 행정소송 &nbsp;<input type="radio" id="INCDNT_GB2" name="INCDNT_GB" value="2" class="input" onfocus="this.blur()"> 민사소송  &nbsp;<input type="radio" id="INCDNT_GB3" name="INCDNT_GB" value="3" class="input" onfocus="this.blur()"> 국가소송</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">중요소송여부</td>
	            <td width="243" class="tbl_list_left">
	            	<select id="IMPORTANT_GB" name="IMPORTANT_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">일반</option>
	                	<option value="2">중요</option>	               					                    		                
	                </select>
	            </td>
				<td width="120" class="tbl_field">직접수행여부</td>
	            <td width="244" class="tbl_list_left">
	            	<select id="DIRECT_GB" name="DIRECT_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">직접수행</option>
	                	<option value="2">변호사</option>	               					                    		                
	                </select>
	            </td>							
	          </tr>
	           <tr>
	            <td width="120" class="tbl_field">사건번호</td>
	            <td width="243" class="tbl_list_left">
	            	<input type="text" id="ICDNT_NO" name="ICDNT_NO" class="input" style="width:200;">
	            </td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left">	            	
				</td>
	          </tr>   
	          <tr>
	            <td width="120" class="tbl_field">수행자</td>
	            <td width="243" class="tbl_list_left">
	            	<input type="text" id="PERFORM_PERSON" name="PERFORM_PERSON" class="input" style="width:200;"> 
	            	<p><input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;" > <a href="#this" id="open" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a></p>
	            </td>
				<td width="120" class="tbl_field">소송대리인</td>
	            <td width="244" class="tbl_list_left">
	            	<select id="LAWYER_GB" name="LAWYER_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="이중광">이중광</option>
	                	<option value="이성섭">이성섭</option>
	                	<option value="하태웅">하태웅</option>
	                	<option value="법무법인 웅빈">법무법인 웅빈</option>
	                	<option value="법무법인 대륙아주">법무법인 대륙아주</option>
						<option value="입력">기타변호사(수동입력)</option>
						<option value="직접수행">직접수행</option>	                		               					                    		                
	                </select> <p><input type="text" id="CHRG_LAWYER" name="CHRG_LAWYER" class="input" style="width:150;"> <a href="#this" id="addLawyer">변호사추가</a></p>
				</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">완료여부</td>
	            <td width="243" class="tbl_list_left">
	            	<select id="DONE_YN" name="DONE_YN" class="input">
	                	<option value="">----------</option>
	                	<option value="1">진행중</option>
	                	<option value="2">완료</option>	               					                    		                
	                </select>
	            </td>
				<td width="120" class="tbl_field">승패여부</td>
	            <td width="244" class="tbl_list_left">
	            	<select id="WIN_LOSE_GB" name="WIN_LOSE_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">승소</option>
	                	<option value="2">패소</option>	               					                    		                
	                </select>
				</td>							
	         </tr>
	          <tr>
	            <td width="120" class="tbl_field">제소일</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="SUE_DATE" name="SUE_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, SUE_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>
				<td width="120" class="tbl_field">접수일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="ACCEPT_DATE" name="ACCEPT_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, ACCEPT_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>							
	          </tr>
	          <tr id="alrim_date1" style="display:none;">
	            <td width="120" class="tbl_field"><span id="APPEAL_TIT"></span></td>
	            <td width="243" class="tbl_list_left"><span id="APPEAL_HTML"></span></td>
				<td width="120" class="tbl_field"><span id="ARGUE_TIT"></span></td>
	            <td width="244" class="tbl_list_left">
	             <div id="fileDiv"><span id="ARGUE_HTML"></span>
	             </div>
	             <span id="add_date"></span>
	            </td>							
	          </tr>
	          <tr id="alrim_date2" style="display:none;">
	            <td width="120" class="tbl_field"><span id="SUBMIT_TIT"></span></td>
	            <td width="243" class="tbl_list_left"><span id="SUBMIT_HTML"></span></td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">판결내역</td>
	            <td width="243" class="tbl_list_left">
	            	<select id="JUDGE_CONT_GB" name="JUDGE_CONT_GB" class="input">	                	                	        					                    		            
	                </select>
				</td>
				<td width="120" class="tbl_field">판결내역상세</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="JUDGE_CONT_NOTES" name="JUDGE_CONT_NOTES" class="input" style="width:200;"></td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">판결일자</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="JUDGE_DATE" name="JUDGE_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, JUDGE_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>
				<td width="120" class="tbl_field">접수일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="JG_ACCPT_DATE" name="JG_ACCPT_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, JG_ACCPT_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">착수금</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="DEPOSIT_AMT" name="DEPOSIT_AMT" class="input" style="width:100;"></td>
				<td width="120" class="tbl_field">착수금 지급일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="DEPOSIT_PAY_DATE" name="DEPOSIT_PAY_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, DEPOSIT_PAY_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">사례금</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="REWARD_AMT" name="REWARD_AMT" class="input" style="width:100;"></td>
				<td width="120" class="tbl_field">사례금 지급일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="REWARD_PAY_DATE" name="REWARD_PAY_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, REWARD_PAY_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">인지대</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="STAMP_AMT" name="STAMP_AMT" class="input" style="width:100;"></td>
				<td width="120" class="tbl_field">지급일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="STAMP_PAY_DATE" name="STAMP_PAY_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, STAMP_PAY_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">송달료</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="TRANSMIT_FEE" name="TRANSMIT_FEE" class="input" style="width:100;"></td>
				<td width="120" class="tbl_field">지급일자</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="TRANSMIT_PAY_DATE" name="TRANSMIT_PAY_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, TRANSMIT_PAY_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>	            						
	          </tr>	          
	         <tr>
	            <td width="120" class="tbl_field">기타</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="ETC1" name="ETC1" class="input" style="width:240;"></td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>	            						
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
                <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
				<c:choose>
	 			<c:when test="${us_rt eq 'A'}">
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="ins"><font color="white">등록</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>               
                </c:when>
                </c:choose>                		
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/sosong/images/btn_type1_head.gif"></td>
                    <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
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
        <!-- -------------- 버튼 끝 --------------  -->
        </td>
      </tr>
    </table>
    </td>    
  </tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>

<!-- POPUP --> 
<div id="pop" style="position:absolute;left:395px;top:190;z-index:200;display:none;"> 
<table width=530 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr> 
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="160"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="60"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">직위</td>						
			</tr>
			<tr>
				<td colspan="5" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable'>			
			</tbody>
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close2"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  
</body>
</html>
