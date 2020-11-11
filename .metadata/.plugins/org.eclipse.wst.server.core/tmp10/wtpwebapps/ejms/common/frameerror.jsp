<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 에러페이지
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<html>
<head>
<style type="text/css">
<!--
.styledb {
	font-size: 15px;
	font-weight: bold;
	color: #d26944;
}
-->
</style>
<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<!-- 탑여백 시작-->
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<p>
  <!-- 탑여백 끝-->
  <!-- 로고 시작-->
  <!--  로고 끝-->
  <!-- '에러테이블' 시작-->
</p>
<p>&nbsp;</p>
<table width="475" height="240" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" > 
      <table width="475" height="300" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/error_back.gif">
        <tr> 
          <td width="152" rowspan="3"><img src="/images/error_map.gif" width="152" height="300" border="0" usemap="#Map" alt=""></td>
          <td width="278" height="105" align="center"><span class="styledb">에러가 발생하였습니다.</span></td>
          <td width="45">&nbsp;</td>
        </tr>        
        <tr> 
          <td class="style1 " >
						내부 Server에 에러가 발생한것 같습니다.<br>
						잠시후 다시 시도(<b>F5키</b>)를 누르시거나 관리자에게 연락을 주십시오. 바로 해결해 드리겠습니다.<br>
						에러가 발생한 페이지는 최선을 다하여 바로 <b>수정조치</b> 하겠습니다. 감사합니다.</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td height="70" align="center">
          	<table width="225" border="0" cellpadding="0" cellspacing="0">
	            <tr>
	              <td width="110"><a href="javascript:parent.location.href='/main.do'"><img src="/images/error_01.gif" width="110" height="25" border="0" alt=""></a></td>
	              <td width="5">&nbsp;</td>
	              <td width="110"><a href="javascript:parent.history.back()"><img src="/images/error_02.gif" width="110" height="25" border="0" alt=""></a></td>
	            </tr>
	          </table>
          </td>
		  		<td>&nbsp;</td>
		  		<!-- 이전/메인 버튼 시작 -->
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- '에러테이블' 끝-->
<!-- 여백설정 시작-->
<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr height>
    <td>&nbsp;</td>
  </tr>
</table>
<DIV id="Layer" style="visibility:hidden;overflow:hidden">
  <table width="500" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#999999">
    <tr>
      <td height="2" bgcolor="DDC89D"></td>
    </tr>
    <tr>
      <td height="24" bgcolor="F8F4EC"><b>오류상세내역</b> </td>
    </tr>
    <tr>
      <td height="1" bgcolor="DDC89D"></td>
    </tr>
		<tr>
      <td bgcolor="#FFFFFF" class="bbs_coment07">     
				<%
					java.lang.Exception e=(java.lang.Exception)request.getAttribute("exception"); 
					e.printStackTrace(); 
					StackTraceElement[] trace=e.getStackTrace(); 
					StringBuffer sb=new StringBuffer(); 
					sb.append(e.toString()); 
					sb.append("<p>");
					for(int i=0; i<trace.length; i++){
						sb.append(trace[i]);
						sb.append("<br>"); 			
					}
					sb.append("</p>");
					out.println(sb.toString());
				%>			
			</td>
    </tr>
		<tr>
      <td height="2" bgcolor="DDC89D"></td>
    </tr>
  </table>
</div>
<!-- 여백설정 끝-->
<!-- 오류상세내역 시작 -->

<!-- 오류상세내역 끝 -->
<table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

<map name="Map" title=""><area shape="rect" coords="27,185,115,200" href="#" onClick="MM_showHideLayers()" alt="" title=""></map>
</body>
<script type="text/JavaScript">
var clickyn = "n";
var height = Layer.style.height;
Layer.style.height = "10px";
function MM_showHideLayers() { //v6.0
 	 if (clickyn == "y"){   
    Layer.style.visibility="hidden";
    Layer.style.height = "10px";
    clickyn = "n";
   }else {
    Layer.style.visibility="visible";
    Layer.style.height = height;
    clickyn = "y"
   }
}
</script>
</html>