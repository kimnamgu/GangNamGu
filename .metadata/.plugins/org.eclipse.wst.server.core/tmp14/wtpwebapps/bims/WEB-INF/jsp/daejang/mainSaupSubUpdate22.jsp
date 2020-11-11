<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_mainSaupSubList();
			});
			
			$("#update").on("click", function(e){ //수정버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_updateMainSaupSubData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#del").on("click", function(e){ //삭제버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_deleteMainSaupSubData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
		});
		
	
		function fn_mainSaupSubList(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/mainSaupSubList.do' />");
			
			comSubmit.addParam("MAST_ID", "${param.MAST_ID}");
			comSubmit.addParam("ID", "${param.MAST_ID}");
			comSubmit.addParam("SAUP_NM", "${param.SAUP_NM}");
			comSubmit.addParam("SAUP_DEPT_CD", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT_NM", "${param.SAUP_DEPT_NM}");
			comSubmit.submit();
		}
		
		function fn_updateMainSaupSubData(){
			var comSubmit = new ComSubmit("form1");
			
			
			if($('#SAUP_PROCESS_PERCENT').val() == "")
			{
				alert('2020년 진척률을 입력하세요.');
				$('#SAUP_PROCESS_PERCENT').focus();
				return false();
			}
						
			if($('#SAUP_PUSH_CONTENT').val() == "")
			{
				alert('금주 추진내용을 입력하세요.');
				$('#SAUP_PUSH_CONTENT').focus();
				return false();
			}
			
			if($('#SAUP_NEXT_CONTENT').val() == "")
			{
				alert('다음주 추진계획을 입력하세요.');
				$('#SAUP_NEXT_CONTENT').focus();
				return false();
			}
						
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/updateMainSaupSub.do' />");
				comSubmit.submit();
			}
		}
		

		function fn_deleteMainSaupSubData(){
			var comSubmit = new ComSubmit("form1");
					
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/deleteMainSaupSub.do' />");
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
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${map.INS_DEPT}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="MAST_ID" name="MAST_ID" value="${map.MAST_ID}">
<input type="hidden" id="ID" name="ID" value="${map.ID}">
<input type="hidden" id="SAUP_NM" name="SAUP_NM" value="${param.SAUP_NM}">
<input type="hidden" id="SAUP_DEPT_NM" name="SAUP_DEPT_NM" value="${param.SAUP_DEPT_NM}">
<input type="hidden" id="LST_GB" name="LST_GB" value="${param.LST_GB}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">주요사업 내용</td>
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
				<td class="title"><img src="/bims/images/title_ico.gif" align="absmiddle">진행상황 확인 및 수정</td>
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
	            <td width="120" class="tbl_field">사업명</td>
	            <td width="500" class="tbl_list_left">${param.SAUP_NM}</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">최종수정일</td>
	            <td width="500" class="tbl_list_left">${param.UPD_DATE}</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">입력일</td>
	            <td width="500" class="tbl_list_left">${param.INS_DATE}</td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">현재진척률</td>
	            <td width="500" class="tbl_list_left">	                
	                <input type="text" id="SAUP_PROCESS_PERCENT" name="SAUP_PROCESS_PERCENT" value="${map.SAUP_PROCESS_PERCENT}" class="input" style="width:80;"> (숫자만 입력)	                
	            </td>
	          </tr>	                 
	          <tr>
	            <td width="120" class="tbl_field">지난 추진내용</td>
	            <td width="500" class="tbl_list_left">	                
	                <textarea rows="15" cols="100" id="SAUP_PUSH_CONTENT" name="SAUP_PUSH_CONTENT" class="input" style="width:650;">${map.SAUP_PUSH_CONTENT}</textarea>	                
	            </td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">이번 추진계획</td>
	            <td width="500" class="tbl_list_left">	                
	                <textarea rows="15" cols="100" id="SAUP_NEXT_CONTENT" name="SAUP_NEXT_CONTENT" class="input" style="width:650;">${map.SAUP_NEXT_CONTENT}</textarea>	                
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
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type1_head.gif"></td>
                    <td background="/bims/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/bims/images/btn_type1_end.gif"></td>
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
