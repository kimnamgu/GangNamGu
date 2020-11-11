<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>선물접수대장</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		
		$(document).ready(function(){
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				//fn_giftMngStatus();
				fn_giftAcceptList();
			});
			
			$("#write").on("click", function(e){ //등록 버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#USER_RIGHT').val() == 'C')
				{				
					e.preventDefault();				
					fn_giftMngWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
			
			});
			
			$("#mast").on("click", function(e){ //선물접수
				e.preventDefault();				
				fn_giftAcceptDetail();
			});	
			
			
			$("#del").on("click", function(e){ //삭제버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#USER_RIGHT').val() == 'C')
				{				
					e.preventDefault();				
					fn_deleteGiftMng();
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
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftMngStatus.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());			
			comSubmit.submit();
		}
		
		
		
		function fn_giftAcceptDetail(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptDetail.do' />");
			comSubmit.addParam("ID", $("#GF_ID").val());
			comSubmit.addParam("BDID", $("#BOARD_ID").val());
			comSubmit.submit();
		}
		
		function fn_giftMngWrite(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/daejang/giftMngWrite.do' />");
			comSubmit.addParam("MID", $("#GF_ID").val());
			comSubmit.addParam("WID", $('#WRITE_ID').val());			
			comSubmit.addParam("GIFT_NM", "${param.GIFT_NM}");
			comSubmit.addParam("GIFT_STD", "${param.GIFT_STD}");
			comSubmit.addParam("GIFT_MAN", "${param.GIFT_MAN}");
			comSubmit.submit();
		}
		
		
		function fn_set_user_info(obj){
						
	
			$("#TAKE_PSN_CLASS_CD").val(obj.parent().find("#JCID").val());
			$("#TAKE_PSN_CLASS_NM").val(obj.parent().find("#JCLASS").val());
			$("#TAKE_PSN_ID").val(obj.parent().find("#JID").val());
			$("#TAKE_PSN_NM").val(obj.parent().find("#JNAME").val());
			
			$('#pop').hide();
		}
		
		
		function fn_jikwonlist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/daejang/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("USER_NAME",$("#USER_NAME").val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
			var str = "";
			var i = 1;
			var body = $("#mytable");
			
			body.empty();
					
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
	                   "<td class=\"list_center\" nowrap>" + 	            	                 
	                   "<a href='#this' name='title' >" + value.USER_NAME  + "</a>" + 					
	                   "<input type='hidden' name='JDID' id='JDID' value=" + value.DEPT_ID + ">" +
					   "<input type='hidden' name='JDEPT' id='JDEPT' value=" + value.DEPT_NAME + ">" +
					   "<input type='hidden' name='JID' id='JID' value=" + value.USER_ID + ">" +
					   "<input type='hidden' name='JNAME' id='JNAME' value=" + value.USER_NAME + ">" +
					   "<input type='hidden' name='JCID' id='JCID' value=" + value.CLASS_ID + ">" +
					   "<input type='hidden' name='JCLASS' id='JCLASS' value=" + value.CLASS_NAME + ">" +
					   "</td>" +	                   
	                   "<td class=\"list_center\" nowrap>" + value.DEPT_NAME + "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + value.CLASS_NAME + "</td>" +
	                   "<td class=\"list\"'>" + value.GRADE_NAME + "</td>" +	                 												
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"5\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_set_user_info($(this));
			});
		}
	
		
		function fn_deleteGiftMng(){
			var comSubmit = new ComSubmit("form1");	
			var check = "${map.ID}";
			
			if(check == ""){
				alert("이미 삭제됬거나  등록 되지않은  상태입니다.");
				return false;
			}
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{					
				comSubmit.setUrl("<c:url value='/daejang/deleteGiftMng.do' />");
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
<input type="hidden" id="TAKE_PSN_CLASS_CD" name="TAKE_PSN_CLASS_CD" value="${map.TAKE_PSN_CLASS_CD}">
<input type="hidden" id="TAKE_PSN_ID" name="TAKE_PSN_ID" value="${map.TAKE_PSN_ID}">
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
				<li class="Lcurrent" id='maindoc1'><span><a href="#this" class="btn" id="eval">관리대장</a></span></li>							
			</ul>
		</div>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">관리대장</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	      	  	      	  
	      	  <tr>
	            <td width="120" class="tbl_field">선물명</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_NM}</td>								
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">규격</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_STD}</td>								
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">신고인</td>
	            <td colspan="3" width="607" class="tbl_list_left">${param.GIFT_MAN}</td>								
	          </tr>	          	          	         
			  <tr>
	            <td width="120" class="tbl_field">선물수령일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <fmt:parseDate value="${map.TAKE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>                
	            </td>								
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">선물수령자</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                                     총무과 (직급  : ${map.TAKE_PSN_CLASS_NM} &nbsp;성명 : ${map.TAKE_PSN_NM} )             
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">보관장소</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	              ${map.KEEP_PLACE}	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
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
</body>
</html>
