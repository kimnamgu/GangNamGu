<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>용역업체관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = '${fn:length(list)+1}';
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
							
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtCompRecordList();
			});
			
			$("#update").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				/*if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{*/
					fn_updatePrvCnrtExpert();
				/*}
				else{
					alert('권한이 없습니다.');
				}*/
			});
			
			
			$("#del").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{
					fn_deletePrvCnrtExpert();
				}
				else{
					alert('권한이 없습니다.');
				}
			});		
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name^='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$("a[name^='name']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
		});
		
		function fn_prvCnrtCompRecordList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.submit();
		}
		
		
		function fn_updatePrvCnrtExpert(){
			var comSubmit = new ComSubmit("form1");
			
			if($('#EXPERT_NM').val() == "")
			{
				alert('성명을 입력하세요!!');
				$('#EXPERT_NM').focus();
				return false();
			}
									
			if($('#RIGHT_NM').val() == "")
			{
				alert('자격 이름을 입력하세요!!');
				$('#RIGHT_NM').focus();
				return false();
			}
			
			
			if($('#MAIN_CAREER').val() == "")
			{
				alert('주요경력을  입력하세요!!');
				$('#MAIN_CAREER').focus();
				return false();
			}
			
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{							
				
				comSubmit.addParam("ID", $("#COMP_ID").val());
				comSubmit.addParam("PARENT_ID", $("#CON_ID").val());
				comSubmit.setUrl("<c:url value='/daejang/updatePrvCnrtExpert.do' />");
				comSubmit.submit();
			}
		}
		
		
		
		function fn_deletePrvCnrtExpert(){
			var comSubmit = new ComSubmit("form1");
					
			if(confirm('데이터를 삭제하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/deletePrvCnrtExpert.do' />");
				comSubmit.addParam("ID", $("#COMP_ID").val());
				comSubmit.submit();
			}
		}

		function fn_addFile(){
			
			var str = "<p>" +
			"<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>"+
			"<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" +
			"</p>";
			$("#fileDiv").append(str);
			$("#delete_"+(gfv_count++)).on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="RIGHT_CODE" name="RIGHT_CODE" value="">
<input type="hidden" id="COMP_ID" name="COMP_ID" value="${param.ID}">
<input type="hidden" id="CON_ID" name="CON_ID" value="${param.CON_ID}">
<input type="hidden" id="ID" name="ID" value="${param.ID}">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="4">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>
<c:set var="w_id" value="${map.INS_ID}"/> 
<c:set var="l_id" value="${sessionScope.userinfo.userId}"/>

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">기술자 보유현황 수정</td>
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
                        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	      	  <tr>
	            <td width="120" class="tbl_field">성명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="EXPERT_NM" name="EXPERT_NM" value="${map.EXPERT_NM}" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">자격(면허)</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="RIGHT_NM" name="RIGHT_NM" value="${map.RIGHT_NM}" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">주요경력</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="10" cols="76" id="MAIN_CAREER" name="MAIN_CAREER" class="input" style="width:557;">${map.MAIN_CAREER}</textarea></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">				
						<c:forEach var="row" items="${list }" varStatus="var">
							<p>
								<input type="hidden" id="FID" name="FID_${var.index }" value="${row.ID }">
								<a href="#this" id="name_${var.index }" name="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index }" name="file_${var.index }"> 
								(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a> (<b>10M이하</b>)
							</p>
						</c:forEach>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a>            
	            </td>
	          </tr>	         
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;">${map.BIGO}</textarea></td>									
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                                                 
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/scms/images/btn_type1_head.gif"></td>
                    <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/scms/images/btn_type1_end.gif"></td>
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
