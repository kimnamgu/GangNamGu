<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력완료 취합양식자료수정
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
String sysdocno = "";
String formseq = "";
String formkind = "";
String enddt = "";
String endcomment = "";

if(request.getParameter("sysdocno") != null) {
	sysdocno = request.getParameter("sysdocno");
} else if(request.getAttribute("sysdocno") != null) {
	sysdocno = request.getAttribute("sysdocno").toString();
}

if(request.getParameter("formseq") != null) {
	formseq = request.getParameter("formseq");
} else if(request.getAttribute("formseq") != null) {
	formseq = request.getAttribute("formseq").toString();
}

if(request.getParameter("formkind") != null) {
	formkind = request.getParameter("formkind");
}

if(request.getAttribute("enddt") != null) {
	enddt = request.getAttribute("enddt").toString();
}

if(request.getAttribute("endcomment") != null) {
	endcomment = request.getAttribute("endcomment").toString();
}
%>

<% if (session.getAttribute("getDept_YN").equals("N")){ %>
<jsp:include page="/include/header_user_005.jsp" flush="true"/>
<% } else { %>
<jsp:include page="/include/header_user.jsp" flush="true"/>
<% } %>

<script src="/script/prototype.js"></script>
<script src="/script/inputing.js"></script>
<script>
menu3.onmouseover();
menu33.onmouseover();

var inputComplete = true;
function iframeScroll() {
	var frmDoc = formFrame.document;
	if(frmDoc.readyState == "complete") {
		frmDoc.body.scrollTop = parseInt("0<%=(String)request.getParameter("frmScrollTop")%>", 10);
		
		var sh = frmDoc.body.scrollHeight;
		if (sh < formFrameTD.height) {	//양식다섯개
			formFrameTD.height = sh;
		}
	}
}

function firstInputSelect() {
	try {
		if(document.forms[0].A.tagName == "SELECT") {
			document.forms[0].A.focus();
		} else if(document.forms[0].A.length > 0) {			
			document.forms[0].A[0].focus();
		} else {
			document.forms[0].A.focus();
		}
	} catch(e) {
	}
}

function checkSave(frm, mode) {
	if(draglayer != "") {
		return;
	}
	
	var isEmpty = true;
	var tblcols = document.forms[0].tblcols.value;
	var divCount = document.forms[0].getElementsByTagName("div").length;
	
	for(var i = 0; i < tblcols; i++) {
		var cellname = "";
		if ( i  < 26 ) cellname = String.fromCharCode(65 + i)
		else cellname = String.fromCharCode(65 + i - 26) + String.fromCharCode(65 + i - 26);	
	
		var objValue = "";
		if(divCount == 0) {
			objValue = eval("document.forms[0]." + cellname + ".value");
		} else {
			objValue = eval("document.forms[0]." + cellname + "[0].value");
		}
		
		if(objValue != "" && objValue != "autocal") {
			isEmpty = false;
		}
	}
	
	if(isEmpty == true) {
		alert("한개 이상의 항목을 입력하셔야 합니다.");
		return;
	}
	
	var param = "?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=" + formFrame.document.body.scrollTop + "&scrollTop=" + document.body.scrollTop;
	param += "&strIdx=" + (document.forms[0].strIdx.value - 1) + "&endIdx=" + document.forms[0].endIdx.value;
	
	frm.target = "actionFrame";
	frm.mode.value = mode;
	//입력완료에서 수정하여 저장시는 dataInsert
	frm.mode.value = "dataInsert";
	frm.action += param;
	frm.submit();
}

function checkSave2(frm, mode) {
	if(draglayer != "") {
		return;
	}
	
	if(confirm("저장하시겠습니까?") == false) {
		return;
	}
	
	var param = "?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=" + formFrame.document.body.scrollTop;
	
	frm.target = "actionFrame";
	frm.mode.value = mode;
	frm.action += param;
	frm.submit();
}

function checkModify(frm,seq) {
	var param = "?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=" + formFrame.document.body.scrollTop + "&scrollTop=" + document.body.scrollTop;
	param += "&strIdx=" + (document.forms[0].strIdx.value - 1) + "&endIdx=" + document.forms[0].endIdx.value;
	var cnf = window.confirm("저장하시겠습니까?");
	
	if(cnf) {
		frm.target = "actionFrame";
		frm.mode.value = "update";
		frm.seq.value = seq;
		frm.action = "/inpCompLineDataSave.do" + param;
		frm.submit();
	}
}	

function checkDelete(frm,seq) {
	if(draglayer != "") {
		return;
	}
	
	var cnf = window.confirm("삭제하시겠습니까?");
	
	var param = "?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=" + formFrame.document.body.scrollTop + "&scrollTop=" + document.body.scrollTop;
	param += "&strIdx=" + (document.forms[0].strIdx.value - 1) + "&endIdx=" + document.forms[0].endIdx.value;
	
	if(cnf) {
		frm.target = "actionFrame";
		frm.mode.value = "delete";
		frm.seq.value = seq;
		frm.action = "/inpCompLineDataSave.do" + param;
		frm.submit();
	}
}

function click_deleteAll(actionpath) {
	if(confirm("모든 데이터가 삭제됩니다\n삭제하시겠습니까?") == true) {
		var param = "?";
		if(actionpath.indexOf("?") != -1) {
			param = "&";
		}
		param += "formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=" + formFrame.document.body.scrollTop;

		document.forms[0].action = actionpath + param;
		document.forms[0].submit();
	}
}

//수정창(레이어) 보이기/숨기기, 이동
var draglayer = "";
var drag = false;
var xPos = 0;
var yPos = 0;
document.onmouseup = mouseUpLayer;
document.ondragstart = function() {
	return false;
}
document.onselectstart = function() {
	if(drag == true) return false;
}

function mouseDownLayer() {
	if(event.srcElement.tagName == "INPUT" ||
			event.srcElement.tagName == "SELECT" ||
			event.srcElement.tagName == "TEXTAREA") {
			return;
	}
	drag = true;
	xPos = event.clientX;
	yPos = event.clientY;
}
function mouseUpLayer() {
	drag = false;
	xPos = 0;
		yPos = 0;
	}
	
	function mouseMoveLayer() {
		if(drag == true) {
			draglayer.style.pixelLeft -= xPos - event.clientX;
			draglayer.style.pixelTop -= yPos - event.clientY;
			
			xPos = event.clientX;
			yPos = event.clientY;
		}
	}
	
	function showModifyLayer(divObj, frm, seq) {
		if(divObj.style.display == "block") {
			divObj.style.display = "none";
			draglayer = "";
			frm.strIdx.readOnly = false;
		} else if(divObj.style.display == "none") {
			frm.strIdx.readOnly = true;
			if(draglayer != "") {
				return;
			}
			var width = tbl[0].clientWidth - 60;
			var height = tbl[0].clientHeight + 90;		
			var frmObj = document.getElementById("bgfrm" + seq);
			frmObj.width = width;
			frmObj.height = height;	
			
			var imgObj = eval("document.bgimg" + seq);
			imgObj.width = width;
			imgObj.height = height;

			var y = getAbsoluteTop(event.srcElement);
			divObj.style.display = "block";
			divObj.width = width;
			divObj.height = height;
			divObj.style.pixelLeft = width / 5;
			divObj.style.pixelTop = y - height / 2;
			divObj.onmousedown=mouseDownLayer;
			divObj.onmousemove=mouseMoveLayer;
			divObj.onmouseup=mouseUpLayer;
			draglayer = divObj;
		}
	}
</script>

<div id="comment" style="display:none; position:absolute;">
<table border="1" style="border-collapse:collapse" bgcolor="rgb(255, 255, 200)"><tr><td id="msg"></td></tr></table>
</div>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/inputing/title_04.gif" alt=""></td>
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
                            <td width="94"><img src="/images/collect/s_m_01.gif" width="94" height="41" alt="취합문서정보" style="cursor:hand" onclick="location.href='/inputCompCollDocInfoView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop"></td>
                            <td width="94"><img src="/images/collect/s_m_02_over.gif" width="94" height="41" alt="취합양식자료"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;">
                            	<% if (nexti.ejms.common.appInfo.isElecappr()){ %><img src="/images/common/btn_elecappr_2.gif" alt="전자결재 후 자료제출" style="cursor:hand" onclick="makeDraftDocument(<%=sysdocno%>)"><% } %>
                              <img src="/images/common/btn_list.gif" alt="목록보기" style="cursor:hand" onclick="location.href='/inputCompleteList.do'">
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
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--마감시한/알림말 테이블 시작-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="250"><font color="#4F4F4F"><b>마감시한 : </b></font><b><font color="#FF5656"><%=enddt%></font></b></td>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="510"><font color="#4F4F4F"><b>마감알림말 : </b></font><font color="#4F4F4F"><%=endcomment%></font></td>
                          </tr>
                        </table>
                        <!--마감시한/알림말 테이블 끝-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top" height="172" id="formFrameTD"><iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="양식목록" onload="iframeScroll()"></iframe></td>
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

<logic:notEmpty name="formatLineForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatLineForm" property="formcomment">
										<bean:define id="formcomment" name="formatLineForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">자료입력</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1"><iframe name="xlsUpload" src="/xlsUploadFrame.do?sysdocno=<bean:write name="formatLineForm" property="sysdocno"/>&formseq=<bean:write name="formatLineForm" property="formseq"/>" frameborder="0" scrolling="no" width="700" height="22" title=""></iframe></td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<form action="/inpCompLineDataSave.do" method="POST" name="formatLineForm">
<input type="hidden" name="sysdocno" value="<bean:write name="formatLineForm" property="sysdocno"/>">
<input type="hidden" name="formseq" value="<bean:write name="formatLineForm" property="formseq"/>">
<input type="hidden" name="tblcols" value="<bean:write name="formatLineForm" property="tblcols"/>">
<input type="hidden" id="datacnt" name="datacnt" value="<bean:write name="formatLineForm" property="tblrows"/>">
<input type="hidden" name="mode">
<input type="hidden" name="seq">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">	
  <tr> 
  	<td><!-- 양식데이터 나타낼 부분 시작-->
			<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
			<bean:write name="formatLineForm" property="formbodyhtml" filter="false"/>
			<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
			<script>instantCalculation("autocal")</script>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
  <tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td class="stitle2">
			&nbsp;&nbsp;&nbsp;<img src="/images/common/dot_04.gif" alt="">&nbsp;입력된 자료
			<%	//임시코드 : 대용량 데이터 처리 방안 도출시 새로 작성해야함
				int strIdx = Integer.parseInt("0"+nexti.ejms.util.Utils.nullToEmptyString(request.getParameter("strIdx")));
				int endIdx = Integer.parseInt("0"+nexti.ejms.util.Utils.nullToEmptyString(request.getParameter("endIdx")));
				if ( endIdx == 0 ) endIdx = 50;
				int totalCnt = 0;
				java.util.List lst = ((nexti.ejms.formatLine.form.FormatLineForm)request.getAttribute("formatLineForm")).getListform();
				if ( lst != null && lst.size() > 0 ) totalCnt = ((nexti.ejms.formatLine.model.FormatLineBean)lst.get(0)).getDatacnt();
			%>
			( 전체자료 <%=totalCnt%>건 중 <input type="text" name="strIdx" size="3" style="text-align:center;" onchange="if(this.value<1){this.value=1}" onkeypress="inputFilterKey('[0-9]')" value="<%=strIdx + 1%>">번 자료부터
			<input type="text" name="endIdx" size="3" style="text-align:center;" onchange="if(this.value<1){this.value=0}" onkeypress="inputFilterKey('[0-9]')" value="<%=endIdx%>">건의
			<u style="cursor:hand" onclick="javascript:loc=location.href+'&strIdx';location.href=loc.substring(0,loc.indexOf('&strIdx'))+'&strIdx='+(strIdx.value-1)+'&endIdx='+endIdx.value">자료조회</u> )
		</td>
	</tr>
  <tr> 
  	<td><!-- 양식데이터 나타낼 부분 시작-->
		<logic:iterate id="list" name="formatLineForm" property="listform">
			<div id="modifyLayer<bean:write name="list" property="seqno"/>" style="display:none; position:absolute; background-color:white; z-index:2">
				<div style="position:absolute;">
					<iframe name="bgfrm<bean:write name="list" property="seqno" />" width="0" height="0" frameborder="0" title="자료수정"></iframe>
				</div>
				<div style="position:absolute;">
					<img name="bgimg<bean:write name="list" property="seqno" />" src="/images/inputing/layerBgImg.gif" width="0" height="0" galleryimg="no" alt="">
				</div>
				<div style="position:absolute; left:20; top:35">
					<bean:write name="list" property="formheaderhtml" filter="false"/>
					<bean:write name="list" property="formbodyhtml" filter="false" />
					<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
					<!--저장,취소 버튼을 formtatilhtml에 담아서 넘김-->
					<bean:write name="list" property="formtailhtml" filter="false" />
					<script>instantCalculation("autocal")</script>
				</div>
			</div>
		</logic:iterate>
		<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
		<logic:iterate id="list_ext" name="formatLineForm" property="listform_ext">
			<bean:write name="list_ext" property="formbodyhtml" filter="false" />
		</logic:iterate>
		<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td>
			<logic:equal name="formatLineForm" property="openinput" value="Y"><img src="/images/common/btn_otherdept_data.gif" style="cursor:hand" onclick="collprocViewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', '')" alt=""></logic:equal>
			<img src="/images/common/btn_b_del.gif" style="cursor:hand" onclick="click_deleteAll('/inputLineDataDel.do?type=comp')" alt="">
			<img src="/images/common/btn_modify3.gif" alt="수정완료" style="cursor:hand" onclick="location.href='/inputCompFormatLineView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop">
		</td>
	</tr>
</table></form><br>
</logic:notEmpty>

<logic:notEmpty name="formatFixedForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatFixedForm" property="formcomment">
										<bean:define id="formcomment" name="formatFixedForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">자료입력</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1"><iframe name="xlsUpload" src="/xlsUploadFrame.do?sysdocno=<bean:write name="formatFixedForm" property="sysdocno"/>&formseq=<bean:write name="formatFixedForm" property="formseq"/>" frameborder="0" scrolling="no" width="700" height="22" title=""></iframe></td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<form action="/inpCompFixedDataSave.do" method="POST" name="formatFixedForm">
<input type="hidden" name="sysdocno" value="<bean:write name="formatFixedForm" property="sysdocno"/>">
<input type="hidden" name="formseq" value="<bean:write name="formatFixedForm" property="formseq"/>">
<input type="hidden" name="tblcols" value="<bean:write name="formatFixedForm" property="tblcols"/>">
<input type="hidden" name="tblrows" value="<bean:write name="formatFixedForm" property="tblrows"/>">
<input type="hidden" name="mode">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td><!-- 양식데이터 나타낼 부분 시작-->
			<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formbodyhtml" filter="false"/>
			<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
			<script>instantCalculation("autocal", document.getElementsByName("A").length)</script>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
  <tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td>
			<input type="text" onfocus="this.blur();savebutton.click()" style="width:0">
			<logic:equal name="formatFixedForm" property="openinput" value="Y"><img src="/images/common/btn_otherdept_data.gif" style="cursor:hand" onclick="collprocViewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', '')" alt=""></logic:equal>
			<img src="/images/common/btn_b_del.gif" style="cursor:hand" onclick="click_deleteAll('/inputFixedDataDel.do?type=comp')" alt="">
			<img src="/images/common/btn_modify3.gif" alt="수정완료" name="savebutton" style="cursor:hand" onclick="checkSave2(document.forms[0],'update')">
		</td>
	</tr>
</table></form><br>
</logic:notEmpty>

<logic:notEmpty name="formatBookForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">양식개요</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatBookForm" property="formcomment">
										<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">제출양식</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:notEmpty name="fileBookForm" property="listfilebook">
										<logic:iterate id="list" name="fileBookForm" property="listfilebook">
											<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
										</logic:iterate>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
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
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
<html:form action="/inpCompDataBookSave.do" method="POST" enctype="multipart/form-data">
<bean:define id="docno" name="formatBookForm" property="sysdocno"/>
<bean:define id="formseq1" name="formatBookForm" property="formseq"/>
<input type="hidden" name="sysdocno" value="<%=Integer.parseInt(docno.toString())%>">
<input type="hidden" name="formseq" value="<%=Integer.parseInt(formseq1.toString())%>">
  <tr> 
  	<td><!-- 양식데이터 나타낼 부분 시작-->
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
	        <td> 
	          <!--자료입력 테이블-->
	          <table width="820" border="0" cellspacing="0" cellpadding="0">
	            <tr> 
	              <td colspan="5" class="list_bg4"></td>
	            </tr>
	            <tr> 
	              <td width="100" class="s3">카테고리</td>
	              <td class="s3">제목</td>
	              <td width="250" class="s3">첨부파일명</td>
	              <td width="85" class="s3">파일크기</td>
	              <td width="60" class="s3">&nbsp;</td>
	            </tr>
	            <tr> 
	              <td colspan="5" class="list_bg4"></td>
	            </tr>
			 		<logic:notEmpty name="dataForm" property="dataList">
			     	<logic:iterate id="list" name="dataForm" property="dataList" indexId="i">
							<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'"> 
					      <td class="list_l"><bean:write name="list" property="categorynm"/></td>
					      <td class="list_l"><bean:write name="list" property="formtitle"/></td>
					      <td class="list_l"><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a></td>
					      <td class="list_l" style="padding-right:10;text-align:right"><script>convertSize(<bean:write name="list" property="filesize"/>)</script></td>
					      <td class="list_l"><img src="/images/common/btn_del.gif" alt="삭제" style="cursor:hand" onclick="InputCompDeleteDataBookFrm('<bean:write name="list" property="sysdocno"/>', '<bean:write name="list" property="formseq"/>', '<bean:write name="list" property="fileseq"/>', '<bean:write name="list" property="filenm"/>')"></td>
					    </tr>
					    <tr> 
					      <td colspan="5" class="list_bg4"></td>
					    </tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty name="dataForm" property="dataList">
							<tr>
								<td class="list_l" colspan="6">검색된 목록이 없습니다</td>
							</tr>
							<tr> 
					      <td colspan="5" class="list_bg4"></td>
					    </tr>
					</logic:empty>
	          </table>
	        </td>
	      </tr>
	      <tr> 
	        <td>&nbsp;</td>
	      </tr>
	      <tr> 
	        <td> 
	          <!--카테고리,파일첨부 테이블-->
	          <table width="820" border="0" cellspacing="0" cellpadding="0">
	            <tr> 
	              <td colspan="7" class="list_bg4"></td>
	            </tr>
	            <tr> 
	              <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">카테고리</td>
	              <td class="bg3"></td>
	              <td class="t1"> 
									<html:select property="categorynm" style="width:98%" onchange="setChanged()">
										<jsp:useBean id="categoryselect" class="nexti.ejms.list.form.CategoryListForm">
											<%categoryselect.setSysdocno(Integer.parseInt(docno.toString()));	//시스템문서번호 지정%>
											<%categoryselect.setFormseq(Integer.parseInt(formseq.toString()));	//양식일련번호 지정%>
										</jsp:useBean>
										<html:optionsCollection name="categoryselect" property="beanCollection"/>
	               	</html:select>
	              </td>
	              <td class="bg3"></td>
	              <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">제목</td>
	              <td class="bg3"></td>
	              <td class="t1" width="400"> 
	                <html:text property="formtitle" style="width:98%" onchange="setChanged();"  onkeydown="if(event.keyCode == 13)return false;"/>
	              </td>
	            </tr>
	            <tr> 
	              <td colspan="7" class="list_bg4"></td>
	            </tr>
	            <tr> 
	              <td width="100" class="s2" rowspan="2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">파일첨부</td>
	              <td class="bg3" rowspan="2"></td>
	              <td colspan="5" class="t1" height="28"> 
	                <input type="file" name="inputFile" style="width:90%" onchange="setChanged()" contentEditable="false">&nbsp;
	                <img src="/images/common/btn_record.gif" width="50" height="21" align="absmiddle" alt="등록" style="cursor:hand" onclick="InputCompInsertDataBookFrm()">
	              </td>
	            </tr>
	            <tr> 
	              <td colspan="5" class="t1">※ <font color="#FF5656">10Mbyte</font>이하의 파일을 선택하세요.</td>
	            </tr>
	            <tr> 
	              <td colspan="7" class="list_bg4"></td>
	            </tr>
	          </table>
	        </td>
	      </tr>
			  <tr>
					<td height="5"></td>
				</tr>
	      <tr> 
      		<td>
      			<logic:equal name="formatBookForm" property="openinput" value="Y"><img src="/images/common/btn_otherdept_data.gif" style="cursor:hand" onclick="collprocViewForm('<%=formkind%>', '<%=sysdocno%>', '<%=formseq%>', '')" alt=""></logic:equal>
      			<img src="/images/common/btn_b_del.gif" style="cursor:hand" onclick="click_deleteAll('/inputBookDataDel.do?type=comp&sysdocno=' + sysdocno.value + '&formseq=' + formseq.value)" alt="">
      			<img src="/images/common/btn_modify3.gif" alt="수정완료" style="cursor:hand" onclick="location.href='/inputCompFormatBookView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop">
      		</td>
	      </tr>
			</table>
  	</td><!-- 양식데이터 나타낼 부분 끝-->
  </tr>
</html:form>
</table><br>
</logic:notEmpty>

<script>
firstInputSelect();
document.body.scrollTop = parseInt("0<%=(String)request.getParameter("scrollTop")%>", 10);
</script>
<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>')</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>