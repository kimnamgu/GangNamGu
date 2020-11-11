<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 답변
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
<title>설문조사</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- <link rel="stylesheet" href="/css/style_gangnam.css" type="text/css"> -->
<link rel="stylesheet" href="/css/survey/bootstrap.min.css">
<link rel="stylesheet" href="/css/survey/style.css">
<link rel="stylesheet" href="/css/survey/font-awesome.min.css">
<script src="/script/common.js"></script>
<script src="/script/research.js"></script>
<script type="text/javascript">

	function cancel(){
		self.close();
	}
	
	function save(frm){
	
		var listcnt = frm.listcnt.value;	
		var idx_cnt = 0;
		for(var i=0; i<listcnt; i++){
			var formtype = eval("document.forms[0].formtype"+i+".value");
			var examtype = eval("document.forms[0].examtype"+i+".value");
			var elistcnt = eval("document.forms[0].elistcnt"+i+".value");
			var require = eval("document.forms[0].require"+i+".value");
			var exsq = eval("document.forms[0].exsqs"+i+".value");
			var cnt=0;
			var cnt1=0;
			var exsq_yn = "N";
			var ex= document.getElementsByClassName(exsq);
			if(exsq != "ex_0_0"){
				if(ex[0].style.display == "none" && formtype == "01"){
					exsq_yn = "Y";
					var ansconts = eval("document.forms[0].anscont"+i);
					for(var j=0;j < ansconts.length ; j++){
						ansconts[j].checked = false;
					}
				}
			}
			if(ex[0].style.display == "none"){continue;}
			idx_cnt++; 
			if(formtype=="01" && require == "Y" && exsq_yn == "N"){	// & false 를 추가하면 필수답변 체크 안함
				for(var j=0; j<elistcnt; j++){	
					if(elistcnt == 1){
						var enc = false;
						if ( examtype == "Y" ) {
							enc = eval("document.forms[0].anscont"+i+"["+j+"].checked");
						} else {
							enc = eval("document.forms[0].anscont"+i+".checked");
						}
					}else{
						var enc = eval("document.forms[0].anscont"+i+"["+j+"].checked");
					}
					
					if(enc){
						cnt++;
					}
				}
				if(examtype=="Y"){
					if(elistcnt == 0){
						var enc = eval("document.forms[0].anscont"+i+".checked");
					}else{
						var enc = eval("document.forms[0].anscont"+i+"["+(j)+"].checked");
					}

					if(enc){
						cnt++;
					}
				}
			}else if(formtype=="02" && require == "Y"){	// & false 를 추가하면 필수답변 체크 안함
				for(var j=0; j<elistcnt; j++){
					var enc = eval("document.forms[0].anscont"+i+"_"+j+".checked");
					
					//alert(enc);
					if(enc){
						cnt++;
					}
				}
				if(examtype=="Y"){
					var enc = eval("document.forms[0].anscont"+i+"_"+(j)+".checked");
					if(enc){
						cnt++;
					}
				}
			}else if(formtype=="03" && require == "Y"){
				var enc = eval("document.forms[0].anscont"+i+"0304.value");
				if(enc){
					cnt++;
				}
			}else if(formtype=="04" && require == "Y"){
				var enc = eval("document.forms[0].anscont"+i+"0304.value");
				
				if(enc){
					cnt++;
				}
			}else{
				cnt++;
			}
			
			if( cnt==0 ){
				//alert((i+1)+"번째 선택 설문에 대한 답변이 없습니다.");
				alert(idx_cnt+"번째 선택 설문에 대한 답변이 없습니다.");
				return;
			}


			//답변 갯수 제한처리
            if(formtype=="02" && document.forms[0].rchno.value == "4671" ){    
				for(var j=0; j<elistcnt; j++){
                    var enc = eval("document.forms[0].anscont"+i+"_"+j+".checked");
                    if(enc){
						cnt1++;
                    }
                }

                if(cnt1 > 2 ){
   					alert('답변을 2개 이하로 선택하셔야 합니다!!');
   					return;
                }
                      
            }
		}
		
		frm.action = "/researchAns.do";		
		frm.submit();		
	}
	
	function check_other(seq, eseq){
		var formtype = eval("document.forms[0].formtype"+seq+".value");
		var examtype = eval("document.forms[0].examtype"+seq+".value");
		var elistcnt = eval("document.forms[0].elistcnt"+seq+".value");
		
		if(formtype == "01"){
			if(examtype=="Y"){			
				if(eseq == elistcnt){
					if(elistcnt == 0){
						eval("document.forms[0].anscont"+seq+".checked = true");
					}else{
						eval("document.forms[0].anscont"+seq+"["+eseq+"].checked = true");
					}
				}else{
					var other = eval("document.forms[0].other"+seq);
					other.value="";
				}
			}
		}else if(formtype == "02"){
			if(examtype=="Y"){
				if(eseq == elistcnt){
					eval("document.forms[0].anscont"+seq+"_"+eseq+".checked= true");
				}
			}
		}
	}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="window.resizeTo(710, screen.availHeight * 0.83);" style="overflow: no;">
<form id=frm name=frm method=post>
<input type="hidden" name="rchno" value="<bean:write name="researchForm" property="rchno"/>">
<bean:size id="listsize" name="researchForm" property="listrch"/>
<input type="hidden" name="listcnt" value="<%=listsize %>">
<div class="wrap" style="width:100%;height:736px; overflow: auto;">
		<div class="container" >
			<div class="survey-wrap" style="width:100%;">
				<h2>설문조사</h2>
				<div class="survey-info">
					<table class="table" style="font-size:13px;">
						<!-- <caption style="font-size:0px;">설문조사 안내 표 : 설문제목,설문개요,설문담당자,설문기간,참여대상으로 구성</caption> -->
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
				<logic:equal name="researchForm" property="rchno" value="4752">
				<p align="center">
				<img src="http://98.23.70.20:9400/upload/researchSample/2019/1566449773961445.jpg" width="500" height="371">
				</p>
				</logic:equal>
				<div class="survey-list">
					<ol class="ol_style">
				<logic:notEmpty name="researchForm" property="listrch">
	        	<logic:iterate id="list" name="researchForm" property="listrch" indexId="idx">
	        			<input type="hidden" name="formseq<%=idx.intValue() %>" value="<bean:write name="list" property="formseq"/>">
			     		<input type="hidden" name="formtype<%=idx.intValue() %>" value="<bean:write name="list" property="formtype"/>">
			     		<input type="hidden" name="examtype<%=idx.intValue() %>" value="<bean:write name="list" property="examtype"/>">
			     		<input type="hidden" name="require<%=idx.intValue() %>" value="<bean:write name="list" property="require"/>">
			     		<input type="hidden" name="exsqs<%=idx.intValue() %>" value="ex_<bean:write name="list" property="ex_frsq"/>_<bean:write name="list" property="ex_exsq"/>">
						<input type="hidden" name="exfrsq<%=idx.intValue() %>" value="<bean:write name="list" property="ex_frsq"/>">
			     		<input type="hidden" name="exexsq<%=idx.intValue() %>" value="<bean:write name="list" property="ex_exsq"/>">
			     		
						<bean:define id="ex_exsq" name="list" property="ex_exsq"/>
						 <%
						String[] exfrsq = nexti.ejms.util.Utils.convertHtmlBrNbsp(ex_exsq.toString()).split("_");
						for(int j = 0 ; j < exfrsq.length ; j++){
						%>
							<input type="hidden" name="ex1_<bean:write name="list" property="ex_frsq"/>_<%=exfrsq[j]%>" value="<bean:write name="list" property="ex_exsq"/>">
						<%	
						}
						%>
						<li class="ex_<bean:write name="list" property="ex_frsq"/>_<bean:write name="list" property="ex_exsq"/>" 
						
			     		<logic:notEqual name="list" property="ex_frsq" value="0">
						 style='display:none'
						</logic:notEqual>					
						>
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
									<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%>, 'imageView<%=idx.intValue()%>', '<%=basePath%><bean:write name="list" property="filenm"/>', 50, 50)"><u>미리보기1</u></a>)									
									<div id="imagePreview<%=idx.intValue()%>" >
										<%-- <table id="imagePreview<%=idx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
											<tr align="center" valign="middle">
												<td><iframe id="imagePreview<%=idx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
											</tr>
										</table> --%>
										<table id="imagePreview<%=idx.intValue()%>_t2" border="0" >
											<tr align="center" valign="middle">
												<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%>)"><img id="imageView<%=idx.intValue()%>" alt="클릭하시면 닫힘니다"  style="border: 1px;" width="1" height="1"></a></td>
											</tr>
										</table>
									</div>
								</p>
									<script type="text/javascript">previewImage(imagePreview<%=idx.intValue()%>, 'imageView<%=idx.intValue()%>', '<%=basePath%><bean:write name="list" property="filenm"/>', 590, 408);</script>
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
													<%-- <input type="radio" name="anscont<%=idx.intValue() %>" style="border:0;background-color:transparent;" value="<%=eseq.toString() %>" onclick="check_other('<%=idx.intValue()%>','<%=eidx.intValue()%>')"> --%>
													<input type="radio" name="anscont<%=idx.intValue() %>" style="border:0px;background-color:transparent;" value="<%=eseq.toString() %>" onclick="check_other('<%=idx.intValue()%>','<%=eidx.intValue()%>'); ex_research('<bean:write name="list" property="formseq"/>','<%=eseq.toString() %>', '<bean:write name="list" property="ex_frsq"/>','<bean:write name="list" property="ex_exsq"/>','<%=elistsize %>','<%=idx.intValue() %>')">
													<label for=""><logic:notEmpty name="elist" property="examcont">
																        	<bean:define id="examcont" name="elist" property="examcont"/>
																			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
																		</logic:notEmpty>
																		<logic:notEmpty name="elist" property="originfilenm">
											                            	<br>(첨부 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
																										<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u>미리보기2</u></a>)
																										<div id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>">
																											<%-- <table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																												<tr align="center" valign="middle">
																													<td><iframe id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																												</tr>
																											</table> --%>
																											<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t2" border="0" >
																												<tr align="center" valign="middle">
																													<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>)"><img id="imageView<%=idx.intValue()%><%=eidx.intValue()%>" alt="클릭하시면 닫힘니다" style="border: 1px;" width="1" height="1"></a></td>
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
													<input type="checkbox" name="anscont<%=idx.intValue() %>_<%=eidx.intValue() %>"  value="<%=eseq.toString()%>" onclick="check_other('<%=idx.intValue()%>','<%=eidx.intValue()%>')">
													<label for="" style="padding-left:0px;">
														<logic:notEmpty name="elist" property="examcont">
												        	<bean:define id="examcont" name="elist" property="examcont"/>
																	<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
														</logic:notEmpty>
														<logic:notEmpty name="elist" property="originfilenm">
							                            	<br>(첨부 : <a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="elist" property="filenm"/>&fileName=<bean:write name="elist" property="originfilenm"/>"><bean:write name="elist" property="originfilenm"/></a>
																						<a class="list_s2" href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>, 'imageView<%=idx.intValue()%><%=eidx.intValue()%>', '<%=basePath%><bean:write name="elist" property="filenm"/>', 50, 50)"><u>미리보기3</u></a>)
																						<div id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>" >
																						<%-- 	<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t1" border="0" bgcolor="white" style="position:absolute">
																								<tr align="center" valign="middle">
																									<td><iframe id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_f" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" title=""></iframe></td>
																								</tr>
																							</table> --%>
																							<table id="imagePreview<%=idx.intValue()%><%=eidx.intValue()%>_t2" border="0" >
																								<tr align="center" valign="middle">
																									<td><a href="javascript:previewImage(imagePreview<%=idx.intValue()%><%=eidx.intValue()%>)"><img id="imageView<%=idx.intValue()%><%=eidx.intValue()%>" alt="클릭하시면 닫힘니다" style="border: 1px;" width="1" height="1"></a></td>
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
						        		<input type="radio" name="anscont<%=idx.intValue() %>"  style="margin-top:6px;" value="" onclick="ex_research('<bean:write name="list" property="formseq"/>','0', '<bean:write name="list" property="ex_frsq"/>','<bean:write name="list" property="ex_exsq"/>','<%=elistsize %>','<%=idx.intValue() %>')">
						        		<label for="" style="width:100%">
						        		기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:93%;" maxlength="2000" onclick="check_other('<%=idx.intValue()%>','<%=elistsize.intValue()%>')">
						        		</label>
										</li>
						        	</logic:equal>
						        	</logic:equal>
						        	<logic:equal name="list" property="formtype" value="02">		 
									<logic:equal name="list" property="examtype" value="Y">
										<li>
											<input type="checkbox" name="anscont<%=idx.intValue() %>_<%=elistsize.intValue()%>" style="margin-top:6px;"  value="">
											<label for="" style="width:100%;margin-top:-18px; ">기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:93%;" maxlength="2000" onclick="check_other('<%=idx.intValue()%>','<%=elistsize.intValue()%>')"></label>
										</li>
									</logic:equal>
									</logic:equal>
									<logic:equal name="list" property="formtype" value="03">
									<logic:equal name="list" property="security" value="Y">
										<li>
											<input type="password" name="anscont<%=idx.intValue() %>0304" style="width:100%;">
										</li>
									</logic:equal>
										
									<logic:notEqual name="list" property="security" value="Y">
										<li>
											<input type="text" name="anscont<%=idx.intValue() %>0304" style="width:100%;" maxlength="2000">
										</li>
									</logic:notEqual>

									</logic:equal>
									<logic:equal name="list" property="formtype" value="04">
										<li>	
											<textarea name="anscont<%=idx.intValue() %>0304" rows="5" style="width:100%;" onkeyup="maxlength_check(this, 2000)"></textarea>
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
				<br>
				<div class="text-center mb20">
					<a href="javascript:save(document.forms[0])" class="btn-join">답변등록하기</a>
				</div>				
			</div>
		</div>
	</div>
</form>
<jsp:include page="/include/tail.jsp" flush="true"/>