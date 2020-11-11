<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			$('#pop1').draggable({cursor:'move',snap:true,scroll:true,scrollSensitivity:100});
			
			fn_dongList();
			fn_yearList();
			
			fn_selectFixedDateList(1);
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				if(user_right == 'A') {
					fn_fixedDateDataWrite();
				}
				else{
					alert('권한이 없습니다.');	
				}				
			});
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();  
			    });
				
				//fn_initial_setting();
			});
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectFixedDateList();
			});
			
		
			$("#DONG").change(function(e){	
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectFixedDateList();
			});
			
			
			$("#YEAR").change(function(e){	
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectFixedDateList();
			});
			
			
			$("#MONTH").change(function(e){	
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectFixedDateList();
			});
			
			
			$("#TENANT_NAME").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					e.preventDefault();
					$("#PAGE_INDEX").val("1");
					fn_selectFixedDateList();
				}

			});
			
			$('#close1').click(function() {
		        $('#pop1').hide();
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
				fn_selectFixedDateList();
			});	
		
		});
		
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		function fn_fixedDateDataWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/fixedDateDataWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_printList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftPrintList.do' />");
			comSubmit.submit();
		}
		
		function fn_fixedDateDataUpdate(obj){
			var comSubmit = new ComSubmit();
			var dep_id = obj.parent().find("#DEP_ID").val();
			
			if( dep_id == $('#DEPT_ID').val() || user_right == "A")
			{
				comSubmit.setUrl("<c:url value='/daejang/fixedDateDataUpdate.do' />");
				comSubmit.addParam("ID", obj.parent().find("#ID").val());
				comSubmit.submit();
			}
			else{
				alert('권한이 없습니다.');
			}
		}
		
		function fn_selectFixedDateList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectFixedDateList.do' />");
			comAjax.setCallback("fn_selectFixedDateListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());		
			comAjax.addParam("DONG",$("#DONG").val());
			comAjax.addParam("YEAR", $("#YEAR").val());
			comAjax.addParam("MONTH",$("#MONTH").val());
			comAjax.addParam("TENANT_NAME",$("#TENANT_NAME").val());
					
			comAjax.ajax();
		}
		
		
		function fn_selectFixedDateListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"8\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"8\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectFixedDateList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
		
				$.each(data.list, function(key, value){
					       t_str = "";
							
						   if(value.DONG_CD_OLD == "DG2" && (value.YEAR == "2002" || value.YEAR == "2003" || value.YEAR == "2006")) {
							   t_str = "<a href='#this' name='LINK3' >" +  							  									   									   
							   "<img src=\"/fds/images/icon_pdf.gif\" width=16 height=16 border=0 vspace=3 style=\"margin-right:5;margin-bottom:-6\" / ></a>" +
							   "<input type='hidden' name='data1' id='IMGLK3' value=" + value.DONG_CD_OLD + "_" + value.YEAR + "_1" + ">";
						   }
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
									   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + NvlStr(value.DONG_NAME_OLD) + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + NvlStr(value.YEAR) + "</td>" + 					                   
					                   "<td class=\"list_center\" nowrap>" + NvlStr(value.TENANT_NAME) + "</td>" +					                  					                					                  									
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.FIXDATE_GRANT_DATE), ".") +  "</td>" +									   									   
									   "<td class=\"list_center\" nowrap>" +
									   "<a href='#this' name='LINK1' >" +  							  									   									   
									   "<img src=\"/fds/images/icon_pdf.gif\" width=16 height=16 border=0 vspace=3 style=\"margin-right:5;margin-bottom:-6\" / ></a>" +
									   "<input type='hidden' name='data1' id='IMGLK1' value=" + value.LINK_IMG_NM + ">" +
									   "</td>" +
									   "<td class=\"list_center\" nowrap>" + 									   
									   "<a href='#this' name='LINK2' >" +  							  									   									   
									   "<img src=\"/fds/images/icon_pdf.gif\" width=16 height=16 border=0 vspace=3 style=\"margin-right:5;margin-bottom:-6\" / ></a>" +
									   "<input type='hidden' name='data1' id='IMGLK2' value=" + value.DONG_CD_OLD + "_" + value.YEAR + ">" +									   
									   t_str +
									   "</td>" +
									   "<td class=\"list_center\" nowrap>" + 
									   "<a href='#this' name='title' >" +  "수정 " + value.ID + "</a>" +
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" +
									   "<input type='hidden' name='dep_id' id='DEP_ID' value=" + value.DEPT_ID + ">" +
									   "</td>" +
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"8\" class=\"list_line\"></td>" + 
									   "</tr>";
							
							      
								    
							
				});
				body.append(str);
				
				$("a[name='LINK1']").on("click", function(e){ //개별 
					if($('#pop1').css("display") != "none")
						$('#pop1').hide();
				
					$('#pop1').show();
					fn_showimg($(this), 1);
				});
				
				$("a[name='LINK2']").on("click", function(e){ //년도
					
					if($('#pop1').css("display") != "none")
						$('#pop1').hide();
									
					$('#pop1').show();
					fn_showimg($(this), 2);
				});
				
				$("a[name='LINK3']").on("click", function(e){ //년도
					
					if($('#pop1').css("display") != "none")
						$('#pop1').hide();
									
					$('#pop1').show();
					fn_showimg($(this), 3);
				});
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_fixedDateDataUpdate($(this));
				});
				
			}
		}
		
		
		
		function fn_showimg(obj, gb){
			var str = "";		
			var body = $("#poptable1");
			var imglink = "";
			var a_imglink = "";
			
			body.empty();
			
			if(gb == 2){
				imglink = obj.parent().find("#IMGLK2").val();
				
				
				alert("data_gb_2=[" + imglink + "]");
				
				a_imglink = imglink.split("_");
				
				str += "<tr>" +
				   "<td align=center><embed src=\"http://98.23.7.109:8099/fixedpdf/" + a_imglink[0] + "/" + imglink + ".pdf\" width=\"562\" height=\"799\"></td>" +                  
				   "</tr>";
				
				//alert(str); 
			}
			else if(gb == 3){
				imglink = obj.parent().find("#IMGLK3").val();
				
				
				alert("data_gb_3=[" + imglink + "]");
				
				a_imglink = imglink.split("_");
				
				str += "<tr>" +
				   "<td align=center><embed src=\"http://98.23.7.109:8099/fixedpdf/" + a_imglink[0] + "/" + imglink + ".pdf\" width=\"562\" height=\"799\"></td>" +                  
				   "</tr>";
				
				//alert(str); 
			}			
			else{
				imglink = obj.parent().find("#IMGLK1").val();
				
				alert("data_gb_1=[" + imglink + "]");
				
				a_imglink = imglink.split("_");
								
				str += "<tr>" +
				   //"<td align=center><embed src=\"http://98.23.7.109:8099/fixedpdf/" + a_imglink[0] + "/" +  a_imglink[0] + "_" + a_imglink[1]  +"/" + imglink  + ".pdf#toolbar=0&navpanes=0&scrollbar=0\" width=\"520\" height=\"740\"></td>" +
				   "<td align=center><embed src=\"http://98.23.7.109:8099/fixedpdf/" + a_imglink[0] + "/" +  a_imglink[0] + "_" + a_imglink[1]  +"/" + imglink  + ".pdf\" width=\"562\" height=\"799\"></td>" +
				   "</tr>";
				   
				//alert(str);
			}
						
			body.append(str);
			
		}
		
		function fn_yearList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/yearList.do' />");
			comAjax.setCallback("fn_yearListCallback");			
			comAjax.ajax();
		}
		
		function fn_yearListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.YEAR + "\">" + value.YEAR + " 년 </option>";
					i++;
			});
			
			$("#YEAR").append(str);			
		}
		
		
		function fn_dongList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/dongList.do' />");
			comAjax.setCallback("fn_dongListCallback");			
			comAjax.ajax();
		}
		
		function fn_dongListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.DONG_CD_OLD + "\">" + value.DONG_NAME_OLD + "</option>";
					i++;
			});
			
			$("#DONG").append(str);			
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
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
        <td class="pupup_title"><img src="/fds/images/popup_title.gif" align="absmiddle">확정일자 수기대장 열람시스템</td>
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
    <부서> : ${sessionScope.userinfo.deptName}  <이름> : ${sessionScope.userinfo.userName} &nbsp;&nbsp; 
    <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
	<c:choose>
    <c:when test="${us_rt eq 'A'}">
    <a href="/fds/common/idApproveList.do"><u>관리자메뉴</u></a>
    </c:when>
    </c:choose> 
    <!-- 
    <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
     <tr>
       <td><img src="/fds/images/btn_type1_head.gif"></td>
       <td background="/fds/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">사용자화면</a></td>
       <td><img src="/fds/images/btn_type1_end.gif"></td>
     </tr>
     </table> 
     -->
  	</td>
  </tr>
  <tr>
    <td class="pupup_frame" style="padding-right:12px">	
 
    <table width="400" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">     
	  <tr>
           <td width="100" class="tbl_field">해당동</td>
           <td colspan="3" width="300" class="tbl_list_left">           	                 
               <select id="DONG" name="DONG" class="input">
	           <option value="">----------</option>	                	
	           </select>               	                	               
           </td>
      </tr>     
	  <tr>
        <td width="100" class="tbl_field">년도 / 월</td>
        <td colspan="3" width="300" class="tbl_list_left">	               
            <select id="YEAR" name="YEAR" class="input">
	          	<option value="">----------</option>	                	
	        </select> &nbsp;	  	        
	        <select id="MONTH" name="MONTH" class="input">
	          	<option value="">----------</option>
	          	<option value="01">1 월</option>
	          	<option value="02">2 월</option>
	          	<option value="03">3 월</option>
	          	<option value="04">4 월</option>
	          	<option value="05">5 월</option>
	          	<option value="06">6 월</option>
	          	<option value="07">7 월</option>
	          	<option value="08">8 월</option>
	          	<option value="09">9 월</option>
	          	<option value="10">10 월</option>
				<option value="11">11 월</option>
                <option value="12">12 월</option>					          		                	
	        </select>                        
        </td>
      </tr>
      <tr>
        <td width="100" class="tbl_field">임차인</td>
        <td colspan="3" width="300" class="tbl_list_left">	               
            <input type="text" id="TENANT_NAME" name="TENANT_NAME" class="input" style="width:200;">	                          
        </td>
      </tr>
      </table>
    
    
    
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">
     <tr>
       <td></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/fds/images/btn_type1_head.gif"></td>
               <td background="/fds/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/fds/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/fds/images/btn_type0_head.gif"></td>
               <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/fds/images/btn_type0_end.gif"></td>
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
           <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
		   <c:choose>
			 <c:when test="${us_rt eq 'A'}">
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/fds/images/btn_type0_head.gif"></td>
               <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">신규입력</font></a></td>
               <td><img src="/fds/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
             </c:when>
           </c:choose>          			
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/fds/images/btn_type0_head.gif"></td>
               <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="logout"><font color="white">로그아웃</font></a></td>
               <td><img src="/fds/images/btn_type0_end.gif"></td>
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
   
    
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="100"></td>
				<td class="list_tit_line_s" width="70"></td>				
				<td class="list_tit_line_s" width="150"></td>								
				<td class="list_tit_line_s" width="140"></td>				
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="80"></td>						
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">행정동</td>
				<td nowrap class="list_tit_bar">년도</td>
				<td nowrap class="list_tit_bar">임차인</td>				
				<td nowrap class="list_tit_bar">확정일자부여일</td>	
				<td nowrap class="list_tit_bar">PDF보기(1page)</td>
				<td nowrap class="list_tit_bar">PDF보기(년도)</td>
				<td nowrap class="list_tit_bar">수정하기</td>
			</tr>
			<tr>
				<td colspan="8" class="list_tit_line_e"></td>
			</tr>
		
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
<div id="pop1" style="position:absolute;left:300px;top:50;z-index:200;display:none;border:4px solid #ffd7eb;"> 
<table width=572 height=839 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr> 
<tr> 
    <td style="border:2px #ddd solid" height=809 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0">				
			<tbody id='poptable1'>			
			</tbody>
			</table>
    </td> 
</tr>     
</table> 
</div>
		
</body>
</html>