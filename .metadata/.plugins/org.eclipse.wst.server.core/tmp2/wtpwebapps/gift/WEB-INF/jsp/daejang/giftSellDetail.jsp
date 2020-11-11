<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>선물접수대장</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		
		$(document).ready(function(){
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				//fn_giftSellStatus();
				fn_giftAcceptList();
			});
			
			$("#write").on("click", function(e){ //등록 버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#USER_RIGHT').val() == 'J')
				{				
					e.preventDefault();				
					fn_insertGiftSell();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			$("#mast").on("click", function(e){ //선물접수
				e.preventDefault();				
				fn_giftAcceptDetail();
			});	
		
			
			$("#del").on("click", function(e){ //삭제			
				if( $('#USER_RIGHT').val() == 'A' || $('#USER_RIGHT').val() == 'J')
				{				
					e.preventDefault();				
					fn_deleteGiftSell();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			$('input:radio[name=SELL_METHOD_GB]:input[value=' + "${map.SELL_METHOD_GB}" + ']').attr("checked", true);
		
		});
		
		
		function fn_giftAcceptList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptList.do' />");
			comSubmit.submit();
		}
		
		function fn_giftSellStatus(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftSellStatus.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());
			comSubmit.addParam("WID", $("#WRITE_ID").val());
			comSubmit.submit();
		}
		
		
		function fn_giftAcceptDetail(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptDetail.do' />");
			comSubmit.addParam("ID", $("#GF_ID").val());
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}
				
		function fn_insertGiftSell(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/giftSellWrite.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.addParam("GIFT_NM", "${param.GIFT_NM}");
			comSubmit.addParam("GIFT_STD", "${param.GIFT_STD}");
			comSubmit.addParam("GIFT_MAN", "${param.GIFT_MAN}");
			comSubmit.submit();
		}
		
		function fn_deleteGiftSell(){
			var comSubmit = new ComSubmit("form1");
			var check = "${map.ID}";
			
			if(check == ""){
				alert("이미 삭제됬거나  등록 되지않은  상태입니다.");
				return false;
			}
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/deleteGiftSell.do' />");
				comSubmit.addParam("MID", $("#GF_ID").val());
				comSubmit.addParam("WID", $('#WRITE_ID').val());			
				comSubmit.submit();
			}
		}
	
	
	</script>
</head>

<body>

<form id="form1" name="form1">
<input type="hidden" id="GF_ID" name="GF_ID" value="${param.MID}">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${param.WID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
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

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
		
		<div class="LblockTab" style="position: relative;">
			<ul class="Lclear">
				<li id='property'><span><a href="#this" class="btn" id="mast">접수대장</a></span></li>					
				<li class="Lcurrent" id='public'><span><a href="#this" class="btn" id="sell">매각대장</a></span></li>						
			</ul>
		</div>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">매각대장</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	      	  <tr>
	            <td width="130" class="tbl_field">선물명</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_NM}</td>								
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">규격</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_STD}</td>								
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">신고인</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_MAN}</td>								
	          </tr>	          	          	         
			  <tr>
	            <td width="130" class="tbl_field">감정가액</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	               <fmt:formatNumber value="${map.APPRAISED_VALUE}" pattern="###,###,###" />                
	            </td>								
	          </tr>			  
			  <tr>
	            <td width="130" class="tbl_field">감정기관</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	               ${map.APPRAISED_INSTITUTE}	                
	            </td>
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">매각일시</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <fmt:parseDate value="${map.SELL_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">매각방법</td>
	            <td colspan="3" width="607" class="tbl_list_left">	           
	            <c:set var="method_gb" value="${map.SELL_METHOD_GB}" />
				<c:choose>
	 			<c:when test="${method_gb eq '1'}">
	 			선물 신고자(우선 매각)
	 			</c:when>
	 			<c:when test="${method_gb eq '2'}">
	 			조달청을 통해 매각
	 			</c:when>
	 			<c:otherwise>
	 			</c:otherwise>
	 			</c:choose>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">매각금액</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <fmt:formatNumber value="${map.SELL_AMT}" pattern="###,###,###" />	                
	            </td>
	          </tr>
	           <tr>
	            <td width="130" class="tbl_field">비고</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	               ${map.BIGO}	                
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
                    <td><img src="/gift/images/btn_type0_head.gif"></td>
                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록/수정</font></a></td>
                    <td><img src="/gift/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/gift/images/btn_type0_head.gif"></td>
                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/gift/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				                			
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
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
