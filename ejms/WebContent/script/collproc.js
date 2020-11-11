/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합진행
 * 설명:
 */

/**
 * 반환값 저장 변수
 */
var result;
var returnpage;
var formkind;

function viewForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "/collproc";
	var addQueryString = "";

	if (args[0] == "01")			{childURL = "/formatLineView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString;
}

function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.basicdate;			//기간시작
	var b = frm.enddt_date;			//기간종료
	
	if (a.onpropertychange == null) {
		a.onpropertychange = setSearchDate;
		b.onpropertychange = setSearchDate;
	} else if (event.propertyName.toLowerCase() == "value") {
		var src = event.srcElement;
		if (src == a) {
			if (a.value > b.value) {
				b.onpropertychange = null;
				b.value = a.value;
				b.onpropertychange = setSearchDate;
			}
		} else if (src == b) {
			if (a.value > b.value || a.value == "") {
				a.onpropertychange = null;
				a.value = b.value;
				a.onpropertychange = setSearchDate;
			}
		}
	}
}

function resetFile(objName) {
	document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}

function popup_open(sysdocno) {
	// 팝업으로 띄울 주소
	var url = "/collprocCloseView.do?sysdocno=" + sysdocno;
	
	// 팝업창 크기
	var win_width = "680";
	var win_height = "210";
	
	// 팝업창을 띄울 위치
	var left = (screen.availWidth - win_width)/2;
	var top = (screen.availHeight - win_height)/2;
	
	// 웹브라우져 버전 체크
	appVer=navigator.appVersion.substring(25,22);
	
	var varFeature = "dialogLeft:" +left + "px; dialogTop:" +top + "px; dialogWidth:"+win_width+"px;dialogHeight:"+win_height+"px;scroll:yes;status:yes;resizable:yes;help:no";
	try {
		window.showModalDialog(url, window, varFeature);
	} catch(exception) {
		alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
					"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
					"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
					"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
		try {processingHide();} catch(ex) {}
		try {parent.processingHide();} catch(ex) {}
	}	
}

function popup_designate(sysdocno) {
	// 팝업으로 띄울 주소
	var url = "/commapproval/designate.do?sysdocno=" + sysdocno + "&type=1&retid=sancusrnm";
	
	// 팝업창 크기
	var win_width = "660";
	var win_height = "400";
	
	// 팝업창을 띄울 위치
	var left = (screen.availWidth - win_width)/2;
	var top = (screen.availHeight - win_height)/2;
	
	// 웹브라우져 버전 체크
	appVer=navigator.appVersion.substring(25,22);
			
	if(appVer == "5.5" || appVer== "6.0"){  // 버전이 6.0 이거나 5.5 일때
	  window.open(url,"return_pop", "channelmode=0,left=" + left + ", top=" + top + ",width=" + win_width + ",height=" + win_height + ", fullscreen=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=0");
	} else if ( appVer == "7.0" ) {  // 7.0 일때
	  window.open(url, "return_pop", "left=" + left + ", top=" + top + ",width=" + win_width + ",height=" + win_height + ",toolbar=no,status=yes,scrollbars=yes,resizable=no,menubar=no");
	} else {
		window.open(url, "return_pop", "left=" + left + ", top=" + top + ",width=" + win_width + ",height=" + win_height + ",toolbar=no,status=yes,scrollbars=yes,resizable=no,menubar=no");
	}
}

//취합마감처리관련 아이콘변경
function changeicon(gubun,sysdocno){
	var changeicon = document.getElementById("changeicon");

	if(gubun == "1"){
		changeicon.innerHTML = "<a href='javascript:chkCancelProc1("+sysdocno+")'><img src='/images/common/btn_release.gif' width='68' height='22' border='0' alt='마감해제'></a>";
	}else{
		changeicon.innerHTML = "<a href='javascript:popup_open("+sysdocno+")'><img src='/images/common/btn_end.gif' width='68' height='22' border='0' alt='취합마감'></a>";
	} 
}

//기안취소처리 
function chkCancelProc(sysdocno, formseq, formkind, frmScrollTop, retpage)
{
	var cnf = window.confirm("기안을 취소하시겠습니까?");
	
	if(cnf) {
		location.href = "/collprocProcess.do?sysdocno=" + sysdocno + "&formseq=" + formseq + 
																				"&formkind=" + formkind + "&frmScrollTop=" + frmScrollTop + "&retpage="+ retpage;
	} else return;
}

	//취합마감해제 처리
function chkCancelProc1(sysdocno){

	var cnf = window.confirm("마감해제를 하시겠습니까?");
	
	if(cnf) {
		parent.actionFrame.location.href = "/collcompProcess.do?sysdocno=" + sysdocno + "&retpage=hidden";			
	} else return;
	
}
	
function getAbsoluteLeft(htmlObject) {
	var xPos = htmlObject.offsetLeft;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		xPos += temp.offsetLeft;
		temp = temp.offsetParent;
	}
	return xPos;
}

function getAbsoluteTop(htmlObject) {
	var yPos = htmlObject.offsetTop;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		yPos += temp.offsetTop;
		temp = temp.offsetParent;
	}
	return yPos;
}
	
function sort(order, column, startIdx) {
	var tblObj = document.getElementById("tbl");
	for(var i = startIdx; i < tblObj.rows.length - 1; i++) {
		for(var j = i + 1; j < tblObj.rows.length; j++) {
			if(order == "up") {
				if(tblObj.rows[i].cells[column].innerHTML < tblObj.rows[j].cells[column].innerHTML) {
					tblObj.moveRow(i, j);
					tblObj.moveRow(j - 1, i);
				}
			} else {
				if(tblObj.rows[i].cells[column].innerHTML > tblObj.rows[j].cells[column].innerHTML) {
					tblObj.moveRow(i, j);
					tblObj.moveRow(j - 1, i);
				}
			}
		}
	}
	
	if(order == "up") {
		event.srcElement.onclick = function() {sort('down', column, startIdx);};
	} else {
		event.srcElement.onclick = function() {sort('up', column, startIdx);};
	}
}

function commentView() {
	var divObj = document.getElementById("comment");
	var tblObj = document.getElementById("tbl");
	if(divObj.style.display == "none") {
		divObj.style.pixelLeft = getAbsoluteLeft(event.srcElement);
		divObj.style.pixelTop = getAbsoluteTop(event.srcElement) + event.srcElement.clientHeight;
		divObj.style.display = "block";
	} else {
		divObj.style.display = "none";
	}
}

function addColldocFile(actionpath) {
	document.forms[0].action = actionpath;
	document.forms[0].submit();
}

function delColldocFile(actionpath, fileseq) {
	document.forms[0].fileseq.value = fileseq;
	document.forms[0].action = actionpath;
	document.forms[0].submit();
}	


//검색 버튼 클릭시
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//조직도 페이지 호출시
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";		
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}

function sch_Reset() {
	var objName = event.srcElement.name;
	var no = objName.substring(objName.length - 1);
	
	if ( parseInt(no) <= 6 ) {
		for ( var i = parseInt(no) + 1; i <= 6; i++ ) {
			try { eval("document.forms[0].sch_deptcd" + i + "[0].selected = true") } catch (e) {}
		}
		document.forms[0].sch_chrgunitcd[0].selected = true;
		document.forms[0].sch_inputusrid[0].selected = true;
	} else if ( objName == "sch_chrgunitcd" ) {
		document.forms[0].sch_inputusrid[0].selected = true;
	}
}

function click_submit(actionpath) {
	document.forms[0].action = actionpath;
	document.forms[0].submit();
}

function click_delete(chk, actionpath)	{
	var chklst = document.getElementsByName(chk);
	
	for(var i = 0; i < chklst.length; i++)
		if(chklst[i].checked == true) {
			if(confirm("삭제 하시겠습니까?") == true)
				click_submit(actionpath);
				
			return;
		}
	alert("삭제할 문서를 선택하세요.");
}

function check_all(chk_parent, chk_child)	{
	var chkall = chk_parent;
	var chklst = document.getElementsByName(chk_child);
	
	for(var i = 0; i < chklst.length; i++)
		chklst[i].checked = chkall.checked;
}