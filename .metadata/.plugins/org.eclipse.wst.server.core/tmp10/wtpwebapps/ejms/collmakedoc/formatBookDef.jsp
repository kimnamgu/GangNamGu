<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 제본자료형 양식정의
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
<title>양식정의</title>
<base target="_self">

<jsp:include page="/include/processing.jsp" flush="true"/>

<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script language="javascript">
	var changed = false;
	
	function change() {
		changed = true;
	}
	
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
	
	function selectAll() {
		var listcat = document.forms[0].listcategorynm1;

		for(var i = 0; i < listcat.length; i++)
			listcat[i].selected = true;
	}
	
	function deselectAll() {
		var listcat = document.forms[0].listcategorynm1;

		for(var i = 0; i < listcat.length; i++)
			listcat[i].selected = false;
	}
	
	function click_formchange(actionpath) {
		var filecount = document.forms[0].filecount;
		var listcat = document.forms[0].listcategorynm1;
		
		if(filecount > 0 || listcat.length > 0) {
			if(confirm("작성된 양식을 버리시겠습니까?") == false) {
				return;
			}
		}
		
		document.forms[0].action = actionpath;
		processingShow();
		document.forms[0].submit();
	}
	
	function click_prev(actionpath1, actionpath2, actionpath3) {
		var filecount = document.forms[0].filecount;
		var listcat = document.forms[0].listcategorynm1;
		var type = document.forms[0].type;
		var actionpath;
		
		if(filecount > 0 || listcat.length > 0) {
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
	
	function click_comp(actionpath) {
		var filecount = document.forms[0].filecount;
		var formtitle = document.forms[0].formtitle;
		var newcat = document.forms[0].newcategorynm;
		var listcat = document.forms[0].listcategorynm1;
		
		if(formtitle.value.trim() == "") {
			formtitle.focus();
			alert("제목을 입력하세요.");
			return;
		}
		
		if(filecount.value == 0) {
			if(confirm("양식파일 첨부없이 완료하시겠습니까?") == false) {
				return;
			}
		} else {
			if(confirm("완료하시겠습니까?") == false) {
				return;
			}
		}
		
		if ( listcat.length == 0 ) {
			newcat.value = formtitle.value.trim();
			click_addcategory(newcat, listcat)
		}
		
		selectAll();
		
		document.forms[0].action = actionpath;
		processingShow();
		document.forms[0].submit();
	}
	
	function click_cancel() {
		if(confirm("작업을 취소하시겠습니까?") == true)
			window.close();
	}
	
	function key_check(newcategorynm, listcategorynm1) {
		if(event.keyCode == 13)
			click_addcategory(newcategorynm, listcategorynm1)
	}
	
	function click_addcategory(newcategorynm, listcategorynm1) {
	  var newcat =  document.forms[0].newcategorynm;
	  var listcat = document.forms[0].listcategorynm1;
	  
		if(newcat.value.trim() == "")
    	return;
    
    for(var i = 0; i < listcat.length; i++)
			if(listcat[i].value.trim() == newcat.value.trim() && listcat.length > 0)
				return;

	  listcat.options[listcat.length] = new Option(newcat.value, newcat.value);
	  
	  newcat.value = "";
	  newcat.focus();
	  change();
	}
	
	function click_delcategory(listcategorynm1) {
  	var listcat = document.forms[0].listcategorynm1;
	  
	  if(listcat.selectedIndex == -1)
    	return;
  
	  listcat.options[listcat.selectedIndex] = null;
	  
	 	change();
	}
	
	function click_addfile(actionpath) {
		processingShow();
		
		setTimeout(function() {
			var uploadfile = document.forms[0].uploadfile;
			
			if(uploadfile.value.trim() == "") {
				processingHide();
				alert("추가할 파일을 선택하세요.");
				return;
			}
			
			selectAll();
	
			document.forms[0].action = actionpath;
			document.forms[0].submit();
			
			change();
		}, 0);
	}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatBookDef" method="post" enctype="multipart/form-data">
<html:hidden property="deptcd"/><html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formseq"/><html:hidden property="filecount"/>
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
          <logic:lessEqual name="formatBookForm" property="type" value="4"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_24.gif" alt="새로만들기"></td></logic:lessEqual>
          <logic:greaterEqual name="formatBookForm" property="type" value="5"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_33.gif" alt="양식수정"></td></logic:greaterEqual>
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
							        	<html:radio property="formkind" value="01" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatLineDef.do')"/><label id="l1" value="01" onmousedown="click_formchange('/formatLineDef.do')">행추가형</label>&nbsp;&nbsp;&nbsp;
							          <html:radio property="formkind" value="02" style="border:0;background-color:transparent;" onmousedown="click_formchange('/formatFixedDef.do')"/><label id="l2" value="02" onmousedown="click_formchange('/formatFixedDef.do')">고정양식형</label>&nbsp;&nbsp;&nbsp;
												<html:radio property="formkind" value="03" style="border:0;background-color:transparent;"/><label id="l3">제본자료형</label>&nbsp;&nbsp;&nbsp;
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
                      <td colspan="7" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt=""><b>카테고리</b></td>
                      <td class="bg2"></td>
                      <td colspan="5" class="t1" height="50">
                      	<table border="0" cellspacing="0" cellpadding="0">
                      		<tr>
								            <td width="200"><input type="text" name="newcategorynm" onkeydown="key_check(newcategorynm, listcategorynm1)"  style="width:99%"/></td>
								            <td align="left" valign="top"><a href="javascript:click_addcategory(document.forms[0].newcategorynm, document.forms[0].listcategorynm1)"><img src="/images/common/btn_s_add.gif" border="0" alt="추가"></a></td>
								          </tr>
								          <tr>
								            <td width="200">
								            	<html:select property="listcategorynm1" ondblclick="click_delcategory(listcategorynm1)" multiple="true" size="3" style="width:99%">
																<logic:notEmpty name="formatBookForm" property="listcategorynm2">
																	<html:optionsCollection name="formatBookForm" property="listcategorynm2"/>
																</logic:notEmpty>
								            	</html:select>
								            </td>
								  					<td align="left" valign="top">
								  						<a href="javascript:click_delcategory(document.forms[0].listcategorynm1)"><img src="/images/common/btn_s_delete.gif" border="0" alt="삭제"></a>
								  						<br><font color="red">&nbsp;-&nbsp;취합 후 제본자료생성시 제출파일을 구분하기 위해 사용합니다.<br>&nbsp;-&nbsp;카테고리를 등록하지 않으면 자동으로 등록됩니다.</font>
								  					</td>
								          </tr>                      	
                      	</table>
											</td>
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
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr height="180"> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top"> 
			<iframe name="fileBookFrame" src="/fileBookFrame.do?type=<bean:write name="formatBookForm" property="type"/>&sysdocno=<bean:write name="formatBookForm" property="sysdocno"/>&formseq=<bean:write name="formatBookForm" property="formseq"/>" frameborder="0" scrolling="Auto" width="100%" height="100%" title="양식목록"></iframe>
    </td>
    <td width="13"></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top"> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td colspan="4" class="list_bg2"></td>
				</tr>
				<tr> 
					<td width="100" class="s1"><img src="/images/common/dot_02.gif" align="absmiddle" alt="">양식파일첨부</td>
					<td class="bg2"></td>
					<td class="t1"><font color="red">※&nbsp;10Mbyte&nbsp;이하의 파일을 선택하세요.</font><input type="file" name="uploadfile" style="width:98%" contentEditable="false"></td>
					<td width="75"><br><a href="javascript:click_addfile('/formatBookFileAdd.do')"><img src="/images/common/btn_fileadd.gif" align="absmiddle" alt="파일첨부"/></a></td>
				</tr>
				<tr> 
					<td colspan="4" class="list_bg2"></td>
				</tr>
			</table>
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
                <logic:lessEqual name="formatBookForm" property="type" value="3">
                	<a href="javascript:click_prev('/formatMake.do', '/formatGetComm.do', '/formatGetUsed.do')"><img src="/images/common/btn_back.gif" align="absmiddle" alt="이전" border="0"></a>
                </logic:lessEqual>
                	<a href="javascript:click_comp('/formatBookSave.do')"><img src="/images/common/btn_complete.gif" align="absmiddle" alt="완료" border="0"></a>
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
<script>setModifyMode();deselectAll()</script>
</body>
</html>