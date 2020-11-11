<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 답변
 * 설명:
--%>
<%@page import="nexti.ejms.dept.model.DeptManager"%>
<%@page import="nexti.ejms.user.model.UserManager"%>
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
<%String ss = session.getAttribute("dept_code").toString();%>


<html>
<head>
<title>설문조사</title>
<link rel="stylesheet" href="/css/style_gangnam.css" type="text/css">
<script src="/script/common.js"></script>
<script src="/script/research.js"></script>

<!--  설문조사 직급, 직위별 참여대상 제한 임시작업 2016 06 시작 -->

<% 
              //특정사용자 직급, 직위 등에 따른 설문참여 조건 설정을 위한 임시 수정 2016.06.10
             
               //대상 설문 번호
               String  com_rchno="4510";
         
               //세션에서 로그인한 아이디값을 가져옴
               String s_id = session.getAttribute("user_id").toString();
             
              
              	nexti.ejms.user.model.UserManager mgrtempuser = UserManager.instance();
              	nexti.ejms.dept.model.DeptManager mgrtempdept =DeptManager.instance();
            	nexti.ejms.user.model.UserBean loginuser = mgrtempuser.getUserInfo(s_id);
            	
            	// 해당 id값의 classid, gradid, deptid, positionid 등을 가져옴
            	String str_Class_id = loginuser.getClass_id();
            	String str_Class_name = loginuser.getClass_name();
            	String str_Dept_id = loginuser.getDept_id();
            	String str_Dept_name = loginuser.getDept_name();
            	String str_Grade_id = loginuser.getGrade_id();
            	String str_Grade_name = loginuser.getGrade_name();
            	String str_Position_id = loginuser.getPosition_id();
            	String str_Position_name = loginuser.getPosition_name();
            	
        
            	String rno1 = request.getParameter("rchno");

            	if(rno1.equals(com_rchno)){
            		if  (!str_Class_id.equals("00911") && !str_Class_id.equals("00909")  &&  !str_Position_id.equals("0036100") && !str_Position_id.equals("0013600"))
		      		 { 
            	%>
            	<script>
	            	alert("본 설문조사의 참여 대상이 아닙니다.");
					self.close();
            	</script>
            	<%
            	}else {System.out.println(rno1);}
            	}
               %>
               <!--  2016 06 임시작업 끝 -->

<!-- 4479설문조사의 항목 클릭시 펼침 스크립트 적용 -->
<%
	String rno = request.getParameter("rchno");
%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<style>
.examdivoff {width:600px;height:15px; text-overflow:ellipsis;white-space:nowrap;overflow:hidden;}
.examdivon {width:600px; height:100%}
</style>

<script type="text/javascript">

var researchNo = <%=rno%>

if (researchNo=="4479"){

$(document).ready(function(){	
	
	$('.ptext2  > div').addClass("examdivoff");
	
	$('.ptext2  > div').click(
		function(){
			
			if($(this).attr("class") == "examdivon"){
				
				$(this).addClass("examdivoned");
				
			}
		
			$('.ptext2  > div').addClass("examdivoff");
			$('.ptext2  > div').removeClass("examdivon");
			
			if($(this).attr("class") == "examdivoned examdivoff"){
				$('.ptext2  > div').removeClass("examdivoned");
			}else{
				$(this).removeClass("examdivoff");	
				$(this).addClass("examdivon");
				$(this).css({"color":"black","cursor":"default"});
			}
			
				
			
		}		
	);	 
	
	$('.ptext2 > div').hover(
			
			function(){		
				
				$(this).css({"color":"#008cd4","cursor":"hand"});
				
			},function(){
				$(this).css({"color":"black"});
			}
			
	);
	
}); //document ready function 닫기

} //if (no=4479) 닫기
</script> 

<!-- 4479설문조사의 항목 클릭시 펼침 스크립트 적용 2016 04 25 여기까지-->

<script type="text/javascript">


	function cancel(){
		self.close();
	}
	
	function save(frm){
	
		var listcnt = frm.listcnt.value;	
		
			
		for(var i=0; i<listcnt; i++){
			var formtype = eval("document.forms[0].formtype"+i+".value");
			var examtype = eval("document.forms[0].examtype"+i+".value");
			var elistcnt = eval("document.forms[0].elistcnt"+i+".value");
			var cnt=0;
			var cnt1=0;
			if(formtype=="01" & true){	// & false 를 추가하면 필수답변 체크 안함
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
			}else if(formtype=="02" & true){	// & false 를 추가하면 필수답변 체크 안함
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
			}else{
				cnt++;
			}
			
			if( cnt==0 ){
				alert((i+1)+"번째 선택 설문에 대한 답변이 없습니다.");
				return;
			}



                       if(formtype=="02" && document.forms[0].rchno.value == "4440" )
                        {    
                                for(var j=0; j<elistcnt; j++){
                                    var enc = eval("document.forms[0].anscont"+i+"_"+j+".checked");
                                    if(enc){
					cnt1++;
                                    }
                                }

                                if(cnt1 != 4 ){
				    alert('답변을 4개 선택하셔야 합니다!!');
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="fullSizeWindow(-1, (screen.availHeight * 0.8))">


<form id=frm name=frm method=post>
<input type="hidden" name="rchno" value="<bean:write name="researchForm" property="rchno"/>">
<bean:size id="listsize" name="researchForm" property="listrch"/>
<input type="hidden" name="listcnt" value="<%=listsize %>">
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
          <td background="/images/common/title_bg.gif" height="38" width="644"><img src="/images/collect/title_36.gif" alt=""></td>
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
                    <logic:present parameter="grp"><bean:parameter id="grp" name="grp"/></logic:present>
                    <logic:notEqual name="grp" value="y">
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
	                    <tr> 
	                      <td colspan="2" height="5"></td>
	                    </tr>
                    </logic:notEqual>
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
        <logic:notEmpty name="researchForm" property="listrch">
        	<logic:iterate id="list" name="researchForm" property="listrch" indexId="idx">
		     		<input type="hidden" name="formseq<%=idx.intValue() %>" value="<bean:write name="list" property="formseq"/>">
		     		<input type="hidden" name="formtype<%=idx.intValue() %>" value="<bean:write name="list" property="formtype"/>">
		     		<input type="hidden" name="examtype<%=idx.intValue() %>" value="<bean:write name="list" property="examtype"/>">
		        <tr> 
		          <td valign="top"> 
		            <table width="644" border="0" cellspacing="0" cellpadding="0">
		            	<logic:notEmpty name="list" property="formgrp">
			            	<tr> 
			                <td class="pbg"></td>
			              </tr>
		            		<tr> 
			                <td class="ptitle">
			                	<table>
			                		<tr>
			                			<td class="ptitle"><img src="/images/common/dot_05.gif" align="absmiddle" alt=""></td>
			                			<td class="ptitle">
			                				<logic:notEmpty name="list" property="formgrp">
											        	<bean:define id="formgrp" name="list" property="formgrp"/>
											    			<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formgrp.toString())%>	
															</logic:notEmpty>
			                			</td>
			                		</tr>
			                	</table>
											</td>
			              </tr>
			              <tr> 
			                <td class="pbg"></td>
			              </tr>
									</logic:notEmpty>
		              <tr> 
		                <td> 
		                  <table width="644" border="0" cellspacing="0" cellpadding="0">
		                    <tr> 
		                      <td>
		                      	<logic:equal name="researchForm" property="imgpreview" value="1">
		                      		<% nexti.ejms.research.model.ResearchSubBean rchSubBean = (nexti.ejms.research.model.ResearchSubBean)list; %>
		                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getFilenm()); %>
		                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchSubBean.getExt()).toLowerCase(); %>
		                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
		                      			<center><br>[질문그림-<%=idx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
															<% } %>
														</logic:equal>
														<table>
					                		<tr>
					                			<td class="ptext" valign="top" style="height:0"><%=idx.intValue()+1%>.</td>
					                			<td class="ptext" style="height:0">
					                				<logic:notEmpty name="list" property="formtitle">
													        	<bean:define id="formtitle" name="list" property="formtitle"/>
													        	<%
													        				//특정설문조사인 경우 html 처리 안함 2016 06
													        				if(rno1.equals(com_rchno))
													        				{
													        	%>
													        			<%=formtitle.toString()%> 
													        	<%}else{ %> 
																		<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formtitle.toString())%>
																<%} %>
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
																</td>
															</tr>
														</table>
		                      </td>
		                    </tr>
		                    <tr> 
		                      <td> 
		                        <table width="620" border="0" cellspacing="0" cellpadding="0" align="center">
		                        	<bean:size id="elistsize" name="list" property="examList"/>
															<input type="hidden" name="elistcnt<%=idx.intValue() %>" value="<%=elistsize %>">
											        <logic:iterate id="elist" name="list" property="examList" indexId="eidx">
											        	<bean:define id="eseq" name="elist" property="examseq" />
												       	<logic:equal name="list" property="formtype" value="01">
													       	<tr> 
				                            <td>
				                            	<logic:equal name="researchForm" property="imgpreview" value="1">
							                      		<% nexti.ejms.research.model.ResearchExamBean rchExamBean = (nexti.ejms.research.model.ResearchExamBean)elist; %>
							                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getFilenm()); %>
							                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getExt()).toLowerCase(); %>
							                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
							                      			<center><br>[보기그림-<%=idx.intValue()+1%>.<%=eidx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
																				<% } %>
																			</logic:equal>
																			<table>
										                		<tr>
										                			<td class="ptext2" valign="top"><input type="radio" name="anscont<%=idx.intValue() %>" style="border:0;background-color:transparent;" value="<%=eseq.toString() %>" onclick="check_other('<%=idx.intValue()%>','<%=eidx.intValue()%>')"></td>
										                			<td class="ptext2">
										                			<div class="">
										                				<logic:notEmpty name="elist" property="examcont">
																		        	<bean:define id="examcont" name="elist" property="examcont"/>
																							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
																						</logic:notEmpty>
																	</div>
																						
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
																					</td>
																				</tr>
																			</table>
				                            </td>
				                          </tr>                 
																</logic:equal>
												       	<logic:equal name="list" property="formtype" value="02">
												       		<tr> 
				                            <td>
				                            	<logic:equal name="researchForm" property="imgpreview" value="1">
							                      		<% nexti.ejms.research.model.ResearchExamBean rchExamBean = (nexti.ejms.research.model.ResearchExamBean)elist; %>
							                      		<% String filenm = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getFilenm()); %>
							                      		<% String fileext = nexti.ejms.util.Utils.nullToEmptyString(rchExamBean.getExt()).toLowerCase(); %>
							                      		<% if ( !filenm.equals("") && (fileext.equals("gif") || fileext.equals("jpg") || fileext.equals("png")) ) { %>
							                      			<center><br>[보기그림-<%=idx.intValue()+1%>.<%=eidx.intValue()+1%>]<br><div style="width:500;overflow:auto"><img src="<%=basePath%><%=filenm%>"></div></center>
																				<% } %>
																			</logic:equal>
																			<table>
										                		<tr>
										                			<td class="ptext2" valign="top"><input type="checkbox" name="anscont<%=idx.intValue() %>_<%=eidx.intValue() %>" style="border:0;background-color:transparent;" value="<%=eseq.toString()%>" onclick="check_other('<%=idx.intValue()%>','<%=eidx.intValue()%>')"></td>
										                			<td class="ptext2">
										                			<div >
										                				<logic:notEmpty name="elist" property="examcont">
																		        	<bean:define id="examcont" name="elist" property="examcont"/>
																							<%=nexti.ejms.util.Utils.convertHtmlBrNbsp(examcont.toString())%>
																						</logic:notEmpty>
																	</div>
																	
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
																					</td>
																				</tr>
																			</table>
				                            </td>
				                          </tr>
																</logic:equal> 
															</logic:iterate>
															<logic:equal name="list" property="formtype" value="01">				
											        	<logic:equal name="list" property="examtype" value="Y">
											        		<tr>
				                            <td>
				                            	<table>
										                		<tr>
										                			<td class="ptext2"><input type="radio" name="anscont<%=idx.intValue() %>" style="border:0;background-color:transparent;" value=""></td>
										                			<td class="ptext2" width="100%">기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:90%;" maxlength="2000" onclick="check_other('<%=idx.intValue()%>','<%=elistsize.intValue()%>')"></td>
																				</tr>
																			</table>
																		</td>
				                          </tr>
												        </logic:equal>	
															</logic:equal>
															<logic:equal name="list" property="formtype" value="02">		 
																<logic:equal name="list" property="examtype" value="Y">
																	<tr>
				                            <td>
				                            	<table>
										                		<tr>
										                			<td class="ptext2"><input type="checkbox" name="anscont<%=idx.intValue() %>_<%=elistsize.intValue()%>" style="border:0;background-color:transparent;" value=""></td>
										                			<td class="ptext2" width="100%">기타&nbsp;:&nbsp;<input type="text" name="other<%=idx.intValue() %>" style="width:90%;" maxlength="2000" onclick="check_other('<%=idx.intValue()%>','<%=elistsize.intValue()%>')"></td></td>
																				</tr>
																			</table>
																		</td>
				                          </tr>
												        </logic:equal>
												      </logic:equal>
															<logic:equal name="list" property="formtype" value="03">
																<logic:equal name="list" property="security" value="Y">
																	<tr> 
				                            <td><input type="password" name="anscont<%=idx.intValue() %>0304" style="width:100%;"></td>
				                          </tr>
												      	</logic:equal>
																<logic:notEqual name="list" property="security" value="Y">
																	<tr> 
				                            <td><input type="text" name="anscont<%=idx.intValue() %>0304" style="width:100%;" maxlength="2000"></td>
				                          </tr>
												      	</logic:notEqual>	      	
											     		</logic:equal>
															<logic:equal name="list" property="formtype" value="04">	
																<tr> 
			                            <td><textarea name="anscont<%=idx.intValue() %>0304" rows="5" style="width:100%;" onkeyup="maxlength_check(this, 2000)"></textarea></td>
			                          </tr>
											      	</logic:equal>
		                        </table>
		                      </td>
		                    </tr>
		                    <tr> 
		                      <td height="5"></td>
		                    </tr>
		                  </table>
		                </td>
		              </tr>
		              <tr>
		              	<td height="10"></td>
		              </tr>
		            </table>
		          </td>
		        </tr>
			  	</logic:iterate>
	      </logic:notEmpty>
	      <tr>
         <td class="pbg"></td>
        </tr>
        <tr>
        	<td height="10"></td>
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
                <td bgcolor="#F6F6F6" align="center" height="35"><a href="javascript:save(document.forms[0])"><img src="/images/common/btn_poll_ok.gif" alt="답변등록하기"></a></td>
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
