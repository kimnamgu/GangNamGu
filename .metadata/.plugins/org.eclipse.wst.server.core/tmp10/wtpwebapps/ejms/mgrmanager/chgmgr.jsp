<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경
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

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_13.gif" alt=""></td>
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
                        <!--마감시한/알림말 테이블 시작-->
                        <html:form method="POST" action="/chgMgrSave">
												<html:hidden property="gbn" value="1"/>
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                            <td width="25" align="center" height="20"><img src="/images/common/dot.gif" width="7" height="7" align="absmiddle" alt=""></td>
                            <td width="50" height="20"><font color="#4F4F4F">관리자ID</font></td>
                            <td width="120" height="20"><html:text property="user_id" style="width:110%;background-color:white"/></td>
                            <td width="500" height="20" align="left"> 													
                            	<a href="javascript:check_submit(document.forms[0])"><img src="/images/common/btn_record.gif" alt="등록" align="absmiddle"></a>
                            	<a href="javascript:showModalPopup('/commdeptuser.do',782,503,0,0)"><img src="/images/common/btn_chart.gif" align="absmiddle" alt="조직도에서 선택"></a>
                            </td>
                          </tr>
                        </table>
                        </html:form>
                        <!--마감시한/알림말 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--검색 리스트 테이블 시작-->
                  <html:form method="POST" action="/chgMgrSave">
										<html:hidden property="user_id1"/>
										<html:hidden property="gbn" value="0"/>
									</html:form>
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr><td colspan="4" class="list_bg"></td></tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <tr> 
                      <td class="list_t" width="205">관리자ID</td>
                      <td class="list_t" width="205">성명</td>
                      <td class="list_t" width="305">부서</td>
                      <td class="list_t" width="105">관리</td>
                    </tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <tr><td colspan="4" class="list_bg"></td></tr>
                    <tr><td colspan="4" height="1"></td></tr>
                    <logic:notEmpty name="managerForm" property="mgrList">
											<logic:iterate id="list" name="managerForm" property="mgrList" indexId="i">
		                    <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">
		                      <td class="list_no"><bean:write name="list" property="user_id"/></td>
		                      <td class="list_l"><bean:write name="list" property="user_name"/></td>
		                      <td class="list_l"><bean:write name="list" property="dept_fullname"/></td>
		                      <td class="list_l"><a href="javascript:check_del('<bean:write name="list" property="user_id"/>')"><img src="/images/common/btn_del2.gif" alt="삭제"></a></td>
		                    </tr>
		                    <tr> 
		                      <td colspan="4" class="list_bg2"></td>
		                    </tr>
		              		</logic:iterate>
		              	</logic:notEmpty>
		              	<logic:empty name="managerForm" property="mgrList">
		              		<tr> 
	                      <td colspan="4" class="list_l" align="center">등록된 관리자가 없습니다.</td>
	                    </tr>
		              		<tr> 
	                      <td colspan="4" class="list_bg2"></td>
	                    </tr>
		              	</logic:empty>
                  </table>
                  <!--검색 리스트 테이블 끝-->
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
<script language="javascript">
menu3.onmouseover();
menu33.onmouseover();

	document.forms[0].user_id.value = "";
	
	function check_submit(frm){
		if (frm.user_id.value.trim() == '')
		{
			alert("추가하고자 하는 담당자의 아이디를 입력하여 주십시오.")
			frm.user_id.focus();
			return;
		}
		frm.submit();
	}
	
	function check_del(p_user_id){
		if (p_user_id == '')
		{
			alert("삭제하고자하는 담당자를 선택하여 주십시오.")
			return;
		}
		if (confirm("선택한 담당자를 삭제하시겠습니까?"))
		{	
			document.forms[1].user_id1.value=p_user_id;		
			document.forms[1].submit();
		} 
	}
</script>
<html:messages id="msg" message="true">
	<script>
	document.onreadystatechange = function() {
		if(document.readyState.toLowerCase() == "complete") {
			alert('<bean:write name="msg"/>');
		}
	}
	</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>