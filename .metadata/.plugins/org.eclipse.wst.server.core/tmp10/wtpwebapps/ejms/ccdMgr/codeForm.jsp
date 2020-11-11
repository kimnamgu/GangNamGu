<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 코드관리
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
menu4.onmouseover();
menu44.onmouseover();

	var Posi = <bean:write name="codeForm" property="posi"/>;
	var cPosi = <bean:write name="codeForm" property="cposi"/>;
	
	function main_submit()
	{
		if (document.forms[1].ccd_cd.value.replace(" ","")=="")
		{
			alert("주코드를 입력하여 주십시오.")
			document.forms[1].ccd_cd.focus();
			return;
		}
		if (document.forms[1].ccd_name.value.replace(" ","")=="")
		{
			alert("주코드명를 입력하여 주십시오.")
			document.forms[1].ccd_name.focus();
			return;
		}
		
		var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
		var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
		document.forms[1].action += '?posi='+ppos+'&cposi='+cpos
		document.forms[1].submit();
	}

	function sub_submit()
	{
		if (document.forms[2].ccd_cd.value.replace(" ","")=="")
		{
			alert("주코드를을 등록 또는 선택하여 주십시오.")
			document.forms[1].ccd_cd.focus();
			return;
		}
		if (document.forms[2].ccd_sub_cd.value.replace(" ","")=="")
		{
			alert("부코드를 입력하여 주십시오.")
			document.forms[2].ccd_sub_cd.focus();
			return;
		}
		if (document.forms[2].ccd_name.value.replace(" ","")=="")
		{
			alert("부코드명를 입력하여 주십시오.")
			document.forms[2].ccd_name.focus();
			return;
		}
		
		var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
		var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
		document.forms[2].action += '?posi='+ppos+'&cposi='+cpos
		document.forms[2].submit();
	}
	
	function hyperlink(CCD_CD)
	{
			var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
			var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
	    var today = new Date();
			window.open('/codeList.do?gbn=<bean:write name="codeForm" property="gbn"/>&ccd_cd='+CCD_CD+'&posi='+ppos+'&cposi='+cpos+'&time='+today,'_self')
	}
	
	function SetScrollPos(tagDIV)
	{
		var positionTop = 0;
		if (tagDIV != null)
		{
			positionTop = parseInt(tagDIV.scrollTop, 10);
			document.getElementById("HeaderTable").style.top = positionTop;
		}
	}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_15.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="830"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="830"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3"> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF" style="padding:3 0 3x 0;"> 
                        <!--검색 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#F7F7F7" height="32">
                          <tr> 
                            <td width="5"></td>
                            <td height="20" width="250"> 
			                      	<html:form method="POST" action="/codeList">
				                      	<html:select property="gbn" style="width:130px;background-color:white" onchange="submit();">
																	<html:option value="a">관리자용 코드목록</html:option>
																	<html:option value="s">시스템용 코드목록</html:option>
																</html:select>
															</html:form>
                            </td>
                            <td width="500"></td>
                          </tr>
                        </table>
                        <!--검색 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <td width="379" valign="top"> 
                  <table width="377" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="t3">주코드</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t2" width="80">주코드</td>
                            <td class="save_t2">주코드명</td>
                            <td class="save_t2" width="95">관리</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--주코드 리스트 테이블-->
                        <div id="parentdiv" style="position:absolute; width:376; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          	<logic:notEmpty name="codeForm" property="mainlist">
															<logic:iterate id="mainCode" name="codeForm" property="mainlist">
																<tr> 
		                              <td class="save_s" width="80"><bean:write name="mainCode" property="ccdcd"/></td>
		                              <td class="save_s2"><a class="saveL" href="javascript:hyperlink('<bean:write name="mainCode" property="ccdcd"/>')"><bean:write name="mainCode" property="ccdname"/></a></td>
		                              <td class="save_s" width="95">
		                              	<logic:equal name="codeForm" property="gbn" value="s">
																			<a href="javascript:if(confirm('시스템용 코드입니다.\n수정시 시스템에 영향이 있을 수 있습니다.\n\n그래도 수정하시겠습니까?')){showModalPopup('/codePopup.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=main_s&ccd_cd=<bean:write name="mainCode" property="ccdcd"/>',680,200,0,0)}"><img src="/images/common/btn_s_modify.gif" alt="수정" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('시스템용 코드입니다.\n삭제시 시스템에 영향이 있을 수 있습니다.\n삭제시 하위코드가 모두 삭제됩니다.\n\n그래도 삭제하시겠습니까?')){window.open('/codeDB.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=main_d&ccd_cd=<bean:write name="mainCode" property="ccdcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" alt="삭제" align="absmiddle"></a>
						                        </logic:equal>
						                        <logic:notEqual name="codeForm" property="gbn" value="s">
						                        	<a href="javascript:showModalPopup('/codePopup.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=main_s&ccd_cd=<bean:write name="mainCode" property="ccdcd"/>',680,200,0,0)"><img src="/images/common/btn_s_modify.gif" alt="수정" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('하위코드가 모두 삭제됩니다.\n그래도 삭제하시겠습니까?')){window.open('/codeDB.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=main_d&ccd_cd=<bean:write name="mainCode" property="ccdcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" alt="삭제" align="absmiddle"></a>
						                       	</logic:notEqual>	
		                              </td>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="codeForm" property="mainlist">
				                  		<tr> 
	                              <td class="save_s2" colspan="3" align="center">등록된 자료가 없습니다.</td>
	                            </tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td> 
                        <!--주코드 저장 테이블-->
                        <html:form method="POST" action="/codeDB.do">
	                      <input type="hidden" name="gbn" value="<bean:write name="codeForm" property="gbn"/>">
	                      <input type="hidden" name="mode" value="main_i">
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t" width="80">주코드</td>
                            <td class="save_t">주코드명</td>
                            <td class="save_t" width="95">저장</td>
                          </tr>
                          <tr> 
                            <td class="save_s"><input type="text" name="ccd_cd" style="width:95%" maxlength="3" onkeydown="key_num()"></td>
                            <td class="save_s"><input type="text" name="ccd_name" style="width:95%" maxlength="50" onkeydown="key_entertotab()"><input type="text" style="width:0" onfocus="main_submit()"></td>
                            <td class="save_s"><a href="javascript:main_submit()"><img src="/images/common/btn_s_save.gif" alt="저장" align="absmiddle"></a>
                          </tr>
                        </table>
                        </html:form>
                      </td>
                    </tr>
                  </table>
                </td>
                <td background="/images/admin/sero_line.gif" width="1"></td>
                <td width="440" valign="top"> 
                  <table width="420" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
                      <td class="t3">
                      	<logic:notEmpty name="codeForm" property="ccd_name">
			                		<font color="#9933CC">[<bean:write name="codeForm" property="ccd_name"/>(<bean:write name="codeForm" property="ccd_cd"/>)]</font> 부코드
			                	</logic:notEmpty>
			                	<logic:empty name="codeForm" property="ccd_name">
			                		부코드
			                	</logic:empty>
                      </td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t2" width="60">부코드</td>
                            <td class="save_t2">부코드명</td>
                            <td class="save_t2" width="100">부코드설명</td>
                            <td class="save_t2" width="95">관리</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--부코드 리스트 테이블-->
                        <div id="childdiv" style="position:absolute; width:436; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table id="HeaderTable" width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          	<logic:notEmpty name="codeForm" property="sublist">
															<logic:iterate id="subCode" name="codeForm" property="sublist">
																<tr> 
		                              <td class="save_s" width="60"><bean:write name="subCode" property="ccdsubcd"/></td>
		                              <td class="save_s2"><bean:write name="subCode" property="ccdname"/></td>
		                              <td class="save_s2" width="100"><bean:write name="subCode" property="ccddesc"/></td>
		                              <td class="save_s" width="95">
		                              	<logic:equal name="codeForm" property="gbn" value="s">
						                        	<a href="javascript:if(confirm('시스템용 코드입니다.\n수정시 시스템에 영향이 있을 수 있습니다.\n\n그래도 수정하시겠습니까?')){showModalPopup('/codePopup.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=sub_s&ccd_cd=<bean:write name="codeForm" property="ccd_cd"/>&ccd_sub_cd=<bean:write name="subCode" property="ccdsubcd"/>',680,290,0,0)}"><img src="/images/common/btn_s_modify.gif" alt="수정" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('시스템용 코드입니다.\n삭제시 시스템에 영향이 있을 수 있습니다.\n\n그래도 삭제하시겠습니까?')){window.open('/codeDB.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=sub_d&ccd_cd=<bean:write name="codeForm" property="ccd_cd"/>&ccd_sub_cd=<bean:write name="subCode" property="ccdsubcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" alt="삭제" align="absmiddle"></a>
						                        </logic:equal>
						                        <logic:notEqual name="codeForm" property="gbn" value="s">
						                        	<a href="javascript:showModalPopup('/codePopup.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=sub_s&ccd_cd=<bean:write name="codeForm" property="ccd_cd"/>&ccd_sub_cd=<bean:write name="subCode" property="ccdsubcd"/>',680,290,0,0)"><img src="/images/common/btn_s_modify.gif" alt="수정" align="absmiddle"></a>
						                        	<a href="javascript:if(confirm('삭제하시겠습니까?')){window.open('/codeDB.do?gbn=<bean:write name="codeForm" property="gbn"/>&mode=sub_d&ccd_cd=<bean:write name="codeForm" property="ccd_cd"/>&ccd_sub_cd=<bean:write name="subCode" property="ccdsubcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" alt="삭제" align="absmiddle"></a>
						                       	</logic:notEqual>	
		                              </td>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="codeForm" property="sublist">
				                  		<tr> 
	                              <td class="save_s2" colspan="4" align="center">등록된 자료가 없습니다.</td>
	                            </tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td> 
                        <!--부코드 저장 테이블-->
                        <html:form method="POST" action="/codeDB.do">
	                      <input type="hidden" name="gbn" value="<bean:write name="codeForm" property="gbn"/>">
	                      <input type="hidden" name="ccd_cd" value="<bean:write name="codeForm" property="ccd_cd"/>">
	                      <input type="hidden" name="mode" value="sub_i">
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4" style="table-layout:fixed;">
                          <tr> 
                            <td class="save_t" width="60">부코드</td>
                            <td class="save_t">부코드명</td>
                            <td class="save_t" width="100">부코드설명</td>
                            <td class="save_t" width="95">저장</td>
                          </tr>
                          <tr> 
                            <td class="save_s"><input type="text" name="ccd_sub_cd" style="width:95%" maxlength="20" onkeydown="key_num()"></td>
                            <td class="save_s"><input type="text" name="ccd_name" style="width:95%" maxlength="50" onkeydown="key_entertotab()"></td>
                            <td class="save_s"><input type="text" name="ccd_desc" style="width:95%" maxlength="50" onkeydown="key_entertotab()"><input type="text" style="width:0" onfocus="sub_submit()">
                            <td class="save_s"><a href="javascript:sub_submit()"><img src="/images/common/btn_s_save.gif" alt="저장" align="absmiddle"></a>
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