<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
			fn_selectPrvCnrtBoardList(1);
				
			$("#insert").on("click", function(e){ //신규입력
				e.preventDefault();			
				fn_insert();				
			});
			
						
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();
				$("#form1").each(function() {				 
			       this.reset();
			    });				
			});
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectPrvCnrtBoardList();
			});
					 
			
			$("#TITLE").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectPrvCnrtBoardList();
				}

			});
			
			$("#logout").on("click", function(e){ //로그아웃				
				e.preventDefault();				
				fn_logout();
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
				fn_selectPrvCnrtBoardList();
			});	
			
		});
		
		
	
		function fn_logout(){
			var comSubmit = new ComSubmit();			
			comSubmit.setUrl("<c:url value='/logout.do' />");
			comSubmit.submit();
		}
		    	  
		function fn_prvCnrtBoardDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.addParam("PARENT_ID", obj.parent().find("#ID").val());
			comSubmit.addParam("BOARD_GB", $("#BOARD_GB").val());
			comSubmit.addParam("BOARD_ID", $("#BOARD_ID").val());
			
			comSubmit.submit();
		}
		
		
		
		function fn_selectPrvCnrtBoardList(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtBoardList.do' />");
			comAjax.setCallback("fn_selectPrvCnrtBoardListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("TITLE", $("#TITLE").val());
			comAjax.addParam("BOARD_ID", $("#BOARD_ID").val());  //보드 게시판 구분 1
			comAjax.addParam("BOARD_GB", $("#BOARD_GB").val()); //첨부파일 구분 7			
			comAjax.ajax();
		}
		
		
		function fn_selectPrvCnrtBoardListCallback(data){			
			data = $.parseJSON(data);
			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
						
			$("#tcnt").text(comma(total));
						
			body.empty();
			if(total == 0){
				var str = "<tr>" + 
					  "<td colspan=\"6\">조회된 결과가 없습니다.</td>" +
					  "</tr>";
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectPrvCnrtBoardList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var str2 = "";
				var t_str = "";
				var tr_class = "";
				var td_class = "";
				var tmp = "";
				
				
/* 				str2 = "<tr class='tr1'>" + 
				  	   "<td>" + "공지"  + "</td>" +							   			  	
				       "<td>" + 
				   	   "<a href='#this' name='title' >" + "서식(출장복명, 업체관리정보, 만족도평가)"  + "</a>" + 									  
				       "<input type='hidden' name='title' id='ID' value='1'>" +								  						 
				       "</td>" +
				       "<td>" + "2019.05.02" + "</td>" +						   									   			  
				       "<td>" + "박지혜" + "</td>" +
				       "<td>" + "<a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" + 
				       "<input type='hidden' name='FID' id='FID' value='785'>" +
				       "</td>" +
				       "<td>" + " " + "</td>" +						   			  
	                   "</tr>";	  */
				
				$.each(data.list, function(key, value){
					       t_str = "";
					       tmp = "";
						
					       if ((value.RNUM % 2) == 0 )
								tr_class = "tr1";
						   else
								tr_class = "tr2";
					       
					       if(value.ATTACH == 'Y')
					    	   tmp = "<a href='#this' name='filenm' ><img src=\"/scms/images/ico_att.gif\" align=\"absmiddle\"></a>" +
					    	         "<input type='hidden' name='FID' id='FID' value=" + value.FID + ">";
												
						   str += "<tr class=\"" + tr_class + "\">" + 
						   			   "<td>" + value.RNUM  + "</td>" +							   			  	
						   			   "<td>" + 
						   			   "<a href='#this' name='title' >" + value.TITLE  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" +								  						 
									   "</td>" +
						   			   "<td>" + formatDate(NvlStr(value.INS_DATE), ".") + "</td>" +						   									   			  
						   			   "<td>" + comma(NvlStr(value.USER_NAME)) + "</td>" +
						   			   "<td>" + tmp + "</td>" +
						   			   "<td>" + value.HIT_CNT + "</td>" +						   			  
					                   "</tr>";													              					                   
					                   
				});
				body.append(str2 + str);
				
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_prvCnrtBoardDetail($(this));
				});
				
				$("a[name='filenm']").on("click", function(e){ //파일명 
					e.preventDefault();
					fn_downloadFile($(this));
				});
											
			}
		}
		
		function fn_insert(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardWrite.do' />");
			comSubmit.addParam("BOARD_ID", $("#BOARD_ID").val());
			comSubmit.submit();
		}

		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();		
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
		
	</script>
</head>
<body bgcolor="#FFFFFF">
<form id="form1" name="form1">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="7">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/scms/images/popup_title.png" align="absmiddle"></a>공지사항</td>
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
 
   
     <table width="600" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">	  
      <tr>
        <td width="120" class="tbl_field">제목</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="TITLE" name="TITLE" class="input" style="width:300;">	                          
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
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type1_head.gif"></td>
               <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
               <td><img src="/scms/images/btn_type1_end.gif"></td>
             </tr>
           </table>                
           </td>
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="search"><font color="white">검색</font></a></td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
             </tr>
           </table>                
           </td>
           <c:choose>
           <c:when test="${u_rt eq 'A'}">
           <td>
           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">             
             <tr>
               <td><img src="/scms/images/btn_type0_head.gif"></td>
               <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="insert"><font color="white">신규등록</font></a></td>
               <td><img src="/scms/images/btn_type0_end.gif"></td>
             </tr>
           </table>
           </td>
           </c:when>
           </c:choose>                                                  
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
          <th>&nbsp;</th><th>제목</th>
          <th>작성일</th><th>작성자</th><th>파일</th><th>조회수</th>          
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