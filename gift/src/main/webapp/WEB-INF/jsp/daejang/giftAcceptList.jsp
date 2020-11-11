<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		$(document).ready(function(){
			fn_selectGiftAcceptList(1);
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				fn_giftAcceptWrite();
			});
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});
			
			
			$("#logout").on("click", function(e){ //로그아웃
				e.preventDefault();
				fn_logout();
			});
		
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
		
		function fn_logout(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
		function fn_giftAcceptWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_printList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftPrintList.do' />");
			comSubmit.submit();
		}
		
		function fn_giftAcceptDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}
		
		function fn_selectGiftAcceptList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectgiftAcceptList.do' />");
			comAjax.setCallback("fn_selectgiftAcceptListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
		
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("TRNSF_DEPT_GB", '');
			else
				comAjax.addParam("TRNSF_DEPT_GB", user_right);
		
			comAjax.ajax();
		}
		
		
		function fn_selectgiftAcceptListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			body.empty();
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"12\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"12\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectGiftAcceptList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var ow_type1 = "";
				var ow_type2 = "";
				var ow_type3 = "";
				$.each(data.list, function(key, value){
					
				
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
									   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + value.TAKE_DEPT_NM + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + value.TAKE_PSN_CLASS_NM + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + value.TAKE_PSN_NM + "</td>" +
					                   "<td class=\"list_left\"'>" + 
									   "<a href='#this' name='title' >" + value.GIFT_ITEM_NM  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" +					                  					                
					                   "<td class=\"list_center\" nowrap>" + value.GIFT_STND + "</td>" + 									 
									   "<td class=\"list_center\" nowrap>" + comma(NvlStr(value.GIFT_NUMBER)) +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + value.GIVE_PSN_NATION_NM +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + value.GIVE_PSN_CLASS_NM +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + value.GIVE_PSN_NM + "</td>" +
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.GIFT_TAKE_DATE), ".") + "</td>" +
									   "<td class=\"list_center\" nowrap>" + value.GIFT_TAKE_PLACE + "</td>" +													
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"12\" class=\"list_line\"></td>" + 
									   "</tr>";
							
							
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_giftAcceptDetail($(this));
				});
			}
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
<br></br>
 <table border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td>&nbsp; <부서> : ${sessionScope.userinfo.deptName}  <이름> : ${sessionScope.userinfo.userName}</td>
    <td>&nbsp;&nbsp; </td>           
    <td>
    <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
      <tr>
        <td><img src="/gift/images/btn_type0_head.gif"></td>
        <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="logout"><font color="white">로그아웃</font></a></td>
        <td><img src="/gift/images/btn_type0_end.gif"></td>
      </tr>
    </table>                
    </td>		
  </tr>
 </table>
 

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
    
    <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
	<c:choose>
	 <c:when test="${us_rt eq 'A'}">
                       
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
             <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/gift/images/btn_type1_head.gif"></td>
                    <td background="/gift/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="print">프린트</a></td>
                    <td><img src="/gift/images/btn_type1_end.gif"></td>
                  </tr>
             </table>          
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/gift/images/btn_type0_head.gif"></td>
               <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
               <td><img src="/gift/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>		
         </tr>
       </table>
       </td>
     </tr>
   </table>
   <!-- -------------- 버튼 끝 ---------------->	
   
     </c:when>	      	
    </c:choose> 	

		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="120"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="80"></td>				
				<td class="list_tit_line_s" width="50"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="70"></td>
				<td class="list_tit_line_s" width="80"></td>				
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">소속</td>
				<td nowrap class="list_tit_bar">직위</td>
				<td nowrap class="list_tit_bar">성명</td>
				<td nowrap class="list_tit_bar">선물품명</td>
				<td nowrap class="list_tit_bar">규격</td>	
				<td nowrap class="list_tit_bar">수량</td>
				<td nowrap class="list_tit_bar">국명</td>
				<td nowrap class="list_tit_bar">직위</td>
				<td nowrap class="list_tit_bar">성명</td>
				<td nowrap class="list_tit_bar">수령일</td>
				<td nowrap class="list_tit_bar">수령장소</td>	
			</tr>
			<tr>
				<td colspan="12" class="list_tit_line_e"></td>
			</tr>
		
		<tbody id='mytable'>
			
		</tbody>
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