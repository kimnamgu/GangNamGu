<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����������
 * ����:
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
<!-- ž���� ����-->
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<p>
  <!-- ž���� ��-->
  <!-- �ΰ� ����-->
  <!--  �ΰ� ��-->
  <!-- '�������̺�' ����-->
</p>
<p>&nbsp;</p>
<table width="475" height="240" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" > 
      <table width="475" height="300" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/error_back.gif">
        <tr> 
          <td width="152" rowspan="3"><img src="/images/error_map.gif" width="152" height="300" border="0" usemap="#Map" alt=""></td>
          <td width="278" height="105" align="center"><span class="styledb">������ �߻��Ͽ����ϴ�.</span></td>
          <td width="45">&nbsp;</td>
        </tr>        
        <tr> 
          <td class="style1 " >
						���� Server�� ������ �߻��Ѱ� �����ϴ�.<br>
						����� �ٽ� �õ�(<b>F5Ű</b>)�� �����ðų� �����ڿ��� ������ �ֽʽÿ�. �ٷ� �ذ��� �帮�ڽ��ϴ�.<br>
						������ �߻��� �������� �ּ��� ���Ͽ� �ٷ� <b>������ġ</b> �ϰڽ��ϴ�. �����մϴ�.</td>
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
		  		<!-- ����/���� ��ư ���� -->
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- '�������̺�' ��-->
<!-- ���鼳�� ����-->
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
      <td height="24" bgcolor="F8F4EC"><b>�����󼼳���</b> </td>
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
<!-- ���鼳�� ��-->
<!-- �����󼼳��� ���� -->

<!-- �����󼼳��� �� -->
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