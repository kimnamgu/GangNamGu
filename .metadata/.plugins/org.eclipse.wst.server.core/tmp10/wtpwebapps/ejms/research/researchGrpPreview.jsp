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
<html>
<head>
<title>설문조사그룹</title>
<base target="_self">
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/research.js"></script>
<script type="text/javascript">
function cancel(){
	self.close();
}

function choice(rchgrpno){
	var opener = window.dialogArguments;
	self.close();  
	opener.location.href = "/researchGrpChoice.do?mode=choice&rchgrpno=" + rchgrpno; 
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow(-1, (screen.availHeight * 0.8))">
<form id=frm name=frm method=post>
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
          <td background="/images/common/title_bg.gif" height="38" width="644">
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
                            <td background="/images/common/poll_r_top.gif" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td background="/images/common/poll_r_left.gif" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:8 8 8 8;" align="center"><b><font color="blue" size="3"><bean:write name="researchForm" property="title"/></font></b></td>
                            <td background="/images/common/poll_r_right.gif" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td background="/images/common/poll_r_bottom.gif" height="6" width="538"></td>
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
                            <td background="/images/common/poll_r_top.gif" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td background="/images/common/poll_r_left.gif" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:8 8 8 8;">
                            	<font color="#333333">
                            		<logic:notEmpty name="researchForm" property="summary">
												        	<bean:define id="summary" name="researchForm" property="summary"/>
												        	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString())%>
												        </logic:notEmpty>
															</font>
														</td>
                            <td background="/images/common/poll_r_right.gif" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td background="/images/common/poll_r_bottom.gif" height="6" width="538"></td>
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
                            <td background="/images/common/poll_r_top.gif" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td background="/images/common/poll_r_left.gif" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:3 8 0 8;"><b><font color="#008CD4">[<bean:write name="researchForm" property="coldeptnm" />]<bean:write name="researchForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="researchForm" property="coldepttel"/></font><b></td>
                            <td background="/images/common/poll_r_right.gif" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td background="/images/common/poll_r_bottom.gif" height="6" width="538"></td>
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
                            <td background="/images/common/poll_r_top.gif" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td background="/images/common/poll_r_left.gif" width="6"></td>
                            <td bgcolor="#FFFFFF" width="538" style="padding:3 8 0 8;"><b><font color="#B043D8"><bean:write name="researchForm" property="strymd"/>&nbsp;부터&nbsp;&nbsp;&nbsp;<bean:write name="researchForm" property="endymd"/>&nbsp;까지</font></b></td>
                            <td background="/images/common/poll_r_right.gif" width="6"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
                            <td background="/images/common/poll_r_bottom.gif" height="6" width="538"></td>
                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <logic:equal name="researchForm" property="mode" value="result">
	                    <tr> 
	                      <td width="70"><b><font color="#8E8E8E">참여대상</font></b></td>
	                      <td width="550"> 
	                        <table width="550" border="0" cellspacing="0" cellpadding="0">
	                          <tr> 
	                            <td><img src="/images/common/poll_r_01.gif" width="6" height="6" alt=""></td>
	                            <td background="/images/common/poll_r_top.gif" height="6" width="538"></td>
	                            <td><img src="/images/common/poll_r_02.gif" width="6" height="6" alt=""></td>
	                          </tr>
	                          <tr> 
	                            <td background="/images/common/poll_r_left.gif" width="6"></td>
	                            <td bgcolor="#FFFFFF" width="538" style="padding:3 8 0 8;">
	                            	<b><font color="darkolivegreen">
	                            		<% if (nexti.ejms.common.appInfo.isOutside()){ %>
		                            		<logic:equal name="researchForm" property="range" value="1">
													        		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
													        	</logic:equal>
													         	<logic:equal name="researchForm" property="range" value="2">
													         		<bean:define id="rangedetail" name="researchForm" property="rangedetail"/>
														      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", "2")%>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)
													        	</logic:equal>&nbsp;&nbsp;
													        <% } %>
	                            	</font></b>
	                            </td>
	                            <td background="/images/common/poll_r_right.gif" width="6"></td>
	                          </tr>
	                          <tr> 
	                            <td><img src="/images/common/poll_r_03.gif" width="6" height="6" alt=""></td>
	                            <td background="/images/common/poll_r_bottom.gif" height="6" width="538"></td>
	                            <td><img src="/images/common/poll_r_04.gif" width="6" height="6" alt=""></td>
	                          </tr>
	                        </table>
	                      </td>
	                    </tr>
	                  </logic:equal>
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
				      		out.print("<tr><td class='ptitle'><img src='/images/common/dot_05.gif' align='absmiddle' alt=''>");
				      		out.print("&nbsp;&nbsp;등록된 설문조사가 없습니다.</td></tr>");
				      	}
			      		for ( int i = 0; rList != null && i < rList.length; i++ ) {
			      			nexti.ejms.research.model.ResearchBean rBean =
			      				nexti.ejms.research.model.ResearchManager.instance().getRchMst(Integer.parseInt(rList[i]), "");
			      	%>
			      	<tr>
			      		<td class="ptitle" width="15"><img src="/images/common/dot_05.gif" align="absmiddle" alt=""></td>
          			<td class="ptitle" width="520"><%=rBean.getTitle()%></td>
          			<td align="right">
          				<logic:equal name="researchForm" property="mode" value="preview">
	          				<a href="javascript:showModalPopup('/researchPreview.do?rchno=<%=rBean.getRchno()%>&grp=y', 700, 680, 0, 0);"><img src="/images/common/view4.gif" alt="미리보기"></a>
          				</logic:equal>
          				<logic:equal name="researchForm" property="mode" value="getused">
	          				<a href="javascript:showModalPopup('/researchPreview.do?rchno=<%=rBean.getRchno()%>&grp=y', 700, 680, 0, 0);"><img src="/images/common/view4.gif" alt="미리보기"></a>
          				</logic:equal>
          				<logic:equal name="researchForm" property="mode" value="research">          				
          					<% if ( "1".equals(rBean.getRange()) ) { %>
          						<a href="javascript:showPopup('<%=basePath%>research.do?rchno=<%=rBean.getRchno()%>&grp=y&rchgrpno=<bean:write name="researchForm" property="rchgrpno"/>', 700, 680, 0, 0);"><img src="/images/common/view5.gif" alt="설문응답"></a>
          					<% } else { %>
          						<a href="javascript:showPopup('<%=outsideUrl%>outsideRchView.do?rchno=<%=rBean.getRchno()%>&grp=y&rchgrpno=<bean:write name="researchForm" property="rchgrpno"/>', 700, 680, 0, 0);"><img src="/images/common/view5.gif" alt="설문응답"></a>
          					<% } %>
          				</logic:equal>
          				<logic:equal name="researchForm" property="mode" value="result">
          					<a href="javascript:showModalPopup('/researchResult.do?rchno=<%=rBean.getRchno()%>&range=<%=rBean.getRange()%>&grp=y', 700, 680, 0, 0);"><img src="/images/common/view6.gif" alt="결과보기"></a>
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
                	<logic:equal name="researchForm" property="mode" value="getused">
										<a href="javascript:choice('<bean:write name="researchForm" property="rchgrpno"/>')"><img src="/images/common/btn_b_select.gif" alt="선택하기"></a>
										<a href="javascript:cancel()"><img src="/images/common/btn_close.gif" alt="닫기"></a>
								  </logic:equal>
								  <logic:notEqual name="researchForm" property="mode" value="getused">
								  	<a href="javascript:cancel()"><img src="/images/common/btn_close.gif" alt="닫기"></a>
								  </logic:notEqual>
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