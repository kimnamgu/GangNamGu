<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리
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
<jsp:include page="/include/header_admin.jsp" flush="true" />
<script language="javascript">
menu3.onmouseover();
menu33.onmouseover();

	var Posi = <bean:write name="chrgUnitForm" property="posi"/>;
	var cPosi = <bean:write name="chrgUnitForm" property="cposi"/>;
	
	function deliveryUser_submit() {
		var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
		var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
	  
		if (document.forms[1].user_id.value.replace(" ","")=="") {
			alert(document.forms[1].user_id[0].text);
			document.forms[1].user_id.focus();
			return;
		}
		document.forms[1].orggbn.value = document.forms[0].orggbn.value;
		document.forms[1].action += '?posi='+ppos+'&cposi='+cpos
		document.forms[1].submit();
	}
	
	function hyperlink(dept_id) {
			var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
			var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
	    var today = new Date();
	    var orggbn = document.forms[0].orggbn.value;
			window.open('/deliveryUserMgr.do?dept_id='+dept_id+'&orggbn='+orggbn+'&posi='+ppos+'&cposi='+cpos+'&time='+today,'_self')
	}
	
	function SetScrollPos(tagDIV) {
		var positionTop = 0;
		if (tagDIV != null) {
			positionTop = parseInt(tagDIV.scrollTop, 10);
			document.getElementById("HeaderTable").style.top = positionTop;
		}
	}
	
	function getSearch(frm) {
		frm.action='/deliveryUserMgr.do?orggbn='+frm.orggbn.value;
		frm.submit();
	}
	
	function searchList() {
		if ( event.keyCode == 13 ) event.returnValue = false;
				
		var list = document.forms[1].user_id;
		var searchValue = document.forms[1].searchValue;

		for ( var i = 0; i < list.length; i++ ) {
			if ( list[i].text.substring(0,5).indexOf(searchValue.value.trim()) != -1 ) {
				list[i].selected = true;
				return;
			}
		}
		list[0].selected = true;
		
	}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_27.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="830"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="830"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="359" valign="top"> 
                  <table width="356" border="0" cellspacing="0" cellpadding="0">
                    <tr>
	                    <td class="save_s" width="82" >
	                    	<table width="82" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
	               						<tr><td class="save_t2">부서목록</td></tr>
               					</table>
               				</td>
											<td class="save_s" width="234">
												<html:form action="/deliveryUserMgr" method="post">
												<table width="234" border="0" align="center" cellpadding="0" cellspacing="0">
													<tr>
														<td>
															<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
																<tr>
																	<td align="right">
																		<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
																		<table width="100%" border="0" cellpadding="0" cellspacing="0">
																			<tr>
																				<td width="15" align="center"><html:img page="/images/common/dot.gif" width="7" height="7" align="absmiddle"/></td>
																			  <td class="t3" width="200">조직구분&nbsp;
																					<html:select name="chrgUnitForm" property="orggbn" style="width:130px" onchange="getSearch(document.forms[0])">
																						<jsp:useBean id="orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
																							<bean:define id="user_gbn" name="user_gbn" scope="session"/>
																							<jsp:setProperty name="orggbn" property="ccd_cd" value="023"/>
																							<jsp:setProperty name="orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
																						</jsp:useBean>
																						<html:optionsCollection name="orggbn" property="beanCollection"/>
																					</html:select>&nbsp;
																				</td>
																			</tr>
																		</table>
																		<% } else { %>
																			<% if ( nexti.ejms.common.appInfo.isSidoldap() ){ %>
																			<html:hidden property="orggbn" value="001"/>
																			<% } else { %>
																			<html:hidden property="orggbn"/>
																			<% } %>
																		<% } %>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
												</html:form>
											</td>
										</tr>
                    <tr> 
                      <td colspan="2"> 
                        <table width="340" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">부서코드</td>
                            <td class="save_t2">부서명칭</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top" colspan="2"> 
                        <!--부서 코드 리스트 테이블-->
                        <div id="parentdiv" style="position:absolute; width:356; height:340; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table width="340" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="chrgUnitForm" property="deptList">
															<logic:iterate id="dept" name="chrgUnitForm" property="deptList">
																<tr> 
		                              <td class="save_s" width="80"><bean:write name="dept" property="dept_id"/></td>
		                              <td class="save_s2"><a class="saveL" href="javascript:hyperlink('<bean:write name="dept" property="dept_id"/>')"><bean:write name="dept" property="dept_fullname"/></a></td>
		                            </tr>
															</logic:iterate>
														</logic:notEmpty>
														<logic:empty name="chrgUnitForm" property="deptList">
															<tr> 
	                              <td class="save_s2" colspan="2">등록된 자료가 없습니다.</td>
	                            </tr>
														</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
                <td background="/images/admin/sero_line.gif" width="1"></td>
                <td width="460" valign="top"> 
                  <table width="440" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
	                    <td class="save_s" width="82">
	                    	<table width="82" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
	               						<tr><td class="save_t2">배부담당</td></tr>
               					</table>
               				</td>
                      <td class="t3" width="360"><font color="#9933CC">[<bean:write name="chrgUnitForm" property="dept_name"/>(<bean:write name="chrgUnitForm" property="dept_id"/>)]</font> </td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <table width="440" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">사용자코드</td>
                            <td class="save_t2">사용자명</td>
                            <td class="save_t2" width="95">관리</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top" colspan="2">
                        <!--부코드 리스트 테이블-->
                        <div id="childdiv" style="position:absolute; width:456; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table id="HeaderTable" width="440" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="chrgUnitForm" property="chrgList">
															<logic:iterate id="chrg" name="chrgUnitForm" property="chrgList">
																<tr> 
		                              <td class="save_s" width="80"><bean:write name="chrg" property="user_id"/></td>
		                              <td class="save_s2"><bean:write name="chrg" property="user_name"/></td>
		                              <td class="save_s" width="95">
		                              	<a href="javascript:if(confirm('삭제하시겠습니까?')){window.open('/deliveryUserMgrDB.do?mode=d&orggbn='+document.forms[0].orggbn.value+'&dept_id=<bean:write name="chrgUnitForm" property="dept_id"/>&user_id=<bean:write name="chrg" property="user_id"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" align="absmiddle" alt="삭제"></a>
		                              </td>
		                            </tr>
			                    		</logic:iterate>
														</logic:notEmpty>
		                        <logic:empty name="chrgUnitForm" property="chrgList">
				                  		<tr> 
	                              <td class="save_s2" colspan="3">배부담당자를 지정하지 않으면 부서원 모두가 배부담당자로 지정됩니다</td>
	                            </tr>
			                  		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td height="7" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 
                        <!--부코드 저장 테이블-->
		                    <html:form method="POST" action="/deliveryUserMgrDB.do">
	                      <input type="hidden" name="mode" value="i">
	                      <input type="hidden" name="orggbn" value="">
	                      <input type="hidden" name="dept_id" value="<bean:write name="chrgUnitForm" property="dept_id"/>">	
                        <table width="440" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t">사용자목록</td>
                            <td class="save_t" width="70">이름검색</td>
                            <td class="save_t" width="50">저장</td>
                          </tr>
                          <tr> 
                            <td class="save_s">
                            	<html:select property="user_id" style="width:95%">
																<jsp:useBean id="userselect" class="nexti.ejms.list.form.DeliveryUserListForm">
																	<bean:define id="coldeptcd" name="chrgUnitForm" property="dept_id"/>
																	<jsp:setProperty name="userselect" property="dept_id" value="<%=(String)coldeptcd%>"/>
																	<jsp:setProperty name="userselect" property="all_fl" value="ALL"/>
																	<jsp:setProperty name="userselect" property="title" value="추가할 담당자를 선택하세요"/>
																</jsp:useBean>
																<html:optionsCollection name="userselect" property="beanCollection"/>
						                 	</html:select>
                            </td>
                            <td class="save_s"><input name="searchValue" type="text" style="width:95%;text-align: center" onkeypress="searchList()"></td>
                            <td class="save_s">
                            	<a href="javascript:deliveryUser_submit()"><img src="/images/common/btn_s_add.gif" align="absmiddle" alt="저장">
                            </td>
                          </tr>
                        </table>
                        </html:form>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
			  			<tr> 
                <td colspan="3" height="20"></td>
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
	parentdiv.scrollTop = Posi;
	childdiv.scrollTop = cPosi;
	
</script>
<html:messages id="msg" message="true">
	<script>//alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>