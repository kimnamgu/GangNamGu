<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서결재선지정 팝업
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<html>
<%
	String reqformno = "0";
	String reqseq = "0";
	String retid = "";
	String deptnm = "";
	
	if(request.getAttribute("reqformno") != null) {
		reqformno = request.getAttribute("reqformno").toString();
	}
	
	if(request.getAttribute("reqseq") != null) {
		reqseq = request.getAttribute("reqseq").toString();
	}
	
	if(request.getAttribute("retid") != null) {
		retid = request.getAttribute("retid").toString();
	}	
	
	if(request.getAttribute("deptnm") != null) {
		deptnm = request.getAttribute("deptnm").toString();
	}	
%>
<head>
<title>결재선 지정</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/dhtmlxtree/dhtmlxtree.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/dhtmlxtree/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxtree/dhtmlxtree.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script type="text/javascript" src="/script/commreqform.js"></script>
<script type="text/javascript" src="/script/dragdrop.js"></script>
<script src="/script/common.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<form name="frm">
<input type="hidden" name="reqformno" value="<%=reqformno %>">
<input type="hidden" name="reqseq" value="<%=reqseq %>">
<input type="hidden" name="retid" value="<%=retid %>">
<table width="620" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20">&nbsp;</td>
    <td>
			<table width="580" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="10" align="right"></td>
				</tr>
				<tr>
	        <td> <span class="style2">결재선지정</span></td>
	      </tr>
	      <tr>
	        <td height="10"></td>
	      </tr>
	      <tr>
					<td>				
						<table width="580" border="0" cellspacing="0" cellpadding="0">
							<tr>
	            	<td width="250"><table width="250" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="110"><a href="#"><img src="" alt="조직도"></a></td>
	                  <td valign="bottom" class="style1"><%=deptnm %></td>
	                </tr>
              	</table></td>
	            	<td width="80" rowspan="2" align="center">
									<table width="70" border="0" cellspacing="0" cellpadding="0">
										<tr>
				    					<td align="center"><input type="radio" name="gubun" value="1" style="border:0;background-color:transparent;" checked>
				      					<span class="style3"><br />검토</span>
				      				</td>
				  					</tr>
									  <tr>
									    <td align="center"><input type="radio" name="gubun" value="2" style="border:0;background-color:transparent;">
									      <br />
									      <span class="style3">최종승인</span>
									  	</td>
									  </tr>
									  <tr>
						          <td height="3" align="center" ></td>
						        </tr>
									  <tr>
	                		<td align="center"><img src="" alt="추가" onclick="addUser()"></td>
	              		</tr>
			              <tr>
			                <td height="3" align="center"></td>
			              </tr>
				  					<tr>
	                		<td align="center"><img src="" alt="삭제" onclick="delUser()"></td>
	              		</tr>
									</table>
	            	</td>
	            	<td width="250" background=""><img src="" alt="검토"></td>
	          	</tr>
	          	<tr>
	            	<td width="250" valign="top">
	            		<table width="250" height="250" cellpadding="0"  cellspacing="0">
			              <tr>
			                <td width="1" bgcolor="#CCCCCC"></td>
			                <td valign="top" style="padding-left:10px">
			                	<div id="treebox_user" style="background-color:#f5f5f5; overflow:auto;"></div>
			                </td>
			                <td width="1" bgcolor="#CCCCCC"></td>
			              </tr>
			              <tr>
			                <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
			              </tr>
	            		</table>
	            	</td>
	            	<td width="250" valign="top">
	            		<table id="drag_drop" width="250" height="250" cellpadding="0"  cellspacing="0">
			              <tr>
			                <td width="1" bgcolor="#CCCCCC"></td>
			                <td height="110" valign="top" >
								        <table  width="248" height="110" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td>
								              <select id="gumTo" name="gumTo" class="text_input" style="width:248; height:110;" multiple="multiple" ondblclick="delUser(this)" onmousedown="d = new drag(this);" onmouseup="d.drop(this.form.sungIn);" onmouseout="if (typeof d != 'undefined') d.setIndex();">
								              </select>
								            </td>
								          </tr>
								        </table>
								      </td>
			                <td width="1" bgcolor="#CCCCCC"></td>
			              </tr>
			              <tr>
			                <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
			              </tr>
			              <tr>
			                <td height="28" colspan="3" valign="bottom" ><img src="" alt="최종승인"></td>
			              </tr>
			              <tr>
			                <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
			              </tr>
			              <tr>
			                <td width="1" bgcolor="#CCCCCC"></td>
			                <td height="110" valign="top" >
								        <table  width="248" height="110" border="0" cellpadding="0" cellspacing="0">
								          <tr>
								            <td>
								              <select id="sungIn" name="sungIn" class="text_input" style="width:248; height:110;" multiple="multiple" ondblclick="delUser(this)" onmousedown="d = new drag(this);" onmouseup="d.drop(this.form.gumTo);" onmouseout="if (typeof d != 'undefined') d.setIndex();">
								              </select>
								            </td>
								          </tr>
								        </table>
								      </td>
			                <td width="1" bgcolor="#CCCCCC"></td>
			              </tr>
			              <tr>
			                <td height="1" colspan="3" bgcolor="#CCCCCC"></td>
			              </tr>
	            		</table>
	            	</td>
	          	</tr>
		          <tr>
		            <td height="3" colspan="3" align="right"></td>
		          </tr>
							<tr>
	            	<td colspan="3" align="right">
									<table width="100" cellspacing="0" cellpadding="0">
									  <tr>
			                <td><img src="" alt="확인" onclick="validation()"></td>
			                <td width="3">&nbsp;</td>
			                <td width="48"><img src="" alt="취소" onclick="cancel()"></td>
			              </tr>
	            		</table>
								</td>
							</tr>
							<tr>
								<td height="3" colspan="3" align="right"></td>
			       	</tr>			       	
						</table>					
      		</td>
	  		</tr>
    	</table>
		</td>
    <td width="20">&nbsp;</td>
	</tr>
</table>
</form>
<script language="Javascript" type="text/javascript">
loadDept();
viewUser();
</script>
</body>
</html>

