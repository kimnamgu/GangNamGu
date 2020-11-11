<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>스마트 출입관리 출입내역 조회</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#login").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_login();
			});
			
			
		    $("input").keydown(function (e) {
	                if (e.which == 13) {    //enter
	                	fn_login();
	                }
	        });
		});
		
		
		
		
		function fn_login(){
			
//     		if($('#USER_ID').val() != '10187140' && $('#USER_ID').val() != 'alias' && $('#USER_ID').val() != 'epvmzhs233') // 정찬식과장님, 조재열, 서보형
//     		{
//     			alert("스마트 출입관리 지정관리자가 아닙니다.");
//     			return false;
//     		}
			
			var comSubmit = new ComSubmit("form1");
			comSubmit.target("_top");				
			comSubmit.setUrl("<c:url value='/common/visitorLogin.do' />");
			comSubmit.submit();
		}
	</script>
</head>

<body>
	<div class="page-wrapper">
        <div class="page-content--bge5">
            <div class="container">
                <div class="login-wrap">
                    <div class="login-content">
                        <div class="login-logo">
							<a href="#this" class="btn" id="logout"><img src="/service/images/popup_title.gif" align="absmiddle"></a>스마트 출입관리 출입내역 조회
                        </div>
                        <div class="login-form">
                            <form id="form1" name="form1" method="post">
                            <input type="hidden" id="userid" name="userid" value="">
                                <div class="form-group">
                                    <label>아이디</label>
                                    <input class="au-input au-input--full" type="text" id="USER_ID" name="USER_ID" placeholder="아이디">
                                </div>
                                <div class="form-group">
                                    <label>비밀번호</label>
                                    <input class="au-input au-input--full" type="password" id="PASS_WD" name="PASS_WD" placeholder="Password">
                                </div>
                                <a href="#this" width="100%" id="login"><button class="au-btn au-btn--block au-btn--green m-b-20" type="button" >로그인</button></a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
