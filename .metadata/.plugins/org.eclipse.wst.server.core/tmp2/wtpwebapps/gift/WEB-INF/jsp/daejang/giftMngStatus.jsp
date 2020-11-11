<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var sel_fucnm = "";
		$(document).ready(function(){
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				
				/*if( $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{*/	
					e.preventDefault();				
					fn_giftMngWrite();
				/*}
				else{
					alert('권한이 없습니다.');
				}*/
				
			});	
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_giftAcceptList();
			});
			
			$("#mast").on("click", function(e){ //위탁사무기본현황
				e.preventDefault();				
				fn_giftAcceptDetail();
			});	
			
			
			$("#sell").on("click", function(e){ // 매각대장
				e.preventDefault();				
				fn_giftSellStatus();
			});
			
		});
		
		
		function fn_giftMngWrite(){			
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/giftMngWrite.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.submit();
		}
		
		function fn_giftAcceptList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_giftAcceptDetail(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptDetail.do' />");
			comSubmit.addParam("ID", $("#GF_ID").val());
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}

		
		function fn_giftSellStatus(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftSellStatus.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());			
			comSubmit.submit();
		}
		
		
		function fn_budgetStatusUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/officework/budgetStatusUpdate.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());			
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${param.WID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="GF_ID" name="GF_ID" value="${param.MID}">
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

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
        
	        <div class="LblockTab" style="position: relative;">
			<ul class="Lclear">
				<li id='property'><span><a href="#this" class="btn" id="mast">접수대장</a></span></li>
				<li class="Lcurrent" id='maindoc1'><span><a href="#this" class="btn" id="Mng">관리대장</a></span></li>			
				<li id='public'><span><a href="#this" class="btn" id="sell">매각대장</a></span></li>						
			</ul>
			</div>    
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">평가결과</td>
					</tr>
				</table>
				</td>				
			</tr>
			</table>
			
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
	                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
	                  <tr>
	                    <td><img src="/gift/images/btn_type0_head.gif"></td>
	                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록/수정</font></a></td>
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
					<td class="list_tit_line_s" width="100%"></td>
					<td class="list_tit_line_s" width="150"></td>
					<td class="list_tit_line_s" width="100"></td>					
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="100"></td>
					<td class="list_tit_line_s" width="150"></td>												
				</tr>
				<tr class="list_tit_tr">
					<td nowrap class="list_tit">선물명</td>
					<td nowrap class="list_tit_bar">규격</td>
					<td nowrap class="list_tit_bar">신고인</td>
					<td nowrap class="list_tit_bar">평가일시</td>
					<td nowrap class="list_tit_bar">평가결과</td>
					<td nowrap class="list_tit_bar">추정가격</td>
					<td nowrap class="list_tit_bar">부서지정</td>							
				</tr>
				<tr>
					<td colspan="7" class="list_tit_line_e"></td>
				</tr>
			
				<tr class="list1" onmouseover="this.className='list_over'" onmouseout="this.className='list1'">
				<td class="list_center" nowrap>${map.GIFT_ITEM_NM}</td>
				<td class="list_center" nowrap>${map.GIFT_STND}</td>
				<td class="list_center" nowrap>${map.TAKE_PSN_NM}</td>
				<td class="list_center" nowrap><fmt:parseDate value="${map.EVAL_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td class="list_center">				
				<c:set var="val" value="${map.EVAL_STATUS}" />
				<c:choose>
			    <c:when test="${val eq '1'}">
		                   신고
		    	</c:when>
			    <c:when test="${val eq '2'}">
		                   반려
		    	</c:when>
		    	</c:choose>				
				</td>
				<td class="list_center" nowrap><fmt:formatNumber value="${map.EVAL_AMT}" pattern="###,###,###" /></td>
				<td class="list_center" nowrap>
				<c:set var="val" value="${map.DATA_STATUS}" />
				<c:choose>
			    <c:when test="${val eq 'J'}">
		                   재무과
		    	</c:when>
			    <c:when test="${val eq 'C'}">
		                  총무과
		    	</c:when>
		    	</c:choose>
			    </td>
				</tr>
				
				<tr> 
					<td colspan="7" class="list_line"></td> 
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
	                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
	                  <tr>
	                    <td><img src="/gift/images/btn_type1_head.gif"></td>
	                    <td background="/gift/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
	                    <td><img src="/gift/images/btn_type1_end.gif"></td>
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
	        <!-- -------------- 버튼 끝 --------------  -->
		</td>
		</tr>
	</table>
	</td>
  </tr>
</table>	
<%@ include file="/WEB-INF/include/include-body.jspf" %>					
</body>
</html>