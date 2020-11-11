<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
		
			//fn_deptList();						
			fn_selectMainSaupSubList(1);
		
			$("#write").on("click", function(e){ //글쓰기 버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_mainSaupSubWrite();
				}
				else{
					alert('권한이 없습니다.');
				}			
			});
			
			$("#update").on("click", function(e){ //기본정보 수정
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_mainSaupUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}						
			});
	
			
			$("#uplist").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_mainSaupList();
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
						
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectMainSaupSubList();
			});	
 			
		});
		
		
	
		function fn_mainSaupList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/mainSaupList.do?year=${param.year}&sgb=${param.sgb}' />");
			comSubmit.submit();
		}
		
		
		function fn_mainSaupSubWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/mainSaupSubWrite.do?year=${param.year}&sgb=${param.sgb}' />");
			comSubmit.addParam("MAST_ID", "${param.MAST_ID}");
			comSubmit.addParam("ID", "${param.MAST_ID}");
			comSubmit.addParam("SAUP_NM", "${param.SAUP_NM}");
			comSubmit.addParam("SAUP_DEPT_CD", "${param.SAUP_DEPT_CD}");
			comSubmit.addParam("SAUP_DEPT_NM", "${param.SAUP_DEPT_NM}");
			comSubmit.submit();
		}
		
		
		
		function fn_mainSaupUpdate(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/mainSaupUpdate.do?year=${param.year}&sgb=${param.sgb}' />");
			comSubmit.addParam("ID", "${param.MAST_ID}");
			comSubmit.addParam("SAUP_NM", "${param.SAUP_NM}");
			comSubmit.addParam("SAUP_DEPT_NM", "${param.SAUP_DEPT_NM}");
			comSubmit.submit();
		}
		
		function fn_mainSaupDetail(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/mainSaupDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.submit();
			
		}
		
		function fn_selectMainSaupSubList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectMainSaupSubList.do' />");
			comAjax.setCallback("fn_selectMainSaupSubListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());				
			comAjax.addParam("MAST_ID","${param.MAST_ID}");
			comAjax.ajax();
		}
		
		
		function fn_selectMainSaupSubListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"10\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectMainSaupSubList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var t_str = "";
				var tr_class = "";
		
				$.each(data.list, function(key, value){
					       t_str = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					      			
						   str += "<tr class=\"" + tr_class + "\">" + 
									   "<td>" + ((total - value.RNUM)+1)  + "</td>" +									   
					                   "<td>" + 					                   
						   			   "<a href='#this' name='title' >" + NvlStr(value.UPD_DATE) + "</a>" +						   			   
						   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" + 
						   			   "<input type='hidden' name='title' id='UMID' value=" + value.MAST_ID + ">" +
						   			   "<input type='hidden' name='title' id='UDATE' value=" + value.UPD_DATE + ">" +
						   			   "<input type='hidden' name='title' id='UINDATE' value=" + value.INS_DATE + ">" +
						   			   "</td>" +						   			  
						   			   "<td>" + NvlStr(value.SAUP_PROCESS_PERCENT) + "</td>" +
						   			   "<td>" + NvlStr(value.SAUP_PROCESS_TOT_PERCENT) + "</td>" +
					                   /*"<td class=\"list_center_sht\">" +
						   			   "<font title=\"" + NvlStr(value.SAUP_PUSH_CONTENT) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.SAUP_PUSH_CONTENT), 18) +  "</font>" +
						   			   "</td>" +
						   			   "<td class=\"list_center_sht\">" +
						   			   "<font title=\"" + NvlStr(value.SAUP_NEXT_CONTENT) +  "\" style=\"CURSOR:hand;\">" + CutString(NvlStr(value.SAUP_NEXT_CONTENT), 18) +  "</font>" +
						   			   "</td>" + */
						   			   "<td>" + NvlStr(value.INS_DATE) + "</td>" +
						   			   "<td>" + NvlStr(value.MOD_DATE) + "</td>" +
									   "</tr>";

				});
				body.append(str);
				
								
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_mainSaupSubUpdate($(this));
				});
								
			}
		}
		
		
		function fn_mainSaupSubUpdate(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/mainSaupSubUpdate.do?year=${param.year}&sgb=${param.sgb}' />");
			comSubmit.addParam("ID", obj.parent().find("#UID").val());
			comSubmit.addParam("MAST_ID", obj.parent().find("#UMID").val());
			comSubmit.addParam("UPD_DATE", obj.parent().find("#UDATE").val());
			comSubmit.addParam("INS_DATE", obj.parent().find("#UINDATE").val());
			comSubmit.addParam("SAUP_NM", "${param.SAUP_NM}");
			comSubmit.addParam("SAUP_DEPT_NM", "${param.SAUP_DEPT_NM}");
			comSubmit.submit();
			
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1" method="post">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${param.SAUP_DEPT_CD}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">주요사업관리시스템</td>
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
 
    <table width="520" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
      <tr>
        <td width="120" class="tbl_field">사업명</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <b>${mapM.SAUP_NM}</b>	                          
        </td>
      </tr>
      <tr>
        <td width="120" class="tbl_field">관련부서</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <b>${mapM.SAUP_DEPT_NM}</b>	                          
        </td>
      </tr>      
    </table>
    
    
    
      
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">     
     <tr>
       <td align="left"><b>Total : <span id="tcnt"></span></b> &nbsp; 
       <select id="DISP_CNT" name="DISP_CNT" class="input">
       <option value="10">10</option>
       <option value="20">20</option>
       <option value="30">30</option>
       <option value="40">40</option>
       <option value="50">50</option>
   	   </select>
       </td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">
         <tr>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/bims/images/btn_type0_head.gif"></td>
               <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">사업정보수정</font></a></td>
               <td><img src="/bims/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>         	           
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/bims/images/btn_type0_head.gif"></td>
               <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
               <td><img src="/bims/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>                     		
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/bims/images/btn_type0_head.gif"></td>
               <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="uplist"><font color="white">사업리스트</font></a></td>
               <td><img src="/bims/images/btn_type0_end.gif"></td>
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
    
	 <table class="tbl1">
     <thead>
        <tr>
          <th>&nbsp;</th><th>최종수정일</th><th>2020년 진척률</th><th>최종목표  대비 진척률</th><th>등록일</th><th>수정시간</th>
        </tr>
      </thead>
      <tbody id='mytable'>     
      </tbody>
      </table>	
	</td>
	</tr>
</table>
</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
</body>
</html>