<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>선물접수대장</title>
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
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				//alert('aa');
				e.preventDefault();
				fn_giftAcceptList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				if( $('#USER_RIGHT').val() == 'A')
				{				
					e.preventDefault();
					fn_insertGiftAccept();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });
			
			$('#open').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop').show();
		        fn_jikwonlist();
		    });
			
			
			//$('#open').keypress(function(e) { 
			//$("input[name=search]").keypress(function(e) {
			/*$('#open').keyup(function(e) {
				alert('kk');
			    if (e.keyCode == 13){
			    	$('#pop').show();
			        fn_jikwonlist();
			    }    
			    
			    
			});

			$('#open').keydown(function (event) {
	                if (event.which == 13) {    //enter
	                	$('#pop').show();
				        fn_jikwonlist();
	                }
	        });*/
			
		});
		

		function fn_giftAcceptList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/giftAcceptList.do' />");
			comSubmit.submit();
		}
		
		function fn_insertGiftAccept(){
			var comSubmit = new ComSubmit("form1");
			
			/*			
			
			if($('#OW_CONTENTS').val() == "")
			{
				alert('위탁내용을 입력하세요.');
				$('#OW_CONTENTS').focus();
				return false();
			}
			
			
			if($('#SISEL_POSITION').val() == "")
			{
				alert('시설위치를 입력하세요.');
				$('#SISEL_POSITION').focus();
				return false();
			}
			
			
			if($('#FIRST_TRUST_DATE').val() == "")
			{
				alert('최초위탁일을 입력하세요.');
				$('#FIRST_TRUST_DATE').focus();
				return false();
			}
			*/
			if(confirm('데이터를 등록하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/insertGiftAccept.do' />");
				comSubmit.submit();
			}
		}
		
		function fn_set_user_info(obj){
			
			
			$("#TAKE_DEPT_CD").val(obj.parent().find("#JDID").val());
			$("#TAKE_DEPT_NM").val(obj.parent().find("#JDEPT").val());
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
		
		
		
	</script>
</head>

<body>

<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="TAKE_DEPT_CD" name="TAKE_DEPT_CD" value="">
<input type="hidden" id="TAKE_PSN_CLASS_CD" name="TAKE_PSN_CLASS_CD" value="">
<input type="hidden" id="TAKE_PSN_ID" name="TAKE_PSN_ID" value="">
<input type="hidden" id="GIVE_PSN_NATION_CD" name="GIVE_PSN_NATION_CD" value="3333">
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
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="100%">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">선물접수</td>
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
	            <td width="120" class="tbl_field">선물명 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="GIFT_ITEM_NM" name="GIFT_ITEM_NM" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">선물사진등록</td>
	            <td width="244" class="tbl_list_left">
	                <!-- <input type="text" id="GIFT_IMG" name="GIFT_IMG" class="input" style="width:174;"> -->
	                
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<!-- <a href="#this" class="btn" id="delete" name="delete">삭제</a>-->
						</p>
					</div>
					<!-- <a href="#this" class="btn" id="addFile">파일 추가</a> -->
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">규격</td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="GIFT_STND" name="GIFT_STND" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">수량</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="GIFT_NUMBER" name="GIFT_NUMBER" class="input" style="width:174;">
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">수령일 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">	                
	                <input type="text" id="GIFT_TAKE_DATE" name="GIFT_TAKE_DATE" class="input" style="width:174;">	<a href="#a" onclick="popUpCalendar(this, GIFT_TAKE_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/gift/images/ic_cal.gif" alt="달력" /></a>	                
	            </td>
				<td width="120" class="tbl_field">수령장소 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="GIFT_TAKE_PLACE" name="GIFT_TAKE_PLACE" class="input" style="width:174;">
	            </td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">수령경위</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="GIFT_TAKE_DETAILS" name="GIFT_TAKE_DETAILS" class="input" style="width:557;">	                
	            </td>
	          </tr>			 
	      </table>
	      
	      
		  <p>&nbsp;</p>
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="100%">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title"><img src="/gift/images/title_ico.gif" align="absmiddle">신고인  &nbsp;<input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;" > <a href="#this" name="search" id="open" class="bk">찾기</a></td>
					</tr>
				</table>
				</td>				
			</tr>
		  </table>
	      
	      <br>
	      
	      <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">소속 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="TAKE_DEPT_NM" name="TAKE_DEPT_NM" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">직위(직급) <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="TAKE_PSN_CLASS_NM" name="TAKE_PSN_CLASS_NM" class="input" style="width:174;">
	            </td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">성명 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="TAKE_PSN_NM" name="TAKE_PSN_NM" class="input" style="width:174;">	                
	            </td>
	            <td width="120" class="tbl_field">신고일 <font class="font_star">*</font></td>
	            <td width="244" class="tbl_list_left">	                
	                <input type="text" id="GIFT_REPORT_DATE" name="GIFT_REPORT_DATE" class="input" style="width:174;">	<a href="#a" onclick="popUpCalendar(this, GIFT_REPORT_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/gift/images/ic_cal.gif" alt="달력" /></a>	                
	            </td>
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
	            <td width="120" class="tbl_field">국명 <font class="font_star">*</font></td>
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="GIVE_PSN_NATION_NM" name="GIVE_PSN_NATION_NM" class="input" style="width:174;">	                
	            </td>
				<td width="120" class="tbl_field">직위</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="GIVE_PSN_CLASS_NM" name="GIVE_PSN_CLASS_NM" class="input" style="width:174;">
	            </td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">성명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="GIVE_PSN_NM" name="GIVE_PSN_NM" class="input" style="width:557;">	                
	            </td>
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
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="EVAL_DATE" name="EVAL_DATE" class="input" style="width:174;"> <a href="#a" onclick="popUpCalendar(this, EVAL_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/gift/images/ic_cal.gif" alt="달력" /></a>	                
	            </td>
				<td width="120" class="tbl_field">평가가격</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="EVAL_AMT" name="EVAL_AMT" class="input" style="width:174;">
	            </td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">평가결과</td>
	            <td width="243" class="tbl_list_left">
	                <input type="radio" id="EVAL_RESULT_GB1" name="EVAL_RESULT_GB" value="1" class="input">  신고처리&nbsp; <input type="radio" id="EVAL_RESULT_GB2" name="EVAL_RESULT_GB" value="2" class="input"> 반려처리             
	            </td>
				<td width="120" class="tbl_field">비고</td>
	            <td width="244" class="tbl_list_left">
	                <input type="text" id="EVAL_BIGO" name="EVAL_BIGO" class="input" style="width:174;">
	            </td>
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
	            <td width="243" class="tbl_list_left">
	                <input type="text" id="TRNSF_DATE" name="TRNSF_DATE" class="input" style="width:174;"> <a href="#a" onclick="popUpCalendar(this, TRNSF_DATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/gift/images/ic_cal.gif" alt="달력" /></a>	                
	            </td>
				<td width="120" class="tbl_field">이관부서</td>
	            <td width="244" class="tbl_list_left">
	                <input type="radio" id="TRNSF_DEPT_GB1" name="TRNSF_DEPT_GB" value="C" class="input"> 총무과(보관) &nbsp; <input type="radio" id="TRNSF_DEPT_GB2" name="TRNSF_DEPT_GB" value="J" class="input"> 재무과(매각)
	            </td>							
	          </tr>	 	
			  <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <input type="text" id="TRNSF_BIGO" name="TRNSF_BIGO" class="input" style="width:557;">	                
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
                    <td background="/gift/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
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
      
</table> 
</div>  


</body>
</html>
