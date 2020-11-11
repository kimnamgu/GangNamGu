<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서작성 취합문서정보
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
<jsp:include page="/include/header_user.jsp" flush="true"/>
<jsp:include page="/include/processing.jsp" flush="true"/>

<script src="/fckeditor/fckeditor.js"></script>
<script src="/script/common.js"></script>
<script src="/script/workPlan.js"></script>
<script language="javascript">
function click_move(actionpath) {
    this.document.location.href = actionpath;
}
function click_proc(action) {
    var frm = document.forms[0];
    var planno = frm.planno.value;
    var path="";
    
    if(action == 'modify') {
        path = '/workplanMake.do?planno='+planno+'&mode='+action;
        click_move(path);
    } else if(action == 'delete') {
    	if ( confirm("계획을 삭제하시겠습니까?") ) {
        path = '/workplanDelEnd.do?planno='+planno+'&mode='+action;
        click_move(path);
      }
    } else if(action == 'end') {
    	if ( confirm("업무를 종료하시겠습니까?") ) {
        path = '/workplanDelEnd.do?planno='+planno+'&mode='+action;
        click_move(path);
    	}
    } else if(action == 'start') {
    	if ( confirm("업무를 재개하시겠습니까?") ) {
        path = '/workplanDelEnd.do?planno='+planno+'&mode='+action;
        click_move(path);
    	}
    }
}
/* 이전 검색값으로 목록보기 (JHKim, 13.05.14) */
function back2list(){
    var searchyear = 'searchyear='+document.forms[0].searchyear.value;
    var searchstrdt = 'searchstrdt='+document.forms[0].searchstrdt.value;
    var searchenddt = 'searchenddt='+document.forms[0].searchenddt.value;
    var searchstatus = 'searchstatus='+document.forms[0].searchstatus.value;
    var searchdeptcd = 'searchdeptcd='+document.forms[0].searchdeptcd.value;
    var searchchrgunitcd = 'searchchrgunitcd='+document.forms[0].searchchrgunitcd.value;
    var searchgubun = 'searchgubun='+document.forms[0].searchgubun.value;
    var searchtitle = 'searchtitle='+document.forms[0].searchtitle.value;
    var dept = 'searchdept='+document.forms[0].searchdeptcd.value;
    var orggbn = 'orggbn='+document.forms[0].orggbn.value;
    var page = 'page='+document.forms[0].page.value;
    
    var path = '/workplanList.do?'+searchyear+'&'+searchstrdt+'&'+searchenddt+'&'+searchstatus+'&'+searchdeptcd+'&'+searchchrgunitcd+'&'+searchgubun+'&'+searchtitle+'&'+dept+'&'+orggbn+'&'+page;
    
    click_move(path);
}
</script>

<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/workplanInfoView" method="post" enctype="multipart/form-data">
<html:hidden property="planno"/>
<html:hidden name="workplanForm" property="searchyear"/>
<html:hidden name="workplanForm" property="searchstrdt"/>
<html:hidden name="workplanForm" property="searchenddt"/>
<html:hidden name="workplanForm" property="searchstatus"/>
<html:hidden name="workplanForm" property="searchdeptcd"/>
<html:hidden name="workplanForm" property="searchchrgunitcd"/>
<html:hidden name="workplanForm" property="searchgubun"/>
<html:hidden name="workplanForm" property="searchtitle"/>
<html:hidden name="workplanForm" property="orggbn"/>
<html:hidden name="workplanForm" property="page"/>
<script>
</script>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top">
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/workplan/title_03.gif" alt=""></td>
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
                  <!--상단 선택메뉴 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/workplan/s_m_01.gif" width="94" height="41" alt="시행계획"></td>
                            <td>&nbsp;</td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;">
                                <logic:equal name="workplanForm" property="status" value="1">
                            	   <img src="/images/workplan/btn_wrkadd_2.gif" width="86" height="24" alt="실적등록" style="cursor:hand" onclick="workFrame1.workFrame2.location.href='/workResultWrite.do?planno=<bean:write name="workplanForm" property="planno"/>'">
                                </logic:equal>
                          		<bean:define id="auth" type="java.lang.String" value="N"/>
                          		<bean:define id="uid" type="java.lang.String" name="workplanForm" property="chrgusrcd"/>
                          		<logic:equal name="user_id" value="<%=uid%>" scope="session">
                          			<!-- 작성자도 수정할 수 있는 권한 없도록 처리
                          		  <bean:define id="auth" type="java.lang.String" value="Y"/>
                          		   -->
                          		</logic:equal>
                          		<logic:equal name="isSysMgr" value="001" scope="session">
                          			<bean:define id="auth" type="java.lang.String" value="Y"/>
                          		</logic:equal>
                          		<logic:equal name="auth" value="Y">
                          			<logic:equal name="workplanForm" property="status" value="1">
				                        	<img src="/images/workplan/btn_plnend.gif" width="86" height="24" alt="업무종료" style="cursor:hand" onclick="click_proc('end')">
				                        </logic:equal>
				                        <logic:equal name="workplanForm" property="status" value="2">
				                        	<img src="/images/workplan/btn_plnstart.gif" width="86" height="24" alt="업무재개" style="cursor:hand" onclick="click_proc('start')">
				                        </logic:equal>
  		                          <img src="/images/workplan/btn_plnmod.gif" width="86" height="24" alt="계획수정" style="cursor:hand" onclick="click_proc('modify')">
	                              <img src="/images/workplan/btn_plndel.gif" width="86" height="24" alt="계획삭제" style="cursor:hand" onclick="click_proc('delete')"> 
                          		</logic:equal>                      		
	                          	<img src="/images/workplan/btn_list.gif" width="86" height="24" alt="목록보기" style="cursor:hand" onclick="back2list()">
														</td>                              
                          </tr>
                        </table>
                      </td>
                      <td width="5"><img src="/images/common/select_right.gif" width="5" height="41" alt=""></td>
                    </tr>
                  </table>
                  <!--상단 선택메뉴 테이블-->
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td> 
                  <!--새취합문서 작성하기 테이블-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">사 업 명</td>
                      <td class="bg2"></td>
                      <td class="t1" width="715"><bean:write name="workplanForm" property="title"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">담 당 자</td>
                      <td class="bg2"></td>
                      <td class="t1">
                      	<bean:write name="workplanForm" property="upperdeptnm"/>&nbsp;&nbsp;
                      	<bean:write name="workplanForm" property="deptnm"/>&nbsp;&nbsp;
                      	<logic:notEmpty name="workplanForm" property="chrgunitnm">
                      		<bean:write name="workplanForm" property="chrgunitnm"/>&nbsp;&nbsp;
                      	</logic:notEmpty>
                      	<bean:write name="workplanForm" property="chrgusrnm"/>&nbsp;&nbsp;
                      	<logic:notEmpty name="workplanForm" property="user_tel">
	                      	전화 : <bean:write name="workplanForm" property="user_tel"/>
                      	</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">사업기간</td>
                      <td class="bg2"></td>
                      <td colspan="3" class="t1">
                        <bean:write name="workplanForm" property="strdt"/> ~ <bean:write name="workplanForm" property="enddt"/>
                        <logic:equal name="workplanForm" property="status" value="1">
                        	<font color="blue" style="font-weight: bold;">(업무진행중)</font>
                        </logic:equal>
                        <logic:equal name="workplanForm" property="status" value="2">
                        	<font color="red" style="font-weight: bold;">(업무종료)</font>
                        </logic:equal>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr>
                      <td colspan="3">
                      	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                      		<tr>
                      			<td>
                      				<div style="width:810; margin:10 10 10 10; overflow:auto;"><bean:write name="workplanForm" property="content" filter="false"/></div>
                      			</td>
                      		</tr>
                      	</table>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일1</td>
                      <td class="bg2"></td>
                      <td class="t1">
                        <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm1"/>&fileName=<bean:write name="workplanForm" property="orgfilenm1"/>"><bean:write name="workplanForm" property="orgfilenm1"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일2</td>
                      <td class="bg2"></td>
                      <td class="t1">
												<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm2"/>&fileName=<bean:write name="workplanForm" property="orgfilenm2"/>"><bean:write name="workplanForm" property="orgfilenm2"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일3</td>
                      <td class="bg2"></td>
                      <td class="t1">
												<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="workplanForm" property="filenm3"/>&fileName=<bean:write name="workplanForm" property="orgfilenm3"/>"><bean:write name="workplanForm" property="orgfilenm3"/></a>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                  </table><br><br>
                  <table width="830" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td id="workFrameTD1" height="500">
												<iframe id="workFrame1" name="workFrame1" src="/workResultList.do?planno=<bean:write name="workplanForm" property="planno"/>" width="100%" height="100%" frameborder="0" scrolling="auto" title="목록"></iframe>
											</td>
										</tr>
									</table>
                  <!--새취합문서 작성하기 테이블-->
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
            </table>
          </td>
          <td width="11"></td>
          <td width="2" bgcolor="#ECF3F9"></td>
        </tr>
      </table>
    </td>
  </tr>
</html:form>
</table>
<br><br>
<script>
try {
	document.forms[0].title.focus();
} catch ( exception ) {}
</script>

<html:messages id="msg" message="true">
<script><bean:write name="msg" filter="false"/></script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>