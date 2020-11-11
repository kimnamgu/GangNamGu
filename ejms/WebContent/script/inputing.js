/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기
 * 설명:
 */
 
/**
 * 반환값 저장 변수
 */
var result;
var docno;
var move_path;
var chrgunitInputPagePath;
var changed = false;
var param = location.href.substring(location.href.indexOf("?"));

function collprocViewForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = collprocViewForm.arguments;
	var childURL = "";
	var parentURL = "/collproc";
	var addQueryString = "";

	if (args[0] == "01")			{childURL = "/formatLineOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	showPopup(parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString, '905', '600');
}

function viewForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inputFormatLineView.do";
	else if (args[0] == "02")	childURL = "/inputFormatFixedView.do";
	else if (args[0] == "03") childURL = "/inputFormatBookView.do";

	try {
		if(inputComplete == true) viewCompForm(args[0], args[1], args[2], args[3]);
		return;
	} catch (e) {}
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function viewCompForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewCompForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inputCompFormatLineView.do";
	else if (args[0] == "02")	childURL = "/inputCompFormatFixedView.do";
	else if (args[0] == "03") childURL = "/inputCompFormatBookView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function viewModifyForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewModifyForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inpCompFrmLineModifyView.do";
	else if (args[0] == "02")	childURL = "/inpCompFrmFixedModifyView.do";
	else if (args[0] == "03") childURL = "/inpCompFrmBookModifyView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function IsAssignChrgUnit(sysdocno) {
	var params = '';
	
	docno = sysdocno;
	
	result = new Ajax.Request('/isAssignChrgUnit.do', {method: 'post', postBody: params, onComplete: callBack});
}

function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	retCode = resultNode[0].childNodes[0].text;
	
	if(move_path == "notapplicable") {
		location.href = "/collDocNotApplicable.do" + param + "&sysdocno=" + docno;
	} else {
		location.href = "/inputingComplete.do" + param + "&sysdocno=" + docno;
	}
	/*
	if(retCode == "NOTASSIGN") {
		alert("입력담당자의 담당단위가 지정되지 않았습니다.\n담당단위를 지정하십시오.");
		location.href=chrgunitInputPagePath;
		return;
	} else {
		if(move_path == "notapplicable") {
			location.href = "/collDocNotApplicable.do" + param + "&sysdocno=" + docno;
		} else {
			location.href = "/inputingComplete.do" + param + "&sysdocno=" + docno;
		}
	}
	*/
}

function IsExistDataFrm(sysdocno, path) {
	var params = '';
	
	docno = sysdocno;
	move_path = path;
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno);
	
	result = new Ajax.Request('/isExistDataFrm.do', {method: 'post', postBody: params, onComplete: completeexecute});
}

function completeexecute(resXML){
	var xmlDoc = resXML.responseXML;
	var retCode;
	var cnf;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	retCode = resultNode[0].childNodes[0].text;
	
	if(retCode == "EXIST" && move_path == "notapplicable") {
		cnf = window.confirm("입력자료가 있습니다.\n입력자료없음 처리시 자료가 삭제됩니다.\n\n입력자료없음 처리 하시겠습니까?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "NOTEXIST" && move_path == "inputcomp") {
		cnf = window.confirm("입력자료가 없습니다.\n\n입력자료없음 처리 하시겠습니까?");
		
		if(cnf) {
			move_path = "notapplicable";
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "NOTEXIST" && move_path == "notapplicable") {
		cnf = window.confirm("입력자료없음 처리 하시겠습니까?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "EXIST" && move_path == "inputcomp") {
		cnf = window.confirm("입력자료제출 처리 하시겠습니까?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	}
}

function CollDocNotApplicable(sysdocno, _path) {
	chrgunitInputPagePath = _path;
	IsExistDataFrm(sysdocno, "notapplicable");
}

function InputingComplete(sysdocno, _path) {
	if(changed == true) {
		changeCheck(sysdocno);
	} else {
		chrgunitInputPagePath = _path;
		IsExistDataFrm(sysdocno, "inputcomp");
	}
}

function setChanged() {
	changed = true;
}

function changeCheck(sysdocno) {
	if(document.forms.length != 1) {
		return;
	}
	
	if(confirm("변경된 사항을 저장 하시겠습니까?") == false) {
		IsExistDataFrm(sysdocno, "inputcomp");
		return;
	}
	
	var frm = document.forms[0];
	
	if(frm.name.toLowerCase().indexOf("line") > -1) {
		document.images["savebutton"].click();
	} else if(frm.name.toLowerCase().indexOf("fixed") > -1) {
		document.forms[0].target = "actionFrame";
		document.forms[0].mode.value = "insert";
		document.forms[0].action += param;
		document.forms[0].submit();
	} else if(frm.name.toLowerCase().indexOf("book") > -1) {
	 	var title = document.dataBookForm.formtitle.value;
		if( title == "") {
			alert("제목을 입력하시기 바랍니다.");
			document.dataBookForm.formtitle.focus();
			return;
		} else if(title.length > 100){
			alert("제목의 입력내용이 자리수를  초과 하였습니다 .");
			document.dataBookForm.formtitle.focus();
			return;	
		}else if(document.dataBookForm.inputFile.value == "") {
			alert("업로드할 파일을 선택하여 주십시오.");
			return;
		} else {			
			document.dataBookForm.target = "actionFrame";
			document.dataBookForm.action += param;
			document.dataBookForm.submit();
		}
	}
}

function InputInsertDataBookFrm() {
 	var title = document.dataBookForm.formtitle.value;
	if( title == "") {
		alert("제목을 입력하시기 바랍니다.");
		document.dataBookForm.formtitle.focus();
		return;
	} else if(title.length > 100){
		alert("제목의 입력내용이 자리수를  초과 하였습니다 .");
		document.dataBookForm.formtitle.focus();
		return;	
	}else if(document.dataBookForm.inputFile.value == "") {
		alert("업로드할 파일을 선택하여 주십시오.");
		return;
	} else {
		if(window.confirm("등록하시겠습니까?") == false) {
			return;
		}		
		document.dataBookForm.target = "actionFrame";
		document.dataBookForm.action += param;
		document.dataBookForm.submit();
	}
}

function InputDeleteDataBookFrm(sysdocno, formseq, fileseq, filename) {
	var cnf = window.confirm("파일을 삭제하시겠습니까?\n한번 삭제된 데이터는 복구가 되지 않습니다.");
	
	if(cnf) {
		location.href = "/inputDataBookDelete.do" + param + "&sysdocno=" + sysdocno + "&formseq=" + formseq + "&fileseq=" + fileseq + "&filename=" + filename;
	}
}

function InputCompInsertDataBookFrm() {
	if(document.dataBookForm.formtitle.value == "") {
		alert("제목을 입력하시기 바랍니다.");
		document.dataBookForm.formtitle.focus();
		return;
	} else if(document.dataBookForm.inputFile.value == "") {
		alert("업로드할 파일을 선택하여 주십시오.");
		return;
	} else {
		document.dataBookForm.target = "actionFrame";
		document.dataBookForm.action += param;
		document.dataBookForm.submit();
	}
}

function InputCompDeleteDataBookFrm(sysdocno, formseq, fileseq, filename) {
	var cnf = window.confirm("파일을 삭제하시겠습니까?\n한번 삭제된 데이터는 복구가 되지 않습니다.");
	
	if(cnf) {
		location.href = "/inpCompDataBookDelete.do" + param + "&action=inpComp&sysdocno=" + sysdocno + "&formseq=" + formseq + "&fileseq=" + fileseq + "&filename=" + filename;
	}
}

var addCount = 0;
function getObj() { 
	var obj = event.srcElement ;
  while (obj.tagName != 'TD') obj = obj.parentElement;
  return obj;
}

function window::onload() {
	try {
		addCount = ((document.getElementById("datacnt").value > 0) ? (document.getElementById("datacnt").value - 1): 0);
	} catch(Exception) {}
}

function addRow() {
	var idx = getObj().parentElement.rowIndex + 2 + addCount; //늦게 입력한게 제일하단에 내려가도록 Idx를 구해온다.
	var r = tbl.insertRow(idx);
	addCount++;
	
	for (var i = 0; i < tbl.rows(0).cells.length; i++) {
		// 입력된 Row의 쉘객체를 가져온다.
		var bb = tbl.rows(0).cells(i);
		// Insert된 Row의 쉘객체를 가져온다.
		var c = tbl.rows(idx).insertCell(i);

		if (i == 0) { // 첫번째 쉘을 만들때 일련번호를 넣는다.
			c.align = 'center';
			c.innerHTML = addCount + 1;
		} else	{			
			c.align ='center';
			c.innerHTML = tbl.rows(1).cells(i).innerHTML;
		}
	}
}

function delRow() { 
	var idx = getObj().parentElement.rowIndex;

	if(addCount == 0) {
		alert("첫번째 행은 삭제할 수 없습니다.");
		return;
	}
	
	tbl.deleteRow(idx);
 	addCount--;
 	
 	// 순번을 초기화 시켜준다.
 	for (var i = 0;i <= addCount ; i++) {
 		tbl.rows(i+1).cells(0).innerHTML = i + 1;
 	}
}

function makeDraftDocument(sysdocno) {
	showModalPopup('/draftDocument.do?sysdocno=' + sysdocno, 800, 750, 0, 0);
}

function instantCalculation(objName, limit) {
	try {
		if ( document.getElementsByName(objName).length == 0 ) return;
	
		var colCount = document.getElementsByName(objName).length;
		var rowCount = document.getElementsByName("A").length;
		var calcCount = colCount / rowCount;
		var index = 0;
		if ( event == null ) {
			if ( limit != undefined && limit > 0) {
				colCount = colCount - (colCount / rowCount * (rowCount - limit))
				rowCount = limit;
				calcCount = colCount / rowCount;
				instantCalculation(objName, limit - 1);
			}
			index = rowCount - 1;
		} else {
			for ( var i = 0; i < colCount; i++ ) {
				if ( event.srcElement == document.getElementsByName(event.srcElement.name)[i] ) {
					index = i;
					break;
				}
			}
		}
			
		for ( var cnt = index * calcCount; cnt < index * calcCount + calcCount; cnt++ ) {
			var infoCell = document.getElementsByName(objName)[cnt];
			var cellname = infoCell.cellname;
			var formula = infoCell.title;
			var calcCell = document.getElementsByName(cellname)[cnt];
			
			for ( var i = 0; i < 52; i++ ) {
				var cell = "";
				if ( i < 26 ) {
					cell = String.fromCharCode("A".charCodeAt() + i);
				} else {
					cell = String.fromCharCode("A".charCodeAt() + i - 26) + String.fromCharCode("A".charCodeAt() + i - 26);
				}

				if ( formula.indexOf(cell) > -1 ) {
					if ( !( i < 26 && formula.substr(formula.indexOf(cell), 2) == cell + cell ) ) {
						var valueCell = document.getElementsByName(cell)[index];
						var val = valueCell.value.replace(/(^\s*)|(\s*$)/gi, "");
	
						if ( isNaN(val) == true || val == "" ) val = 0;
						if ( i < 26 ) {
							formula = formula.replace(new RegExp(cell + "{1}", "g"), "(" + val + ")");
						} else {
							formula = formula.replace(new RegExp(cell, "g"), "(" + val + ")");
						}

						try { eval(formula); break; } catch(exception) {}
					}
				}
			}
			infoCell.value = numberFormat(eval(formula));
		}
	} catch(exception) {}
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