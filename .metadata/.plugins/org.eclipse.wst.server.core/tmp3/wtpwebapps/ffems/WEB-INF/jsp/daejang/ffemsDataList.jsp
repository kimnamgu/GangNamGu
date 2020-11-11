<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		var sort_gb = "";
		
		$(document).ready(function(){
									
			fn_selectFfemsDataList(1);
						
			$("#upload").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();			
				fn_ffemsDataUpload();							
			});
			
			
			$("#admin").on("click", function(e){ //관리자메뉴
				e.preventDefault();
				if(user_right == 'A') {
					fn_adminMenu();
				}
				else{
					alert('권한이 없습니다.');	
				}				
			});
			
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				sort_gb = "";
				$("#form1").each(function() {				 
			       this.reset();			       			      
			    });
			});
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				sort_gb = "";
				$("#PAGE_INDEX").val("1");
				fn_selectFfemsDataList();
			});
			
			$("#logout").on("click", function(e){ //로그아웃				
				e.preventDefault();				
				fn_logout();
			});
			
			$("#SAUP_NM").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectFfemsDataList();
				}

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
			
			
			function fn_adminMenu(){
				var comSubmit = new ComSubmit();
				comSubmit.setUrl("<c:url value='/common/idApproveList.do?year=${param.year}' />");
				comSubmit.submit();
			}
			
			$("#DISP_CNT").change(function(){				
				$("#RECORD_COUNT").val($("#DISP_CNT option:selected").val());
				$("#PAGE_INDEX").val("1");
				fn_selectFfemsDataList();
			});	
		});
		
				
		//입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	       
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	 
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
		
		
		
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		
	
		
		function fn_ffemsDataUpload(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/ffemsDataUpload.do' />");
			comSubmit.submit();
		}
		
		
		function fn_ffemsDataDetail(obj){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/ffemsDataDetail.do' />");
			comSubmit.addParam("UPLOAD_FLAG", obj.parent().find("#UID").val());
			comSubmit.submit();			
		}
		
		
		
		function fn_ffemsDataDel(obj){
			var comSubmit = new ComSubmit();
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{	
				comSubmit.setUrl("<c:url value='/daejang/ffemsDataDelete.do' />");
				comSubmit.addParam("UPLOAD_FLAG", obj.parent().find("#UID2").val());
				comSubmit.submit();
			}
		}
		
		
		
		function fn_selectFfemsDataList(pageNo){			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectFfemsDataList.do' />");
			comAjax.setCallback("fn_selectFfemsDataListCallback");
			comAjax.addParam("SAUP_YEAR", "${param.year}");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());				
			comAjax.addParam("SAUP_DEPT_CD",$("#SAUP_DEPT_CD").val());
			comAjax.addParam("SAUP_NM",$("#SAUP_NM").val());
			comAjax.addParam("SAUP_AMT1",$("#SAUP_AMT1").val());
			comAjax.addParam("SAUP_AMT2",$("#SAUP_AMT2").val());
			comAjax.addParam("SORT_GB", sort_gb);
			comAjax.ajax();
		}
		
		
		function fn_selectFfemsDataListCallback(data){			
			//data = $.parseJSON(data);			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"7\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectFfemsDataList"
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
						   "<td>" + value.RNUM + "</td>" +
						   "<td>" + value.ID + "</td>" +
		                   "<td>" + 
			   			   "<a href='#this' name='title' >" + formatDate(NvlStr(value.GIJUN_DATE), ".") + "</a>" +
			   			   "<input type='hidden' name='title' id='UID' value=" + value.ID + ">" +			   			   
			   			   "</td>" +						   			  
			   			   "<td>" + NvlStr(value.ORIGINAL_FILE_NAME) + "</td>" +						   			  						   			   
			   			   "<td>" + comma(NvlStr(value.FILE_SIZE)) + "</td>" +			   			  
			   			   "<td>" + NvlStr(value.INS_DATE) + "</td>";
			   			   
			   			if($('#USER_RIGHT').val() == 'A')
			   			{
			   				str += "<td><a href='#this' name='title2' >삭제 </a>" +
						   "<input type='hidden' name='title2' id='UID2' value=" + value.ID + ">" +
			   			   "</td>";
			   			}
			   			else{
			   				str += "<td>&nbsp;</td>";
			   			}
			   			str += "</tr>";									  						

				});
				body.append(str);
				
								
				$("a[name='title']").on("click", function(e){ //일자 (상세)
					e.preventDefault();
					fn_ffemsDataDetail($(this));
				});
				
				$("a[name='title2']").on("click", function(e){ //삭제 
					e.preventDefault();
					fn_ffemsDataDel($(this));
				});
			}
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>
<c:set var="year" value="${param.year}"/>

 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/ffems/images/popup_title.gif" align="absmiddle"></a>신속집행현황 </td>
      </tr>
      <tr>
        <td class="margin_title">
        </td>
      </tr>
      <!-- 
      <tr>
        <td class="margin_title"><br>
         
      <table width="800" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
      <tr>
        <td width="800" class="tbl_field">3월부터 운영중인 ‘주요사업 관리시스템’ 과 관련하여 대상사업 담당자는 2019. 3. 15.(금) 16시까지 추진실적을 입력해주시기 바랍니다.<br><br>				  
		 → 입력 내용은 키워드 중심의 메모 형식으로 간략하게 작성 (매우 중요!!)<br>
		 → 실적내용을 미리 입력하셔도 되지만 입력기한보다 한참 전에  입력하면 최근자료가 아닌 것처럼 생각되므로<br> 
		       &nbsp;&nbsp;&nbsp;&nbsp;입력기한 당일 또는 그 전날 중에 처리해주시기 바랍니다<br>
		 → 다음 입력기한은 2019. 3. 29.(금) 16시까지입니다 <br><br>
		 
		 [ ※ 실적입력 주기 : 매월 1· 3 · 5주  금요일  16시 까지 (예) 4.5 / 4.19 / 5.3 ~ ]</td>
      </tr>
      </table>
         
        </td>
      </tr>-->
         
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  <tr>
    <td class="pupup_frame" style="padding-right:12px">	
 <!-- 
    <table width="520" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">    
      <tr>
        <td width="120" class="tbl_field">기간</td>
        <td colspan="3" width="400" class="tbl_list_left">	               
            <input type="text" id="SAUP_AMT1" name="SAUP_AMT1" class="input" style="width:100;"> ~
            <input type="text" id="SAUP_AMT2" name="SAUP_AMT2" class="input" style="width:100;">            	                          
        </td>
      </tr>
    </table>
   -->    
 <!-- -------------- 버튼 시작 --------------  -->
   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">     
     <tr>
       <td align="left"><b>Total : <span id="tcnt"></span></b> &nbsp; 
       <select id="DISP_CNT" name="DISP_CNT" class="input">   
       <option value="20">20</option>
       <option value="10">10</option>
   	   </select></td>
       <td align="right">
       <table border="0" cellspacing="0" cellpadding="0">                 
         <tr>                    
           <!-- <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/ffems/images/btn_type1_head.gif"></td>
               <td background="/ffems/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/ffems/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td> -->
           <c:choose>
           <c:when test="${u_rt eq 'A'}">          	           
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
             <tr>
               <td><img src="/ffems/images/btn_type0_head.gif"></td>
               <td background="/ffems/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="upload"><font color="white">업로드</font></a></td>
               <td><img src="/ffems/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           </c:when>
           </c:choose>                           		
           <!-- <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/ffems/images/btn_type0_head.gif"></td>
               <td background="/ffems/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/ffems/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td> -->                                     
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
          <th>&nbsp;</th><th>&nbsp;</th><th>기준일</th><th>파일명</th><th>파일크기</th><th>업로드일자</th><th>&nbsp;</th>
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
	
	
<!-- POPUP --> 
<div id="pop1" style="position:absolute;left:300px;top:50;z-index:200;display:none;border:2px solid #c84637;"> 
<table width=572 height=839 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr> 
<tr> 
    <td style="border:2px #ddd solid" height=809 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0">				
			<tbody id='poptable1'>			
			</tbody>
			</table>
    </td> 
</tr>     
</table> 
</div>
		
</body>
</html>