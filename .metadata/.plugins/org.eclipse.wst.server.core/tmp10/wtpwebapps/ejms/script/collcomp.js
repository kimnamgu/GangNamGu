/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료
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
	var parentURL = "/collcomp";
	var addQueryString = "";
	
	if (args[0] == "01")			{childURL = "/formatLineView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString;
}

//취합마감해제 처리
function chkCancelProc(sysdocno){
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