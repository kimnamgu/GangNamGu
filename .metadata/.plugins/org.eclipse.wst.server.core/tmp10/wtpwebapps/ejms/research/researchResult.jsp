<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 결과보기
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
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>설문조사 결과보기</title>
<base target="_self">
<!-- <link rel="stylesheet" href="/css/style_gangnam.css" type="text/css"> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"> -->
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/research.js"></script>
<script type="text/javascript">
	function cancel(){
		self.close();
	}
</script>
</head>

<!-- <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="fullSizeWindow()"> -->
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="window.resizeTo(710, screen.availHeight * 0.8)">
<html:form action="/researchResult.do" method="POST">
<html:hidden name="researchForm" property="rchno"/>
<html:hidden name="researchForm" property="range"/>
<bean:define id="rchno" name="researchForm" property="rchno"/>
<bean:define id="totcnt" name="researchForm" property="anscount"/>
<div class="wrap" style="width:690px;height:719px; overflow-y: scroll;">
		<div class="container">
			<div class="survey-wrap">
				<h2>설문조사 결과보기</h2>
				<div class="survey-info">
					<table class="table" style="font-size:13px;">
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
									<strong class="text-blue"><bean:write name="researchForm" property="strymd"/>&nbsp;부터&nbsp;&nbsp;&nbsp;<bean:write name="researchForm" property="endymd"/>&nbsp;까지</strong>
								</td>
							</tr>
							<tr>
								<th scope="row">참여대상</th>
								<td>
									<% if (nexti.ejms.common.appInfo.isOutside()){ %>
		                            		<logic:equal name="researchForm" property="range" value="1">
								        		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%>
								        	</logic:equal>
								         	<logic:equal name="researchForm" property="range" value="2">
								         		<bean:define id="rangedetail" name="researchForm" property="rangedetail"/>
									      		<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", "2")%>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)
								        	</logic:equal>&nbsp;&nbsp;
								        <% } %>
													        <span class="bar">l</span>
													<strong class="text-red">총 참여자수 : <bean:write name="researchForm" property="anscount"/>명</strong>
								</td>
							</tr>
							</logic:notEqual>
						</tbody>
					</table>
				</div>
				<div class="survey-write-top-area">
					<div class="result-doc-down">
						<ul>
							<logic:equal name="isOwner" value="true" scope="request">
							<li><a href="javascript:resultDown('/researchIndividualToHwp.do')" title="다운로드" style="margin-right:20px;">개인별 결과 내려받기 (HWP)</a></li>
							</logic:equal>
							
							<logic:equal name="isOwner" value="true" scope="request">
							<li><a href="javascript:resultDown('/researchIndividualToXls.do')" title="다운로드">개인별 결과 내려받기 (XLS)</a></li>
							</logic:equal>
							<li><a href="javascript:resultDown('/researchResultToHwp.do')"  title="다운로드">전체 결과 내려받기 (HWP)</a></li>
							<li><a href="javascript:resultDown('/researchResultToXls.do')" title="다운로드">전체 결과 내려받기 (XLS)</a></li>
							
						</ul>
					</div>
				</div>	
				<logic:equal name="researchForm" property="range" value="1">
				<div style="margin-left: 15px;">
				<ul>
							부서별 
							<% if (session.getAttribute("user_gbn").equals("001") && nexti.ejms.common.appInfo.isSidoldap() ){ %>
								<html:select name="researchForm" property="sch_orggbn" style="width:90px" onchange="javascript:document.researchForm.submit();">
											<jsp:useBean id="sch_orggbn" class="nexti.ejms.list.form.UsrGbnListForm">
												<bean:define id="user_gbn" name="user_gbn" scope="session"/>
												<jsp:setProperty name="sch_orggbn" property="ccd_cd" value="023"/>
												<jsp:setProperty name="sch_orggbn" property="user_gbn" value="<%=(String)user_gbn%>"/>
												<jsp:setProperty name="sch_orggbn" property="all_fl" value="ALL"/>
											</jsp:useBean>
											<html:optionsCollection name="sch_orggbn" property="beanCollection"/>
										</html:select>&nbsp;
							<% } else { %>
								<html:hidden property="sch_orggbn"/>
							<% } %>
							<html:select name='researchForm' property="sch_deptcd" style="width:100" onchange="javascript:document.researchForm.submit();">
								<jsp:useBean id="deptselect" scope="page" class='nexti.ejms.list.form.RchDeptListForm'>
									<bean:define id="sch_orggbn" name="sch_orggbn" scope="request"/>
									<jsp:setProperty name='deptselect' property='rchno'  		 value="<%=Integer.parseInt(rchno.toString())%>"/>
									<jsp:setProperty name='deptselect' property='sch_orggbn' value="<%=(String)sch_orggbn%>"/>
									<jsp:setProperty name='deptselect' property='all_fl' value='ALL'/>
								</jsp:useBean>
								<html:optionsCollection name='deptselect' property='beanCollection'/>
							</html:select>&nbsp;
							성별 
							<html:select name="researchForm" property="sch_sex"  style="width:70" onchange="javascript:document.researchForm.submit();">
								<option value="%" <logic:equal name="researchForm" property="sch_sex" value="%">selected</logic:equal>>전체</option>
								<option value="M" <logic:equal name="researchForm" property="sch_sex" value="M">selected</logic:equal>>남자</option>
								<option value="F" <logic:equal name="researchForm" property="sch_sex" value="F">selected</logic:equal>>여자</option>
							</html:select>
							연령 
							<html:select name='researchForm' property="sch_age" style="width:70" onchange="javascript:document.researchForm.submit();">
								<option value="" <logic:equal name="researchForm" property="sch_age" value="">selected</logic:equal>>전체</option>
								<option value="10" <logic:equal name="researchForm" property="sch_age" value="10">selected</logic:equal>>10대</option>
								<option value="20" <logic:equal name="researchForm" property="sch_age" value="20">selected</logic:equal>>20대</option>	
								<option value="30" <logic:equal name="researchForm" property="sch_age" value="30">selected</logic:equal>>30대</option>	
								<option value="40" <logic:equal name="researchForm" property="sch_age" value="40">selected</logic:equal>>40대</option>
								<option value="50" <logic:equal name="researchForm" property="sch_age" value="50">selected</logic:equal>>50대</option>	
								<option value="60" <logic:equal name="researchForm" property="sch_age" value="60">selected</logic:equal>>60대</option>		 
								<option value="70" <logic:equal name="researchForm" property="sch_age" value="70">selected</logic:equal>>70대</option>	 
								<option value="80" <logic:equal name="researchForm" property="sch_age" value="80">selected</logic:equal>>80대</option>
								<option value="90" <logic:equal name="researchForm" property="sch_age" value="90">selected</logic:equal>>90대</option>		 
								<option value="100" <logic:equal name="researchForm" property="sch_age" value="100">selected</logic:equal>>100대</option>	 
								<option value="110" <logic:equal name="researchForm" property="sch_age" value="110">selected</logic:equal>>100대이상</option>	            	          				
							</html:select>
				</ul>
				</div>
							</logic:equal>
				<div class="txt-area">
					<logic:notEmpty name="researchForm" property="headcont">
		        	<bean:define id="headcont" name="researchForm" property="headcont"/>
		    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(headcont.toString())%>
						</logic:notEmpty>
				</div>
				<div class="survey-list">
					<ol>
						<logic:notEmpty name="researchForm" property="listrch">
						<%int selectSeq = 0;%>
			        	<logic:iterate id="list" name="researchForm" property="listrch" indexId="idx">
					     		<bean:define id="formtype" name="list" property="formtype"/>
					     		<bean:define id="listseq" name="list" property="formseq"/>
					     		<html:hidden name="list" property="formseq"/>
					     
							
						<li style="list-style-type:none !important;">
						<div class="survey-list-title">
							<logic:notEmpty name="list" property="formgrp">
			        	<bean:define id="formgrp" name="list" property="formgrp"/>
			    			  &nbsp;<img src="/images/common/dot_05.gif" align="absmiddle" alt=""> <span style="font-size:14px;font-weight:bold;color:#blue"><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formgrp.toString())%></sapn><br>
							</logic:notEmpty>
						<logic:equal name="researchForm" property="imgpreview" value="1">
		                      		<% nexti.ejms.research.model.ResearchSubBean rchSubBean = (nexti.ejms.research.model.ResearchSubBean)list; %>
		                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getFilenm()); %>
		                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getExt()).toLowerCase(); %>
		                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
		                      			<center><br>[질문그림-<%=idx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
															<% } %>
						</logic:equal>
			                				<logic:notEmpty name="list" property="formtitle">
											        	<bean:define id="formtitle" name="list" property="formtitle"/>
											        	<bean:define id="formseq" name="list" property="formseq"/>
																<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formseq.toString())%>. <%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formtitle.toString())%>
															</logic:notEmpty>
			                      	<logic:notEmpty name="list" property="originfilenm">
				                      	<br>(첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a>
																<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%>, 'imageView<%=idx.intValue()%>', '<%=basePath%><bean:write name="list" property="filenm"/>', 50, 50)"><u>미리보기</u></a>)
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
															</logic:notEmpty>
														</div>
							<!-- <div class="survey-list-title">
								강남구에서는  생애주기별  구민  재난안전  교육  실시의  일환으로  관내  어린이집·유치원  대상의  「생활안전교육  및  안전  연극공연」  실시,  초등학생을  대상으로  하는「학교로  찾아가는  전교생  안전체험  캠프」운영  등  자라나는  어린이  등을  주요  대상으로  다양한  재난예방교육을  실시하고  있습니다.  귀하께서는  이러한  생애주기별  어린이  대상  재난안전교육에  대하여  어느  정도  알고  계십니까?
							</div> -->
							<div class="survey-list-result">
								<div class="progress-result">
									<logic:lessEqual name="list" property="formtype" value="02">
									<!-- <img src="/images/common/arrow.gif" align="absmiddle" alt="">&nbsp;답변유형별&nbsp; -->
										<bean:define id="exam1" name="list" property="sch_exam"/>
										<html:select alt="<%=Integer.toString(selectSeq++, 10)%>" name="researchForm" property="sch_exam" onchange="javascript:document.researchForm.submit();">
											<option value="%" <% if ("%".equals(exam1)){ %>selected<% } %>>전체</option>
											<logic:notEmpty name="list" property="examList">
				       					<logic:iterate id="elist" name="list" property="examList" indexId="eidx">
					       					<bean:define id="examseq1" name="elist" property="examseq"/>
					       					<logic:notEmpty name="list" property="sch_exam">
														<option value="<%=listseq %><bean:write name="elist" property="examseq"/>" <% if (exam1.equals(listseq+examseq1.toString())){ %>selected<% } %> ><bean:write name="elist" property="examcont"/></option>						
													</logic:notEmpty>
					       					<logic:empty name="list" property="sch_exam">
														<option value="<%=listseq %><bean:write name="elist" property="examseq"/>" ><bean:write name="elist" property="examcont"/></option>						
													</logic:empty>								
												</logic:iterate>
												<logic:equal name="list" property="examtype" value="Y">
													<option value="<%=listseq %>0" <% if (exam1.equals(listseq+"0")){ %>selected<% } %>>기타</option>
												</logic:equal>
											</logic:notEmpty>
			            				</html:select>
									<div class="progress-area">
										<ul>
										 <bean:size id="esize" name="list" property="examList"/>
										  <logic:notEmpty name="list" property="examList">
										  	<logic:iterate id="elist" name="list" property="examList" indexId="eidx">          	
											          		<logic:notEmpty name="elist" property="examcont">
											          			<bean:define id="anscnt" name="elist" property="examcnt"/>
											<li>
											<logic:equal name="researchForm" property="imgpreview" value="1">
							                      		<% nexti.ejms.research.model.ResearchExamBean rchExamBean = (nexti.ejms.research.model.ResearchExamBean)elist; %>
							                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getFilenm()); %>
							                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getExt()).toLowerCase(); %>
							                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
							                      			<center><br>[보기그림-<%=idx.intValue()+1%>.<%=eidx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
																				<% } %>
											</logic:equal>
											<logic:notEmpty name="elist" property="examcont">
										        	<bean:define id="examcont" name="elist" property="examcont"/>
															<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
											</logic:notEmpty>
											<logic:notEmpty name="elist" property="originfilenm">
					                            	<br>(첨부파일 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
														<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u>미리보기</u></a>)
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
											<% 
							              		int rate = 0;
							              		double drate = 0;
							              		if(Integer.parseInt(totcnt.toString()) > 0){
							              			drate  = (Double.parseDouble(anscnt.toString())/Double.parseDouble(totcnt.toString()))*100;
							              			rate = (int)Math.round(drate);
							              		}
							              		%>
												 <strong class="text-mint"><%=rate%>% (<bean:write name="elist" property="examcnt"/>명)</strong>
												<div class="progress">
												  <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <%=rate%>%;"></div>
												</div>
											</li>
											</logic:notEmpty>
											</logic:iterate>
											</logic:notEmpty>
										<logic:equal name="list" property="examtype" value="Y">
																	<bean:size id="osize" name="list" property="otherList"/>
											<li>
											<%=esize.intValue()+1%>) 기타
											<% 
										              		int rate = 0;
										              		double drate = 0;
										              		if(Integer.parseInt(totcnt.toString()) > 0){
										              			drate  = (Double.parseDouble(osize.toString())/Double.parseDouble(totcnt.toString()))*100;
										              			rate = (int)Math.round(drate);
										              		}
										              		%>
										       <strong class="text-mint"><%=rate%>% (<%=osize.intValue() %> 명)</strong>        		
											<ul>
											  <logic:notEmpty name="list" property="otherList">
													<logic:iterate id="olist" name="list" property="otherList" indexId="oidx">
														<logic:notEmpty name="elist" property="examcont">
																<li >&nbsp;&nbsp;&nbsp;&nbsp;- <bean:write name="olist" property="other"/></li>
														</logic:notEmpty>
													</logic:iterate>
												</logic:notEmpty>
											</ul>
									</li>		
										</logic:equal>
											</ul>
											</div>
									</logic:lessEqual>
								<logic:greaterEqual name="list" property="formtype" value="03">						 
						     		<logic:notEmpty name="list" property="examList">
						     		<div class="txt-result"><ul><li><ul>
							     		<logic:iterate id="elist" name="list" property="examList">
								     		<logic:notEmpty name="elist" property="anscont">
								     			<li>
									     			<bean:define id="cont" name="elist" property="anscont" /><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(cont.toString())%>
									     		</li>
								        	</logic:notEmpty>
							       		</logic:iterate>
							       		</ul></li></ul></div>
									</logic:notEmpty> 
								</logic:greaterEqual>
								</div>
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
					<a href="javascript:cancel()" class="btn-close">닫기</a>
				</div>
			</div>
		</div>
	</div>
</html:form>
<script>
function doRun() {
	return confirm("참여자수가 많은 경우 시간이 오래걸릴 수 있습니다\n계속하시겠습니까?")
}

var _submit = document.researchForm.submit;
function resultDown(url) {
	if ( !doRun()) return;

	var target = "actionFrame";
	var oldSubmit = document.researchForm.submit;
	var oldAction = document.researchForm.action;
	var oldTarget = document.researchForm.target;
	
	document.researchForm.action = url;
	document.researchForm.target = target;
	document.researchForm.submit = _submit;
	document.researchForm.submit();
	
	document.researchForm.submit = oldSubmit;
	document.researchForm.action = oldAction;
	document.researchForm.target = oldTarget;
}
function pageScroll() {	
	document.onreadystatechange = function() {
		if(document.readyState == "complete") {
			document.body.scrollTop = parseInt("0<%=(String)request.getParameter("pageScrollTop")%>", 10);
			var diff = 0;
			if("<%=(String)request.getParameter("selectIndex")%>".trim() != "null"
					&& "<%=(String)request.getParameter("selectIndex")%>".trim() != "undefined") {
				diff = parseInt("0<%=(String)request.getParameter("selectSite")%>", 10)
									- getAbsoluteTop(document.forms[0].sch_exam[parseInt("0<%=(String)request.getParameter("selectIndex")%>", 10)]);
			}			
			document.body.scrollTop = parseInt("0<%=(String)request.getParameter("pageScrollTop")%>", 10) - diff;
		}
	}
	
	document.forms[0].submit = function() {
		document.forms[0].action = "/researchResult.do" +
															 "?selectIndex=" + event.srcElement.alt +
															 "&selectSite=" + getAbsoluteTop(event.srcElement) +
															 "&pageScrollTop=" + document.body.scrollTop;
		<logic:equal name="grp" value="y">
			document.forms[0].action += "&grp=y";
		</logic:equal>
		_submit();
	}
}
pageScroll();
</script>
<jsp:include page="/include/tail.jsp" flush="true"/>
