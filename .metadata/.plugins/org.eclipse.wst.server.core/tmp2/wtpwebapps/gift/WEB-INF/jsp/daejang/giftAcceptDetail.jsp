<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>선물접수대장</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_giftAcceptList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();
					fn_giftAcceptUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#mast").on("click", function(e){ //선물접수
				e.preventDefault();
				fn_officeworkBasicMod();
			});
			
			$("#mng").on("click", function(e){ //관리대장
				e.preventDefault();
				//fn_giftMngStatus();
				//fn_giftMngWrite();
				fn_giftMngDetail();
			});
			
			
			$("#sell").on("click", function(e){ //매각대장
				e.preventDefault();				
				//fn_giftSellStatus();
				//fn_giftSellWrite();
				fn_giftSellDetail();
				
			});
			
		
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#USER_RIGHT').val() == 'A')
				{	
					e.preventDefault();				
					fn_deleteGiftAccept();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
		
		});
		
		function fn_giftAcceptList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_giftMngStatus(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftMngStatus.do' />");
			comSubmit.addParam("MID", id);				
			comSubmit.submit();
		}
		
		function fn_giftMngWrite(){			
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftMngWrite.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.addParam("GIFT_NM", "${map.GIFT_ITEM_NM}");
			comSubmit.addParam("GIFT_STD", "${map.GIFT_STND}");
			comSubmit.addParam("GIFT_MAN", "${map.TAKE_PSN_NM}");
			comSubmit.submit();
		}
		
		function fn_giftMngDetail(){			
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftMngDetail.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.addParam("GIFT_NM", "${map.GIFT_ITEM_NM}");
			comSubmit.addParam("GIFT_STD", "${map.GIFT_STND}");
			comSubmit.addParam("GIFT_MAN", "${map.TAKE_PSN_NM}");
			comSubmit.submit();
		}
		
		function fn_giftAcceptUpdate(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptUpdate.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}
		
		function fn_giftSellStatus(obj){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/giftSellStatus.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("WID", "${map.INS_ID}");
			comSubmit.submit();
		}
		
		
		function fn_giftSellWrite(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			
			comSubmit.setUrl("<c:url value='/daejang/giftSellWrite.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.addParam("GIFT_NM", "${map.GIFT_ITEM_NM}");
			comSubmit.addParam("GIFT_STD", "${map.GIFT_STND}");
			comSubmit.addParam("GIFT_MAN", "${map.TAKE_PSN_NM}");
			comSubmit.submit();
		}
		
		function fn_giftSellDetail(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();			
			
			comSubmit.setUrl("<c:url value='/daejang/giftSellDetail.do' />");
			comSubmit.addParam("MID", id);
			comSubmit.addParam("WID", $('#WRITE_ID').val());
			comSubmit.addParam("GIFT_NM", "${map.GIFT_ITEM_NM}");
			comSubmit.addParam("GIFT_STD", "${map.GIFT_STND}");
			comSubmit.addParam("GIFT_MAN", "${map.TAKE_PSN_NM}");
			comSubmit.submit();
		}
		
		function fn_deleteGiftAccept(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/deleteGiftAccept.do' />");
				comSubmit.addParam("ID", id);
				comSubmit.submit();
			}
		}
		
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

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
			<li class="Lcurrent" id='property'><span><a href="#this" class="btn" id="mast">접수대장</a></span></li>
			<c:set var="dept_gb" value="${map.TRNSF_DEPT_GB}" />
				<c:choose>
	 			<c:when test="${dept_gb eq 'C'}">
	 			<li id='maindoc1'><span><a href="#this" class="btn" id="mng">관리대장</a></span></li>		
	 			</c:when>
	 			<c:when test="${dept_gb eq 'J'}">
	 			<li id='public'><span><a href="#this" class="btn" id="sell">매각대장</a></span></li>
	 			</c:when>				
	 		</c:choose>	
		</ul>
		</div>
                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">선물명</td>
	            <td width="243" class="tbl_list_left">${map.GIFT_ITEM_NM}</td>
				<td width="120" class="tbl_field">선물사진등록</td>
	            <td width="244" class="tbl_list_left">
	            <c:forEach var="row" items="${list }">
	            <img src="/simg/${row.STORED_FILE_NAME }" width="300" hight="250">
	            <input type="hidden" id="BOARD_ID" name="BOARD_ID" value="${row.BOARD_ID }">
	            </c:forEach>
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">규격</td>
	            <td width="243" class="tbl_list_left">${map.GIFT_STND}</td>
				<td width="120" class="tbl_field">수량</td>
	            <td width="244" class="tbl_list_left"><fmt:formatNumber value="${map.GIFT_NUMBER}" pattern="###,###,###" /></td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">수령일</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.GIFT_TAKE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">수령장소</td>
	            <td width="244" class="tbl_list_left">${map.GIFT_TAKE_PLACE}</td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">수령경위 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.GIFT_TAKE_DETAILS}</td>
	          </tr>			 
	      </table>
	      
	       <p>&nbsp;</p>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">신고인</td>
					</tr>
				</table>
				</td>				
			</tr>
		  </table>
	      
	      <br>
	      
	      <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">소속</td>
	            <td width="243" class="tbl_list_left">${map.TAKE_DEPT_NM}</td>
				<td width="120" class="tbl_field">직위(직급)</td>
	            <td width="244" class="tbl_list_left">${map.TAKE_PSN_CLASS_NM}</td>							
	          </tr>			
	          <tr>
	            <td width="120" class="tbl_field">성명 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">${map.TAKE_PSN_NM}</td>
	            <td width="120" class="tbl_field">신고일</td>
	            <td width="244" class="tbl_list_left"><fmt:parseDate value="${map.GIFT_REPORT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
	          </tr>
	          		 
	      </table>
	      
	      
	      
	      <p>&nbsp;</p>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">증정인</td>
					</tr>
				</table>
				</td>				
			</tr>
		  </table>
	      
	      <br>
	      
	       <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">국명</td>
	            <td width="243" class="tbl_list_left">${map.GIVE_PSN_NATION_NM}</td>
				<td width="120" class="tbl_field">직위</td>
	            <td width="244" class="tbl_list_left">${map.GIVE_PSN_CLASS_NM}</td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">성명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.GIVE_PSN_NM}</td>
	          </tr>			 
	      </table>
	      
	      
	      <p>&nbsp;</p>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">선물평가</td>
					</tr>
				</table>
				</td>				
			</tr>
		  </table>
	      
	      <br>
	      
	      <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">평가일시</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.EVAL_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">평가가격</td>
	            <td width="244" class="tbl_list_left"><fmt:formatNumber value="${map.EVAL_AMT}" pattern="###,###,###" /></td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">평가결과</td>
	            <td width="243" class="tbl_list_left">
	            <c:set var="rst_gb" value="${map.EVAL_RESULT_GB}" />
				<c:choose>
	 			<c:when test="${rst_gb eq '1'}">
	 			신고처리
	 			</c:when>
	 			<c:when test="${rst_gb eq '2'}">
	 			반려처리
	 			</c:when>
	 			<c:otherwise>
	 			</c:otherwise>
	 			</c:choose>
	            </td>
				<td width="120" class="tbl_field">비고</td>
	            <td width="244" class="tbl_list_left">${map.EVAL_BIGO}</td>
	          </tr>			 
	      </table>
	      
	      
	       <p>&nbsp;</p>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">선물이관</td>
					</tr>
				</table>
				</td>				
			</tr>
		  </table>
	      
	      <br>
	      
	      <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">이관일시</td>
	            <td width="243" class="tbl_list_left"><fmt:parseDate value="${map.TRNSF_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
				<td width="120" class="tbl_field">이관부서</td>
	            <td width="244" class="tbl_list_left">
	            <c:set var="dept_gb" value="${map.TRNSF_DEPT_GB}" />
				<c:choose>
	 			<c:when test="${dept_gb eq 'J'}">
	 			재무과
	 			</c:when>
	 			<c:when test="${dept_gb eq 'C'}">
	 			총무과
	 			</c:when>
	 			<c:otherwise>
	 			</c:otherwise>
	 			</c:choose>
	            </td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.TRNSF_BIGO}</td>	
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
                <c:set var="us_rt" value="${sessionScope.userinfo.userright}" />
				<c:choose>
	 			<c:when test="${us_rt eq 'A'}">
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/gift/images/btn_type0_head.gif"></td>
                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정</font></a></td>
                    <td><img src="/gift/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/gift/images/btn_type0_head.gif"></td>
                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/gift/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                </c:when>
                </c:choose>                		
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
