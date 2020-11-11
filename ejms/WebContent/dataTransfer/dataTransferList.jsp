<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 자료이관 자료이관목록
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
<jsp:include page="/include/header_admin.jsp" flush="true"/>

<script>
menu3.onmouseover();
menu33.onmouseover();

function dataTransferProc(url) {
	var gbn = document.forms[0].gbn;
	var tgtuserid = document.forms[0].tgtuserid;
	if ( confirm("이관처리 하시겠습니까?") == false) {
		return;
	}
	
	if ( gbn.length == undefined ) {
		if ( tgtuserid.value == "" ) {
			alert("이관할 자료가 없습니다");
			return;
		}
	} else {
		var exist = false;
		for ( var i = 0; i < tgtuserid.length; i++ ) {
			if ( tgtuserid[i].value != "" ) {
				exist = true;
				continue;
			}
		}
		if ( exist == false ) {
			alert("이관할 자료가 없습니다");
			return;
		}
	}
	
	document.forms[0].sch_deptid[0].selected = true;
	document.forms[0].sch_userid[0].selected = true;
	document.forms[0].cause.value = "";
	document.forms[0].action = url;
	document.forms[0].submit();
}

 /*
function setTgtUserList(obj, list) {
	alert(obj);
	list = list.split(",");
	obj = document.getElementById(obj);
	obj.innerHTML = "";

	var idx = 0;
	obj.options[idx++] = new Option("사용자를선택하세요.", "");
	for ( var i = 0; i < list.length; i += 2) {
		obj.options[idx] = new Option(list[i + 1] + " (" + list[i] + ")", list[i]);
		obj.options[idx++].title = list[i + 1] + " (" + list[i] + ")";
	}

}
*/

function setTgtUserList(obj, list) {
	list = list.split(",");
	obj = document.getElementById(obj);
	obj.innerHTML = "";

	var idx = 0;
	obj.options[idx++] = new Option("사용자를선택하세요.", "");
	for ( var i = 0; i < list.length; i += 2) {
		obj.options[idx] = new Option(list[i + 1] + " (" + list[i] + ")", list[i]);
		obj.options[idx++].title = list[i + 1] + " (" + list[i] + ")";
	}

}

function setTgtDeptList(tgtdept, list, num, len_tgtdeptid) {
	if(num == 1){
		for(var i=0; i < len_tgtdeptid; i++){
			document.forms[0].tgtdeptid[i].value = document.forms[0].tgtdeptid[0].value;
			document.forms[0].tgtuserid[i].value = document.forms[0].tgtuserid[0].value;
			setTgtUserList(tgtdept+i, list);
		}
	}else{
			setTgtUserList(tgtdept+len_tgtdeptid, list);
	}
}

function getTgtDeptList(tgtdept, num) {
	var url;
	if(document.forms[0].allChange.checked){
		var len_tgtdeptid = document.forms[0].tgtdeptid.length;
		url = '/getTgtUserList.do?listnm=tgtuserid&tgtdept='+tgtdept+'&num=1&len_tgtdeptid='+len_tgtdeptid;
	}else{
		url = '/getTgtUserList.do?listnm=tgtuserid&tgtdept='+tgtdept+'&num=0&len_tgtdeptid='+num;
	}
	actionFrame.location.href = url;
}

function getTgtUserList(tgtuserid, num) {
	if(document.forms[0].allChange.checked){
		var len_tgtuserid = document.forms[0].tgtuserid.length;
		for(var i=0; i < len_tgtuserid; i++){
			document.forms[0].tgtuserid[i].value = document.forms[0].tgtuserid[0].value;
		}
	}
}

function getAllChange(){	
	if(document.forms[0].allChange.checked){
		document.getElementById("tgtdeptid0").style.background="lightblue";
		document.getElementById("tgtuserid0").style.background="lightblue";
	}else{
		document.getElementById("tgtdeptid0").style.background="#F8F8F8";
		document.getElementById("tgtuserid0").style.background="#F8F8F8";
	}
}
</script>

<html:form method="post" action="/dataTransferList">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_24.gif" alt="관리자변경"></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
                        <!--검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr style="padding-top:5"> 
                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="90"><font color="#4F4F4F">부서명(부서ID)</font></td>
														<% if ( "001".equals(session.getAttribute("user_gbn")) && nexti.ejms.common.appInfo.isSidoldap() ) { %>
                            <td width="130">
														<html:select name="dataTransferForm" property="sch_orggbn" style="width:130px;background-color:white" onchange="sch_deptid[0].selected=true;submit()">
															<jsp:useBean id="sch_orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
																<bean:define id="user_gbn" name="user_gbn" scope="session"/>
																<jsp:setProperty name="sch_orggbn" property="ccd_cd" value="023"/>
																<jsp:setProperty name="sch_orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
															</jsp:useBean>
															<html:optionsCollection name="sch_orggbn" property="beanCollection"/>
														</html:select>&nbsp;
                            </td>
														<% } else { %>
														<html:hidden property="sch_orggbn"/>
														<% ((nexti.ejms.dataTransfer.form.DataTransferForm)request.getAttribute("dataTransferForm")).setSch_orggbn((String)session.getAttribute("user_gbn")); %>
														<% } %>
                            <td width="170"><html:select property="sch_deptid" style="width:100%;background-color:white" onchange="sch_userid[0].selected=true;submit()"><bean:write name="dataTransferForm" property="orgdept" filter="false"/></html:select></td>
                            <td width="15">&nbsp;</td>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="125"><font color="#4F4F4F">사용자명(사용자ID)</font></td>
                            <td width="150"><html:select property="sch_userid" style="width:100%;background-color:white" onchange="submit()"><bean:write name="dataTransferForm" property="orguser" filter="false"/></html:select></td>
                            <td width="130">&nbsp;</td>
                          </tr>
                          <tr> 
	                          <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="90"><font color="#4F4F4F">자료이관사유</font></td>
                            <td colspan="7">
                            	<html:text property="cause" style="width:80%;background-color:white" maxlength="50" onkeyup="cause2.value=this.value"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            	<a href="javascript:dataTransferProc('/dataTransferProc.do')"><img src="/images/common/btn_datatransfer.gif" align="<%="center"%>" alt=""></a>
                            </td>
                          </tr>
                        </table>
                        <!--검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr>
	              <td>
	              	&nbsp;&nbsp;<img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt="">&nbsp;&nbsp;삭제되었거나 사용 중이지 않는 부서와 사용자는 "정보없음"으로 표시됩니다.<br>
	              	&nbsp;&nbsp;<img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt="">&nbsp;&nbsp;이관대상의 셀렉트박스에서 사용자를 선택한 자료만 해당 사용자에게 이관되면 선택하지 않은 자료는 무시됩니다.
	              </td>
              </tr>
              <tr>
	              <td height="5"></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="7" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
                    </tr>
                    <tr> 
                      <td class="list_t" width="40">번호</td>
                      <td class="list_t" width="60">종류</td>
                      <td class="list_t" width="210">제목</td>
                      <td class="list_t" width="160">부서명(부서ID)</td>
                      <td class="list_t" width="130">사용자명(사용자ID)</td>
                      <td class="list_t" width="80">최종수정일</td>
                      <td class="list_t" width="150">&nbsp;&nbsp;&nbsp;
                      	이관대상 (<input type="checkbox" name="allChange" onClick="getAllChange();" style="padding:0 0 10 0;border:0px;background:transparent;" id="chkbox"><label for="chkbox">전체</label>)
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" class="list_bg"></td>
                    </tr>
                    <tr> 
                      <td colspan="7" height="1"></td>
                    </tr>
                    <logic:notEmpty name="dataTransferForm" property="datalist">
											<logic:iterate id="list" name="dataTransferForm" property="datalist" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td class="list_no"><bean:write name="list" property="seq"/></td>
		                      <td class="list_l">
		                      	<logic:equal name="list" property="type" value="COLLECT">자료취합</logic:equal>
		                      	<logic:equal name="list" property="type" value="RESEARCH">설문조사</logic:equal>
		                      	<logic:equal name="list" property="type" value="REQUEST">신청서</logic:equal>
		                      </td>
		                      <td class="list_l"><bean:write name="list" property="title"/></td>
		                      <td class="list_l"><bean:write name="list" property="deptnm"/>(<bean:write name="list" property="deptid"/>)<br><logic:equal name="list" property="existdeptid" value="Y">정보있음</logic:equal><logic:equal name="list" property="existdeptid" value="N">정보없음</logic:equal></td>
		                      <td class="list_l"><bean:write name="list" property="usernm"/>(<bean:write name="list" property="userid"/>)<br><logic:equal name="list" property="existuserid" value="Y">정보있음</logic:equal><logic:equal name="list" property="existuserid" value="N">정보없음</logic:equal></td>
		                      <td class="list_l"><bean:write name="list" property="uptdt"/></td>
		                      <td class="list_l">
		                      	<select name="tgtdeptid" style="width:100%" id="tgtdeptid<%=i.intValue()%>" onchange="getTgtDeptList(this.value, <%=i.intValue()%>)"><bean:write name="dataTransferForm" property="tgtdept" filter="false"/></select><br>
		                      	<select name="tgtuserid" style="width:100%" id="tgtuserid<%=i.intValue()%>" onchange="getTgtUserList(this.value, <%=i.intValue()%>)"><bean:write name="dataTransferForm" property="tgtuser" filter="false"/></select>
		                      	<input type="hidden" name="gbn" value="<bean:write name="list" property="type"/>">
		                      	<input type="hidden" name="gbnid" value="<bean:write name="list" property="no"/>">
		                      </td>
		                    </tr>
		                    <tr> 
		                      <td colspan="7" class="list_bg2"></td>
		                    </tr>
                    	</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="dataTransferForm" property="datalist">
											<tr>
												<td colspan="7" class="list_s" align="center">이관할 자료가 없습니다.</td>
											</tr>
											<tr>
												<td colspan="7" class="list_bg2"></td>
											</tr>
										</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3 0;"> 
                        <!--검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
	                          <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="90"><font color="#4F4F4F">자료이관사유</font></td>
                            <td>
                            	<input type="text" name="cause2" style="width:80%;background-color:white" maxlength="50" onkeyup="cause.value=this.value"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            	<a href="javascript:dataTransferProc('/dataTransferProc.do')"><img src="/images/common/btn_datatransfer.gif" align="<%="center"%>" alt=""></a>
                            </td>
                          </tr>
                        </table>
                        <!--검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</table>
</html:form>
<script>
document.forms[0].cause2.value=document.forms[0].cause.value;
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<iframe name="actionFrame" width="0" height="0" title=""></iframe>
<jsp:include page="/include/tail.jsp" flush="true"/>