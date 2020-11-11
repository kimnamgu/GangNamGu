<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#update").on("click", function(e){ //수정하기 버튼
				e.preventDefault();
				fn_openBoardUpdate();
			});
			
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
		});
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegList.do' />");
			comSubmit.submit();
		}
		
		function fn_openBoardUpdate(){
			var idx = "${map.IDX}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/openBoardUpdate.do' />");
			comSubmit.addParam("IDX", idx);
			comSubmit.submit();
		}
		
	</script>
</head>

<body>


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_fixed">
  <tr>
    <td>
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">위탁사무등록</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  <tr>



    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            &nbsp;
            </td>
          </tr>
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        <!-- -------------- 버튼 끝 --------------  -->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="tbl_line_s"></td>
          </tr>
        </table>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">위탁사무명 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.USER_ID}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">담당부서 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.USER_NAME}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁유형 <font class="font_star">*</font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.DEPT_ID}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">위탁근거 법규</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.DEPT_NAME}</td>
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">위탁내용</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.CLASS_ID}</td>
	          </tr>	
			  
			  <tr>
	            <td width="120" class="tbl_field">시설위치</td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.CLASS_NAME}</td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">시설규모</td>
	            <td colspan="3" width="607" class="tbl_list_left"></td>
	          </tr>	
			  <tr>
	            <td width="120" class="tbl_field">시설준공일</td>
	            <td width="243" class="tbl_list_left"></td>
				<td width="120" class="tbl_field">최초위탁일</td>
	            <td width="244" class="tbl_list_left"></td>							
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용대상</td>
	            <td colspan="3" width="607" class="tbl_list_left"></td>
	          </tr>
			  <tr>
	            <td width="120" class="tbl_field">이용자수</td>
	            <td width="243" class="tbl_list_left"></td>
				<td width="120" class="tbl_field">위탁중지일</td>
	            <td width="244" class="tbl_list_left"></td>							
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
                    <td><img src="/oms/images/btn_type1_head.gif"></td>
                    <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#" onClick="onChangeMemoBody(); return(false);" ><span id="changeBodyText2">수정</span></a></td>
                    <td><img src="/oms/images/btn_type1_end.gif"></td>
                  </tr>
                </table>
                </td>
				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/oms/images/btn_type1_head.gif"></td>
                    <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#" onClick="onChangeMemoBody(); return(false);" ><span id="changeBodyText2">삭제</span></a></td>
                    <td><img src="/oms/images/btn_type1_end.gif"></td>
                  </tr>
                </table>
                </td>
							
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/oms/images/btn_type1_head.gif"></td>
                    <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/oms/images/btn_type1_end.gif"></td>
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
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
