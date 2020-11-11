<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 작성
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
<script language="javascript">
menu5.onmouseover();
menu55.onmouseover();

	function check_submit(frm){
		var title = frm.title.value.trim();
		var content = frm.content.value.trim();
				
		if(title == ''){
			alert('제목을 입력하세요.');
			frm.title.focus();
			return;
		}

		if(content == ''){
			alert('내용을 입력하세요.');
			frm.content.focus();
			return;
		}
	
		frm.submit();
	}
</script>

<html:form method="POST" action="/noticeSave" enctype="multipart/form-data">
<html:hidden property="seq"/>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/admin/title_20.gif" alt="공지사항 새로작성" /></td>
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
                  <!--글쓰기 테이블 시작-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class="list_bg2"></td>
                    </tr>
                    <tr> 
                      <td width="70" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">제목</td>
                      <td class="bg2"></td>
                      <td width="749" class="t1Bg"><html:text property="title" style="width:100%;"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr valign="top"> 
                      <td colspan="3" class="t1" style="padding:10 10 10 10;"><html:textarea property="content" rows="12" style="width:100%;"/></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="70" class="s1"><img src="/images/common/dot_02.gif" width="15" height="14" align="absmiddle" alt="">첨부파일</td>
                      <td class="bg2"></td>
                      <td class="t1Bg2">
                      	<logic:notEmpty name="noticeForm" property="flist">
													<logic:iterate id="filelist" name="noticeForm" property="flist">
														<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="filelist" property="filenm"/>&fileName=<bean:write name="filelist" property="originfilenm"/>"><bean:write name="filelist" property="originfilenm"/></a>&nbsp;
														<a href="/noticeFileDel.do?seq=<bean:write name="filelist" property="seq"/>&fileseq=<bean:write name="filelist" property="fileseq"/>"><img src="/images/common/btn_del2.gif" align="absmiddle" alt=""></a><br>
													</logic:iterate>
												</logic:notEmpty>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                    <tr> 
                      <td width="70" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">파일첨부</td>
                      <td class="bg2"></td>
                      <td class="t1Bg2">
                      	<input type="file" name="fileList[0]" style="width:100%" contentEditable="false"><br>
												※&nbsp;<font color="red">10Mbyte</font>&nbsp;이하의 파일을 선택하세요.
											</td>
                    </tr>
                    <tr> 
                      <td colspan="3" class="bg1"></td>
                    </tr>
                  </table>
                  <!--글쓰기 테이블 끝-->
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
                      	<a href="javascript:check_submit(document.forms[0])"><img src="/images/common/btn_save2.gif" alt="저장"></a>
                        <a href="/noticeList.do"><img src="/images/common/btn_cancel.gif" alt="취소"></a>
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
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>