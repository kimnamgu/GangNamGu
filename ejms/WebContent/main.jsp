<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 프로그램 메인화면
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
	if ( "수원3740000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
		String user_id = (String)session.getAttribute("user_id");
		String delivery_yn = nexti.ejms.user.model.UserManager.instance().getUserInfo(user_id).getDelivery_yn();
		delivery_yn = nexti.ejms.util.Utils.nullToEmptyString(delivery_yn);
		
		if ( !("Y".equals(delivery_yn) || "001".equals((String)session.getAttribute("isSysMgr"))) ) {
			pageContext.setAttribute("수원3740000", "style='display:none'");
			
			//설문조사, 신청서 양식 보유여부
			java.sql.Connection con = null;
			java.sql.PreparedStatement pstmt = null;
			java.sql.ResultSet rs = null;
			try {
				pageContext.setAttribute("수원3740000-RCH", "style='display:none'");
				pageContext.setAttribute("수원3740000-REQ", "style='display:none'");
				pageContext.setAttribute("수원3740000-RCHREQ", "style='display:none'");	//기타업무 현황이미지 표시여부
				String dept_code = (String)session.getAttribute("dept_code");
				String sql = "SELECT \n" +
				             "  (SELECT COUNT(*) FROM RCHMST WHERE COLDEPTCD LIKE '" + dept_code + "' AND CHRGUSRID LIKE '" + user_id + "'), \n" +
				             "  (SELECT COUNT(*) FROM REQFORMMST WHERE COLDEPTCD LIKE '" + dept_code + "' AND CHRGUSRID LIKE '" + user_id + "') \n" +
				             "FROM DUAL";
				con = nexti.ejms.common.ConnectionManager.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					if ( rs.getInt(1) > 0 ) pageContext.removeAttribute("수원3740000-RCH");
					if ( rs.getInt(2) > 0 ) pageContext.removeAttribute("수원3740000-REQ");
					if ( pageContext.getAttribute("수원3740000-RCH") == null && pageContext.getAttribute("수원3740000-REQ") == null ) {
						pageContext.removeAttribute("수원3740000-RCHREQ");
					} else if ( pageContext.getAttribute("수원3740000-RCH") == null || pageContext.getAttribute("수원3740000-REQ") == null ) {
						pageContext.setAttribute("수원3740000-RCHREQ", "style='width:84'");
					}
				}
			} finally {
				nexti.ejms.common.ConnectionManager.close(con, pstmt, rs);
			}
		}
	}
%>
															
<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/exhibit.js"></script>
<table width="930" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td rowspan="2" width="20">&nbsp;</td>
    <td width="910" height="94" align="right" background="/images/img_01.gif" style="background-repeat:no-repeat;">
			<logic:equal value="N" name="getDept_YN" scope="session">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr> 
	          <td colspan="2"><img src="/images/r_01.gif" width="6" height="19" alt=""></td>
	          <td colspan="5" background="/images/r_bg.gif" width="264" height="19" align="center"><img src="/images/info_title_01.gif" width="65" height="19" alt="제출업무"></td>
	          <td colspan="2"><img src="/images/r_02.gif" width="6" height="19" alt=""></td>
	        </tr>
	        <tr>
	        	<td rowspan="2" bgcolor="#4F94E7" width="2"></td>
						<td rowspan="2">&nbsp;</td>
	        	<td colspan="5" height="15"></td>
	        	<td rowspan="2">&nbsp;</td>
            <td rowspan="2" bgcolor="#4F94E7" width="2"></td>
	        </tr>
					<tr>
            <td> 
              <table width="90" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td align="center"><a href="/deliveryList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image52','','/images/info_01_over.gif',1)"><img name="Image52" border="0" src="/images/info_01.gif" alt="배부대기"></a></td>
                </tr>
                <tr>
                  <td align="center"><a href="/deliveryList.do" class="info" title="배부대기"><%=request.getAttribute("deliCount").toString()%></a></td>
                </tr>
              </table>
            </td>
            <td width="1"><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
            <td> 
              <table width="90" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td align="center"><a href="/inputingList.do?gubun=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image53','','/images/info_02_over.gif',1)"><img name="Image53" border="0" src="/images/info_02.gif" alt="입력대기"></a></td>
                </tr>
                <tr> 
                  <td align="center"><a href="/inputingList.do?gubun=1" class="info" title="입력대기"><%=request.getAttribute("inputCount").toString()%></a></td>
                </tr>
              </table>
            </td>
            <td width="1"><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
            <td> 
              <table width="90" border="0" cellspacing="0" cellpadding="0">
                <tr> 
	                <td align="center"><a <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1  && "005".equals(session.getAttribute("user_gbn")) ) out.print("href='#'"); %> href="/doList.do?setSelectTopDept=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image58','','/images/info_07_over.gif',1)"><img name="Image58" border="0" src="/images/info_07.gif" alt="신청양식"></a></td>
	              </tr>
	              <tr> 
	                <td align="center"><a <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1  && "005".equals(session.getAttribute("user_gbn")) ) out.print("style='visibility:hidden'"); %> href="/doList.do?setSelectTopDept=1" class="info" title="신청양식"><%=request.getAttribute("reqCount").toString()%></a></td>
	              </tr>
              </table>
            </td>
					</tr>
				</table><br>
			</logic:equal>    	
    </td>
  </tr>
  <tr> 
    <td width="910"> 
      <table width="910" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/r_01.gif" width="6" height="19" alt=""></td>
          <td background="/images/r_bg.gif" width="898" height="19">
						<logic:equal value="Y" name="getDept_YN" scope="session">
	            <table border="0" cellspacing="0" cellpadding="0">
	              <tr> 
	                <td width="175">&nbsp;</td>
	                <td width="169" align="center"><img src="/images/info_title_01.gif" width="65" height="19" alt="제출업무"></td>
	                <td width="169" align="center"><img src="/images/info_title_02.gif" width="65" height="19" alt="취합업무"></td>
	                <td width="169" align="center" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='width:84'"); %> <%=pageContext.getAttribute("수원3740000-RCHREQ")%>><img src="/images/info_title_04.gif" width="65" height="19" alt="기타업무"></td>
	                <td width="169" align="center"><% if ( nexti.ejms.common.appInfo.isElecappr()) { %><img src="/images/info_title_03.gif" width="65" height="19" alt="전자결재"><% } %></td>
	                <td><img src="/images/img_02.gif" alt="" width="1"></td>
	              </tr>
	            </table>
            </logic:equal>
          </td>
          <td colspan="2"><img src="/images/r_02.gif" width="6" height="19" alt=""></td>
        </tr>
        <tr> 
          <td bgcolor="#4F94E7" width="2"></td>
          <td width="4"></td>
          <td width="898"> 
            <table width="898" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td background="/images/img_03.gif">
                	<logic:equal value="Y" name="getDept_YN" scope="session">
	                  <table width="898" height="110" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
												<td width="15"></td>
	                      <td width="160" align="center"><a href="/colldocMake.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image45','','/images/write_btn_over.gif',1)"><img name="Image45" border="0" src="/images/write_btn.gif" alt="새취합문서 작성하기"></a></td>
	                      <td width="683"> 
	                        <!--전체수치표시 테이블 시작-->
	                        <table border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                          	<td width="1"><img src="/images/bar_on.gif" width="1" height="15" alt=""></td>
	                            <td width="84"> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/deliveryList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image52','','/images/info_01_over.gif',1)"><img name="Image52" border="0" src="/images/info_01.gif" alt="배부대기"></a></td>
	                                </tr>
	                                <tr>
	                                  <td align="center"><a href="/deliveryList.do" class="info" title="배부대기"><%=request.getAttribute("deliCount").toString()%></a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1"><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
	                            <td width="84"> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/inputingList.do?gubun=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image53','','/images/info_02_over.gif',1)"><img name="Image53" border="0" src="/images/info_02.gif" alt="입력대기"></a></td>
	                                </tr>
	                                <tr> 
	                                  <td align="center"><a href="/inputingList.do?gubun=1" class="info" title="입력대기"><%=request.getAttribute("inputCount").toString()%></a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1"><img src="/images/bar_on.gif" width="1" height="15" alt=""></td>
	                            <td width="84"> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/collprocList.do?docstate=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image54','','/images/info_03_over.gif',1)"><img name="Image54" border="0" src="/images/info_03.gif" alt="취합진행"></a></td>
	                                </tr>
	                                <tr> 
	                                  <td align="center"><a href="/collprocList.do?docstate=2" class="info" title="취합진행"><%=request.getAttribute("collCount").toString()%></a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1"><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
	                            <td width="84"> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/collprocList.do?docstate=3" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image55','','/images/info_04_over.gif',1)"><img name="Image55" border="0" src="/images/info_04.gif" alt="마감대기"></a></td>
	                                </tr>
	                                <tr> 
	                                  <td align="center"><a href="/collprocList.do?docstate=3" class="info" title="마감대기"><%=request.getAttribute("endCount").toString()%></a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1"><img src="/images/bar_on.gif" width="1" height="15" alt=""></td>
	                            <td width="84" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %> <%=pageContext.getAttribute("수원3740000-RCH")%>> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/researchMyList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image57','','/images/info_06_over.gif',1)"><img name="Image57" border="0" src="/images/info_06.gif" alt="설문조사"></a></td>
	                                </tr>
	                                <tr> 
	                                  <td align="center"><a href="/researchMyList.do" class="info" title="설문조사"><%=request.getAttribute("rchCount").toString()%></a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %> <%=pageContext.getAttribute("수원3740000-RCH")%>><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
	                            <td width="84" <%=pageContext.getAttribute("수원3740000-REQ")%>> 
	                              <table width="84" border="0" cellspacing="0" cellpadding="0">
	                                <tr> 
	                                  <td align="center"><a href="/formList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image58','','/images/info_07_over.gif',1)"><img name="Image58" border="0" src="/images/info_07.gif" alt="신청양식"></a></td>
	                                </tr>
	                                <tr> 
	                                  <td align="center"><a href="/formList.do" class="info" title="신청양식"><%=request.getAttribute("jupCnt").toString()%>(<%=request.getAttribute("notProcCnt").toString()%>)</a></td>
	                                </tr>
	                              </table>
	                            </td>
	                            <td width="1" <%=pageContext.getAttribute("수원3740000-REQ")%>><img src="/images/bar_on.gif" width="1" height="15" alt=""></td>
	                            <% if (nexti.ejms.common.appInfo.isElecappr()) { %>
		                            <td width="84"> 
		                              <table width="84" border="0" cellspacing="0" cellpadding="0">
		                                <tr> 
		                                  <td align="center"><a href="/inputingList.do?gubun=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image59','','/images/info_08_over.gif',1)"><img name="Image59" border="0" src="/images/info_08.gif" alt="취합문서"></a></td>
		                                </tr>
		                                <tr> 
		                                  <td align="center"><a href="/inputingList.do?gubun=2" class="info" title="취합문서"><%=request.getAttribute("apprCol").toString()%></a></td>
		                                </tr>
		                              </table>
		                            </td>
	                            	<td width="1"><img src="/images/bar_off.gif" width="1" height="15" alt=""></td>
		                            <td widtx`h="84">
		                              <table width="84" border="0" cellspacing="0" cellpadding="0">
		                                <tr> 
		                                  <td align="center"><a href="/myList.do?gubun=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image60','','/images/info_09_over.gif',1)"><img name="Image60" border="0" src="/images/info_09.gif" alt="신청서"></a></td>
		                                </tr>
		                                <tr> 
		                                  <td align="center"><a href="/myList.do?gubun=2" class="info" title="신청서"><%=request.getAttribute("apprReq").toString()%></a></td>
		                                </tr>
		                              </table>
		                            </td>
		                            <td width="1"><img src="/images/bar_on.gif" width="1" height="15" alt=""></td>
	                            <% } else { %>
	                            	<td width="170"></td>
	                            <% } %>
	                            <td width="3"> 
	                            </td>
	                          </tr>
	                        </table>
	                        <!--전체수치표시 테이블 끝-->
	                      </td>
	                      <td width="40"></td>
	                    </tr>
	                  </table>
	              	</logic:equal>
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="871" border="0" cellspacing="0" cellpadding="0" align="center" <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("style='display:none'"); %>>
                    <tr> 
                      <td width="6"><img src="/images/poll_left.gif" width="6" height="38" alt=""></td>
                      <td background="/images/poll_bg.gif" width="859"> 
                        <!--설문조사 최근게시물 시작-->
                        <table width="850" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
                          <tr> 
                            <td width="185" align="center"><a href="/researchParticiList.do"><img src="/images/poll_title.gif" width="158" height="11" border="0" alt="현재 진행중인 설문조사"></a></td>
												<logic:empty name="mainForm" property="rchList">
												  	<td width="632" align="center" valign="middle"><font color="#D9E6FF">진행중인 설문이 없습니다.</font></td>                
												</logic:empty>
                 				<logic:notEmpty name="mainForm" property="rchList">
													<logic:iterate id="list" name="mainForm" property="rchList" length="1">
                            <td width="90" style="padding:3 0 0 0;"><font color="#FFBCCD"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></font></td>
                            <td width="460" style="padding:3 0 0 0;"><span style="width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
                            	<logic:equal name="list" property="groupyn" value="N">
											      		<a href="#" onclick="showPopup2('/research.do?rchno=<bean:write name="list" property="rchno"/>',700,750,'','');"><font color="#D9E6FF"><bean:write name="list" property="title"/></font></a>
											      	</logic:equal>
											      	<logic:equal name="list" property="groupyn" value="Y">
											      		<a href="#" onclick="showPopup2('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="rchgrpno"/>&mode=research', 700, 680, 0, 0);"><font color="#D9E6FF"><bean:write name="list" property="title"/></font></a>
											      	</logic:equal>
											      	</span>
                            </td>
                            <td width="82">
                            	<logic:equal name="list" property="groupyn" value="N">
											      		<a href="#" onclick="showPopup2('/research.do?rchno=<bean:write name="list" property="rchno"/>',700,750,0,0);" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','/images/poll_btn_on.gif',1)"><img name="Image8" border="0" src="/images/poll_btn.gif" width="70" height="23" alt="설문조사 참여하기"></a>
											      	</logic:equal>
											      	<logic:equal name="list" property="groupyn" value="Y">
											      		<a href="#" onclick="showPopup2('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="rchgrpno"/>&mode=research', 700, 680, 0, 0);" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','/images/poll_btn_on.gif',1)"><img name="Image8" border="0" src="/images/poll_btn.gif" width="70" height="23" alt="설문조사 참여하기"></a>
											      	</logic:equal>
                            </td>
												 </logic:iterate>
												</logic:notEmpty>
                            <td width="33"><a href="/researchParticiList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','/images/more_on.gif',1)"><img name="Image9" border="0" src="/images/more_off.gif" width="32" height="7" alt="설문조사 더보기"></a></td>
                          </tr>
                        </table>
                        <!--설문조사 최근게시물 끝-->
                      </td>
                      <td width="6"><img src="/images/poll_right.gif" width="6" height="38" alt=""></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td> 
                  <table width="871" border="0" cellspacing="0" cellpadding="0" align="center" height="190">
                    <tr valign="top" height="190"> 
                      <td width="288"> 
                        <!--공지사항 최근게시물 시작-->
                        <table width="265" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="220" height="40" style="padding:0 0 0 5;"><a href="/noticeList.do"><img src="/images/notice_title.gif" width="62" height="11" border="0" alt="공지사항"></a></td>
                            <td width="45" height="40"><a href="/noticeList.do"><img src="/images/more_01.gif" width="32" height="7" border="0" alt="공지사항 더보기"></a></td>
                          </tr>
                          <tr bgcolor="#B7D1D4"> 
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr valign="top"> 
                            <td colspan="2" style="padding:3 0 3 0;"> 
                              <table width="265" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
                 						<logic:empty name="mainForm" property="noticeList">
																<tr>
																	<td height="130" align="center" valign="middle">공지사항이 없습니다.</td>
																</tr>				                        
														</logic:empty>
														<logic:notEmpty name="mainForm" property="noticeList">
															<logic:iterate id="list" name="mainForm" property="noticeList">
																<tr> 
                                  <td width="15" align="center"><img src="/images/notice_dot.gif" width="2" height="2" alt=""></td>
                                  <td width="180" style="padding:3 0 0 0;">
                                  	<span style="width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
                                  		<a href="/noticeView.do?seq=<bean:write name="list" property="seq"/>" class="notice" ><bean:write name="list" property="title"/></a>
                                  		<logic:notEqual name="list" property="filenm" value=""><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><img src="/images/collect/icon_folder.gif" align="absmiddle" alt="<bean:write name="list" property="originfilenm"/>"></a>&nbsp;</logic:notEqual>
									                		<logic:equal name="list" property="newfl" value="1"><img src="/images/new.gif" align="absmiddle" alt="새로 등록된 글"></logic:equal>
                                  	</span>
                                  </td>
                                  <td width="70" class="date"><bean:write name="list" property="crtdt"/></td>
                                </tr>
															</logic:iterate>
														</logic:notEmpty>
                              </table>
                            </td>
                          </tr>
                        </table>
                        <!--공지사항 최근게시물 끝-->
                      </td>
                      <td width="295"> 
                        <table width="295" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="2" rowspan="2"><img src="/images/data_r_01.gif" width="6" height="6" alt=""></td>
                            <td bgcolor="#D0D0F3" height="4" width="283"></td>
                            <td colspan="2" rowspan="2"><img src="/images/data_r_02.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td height="2" width="283"></td>
                          </tr>
                          <tr> 
                            <td bgcolor="#D0D0F3" width="4"></td>
                            <td width="2"></td>
                            <td width="283" height="178" valign="top"> 
                              <!--최근취합된자료 최근게시물 시작-->
                              <table width="265" border="0" cellspacing="0" cellpadding="0" align="center">
                                <tr> 
                                  <td width="220" height="34"><a href="/exhibitList.do"><img src="/images/data_title.gif" width="105" height="11" border="0" alt="최근 취합된 자료"></a></td>
                                  <td width="45" height="34"><a href="/exhibitList.do"><img src="/images/more_02.gif" width="32" height="7" border="0" alt="최근 취합된 자료 더보기"></a></td>
                                </tr>
                                <tr bgcolor="#E0D0F3"> 
                                  <td colspan="2" height="1"></td>
                                </tr>
                                <tr> 
                                  <td colspan="2" style="padding:3 0 3 0;" valign="top"> 
                                    <table width="265" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
                                  <logic:empty name="mainForm" property="recentList">
																			<tr>
	    																	<td height="130" align="center" valign="middle">최근 취합자료가 없습니다.</td>
																		  </tr>			                          
																	</logic:empty>
																	<logic:notEmpty name="mainForm" property="recentList">
																		<logic:iterate id="list" name="mainForm" property="recentList">
																			<tr> 
			                                  <td width="15" align="center"><img src="/images/notice_dot.gif" width="2" height="2" alt=""></td>
			                                  <td width="180" style="padding:3 0 0 0;">
			                                  	<span style="width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
			                                  		<logic:equal name="list" property="gubun" value="1">
																							<a class="list_s2" href="javascript:parent.viewForm('<bean:write name='list' property='formkind'/>', '<bean:write name='list' property='sysdocno'/>', '<bean:write name='list' property='formseq'/>')">[취합]<bean:write name="list" property="doctitle"/></a>
																						</logic:equal>
																						<logic:equal name="list" property="gubun" value="2">
																							<a class="list_s2" href="javascript:showModalPopup('/researchResult.do?rchno=<bean:write name="list" property="sysdocno"/>&range=<bean:write name="list" property="formkind"/>', 700, 680, 0, -40)">[설문]<bean:write name="list" property="doctitle"/></a>
																						</logic:equal>
																						<logic:equal name="list" property="gubun" value="3">
																							<a class="list_s2" href="javascript:showPopup('/researchGrpPreview.do?rchgrpno=<bean:write name="list" property="sysdocno"/>&range=<bean:write name="list" property="formkind"/>&mode=result', 700, 680, 0, -40)">[설문]<bean:write name="list" property="doctitle"/></a>
																						</logic:equal>
		                                  		</span>
		                                  	</td>
			                                  <td width="70" class="date"><bean:write name="list" property="enddt_date"/></td>
			                                </tr>
																	 	</logic:iterate>
																	</logic:notEmpty>
                                    </table>
                                  </td>
                                </tr>
                              </table>
                              <!--최근취합된자료 최근게시물 끝-->
                            </td>
                            <td width="2"></td>
                            <td bgcolor="#D0D0F3" width="4"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" rowspan="2"><img src="/images/data_r_03.gif" width="6" height="6" alt=""></td>
                            <td height="2" width="283"></td>
                            <td colspan="2" rowspan="2"><img src="/images/data_r_04.gif" width="6" height="6" alt=""></td>
                          </tr>
                          <tr> 
                            <td bgcolor="#D0D0F3" height="4" width="283"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="288"> 
                        <!--신청하세요 최근게시물 시작-->
                        <table width="265" border="0" cellspacing="0" cellpadding="0" align="right">
                          <tr> 
                            <td width="93" height="40" align="center"><a <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "005".equals(session.getAttribute("user_gbn")) ) out.print("href='#'"); %> href="/doList.do"><img src="/images/file_title.gif" width="73" height="15" border="0" alt="신청하세요"></a></td>
                            <td width="72" height="40"><a <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "005".equals(session.getAttribute("user_gbn")) ) out.print("href='#'"); %> href="/doList.do"><img src="/images/more_03.gif" width="32" height="7" border="0" alt="신청하세요 더보기"></a></td>
                            <td width="100" height="40" valign="bottom"><a <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "005".equals(session.getAttribute("user_gbn")) ) out.print("href='#'"); %> href="/myList.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image21','','/images/myfile_on.gif',1)"><img name="Image21" border="0" src="/images/myfile_off.gif" width="99" height="32" alt="내신청서함보기"></a></td>
                          </tr>
                          <tr bgcolor="#CACACA"> 
                            <td colspan="3" height="1"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" style="padding: 3 0 3 0;" valign="top"> 
                              <table width="265" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed">
 														<logic:empty name="mainForm" property="sinchungList">
																<tr>
	 																	<td height="130" align="center" valign="middle">신청서 목록이 없습니다.</td>
															  </tr>			                          
														</logic:empty>
														<logic:notEmpty name="mainForm" property="sinchungList">
															<logic:iterate id="list" name="mainForm" property="sinchungList">
																<tr <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "005".equals(session.getAttribute("user_gbn")) ) out.print("style='display:none'"); %>> 
	                                 <td width="15" align="center"><img src="/images/notice_dot.gif" width="2" height="2" alt=""></td>
	                                 <td width="185" style="padding:3 0 0 0;"><span style="width:100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><a href="#" class="notice" onclick="showPopup('/reqForm.do?reqformno=<bean:write name="list" property="reqformno"/>',705,650,'','');"><bean:write name="list" property="title"/></a></span></td>
	                                 <td width="65" class="date"><bean:write name="list" property="strdt"/>~<bean:write name="list" property="enddt"/></td>
	                               </tr>
														 	</logic:iterate>
														</logic:notEmpty>
                              </table>
                            </td>
                          </tr>
                        </table>
                        <!--신청하세요 최근게시물 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td width="4"></td>
          <td bgcolor="#4F94E7" width="2"></td>
        </tr>
        <tr> 
          <td colspan="2" valign="top"><img src="/images/r_03.gif" width="6" height="19" alt=""></td>
          <td height="20" valign="bottom"><img src="/images/r_bg.gif" width="898" height="19" alt=""></td>
          <td colspan="2" valign="top"><img src="/images/r_04.gif" width="6" height="19" alt=""></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="930" border="0" cellspacing="0" cellpadding="0" style="padding-left:20;">
  <tr>
        <%if ( "영천5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {%>
		<td style='visibility:hidden' align="center" width="60"><img src="/images/mopas_logo_1.gif" alt="안전행정부 로고" onclick="window.open('/main.do','ejms','width=1015,height=740,resizable=no,scrollbars=yes')"></td>
		<td width="830">
			<font color="#888888" style="font-size:11;visibility:hidden">
				본 프로그램은 안전행정부에서 주관하는 2009년도 자치단체 우수정보시스템으로, 한국지역정보개발원의 위탁으로 (주)차세대정보기술에서 개발하였습니다.<br>
				대구광역시 달서구 본동 831 대구디지털산업진흥원 ICT Park 205호 (주)이튜 / Tel 053-527-5777,526-5777 / Fax 053-524-7786<br>
				COPYRIGHT 2009 BY MINISTRY OF PUBLIC ADMINISTRATION AND SECURITY. ALLRIGHTS RESERVED.
				<font color="white"><b>[IP:<bean:write name="loginip" scope="session"/>]</b></font>
				<% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("<img src='/images/won.gif' align='absmiddle' onclick='if(confirm(\"설문조사는 테스트 중이므로 사용하실 수 없습니다\"))location=\"/researchMyList.do\"'>"); %>
			</font>
		</td>
		<%} else if ( "달서".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {%>
		<td align="center" width="60"><img src="/images/mopas_logo_1.gif" alt="안전행정부 로고" onclick="window.open('/main.do','ejms','width=1015,height=740,resizable=no,scrollbars=yes')"></td>
		<td width="830">
			<font color="#888888" style="font-size:11">
				본 프로그램은 안전행정부에서 주관하는 2009년도 자치단체 우수정보시스템으로, 한국지역정보개발원의 위탁으로 (주)차세대정보기술에서 개발하였습니다.<br>
				대구광역시 달서구 구마로 146 (본동) 대구디지털산업진흥원 ICT Park 205호 (주)이튜 / Tel 053-527-5777,526-5777 / Fax 053-524-7786<br>
				COPYRIGHT 2009 BY MINISTRY OF PUBLIC ADMINISTRATION AND SECURITY. ALLRIGHTS RESERVED.
				<font color="white"><b>[IP:<bean:write name="loginip" scope="session"/>]</b></font>
				<% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("<img src='/images/won.gif' align='absmiddle' onclick='if(confirm(\"설문조사는 테스트 중이므로 사용하실 수 없습니다\"))location=\"/researchMyList.do\"'>"); %>
			</font>
		</td>
        <%} else if ( "원주4190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) { %>
        <td style='visibility:hidden' align="center" width="60"><img src="/images/mopas_logo_1.gif" alt="안전행정부 로고" onclick="window.open('/main.do','ejms','width=1015,height=740,resizable=no,scrollbars=yes')"></td>
        <td width="830">
            <font color="#888888" style="font-size:11;visibility:hidden">
                본 프로그램은 안전행정부에서 주관하는 2009년도 자치단체 우수정보시스템입니다.
                <font color="white"><b>[IP:<bean:write name="loginip" scope="session"/>]</b></font>
                <% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("<img src='/images/won.gif' align='absmiddle' onclick='if(confirm(\"설문조사는 테스트 중이므로 사용하실 수 없습니다\"))location=\"/researchMyList.do\"'>"); %>
            </font>
        </td>
		<%} else {%>
		<td align="center" width="60"><img src="/images/mopas_logo_1.gif" alt="안전행정부 로고" onclick="window.open('/main.do','ejms','width=1015,height=740,resizable=no,scrollbars=yes')"></td>
		<td width="830">
			<font color="#888888" style="font-size:11;">
				본 프로그램은 안전행정부에서 주관하는 2009년도 자치단체 우수정보시스템으로, 한국지역정보개발원의 위탁으로 (주)차세대정보기술에서 개발하였습니다.<br>
				대구광역시 달서구 본동 831 대구디지털산업진흥원 ICT Park 203,205호 (주)이튜 / Tel 053-527-5777,526-5777 / Fax 053-524-7786<br>
				COPYRIGHT 2009 BY MINISTRY OF PUBLIC ADMINISTRATION AND SECURITY. ALLRIGHTS RESERVED.
				<font color="white"><b>[IP:<bean:write name="loginip" scope="session"/>]</b></font>
				<% if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) out.print("<img src='/images/won.gif' align='absmiddle' onclick='if(confirm(\"설문조사는 테스트 중이므로 사용하실 수 없습니다\"))location=\"/researchMyList.do\"'>"); %>
			</font>
		</td><%} %>
<%--
		<!-- 거제시청 시작 -->
		<td align="center" width="160"><img src="/client/images/logo_foot.gif" alt=""></td>
		<td width="750">
			<font color="#888888" style="font-size:11;">
				COPYRIGHT 2009 EJMS. ALLRIGHTS RESERVED.
			</font>
		</td>
		<!-- 거제시청 끝 -->
--%>
  </tr>
</table>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>