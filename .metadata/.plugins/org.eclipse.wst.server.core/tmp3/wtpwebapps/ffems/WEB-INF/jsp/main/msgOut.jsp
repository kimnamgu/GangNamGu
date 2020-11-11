<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>공약·역점 사업관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>

<script type="text/javascript">
		var flag = "${flag}";
		
		$(document).ready(function(){
			
			if(flag == '1')
			{
				$("#msg").html('사용승인이 안되어 있습니다!!<br><br>관리자에게 연락하여 [사용승인] 받고 이용하시길 바랍니다.(☎ 5452)');
			}
			else if(flag == '2'){
				$("#msg").html('등록이 완료되었습니다.<br><br>관리자에게 연락하여 [사용승인] 받고  다시 접속하시길 바랍니다.(☎ 5452)');
			}
			else if(flag == '3'){
				$("#msg").html('아이디가 틀립니다!!<br><br>다시 확인하시고 로그인 하시길 바랍니다.');
			}
			else if(flag == '4'){
				$("#msg").html('패스워드가 틀립니다!!<br><br>다시 확인하시고 로그인 하시길 바랍니다.');
			}
			else if(flag == '5'){
				$("#msg").html('파일업로드 에러!!<br><br>다시 확인하시고 업로드하시길 바랍니다.');
			}
			else if(flag == '6'){
				$("#msg").html('파일업로드 성공!!');
			}
			
			$("#click").on("click", function(e){ //작성하기 버튼
				location.href = "/ffems/"
			});
		});
		
	</script>
</head>

<body>

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/ffems/images/popup_title.gif" align="absmiddle">결과메세지</td>
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
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/ffems/images/title_ico.gif" align="absmiddle">메세지</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	         
			 <tr>
	            <td width="600" class="tbl_field_120"><b></b><span id="msg"></span></b></td>	            		
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
                    <td><img src="/ffems/images/btn_type0_head.gif"></td>
                    <td background="/ffems/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="click"><font color="white">확인</font></a></td>
                    <td><img src="/ffems/images/btn_type0_end.gif"></td>
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
</body>
</html>
