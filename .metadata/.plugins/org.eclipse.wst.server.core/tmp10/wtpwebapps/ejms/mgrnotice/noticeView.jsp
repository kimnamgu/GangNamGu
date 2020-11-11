<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 보기
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
<% if ("001".equals(session.getAttribute("isSysMgr"))){ %>
	<jsp:include page="/include/header_admin.jsp" flush="true"/>
<% } else { %>
	<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>
<script>
menu5.onmouseover();
menu55.onmouseover();

function clickdel() {
	if (confirm('삭제하시겠습니까?')==false) return;
	window.open('/noticeSave.do?seq=<bean:write name="noticeForm" property="seq"/>&gbn=D', '_self');
}
</script>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_19.gif" alt="공지사항 상세보기" /></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp; </td>
              </tr>
              <tr> 
                <td valign="top"> 
                  <!--글보기 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="70" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제목</td>
                      <td class="bg2"></td>
                      <td width="749" class="t1Bg"><bean:write name="noticeForm" property="title"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr valign="top"> 
                      <td colspan="3" class="t1" style="padding:30 10 30 10;"><bean:write name="noticeForm" property="content" filter="false"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="70" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">첨부파일</td>
                      <td class="bg2"></td>
                      <td class="t1Bg2">
                      	<logic:notEmpty name="noticeForm" property="flist">
													<logic:iterate id="filelist" name="noticeForm" property="flist">
														<table border="0" cellspacing="0" cellpadding="0">
			                    		<tr height="25">
			                    			<td><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="filelist" property="filenm"/>&fileName=<bean:write name="filelist" property="originfilenm"/>"><bean:write name="filelist" property="originfilenm"/></a></td>														
															</tr>
														</table>
													</logic:iterate>
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                  </table>
                  <!--글보기 테이블 끝-->
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right">
					            	<% if ("001".equals(session.getAttribute("isSysMgr"))){ %>
					            		<a class="list_s2" href="/noticeWrite.do?seq=<bean:write name="noticeForm" property="seq"/>"><img src="/images/common/btn_modify4.gif" alt="수정"></a>
	                        <a class="list_s2" href="javascript:clickdel()"><img src="/images/common/btn_del3.gif" alt="삭제"></a>
					            	<% } %>
                        <a class="list_s2" href="/noticeList.do"><img src="/images/common/btn_list2.gif" alt="목록"></a>
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
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>