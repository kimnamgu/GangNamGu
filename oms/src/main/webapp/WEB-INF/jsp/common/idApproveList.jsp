<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var id_list;
		
		$(document).ready(function(){
			fn_selectOfficeworkRegList(1);
			
			$("#approve").on("click", function(e){ //글쓰기 버튼
				id_list = "";
				e.preventDefault();				
				fn_officeworkRegWrite();
			});
			
			$("#all").on("click", function(e){ 
				if($("input[name=chkBox]:checkbox").is(":checked"))
					$("input[name=chkBox]:checkbox").attr("checked", false);
				else					
					$("input[name=chkBox]:checkbox").attr("checked", true);
			});
		
		});
		
		
		function fn_officeworkRegWrite(){
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
				comSubmit.setUrl("<c:url value='/common/updateIdApproveList.do' />");
				comSubmit.submit();
			}
		}
		
		
		
		function fn_selectOfficeworkRegList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/common/selectIdApproveList.do' />");
			comAjax.setCallback("fn_selectOfficeworkRegListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("OW_DEPT_CD", '');
			else
				comAjax.addParam("OW_DEPT_CD", $("#DEPT_ID").val());
		
			comAjax.ajax();
		}
		
		
		function fn_selectOfficeworkRegListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
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
					eventName : "fn_selectOfficeworkRegList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var ow_type1 = "";
				var ow_type2 = "";
				var ow_type3 = "";				
				$.each(data.list, function(key, value){
					
					ow_type1 = "";
					if (value.OW_TYPE1 == "01")
						ow_type1 = "민간위탁";
					else if (value.OW_TYPE1 == "02")
						ow_type1 = "용역";
					else if (value.OW_TYPE1 == "03")
						ow_type1 = "대행·공공위탁";
					else if (value.OW_TYPE1 == "04")
						ow_type1 = "연구용역";
				 
				
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\" nowrap><input type='checkbox' id='chkid' name='chkBox' value='" +  value.USER_ID + "'></td>" +
					                   "<td class=\"list_center\" nowrap>" + value.USER_ID + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + value.USER_NAME + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + value.DEPT_NAME + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + value.CLASS_NAME  + "</td>" +
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.USER_TEL) +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.APPLY_REASON) +  "</td>" +									   
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"8\" class=\"list_line\"></td>" + 
									   "</tr>";
														
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_officeworkBasicDetail($(this));
				});
			}
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="ID_LIST" name="ID_LIST" value="">
<!--  아이디 : ${sessionScope.userinfo.userId}  이름 : ${sessionScope.userinfo.userName}<br>  -->
 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">사용 승인</td>
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
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/oms/images/btn_type0_head.gif"></td>
               <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="approve"><font color="white">승인</font></a></td>
               <td><img src="/oms/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>		
         </tr>
       </table>
       </td>
     </tr>
   </table>
   <!-- -------------- 버튼 끝 ---------------->	
    	

		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="48"></td>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="160"></td>
				<td class="list_tit_line_s" width="110"></td>				
				<td class="list_tit_line_s" width="100%"></td>								
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit">번호</td>
				<td nowrap class="list_tit_bar"><a href="#this" class="btn" id="all"><u>all</u></a></td>
				<td nowrap class="list_tit_bar">아이디</td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">전화번호</td>
				<td nowrap class="list_tit_bar">신청사유</td>			
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
		
</body>
</html>