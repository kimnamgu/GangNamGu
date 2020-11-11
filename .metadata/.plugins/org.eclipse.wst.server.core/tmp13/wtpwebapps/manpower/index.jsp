<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>공공근로 인력관리현황</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			if(NvlStr($("#userid").val()) != "")
			{	
				document.location.href = "/manpower/body.do";
			}
			
			$("#login").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_login();
			});
			
			
		    $("input").keydown(function (event) {
	                if (event.which == 13) {    //enter
	                	fn_login();
	                }
	        });
 			
		});
		
		
		
		
		function fn_login(){
/* 			$('#USER_ID').val('bluegreen');
			$('#PASS_WD').val('111111'); */
			
			var comSubmit = new ComSubmit("form1");
			comSubmit.target("_top");				
			comSubmit.setUrl("<c:url value='/common/login.do' />");
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
							<a href="#this" class="btn" id="logout"><img src="/manpower/images/popup_title.gif" align="absmiddle"></a>공공근로 인력관리현황
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
