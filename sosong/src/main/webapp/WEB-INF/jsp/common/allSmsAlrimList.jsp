<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
	
			fn_selectAllSmsAlrimList(1);
						
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectAllSmsAlrimList();
			});
			
			
			$('#close1').click(function() {
		        $('#pop1').hide();
		    });
				
			$('#close3').click(function() {
		        $('#pop2').hide();
		    });
			
			$('#close4').click(function() {
		        $('#pop2').hide();
		    });
			
			/*$('#phone').click(function() {
		        				
				
		    });*/
		});
				
		function fn_indTrialDetail(obj){			
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/incident/indTrialDetail.do' />");
			
			comSubmit.addParam("MID", obj.parent().find("#MAST_ID").val());
			comSubmit.addParam("ICDNT_NO", obj.parent().find("#ID").val());
			comSubmit.addParam("ICDNT_TRIAL_NO", obj.parent().find("#ICDNT_TRIAL_NO").val());
			comSubmit.addParam("INCDNT_GB", obj.parent().find("#INCDNT_GB").val());
			comSubmit.addParam("URL_GB", "2"); //메뉴 어디에서 연결되었는가? 1: 소송현황  2: 관리자 알림대상조회
			comSubmit.addParam("TRNO_GR_CD", "15");			
			comSubmit.submit();
		}
		
		function fn_selectAllSmsAlrimList(pageNo){			
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/common/selectAllSmsAlrimList.do' />");
			comAjax.setCallback("fn_selectAllSmsAlrimListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("CMPLN_NAME",$("#CMPLN_NAME").val());
			comAjax.addParam("ICDNT_NO", $("#ICDNT_NO").val());
			comAjax.addParam("ID",$("#ID").val());
			
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
		
		
		function fn_selectAllSmsAlrimListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			var i = 0;
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
								"<td colspan=\"10\">조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"10\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectAllSmsAlrimList"
				};
				gfn_renderPaging(params);
				
				var str = "";				
				var str_incdntgb = "";
				var str_trino = "";				
				var tr_class = "";
				var s_user_phone = "";
				var s_user_id = "";
				var str_tmp_phone = "";
				var str_tmp_id = "";
				var j, k;
				
				$.each(data.list, function(key, value){
				
				str_tmp_phone = "";
				str_tmp_id = "";
				
				if(NvlStr(value.INCDNT_GB) == "1")
					str_incdntgb = "행정";
				else if(NvlStr(value.INCDNT_GB) == "2")	
					str_incdntgb = "민사";
				
				if(NvlStr(value.ICDNT_TRIAL_NO) == "11")
					str_trino = "1심";
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "12")
					str_trino = "신청사건";
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "13")
					str_trino = "신청사건(즉시항고)";
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "21")
					str_trino = "2심";				
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "31")
					str_trino = "3심";
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "32")
					str_trino = "환송후사건";
				else if(NvlStr(value.ICDNT_TRIAL_NO) == "33")
					str_trino = "소송비용확정결정";
				
				
				if ((i % 2) == 0 )
					tr_class = "tr2";
				else
					tr_class = "tr1";
							
				s_user_phone = NvlStr(value.PERFORM_USERHNO).split( ',' );
				s_user_id = NvlStr(value.PERFORM_USERID).split( ',' ); 
			    				
											
				str += "<tr class=\"" + tr_class + "\">" + 
				           "<td>" + value.GB + "</td>" +	
						   "<td>" + 
						   "<a href='#this' name='title' >" + NvlStr(value.ICDNT_NO) + "</a>" + 									  
						   "<input type='hidden' name='data1' id='ID' value=" + value.ICDNT_NO + ">" +
						   "<input type='hidden' name='data2' id='MAST_ID' value=" + value.MAST_ID + ">" +
						   "<input type='hidden' name='data3' id='ICDNT_TRIAL_NO' value=" + value.ICDNT_TRIAL_NO + ">" +
						   "<input type='hidden' name='data4' id='INCDNT_GB' value=" + value.INCDNT_GB + ">" +
						   "</td>" +		                   								 
						   "<td>" + str_incdntgb + "</td>" +
						   "<td>" + str_trino + "</td>" +
						   "<td>" + formatDate(NvlStr(value.APPEAL_RESPONSE_DATE),".") + "</td>" +	
						   "<td>" + formatDate(NvlStr(value.APPEAL_REASON_DATE),".") + "</td>" +	
						   "<td>" + formatDate(NvlStr(value.ARGUE_SET_DATE),".") + "</td>" +	
						   "<td>" + formatDate(NvlStr(value.APPEAL_SUBMIT_DATE),".") + "</td>" +	
						   "<td>";
						   						   
						   for ( j = 0 ; j < s_user_phone.length; j++ ) {
						    	
							   str_tmp_id += "<a href='#this' name='userid" + i + "' id ='" + j + "' >" + s_user_id[j] + "</a>" +
						    	"<input type='hidden' name='data5" + i + "' id='UID" + j + "' value=" + s_user_id[j] + ">";
						    	if( j != (s_user_id.length-1))
						    		str_tmp_id += ", ";						    		
						    }
				
			    str += str_tmp_id + "</td><td>"; 
						   
						    for ( k = 0 ; k < s_user_phone.length; k++ ) {
						    	
						    	str_tmp_phone += "<a href='#this' name='phone" + i + "' id ='" + k + "' >" + s_user_phone[k] + "</a>" +
						    	"<input type='hidden' name='data6" + i + "' id='USER_PHONE" + k + "' value=" + s_user_phone[k] + ">";
						    	if( k != (s_user_phone.length-1))
						    			str_tmp_phone += ", ";						    		
						    }
						    
				str += str_tmp_phone + "</td></tr>";
				
				//console.log(str);
				i++;
				
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_indTrialDetail($(this));
				});
				
				
				$("a[name^=userid]").on("click", function(e){ //제목					
					$('#pop1').show();
					fn_jikwonlist($(this), $(this).attr('id'));
				});
				
				$("a[name^=phone]").on("click", function(e){ //제목					
					$('#pop2').show();
					fn_smsSendList($(this), $(this).attr('id'));
				});
				
				
				
			}
		}
		
		
		function fn_jikwonlist(obj, num){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("NDU_USER_ID", obj.parent().find("#UID" + num).val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
			var str = "";
			var i = 1;
			var body = $("#poptable1");
			
			body.empty();
					
			$.each(data.list, function(key, value){										
					
				str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
				   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_USER_NAME)  + "</td>" +	                   
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
					
		}
		
		
		function fn_smsSendList(obj, num){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popSmsSendList.do' />");
			comAjax.setCallback("fn_smsSendListCallback");
			comAjax.addParam("DSTADDR", obj.parent().find("#USER_PHONE" + num).val());
			//comAjax.addParam("DSTADDR", "01051246045");
			comAjax.addParam("PAGE_ROW", "400");
			comAjax.ajax();
		}
		
		function fn_smsSendListCallback(data){
			var str = "";
			var i = 1;
			var body = $("#poptable2");
			
			body.empty();
					
			$.each(data.list, function(key, value){										
					
				str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
				   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +				                                                
                   "<td class=\"list_center\" nowrap>" + NvlStr(value.SEND_TIME) + "</td>" +
                   "<td class=\"list_center\" nowrap>" + NvlStr(value.REPORT_TIME) + "</td>" +
                   "<td class=\"list_center\" nowrap>" + NvlStr(value.RESULT) + "</td>" +
                   "<td class=\"list_center\" nowrap>" + NvlStr(value.TEXT) + "</td>" +
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
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">알림데이터 조회</td>
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
    
    
    
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">
     <tr>
       <td></td>
       <td align="right">
       &nbsp;
       </td>
     </tr>
     <tr>
       <td align="left"><!-- <b>Total : <span id="tcnt"></span></b> &nbsp; 
       <select id="DISP_CNT" name="DISP_CNT" class="input">
       <option value="10">10</option>
       <option value="20">20</option>
       <option value="30">30</option>
       <option value="40">40</option>
       <option value="50">50</option>
   	   </select>
   	    -->
       </td>
       <td align="right">
       &nbsp;
       </td>
     </tr>
     <tr>
       <td class="margin_btn" colspan="2"></td>
     </tr>
   </table>	
    
	 <table class="tbl1">
     <thead>
        <tr>
          <th>&nbsp;</th><th>사건번호</th><th>사건구분</th><th>사건심</th><th>답변서제출일자</th><th>항소(상고)이유서<br />제출일자</th><th>변론기일</th><th>항소(상고)장<br />제출일자</th><th>대상자</th><th>핸드폰번호</th>
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

<!-- POPUP --> 
<div id="pop1" style="position:absolute;left:200px;top:190;z-index:200;display:none;"> 
<table width=530 height=120 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr> 
<tr> 
    <td style="border:1px #666666 solid" height=100 align=center bgcolor=white> 
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
			<tbody id='poptable1'>			
			</tbody>
			</table>
    </td> 
</tr>     
</table> 
</div>


<!-- POPUP --> 
<div id="pop2" style="position:absolute;left:80px;top:120;z-index:200;display:none;"> 
<table width=800 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close3"><B>[닫기]</B></a> 
    </td> 
</tr> 
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>		
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="50"></td>
				<td class="list_tit_line_s" width="100%"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>			
				<td nowrap class="list_tit_bar">보낸시간</td>
				<td nowrap class="list_tit_bar">받은시간</td>
				<td nowrap class="list_tit_bar">결과</td>
				<td nowrap class="list_tit_bar">내용</td>							
			</tr>
			<tr>
				<td colspan="5" class="list_tit_line_e"></td>
			</tr>
			<tbody id='poptable2'>			
			</tbody>
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close4"><B>[닫기]</B></a> 
    </td> 
</tr>      
</table> 
</div> 		
</body>
</html>