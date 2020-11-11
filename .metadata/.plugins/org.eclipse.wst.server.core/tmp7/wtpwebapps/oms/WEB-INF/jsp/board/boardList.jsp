<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
	$(document).ready(function(){
		fn_selectBoardList(1);
		
		$("#write").on("click", function(e){ //글쓰기 버튼
			e.preventDefault();
			fn_openBoardWrite();
		});	
		
	});
	
	
	function fn_openBoardWrite(){
		var comSubmit = new ComSubmit();
		var user_right =  $("#USER_RIGHT").val();
		
		if(user_right == 'A'){
			comSubmit.setUrl("<c:url value='/board/openBoardWrite.do' />");
			comSubmit.addParam("BDID", "${param.bdId}");
			comSubmit.submit();
		}
		else{
			alert('권한이 없습니다.');
		}
	}
	
	function fn_openBoardDetail(obj){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
		comSubmit.addParam("ID", obj.parent().find("#ID").val());		
		comSubmit.addParam("BDID", "${param.bdId}");
		comSubmit.submit();
	}
	
	function fn_selectBoardList(pageNo){			
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/board/selectBoardList.do' />");
		comAjax.setCallback("fn_selectBoardListCallback");
		comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
		//alert($("#PAGE_INDEX").val());
		comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
		comAjax.addParam("BOARD_ID", "${param.bdId}");
		//alert($("#RECORD_COUNT").val());
		comAjax.ajax();
	}
	
	function fn_selectBoardListCallback(data){	
		var total = data.TOTAL;
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		body.empty();
		if(total == 0){
			var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
					  "<td colspan=\"7\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
					  "</tr>" +
					  "<tr>" + 
						    "<td colspan=\"7\" class=\"list_line\"></td>" + 
					  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectBoardList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var attach = "";
			
			$.each(data.list, function(key, value){
				
				attach = "";
				if(NvlStr(value.ATTACH) == 'Y')
					attach = "<img src=\"/oms/images/ico_att.gif\" align=\"absmiddle\">";
								
						str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
				                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" + 
				                   "<td class=\"list_center\" nowrap>" + value.ID + "</td>" + 
								   "<td class=\"list\"'>" + 
								   "<a href='#this' name='title' >" + value.TITLE  + "</a>" + 									  
								   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" +								  						 
								   "</td>" +
								   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.INS_DATE), ".") +  "</td>" +
								   "<td class=\"list_center\" nowrap>" + value.USER_NAME +  "</td>" +
								   "<td class=\"list_center\" nowrap style=\"text-overflow: ellipsis; overflow: hidden;\">" + attach + "</td>" + 
								   "<td class=\"list_center\" nowrap style=\"text-overflow: ellipsis; overflow: hidden;\">" + value.HIT_CNT + "</td>" +								   			
								   "</tr>" +
								   "<tr>" + 
							       "<td colspan=\"7\" class=\"list_line\"></td>" + 
								   "</tr>";
						
						
			});
			body.append(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				e.preventDefault();
				fn_openBoardDetail($(this));
			});
		}
	}
	</script>
</head>
<body bgcolor="#FFFFFF">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">
        <c:set var="bdId" value="${param.bdId}" />
		<c:choose>
	    <c:when test="${bdId eq '1'}">
                   자료실
    	</c:when>
	    <c:when test="${bdId eq '2'}">
                  공지사항
    	</c:when>
    	<c:otherwise>
	    </c:otherwise>
		</c:choose>
        </td>
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
               <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
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
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="50"></td>							
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="100"></td>
				<td class="list_tit_line_s" width="100"></td>			
				<td class="list_tit_line_s" width="100"></td>
				<td class="list_tit_line_s" width="100"></td>								
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit">번호</td>
				<td nowrap class="list_tit_bar">ID</td>
				<td nowrap class="list_tit_bar">제목</td>
				<td nowrap class="list_tit_bar">작성일</td>
				<td nowrap class="list_tit_bar">작성자</td>
				<td nowrap class="list_tit_bar">파일</td>
				<td nowrap class="list_tit_bar">조회수</td>				
			</tr>
			<tr>
				<td colspan="7" class="list_tit_line_e"></td>
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