<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_selectSelfDgnsMastList(1);
			
			$("#down").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();				
				
				if(user_right == 'A')
				{
					fn_down();
				}
				else
				{
					alert('권한이 없습니다.')
				}				
			});
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#insert").on("click", function(e){ //dlqfur
				e.preventDefault();
			
				if(user_right == 'A')
				{
					fn_insert();
				}
				else
				{
					alert('권한이 없습니다.')
				}	
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
				fn_selectSelfDgnsMastList();
			});
			
		    
			$("#inquiry").on("click", function(e){ //조회
				e.preventDefault();				
				fn_inquiry();
			});
			
		
			$("input:radio[name=SHORTEN_YN]").change(function(e){			
				//e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectSelfDgnsMastList();
			});
						
			$("#USER_NAME").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectSelfDgnsMastList();
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
				fn_selectSelfDgnsMastList();
			});	
 
		});
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		function fn_down(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/diagnose/exceldown.do' />");
			//comSubmit.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			//comSubmit.addParam("PAGE_ROW", $("#RECORD_COUNT").val());					
			comSubmit.addParam("DGNS_NO",$("#DGNS_NO").val());					
			comSubmit.submit();
		}
		
		function fn_printList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/diagnose/giftPrintList.do' />");
			comSubmit.submit();
		}
		
	    function fn_inquiry(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsMastList.do' />");			
			comSubmit.submit();
		}
		
	    
	
	    	  
	    function fn_selfDgnsContent(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("DGNS_NO", obj.parent().find("#UID").val());
			comSubmit.addParam("BOT_IMG", obj.parent().find("#IMGID").val());
			comSubmit.addParam("SBJ_SEQ", "1");			
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsContent.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_fixedDateDataUpdate(obj){
			var comSubmit = new ComSubmit();
			var dep_id = obj.parent().find("#DEP_ID").val();
			
			if( dep_id == $('#DEPT_ID').val() || user_right == "A")
			{
				comSubmit.setUrl("<c:url value='/diagnose/fixedDateDataUpdate.do' />");
				comSubmit.addParam("ID", obj.parent().find("#ID").val());
				comSubmit.addParam("DEP_ID", dep_id);
				comSubmit.submit();
			}
			else{
				alert('권한이 없습니다.');
			}
		}
		
		function fn_selectSelfDgnsMastList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/diagnose/selectSelfDgnsMastList.do' />");
			comAjax.setCallback("fn_selectSelfDgnsMastListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());					
			comAjax.addParam("DGNS_NO",$("#DGNS_NO").val());			
			
			comAjax.ajax();
		}
		
		
		function fn_selectSelfDgnsMastListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"15\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectSelfDgnsMastList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var tmp = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       
					       tmp = "";
												
						   str += "<tr class=\"" + tr_class + "\">" + 
									   "<td>" + ((total - value.RNUM)+1)  + "</td>" +									     					                   
					                   "<td>" +
									   "<a href='#this' name='title' >" + NvlStr(value.TITLE) + "</a>" + 									  
						   			   "<input type='hidden' name='title' id='UID' value=" + value.DGNS_NO + ">" + 
						   			   "<input type='hidden' name='title2' id='IMGID' value=" + NvlStr(value.IMG_FILE_NAME) + ">" +
					                   "</td>" +					                   
					                   "<td>" + 
					                   "<a href='#this' name='filenm' >" + NvlStr(value.ORIGINAL_FILE_NAME) + "</a>" + 									  
						   			   "<input type='hidden' name='filenm' id='FID' value=" + value.DGNS_NO + ">" +						   			   
					                   "</td>";					                   
									   
					                   if( user_right == "A")
					                   {					                   
					                		tmp += "<td>" + NvlStr(value.ANSCOUNT) + "</td>" +
									           	   "<td>" + NvlStr(value.DOWNCOUNT) + "</td>" +	
									           	   "<td>" + 
								                   "<a href='#this' name='no1' >수정/삭제</a>" +
								                   "<input type='hidden' name='no1' id='NNO' value=" + value.DGNS_NO + ">" +
								                   "</td>";					                  			                  
								                   
					                   }
					                   else
					                  {
					                	   tmp += "<td>&nbsp;</td>" + 
					                	          "<td>&nbsp;</td>" +
					                	          "<td>&nbsp;</td>";
					                	   
					                  }
									   
					                 str += tmp + "</tr>"; 
					                   
							      
								    
							
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_selfDgnsContent($(this));
				});
				
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
				
				
				$("a[name='no1']").on("click", function(e){ //번호
					e.preventDefault();
					fn_basicInfo($(this));
				});
				
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsMastWrite.do' />");
			comSubmit.submit();
		}

		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
						
			/*var cn = $.cookie('down'+idx);
			alert(cn + " " + idx);
			if(cn == null || cn == undefined)
			{
			$.cookie('down'+idx, 'Y',  { expires : 1, path : '/' });
			*/
				comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
				comSubmit.addParam("DGNS_NO", idx);
				comSubmit.submit();
			//}
		}			
		
		
		function fn_basicInfo(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("DGNS_NO", obj.parent().find("#NNO").val());						
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsBasicInfo.do' />");			
			comSubmit.submit();
		}

	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="US_ID" name="US_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="DGNS_NO" name="DGNS_NO" value="">
<input type="hidden" id="SBJ_SEQ" name="SBJ_SEQ" value="">
<input type="hidden" id="BOT_IMG" name="BOT_IMG" value="">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/sds/images/popup_title.gif" align="absmiddle"></a>자가진단시스템</td>
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
           <c:choose>
           <c:when test="${chkAdmin eq 'A'}">           
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/sds/images/btn_type0_head.gif"></td>
               <td background="/sds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="down"><font color="white">엑셀다운</font></a></td>
               <td><img src="/sds/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>           
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/sds/images/btn_type0_head.gif"></td>
               <td background="/sds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="insert"><font color="white">입력</font></a></td>
               <td><img src="/sds/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           </c:when>
           </c:choose>                                        
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
          <th>&nbsp;</th><th>이름</th><th>다운로드</th>                   
          <c:choose>
          <c:when test="${chkAdmin eq 'A'}">
          <th>실시횟수</th><th>다운로드횟수</th><th>수정/삭제</th>
          </c:when>
          <c:otherwise>
          <th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th>
          </c:otherwise>
          </c:choose>
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
<div id="pop1" style="position:absolute;left:300px;top:50;z-index:200;display:none;border:2px solid #c84637;"> 
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