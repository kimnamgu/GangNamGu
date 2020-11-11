<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>확정일자 수기대장 열람시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<style type="text/css">
 #pop{
  width:300px; height:250px; background:#3d3d3d; color:#fff; 
  position:absolute; top:10px; left:100px; text-align:center; 
  border:1px solid #000;
   }
</style>
<script type="text/javascript">
		var gfv_count = 1;
		var dep_id = "${param.DEP_ID}";
		
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				//alert('aa');
				e.preventDefault();
				fn_fixedDateList();
			});
			
			$("#mod").on("click", function(e){ //수정하기 버튼
				if( $('#MOD_DEPT').val() == dep_id ||  $('#USER_RIGHT').val() == 'A')
				{
					e.preventDefault();
					fn_updateFixedDateData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#del").on("click", function(e){ //삭제하기 버튼
				if( $('#MOD_DEPT').val() == dep_id ||  $('#USER_RIGHT').val() == 'A')
				{				
					e.preventDefault();
					fn_deleteFixedDateData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
		});
		

		function fn_fixedDateList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/fixedDateList.do' />");
			comSubmit.submit();
		}
		
		function fn_updateFixedDateData(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
			
			if($('#TENANT_NAME').val() == "")
			{
				alert('임차인을 입력하세요.');
				$('#TENANT_NAME').focus();
				return false();
			}
			
			
			if($('#FIXDATE_GRANT_DATE').val() == "")
			{
				alert('확정일자 부여일을 입력하세요.');
				$('#FIXDATE_GRANT_DATE').focus();
				return false();
			}
			
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/updateFixedDateData.do' />");
				comSubmit.addParam("ID", id);
				comSubmit.submit();
			}
		}
		

		function fn_deleteFixedDateData(){
			var comSubmit = new ComSubmit("form1");
			var id = "${map.ID}";
			
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/deleteFixedDateData.do' />");
				comSubmit.addParam("ID", id);
				comSubmit.submit();
			}
		}
		
	</script>
</head>

<body>

<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/fds/images/popup_title.gif" align="absmiddle">확정일자 데이터 수정</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="100%">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title"><img src="/fds/images/title_ico.gif" align="absmiddle">데이터 수정</td>
			</tr>
		</table>
		</td>				
	</tr>
	</table>
	
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">임차인 <font class="font_star">*</font></td>
	            <td width="500" class="tbl_list_left">
	                <input type="text" id="TENANT_NAME" name="TENANT_NAME" class="input" style="width:174;" value="${map.TENANT_NAME}">	                
	            </td>
	          </tr>
	          
	          <tr>
	            <td width="120" class="tbl_field">확정일자부여일<font class="font_star">*</font></td>
	            <td width="500" class="tbl_list_left">
	            	<c:set var="numberAsString">${map.FIXDATE_GRANT_DATE}</c:set>	            	
	            	<c:choose>
	            	<c:when test="${numberAsString.matches('[0-9]+')}">	                
	                <input type="text" id="FIXDATE_GRANT_DATE" name="FIXDATE_GRANT_DATE" class="input" style="width:174;" value="<fmt:parseDate value="${map.FIXDATE_GRANT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>"> <a href="#a" onclick="popUpCalendar(this, FIXDATE_GRANT_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/fds/images/ic_cal.gif" alt="달력" /></a>
	                </c:when>
	                <c:otherwise>	                
	                <input type="text" id="FIXDATE_GRANT_DATE" name="FIXDATE_GRANT_DATE" class="input" style="width:174;" value="${map.FIXDATE_GRANT_DATE}"> <a href="#a" onclick="popUpCalendar(this, FIXDATE_GRANT_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/fds/images/ic_cal.gif" alt="달력" /></a>
	                </c:otherwise>
	                </c:choose>	                	                
	            </td>
	          </tr>
	          <tr>
				<td width="120" class="tbl_field">pdf 파일 <font class="font_star">*</font></td>
	            <td width="500" class="tbl_list_left">
	                <!-- <input type="text" id="GIFT_IMG" name="GIFT_IMG" class="input" style="width:174;"> -->
	                
	                <div id="fileDiv">
						<p>
							<input width="350" type="file" id="file" name="file_0">
							<!-- <a href="#this" class="btn" id="delete" name="delete">삭제</a>-->
							 &nbsp; ${map.LINK_IMG_NM}
						</p>
					</div>
					<!-- <a href="#this" class="btn" id="addFile">파일 추가</a> -->
	            </td>							
	          </tr>
	          <tr>
				<td width="120" class="tbl_field">비고</td>
	            <td width="500" class="tbl_list_left">
	                <input type="text" id="BIGO" name="BIGO" class="input" style="width:230;" value="${map.BIGO}">
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
                    <td><img src="/fds/images/btn_type0_head.gif"></td>
                    <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="mod"><font color="white">수정</font></a></td>
                    <td><img src="/fds/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/fds/images/btn_type0_head.gif"></td>
                    <td background="/fds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/fds/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/fds/images/btn_type1_head.gif"></td>
                    <td background="/fds/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/fds/images/btn_type1_end.gif"></td>
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


<!-- POPUP --> 
<div id="pop" style="position:absolute;left:395px;top:190;z-index:200;display:none;"> 
<table width=430 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr>
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="60"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">직위</td>							
			</tr>
			<tr>
				<td colspan="5" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable'>			
			</tbody>
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close2"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  


</body>
</html>
