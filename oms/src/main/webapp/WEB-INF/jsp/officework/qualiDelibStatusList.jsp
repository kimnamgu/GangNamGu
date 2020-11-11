<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		$(document).ready(function(){
			fn_selectBoardList();
			fn_selectBoardList2();
			
			$("#write1").on("click", function(e){ //글쓰기 버튼
				
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_qualiDelibStatusWrite();											
				}
				else{
					alert('권한이 없습니다.');
				}
			});	
			
			$("#write2").on("click", function(e){ //글쓰기 버튼
								
				if( $('#SAUP_DEPT').val() == $('#LOGIN_DEPT').val() || $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_guAssembAgreementWrite();										
				}
				else{
					alert('권한이 없습니다.');
				}
			});	
			
			$("#basic").on("click", function(e){ //위탁사무기본현황
				e.preventDefault();				
				fn_officeworkBasicDetail();
			});	
			
			
			$("#sutak").on("click", function(e){ //수정하기 버튼
				e.preventDefault();
				fn_consignStatusList();
			});
			
			
			$("#mng").on("click", function(e){ //운영현황
				e.preventDefault();				
				fn_superviseStatusList();
			});
			
			$("#delib").on("click", function(e){ //심의현황
				e.preventDefault();				
				fn_qualiDelibStatusList();
			});
			
		});
		
		
		function fn_qualiDelibStatusUpdate(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusUpdate.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_guAssembAgreementUpdate(obj){			
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/guAssembAgreementUpdate.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_qualiDelibStatusWrite(){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusWrite.do' />");
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.addParam("WDID", $('#WRITE_DEPT').val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		
		function fn_guAssembAgreementWrite(){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/officework/guAssembAgreementWrite.do' />");
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.addParam("WDID", $('#WRITE_DEPT').val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		
		
		function fn_officeworkBasicDetail(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/officework/officeworkBasicDetail.do' />");
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.submit();
		}
		
		function fn_consignStatusList(){
			var comSubmit = new ComSubmit();
		
			comSubmit.setUrl("<c:url value='/officework/consignStatusList.do' />");		
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.addParam("WDID", $('#WRITE_DEPT').val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_superviseStatusList(obj){
			var comSubmit = new ComSubmit();		
			
			comSubmit.setUrl("<c:url value='/officework/superviseStatusList.do' />");			
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.addParam("WDID", $('#WRITE_DEPT').val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_qualiDelibStatusList(obj){			
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/officework/qualiDelibStatusList.do' />");			
			comSubmit.addParam("ID", $("#BID").val());
			comSubmit.addParam("WDID", $('#WRITE_DEPT').val());
			comSubmit.addParam("SAUP_DEPT", $('#SAUP_DEPT').val());
			comSubmit.submit();
		}
		
		function fn_selectBoardList(){			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/officework/selectqualiDelibStatusList.do' />");
			comAjax.setCallback("fn_selectBoardListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", 20);
			//alert($("#BID").val());
			comAjax.addParam("OW_ID", $("#BID").val());
			comAjax.ajax();
		}
		
		function fn_selectBoardList2(){			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/officework/selectguAssembAgreementList.do' />");
			comAjax.setCallback("fn_selectBoardListCallback2");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", 20);
			//alert($("#BID").val());
			comAjax.addParam("OW_ID", $("#BID").val());
			comAjax.ajax();
		}
		
		function fn_sesiondelete(){
			alert('bb');
			$.session.clear();
			alert('cc');
		}
				
		function fn_selectBoardListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable1");
			
			body.empty();
			
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"5\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"5\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,					
					eventName : "fn_selectBoardList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				$.each(data.list, function(key, value){
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\"'>" + 
									   "<a href='#this' name='title' >" + formatDate(value.QD_ILJA, ".")  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" +				
					                   "<td class=\"list\" nowrap>" + value.QD_CONTENTS + "</td>" + 									   					  
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.QD_RESULT) +  "</td>" +
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.QD_BIGO) +  "</td>" +									 
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"5\" class=\"list_line\"></td>" + 
									   "</tr>";
							
							
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_qualiDelibStatusUpdate($(this));
				});
			}
		}
		
		
		function fn_selectBoardListCallback2(data){			
			var total = data.TOTAL;			
			var body = $("#mytable2");
			
			body.empty();
			
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"4\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"4\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI2",
					pageIndex : "PAGE_INDEX",
					totalCount : total,					
					eventName : "fn_selectBoardList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				$.each(data.list, function(key, value){
												
							str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					                   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					                   "<td class=\"list_center\"'>" + 
									   "<a href='#this' name='title2' >" + formatDate(value.GA_ILJA, ".") + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" +
					                   "<td class=\"list\" nowrap>" + NvlStr(value.GA_RESULT) + "</td>" + 									   									   
									   "<td class=\"list_center\" nowrap>" + NvlStr(value.GA_BIGO) +  "</td>" +									  									
									   "</tr>" +
									   "<tr>" + 
								       "<td colspan=\"4\" class=\"list_line\"></td>" + 
									   "</tr>";											
									   
									   
				});
				body.append(str);
				
				$("a[name='title2']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_guAssembAgreementUpdate($(this));
				});
			}
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${param.WDID}">
<input type="hidden" id="SAUP_DEPT" name="SAUP_DEPT" value="${param.SAUP_DEPT}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

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

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
        
	        <div class="LblockTab" style="position: relative;">
			<ul class="Lclear">
				<li id='property'><span><a href="#this" class="btn" id="basic">위탁사무기본현황</a></span></li>
				<li id='maindoc1'><span><a href="#this" class="btn" id="sutak">수탁·예산현황</a></span></li>			
				<li id='public'><span><a href="#this" class="btn" id="mng">운영현황</a></span></li>	
				<li class="Lcurrent" id='manage'><span><a href="#a" onclick="onTabChange('manage')">심의현황</a></span></li>			
			</ul>
			</div>    
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">적격자 심의위원회 심의현황</td>
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
	                    <td><img src="/oms/images/btn_type0_head.gif"></td>
	                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write1"><font color="white">등록</font></a></td>
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
					<td class="list_tit_line_s" width="150"></td>
					<td class="list_tit_line_s" width="100%"></td>
					<td class="list_tit_line_s" width="150"></td>				
					<td class="list_tit_line_s" width="150"></td>							
				</tr>
				<tr class="list_tit_tr">
					<td nowrap class="list_tit">번호</td>
					<td nowrap class="list_tit_bar">년월일</td>
					<td nowrap class="list_tit_bar">심의내용</td>
					<td nowrap class="list_tit_bar">심의결과</td>
					<td nowrap class="list_tit_bar">비고</td>					
				</tr>
				<tr>
					<td colspan="5" class="list_tit_line_e"></td>
				</tr>
			
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
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
	
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">구의회 동의현황</td>
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
               <td><img src="/oms/images/btn_type0_head.gif"></td>
               <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write2"><font color="white">등록</font></a></td>
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
			<td class="list_tit_line_s" width="150"></td>
			<td class="list_tit_line_s" width="100%"></td>
			<td class="list_tit_line_s" width="150"></td>											
		</tr>
		<tr class="list_tit_tr">
			<td nowrap class="list_tit">번호</td>
			<td nowrap class="list_tit_bar">일자</td>
			<td nowrap class="list_tit_bar">결과</td>
			<td nowrap class="list_tit_bar">비고</td>			
		</tr>
		<tr>
			<td colspan="4" class="list_tit_line_e"></td>
		</tr>
		<tbody id='mytable2'>
		
		</tbody>
	</table>
			
	<p></p>		
	<div id="PAGE_NAVI2" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	
	</td>
  </tr>
</table>	
					
</body>
</html>