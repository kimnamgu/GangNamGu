<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 미리보기
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
<%
	String basePath = nexti.ejms.common.appInfo.getAp_address();
	String outsideUrl = nexti.ejms.common.appInfo.getOutsideweburl();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>설문조사그룹</title>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript" src="/script/research.js"></script>
<script type="text/javascript">
function cancel(){
	self.close();
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="window.resizeTo(710, screen.availHeight * 0.8)">
<form id=frm name=frm method=post action="">
<input type="hidden" name="rchgrpno" value="<bean:write name="researchForm" property="rchgrpno"/>">
<table width="680" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" width="13" height="13" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" width="13" height="13" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="644" valign="top"> 
      <table width="644" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td style="background-image: url('/images/common/title_bg.gif')" height="38" width="644">
          	<logic:equal name="researchForm" property="mode" value="preview">
          		<img src="/images/collect/title_48.gif" alt="">
          	</logic:equal>
          	<logic:equal name="researchForm" property="mode" value="getused">
          		<img src="/images/collect/title_48.gif" alt="">
          	</logic:equal>
          	<logic:equal name="researchForm" property="mode" value="research">
          		<img src="/images/collect/title_36.gif" alt="">
          	</logic:equal>
          	<logic:equal name="researchForm" property="mode" value="result">
	          	<img src="/images/collect/title_49.gif" alt="">
          	</logic:equal>
          </td>
        </tr>
        <tr> 
          <td height="5" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_05.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#EEEEEE" height="5" width="634"></td>
                <td><img src="/images/common/btn_r_06.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#EEEEEE"> 
                <td width="5"></td>
                <td bgcolor="#EEEEEE" width="634"> 
                  <table width="620" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr> 
                      <td colspan="2" height="5"></td>
                    </tr>
                    <tr> 
                      <td width="70"><b><font color="#8E8E8E">설문제목</font></b></td>
                      <td width="550"> 
                        <table width="550" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/common/poll_r_01.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_top.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td style="background-image: url('/images/common/poll_r_left.gif')" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:8px 8px 8px 8px;" align="center"><b><font color="blue" size="3"><bean:write name="researchForm" property="title"/></font></b></td>
                            <td style="background-image: url('/images/common/poll_r_right.gif')" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_bottom.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="5"></td>
                    </tr>
                    <tr> 
                      <td width="70"><b><font color="#8E8E8E">설문개요</font></b></td>
                      <td width="550"> 
                        <table width="550" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/common/poll_r_01.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_top.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td style="background-image: url('/images/common/poll_r_left.gif')" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:8px 8px 8px 8px;">
                            	<font color="#333333">
                            		<logic:notEmpty name="researchForm" property="summary">
												        	<bean:define id="summary" name="researchForm" property="summary"/>
												        	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString())%>
												        </logic:notEmpty>
															</font>
														</td>
                            <td style="background-image: url('/images/common/poll_r_right.gif')" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_bottom.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="5"></td>
                    </tr>
                    <tr> 
                      <td width="70"><b><font color="#8E8E8E">설문담당자</font></b></td>
                      <td width="550"> 
                        <table width="550" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/common/poll_r_01.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_top.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td style="background-image: url('/images/common/poll_r_left.gif')" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:3px 8px 0px 8px;"><b><font color="#008CD4">[<bean:write name="researchForm" property="coldeptnm" />]<bean:write name="researchForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="researchForm" property="coldepttel"/></font></b></td>
                            <td style="background-image: url('/images/common/poll_r_right.gif')" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_bottom.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="5"></td>
                    </tr>
                    <tr> 
                      <td width="70"><b><font color="#8E8E8E">설문기간</font></b></td>
                      <td width="550"> 
                        <table width="550" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/common/poll_r_01.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_top.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td style="background-image: url('/images/common/poll_r_left.gif')" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:3px 8px 0px 8px;"><b><font color="#B043D8"><bean:write name="researchForm" property="strymd"/>&nbsp;부터&nbsp;&nbsp;&nbsp;<bean:write name="researchForm" property="endymd"/>&nbsp;까지</font></b></td>
                            <td style="background-image: url('/images/common/poll_r_right.gif')" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td style="background-image: url('/images/common/poll_r_bottom.gif')" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2" height="5"></td>
                    </tr>
                  </table>
                </td>
                <td width="5"></td>
              </tr>
              <tr> 
                <td><img src="/images/common/btn_r_07.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#EEEEEE" height="5" width="634"></td>
                <td><img src="/images/common/btn_r_08.gif" width="5" height="5" alt=""></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" width="644"></td>
        </tr>
        <tr> 
          <td width="644" class="ptext3">
	          <logic:notEmpty name="researchForm" property="headcont">
		        	<bean:define id="headcont" name="researchForm" property="headcont"/>
		    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>	
						</logic:notEmpty>
          </td>
        </tr>
        <tr>
         <td class="pbg"></td>
        </tr>
        <tr>
        	<td height="10"></td>
        </tr>
        <tr>
        	<td>
			      <table width="644" border="0" cellspacing="0" cellpadding="0">
			      	<%
			      		String rchnolist = ((nexti.ejms.research.form.ResearchForm)request.getAttribute("researchForm")).getRchnolist();
			      		String[] rList = null;
				      	if ( rchnolist != null ) {
				      		rList = rchnolist.toString().split(",");
				      	} else {
				      		out.print("<tr><td class='ptitle'><img src='/images/common/dot_05.gif' align='middle' alt=''>");
				      		out.print("&nbsp;&nbsp;등록된 설문조사가 없습니다.</td></tr>");
				      	}
			      		for ( int i = 0; rList != null && i < rList.length; i++ ) {
			      			nexti.ejms.research.model.ResearchBean rBean =
			      				nexti.ejms.outside.model.OutsideManager.instance().getRchMst(Integer.parseInt(rList[i]), "");
			      	%>
			      	<tr>
			      		<td class="ptitle" width="15"><img src="/images/common/dot_05.gif" align="middle" alt=""></td>
          			<td class="ptitle" width="520"><%=rBean.getTitle()%></td>
          			<td align="right">
          				<logic:equal name="researchForm" property="mode" value="research">          				
          					<% if ( "1".equals(rBean.getRange()) ) { %>
          						<a href="javascript:showPopup('<%=basePath%>research.do?rchno=<%=rBean.getRchno()%>&grp=y&rchgrpno=<bean:write name="researchForm" property="rchgrpno"/>&uid=<bean:write name="researchForm" property="crtusrid"/>', 700, 680, 0, 0);"><img src="/images/common/view5.gif" alt="설문응답"></a>
          					<% } else { %>
          						<a href="javascript:showPopup('<%=outsideUrl%>outsideRchView.do?rchno=<%=rBean.getRchno()%>&grp=y&rchgrpno=<bean:write name="researchForm" property="rchgrpno"/>&uid=<bean:write name="researchForm" property="crtusrid"/>', 700, 680, 0, 0);"><img src="/images/common/view5.gif" alt="설문응답"></a>
          					<% } %>
          				</logic:equal>
          			</td>
          		</tr>
          		<%
			      		}
          		%>
          	</table>
        	</td>
        </tr>
        <tr>
        	<td height="5"></td>
        </tr>
        <tr>
         <td class="pbg"></td>
        </tr>
        <tr>
        	<td height="5"></td>
        </tr>
        <tr>
					<td class="ptext3">
						<logic:notEmpty name="researchForm" property="tailcont">
							<bean:define id="tailcont" name="researchForm" property="tailcont"/>
							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(tailcont.toString())%> 
						</logic:notEmpty>	
					</td>
				</tr>
				<tr>
        	<td height="10"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="644" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="634"></td>
                <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#F6F6F6"> 
                <td bgcolor="#F6F6F6" width="5"></td>
                <td bgcolor="#F6F6F6" align="center" height="35">
								  	<a href="javascript:cancel()"><img src="/images/common/btn_close.gif" alt="닫기"></a>
                </td>
                <td width="5"></td>
              </tr>
              <tr> 
                <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="634"></td>
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
</form>
<jsp:include page="/include/tail.jsp" flush="true"/>