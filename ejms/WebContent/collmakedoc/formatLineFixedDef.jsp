<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 행추가형,고정양식형 양식정의
 * 설명:
--%>
<%@ page contentType="text/html;charset=EUC-KR" %>
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
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>양식정의</title>
<base target="_self">

<jsp:include page="/include/processing.jsp" flush="true"/>

<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/common.js"></script>
<script language="javascript">	
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/gi, "");
	}
	
	function setModifyMode() {
		var type = document.forms[0].type;
		var formkind = document.forms[0].formkind;
		
		if(type.value == "5" || type.value == "6") {
			for(var i = 0; i < formkind.length; i++) {
				if(formkind[i].checked == false) {
					formkind[i].disabled = true;
					document.getElementById("l" + (i + 1)).disabled = true;
					document.getElementById("l" + (i + 1)).onmousedown = null;
				}
			}
		}
	}
	
	function click_cancel() {
		if(confirm("작업을 취소하시겠습니까?") == true)
			window.close();
	}

	function click_formchange(actionpath) {	
		var fck = FCKeditorAPI.GetInstance("formhtml");
		var formhtml = document.forms[0].formhtml;
		
		if(event.srcElement.value == "03" && formhtml.value.trim() != fck.GetXHTML(true).trim()) {
			if(confirm("작성된 양식을 버리시겠습니까?") == false) {
				return;
			}
		}
		
		document.forms[0].action = actionpath;
		processingShow();
		document.forms[0].submit();
	}
		
	function click_prev(actionpath1, actionpath2, actionpath3) {
		var fck = FCKeditorAPI.GetInstance("formhtml");
		var formhtml = document.forms[0].formhtml;
		var type = document.forms[0].type;
		var actionpath;

		if(formhtml.value.trim() != fck.GetXHTML(true).trim()) {
			if(confirm("작성된 양식을 버리시겠습니까?") == false) {
				return;
			}
		}
		
		if(type.value == "1")
			actionpath = actionpath1;
		else if(type.value == "2")
			actionpath = actionpath2;
		else if(type.value == "3")
			actionpath = actionpath3;
	
		document.forms[0].action = actionpath;
		processingShow();
		document.forms[0].submit();
	}

	function click_next(actionpath1, actionpath2) {
		processingShow();
		
		setTimeout(function() {
			var fck = FCKeditorAPI.GetInstance("formhtml");
			var formhtml = document.frames[1];	//웹에디터
			var formkind = document.forms[0].formkind;
			var actionpath;
			
			if(fck.GetXHTML(true).toLowerCase().indexOf("<table") == -1
					|| fck.GetXHTML(true).toLowerCase().indexOf("</table>") == -1) {
				processingHide();
				alert("양식을 작성하세요.")
				formhtml.focus();
				return;
			}

			bgProcFrame.document.write(fck.GetXHTML(true));
			var tbl = bgProcFrame.document.getElementsByTagName("table")[0];
			
			if(formkind[0].checked == true || formkind[1].checked == true) {
				//////////강제 width 삽입//////////
				var tblWidth = 0;
				for(var i = 0; i < tbl.rows.length; i++) {
					var tmpWidth = 0;
					for(var j = 0; j < tbl.rows[i].cells.length; j++) {
						if(tbl.rows[i].cells[j].width == "") {
							tbl.rows[i].cells[j].width = tbl.rows[i].cells[j].clientWidth;
							tmpWidth += parseInt(tbl.rows[i].cells[j].clientWidth, 10);
						} else {
							tmpWidth += parseInt(tbl.rows[i].cells[j].width, 10);
						}
					}
					if(tblWidth < tmpWidth) {
						tblWidth = tmpWidth;
					}
				}
				tbl.width = tblWidth;
				
				if(fck.EditMode == 0) {
					fck.SwitchEditMode();		//EditMode=0(미리보기)일 때 SwitchEditMode()를 호출하지 않으면 오류 발생
				}
				fck.SetHTML(tbl.outerHTML);
				//////////////////////////////////////////////////
			}
				
			if(formkind[0].checked == true)
				actionpath = actionpath1;
			else if(formkind[1].checked == true)
				actionpath = actionpath2;
	
			document.forms[0].action = actionpath;
			document.forms[0].submit();
			if(fck.EditMode == 1) {
				fck.SwitchEditMode();
			}
		}, 0);
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatLineDef" method="post">
<html:hidden property="deptcd"/><html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formseq"/>
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top"> 
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <logic:lessEqual name="formatLineForm" property="type" value="4"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_24.gif" alt="새로만들기"></td></logic:lessEqual>
          <logic:greaterEqual name="formatLineForm" property="type" value="5"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_33.gif" alt="양식수정"></td></logic:greaterEqual>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td valign="top"> 
                  <!--추가양식 테이블-->
                  <table width="744" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>양식제목</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"><html:text property="formtitle" maxlength="100" style="width:99%" onkeypress="if(event.keyCode==13)event.returnValue=false"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>양식유형</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1"> 
												<logic:equal name="formatLineForm" property="formkind" value="01">
													<html:radio property="formkind" value="01" style="border:0;background-color:transparent;"/><label id="l1">행추가형</label>&nbsp;&nbsp;&nbsp;
													<html:radio property="formkind" value="02" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatFixedDef.do')"/><label id="l2" value="02" onmousedown="click_formchange('/formatFixedDef.do')">고정양식형</label>&nbsp;&nbsp;&nbsp;
													<html:radio property="formkind" value="03" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatBookDef.do')"/><label id="l3" value="03" onmousedown="click_formchange('/formatBookDef.do')">제본자료형</label>&nbsp;&nbsp;&nbsp;
												</logic:equal>
												<logic:equal name="formatLineForm" property="formkind" value="02">
													<html:radio property="formkind" value="01" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatLineDef.do')"/><label id="l1" value="01" onmousedown="click_formchange('/formatLineDef.do')">행추가형</label>&nbsp;&nbsp;&nbsp;
													<html:radio property="formkind" value="02" style="border:0;background-color:transparent;"/><label id="l2">고정양식형</label>&nbsp;&nbsp;&nbsp;
													<html:radio property="formkind" value="03" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatBookDef.do')"/><label id="l3" value="03" onmousedown="click_formchange('/formatBookDef.do')">제본자료형</label>&nbsp;&nbsp;&nbsp;
												</logic:equal>
												<a href="javascript:showPopup('/help/formatHelp.html', 700, 600, 100, 0)"><img src="/images/help/helpbtn.gif" align="absmiddle" alt=""></a>
											</td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">양식개요</td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1" height="50"><html:textarea property="formcomment" style="width:99%;height:40" onkeyup="maxlength_check(this, 1000)"/></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg2"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="11"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="13"></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
</table>
<!--양식테이블-->
<table width="780" border="0" cellspacing="0" cellpadding="0" height="288">
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top"> 
			<html:textarea property="formhtml" style="width:100%;"/>
			<script>
				// Automatically calculates the editor base path based on the _samples directory.
				// This is usefull only for these samples. A real application should use something like this:
				// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
				var oFCKeditor = new FCKeditor('formhtml');
				oFCKeditor.ToolbarSet = 'ejms_formMake';
				oFCKeditor.Height = 280;
				oFCKeditor.ReplaceTextarea();
			</script>
    </td>
    <td width="13"></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
</table>
<!--버튼 들어가는 테이블-->
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top"> 
      <table width="744" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td>
            <table width="744" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="734"></td>
                <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#F6F6F6"> 
                <td bgcolor="#F6F6F6" width="5"></td>
                <td bgcolor="#F6F6F6" align="right" height="35">	          
                <logic:lessEqual name="formatLineForm" property="type" value="3">
                	<a href="javascript:click_prev('/formatMake.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/common/btn_back.gif" align="absmiddle" alt="이전" border="0"></a>
                </logic:lessEqual>
                	<a href="javascript:click_next('/formatLineAtt.do', '/formatFixedAtt.do')"><img src="/images/common/btn_next.gif" align="absmiddle" alt="다음" border="0"></a>
                  <a href="javascript:click_cancel()"><img src="/images/common/btn_cancel.gif" align="absmiddle" alt="취소" border="0"></a>
                </td>
                <td width="5"></td>
              </tr>
              <tr> 
                <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="734"></td>
                <td><img src="/images/common/btn_r_04.gif" width="5" height="5" alt=""></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="13"></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_03.gif" width="13" height="13" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
<iframe name="bgProcFrame" height="0" title=""></iframe>
<script>setModifyMode()</script>
<%
	if((String)request.getAttribute("errorMsg") != null) {
		out.print((String)request.getAttribute("errorMsg"));
	}
%>
</body>
</html>