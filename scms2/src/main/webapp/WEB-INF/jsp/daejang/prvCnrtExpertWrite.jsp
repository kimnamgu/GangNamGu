<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>용역업체관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
		
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtCompRecordList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				fn_insertPrvCnrtExpert();				
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
		});
		
		function fn_prvCnrtCompRecordList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.submit();
		}
		
	
		
	
		
		
		function fn_insertPrvCnrtExpert(){
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
			
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtExpert.do' />");
				comSubmit.addParam("ID", $("#COMP_ID").val());
				comSubmit.submit();
			}
		}
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'> <a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
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
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="RIGHT_CODE" name="RIGHT_CODE" value="">
<input type="hidden" id="COMP_ID" name="COMP_ID" value="${param.ID}">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="4">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">기술자 보유현황 등록</td>
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
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="EXPERT_NM" name="EXPERT_NM" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">자격(면허)</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="RIGHT_NM" name="RIGHT_NM" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">주요경력</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="10" cols="76" id="MAIN_CAREER" name="MAIN_CAREER" class="input" style="width:557;"></textarea></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a> (<b>10M이하</b>)
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)           
	            </td>
	          </tr>	         
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;"></textarea></td>									
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
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
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
