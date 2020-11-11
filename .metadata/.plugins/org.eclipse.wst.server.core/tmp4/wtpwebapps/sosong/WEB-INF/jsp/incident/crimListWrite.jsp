<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		$(document).ready(function(){
			fn_deptList();
			
			fn_init_setting();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_allIncidentList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_crimListWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1,2,3심
				e.preventDefault();								
				fn_indTrialWrite();				
			});
			
			$("#1sim-2").on("click", function(e){ //신청사건
				e.preventDefault();				
				fn_firstTrial2Write();
			});
			
			$("#1sim-3").on("click", function(e){ //신청사건(즉시항고)
				e.preventDefault();				
				fn_firstTrial3Write();
			});
			
			$("#3sim-2").on("click", function(e){ //환송후 사건
				e.preventDefault();								
				fn_thirdTrial2Write();				
			});
			
			$("#3sim-3").on("click", function(e){ //소송비용 확정 결정
				e.preventDefault();								
				fn_thirdTrial3Write();				
			});
			
			
			$("#I_DEPT_CD").change(function() {
			   //alert($(this).children(":selected").val());
			   //alert($(this).children("option:selected").text());
			   //alert($('#I_DEPT_CD option').size());
				
			   
			});
			
			$('#clear').click(function() {
				
				$('#PERFORM_PERSON').val("");
				$('#PERFORM_USERID').val("");
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
		});
		
		function fn_init_setting(){
			
			$("#PERFORM_PERSON").attr("readonly", true);
			$("#CHRG_LAWYER").attr("readonly", true);
		}
		
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
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
		
		function fn_firstTrial2Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/firstTrial2Write.do' />");
			comSubmit.submit();
		}
		
		function fn_firstTrial3Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/firstTrial3Write.do' />");
			comSubmit.submit();
		}
		
		function fn_thirdTrial2Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/thirdTrial2Write.do' />");
			comSubmit.submit();
		}
		
		function fn_thirdTrial3Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/thirdTrial3Write.do' />");
			comSubmit.submit();
		}
		
		
		
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME + ",</option>";
					i++;
			});
			
			$("#I_DEPT_CD").append(str);			
		}	
		
		
		
		function fn_crimListWrite(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			var c_gn_gb = "";
			
			if($('#INCDNT_NAME').val() == "")
			{
				alert('사건명을 입력하세요!!');
				$('#INCDNT_NAME').focus();
				return false();
			}
			
			
			tmp = $("#I_DEPT_CD > option:selected").text();
			tmp = tmp.substring(0, tmp.length-1);
						
			if(tmp == "" || tmp == null)
			{
				alert('담당부서를 선택하세요!!');
				$('#I_DEPT_CD').focus();
				return false();
			}
			
			if($('#PERFORM_PERSON').val() == "")
			{
				alert('직무관련 공무원을 입력하세요!!');
				$('#PERFORM_PERSON').focus();
				return false();
			}
			
			if($("#LAWYER_GB option:selected").val() == "" )
			{
				alert('변호사를 선택하세요!!');
				$('#LAWYER_GB').focus();
				return false();
			}
			
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/incident/insertCrimList.do' />");
				//comSubmit.addParam("BDID", $("#BOARD_ID").val());
				//comSubmit.addParam("WDID", $("#WRITE_DEPT").val());
				comSubmit.addParam("GN_GB", "");
				comSubmit.addParam("DEPT_CD", $('#I_DEPT_CD').val().toString());
				comSubmit.addParam("DEPT_NAME", tmp);
				comSubmit.submit();
			}
		}
		
		function fn_set_user_info(obj){
			var tmp1 = "";
			var tmp2 = "";
			
			tmp1 = $("#PERFORM_PERSON").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1 + ",";
			tmp2 = obj.parent().find("#JNAME").val();
			
			tmp1 = tmp1.replace(/ /g, '');
			
			
			$("#PERFORM_PERSON").val(tmp1 + tmp2);
			
			
			tmp1 = $("#PERFORM_USERID").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1 + ",";
			tmp2 = obj.parent().find("#JID").val();
			
			tmp1 = tmp1.replace(/ /g, '');
			
			$("#PERFORM_USERID").val(tmp1 + tmp2);
			
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
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="INCDNT_GB" name="INCDNT_GB" value="4">
<input type="hidden" id="CMPLN_NAME" name="CMPLN_NAME" value="없음">
<input type="hidden" id="ICDNT_TRIAL_NO" name="ICDNT_TRIAL_NO" value="41">
<input type="hidden" id="NOTE" name="NOTE" value="">
<input type="hidden" id="DFNDNT_NAME" name="DFNDNT_NAME" value="">
<input type="hidden" id="LAWSUIT_VALUE" name="LAWSUIT_VALUE" value="">
<input type="hidden" id="LAST_RSLT_CONT" name="LAST_RSLT_CONT" value="">

<input type="hidden" id="WIN_LOSE_GB" name="WIN_LOSE_GB" value="">
<input type="hidden" id="LAST_SIM_GB" name="LAST_SIM_GB" value="">
<input type="hidden" id="BIGO" name="BIGO" value="">
<input type="hidden" id="ACCEPT_DATE" name="ACCEPT_DATE" value="">
<input type="hidden" id="IMPORTANT_GB" name="IMPORTANT_GB" value="">
<input type="hidden" id="DIRECT_GB" name="DIRECT_GB" value="">
<input type="hidden" id="PERFORM_USERID" name="PERFORM_USERID" value="">
<input type="hidden" id="DONE_YN" name="DONE_YN" value="">
<input type="hidden" id="JUDGE_CONT_GB" name="JUDGE_CONT_GB" value="">
<input type="hidden" id="JUDGE_CONTENT" name="JUDGE_CONTENT" value="">
<input type="hidden" id="JUDGE_CONT_NOTES" name="JUDGE_CONT_NOTES" value="">
<input type="hidden" id="JUDGE_DATE" name="JUDGE_DATE" value="">
<input type="hidden" id="JG_ACCPT_DATE" name="JG_ACCPT_DATE" value="">
<input type="hidden" id="STAMP_AMT" name="STAMP_AMT" value="">
<input type="hidden" id="STAMP_PAY_DATE" name="STAMP_PAY_DATE" value="">
<input type="hidden" id="TRANSMIT_FEE" name="TRANSMIT_FEE" value="">
<input type="hidden" id="TRANSMIT_PAY_DATE" name="TRANSMIT_PAY_DATE" value="">
<input type="hidden" id="ETC1" name="ETC1" value="">
<input type="hidden" id="ETC2" name="ETC2" value="">
<input type="hidden" id="APPEAL_RESPONSE_DATE" name="APPEAL_RESPONSE_DATE" value="">
<input type="hidden" id="APPEAL_REASON_DATE" name="APPEAL_REASON_DATE" value="">
<input type="hidden" id="APPEAL_SUBMIT_DATE" name="APPEAL_SUBMIT_DATE" value="">
<input type="hidden" id="ARGUE_SET_DATE" name="ARGUE_SET_DATE" value="">
<!-- <input type="hidden" id="PERFORM_USERHNO" name="PERFORM_USERHNO" value=""> -->


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">형사사건</td>
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
			<li class="Lcurrent" id='tab1'><span><a href="#this" class="btn" id="basic">형사사건</a></span></li>			
			<!-- <li id='tab2'><span><a href="#this" class="btn" id="indsim">1,2,3심</a></span></li>
			<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
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
	            <td width="120" class="tbl_field">사건명</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="INCDNT_NAME" name="INCDNT_NAME" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당부서</td>
	            <td width="243" class="tbl_list_left">
				<select id="I_DEPT_CD" name="I_DEPT_CD" class="input" size="10" multiple>	                		                    		               
	            </select>
				</td>
				<td width="120" class="tbl_field">직무관련 공무원</td>
	            <td width="244" class="tbl_list_left">
				<input type="text" id="PERFORM_PERSON" name="PERFORM_PERSON" class="input" style="width:200;"> 
	            	<p><input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;" > <a href="#this" id="open" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a></p>				
				</td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">변호사</td>
	            <td width="243" class="tbl_list_left">
				<select id="LAWYER_GB" name="LAWYER_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="이중광">이중광</option>
	                	<option value="이성섭">이성섭</option>
	                	<option value="하태웅">하태웅</option>
	                	<option value="법무법인 웅빈">법무법인 웅빈</option>
	                	<option value="법무법인 대륙아주">법무법인 대륙아주</option>
						<option value="입력">기타변호사(수동입력)</option>
						<option value="직접수행">직접수행</option>	                		               					                    		                
	            </select> <p><input type="text" id="CHRG_LAWYER" name="CHRG_LAWYER" class="input" style="width:150;"></p>	
				</td>
				<td width="120" class="tbl_field">선임일</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="SUE_DATE" name="SUE_DATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, SUE_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a></td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">결과</td>
	            <td width="243" class="tbl_list_left">
	            <select id="LAST_RSLT_GB" name="LAST_RSLT_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">기소</option>
	                	<option value="2">불기소</option>
	                	<option value="3">무혐의</option>
	                	<option value="4">기소유예</option>
	                	<option value="5">고소취하</option>	                		               					                    		               
	            </select>
				</td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
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
	      </table>
	      
	     
	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
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
<table width=430 height=250 cellpadding=2 cellspacing=0>
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
