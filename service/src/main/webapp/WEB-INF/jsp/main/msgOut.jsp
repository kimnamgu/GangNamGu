<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업 관리시스템</title>
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
			
			
			$("#click").on("click", function(e){ //작성하기 버튼
				location.href = "/service/"
			});
		});
		
	</script>
</head>

<body>
	<div class="page-wrapper">
        <div class="page-content--bge5">
            <div class="container">
                <div class="login-wrap">
                    <div class="login-content">
                        <div class="login-logo">
							<img src="/service/images/popup_title.gif" align="absmiddle">결과메세지
                        </div>
                        <div class="login-form">
                        	<div class="form-group">
								<img src="/service/images/title_ico.gif" align="absmiddle">메세지
								<br>
								<br>
								<b><span id="msg"></span></b>
							</div>
							<a href="#this" width="100%" id="click"><button class="au-btn au-btn--block au-btn--blue m-b-20" type="button" >확인</button></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
