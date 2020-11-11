<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		$(document).ready(function(){
			
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				fn_giftAcceptWrite();
			});
			
			
			$("a[name='title']").on("click", function(e){ //제목 
				e.preventDefault();
				fn_giftAcceptDetail($(this));
			});
		
		});
		
		
	</script>
</head>
<body bgcolor="#FFFFFF">
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
        <td class="pupup_title"><img src="/gift/images/popup_title.gif" align="absmiddle">선물접수대장</td>
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
    
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="80"></td>								
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">소속</td>
				<td nowrap class="list_tit_bar">직위</td>
				<td nowrap class="list_tit_bar">성명</td>
				<td nowrap class="list_tit_bar">선물품명</td>
				<td nowrap class="list_tit_bar">규격</td>	
			</tr>
			<tr>
				<td colspan="6" class="list_tit_line_e"></td>
			</tr>
		
			<tr class="list1" onmouseover="this.className='list_over'" onmouseout="this.className='list1'">
			   <td class="list_center" nowrap></td> 
	                 <td class="list_center" nowrap></td>
	                 <td class="list_center" nowrap></td>
	                 <td class="list_center" nowrap></td>
	                 <td class="list_center" nowrap></td>
	                 <td class="list_center" nowrap></td>													
			   </tr>
			 <tr> 
		       <td colspan="6" class="list_line"></td>
			 </tr>
		
	</table>
	
	</td>
	</tr>
</table>

		
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
		
</body>
</html>