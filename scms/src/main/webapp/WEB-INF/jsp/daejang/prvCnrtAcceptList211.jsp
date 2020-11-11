<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
	
		$(document).ready(function(){
			
		
			fn_selectPrvCnrtAcceptList(1);
			
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
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtAcceptList();
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
				fn_selectPrvCnrtAcceptList();
			});	
 
		});
		
		
	
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
	    function fn_prvCnrtPlanUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "3");  //실적 1, 평가 2 발주계획 3
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanUpdate.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_selectPrvCnrtAcceptList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtAcceptList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtAcceptListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			comAjax.addParam("SAUP_NM", $("#SAUP_NM").val());
			//comAjax.addParam("INS_ID", "${sessionScope.userinfo.userId}");
			comAjax.addParam("INS_ID", "20633120");			
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtAcceptListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			var i = 0;
			
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
					eventName : "fn_selectPrvCnrtAcceptList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp1 = "";
				var tmp2 = "";
				var tmp3 = "";
				var tmp4 = "";
				var tmp5 = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
					       tmp1 = "";
					       tmp2 = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       
					       if(NvlStr(value.ATT1) != ''){
					    	   tmp1 = "<a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
					    	         "<input type='hidden' name='FID' id='FID' value=" + value.ATT1 + ">" +
					    	         "<input type='hidden' name='FKEY' id='FKEY' value=" + value.CSP_FILE_KEY + ">";
					    	         
					       }
					       
					       if(NvlStr(value.ATT2) != ''){
					    	   tmp2 = " <a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
					    	          "<input type='hidden' name='FID' id='FID' value=" + value.ATT2 + ">" +
				    	              "<input type='hidden' name='FKEY' id='FKEY' value=" + value.CSP_FILE_KEY + ">";			    	         					    	         
					       }
					       
					       
					       if(NvlStr(value.ATT3) != ''){
					    	   tmp3 = " <a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
				    	          "<input type='hidden' name='FID' id='FID' value=" + value.ATT3 + ">" +
			    	              "<input type='hidden' name='FKEY' id='FKEY' value=" + value.CSP_FILE_KEY + ">";						    	         					    	         
					       }
					       
					       if(NvlStr(value.ATT4) != ''){
					    	   tmp4 = " <a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
				    	          "<input type='hidden' name='FID' id='FID' value=" + value.ATT4 + ">" +
			    	              "<input type='hidden' name='FKEY' id='FKEY' value=" + value.CSP_FILE_KEY + ">";						    	         					    	         
					       }
					       
					       if(NvlStr(value.ATT5) != ''){
					    	   tmp5 = " <a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
				    	          "<input type='hidden' name='FID' id='FID' value=" + value.ATT5 + ">" +
			    	              "<input type='hidden' name='FKEY' id='FKEY' value=" + value.CSP_FILE_KEY + ">";						    	         					    	         
					       }
					       
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.RNUM  + "</td>" +						   			                
						   			   "<td>" + dv_yrmonth(NvlStr(value.SAUP_YEAR), '.') + "</td>" +						   			 					   	
						   			   "<td class=\"list_center_sht\">" +
						   			   "<font title=\"" + NvlStr(value.SAUP_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.SAUP_NM), 18) +  "</font>" +						   			   
						   			   "</td>" +
						   			   "<td>" + NvlStr(value.CSP_BUSINESS_NAME) + "</td>" +
						   			   "<td>" + NvlStr(value.CSP_USER_NAME) + "</td>" +
						   			   "<td>" + NvlStr(value.CSP_TEL_NO) + "</td>" +
						   			   "<td>" + formatDate(NvlStr(value.CSP_REGI_DATE), ".") + "</td>" +						   			   
						   			   "<td>" + tmp1 + "</td>" +
						   			   "<td>" + tmp2 + "</td>" +
						   			   "<td>" + tmp3 + "</td>" +
						   			   "<td>" + tmp4 + "</td>" +
						   			   "<td>" + tmp5 + "</td>" +
					                   "</tr>";
					                   i++;
							
				});
				body.append(str);
			
				$("a[name='filenm']").on("click", function(e){ //파일명 				
					e.preventDefault();
					fn_downloadFile($(this));
				});
											
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanWrite.do' />");
			comSubmit.submit();
		}

		function fn_downloadFile(obj){			
			var idx = obj.parent().find("#FID").val();
			//var idx = obj.parent().find("[name='FID']").attr('id');			
			var ikey = obj.parent().find("#FKEY").val();
			//alert("idx= " + idx);			
			var comSubmit = new ComSubmit();
			var furl = "http://www.gangnam.go.kr/file/" + idx + "/get/" + ikey + "/download.do"; 
			comSubmit.setUrl("<c:url value='" + furl + "' />");			
			comSubmit.submit();
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
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>사업접수현황</td>
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
        <td width="120" class="tbl_field">사업명</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="SAUP_NM" name="SAUP_NM" class="input" style="width:300;">	                          
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
          <th>&nbsp;</th><th>발주시기</th>
          <th>사업명</th><th>업체명</th><th>담당자</th>
          <th>연락처</th><th>접수시간</th><th>첨부</th><th></th><th></th><th></th><th></th>
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