<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var id_list;
		var dgb = "${param.USER_STATUS}";
		
		$(document).ready(function(){
			fn_idApproveList(1);
			
			fn_initial_setting();
			
			$("#approve").on("click", function(e){ //글쓰기 버튼
				id_list = "";
				e.preventDefault();				
				fn_updateIdApprove();
			});
			
			$("#list").on("click", function(e){ //리스트				
				e.preventDefault();				
				fn_fixedDateList();
			});
			
			$("#all").on("click", function(e){ 
				if($("input[name=chkBox]:checkbox").is(":checked"))
					$("input[name=chkBox]:checkbox").attr("checked", false);
				else					
					$("input[name=chkBox]:checkbox").attr("checked", true);
			});
			
			
			$("#srch").on("click", function(e){ //조회버튼
				
				e.preventDefault();				
				fn_idApproveList();
			});
			
			$("input:radio[name=USER_STATUS]").click(function(){
				dgb = "";
				$("#PAGE_INDEX").val("1");
				fn_idApproveList();
			});
		
		});
		
		function fn_initial_setting(){
			
			if(dgb == "" || dgb == null)
				$('input:radio[value=Z]').prop("checked", true);
			else				
				$('input:radio[value=' + dgb + ']').prop("checked", true);
			
		}
		
		function fn_updateIdApprove(){
			var comSubmit = new ComSubmit("form1");

			//var tcnt = $("#chkid:checked").length;
			
			$('#chkid:checked').each(function() { 
		        if(id_list != "")
		        	id_list = id_list + " , " + "'" + $(this).val() + "'";
		        else	
					id_list = "'" +  $(this).val() + "'";
		   });

			$('#ID_LIST').val(id_list); 
			
			if(confirm('선택한 아이디를 사용승인 하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/common/updateIdApprove.do' />");
				comSubmit.submit();
			}
		}
		
		
		
		function fn_idApproveList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			var tmp = "";
			
			comAjax.setUrl("<c:url value='/common/selectIdApproveList.do' />");
			comAjax.setCallback("fn_selectIdApproveListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			
		
			tmp = $('input:radio[name=USER_STATUS]:checked').val();
			
			if(dgb == "" || dgb == null)
			{
				if( typeof tmp == 'undefined')
					tmp = "Z";
			}
			else{
				tmp = dgb;
			}
			
			comAjax.addParam("USER_STATUS", tmp);		
			comAjax.ajax();
		}
		
		
		function fn_selectIdApproveListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
								"<td colspan=\"8\">조회된 결과가 없습니다.</td>" +
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_idApproveList"
				};
				gfn_renderPaging(params);
				
				var str = "";
							
				$.each(data.list, function(key, value){
					
				
												
							str += "<tr>" +
					                   "<td>" + value.RNUM + "</td>" +
					                   "<td><input type='checkbox' id='chkid' name='chkBox' value='" +  value.USER_ID + "'></td>" +
					                   "<td class=\"list_center\"'>" + 
									   "<a href='#this' name='title' >" + NvlStr(value.USER_ID) + "</a>" + 									  
									   "<input type='hidden' name='title' id='UID' value=" + value.USER_ID + ">" + 
									   "</td>" +					                   
					                   "<td>" + value.USER_NAME + "</td>" + 
					                   "<td>" + value.DEPT_NAME + "</td>" + 
					                   "<td>" + value.CLASS_NAME  + "</td>" +
									   "<td>" + NvlStr(value.USER_TEL) +  "</td>" +
									   "<td>" + NvlStr(value.APPLY_REASON) +  "</td>" +									   
									   "</tr>";
														
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_idApproveListDetail($(this));
				});
			}
		}
		
		
		
		function fn_fixedDateList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/manage/fixedDateList.do' />");			
			comSubmit.submit();			
		}	
			
		function fn_idApproveListDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/idApproveListDetail.do' />");
			comSubmit.addParam("USER_ID", obj.parent().find("#UID").val());
			comSubmit.addParam("USER_STATUS", $('input:radio[name=USER_STATUS]:checked').val());
			comSubmit.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="ID_LIST" name="ID_LIST" value="">


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/fds/images/popup_title.png" align="absmiddle">사용자 승인</td>
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
    
    
   <table width="720" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
	  <tr>
           <td width="100" class="tbl_field">구분</td>
           <td colspan="3" width="660" class="tbl_list_left">           	                 
               <input type="radio" id="USER_STATUS1" name="USER_STATUS" value="Z" class="input" onfocus="this.blur()"> 미승인 &nbsp; <input type="radio" id="USER_STATUS2" name="USER_STATUS" value="0" class="input" onfocus="this.blur()"> 승인                	                	               
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
               <td background="/fds/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">사용자화면</a></td>
               <td><img src="/fds/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/fds/images/btn_type0_head.gif"></td>
               <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="approve"><font color="white">승인</font></a></td>
               <td><img src="/fds/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/fds/images/btn_type0_head.gif"></td>
               <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="srch"><font color="white">조회</font></a></td>
               <td><img src="/fds/images/btn_type0_end.gif"></td>
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
				<th>번호</th>
				<th><div id="all"><u>all</u></div></th>
				<th>아이디</th>
				<th>이름</th>
				<th>부서</th>
				<th>직급</th>
				<th>전화번호</th>
				<th>신청사유</th>			
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