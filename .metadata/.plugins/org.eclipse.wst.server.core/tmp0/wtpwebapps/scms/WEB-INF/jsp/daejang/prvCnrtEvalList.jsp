<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var sel_fucnm = "";
		$(document).ready(function(){
			
			fn_selectPrvCnrtEvalList(1);
						
			$("#write").on("click", function(e){ //글쓰기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_scEvalWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});	
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_basicinfo();
			});
			
			$("#recode").on("click", function(e){ //
				e.preventDefault();								
				fn_recodeList();				
			});
			
			
			$("#reason").on("click", function(e){ //
				e.preventDefault();								
				fn_reasonList();				
			});
	

			
		});
		
		
		function fn_scEvalWrite(){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/scEvalWrite.do' />");
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.submit();
		}
		
		

		function fn_prvCnrtEvalUpdate(obj){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("CON_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("ID","${param.ID}");
			comSubmit.addParam("PARENT_ID",obj.parent().find("#UID").val());
			comSubmit.addParam("BOARD_GB", "2");  //실적 1, 평가 2 발주계획 3
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtEvalUpdate.do' />");
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
		
		function fn_recodeList(){
			var comSubmit = new ComSubmit();
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");
			comSubmit.submit();
			
		}

		
		function fn_reasonList(){
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtReasonList.do' />");
			comSubmit.submit();
		}
		
	
		function fn_sesiondelete(){
			alert('bb');
			$.session.clear();
			alert('cc');
		}
		
		
		
		function fn_selectPrvCnrtEvalList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
		
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtEvalList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtEvalListCallback");
			comAjax.addParam("COMP_ID","${param.ID}");
			comAjax.addParam("DUTY_GB","3");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());		
			comAjax.ajax();
		}
		
		function fn_selectPrvCnrtEvalListCallback(data){		
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
					eventName : "fn_selectPrvCnrtEvalList"
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
						   			   "<td class=\"list_center_sht\">" + 
						   			   "<a href='#this' name='title' ><font title=\"" + NvlStr(value.CNRT_NM) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.CNRT_NM), 18) + "</font></a>" +
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "</td>" +
						   			   "<td>" + dv_yrmonth(NvlStr(value.CNRT_YEAR), '.') +  "</td>" +
						   			   "<td>" + comma(NvlStr(value.CNRT_AMT)) + "</td>" + 	
						   			   "<td>" + NvlStr(value.CNRT_DEPT_NM) + "</td>" +
						   			   "<td>" + out_eval(NvlStr(value.CNRT_EVAL)) + "</td>" +
						   			   "<td>" + tmp + "</td>" +						   			  					   			   									  
					                   "</tr>";					                   						
				});
				body.append(str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtEvalUpdate($(this));
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
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">수의계약 관리시스템</td>
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
			<li id='tab2'><span><a href="#this" class="btn" id="recode">기술능력정보</a></span></li>			
			<li id='tab3'><span><a href="#this" class="btn" id="reason">선정사유</a></span></li>
			<li class="Lcurrent" id='tab4'><span><a href="#this" class="btn" id="eval">부서평가정보</a></span></li>						
			</ul>
			</div>    
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/scms/images/title_ico.gif" align="absmiddle">만족도 평가</td>
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
	                    <td>&nbsp;</td>
	                    <td>&nbsp;</td>
	                    <td>&nbsp;</td>
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
		          <th>&nbsp;</th><th>과업명</th><th>과업년월</th><th>계약금액(원)</th>
		          <th>소관부서</th><th>평가결과</th><th>체크리스트</th>		             
		        </tr>
		     </thead>
			 <tbody id='mytable'>				
			 </tbody>
			</table>	
		</td>
		</tr>
	</table>
	</td>	
  </tr>
</table>
<div id="PAGE_NAVI" align='center'></div>
<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>
	
<%@ include file="/WEB-INF/include/include-body.jspf" %>				
</body>
</html>