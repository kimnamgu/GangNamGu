<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var sel_fucnm = "";
		$(document).ready(function(){
			
			fn_selectPrvCnrtDutyList(1);
			fn_selectPrvCnrtExpertList(1);
			
			$("#write1").on("click", function(e){ //글쓰기 버튼			
				e.preventDefault();				
				fn_prvCnrtDutyWrite();				
			});	
			
			$("#write2").on("click", function(e){ //글쓰기 버튼
						
				e.preventDefault();				
				fn_prvCnrtExpertWrite();					
			});	
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_basicinfo();
			});
			
			$("#record").on("click", function(e){ //
				e.preventDefault();								
				fn_recordList();				
			});
			
			
			$("#reason").on("click", function(e){ //
				e.preventDefault();								
				fn_reasonList();				
			});
			
			
			$("#eval").on("click", function(e){ //
				e.preventDefault();								
				fn_evalList();				
			});
			
			
		});
		
		
		function fn_prvCnrtDutyWrite(){			
			var comSubmit = new ComSubmit();
	
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtDutyWrite.do' />");
			comSubmit.addParam("ID", "${param.ID}");	
			comSubmit.submit();
		}
		
		
		function fn_prvCnrtExpertWrite(){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtExpertWrite.do' />");
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.submit();
		}
		
		function fn_basicinfo(){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.addParam("PARENT_ID", "${param.ID}");
			comSubmit.addParam("BOARD_GB", "5");  //실적 1, 평가 2 발주계획 3 기술자보유 4 업체등록 5
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompInfo.do' />");
			comSubmit.submit();
		}

		function fn_reasonList(){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtReasonList.do' />");
			comSubmit.submit();
		}
				
		function fn_evalList(){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${param.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtEvalList.do' />");
			comSubmit.submit();
		}
		
		
	
		function fn_selectPrvCnrtDutyList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtDutyList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtDutyListCallback");
			comAjax.addParam("COMP_ID","${param.ID}");			
			comAjax.addParam("DATA_GB","1");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());			
			
			comAjax.ajax();
		}
		
		function fn_selectPrvCnrtDutyListCallback(data){
			data = $.parseJSON(data);
			
			var total = data.TOTAL;	
			//alert(total);
			var body = $("#mytable1");
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
					eventName : "fn_selectPrvCnrtDutyList"
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
						   			   "<td>" + ((total - value.RNUM)+1)  + "</td>" +						   			                
						   			   "<td>" + 
						   			   "<a href='#this' name='title1' >" + dv_yrmonth(NvlStr(value.CNRT_YEAR), ".") + "</a>" +
						   			   "<input type='hidden' name='title1' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + NvlStr(value.CNRT_AGENCY_NM) + "</td>" +	     
						   			   "<td class=\"list_center_sht\">" + 
						   			   "<a href='#this' name='title1' ><font title=\"" + NvlStr(value.CNRT_NM) + "\" style=\"CURSOR:hand;\">" +  CutString(NvlStr(value.CNRT_NM), 18) + "</font></a>" +
						   			   "<input type='hidden' name='title1' id='UID' value=" + value.ID + ">" +
						   			   "</td>" +
						   			   "<td class=\"list_center_sht\"><font title=\"" + NvlStr(value.CNRT_DETAIL) + "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_DETAIL), 20) + "</font></a></td>" +
						   			   "<td>" + comma(NvlStr(value.CNRT_AMT)) + "</td>" +
						   			   "<td>" + tmp + "</td>" +						 		   			   									  
					                   "</tr>"; 					                   							  								    
							
				});
				body.append(str);
				
				
				$("a[name='title1']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtDutyUpdate($(this));
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
		
		
		
		function fn_prvCnrtDutyUpdate(obj){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("CON_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("ID","${param.ID}");
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "1");  //실적 1, 평가 2 발주계획 3
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtDutyUpdate.do' />");
			comSubmit.submit();
		}
		
		
		function fn_selectPrvCnrtExpertList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtExpertList.do' />");			
			comAjax.setCallback("fn_selectPrvCnrtExpertListCallback");
			comAjax.addParam("COMP_ID","${param.ID}");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX2").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT2").val());					
			
			comAjax.ajax();
		}
		
		
		
		
		function fn_selectPrvCnrtExpertListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable2");
			var recordcnt = $("#RECORD_COUNT2").val();
						
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
					divId : "PAGE_NAVI2",
					pageIndex : "PAGE_INDEX2",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtExpertList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp = "";
				
				$.each(data.list, function(key, value){
					       t_str = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";												     
												
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + ((total - value.RNUM)+1)  + "</td>" +						   			                
						   			   "<td>" + 
						   			   "<a href='#this' name='title2' >" + NvlStr(value.EXPERT_NM) + "</a>" +
						   			   "<input type='hidden' name='title2' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + NvlStr(value.RIGHT_NM) + "</td>" +	     
						   			   "<td class=\"list_center_sht\"><font title=\"" + NvlStr(value.MAIN_CAREER) + "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.MAIN_CAREER), 20) + "</font></a></td>" +						   			 			   			  						   			  
						   			   "<td>" + NvlStr(value.BIGO) + "</td>" + 						   			   									   
					                   "</tr>"; 
					                   							      								   						
				});
				body.append(str);
				
				
				$("a[name='title2']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtExpertUpdate($(this));
				});
							
				
			}
		}
		
		function fn_prvCnrtExpertUpdate(obj){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("CON_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("ID","${param.ID}");
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "4");  //실적 1, 평가 2 발주계획 3 보유현황 4
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtExpertUpdate.do' />");
			comSubmit.submit();
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
<input type="hidden" id="US_ID" name="US_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<c:set var="chkAdmin" value="${sessionScope.userinfo.userright}"/> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">업체등록</td>
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
			<li class="Lcurrent" id='tab2'><span><a href="#this" class="btn" id="record">기술능력정보</a></span></li>			
			<li id='tab3'><span><a href="#this" class="btn" id="reason">선정사유</a></span></li>
			<li id='tab4'><span><a href="#this" class="btn" id="eval">부서평가정보</a></span></li>						
			</ul>
			</div>    
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/scms/images/title_ico.gif" align="absmiddle">과업실적</td>
					</tr>
				</table>
				</td>				
			</tr>
			</table>
						    
	        <!-- -------------- 버튼 시작 --------------  -->
	        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td>&nbsp;</td>
	            <td align="right">
	            <table border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td>
	                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
	                  <tr>
	                    <td><img src="/scms/images/btn_type0_head.gif"></td>
	                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write1"><font color="white">등록</font></a></td>
	                    <td><img src="/scms/images/btn_type0_end.gif"></td>
	                  </tr>
	                </table>                
	                </td>		
	              </tr>
	            </table>
	            </td>
	          </tr>
	        </table>
	        <!-- -------------- 버튼 끝 ---------------->	
		

			<table class="tbl1">
			<thead>
		        <tr>
		          <th>&nbsp;</th><th>계약년월</th><th>발주처</th>
		          <th>사업명</th><th>과업내용</th><th>계약금액(원)</th>
		          <th>첨부</th>
		        </tr>
		     </thead>
			 <tbody id='mytable1'>				
			 </tbody>
			</table>
	
		</td>
		</tr>
	</table>

	<p></p>    
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="BID" name="BID" value="${param.ID}">
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="2"/>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/scms/images/title_ico.gif" align="absmiddle">기술자 보유 현황</td>
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
           <td>&nbsp;</td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write2"><font color="white">등록</font></a></td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>		
         </tr>
       </table>
       </td>
     </tr>
    </table>
        <!-- -------------- 버튼 끝 ---------------->
		

	<table class="tbl1">
		<thead>
		        <tr>
		          <th>&nbsp;</th><th>성명</th><th>자격(면허)</th>
		          <th>주요경력</th><th>비고</th>
		        </tr>
		     </thead>
	
		<tbody id='mytable2'>		
		</tbody>
	</table>
			
	<p></p>		
	<div id="PAGE_NAVI2" align='center'></div>
	<input type="hidden" id="PAGE_INDEX2" name="PAGE_INDEX2"/>
	<input type="hidden" id="RECORD_COUNT2" name="RECORD_COUNT2" value="10"/>
	</td>
  </tr>
</table>	
<%@ include file="/WEB-INF/include/include-body.jspf" %>				
</body>
</html>