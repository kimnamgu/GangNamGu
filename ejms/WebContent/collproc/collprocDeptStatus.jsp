<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 진행중인취합문서 취합진행현황
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.List" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<html>
<head>
<title>제출현황보기</title>
<base target="_self"> 
<link rel="stylesheet" href="/css/style.css" type="text/css">
<script src="/script/common.js"></script>
<script type="text/javascript" src="/script/prototype.js"></script>
<script>
function submit_view(gbn) {
	document.forms[0].mode.value = "view";
	document.forms[0].viewmode.value = gbn;
	document.forms[0].submit();
}

function checkAll_modifyyn(value) {
	if (value) {
		value = "1";
	} else {
		value = "0";
	}
	
	document.forms[0].mode.value = "modifyAll";
	document.forms[0].modifyvalue.value = value;
}

function checkAll_modifyyn(value, chk_parent, chk_child)	{
	if (value) {
		value = "1";
	} else {
		value = "0";
	}
	
	var chkall = chk_parent;
	var chklst = document.getElementsByName(chk_child);
	
	if ( chklst.length < 1 ) return;
	
	for(var i = 0; i < chklst.length; i++)
		chklst[i].checked = chkall.checked;
	
	document.forms[0].mode.value = "modifyAll";
	document.forms[0].modifyvalue.value = value;
	document.forms[0].submit();
}

function submit_modify(tgtdeptcd, modifyvalue, chk_child) {
	if (modifyvalue) {
		modifyvalue = "1";
	} else {
		modifyvalue = "0";
	}

	document.forms[0].mode.value = "modify";
	document.forms[0].modifydept.value = tgtdeptcd;
	document.forms[0].modifyvalue.value = modifyvalue;
	document.forms[0].submit();
}

function igroup_t_updown(mstobj,img){
	if (mstobj.up == "0"){
		mstobj.up = "1";
		img.src = "/formation/xtree/images/Lminus.png";
		mstobj.style.display = "block";
	} else {
		mstobj.up = "0";
		img.src = "/formation/xtree/images/Lplus.png";
		mstobj.style.display = "none";
	}
	return false;
}

function showDeptInfoAll(obj) {
	var tr = document.getElementsByTagName("tr");
	for ( var i = 0; i < tr.length; i++ ) {
		if ( tr[i].style.display.trim().toLowerCase() == "block" || tr[i].style.display.trim().toLowerCase() == "none" ) {
			tr[i].style.display = ( obj.checked ) ? "block" : "none";
		}
	}

	var img = document.getElementsByTagName("img");
	for ( var i = 0; i < img.length; i++ ) {
		if ( img[i].src.trim().indexOf("Lplus.png") != -1 || img[i].src.trim().indexOf("Lminus.png") != -1 ) {
			img[i].src = ( obj.checked ) ? "/formation/xtree/images/Lminus.png" : "/formation/xtree/images/Lplus.png";
		}
	}
}

function showMessageSend() {
	var viewmode = document.forms[0].viewmode;
	var receiveTarget = document.forms[0].receiveTarget;
	var title = document.forms[0].title;
	var state01 = document.forms[0].state01;	//배부대기(01)
	var state02 = document.forms[0].state02;	//입력진행(02)
	var state05 = document.forms[0].state05;	//제출완료(05)
		
	receiveTarget[0].checked = false;
	receiveTarget[1].checked = false;
	receiveTarget[2].checked = false;
	receiveTarget[3].checked = false;
	if ( state01 != undefined ) {
		if ( state01.length == undefined ) {
			if ( state01.checked == true ) {
				receiveTarget[0].checked = true;
			}
		} else {
			for ( var i = 0; i < state01.length; i++ ) {
				if ( state01[i].checked == true ) {
					receiveTarget[0].checked = true;
					break;
				}
			}		
		}
	}
	if ( state02 != undefined ) {
		if ( state02.length == undefined ) {
			if ( state02.checked == true ) {
				receiveTarget[0].checked = true;
			}
		} else {
			for ( var i = 0; i < state02.length; i++ ) {
				if ( state02[i].checked == true ) {
					receiveTarget[0].checked = true;
					break;
				}
			}		
		}
	}
	if ( state05 != undefined ) {
		if ( state05.length == undefined ) {
			if ( state05.checked == true ) {
				receiveTarget[0].checked = true;
			}
		} else {
			for ( var i = 0; i < state05.length; i++ ) {
				if ( state05[i].checked == true ) {
					receiveTarget[0].checked = true;
					break;
				}
			}		
		}
	}
	if ( receiveTarget[0].checked != true ) {
		if ( viewmode.value.trim() == "all" ) {
			receiveTarget[1].checked = true;
			receiveTarget[2].checked = true;
			receiveTarget[3].checked = true;
		} else if ( viewmode.value.trim() == "comp" ) {
			receiveTarget[3].checked = true;
		} else if ( viewmode.value.trim() == "nocomp" ) {
			receiveTarget[1].checked = true;
			receiveTarget[2].checked = true;
		}
	}
	
	if ( messageBox.style.display != "block" ) {
		messageBox.style.display = "block";
		title.focus();
	} else {
		messageBox.style.display = "none";
	}
}

function messageSend() {
	var receiveTarget = document.forms[0].receiveTarget;
	var target = document.forms[0].target;
	var title = document.forms[0].title;
	var content = document.forms[0].content;
	var state01 = document.forms[0].state01;	//배부대기
	var state02 = document.forms[0].state02;	//입력진행
	var state05 = document.forms[0].state05;	//제출완료
	
	if ( receiveTarget[0].checked != true &&
			receiveTarget[1].checked != true &&
			receiveTarget[2].checked != true &&
			receiveTarget[3].checked != true ) {
		alert("수신대상을 선택하세요");
		receiveTarget[0].focus();
		return;
	} else if ( title.value.trim() == "" ) {
		alert("제목을 입력하세요");
		title.focus();
		return;
	} else if ( content.value.trim() == "" ) {
		alert("내용을 입력하세요");
		content.focus();
		return;
	}
	
	target.value = "";
	var deptTarget = document.forms[0].sysdocno.value + ",";
	if ( receiveTarget[0].checked == true ) {
		if ( state01 != undefined ) {
			if ( state01.length == undefined ) {
				if ( state01.checked == true ) {
					deptTarget += state01.value + ",";
				}
			} else {
				for ( var i = 0; i < state01.length; i++ ) {
					if ( state01[i].checked == true ) {
						deptTarget += state01[i].value + ",";
					}
				}		
			}
		}
		if ( state02 != undefined ) {
			if ( state02.length == undefined ) {
				if ( state02.checked == true ) {
					target.value += state02.value + ",";
				}
			} else {
				for ( var i = 0; i < state02.length; i++ ) {
					if ( state02[i].checked == true ) {
						target.value += state02[i].value + ",";
					}
				}		
			}
		}
		if ( state05 != undefined ) {
			if ( state05.length == undefined ) {
				if ( state05.checked == true ) {
					target.value += state05.value + ",";
				}
			} else {
				for ( var i = 0; i < state05.length; i++ ) {
					if ( state05[i].checked == true ) {
						target.value += state05[i].value + ",";
					}
				}		
			}
		}
	} else {
		if ( receiveTarget[1].checked == true && state01 != undefined ) {
			if ( state01.length == undefined ) {
				deptTarget += state01.value + ",";
			} else {
				for ( var i = 0; i < state01.length; i++ ) {
					deptTarget += state01[i].value + ",";
				}		
			}
		}
		if ( receiveTarget[2].checked == true && state02 != undefined) {
			if ( state02.length == undefined ) {
				target.value += state02.value + ",";
			} else {
				for ( var i = 0; i < state02.length; i++ ) {
					target.value += state02[i].value + ",";
				}		
			}
		}
		if ( receiveTarget[3].checked == true && state05 != undefined ) {
			if ( state05.length == undefined ) {
				target.value += state05.value + ",";
			} else {
				for ( var i = 0; i < state05.length; i++ ) {
					target.value += state05[i].value + ",";
				}		
			}
		}
	}

	var callBack = function(resultXML) {
		try {
			var xmlDoc = resultXML.responseXML;
			var resultNode = xmlDoc.getElementsByTagName('result');

			var returnCode = resultNode[0].childNodes[0].text;
			if ( returnCode.trim() == "0" ) {
				alert("메세지가 발송되었습니다");
				showMessageSend();
			} else {
				alert("메세지가 발송되지 않았습니다. 잠시 후 다시 시도하세요");
			}
		} catch ( exception ) {}
	}
	var params = "target=" + encodeURIComponent(target.value) +
							"&title=" + encodeURIComponent(title.value) +
							"&content=" + encodeURIComponent(content.value) +
							"&deptTarget=" + encodeURIComponent(deptTarget);
	new Ajax.Request("/messageSend.do", {method: "post", postBody: params, onComplete: callBack});
}

function messageTagetCheckAll(obj) {
	var chk = document.getElementsByTagName("input")
	for ( var i = 0; i < chk.length; i++ ) {
		if ( chk[i].type == "checkbox" && chk[i].name.substring(0, 5) == "state" ) {
			chk[i].checked = obj.checked;
		}
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="fullSizeWindow()">
<html:form method="POST" action="/collprocDeptStatus">
<html:hidden property="sysdocno" />
<html:hidden property="mode" />
<html:hidden property="viewmode" />
<html:hidden property="modifydept" />
<html:hidden property="modifyvalue" />
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"><img src="/images/common/popup_r_01.gif" alt=""></td>
    <td width="744" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_02.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" width="5"></td>
    <td width="13"></td>
    <td width="744" valign="top" height="514">
      <table width="744" border="0" cellspacing="0" cellpadding="0">
        <!--타이틀-->
        <tr>
        	<td background="/images/common/title_bg.gif" height="38" width="644">
	        	<% if ( nexti.ejms.common.appInfo.isMessanger() ) { %>
	          	<img src="/images/collect/title_42.gif" width="176" height="38" alt="제출현황보기/메세지전송">
	          <% } else { %>
	          	<img src="/images/collect/title_38.gif" width="103" height="38" alt="제출현황보기">
	          <% } %>
	      	</td>
        </tr>
        <tr> 
          <td height="11" width="644"></td>
        </tr>
        <tr> 
          <td valign="top"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="7" class="list_bg2"></td>
              </tr>
              <tr> 
                <td width="90" class="s1"><a href="javascript:submit_view('all')" class="s1Black"><img src="/images/common/btn_status_all.gif" width="70" height="19" alt="제출대상"></a></td>
                <td class="bg2"></td>
                <td colspan="5" class="t1"><logic:equal name="commTreatDeptStatusForm" property="viewmode" value="all"><b><font color="green"></logic:equal><bean:write name="commTreatDeptStatusForm" property="submitdepttotcount"/>개 부서</td>
              </tr>
              <tr> 
                <td colspan="7" class="bg1"></td>
              </tr>
              <tr> 
                <td width="90" class="s1"><a href="javascript:submit_view('comp')" class="s1Purple"><img src="/images/common/btn_status_comp.gif" width="70" height="19" alt="제출완료"></a></td>
                <td class="bg2"></td>
                <td colspan="5" class="t1"><logic:equal name="commTreatDeptStatusForm" property="viewmode" value="comp"><b><font color="blue"></logic:equal><bean:write name="commTreatDeptStatusForm" property="submitdeptcompcount"/>개 부서</td>
              </tr>
              <tr> 
                <td colspan="7" class="bg1"></td>
              </tr>
              <tr> 
                <td width="90" class="s1"><a href="javascript:submit_view('nocomp')" class="s1Red"><img src="/images/common/btn_status_nocomp.gif" width="70" height="19" alt="미제출"></a></td>
                <td class="bg2"></td>
                <td colspan="5" class="t1"><logic:equal name="commTreatDeptStatusForm" property="viewmode" value="nocomp"><b><font color="red"></logic:equal><bean:write name="commTreatDeptStatusForm" property="submitdeptnocompcount"/>개 부서</td>
              </tr>
              <tr> 
                <td colspan="7" class="list_bg2"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" width="644"></td>
        </tr>
        <tr> 
          <td valign="top" height="350"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="5" class="list_bg"></td>
              </tr>
              <tr> 
                <td colspan="5" height="1"></td>
              </tr>
              <tr> 
                <td width="430">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                		<tr>
                			<td width="75" valign="baseline" class="list_t"><input type="checkbox" style="border:0;background-color:transparent;vertical-align:-1;" onclick="showDeptInfoAll(this)" id="chk1"><label for="chk1">전체보기</label></td>
                			<td align="center" valign="baseline" class="list_t">
			                	<logic:equal name="commTreatDeptStatusForm" property="viewmode" value="all">제출대상부서</logic:equal>
			                	<logic:equal name="commTreatDeptStatusForm" property="viewmode" value="comp">제출완료부서</logic:equal>
			                	<logic:equal name="commTreatDeptStatusForm" property="viewmode" value="nocomp">미제출부서</logic:equal>
			                	<% if ( nexti.ejms.common.appInfo.isMessanger() ) { %>
			                		(<input type="checkbox" style="border:0;background-color:transparent;vertical-align:-1;" onclick="messageTagetCheckAll(this)" checked id="chk2"><label for="chk2">전체메세지전송</label>)
			                	<% } %>
			                </td>
			              </tr>
			            </table>
                </td>
                <td class="list_t" width="60">배부자</td>
                <td class="list_t" width="120"><logic:notEqual name="commTreatDeptStatusForm" property="viewmode" value="nocomp">제출일시</logic:notEqual></td>
                <td class="list_t" width="60">진행상태</td>
                <td valign="top" class="list_t">
                	<logic:notEqual name="commTreatDeptStatusForm" property="viewmode" value="nocomp">
                		<input type="checkbox" style="border:0;background-color:transparent;vertical-align:-1;" onclick="checkAll_modifyyn(this.checked, this, 'list[]')" <%=(String)request.getAttribute("checkAll")%> id="chk3"><label for="chk3">수정권한</label>
                	</logic:notEqual>
                </td>
              </tr>
              <tr> 
                <td colspan="5" height="1"></td>
              </tr>
              <tr> 
                <td colspan="5" class="list_bg"></td>
              </tr>
              <tr> 
                <td colspan="5" height="1"></td>
              </tr>
              <tr>
              	<td colspan="5">
              		<div style="overflow-y:auto; height:320; position: absolute">
             			<table width="744" border="0" cellspacing="0" cellpadding="0">
           				<logic:notEmpty name="commTreatDeptStatusForm" property="deptlist">
										<bean:define id="deptlist" name="commTreatDeptStatusForm" property="deptlist"/>	
										<bean:define id="viewmode" name="commTreatDeptStatusForm" property="viewmode"/>
										<bean:define id="docstate" name="commTreatDeptStatusForm" property="docstate"/>	
										<tr>
											<td width="744" valign="top" id="tbl"><%=nexti.ejms.util.commfunction.chartList((List)deptlist, viewmode.toString(), docstate.toString())%></td>
										</tr>
	           			</logic:notEmpty>
           				<logic:empty name="commTreatDeptStatusForm" property="deptlist">
           					<tr onMouseOver="this.style.background='#FFFAD1'" onMouseOut="this.style.background='#ffffff'"> 
            					<td class="list_l">조회된 결과가 없습니다.</td>
            				</tr>
            				<tr>
            					<td class="list_bg2"></td>
            				</tr>
           				</logic:empty>
           				</table>
              		</div>
              	</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr> 
          <td height="10" width="744"></td>
        </tr>
        <tr> 
          <td valign="top" height="45"> 
            <table width="744" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/common/btn_r_01.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="734" colspan="2"></td>
                <td><img src="/images/common/btn_r_02.gif" width="5" height="5" alt=""></td>
              </tr>
              <tr bgcolor="#F6F6F6"> 
                <td bgcolor="#F6F6F6" width="5"></td>
                <td bgcolor="#F6F6F6" width="654">
                	<% if ( nexti.ejms.common.appInfo.isMessanger() ) { %>
	                	<img src="/images/common/btn_msg.gif" style="cursor:hand" alt="메세지전송" onclick="showMessageSend()">
	                	<div id="messageBox" style="border:medium ridge lightblue; background-color:white; top:270; position:absolute; display:none;">
	                		<input type="hidden" name="target">
		                	<table border="0" cellspacing="0" cellpadding="0">
											  <tr> 
											    <td bgcolor="#0098E6" colspan="5" height="5"></td>
											  </tr>
											  <tr> 
											    <td bgcolor="#0098E6" width="5"></td>
											    <td width="13"><img src="/images/common/popup_r_01.gif" alt=""></td>
											    <td height="13"></td>
											    <td width="13"><img src="/images/common/popup_r_02.gif" alt=""></td>
											    <td bgcolor="#0098E6" width="5"></td>
											  </tr>
											  <tr> 
											    <td bgcolor="#0098E6" width="5"></td>
											    <td width="13"></td>
											    <td valign="top"> 
				                		<table border="0" cellspacing="0" cellpadding="0">
								              <tr>
								                <td colspan="3" class="list_bg2"></td>
								              </tr>
								              <tr>
								                <td class="s1" width="69"><b>수신대상</b></td>
								                <td class="bg2"></td>
								                <td width="380">&nbsp;
									                <input type="checkbox" name="receiveTarget" style="border:0;background-color:transparent;" id="tgt1"><label for="tgt1">선택한전송대상</label>&nbsp;&nbsp;
								                	<input type="checkbox" name="receiveTarget" style="border:0;background-color:transparent;" id="tgt2"><label for="tgt2">배부대기</label>&nbsp;&nbsp;
								                	<input type="checkbox" name="receiveTarget" style="border:0;background-color:transparent;" id="tgt3"><label for="tgt3">입력진행</label>&nbsp;&nbsp;
								                	<input type="checkbox" name="receiveTarget" style="border:0;background-color:transparent;" id="tgt4"><label for="tgt4">제출완료</label>
								                </td>
								              </tr>
								              <tr> 
								                <td colspan="3" class="bg1"></td>
								              </tr>
								              <tr>
								                <td class="s1"><b>제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</b></td>
								                <td class="bg2"></td>
								                <td align="right"><input type="text" name="title" style="width:98%"></td>
								              </tr>
								              <tr> 
								                <td colspan="3" class="bg1"></td>
								              </tr>
								              <tr>
								                <td class="s1"><b>내&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용</b></td>
								                <td class="bg2"></td>
								                <td height="155" align="right"><textarea name="content" rows="10" style="width:98%"></textarea></td>
								              </tr>
								              <tr> 
								                <td colspan="3" class="list_bg2"></td>
								              </tr>
								              <tr> 
								                <td colspan="3" height="5"></td>
								              </tr>
								              <tr>
								                <td colspan="3" class="t1" align="right">
								                	<img src="/images/common/btn_msgsend.gif" style="cursor:hand" alt="발송" onclick="messageSend()">
							            				<img src="/images/common/btn_msgcancel.gif" style="cursor:hand" alt="취소" onclick="showMessageSend()">
								                </td>
								              </tr>
								            </table>
							            </td>
											    <td width="13"></td>
											    <td bgcolor="#0098E6" width="5"></td>
											  </tr>
											  <tr> 
											    <td bgcolor="#0098E6" width="5"></td>
											    <td width="13"><img src="/images/common/popup_r_03.gif" alt=""></td>
											    <td height="13"></td>
											    <td width="13"><img src="/images/common/popup_r_04.gif" alt=""></td>
											    <td bgcolor="#0098E6" width="5"></td>
											  </tr>
											  <tr> 
											    <td bgcolor="#0098E6" colspan="5" height="5"></td>
											  </tr>
											</table>     				
	                	</div>
                	<% } %>										
									<img src="/images/common/btn_excel.gif" height="27" style="cursor:hand" onclick="statusDownload();" alt="엑셀다운로드">
                </td>
                <td bgcolor="#F6F6F6" width="80" align="right" height="35"><a href="javascript:window.close()"><img src="/images/common/btn_close2.gif" border="0" alt="닫기"></a></td>
                <td width="5"></td>
              </tr>
              <tr> 
                <td><img src="/images/common/btn_r_03.gif" width="5" height="5" alt=""></td>
                <td bgcolor="#F6F6F6" height="5" width="634" colspan="2"></td>
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
    <td width="13"><img src="/images/common/popup_r_03.gif" alt=""></td>
    <td width="644" height="13"></td>
    <td width="13"><img src="/images/common/popup_r_04.gif" alt=""></td>
    <td bgcolor="#0098E6" width="5"></td>
  </tr>
  <tr> 
    <td bgcolor="#0098E6" colspan="5" height="5"></td>
  </tr>
</table>
</html:form>
<script>
function statusDownload() {
	if ( document.getElementById("tbl") == null ) {
		alert("조회된 결과가 없습니다");
		return;
	}

	var form1 = document.forms[0];
	var form2 = document.formatDataForm;
	var chk = document.getElementById("chk1");
	var prevChk = chk.checked;
	chk.checked = "true";
	showDeptInfoAll(chk);
	form2.sysdocno.value = form1.sysdocno.value;
	form2.formatData.value = document.getElementById("tbl").innerHTML;
	chk.checked = prevChk;
	showDeptInfoAll(chk);

	var format1 = document.createTextNode(form2.formatData.value);
	while ( true ) {
		var findIndex1 = -1;
		var findIndex2 = -1;
		var format2 = format1.data.toLowerCase();
		if ( (findIndex1 = format2.indexOf("<tr>\r\n<td class=list_bg2 colspan=5></td></tr>")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1 + "<tr>\r\n<td class=list_bg2 colspan=5></td></tr>".length) + ">".length;
			format1.replaceData(findIndex2, 0, "");
			format1.replaceData(findIndex1, "<tr>\r\n<td class=list_bg2 colspan=5></td></tr>".length, "");
			continue;
		} else if ( (findIndex1 = format2.indexOf("전체보기")) != -1 ) {
			findIndex2 = format2.indexOf("기", findIndex1) + ">".length;
			format1.replaceData(findIndex2, 0, "제출대상부서");
			format1.replaceData(findIndex1, "전체보기".length, "");
			continue;
		} else if ( (findIndex1 = format2.indexOf("(메세지전송)")) != -1 ) {
			findIndex2 = format2.indexOf(")", findIndex1) + ")".length;
		} else if ( (findIndex1 = format2.indexOf("<input")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("<img")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("<b")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("<font")) != -1 ) {
			findIndex2 = format2.indexOf(">", findIndex1) + ">".length;
		} else if ( (findIndex1 = format2.indexOf("this.style")) != -1 ) {
			findIndex2 = format2.indexOf("'\"", findIndex1) + "'\"".length;
		}
		if ( findIndex1 != -1 && findIndex2 != -1 ) {
			format1.replaceData(findIndex1, findIndex2 - findIndex1, "");
		} else {
			break;
		}
	}
	
	var title = "";
	if ( form1.viewmode.value.trim() == "all" ) {
		title = "<table><tr><td><b>제출대상부서</td><td><b>배부자</td><td><b>제출일시</td><td><b>진행상태</td></tr></table>";
	} else if ( form1.viewmode.value.trim() == "comp" ) {
		title = "<table><tr><td><b>제출완료부서</td><td><b>배부자</td><td><b>제출일시</td><td><b>진행상태</td></tr></table>";
	} else if ( form1.viewmode.value.trim() == "nocomp" ) {
		title = "<table><tr><td><b>미제출부서</td><td><b>배부자</td><td><b></td><td><b>진행상태</td></tr></table>";
	}

	form2.formatData.value = title + format1.data
	form2.submit();
}
</script>
<form name="formatDataForm" method="post" action="/xlsDownload.do" target="actionFrame">
	<input type="hidden" name="sysdocno">
	<input type="hidden" name="formatData">
</form>
<jsp:include page="/include/tail.jsp" flush="true"/>