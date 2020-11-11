<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리
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
menu1.onmouseover();
menu11.onmouseover();

	var Posi = <bean:write name="groupForm" property="posi"/>;
	var cPosi = <bean:write name="groupForm" property="cposi"/>;
	
	function main_submit()
	{
		if (document.forms[1].grplistcd.value.replace(" ","")=="")
		{
			alert("주코드를 입력하여 주십시오.")
			document.forms[1].grplistcd.focus();
			return;
		}
		if (document.forms[1].grplistnm.value.replace(" ","")=="")
		{
			alert("주코드명를 입력하여 주십시오.")
			document.forms[1].grplsitnm.focus();
			return;
		}

		document.forms[1].submit();
	}
	
	function hyperlink(GRPLISTCD)
	{
			var ppos = parentdiv.scrollTop;	 //부모창스크롤(좌측)
			var cpos = childdiv.scrollTop;	 //자식창스크롤(우측)
	    var today = new Date();
			window.open('/grpList.do?grplistcd='+GRPLISTCD+'&posi='+ppos+'&cposi='+cpos+'&time='+today,'_self')
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
          <td background="/images/common/title_bg.gif" height="38" width="830"><img src="/images/admin/title_02.gif" alt="배포목록관리" /></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11" width="830"></td>
        </tr>
        <tr> 
          <td height="6" valign="top" width="830"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="379" valign="top"> 
                  <table width="377" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="t3">배포목록 마스터</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">목록코드</td>
                            <td class="save_t2">배포목록명칭</td>
                            <td class="save_t2" width="95">관리</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top"> 
                        <!--배포목록 마스터 리스트 테이블-->
                        <div id="parentdiv" style="position:absolute; width:376; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table width="360" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="groupForm" property="mainlist">
															<logic:iterate id="mainCode" name="groupForm" property="mainlist">
		                            <tr> 
		                              <td class="save_s" width="80"><bean:write name="mainCode" property="grplistcd"/></td>
		                              <td class="save_s2"><a class="saveL" href="javascript:hyperlink('<bean:write name="mainCode" property="grplistcd"/>')"><bean:write name="mainCode" property="grplistnm"/></a></td>
		                              <td class="save_s" width="95">
		                                <a href="javascript:showModalPopup('/grpPopup.do?mode=main_m&grplistcd=<bean:write name="mainCode" property="grplistcd"/>',680,230,0,0)"><img src="/images/common/btn_s_modify.gif" align="absmiddle" alt="수정"></a>
		                                <a href="javascript:if(confirm('하위코드가 모두 삭제됩니다.\n그래도 삭제하시겠습니까?')){window.open('/grpDB.do?mode=main_d&grplistcd=<bean:write name="mainCode" property="grplistcd"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self')}"><img src="/images/common/btn_del2.gif" align="absmiddle" alt="삭제"></a>
		                              </td>
		                            </tr>
	                            </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="groupForm" property="mainlist">
				                 			<tr>
				                 				<td colspan="3" class="save_s2" align="center">등록된 자료가 없습니다.</td>
				                			</tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
                <td background="/images/admin/sero_line.gif" width="1"></td>
                <td width="440" valign="top"> 
                  <table width="420" border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr> 
                      <td class="t3"><font color="#9933CC">[<bean:write name="groupForm" property="grplistnm"/>(<bean:write name="groupForm" property="grplistcd"/>)]</font> 세부코드</td>
                    </tr>
                    <tr> 
                      <td> 
                        <table width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          <tr> 
                            <td class="save_t2" width="80">부서코드</td>
                            <td class="save_t2">부서명</td>
                            <td class="save_t2" width="60">관리</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="280" valign="top">                       
                        <!--세부코드 리스트 테이블-->
                        <div id="childdiv" style="position:absolute; width:436; height:280; z-index:2; overflow: auto" onscroll="SetScrollPos(this)"> 
                          <table id="HeaderTable" width="420" border="0" cellspacing="1" cellpadding="0" bgcolor="#C4C4C4">
                          	<logic:notEmpty name="groupForm" property="sublist">
															<logic:iterate id="subCode" name="groupForm" property="sublist">
																<tr> 
		                              <td class="save_s" width="80"><bean:write name="subCode" property="code"/></td>
		                              <td class="save_s2"><bean:write name="subCode" property="displayName"/></td>
		                              <td class="save_s" width="60"><a href="javascript:if(confirm('삭제하시겠습니까?')){window.open('/grpDB.do?mode=sub_d&grplistcd=<bean:write name="groupForm" property="grplistcd"/>&seq=<bean:write name="subCode" property="seq"/>'+'&posi='+parentdiv.scrollTop+'&cposi='+childdiv.scrollTop,'_self') }"><img src="/images/common/btn_del2.gif" align="absmiddle" alt="삭제"></a>
		                            </tr>
				                      </logic:iterate>
				                 		</logic:notEmpty>
				                  	<logic:empty name="groupForm" property="sublist">
				                 			<tr>
				                 				<td colspan="3" class="save_s2" align="center">등록된 자료가 없습니다.</td>
				                			</tr>
				                 		</logic:empty>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <td colspan="3" height="1" bgcolor="#E8E8E8"></td>
              </tr>
              <tr> 
                <td colspan="3" height="5"></td>
              </tr>
              <tr> 
                <!--하단 버튼 -->
                <td colspan="3" height="2">
                	<a href="javascript:showModalPopup('/grpPopup.do?mode=main_i',680,200,0,0)"><img src="/images/common/btn_master.gif" alt="마스터등록"></a>
                  <a href="javascript:showModalPopup('/grpPopup.do?mode=sub_i',680,400,0,0)"><img src="/images/common/btn_code.gif" alt="세부부서코드등록"></a>
                </td>
              </tr>
              <tr> 
                <td colspan="3">&nbsp;</td>
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