<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<title>아웃소싱현황관리 시스템</title>

<script type='text/javascript'>
$(document).ready(function(){
	
	$("ul.tabs li:first").attr("id", "current");
	$("ul.tabs span:first").addClass("active").css("font-weight", "bold");
	
	$("ul.tabs li").click(function () {                
        $("ul.tabs li").removeAttr("id", "");
        $(this).attr("id", "current");
    });
    
    $("ul.tabs span").click(function () {
        $("ul.tabs span").removeClass("active").css("font-weight", "");
        $(this).addClass("active").css("font-weight", "bold");    	
    });
	
    $('a').focus(function(){$(this).blur();});
    
    $("#jaryo").on("click", function(e){ //수정하기 버튼
		e.preventDefault();
		fn_boardList1();
	});
        
    $("#gongji").on("click", function(e){ //수정하기 버튼
		e.preventDefault();
		fn_boardList2();
	});
    
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

function fn_boardList1(){
	
	var comSubmit = new ComSubmit();
	
	comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
	comSubmit.addParam("bdId", "1");
	comSubmit.target("content");
	comSubmit.submit();
}

function fn_boardList2(){
	
	var comSubmit = new ComSubmit();
	
	comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");	
	comSubmit.addParam("bdId", "2");
	comSubmit.target("content");
	comSubmit.submit();
}
</script>

<style type="text/css">
<!--
    body {
        margin:0;
        padding:0;
        font: bold 11px/1.5em Verdana;
}

h2 {
        font: bold 17px Verdana, Arial, Helvetica, sans-serif;
        color: #000;
        margin: 0px;
        padding: 0px 0px 0px 15px;
}


img {
border: none;
outline: none;
}

/*- Menu Tabs 5--------------------------- */

    #tabs5 {
      float:left;
      width:100%;
      background:#E3ECF3;
      font-size:93%;
      line-height:normal;

      }
    #tabs5 ul {
          margin:0;
          padding:10px 10px 0 50px;
          list-style:none;
      }
    #tabs5 li {
      display:inline;
      margin:0;
      padding:0;
      }
    #tabs5 a {
      float:left;
      background:url("/oms/images/tableft5.gif") no-repeat left top;
      margin:1;
      padding:0 0 0 4px;
      text-decoration:none;
      }
    #tabs5 a span {
      float:left;
      display:block;
      background:url("/oms/images/tabright5.gif") no-repeat right top;
      padding:5px 15px 5px 6px;
      color:#FFF;
      }
    /* Commented Backslash Hack hides rule from IE5-Mac \*/
    #tabs5 a span {float:none;}
    /* End IE5-Mac hack */
    #tabs5 a:hover span {
      color:#FFF;
      }
    #tabs5 a:hover {
      background-position:0% -42px;
      }
    #tabs5 a:hover span {
      background-position:100% -42px;
      }

      #tabs5 #current a {
              background-position:0% -42px;
      }
      #tabs5 #current a span {
              background-position:100% -42px;
      }
-->
</style>


</head>
<body>
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="pupup_frame">
    
     <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
     <tr>
        <td><img src="/oms/images/main/main_logo.jpg"></td>
        <td width="30">&nbsp;</td>     
        <td><u>안녕하세요. ${sessionScope.userinfo.deptName} ${sessionScope.userinfo.userName}님</u></td>
      </tr>
     </table>
	</td>
  </tr>
<tr>
<td>
	<div id="tabs5">
		<ul class="tabs">
		<!-- CSS Tabs -->
		<li><a href="/oms/main.do"><span>HOME</span></a></li>
		<li><a href="/oms/officework/officeworkRegList.do" target="content"><span id="a1">위탁사무등록</span></a></li>
		<li><a href="/oms/officework/trustOworkStatusList.do" target="content"><span>위탁사무현황조회</span></a></li>
		<li><a href="#this"><span id="jaryo">자료실</span></a></li>
		<li><a href="#this"><span id="gongji">공지사항</span></a></li>
		<c:set var="right" value="${sessionScope.userinfo.userright}" />
		<c:choose>
	    <c:when test="${right eq 'A'}">
	    <li><a href="/oms/common/idApproveList.do" target="content"><span>관리자</span></a></li>	   	
	   	</c:when>
		</c:choose> 		
		<li><a href="/oms/logout.do"><span>로그아웃</span></a></li>	
	 	</ul>
	</div>
	</td>	
</tr>
</table>  
<iframe name="content" src="/oms/main_btm.do" width="100%" height="648" scrolling="auto" align="middle" frameborder="0"></iframe>
<iframe name="content" src="/oms/bottom.do" width="100%" height="80" scrolling="auto" align="middle" frameborder="0"></iframe>
<%@ include file="/WEB-INF/include/include-body.jspf" %>               
</body>
</html>