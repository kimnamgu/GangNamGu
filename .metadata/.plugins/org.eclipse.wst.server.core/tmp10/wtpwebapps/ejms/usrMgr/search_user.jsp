<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 조직관리 사용자검색
 * 설명:
--%>
<%@page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>사용자검색</title>
<script src="/script/common.js"></script>
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script language="JavaScript">
//조회
function querySubmit(frm){	
	if (frm.s_word.value.trim()=="") {
		alert("성명을 입력한 후 검색버튼을 클릭하여 주세요.");
		frm.s_word.focus();
		return false;
	}
  	frm.submit();
}

function searchSubmit(p_dept_id, p_dept_name, p_user_id){
	opener.clickSearch(p_dept_id, p_dept_name, p_user_id);
	self.close();
}
</script>
</head>
<body onload="fullSizeWindow()">
<table width="510" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="10"></td>
		<td>
			<html:form method="POST" action="/searchUser.do" enctype="multipart/form-data">
			<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="4"><html:img page="/images/usermgr/g_t_1.gif" width="4" height="4"/></td>
								<td background="/images/usermgr/g_t_t.gif"></td>
								<td width="4"><html:img page="/images/usermgr/g_t_2.gif" width="4" height="4"/></td>
							</tr>
							<tr>
								<td background="/images/usermgr/g_t_l.gif"></td>
								<td style="padding:7px 7px 7px 7px ">
									<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
										<tr>
											<td align="right">	
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="40" align="right"><html:img page="/images/usermgr/search_text.gif" width="37" height="14"/></td>
														<td width="190">성명 <html:text name="usrMgrForm" property="s_word" styleClass="input2" size="25" style="ime-mode:active;"/></td>
														<td width="40" align="left"><html:img page="/images/usermgr/btn_search2.gif" width="36" height="20" align="absmiddle" style="cursor:pointer;" onclick="return querySubmit(document.forms[0]);"/></td>
														<td>※ 검색 후 사용자명을 클릭해 주세요.</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
								<td background="/images/usermgr/g_t_r.gif"></td>
							</tr>
							<tr>
								<td><html:img page="/images/usermgr/g_t_3.gif" width="4" height="4"/></td>
								<td background="/images/usermgr/g_t_b.gif"></td>
								<td><html:img page="/images/usermgr/g_t_4.gif" width="4" height="4"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr bgcolor="98BCE0"><td colspan="19" height="2"></td></tr>
							<tr height="25" bgcolor="F1F7FC" align="center">
								<td width="400" bgcolor="F1F7FC" class="sm9">부서</td>
								<td width="1" bgcolor="C5DAF0"></td>
								<td bgcolor="F1F7FC" class="sm9">사용자</td>
							</tr>
							<tr bgcolor="98BCE0"><td colspan="19" height="1"></td></tr>
						</table>
						<DIV style="OVERFLOW-Y: auto; OVERFLOW: auto; WIDTH: 500px; HEIGHT: 150px">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">	
						<logic:empty name="usrMgrForm" property="usrLists">												
							<tr><td height="23" colspan="3" align="center">검색된 데이터가 없습니다.</td></tr>
							<tr><td height="1" colspan="3" bgcolor="C5DAF0"></td></tr>
						</logic:empty>		
						<logic:notEmpty name="usrMgrForm" property="usrLists">												
						<logic:iterate id="usrLists" name="usrMgrForm" property="usrLists">	
							<tr height="23" align="center">
								<td width="400"><bean:write name="usrLists" property="dept_fullname"/></td>
								<td width="1" bgcolor="C5DAF0"></td>
								<td><a href="#" onclick="searchSubmit('<bean:write name="usrLists" property="dept_id"/>','<bean:write name="usrLists" property="dept_name"/>','<bean:write name="usrLists" property="user_id"/>')"><bean:write name="usrLists" property="user_name"/></a></td>
							</tr>
							<tr><td colspan="3" bgcolor="DEDEDE" height="1"></td></tr>
						</logic:iterate>
						</logic:notEmpty>								
						</table>
						</DIV>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="5" bgcolor="98BCE0"></td>
				</tr>
				<tr>
					<td height="10"></td>
				</tr>
				<tr>
					<td align="center"><html:img page="/images/usermgr/btn_end.gif" width="43" height="20" style="cursor:pointer;" onclick="window.close();"/></td>
				</tr>	
			</table>
			</html:form>
		</td>
	</tr>
</table>
</body>
</html>