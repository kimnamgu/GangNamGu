<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 전자결재기안대기전송
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
<head>
<title>전자결재 기안문 작성</title>
<base target="_self">
<jsp:include page="/include/processing.jsp" flush="true"/>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/common.js"></script>
<script language="javascript">
function click_cancel() {
	if ( confirm("기안작성을 취소하시겠습니까?") ) {
		window.close();
		window.dialogArguments.close();
	}
}

function click_send() {
	processingShow();
	
	setTimeout(function() {
		var fck = FCKeditorAPI.GetInstance("formhtml");
		var formhtml = document.frames[1];	//웹에디터
		var formtitle = document.forms[0].formtitle;
		var formcomment = document.forms[0].formcomment;
		var ea_id = document.forms[0].ea_id;
		var attach = document.forms[0].attach;

		var content = fck.GetXHTML(true).replace(/&nbsp;/g, "");
		content = content.replace(/<br>/g, "").replace(/<\/br>/g, "");
		content = content.replace(/<p>/g, "").replace(/<\/p>/g, "");

		if (formtitle.value.trim() == "") {
			processingHide();
			alert("기안명을 입력하세요");
			formtitle.focus();
			return;
		}
		
		if (formcomment.value.trim() == "") {
			processingHide();
			alert("본문을 입력하세요");
			formcomment.focus();
			return;
		}
		
		if ( (ea_id.value = ea_id.value.trim()) == "") {
			processingHide();
			alert("전자결재아이디를 입력하세요");
			ea_id.disabled = false;
			ea_id.focus();
			return;
		}

		if (content.trim() == "") {
			processingHide();
			if (confirm("붙임내용 없이 전송하시겠습니까?")) {
				ea_id.disabled = false;
				attach.value = "false";
				document.forms[0].action = "/exchangeSend.do";
				document.forms[0].submit();
			} else {
				formhtml.focus();
			}
			return;
		} else {
			processingHide();
			if (confirm("기안대기로 전송하시겠습니까?")) {
				ea_id.disabled = false;
				document.forms[0].action = "/exchangeSend.do";
				document.forms[0].submit();
			}
		}
	}, 0);
}

function back_opener(){
    window.dialogArguments.move_formatLineView();
    close();
}
</script>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/draftDocument">
	<html:hidden property="type"/><html:hidden property="reqformno"/><html:hidden property="reqseq"/>
	<html:hidden property="sysdocno"/><html:hidden property="attach"/>
<table width="780" border="0" cellspacing="0" cellpadding="0" height="600">
  <tr> 
    <td valign="top"> 
      <table width="750" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
        	<td height="15"></td>
        </tr>
        <tr> 
          <td> 
            <table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""> 
                </td>
                <td><b>기안명</b></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td style="padding:0 0 0 20;"><html:text property="formtitle" style="width:100%"/></td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td><b>본문</b></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td  style="padding:0 0 0 20;">
          	<textarea name="formcomment" style="width:100%; height:100">
<%
String formtitle = ((nexti.ejms.format.form.FormatForm)request.getAttribute("formatForm")).getFormtitle();
String comment = "";
comment += "\t1. \n";
comment += "\t2. [" + formtitle + "] 업무와 관련하여 붙임과 같이 제출합니다.\n";
comment += "\n";
comment += "\t붙임. 제출자료 각 1부. 끝.\n";
if ( formtitle.indexOf("행정전자") != -1 || formtitle.indexOf("행정 전자") != -1 ) {
	if ( "동작3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t○○○○○ 사용을 위하여 행정전자서명 발급을 신청하니, 처리하여 주시기 바랍니다.\n";
		comment += "\n";
		comment += "\t붙임 : 행정전자서명 신청서. 끝.\n";
	} else if ( "성동3030000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t○○○○○ 업무에 활용하고자 행정전자 서명 신청서를 붙임과 같이 제출하오니 발급하여 주시기 바랍니다.\n";
		comment += "\n";
		comment += "\t붙임 : 행정전자서명 신청서 1부. 끝.\n";
	} else if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		comment = "";
		comment += "\t[" + formtitle + "] 업무와 관련하여 붙임과 같이 제출합니다.\n";
		comment += "\n";
		if ( 2 == ((nexti.ejms.format.form.FormatForm)request.getAttribute("formatForm")).getType() ) {
			comment += "\t붙임. 신청서 1부. 끝.\n";
		} else {
			comment += "\t붙임. 제출자료 1부. 끝.\n";
		}
	} else if ( "수원347000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
        comment = "";
        comment += "\t 행정전자셔명을 붙임과 같이 신청하오니 처리하여 주시기 바랍니다.\n";
        comment += "\n";
        comment += "붙임  행정전자서명신청서(개인용) 1부. 끝.";
    }
}
out.print(comment);
%>
          	</textarea>
          </td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td><b>붙임내용</b></td>
                <% if ( "원주4190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
                    <td width="5"></td>
                    <td><font color="#FF5656">
                      <b>- 교육대상자 명단 등 제출자료는 아래 에디터에서 직접 편집하시지 마세요.<br>
                      - [취합양식자료]에서 작성하세요. </b></font> <a href="javascript:back_opener()"><font class="result_no">(이동하기)</font></a>
                    </td>
                <% } %>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td  style="padding:0 0 0 20;"><html:textarea property="formhtml"/></td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr>
        	<td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td <% if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='visibility:hidden'"); %> width="20"><img src="/images/right-.gif" align="absmiddle" alt=""></td>
                <td <% if ("중구30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %> style='visibility:none' width="250"><font color="red"><b>기안자의 주민등록번호 13자리(-제외)&nbsp;&nbsp;:&nbsp;&nbsp;</b></font></td>
                <td <% } else if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %> style='visibility:hidden' width="105"><b>전자결재아이디 :<b></td>
								<td <% } else { %> width="105"><b>전자결재아이디 :<b></td>
								<% } %>
								<td <% if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='visibility:hidden'"); %>>
                <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
									<input type="text" name="secret" style="width:35%;text-align:center" value="<bean:write name="formatForm" property="ea_id"/>">
									<script>
										try {
											var sec = document.forms[0].secret;
											sec.style.position = "absolute";
											sec.value = sec.value.substring(0,6) + "●●●●●●●";
											sec.onchange = function() {
												sec.value = sec.value.replace(/●/g, '');
												document.forms[0].ea_id.value = sec.value;
											}
										} catch ( exception ) {}
									</script>
								<% } %>
                                    <% if ( "수원6460000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) { %>
                                        <input type="text" name="ea_id" style="width:35%;text-align:center" value="<%=session.getAttribute("user_id") %>"/>
                                    <% } else { %>
									<logic:empty name="formatForm" property="ea_id">
										<input type="text" name="ea_id" style="width:35%;text-align:center">
									</logic:empty>
									<logic:notEmpty name="formatForm" property="ea_id">
										<input type="text" name="ea_id" style="width:35%;text-align:center" value="<bean:write name="formatForm" property="ea_id"/>" 
											<% if ( "인천6280000, 성남3780000, 서초3210000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) 
												out.print("disabled"); 
											%>>
									</logic:notEmpty>
                                    <% } %>
									<font color="red">
										<% if ( "동작".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
											<b>☜ 전자결재아이디는 주민등록번호('-' 포함)</b>
										<% } else if ( "중구30100000000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
											<%--<b>☜ 전송 전 반드시 확인</b>--%>
										<% } else { %>
											<b>☜ 전송 전 반드시 확인</b>
										<% } %>
									</font>
                </td>
                <td width="185" align="right">
                	<a href="javascript:click_send()"><img src="/images/common/btn_sendyes.gif" align="absmiddle" alt="기안대기전송"></a>
			          	<a href="javascript:click_cancel()"><img src="/images/common/btn_sendno.gif" align="absmiddle" alt="기안취소"></a>
								</td>
							</tr>
            </table>
          </td>
        </tr>
				<tr>
        	<td height="10"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>
</body>
<script>
<% if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
	var fh = document.forms[0].formhtml;
	fh.value = fh.value.replace("<meta http-equiv='Content-Type' content='text/html;charset=euc-kr'>", "");
	fh.value = fh.value.replace('<meta http-equiv="Content-Type" content="text/html;charset=euc-kr">', '');
<% } %>
var oFCKeditor = new FCKeditor('formhtml');
oFCKeditor.ToolbarSet = 'ejms_formMake';
oFCKeditor.Height = 450;
oFCKeditor.ReplaceTextarea();
</script>
</html>