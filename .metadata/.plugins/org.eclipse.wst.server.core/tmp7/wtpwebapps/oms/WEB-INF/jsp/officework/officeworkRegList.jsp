<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		$(document).ready(function(){
			fn_selectOfficeworkRegList(1);
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				fn_officeworkRegWrite();
			});
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();				
				$("#PAGE_INDEX").val("1");
				fn_selectOfficeworkRegList();
				
			});
			
			$("#OW_DEPT_NM").keypress(function (e) {
		        if (e.which == 13){
		        	fn_selectOfficeworkRegList();  // 실행할 이벤트
		        }
		    });
			
			$("#OW_NAME").keypress(function (e) {
		        if (e.which == 13){
		        	fn_selectOfficeworkRegList();  // 실행할 이벤트
		        }
		    });
		
		});
		
		
		function fn_officeworkRegWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_officeworkBasicDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());			
			comSubmit.submit();
		}
		
		function fn_selectOfficeworkRegList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/officework/selectOfficeworkRegList.do' />");
			comAjax.setCallback("fn_selectOfficeworkRegListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("OW_NAME",$("#OW_NAME").val());
			comAjax.addParam("OW_DEPT_NM",$("#OW_DEPT_NM").val());
			
			if(user_right == 'A') //관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("OW_DEPT_CD", '');
			else
				comAjax.addParam("OW_DEPT_CD", $("#DEPT_ID").val());
		
			comAjax.ajax();
		}
		
		function fn_sesiondelete(){
			alert('bb');
			$.session.clear();
		}
		
		function fn_selectOfficeworkRegListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(total);
			
			body.empty();
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"9\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"9\" class=\"list_line\"></td>" + 
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
				 
					ow_type2 = "";
					if (value.OW_TYPE2 == "01")
						ow_type2 = "사무";
					else if (value.OW_TYPE2 == "02")
						ow_type2 = "시설";
					
					ow_type3 = "";
					if (value.OW_TYPE3 == "01")
						ow_type3 = "수익창출형";
					else if (value.OW_TYPE3 == "02")
						ow_type3 = "예산지원형";
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\" nowrap>" + ow_type1 + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + ow_type2 + "</td>" + 
					                   "<td class=\"list_center\" nowrap>" + ow_type3 + "</td>" + 
									   "<td class=\"list_left\"'>" + 
									   "<a href='#this' name='title' >" + value.OW_NAME  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" +									  
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.OW_DEPT_NM) +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.CS_INST_NAME) +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.FIRST_TRUST_DATE), ".") +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + formatDate(NvlStr(value.CUR_TRUST_STDATE),".") + " ~ " + formatDate(NvlStr(value.CUR_TRUST_EDDATE),".") + "</td>" +
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"9\" class=\"list_line\"></td>" + 
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
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<!--  아이디 : ${sessionScope.userinfo.userId}  이름 : ${sessionScope.userinfo.userName}  rank : ${sessionScope.userinfo.deptRank}<br>  -->
 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">위탁사무등록</td>
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
    
    
    
    <table width="720" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	         
			  <tr>
	            <td width="100" class="tbl_field">담당부서</td>
	            <td colspan="3" width="660" class="tbl_list_left">	               
	                <input type="text" id="OW_DEPT_NM" name="OW_DEPT_NM" class="input" style="width:300;">	                          
	            </td>
	          </tr>			  
			  <tr>
	            <td width="100" class="tbl_field">위탁사무명</td>
	            <td colspan="3" width="260" class="tbl_list_left">
	                <input type="text" id="OW_NAME" name="OW_NAME" class="input" style="width:180;">	                
	            </td>	           
	          </tr>
    	</table>
    
    
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td align="left"><b>Total : <span id="tcnt"></span></b></td>
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
          <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/oms/images/btn_type1_head.gif"></td>
               <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search">검색</a></td>
               <td><img src="/oms/images/btn_type1_end.gif"></td>
             </tr>
           </table>
           </td>		
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
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="100"></td>				
				<td class="list_tit_line_s" width="140"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="150"></td>				
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit">번호</td>
				<td nowrap class="list_tit_bar">위탁유형</td>
				<td nowrap class="list_tit_bar">위탁유형2</td>
				<td nowrap class="list_tit_bar">위탁유형3</td>
				<td nowrap class="list_tit_bar">위탁사무명</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">수탁기관명</td>	
				<td nowrap class="list_tit_bar">최초위탁일</td>
				<td nowrap class="list_tit_bar">위탁기간</td>
			</tr>
			<tr>
				<td colspan="9" class="list_tit_line_e"></td>
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