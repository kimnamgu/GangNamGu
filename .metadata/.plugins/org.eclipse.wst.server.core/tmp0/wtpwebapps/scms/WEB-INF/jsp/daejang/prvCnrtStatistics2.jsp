<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var cid = "";
		
		$(document).ready(function(){
			
			fn_selectPrvCnrtStatistics2(1);
						
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();
			    });				
			});
			
			
			$("#search").on("click", function(e){ //검색 버튼
				
				e.preventDefault();
				$("#PAGE_INDEX").val("1");				
				fn_selectPrvCnrtStatistics2();					
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
				fn_selectPrvCnrtStatistics2();
			});	
			
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });					
		});
		
	
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
				
		function fn_setCountList(obj){
		    cid = "";
		    $("#PAGE_INDEX2").val("1"); //페이지 초기화 1 page로
			cid = obj.parent().find("#CID").val();
			fn_countList();
		}
	
		function fn_countList(){
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/daejang/selectCountList.do' />");
			comAjax.setCallback("fn_countListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX2").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT2").val());
			comAjax.addParam("COMP_ID",cid);			
			comAjax.ajax();
		}
		
		function fn_countListCallback(data){
			data = $.parseJSON(data);			
			var str = "";
			var i = 1;			
			var body = $("#cnt_table");
			var total = data.TOTAL;
			var recordcnt = $("#RECORD_COUNT2").val();
			
			body.empty();
			
			var params = {
					divId : "PAGE_NAVI2",
					pageIndex : "PAGE_INDEX2",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_countList"
			};
			gfn_renderPaging(params);
			
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +	
					   "<td class=\"list_center\" nowrap>" + dv_yrmonth(NvlStr(value.CNRT_YEAR), '.') + "</td>" +
	                   "<td class=\"list_center\" nowrap><font title=\"" + NvlStr(value.CNRT_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_NM), 20) +  "</font></td>" +
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.CNRT_DEPT_NM) + "</td>" +	                   
	                   "<td class=\"list_center\" nowrap>" + comma(NvlStr(value.CNRT_AMT)) + "</td>" +
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.CNRT_EVAL) + "</td>" +	                   
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"6\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});			
			
			body.append(str);			
		}
	
				
		function fn_selectPrvCnrtStatistics2(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			if(user_right == 'A'){
				comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtStatistics2.do' />");
				comAjax.setCallback("fn_selectPrvCnrtStatistics2Callback");
				comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
				comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());	
				comAjax.addParam("SAUP_YEAR", $("#SAUP_YEAR").val());			
				comAjax.addParam("COMP_NM", $("#COMP_NM").val());		      
				comAjax.ajax();
			}
			else{
				alert('권한이 없어 조회할 수 없습니다.');
			}
			
		}
		
		
		function fn_selectPrvCnrtStatistics2Callback(data){			
			data = $.parseJSON(data);			
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"4\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtStatistics2"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
					       tmp = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       
					       if(value.ATTACH == 'Y')
					    	   tmp = "<a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
					    	         "<input type='hidden' name='FID' id='FID' value=" + value.FID + ">";
												
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.RNUM  + "</td>" +
						   			   "<td>" + out_UH(NvlStr(value.GB1))  + "</td>" +
						   			   //"<td>" + out_UH2(NvlStr(value.GB2))  + "</td>" +						   			
						   			   "<td>" + 
						   			   "<a href='#this' name='title'>" + value.COMP_NM + "</a>" +
						   			   "<input type='hidden' name='title' id='CID' value=" + value.COMP_ID + ">" + 
						   			   "</td>" +						   			
						   			   "<td>" + value.CNT + "</td>" +						   			  					   
					                   "</tr>";					    
							
				});
				body.append(str);
								
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					$('#pop').show();			        
					fn_setCountList($(this));
				});														
			}
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>업체별 만족도 평가 조회</td>
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
        <td width="120" class="tbl_field">업체명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="COMP_NM" name="COMP_NM" class="input" style="width:300;">	                          
        </td>
      </tr>       
      </table>
          
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">    
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
          <th>&nbsp;</th>
          <th>유형</th>
          <!-- <th>세부유형</th> -->
          <th>업체명</th>
          <th>건수</th>
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
<div id="pop" style="position:absolute;left:395px;top:90;z-index:200;display:none;"> 
<table width=560 height=250 cellpadding=2 cellspacing=0>
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
				<td class="list_tit_line_s" width="70"></td>				
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="100"></td>
				<td class="list_tit_line_s" width="70"></td>
				<td class="list_tit_line_s" width="40"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">계약년월</td>
				<td nowrap class="list_tit_bar">계약명</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">계약금액</td>
				<td nowrap class="list_tit_bar">평가</td>										
			</tr>
			<tr>
				<td colspan="6" class="list_tit_line_e"></td>
			</tr>
			<tbody id='cnt_table'>			
			</tbody>
			<tr>
				<td colspan="6" height="20"></td>
			</tr>
			<tr>
				<td colspan="6">
				<div id="PAGE_NAVI2" align='center'></div>
				<input type="hidden" id="PAGE_INDEX2" name="PAGE_INDEX2"/>
				<input type="hidden" id="RECORD_COUNT2" name="RECORD_COUNT2" value="10"/>
				<%@ include file="/WEB-INF/include/include-body.jspf" %>
				</td>
			</tr>
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