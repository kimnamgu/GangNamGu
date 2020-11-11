<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 고정양식형 속성정의
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
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>속성정의</title>
<base target="_self">

<jsp:include page="/include/processing.jsp" flush="true"/>

<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/formatAtt.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form action="/formatFixedAtt" method="post">
<html:hidden property="deptcd"/><html:hidden property="type"/><html:hidden property="sysdocno"/><html:hidden property="formseq"/><html:hidden property="formkind"/><html:hidden property="formhtml"/>
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
          <logic:lessEqual name="formatFixedForm" property="type" value="4"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_20.gif" alt="새로만들기"></td></logic:lessEqual>
          <logic:greaterEqual name="formatFixedForm" property="type" value="5"><td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_33.gif" alt="양식수정"></td></logic:greaterEqual>
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
                      <td colspan="5" class="t1"><bean:write name="formatFixedForm" property="formkindname"/></td>
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
    	<div id="formatLayer" style="width:744; height:285; overflow:auto; border:lightgrey 1 solid">
<!-- 속성지정 DIV -->
<!-- <div id="mainmenu" style="display:none; position:absolute" onmouseover="showMainMenu(eventCellAtt, this)" onmouseout="hideMainMenu(eventCellAtt, this)"> -->
<div id="mainmenu" style="display:none; position:absolute">
	<table width="55" height="100" border="1" cellpadding="0" cellspacing="0" bgcolor="FFFFFF" style="border-collapse:collapse; cursor:hand">
		<tr align="center">
			<td id="mainmenu01" onmouseover="showSubMenu(this, '');hideSubMenu(this, submenu02);hideSubMenu(this, submenu03);hideSubMenu(this, submenu04)" onmouseout="return;hideSubMenu(this, '')" onclick="setCellatt('숫자', '01', '')">
				<span style="font-size:12; font-weight:none; color:000066;">숫자 </span>&nbsp;&nbsp;<img src="/images/s_arrow.gif" style="visibility:hidden" alt="">
			</td>
		</tr>
		<tr align="center">
			<td id="mainmenu02" bgcolor="FFFFFF" onmouseover="showSubMenu(this, submenu02);hideSubMenu(this, submenu03);hideSubMenu(this, submenu04)" onmouseout="return;hideSubMenu(this, submenu02)">
				<span style="font-size:12; font-weight:none; color:000066">문자 </span>&nbsp;&nbsp;<img src="/images/s_arrow.gif" alt="">
				<div id="submenu02" style="display:none; position:absolute">
					<table width="145" cellpadding="0" cellspacing="0" bgcolor="FFFFFF" bordercolor="gray" style="border:1 solid;padding:2 2 2 10;">
						<tr>
							<td width="100"><input type="hidden" name="character">
								<input type="radio" name="characterRadio" style="border:0;background-color:transparent;" onclick="character.value='1'" id="charType1" checked="checked"><label for="charType1" style="font-size:12; color:000066;">단문(1줄)</label>
								<input type="radio" name="characterRadio" style="border:0;background-color:transparent;" onclick="character.value='5'" id="charType2"><label for="charType2" style="font-size:12; color:000066;">장문</label>
							</td>
							<td width="45"><img src="/images/common/btn_s_ok.gif" onclick="setCellatt('문자', '02', character.value)" alt=""></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr align="center">
			<td id="mainmenu03" bgcolor="FFFFFF" onmouseover="showSubMenu(this, submenu03);hideSubMenu(this, submenu02);hideSubMenu(this, submenu04)" onmouseout="return;hideSubMenu(this, submenu03)">
				<span style="font-size:12; font-weight:none; color:000066">수식 </span>&nbsp;&nbsp;<img src="/images/s_arrow.gif" alt="">
				<div id="submenu03" style="display:none; position:absolute">
					<table cellpadding="0" cellspacing="0" bgcolor="FFFFFF" bordercolor="gray" style="border:1 solid;padding:2 2 2 10;">
						<tr>
							<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><input type="text" name="formula" size="35" maxlength="196" style="text-align:center; ime-mode:disabled" onkeypress="filterKey('[a-zA-Z0-9()+*/.-]')"></td>
										<td>&nbsp;<img src="/images/common/btn_s_ok.gif" onclick="setCellatt('수식', '03', formula.value)" alt="">&nbsp;</td>
									</tr>
								</table>
								<font color="#FF567E">※&nbsp;숫자속성을 대상으로 사칙연산만 가능합니다.</font>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr align="center">
			<td id="mainmenu04" bgcolor="FFFFFF" onmouseover="showSubMenu(this, submenu04);hideSubMenu(this, submenu02);hideSubMenu(this, submenu03)" onmouseout="return;hideSubMenu(this, submenu04)">
				<span style="font-size:12; font-weight:none; color:000066">목록 </span>&nbsp;&nbsp;<img src="/images/s_arrow.gif" alt="">
				<div id="submenu04" style="display:none; position:absolute">
					<table cellpadding="0" cellspacing="0" bgcolor="FFFFFF" bordercolor="gray" style="border:1 solid;padding:5 5 5 10;">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="2">
									  	<input type="radio" name="listType" style="border:0;background-color:transparent;display:none" onclick="showList(userList, adminList, detailList, userActView, adminActView, 'listTypeTab', '/images/collect/list_tab_01_over.gif', '/images/collect/list_tab_02.gif')" checked="checked">
									  	<input type="radio" name="listType" style="border:0;background-color:transparent;display:none" onclick="showList(adminList, userList, detailList, adminActView, userActView, 'listTypeTab', '/images/collect/list_tab_01.gif', '/images/collect/list_tab_02_over.gif')">
										<td>
									</tr>
						      <tr>
						        <td width="89" background="/images/collect/list_tab_bg.gif"><a href="javascript:showListTab(document.forms[0].listType[0])"><img name="listTypeTab" src="/images/collect/list_tab_01_over.gif" border="0" alt="내목록보기"></a></td>
						        <td background="/images/collect/list_tab_bg.gif"><a href="javascript:showListTab(document.forms[0].listType[1])"><img name="listTypeTab" src="/images/collect/list_tab_02.gif" border="0" alt="공통목록보기"></a></td>
						      </tr>
									<tr>
										<td colspan="2" height="5"></td>
									</tr>
								</table>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr align="center">
										<td style="font-size:12; color:000066;">목 록 명</td>
										<td width="5">&nbsp;</td>
										<td style="font-size:12; color:000066;">목 록 내 용</td>
									</tr>
									<tr>
										<td>
											<select name="userList" size="10" style="width:150; border:0; display:block" onclick="masterListSelect(userList, detailList, masterCode, detailCode, 'show')">
												<bean:write name="formatFixedForm" property="attoption1" filter="false"/>
											</select>
											<select name="adminList" size="10" style="width:150; border:0; display:none" onclick="masterListSelect(adminList, detailList, masterCode, detailCode, 'hide')">
												<bean:write name="formatFixedForm" property="attoption2" filter="false"/>
											</select>
										</td>
										<td width="5">&nbsp;</td>
									  <td>
									  	<select name="detailList" size="10" style="width:150; border:0" onclick="detailListSelect(userList, detailList, masterCode, detailCode)"></select>
									  </td>
									</tr>
									<tr>
									  <td colspan="3">
									  	<div id="userActView" style="padding-top:2; display:block;">
									  		<table border="0" cellpadding="0" cellspacing="0" width="100%">
									  			<tr id="userActViewInputBox" style="display:none">
									  				<td colspan="2">
												  		<input type="text" name="masterCode" style="width:150" onkeyup="changeMode(userList, detailList, masterCode, detailCode, 'master')">
												  		<input type="text" name="detailCode" style="width:150" onkeyup="changeMode(userList, detailList, masterCode, detailCode, 'detail')">
									  				</td>
									  			</tr>
									  			<tr>
									  				<td width="100">
									  					<input type="checkbox" name="editMode" style="border:0;background-color:transparent;" onclick="showEdit(editMode, userActViewInputBox, addButton, modButton, delButton, okButton, message)" id="edit"><label for="edit" style="font-size:12; color:000066">추가/변경</label>									  					
									  				</td>
									  				<td align="right">
									  					<img id="addButton" style="visibility:hidden" src="/images/common/btn_s_add.gif" onclick="saveAttList('/saveAttrList.do', 'insert', masterCode, detailCode)" align="absmiddle" alt="">
													  	<img id="modButton" style="visibility:hidden" src="/images/common/btn_s_update.gif" onclick="saveAttList('/saveAttrList.do', 'update', masterCode, detailCode)" align="absmiddle" alt="">
													  	<img id="delButton" style="visibility:hidden" src="/images/common/btn_s_delete.gif" onclick="saveAttList('/saveAttrList.do', 'delete', masterCode, detailCode)" align="absmiddle" alt="">
													  	<img id="okButton" style="visibility:visible" src="/images/common/btn_s_ok.gif" onclick="setCellattList('목록', '04', 'selectedList[selectedList.selectedIndex].value')" align="absmiddle" alt="">
									  				</td>
									  			</tr>
									  			<tr>
									  				<td colspan="2" style="font-size:12; color:#FF567E" id="message"></td>
									  			</tr>
									  		</table>
									  	</div>
									  	<div id="adminActView" align="right" style="padding-top:2; display:none">
										  	<img src="/images/common/btn_s_ok.gif" onclick="setCellattList('목록', '04', 'selectedList[selectedList.selectedIndex].value')" align="absmiddle" alt="">
									  	</div>
									  </td>
								  </tr>
							  </table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<!-- 속성지정 DIV -->
				<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
				<bean:write name="formatFixedForm" property="formbodyhtml_ext" filter="false"/>
				<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
			</div>
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
	                <a href="javascript:click_preview('/formatFixedProcPreview.do')"><img src="/images/common/btn_view.gif" align="absmiddle" alt="미리보기" border="0"></a>
                	<a href="javascript:click_prev('/formatFixedDef.do')"><img src="/images/common/btn_back.gif" align="absmiddle" alt="이전" border="0"></a>
                	<a href="javascript:click_preview('/formatFixedProcPreview.do?saveCheck=1')"><img src="/images/common/btn_complete.gif" align="absmiddle" alt="완료" border="0"></a>
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
<iframe name="bgProcFrame" width="0" height="0" title=""></iframe>
</body>
</html>