<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 미리보기
 * 설명: 
 *  수정일 			수정내용
 * ------------------------------------------
 *  2017.05.19	화면 디자인 수정
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
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>설문조사 미리보기</title>
<base target="_self">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- <link rel="stylesheet" href="/css/style_gangnam.css" type="text/css"> -->
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/research.js"></script>
<script type="text/javascript">
function cancel(){
	self.close();
}

function choice(rchno){
	var opener = window.dialogArguments;
	self.close();  
	opener.location.href = "/researchChoice.do?mode=choice&rchno=" + rchno; 
}
</script>
</head>

<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="window.resizeTo(710, screen.availHeight * 0.8)" style="overflow:hidden;">
<form id=frm name=frm method=post>
<input type="hidden" name="rchno" value="<bean:write name="researchForm" property="rchno"/>">
<bean:size id="listsize" name="researchForm" property="listrch"/>
<input type="hidden" name="listcnt" value="<%=listsize %>">
<div class="wrap" style="width:690px;height:746px; overflow-y: scroll;">
		<div class="container">
			<div class="survey-wrap">
				<h2>설문조사</h2>
				<div class="survey-info">
					<table class="table">
						<caption>설문조사 안내 표 : 설문제목,설문개요,설문담당자,설문기간,참여대상으로 구성</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="2" class="text-center survey-title">
									<bean:write name="researchForm" property="title"/>
								</th>
							</tr>
							<tr>
								<th scope="row">설문개요</th>
								<td>
									<logic:notEmpty name="researchForm" property="summary">
												        	<bean:define id="summary" name="researchForm" property="summary"/>
												        	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(summary.toString())%>
												        </logic:notEmpty>
								</td>
							</tr>
							  <logic:present parameter="grp"><bean:parameter id="grp" name="grp"/></logic:present>
                    		<logic:notEqual name="grp" value="y">
							<tr>
								<th scope="row">설문담당자</th>
								<td>
									<span class="dept-info"><bean:write name="researchForm" property="coldeptnm" /></span>
									<strong><bean:write name="researchForm" property="chrgusrnm"/></strong>
									<span class="bar">l</span>
									문의전화 : <bean:write name="researchForm" property="coldepttel"/>
								</td>
							</tr>
							<tr>
								<th scope="row">설문기간</th>
								<td>
									<strong class="text-blue"><bean:write name="researchForm" property="strymd"/> 부터 <bean:write name="researchForm" property="endymd"/> 까지</strong>
								</td>
							</tr>
							</logic:notEqual>
						<%-- <tr>
								<th scope="row">참여대상</th>
								<td>
									<!-- 인터넷(비회원)<span class="bar">l</span><strong class="text-red">총 참여자수 : 226명</strong> -->
									<logic:notEmpty name="researchForm" property="headcont">
						        	<bean:define id="headcont" name="researchForm" property="headcont"/>
						    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>	
										</logic:notEmpty> 
								</td>
							</tr> --%>
						</tbody>
					</table>
				</div>
				 <logic:notEmpty name="researchForm" property="headcont">
				<div class="survey-write-top-area">
					<div class="write-info">
						<bean:define id="headcont" name="researchForm" property="headcont"/>
						    <%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>	
					</div>
				</div>	
				</logic:notEmpty>
				<div class="survey-list">
					<ol>
				<logic:notEmpty name="researchForm" property="listrch">
	        	<logic:iterate id="list" name="researchForm" property="listrch" indexId="idx">
	        			<input type="hidden" name="formseq<%=idx.intValue() %>" value="<bean:write name="list" property="formseq"/>">
			     		<input type="hidden" name="formtype<%=idx.intValue() %>" value="<bean:write name="list" property="formtype"/>">
			     		<input type="hidden" name="examtype<%=idx.intValue() %>" value="<bean:write name="list" property="examtype"/>">
						<li>
						<logic:equal name="researchForm" property="imgpreview" value="1">
		                      		<% nexti.ejms.research.model.ResearchSubBean rchSubBean = (nexti.ejms.research.model.ResearchSubBean)list; %>
		                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getFilenm()); %>
		                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getExt()).toLowerCase(); %>
		                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
		                      			<center><br>[질문그림-<%=idx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
															<% } %>
								</logic:equal>
							<div class="survey-list-title" style="margin-top:0px; padding-top:25px;">
								<logic:notEmpty name="list" property="formgrp">
									<logic:notEmpty name="list" property="formgrp">
							        	<bean:define id="formgrp" name="list" property="formgrp"/>
						    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formgrp.toString())%>	
									</logic:notEmpty>
								</logic:notEmpty>
								
								<logic:notEmpty name="list" property="formtitle">
								<p class="detail-txt">
								        	<bean:define id="formtitle" name="list" property="formtitle"/>
											<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formtitle.toString())%>
								</p>			
								</logic:notEmpty>
								
									<logic:notEmpty name="list" property="originfilenm">
								<p class="detail-txt">
						                      	(첨부 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a>
																		<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%>, 'imageView<%=idx.intValue()%>', '<%=basePath%><bean:write name="list" property="filenm"/>', 50, 50)"><u></u></a>)
																		<div id="imagePreview<%=idx.intValue()%>" style="position:absolute;display:none;z-index:999">
																			<table id="imagePreview<%=idx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																				<tr align="center" valign="middle">
																					<td><iframe id="imagePreview<%=idx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																				</tr>
																			</table>
																			<table id="imagePreview<%=idx.intValue()%>_t2" border="1" bordercolor="gray" style="position:absolute;">
																				<tr align="center" valign="middle">
																					<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%>)"><img id="imageView<%=idx.intValue()%>" alt="클릭하시면 닫힘니다"></a></td>
																				</tr>
																			</table>
																		</div>
								</p>
														</logic:notEmpty>
							</div>
							<div class="survey-list-form">
								<ul>
									 <bean:size id="elistsize" name="list" property="examList"/>
									 <input type="hidden" name="elistcnt<%=idx.intValue() %>" value="<%=elistsize %>">	
									 <logic:iterate id="elist" name="list" property="examList" indexId="eidx">
										<bean:define id="eseq" name="elist" property="examseq" />
									   		<logic:equal name="list" property="formtype" value="01">
										   		<li>
									   				<logic:equal name="researchForm" property="imgpreview" value="1">
							                      		<% nexti.ejms.research.model.ResearchExamBean rchExamBean = (nexti.ejms.research.model.ResearchExamBean)elist; %>
							                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getFilenm()); %>
							                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getExt()).toLowerCase(); %>
							                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
							                      			<center><br>[보기그림-<%=idx.intValue()+1%>.<%=eidx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
																				<% } %>
													</logic:equal>
										   			<input type="radio" name="anscont<%=idx.intValue() %>" style="border:0;background-color:transparent;" value="<%=eseq.toString() %>" >
													<label for=""><logic:notEmpty name="elist" property="examcont">
																        	<bean:define id="examcont" name="elist" property="examcont"/>
																			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
																		</logic:notEmpty>
																		<logic:notEmpty name="elist" property="originfilenm">
											                            	<br>(첨부 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
																										<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u></u></a>)
																										<div id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>" style="position:absolute;display:none;z-index:999">
																											<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																												<tr align="center" valign="middle">
																													<td><iframe id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																												</tr>
																											</table>
																											<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t2" border="1" bordercolor="gray" style="position:absolute;">
																												<tr align="center" valign="middle">
																													<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>)"><img id="imageView<%=idx.intValue()%><%=eidx.intValue()%>" alt="클릭하시면 닫힘니다"></a></td>
																												</tr>
																											</table>
																										</div>
																	 </logic:notEmpty>
													</label>
										   		</li>
									   		</logic:equal>
									   	 	<logic:equal name="list" property="formtype" value="02">
									   	 		<li>
										   	 		<logic:equal name="researchForm" property="imgpreview" value="1">
								                      		<% nexti.ejms.research.model.ResearchExamBean rchExamBean = (nexti.ejms.research.model.ResearchExamBean)elist; %>
								                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getFilenm()); %>
								                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getExt()).toLowerCase(); %>
								                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
								                      			<center><br>[보기그림-<%=idx.intValue()+1%>.<%=eidx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
																					<% } %>
													</logic:equal>
													<input type="checkbox" name="anscont<%=idx.intValue() %><%=eidx.intValue() %>" style="border:0;background-color:transparent;" value="<%=eseq.toString()%>">
													<label for="" style="padding-left:0px;">
														<logic:notEmpty name="elist" property="examcont">
												        	<bean:define id="examcont" name="elist" property="examcont"/>
																	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
														</logic:notEmpty>
														<logic:notEmpty name="elist" property="originfilenm">
							                            	<br>(첨부 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
																						<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u></u></a>)
																						<div id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>" style="position:absolute;display:none;z-index:999">
																							<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																								<tr align="center" valign="middle">
																									<td><iframe id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																								</tr>
																							</table>
																							<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t2" border="1" bordercolor="gray" style="position:absolute;">
																								<tr align="center" valign="middle">
																									<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>)"><img id="imageView<%=idx.intValue()%><%=eidx.intValue()%>" alt="클릭하시면 닫힘니다"></a></td>
																								</tr>
																							</table>
																						</div>
														</logic:notEmpty>
													</label>
										   		</li>
									   	 	</logic:equal>
									 </logic:iterate>
									 <logic:equal name="list" property="formtype" value="01">				
						        	<logic:equal name="list" property="examtype" value="Y">
						        		<li>
						        		<input type="radio" name="anscont<%=idx.intValue() %>" style="border:0;background-color:transparent;margin-top:6px;" value="">
						        		<label for="" style="width:100%">기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:93%;" maxlength="2000"></label>
										</li>
						        	</logic:equal>
						        	</logic:equal>
						        	<logic:equal name="list" property="formtype" value="02">		 
									<logic:equal name="list" property="examtype" value="Y">
										<li>
											<input type="checkbox" name="anscont<%=idx.intValue() %><%=elistsize.intValue()%>" style="border:0;background-color:transparent;margin-top:6px;" value="">
											<label for="" style="width:100%;margin-top:-25px;margin-left:16px;padding-left:0px;">기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:93%;" maxlength="2000"></label>
										</li>
									</logic:equal>
									</logic:equal>
									<logic:equal name="list" property="formtype" value="03">
									<logic:equal name="list" property="security" value="Y">
										<li>
											<input type="password" name="anscont<%=idx.intValue() %>1" style="width:100%;">
										</li>
									</logic:equal>
										
									<logic:notEqual name="list" property="security" value="Y">
										<li>
											<input type="text" name="anscont<%=idx.intValue() %>1" style="width:100%;" maxlength="2000">
										</li>
									</logic:notEqual>

									</logic:equal>
									<logic:equal name="list" property="formtype" value="04">
										<li>	
											<textarea name="anscont<%=idx.intValue() %>1" rows="5" style="width:100%;"></textarea>
										</li>
									</logic:equal>
								</ul>
							</div>
						</li>
						</logic:iterate>
	      </logic:notEmpty>
						
					</ol>
				</div>
				<div class="survey-sp-txt">
					<logic:notEmpty name="researchForm" property="tailcont">
							<bean:define id="tailcont" name="researchForm" property="tailcont"/>
							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(tailcont.toString())%> 
						</logic:notEmpty>	
				</div>
				<BR>
				<div class="text-center mb20">
					<logic:equal name="researchForm" property="mode" value="getused">
							<a href="javascript:choice('<bean:write name="researchForm" property="rchno"/>')" class="btn-join">참여하기</a>
							<a href="javascript:cancel()" class="btn-join">닫기</a>
					  </logic:equal>
					  <logic:notEqual name="researchForm" property="mode" value="getused">
					  	<a href="javascript:cancel()" class="btn-join">닫기</a>
					  </logic:notEqual>
				</div>				
			</div>
		</div>
	</div>
</form>
<jsp:include page="/include/tail.jsp" flush="true"/>
