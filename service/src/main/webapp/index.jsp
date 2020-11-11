<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>민원서류발급 사전예약신청 조회</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			
			if(NvlStr($("#userid").val()) != "")
			{	
				document.location.href = "/service/docIssueReserveList.do";
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
		    
		    
		    $("#test1").on("click", function(e){ //글쓰기 버튼
				//e.preventDefault();			
				//fn_mainSaupTest();
				fn_getDocumentKindList();
			});
			
			$("#test2").on("click", function(e){ //글쓰기 버튼
				//e.preventDefault();			
				//fn_mainSaupTest();
				fn_getDocIssueReserveDetail();
			});

			
		});
		
		
		
		
		function fn_login(){
			var comSubmit = new ComSubmit("form1");
			comSubmit.target("_top");				
			comSubmit.setUrl("<c:url value='/common/login.do' />");
			comSubmit.submit();
		}
		
		
		function fn_getDocumentKindList(){
	    	
	    	var jsnobj = new Object();
	    
	    	jsnobj.ITEM_GB = "";
	    	jsnobj.PAGE_INDEX = "";
	    	jsnobj.PAGE_ROW = "10";
	   	
			var jsonData = JSON.stringify(jsnobj);
			alert(jsonData);
			
			$.ajax({
			        type:"POST",			        
			        url:"/service/api/v1/getDocumentKindList.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
						alert("success " + "data=[" + JSON.stringify(data) + "]" );
						//alert("success!!");
						
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

	    }
		
		
		function fn_getDocIssueReserveDetail(){
	    	
	    	var jsnobj = new Object();	
	    	jsnobj.ID = $("#userid").val();  	
			var jsonData = JSON.stringify(jsnobj);
			alert(jsonData);
			
			$.ajax({
			        type:"POST",
			        url:"/service/api/v1/getDocIssueReserveDetail.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
						alert("success " + "data=[" + JSON.stringify(data) + "]" );
						//alert("success!!");
						
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

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
							<a href="#this" class="btn" id="logout"><img src="/service/images/popup_title.gif" align="absmiddle"></a>민원서류발급 사전예약신청 조회
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
